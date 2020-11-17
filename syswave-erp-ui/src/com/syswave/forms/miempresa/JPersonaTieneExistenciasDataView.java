package com.syswave.forms.miempresa;

import com.syswave.entidades.common.Entidad;
import com.syswave.entidades.configuracion.Usuario_tiene_Permiso;
import com.syswave.entidades.miempresa.ControlAlmacen;
import com.syswave.entidades.miempresa.Persona;
import com.syswave.entidades.miempresa.Persona_tiene_Existencia;
import com.syswave.forms.common.JTableDataView;
import com.syswave.forms.databinding.ControlAlmacenesComboBoxModel;
import com.syswave.swing.models.POJOTableModel;
import com.syswave.forms.databinding.PersonaTieneExistenciasTableModel;
import com.syswave.forms.databinding.PersonasComboBoxModel;
import com.syswave.swing.table.editors.LookUpComboBoxTableCellEditor;
import com.syswave.swing.table.renders.LookUpComboBoxTableCellRenderer;
import com.syswave.logicas.miempresa.ControlAlmacenesBusinessLogic;
import com.syswave.logicas.miempresa.PersonaTieneExistenciasBusinessLogic;
import com.syswave.logicas.miempresa.PersonasBusinessLogic;
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
public class JPersonaTieneExistenciasDataView extends JTableDataView implements TableModelListener, IPersonaTieneExistenciaMediator
{

    private final int opLOAD = 0;
    private final int opINSERT_LIST = 1;
    private final int opUPDATE_LIST = 2;
    private final int opDELETE_LIST = 3;
    private final int opINSERT = 4;
    private final int opUPDATE = 5;
    private final int opSAVE = 6;

    private boolean can_browse, can_create, can_update, can_delete;

    PersonaTieneExistenciasTableModel bsExistencias;
    PersonaTieneExistenciasBusinessLogic existencias;
    PersonasBusinessLogic personas;
    ControlAlmacenesBusinessLogic series;
    PersonasComboBoxModel bsPersonasRender;
    PersonasComboBoxModel bsPersonasEditor;
    ControlAlmacenesComboBoxModel bsSeriesRender;
    ControlAlmacenesComboBoxModel bsSeriesEditor;

    TableColumn colPersona;
    TableColumn colSerie;
    TableColumn colExistencia;
    PersonaTieneExistenciasSwingWorker swSecondPlane;
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
    public JPersonaTieneExistenciasDataView(IWindowContainer container)
    {
        super(container);
        initAttributes();
        grantAllPermisions();
    }

    // -------------------------------------------------------------------
    public JPersonaTieneExistenciasDataView(IWindowContainer container, List<Usuario_tiene_Permiso> values)
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

        bsExistencias.setReadOnly(!can_update);
    }

    //---------------------------------------------------------------------
    private void initAttributes()
    {
        bsExistencias = new PersonaTieneExistenciasTableModel(new String[]
        {
            "Persona:{id_persona}", "Serie:{id_serie}", "Existencia:{existencia}"
        });
        bsExistencias.addTableModelListener(this);
        bsPersonasRender = new PersonasComboBoxModel();
        bsPersonasEditor = new PersonasComboBoxModel();
        bsSeriesRender = new ControlAlmacenesComboBoxModel();
        bsSeriesEditor = new ControlAlmacenesComboBoxModel();
        personas = new PersonasBusinessLogic(mainContainer.getOrigenDatoActual().getNombre());
        existencias = new PersonaTieneExistenciasBusinessLogic(personas.getEsquema());
        series = new ControlAlmacenesBusinessLogic(personas.getEsquema());
        jtbData.setModel(bsExistencias);

        if (jtbData.getColumnCount() > 0)
        {
            colSerie = jtbData.getColumnModel().getColumn(1);
            colSerie.setPreferredWidth(340);
            colSerie.setCellRenderer(new LookUpComboBoxTableCellRenderer<ControlAlmacen>(bsSeriesRender));
            colSerie.setCellEditor(new LookUpComboBoxTableCellEditor<ControlAlmacen>(bsSeriesEditor));

            colPersona = jtbData.getColumnModel().getColumn(0);
            colPersona.setCellRenderer(new LookUpComboBoxTableCellRenderer<Persona>(bsPersonasRender));
            colPersona.setCellEditor(new LookUpComboBoxTableCellEditor<Persona>(bsPersonasEditor));

            colExistencia = jtbData.getColumnModel().getColumn(2);
            colExistencia.setPreferredWidth(10);

            //Nota: Debido a los renders que se estan utilizando es necesario tener un renglón más alto.
            jtbData.setRowHeight((int) (jtbData.getRowHeight() * 1.5));
        }
    }

    // -------------------------------------------------------------------
    @Override
    public void showDetail(Persona_tiene_Existencia elemento)
    {
        JPersonaTieneExistenciaDetailView dialogo = new JPersonaTieneExistenciaDetailView(this);
        mainContainer.addWindow(dialogo);

        if (elemento != null)
            dialogo.prepareForModify(elemento);

        else
            dialogo.prepareForNew();

        mainContainer.showCenter(dialogo);
    }

    // -------------------------------------------------------------------
    @Override
    public void onAcceptNewElement(Persona_tiene_Existencia nuevo)
    {
        if (swSecondPlane == null || swSecondPlane.isDone())
        {
            List<Object> parametros = new ArrayList<Object>();
            swSecondPlane = new PersonaTieneExistenciasSwingWorker();
            swSecondPlane.addPropertyChangeListener(this);
            parametros.add(opINSERT);
            parametros.add(nuevo);

            jbMessage.setText("Guardando...");
            swSecondPlane.execute(parametros);
        }

    }

    // -------------------------------------------------------------------
    @Override
    public void onAcceptModifyElement(Persona_tiene_Existencia modificado)
    {
        if (swSecondPlane == null || swSecondPlane.isDone())
        {
            List<Object> parametros = new ArrayList<Object>();
            swSecondPlane = new PersonaTieneExistenciasSwingWorker();
            swSecondPlane.addPropertyChangeListener(this);
            parametros.add(opUPDATE);
            parametros.add(modificado);

            jbMessage.setText("Guardando...");
            swSecondPlane.execute(parametros);
        }
    }

    //---------------------------------------------------------------------
    private void resetElement(Persona_tiene_Existencia value)
    {
        //value.acceptChanges();
        bsExistencias.fireTableRowsUpdated(jtbData.getSelectedRow(), jtbData.getSelectedRow());
        jbMessage.setText("Cambio guardado");
    }

    // -------------------------------------------------------------------
    @Override
    public List<Persona> obtenerPersonas()
    {
        return bsPersonasEditor.getData();
    }

    // -------------------------------------------------------------------
    @Override
    public List<ControlAlmacen> obtenerSeries()
    {
        return bsSeriesEditor.getData();
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
                showDetail(bsExistencias.getElementAt(index));
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
                    swSecondPlane = new PersonaTieneExistenciasSwingWorker();
                    swSecondPlane.addPropertyChangeListener(this);
                    parametros.add(opDELETE_LIST);
                    int[] rowsHandlers = jtbData.getSelectedRows();
                    for (int i = 0; i < rowsHandlers.length; i++)
                    {
                        rowsHandlers[i] = jtbData.convertRowIndexToModel(rowsHandlers[i]);
                    }
                    parametros.add(bsExistencias.getElementsAt(rowsHandlers));
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
                swSecondPlane = new PersonaTieneExistenciasSwingWorker();
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
        List<Persona_tiene_Existencia> modificados = new ArrayList<>();
        List<Persona_tiene_Existencia> agregados = new ArrayList<>();
        List<Persona_tiene_Existencia> datos = bsExistencias.getData();

        TableCellEditor editor = jtbData.getCellEditor();
        if (editor != null)
            editor.stopCellEditing();

        for (Persona_tiene_Existencia elemento : datos)
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
                swSecondPlane = new PersonaTieneExistenciasSwingWorker();
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
                showDetail(bsExistencias.getElementAt(index));
            }
        }
        else
            JOptionPane.showMessageDialog(this, "No cuenta con los permisos necesarios para ACTUALIZAR elementos");
    }

    // -------------------------------------------------------------------
    private void setDisplayData(List<ControlAlmacen> listaSeries, List<Persona> listaPersonas, List<Persona_tiene_Existencia> values)
    {
        bsSeriesEditor.setData(listaSeries);
        bsSeriesRender.setData(listaSeries);
        bsPersonasRender.setData(listaPersonas);
        bsPersonasEditor.setData(listaPersonas);
        bsExistencias.setData(values);

        printRecordCount();
        jbMessage.setText("Información obtenida");
    }

    //---------------------------------------------------------------------
    private void setDisplayData(Persona_tiene_Existencia value)
    {
        // value.acceptChanges();
        bsExistencias.addRow(value);
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

                //Se esta modificando la llave foranea primaria.
                if (e.getColumn() == colPersona.getModelIndex()
                        && !((Persona_tiene_Existencia) data).getHasOnePersona().isSet())
                    ((Persona_tiene_Existencia) data).getHasOnePersona().setModified();

                setChanges();
                jbMessage.setText("Cambios pendientes");
            }
        }
    }

    //---------------------------------------------------------------------
    //--  Esta clase controla el cargado en segundo plano de los origenes de datos.
    //---------------------------------------------------------------------
    private class PersonaTieneExistenciasSwingWorker extends SwingWorker<List<Object>, Void>
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
                        results.add(existencias.obtenerLista());
                        results.add(personas.obtenerLista());
                        results.add(series.obtenerListaSeriesCorta());
                        break;

                    case opINSERT:
                        results.add(existencias.agregar((Persona_tiene_Existencia) arguments.get(1)));
                        /*arguments.add(detail1.guardar((List<PersonaDireccion>)arguments.get(2), 
                         (List<PersonaDireccion>)arguments.get(3)));*/
                        break;

                    case opUPDATE:
                        results.add(existencias.actualizar((Persona_tiene_Existencia) arguments.get(1)));
                        /* arguments.add(detail1.guardar((List<PersonaDireccion>)arguments.get(2), 
                         (List<PersonaDireccion>)arguments.get(3)));*/
                        break;

                    case opDELETE_LIST:
                        List<Persona_tiene_Existencia> selecteds = (List<Persona_tiene_Existencia>) arguments.get(1);
                        results.add(existencias.borrar(selecteds));
                        break;

                    case opSAVE:
                        List<Persona_tiene_Existencia> adds = (List<Persona_tiene_Existencia>) arguments.get(1);
                        List<Persona_tiene_Existencia> modifieds = (List<Persona_tiene_Existencia>) arguments.get(2);
                        if (adds.size() > 0)
                            results.add(existencias.agregar(adds));
                        if (modifieds.size() > 0)
                            results.add(existencias.actualizar(modifieds));
                        break;

                    default:
                        results.add(existencias.obtenerLista());
                        results.add(personas.obtenerLista());
                        results.add(series.obtenerListaSeriesCorta());
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
                                List<Persona_tiene_Existencia> listaExistencias = (List<Persona_tiene_Existencia>) results.get(1);
                                List<Persona> listaPersonas = (List<Persona>) results.get(2);
                                List<ControlAlmacen> listaSeries = (List<ControlAlmacen>) results.get(3);
                                if (listaPersonas.size() > 0 && listaSeries.size() > 0)
                                    setDisplayData(listaSeries, listaPersonas, listaExistencias);
                                else if (!personas.esCorrecto())
                                    JOptionPane.showMessageDialog(null, personas.getMensaje());

                                else if (!series.esCorrecto())
                                    JOptionPane.showMessageDialog(null, series.getMensaje());
                                break;

                            case opINSERT:
                                if (existencias.esCorrecto())
                                    setDisplayData((Persona_tiene_Existencia) arguments.get(1));

                                else
                                    jbMessage.setText(existencias.getMensaje());
                                break;

                            case opUPDATE:
                                if (existencias.esCorrecto())
                                    resetElement((Persona_tiene_Existencia) arguments.get(1));

                                else
                                    jbMessage.setText(existencias.getMensaje());
                                break;

                            case opDELETE_LIST:
                                if (existencias.esCorrecto())
                                {
                                    bsExistencias.removeRows((int[]) arguments.get(2));
                                    printRecordCount();
                                
                                    jbMessage.setText("Elemento(s) eliminado(s)");
                                }
                                else
                                    jbMessage.setText(existencias.getEsquema());
                                break;

                            case opSAVE:
                                jbMessage.setText(existencias.getMensaje());
                                if (existencias.esCorrecto())
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
