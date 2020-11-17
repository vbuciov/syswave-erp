package de.hameister.treetable;

import java.util.EventListener;
import javax.swing.JTree;
import javax.swing.event.EventListenerList;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.table.TableModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public abstract class AbstractTreeTableModel implements TreeModel, TableModel
{

    protected TreeNode root;
    protected JTree source;
    protected boolean asksAllowsChildren;
    protected EventListenerList listenerList = new EventListenerList();

    //--------------------------------------------------------------------
    /**
     * Creates a tree in which any node can have children.
     *
     * @param root a TreeNode object that is the root of the tree
     * @see #DefaultTreeModel(TreeNode, boolean)
     */
    public AbstractTreeTableModel(TreeNode root)
    {
        this(root, false);
    }

    //--------------------------------------------------------------------
    /**
     * Creates a tree specifying whether any node can have children, or whether
     * only certain nodes can have children.
     *
     * @param root a TreeNode object that is the root of the tree
     * @param asksAllowsChildren a boolean, false if any node can have children,
     * true if each node is asked to see if it can have children
     * @see #asksAllowsChildren
     */
    public AbstractTreeTableModel(TreeNode root, boolean asksAllowsChildren)
    {
        this.root = root;
        this.asksAllowsChildren = asksAllowsChildren;
    }

    //--------------------------------------------------------------------
    /**
     * Sets whether or not to test leafness by asking getAllowsChildren() or
     * isLeaf() to the TreeNodes. If newvalue is true, getAllowsChildren() is
     * messaged, otherwise isLeaf() is messaged.
     *
     * @param newValue
     */
    public void setAsksAllowsChildren(boolean newValue)
    {
        asksAllowsChildren = newValue;
    }

    //--------------------------------------------------------------------
    /**
     * Tells how leaf nodes are determined.
     *
     * @return true if only nodes which do not allow children are leaf nodes,
     * false if nodes which have no children (even if allowed) are leaf nodes
     * @see #asksAllowsChildren
     */
    public boolean asksAllowsChildren()
    {
        return asksAllowsChildren;
    }

    //--------------------------------------------------------------------
    /**
     * Returns the root of the tree. Returns null only if the tree has no nodes.
     *
     * @return the root of the tree
     */
    @Override
    public Object getRoot()
    {
        return root;
    }

    //--------------------------------------------------------------------
    /**
     * Sets the root to <code>root</code>. A null <code>root</code> implies the
     * tree is to display nothing, and is legal.
     *
     * @param root
     */
    public void setRoot(TreeNode root)
    {
        Object oldRoot = this.root;
        this.root = root;
        if (root == null && oldRoot != null)
            fireTreeStructureChanged(this, null);

        else
            nodeStructureChanged(root);
    }

    //--------------------------------------------------------------------
    /**
     * Returns the index of child in parent. If either the parent or child is
     * <code>null</code>, returns -1.
     *
     * @param parent a note in the tree, obtained from this data source
     * @param child the node we are interested in
     * @return the index of the child in the parent, or -1 if either the parent
     * or the child is <code>null</code>
     */
    @Override
    public int getIndexOfChild(Object parent, Object child)
    {
        if (parent == null || child == null)
            return -1;
        return ((TreeNode) parent).getIndex((TreeNode) child);
    }

    //--------------------------------------------------------------------
    /**
     * Returns the child of <I>parent</I> at index <I>index</I> in the parent's
     * child array.  <I>parent</I> must be a node previously obtained from this
     * data source. This should not return null if <i>index</i>
     * is a valid index for <i>parent</i> (that is <i>index</i> &gt;= 0
     * &amp;&amp;
     * <i>index</i> &lt; getChildCount(<i>parent</i>)).
     *
     * @param parent a node in the tree, obtained from this data source
     * @param index
     * @return the child of <I>parent</I> at index <I>index</I>
     */
    @Override
    public Object getChild(Object parent, int index)
    {
        return ((TreeNode) parent).getChildAt(index);
    }

    //--------------------------------------------------------------------
    /**
     * Returns the number of children of <I>parent</I>. Returns 0 if the node is
     * a leaf or if it has no children.  <I>parent</I> must be a node previously
     * obtained from this data source.
     *
     * @param parent a node in the tree, obtained from this data source
     * @return the number of children of the node <I>parent</I>
     */
    @Override
    public int getChildCount(Object parent)
    {
        return ((TreeNode) parent).getChildCount();
    }

    //--------------------------------------------------------------------
    /**
     * Returns whether the specified node is a leaf node. The way the test is
     * performed depends on the <code>askAllowsChildren</code> setting.
     *
     * @param node the node to check
     * @return true if the node is a leaf node
     *
     * @see #asksAllowsChildren
     * @see TreeModel#isLeaf
     */
    @Override
    public boolean isLeaf(Object node)
    {
        if (asksAllowsChildren)
            return !((TreeNode) node).getAllowsChildren();
        return ((TreeNode) node).isLeaf();
    }

    //--------------------------------------------------------------------
    /**
     * Invoke this method if you've modified the {@code TreeNode}s upon which
     * this model depends. The model will notify all of its listeners that the
     * model has changed.
     */
    public void reload()
    {
        reload(root);
    }

    //--------------------------------------------------------------------
    /**
     * Invoke this method after you've changed how node is to be represented in
     * the tree.
     *
     * @param node
     */
    public void nodeChanged(TreeNode node)
    {
        if (listenerList != null && node != null)
        {
            TreeNode parent = node.getParent();

            if (parent != null)
            {
                int anIndex = parent.getIndex(node);
                if (anIndex != -1)
                {
                    int[] cIndexs = new int[1];

                    cIndexs[0] = anIndex;
                    nodesChanged(parent, cIndexs);
                }
            }
            else if (node == getRoot())
            {
                nodesChanged(node, null);
            }
        }
    }

    //--------------------------------------------------------------------
    /**
     * Invoke this method if you've modified the {@code TreeNode}s upon which
     * this model depends. The model will notify all of its listeners that the
     * model has changed below the given node.
     *
     * @param node the node below which the model has changed
     */
    public void reload(TreeNode node)
    {
        if (node != null)
        {
            fireTreeStructureChanged(this, getPathToRoot(node), null, null);
        }
    }

    //--------------------------------------------------------------------
    /**
     * Builds the parents of node up to and including the root node, where the
     * original node is the last element in the returned array. The length of
     * the returned array gives the node's depth in the tree.
     *
     * @param aNode the TreeNode to get the path for
     * @return
     */
    public TreeNode[] getPathToRoot(TreeNode aNode)
    {
        return getPathToRoot(aNode, 0);
    }

    //--------------------------------------------------------------------
    /**
     * Builds the parents of node up to and including the root node, where the
     * original node is the last element in the returned array. The length of
     * the returned array gives the node's depth in the tree.
     *
     * @param aNode the TreeNode to get the path for
     * @param depth an int giving the number of steps already taken towards the
     * root (on recursive calls), used to size the returned array
     * @return an array of TreeNodes giving the path from the root to the
     * specified node
     */
    protected TreeNode[] getPathToRoot(TreeNode aNode, int depth)
    {
        TreeNode[] retNodes;
        // This method recurses, traversing towards the root in order
        // size the array. On the way back, it fills in the nodes,
        // starting from the root and working back to the original node.

        /* Check for null, in case someone passed in a null node, or
         they passed in an element that isn't rooted at root. */
        if (aNode == null)
        {
            if (depth == 0)
                return null;
            else
                retNodes = new TreeNode[depth];
        }
        else
        {
            depth++;
            if (aNode == root)
                retNodes = new TreeNode[depth];
            else
                retNodes = getPathToRoot(aNode.getParent(), depth);
            retNodes[retNodes.length - depth] = aNode;
        }
        return retNodes;
    }

    //--------------------------------------------------------------------
    /**
     * Returns a default name for the column using spreadsheet conventions: A,
     * B, C, ... Z, AA, AB, etc. If <code>column</code> cannot be found, returns
     * an empty string.
     *
     * @param column the column being queried
     * @return a string containing the default name of <code>column</code>
     */
    @Override
    public String getColumnName(int column)
    {
        String result = "";
        for (; column >= 0; column = column / 26 - 1)
        {
            result = (char) ((char) (column % 26) + 'A') + result;
        }
        return result;
    }

    //--------------------------------------------------------------------
    /**
     * Returns a column given its name. Implementation is naive so this should
     * be overridden if this method is to be called often. This method is not in
     * the <code>TableModel</code> interface and is not used by the
     * <code>JTable</code>.
     *
     * @param columnName string containing name of column to be located
     * @return the column with <code>columnName</code>, or -1 if not found
     */
    public int findColumn(String columnName)
    {
        for (int i = 0; i < getColumnCount(); i++)
        {
            if (columnName.equals(getColumnName(i)))
            {
                return i;
            }
        }
        return -1;
    }

    //--------------------------------------------------------------------
    /**
     * Returns <code>Object.class</code> regardless of <code>columnIndex</code>.
     *
     * @param columnIndex the column being queried
     * @return the Object.class
     */
    @Override
    public Class<?> getColumnClass(int columnIndex)
    {
        return Object.class;
    }

    //--------------------------------------------------------------------
    /**
     * Returns false. This is the default implementation for all cells.
     *
     * @param rowIndex the row being queried
     * @param columnIndex the column being queried
     * @return false
     */
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
        return false;
    }

    /**
     * This empty implementation is provided so users don't have to implement
     * this method if their data model is not editable.
     *
     * @param aValue value to assign to cell
     * @param rowIndex row of cell
     * @param columnIndex column of cell
     */
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex)
    {
    }

    /**
     * This empty implementation is provided so users don't have to implement
     * this method if their data model is not editable.
     */
    @Override
    public void valueForPathChanged(TreePath path, Object newValue)
    {
    }

    //--------------------------------------------------------------------
    //  Events
    //--------------------------------------------------------------------  
    /**
     * Adds a listener for the TreeModelEvent posted after the tree changes.
     *
     * @see #removeTreeModelListener
     * @param l the listener to add
     */
    @Override
    public void addTreeModelListener(TreeModelListener l)
    {
        listenerList.add(TreeModelListener.class, l);
    }

    //--------------------------------------------------------------------
    /**
     * Removes a listener previously added with <B>addTreeModelListener()</B>.
     *
     * @see #addTreeModelListener
     * @param l the listener to remove
     */
    @Override
    public void removeTreeModelListener(TreeModelListener l)
    {
        listenerList.remove(TreeModelListener.class, l);
    }

    //--------------------------------------------------------------------
    /**
     * Returns an array of all the tree model listeners registered on this
     * model.
     *
     * @return all of this model's <code>TreeModelListener</code>s or an empty
     * array if no tree model listeners are currently registered
     *
     * @see #addTreeModelListener
     * @see #removeTreeModelListener
     *
     * @since 1.4
     */
    public TreeModelListener[] getTreeModelListeners()
    {
        return listenerList.getListeners(TreeModelListener.class);
    }

    //--------------------------------------------------------------------
    /**
     * Adds a listener to the list that's notified each time a change to the
     * data model occurs.
     *
     * @param l the TableModelListener
     */
    @Override
    public void addTableModelListener(TableModelListener l)
    {
        listenerList.add(TableModelListener.class, l);
    }

    /**
     * Removes a listener from the list that's notified each time a change to
     * the data model occurs.
     *
     * @param l the TableModelListener
     */
    @Override
    public void removeTableModelListener(TableModelListener l)
    {
        listenerList.remove(TableModelListener.class, l);
    }

    /**
     * Returns an array of all the table model listeners registered on this
     * model.
     *
     * @return all of this model's <code>TableModelListener</code>s or an empty
     * array if no table model listeners are currently registered
     *
     * @see #addTableModelListener
     * @see #removeTableModelListener
     *
     * @since 1.4
     */
    public TableModelListener[] getTableModelListeners()
    {
        return listenerList.getListeners(TableModelListener.class);
    }

    //--------------------------------------------------------------------
    /**
     * Notifies all listeners that have registered interest for notification on
     * this event type. The event instance is lazily created using the
     * parameters passed into the fire method.
     *
     * @param source the source of the {@code TreeModelEvent}; typically
     * {@code this}
     * @param path the path to the parent of the nodes that changed; use
     * {@code null} to identify the root has changed
     * @param childIndices the indices of the changed elements
     * @param children the changed elements
     */
    protected void fireTreeNodesChanged(Object source, Object[] path,
                                        int[] childIndices,
                                        Object[] children)
    {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        TreeModelEvent e = null;
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == TreeModelListener.class)
            {
                // Lazily create the event:
                if (e == null)
                    e = new TreeModelEvent(source, path,
                                           childIndices, children);
                ((TreeModelListener) listeners[i + 1]).treeNodesChanged(e);
            }
        }
    }

    //--------------------------------------------------------------------
    /**
     * Notifies all listeners that have registered interest for notification on
     * this event type. The event instance is lazily created using the
     * parameters passed into the fire method.
     *
     * @param source the source of the {@code TreeModelEvent}; typically
     * {@code this}
     * @param path the path to the parent the nodes were added to
     * @param childIndices the indices of the new elements
     * @param children the new elements
     */
    protected void fireTreeNodesInserted(Object source, Object[] path,
                                         int[] childIndices,
                                         Object[] children)
    {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        TreeModelEvent e = null;
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == TreeModelListener.class)
            {
                // Lazily create the event:
                if (e == null)
                    e = new TreeModelEvent(source, path,
                                           childIndices, children);
                ((TreeModelListener) listeners[i + 1]).treeNodesInserted(e);
            }
        }
    }

    //--------------------------------------------------------------------
    /**
     * Notifies all listeners that have registered interest for notification on
     * this event type. The event instance is lazily created using the
     * parameters passed into the fire method.
     *
     * @param source the source of the {@code TreeModelEvent}; typically
     * {@code this}
     * @param path the path to the parent the nodes were removed from
     * @param childIndices the indices of the removed elements
     * @param children the removed elements
     */
    protected void fireTreeNodesRemoved(Object source, Object[] path,
                                        int[] childIndices,
                                        Object[] children)
    {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        TreeModelEvent e = null;
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == TreeModelListener.class)
            {
                // Lazily create the event:
                if (e == null)
                    e = new TreeModelEvent(source, path,
                                           childIndices, children);
                ((TreeModelListener) listeners[i + 1]).treeNodesRemoved(e);
            }
        }
    }

    //--------------------------------------------------------------------
    /**
     * Notifies all listeners that have registered interest for notification on
     * this event type. The event instance is lazily created using the
     * parameters passed into the fire method.
     *
     * @param source the source of the {@code TreeModelEvent}; typically
     * {@code this}
     * @param path the path to the parent of the structure that has changed; use
     * {@code null} to identify the root has changed
     * @param childIndices the indices of the affected elements
     * @param children the affected elements
     */
    protected void fireTreeStructureChanged(Object source, Object[] path,
                                            int[] childIndices,
                                            Object[] children)
    {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        TreeModelEvent e = null;
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == TreeModelListener.class)
            {
                // Lazily create the event:
                if (e == null)
                    e = new TreeModelEvent(source, path,
                                           childIndices, children);
                ((TreeModelListener) listeners[i + 1]).treeStructureChanged(e);
            }
        }
    }

    //--------------------------------------------------------------------
    /**
     * Notifies all listeners that have registered interest for notification on
     * this event type. The event instance is lazily created using the
     * parameters passed into the fire method.
     *
     * @param source the source of the {@code TreeModelEvent}; typically
     * {@code this}
     * @param path the path to the parent of the structure that has changed; use
     * {@code null} to identify the root has changed
     */
    private void fireTreeStructureChanged(Object source, TreePath path)
    {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        TreeModelEvent e = null;
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == TreeModelListener.class)
            {
                // Lazily create the event:
                if (e == null)
                    e = new TreeModelEvent(source, path);
                ((TreeModelListener) listeners[i + 1]).treeStructureChanged(e);
            }
        }
    }

    //--------------------------------------------------------------------
    /**
     * Invoke this method after you've inserted some TreeNodes into node.
     * childIndices should be the index of the new elements and must be sorted
     * in ascending order.
     *
     * @param node
     * @param childIndices
     */
    public void nodesWereInserted(TreeNode node, int[] childIndices)
    {
        if (listenerList != null && node != null && childIndices != null
                && childIndices.length > 0)
        {
            int cCount = childIndices.length;
            Object[] newChildren = new Object[cCount];

            for (int counter = 0; counter < cCount; counter++)
                newChildren[counter] = node.getChildAt(childIndices[counter]);
            fireTreeNodesInserted(this, getPathToRoot(node), childIndices,
                                  newChildren);
        }
    }

    //--------------------------------------------------------------------
    /**
     * Invoke this method after you've removed some TreeNodes from node.
     * childIndices should be the index of the removed elements and must be
     * sorted in ascending order. And removedChildren should be the array of the
     * children objects that were removed.
     *
     * @param node
     * @param childIndices
     * @param removedChildren
     */
    public void nodesWereRemoved(TreeNode node, int[] childIndices,
                                 Object[] removedChildren)
    {
        if (node != null && childIndices != null)
        {
            fireTreeNodesRemoved(this, getPathToRoot(node), childIndices,
                                 removedChildren);
        }
    }

    //--------------------------------------------------------------------
    /**
     * Invoke this method after you've changed how the children identified by
     * childIndicies are to be represented in the tree.
     *
     * @param node
     * @param childIndices
     */
    public void nodesChanged(TreeNode node, int[] childIndices)
    {
        if (node != null)
        {
            if (childIndices != null)
            {
                int cCount = childIndices.length;

                if (cCount > 0)
                {
                    Object[] cChildren = new Object[cCount];

                    for (int counter = 0; counter < cCount; counter++)
                        cChildren[counter] = node.getChildAt(childIndices[counter]);
                    fireTreeNodesChanged(this, getPathToRoot(node),
                                         childIndices, cChildren);
                }
            }
            else if (node == getRoot())
            {
                fireTreeNodesChanged(this, getPathToRoot(node), null, null);
            }
        }
    }

    //--------------------------------------------------------------------
    /**
     * Invoke this method if you've totally changed the children of node and its
     * children's children... This will post a treeStructureChanged event.
     *
     * @param node
     */
    public void nodeStructureChanged(TreeNode node)
    {
        if (node != null)
        {
            fireTreeStructureChanged(this, getPathToRoot(node), null, null);
        }
    }

    //--------------------------------------------------------------------
    /**
     * Notifies all listeners that all cell values in the table's rows may have
     * changed. The number of rows may also have changed and the
     * <code>JTable</code> should redraw the table from scratch. The structure
     * of the table (as in the order of the columns) is assumed to be the same.
     *
     * @see TableModelEvent
     * @see EventListenerList
     * @see javax.swing.JTable#tableChanged(TableModelEvent)
     */
    public void fireTableDataChanged()
    {
        fireTableChanged(new TableModelEvent(this));
    }

    //--------------------------------------------------------------------
    /**
     * Notifies all listeners that the table's structure has changed. The number
     * of columns in the table, and the names and types of the new columns may
     * be different from the previous state. If the <code>JTable</code> receives
     * this event and its <code>autoCreateColumnsFromModel</code> flag is set it
     * discards any table columns that it had and reallocates default columns in
     * the order they appear in the model. This is the same as calling
     * <code>setModel(TableModel)</code> on the <code>JTable</code>.
     *
     * @see TableModelEvent
     * @see EventListenerList
     */
    public void fireTableStructureChanged()
    {
        fireTableChanged(new TableModelEvent(this, TableModelEvent.HEADER_ROW));
    }

    //--------------------------------------------------------------------
    /**
     * Notifies all listeners that rows in the range
     * <code>[firstRow, lastRow]</code>, inclusive, have been inserted.
     *
     * @param firstRow the first row
     * @param lastRow the last row
     *
     * @see TableModelEvent
     * @see EventListenerList
     *
     */
    public void fireTableRowsInserted(int firstRow, int lastRow)
    {
        fireTableChanged(new TableModelEvent(this, firstRow, lastRow,
                                             TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
    }

    //--------------------------------------------------------------------
    /**
     * Notifies all listeners that rows in the range
     * <code>[firstRow, lastRow]</code>, inclusive, have been updated.
     *
     * @param firstRow the first row
     * @param lastRow the last row
     *
     * @see TableModelEvent
     * @see EventListenerList
     */
    public void fireTableRowsUpdated(int firstRow, int lastRow)
    {
        fireTableChanged(new TableModelEvent(this, firstRow, lastRow,
                                             TableModelEvent.ALL_COLUMNS, TableModelEvent.UPDATE));
    }

    //--------------------------------------------------------------------
    /**
     * Notifies all listeners that rows in the range
     * <code>[firstRow, lastRow]</code>, inclusive, have been deleted.
     *
     * @param firstRow the first row
     * @param lastRow the last row
     *
     * @see TableModelEvent
     * @see EventListenerList
     */
    public void fireTableRowsDeleted(int firstRow, int lastRow)
    {
        fireTableChanged(new TableModelEvent(this, firstRow, lastRow,
                                             TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE));
    }

    //--------------------------------------------------------------------
    /**
     * Notifies all listeners that the value of the cell at
     * <code>[row, column]</code> has been updated.
     *
     * @param row row of cell which has been updated
     * @param column column of cell which has been updated
     * @see TableModelEvent
     * @see EventListenerList
     */
    public void fireTableCellUpdated(int row, int column)
    {
        fireTableChanged(new TableModelEvent(this, row, row, column));
    }

    //--------------------------------------------------------------------
    /**
     * Forwards the given notification event to all
     * <code>TableModelListeners</code> that registered themselves as listeners
     * for this table model.
     *
     * @param e the event to be forwarded
     *
     * @see #addTableModelListener
     * @see TableModelEvent
     * @see EventListenerList
     */
    public void fireTableChanged(TableModelEvent e)
    {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == TableModelListener.class)
            {
                ((TableModelListener) listeners[i + 1]).tableChanged(e);
            }
        }
    }

    //--------------------------------------------------------------------
    /**
     * Returns an array of all the objects currently registered as
     * <code><em>Foo</em>Listener</code>s upon this model.
     * <code><em>Foo</em>Listener</code>s are registered using the
     * <code>add<em>Foo</em>Listener</code> method.
     *
     * <p>
     *
     * You can specify the <code>listenerType</code> argument with a class
     * literal, such as <code><em>Foo</em>Listener.class</code>. For example,
     * you can query a <code>DefaultTreeModel</code> <code>m</code> for its tree
     * model listeners with the following code:
     *
     * <pre>TreeModelListener[] tmls = (TreeModelListener[])(m.getListeners(TreeModelListener.class));</pre>
     *
     * If no such listeners exist, this method returns an empty array.
     *
     * @param <T>
     * @param listenerType the type of listeners requested; this parameter
     * should specify an interface that descends from
     * <code>java.util.EventListener</code>
     * @return an array of all objects registered as
     * <code><em>Foo</em>Listener</code>s on this component, or an empty array
     * if no such listeners have been added
     * @exception ClassCastException if <code>listenerType</code> doesn't
     * specify a class or interface that implements
     * <code>java.util.EventListener</code>
     *
     * @see #getTreeModelListeners
     *
     * @since 1.3
     */
    public <T extends EventListener> T[] getListeners(Class<T> listenerType)
    {
        return listenerList.getListeners(listenerType);
    }

    //--------------------------------------------------------------------
    public JTree getSource()
    {
        return source;
    }

    //--------------------------------------------------------------------
    public void setSource(JTree value)
    {
        this.source = value;
    }

    //--------------------------------------------------------------------
    protected TreeNode nodeForRow(int row)
    {
        if (source != null)
        {
            TreePath treePath = source.getPathForRow(row);
            Object selectedComponent = treePath.getLastPathComponent();

            if (selectedComponent != null && selectedComponent instanceof TreeNode)
                return (TreeNode) selectedComponent;
        }

        return null;
    }

    //--------------------------------------------------------------------
    @Override
    public int getRowCount()
    {
        return source.getRowCount();
    }

    // Serialization support.
    /*private void writeObject(ObjectOutputStream s) throws IOException {
     Vector<Object> values = new Vector<>();

     s.defaultWriteObject();
     // Save the root, if its Serializable.
     if(root != null && root instanceof Serializable) {
     values.addElement("root");
     values.addElement(root);
     }
     s.writeObject(values);
     }

     private void readObject(ObjectInputStream s)
     throws IOException, ClassNotFoundException {
     s.defaultReadObject();

     Vector          values = (Vector)s.readObject();
     int             indexCounter = 0;
     int             maxCounter = values.size();

     if(indexCounter < maxCounter && values.elementAt(indexCounter).
     equals("root")) {
     root = (TreeNode)values.elementAt(++indexCounter);
     indexCounter++;
     }
     }*/
}
