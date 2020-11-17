package com.syswave.forms.databinding;

import com.syswave.entidades.miempresa.Horario;
import com.syswave.swing.models.POJOTableModel;
import com.syswave.swing.table.events.TableModelCellFormatEvent;
import com.syswave.swing.table.events.TableModelGetValueEvent;
import com.syswave.swing.table.events.TableModelSetValueEvent;
import java.util.Date;
import java.util.List;

/**
 *
 * @author sis5
 */
public class HorarioTableModel extends POJOTableModel<Horario>
{

    //--------------------------------------------------------------------
    public HorarioTableModel()
    {
        super();
    }

    //--------------------------------------------------------------------
    public HorarioTableModel(String[] columns)
    {
        super(columns);

    }

    //--------------------------------------------------------------------
    public HorarioTableModel(List<Horario> dataSource)
    {
        super(dataSource);
    }

    //--------------------------------------------------------------------
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
        return true; //Todo es editable
    }

    //--------------------------------------------------------------------
    @Override
    public void onSetValueAt(TableModelSetValueEvent<Horario> e)
    {
        Horario actual = e.getItem();

        switch (e.getDataProperty())
        {
            case "id_jornada":
                actual.setIdJornada((Integer) e.getNewValue());
                break;
            case "nombre":
                actual.setNombre((String) e.getNewValue());
                break;
            case "hora_inicio":
                actual.setHoraInicio((Date) e.getNewValue());
                break;
            case "hora_fin":
                actual.setHoraFin((Date) e.getNewValue());
                break;
        }
    }

    //--------------------------------------------------------------------
    @Override
    public Object onGetValueAt(TableModelGetValueEvent<Horario> e)
    {
        Horario actual = e.getItem();

        switch (e.getDataProperty())
        {
            case "id_jornada":
                return actual.getIdJornada();
            case "nombre":
                return actual.getNombre();
            case "hora_inicio":
                return actual.getHoraInicio();
            case "hora_fin":
                return actual.getHoraFin();
        }
        return null;
    }

    //--------------------------------------------------------------------
    @Override
    public Class<?> onGetColumnClass(TableModelCellFormatEvent e)
    {
        switch (e.getDataProperty())
        {
            case "id_jornada":
                return Integer.class;
            case "nombre":
                return String.class;
            case "hora_inicio":
            case "hora_fin":
                return Date.class;
        }
        return getDefaultColumnClass();
    }

}
