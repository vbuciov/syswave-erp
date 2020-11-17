package com.syswave.forms.databinding;

import com.syswave.swing.models.POJOTableModel;
import com.syswave.swing.table.events.TableModelCellFormatEvent;
import com.syswave.swing.table.events.TableModelGetValueEvent;
import com.syswave.swing.table.events.TableModelSetValueEvent;
import com.syswave.entidades.miempresa.Persona;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Victor Manuel Bucio Vargas.
 */
public class PersonaTableModel extends POJOTableModel<Persona>
{

    //---------------------------------------------------------------------
    public PersonaTableModel()
    {
        super();
    }

    //---------------------------------------------------------------------
    public PersonaTableModel(String[] columns)
    {
        super(columns);
    }

    //---------------------------------------------------------------------
    public PersonaTableModel(List<Persona> dataSource)
    {
        super(dataSource);
    }

    //---------------------------------------------------------------------
    @Override
    public void onSetValueAt(TableModelSetValueEvent<Persona> e)
    {
        Persona actual = e.getItem();
        switch (e.getDataProperty())
        {
            case "nombres": //Columna del Identificador
                actual.setNombres((String) e.getNewValue());
                break;

            case "apellidos": //Columna de la clave
                actual.setApellidos((String) e.getNewValue());
                break;

            case "nacimiento": //Columna del estatus.
                actual.setNacimiento((Date) e.getNewValue());
                break;

            case "id_tipo_persona": //Columna del estatus. Cuando se utiliza un combo se reciben objetos persona.
                actual.setId_tipo_pesrona((Integer) e.getNewValue());
                break;

            case "es_activo": //Columna del estatus.
                actual.setActivo((Boolean) e.getNewValue());
                break;
        }
    }

    //---------------------------------------------------------------------
    @Override
    public Object onGetValueAt(TableModelGetValueEvent<Persona> e)
    {
        Persona actual = e.getItem();
        switch (e.getDataProperty())
        {
            case "nombres": //Columna del Identificador
                return actual.getNombres();

            case "apellidos": //Columna de la clave
                return actual.getApellidos();

            case "nacimiento":
                return actual.getNacimiento();

            case "id_tipo_persona":
                return actual.getId_tipo_persona();

            case "es_activo": //Columna del estatus.
                return actual.esActivo();
        }

        return null;
    }

    //---------------------------------------------------------------------
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
        return true; //Todo es editable
    }

    //---------------------------------------------------------------------
    @Override
    public Class<?> onGetColumnClass(TableModelCellFormatEvent e)
    {
        switch (e.getDataProperty())
        {
            case "nacimiento":
                return Date.class;

            case "id_tipo_persona":
                return Integer.class;

            case "es_activo": //Columna del estatus.
                return Boolean.class;
        }

        return getDefaultColumnClass();
    }
}
