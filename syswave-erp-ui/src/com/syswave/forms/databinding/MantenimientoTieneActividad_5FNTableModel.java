package com.syswave.forms.databinding;

import com.syswave.entidades.miempresa_vista.MantenimientoTieneActividad_5FN;
import com.syswave.swing.models.POJOTableModel;
import com.syswave.swing.table.events.TableModelCellFormatEvent;
import com.syswave.swing.table.events.TableModelGetValueEvent;
import com.syswave.swing.table.events.TableModelSetValueEvent;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class MantenimientoTieneActividad_5FNTableModel extends POJOTableModel<MantenimientoTieneActividad_5FN>
{
    //--------------------------------------------------------------------
    public MantenimientoTieneActividad_5FNTableModel(String[] columns)
    {
        super(columns);
    }

    //--------------------------------------------------------------------
    @Override
    public void onSetValueAt(TableModelSetValueEvent<MantenimientoTieneActividad_5FN> e)
    {
        if (e.getNewValue() != null)
        {
            MantenimientoTieneActividad_5FN actual = e.getItem();
            switch (e.getDataProperty())
            {
                case "selected":
                    actual.setSelected((boolean) e.getNewValue());
                    break;

                case "actividad":
                    actual.setActividad((String) e.getNewValue());
                    break;

                case "id_mantenimiento":
                    actual.setIdMantenimiento((int) e.getNewValue());
                    break;

                case "linea_plan":
                    actual.setLinea((int) e.getNewValue());
                    break;

                case "id_variante_plan":
                    actual.setIdVariante((int) e.getNewValue());
                    break;

                case "id_actividad":
                    String[] key = ((String) e.getNewValue()).split(",");
                    actual.setIdVariante(Integer.parseInt(key[0]));
                    actual.setLinea(Integer.parseInt(key[1]));
                    //actual.getFk_mantenimiento_plan_id_actividad().aetCompositeKey();
                    break;
            }
        }
    }

    //--------------------------------------------------------------------
    @Override
    public Object onGetValueAt(TableModelGetValueEvent<MantenimientoTieneActividad_5FN> e)
    {
        MantenimientoTieneActividad_5FN actual = e.getItem();
        switch (e.getDataProperty())
        {
            case "selected":
                return actual.isSelected();

            case "actividad":
                return actual.getActividad();

            case "id_mantenimiento":
                return actual.getIdMantenimiento();

            case "linea_plan":
                return actual.getLinea();

            case "id_variante_plan":
                return actual.getIdVariante();

            case "id_actividad":
                return actual.getHasOneActividad().getCompositeKey();
        }

        return null;
    }

    //--------------------------------------------------------------------
    @Override
    public Class<?> onGetColumnClass(TableModelCellFormatEvent e)
    {
        switch (e.getDataProperty())
        {
            case "selected":
                return Boolean.class;

            case "id_mantenimiento":
            case "linea_plan":
            case "id_variante_plan":
                return Integer.class;
        }

        return getDefaultColumnClass();
    }

    //--------------------------------------------------------------------
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
        return columnIndex != 1;
    }

}
