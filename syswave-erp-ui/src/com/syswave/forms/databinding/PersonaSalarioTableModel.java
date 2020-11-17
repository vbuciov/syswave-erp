package com.syswave.forms.databinding;

import com.syswave.entidades.miempresa.PersonaSalario;
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
public class PersonaSalarioTableModel extends POJOTableModel<PersonaSalario>
{
    public PersonaSalarioTableModel()
    {
        super();
    }
    
    public PersonaSalarioTableModel(String[] columns)
    {
        super(columns);
    }
    
    public PersonaSalarioTableModel(List<PersonaSalario> dataSource)
    {
        super(dataSource);
    }

    @Override
    public void onSetValueAt(TableModelSetValueEvent<PersonaSalario> e)
    {
        PersonaSalario actual = e.getItem();
        
        switch(e.getDataProperty())
        {
            case "consecutivo":
                actual.setConsecutivo((Integer) e.getNewValue());
                break;
                
            case "id_persona":
                actual.setIdPersona((Integer) e.getNewValue());
                break;
                
            case "fecha_vigor":
                actual.setFechaVigor((Date) e.getNewValue());
                break;
                
            case "sueldo_neto":
                actual.setSueldoNeto((Double) e.getNewValue());
                break;
                
            case "id_moneda":
                actual.setIdMoneda((Integer) e.getNewValue());
                break;
                
            case "frecuencia":
                actual.setFrecuencia((Integer) e.getNewValue());
                break;
        }
    }

    @Override
    public Object onGetValueAt(TableModelGetValueEvent<PersonaSalario> e)
    {
        PersonaSalario actual = e.getItem();
        
        switch(e.getDataProperty())
        {
            case "consecutivo":
                return actual.getConsecutivo();
                
            case "id_persona":
                return actual.getIdPersona();
                
            case "fecha_vigor":
                return actual.getFechaVigor();
                
            case "sueldo_neto":
                return actual.getSueldoNeto();
                
            case "id_moneda":
                return actual.getIdMoneda();
                
            case "frecuencia":
                return actual.getFrecuencia();
        }
        return null;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
        return true;//super.isCellEditable(rowIndex, columnIndex); //To change body of generated methods, choose Tools | Templates.
    }

    
    @Override
    public Class<?> onGetColumnClass(TableModelCellFormatEvent e)
    {
        switch(e.getDataProperty())
        {
            case "consecutivo":
            case "id_persona":
            case "id_moneda":
            case "frecuencia":
                return Integer.class;
                
            case "fecha_vigor":
                return Date.class;
                
            case "sueldo_neto":
                return Double.class;
        }
        return getDefaultColumnClass();
    }
    
}
