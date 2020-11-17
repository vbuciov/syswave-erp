package com.syswave.forms.miempresa;

import com.syswave.entidades.common.Entidad;
import com.syswave.entidades.configuracion.Usuario_tiene_Permiso;
import com.syswave.entidades.miempresa.AreaPrecio;
import com.syswave.entidades.miempresa.BienVariante;
import com.syswave.entidades.miempresa.ControlPrecio;
import com.syswave.entidades.miempresa.DesgloseCosto;
import com.syswave.entidades.miempresa.Moneda;
import com.syswave.entidades.miempresa.Valor;
import com.syswave.entidades.miempresa_vista.DesgloseCosto_5FN;
import com.syswave.forms.common.JTableDataView;
import com.syswave.forms.databinding.BienVariantesComboBoxModel;
import com.syswave.forms.databinding.ControlPreciosTableModel;
import com.syswave.forms.databinding.MonedasComboBoxModel;
import com.syswave.swing.models.POJOTableModel;
import com.syswave.forms.databinding.AreasPreciosComboBoxModel;
import com.syswave.forms.databinding.ValorComboBoxModel;
import com.syswave.swing.table.editors.LookUpComboBoxTableCellEditor;
import com.syswave.swing.table.renders.LookUpComboBoxTableCellRenderer;
import com.syswave.logicas.miempresa.AreasPreciosBusinessLogic;
import com.syswave.logicas.miempresa.BienVariantesBusinessLogic;
import com.syswave.logicas.miempresa.ControlPreciosBusinessLogic;
import com.syswave.logicas.miempresa.DesgloseCostosBusinessLogic;
import com.syswave.logicas.miempresa.MonedasBusinessLogic;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.event.ChangeEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import com.syswave.forms.common.IWindowContainer;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class JControlPreciosDataView extends JTableDataView implements IControlPrecio, TableModelListener
{

    private final int opLOAD = 0;
    private final int opINSERT_LIST = 1;
    private final int opUPDATE_LIST = 2;
    private final int opDELETE_LIST = 3;
    private final int opINSERT = 4;
    private final int opUPDATE = 5;
    private final int opSAVE = 6;

    private boolean can_browse, can_create, can_update, can_delete;

    private BienVariantesComboBoxModel bsPresentacionesEditor;
    private BienVariantesComboBoxModel bsPresentacionesRender;
    /*private ValorComboBoxModel bsTipoPreciosRender;
    private ValorComboBoxModel bsTipoPreciosEditor;*/
    private MonedasComboBoxModel bsMonedasEditor;
    private MonedasComboBoxModel bsMonedasRender;
    private AreasPreciosComboBoxModel bsAreasPreciosEditor;
    private AreasPreciosComboBoxModel bsAreasPreciosRender;

    private ControlPreciosTableModel bsControlPrecios;
    private DesgloseCostosBusinessLogic desglose_costos;
    private ControlPreciosBusinessLogic precios;
    private MonedasBusinessLogic monedas;
    private BienVariantesBusinessLogic presentaciones;
    private AreasPreciosBusinessLogic areas_precios;

    private TableColumn colTipoPrecio, colPresentacion, colDescripcionPrecio,
            colBase, colMargen, colFactor, colFinal, colMonedas, colAreaPrecio;
    private ControlPrecioSwingWorker swSecondPlane;
    private List<Usuario_tiene_Permiso> permisos;

    //---------------------------------------------------------------------
    private void grantAllPermisions()
    {
        can_browse = true;
        can_create = true;
        can_update = true;
        can_delete = true;
    }

    // -------------------------------------------------------------------
    public JControlPreciosDataView(IWindowContainer container)
    {
        super(null, container);
        initAttributes();
        grantAllPermisions();
    }

    // -------------------------------------------------------------------
    public JControlPreciosDataView(IWindowContainer container, List<Usuario_tiene_Permiso> values)
    {
        super(null, container);
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

        bsControlPrecios.setReadOnly(!can_update);
    }

    // -------------------------------------------------------------------
    private void initAttributes()
    {
        jtbData = createJtbPrecios();
        setTable(jtbData);
        jtbData.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        bsControlPrecios = new ControlPreciosTableModel(new String[]
        {
            "Presentación:{id_variante}", "Descripción:{descripcion}", 
            "Costo Directo:{costo_directo}", "Margen Utilidad:{margen}", "Factor %:{factor}", 
            "Final:{precio_final}", "Moneda:{id_moneda}", "Área precio:{id_area_precio}"
        });
        bsControlPrecios.addTableModelListener(this);
        /*bsTipoPreciosRender = new ValorComboBoxModel();
        bsTipoPreciosEditor = new ValorComboBoxModel();*/
        bsMonedasEditor = new MonedasComboBoxModel();
        bsMonedasRender = new MonedasComboBoxModel();
        bsPresentacionesEditor = new BienVariantesComboBoxModel();
        bsPresentacionesRender = new BienVariantesComboBoxModel();
        bsAreasPreciosEditor = new AreasPreciosComboBoxModel();
        bsAreasPreciosRender = new AreasPreciosComboBoxModel();
        precios = new ControlPreciosBusinessLogic(mainContainer.getOrigenDatoActual().getNombre());
        monedas = new MonedasBusinessLogic(precios.getEsquema());
        presentaciones = new BienVariantesBusinessLogic(precios.getEsquema());
        areas_precios = new AreasPreciosBusinessLogic(precios.getEsquema());
        desglose_costos = new DesgloseCostosBusinessLogic(precios.getEsquema());
        jtbData.setModel(bsControlPrecios);

        if (jtbData.getColumnCount() > 0)
        {
            colPresentacion = jtbData.getColumnModel().getColumn(0);
            colPresentacion.setCellEditor(new LookUpComboBoxTableCellEditor<BienVariante>(bsPresentacionesEditor));
            colPresentacion.setCellRenderer(new LookUpComboBoxTableCellRenderer<BienVariante>(bsPresentacionesRender));
            colPresentacion.setPreferredWidth(250);

            colDescripcionPrecio = jtbData.getColumnModel().getColumn(1);
            colDescripcionPrecio.setPreferredWidth(130);

            colBase = jtbData.getColumnModel().getColumn(2);

            colMargen = jtbData.getColumnModel().getColumn(3);

            colFactor = jtbData.getColumnModel().getColumn(4);

            colFinal = jtbData.getColumnModel().getColumn(5);

            colMonedas = jtbData.getColumnModel().getColumn(6);
            colMonedas.setCellEditor(new LookUpComboBoxTableCellEditor<Moneda>(bsMonedasEditor));
            colMonedas.setCellRenderer(new LookUpComboBoxTableCellRenderer<Moneda>(bsMonedasRender));
            colMonedas.setPreferredWidth(80);

            /*colTipoPrecio = jtbData.getColumnModel().getColumn(7);
            colTipoPrecio.setCellEditor(new LookUpComboBoxTableCellEditor<Valor>(bsTipoPreciosEditor));
            colTipoPrecio.setCellRenderer(new LookUpComboBoxTableCellRenderer<Valor>(bsTipoPreciosRender));
            colTipoPrecio.setPreferredWidth(80);*/

            colAreaPrecio = jtbData.getColumnModel().getColumn(7);
            colAreaPrecio.setCellEditor(new LookUpComboBoxTableCellEditor<AreaPrecio>(bsAreasPreciosEditor));
            colAreaPrecio.setCellRenderer(new LookUpComboBoxTableCellRenderer<AreaPrecio>(bsAreasPreciosRender));
            colAreaPrecio.setPreferredWidth(200);

            //Nota: Debido a los renders que se estan utilizando es necesario tener un renglón más alto.
            jtbData.setRowHeight((int) (jtbData.getRowHeight() * 1.5));
        }
    }

    //--------------------------------------------------------------------
    private JTable createJtbPrecios()
    {
        return new JTable()
        {
            @Override // Always selectAll()
            public void editingStopped(ChangeEvent e)
            {
                jtbPrecios_onCellValueChanged(this, getCellEditor());
                super.editingStopped(e);
            }
        };
    }

    //----------------------------------------------------------------------
    public void jtbPrecios_onCellValueChanged(JTable sender, TableCellEditor editor)
    {
        TableColumnModel columnas = sender.getColumnModel();
        int modelColIndex = sender.convertColumnIndexToModel(sender.getEditingColumn());
        int modelRowIndex = sender.convertRowIndexToModel(sender.getEditingRow());
        TableColumn actual = columnas.getColumn(modelColIndex);
        ControlPreciosTableModel dataBinding = (ControlPreciosTableModel) sender.getModel();
        ControlPrecio seleccionado = dataBinding.getElementAt(modelRowIndex);

        if (actual == colBase)
        {
            float base = (float) editor.getCellEditorValue();

            dataBinding.setValueAt(base * seleccionado.getFactor() / 100.0000F, modelRowIndex, colMargen.getModelIndex());
            dataBinding.setValueAt(base + seleccionado.getMargen(), modelRowIndex, colFinal.getModelIndex());
        }

        else if (actual == colMargen)
        {
            float margen = (float) editor.getCellEditorValue();
            float base_temp = seleccionado.getCostoDirecto();

            if (base_temp > 0)
                dataBinding.setValueAt((int) (margen * 100.0000F / base_temp), modelRowIndex, colFactor.getModelIndex());

            else
                dataBinding.setValueAt(0, modelRowIndex, colFactor.getModelIndex());

            dataBinding.setValueAt(base_temp + margen, modelRowIndex, colFinal.getModelIndex());
        }

        else if (actual == colFactor)
        {
            int factor = (int) editor.getCellEditorValue();

            dataBinding.setValueAt(factor / 100.0000F * seleccionado.getCostoDirecto(), modelRowIndex, colMargen.getModelIndex());
            dataBinding.setValueAt(seleccionado.getMargen() + seleccionado.getCostoDirecto(), modelRowIndex, colFinal.getModelIndex());
        }

        else if (actual == colFinal)
        {
            float pfinal = (float) editor.getCellEditorValue();
            float base_temp = seleccionado.getCostoDirecto();

            dataBinding.setValueAt(pfinal - base_temp, modelRowIndex, colMargen.getModelIndex());
            if (base_temp > 0)
                dataBinding.setValueAt((int) (seleccionado.getMargen() * 100.0000F / base_temp), modelRowIndex, colFactor.getModelIndex());

            else
                dataBinding.setValueAt(0, modelRowIndex, colFactor.getModelIndex());
        }

        //dataBinding.setValueAt(POJOModel.getCurrent().getFk_variante_id_unidad_masa().getNombre(), jtbPartes.getEditingRow(),  jtbPartes.getColumnCount() - 1);
    }

    // -------------------------------------------------------------------
    @Override
    public void showDetail(ControlPrecio elemento)
    {
        JControlPreciosDetailView dialogo = new JControlPreciosDetailView(this);
        mainContainer.addWindow(dialogo);

        if (elemento != null)
            dialogo.prepareForModify(elemento);

        else
            dialogo.prepareForNew();

        mainContainer.showCenter(dialogo);
    }

    // -------------------------------------------------------------------
    @Override
    public void onAcceptNewElement(ControlPrecio nuevo, List<DesgloseCosto_5FN> desgloses /*, List<ControlPrecioDireccion> direcciones_borradas*/)
    {
        if (swSecondPlane == null || swSecondPlane.isDone())
        {
            List<Object> parametros = new ArrayList<Object>();
            swSecondPlane = new ControlPrecioSwingWorker();
            swSecondPlane.addPropertyChangeListener(this);
            parametros.add(opINSERT);
            parametros.add(nuevo);
            parametros.add(desgloses);
             //parametros.add(direcciones_borradas);

            jbMessage.setText("Guardando...");
            swSecondPlane.execute(parametros);
        }

    }

    // -------------------------------------------------------------------
    @Override
    public void onAcceptModifyElement(ControlPrecio modificado, List<DesgloseCosto_5FN> desgloses, List<DesgloseCosto> desgloses_borrados)
    {
        if (swSecondPlane == null || swSecondPlane.isDone())
        {
            List<Object> parametros = new ArrayList<Object>();
            swSecondPlane = new ControlPrecioSwingWorker();
            swSecondPlane.addPropertyChangeListener(this);
            parametros.add(opUPDATE);
            parametros.add(modificado);
            parametros.add(desgloses);
             parametros.add(desgloses_borrados);

            jbMessage.setText("Guardando...");
            swSecondPlane.execute(parametros);
        }
    }

    //---------------------------------------------------------------------
    private void resetElement(ControlPrecio value)
    {
        //value.acceptChanges();
        bsControlPrecios.fireTableRowsUpdated(jtbData.getSelectedRow(), jtbData.getSelectedRow());
        jbMessage.setText("Cambio guardado");
    }

    // -------------------------------------------------------------------
    @Override
    public List<BienVariante> obtenerPresentaciones()
    {
        return bsPresentacionesEditor.getData();
    }

    // -------------------------------------------------------------------
    @Override
    public List<Moneda> obtenerMonedas()
    {
        return bsMonedasEditor.getData();
    }

    // -------------------------------------------------------------------
    /*@Override
    public List<Valor> obtenerTipoPrecios()
    {
        return bsTipoPreciosEditor.getData();
    }*/

    //--------------------------------------------------------------------
    @Override
    public List<AreaPrecio> obtenerAreasPrecios()
    {
        return bsAreasPreciosEditor.getData();
    }

    // -------------------------------------------------------------------
    @Override
    public DesgloseCostosBusinessLogic obtenerLogicaDesglose()
    {
        return desglose_costos;
    }

    // -------------------------------------------------------------------
    @Override
    public void agregaContenedorPrincipal(JInternalFrame Dialog)
    {
        mainContainer.addWindow(Dialog);
    }

    // -------------------------------------------------------------------
    @Override
    public void mostrarCentrado(JInternalFrame Dialog)
    {
        mainContainer.showCenter(Dialog);
    }

    //--------------------------------------------------------------------
    @Override
    public String getEsquema()
    {
        return mainContainer.getOrigenDatoActual().getNombre();
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
                showDetail(bsControlPrecios.getElementAt(index));
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
                    swSecondPlane = new ControlPrecioSwingWorker();
                    swSecondPlane.addPropertyChangeListener(this);
                    parametros.add(opDELETE_LIST);
                    int[] rowsHandlers = jtbData.getSelectedRows();
                    for (int i = 0; i < rowsHandlers.length; i++)
                    {
                        rowsHandlers[i] = jtbData.convertRowIndexToModel(rowsHandlers[i]);
                    }
                    parametros.add(bsControlPrecios.getElementsAt(rowsHandlers));
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
                swSecondPlane = new ControlPrecioSwingWorker();
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
        List<ControlPrecio> modificados = new ArrayList<>();
        List<ControlPrecio> agregados = new ArrayList<>();
        List<ControlPrecio> datos = bsControlPrecios.getData();

        TableCellEditor editor = jtbData.getCellEditor();
        if (editor != null)
            editor.stopCellEditing();

        for (ControlPrecio elemento : datos)
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
                swSecondPlane = new ControlPrecioSwingWorker();
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
                showDetail(bsControlPrecios.getElementAt(index));
            }
        }
        else
            JOptionPane.showMessageDialog(this, "No cuenta con los permisos necesarios para ACTUALIZAR elementos");
    }

    // -------------------------------------------------------------------
    private void setDisplayData(List<BienVariante> listaPresentaciones, List<Moneda> listaMonedas, List<ControlPrecio> values, List<AreaPrecio> listaAreas)
    {
        /*bsTipoPreciosRender.setData(ListaTipos);
        bsTipoPreciosEditor.setData(ListaTipos);*/
        bsMonedasEditor.setData(listaMonedas);
        bsMonedasRender.setData(listaMonedas);
        bsPresentacionesEditor.setData(listaPresentaciones);
        bsPresentacionesRender.setData(listaPresentaciones);
        bsControlPrecios.setData(values);
        bsAreasPreciosEditor.setData(listaAreas);
        bsAreasPreciosRender.setData(listaAreas);

        printRecordCount();
        jbMessage.setText("Información obtenida");
    }

    //---------------------------------------------------------------------
    private void setDisplayData(ControlPrecio value)
    {
        bsControlPrecios.addRow(value);
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
    private class ControlPrecioSwingWorker extends SwingWorker<List<Object>, Void>
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
                        results.add(precios.obtenerLista());
                        results.add(monedas.obtenerLista());
                        results.add(presentaciones.obtenerListaPartes());
                        //results.add(precios.obtenerTipoPrecios());
                        results.add(areas_precios.obtenerListaHojas());
                        break;

                    case opINSERT:
                        arguments.add(precios.agregar((ControlPrecio) arguments.get(1)));
                        arguments.add(desglose_costos.guardar((List<DesgloseCosto_5FN>)arguments.get(2), 
                        null));
                        break;

                    case opUPDATE:
                        arguments.add(precios.actualizar((ControlPrecio) arguments.get(1)));
                        arguments.add(desglose_costos.guardar((List<DesgloseCosto_5FN>)arguments.get(2), 
                         (List<DesgloseCosto>)arguments.get(3)));
                        break;

                    case opDELETE_LIST:
                        List<ControlPrecio> selecteds = (List<ControlPrecio>) arguments.get(1);
                        precios.borrar(selecteds);
                        break;

                    case opSAVE:
                        List<ControlPrecio> adds = (List<ControlPrecio>) arguments.get(1);
                        List<ControlPrecio> modifieds = (List<ControlPrecio>) arguments.get(2);
                        if (adds.size() > 0)
                            arguments.add(precios.agregar(adds));
                        if (modifieds.size() > 0)
                            arguments.add(precios.actualizar(modifieds));
                        break;

                    default:
                        results.add(precios.obtenerLista());
                        results.add(monedas.obtenerLista());
                        results.add(presentaciones.obtenerListaPartes());
                        //results.add(precios.obtenerTipoPrecios());
                        results.add(areas_precios.obtenerListaHojas());
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
                                List<ControlPrecio> listaPrecios = (List<ControlPrecio>) results.get(1);
                                List<Moneda> listaMonedas = (List<Moneda>) results.get(2);
                                List<BienVariante> listaPresentaciones = (List<BienVariante>) results.get(3);
                                //List<Valor> listaTipoPrecios = (List<Valor>) results.get(4);
                                List<AreaPrecio> listaAres = (List<AreaPrecio>) results.get(4);
                                if (listaAres.size() > 0)
                                    setDisplayData(listaPresentaciones, listaMonedas, listaPrecios, listaAres);
                                else if (!precios.esCorrecto())
                                    JOptionPane.showMessageDialog(null, precios.getMensaje());
                                break;

                            case opINSERT:
                                if (precios.esCorrecto())
                                    setDisplayData((ControlPrecio) arguments.get(1));

                                else
                                    jbMessage.setText(precios.getMensaje());
                                break;

                            case opUPDATE:
                                if (precios.esCorrecto())
                                    resetElement((ControlPrecio) arguments.get(1));

                                else
                                    jbMessage.setText(precios.getMensaje());
                                break;

                            case opDELETE_LIST:
                                if (precios.esCorrecto())
                                {
                                    bsControlPrecios.removeRows((int[]) arguments.get(2));
                                    jbMessage.setText(precios.getMensaje());
                                }
                                else
                                    jbMessage.setText(precios.getEsquema());
                                break;

                            case opSAVE:
                                jbMessage.setText(precios.getMensaje());
                                if (precios.esCorrecto())
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
