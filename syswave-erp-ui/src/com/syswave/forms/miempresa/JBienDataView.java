package com.syswave.forms.miempresa;

import com.syswave.entidades.common.Entidad;
import com.syswave.entidades.configuracion.Unidad;
import com.syswave.entidades.configuracion.Usuario_tiene_Permiso;
import com.syswave.entidades.miempresa.Bien;
import com.syswave.entidades.miempresa.BienCompuesto;
import com.syswave.entidades.miempresa.BienVariante;
import com.syswave.entidades.miempresa.BienVarianteFoto;
import com.syswave.entidades.miempresa.ControlInventario;
import com.syswave.entidades.miempresa.ControlPrecio;
import com.syswave.entidades.miempresa.PlanMantenimiento;
import com.syswave.entidades.miempresa_vista.BienCompuestoVista;
import com.syswave.entidades.miempresa_vista.VarianteIdentificadorVista;
import com.syswave.forms.common.JTableDataView;
import com.syswave.forms.databinding.BienComboBoxModel;
import com.syswave.forms.databinding.BienVariantesTableModel;
import com.syswave.swing.models.POJOTableModel;
import com.syswave.forms.databinding.UnidadComboBoxModel;
import com.syswave.swing.table.editors.LookUpComboBoxTableCellEditor;
import com.syswave.swing.table.renders.LookUpComboBoxTableCellRenderer;
import com.syswave.logicas.configuracion.UnidadesBusinessLogic;
import com.syswave.logicas.miempresa.BienCompuestosBusinessLogic;
import com.syswave.logicas.miempresa.BienVarianteFotosBusinessLogic;
import com.syswave.logicas.miempresa.BienVariantesBusinessLogic;
import com.syswave.logicas.miempresa.BienesBusinessLogic;
import com.syswave.logicas.miempresa.ControlInventariosBusinessLogic;
import com.syswave.logicas.miempresa.ControlPreciosBusinessLogic;
import com.syswave.logicas.miempresa.PlanMantenimientosBusinessLogic;
import com.syswave.logicas.miempresa.VarianteIdentificadoresBusinessLogic;
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
public class JBienDataView extends JTableDataView implements IBienMediator, TableModelListener
{

    private final int opLOAD = 0;
    private final int opINSERT_LIST = 1;
    private final int opUPDATE_LIST = 2;
    private final int opDELETE_LIST = 3;
    private final int opINSERT = 4;
    private final int opUPDATE = 5;
    private final int opSAVE = 6;

    private boolean can_browse, can_create, can_update, can_delete;

    /*UnidadComboBoxModel bsUnidadMasaRender;
    UnidadComboBoxModel bsUnidadMasaEditor;
    UnidadComboBoxModel bsUnidadLongitudRender;
    UnidadComboBoxModel bsUnidadLongitudEditor;*/
    BienComboBoxModel bsTipoBienRender;
    BienComboBoxModel bsTipoBienEditor;

    BienVariantesTableModel bsVariantes;
    BienVariantesBusinessLogic bienes;
    UnidadesBusinessLogic unidades;
    BienCompuestosBusinessLogic compuestos;
    BienesBusinessLogic tipoBienes;
    ControlInventariosBusinessLogic lotes;
    PlanMantenimientosBusinessLogic actividades;
    ControlPreciosBusinessLogic precios;
    BienVarianteFotosBusinessLogic fotos;

    VarianteIdentificadoresBusinessLogic identificadores;

    TableColumn colTipoBien, colDescripcion;
    BienSwingWorker swSecondPlane;
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
    public JBienDataView(IWindowContainer container)
    {
        super(container);
        initAttributes();
        grantAllPermisions();
    }

    // -------------------------------------------------------------------
    public JBienDataView(IWindowContainer container, List<Usuario_tiene_Permiso> permisos)
    {
        super(container);
        initAttributes();
        grant(permisos);
    }

    // -------------------------------------------------------------------
    private void initAttributes()
    {
        esquema = mainContainer.getOrigenDatoActual().getNombre();
        bsVariantes = new BienVariantesTableModel(new String[]
        {
            "Grupo:{id_bien}", "Descripción:{descripcion}", 
            "Activo:{es_activo}", 
            "Controlar Inventario:{es_inventario}",
            "Comercializar:{es_comercializar}"
        });
        bsVariantes.addTableModelListener(this);
        /*bsUnidadMasaRender = new UnidadComboBoxModel();
        bsUnidadMasaEditor = new UnidadComboBoxModel();
        bsUnidadLongitudRender = new UnidadComboBoxModel();
        bsUnidadLongitudEditor = new UnidadComboBoxModel();*/
        bsTipoBienRender = new BienComboBoxModel();
        bsTipoBienEditor = new BienComboBoxModel();
        bienes = new BienVariantesBusinessLogic(esquema);
        tipoBienes = new BienesBusinessLogic(esquema);
        identificadores = new VarianteIdentificadoresBusinessLogic(esquema);
        lotes = new ControlInventariosBusinessLogic(esquema);
        actividades = new PlanMantenimientosBusinessLogic(esquema);
        precios = new ControlPreciosBusinessLogic(esquema);
        fotos = new BienVarianteFotosBusinessLogic(esquema);
        unidades = new UnidadesBusinessLogic("configuracion");

        compuestos = new BienCompuestosBusinessLogic(bienes.getEsquema());
        jtbData.setModel(bsVariantes);
        jtbData.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        if (jtbData.getColumnCount() > 0)
        {
            colTipoBien = jtbData.getColumnModel().getColumn(0);
            colTipoBien.setCellRenderer(new LookUpComboBoxTableCellRenderer<Bien>(bsTipoBienRender));
            colTipoBien.setCellEditor(new LookUpComboBoxTableCellEditor<Bien>(bsTipoBienEditor));
            colTipoBien.setPreferredWidth(200);

            colDescripcion = jtbData.getColumnModel().getColumn(1);
            colDescripcion.setPreferredWidth(250);

            jtbData.setRowHeight((int) (jtbData.getRowHeight() * 1.5));
        }
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

        bsVariantes.setReadOnly(!can_update);
    }

    // -------------------------------------------------------------------
    @Override
    public void showDetail(BienVariante elemento)
    {
        JBienDetailView dialogo = new JBienDetailView(this);
        mainContainer.addWindow(dialogo);

        if (elemento != null)
            dialogo.prepareForModify(elemento);

        else
            dialogo.prepareForNew();

        mainContainer.showCenter(dialogo);
    }

    // -------------------------------------------------------------------
    @Override
    public void onAcceptNewElement(BienVariante nuevo, List<BienCompuestoVista> compuestos,
            List<VarianteIdentificadorVista> identificadores,
            List<ControlInventario> lotes,
            List<PlanMantenimiento> actividades,
            List<ControlPrecio> precios,
            BienVarianteFoto foto)
    {
        if (swSecondPlane == null || swSecondPlane.isDone())
        {
            List<Object> parametros = new ArrayList<Object>();
            swSecondPlane = new BienSwingWorker();
            swSecondPlane.addPropertyChangeListener(this);
            parametros.add(opINSERT);
            parametros.add(nuevo);
            parametros.add(compuestos);
            parametros.add(identificadores);
            parametros.add(lotes);
            parametros.add(actividades);
            parametros.add(precios);
            parametros.add(foto);

            jbMessage.setText("Guardando...");
            swSecondPlane.execute(parametros);
        }

    }

    // -------------------------------------------------------------------
    @Override
    public void onAcceptModifyElement(BienVariante modificado, List<BienCompuestoVista> compuestos, List<BienCompuesto> compuestos_borrados,
            List<VarianteIdentificadorVista> identificadores,
            List<ControlInventario> lotes, List<ControlInventario> lotes_borrados,
            List<PlanMantenimiento> actividades, List<PlanMantenimiento> actividades_borrados,
            List<ControlPrecio> precios, List<ControlPrecio> precios_borrados,
            BienVarianteFoto foto,
            BienVarianteFoto foto_borrada)
    {
        if (swSecondPlane == null || swSecondPlane.isDone())
        {
            List<Object> parametros = new ArrayList<Object>();
            swSecondPlane = new BienSwingWorker();
            swSecondPlane.addPropertyChangeListener(this);
            parametros.add(opUPDATE);
            parametros.add(modificado);
            parametros.add(compuestos);
            parametros.add(compuestos_borrados);
            parametros.add(identificadores);
            parametros.add(lotes);
            parametros.add(lotes_borrados);
            parametros.add(actividades);
            parametros.add(actividades_borrados);
            parametros.add(precios);
            parametros.add(precios_borrados);
            parametros.add(foto);
            parametros.add(foto_borrada);

            jbMessage.setText("Guardando...");
            swSecondPlane.execute(parametros);
        }
    }

    //---------------------------------------------------------------------
    private void resetElement(BienVariante value)
    {
        value.acceptChanges();
        bsVariantes.fireTableRowsUpdated(jtbData.getSelectedRow(), jtbData.getSelectedRow());
        jbMessage.setText("Cambio guardado");
        printRecordCount();
    }

    // -------------------------------------------------------------------
    @Override
    public String obtenerOrigenDato()
    {
        return esquema;
    }

    //---------------------------------------------------------------------
    @Override
    public List<Bien> obtenerTiposBienes()
    {
        return bsTipoBienEditor.getData();
    }

    // -------------------------------------------------------------------
    @Override
    public List<BienCompuestoVista> obtenerCompuestos(BienVariante elemento)
    {
        BienCompuestoVista filtro = new BienCompuestoVista();
        filtro.setIdBienFormal(elemento.getId());

        return compuestos.obtenerListaVista(filtro);
    }

    //--------------------------------------------------------------------
    @Override
    public List<VarianteIdentificadorVista> obtenerIdentificadores(BienVariante elemento)
    {
        return identificadores.obtenerListaVista(elemento.getId());
    }

    //---------------------------------------------------------------------
    @Override
    public List<ControlInventario> obtenerLotes(BienVariante elemento)
    {
        ControlInventario filtro = new ControlInventario();
        filtro.setIdVariante(elemento.getId());
        return lotes.obtenerLista(filtro);
    }

    //---------------------------------------------------------------------
    @Override
    public List<PlanMantenimiento> obtenerActividades(BienVariante elemento)
    {
        PlanMantenimiento filtro = new PlanMantenimiento();
        filtro.setIdVariante(elemento.getId());
        return actividades.obtenerLista(filtro);
    }

    //---------------------------------------------------------------------
    @Override
    public List<ControlPrecio> obtenerPrecios(BienVariante elemento)
    {
        ControlPrecio filtro = new ControlPrecio();
        filtro.setIdVariante(elemento.getId());
        return precios.obtenerLista(filtro);
    }

    //--------------------------------------------------------------------
    @Override
    public List<BienVarianteFoto> obtenerFotos(BienVariante elemento)
    {
        BienVarianteFoto filtro = new BienVarianteFoto();
        filtro.setIdVariante(elemento.getId());
        filtro.setConsecutivo(1); //Solo devolvemos la primera imagen.

        return fotos.obtenerMiniaturas(filtro);
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
                showDetail(bsVariantes.getElementAt(index));
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
                    swSecondPlane = new BienSwingWorker();
                    swSecondPlane.addPropertyChangeListener(this);
                    parametros.add(opDELETE_LIST);
                    int[] rowsHandlers = jtbData.getSelectedRows();
                    for (int i = 0; i < rowsHandlers.length; i++)
                    {
                        rowsHandlers[i] = jtbData.convertRowIndexToModel(rowsHandlers[i]);
                    }
                    parametros.add(bsVariantes.getElementsAt(rowsHandlers));
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
                swSecondPlane = new BienSwingWorker();
                swSecondPlane.addPropertyChangeListener(this);
                parametros.add(opLOAD);
                jbMessage.setText("Consultando...");
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
        List<BienVariante> modificados = new ArrayList<>();
        List<BienVariante> agregados = new ArrayList<>();
        List<BienVariante> datos = bsVariantes.getData();

        TableCellEditor editor = jtbData.getCellEditor();
        if (editor != null)
            editor.stopCellEditing();

        for (BienVariante elemento : datos)
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
                swSecondPlane = new BienSwingWorker();
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
                showDetail(bsVariantes.getElementAt(index));
            }
        }
        else
            JOptionPane.showMessageDialog(this, "No cuenta con los permisos necesarios para ACTUALIZAR elementos");
    }

    // -------------------------------------------------------------------
    private void setDisplayData(List<Bien> tipos, List<BienVariante> values)
    {
        bsTipoBienEditor.setData(tipos);
        bsTipoBienRender.setData(tipos);
        bsVariantes.setData(values);
        jbMessage.setText("Información obtenida");
        
        printRecordCount();
    }

    //---------------------------------------------------------------------
    private void setDisplayData(BienVariante value)
    {
        value.acceptChanges();
        bsVariantes.addRow(value);
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
    private class BienSwingWorker extends SwingWorker<List<Object>, Void>
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
                        results.add(bienes.obtenerLista());
                        setProgress(80);
                        results.add(tipoBienes.obtenerLista());
                        break;

                    case opINSERT:
                        results.add(bienes.agregar((BienVariante) arguments.get(1)));
                        setProgress(65);
                        results.add(compuestos.guardar((List<BienCompuestoVista>) arguments.get(2), null));
                        setProgress(70);
                        results.add(identificadores.guardar((List<VarianteIdentificadorVista>) arguments.get(3)));
                        setProgress(75);
                        results.add(lotes.guardar((List<ControlInventario>) arguments.get(4), null));
                        setProgress(80);
                        results.add(actividades.guardar((List<PlanMantenimiento>) arguments.get(5), null));
                        setProgress(85);
                        results.add(precios.guardar((List<ControlPrecio>) arguments.get(6), null));
                        setProgress(90);
                        results.add(fotos.guardar((BienVarianteFoto) arguments.get(7), null));
                        break;

                    case opUPDATE:
                        results.add(bienes.actualizar((BienVariante) arguments.get(1)));
                        setProgress(65);
                        results.add(compuestos.guardar((List<BienCompuestoVista>) arguments.get(2),
                                (List<BienCompuesto>) arguments.get(3)));
                        setProgress(70);
                        results.add(identificadores.guardar((List<VarianteIdentificadorVista>) arguments.get(4)));
                        setProgress(75);
                        results.add(lotes.guardar((List<ControlInventario>) arguments.get(5),
                                (List<ControlInventario>) arguments.get(6)));
                        setProgress(80);
                        results.add(actividades.guardar((List<PlanMantenimiento>) arguments.get(7),
                                (List<PlanMantenimiento>) arguments.get(8)));
                        setProgress(85);
                        results.add(precios.guardar((List<ControlPrecio>) arguments.get(9),
                                (List<ControlPrecio>) arguments.get(10)));
                        setProgress(90);
                        results.add(fotos.guardar((BienVarianteFoto) arguments.get(11),
                                (BienVarianteFoto) arguments.get(12)));
                        break;

                    case opDELETE_LIST:
                        List<BienVariante> selecteds = (List<BienVariante>) arguments.get(1);
                        results.add(bienes.borrar(selecteds));
                        break;

                    case opSAVE:
                        List<BienVariante> adds = (List<BienVariante>) arguments.get(1);
                        List<BienVariante> modifieds = (List<BienVariante>) arguments.get(2);
                        if (adds.size() > 0)
                            results.add(bienes.agregar(adds));
                        if (modifieds.size() > 0)
                            results.add(bienes.actualizar(modifieds));
                        break;

                    default:
                        results.add(bienes.obtenerLista());
                        setProgress(70);
                        results.add(unidades.obtenerLista());
                        setProgress(80);
                        results.add(tipoBienes.obtenerLista());
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
                                List<BienVariante> listaBienes = (List<BienVariante>) results.get(1);
                                List<Bien> listaTipos = (List<Bien>) results.get(2);
                                if (listaBienes.size() > 0)
                                    setDisplayData(listaTipos, listaBienes);
                                else if (!bienes.esCorrecto())
                                    JOptionPane.showMessageDialog(null, bienes.getMensaje());
                                break;

                            case opINSERT:
                                if (bienes.esCorrecto())
                                    setDisplayData((BienVariante) arguments.get(1));

                                else
                                    jbMessage.setText(bienes.getMensaje());
                                break;

                            case opUPDATE:
                                if (bienes.esCorrecto())
                                    resetElement((BienVariante) arguments.get(1));

                                else
                                    jbMessage.setText(bienes.getMensaje());
                                break;

                            case opDELETE_LIST:
                                if (bienes.esCorrecto())
                                {
                                    bsVariantes.removeRows((int[]) arguments.get(2));
                                    printRecordCount();
                                
                                    jbMessage.setText("Elemento(s) eliminado");
                                }
                                else
                                    jbMessage.setText(bienes.getEsquema());
                                break;

                            case opSAVE:
                                jbMessage.setText(bienes.getMensaje());
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
