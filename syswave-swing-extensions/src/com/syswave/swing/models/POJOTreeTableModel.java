package com.syswave.swing.models;

import com.syswave.forms.common.INotifyTableProperties;
import com.syswave.forms.common.INotifyTreeProperties;
import com.syswave.forms.common.INotifyTreePropertyChanged;
import com.syswave.swing.table.events.TableModelCellFormatEvent;
import com.syswave.swing.table.events.TableModelGetValueEvent;
import com.syswave.swing.table.events.TableModelSetValueEvent;
import de.hameister.treetable.AbstractMutableTreeTableModel;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.event.TableModelEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public abstract class POJOTreeTableModel<E> extends AbstractMutableTreeTableModel implements Serializable, INotifyTableProperties<E>, INotifyTreeProperties<E>, INotifyTreePropertyChanged<E>
{

    final private String NOT_BINDING = "";
    private List<E> data;
    protected Vector dataVector;
    protected List<String> columnsNames;
    private List<Integer> BindingColumns;
    private List<Integer> DataColumns;
    private List<String> BindingNames;
    private TableModelGetValueEvent<E> getEvent;
    private TableModelSetValueEvent<E> setEvent;
    private TableModelCellFormatEvent formatEvent;
    private Pattern pattern;
    private boolean read_only;
    //private List<E> selectedDatas;
    private List<DefaultMutableTreeNode> checkedNodes;
    private List<E> modifiedDatas;
    private boolean canRecursiveAddToModified, useForCheckeBoxes;

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
        columnsNames = new ArrayList<String>();

        pattern = Pattern.compile(":\\{(.*?)\\}");
        getEvent = new TableModelGetValueEvent<E>();
        setEvent = new TableModelSetValueEvent<E>();
        formatEvent = new TableModelCellFormatEvent();
        BindingColumns = new ArrayList<Integer>();
        DataColumns = new ArrayList<Integer>();
        BindingNames = new ArrayList<String>();
        dataVector = new Vector();
        read_only = false;
    }

    //----------------------------------------------------------------------
    /**
     * Inicializa el modelo especificando las columnas en formtato
     * HeaderText:{Binding} o HeaderText
     *
     * @param columnHeaders
     */
    public POJOTreeTableModel(String[] columnHeaders)
    {
        super(new DefaultMutableTreeNode("root"), false);
        initAtributes();
        data = new ArrayList<>();
        columnsNames = new ArrayList<>(Arrays.asList(columnHeaders));

        buildBindings(columnsNames);
    }

    //----------------------------------------------------------------------
    /**
     * Construye una instancia del modelo indicando el origen de datos.
     *
     * @param columnHeaders
     * @param dataSource Origen de datos.
     */
    public POJOTreeTableModel(String[] columnHeaders, List<E> dataSource)
    {
        super(new DefaultMutableTreeNode("root"), false);
        initAtributes();
        setData(dataSource);
        columnsNames = new ArrayList<>(Arrays.asList(columnHeaders));

        buildBindings(columnsNames);

        //dataVector.setSize(data.size());
        justifyRows(0, getRowCount());
    }

    //---------------------------------------------------------------------
    /**
     * Construye un modelo de árbol con la raíz especificada.
     *
     * @param columnHeaders
     * @param root
     */
    public POJOTreeTableModel(String[] columnHeaders, E root)
    {
        super(new DefaultMutableTreeNode(root));
        initAtributes();

        columnsNames = new ArrayList<>(Arrays.asList(columnHeaders));

        buildBindings(columnsNames);
    }

    //---------------------------------------------------------------------
    /**
     * Construye un modelo de árbol con la raíz especificada.
     *
     * @param columnHeaders
     * @param root
     * @param dataSource
     */
    public POJOTreeTableModel(String[] columnHeaders, E root, List<E> dataSource)
    {
        super(new DefaultMutableTreeNode(root));
        initAtributes();
        setData(dataSource);

        columnsNames = new ArrayList<>(Arrays.asList(columnHeaders));

        buildBindings(columnsNames);

        //dataVector.setSize(data.size());
        justifyRows(0, getRowCount());
    }

    //---------------------------------------------------------------------
    /**
     * Establece la lista de elementos a partir de la cual se dibujara el árbol.
     *
     * @param dataSource
     */
    public final void setData(List<E> dataSource)
    {
        int old_rowcount = source != null && source.getRowCount() > 0 ? source.getRowCount() : 0;

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

        if (!source.isExpanded(0))
            source.expandPath(new TreePath(((DefaultMutableTreeNode) getRoot()).getPath()));

        int new_rowcount = source != null && source.getRowCount() > 0 ? source.getRowCount() : 0;
        int difference = new_rowcount - old_rowcount;

        justifyRows(0, getRowCount());
        if (!data.isEmpty())
        {
            //The new data is less than previous
            if (difference < 0)
            {
                //fireTableRowsDeleted(new_rowcount, old_rowcount - 1);
                if (new_rowcount > 0)
                    fireTableRowsUpdated(0, new_rowcount - 1);
            }

            //The new data is greather than previous
            else if (difference > 0)
            {
                fireTableRowsInserted(old_rowcount, new_rowcount - 1);
                if (old_rowcount > 0)
                    fireTableRowsUpdated(0, old_rowcount - 1);
            }

            //The new data is equal than previous
            else
                fireTableRowsUpdated(0, new_rowcount - 1);

            //fireTableDataChanged(); //Indica que el renglón y columna 0, .1 han cambiado.
            //fireTableStructureChanged(); //Indica el renglon y columna -1, -1 han cambiado.
            //
        }
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
    @Override
    public int getColumnCount()
    {
        return columnsNames.size();
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
    public void addRow(E value)
    {
        addNodeToRoot(new DefaultMutableTreeNode(value));
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
        MutableTreeNode selected;
        for (TreePath node : nodes)
        {
            selected = (MutableTreeNode) node.getLastPathComponent();
            removeNodeFromParent(selected);
        }
    }

    //--------------------------------------------------------------
    public E getElementAt(int index)
    {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) nodeMutableForRow(index);

        return (E) node.getUserObject();
    }

    //----------------------------------------------------------------------
    private void buildBindings(List<String> formatNames)
    {
        String result;
        BindingColumns.clear();
        BindingNames.clear();
        DataColumns.clear();
        for (int i = 0; i < formatNames.size(); i++)
        {
            result = getBinding(formatNames.get(i));

            if (result != NOT_BINDING)
            {
                BindingColumns.add(i);
                BindingNames.add(result);
            }

            else
                DataColumns.add(i);
        }
    }

    //---------------------------------------------------------------------
    private void justifyRows(int from, int to)
    {
        int notBindingColumnsSize = getColumnCount() - BindingColumns.size();

        if (notBindingColumnsSize > 0)
        {
            dataVector.setSize(to);
            for (int i = from; i < to; i++)
            {
                if (dataVector.elementAt(i) == null)
                    dataVector.setElementAt(new Vector(), i);

                ((Vector) dataVector.elementAt(i)).setSize(notBindingColumnsSize);
            }
        }
    }

    //---------------------------------------------------------------------
    /**
     * Agrega un nuevo renglón al modelo de datos.
     *
     * @param value El valor a agregar
     * @return
     */
    /* public int addRow(E value)
     {
     int index;

     if (data == null)
     data = new ArrayList<E>();

     data.add(value);
     index = data.size() - 1;

     //Not Binding columns
     justifyRows(index, index + 1);

     fireTableRowsInserted(index, index);

     return index;
     }*/
    //---------------------------------------------------------------------
    /**
     * Agrega varios renglónes nuevos al modelo de datos.
     *
     * @param values La lsita de valores a agregar.
     * @return
     */
    /* public boolean addRows(List<E> values)
     {
     boolean success;

     int firts;

     if (data == null)
     data = new ArrayList<E>();

     firts = data.size() - 1;

     success = data.addAll(values);

     //Not Binding columns
     justifyRows(firts, getRowCount());

     fireTableRowsInserted(firts, data.size() - 1);

     return success;
     }*/
    //---------------------------------------------------------------------
    /**
     * Agrega un núevo renglón al modelo de datos.
     *
     * @return Referencia al nuevo renglón.
     */
    /*public E addNewRow() 
     {
     E current = null;
     try
     {
         
     current = (E) getClass().newInstance();
     data.add(current);
     }
     catch (InstantiationException ex)
     {
     Logger.getLogger(POJOTableModel.class.getName()).log(Level.SEVERE, null, ex);
     }
     catch (IllegalAccessException ex)
     {
     Logger.getLogger(POJOTableModel.class.getName()).log(Level.SEVERE, null, ex);
     }
     finally
     {
     fireTableRowsInserted(data.size() -1, data.size() - 1);
     }
      
     return current;
     }*/
    //---------------------------------------------------------------------
    /**
     * Agrega un nuevo elemento al modelo de datos en la posición especificada.
     *
     * @param row Renglón en donde se agregará.
     * @param value El valor que se agregara
     */
    /*public void insertRow(int row, E value)
     {
     data.add(row, value);

     //Not Binding columns
     justifyRows(row, row + 1);

     fireTableRowsInserted(row, row);
     }*/
    //---------------------------------------------------------------------
    /**
     * Agrega un nuevo elemento al modelo de datos en la posición especificada.
     *
     * @param row Renglón en donde se agregará.
     * @param values Colección de valores a agregar
     */
    /*public void insertRows(int row, List<E> values)
     {
     data.addAll(row, values);

     //Not Binding columns
     for (int i = 0; i < values.size(); i++)
     {
     justifyRows(row, row + 1);
     }

     fireTableRowsInserted(row, row + values.size() - 1);
     }*/
    //---------------------------------------------------------------------
    /**
     * Remueve el elemento en el renglón especificado.
     *
     * @param row Indice del renglón a remover.
     * @return una referencia al elemento removido.
     */
    /*public E removeRow(int row)
     {
     E element = data.remove(row);

     dataVector.remove(row);

     fireTableRowsDeleted(row, row);
     return element;
     }

     //---------------------------------------------------------------------
     /**
     * Remueve los elementos especificados por la colección de indices.
     *
     * @param rowsIndex Colección de los indices a remover
     * @return una colección de referencias hacia los elementos removidos.
     */
    /*public List<E> removeRows(int[] rowsIndex)
     {
     int min, max;
     List<E> selecteds = new ArrayList<E>();
     List<Object> dataSelecteds = new ArrayList<>();
     int notBindingColumnsSize = getColumnCount() - BindingColumns.size();

     //1.- Inicializamos las variables del mínimo y máximo índice.
     if (rowsIndex.length > 0)
     {
     min = rowsIndex[0];
     max = rowsIndex[0];
     }
     else
     {
     min = 0;
     max = 0;
     }

     //2.- Recorremos los elementos seleccionados.
     for (int i = 0; i < rowsIndex.length; i++)
     {
     if (min > rowsIndex[i])
     min = rowsIndex[i];

     else if (max < rowsIndex[i])
     max = rowsIndex[i];

     selecteds.add(data.get(rowsIndex[i]));
     if (notBindingColumnsSize > 0)
     dataSelecteds.add(dataVector.get(rowsIndex[i]));
     }

     //3.- Removemos todos los elementos seleccionados.
     data.removeAll(selecteds);
     if (!dataSelecteds.isEmpty())
     dataVector.removeAll(dataSelecteds);
     fireTableRowsDeleted(min, max);

     return selecteds;
     }*/
    //---------------------------------------------------------------------
    /**
     * Moves one or more rows from the inclusive range <code>start</code> to
     * <code>end</code> to the <code>to</code> position in the model. After the
     * move, the row that was at index <code>start</code> will be at index
     * <code>to</code>. This method will send a <code>tableChanged</code>
     * notification message to all the listeners.
     *
     * <pre>
     *  Examples of moves:
     *
     *  1. moveRow(1,3,5);
     *          a|B|C|D|e|f|g|h|i|j|k   - before
     *          a|e|f|g|h|B|C|D|i|j|k   - after
     *
     *  2. moveRow(6,7,1);
     *          a|b|c|d|e|f|G|H|i|j|k   - before
     *          a|G|H|b|c|d|e|f|i|j|k   - after
     * </pre>
     *
     * @param start the starting row index to be moved
     * @param end the ending row index to be moved
     * @param to the destination of the rows to be moved
     * @exception ArrayIndexOutOfBoundsException if any of the elements would be
     * moved out of the table's range
     *
     */
    /*public void moveRow(int start, int end, int to) {
     int shift = to - start;
     int first, last;
     if (shift < 0) {
     first = to;
     last = end;
     }
     else {
     first = start;
     last = to + end - start;
     }
     rotate(dataVector, first, last + 1, shift);

     fireTableRowsUpdated(first, last);
     }*/
    //---------------------------------------------------------------------
    /**
     * Limpia toda la información desplegada.
     */
    public void clear()
    {
        int min = 0, max = data.size() - 1;
        data.clear();
        dataVector.clear();

        if (max >= 0)
            fireTableRowsDeleted(min, max);
    }

   //---------------------------------------------------------------------
   /*public void removeRows (List<Usuario> selecteds )
     {
     data.removeAll(selecteds);
     }*/
    //---------------------------------------------------------------------
    /**
     * Obtiene el elemento en el renglón especificado.
     *
     * @param row Numero de renglón.
     * @return Referencia al elemento
     */
    /*public E getElementAt(int row)
     {
     return data.get(row);
     }*/
    //---------------------------------------------------------------------
    /**
     * Obtiene los elementos especificados en la colección de indices.
     *
     * @param rowsIndex Colección con los indices de los elementos a obtener.
     * @return Referencias a los elementos
     */
    /*public List<E> getElementsAt(int[] rowsIndex)
     {
     List<E> selecteds = new ArrayList<E>();

     for (int i = 0; i < rowsIndex.length; i++)
     {
     selecteds.add(data.get(rowsIndex[i]));
     }

     return selecteds;
     }*/
    //---------------------------------------------------------------------
    /**
     * Obtiene el indice de un elemento dado
     *
     * @param value The elemento a conocer su indice.
     * @return El indice del elemento.
     */
    public int indexOf(E value)
    {
        return data.indexOf(value);
    }

    //---------------------------------------------------------------------
    public String[] getColumnsNames()
    {
        return columnsNames.toArray(new String[columnsNames.size()]);
    }

    //---------------------------------------------------------------------
    public void setColumnsNames(String[] columnsHeaders)
    {
        if (columnsNames.size() > 0)
            columnsNames.clear();

        columnsNames = new ArrayList<String>(Arrays.asList(columnsHeaders));

        buildBindings(columnsNames);
        justifyRows(0, getRowCount());
        fireTableStructureChanged();
    }

    //---------------------------------------------------------------------
    public void setColumnCount(int columnCount)
    {
        int dif;

        if (columnCount > columnsNames.size())
        {
            dif = columnCount - columnsNames.size();
            for (int i = columnsNames.size(); i < dif; i++)
            {
                columnsNames.add(super.getColumnName(i));
            }
        }

        else
        {
            dif = columnsNames.size() - columnCount;
            for (int j = 0; j < dif; j++)
            {
                columnsNames.remove(columnsNames.size() - 1);
            }
        }

        justifyRows(0, getRowCount());
        fireTableStructureChanged();
    }

    //----------------------------------------------------------------------
    private String getHeaderText(String formatValue)
    {
        String[] parts = formatValue.split(":\\{");

        return parts[0];
    }

    //----------------------------------------------------------------------
    private String getBinding(String formatValue)
    {
        String result;

        Matcher matcher = pattern.matcher(formatValue);
        if (matcher.find())
        {
            result = matcher.group();
            result = result.substring(2, result.length() - 1);
        }

        else
            result = NOT_BINDING;

        return result;
    }

    //---------------------------------------------------------------------
    /**
     * Agrega una columna nueva al modelo.
     *
     * @param formatValue HeaderText:{Binding} o HeaderText
     */
    public void addColumnName(String formatValue)
    {
        columnsNames.add(formatValue);

        int newIndex = columnsNames.size() - 1;
        String result = getBinding(columnsNames.get(newIndex));

        if (result != NOT_BINDING)
        {
            BindingColumns.add(newIndex);
            BindingNames.add(result);
        }

        else
            DataColumns.add(newIndex);

        justifyRows(0, getRowCount());
        fireTableStructureChanged();
    }

    //---------------------------------------------------------------------
    /**
     * Returns the column name.
     *
     * @return a name for this column using the string value of the appropriate
     * member in <code>columnIdentifiers</code>. If
     * <code>columnIdentifiers</code> does not have an entry for this index,
     * returns the default name provided by the superclass.
     */
    @Override
    public String getColumnName(int column)
    {
        Object id = null;
        // This test is to cover the case when
        // getColumnCount has been subclassed by mistake ...
        if (column < columnsNames.size() && (column >= 0))
        {
            id = getHeaderText(columnsNames.get(column));
        }

        return id == null ? super.getColumnName(column) : (String) id;
    }

    //---------------------------------------------------------------------
    /**
     * Returns the column index.
     *
     * @return a index for this column using the string value of the appropriate
     * member in <code>columnIdentifiers</code>. If
     * <code>columnIdentifiers</code> does not have an entry for this index,
     * returns the default name provided by the superclass.
     */
    @Override
    public int findColumn(String columnName)
    {
        int i = 0, colIndex = -1;
        boolean find = false;
        while (!find && i < columnsNames.size())
        {
            find = columnName.equals(getHeaderText(columnsNames.get(i)));
            if (find)
                colIndex = i;
            else
                i++;
        }
        return colIndex;
    }

    //--------------------------------------------------------------------
    /**
     * Sets the object value for the cell at <code>column</code> and
     * <code>row</code>.  <code>aValue</code> is the new value. This method will
     * generate a <code>tableChanged</code> notification.
     *
     * @param aValue the new value; this can be null
     * @param rowIndex the row whose value is to be changed
     * @param columnIndex the column whose value is to be changed
     * @exception ArrayIndexOutOfBoundsException if an invalid row or column was
     * given
     */
    @Override
    final public void setValueAt(Object aValue, int rowIndex, int columnIndex)
    {
        int bindingIndex = BindingColumns.indexOf(columnIndex);
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) nodeMutableForRow(rowIndex);

        if (bindingIndex >= 0)
        {
            setEvent.set(aValue, (E) node.getUserObject(), rowIndex, columnIndex, BindingNames.get(bindingIndex));
            onSetValueAt(setEvent);
        }

        else
        {
            Vector rowVector = (Vector) dataVector.elementAt(rowIndex);
            rowVector.setElementAt(aValue, DataColumns.indexOf(columnIndex));
        }

        fireTableCellUpdated(rowIndex, columnIndex);
    }

    //---------------------------------------------------------------------
    /**
     * Returns an attribute value for the cell at <code>row</code> and
     * <code>column</code>.
     *
     * @param rowIndex the row whose value is to be queried
     * @param columnIndex the column whose value is to be queried
     * @return the value Object at the specified cell
     * @exception ArrayIndexOutOfBoundsException if an invalid row or column was
     * given
     */
    @Override
    final public Object getValueAt(int rowIndex, int columnIndex)
    {
        Object value = null;
        int bindingIndex = BindingColumns.indexOf(columnIndex);
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) nodeMutableForRow(rowIndex);

        if (bindingIndex >= 0 && !(node.getUserObject() instanceof String))
        {
            getEvent.set((E) node.getUserObject(), rowIndex, columnIndex, BindingNames.get(bindingIndex));
            value = onGetValueAt(getEvent);
        }

        else
        {
            if (dataVector.size() > 0)
            {
                Vector rowVector = (Vector) dataVector.elementAt(rowIndex);
                value = rowVector.elementAt(DataColumns.indexOf(columnIndex));
                formatEvent.set(columnIndex, NOT_BINDING);
            }

            else
                formatEvent.set(columnIndex, BindingNames.get(bindingIndex));

            if (value == null)
            {
                Class current = onGetColumnClass(formatEvent);

                if (current == Boolean.class)
                    value = false;

                else if (current == Integer.class)
                    value = 0;

                else if (current == Float.class)
                    value = 0.0F;

                else if (current == Double.class)
                    value = 0.0D;

                else if (current == Date.class)
                    value = Calendar.getInstance().getTime();

                else
                    value = "";
            }

        }

        if (value == null)
            throw new IllegalArgumentException("Null value not permited on cell [" + rowIndex + ", " + columnIndex + "]");

        return value;
    }

    //---------------------------------------------------------------------
   /*
     * Bajo ninguna circunstancia invoque este método desde onGetColumnClass
     */
    @Override
    final public Class<?> getColumnClass(int columnIndex)
    {
        int bindingIndex = BindingColumns.indexOf(columnIndex);

        if (bindingIndex >= 0)
            formatEvent.set(columnIndex, BindingNames.get(bindingIndex));

        else
            formatEvent.set(columnIndex, NOT_BINDING);

        return onGetColumnClass(formatEvent);
    }

    //---------------------------------------------------------------------
    public final Class<?> getDefaultColumnClass()
    {
        return super.getColumnClass(0);
    }

    //---------------------------------------------------------------------
    /**
     * Regresa verdadero solamente si el modelo no es para lectura
     *
     * @param row the row whose value is to be queried
     * @param column the column whose value is to be queried
     * @return true
     * @see #setValueAt
     */
    @Override
    public boolean isCellEditable(int row, int column)
    {
        return !read_only;
    }

    //---------------------------------------------------------------------
    /**
     * Indica si el modelo es solamente para lectura.
     *
     * @return un valor que indica si es de solo lectura
     */
    public final boolean isReadOnly()
    {
        return read_only;
    }

    //---------------------------------------------------------------------
    /**
     * Establece si el modelo es solamente para lectura
     *
     * @param value un valor para indicar el comportamiento
     */
    public final void setReadOnly(boolean value)
    {
        this.read_only = value;
    }

    //--------------------------------------------------------------------
    /**
     * Indica a los componentes asociados a este modelo que ha ocurrido un
     * cambio en el elemento.
     *
     * @param value elemento.
     */
    public void resetElement(E value)
    {
        fireTableCellUpdated(data.indexOf(value), TableModelEvent.ALL_COLUMNS);
    }

}
