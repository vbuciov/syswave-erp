package com.syswave.swing.table.renders;

import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class PasswordTableCellRenderer extends DefaultTableCellRenderer
{
    private static final String ASTERISKS = "************************";

    //--------------------------------------------------------------------
    @Override
    protected void setValue(Object value)
    {
        int length = 0;

        if (value instanceof String)
            length = ((String) value).length();

        else if (value instanceof char[])
            length = ((char[]) value).length;

        setText(asterisks(length));
    }

    //--------------------------------------------------------------------
    private String asterisks(int length)
    {
        if (length > ASTERISKS.length())
        {
            StringBuilder sb = new StringBuilder(length);
            for (int i = 0; i < length; i++)
            {
                sb.append('*');
            }
            return sb.toString();
        }
        else
        {
            return ASTERISKS.substring(0, length);
        }
    }
    
    
      //--------------------------------------------------------------------
   /*@Override
     public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean hasFocus, int row, int column)
     {
     int length = 0;
      
     Component label = super.getTableCellRendererComponent(table, value, selected, hasFocus, row, column);
      
     setEnabled(table.isEnabled());

     if (value instanceof String)
     length = ((String) value).length();
      
     else if (value instanceof char[])
     length = ((char[]) value).length;
      
    
     setText(asterisks(length));

     return label;
     }*/
}
