/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.orbital;

import com.syswave.swing.models.POJOComboBoxModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.ComboBoxEditor;
import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import javax.swing.text.PlainDocument;

/**
 *
 * @author sis2
 */
public final class AutoCompleteDocument extends PlainDocument
{

    private final JComboBox comboBox;
    private ComboBoxModel model;
    private JTextComponent editor;
   // private Object selectedItem;
    private JList list;
    // flag to indicate if setSelectedItem has been called
    // subsequent calls to remove/insertString should be ignored
    private boolean selecting = false;
    private final boolean hidePopupOnFocusLoss;
    private boolean hitBackspace = false;
    private boolean hitBackspaceOnSelection;

    private KeyListener editorKeyListener;
    private FocusListener editorFocusListener;

    //--------------------------------------------------------------------
    public AutoCompleteDocument(final JComboBox comboBox)
    {
        this.comboBox = comboBox;
        model = comboBox.getModel();

        Object comp = comboBox.getUI().getAccessibleChild(comboBox, 0);
        if (comp instanceof BasicComboPopup)
            list = ((BasicComboPopup) comp).getList();

        // Bug 5100422 on Java 1.5: Editable JComboBox won't hide popup when tabbing out
        hidePopupOnFocusLoss = System.getProperty("java.version").startsWith("1.5");

        configureEvents(comboBox);

        // Highlight whole text when gaining focus
        configureEditor(comboBox.getEditor());

        // Handle initially selected object
       /*selectedItem = model.getSelectedItem();
        if (selectedItem != null)
            setText(model instanceof POJOComboBoxModel ? ((POJOComboBoxModel) model).onGetText(selectedItem) : selectedItem.toString(), null);*/
        
 
        if ( model.getSelectedItem() != null)
            setText(model instanceof POJOComboBoxModel ? ((POJOComboBoxModel) model).onGetText(model.getSelectedItem()) : model.getSelectedItem().toString());
        highlightCompletedText(0);
    }

    //--------------------------------------------------------------------
    public static void enable(JComboBox comboBox)
    {
        // has to be editable
        comboBox.setEditable(true);
        // change the editor's document
        AutoCompleteDocument extension = new AutoCompleteDocument(comboBox);

        if (comboBox.getModel() instanceof POJOComboBoxModel)
            comboBox.setEditor(new POJOComboBoxTextEditor((POJOComboBoxModel) comboBox.getModel()));
    }

    //--------------------------------------------------------------------
    private void configureEvents(final JComboBox combo)
    {
        combo.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (!selecting)
                    highlightCompletedText(0);
            }
        });

        combo.addPropertyChangeListener(new PropertyChangeListener()
        {
            @Override
            public void propertyChange(PropertyChangeEvent e)
            {
                if (e.getPropertyName().equals("editor"))
                    configureEditor((ComboBoxEditor) e.getNewValue());

                else if (e.getPropertyName().equals("model"))
                    model = (ComboBoxModel) e.getNewValue();
            }
        });

        editorKeyListener = new KeyAdapter()
        {
            @Override
            public void keyPressed(KeyEvent e)
            {
                if (combo.isDisplayable() && !combo.isPopupVisible())
                    combo.setPopupVisible(true);

                hitBackspace = false;
                switch (e.getKeyCode())
                {
                    // determine if the pressed key is backspace (needed by the remove method)
                    case KeyEvent.VK_BACK_SPACE:
                        hitBackspace = true;
                        hitBackspaceOnSelection = editor.getSelectionStart() != editor.getSelectionEnd();
                        break;
                    // ignore delete key
                    case KeyEvent.VK_DELETE:
                        e.consume();
                        combo.getToolkit().beep();
                        break;

                    /*case KeyEvent.VK_ENTER:
                        if (list != null)
                        {
                            selecting = true;
                            model.setSelectedItem(selectedItem);
                            selecting = false;
                        }
                        break;*/
                }
            }
        };

        editorFocusListener = new FocusAdapter()
        {
            @Override
            public void focusGained(FocusEvent e)
            {
                highlightCompletedText(0);
            }

            @Override
            public void focusLost(FocusEvent e)
            {
                // Workaround for Bug 5100422 - Hide Popup on focus loss
                if (hidePopupOnFocusLoss)
                    comboBox.setPopupVisible(false);
            }
        };
    }

    //--------------------------------------------------------------------
    void configureEditor(ComboBoxEditor newEditor)
    {
        if (editor != null)
        {
            editor.removeKeyListener(editorKeyListener);
            editor.removeFocusListener(editorFocusListener);
        }

        if (newEditor != null)
        {
            editor = (JTextComponent) newEditor.getEditorComponent();
            editor.addKeyListener(editorKeyListener);
            editor.addFocusListener(editorFocusListener);
            editor.setDocument(this);
        }
    }

    //--------------------------------------------------------------------
    @Override
    public void remove(int offs, int len) throws BadLocationException
    {
        // return immediately when selecting an item
        if (selecting)
            return;

        if (hitBackspace)
        {
            // user hit backspace => move the selection backwards
            // old item keeps being selected
            if (offs > 0)
            {
                if (hitBackspaceOnSelection)
                    offs--;
            }
            else
            {
                // User hit backspace with the cursor positioned on the start => beep
                comboBox.getToolkit().beep(); // when available use: UIManager.getLookAndFeel().provideErrorFeedback(comboBox);
            }
            highlightCompletedText(offs);
        }
        else
        {
            super.remove(offs, len);
        }
    }

    //--------------------------------------------------------------------
    @Override
    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException
    {
        // return immediately when selecting an item
        if (selecting)
            return;
        
        // insert the string into the document
        super.insertString(offs, str, a);

        String pattern = getText(0, getLength());

        Object item;

        if (pattern == null || pattern.length() == 0)
        {
            // keep old item selected if there is no match
            item = model.getSelectedItem(); //selectedItem;
            // imitate no insert (later on offs will be incremented by str.length(): selection won't move forward)
            offs = offs - str.length();
            // provide feedback to the user that his input has been received but can not be accepted
            comboBox.getToolkit().beep(); // when available use: UIManager.getLookAndFeel().provideErrorFeedback(comboBox);
        }

        else
        {
            item = lookupItem(pattern);
            
            //Debemos reemplazar el texto de un elemento inexistente por uno que si exista.
            if (item == null)
            {
                item  = model.getElementAt(list.getSelectedIndex());
                offs = offs - str.length();
            }
            
            setSelectedItem(item);
        }

        if (item != null)
            setText(model instanceof POJOComboBoxModel ? ((POJOComboBoxModel) model).onGetText(item) : item.toString());
        
        //System.out.println(getText(0, getLength()));

        // select the completed part
        highlightCompletedText(offs + str.length());
    }

    private void setText(String text)
    {
        try
        {
            // remove all text and insert the completed string
            super.remove(0, getLength());
            super.insertString(0, text, null);
        }
        catch (BadLocationException e)
        {
            throw new RuntimeException(e.toString());
        }
    }

    private void highlightCompletedText(int start)
    {
        editor.setCaretPosition(getLength());
        editor.moveCaretPosition(start);
    }

    private void setSelectedItem(Object item)
    {
        if (item == model.getSelectedItem())
            return;

        selecting = true;

        //selectedItem = item;

        if (list != null)
        {
            int index = indexOf(item);
            list.setSelectedIndex(index);
            list.ensureIndexIsVisible(index);
        }
        
        //Nota: Si seleccionas a traves del modelo, se commitea el Lookup
        //cuando es parte de una celda en el componente JTable
         //model.setSelectedItem(item);
       
        selecting = false;
    }

    private int indexOf(Object item)
    {
        int i = 0;
        boolean seEncontro = false;

        while (i < model.getSize() && !seEncontro)
        {
            seEncontro = item == model.getElementAt(i);

            if (!seEncontro)
                i++;
        }

        return i;

    }

    private Object lookupItem(String pattern)
    {
        Object currentItem =  model.getSelectedItem(); //selectedItem

        if (currentItem != null && startsWithIgnoreCase(model instanceof POJOComboBoxModel ? ((POJOComboBoxModel) model).onGetText(currentItem) : currentItem.toString(), pattern))
            return currentItem;

        for (int i = 0, n = model.getSize(); i < n; i++)
        {
            currentItem = model.getElementAt(i);
            // current item starts with the pattern?
            if (startsWithIgnoreCase(model instanceof POJOComboBoxModel ? ((POJOComboBoxModel) model).onGetText(currentItem) : currentItem.toString(), pattern))
                return currentItem;
        }

        return null;
    }

    // checks if str1 starts with str2 - ignores case
    private boolean startsWithIgnoreCase(String str1, String str2)
    {
        return str1.toUpperCase().startsWith(str2.toUpperCase());
    }
}
