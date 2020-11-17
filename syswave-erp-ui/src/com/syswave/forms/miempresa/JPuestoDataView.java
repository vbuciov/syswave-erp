package com.syswave.forms.miempresa;

import com.syswave.entidades.configuracion.Usuario_tiene_Permiso;
import com.syswave.entidades.miempresa.Puesto;
import com.syswave.forms.common.JTreeDataView;
import com.syswave.forms.databinding.PuestosTreeModel;
import com.syswave.swing.tree.editors.POJOTreeCellEditor;
import com.syswave.swing.tree.renders.POJOTreeCellRenderer;
import com.syswave.logicas.miempresa.PuestoBusinessLogic;
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
 * @author sis5
 */
public class JPuestoDataView extends JTreeDataView implements IPuestoMediator, TreeModelListener
{

    private final int opLOAD = 0;
    private final int opINSERT_LIST = 1;
    private final int opUPDATE_LIST = 2;
    private final int opDELETE_LIST = 3;
    private final int opINSERT = 4;
    private final int opUPDATE = 5;
    private final int opSAVE = 6;

    private boolean can_browse, can_create, can_update, can_delete;

    private PuestoBusinessLogic puestos;
    private PuestosTreeModel bsPuestos;
    private PuestoSwingWorker swSecondPlane;
    List<Usuario_tiene_Permiso> permisos;

    //------------------------------------------------------------------
    private void grantAllPermisions()
    {
        can_browse = true;
        can_create = true;
        can_update = true;
        can_delete = true;
    }

    //------------------------------------------------------------------
    public JPuestoDataView(IWindowContainer container)
    {
        super(container);
        initAttributes(container);
        grantAllPermisions();
    }

    //------------------------------------------------------------------
    public JPuestoDataView(IWindowContainer container, List<Usuario_tiene_Permiso> values)
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

        //bsPuestos.setReadOnly(!can_update);
    }

    //------------------------------------------------------------------
    private void initAttributes(IWindowContainer container)
    {
        puestos = new PuestoBusinessLogic(container.getOrigenDatoActual().getNombre());
        bsPuestos = new PuestosTreeModel();
        bsPuestos.setUseForCheckeBoxes(false);
        bsPuestos.setCanRecursiveAddToModified(false);
        bsPuestos.addTreeModelListener(this);
        jtData.setModel(bsPuestos);
        jtData.setShowsRootHandles(true);
        jtData.setRootVisible(false);
        jtData.setCellRenderer(new POJOTreeCellRenderer<Puesto>());
        jtData.setCellEditor(new POJOTreeCellEditor<Puesto>());
        jtData.setToggleClickCount(1); //Clicks Necesarios para expandir
        jtData.setEditable(true);
    }

    //---------------------------------------------------------------------
    public void showDetail(Puesto elemento)
    {
        JPuestoDetailView dialogo = new JPuestoDetailView(this);
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
            if (jtData.getSelectionCount() > 0)
            {
                TreeNode selected = (TreeNode) jtData.getSelectionPath().getLastPathComponent();
                if (selected instanceof DefaultMutableTreeNode)
                    showDetail((Puesto) ((DefaultMutableTreeNode) selected).getUserObject());
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
        {
            if (JOptionPane.showConfirmDialog(this, String.format("Ha seleccionado %d registro(s) para ser eleiminado(s)\n¿Esta seguro de continuar?", jtData.getSelectionCount())) == JOptionPane.OK_OPTION)
            {
                if (swSecondPlane == null || swSecondPlane.isDone())
                {
                    List<Object> parametros = new ArrayList<Object>();
                    jbMessage.setText("Eliminando elemento(s)...");
                    swSecondPlane = new PuestoSwingWorker();
                    swSecondPlane.addPropertyChangeListener(this);
                    parametros.add(opDELETE_LIST);
                    TreePath[] rowsHandlers = jtData.getSelectionPaths();
                    List<Puesto> elementos = new ArrayList<Puesto>();
                    TreeNode selected;
                    for (TreePath path : rowsHandlers)
                    {
                        selected = (TreeNode) path.getLastPathComponent();
                        if (selected instanceof DefaultMutableTreeNode)
                            elementos.add((Puesto) ((DefaultMutableTreeNode) selected).getUserObject());
                    }
                    parametros.add(elementos);
                    parametros.add(rowsHandlers);
                    swSecondPlane.execute(parametros);
                }
            }
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
                jbMessage.setText("Obteniendo puestos...");
                swSecondPlane = new PuestoSwingWorker();
                swSecondPlane.addPropertyChangeListener(this);
                parametros.add(opLOAD);
                swSecondPlane.execute(parametros);
            }
        }
        else
            JOptionPane.showMessageDialog(this, "No cuenta con los permisos necesarios para acceder a la información");
    }

    //------------------------------------------------------------------
    @Override
    public void doSaveProcess()
    {
        int elementosTotales = 0;
        List<Puesto> modificados = new ArrayList<>();
        List<Puesto> agregados = new ArrayList<>();
        List<Puesto> datos = bsPuestos.getModifiedDatas();

        for (Puesto elemento : datos)
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
                swSecondPlane = new PuestoSwingWorker();
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

    //------------------------------------------------------------------
    @Override
    public void doOpenProcess()
    {
        if (can_update)
        {
            if (jtData.getSelectionCount() > 0)
            {
                TreeNode selected = (TreeNode) jtData.getSelectionPath().getLastPathComponent();
                if (selected instanceof DefaultMutableTreeNode)
                    showDetail((Puesto) ((DefaultMutableTreeNode) selected).getUserObject());
            }
        }
        else
            JOptionPane.showMessageDialog(this, "No cuenta con los permisos necesarios para ACTUALIZAR elementos");
    }

    //------------------------------------------------------------------
    @Override
    public void onAcceptModifyElement(Puesto elemento)
    {
        if (swSecondPlane == null || swSecondPlane.isDone())
        {
            List<Object> parametros = new ArrayList<Object>();
            swSecondPlane = new PuestoSwingWorker();
            swSecondPlane.addPropertyChangeListener(this);
            parametros.add(opUPDATE);
            parametros.add(elemento);
            jbMessage.setText("Guardando...");
            swSecondPlane.execute(parametros);
        }
    }

    //------------------------------------------------------------------
    @Override
    public void onAcceptNewElement(Puesto nuevo)
    {
        if (swSecondPlane == null || swSecondPlane.isDone())
        {
            DefaultMutableTreeNode parentNode = null;
            List<Object> parametros = new ArrayList<Object>();
            swSecondPlane = new PuestoSwingWorker();
            swSecondPlane.addPropertyChangeListener(this);

            if (jtData.getSelectionCount() > 0)
            {
                if (JOptionPane.showConfirmDialog(this, "¿Deseas agregar el nuevo elemento como hijo del elemento seleccionado?,\n en caso contrario será como una raíz", "Pregunta", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
                {
                    parentNode = (DefaultMutableTreeNode) jtData.getSelectionPath().getLastPathComponent();
                    Puesto parentElement = (Puesto) parentNode.getUserObject();
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

    //------------------------------------------------------------------
    @Override
    public void treeNodesChanged(TreeModelEvent e)
    {
        setChanges();
        jbMessage.setText("Cambios pendientes");
    }

    //---------------------------------------------------------------------
    private void resetElement(Puesto value)
    {
        //value.acceptChanges();
        if (jtData.getSelectionCount() > 0)
        {
            TreeNode selected = (TreeNode) jtData.getSelectionPath().getLastPathComponent();
            bsPuestos.nodeChanged(selected);
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
            bsPuestos.removeNodeFromParent((MutableTreeNode) selected);
        }
    }

    //------------------------------------------------------------------
    @Override
    public void treeNodesInserted(TreeModelEvent e)
    {
    }

    //------------------------------------------------------------------
    @Override
    public void treeNodesRemoved(TreeModelEvent e)
    {
    }

    //------------------------------------------------------------------
    @Override
    public void treeStructureChanged(TreeModelEvent e)
    {
    }

    //------------------------------------------------------------------
    private void setDisplayData(List<Puesto> values)
    {
        bsPuestos.setData(values);

        jtData.expandPath(new TreePath(((DefaultMutableTreeNode) bsPuestos.getRoot()).getPath()));
        
         printRecordCount();
        jbMessage.setText("Puestos obtenidos");
    }

    //---------------------------------------------------------------------
    @Override
    public List<Puesto> obtenerPuestos()
    {
        return puestos.obtenerLista();
    }

    //---------------------------------------------------------------------
    @Override
    public String obtenerOrigenDato()
    {
        return mainContainer.getOrigenDatoActual().getNombre();
    }

    //---------------------------------------------------------------------
    private void setDisplayData(Puesto value, DefaultMutableTreeNode parentNode)
    {
        if (parentNode != null)
        {
            bsPuestos.addNodeTo(new DefaultMutableTreeNode(value), parentNode);
        }

        else
        {
            bsPuestos.addNodeToRoot(new DefaultMutableTreeNode(value));
        }
        printRecordCount();
        jbMessage.setText("Nuevo agregado");
        
    }

    //---------------------------------------------------------------------
    //---------------------------------------------------------------------
    private class PuestoSwingWorker extends SwingWorker<List<Object>, Void>
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
                List<Object> results = new ArrayList<>();
                results.add(opcion);
                switch (opcion)
                {
                    case opLOAD:
                        results.add(puestos.obtenerLista());
                        break;
                    case opINSERT:
                        results.add(puestos.agregar((Puesto) arguments.get(1)));
                        break;
                    case opUPDATE:
                        results.add(puestos.actualizar((Puesto) arguments.get(1)));
                        break;
                    case opDELETE_LIST:
                        results.add(puestos.borrar((List<Puesto>) arguments.get(1)));
                        break;
                    case opSAVE:
                        List<Puesto> adds = (List<Puesto>) arguments.get(1);
                        List<Puesto> modifieds = (List<Puesto>) arguments.get(2);
                        if (adds.size() > 0)
                            results.add(puestos.agregar(adds));
                        if (modifieds.size() > 0)
                            results.add(puestos.actualizar(modifieds));
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
                                List<Puesto> listaUsuarios = (List<Puesto>) results.get(1);
                                if (listaUsuarios.size() > 0)
                                    setDisplayData(listaUsuarios);
                                else if (!puestos.esCorrecto())
                                    JOptionPane.showMessageDialog(null, puestos.getMensaje());
                                break;
                            case opINSERT:
                                if (puestos.esCorrecto())
                                    setDisplayData((Puesto) arguments.get(1), (DefaultMutableTreeNode) arguments.get(2));
                                break;
                            case opUPDATE:
                                if (puestos.esCorrecto())
                                    resetElement((Puesto) arguments.get(1));
                                break;
                            case opDELETE_LIST:
                                if (puestos.esCorrecto())
                                {
                                    removeNodes((TreePath[]) arguments.get(2));
                                     printRecordCount();
                                
                                    jbMessage.setText("Elemento(s) eliminado(s)");
                                }
                                else
                                    jbMessage.setText(puestos.getEsquema());
                                break;
                            case opSAVE:
                                jbMessage.setText(puestos.getMensaje());
                                if (puestos.esCorrecto())
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
