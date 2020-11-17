package de.hameister.treetable;

import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

/**
 * Permite trabajar con Nodos Mutables.
 * @author Victor Manuel Bucio Vargas
 */
public abstract class AbstractMutableTreeTableModel extends AbstractTreeTableModel
{

     /**
      * Creates a tree in which any node can have children.
      *
      * @param root a TreeNode object that is the root of the tree
      * @see #DefaultTreeModel(TreeNode, boolean)
      */
    public AbstractMutableTreeTableModel(MutableTreeNode root)
    {
        super(root);
    }
    
      /**
      * Creates a tree specifying whether any node can have children,
      * or whether only certain nodes can have children.
      *
      * @param root a TreeNode object that is the root of the tree
      * @param asksAllowsChildren a boolean, false if any node can
      *        have children, true if each node is asked to see if
      *        it can have children
      * @see #asksAllowsChildren
      */
    public AbstractMutableTreeTableModel(MutableTreeNode root, boolean asksAllowsChildren) {
       super(root, asksAllowsChildren);
    }
    
    /**
      * This sets the user object of the TreeNode identified by path
      * and posts a node changed.  If you use custom user objects in
      * the TreeModel you're going to need to subclass this and
      * set the user object of the changed node to something meaningful.
     * @param path
     * @param newValue
      */
    @Override
    public void valueForPathChanged(TreePath path, Object newValue) {
        MutableTreeNode   aNode = (MutableTreeNode)path.getLastPathComponent();

        aNode.setUserObject(newValue);
        nodeChanged(aNode);
    }

    /**
     * Invoked this to insert newChild at location index in parents children.
     * This will then message nodesWereInserted to create the appropriate
     * event. This is the preferred way to add children as it will create
     * the appropriate event.
     * @param newChild
     * @param parent
     * @param index
     */
    public void insertNodeInto(MutableTreeNode newChild,
                               MutableTreeNode parent, int index){
        parent.insert(newChild, index);

        int[]           newIndexs = new int[1];

        newIndexs[0] = index;

        fireTableRowsInserted(index - 1, index- 1);
        
        nodesWereInserted(parent, newIndexs);
    }

    /**
     * Message this to remove node from its parent. This will message
     * nodesWereRemoved to create the appropriate event. This is the
     * preferred way to remove a node as it handles the event creation
     * for you.
     * @param node
     */
    public void removeNodeFromParent(MutableTreeNode node) {
        MutableTreeNode         parent = (MutableTreeNode)node.getParent();

        if(parent == null)
            throw new IllegalArgumentException("node does not have a parent.");

        int[]            childIndex = new int[1];
        Object[]         removedArray = new Object[1];

        childIndex[0] = parent.getIndex(node);
        parent.remove(childIndex[0]);
        removedArray[0] = node;
        nodesWereRemoved(parent, childIndex, removedArray);
        //fireTableRowsDeleted(childIndex, childIndex);
        fireTableDataChanged(); //Notify the event to Table too
    }
    
    //--------------------------------------------------------------------
    protected MutableTreeNode nodeMutableForRow(int row)
    {
        if (source != null)
        {
            TreePath treePath = source.getPathForRow(row);
            Object selectedComponent = treePath.getLastPathComponent();

            if (selectedComponent != null && selectedComponent instanceof MutableTreeNode)
                return ((MutableTreeNode) selectedComponent);
        }

        return null;
    }

}
