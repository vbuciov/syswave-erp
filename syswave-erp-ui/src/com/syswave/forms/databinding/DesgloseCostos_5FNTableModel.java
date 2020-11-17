package com.syswave.forms.databinding;

import com.syswave.entidades.miempresa_vista.DesgloseCosto_5FN;
import com.syswave.swing.models.POJOTableModel;
import com.syswave.swing.table.events.TableModelCellFormatEvent;
import com.syswave.swing.table.events.TableModelGetValueEvent;
import com.syswave.swing.table.events.TableModelSetValueEvent;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class DesgloseCostos_5FNTableModel extends POJOTableModel<DesgloseCosto_5FN>
{

    //---------------------------------------------------------------------
    public DesgloseCostos_5FNTableModel(String[] columns)
    {
        super(columns);
    }

    //---------------------------------------------------------------------
    @Override
    public void onSetValueAt(TableModelSetValueEvent<DesgloseCosto_5FN> e)
    {
        if (e.getNewValue() != null)
        {
            DesgloseCosto_5FN actual = e.getItem();

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
                    
                case "presentacion":
                    actual.setPresentacion((String) e.getNewValue());
                    break;

                case "id_categoria":
                    actual.setIdCategoria((int) e.getNewValue());
                    break;

                case "categoria":
                    actual.setCategoria((String) e.getNewValue());
                    break;

                case "id_area_precio":
                    actual.setIdArea_precio((int) e.getNewValue());
                    break;

                case "area_precio":
                    actual.setArea_precio((String) e.getNewValue());
                    break;

                case "id_moneda":
                    actual.setIdMoneda((int) e.getNewValue());
                    break;

                case "moneda":
                    actual.setMoneda((String) e.getNewValue());
                    break;

                case "id_variante":
                    actual.setIdVariante((int) e.getNewValue());
                    break;

                case "id_bien":
                    actual.setId_grupo((int) e.getNewValue());
                    break;

                case "tipo_bien":
                    actual.setTipo_bien((String) e.getNewValue());
                    break;

/*                case "id_unidad":
                    actual.setIdUnidad((int) e.getNewValue());
                    break;

                case "unidad":
                    actual.setUnidad((String) e.getNewValue());
                    break;*/

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
    public Object onGetValueAt(TableModelGetValueEvent<DesgloseCosto_5FN> e)
    {
        DesgloseCosto_5FN actual = e.getItem();

        switch (e.getDataProperty())
        {
            case "linea":
                return actual.getLinea();
             
            case "id_precio_variable":
                return actual.getIdPrecioVariable();

            case "id_precio_indirecto":
                return actual.getIdPrecioIndirecto();

            case "presentacion":
                return actual.getPresentacion();

            case "id_categoria":
                return actual.getIdCategoria();

            case "categoria":
                return actual.getCategoria();

            case "id_area_precio":
                return actual.getIdArea_precio();

            case "area_precio":
                return actual.getArea_precio();

            case "id_moneda":
                return actual.getIdMoneda();

            case "moneda":
                return actual.getMoneda();

            case "id_variante":
                return actual.getIdVariante();

            case "id_bien":
                return actual.getId_grupo();

            case "tipo_bien":
                return actual.getTipo_bien();

            /*case "id_unidad":
                return actual.getIdUnidad();

            case "unidad":
                return actual.getUnidad();*/

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
            case "id_precio_variable":
            case "id_precio_indirecto":
            case "id_categoria":
            case "id_area_precio":
            case "id_moneda":
            case "id_variante":
            case "id_bien":
            //case "id_unidad":
            case "factor":
            case "#":
                return Integer.class;

            case "cantidad":
            case "factor_cantidad":
            case "precio":
            case "factor_precio":
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
        return columnIndex > 3;
    }
}