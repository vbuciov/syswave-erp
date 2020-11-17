package com.syswave.forms.miempresa;

import com.syswave.entidades.common.Entidad;
import com.syswave.entidades.configuracion.Usuario_tiene_Permiso;
import com.syswave.entidades.miempresa.Moneda;
import com.syswave.entidades.miempresa.MonedaCambio;
import com.syswave.forms.common.JTableDataView;
import com.syswave.forms.databinding.MonedaCambioTableModel;
import com.syswave.forms.databinding.MonedasComboBoxModel;
import com.syswave.swing.models.POJOTableModel;
import com.syswave.swing.table.editors.LookUpComboBoxTableCellEditor;
import com.syswave.swing.table.renders.LookUpComboBoxTableCellRenderer;
import com.syswave.logicas.miempresa.MonedaCambioBusinessLogic;
import com.syswave.logicas.miempresa.MonedasBusinessLogic;
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
public class JMonedaCambioDataView extends JTableDataView implements TableModelListener, IMonedaCambioMediator
{

    private final int opLOAD = 0;
    private final int opINSERT_LIST = 1;
    private final int opUPDATE_LIST = 2;
    private final int opDELETE_LIST = 3;
    private final int opINSERT = 4;
    private final int opUPDATE = 5;
    private final int opSAVE = 6;

    private boolean can_browse, can_create, can_update, can_delete;

    MonedaCambioTableModel bsCambioMoneda;
    MonedasComboBoxModel bsMonedaOrigenRender;
    MonedasComboBoxModel bsMonedaOrigenEditor;
    MonedasComboBoxModel bsMonedaDestinoRender;
    MonedasComboBoxModel bsMonedaDestinoEditor;

    MonedasBusinessLogic monedas;
    MonedaCambioBusinessLogic monedasCambio;

    IMonedaCambioFramedMediator owner;
    boolean esNuevo;
    TableColumn colIdMonedaOrigen, colProporcion, colIdMonedaDestino,
            colFecha;

    MonedaCambioSwingWorker swSecondPlane;
    List<Usuario_tiene_Permiso> permisos;

    //---------------------------------------------------------------------
    public JMonedaCambioDataView(IWindowContainer container)
    {
        super(container);
        initAtributes();
        grantAllPermisions();
    }

    //---------------------------------------------------------------------
    public JMonedaCambioDataView(IWindowContainer container, List<Usuario_tiene_Permiso> values)
    {
        super(container);
        initAtributes();
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

        bsCambioMoneda.setReadOnly(!can_update);
    }

    //---------------------------------------------------------------------
    private void grantAllPermisions()
    {
        can_browse = true;
        can_create = true;
        can_update = true;
        can_delete = true;
    }

    //---------------------------------------------------------------------
    private void initAtributes()
    {
        esNuevo = true;

        monedas = new MonedasBusinessLogic(mainContainer.getOrigenDatoActual().getNombre());
        monedasCambio = new MonedaCambioBusinessLogic(monedas.getEsquema());
        bsCambioMoneda = new MonedaCambioTableModel(new String[]
        {
            "Un (1):{id_moneda_origen}", "Son:{proporcion}", "De (N):{id_moneda_destino}", "Fecha Validez:{fecha_validez}"
        });
        bsMonedaOrigenRender = new MonedasComboBoxModel();
        bsMonedaOrigenEditor = new MonedasComboBoxModel();
        bsMonedaDestinoRender = new MonedasComboBoxModel();
        bsMonedaDestinoEditor = new MonedasComboBoxModel();
        bsCambioMoneda.addTableModelListener(this);

        jtbData.setModel(bsCambioMoneda);
        if (jtbData.getColumnCount() > 0)
        {
            colIdMonedaOrigen = jtbData.getColumnModel().getColumn(0);
            colIdMonedaOrigen.setCellRenderer(new LookUpComboBoxTableCellRenderer<Moneda>(bsMonedaOrigenRender));
            colIdMonedaOrigen.setCellEditor(new LookUpComboBoxTableCellEditor<Moneda>(bsMonedaOrigenEditor));
            colIdMonedaOrigen.setPreferredWidth(150);

            colProporcion = jtbData.getColumnModel().getColumn(1);
            colProporcion.setPreferredWidth(60);

            colIdMonedaDestino = jtbData.getColumnModel().getColumn(2);
            colIdMonedaDestino.setCellRenderer(new LookUpComboBoxTableCellRenderer<Moneda>(bsMonedaDestinoRender));
            colIdMonedaDestino.setCellEditor(new LookUpComboBoxTableCellEditor<Moneda>(bsMonedaDestinoEditor));
            colIdMonedaDestino.setPreferredWidth(60);

            colFecha = jtbData.getColumnModel().getColumn(3);
            colFecha.setCellEditor(new JDateChooserCellEditor());
            colFecha.setPreferredWidth(150);

            //Nota: Debido a los renders que se estan utilizando es necesario tener un renglón más alto.
            jtbData.setRowHeight((int) (jtbData.getRowHeight() * 1.5));
        }
    }

    // -------------------------------------------------------------------
    @Override
    public void showDetail(MonedaCambio elemento)
    {
        JMonedaCambioDetailView dialogo = new JMonedaCambioDetailView(this);
        mainContainer.addWindow(dialogo);

        if (elemento != null)
            dialogo.prepareForModify(elemento);

        else
            dialogo.prepareForNew();

        mainContainer.showCenter(dialogo);
    }

    // -------------------------------------------------------------------
    @Override
    public void onAcceptNewElement(MonedaCambio nuevo)
    {
        if (swSecondPlane == null || swSecondPlane.isDone())
        {
            List<Object> parametros = new ArrayList<Object>();
            swSecondPlane = new MonedaCambioSwingWorker();
            swSecondPlane.addPropertyChangeListener(this);
            parametros.add(opINSERT);
            parametros.add(nuevo);

            jbMessage.setText("Guardando...");
            swSecondPlane.execute(parametros);
        }

    }

    // -------------------------------------------------------------------
    @Override
    public void onAcceptModifyElement(MonedaCambio modificado)
    {
        if (swSecondPlane == null || swSecondPlane.isDone())
        {
            List<Object> parametros = new ArrayList<Object>();
            swSecondPlane = new MonedaCambioSwingWorker();
            swSecondPlane.addPropertyChangeListener(this);
            parametros.add(opUPDATE);
            parametros.add(modificado);

            jbMessage.setText("Guardando...");
            swSecondPlane.execute(parametros);
        }
    }

    //---------------------------------------------------------------------
    private void resetElement(MonedaCambio value)
    {
        //value.acceptChanges();
        bsCambioMoneda.fireTableRowsUpdated(jtbData.getSelectedRow(), jtbData.getSelectedRow());
        jbMessage.setText("Cambio guardado");
    }

    // -------------------------------------------------------------------
    @Override
    public String obtenerOrigenDato()
    {
        return mainContainer.getOrigenDatoActual().getNombre();
    }

    //---------------------------------------------------------------------
    @Override
    public List<Moneda> obtenerMonedas()
    {
        return bsMonedaOrigenEditor.getData();
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
                showDetail(bsCambioMoneda.getElementAt(index));
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
                    swSecondPlane = new MonedaCambioSwingWorker();
                    swSecondPlane.addPropertyChangeListener(this);
                    parametros.add(opDELETE_LIST);
                    int[] rowsHandlers = jtbData.getSelectedRows();
                    for (int i = 0; i < rowsHandlers.length; i++)
                    {
                        rowsHandlers[i] = jtbData.convertRowIndexToModel(rowsHandlers[i]);
                    }
                    parametros.add(bsCambioMoneda.getElementsAt(rowsHandlers));
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
                swSecondPlane = new MonedaCambioSwingWorker();
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
        List<MonedaCambio> modificados = new ArrayList<>();
        List<MonedaCambio> agregados = new ArrayList<>();
        List<MonedaCambio> datos = bsCambioMoneda.getData();

        tryStopDetailCellEditor(jtbData);

        for (MonedaCambio elemento : datos)
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
                swSecondPlane = new MonedaCambioSwingWorker();
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
                showDetail(bsCambioMoneda.getElementAt(index));
            }
        }
        else
            JOptionPane.showMessageDialog(this, "No cuenta con los permisos necesarios para ACTUALIZAR elementos");
    }

    // -------------------------------------------------------------------
    private void setDisplayData(List<Moneda> tiposMoneda, List<MonedaCambio> values)
    {
        bsMonedaDestinoEditor.setData(tiposMoneda);
        bsMonedaDestinoRender.setData(tiposMoneda);
        bsMonedaOrigenEditor.setData(tiposMoneda);
        bsMonedaOrigenRender.setData(tiposMoneda);
        bsCambioMoneda.setData(values);
        
         printRecordCount();
        jbMessage.setText("Información obtenida");
    }

    //---------------------------------------------------------------------
    private void setDisplayData(MonedaCambio value)
    {
        //value.acceptChanges();
        bsCambioMoneda.addRow(value);
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

                if (e.getColumn() == colIdMonedaOrigen.getModelIndex()
                        && !((MonedaCambio) data).getHasOneMonedaOrigen().isSet())
                    ((MonedaCambio) data).getHasOneMonedaOrigen().setModified();

                else if (e.getColumn() == colIdMonedaDestino.getModelIndex()
                        && !((MonedaCambio) data).getHasOneMonedaDestino().isSet())
                    ((MonedaCambio) data).getHasOneMonedaDestino().setModified();

                setChanges();
                jbMessage.setText("Cambios pendientes");
            }
        }
    }

    //---------------------------------------------------------------------
    //--  Esta clase controla el cargado en segundo plano de los origenes de datos.
    //---------------------------------------------------------------------
    private class MonedaCambioSwingWorker extends SwingWorker<List<Object>, Void>
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
                        results.add(monedasCambio.obtenerLista());
                        results.add(monedas.obtenerLista());
                        break;

                    case opINSERT:
                        results.add(monedasCambio.agregar((MonedaCambio) arguments.get(1)));
                        break;

                    case opUPDATE:
                        results.add(monedasCambio.actualizar((MonedaCambio) arguments.get(1)));
                        break;

                    case opDELETE_LIST:
                        results.add(monedasCambio.borrar((List<MonedaCambio>) arguments.get(1)));
                        break;

                    case opSAVE:
                        List<MonedaCambio> adds = (List<MonedaCambio>) arguments.get(1);
                        List<MonedaCambio> modifieds = (List<MonedaCambio>) arguments.get(2);
                        if (adds.size() > 0)
                            results.add(monedasCambio.agregar(adds));
                        if (modifieds.size() > 0)
                            results.add(monedasCambio.actualizar(modifieds));
                        break;

                    default:
                        results.add(monedasCambio.obtenerLista());
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
                                List<MonedaCambio> listaCambio = (List<MonedaCambio>) results.get(1);
                                List<Moneda> listaMonedas = (List<Moneda>) results.get(2);
                                if (listaMonedas.size() > 0)
                                    setDisplayData(listaMonedas, listaCambio);
                                else if (!monedasCambio.esCorrecto())
                                    JOptionPane.showMessageDialog(null, monedasCambio.getMensaje());
                                break;

                            case opINSERT:
                                if (monedasCambio.esCorrecto())
                                    setDisplayData((MonedaCambio) arguments.get(1));

                                else
                                    jbMessage.setText(monedasCambio.getMensaje());
                                break;

                            case opUPDATE:
                                if (monedasCambio.esCorrecto())
                                    resetElement((MonedaCambio) arguments.get(1));

                                else
                                    jbMessage.setText(monedasCambio.getMensaje());
                                break;

                            case opDELETE_LIST:
                                if (monedasCambio.esCorrecto())
                                {
                                    bsCambioMoneda.removeRows((int[]) arguments.get(2));
                                     printRecordCount();
                                
                                    jbMessage.setText("Elemento(s) eliminado(s)");
                                }
                                else
                                    jbMessage.setText(monedasCambio.getEsquema());
                                break;

                            case opSAVE:
                                jbMessage.setText(monedasCambio.getMensaje());
                                if (monedasCambio.esCorrecto())
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
