package com.syswave.swing.table.editors;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.EventObject;
import javax.swing.AbstractCellEditor;
import javax.swing.JComponent;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.TableCellEditor;
import javax.swing.tree.TreeCellEditor;

/**
 * Permite utilizar un Spinner como celda de un Table
 * @author Victor Manuel Bucio Vargas
 */
public class SpinnerTableCellEditor extends AbstractCellEditor
    implements TableCellEditor, TreeCellEditor
{
    JSpinner spinnerCell;
    protected int clickCountToStart;
    String formatPattern;
    
    //--------------------------------------------------------------------
    /**
     * Constructs a spinner with an <code>Integer SpinnerNumberModel</code>
     * with initial value 0 and no minimum or maximum limits.
     */
    public SpinnerTableCellEditor()
    {
        spinnerCell = new JSpinner();
        spinnerCell.setBorder(null);
        clickCountToStart = 2;
    }
    
    //--------------------------------------------------------------------
    /**
     * Constructs a spinner for the given model. The spinner has
     * a set of previous/next buttons, and an editor appropriate
     * for the model.
     *
     * @param model
     * @throws NullPointerException if the model is {@code null}
     */
    public SpinnerTableCellEditor (SpinnerModel model)
    {
        spinnerCell = new JSpinner(model);
        spinnerCell.setBorder(null);
        clickCountToStart = 2;
    }
    
    //--------------------------------------------------------------------
    /**
     * Constructs a spinner for the given model. The spinner has
     * a set of previous/next buttons, format Pattern, and an editor appropriate
     * for the model.
     *
     * @param model
     * @throws NullPointerException if the model is {@code null}
     */
    public SpinnerTableCellEditor (SpinnerModel model, final String formatPattern)
    {
        spinnerCell = new JSpinner(model)
        {
            @Override
            protected JComponent createEditor(SpinnerModel model) {
                    if (model instanceof SpinnerDateModel) 
                        return new JSpinner.DateEditor(this, formatPattern);
                    else if (model instanceof SpinnerListModel) 
                        return new JSpinner.ListEditor(this);
                    else if (model instanceof SpinnerNumberModel) 
                        return new JSpinner.NumberEditor(this, formatPattern);
                    else 
                        return new JSpinner.DefaultEditor(this);
            }
        };
        spinnerCell.setBorder(null);
        clickCountToStart = 2;
    }
    
    //--------------------------------------------------------------------
       /**
     * Changes the model that represents the value of this spinner.
     * If the editor property has not been explicitly set,
     * the editor property is (implicitly) set after the <code>"model"</code>
     * <code>PropertyChangeEvent</code> has been fired.  The editor
     * property is set to the value returned by <code>createEditor</code>,
     * as in:
     * <pre>
     * setEditor(createEditor(model));
     * </pre>
     *
     * @param model the new <code>SpinnerModel</code>
     * @see #getModel
     * @see #getEditor
     * @see #setEditor
     * @throws IllegalArgumentException if model is <code>null</code>
     *
     * @beaninfo
     *        bound: true
     *    attribute: visualUpdate true
     *  description: Model that represents the value of this spinner.
     */
    public void setModel(SpinnerModel model)
    {
        spinnerCell.setModel(model);
    }
    
    //--------------------------------------------------------------------
      /**
     * Returns the <code>SpinnerModel</code> that defines
     * this spinners sequence of values.
     *
     * @return the value of the model property
     * @see #setModel
     */
    public SpinnerModel getModel() {
        return spinnerCell.getModel();
    }

    //--------------------------------------------------------------------
    @Override
    public Object getCellEditorValue()
    {
        return spinnerCell.getValue();
    }

    //--------------------------------------------------------------------
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)
    {
        spinnerCell.setValue(value);
        return spinnerCell;
    }

    //--------------------------------------------------------------------
    @Override
    public Component getTreeCellEditorComponent(JTree tree, Object value, boolean isSelected, boolean expanded, boolean leaf, int row)
    {
        spinnerCell.setValue(value);
        return spinnerCell;
    }

    
    //--------------------------------------------------------------------
     /**
     * Returns a reference to the editor component.
     *
     * @return the editor <code>Component</code>
     */
    public Component getComponent() {
        return spinnerCell;
    }

    //--------------------------------------------------------------------
    /**
     * Specifies the number of clicks needed to start editing.
     *
     * @param count  an int specifying the number of clicks needed to start editing
     * @see #getClickCountToStart
     */
    public void setClickCountToStart(int count) {
        clickCountToStart = count;
    }

    //--------------------------------------------------------------------
    /**
     * Returns the number of clicks needed to start editing.
     * @return the number of clicks needed to start editing
     */
    public int getClickCountToStart() {
        return clickCountToStart;
    }
    
    //--------------------------------------------------------------------
    /**
     * Forwards the message from the <code>CellEditor</code> to
     * the <code>delegate</code>.
     * @see EditorDelegate#isCellEditable(EventObject)
     */
    @Override
    public boolean isCellEditable(EventObject anEvent) {
          if (anEvent instanceof MouseEvent) {
                return ((MouseEvent)anEvent).getClickCount() >= clickCountToStart;
            }
            return true;//delegate.isCellEditable(anEvent);
    }

    //--------------------------------------------------------------------
    /**
     * Forwards the message from the <code>CellEditor</code> to
     * the <code>delegate</code>.
     * @see EditorDelegate#shouldSelectCell(EventObject)
     */
    @Override
    public boolean shouldSelectCell(EventObject anEvent) {
        return true;//delegate.shouldSelectCell(anEvent);
    }
    
    //--------------------------------------------------------------------
     /**
      * Returns true to indicate that editing has begun.
      *
      * @param anEvent          the event
     * @return 
      */
     public boolean startCellEditing(EventObject anEvent) {
          return true;
     }

     //--------------------------------------------------------------------
    /**
     * Forwards the message from the <code>CellEditor</code> to
     * the <code>delegate</code>.
     * @see EditorDelegate#stopCellEditing
     */
    @Override
    public boolean stopCellEditing() {
         fireEditingStopped();
         return true; //delegate.stopCellEditing();
    }

    //--------------------------------------------------------------------
    /**
     * Forwards the message from the <code>CellEditor</code> to
     * the <code>delegate</code>.
     * @see EditorDelegate#cancelCellEditing
     */
    public void cancelCellEditing() {
         fireEditingCanceled();//delegate.cancelCellEditing();
    }
}