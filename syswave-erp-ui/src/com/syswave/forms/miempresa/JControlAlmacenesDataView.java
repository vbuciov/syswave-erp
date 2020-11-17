package com.syswave.forms.miempresa;

import com.syswave.entidades.common.Entidad;
import com.syswave.entidades.configuracion.Usuario_tiene_Permiso;
import com.syswave.entidades.miempresa.ControlAlmacen;
import com.syswave.entidades.miempresa.ControlInventario;
import com.syswave.entidades.miempresa.Persona_tiene_Existencia;
import com.syswave.entidades.miempresa.Ubicacion;
import com.syswave.forms.common.JTableDataView;
import com.syswave.swing.models.POJOComboBoxModel;
import com.syswave.forms.databinding.ControlAlmacenTableModel;
import com.syswave.forms.databinding.ControlInventariosComboBoxModel;
import com.syswave.swing.models.POJOTableModel;
import com.syswave.forms.databinding.UbicacionesComboBoxModel;
import com.syswave.swing.table.editors.LookUpComboBoxTableCellEditor;
import com.syswave.swing.table.renders.LookUpComboBoxTableCellRenderer;
import com.syswave.logicas.miempresa.ControlAlmacenesBusinessLogic;
import com.syswave.logicas.miempresa.ControlInventariosBusinessLogic;
import com.syswave.logicas.miempresa.PersonaTieneExistenciasBusinessLogic;
import com.syswave.logicas.miempresa.PersonasBusinessLogic;
import com.syswave.logicas.miempresa.UbicacionesBusinessLogic;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.event.ChangeEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import com.syswave.forms.common.IWindowContainer;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public final class JControlAlmacenesDataView extends JTableDataView implements TableModelListener, IControlAlmacenMediator
{

    private final int opLOAD = 0;
    private final int opINSERT_LIST = 1;
    private final int opUPDATE_LIST = 2;
    private final int opDELETE_LIST = 3;
    private final int opINSERT = 4;
    private final int opUPDATE = 5;
    private final int opSAVE = 6;

    private boolean can_browse, can_create, can_update, can_delete;
    ControlAlmacenesBusinessLogic almacenes;
    PersonasBusinessLogic personas;
    UbicacionesBusinessLogic ubicaciones;
    ControlInventariosBusinessLogic lotes;
    PersonaTieneExistenciasBusinessLogic existencias;
    ControlAlmacenTableModel bsControlAlmacenes;
    ControlInventariosComboBoxModel bsLotesEditor;
    ControlInventariosComboBoxModel bsLotesRender;
    UbicacionesComboBoxModel bsUbicacionesEditor;
    UbicacionesComboBoxModel bsUbicacionesRender;
    ControlAlmacenSwingWorker swSecondPlane;
    List<Usuario_tiene_Permiso> permisos;
    String esquema;

    TableColumn colUbicacion, colLote, colCantidad, colSerie, 
                colObservaciones, colEtiqueta;

    //---------------------------------------------------------------------
    private void grantAllPermisions()
    {
        can_browse = true;
        can_create = true;
        can_update = true;
        can_delete = true;
    }

    //---------------------------------------------------------------------
    public JControlAlmacenesDataView(IWindowContainer container)
    {
        super(null, container);
        initAttributes(container);
        grantAllPermisions();
    }

    //---------------------------------------------------------------------
    public JControlAlmacenesDataView(IWindowContainer container, List<Usuario_tiene_Permiso> values)
    {
        super(null, container);
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

        bsControlAlmacenes.setReadOnly(!can_update);
    }

    //---------------------------------------------------------------------
    private void initAttributes(IWindowContainer container)
    {
        jtbData = crearTablaConSoporteCambios();
        setTable(jtbData);
        esquema = container.getOrigenDatoActual().getNombre();
        bsControlAlmacenes = new ControlAlmacenTableModel(new String[]
        {
            "Ubicacion:{id_ubicacion}", "Lote y/o Marca:{id_lote}",
            "Cantidad:{cantidad}", "Serie:{serie}", 
            "Observaciones:{observaciones}", 
            "Valor actual:{valor_acumulado}",
            "Unidad:{etiqueta_mantenimiento}"});
        bsControlAlmacenes.addTableModelListener(this);
        bsUbicacionesEditor = new UbicacionesComboBoxModel();
        bsUbicacionesRender = new UbicacionesComboBoxModel();
        bsLotesEditor = new ControlInventariosComboBoxModel();
        bsLotesRender = new ControlInventariosComboBoxModel();
        ubicaciones = new UbicacionesBusinessLogic(esquema);
        almacenes = new ControlAlmacenesBusinessLogic(esquema);
        personas = new PersonasBusinessLogic(esquema);
        lotes = new ControlInventariosBusinessLogic(esquema);
        existencias = new PersonaTieneExistenciasBusinessLogic(esquema);

        jtbData.setModel(bsControlAlmacenes);
        jtbData.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        if (jtbData.getColumnCount() > 0)
        {
            colUbicacion = jtbData.getColumnModel().getColumn(0);
            colUbicacion.setCellRenderer(new LookUpComboBoxTableCellRenderer<Ubicacion>(bsUbicacionesRender));
            colUbicacion.setCellEditor(new LookUpComboBoxTableCellEditor<Ubicacion>(bsUbicacionesEditor));
            colUbicacion.setPreferredWidth(180);

            colLote = jtbData.getColumnModel().getColumn(1);
            colLote.setCellRenderer(new LookUpComboBoxTableCellRenderer<ControlInventario>(bsLotesRender));
            colLote.setCellEditor(new LookUpComboBoxTableCellEditor<ControlInventario>(bsLotesEditor));
            colLote.setPreferredWidth(300);

            colCantidad = jtbData.getColumnModel().getColumn(2);
            colCantidad.setPreferredWidth(100);

            colSerie = jtbData.getColumnModel().getColumn(3);
            colSerie.setPreferredWidth(150);
            
            colObservaciones = jtbData.getColumnModel().getColumn(4);
            colObservaciones.setPreferredWidth(220);

            colEtiqueta = jtbData.getColumnModel().getColumn(6);
            colEtiqueta.setPreferredWidth(150);

            jtbData.setRowHeight((int) (jtbData.getRowHeight() * 1.5));
        }
    }

    //--------------------------------------------------------------------
    private JTable crearTablaConSoporteCambios()
    {
        return new JTable()
        {
            @Override // Always selectAll()
            public void editingStopped(ChangeEvent e)
            {
                jtbData_onCellValueChanged(e, getCellEditor());
                super.editingStopped(e);
            }
        };
    }

    //---------------------------------------------------------------------
    public void showDetail(ControlAlmacen elemento)
    {
        if (bsUbicacionesEditor.getData().size() > 0 && bsLotesEditor.getData().size() > 0)
        {
            JControlAlmacenesDetailView dialogo = new JControlAlmacenesDetailView(this);
            mainContainer.addWindow(dialogo);

            if (elemento != null)
                dialogo.prepareForModify(elemento);

            else
                dialogo.prepareForNew();

            mainContainer.showCenter(dialogo);
        }

        else
            JOptionPane.showMessageDialog(this, "Es necesario haber capturado ubicaciones y lotes", "Información", JOptionPane.WARNING_MESSAGE);
    }

    // -------------------------------------------------------------------
    @Override
    public void onAcceptNewElement(ControlAlmacen nuevo, List<Persona_tiene_Existencia> existencias)
    {
        if (swSecondPlane == null || swSecondPlane.isDone())
        {
            List<Object> parametros = new ArrayList<Object>();
            swSecondPlane = new ControlAlmacenSwingWorker();
            swSecondPlane.addPropertyChangeListener(this);
            parametros.add(opINSERT);
            parametros.add(nuevo);
            parametros.add(existencias);

            jbMessage.setText("Guardando...");
            swSecondPlane.execute(parametros);
        }

    }

    // -------------------------------------------------------------------
    @Override
    public void onAcceptModifyElement(ControlAlmacen modificado, List<Persona_tiene_Existencia> existencias, List<Persona_tiene_Existencia> existencias_borradas)
    {
        if (swSecondPlane == null || swSecondPlane.isDone())
        {
            List<Object> parametros = new ArrayList<Object>();
            swSecondPlane = new ControlAlmacenSwingWorker();
            swSecondPlane.addPropertyChangeListener(this);
            parametros.add(opUPDATE);
            parametros.add(modificado);
            parametros.add(existencias);
            parametros.add(existencias_borradas);

            jbMessage.setText("Guardando...");
            swSecondPlane.execute(parametros);
        }
    }

    //---------------------------------------------------------------------
    private void resetElement(ControlAlmacen value)
    {
        bsControlAlmacenes.fireTableRowsUpdated(jtbData.getSelectedRow(), jtbData.getSelectedRow());
        jbMessage.setText("Cambio guardado");
    }

    //-------------------------------------------------------------------
    @Override
    public String obtenerOrigenDato()
    {
        return esquema;
    }

    // -------------------------------------------------------------------
    @Override
    public List<ControlInventario> obtenerLotes()
    {
        return bsLotesEditor.getData();
    }

    // -------------------------------------------------------------------
    @Override
    public List<Ubicacion> obtenerUbicaciones()
    {
        return bsUbicacionesEditor.getData();
    }

    /*// -------------------------------------------------------------------
     @Override
     public String obtenerEtiqueta(int mantenimientoComo)
     {
     return bsControlAlmacenes.calcularEtiqueta(mantenimientoComo);
     }*/
    // -------------------------------------------------------------------
    @Override
    public List<Persona_tiene_Existencia> obtenerPersonaExistencias(ControlAlmacen valor)
    {
        Persona_tiene_Existencia filtro = new Persona_tiene_Existencia();
        filtro.setEntrada(valor.getEntrada_Viejo());
        filtro.setIdUbicacion(valor.getIdUbicacion_Viejo());
        return existencias.obtenerLista(filtro);
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
                showDetail(bsControlAlmacenes.getElementAt(index));
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
                    swSecondPlane = new ControlAlmacenSwingWorker();
                    swSecondPlane.addPropertyChangeListener(this);
                    parametros.add(opDELETE_LIST);
                    int[] rowsHandlers = jtbData.getSelectedRows();
                    for (int i = 0; i < rowsHandlers.length; i++)
                    {
                        rowsHandlers[i] = jtbData.convertRowIndexToModel(rowsHandlers[i]);
                    }
                    parametros.add(bsControlAlmacenes.getElementsAt(rowsHandlers));
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
                swSecondPlane = new ControlAlmacenSwingWorker();
                swSecondPlane.addPropertyChangeListener(this);
                parametros.add(opLOAD);
                jbMessage.setText("consultando información...");
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
        List<ControlAlmacen> modificados = new ArrayList<>();
        List<ControlAlmacen> agregados = new ArrayList<>();
        List<ControlAlmacen> datos = bsControlAlmacenes.getData();

        TableCellEditor editor = jtbData.getCellEditor();
        if (editor != null)
            editor.stopCellEditing();

        for (ControlAlmacen elemento : datos)
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
                swSecondPlane = new ControlAlmacenSwingWorker();
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
                showDetail(bsControlAlmacenes.getElementAt(index));
            }
        }
        else
            JOptionPane.showMessageDialog(this, "No cuenta con los permisos necesarios para ACTUALIZAR elementos");
    }

    // -------------------------------------------------------------------
    private void setDisplayData(List<Ubicacion> bienes, List<ControlInventario> lotes, List<ControlAlmacen> values)
    {
        bsUbicacionesEditor.setData(bienes);
        bsUbicacionesRender.setData(bienes);
        bsLotesEditor.setData(lotes);
        bsLotesRender.setData(lotes);
        bsControlAlmacenes.setData(values);
        
        printRecordCount();
        jbMessage.setText("Información obtenida");
    }
    
    //---------------------------------------------------------------------
    private void setDisplayData(ControlAlmacen value)
    {
        //value.acceptChanges();
        bsControlAlmacenes.addRow(value);
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

                //Se esta cambiando la llave foranea primaria.
                if (e.getColumn() == colUbicacion.getModelIndex()
                        && !((ControlAlmacen) data).isSet())
                    ((ControlAlmacen) data).setModified();

                setChanges();
                jbMessage.setText("Cambios pendientes");
            }
        }
    }

    //----------------------------------------------------------------------
    public void jtbData_onCellValueChanged(ChangeEvent e, TableCellEditor editor)
    {
        if (jtbData.getEditingColumn() == 1 && editor instanceof LookUpComboBoxTableCellEditor)
        {
            ComboBoxModel model = ((LookUpComboBoxTableCellEditor) editor).getModel();
            if (model instanceof POJOComboBoxModel)
            {
                POJOComboBoxModel<ControlInventario> POJOModel = (POJOComboBoxModel<ControlInventario>) model;

                if (POJOModel.getCurrent() != null)
                    bsControlAlmacenes.getElementAt(jtbData.getSelectedRow()).getHasOneControlInventario().getHasOneBienVariante().setMantenimientoComo(
                            POJOModel.getCurrent().getHasOneBienVariante().getMantenimientoComo());
                //jtbData.setValueAt(POJOModel.getCurrent().getFk_variante_id_unidad_masa().getNombre(), jtbPartes.getEditingRow(), jtbPartes.getColumnCount() - 1);
            }
        }
    }

    //---------------------------------------------------------------------
    //--  Esta clase controla el cargado en segundo plano las localidades.
    //---------------------------------------------------------------------
    private class ControlAlmacenSwingWorker extends SwingWorker<List<Object>, Void>
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
                        results.add(almacenes.obtenerListaConInfoSeries());
                        results.add(ubicaciones.obtenerListaHojas());
                        results.add(lotes.obtenerListaLotesCorta());
                        break;

                    case opINSERT:
                        results.add(almacenes.agregar((ControlAlmacen) arguments.get(1)));
                        results.add(existencias.guardar((List<Persona_tiene_Existencia>) arguments.get(2),
                                null));
                        break;

                    case opUPDATE:
                        results.add(almacenes.actualizar((ControlAlmacen) arguments.get(1)));
                        results.add(existencias.guardar((List<Persona_tiene_Existencia>) arguments.get(2),
                                (List<Persona_tiene_Existencia>) arguments.get(3)));
                        break;

                    case opDELETE_LIST:
                        List<ControlAlmacen> selecteds = (List<ControlAlmacen>) arguments.get(1);
                        results.add(almacenes.borrar(selecteds));
                        break;

                    case opSAVE:
                        List<ControlAlmacen> adds = (List<ControlAlmacen>) arguments.get(1);
                        List<ControlAlmacen> modifieds = (List<ControlAlmacen>) arguments.get(2);
                        if (adds.size() > 0)
                            results.add(almacenes.agregar(adds));
                        if (modifieds.size() > 0)
                            results.add(almacenes.actualizar(modifieds));
                        break;

                    default:
                        results.add(almacenes.obtenerListaConInfoSeries());
                        results.add(ubicaciones.obtenerLista());
                        results.add(lotes.obtenerListaLotesCorta());
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
                                List<ControlAlmacen> listaExistencias = (List<ControlAlmacen>) results.get(1);
                                List<Ubicacion> listaUbicaciones = (List<Ubicacion>) results.get(2);
                                List<ControlInventario> listaLotes = (List<ControlInventario>) results.get(3);

                                if (listaUbicaciones.size() > 0 && listaLotes.size() > 0)
                                    setDisplayData(listaUbicaciones, listaLotes, listaExistencias);
                                else if (!almacenes.esCorrecto())
                                    JOptionPane.showMessageDialog(null, almacenes.getMensaje());
                                break;

                            case opINSERT:
                                if (almacenes.esCorrecto())
                                    setDisplayData((ControlAlmacen) arguments.get(1));

                                else
                                    jbMessage.setText(almacenes.getMensaje());
                                break;

                            case opUPDATE:
                                if (almacenes.esCorrecto())
                                    resetElement((ControlAlmacen) arguments.get(1));

                                else
                                    jbMessage.setText(almacenes.getMensaje());
                                break;

                            case opDELETE_LIST:
                                if (almacenes.esCorrecto())
                                {
                                    bsControlAlmacenes.removeRows((int[]) arguments.get(2));
                                    printRecordCount();
                                
                                    jbMessage.setText("Elemento(s) eliminado(s)");
                                }
                                else
                                    jbMessage.setText(almacenes.getEsquema());
                                break;

                            case opSAVE:
                                jbMessage.setText(almacenes.getMensaje());
                                if (almacenes.esCorrecto())
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
