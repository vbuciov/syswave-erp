package com.syswave.forms.databinding;

import com.syswave.swing.models.POJOTableModel;
import com.syswave.swing.table.events.TableModelCellFormatEvent;
import com.syswave.swing.table.events.TableModelGetValueEvent;
import com.syswave.swing.table.events.TableModelSetValueEvent;
import com.syswave.entidades.miempresa.PersonaIdentificador;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class PersonaIdentificadoresTableModel extends POJOTableModel<PersonaIdentificador>
{

    //---------------------------------------------------------------------

    public PersonaIdentificadoresTableModel(String[] columns)
    {
        super(columns);
    }

    //---------------------------------------------------------------------
    @Override
    public void onSetValueAt(TableModelSetValueEvent<PersonaIdentificador> e)
    {
        PersonaIdentificador actual = e.getItem();

        if (e.getNewValue() != null)
        {
            switch (e.getDataProperty())
            {
                case "clave":
                    actual.setClave((String) e.getNewValue());
                    break;

                case "id_persona":
                    actual.setIdPersona((int) e.getNewValue());
                    break;

                case "id_tipo_identificador":
                    actual.setIdTipoIdentificador((int) e.getNewValue());
                    break;
                    
                case "nota":
                    actual.setNota((String) e.getNewValue());
                    break;
            }
        }
    }

    //---------------------------------------------------------------------
    @Override
    public Object onGetValueAt(TableModelGetValueEvent<PersonaIdentificador> e)
    {
        PersonaIdentificador actual = e.getItem();

        switch (e.getDataProperty())
        {
            case "clave":
                return actual.getClave();

            case "id_persona":
                return actual.getIdPersona();

            case "id_tipo_identificador":
                return actual.getIdTipoIdentificador();
            case "nota":
                return actual.getNota();
        }

        return null;
    }

    //---------------------------------------------------------------------
    @Override
    public Class<?> onGetColumnClass(TableModelCellFormatEvent e)
    {
        switch (e.getDataProperty())
        {
            case "id_persona":

            case "id_tipo_identificador":
                return Integer.class;

            case "es_activo":
                return Boolean.class;
        }

        return getDefaultColumnClass();
    }

    //---------------------------------------------------------------------
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
        return true; //To change body of generated methods, choose Tools | Templates.
    }

}
