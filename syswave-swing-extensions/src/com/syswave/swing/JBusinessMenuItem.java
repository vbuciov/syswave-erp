package com.syswave.swing;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JMenuItem;

/**
 *
 * @author victor
 */
public class JBusinessMenuItem<T> extends JMenuItem
{
    T tag;
   
    public JBusinessMenuItem() {
       super();
    }
    
     public JBusinessMenuItem(T tag, String titulo) {
       super(titulo);
       this.tag = tag;
    }

    /**
     * Creates a <code>JMenuItem</code> with the specified icon.
     *
     * @param icon the icon of the <code>JMenuItem</code>
     */
    public JBusinessMenuItem(Icon icon) {
       super(icon);
    }

    /**
     * Creates a <code>JMenuItem</code> with the specified text.
     *
     * @param text the text of the <code>JMenuItem</code>
     */
    public JBusinessMenuItem(String text) {
        super (text);
    }

    /**
     * Creates a menu item whose properties are taken from the
     * specified <code>Action</code>.
     *
     * @param a the action of the <code>JMenuItem</code>
     * @since 1.3
     */
    public JBusinessMenuItem(Action a) {
       super(a);
    }

    /**
     * Creates a <code>JMenuItem</code> with the specified text and icon.
     *
     * @param text the text of the <code>JMenuItem</code>
     * @param icon the icon of the <code>JMenuItem</code>
     */
    public JBusinessMenuItem(String text, Icon icon) {
        super(text, icon);
    }

    /**
     * Creates a <code>JMenuItem</code> with the specified text and
     * keyboard mnemonic.
     *
     * @param text the text of the <code>JMenuItem</code>
     * @param mnemonic the keyboard mnemonic for the <code>JMenuItem</code>
     */
    public JBusinessMenuItem(String text, int mnemonic) {
       super(text, mnemonic  );
    }

   //----------------------------------------------
   public T getTag()
   {
      return tag;
   }

   //--------------------------------------------------------------
   public void setTag(T tag)
   {
      this.tag = tag;
   }

   
}
