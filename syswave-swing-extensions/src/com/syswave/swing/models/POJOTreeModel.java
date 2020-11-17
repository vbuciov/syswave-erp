package com.syswave.swing.models;

import com.syswave.forms.common.INotifyTreeProperties;
import com.syswave.forms.common.INotifyTreePropertyChanged;
import java.util.ArrayList;
import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

/**
 * Representa un origen de datos para una lista generica de POJO's
 *
 * @author Victor Manuel Bucio Vargas
 * @param <E> La entidad que se requiere utilizar.
 */
public abstract class POJOTreeModel<E> extends DefaultTreeModel implements INotifyTreeProperties<E>, INotifyTreePropertyChanged<E>
{

    List<E> data;
    //List<E> selectedDatas;
    List<DefaultMutableTreeNode> checkedNodes;
    List<E> modifiedDatas;
    boolean canRecursiveAddToModified, useForCheckeBoxes;

    //---------------------------------------------------------------------
    /**
     * Inicializa los atributos internos del árbol.
     */
    private void initAtributes()
    {
        canRecursiveAddToModified = true;
        useForCheckeBoxes = true;
        //data = null;
        data = new ArrayList<E>();
        //selectedDatas = new ArrayList<E>();
        checkedNodes = new ArrayList<DefaultMutableTreeNode>();
        modifiedDatas = new ArrayList<E>();
    }

    //---------------------------------------------------------------------
    public POJOTreeModel()
    {
        super(new DefaultMutableTreeNode("root"), false);
        initAtributes();
    }

    //---------------------------------------------------------------------
    public POJOTreeModel(List<E> dataSource)
    {
        super(new DefaultMutableTreeNode("root"), false);
        initAtributes();
        setData(dataSource);
    }

    //---------------------------------------------------------------------
    /**
     * Construye un modelo de árbol con la raíz especificada.
     *
     * @param root
     */
    public POJOTreeModel(E root)
    {
        super(new DefaultMutableTreeNode(root));
        initAtributes();
    }

    //---------------------------------------------------------------------
    /**
     * Construye un modelo de árbol con la raíz especificada.
     *
     * @param root
     */
    public POJOTreeModel(E root, List<E> dataSource)
    {
        super(new DefaultMutableTreeNode(root));
        initAtributes();
        setData(dataSource);
    }

    //---------------------------------------------------------------------
    /**
     * Establece la lista de elementos a partir de la cual se dibujara el árbol.
     *
     * @param dataSource
     */
    public final void setData(List<E> dataSource)
    {
        //List<E> bean = new ArrayList<E>(dataSource);

        if (data != null && data.size() > 0)
            data.clear();

      //data = dataSource;
        //Si previamente se tiene una raíz, ya construida.
        if (root.getChildCount() > 0)
        {
            ((DefaultMutableTreeNode) root).removeAllChildren();
            setRoot(new DefaultMutableTreeNode(((DefaultMutableTreeNode) root).getUserObject()));

         //Nota: Es necesario volver a crear la raíz debido a que por algún motivo al llamar
            //al método removeAllChildren es marcado para ser eliminado y así se hace cuando
            //se repitan la interfaz.
        }

        /*if (selectedDatas.size() > 0)
         selectedDatas.clear();*/
        if (checkedNodes.size() > 0)
            checkedNodes.clear();

        // buildTree(bean);
        buildTree(dataSource);

    }

    //---------------------------------------------------------------------
    /**
     * Obteiene la lista de elementos a partir de la cual se construyo el árbol.
     *
     * @return
     */
    public List<E> getData()
    {
        return data;
    }

    //---------------------------------------------------------------------
    /**
     * Devuelve la cantidad de elementos existentes en el árbol.
     *
     * @return entero
     */
    public int size()
    {
        return data != null ? data.size() : 0;
    }

    //---------------------------------------------------------------------
    /**
     * Establece un valor que Indica si al ocurrir un cambio en un elemento,
     * también los descendientes propagan ese cambio.
     *
     * @param value
     */
    public void setCanRecursiveAddToModified(boolean value)
    {
        canRecursiveAddToModified = value;
    }

    //---------------------------------------------------------------------
    /**
     * Obtiene un valor que Indica si al ocurrir un cambio en un elemento,
     * también los descendientes propagan ese cambio.
     *
     * @return
     */
    public boolean isCanRecursiveAddToModified()
    {
        return canRecursiveAddToModified;
    }

    //---------------------------------------------------------------------
    /**
     * Obtiene un valor que indica si se estan considerando mantener información
     * de selección.
     *
     * @return
     */
    public boolean isUseForCheckeBoxes()
    {
        return useForCheckeBoxes;
    }

    //---------------------------------------------------------------------
    /**
     * Establece un valor que indica si se debe considerar mantener inforamción
     * de selección.
     *
     * @param value
     */
    public void setUseForCheckeBoxes(boolean value)
    {
        this.useForCheckeBoxes = value;
    }

    //----------------------------------------------------------------
    /**
     * Construye el árbol basado en una semilla.
     */
    private void buildTree(List<E> semilla)
    {
        DefaultMutableTreeNode nodo;
        E elemento;
        //long start = System.currentTimeMillis();
        int[] indices = busquedaBinariaPlus(semilla, getNullParent(), 0, 0); //Padre, Nivel, Inicio
        while (indices[0] <= indices[1])
        {
            elemento = semilla.get(indices[0]);
            nodo = new DefaultMutableTreeNode(elemento);
            data.add(semilla.remove(indices[0]));

            if (semilla.size() > 0)
                fillChilds(semilla, nodo, indices[1], 1);

            ((DefaultMutableTreeNode) root).add(nodo);

            indices[1]--;

            //Vamos generando una lista con todos los elementos seleccionados.
            if (useForCheckeBoxes && onGetChecked((E) nodo.getUserObject()))
            {
                checkedNodes.add(nodo);
                //selectedDatas.add(elemento);
            }
        }

        if (semilla.size() > 0)
            System.out.println(String.format("%d elementos no puedieron ser construidos, asegurese de que esta ordenada la semilla", semilla.size()));
       // Nota: Para calcular el tiempo que se tarda en construir el árbol.
      /*long end = System.currentTimeMillis();
         long res = end - start;
         System.out.println("Segundos: "+(res/1000.0) % 60 + " con semilla resultante " + semilla.size() + " y datos en " + data.size());*/
    }

    //----------------------------------------------------------------
    /**
     * Llena el nodo según encuentre a sus respectivos hijos.
     */
    private void fillChilds(List<E> semilla, DefaultMutableTreeNode elementoPadre, int inicio, int nivel)
    {
        DefaultMutableTreeNode nodo;
        int[] indices;
        E elemento;

        //Nota: Verificamo si el máximo valor coincide con el primer elemento al menos.
        if (!semilla.isEmpty() && elementoPadre != null)
        {
            //1.- Obtenemos el rango en el que se encuentran los elementos sin padre
            indices = busquedaBinariaPlus(semilla, (int) onGetIDValue((E) elementoPadre.getUserObject()), nivel, inicio); //Padre, Nivel, Inicio

            //2.- Recorremos los elementos especificados por el rango [i, j]
            while (indices[0] <= indices[1])
            {
                elemento = semilla.get(indices[0]);
                nodo = new DefaultMutableTreeNode(elemento);
                data.add(semilla.remove(indices[0]));

                elementoPadre.add(nodo);

                if (semilla.size() > 0)
                    fillChilds(semilla, nodo, indices[1], nivel + 1);

                indices[1]--;

                //Vamos generando una lista con todos los elementos seleccionados.
                if (useForCheckeBoxes)
                {
                    if (onGetChecked((E) nodo.getUserObject()))
                    {
                        checkedNodes.add(nodo);
                        //selectedDatas.add(elemento);
                    }

                    if (nodo.getParent() != null)
                        CheckAllAncestor(nodo, onGetChecked((E) nodo.getUserObject()), false);
                }
            }

        }
    }

    //----------------------------------------------------------------
    /**
     * Realiza la busqueda de todos los elementos que presentan el mismo valor.
     */
    private int[] busquedaBinariaPlus(List<E> semilla, Object id_padre_buscado, int nivel_buscado, int inicio)
    {
        int[] Resultado = new int[2];
        int mitad, nivelActual;
        Object idPadreActual;
        boolean forzarSalida = false;

        Resultado[0] = inicio;
        Resultado[1] = semilla.size() - 1;
        mitad = (Resultado[0] + Resultado[1]) / 2;

        while (!forzarSalida && Resultado[0] <= Resultado[1])
        {
            idPadreActual = onGetIDParentValue(semilla.get(mitad));
            nivelActual = onGetLevel(semilla.get(mitad));

            if (idPadreActual.equals(id_padre_buscado))
            {
                Resultado[0] = mitad - contar(false, semilla, mitad, id_padre_buscado); //CONTAMOS A LA IZQUIERDA DEL ELEMENTO
                Resultado[1] = mitad + contar(true, semilla, mitad, id_padre_buscado); //CONTAMOS A LA DERECHA DEL ELEMENTO
                forzarSalida = true;
            }

            else if (nivelActual > nivel_buscado || (nivelActual == nivel_buscado && greaterThan(idPadreActual, id_padre_buscado)))
            {
                Resultado[1] = mitad - 1;
                mitad = (Resultado[0] + Resultado[1]) / 2;
            }

            else if (nivelActual < nivel_buscado || lessThan(idPadreActual, id_padre_buscado))
            {
                Resultado[0] = mitad + 1;
                mitad = (Resultado[0] + Resultado[1]) / 2;
            }

            else
            {
                forzarSalida = true;
                Resultado[0] = 0;
                Resultado[1] = -1;
            }
        }

        return Resultado;
    }

    //---------------------------------------------------------------------
    /**
     * Determina si el valor es mayor que
     *
     * @param current_node The Node to compare
     * @param value The value to compare
     * @return Indicate the
     */
    @Override
    public boolean greaterThan(Object current_node, Object value)
    {
        if (current_node instanceof String)
            return ((String) current_node).compareTo((String) value) > 0;
        else if (current_node instanceof Integer)
            return (Integer) current_node > (Integer) value;
        else
            return false;
    }

    //---------------------------------------------------------------------
    /**
     * Determina si el valor es menor que
     *
     * @param current_node The Node to compare
     * @param value The value to compare
     * @return Indicate the
     */
    @Override
    public boolean lessThan(Object current_node, Object value)
    {
        if (current_node instanceof String)
            return ((String) current_node).compareTo((String) value) < 0;
        else if (current_node instanceof Integer)
            return (Integer) current_node < (Integer) value;
        else
            return false;
    }

    //----------------------------------------------------------------
    /**
     * Cuenta todos los elementos a la izquierda o derecha del elemento con el
     * mismo valor
     */
    private int contar(boolean Lado, List<E> semilla, int mitad, Object id_padre)
    {
        int i, contador = 0;
        boolean continuar = true;

        if (Lado)
            i = mitad + 1;
        else
            i = mitad - 1;

        while (continuar && i >= 0 && i < semilla.size())
        {
            continuar = onGetIDParentValue(semilla.get(i)).equals(id_padre); //Esta línea cambia según el tipo

            if (continuar)
            {
                contador++;

                if (Lado)
                    i += 1;

                else
                    i -= 1;
            }
        }

        return contador;
    }

    //--------------------------------------------------------------
    /**
     * Updates all child tree nodes recursively.
     */
    private void CheckAllChildNodes(DefaultMutableTreeNode withChilds, boolean nodeChecked)
    {
        DefaultMutableTreeNode currentChild;
        boolean hasData;
        for (int i = 0; i < withChilds.getChildCount(); i++)
        {
            currentChild = (DefaultMutableTreeNode) withChilds.getChildAt(i);
            hasData = currentChild.getUserObject() != null && !(currentChild.getUserObject() instanceof String);

            //Vamos generando una lista con todos los elementos seleccionados.
            if (!nodeChecked)
            {
                /* if (hasData && onGetChecked((E) currentChild.getUserObject()))
                 selectedDatas.remove((E)currentChild.getUserObject());*/

                checkedNodes.remove(currentChild);
            }

            else
            {
                /*if (hasData &&  !onGetChecked((E) currentChild.getUserObject()))
                 selectedDatas.add((E)currentChild.getUserObject());*/

                checkedNodes.add(currentChild);
            }

            //Marcamos el elemento como modificado.
            if (hasData)
            {
                onSetChecked((E) currentChild.getUserObject(), nodeChecked);

                if (canRecursiveAddToModified && canAddToModified((E) currentChild.getUserObject()))
                {
                    modifiedDatas.add((E) currentChild.getUserObject());
                    onModifiedAdded((E) currentChild.getUserObject());
                }
            }

            if (currentChild.getChildCount() > 0)
                this.CheckAllChildNodes(currentChild, nodeChecked);
        }
    }

    //--------------------------------------------------------------
    /**
     * Se encarga de determinar si los ancestros del nodo deberían estar
     * marcados como seleccionados.
     */
    private void CheckAllAncestor(DefaultMutableTreeNode node, boolean nodeChecked, boolean BuildSelected)
    {
        DefaultMutableTreeNode child, parent = (DefaultMutableTreeNode) node.getParent();
        boolean hasData = parent.getUserObject() != null && !(parent.getUserObject() instanceof String);
        boolean realChecked;

        if (!nodeChecked)
        {
            boolean isAllChiledSelected = false;

            //Buscamos el primer nodo del padre que este seleccionado.
            for (int i = 0; i < parent.getChildCount() && !isAllChiledSelected; i++)
            {
                child = (DefaultMutableTreeNode) parent.getChildAt(i);
                isAllChiledSelected = onGetChecked((E) child.getUserObject());
            }

            //Vamos generando una lista con todos los elementos seleccionados.
            if (BuildSelected)
            {
                if (!isAllChiledSelected)
                {
                    /*if (hasData && onGetChecked((E) parent.getUserObject()))
                     selectedDatas.remove((E)parent.getUserObject());*/
                    checkedNodes.remove(parent);
                }

                else
                {
                    /*if (hasData && !onGetChecked((E) parent.getUserObject()) )
                     selectedDatas.add((E)parent.getUserObject());*/
                    checkedNodes.add(parent);
                }
            }

            //Si al menos un hijo del padre sigue seleccionado el también debe estarlo.
            realChecked = isAllChiledSelected;
        }

        else
        {
            //Vamos generando una lista con todos los elementos seleccionados.
            if (BuildSelected)
            {
                if (!onGetChecked((E) parent.getUserObject()) && nodeChecked)
                {
                    checkedNodes.add(parent);
                    /*if (hasData)
                     selectedDatas.add((E)parent.getUserObject());*/
                }
            }

            realChecked = nodeChecked;
        }

        //Marcamos el elemento como modificado.
        if (hasData)
        {
            onSetChecked((E) parent.getUserObject(), realChecked);

            if (BuildSelected && canRecursiveAddToModified && canAddToModified((E) parent.getUserObject()))
            {
                modifiedDatas.add((E) parent.getUserObject());
                onModifiedAdded((E) parent.getUserObject());
            }
        }

        //Calculamos si el padre del padre actual, tiene datos.
        hasData = parent.getParent() != null && !(((DefaultMutableTreeNode) parent.getParent()).getUserObject() instanceof String);

        if (hasData && onGetChecked((E) parent.getUserObject()) != onGetChecked((E) ((DefaultMutableTreeNode) parent.getParent()).getUserObject()))
            CheckAllAncestor(parent, onGetChecked((E) parent.getUserObject()), BuildSelected);
    }

    //--------------------------------------------------------------
    /**
     * Obtiene los nodos con selección. (Air mehtod)
     *
     * @return
     */
    public List<DefaultMutableTreeNode> getCheckedNodes()
    {
        return checkedNodes;
    }

    //--------------------------------------------------------------
    /**
     * Obtiene los datos de cada nodo con selección. (Air mehtod)
     *
     * @return
     */
    /*public List<E> getSelectedDatas()
     {
     return selectedDatas;
     }*/
    //--------------------------------------------------------------
    /**
     * Obtiene todos los objetos de usuarios que se consideran modificados.
     *
     * @return
     */
    public List<E> getModifiedDatas()
    {
        return modifiedDatas;
    }

    //----------------------------------------------------------------
    /**
     * Este evento se dispara por parte del TreeCellEditor, cuando a terminado
     * de efectuar los cambios.
     *
     * @param path Es la ruta al nodo que ha recibido el cambio.
     * @param newValue Es el valor obtenido por parte del editor.
     */
    @Override
    public void valueForPathChanged(TreePath path, Object newValue)
    {
        Object selectedComponent = path.getLastPathComponent();

        if (selectedComponent != null && selectedComponent instanceof DefaultMutableTreeNode)
        {
            DefaultMutableTreeNode editedNode = (DefaultMutableTreeNode) selectedComponent;
            boolean hasData = editedNode.getUserObject() != null && !(editedNode.getUserObject() instanceof String);

            if (newValue instanceof Boolean)
            {
                boolean checked = (boolean) newValue;

                //El nuevo valor le esta quitando la seleccion.
                if (!checked)
                {
                    /* if (hasData && onGetChecked((E) editedNode.getUserObject()) )
                     selectedDatas.remove((E)editedNode.getUserObject());*/

                    checkedNodes.remove(editedNode);
                }

                //El nuevo valor le esta otorgando la seleccion.
                else
                {
                    /*if (hasData && !onGetChecked((E) editedNode.getUserObject()))
                     selectedDatas.add((E)editedNode.getUserObject());*/

                    checkedNodes.add(editedNode);
                }

                //Marcamos el elemento como modificado.
                if (hasData)
                {
                    onSetChecked((E) editedNode.getUserObject(), checked);

                    if (canAddToModified((E) editedNode.getUserObject()))
                    {
                        modifiedDatas.add((E) editedNode.getUserObject());
                        onModifiedAdded((E) editedNode.getUserObject());
                    }
                }

                //Si el elemento tienes hijos, todos sus descendientes reflejan el cambio.
                if (useForCheckeBoxes)
                {
                    if (!editedNode.isLeaf())
                        CheckAllChildNodes(editedNode, checked);

                    //Si el elemento tiene padre, sus ancestros reflejan el cambio.
                    if (editedNode.getParent() != null && !(((DefaultMutableTreeNode) editedNode.getParent()).getUserObject() instanceof String) && onGetChecked((E) ((DefaultMutableTreeNode) editedNode.getParent()).getUserObject()) != checked)
                        CheckAllAncestor(editedNode, checked, true);
                }
            }

            else if (newValue instanceof String)
            {
                if (hasData)
                {
                    onSetText((E) editedNode.getUserObject(), (String) newValue);

                    if (canAddToModified((E) editedNode.getUserObject()))
                    {
                        modifiedDatas.add((E) editedNode.getUserObject());
                        onModifiedAdded((E) editedNode.getUserObject());
                    }
                }
            }

            nodeChanged(editedNode);
        }
    }

    //--------------------------------------------------------------
    public void addNodeToRoot(DefaultMutableTreeNode newChild)
    {
        insertNodeInto(newChild, (MutableTreeNode) root, getChildCount(root));
    }

    //--------------------------------------------------------------
    public void addNodeTo(MutableTreeNode newChild, MutableTreeNode parent)
    {
        insertNodeInto(newChild, parent, getChildCount(parent));
    }

    //--------------------------------------------------------------
    @Override
    public void insertNodeInto(MutableTreeNode newChild,
                               MutableTreeNode parent, int index)
    {
        super.insertNodeInto(newChild, parent, index);

        if (newChild instanceof DefaultMutableTreeNode)
        {
            modifiedDatas.add((E) ((DefaultMutableTreeNode) newChild).getUserObject());
            data.add((E) ((DefaultMutableTreeNode) newChild).getUserObject());
        }

    }

    //--------------------------------------------------------------
    @Override
    public void removeNodeFromParent(MutableTreeNode node)
    {
        if (node instanceof DefaultMutableTreeNode)
        {
            modifiedDatas.remove((E) ((DefaultMutableTreeNode) node).getUserObject());
            data.remove((E) ((DefaultMutableTreeNode) node).getUserObject());
        }
        super.removeNodeFromParent(node);
    }

    //---------------------------------------------------------------------
    public void removeNodes(TreePath[] nodes)
    {
        TreeNode selected;
        for (TreePath node : nodes)
        {
            selected = (TreeNode) node.getLastPathComponent();
            removeNodeFromParent((MutableTreeNode) selected);
        }
    }

    //--------------------------------------------------------------
    /**
     * /* Obtiene los datos de cada nodo con selección. (Iterative method)
   *
     */
    /*public List<E> getSelectedDatas()
     {
     List<E> resultado = new ArrayList<E>();

     for (E item : data)
     {

     if (item.isSelected())
     resultado.add(item);
     }

     return resultado;
     }*/
    //------------------------------------------------------------------
    /**
     * /* Obtiene los datos de cada nodo con selección. (Recursive method)
     *
     * @return
     */
    /*public List<E> getSelectedDatas()
     {
     List<E> resultado = new ArrayList<E>();
      
     DefaultMutableTreeNode currentChild;
     E currentData;
     for (int i = 0; i < root.getChildCount(); i++)
     {
     currentChild = (DefaultMutableTreeNode)root.getChildAt(i); 
     currentData = currentChild.getData();
     if (currentData.isSelected())
     {
     //El elemento no es hoja, por tanto es necesario seguir al siguiente nivel.
     if (currentChild.getChildCount() > 0)
     {
     resultado.addAll(obtenerPermisosModificados(currentChild));
     }

     resultado.add(currentData);
     }
     }

     return resultado;
     }*/
    //--------------------------------------------------------------
    /**
     * /* Obtiene los datos de cada nodo con selección. (Recursive method)
     *
     * @return
     */
    /*private List<E> obtenerPermisosModificados(DefaultMutableTreeNode nodeParent)
     {
     List<E> resultado = new ArrayList<E>();
      
     DefaultMutableTreeNode currentChild;
     E currentData;
     for (int i = 0; i < nodeParent.getChildCount(); i++)
     {
     currentChild = (DefaultMutableTreeNode)nodeParent.getChildAt(i); 
     currentData = currentChild.getData();
     if (currentData.isSelected())
     {
     //El elemento no es hoja, por tanto es necesario seguir al siguiente nivel.
     if (currentChild.getChildCount() > 0)
     {
     resultado.addAll(obtenerPermisosModificados(currentChild));
     }

     resultado.add(currentData);
     }
     }

     return resultado;
     }*/
}
