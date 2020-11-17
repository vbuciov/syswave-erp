package com.syswave.forms.miempresa;

import com.syswave.entidades.common.Entidad;
import com.syswave.entidades.configuracion.Usuario_tiene_Permiso;
import com.syswave.entidades.miempresa.TipoComprobante;
import com.syswave.forms.common.JTableDataView;
import com.syswave.swing.models.POJOTreeTableModel;
import com.syswave.forms.databinding.TiposComprobantesTreeTableModel;
import com.syswave.swing.tree.renders.POJOTreeCellRenderer;
import com.syswave.logicas.miempresa.TiposComprobantesBusinessLogic;
import de.hameister.treetable.MyTreeTable;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import com.syswave.forms.common.IWindowContainer;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class JTiposComprobantesDataView extends JTableDataView implements ITipoComprobanteMediator, TableModelListener
{

    private final int opLOAD = 0;
    /* private final int opINSERT_LIST = 1;
     private final int opUPDATE_LIST = 2;*/
    private final int opDELETE_LIST = 3;
    private final int opINSERT = 4;
    private final int opUPDATE = 5;
    private final int opSAVE = 6;

    private boolean can_browse, can_create, can_update, can_delete;

    TiposComprobantesTreeTableModel bsTiposComprobantes;
    TiposComprobantesBusinessLogic bienes;

    TableColumn colNombre;
    TipoComprobanteSwingWorker swSecondPlane;
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
    public JTiposComprobantesDataView(IWindowContainer container)
    {
        super(null, container);
        initAttributes();
        grantAllPermisions();
    }

    // -------------------------------------------------------------------
    public JTiposComprobantesDataView(IWindowContainer container, List<Usuario_tiene_Permiso> values)
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

        bsTiposComprobantes.setReadOnly(!can_update);
    }

    private void initAttributes()
    {
        esquema = mainContainer.getOrigenDatoActual().getNombre();
        bienes = new TiposComprobantesBusinessLogic(esquema);
        
        bsTiposComprobantes = new TiposComprobantesTreeTableModel(new String[]
        {
            "Nombre:{node}",
            "¿Afectar Inventario?:{afecta_inventario}",
            "¿Afecta saldos?:{afecta_saldos}",
            "¿Es documento comercial?:{es_comercial}",
            "¿Activo?:{es_activo}"
        });
        bsTiposComprobantes.addTableModelListener(this);

        jtbData = new MyTreeTable(bsTiposComprobantes);
        MyTreeTable realTable = ((MyTreeTable) jtbData);
        realTable.setRootVisible(false);
        realTable.setCellRenderer(new POJOTreeCellRenderer<TipoComprobante>());
        setTable(jtbData);
        //jtbData.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        if (jtbData.getColumnCount() > 0)
        {
            colNombre = jtbData.getColumnModel().getColumn(0);
            colNombre.setPreferredWidth(200);

            //Nota: Debido a los renders que se estan utilizando es necesario tener un renglón más alto.
            //jtbData.setRowHeight( (int)(jtbData.getRowHeight() * 1.5));
        }
    }

    // -------------------------------------------------------------------
    @Override
    public void showDetail(TipoComprobante elemento)
    {
        JTipoComprobanteDetailView dialogo = new JTipoComprobanteDetailView(this);
        mainContainer.addWindow(dialogo);

        if (elemento != null)
            dialogo.prepareForModify(elemento);

        else
            dialogo.prepareForNew();

        mainContainer.showCenter(dialogo);
    }

    // -------------------------------------------------------------------
    @Override
    public void onAcceptNewElement(TipoComprobante nuevo)
    {
        if (swSecondPlane == null || swSecondPlane.isDone())
        {
            DefaultMutableTreeNode parentNode = null;
            List<Object> parametros = new ArrayList<Object>();
            swSecondPlane = new TipoComprobanteSwingWorker();
            swSecondPlane.addPropertyChangeListener(this);

            MyTreeTable jtData = (MyTreeTable) jtbData;

            if (jtData.getSelectionCount() > 0)
            {
                if (JOptionPane.showConfirmDialog(this, "¿Deseas agregar el nuevo elemento como hijo del elemento seleccionado?,\n en caso contrario será como una raíz", "Pregunta", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
                {
                    parentNode = (DefaultMutableTreeNode) jtData.getSelectionPath().getLastPathComponent();
                    TipoComprobante parentElement = (TipoComprobante) parentNode.getUserObject();
                    nuevo.setIdPadre(parentElement.getId());
                    nuevo.setNivel(parentElement.getNivel() + 1);
                }

                else
                    nuevo.setNivel(0);
            }

            else
                nuevo.setNivel(0);
            parametros.add(opINSERT);
            parametros.add(nuevo);
            parametros.add(parentNode);

            jbMessage.setText("Guardando...");
            swSecondPlane.execute(parametros);
        }

    }

    // -------------------------------------------------------------------
    @Override
    public void onAcceptModifyElement(TipoComprobante modificado)
    {
        if (swSecondPlane == null || swSecondPlane.isDone())
        {
            List<Object> parametros = new ArrayList<Object>();
            swSecondPlane = new TipoComprobanteSwingWorker();
            swSecondPlane.addPropertyChangeListener(this);
            parametros.add(opUPDATE);
            parametros.add(modificado);

            jbMessage.setText("Guardando...");
            swSecondPlane.execute(parametros);
        }
    }


    // -------------------------------------------------------------------
    @Override
    public String obtenerOrigenDato()
    {
        return esquema;
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
                showDetail(bsTiposComprobantes.getElementAt(index));
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
                    swSecondPlane = new TipoComprobanteSwingWorker();
                    swSecondPlane.addPropertyChangeListener(this);
                    parametros.add(opDELETE_LIST);
                    /*int[] rowsHandlers = jtbData.getSelectedRows();
                     for (int i = 0; i < rowsHandlers.length; i++)
                     {
                     rowsHandlers[i] = jtbData.convertRowIndexToModel(rowsHandlers[i]);
                     }
                     parametros.add(bsTiposComprobantes.getElementsAt(rowsHandlers));
                     parametros.add(rowsHandlers);*/

                    TreePath[] rowsHandlers = ((MyTreeTable) jtbData).getSelectionPaths();
                    List<TipoComprobante> elementos = new ArrayList<TipoComprobante>();
                    TreeNode selected;
                    for (TreePath path : rowsHandlers)
                    {
                        selected = (TreeNode) path.getLastPathComponent();
                        if (selected instanceof DefaultMutableTreeNode)
                            elementos.add((TipoComprobante) ((DefaultMutableTreeNode) selected).getUserObject());
                    }
                    parametros.add(elementos);
                    parametros.add(rowsHandlers);
                    jbMessage.setText("Eleminando elemento(s)...");
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
                swSecondPlane = new TipoComprobanteSwingWorker();
                swSecondPlane.addPropertyChangeListener(this);
                parametros.add(opLOAD);
                jbMessage.setText("Obteniendo tipos de comprobantes...");
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
        List<TipoComprobante> modificados = new ArrayList<>();
        List<TipoComprobante> agregados = new ArrayList<>();
        List<TipoComprobante> datos = bsTiposComprobantes.getData();

        TableCellEditor editor = jtbData.getCellEditor();
        if (editor != null)
            editor.stopCellEditing();

        for (TipoComprobante elemento : datos)
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
                swSecondPlane = new TipoComprobanteSwingWorker();
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
                showDetail(bsTiposComprobantes.getElementAt(index));
            }
        }
        else
            JOptionPane.showMessageDialog(this, "No cuenta con los permisos necesarios para ACTUALIZAR elementos");
    }

    // -------------------------------------------------------------------
    private void setDisplayData(List<TipoComprobante> values)
    {
        bsTiposComprobantes.setData(values);

        printRecordCount();
        jbMessage.setText("Tipos de comprobantes obtenidos");
    }

    //---------------------------------------------------------------------
    private void setDisplayData(TipoComprobante value, DefaultMutableTreeNode parentNode)
    {
         if (parentNode != null)
        {
            bsTiposComprobantes.addNodeTo(new DefaultMutableTreeNode(value), parentNode);
        }

        else
        {
            bsTiposComprobantes.addNodeToRoot(new DefaultMutableTreeNode(value));
        }


        // bsTiposComprobantes.addRow(value);
        jbMessage.setText("Nuevo agregado");
        printRecordCount();
    }
    
     //---------------------------------------------------------------------
    private void resetElement(TipoComprobante value)
    {
         MyTreeTable jtData = (MyTreeTable) jtbData;
        //value.acceptChanges();
        if (jtData.getSelectionCount() > 0)
        {
            TreeNode selected = (TreeNode) jtData.getSelectionPath().getLastPathComponent();
           
            bsTiposComprobantes.nodeChanged(selected);
             bsTiposComprobantes.fireTableRowsUpdated(jtbData.getSelectedRow(), jtbData.getSelectedRow());
        }

        jbMessage.setText("Cambio guardado");
    }
    
      //---------------------------------------------------------------------
   /* private void resetElement(TipoComprobante value)
    {
        value.acceptChanges();
        bsTiposComprobantes.fireTableRowsUpdated(jtbData.getSelectedRow(), jtbData.getSelectedRow());
        jbMessage.setText("Cambio guardado");
    }*/

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
                POJOTreeTableModel model = (POJOTreeTableModel) e.getSource();
                //String columnName = model.getColumnName(column);
                Entidad data = (Entidad) model.getElementAt(row);

                if (!data.isSet())
                    data.setModified();
                setChanges();
            }
        }
    }

    //---------------------------------------------------------------------
    @Override
    protected void printRecordCount()
    {
        TreeModel current = (TreeModel) jtbData.getModel();
        if (current instanceof POJOTreeTableModel)
            jbContador.setText(((POJOTreeTableModel) current).size() + " Registro(s)");

        else
            jbContador.setText(jtbData.getRowCount() + " Registro(s)");
    }

    //---------------------------------------------------------------------
    //--  Esta clase controla el cargado en segundo plano de los origenes de datos.
    //---------------------------------------------------------------------
    private class TipoComprobanteSwingWorker extends SwingWorker<List<Object>, Void>
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
                        //results.add(categorias.obtenerListaHojas());
                        break;

                    case opINSERT:
                        arguments.add(bienes.agregar((TipoComprobante) arguments.get(1)));
                        /*arguments.add(variantes.guardar((List<TipoComprobanteVariante>)arguments.get(2), 
                         (List<TipoComprobanteVariante>)arguments.get(3)));*/
                        break;

                    case opUPDATE:
                        arguments.add(bienes.actualizar((TipoComprobante) arguments.get(1)));
                        /*arguments.add(variantes.guardar((List<TipoComprobanteVariante>)arguments.get(2), 
                         (List<TipoComprobanteVariante>)arguments.get(3)));*/
                        break;

                    case opDELETE_LIST:
                        List<TipoComprobante> selecteds = (List<TipoComprobante>) arguments.get(1);
                        results.add(bienes.borrar(selecteds));
                        break;

                    case opSAVE:
                        List<TipoComprobante> adds = (List<TipoComprobante>) arguments.get(1);
                        List<TipoComprobante> modifieds = (List<TipoComprobante>) arguments.get(2);
                        if (adds.size() > 0)
                            arguments.add(bienes.agregar(adds));
                        if (modifieds.size() > 0)
                            arguments.add(bienes.actualizar(modifieds));
                        break;

                    default:
                        results.add(bienes.obtenerLista());
                        //results.add(categorias.obtenerLista());
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
                                List<TipoComprobante> listaTipos = (List<TipoComprobante>) results.get(1);
                                //List<Categoria> listaTipos = (List<Categoria>) results.get(2);
                                if (listaTipos.size() > 0)
                                    setDisplayData(listaTipos);
                                else if (!bienes.esCorrecto())
                                    JOptionPane.showMessageDialog(null, bienes.getMensaje());
                                break;

                            case opINSERT:
                                if (bienes.esCorrecto())
                                    setDisplayData((TipoComprobante) arguments.get(1), (DefaultMutableTreeNode) arguments.get(2));

                                else
                                    jbMessage.setText(bienes.getMensaje());
                                break;

                            case opUPDATE:
                                if (bienes.esCorrecto())
                                    resetElement((TipoComprobante) arguments.get(1));

                                else
                                    jbMessage.setText(bienes.getMensaje());
                                break;

                            case opDELETE_LIST:
                                if (bienes.esCorrecto())
                                {
                                    //bsTiposComprobantes.removeRows((int[]) arguments.get(2));
                                    bsTiposComprobantes.removeNodes((TreePath[]) arguments.get(2));
                                    printRecordCount();

                                    jbMessage.setText("Elemento(s) eliminados");
                                }
                                else
                                    jbMessage.setText(bienes.getMensaje());
                                break;

                            case opSAVE:
                                jbMessage.setText(bienes.getMensaje());
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
