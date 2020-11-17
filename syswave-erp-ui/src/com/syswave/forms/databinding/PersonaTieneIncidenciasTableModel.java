/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.syswave.forms.databinding;

import com.syswave.entidades.miempresa.PersonaTieneIncidencia;
import com.syswave.swing.models.POJOTableModel;
import com.syswave.swing.table.events.TableModelCellFormatEvent;
import com.syswave.swing.table.events.TableModelGetValueEvent;
import com.syswave.swing.table.events.TableModelSetValueEvent;
import java.util.Date;

/**
 *
 * @author sis1
 */
public class PersonaTieneIncidenciasTableModel extends POJOTableModel<PersonaTieneIncidencia>
{

    public PersonaTieneIncidenciasTableModel()
    {
        super();
    }

    public PersonaTieneIncidenciasTableModel(String[] columns)
    {
        super(columns);
    }

    @Override
    public void onSetValueAt(TableModelSetValueEvent<PersonaTieneIncidencia> e)
    {
        PersonaTieneIncidencia actual = e.getItem();

        switch (e.getDataProperty())
        {
            case "id":
                actual.setId((Integer) e.getNewValue());
                break;
            case "fecha":
                actual.setFecha((Date) e.getNewValue());
                break;
            case "hora":
                actual.setHora((Date) e.getNewValue());
                break;
            case "id_persona":
                actual.setIdPersona((Integer) e.getNewValue());
                break;
            case "tipo_incidencia":
                actual.setTipo_incidencia((Integer) e.getNewValue());
                break;
            case "observaciones":
                 actual.setObservaciones((String) e.getNewValue());
                break;
        }
    }

    @Override
    public Object onGetValueAt(TableModelGetValueEvent<PersonaTieneIncidencia> e)
    {
        PersonaTieneIncidencia actual = e.getItem();

        switch (e.getDataProperty())
        {
            case "id":
                return actual.getId();

            case "fecha":
                return actual.getFecha();

            case "hora":
                return actual.getHora();

            case "id_persona":
                return actual.getIdPersona();

            case "tipo_incidencia":
                return actual.getTipo_incidencia();
                
            case "observaciones":
                return actual.getObservaciones();

        }

        return null;
    }

    @Override
    public Class<?> onGetColumnClass(TableModelCellFormatEvent e)
    {
        switch (e.getDataProperty())
        {
            case "id":
            case "id_persona":
            case "tipo_incidencia":
                return Integer.class;

            case "fecha":
            case "hora":
                return Date.class;
        }

        return getDefaultColumnClass();
    }

}
