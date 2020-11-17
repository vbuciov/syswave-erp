package com.syswave.swing.table.editors;

import com.orbital.AutoCompleteDocument;
import com.syswave.swing.models.POJOComboBoxModel;
import com.syswave.swing.renders.POJOListCellRenderer;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.EventObject;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.MutableComboBoxModel;

/**
 * Este Editor permite realizar Operaciones de LookUP dentro de una celda
 * ComboBox.
 *
 * @author Victor Manuel Bucio Vargas
 * @param <E>
 */
public class LookUpComboBoxTableCellEditor<E> extends DefaultCellEditor
{
   private  JComboBox<E> comboCell;

    //----------------------------------------------------------------------
    public LookUpComboBoxTableCellEditor()
    {
        this(true);
    }

    //---------------------------------------------------------------------
    public LookUpComboBoxTableCellEditor(ComboBoxModel dataSource)
    {
        this(dataSource, true);
    }

    //----------------------------------------------------------------------
    public LookUpComboBoxTableCellEditor(boolean autoCompleteEnable)
    {
        super(new JComboBox<E>());
        comboCell = (JComboBox<E>) editorComponent;
        comboCell.setBorder(null);
        //setUpLookUpDelegate(2, autoCompleteEnable);
        setUpLookUpDelegate(2);
        comboCell.setRenderer(new POJOListCellRenderer<E>());

        if (autoCompleteEnable)
            AutoCompleteDocument.enable(comboCell);

        /*comboCell.setEditable(true);
         setUpAutoComplete (comboCell);*/
    }

    //---------------------------------------------------------------------
    public LookUpComboBoxTableCellEditor(ComboBoxModel dataSource, boolean autoCompleteEnable)
    {
        super(new JComboBox<E>());
        comboCell = (JComboBox<E>) editorComponent;
        comboCell.setBorder(null);
        //setUpLookUpDelegate(2, autoCompleteEnable);
        setUpLookUpDelegate(2);
        comboCell.setRenderer(new POJOListCellRenderer<E>());
        comboCell.setModel(dataSource);

        if (autoCompleteEnable)
            AutoCompleteDocument.enable(comboCell);

        /*comboCell.setEditable(true);
         setUpAutoComplete(comboCell);*/
    }

    //---------------------------------------------------------------------
    public void setModel(ComboBoxModel dataSource)
    {
        comboCell.setModel(dataSource);
    }

    //---------------------------------------------------------------------
    public ComboBoxModel getModel()
    {
        return comboCell.getModel();
    }

    //---------------------------------------------------------------------
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)
    {
        Object realValue;
        MutableComboBoxModel<E> model = (MutableComboBoxModel<E>) comboCell.getModel();

        if (model instanceof POJOComboBoxModel && value != null)
        {
            POJOComboBoxModel<E> realModel = (POJOComboBoxModel<E>) model;
            realValue = realModel.getElementAt(realModel.indexOfValue(value));
        }

        else
            realValue = value;
        
        //Revisamos si la tabla esta preparada para dejar al combo iniciar edición
        //tan pronto se presione una tecla.
        if ( comboCell.isEditable() && !table.getSurrendersFocusOnKeystroke())
            table.setSurrendersFocusOnKeystroke(true);
        
        return super.getTableCellEditorComponent(table, realValue, isSelected, row, column);
    }

    //---------------------------------------------------------------------
    /**
     * Removes the delegate default configuratio for ComboBox. And create the
     * own logic.
     */
    private void setUpLookUpDelegate(int click_count/*, boolean autoCompleteEnable*/)
    {
        comboCell.removeActionListener(delegate);
        setClickCountToStart(click_count); //Indicamos cuantos clicks se necesitan para iniciar edición.
        
        //Si esta propiedad esta habilida se permite escribir aunque no se encuentre en modo de edición.
        //comboCell.putClientProperty("JComboBox.isTableCellEditor", !autoCompleteEnable);

        delegate = new EditorDelegate()
        {
            @Override
            public void setValue(Object value)
            {
                comboCell.setSelectedItem(value);
            }

            @Override
            public Object getCellEditorValue()
            {
                if (comboCell.getModel() instanceof POJOComboBoxModel)
                {
                    POJOComboBoxModel<E> realModel = (POJOComboBoxModel<E>) comboCell.getModel();
                    return realModel.getSelectedValue();
                }
                else
                    return comboCell.getSelectedItem();
            }

            @Override
            public boolean shouldSelectCell(EventObject anEvent)
            {
                if (anEvent instanceof MouseEvent)
                {
                    MouseEvent e = (MouseEvent) anEvent;
                    return e.getID() != MouseEvent.MOUSE_DRAGGED;
                }
                return true;
            }

            @Override
            public boolean stopCellEditing()
            {
                if (comboCell.isEditable())
                {
                    // Commit edited value.
                    comboCell.actionPerformed(new ActionEvent(LookUpComboBoxTableCellEditor.this, 0, ""));
                }
                return super.stopCellEditing();
            }
        };

        comboCell.addActionListener(delegate);
    }
}
