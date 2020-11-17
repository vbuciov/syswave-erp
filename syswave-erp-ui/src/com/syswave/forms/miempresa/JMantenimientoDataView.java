package com.syswave.forms.miempresa;

import com.syswave.entidades.common.Entidad;
import com.syswave.entidades.configuracion.Usuario_tiene_Permiso;
import com.syswave.entidades.miempresa.ControlAlmacen;
import com.syswave.entidades.miempresa.Mantenimiento;
import com.syswave.entidades.miempresa.MantenimientoCosto;
import com.syswave.entidades.miempresa.Mantenimiento_tiene_Actividad;
import com.syswave.entidades.miempresa.Moneda;
import com.syswave.entidades.miempresa.Persona;
import com.syswave.entidades.miempresa.TipoPersona;
import com.syswave.entidades.miempresa.Valor;
import com.syswave.entidades.miempresa_vista.MantenimientoDescripcion_5FN;
import com.syswave.entidades.miempresa_vista.MantenimientoTieneActividad_5FN;
import com.syswave.forms.common.JTableDataView;
import com.syswave.swing.models.POJOTableModel;
import com.syswave.forms.databinding.ControlAlmacenesComboBoxModel;
import com.syswave.forms.databinding.MantenimientosTableModel;
import com.syswave.forms.databinding.MonedasComboBoxModel;
import com.syswave.forms.databinding.PersonasComboBoxModel;
import com.syswave.forms.databinding.ValorComboBoxModel;
import com.syswave.swing.table.editors.LookUpComboBoxTableCellEditor;
import com.syswave.swing.table.editors.SpinnerTableCellEditor;
import com.syswave.swing.table.renders.DateTimeTableCellRenderer;
import com.syswave.swing.table.renders.LookUpComboBoxTableCellRenderer;
import com.syswave.logicas.miempresa.ControlAlmacenesBusinessLogic;
import com.syswave.logicas.miempresa.MantenimientoCostosBusinessLogic;
import com.syswave.logicas.miempresa.MantenimientoDescripcionesBusinessLogic;
import com.syswave.logicas.miempresa.MantenimientoTieneActividadesBusinessLogic;
import com.syswave.logicas.miempresa.MantenimientosBusinessLogic;
import com.syswave.logicas.miempresa.MonedasBusinessLogic;
import com.syswave.logicas.miempresa.PersonasBusinessLogic;
import com.toedter.calendar.JDateChooserCellEditor;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SpinnerDateModel;
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
public class JMantenimientoDataView extends JTableDataView implements TableModelListener, IMantenimientoMediator
{

    private final int opLOAD = 0;
    private final int opINSERT_LIST = 1;
    private final int opUPDATE_LIST = 2;
    private final int opDELETE_LIST = 3;
    private final int opINSERT = 4;
    private final int opUPDATE = 5;
    private final int opSAVE = 6;

    private boolean can_browse, can_create, can_update, can_delete;

    private MantenimientosBusinessLogic mantenimientos;
    private MantenimientoDescripcionesBusinessLogic descripciones;
    private MantenimientoTieneActividadesBusinessLogic actividades;
    private MonedasBusinessLogic monedas;
    private ControlAlmacenesBusinessLogic series;
    private PersonasBusinessLogic personas;
    private MantenimientoCostosBusinessLogic costos;

    private MantenimientosTableModel bsMantenimientos;
    MonedasComboBoxModel bsMonedasRender;
    MonedasComboBoxModel bsMonedasEditor;
    ValorComboBoxModel bsTipoComprobantesRender;
    ValorComboBoxModel bsTipoComprobantesEditor;
    PersonasComboBoxModel bsResponsablesRender;
    PersonasComboBoxModel bsResponsablesEditor;
    ControlAlmacenesComboBoxModel bsSerieRender;
    ControlAlmacenesComboBoxModel bsSerieEditor;

    TableColumn colFolio, colFecha, colFechaFinalizacion, colTipo, colResponsable,
            colMoneda, colActivo, colHoraInicio, colHoraFinal,
            colNota, colCostoTotal, colSerie;

    private MantenimientoSwingWorker swSecondPlane;
    List<Usuario_tiene_Permiso> permisos;

    //---------------------------------------------------------------------
    private void grantAllPermisions()
    {
        can_browse = true;
        can_create = true;
        can_update = true;
        can_delete = true;
    }

    //---------------------------------------------------------------------
    public JMantenimientoDataView(IWindowContainer container)
    {
        super(container);
        initAttributes(container);
        grantAllPermisions();
    }

    //---------------------------------------------------------------------
    public JMantenimientoDataView(IWindowContainer container, List<Usuario_tiene_Permiso> values)
    {
        super(container);
        initAttributes(container);
        grant(values);
    }

    //---------------------------------------------------------------------
    /**
     * Este método sirve para asignar los permisos correspondientes.
     *
     * @param values Son los permisos que se pueden conceder
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

        bsMantenimientos.setReadOnly(!can_update);
    }

    private void initAttributes(IWindowContainer container)
    {
        mantenimientos = new MantenimientosBusinessLogic(container.getOrigenDatoActual().getNombre());
        descripciones = new MantenimientoDescripcionesBusinessLogic(mantenimientos.getEsquema());
        actividades = new MantenimientoTieneActividadesBusinessLogic(mantenimientos.getEsquema());
        monedas = new MonedasBusinessLogic(mantenimientos.getEsquema());
        series = new ControlAlmacenesBusinessLogic(mantenimientos.getEsquema());
        personas = new PersonasBusinessLogic(mantenimientos.getEsquema());
        costos = new MantenimientoCostosBusinessLogic(mantenimientos.getEsquema());

        bsTipoComprobantesRender = new ValorComboBoxModel();
        bsTipoComprobantesEditor = new ValorComboBoxModel();
        bsMonedasRender = new MonedasComboBoxModel();
        bsMonedasEditor = new MonedasComboBoxModel();
        bsResponsablesRender = new PersonasComboBoxModel();
        bsResponsablesEditor = new PersonasComboBoxModel();
        bsSerieRender = new ControlAlmacenesComboBoxModel();
        bsSerieEditor = new ControlAlmacenesComboBoxModel();
        bsMantenimientos = new MantenimientosTableModel(new String[]
        {
            "Tipo:{es_tipo}", "Folio:{folio}", "Responsable:{id_persona}",
            "Serie:{id_serie}", "Costo:{costo_total}", "Moneda:{id_moneda}",
            "Activo:{es_activo}",
            "Fecha elaboracion:{fecha_elaboracion}", "Hora Inicio:{hora_inicio}",
            "Hora Final:{hora_final}", "Fecha terminación:{fecha_finalizacion}",
            "Nota:{nota}"
        });
        bsMantenimientos.addTableModelListener(this);

        jtbData.setModel(bsMantenimientos);
        jtbData.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        if (jtbData.getColumnCount() > 0)
        {
            colTipo = jtbData.getColumnModel().getColumn(0);
            colTipo.setCellRenderer(new LookUpComboBoxTableCellRenderer<Valor>(bsTipoComprobantesRender));
            colTipo.setCellEditor(new LookUpComboBoxTableCellEditor<Valor>(bsTipoComprobantesEditor));
            colTipo.setPreferredWidth(100);

            colFolio = jtbData.getColumnModel().getColumn(1);
            colFolio.setPreferredWidth(90);

            colResponsable = jtbData.getColumnModel().getColumn(2);
            colResponsable.setCellRenderer(new LookUpComboBoxTableCellRenderer<Persona>(bsResponsablesRender));
            colResponsable.setCellEditor(new LookUpComboBoxTableCellEditor<Persona>(bsResponsablesEditor));
            colResponsable.setPreferredWidth(250);

            colSerie = jtbData.getColumnModel().getColumn(3);
            colSerie.setCellRenderer(new LookUpComboBoxTableCellRenderer<ControlAlmacen>(bsSerieRender));
            colSerie.setCellEditor(new LookUpComboBoxTableCellEditor<ControlAlmacen>(bsSerieEditor));
            colSerie.setPreferredWidth(450);

            colCostoTotal = jtbData.getColumnModel().getColumn(4);
            colCostoTotal.setPreferredWidth(70);

            colMoneda = jtbData.getColumnModel().getColumn(5);
            colMoneda.setCellRenderer(new LookUpComboBoxTableCellRenderer<Moneda>(bsMonedasRender));
            colMoneda.setCellEditor(new LookUpComboBoxTableCellEditor<Moneda>(bsMonedasEditor));
            colMoneda.setPreferredWidth(60);

            colActivo = jtbData.getColumnModel().getColumn(6);
            colActivo.setPreferredWidth(60);

            colFecha = jtbData.getColumnModel().getColumn(7);
            colFecha.setCellEditor(new JDateChooserCellEditor());
            colFecha.setPreferredWidth(120);

            colHoraInicio = jtbData.getColumnModel().getColumn(8);
            colHoraInicio.setCellRenderer(new DateTimeTableCellRenderer("HH:mm"));
            colHoraInicio.setCellEditor(new SpinnerTableCellEditor(new SpinnerDateModel(new Date(), null, null, java.util.Calendar.HOUR_OF_DAY), "HH:mm"));
            colHoraInicio.setPreferredWidth(70);

            colHoraFinal = jtbData.getColumnModel().getColumn(9);
            colHoraFinal.setCellRenderer(new DateTimeTableCellRenderer("HH:mm"));
            colHoraFinal.setCellEditor(new SpinnerTableCellEditor(new SpinnerDateModel(new Date(), null, null, java.util.Calendar.HOUR_OF_DAY), "HH:mm"));
            colHoraFinal.setPreferredWidth(70);

            colFechaFinalizacion = jtbData.getColumnModel().getColumn(10);
            colFechaFinalizacion.setCellEditor(new JDateChooserCellEditor());
            colFechaFinalizacion.setPreferredWidth(120);

            colNota = jtbData.getColumnModel().getColumn(11);
            colNota.setPreferredWidth(250);

            //Nota: Debido a los renders que se estan utilizando es necesario tener un renglón más alto.
            jtbData.setRowHeight((int) (jtbData.getRowHeight() * 1.5));
        }
    }

    //--------------------------------------------------------------------
    private void crearTiposMantenimieto()
    {
        List<Valor> SonTipos = new ArrayList<>();

        SonTipos.add(new Valor(0, "Preventivo"));
        SonTipos.add(new Valor(1, "Correctivo"));
        SonTipos.add(new Valor(2, "Solicitado"));
        SonTipos.add(new Valor(3, "Predictivo"));

        bsTipoComprobantesRender.setData(SonTipos);
        bsTipoComprobantesEditor.setData(SonTipos);
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
                showDetail(bsMantenimientos.getElementAt(index));
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
        {
            if (JOptionPane.showConfirmDialog(this, String.format("Ha seleccionado %d registro(s) para ser eleiminado(s)\n¿Esta seguro de continuar?", jtbData.getSelectedRowCount())) == JOptionPane.OK_OPTION)
            {
                if (swSecondPlane == null || swSecondPlane.isDone())
                {
                    List<Object> parametros = new ArrayList<Object>();
                    swSecondPlane = new MantenimientoSwingWorker();
                    swSecondPlane.addPropertyChangeListener(this);
                    parametros.add(opDELETE_LIST);
                    int[] rowsHandlers = jtbData.getSelectedRows();
                    for (int i = 0; i < rowsHandlers.length; i++)
                    {
                        rowsHandlers[i] = jtbData.convertRowIndexToModel(rowsHandlers[i]);
                    }
                    parametros.add(bsMantenimientos.getElementsAt(rowsHandlers));
                    parametros.add(rowsHandlers);
                    jbMessage.setText("Eliminando elemento(s)....");
                    swSecondPlane.execute(parametros);
                }
            }
        }
        else
        {
            JOptionPane.showMessageDialog(this, "No cuenta con los permisos necesarios para eliminar elementos");
        }
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
                swSecondPlane = new MantenimientoSwingWorker();
                swSecondPlane.addPropertyChangeListener(this);
                TipoPersona filtro = new TipoPersona();
                filtro.setNombre("");
                filtro.setSiglas("");
                filtro.setActivo(true);
                filtro.setUsaPersonal(true);
                parametros.add(opLOAD);
                parametros.add(filtro);
                jbMessage.setText("Obteniendo información...");
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
        List<Mantenimiento> modificados = new ArrayList<>();
        List<Mantenimiento> agregados = new ArrayList<>();
        List<Mantenimiento> datos = bsMantenimientos.getData();

        TableCellEditor editor = jtbData.getCellEditor();
        if (editor != null)
        {
            editor.stopCellEditing();
        }

        for (Mantenimiento elemento : datos)
        {
            if (elemento.isNew())
            {
                agregados.add(elemento);
            }
            else if (elemento.isModified())
            {
                modificados.add(elemento);
            }
        }

        elementosTotales = modificados.size() + agregados.size();

        if (elementosTotales > 0)
        {
            if (swSecondPlane == null || swSecondPlane.isDone())
            {
                List<Object> parametros = new ArrayList<Object>();
                swSecondPlane = new MantenimientoSwingWorker();
                swSecondPlane.addPropertyChangeListener(this);
                parametros.add(opSAVE);
                parametros.add(agregados);
                parametros.add(modificados);
                swSecondPlane.execute(parametros);
            }
        }
        else
        {
            JOptionPane.showMessageDialog(this, "No existen cambios a guardar");
        }
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
                showDetail(bsMantenimientos.getElementAt(index));
            }
        }
        else
            JOptionPane.showMessageDialog(this, "No cuenta con los permisos necesarios para ACTUALIZAR elementos");
    }

    //--------------------------------------------------------------------
    @Override
    public void doPrint()
    {

        int index = jtbData.getSelectedRow();

        if (index >= 0 && index < bsMantenimientos.getRowCount())
        {
            showParams(bsMantenimientos.getElementAt(index));
        }
        else
        {
            showParams(null);
        }

    }

    //--------------------------------------------------------------------
    @Override
    public void printHelp(String title, String resourcePath)
    {
        Connection forJasper = mantenimientos.getConexion();
        jasperSoftToPrint(forJasper, title, resourcePath);
        mantenimientos.setAvailableConextion(forJasper);
    }

    //--------------------------------------------------------------------
    @Override
    public void printHelp(String title, String resourcePath, Map parameters)
    {
        Connection forJasper = mantenimientos.getConexion();
        jasperSoftToPrint(forJasper, title, resourcePath, parameters);
        mantenimientos.setAvailableConextion(forJasper);
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

    //-------------------------------------------------------------------
    @Override
    public void showDetail(Mantenimiento elemento)
    {
        JMantenimientoDetailView dialogo = new JMantenimientoDetailView(this);
        mainContainer.addWindow(dialogo);

        if (elemento != null)
        {
            dialogo.prepareForModify(elemento);
        }
        else
        {
            dialogo.prepareForNew();
        }

        mainContainer.showCenter(dialogo);
    }

    //-------------------------------------------------------------------
    public void showParams(Mantenimiento elemento)
    {
        JMantenimientoPDialog filtro = new JMantenimientoPDialog(null, true, this);

        if (elemento != null)
            filtro.loadDefaults(elemento);
        else
            filtro.prepareForNew();

        filtro.setLocationRelativeTo(this);
        filtro.setVisible(true);

        if (filtro.isOk())
        {
            Map parametros = new HashMap();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            parametros.put("es_tipo", filtro.getTipo());
            parametros.put("folio", filtro.getFolio());
            parametros.put("fecha_inicio", df.format(filtro.getFechaInicio()));
            parametros.put("fecha_fin", df.format(filtro.getFechaFin()));
            parametros.put("responsable", filtro.getResponsable());
            String[] keys = filtro.getSerie().split(",");
            parametros.put("entrada_serie", Integer.parseInt(keys[0]));
            parametros.put("ubicacion_serie", Integer.parseInt(keys[1]));
            parametros.put("estatus", filtro.getEstatus());
            Connection forJasper = mantenimientos.getConexion();
            jasperSoftToPrint(forJasper, "Lista de Mantenimientos", "/com/carmona/cmms/mantenimientos.jasper", parametros);
            mantenimientos.setAvailableConextion(forJasper);

        }
    }

    //-------------------------------------------------------------------
    private void setDisplayData(List<Moneda> listaMonedas, List<Mantenimiento> listaDocumentos, List<ControlAlmacen> listaSeries, List<Persona> listaPersonas)
    {
        bsMonedasEditor.setData(listaMonedas);
        bsMonedasRender.setData(listaMonedas);

        bsSerieEditor.setData(listaSeries);
        bsSerieRender.setData(listaSeries);

        bsResponsablesEditor.setData(listaPersonas);
        bsResponsablesRender.setData(listaPersonas);

        bsMantenimientos.setData(listaDocumentos);

        printRecordCount();

        jbMessage.setText("Información obtenida");
    }

    //-------------------------------------------------------------------
    @Override
    public void onAcceptNewElement(Mantenimiento elemento, List<MantenimientoDescripcion_5FN> descripciones,
                                   List<MantenimientoTieneActividad_5FN> actividades, List<MantenimientoCosto> listacostos)
    {
        if (swSecondPlane == null || swSecondPlane.isDone())
        {
            List<Object> parametros = new ArrayList<Object>();
            swSecondPlane = new MantenimientoSwingWorker();
            swSecondPlane.addPropertyChangeListener(this);
            parametros.add(opINSERT);
            parametros.add(elemento);
            parametros.add(descripciones);
            parametros.add(actividades);
            parametros.add(listacostos);

            jbMessage.setText("Guardando...");
            swSecondPlane.execute(parametros);
        }
    }

    //-------------------------------------------------------------------
    @Override
    public void onAcceptModifyElement(Mantenimiento nuevo, List<MantenimientoDescripcion_5FN> descripciones,
                                      List<MantenimientoTieneActividad_5FN> actividades, List<MantenimientoCosto> listacostos,
                                      List<MantenimientoCosto> listacostos_borrados)
    {
        if (swSecondPlane == null || swSecondPlane.isDone())
        {
            List<Object> parametros = new ArrayList<Object>();
            swSecondPlane = new MantenimientoSwingWorker();
            swSecondPlane.addPropertyChangeListener(this);
            parametros.add(opUPDATE);
            parametros.add(nuevo);
            parametros.add(descripciones);
            parametros.add(actividades);
            parametros.add(listacostos);
            parametros.add(listacostos_borrados);
            //parametros.add(identificadores);*/

            jbMessage.setText("Guardando...");
            swSecondPlane.execute(parametros);
        }
    }

    //---------------------------------------------------------------------
    private void setDisplayData(Mantenimiento value)
    {
        bsMantenimientos.addRow(value);
        jbMessage.setText("Nuevo agregado");
        printRecordCount();
    }

    //---------------------------------------------------------------------
    private void resetCurrentElement()
    {
        bsMantenimientos.fireTableRowsUpdated(jtbData.getSelectedRow(), jtbData.getSelectedRow());
        jbMessage.setText("Cambio guardado");
    }

    //-------------------------------------------------------------------
    @Override
    public String obtenerOrigenDato()
    {
        return mainContainer.getOrigenDatoActual().getNombre();
    }

    //-------------------------------------------------------------------
    @Override
    public List<Valor> obtenerTiposMantenimiento()
    {
        return bsTipoComprobantesEditor.getData();
    }

    //-------------------------------------------------------------------
    @Override
    public List<Persona> obtenerResponsables()
    {
        return bsResponsablesEditor.getData();
    }

    //-------------------------------------------------------------------
    @Override
    public List<ControlAlmacen> obtenerSeries()
    {
        return bsSerieEditor.getData();
    }

    //-------------------------------------------------------------------
    @Override
    public List<Moneda> obtenerMonedas()
    {
        return bsMonedasEditor.getData();
    }

    //-------------------------------------------------------------------
    @Override
    public List<MantenimientoDescripcion_5FN> obtenerDescripciones(Mantenimiento mantenimiento)
    {
        return descripciones.obtenerListaVista(mantenimiento.getId());
    }

    //-------------------------------------------------------------------
    @Override
    public List<MantenimientoTieneActividad_5FN> obtenerCheckList(Mantenimiento elemento, ControlAlmacen serie)
    {
        Mantenimiento_tiene_Actividad filtro = new Mantenimiento_tiene_Actividad();

        filtro.setIdVariante(serie.getIdVariante());
        filtro.setIdMantenimiento(elemento.getId());

        return actividades.obtenerListaRevision(filtro);
    }

    //---------------------------------------------------------------------
    @Override
    public List<MantenimientoCosto> obtenerCostos(Mantenimiento elemento)
    {
        MantenimientoCosto filtro = new MantenimientoCosto();
        filtro.setIdMantenimiento(elemento.getId());

        return costos.obtenerLista(filtro);
    }


    //---------------------------------------------------------------------
    //--  Esta clase controla el cargado en segundo plano de los origenes de datos.
    //---------------------------------------------------------------------
    private class MantenimientoSwingWorker extends SwingWorker<List<Object>, Void>
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
                        results.add(mantenimientos.obtenerLista());
                        results.add(monedas.obtenerLista());
                        results.add(series.obtenerListaSeriesCorta());
                        results.add(personas.obtenerListaPorTipo((TipoPersona) arguments.get(1)));
                        break;

                    case opINSERT:
                        results.add(mantenimientos.agregar((Mantenimiento) arguments.get(1)));

                        results.add(descripciones.guardar((List<MantenimientoDescripcion_5FN>) arguments.get(2)));
                        results.add(actividades.guardar((List<MantenimientoTieneActividad_5FN>) arguments.get(3)));
                        results.add(costos.guardar((List<MantenimientoCosto>) arguments.get(4), null));
                        break;

                    case opUPDATE:
                        results.add(mantenimientos.actualizar((Mantenimiento) arguments.get(1)));
                        results.add(descripciones.guardar((List<MantenimientoDescripcion_5FN>) arguments.get(2)));
                        results.add(actividades.guardar((List<MantenimientoTieneActividad_5FN>) arguments.get(3)));
                        results.add(costos.guardar((List<MantenimientoCosto>) arguments.get(4),
                                                   (List<MantenimientoCosto>) arguments.get(5)));
                        break;

                    case opDELETE_LIST:
                        List<Mantenimiento> selecteds = (List<Mantenimiento>) arguments.get(1);
                        results.add(mantenimientos.borrar(selecteds));
                        break;

                    case opSAVE:
                        List<Mantenimiento> adds = (List<Mantenimiento>) arguments.get(1);
                        List<Mantenimiento> modifieds = (List<Mantenimiento>) arguments.get(2);
                        if (adds.size() > 0)
                        {
                            results.add(mantenimientos.agregar(adds));
                        }
                        if (modifieds.size() > 0)
                        {
                            results.add(mantenimientos.actualizar(modifieds));
                        }
                        break;

                    default:
                        results.add(mantenimientos.obtenerLista());
                        results.add(monedas.obtenerLista());
                        results.add(series.obtenerListaSeriesCorta());
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
                                List<Mantenimiento> listaDocumentos = (List<Mantenimiento>) results.get(1);
                                List<Moneda> listaMonedas = (List<Moneda>) results.get(2);
                                List<ControlAlmacen> listaSeries = (List<ControlAlmacen>) results.get(3);
                                List<Persona> listaPersona = (List<Persona>) results.get(4);
                                if (listaMonedas.size() > 0)
                                {
                                    crearTiposMantenimieto();
                                    setDisplayData(listaMonedas, listaDocumentos, listaSeries, listaPersona);
                                }
                                else if (!monedas.esCorrecto())
                                {
                                    JOptionPane.showMessageDialog(null, monedas.getMensaje());
                                }
                                break;

                            case opINSERT:
                                if (mantenimientos.esCorrecto())
                                {
                                    setDisplayData((Mantenimiento) arguments.get(1));
                                }
                                else
                                {
                                    jbMessage.setText(mantenimientos.getMensaje());
                                }
                                break;

                            case opUPDATE:
                                if (mantenimientos.esCorrecto())
                                {
                                    resetCurrentElement();
                                }
                                else
                                {
                                    jbMessage.setText(mantenimientos.getMensaje());
                                }
                                break;

                            case opDELETE_LIST:
                                if (mantenimientos.esCorrecto())
                                {
                                    bsMantenimientos.removeRows((int[]) arguments.get(2));
                                    printRecordCount();
                                    jbMessage.setText("Elemento(s) eliminado(s)");
                                }
                                else
                                    jbMessage.setText(mantenimientos.getEsquema());
                                break;

                            case opSAVE:
                                jbMessage.setText(mantenimientos.getMensaje());
                                if (mantenimientos.esCorrecto())
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
                {
                    arguments.clear();
                }
            }
        }
    }

}
