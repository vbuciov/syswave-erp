package com.syswave.forms.databinding;

import com.syswave.entidades.miempresa.PersonaContrato;
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
public class PersonaContratoTableModel extends POJOTableModel<PersonaContrato>
{
    public PersonaContratoTableModel()
    {
        super();
    }
    
    public PersonaContratoTableModel(String[] columns)
    {
        super(columns);
    }
    
    public PersonaContratoTableModel(List<PersonaContrato> dataSource)
    {
        super(dataSource);
    }
    
     @Override
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
        return columnIndex > 0; //Todo es editable
    }

    @Override
    public void onSetValueAt(TableModelSetValueEvent<PersonaContrato> e)
    {
        PersonaContrato actual = e.getItem();
        
        switch(e.getDataProperty())
        {
            case "consecutivo":
                actual.setConsecutivo((Integer) e.getNewValue());
                break;
                
            case "id_persona":
                actual.setIdPersona((Integer) e.getNewValue());
                break;
                
            case "id_area":
                actual.setIdArea((Integer) e.getNewValue());
                break;
                
            case "id_puesto":
                actual.setIdPuesto((Integer) e.getNewValue());
                break;
                
            case "fecha_inicio":
                actual.setFechaInicio((Date) e.getNewValue());
                break;
                
            case "fecha_terminacion":
                actual.setFechaTerminacion((Date) e.getNewValue());
                break;
                
            case "id_jornada":
                actual.setIdJornada((Integer) e.getNewValue());
                break;
                
            case "es_tipo":
                actual.setEsTipo((Integer) e.getNewValue());
                break;
        }
    }

    @Override
    public Object onGetValueAt(TableModelGetValueEvent<PersonaContrato> e)
    {
        PersonaContrato actual = e.getItem();
        
        switch(e.getDataProperty())
        {
            case "consecutivo":
                return actual.getConsecutivo();
                
            case "id_persona":
                return actual.getIdPersona();
                
            case "id_area":
                return actual.getIdArea();
                
            case "id_puesto":
                return actual.getIdPuesto();
                
            case "fecha_inicio":
                return actual.getFechaInicio();
                
            case "fecha_terminacion":
                return actual.getFechaTerminacion()!= null?actual.getFechaTerminacion():PersonaContrato.EMPTY_DATE ;
                
            case "id_jornada":
                return actual.getIdJornada();
                
            case "es_tipo":
                return actual.getEsTipo();
        }
        return null;
    }

    @Override
    public Class<?> onGetColumnClass(TableModelCellFormatEvent e)
    {
        switch(e.getDataProperty())
        {
            case "consecutivo":
            case "id_persona":
            case "id_area":
            case "id_puesto":
            case "id_jornada":
            case "es_tipo":
                return Integer.class;
                
            case "fecha_inicio":
            case "fecha_terminacion":
                return Date.class;
        }
        return getDefaultColumnClass();
    }
    
}
