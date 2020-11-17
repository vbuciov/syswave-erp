package com.syswave.forms.databinding;

import com.syswave.entidades.miempresa.PlanMantenimiento;
import com.syswave.swing.models.POJOTableModel;
import com.syswave.swing.table.events.TableModelCellFormatEvent;
import com.syswave.swing.table.events.TableModelGetValueEvent;
import com.syswave.swing.table.events.TableModelSetValueEvent;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class PlanMantenimientosTableModel extends POJOTableModel<PlanMantenimiento>
{
    //--------------------------------------------------------------------
    public PlanMantenimientosTableModel(String[] columns)
    {
        super(columns);
    }

    //--------------------------------------------------------------------
    @Override
    public void onSetValueAt(TableModelSetValueEvent<PlanMantenimiento> e)
    {
        if (e.getNewValue() != null)
        {
            PlanMantenimiento actual = e.getItem();
            switch (e.getDataProperty())
            {
                case "linea":
                    actual.setLinea((int) e.getNewValue());
                    break;

                case "id_variante":
                    actual.setIdVariante((int) e.getNewValue());
                    break;

                case "actividad":
                    actual.setActividad((String) e.getNewValue());
                    break;

                case "es_activo":
                    actual.setActivo((boolean) e.getNewValue());
                    break;
            }
        }
    }

    //--------------------------------------------------------------------
    @Override
    public Object onGetValueAt(TableModelGetValueEvent<PlanMantenimiento> e)
    {
        PlanMantenimiento actual = e.getItem();
        switch (e.getDataProperty())
        {
            case "linea":
                return actual.getLinea();

            case "id_variante":
                return actual.getIdVariante();

            case "actividad":
                return actual.getActividad();

            case "es_activo":
                return actual.esActivo();
        }

        return null;
    }

    //--------------------------------------------------------------------
    @Override
    public Class<?> onGetColumnClass(TableModelCellFormatEvent e)
    {
        switch (e.getDataProperty())
        {
            case "linea":
            case "id_variante":
              return Integer.class;
                
            case "es_activo":
                return Boolean.class;
        }
        
        return getDefaultColumnClass();
    }

    //--------------------------------------------------------------------
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
        return true;
    }
}
