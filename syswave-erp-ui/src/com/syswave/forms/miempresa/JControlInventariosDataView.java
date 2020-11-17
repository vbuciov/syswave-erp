package com.syswave.forms.miempresa;

import com.syswave.entidades.common.Entidad;
import com.syswave.entidades.configuracion.Usuario_tiene_Permiso;
import com.syswave.entidades.miempresa.BienVariante;
import com.syswave.entidades.miempresa.ControlAlmacen;
import com.syswave.entidades.miempresa.ControlInventario;
import com.syswave.forms.common.JTableDataView;
import com.syswave.forms.databinding.BienVariantesComboBoxModel;
import com.syswave.forms.databinding.ControlInventarioTableModel;
import com.syswave.swing.models.POJOTableModel;
import com.syswave.swing.table.editors.LookUpComboBoxTableCellEditor;
import com.syswave.swing.table.renders.LookUpComboBoxTableCellRenderer;
import com.syswave.logicas.miempresa.BienVariantesBusinessLogic;
import com.syswave.logicas.miempresa.ControlAlmacenesBusinessLogic;
import com.syswave.logicas.miempresa.ControlInventariosBusinessLogic;
import com.toedter.calendar.JDateChooserCellEditor;
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
public class JControlInventariosDataView extends JTableDataView implements IControlInventarioMediator, TableModelListener
{

    private final int opLOAD = 0;
    private final int opINSERT_LIST = 1;
    private final int opUPDATE_LIST = 2;
    private final int opDELETE_LIST = 3;
    private final int opINSERT = 4;
    private final int opUPDATE = 5;
    private final int opSAVE = 6;

    private boolean can_browse, can_create, can_update, can_delete;
    ControlInventariosBusinessLogic inventarios;
    ControlAlmacenesBusinessLogic almacenes;
    BienVariantesBusinessLogic partes;
    ControlInventarioTableModel bsControlInventarioes;
    BienVariantesComboBoxModel bsPresentacionesEditor;
    BienVariantesComboBoxModel bsPresentacionesRender;
    ControlInventarioSwingWorker swSecondPlane;
    List<Usuario_tiene_Permiso> permisos;
    String esquema;

    TableColumn colPresentacion;
    TableColumn colLote;
    TableColumn colExistencia;
    TableColumn colFechaEntrada;

    //---------------------------------------------------------------------
    private void grantAllPermisions()
    {
        can_browse = true;
        can_create = true;
        can_update = true;
        can_delete = true;
    }

    //---------------------------------------------------------------------
    public JControlInventariosDataView(IWindowContainer container)
    {
        super(container);
        initAttributes();
        grantAllPermisions();
    }

    //---------------------------------------------------------------------
    public JControlInventariosDataView(IWindowContainer container, List<Usuario_tiene_Permiso> values)
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

        bsControlInventarioes.setReadOnly(!can_update);
    }

    //---------------------------------------------------------------------
    private void initAttributes()
    {
        esquema = mainContainer.getOrigenDatoActual().getNombre();
        bsControlInventarioes = new ControlInventarioTableModel(new String[]
        {
            "Presentación:{id_variante}", "Lote y/o Marca:{lote}", "Existencia:{existencia}",
            "Minimo:{minimo}", "Máximo:{maximo}", "Reorden:{reorden}", "Fecha Entrada:{fecha_entrada}"
        });
        bsControlInventarioes.addTableModelListener(this);
        bsPresentacionesEditor = new BienVariantesComboBoxModel();
        bsPresentacionesRender = new BienVariantesComboBoxModel();
        partes = new BienVariantesBusinessLogic(esquema);
        inventarios = new ControlInventariosBusinessLogic(esquema);
        almacenes = new ControlAlmacenesBusinessLogic(esquema);

        jtbData.setModel(bsControlInventarioes);
        jtbData.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        if (jtbData.getColumnCount() > 0)
        {
            colPresentacion = jtbData.getColumnModel().getColumn(0);
            colPresentacion.setCellRenderer(new LookUpComboBoxTableCellRenderer<BienVariante>(bsPresentacionesRender));
            colPresentacion.setCellEditor(new LookUpComboBoxTableCellEditor<BienVariante>(bsPresentacionesEditor));
            colPresentacion.setPreferredWidth(280);

            colLote = jtbData.getColumnModel().getColumn(1);
            colLote.setPreferredWidth(250);

            colExistencia = jtbData.getColumnModel().getColumn(2);
            colExistencia.setPreferredWidth(100);

            colFechaEntrada = jtbData.getColumnModel().getColumn(6);
            colFechaEntrada.setCellEditor(new JDateChooserCellEditor());
            colFechaEntrada.setPreferredWidth(150);

            jtbData.setRowHeight((int) (jtbData.getRowHeight() * 1.5));
        }
    }

    //---------------------------------------------------------------------
    public void showDetail(ControlInventario elemento)
    {
        if (bsPresentacionesEditor.getData().size() > 0)
        {
            JControlInventariosDetailView dialogo = new JControlInventariosDetailView(this);
            mainContainer.addWindow(dialogo);

            if (elemento != null)
                dialogo.prepareForModify(elemento);

            else
                dialogo.prepareForNew();

            mainContainer.showCenter(dialogo);
        }

        else
            JOptionPane.showMessageDialog(this, "No se puede continuar sin haber capturado presentaciones", "Información", JOptionPane.WARNING_MESSAGE);
    }

    // -------------------------------------------------------------------
    @Override
    public void onAcceptNewElement(ControlInventario nuevo, List<ControlAlmacen> almacenes, List<ControlAlmacen> almacenes_borrados)
    {
        if (swSecondPlane == null || swSecondPlane.isDone())
        {
            List<Object> parametros = new ArrayList<Object>();
            swSecondPlane = new ControlInventarioSwingWorker();
            swSecondPlane.addPropertyChangeListener(this);
            parametros.add(opINSERT);
            parametros.add(nuevo);
            parametros.add(almacenes);
            parametros.add(almacenes_borrados);

            jbMessage.setText("Guardando...");
            swSecondPlane.execute(parametros);
        }

    }

    // -------------------------------------------------------------------
    @Override
    public void onAcceptModifyElement(ControlInventario modificado, List<ControlAlmacen> almacenes, List<ControlAlmacen> almacenes_borrados)
    {
        if (swSecondPlane == null || swSecondPlane.isDone())
        {
            List<Object> parametros = new ArrayList<Object>();
            swSecondPlane = new ControlInventarioSwingWorker();
            swSecondPlane.addPropertyChangeListener(this);
            parametros.add(opUPDATE);
            parametros.add(modificado);
            parametros.add(almacenes);
            parametros.add(almacenes_borrados);

            jbMessage.setText("Guardando...");
            swSecondPlane.execute(parametros);
        }
    }

    //---------------------------------------------------------------------
    private void resetElement(ControlInventario value)
    {
        bsControlInventarioes.fireTableRowsUpdated(jtbData.getSelectedRow(), jtbData.getSelectedRow());
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
    public List<ControlAlmacen> obtenerControlAlmacenes(ControlInventario elemento)
    {
        ControlAlmacen filtro = new ControlAlmacen();
        filtro.setIdVariante(elemento.getIdVariante());
        filtro.setConsecutivo(elemento.getConsecutivo());

        return almacenes.obtenerLista(filtro);
    }

    // -------------------------------------------------------------------
    @Override
    public List<BienVariante> obtenerPartes()
    {
        return bsPresentacionesEditor.getData();
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
                showDetail(bsControlInventarioes.getElementAt(index));
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
                    swSecondPlane = new ControlInventarioSwingWorker();
                    swSecondPlane.addPropertyChangeListener(this);
                    parametros.add(opDELETE_LIST);
                    int[] rowsHandlers = jtbData.getSelectedRows();
                    for (int i = 0; i < rowsHandlers.length; i++)
                    {
                        rowsHandlers[i] = jtbData.convertRowIndexToModel(rowsHandlers[i]);
                    }
                    parametros.add(bsControlInventarioes.getElementsAt(rowsHandlers));
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
                swSecondPlane = new ControlInventarioSwingWorker();
                swSecondPlane.addPropertyChangeListener(this);
                BienVariante buscarPartes = new BienVariante();
                buscarPartes.setEsInventario(true); //Solo bienes con control de inventario
                parametros.add(opLOAD);
                jbMessage.setText("Obteniendo información...");
                parametros.add(buscarPartes);

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
        List<ControlInventario> modificados = new ArrayList<>();
        List<ControlInventario> agregados = new ArrayList<>();
        List<ControlInventario> datos = bsControlInventarioes.getData();

        TableCellEditor editor = jtbData.getCellEditor();
        if (editor != null)
            editor.stopCellEditing();

        for (ControlInventario elemento : datos)
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
                swSecondPlane = new ControlInventarioSwingWorker();
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
                showDetail(bsControlInventarioes.getElementAt(index));
            }
        }
        else
            JOptionPane.showMessageDialog(this, "No cuenta con los permisos necesarios para ACTUALIZAR elementos");
    }

    // -------------------------------------------------------------------
    private void setDisplayData(List<BienVariante> bienes, List<ControlInventario> values)
    {
        bsPresentacionesEditor.setData(bienes);
        bsPresentacionesRender.setData(bienes);
        bsControlInventarioes.setData(values);
        
        printRecordCount();
        jbMessage.setText("Información obtenida");
    }

    //---------------------------------------------------------------------
    private void setDisplayData(ControlInventario value)
    {
        //value.acceptChanges();
        bsControlInventarioes.addRow(value);
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

                //Se esta cambiando la llave foranea-primaria
                /*if (e.getColumn() == colPresentacion.getModelIndex()
                        && !((ControlInventario) data).isSet())
                    ((ControlInventario) data).setModified();*/

                setChanges();
                jbMessage.setText("Cambios pendientes");
            }
        }
    }

    //---------------------------------------------------------------------
    //--  Esta clase controla el cargado en segundo plano las localidades.
    //---------------------------------------------------------------------
    private class ControlInventarioSwingWorker extends SwingWorker<List<Object>, Void>
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
                        results.add(inventarios.obtenerLista());
                        results.add(partes.obtenerListaPartes((BienVariante) arguments.get(1)));
                        break;

                    case opINSERT:
                        results.add(inventarios.agregar((ControlInventario) arguments.get(1)));
                        results.add(almacenes.guardar((List<ControlAlmacen>) arguments.get(2),
                                (List<ControlAlmacen>) arguments.get(3)));
                        break;

                    case opUPDATE:
                        results.add(inventarios.actualizar((ControlInventario) arguments.get(1)));
                        results.add(almacenes.guardar((List<ControlAlmacen>) arguments.get(2),
                                (List<ControlAlmacen>) arguments.get(3)));
                        break;

                    case opDELETE_LIST:
                        List<ControlInventario> selecteds = (List<ControlInventario>) arguments.get(1);
                        results.add(inventarios.borrar(selecteds));
                        break;

                    case opSAVE:
                        List<ControlInventario> adds = (List<ControlInventario>) arguments.get(1);
                        List<ControlInventario> modifieds = (List<ControlInventario>) arguments.get(2);
                        if (adds.size() > 0)
                            results.add(inventarios.agregar(adds));
                        if (modifieds.size() > 0)
                            results.add(inventarios.actualizar(modifieds));
                        break;

                    default:
                        results.add(inventarios.obtenerLista());
                        results.add(partes.obtenerLista());
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
                                List<ControlInventario> listaInventarios = (List<ControlInventario>) results.get(1);
                                List<BienVariante> listaBienes = (List<BienVariante>) results.get(2);

                                if (listaBienes.size() > 0)
                                    setDisplayData(listaBienes, listaInventarios);
                                else if (!inventarios.esCorrecto())
                                    JOptionPane.showMessageDialog(null, inventarios.getMensaje());
                                break;

                            case opINSERT:
                                if (inventarios.esCorrecto())
                                    setDisplayData((ControlInventario) arguments.get(1));

                                else
                                    jbMessage.setText(inventarios.getMensaje());
                                break;

                            case opUPDATE:
                                if (inventarios.esCorrecto())
                                    resetElement((ControlInventario) arguments.get(1));

                                else
                                    jbMessage.setText(inventarios.getMensaje());
                                break;

                            case opDELETE_LIST:
                                if (inventarios.esCorrecto())
                                {
                                    bsControlInventarioes.removeRows((int[]) arguments.get(2));
                                     printRecordCount();
                                
                                    jbMessage.setText("Elemento(s) eliminado(s)");
                                }
                                else
                                    jbMessage.setText(inventarios.getEsquema());
                                break;

                            case opSAVE:
                                jbMessage.setText(inventarios.getMensaje());
                                if (inventarios.esCorrecto())
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
