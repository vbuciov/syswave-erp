package com.syswave.forms.miempresa;

import com.syswave.entidades.common.Entidad;
import com.syswave.entidades.configuracion.Usuario_tiene_Permiso;
import com.syswave.entidades.miempresa.Horario;
import com.syswave.entidades.miempresa.Jornada;
import com.syswave.forms.common.JTableDataView;
import com.syswave.swing.models.POJOTableModel;
import com.syswave.forms.databinding.JornadaTableModel;
import com.syswave.logicas.miempresa.HorarioBusinessLogic;
import com.syswave.logicas.miempresa.JornadasBusinessLogic;
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
 * @author sis5
 */
public class JJornadaDataView extends JTableDataView implements IJornadaMediator, TableModelListener
{

    private final int opLOAD = 0;
    private final int opINSERT_LIST = 1;
    private final int opUPDATE_LIST = 2;
    private final int opDELETE_LIST = 3;
    private final int opINSERT = 4;
    private final int opUPDATE = 5;
    private final int opSAVE = 6;

    private boolean can_browse, can_create, can_update, can_delete;

    JornadaTableModel bsJornada;
    JornadasBusinessLogic jornadas;
    HorarioBusinessLogic horarios;
    JornadaSwingWorker swSecondPlane;
    List<Usuario_tiene_Permiso> permisos;

    TableColumn colNombre, colTiempoEfectivo, colDescripcion;

    //--------------------------------------------------------------------
    private void grantAllPermisions()
    {
        can_browse = true;
        can_create = true;
        can_update = true;
        can_delete = true;
    }

    //--------------------------------------------------------------------
    public JJornadaDataView(IWindowContainer container)
    {
        super(container);
        initAttributes(container);
        grantAllPermisions();
    }

    //--------------------------------------------------------------------
    public JJornadaDataView(IWindowContainer container, List<Usuario_tiene_Permiso> values)
    {
        super(container);
        initAttributes(container);
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

        bsJornada.setReadOnly(!can_update);
    }

    private void initAttributes(IWindowContainer container)
    {
        jornadas = new JornadasBusinessLogic(container.getOrigenDatoActual().getNombre());
        horarios = new HorarioBusinessLogic(jornadas.getEsquema());

        bsJornada = new JornadaTableModel(new String[]
        {
            "Nombre:{nombre}", "Tiempo efectivo:{tiempo_efectivo}", "Descripción:{descripcion}"
        });
        bsJornada.addTableModelListener(this);
        jtbData.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jtbData.setModel(bsJornada);

        if (jtbData.getColumnCount() > 0)
        {
            colNombre = jtbData.getColumnModel().getColumn(0);
            colNombre.setPreferredWidth(180);

            colTiempoEfectivo = jtbData.getColumnModel().getColumn(1);
            colTiempoEfectivo.setPreferredWidth(120);

            colDescripcion = jtbData.getColumnModel().getColumn(2);
            colDescripcion.setPreferredWidth(450);
        }
    }

    //--------------------------------------------------------------------
    @Override
    public void showDetail(Jornada elemento)
    {
        JJornadaDetailView dialogo = new JJornadaDetailView(this);
        mainContainer.addWindow(dialogo);

        if (elemento != null)
            dialogo.prepareForModify(elemento);

        else
            dialogo.prepareForNew();

        mainContainer.showCenter(dialogo);

    }

    //--------------------------------------------------------------------
    private void setDisplayData(Jornada nuevo)
    {
        jbMessage.setText("Nuevo agregado");
        bsJornada.addRow(nuevo);
        printRecordCount();
    }

    //--------------------------------------------------------------------
    private void setDisplayData(List<Jornada> values)
    {
        bsJornada.setData(values);
         printRecordCount();
        jbMessage.setText("Jordanas obtenidas");
    }

    //--------------------------------------------------------------------
    private void resetCurrentElement()
    {
        jbMessage.setText("Cambio guardado");
        bsJornada.fireTableRowsUpdated(jtbData.getSelectedRow(), jtbData.getSelectedRow());
    }

    //--------------------------------------------------------------------
    @Override
    public void doCreateProcess()
    {
        if (can_create)
            showDetail(null);

        else
            JOptionPane.showMessageDialog(this, "No cuenta con los permisos necesarios para INSERTAR elementos");
    }

    //--------------------------------------------------------------------
    @Override
    public void doUpdateProcess()
    {
        if (can_update)
        {
            int index = jtbData.getSelectedRow();

            if (index >= 0)
            {
                index = jtbData.convertRowIndexToModel(index);
                showDetail(bsJornada.getElementAt(index));
            }
        }
        else
            JOptionPane.showMessageDialog(this, "No cuenta con los permisos necesarios para ACTUALIZAR elementos");
    }

    //--------------------------------------------------------------------
    @Override
    public void doDeleteProcess()
    {
        if (can_delete)
            if (JOptionPane.showConfirmDialog(this, String.format("Ha seleccionado %d registro(s) para ser eleiminado(s)\n¿Esta seguro de continuar?", jtbData.getSelectedRowCount())) == JOptionPane.OK_OPTION)
                if (swSecondPlane == null || swSecondPlane.isDone())
                {
                    List<Object> parametros = new ArrayList<Object>();
                    jbMessage.setText("Eliminando elemento(s)...");
                    swSecondPlane = new JornadaSwingWorker();
                    swSecondPlane.addPropertyChangeListener(this);
                    parametros.add(opDELETE_LIST);
                    int[] rowsHandlers = jtbData.getSelectedRows();
                    for (int i = 0; i < rowsHandlers.length; i++)
                    {
                        rowsHandlers[i] = jtbData.convertRowIndexToModel(rowsHandlers[i]);
                    }
                    parametros.add(bsJornada.getElementsAt(rowsHandlers));
                    parametros.add(rowsHandlers);
                    swSecondPlane.execute(parametros);
                }
                else
                    JOptionPane.showMessageDialog(this, "No cuenta con los permisos necesarios para eliminar elementos");
    }

    //--------------------------------------------------------------------
    @Override
    public void doRetrieveProcess()
    {
        if (can_browse)
        {
            if (swSecondPlane == null || swSecondPlane.isDone())
            {
                List<Object> parametros = new ArrayList<Object>();
                jbMessage.setText("Jornadas obtenidas...");
                swSecondPlane = new JornadaSwingWorker();
                swSecondPlane.addPropertyChangeListener(this);
                parametros.add(opLOAD);
                swSecondPlane.execute(parametros);
            }
        }
        else
        {
            JOptionPane.showMessageDialog(this, "No cuenta con los permisos necesarios para acceder a la información");
        }
    }

    //--------------------------------------------------------------------
    @Override
    public void doSaveProcess()
    {
        int elementosTotales = 0;
        List<Jornada> agregados = new ArrayList<>();
        List<Jornada> modificados = new ArrayList<>();
        List<Jornada> datos = bsJornada.getData();

        TableCellEditor editor = jtbData.getCellEditor();
        if (editor != null)
            editor.stopCellEditing();

        for (Jornada elemento : datos)
        {
            if (elemento.isNew())
                agregados.add(elemento);
            else if (elemento.isModified())
                modificados.add(elemento);
        }

        elementosTotales = modificados.size() + agregados.size();

        if (elementosTotales > 0)
            if (swSecondPlane == null || swSecondPlane.isDone())
            {
                List<Object> parametros = new ArrayList<Object>();
                swSecondPlane = new JornadaSwingWorker();
                swSecondPlane.addPropertyChangeListener(this);
                parametros.add(opSAVE);
                parametros.add(agregados);
                parametros.add(modificados);
                swSecondPlane.execute(parametros);
            }
            else
                JOptionPane.showMessageDialog(this, "No existen cambios a guardar");
    }

    //--------------------------------------------------------------------
    @Override
    public void doOpenProcess()
    {
        if (can_update)
        {
            int index = jtbData.getSelectedRow();

            if (index >= 0)
            {
                index = jtbData.convertRowIndexToModel(index);
                showDetail(bsJornada.getElementAt(index));
            }
        }
        else
            JOptionPane.showMessageDialog(this, "No cuenta con los permisos necesarios para ACTUALIZAR elementos");
    }

    //--------------------------------------------------------------------
    @Override
    public void onAcceptNewElement(Jornada nuevo, List<Horario> listHorarios)
    {
        if (swSecondPlane == null || swSecondPlane.isDone())
        {
            List<Object> parametros = new ArrayList<Object>();
            jbMessage.setText("Guardando...");
            swSecondPlane = new JornadaSwingWorker();
            swSecondPlane.addPropertyChangeListener(this);
            parametros.add(opINSERT);
            parametros.add(nuevo);
            parametros.add(listHorarios);
            swSecondPlane.execute(parametros);
        }
    }

    //--------------------------------------------------------------------
    @Override
    public void onAcceptModifyElement(Jornada modificado, List<Horario> listHorarios, List<Horario> eliminados)
    {
        if (swSecondPlane == null || swSecondPlane.isDone())
        {
            List<Object> parametros = new ArrayList<Object>();
            jbMessage.setText("Guardando...");
            swSecondPlane = new JornadaSwingWorker();
            swSecondPlane.addPropertyChangeListener(this);
            parametros.add(opUPDATE);
            parametros.add(modificado);
            parametros.add(listHorarios);
            parametros.add(eliminados);
            swSecondPlane.execute(parametros);
        }
    }

    //--------------------------------------------------------------------
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

    //--------------------------------------------------------------------
    @Override
    public String obtenerOrigenDato()
    {
        return mainContainer.getOrigenDatoActual().getNombre();
    }

    //--------------------------------------------------------------------
    @Override
    public List<Jornada> obtenerJornada(Jornada elemento)
    {
        Jornada filtro = new Jornada();
        filtro.setNombre(elemento.getNombre());
        return jornadas.obtenerLista(filtro);
    }

    //--------------------------------------------------------------------
    @Override
    public List<Horario> obtenerHorarios(Jornada elemento)
    {
        Horario filtro = new Horario();

        filtro.setIdJornada(elemento.getId());

        return horarios.obtenerLista(filtro);
    }

    //--------------------------------------------------------------------
    private class JornadaSwingWorker extends SwingWorker<List<Object>, Void>
    {

        private List<Object> arguments;

        //------------------------------------------------------------------
        public void execute(List<Object> values)
        {
            arguments = values;
            execute();
        }

        //--------------------------------------------------------------------
        @Override
        protected List<Object> doInBackground() throws Exception
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
                        results.add(jornadas.obtenerLista());
                        break;
                    case opINSERT:
                        results.add(jornadas.agregar((Jornada) arguments.get(1)));
                        results.add(horarios.guardar((List<Horario>) arguments.get(2), null));
                        break;
                    case opUPDATE:
                        results.add(jornadas.actualizar((Jornada) arguments.get(1)));
                        results.add(horarios.guardar((List<Horario>) arguments.get(2), (List<Horario>) arguments.get(3)));
                        break;
                    case opSAVE:
                        List<Jornada> agregados = (List<Jornada>) arguments.get(1);
                        List<Jornada> modificados = (List<Jornada>) arguments.get(2);
                        if (agregados.size() > 0)
                            results.add(jornadas.agregar(agregados));
                        if (modificados.size() > 0)
                            results.add(jornadas.actualizar(modificados));
                        break;
                    case opDELETE_LIST:
                        List<Jornada> borrados = (List<Jornada>) arguments.get(1);
                        results.add(jornadas.borrar(borrados));
                        break;
                }

                setProgress(100);
                return results;
            }
            else
            {
                return null;
            }
        }

        //--------------------------------------------------------------------
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
                                List<Jornada> listaJornada = (List<Jornada>) results.get(1);
                                setDisplayData(listaJornada);
                                break;
                            case opINSERT:
                                if (jornadas.esCorrecto())
                                    setDisplayData((Jornada) arguments.get(1));
                                break;
                            case opUPDATE:
                                if (jornadas.esCorrecto())
                                    resetCurrentElement();
                                break;
                            case opSAVE:
                                jbMessage.setText(jornadas.getMensaje());
                                if (jornadas.esCorrecto())
                                    acceptChanges();
                                break;
                            case opDELETE_LIST:
                                if (jornadas.esCorrecto())
                                {
                                    bsJornada.removeRows((int[]) arguments.get(2));
                                     printRecordCount();
                                
                                    jbMessage.setText("Elemento(s) eliminado(s)");
                                }
                                else
                                    jbMessage.setText(jornadas.getMensaje());
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
                {
                    arguments.clear();
                }
            }
        }
    }
}
