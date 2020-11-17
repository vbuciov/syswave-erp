package com.syswave.forms.configuracion;

import com.syswave.entidades.configuracion.Unidad;
import com.syswave.entidades.configuracion.Usuario_tiene_Permiso;
import com.syswave.forms.common.JTreeDataView;
import com.syswave.forms.databinding.UnidadesTreeModel;
import com.syswave.swing.tree.editors.POJOTreeCellEditor;
import com.syswave.swing.tree.renders.POJOTreeCellRenderer;
import com.syswave.logicas.configuracion.UnidadesBusinessLogic;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import com.syswave.forms.common.IWindowContainer;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class JUnidadDataView extends JTreeDataView implements IUnidadMediator, TreeModelListener
{

    private final int opLOAD = 0;
    private final int opINSERT_LIST = 1;
    private final int opUPDATE_LIST = 2;
    private final int opDELETE_LIST = 3;
    private final int opINSERT = 4;
    private final int opUPDATE = 5;
    private final int opSAVE = 6;

    private boolean can_browse, can_create, can_update, can_delete;
    UnidadesBusinessLogic Unidades;
    UnidadesTreeModel bsUnidades;
    UnidadSwingWorker swCargador;
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
    public JUnidadDataView(IWindowContainer container)
    {
        super(container);
        initAttributes();
        grantAllPermisions();
    }

    //---------------------------------------------------------------------
    public JUnidadDataView(IWindowContainer container, List<Usuario_tiene_Permiso> values)
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

        //bsUnidades.setReadOnly(!can_update);
    }

    private void initAttributes()
    {
        Unidades = new UnidadesBusinessLogic("configuracion");
        bsUnidades = new UnidadesTreeModel();
        bsUnidades.setUseForCheckeBoxes(false);
        bsUnidades.setCanRecursiveAddToModified(false);
        bsUnidades.addTreeModelListener(this);
        jtData.setModel(bsUnidades);
        jtData.setShowsRootHandles(true);
        jtData.setRootVisible(false);
        jtData.setCellRenderer(new POJOTreeCellRenderer<Unidad>());
        jtData.setCellEditor(new POJOTreeCellEditor<Unidad>());
        jtData.setToggleClickCount(1); //Clicks Necesarios para expandir
        jtData.setEditable(true);
    }

    //---------------------------------------------------------------------
    public void showDetail(Unidad elemento)
    {
        JUnidadDetailView dialogo = new JUnidadDetailView(this);
        mainContainer.addWindow(dialogo);

        if (elemento != null)
            dialogo.prepareForModify(elemento);

        else
            dialogo.prepareForNew();

        mainContainer.showCenter(dialogo);
    }

    //---------------------------------------------------------------------
    @Override
    public void doCreateProcess()
    {
        if (can_create)
            showDetail(null);
        else
            JOptionPane.showMessageDialog(this, "No cuenta con los permisos necesarios para INSERTAR elementos");
    }

    //---------------------------------------------------------------------
    @Override
    public void doUpdateProcess()
    {
        if (can_update)
        {
            if (jtData.getSelectionCount() > 0)
            {
                TreeNode selected = (TreeNode) jtData.getSelectionPath().getLastPathComponent();
                if (selected instanceof DefaultMutableTreeNode)
                    showDetail((Unidad) ((DefaultMutableTreeNode) selected).getUserObject());
            }
        }
        else
            JOptionPane.showMessageDialog(this, "No cuenta con los permisos necesarios para ACTUALIZAR elementos");
    }

    //---------------------------------------------------------------------
    @Override
    public void doDeleteProcess()
    {
        if (can_delete)
        {
            if (JOptionPane.showConfirmDialog(this, String.format("Ha seleccionado %d registro(s) para ser eleiminado(s)\n¿Esta seguro de continuar?", jtData.getSelectionCount())) == JOptionPane.OK_OPTION)
            {
                if (swCargador == null || swCargador.isDone())
                {
                    List<Object> parametros = new ArrayList<Object>();
                    swCargador = new UnidadSwingWorker();
                     swCargador.addPropertyChangeListener(this);
                    parametros.add(opDELETE_LIST);
                    TreePath[] rowsHandlers = jtData.getSelectionPaths();
                    List<Unidad> elementos = new ArrayList<Unidad>();
                    TreeNode selected;
                    for (TreePath path : rowsHandlers)
                    {
                        selected = (TreeNode) path.getLastPathComponent();
                        if (selected instanceof DefaultMutableTreeNode)
                            elementos.add((Unidad) ((DefaultMutableTreeNode) selected).getUserObject());
                    }
                    parametros.add(elementos);
                    parametros.add(rowsHandlers);
                    swCargador.execute(parametros);
                }
            }
        }

        else
            JOptionPane.showMessageDialog(this, "No cuenta con los permisos necesarios para eliminar elementos");
    }

    //---------------------------------------------------------------------
    @Override
    public void doRetrieveProcess()
    {
        if (can_browse)
        {
            if (swCargador == null || swCargador.isDone())
            {
                List<Object> parametros = new ArrayList<Object>();
                swCargador = new UnidadSwingWorker();
                 swCargador.addPropertyChangeListener(this);
                parametros.add(opLOAD);
                swCargador.execute(parametros);
            }
        }
        else
            JOptionPane.showMessageDialog(this, "No cuenta con los permisos necesarios para acceder a la información");
    }

    //---------------------------------------------------------------------
    @Override
    public void doSaveProcess()
    {
        int elementosTotales = 0;
        List<Unidad> modificados = new ArrayList<>();
        List<Unidad> agregados = new ArrayList<>();
        List<Unidad> datos = bsUnidades.getModifiedDatas();

        for (Unidad elemento : datos)
        {
            if (elemento.isNew())
                agregados.add(elemento);

            else if (elemento.isModified())
                modificados.add(elemento);
        }

        elementosTotales = modificados.size() + agregados.size();

        if (elementosTotales > 0)
        {
            if (swCargador == null || swCargador.isDone())
            {
                List<Object> parametros = new ArrayList<Object>();
                swCargador = new UnidadSwingWorker();
                 swCargador.addPropertyChangeListener(this);
                parametros.add(opSAVE);
                parametros.add(agregados);
                parametros.add(modificados);
                swCargador.execute(parametros);
            }
        }
        else
            JOptionPane.showMessageDialog(this, "No existen cambios a guardar");

    }

    //---------------------------------------------------------------------
    @Override
    public void doOpenProcess()
    {
        if (can_update)
        {
            if (jtData.getSelectionCount() > 0)
            {
                TreeNode selected = (TreeNode) jtData.getSelectionPath().getLastPathComponent();
                if (selected instanceof DefaultMutableTreeNode)
                    showDetail((Unidad) ((DefaultMutableTreeNode) selected).getUserObject());
            }
        }
        else
            JOptionPane.showMessageDialog(this, "No cuenta con los permisos necesarios para ACTUALIZAR elementos");
    }

    //---------------------------------------------------------------------
    private void setDisplayData(List<Unidad> values)
    {
        bsUnidades.setData(values);

        jtData.expandPath(new TreePath(((DefaultMutableTreeNode) bsUnidades.getRoot()).getPath()));
        printRecordCount();
    }

    //---------------------------------------------------------------------
    private void setDisplayData(Unidad value, DefaultMutableTreeNode parentNode)
    {
        if (parentNode != null)
        {
            bsUnidades.addNodeTo(new DefaultMutableTreeNode(value), parentNode);
        }

        else
        {
            bsUnidades.addNodeToRoot(new DefaultMutableTreeNode(value));
        }

        jbMessage.setText("Cambio guardado");
        printRecordCount();
    }

    //---------------------------------------------------------------------
    @Override
    public void onAcceptModifyElement(Unidad elemento)
    {
        if (swCargador == null || swCargador.isDone())
        {
            List<Object> parametros = new ArrayList<Object>();
            swCargador = new UnidadSwingWorker();
             swCargador.addPropertyChangeListener(this);
            parametros.add(opUPDATE);
            parametros.add(elemento);
            jbMessage.setText("Guardando...");
            swCargador.execute(parametros);
        }

    }

    //---------------------------------------------------------------------
    @Override
    public void onAcceptNewElement(Unidad nuevo)
    {
        if (swCargador == null || swCargador.isDone())
        {
            DefaultMutableTreeNode parentNode = null;
            List<Object> parametros = new ArrayList<Object>();
            swCargador = new UnidadSwingWorker();
             swCargador.addPropertyChangeListener(this);

            if (jtData.getSelectionCount() > 0)
            {
                if (JOptionPane.showConfirmDialog(this, "¿Deseas agregar el nuevo elemento como hijo del elemento seleccionado?,\n en caso contrario será como una raíz", "Pregunta", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
                {
                    parentNode = (DefaultMutableTreeNode) jtData.getSelectionPath().getLastPathComponent();
                    Unidad parentElement = (Unidad) parentNode.getUserObject();
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
            swCargador.execute(parametros);
        }
    }

    //---------------------------------------------------------------------
    private void resetElement(Unidad value)
    {
        //value.acceptChanges();
        if (jtData.getSelectionCount() > 0)
        {
            TreeNode selected = (TreeNode) jtData.getSelectionPath().getLastPathComponent();
            bsUnidades.nodeChanged(selected);
        }

        jbMessage.setText("Cambio guardado");
    }

    //---------------------------------------------------------------------
    private void removeNodes(TreePath[] nodes)
    {
        TreeNode selected;
        for (TreePath node : nodes)
        {
            selected = (TreeNode) node.getLastPathComponent();
            bsUnidades.removeNodeFromParent((MutableTreeNode) selected);
        }
    }

    //---------------------------------------------------------------------
    @Override
    public void treeNodesChanged(TreeModelEvent e)
    {
        setChanges();
        jbMessage.setText("Cambios pendientes");
    }

    //---------------------------------------------------------------------
    @Override
    public void treeNodesInserted(TreeModelEvent e)
    {

    }

    //---------------------------------------------------------------------
    @Override
    public void treeNodesRemoved(TreeModelEvent e)
    {

    }

    //---------------------------------------------------------------------
    @Override
    public void treeStructureChanged(TreeModelEvent e)
    {

    }

    //---------------------------------------------------------------------
    //--  Esta clase controla el cargado en segundo plano las localidades.
    //---------------------------------------------------------------------
    private class UnidadSwingWorker extends SwingWorker<List<Object>, Void>
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
                        results.add(Unidades.obtenerLista());
                        break;

                    case opINSERT:
                        results.add(Unidades.agregar((Unidad) arguments.get(1)));
                        /*arguments.add(UsuarioPermisos.guardar((Usuario)arguments.get(1), 
                         (List<NodoPermiso>)arguments.get(2), 
                         (int)arguments.get(3)));*/
                        break;

                    case opUPDATE:
                        results.add(Unidades.actualizar((Unidad) arguments.get(1)));
                        /*arguments.add(UsuarioPermisos.guardar((Usuario)arguments.get(1), 
                         (List<NodoPermiso>)arguments.get(2), 
                         (int)arguments.get(3)));*/
                        break;

                    case opDELETE_LIST:
                        List<Unidad> selecteds = (List<Unidad>) arguments.get(1);
                        results.add(Unidades.borrar(selecteds));
                        break;

                    case opSAVE:
                        List<Unidad> adds = (List<Unidad>) arguments.get(1);
                        List<Unidad> modifieds = (List<Unidad>) arguments.get(2);
                        if (adds.size() > 0)
                            results.add(Unidades.agregar(adds));
                        if (modifieds.size() > 0)
                            results.add(Unidades.actualizar(modifieds));
                        break;

                    default:
                        results.add(Unidades.obtenerLista());
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
                                List<Unidad> listaUsuarios = (List<Unidad>) results.get(1);
                                if (listaUsuarios.size() > 0)
                                    setDisplayData(listaUsuarios);
                                else if (!Unidades.esCorrecto())
                                    JOptionPane.showMessageDialog(null, Unidades.getMensaje());
                                break;

                            case opINSERT:
                                if (Unidades.esCorrecto())
                                    setDisplayData((Unidad) arguments.get(1), (DefaultMutableTreeNode) arguments.get(2));

                                else
                                    jbMessage.setText(Unidades.getMensaje());
                                break;

                            case opUPDATE:
                                if (Unidades.esCorrecto())
                                    resetElement((Unidad) arguments.get(1));

                                else
                                    jbMessage.setText(Unidades.getMensaje());
                                break;

                            case opDELETE_LIST:
                                if (Unidades.esCorrecto())
                                {
                                    removeNodes((TreePath[]) arguments.get(2));
                                    printRecordCount();
                                }
                                break;

                            case opSAVE:
                                jbMessage.setText(Unidades.getMensaje());
                                if (Unidades.esCorrecto())
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
