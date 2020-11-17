package com.syswave.forms.miempresa;

import com.syswave.entidades.common.Entidad;
import com.syswave.entidades.configuracion.Usuario_tiene_Permiso;
import com.syswave.entidades.miempresa.CondicionPago;
import com.syswave.entidades.miempresa.Valor;
import com.syswave.forms.common.JTableDataView;
import com.syswave.forms.databinding.CondicionPagoTableModel;
import com.syswave.swing.models.POJOTableModel;
import com.syswave.forms.databinding.ValorComboBoxModel;
import com.syswave.swing.table.editors.LookUpComboBoxTableCellEditor;
import com.syswave.swing.table.renders.LookUpComboBoxTableCellRenderer;
import com.syswave.logicas.miempresa.CondicionesPagoBusinessLogic;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
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
public class JCondicionPagoDataView extends JTableDataView implements ICondicionPagoMediator, TableModelListener
{

    private final int opLOAD = 0;
    /* private final int opINSERT_LIST = 1;
     private final int opUPDATE_LIST = 2;*/
    private final int opDELETE_LIST = 3;
    private final int opINSERT = 4;
    private final int opUPDATE = 5;
    private final int opSAVE = 6;

    private boolean can_browse, can_create, can_update, can_delete;

    CondicionPagoTableModel bsCondicionesPago;
    CondicionesPagoBusinessLogic condicionesPago;

    ValorComboBoxModel bsTiposCondicionRender;
    ValorComboBoxModel bsTiposCondicionEditor;

    TableColumn colNombre, colTipoCondicion;
    CondicionPagoSwingWorker swSecondPlane;
    List<Usuario_tiene_Permiso> permisos;
    String esquema;

    //---------------------------------------------------------------------
    private void grantAllPermisions()
    {
        can_browse = true;
        can_create = true;
        can_update = true;
        can_delete = true;
    }

    // -------------------------------------------------------------------
    public JCondicionPagoDataView(IWindowContainer container)
    {
        super(container);
        initAttributes();
        grantAllPermisions();
    }

    // -------------------------------------------------------------------
    public JCondicionPagoDataView(IWindowContainer container, List<Usuario_tiene_Permiso> values)
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

        // bsCategoriaes.setReadOnly(!can_update);
    }

    //---------------------------------------------------------------------
    private void initAttributes()
    {
        esquema = mainContainer.getOrigenDatoActual().getNombre();
        bsCondicionesPago = new CondicionPagoTableModel(new String[]
        {
            "Nombre:{nombre}", "Valor:{valor}",
            "Tipo Condición:{id_tipo_condicion}"
        });
        bsCondicionesPago.addTableModelListener(this);

        condicionesPago = new CondicionesPagoBusinessLogic(esquema);

        bsTiposCondicionEditor = new ValorComboBoxModel();
        bsTiposCondicionRender = new ValorComboBoxModel();

        jtbData.setModel(bsCondicionesPago);
        jtbData.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        if (jtbData.getColumnCount() > 0)
        {
            colNombre = jtbData.getColumnModel().getColumn(0);
            colNombre.setPreferredWidth(200);

            colTipoCondicion = jtbData.getColumnModel().getColumn(2);
            colTipoCondicion.setCellRenderer(new LookUpComboBoxTableCellRenderer(bsTiposCondicionRender));
            colTipoCondicion.setCellEditor(new LookUpComboBoxTableCellEditor(bsTiposCondicionEditor));
            colTipoCondicion.setPreferredWidth(250);

            //Nota: Debido a los renders que se estan utilizando es necesario tener un renglón más alto.
            jtbData.setRowHeight((int) (jtbData.getRowHeight() * 1.5));
        }
    }

    // -------------------------------------------------------------------
    @Override
    public void showDetail(CondicionPago elemento)
    {
        JCondicionPagoDetailView dialogo = new JCondicionPagoDetailView(this);
        mainContainer.addWindow(dialogo);

        if (elemento != null)
            dialogo.prepareForModify(elemento);

        else
            dialogo.prepareForNew();

        mainContainer.showCenter(dialogo);
    }

    // -------------------------------------------------------------------
    @Override
    public void onAcceptNewElement(CondicionPago nuevo)
    {
        if (swSecondPlane == null || swSecondPlane.isDone())
        {
            List<Object> parametros = new ArrayList<Object>();
            swSecondPlane = new CondicionPagoSwingWorker();
            swSecondPlane.addPropertyChangeListener(this);
            parametros.add(opINSERT);
            parametros.add(nuevo);

            jbMessage.setText("Guardando...");
            swSecondPlane.execute(parametros);
        }

    }

    // -------------------------------------------------------------------
    @Override
    public void onAcceptModifyElement(CondicionPago modificado)
    {
        if (swSecondPlane == null || swSecondPlane.isDone())
        {
            List<Object> parametros = new ArrayList<Object>();
            swSecondPlane = new CondicionPagoSwingWorker();
            swSecondPlane.addPropertyChangeListener(this);
            parametros.add(opUPDATE);
            parametros.add(modificado);

            jbMessage.setText("Guardando...");
            swSecondPlane.execute(parametros);
        }
    }

    //---------------------------------------------------------------------
    private void resetElement(CondicionPago value)
    {
        // value.acceptChanges();
        bsCondicionesPago.fireTableRowsUpdated(jtbData.getSelectedRow(), jtbData.getSelectedRow());
        jbMessage.setText("Cambio guardado");
    }

    // -------------------------------------------------------------------
    @Override
    public String obtenerOrigenDato()
    {
        return esquema;
    }

    // -------------------------------------------------------------------
    @Override
    public List<Valor> obtenerTiposCondicion()
    {
        return bsTiposCondicionEditor.getData();
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
                showDetail(bsCondicionesPago.getElementAt(index));
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
                    swSecondPlane = new CondicionPagoSwingWorker();
                    swSecondPlane.addPropertyChangeListener(this);
                    parametros.add(opDELETE_LIST);
                    int[] rowsHandlers = jtbData.getSelectedRows();
                    for (int i = 0; i < rowsHandlers.length; i++)
                    {
                        rowsHandlers[i] = jtbData.convertRowIndexToModel(rowsHandlers[i]);
                    }
                    parametros.add(bsCondicionesPago.getElementsAt(rowsHandlers));
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
                swSecondPlane = new CondicionPagoSwingWorker();
                swSecondPlane.addPropertyChangeListener(this);
                parametros.add(opLOAD);
                jbMessage.setText("Consultando información...");
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
        List<CondicionPago> modificados = new ArrayList<>();
        List<CondicionPago> agregados = new ArrayList<>();
        List<CondicionPago> datos = bsCondicionesPago.getData();

        tryStopDetailCellEditor(jtbData);

        for (CondicionPago elemento : datos)
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
                swSecondPlane = new CondicionPagoSwingWorker();
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

    //---------------------------------------------------------------------
    private void tryStopDetailCellEditor(JTable subDetailView)
    {
        if (subDetailView.isEditing())
        {
            TableCellEditor editor;
            editor = subDetailView.getCellEditor();
            if (editor != null)
                editor.stopCellEditing();
        }
    }

    // -------------------------------------------------------------------
    @Override
    public void doOpenProcess()
    {
        if (can_update)
        {
            int index = jtbData.getSelectedRow();

            if (index >= 0)
            {
                index = jtbData.convertRowIndexToModel(index);
                showDetail(bsCondicionesPago.getElementAt(index));
            }
        }
        else
            JOptionPane.showMessageDialog(this, "No cuenta con los permisos necesarios para ACTUALIZAR elementos");
    }

    // -------------------------------------------------------------------
    private void setDisplayData(List<CondicionPago> values, List<Valor> listaTiposCondicion)
    {
        bsCondicionesPago.setData(values);
        
        printRecordCount();

        if (listaTiposCondicion != null)
        {
            bsTiposCondicionEditor.setData(listaTiposCondicion);
            bsTiposCondicionRender.setData(listaTiposCondicion);
        }
        jbMessage.setText("Información obtenida");
    }

    //---------------------------------------------------------------------
    private void setDisplayData(CondicionPago value)
    {
        // value.acceptChanges();
        bsCondicionesPago.addRow(value);
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
    private class CondicionPagoSwingWorker extends SwingWorker<List<Object>, Void>
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
                        results.add(condicionesPago.obtenerLista());
                        results.add(condicionesPago.obtenerListaTiposCondicion());
                        break;

                    case opINSERT:
                        arguments.add(condicionesPago.agregar((CondicionPago) arguments.get(1)));
                        /*arguments.add(variantes.guardar((List<CondicionPagoVariante>)arguments.get(2), 
                         (List<CondicionPagoVariante>)arguments.get(3)));*/
                        break;

                    case opUPDATE:
                        arguments.add(condicionesPago.actualizar((CondicionPago) arguments.get(1)));
                        /*arguments.add(variantes.guardar((List<CondicionPagoVariante>)arguments.get(2), 
                         (List<CondicionPagoVariante>)arguments.get(3)));*/
                        break;

                    case opDELETE_LIST:
                        List<CondicionPago> selecteds = (List<CondicionPago>) arguments.get(1);
                        condicionesPago.borrar(selecteds);
                        break;

                    case opSAVE:
                        List<CondicionPago> adds = (List<CondicionPago>) arguments.get(1);
                        List<CondicionPago> modifieds = (List<CondicionPago>) arguments.get(2);
                        if (adds.size() > 0)
                            arguments.add(condicionesPago.agregar(adds));
                        if (modifieds.size() > 0)
                            arguments.add(condicionesPago.actualizar(modifieds));
                        break;

                    default:
                        results.add(condicionesPago.obtenerLista());
                        results.add(condicionesPago.obtenerListaTiposCondicion());
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
                                List<CondicionPago> listaTipos = (List<CondicionPago>) results.get(1);
                                List<Valor> listaTiposCondicion = (List<Valor>) results.get(2);
                                if (listaTiposCondicion.size() > 0)
                                    setDisplayData(listaTipos, listaTiposCondicion);
                                else if (!condicionesPago.esCorrecto())
                                    JOptionPane.showMessageDialog(null, condicionesPago.getMensaje());
                                break;

                            case opINSERT:
                                if (condicionesPago.esCorrecto())
                                    setDisplayData((CondicionPago) arguments.get(1));

                                else
                                    jbMessage.setText(condicionesPago.getMensaje());
                                break;

                            case opUPDATE:
                                if (condicionesPago.esCorrecto())
                                    resetElement((CondicionPago) arguments.get(1));

                                else
                                    jbMessage.setText(condicionesPago.getMensaje());
                                break;

                            case opDELETE_LIST:
                                if (condicionesPago.esCorrecto())
                                {
                                    bsCondicionesPago.removeRows((int[]) arguments.get(2));
                                     printRecordCount();
                                
                                    jbMessage.setText("Elemento(s) eliminado(s)");
                                }
                                else
                                    jbMessage.setText(condicionesPago.getEsquema());
                                break;

                            case opSAVE:
                                jbMessage.setText(condicionesPago.getMensaje());
                                if (condicionesPago.esCorrecto())
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
