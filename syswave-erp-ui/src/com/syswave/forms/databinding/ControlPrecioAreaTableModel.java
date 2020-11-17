package com.syswave.forms.databinding;

import com.syswave.entidades.miempresa.ControlPrecio_tiene_Area;
import com.syswave.swing.models.POJOTableModel;
import com.syswave.swing.table.events.TableModelCellFormatEvent;
import com.syswave.swing.table.events.TableModelGetValueEvent;
import com.syswave.swing.table.events.TableModelSetValueEvent;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class ControlPrecioAreaTableModel extends POJOTableModel<ControlPrecio_tiene_Area>
{

    //---------------------------------------------------------------------
    public ControlPrecioAreaTableModel(String[] columns)
    {
        super(columns);
    }

    //---------------------------------------------------------------------
    @Override
    public void onSetValueAt(TableModelSetValueEvent<ControlPrecio_tiene_Area> e)
    {
        ControlPrecio_tiene_Area actual = e.getItem();

        if (e.getNewValue() != null)
        {
            switch (e.getDataProperty())
            {
                case "id_area_precio":
                    actual.setIdAreaPrecio((int) e.getNewValue());
                    break;

                case "cantidad":
                    actual.setCantidad((float) e.getNewValue());
                    break;

                case "subtotal":
                    actual.setSubtotal((float) e.getNewValue());
                    break;

                case "monto":
                    actual.setMonto((float) e.getNewValue());
                    break;

                case "factor":
                    actual.setFactor((int) e.getNewValue());
                    break;

                case "total":
                    actual.setTotal((float) e.getNewValue());
                    break;

                case "descripcion":
                    actual.getHasOneAreaPrecio().setDescripcion((String) e.getNewValue());
                    break;
            }
        }
    }

    //---------------------------------------------------------------------
    @Override
    public Object onGetValueAt(TableModelGetValueEvent<ControlPrecio_tiene_Area> e)
    {
        ControlPrecio_tiene_Area actual = e.getItem();
        switch (e.getDataProperty())
        {
            case "id_area_precio":
                return actual.getIdAreaPrecio();

            case "cantidad":
                return actual.getCantidad();

            case "subtotal":
                return actual.getSubtotal();

            case "monto":
                return actual.getMonto();

            case "factor":
                return actual.getFactor();

            case "total":
                return actual.getTotal();

            case "descripcion":
                return actual.getHasOneAreaPrecio().getDescripcion();
        }

        return null;
    }

    //---------------------------------------------------------------------
    @Override
    public Class<?> onGetColumnClass(TableModelCellFormatEvent e)
    {
        switch (e.getDataProperty())
        {
            case "factor":
            case "id_area_precio":
                return Integer.class;

            case "cantidad":
            case "subtotal":
            case "monto":
            case "total":
                return Float.class;
        }

        return getDefaultColumnClass();
    }

    //---------------------------------------------------------------------
    @Override
    public boolean isCellEditable(int row, int column)
    {
        return column > 2;
    }

}
