package com.syswave.forms.databinding;

import com.syswave.entidades.miempresa.Contrato;
import com.syswave.swing.models.POJOTableModel;
import com.syswave.swing.table.events.TableModelCellFormatEvent;
import com.syswave.swing.table.events.TableModelGetValueEvent;
import com.syswave.swing.table.events.TableModelSetValueEvent;

/**
 *
 * @author sis5
 */
public class ContratoTableModel extends POJOTableModel<Contrato>
{

    //---------------------------------------------------------------------
    public ContratoTableModel()
    {
        super();
    }

    //---------------------------------------------------------------------
    public ContratoTableModel(String[] columns)
    {
        super(columns);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
        return columnIndex == 0; //Todo es editable
    }
    
    //---------------------------------------------------------------------
    @Override
    public void onSetValueAt(TableModelSetValueEvent<Contrato> e)
    {
        Contrato actual = e.getItem();

        if (e.getNewValue() != null)
        {
            switch (e.getDataProperty())
            {
                case "id":
                    actual.setId((int) e.getNewValue());
                    break;

                case "nombre":
                    actual.setNombre((String) e.getNewValue());
                    break;

                case "contenido":
                    actual.setContenido((byte[]) e.getNewValue());
                    break;

                case "formato":
                    actual.setFormato((String) e.getNewValue());
                    break;

                case "longitud":
                    actual.setLongitud((int) e.getNewValue());
                    break;
            }
        }
    }

    //---------------------------------------------------------------------
    @Override
    public Object onGetValueAt(TableModelGetValueEvent<Contrato> e)
    {
        Contrato actual = e.getItem();

        switch (e.getDataProperty())
        {
            case "id":
                return actual.getId();

            case "nombre":
                return actual.getNombre();

            case "contenido":
                return actual.getContenido();

            case "formato":
                return actual.getFormato();

            case "longitud":
                return actual.getLongitud();
        }
        return null;
    }

    //---------------------------------------------------------------------
    @Override
    public Class<?> onGetColumnClass(TableModelCellFormatEvent e)
    {
        switch (e.getDataProperty())
        {
            case "id":
            case "longitud":
                return Integer.class;

            case "contenido":
                return Byte.class;
        }
        return getDefaultColumnClass();
    }

}
