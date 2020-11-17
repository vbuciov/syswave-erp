package com.syswave.forms.miempresa;

import com.syswave.entidades.common.Entidad;
import com.syswave.entidades.configuracion.Usuario_tiene_Permiso;
import com.syswave.entidades.miempresa.Horario;
import com.syswave.entidades.miempresa.Jornada;
import com.syswave.forms.common.JTableDataView;
import com.syswave.swing.models.POJOTableModel;
import com.syswave.forms.databinding.HorarioTableModel;
import com.syswave.forms.databinding.JornadaComboBoxModel;
import com.syswave.swing.table.editors.LookUpComboBoxTableCellEditor;
import com.syswave.swing.table.editors.SpinnerTableCellEditor;
import com.syswave.swing.table.renders.DateTimeTableCellRenderer;
import com.syswave.swing.table.renders.LookUpComboBoxTableCellRenderer;
import com.syswave.logicas.miempresa.HorarioBusinessLogic;
import com.syswave.logicas.miempresa.JornadasBusinessLogic;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SpinnerDateModel;
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
public class JHorarioDataView extends JTableDataView implements TableModelListener, IHorarioMediator
{

    private final int opLOAD = 0;
    private final int opINSERT_LIST = 1;
    private final int opUPDATE_LIST = 2;
    private final int opDELETE_LIST = 3;
    private final int opINSERT = 4;
    private final int opUPDATE = 5;
    private final int opSAVE = 6;

    private boolean can_browse, can_create, can_update, can_delete;
    private HorarioBusinessLogic horarios;
    private JornadasBusinessLogic jornadas;
    private HorarioTableModel bsHorarios;
    JornadaComboBoxModel bsJornadasRender;
    JornadaComboBoxModel bsJornadasEditor;

    TableColumn colJornada, colHoraInicio, colHoraFin;

    private HorarioSwingWorker swSecondPlane;
    List<Usuario_tiene_Permiso> permisos;

    //---------------------------------------------------------------------
    private void grantAllPermisions()
    {
        can_browse = true;
        can_create = true;
        can_update = true;
        can_delete = true;
    }

    //------------------------------------------------------------------
    public JHorarioDataView(IWindowContainer container)
    {
        super(container);
        initAttributes(container);
        grantAllPermisions();
    }

    //------------------------------------------------------------------
    public JHorarioDataView(IWindowContainer container, List<Usuario_tiene_Permiso> values)
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

        bsHorarios.setReadOnly(!can_update);
    }

    private void initAttributes(IWindowContainer container)
    {
        horarios = new HorarioBusinessLogic(container.getOrigenDatoActual().getNombre());
        jornadas = new JornadasBusinessLogic(container.getOrigenDatoActual().getNombre());
        bsJornadasRender = new JornadaComboBoxModel();
        bsJornadasEditor = new JornadaComboBoxModel();
        bsHorarios = new HorarioTableModel(new String[]
        {
            "ID Jornada:{id_jornada}",
            "Nombre:{nombre}",
            "Hora inicial:{hora_inicio}",
            "Hora final:{hora_fin}"
        }
        );
        bsHorarios.addTableModelListener(this);
        jtbData.setModel(bsHorarios);

        if (jtbData.getColumnCount() > 0)
        {
            colJornada = jtbData.getColumnModel().getColumn(0);
            colJornada.setCellRenderer(new LookUpComboBoxTableCellRenderer<Jornada>(bsJornadasRender));
            colJornada.setCellEditor(new LookUpComboBoxTableCellEditor<Jornada>(bsJornadasEditor));

            colHoraInicio = jtbData.getColumnModel().getColumn(2);
            colHoraInicio.setCellRenderer(new DateTimeTableCellRenderer("HH:mm"));
            colHoraInicio.setCellEditor(new SpinnerTableCellEditor(new SpinnerDateModel(new Date(), null, null, java.util.Calendar.HOUR_OF_DAY), "HH:mm"));
            colHoraInicio.setPreferredWidth(70);

            colHoraFin = jtbData.getColumnModel().getColumn(3);
            colHoraFin.setCellRenderer(new DateTimeTableCellRenderer("HH:mm"));
            colHoraFin.setCellEditor(new SpinnerTableCellEditor(new SpinnerDateModel(new Date(), null, null, java.util.Calendar.HOUR_OF_DAY), "HH:mm"));
            colHoraFin.setPreferredWidth(70);

            //Nota: Debido a los renders que se estan utilizando es necesario tener un renglón más alto.
            jtbData.setRowHeight((int) (jtbData.getRowHeight() * 1.5));
        }
    }

    //------------------------------------------------------------------
    @Override
    public void showDetail(Horario elemento)
    {
        JHorarioDetailView dialogo = new JHorarioDetailView(this);
        mainContainer.addWindow(dialogo);

        if (elemento != null)
            dialogo.prepareForModify(elemento);

        else
            dialogo.prepareForNew();

        mainContainer.showCenter(dialogo);
    }

    //------------------------------------------------------------------
    @Override
    public void doCreateProcess()
    {
        if (can_create)
            showDetail(null);
        else
            JOptionPane.showMessageDialog(this, "No cuenta con los permisos necesarios para INSERTAR elementos");
    }

    //------------------------------------------------------------------
    @Override
    public void doUpdateProcess()
    {
        if (can_update)
        {
            int index = jtbData.getSelectedRow();

            if (index >= 0)
            {
                index = jtbData.convertRowIndexToModel(index);
                showDetail(bsHorarios.getElementAt(index));
            }
        }
        else
            JOptionPane.showMessageDialog(this, "No cuenta con los permisos necesarios para ACTUALIZAR elementos");
    }

    //------------------------------------------------------------------
    @Override
    public void doDeleteProcess()
    {
        if (can_delete)
            if (JOptionPane.showConfirmDialog(this, String.format("Ha seleccionado %d registro(s) para ser eleiminado(s)\n¿Esta seguro de continuar?", jtbData.getSelectedRowCount())) == JOptionPane.OK_OPTION)
                if (swSecondPlane == null || swSecondPlane.isDone())
                {
                    List<Object> parametros = new ArrayList<Object>();
                    jbMessage.setText("Eliminando elemento(s)...");
                    swSecondPlane = new HorarioSwingWorker();
                    swSecondPlane.addPropertyChangeListener(this);
                    parametros.add(opDELETE_LIST);
                    int[] rowsHandlers = jtbData.getSelectedRows();
                    for (int i = 0; i < rowsHandlers.length; i++)
                    {
                        rowsHandlers[i] = jtbData.convertRowIndexToModel(rowsHandlers[i]);
                    }
                    parametros.add(bsHorarios.getElementsAt(rowsHandlers));
                    parametros.add(rowsHandlers);
                    swSecondPlane.execute(parametros);
                }
                else
                    JOptionPane.showMessageDialog(this, "No cuenta con los permisos necesarios para eliminar elementos");
    }

    //------------------------------------------------------------------
    @Override
    public void doRetrieveProcess()
    {
        if (can_browse)
        {
            if (swSecondPlane == null || swSecondPlane.isDone())
            {
                List<Object> parametros = new ArrayList<Object>();
                jbMessage.setText("Obteniendo horarios...");
                swSecondPlane = new HorarioSwingWorker();
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

    //------------------------------------------------------------------
    @Override
    public void doSaveProcess()
    {
        int elementosTotales = 0;
        List<Horario> agregados = new ArrayList<>();
        List<Horario> modificados = new ArrayList<>();
        List<Horario> datos = bsHorarios.getData();

        TableCellEditor editor = jtbData.getCellEditor();
        if (editor != null)
            editor.stopCellEditing();

        for (Horario elemento : datos)
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
                swSecondPlane = new JHorarioDataView.HorarioSwingWorker();
                swSecondPlane.addPropertyChangeListener(this);
                parametros.add(opSAVE);
                parametros.add(agregados);
                parametros.add(modificados);
                swSecondPlane.execute(parametros);
            }
            else
                JOptionPane.showMessageDialog(this, "No existen cambios a guardar");
    }

    //------------------------------------------------------------------
    @Override
    public void doOpenProcess()
    {
        if (can_update)
        {
            int index = jtbData.getSelectedRow();

            if (index >= 0)
            {
                index = jtbData.convertRowIndexToModel(index);
                showDetail(bsHorarios.getElementAt(index));
            }
        }
        else
            JOptionPane.showMessageDialog(this, "No cuenta con los permisos necesarios para ACTUALIZAR elementos");
    }

    //------------------------------------------------------------------
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

    //------------------------------------------------------------------
    @Override
    public void onAcceptNewElement(Horario nuevo)
    {
        if (swSecondPlane == null || swSecondPlane.isDone())
        {
            List<Object> parametros = new ArrayList<Object>();
            swSecondPlane = new HorarioSwingWorker();
            swSecondPlane.addPropertyChangeListener(this);
            parametros.add(opINSERT);
            parametros.add(nuevo);
            jbMessage.setText("Guardando...");
            swSecondPlane.execute(parametros);
        }
    }

    //------------------------------------------------------------------
    @Override
    public void onAcceptModifyElement(Horario modificado)
    {
        if (swSecondPlane == null || swSecondPlane.isDone())
        {
            List<Object> parametros = new ArrayList<Object>();
            swSecondPlane = new HorarioSwingWorker();
            swSecondPlane.addPropertyChangeListener(this);
            parametros.add(opUPDATE);
            parametros.add(modificado);
            jbMessage.setText("Guardando...");
            swSecondPlane.execute(parametros);
        }
    }

    //------------------------------------------------------------------
    @Override
    public List<Horario> obtenerHorario(Horario elemento)
    {
        Horario filtro = new Horario();
        filtro.setNombre(elemento.getNombre());
        return horarios.obtenerLista(filtro);
    }

    //------------------------------------------------------------------
    @Override
    public String obtenerOrigenDato()
    {
        return mainContainer.getOrigenDatoActual().getNombre();
    }

    //------------------------------------------------------------------
    private void setDisplayData(List<Horario> listaHorario, List<Jornada> listaJornada)
    {
        bsJornadasRender.setData(listaJornada);
        bsJornadasEditor.setData(listaJornada);

        bsHorarios.setData(listaHorario);
         printRecordCount();
        jbMessage.setText("Horarios obtenidos");
    }

    private void setDisplayData(Horario nuevo)
    {
        jbMessage.setText("Nuevo agregado");
        bsHorarios.addRow(nuevo);
        printRecordCount();
    }

    //------------------------------------------------------------------
    private void resetCurrentElement()
    {
        jbMessage.setText("Cambio guardado");
        bsHorarios.fireTableRowsUpdated(jtbData.getSelectedRow(), jtbData.getSelectedRow());
    }

    @Override
    public List<Jornada> obtenerJornadas()
    {
        return jornadas.obtenerLista();
    }

    //------------------------------------------------------------------
    private class HorarioSwingWorker extends SwingWorker<List<Object>, Void>
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
        protected List<Object> doInBackground() throws Exception
        {
            setProgress(50);
            if (!isCancelled() && arguments != null && arguments.size() > 0)
            {
                int opcion = (int) arguments.get(0); //Debe haber un entero en la primera posición

                switch (opcion)
                {
                    case opLOAD:
                        arguments.add(horarios.obtenerLista());
                        arguments.add(jornadas.obtenerLista());
                        break;
                    case opINSERT:
                        arguments.add(horarios.agregar((Horario) arguments.get(1)));
                        break;
                    case opUPDATE:
                        arguments.add(horarios.actualizar((Horario) arguments.get(1)));
                        break;
                    case opSAVE:
                        List<Horario> agregados = (List<Horario>) arguments.get(1);
                        List<Horario> modificados = (List<Horario>) arguments.get(2);
                        if (agregados.size() > 0)
                            arguments.add(horarios.agregar(agregados));
                        if (modificados.size() > 0)
                            arguments.add(horarios.actualizar(modificados));
                        break;
                    case opDELETE_LIST:
                        List<Horario> borrados = (List<Horario>) arguments.get(1);
                        arguments.add(horarios.borrar(borrados));
                        break;
                }

                setProgress(100);
                return arguments;
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
                                List<Horario> listaHorarios = (List<Horario>) results.get(1);
                                List<Jornada> listaJornadas = (List<Jornada>) results.get(2);
                                setDisplayData(listaHorarios, listaJornadas);
                                break;
                            case opINSERT:
                                if (horarios.esCorrecto())
                                    setDisplayData((Horario) results.get(1));
                                break;
                            case opUPDATE:
                                if (horarios.esCorrecto())
                                    resetCurrentElement();
                            case opSAVE:
                                jbMessage.setText(horarios.getMensaje());
                                if (horarios.esCorrecto())
                                    acceptChanges();
                                break;
                            case opDELETE_LIST:
                                if (horarios.esCorrecto())
                                {
                                    bsHorarios.removeRows((int[]) arguments.get(2));
                                     printRecordCount();
                                
                                    jbMessage.setText("Elemento(s) eliminado(s)");
                                }
                                else
                                    jbMessage.setText(horarios.getMensaje());
                                break;
                        }
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
