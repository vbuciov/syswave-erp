package com.syswave.swing.table.renders;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Permite especificar el formato con el que se desplegara una fecha.
 *
 * @author Victor Manuel Bucio Vargas
 */
public class DateTimeTableCellRenderer extends DefaultTableCellRenderer
{
    String dateFormat;
    SimpleDateFormat sdf;

    //--------------------------------------------------------------------
    public DateTimeTableCellRenderer(String format)
    {
        super();
        dateFormat = format;
        sdf = new SimpleDateFormat(format);
    }

    //--------------------------------------------------------------------
    @Override
    protected void setValue(Object value)
    {
        if (value != null && value instanceof Date)
            setText(sdf.format((Date)value));

        else
            super.setValue(value);
    }
}
