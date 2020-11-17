package com.syswave.forms.databinding;

import com.syswave.entidades.miempresa.DesgloseCosto;
import com.syswave.swing.models.POJOTableModel;
import com.syswave.swing.table.events.TableModelCellFormatEvent;
import com.syswave.swing.table.events.TableModelGetValueEvent;
import com.syswave.swing.table.events.TableModelSetValueEvent;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class DesgloseCostosTableModel extends POJOTableModel<DesgloseCosto>
{

    //---------------------------------------------------------------------
    public DesgloseCostosTableModel(String[] columns)
    {
        super(columns);
    }

    //---------------------------------------------------------------------
    @Override
    public void onSetValueAt(TableModelSetValueEvent<DesgloseCosto> e)
    {
        if (e.getNewValue() != null)
        {
            DesgloseCosto actual = e.getItem();

            switch (e.getDataProperty())
            {
                case "linea":
                    actual.setLinea((int) e.getNewValue());
                    break;
                    
                case "id_precio_variable":
                    actual.setIdPrecioVariable((int) e.getNewValue());
                    break;

                case "id_precio_indirecto":
                    actual.setIdPrecioIndirecto((int) e.getNewValue());
                    break;

                case "cantidad":
                    actual.setCantidad((float) e.getNewValue());
                    break;
                    
                case "factor_cantidad":
                    actual.setFactor_cantidad((float) e.getNewValue());
                    break;

                case "precio":
                    actual.setPrecio((float) e.getNewValue());
                    break;
                    
                case "factor_precio":
                    actual.setFactor_precio((float) e.getNewValue());
                    break;

                case "subtotal":
                    actual.setSubtotal((float) e.getNewValue());
                    break;

                case "factor":
                    actual.setFactor((int) e.getNewValue());
                    break;

                case "monto":
                    actual.setMonto((float) e.getNewValue());
                    break;

                case "total":
                    actual.setTotal((float) e.getNewValue());
                    break;

                case "observacion":
                    actual.setObservacion((String) e.getNewValue());
                    break;
            }

        }
    }

    //---------------------------------------------------------------------
    @Override
    public Object onGetValueAt(TableModelGetValueEvent<DesgloseCosto> e)
    {
        DesgloseCosto actual = e.getItem();

        switch (e.getDataProperty())
        {
            case "linea":
                return actual.getLinea();
                
            case "id_precio_variable":
                return actual.getIdPrecioVariable();

            case "id_precio_indirecto":
                return actual.getIdPrecioIndirecto();
                
            case "cantidad":
                return actual.getCantidad();
                
            case "factor_cantidad":
                return actual.getFactor_cantidad();

            case "precio":
                return actual.getPrecio();
                
            case "factor_precio":
                return actual.getFactor_precio();

            case "subtotal":
                return actual.getSubtotal();

            case "factor":
                return actual.getFactor();

            case "monto":
                return actual.getMonto();

            case "total":
                return actual.getTotal();

            case "observacion":
                return actual.getObservacion();

            case "#":
                return e.getRowIndex() + 1;
        }

        return null;
    }

    //---------------------------------------------------------------------
    @Override
    public Class<?> onGetColumnClass(TableModelCellFormatEvent e)
    {
        switch (e.getDataProperty())
        {
            case "linea":
            case "id_precio_indirecto":
            case "id_precio_directo":
            case "factor":
            case "#":
                return Integer.class;

            case "cantidad":
            case "precio":
            case "subtotal":
            case "monto":
            case "total":
                return Float.class;
        }

        return getDefaultColumnClass();
    }

    //---------------------------------------------------------------------
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
        return columnIndex > 0;
    }
}
