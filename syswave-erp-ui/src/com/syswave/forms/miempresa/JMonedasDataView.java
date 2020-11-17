package com.syswave.forms.miempresa;

import com.syswave.entidades.common.Entidad;
import com.syswave.entidades.configuracion.Usuario_tiene_Permiso;
import com.syswave.entidades.miempresa.Moneda;
import com.syswave.forms.common.JTableDataView;
import com.syswave.forms.databinding.MonedasTableModel;
import com.syswave.swing.models.POJOTableModel;
import com.syswave.logicas.miempresa.MonedasBusinessLogic;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import com.syswave.forms.common.IWindowContainer;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class JMonedasDataView extends JTableDataView implements IMonedaMediator, TableModelListener
{

    private final int opLOAD = 0;
    private final int opINSERT_LIST = 1;
    private final int opUPDATE_LIST = 2;
    private final int opDELETE_LIST = 3;
    private final int opINSERT = 4;
    private final int opUPDATE = 5;
    private final int opSAVE = 6;

    private boolean can_browse, can_create, can_update, can_delete;

    MonedasTableModel bsMonedas;
    MonedasBusinessLogic monedas;

    TableColumn colNombre;
    TableColumn colSiglas;
    MonedaSwingWorker swSecondPlane;
    List<Usuario_tiene_Permiso> permisos;

    //---------------------------------------------------------------------
    private void grantAllPermisions()
    {
        can_browse = true;
        can_create = true;
        can_update = true;
        can_delete = true;
    }

    // -------------------------------------------------------------------
    public JMonedasDataView(IWindowContainer container)
    {
        super(container);
        initAttributes();
        grantAllPermisions();
    }

    // -------------------------------------------------------------------
    public JMonedasDataView(IWindowContainer container, List<Usuario_tiene_Permiso> values)
    {
        super(container);
        initAttributes();
        grant(values);
    }

    //---------------------------------------------------------------------
    /**
     * Este método sirve para asignar los permisos correspondientes.
     *
     * @param values
     */
    public final void grant(List<Usuario_tiene_Permiso> values)
    {
        if (permisos != null && permisos.size() > 0)
            permisos.clear();

        can_browse = false;
        can_create = false;
        can_update = false;
        can_delete = false;
        permisos = values;

        for (int i = 0; i < values.size(); i++)
        {
            if (values.get(i).getLlave().equals("browse"))
                can_browse = true;

            else if (values.get(i).getLlave().equals("create"))
                can_create = true;

            else if (values.get(i).getLlave().equals("update"))
                can_update = true;

            else if (values.get(i).getLlave().equals("delete"))
                can_delete = true;
        }

        bsMonedas.setReadOnly(!can_update);
    }

    private void initAttributes()
    {
        bsMonedas = new MonedasTableModel(new String[]
        {
            "Nombre:{nombre}", "Siglas:{siglas}"
        });
        bsMonedas.addTableModelListener(this);
        monedas = new MonedasBusinessLogic(mainContainer.getOrigenDatoActual().getNombre());
        jtbData.setModel(bsMonedas);

        if (jtbData.getColumnCount() > 0)
        {
            colNombre = jtbData.getColumnModel().getColumn(0);

            colSiglas = jtbData.getColumnModel().getColumn(1);

            //Nota: Debido a los renders que se estan utilizando es necesario tener un renglón más alto.
            //jtbData.setRowHeight( (int)(jtbData.getRowHeight() * 1.5));
        }
    }

    // -------------------------------------------------------------------
    @Override
    public void showDetail(Moneda elemento)
    {
        JMonedasDetailView dialogo = new JMonedasDetailView(this);
        mainContainer.addWindow(dialogo);

        if (elemento != null)
            dialogo.prepareForModify(elemento);

        else
            dialogo.prepareForNew();

        mainContainer.showCenter(dialogo);
    }

    // -------------------------------------------------------------------
    @Override
    public void onAcceptNewElement(Moneda nuevo)
    {
        if (swSecondPlane == null || swSecondPlane.isDone())
        {
            List<Object> parametros = new ArrayList<Object>();
            swSecondPlane = new MonedaSwingWorker();
            swSecondPlane.addPropertyChangeListener(this);
            parametros.add(opINSERT);
            parametros.add(nuevo);

            jbMessage.setText("Guardando...");
            swSecondPlane.execute(parametros);
        }

    }

    // -------------------------------------------------------------------
    @Override
    public void onAcceptModifyElement(Moneda modificado)
    {
        if (swSecondPlane == null || swSecondPlane.isDone())
        {
            List<Object> parametros = new ArrayList<Object>();
            swSecondPlane = new MonedaSwingWorker();
            swSecondPlane.addPropertyChangeListener(this);
            parametros.add(opUPDATE);
            parametros.add(modificado);

            jbMessage.setText("Guardando...");
            swSecondPlane.execute(parametros);
        }
    }

    //---------------------------------------------------------------------
    private void resetElement(Moneda value)
    {
        //value.acceptChanges();
        bsMonedas.fireTableRowsUpdated(jtbData.getSelectedRow(), jtbData.getSelectedRow());
        jbMessage.setText("Cambio guardado");
    }

    // -------------------------------------------------------------------
    @Override
    public void doCreateProcess()
    {
        if (can_create)
            showDetail(null);
        else
            JOptionPane.showMessageDialog(this, "No cuenta con los permisos necesarios para INSERTAR elementos");
    }

    // -------------------------------------------------------------------
    @Override
    public void doUpdateProcess()
    {
        if (can_update)
        {
            int index = jtbData.getSelectedRow();

            if (index >= 0)
            {
                index = jtbData.convertRowIndexToModel(index);
                showDetail(bsMonedas.getElementAt(index));
            }
        }
        else
            JOptionPane.showMessageDialog(this, "No cuenta con los permisos necesarios para ACTUALIZAR elementos");
    }

    // -------------------------------------------------------------------
    @Override
    public void doDeleteProcess()
    {
        if (can_delete)
        {
            if (JOptionPane.showConfirmDialog(this, String.format("Ha seleccionado %d registro(s) para ser eleiminado(s)\n¿Esta seguro de continuar?", jtbData.getSelectedRowCount())) == JOptionPane.OK_OPTION)
            {
                if (swSecondPlane == null || swSecondPlane.isDone())
                {
                    List<Object> parametros = new ArrayList<Object>();
                    swSecondPlane = new MonedaSwingWorker();
                    swSecondPlane.addPropertyChangeListener(this);
                    parametros.add(opDELETE_LIST);
                    int[] rowsHandlers = jtbData.getSelectedRows();
                    for (int i = 0; i < rowsHandlers.length; i++)
                    {
                        rowsHandlers[i] = jtbData.convertRowIndexToModel(rowsHandlers[i]);
                    }
                    parametros.add(bsMonedas.getElementsAt(rowsHandlers));
                    parametros.add(rowsHandlers);
                    jbMessage.setText("Eliminando elemento(s)...");
                    swSecondPlane.execute(parametros);
                }
            }
        }

        else
            JOptionPane.showMessageDialog(this, "No cuenta con los permisos necesarios para eliminar elementos");
    }

    // -------------------------------------------------------------------
    @Override
    public void doRetrieveProcess()
    {
        if (can_browse)
        {
            if (swSecondPlane == null || swSecondPlane.isDone())
            {
                List<Object> parametros = new ArrayList<Object>();
                swSecondPlane = new MonedaSwingWorker();
                swSecondPlane.addPropertyChangeListener(this);
                parametros.add(opLOAD);
                jbMessage.setText("Obteniendo información...");
                swSecondPlane.execute(parametros);
            }
        }
        else
            JOptionPane.showMessageDialog(this, "No cuenta con los permisos necesarios para acceder a la información");
    }

    // -------------------------------------------------------------------
    @Override
    public void doSaveProcess()
    {
        int elementosTotales = 0;
        List<Moneda> modificados = new ArrayList<>();
        List<Moneda> agregados = new ArrayList<>();
        List<Moneda> datos = bsMonedas.getData();

        TableCellEditor editor = jtbData.getCellEditor();
        if (editor != null)
            editor.stopCellEditing();

        for (Moneda elemento : datos)
        {
            if (elemento.isNew())
                agregados.add(elemento);

            else if (elemento.isModified())
                modificados.add(elemento);
        }

        elementosTotales = modificados.size() + agregados.size();

        if (elementosTotales > 0)
        {
            if (swSecondPlane == null || swSecondPlane.isDone())
            {
                List<Object> parametros = new ArrayList<Object>();
                swSecondPlane = new MonedaSwingWorker();
                swSecondPlane.addPropertyChangeListener(this);
                parametros.add(opSAVE);
                parametros.add(agregados);
                parametros.add(modificados);
                swSecondPlane.execute(parametros);
            }
        }
        else
            JOptionPane.showMessageDialog(this, "No existen cambios a guardar");

    }

    //-------------------------------------------------------------------
    @Override
    public void doOpenProcess()
    {
        if (can_update)
        {
            int index = jtbData.getSelectedRow();

            if (index >= 0)
            {
                index = jtbData.convertRowIndexToModel(index);
                showDetail(bsMonedas.getElementAt(index));
            }
        }
        else
            JOptionPane.showMessageDialog(this, "No cuenta con los permisos necesarios para ACTUALIZAR elementos");
    }

    // -------------------------------------------------------------------
    private void setDisplayData(List<Moneda> values)
    {
        bsMonedas.setData(values);
        
         printRecordCount();
        jbMessage.setText("Información obtenida");
    }

    //---------------------------------------------------------------------
    private void setDisplayData(Moneda value)
    {
        bsMonedas.addRow(value);
        jbMessage.setText("Nuevo agregado");
        printRecordCount();
    }

    //---------------------------------------------------------------------
    @Override
    public void tableChanged(TableModelEvent e)
    {
        //Nota: Cuando una columna comienza a editarse, también se dispara el evento... pero
        //no sirve de nada porque no marca la columna exacta.
        if (e.getType() == TableModelEvent.UPDATE && e.getColumn() != TableModelEvent.ALL_COLUMNS)
        {
            int row = e.getFirstRow();

            if (row != TableModelEvent.HEADER_ROW)
            {
                POJOTableModel model = (POJOTableModel) e.getSource();
                //String columnName = model.getColumnName(column);
                Entidad data = (Entidad) model.getElementAt(row);

                if (!data.isSet())
                    data.setModified();

                setChanges();
                jbMessage.setText("Cambios pendientes");
            }
        }
    }

    //---------------------------------------------------------------------
    //--  Esta clase controla el cargado en segundo plano de los origenes de datos.
    //---------------------------------------------------------------------
    private class MonedaSwingWorker extends SwingWorker<List<Object>, Void>
    {

        private List<Object> arguments;

        //------------------------------------------------------------------
        public void execute(List<Object> values)
        {
            arguments = values;
            execute();
        }

        //------------------------------------------------------------------
        @Override
        public List<Object> doInBackground()
        {
            setProgress(50);
            if (!isCancelled() && arguments != null && arguments.size() > 0)
            {
                int opcion = (int) arguments.get(0); //Debe haber un entero en la primera posición
                List<Object> results = new ArrayList<>();
                results.add(opcion);
                switch (opcion)
                {
                    case opLOAD:
                        results.add(monedas.obtenerLista());
                        break;

                    case opINSERT:
                        arguments.add(monedas.agregar((Moneda) arguments.get(1)));
                        /*arguments.add(direcciones.guardar((List<MonedaDireccion>)arguments.get(2), 
                         (List<MonedaDireccion>)arguments.get(3)));*/
                        break;

                    case opUPDATE:
                        arguments.add(monedas.actualizar((Moneda) arguments.get(1)));
                        /*arguments.add(direcciones.guardar((List<MonedaDireccion>)arguments.get(2), 
                         (List<MonedaDireccion>)arguments.get(3)));*/
                        break;

                    case opDELETE_LIST:
                        List<Moneda> selecteds = (List<Moneda>) arguments.get(1);
                        monedas.borrar(selecteds);
                        break;

                    case opSAVE:
                        List<Moneda> adds = (List<Moneda>) arguments.get(1);
                        List<Moneda> modifieds = (List<Moneda>) arguments.get(2);
                        if (adds.size() > 0)
                            arguments.add(monedas.agregar(adds));
                        if (modifieds.size() > 0)
                            arguments.add(monedas.actualizar(modifieds));
                        break;

                    default:
                        results.add(monedas.obtenerLista());
                        break;
                }

                setProgress(100);
                return results;
            }

            else
                return null;
        }

        //------------------------------------------------------------------
        @Override
        public void done()
        {
            try
            {
                if (!isCancelled())
                {
                    List<Object> results = get();

                    if (results.size() > 0)
                    {
                        int opcion = (int) results.get(0);

                        switch (opcion)
                        {
                            case opLOAD:
                                List<Moneda> listaUsuarios = (List<Moneda>) results.get(1);
                                if (listaUsuarios.size() > 0)
                                    setDisplayData(listaUsuarios);
                                else if (!monedas.esCorrecto())
                                    JOptionPane.showMessageDialog(null, monedas.getMensaje());
                                break;

                            case opINSERT:
                                if (monedas.esCorrecto())
                                    setDisplayData((Moneda) arguments.get(1));

                                else
                                    jbMessage.setText(monedas.getMensaje());
                                break;

                            case opUPDATE:
                                if (monedas.esCorrecto())
                                    resetElement((Moneda) arguments.get(1));

                                else
                                    jbMessage.setText(monedas.getMensaje());
                                break;

                            case opDELETE_LIST:
                                if (monedas.esCorrecto())
                                {
                                    bsMonedas.removeRows((int[]) arguments.get(2));
                                     printRecordCount();
                                
                                    jbMessage.setText("Elemento(s) eliminado(s)");
                                }
                                else
                                    jbMessage.setText(monedas.getEsquema());
                                break;

                            case opSAVE:
                                jbMessage.setText(monedas.getMensaje());
                                if (monedas.esCorrecto())
                                    acceptChanges();
                                break;

                        }

                        results.clear();
                    }
                }
            }
            catch (InterruptedException ignore)
            {
            }
            catch (java.util.concurrent.ExecutionException e)
            {
                String why;
                Throwable cause = e.getCause();
                if (cause != null)
                {
                    why = cause.getMessage();
                }
                else
                {
                    why = e.getMessage();
                }
                System.err.println("Error retrieving file: " + why);
            }
            finally
            {
                if (arguments != null && arguments.size() > 0)
                    arguments.clear();
            }
        }
    }

}
