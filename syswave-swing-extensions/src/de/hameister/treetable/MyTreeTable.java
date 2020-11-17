package de.hameister.treetable;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class MyTreeTable extends JTable
{

    private JTree extension;
    private TreeDecoratorTableCellRenderer hackRender;
    private TreeDecoratorTableCellEditor hackEditor;
    private AbstractTreeTableModel treeTableModel;
    private TreeTableSelectionModel shareSelectionModel;

    //--------------------------------------------------------------------
    public MyTreeTable(AbstractTreeTableModel model)
    {
        setDefaultRenderer(TreeModel.class, hackRender);
        setDefaultEditor(TreeModel.class, hackEditor);
        setAutoCreateRowSorter(false);

        // JTree erstellen.
        // Kein Grid anzeigen.
        setShowGrid(false);

        // Keine Abstaende.
        setIntercellSpacing(new Dimension(0, 0));

        setModel(model);
    }

    //--------------------------------------------------------------------
    @Override
    protected TableModel createDefaultDataModel()
    {
        extension = new JTree((TreeNode) null)
        {
            /**
             * Tree muss die gleiche Hoehe haben wie Table.
             */
            @Override
            public void setBounds(int x, int y, int w, int h)
            {
                super.setBounds(x, 0, w, getTableRowHeight());
            }

            @Override
            public void paint(Graphics g)
            {
                g.translate(0, -hackRender.getVisibleRow() * getTableRowHeight());

                super.paint(g);
            }
        };

        //Note: Considere by logic order the createDefaultSelectionModel is 
        //execute first
        extension.setSelectionModel(shareSelectionModel);

        //When the tree is expandend or collapsed, is changed the information.
        extension.addTreeExpansionListener(new TreeExpansionListener()
        {
            @Override
            public void treeExpanded(TreeExpansionEvent event)
            {
                if (treeTableModel != null)
                    treeTableModel.fireTableDataChanged();
            }

            @Override
            public void treeCollapsed(TreeExpansionEvent event)
            {
                if (treeTableModel != null)
                    treeTableModel.fireTableDataChanged();
            }
        });

        hackRender = new TreeDecoratorTableCellRenderer(extension);
        hackEditor = new TreeDecoratorTableCellEditor(extension, this);

        return new DefaultTreeTableModel();
    }

    //--------------------------------------------------------------------
    /**
     * Hack the table constructor for share the list Selection Model, with tree.
     *
     * @return
     */
    @Override
    protected ListSelectionModel createDefaultSelectionModel()
    {
        shareSelectionModel = new TreeTableSelectionModel();
        return shareSelectionModel.getListSelectionModel();
    }

    //--------------------------------------------------------------------
    @Override
    public final void setModel(TableModel dataModel)
    {
        if (dataModel instanceof AbstractTreeTableModel)
        {
            super.setModel(dataModel); //To change body of generated methods, choose Tools | Templates.
            extension.setModel((TreeModel) dataModel);
            treeTableModel = (AbstractTreeTableModel) dataModel;
            treeTableModel.setSource(extension);
        }
        else
            throw new IllegalArgumentException("Cannot set a simple TableModel");
    }

    
    //--------------------------------------------------------------------
    //---   JTree Decorate Methods                              ----------
    //--------------------------------------------------------------------
    /**
     * Determines whether the tree is editable. Fires a property change event if
     * the new setting is different from the existing setting.
     * <p>
     * This is a bound property.
     *
     * @param flag a boolean value, true if the tree is editable
     * @beaninfo bound: true description: Whether the tree is editable.
     */
    public void setEditable(boolean flag)
    {
        extension.setEditable(flag);
    }

    /**
     * Returns true if the tree is editable.
     *
     * @return true if the tree is editable
     */
    public boolean isEditable()
    {
        return extension.isEditable();
    }

    /**
     * Returns true if the root node of the tree is displayed.
     *
     * @return true if the root node of the tree is displayed
     * @see #rootVisible
     */
    public boolean isRootVisible()
    {
        return extension.isRootVisible();
    }

    /**
     * Determines whether or not the root node from the <code>TreeModel</code>
     * is visible.
     * <p>
     * This is a bound property.
     *
     * @param rootVisible true if the root node of the tree is to be displayed
     * @see #rootVisible
     * @beaninfo bound: true description: Whether or not the root node from the
     * TreeModel is visible.
     */
    public void setRootVisible(boolean rootVisible)
    {
        extension.setRootVisible(rootVisible);
    }

    /**
     * Sets the value of the <code>showsRootHandles</code> property, which
     * specifies whether the node handles should be displayed. The default value
     * of this property depends on the constructor used to create the
     * <code>JTree</code>. Some look and feels might not support handles; they
     * will ignore this property.
     * <p>
     * This is a bound property.
     *
     * @param newValue <code>true</code> if root handles should be displayed;
     * otherwise, <code>false</code>
     * @see #showsRootHandles
     * @see #getShowsRootHandles
     * @beaninfo bound: true description: Whether the node handles are to be
     * displayed.
     */
    public void setShowsRootHandles(boolean newValue)
    {
        extension.setShowsRootHandles(newValue);
    }

    /**
     * Returns the value of the <code>showsRootHandles</code> property.
     *
     * @return the value of the <code>showsRootHandles</code> property
     * @see #showsRootHandles
     */
    public boolean getShowsRootHandles()
    {
        return extension.getShowsRootHandles();
    }

    /**
     * Sets the height of each cell, in pixels. If the specified value is less
     * than or equal to zero the current cell renderer is queried for each row's
     * height.
     * <p>
     * This is a bound property.
     *
     * @param rowHeight the height of each cell, in pixels
     * @beaninfo bound: true description: The height of each cell.
     */
    @Override
    public void setRowHeight(int rowHeight)
    {
        super.setRowHeight(rowHeight);
        extension.setRowHeight(rowHeight);
    }

    //--------------------------------------------------------------------
    private int getTableRowHeight()
    {
        return getRowHeight();
    }

    /**
     * Specifies whether the UI should use a large model. (Not all UIs will
     * implement this.) Fires a property change for the LARGE_MODEL_PROPERTY.
     * <p>
     * This is a bound property.
     *
     * @param newValue true to suggest a large model to the UI
     * @see #largeModel
     * @beaninfo bound: true description: Whether the UI should use a large
     * model.
     */
    public void setLargeModel(boolean newValue)
    {
        extension.setLargeModel(newValue);
    }

    /**
     * Returns true if the tree is configured for a large model.
     *
     * @return true if a large model is suggested
     * @see #largeModel
     */
    public boolean isLargeModel()
    {
        return extension.isLargeModel();
    }

    /**
     * Determines what happens when editing is interrupted by selecting another
     * node in the tree, a change in the tree's data, or by some other means.
     * Setting this property to <code>true</code> causes the changes to be
     * automatically saved when editing is interrupted.
     * <p>
     * Fires a property change for the INVOKES_STOP_CELL_EDITING_PROPERTY.
     *
     * @param newValue true means that <code>stopCellEditing</code> is invoked
     * when editing is interrupted, and data is saved; false means that
     * <code>cancelCellEditing</code> is invoked, and changes are lost
     * @beaninfo bound: true description: Determines what happens when editing
     * is interrupted, selecting another node in the tree, a change in the
     * tree's data, or some other means.
     */
    public void setInvokesStopCellEditing(boolean newValue)
    {
        extension.setInvokesStopCellEditing(newValue);
    }

    /**
     * Returns the indicator that tells what happens when editing is
     * interrupted.
     *
     * @return the indicator that tells what happens when editing is interrupted
     * @see #setInvokesStopCellEditing
     */
    public boolean getInvokesStopCellEditing()
    {
        return extension.getInvokesStopCellEditing();
    }

    /**
     * Sets the <code>scrollsOnExpand</code> property, which determines whether
     * the tree might scroll to show previously hidden children. If this
     * property is <code>true</code> (the default), when a node expands the tree
     * can use scrolling to make the maximum possible number of the node's
     * descendants visible. In some look and feels, trees might not need to
     * scroll when expanded; those look and feels will ignore this property.
     * <p>
     * This is a bound property.
     *
     * @param newValue <code>false</code> to disable scrolling on expansion;
     * <code>true</code> to enable it
     * @see #getScrollsOnExpand
     *
     * @beaninfo bound: true description: Indicates if a node descendant should
     * be scrolled when expanded.
     */
    public void setScrollsOnExpand(boolean newValue)
    {
        extension.setScrollsOnExpand(newValue);
    }

    /**
     * Returns the value of the <code>scrollsOnExpand</code> property.
     *
     * @return the value of the <code>scrollsOnExpand</code> property
     */
    public boolean getScrollsOnExpand()
    {
        return extension.getScrollsOnExpand();
    }

    /**
     * Sets the number of mouse clicks before a node will expand or close. The
     * default is two.
     * <p>
     * This is a bound property.
     *
     * @param clickCount
     * @since 1.3
     * @beaninfo bound: true description: Number of clicks before a node will
     * expand/collapse.
     */
    public void setToggleClickCount(int clickCount)
    {
        extension.setToggleClickCount(clickCount);
    }

    /**
     * Returns the number of mouse clicks needed to expand or close a node.
     *
     * @return number of mouse clicks before node is expanded
     * @since 1.3
     */
    public int getToggleClickCount()
    {
        return extension.getToggleClickCount();
    }
    
     /**
     * Configures the <code>expandsSelectedPaths</code> property. If
     * true, any time the selection is changed, either via the
     * <code>TreeSelectionModel</code>, or the cover methods provided by
     * <code>JTree</code>, the <code>TreePath</code>s parents will be
     * expanded to make them visible (visible meaning the parent path is
     * expanded, not necessarily in the visible rectangle of the
     * <code>JTree</code>). If false, when the selection
     * changes the nodes parent is not made visible (all its parents expanded).
     * This is useful if you wish to have your selection model maintain paths
     * that are not always visible (all parents expanded).
     * <p>
     * This is a bound property.
     *
     * @param newValue the new value for <code>expandsSelectedPaths</code>
     *
     * @since 1.3
     * @beaninfo
     *        bound: true
     *  description: Indicates whether changes to the selection should make
     *               the parent of the path visible.
     */
    public void setExpandsSelectedPaths(boolean newValue) {
        extension.setExpandsSelectedPaths(newValue);
    }
    
        /**
     * Returns the <code>expandsSelectedPaths</code> property.
     * @return true if selection changes result in the parent path being
     *         expanded
     * @since 1.3
     * @see #setExpandsSelectedPaths
     */
    public boolean getExpandsSelectedPaths() {
        return extension.getExpandsSelectedPaths();
    }
    
    /**
     * Turns on or off automatic drag handling. In order to enable automatic
     * drag handling, this property should be set to {@code true}, and the
     * tree's {@code TransferHandler} needs to be {@code non-null}.
     * The default value of the {@code dragEnabled} property is {@code false}.
     * <p>
     * The job of honoring this property, and recognizing a user drag gesture,
     * lies with the look and feel implementation, and in particular, the tree's
     * {@code TreeUI}. When automatic drag handling is enabled, most look and
     * feels (including those that subclass {@code BasicLookAndFeel}) begin a
     * drag and drop operation whenever the user presses the mouse button over
     * an item and then moves the mouse a few pixels. Setting this property to
     * {@code true} can therefore have a subtle effect on how selections behave.
     * <p>
     * If a look and feel is used that ignores this property, you can still
     * begin a drag and drop operation by calling {@code exportAsDrag} on the
     * tree's {@code TransferHandler}.
     *
     * @param b whether or not to enable automatic drag handling
     * @exception HeadlessException if
     *            <code>b</code> is <code>true</code> and
     *            <code>GraphicsEnvironment.isHeadless()</code>
     *            returns <code>true</code>
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see #getDragEnabled
     * @see #setTransferHandler
     * @see TransferHandler
     * @since 1.4
     *
     * @beaninfo
     *  description: determines whether automatic drag handling is enabled
     *        bound: false
     */
    @Override
    public void setDragEnabled(boolean b) {
       super.setDragEnabled(b);
       extension.setDragEnabled(b);
    }
    
    /**
     * Returns <code>isEditable</code>. This is invoked from the UI before
     * editing begins to insure that the given path can be edited. This
     * is provided as an entry point for subclassers to add filtered
     * editing without having to resort to creating a new editor.
     *
     * @param path
     * @return true if every parent node and the node itself is editable
     * @see #isEditable
     */
    public boolean isPathEditable(TreePath path) {
        return extension.isPathEditable(path);
    }
    
    /**
     * Called by the renderers to convert the specified value to
     * text. This implementation returns <code>value.toString</code>, ignoring
     * all other arguments. To control the conversion, subclass this
     * method and use any of the arguments you need.
     *
     * @param value the <code>Object</code> to convert to text
     * @param selected true if the node is selected
     * @param expanded true if the node is expanded
     * @param leaf  true if the node is a leaf node
     * @param row  an integer specifying the node's display row, where 0 is
     *             the first row in the display
     * @param hasFocus true if the node has the focus
     * @return the <code>String</code> representation of the node's value
     */
   public String convertValueToText(Object value, boolean selected,
                                     boolean expanded, boolean leaf, int row,
                                     boolean hasFocus) {
       return extension.convertValueToText(value, selected, expanded, leaf, row, hasFocus);
    }
   
   /**
     * Selects the node identified by the specified path. If any
     * component of the path is hidden (under a collapsed node), and
     * <code>getExpandsSelectedPaths</code> is true it is
     * exposed (made viewable).
     *
     * @param path the <code>TreePath</code> specifying the node to select
     */
    public void setSelectionPath(TreePath path) {
        extension.setSelectionPath(path);
    }

    /**
     * Selects the nodes identified by the specified array of paths.
     * If any component in any of the paths is hidden (under a collapsed
     * node), and <code>getExpandsSelectedPaths</code> is true
     * it is exposed (made viewable).
     *
     * @param paths an array of <code>TreePath</code> objects that specifies
     *          the nodes to select
     */
    public void setSelectionPaths(TreePath[] paths) {
       extension.setSelectionPaths(paths);
    }
    
    /**
     * Sets the path identifies as the lead. The lead may not be selected.
     * The lead is not maintained by <code>JTree</code>,
     * rather the UI will update it.
     * <p>
     * This is a bound property.
     *
     * @param newPath  the new lead path
     * @since 1.3
     * @beaninfo
     *        bound: true
     *  description: Lead selection path
     */
    public void setLeadSelectionPath(TreePath newPath) {
       extension.setLeadSelectionPath(newPath);
    }

    /**
     * Sets the path identified as the anchor.
     * The anchor is not maintained by <code>JTree</code>, rather the UI will
     * update it.
     * <p>
     * This is a bound property.
     *
     * @param newPath  the new anchor path
     * @since 1.3
     * @beaninfo
     *        bound: true
     *  description: Anchor selection path
     */
    public void setAnchorSelectionPath(TreePath newPath) {
       extension.setAnchorSelectionPath(newPath);
    }
    
     /**
     * Selects the node at the specified row in the display.
     *
     * @param row  the row to select, where 0 is the first row in
     *             the display
     */
    public void setSelectionRow(int row) {
       extension.setSelectionRow(row);
    }
    
        /**
     * Selects the nodes corresponding to each of the specified rows
     * in the display. If a particular element of <code>rows</code> is
     * &lt; 0 or &gt;= <code>getRowCount</code>, it will be ignored.
     * If none of the elements
     * in <code>rows</code> are valid rows, the selection will
     * be cleared. That is it will be as if <code>clearSelection</code>
     * was invoked.
     *
     * @param rows  an array of ints specifying the rows to select,
     *              where 0 indicates the first row in the display
     */
    public void setSelectionRows(int[] rows) {
       extension.setSelectionRows(rows);
    }
    
    /**
     * Adds the node identified by the specified <code>TreePath</code>
     * to the current selection. If any component of the path isn't
     * viewable, and <code>getExpandsSelectedPaths</code> is true it is
     * made viewable.
     * <p>
     * Note that <code>JTree</code> does not allow duplicate nodes to
     * exist as children under the same parent -- each sibling must be
     * a unique object.
     *
     * @param path the <code>TreePath</code> to add
     */
    public void addSelectionPath(TreePath path) {
       extension.addSelectionPath(path);
    }
    
     /**
     * Adds each path in the array of paths to the current selection. If
     * any component of any of the paths isn't viewable and
     * <code>getExpandsSelectedPaths</code> is true, it is
     * made viewable.
     * <p>
     * Note that <code>JTree</code> does not allow duplicate nodes to
     * exist as children under the same parent -- each sibling must be
     * a unique object.
     *
     * @param paths an array of <code>TreePath</code> objects that specifies
     *          the nodes to add
     */
    public void addSelectionPaths(TreePath[] paths) {
       extension.addSelectionPaths(paths);
    }
    
    /**
     * Adds the path at the specified row to the current selection.
     *
     * @param row  an integer specifying the row of the node to add,
     *             where 0 is the first row in the display
     */
    public void addSelectionRow(int row) {
       extension.addSelectionRow(row);
    }

        /**
     * Adds the paths at each of the specified rows to the current selection.
     *
     * @param rows  an array of ints specifying the rows to add,
     *              where 0 indicates the first row in the display
     */
    public void addSelectionRows(int[] rows) {
       extension.addSelectionRows(rows);
    }
    
    /**
     * Returns the last path component of the selected path. This is
     * a convenience method for
     * {@code getSelectionModel().getSelectionPath().getLastPathComponent()}.
     * This is typically only useful if the selection has one path.
     *
     * @return the last path component of the selected path, or
     *         <code>null</code> if nothing is selected
     * @see TreePath#getLastPathComponent
     */
    public Object getLastSelectedPathComponent() {
       return extension.getLastSelectedPathComponent();
    }
    
    /**
     * Returns the path identified as the lead.
     * @return path identified as the lead
     */
    public TreePath getLeadSelectionPath() {
        return extension.getLeadSelectionPath();
    }
    
        /**
     * Returns the path identified as the anchor.
     * @return path identified as the anchor
     * @since 1.3
     */
    public TreePath getAnchorSelectionPath() {
        return extension.getAnchorSelectionPath();
    }

    /**
     * Returns the path to the first selected node.
     *
     * @return the <code>TreePath</code> for the first selected node,
     *          or <code>null</code> if nothing is currently selected
     */
    public TreePath getSelectionPath() {
        return extension.getSelectionPath();
    }
    /**
     * Returns the paths of all selected values.
     *
     * @return an array of <code>TreePath</code> objects indicating the selected
     *         nodes, or <code>null</code> if nothing is currently selected
     */
    public TreePath[] getSelectionPaths() {
        return extension.getSelectionPaths();
    }

    /**
     * Returns all of the currently selected rows. This method is simply
     * forwarded to the <code>TreeSelectionModel</code>.
     * If nothing is selected <code>null</code> or an empty array will
     * be returned, based on the <code>TreeSelectionModel</code>
     * implementation.
     *
     * @return an array of integers that identifies all currently selected rows
     *         where 0 is the first row in the display
     */
    public int[] getSelectionRows() {
        return extension.getSelectionRows();
    }
 

    //--------------------------------------------------------------------
    public TreePath getClosestPathForLocation(int x, int y)
    {
        return extension.getClosestPathForLocation(x, y);
    }

    //--------------------------------------------------------------------
    public TreePath getPathForRow(int row)
    {
        return extension.getPathForRow(row);
    }

    //--------------------------------------------------------------------
    public int getVisibleRowCount()
    {
        return extension.getVisibleRowCount();
    }

    //--------------------------------------------------------------------
    public int getRowForPath(TreePath path)
    {
        return extension.getRowForPath(path);
    }

    //--------------------------------------------------------------------
    public int getRowForLocation(int x, int y)
    {
        return extension.getRowForLocation(x, y);
    }

    //--------------------------------------------------------------------
    public void setCellRenderer(TreeCellRenderer value)
    {
        extension.setCellRenderer(value);
    }

    //--------------------------------------------------------------------
    public void setCellEditor(TreeCellEditor value)
    {
        extension.setCellEditor(value);
    }

    //--------------------------------------------------------------------
    public int getSelectionCount()
    {
        return extension.getSelectionCount();
    }

    //--------------------------------------------------------------------
    private static class DefaultTreeTableModel extends AbstractTreeTableModel
    {

        public DefaultTreeTableModel()
        {
            super(new DefaultMutableTreeNode("root"), false);
        }

        @Override
        public int getColumnCount()
        {
            return 0;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex)
        {
            return "";
        }
    }

}
