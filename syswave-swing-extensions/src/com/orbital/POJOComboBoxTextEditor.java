package com.orbital;

import com.syswave.swing.models.POJOComboBoxModel;
import javax.swing.plaf.basic.BasicComboBoxEditor;

/**
 *
 * @author sis2
 */
public class POJOComboBoxTextEditor extends BasicComboBoxEditor
{

    protected Object oldValue;
    private final POJOComboBoxModel model;

    public POJOComboBoxTextEditor(POJOComboBoxModel model)
    {
        this.model = model;
    }

    // checks if str1 starts with str2 - ignores case
    private boolean startsWithIgnoreCase(String str1, String str2)
    {
        return str1.toUpperCase().startsWith(str2.toUpperCase());
    }

    private Object lookupItem(String pattern)
    {
        Object currentItem = model.getSelectedItem();

        if (currentItem != null && startsWithIgnoreCase(model instanceof POJOComboBoxModel ? ((POJOComboBoxModel) model).onGetText(currentItem) : currentItem.toString(), pattern))
            return currentItem;
        
        for (int i = 0 , n = model.getSize(); i < n; i++)
        {
            currentItem = model.getElementAt(i);
            // current item starts with the pattern?
            if (startsWithIgnoreCase(model instanceof POJOComboBoxModel ? ((POJOComboBoxModel) model).onGetText(currentItem) : currentItem.toString(), pattern))
                return currentItem;
        }

        return null;
    }

    @Override
    public Object getItem()
    {
        Object newValue = lookupItem(editor.getText());

        if (oldValue != null)
        {
            // The original value is not a string. Should return the value in it's
            // original type.
             if (newValue == null || newValue.equals(oldValue))
                return oldValue;
        }
        return newValue;
    }

    @Override
    public void setItem(Object anObject)
    {
        String text;

        if (anObject != null)
        {
            text = model.onGetText(anObject);

            if (text == null)
                text = "";

            oldValue = anObject;
        }
        else
            text = "";

        // workaround for 4530952
        if (!text.equals(editor.getText()))
            editor.setText(text);
    }

}
