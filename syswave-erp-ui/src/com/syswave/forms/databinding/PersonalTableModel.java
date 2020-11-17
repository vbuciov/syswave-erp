/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.syswave.forms.databinding;

import com.syswave.entidades.miempresa_vista.Personal;
import com.syswave.swing.models.POJOTableModel;
import com.syswave.swing.table.events.TableModelCellFormatEvent;
import com.syswave.swing.table.events.TableModelGetValueEvent;
import com.syswave.swing.table.events.TableModelSetValueEvent;
import java.util.Date;
import java.util.List;

/**
 *
 * @author sistemas1
 */
public class PersonalTableModel extends POJOTableModel<Personal>
{

    //---------------------------------------------------------------------
    public PersonalTableModel(String[] columns)
    {
        super(columns);
    }

    //---------------------------------------------------------------------
    public PersonalTableModel(List<Personal> dataSource)
    {
        super(dataSource);
    }

    //---------------------------------------------------------------------
    @Override
    public void onSetValueAt(TableModelSetValueEvent<Personal> e)
    {
        Personal actual = e.getItem();

        if (e.getNewValue() != null)
        {
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
                    actual.setId_Tipo_Persona((Integer) e.getNewValue());
                    break;

                case "es_activo": //Columna del estatus.
                    actual.setActivo((Boolean) e.getNewValue());
                    break;

                case "nacionalidad": //Columna de la nacionalidad
                    actual.setNacionalidad((String) e.getNewValue());
                    break;

                case "religion": //Columna de religion.
                    actual.setReligion((String) e.getNewValue());
                    break;

                case "es_genero": //Columna del genero. true: femenino, false: masculino
                    actual.setGenero( ( (Integer) e.getNewValue()) > 0  );
                    break;

                case "es_estado_civil": //Columna del estado civil.
                    actual.setEstadoCivil((Integer) e.getNewValue());
                    break;

                case "es_tipo_sangre": //Columna del tipo de sangre.
                    actual.setEsTipoSangre((Integer) e.getNewValue());
                    break;

                case "altura": //Columna de la altura.
                    actual.setAltura((Integer) e.getNewValue());
                    break;

                case "peso": //Columna de la altura.
                    actual.setPeso((Integer) e.getNewValue());
                    break;
            }
        }
    }

    //---------------------------------------------------------------------
    @Override
    public Object onGetValueAt(TableModelGetValueEvent<Personal> e)
    {
        Personal actual = e.getItem();
        switch (e.getDataProperty())
        {
            case "nombres": //Columna del Identificador
                return actual.getNombres();

            case "apellidos": //Columna de la clave
                return actual.getApellidos();

            case "nacimiento":
                return actual.getNacimiento();

            case "id_tipo_persona":
                return actual.getId_Tipo_Persona();

            case "es_activo": //Columna del estatus.
                return actual.esActivo();

            case "nacionalidad": //Columna de la nacionalidad
                return actual.getNacionalidad();

            case "religion": //Columna de religion.
                return actual.getReligion();

            case "es_genero": //Columna del genero. (1) Femenino, (0) Masculino
                return actual.esGenero()?1:0;

            case "es_estado_civil": //Columna del estado civil.
                return actual.getEstadoCivil();

            case "es_tipo_sangre": //Columna del estado civil.
                return actual.getEsTipoSangre();

            case "altura": //Columna del estado civil.
                return actual.getAltura();

            case "peso": //Columna del estado civil.
                return actual.getPeso();

            case "no_empleado":
                return actual.getNo_empleado();
        }

        return null;
    }

    //---------------------------------------------------------------------
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
        return columnIndex > 0; //La primer columna debe ser número de empleado.
    }

    //---------------------------------------------------------------------
    @Override
    public Class<?> onGetColumnClass(TableModelCellFormatEvent e)
    {
        switch (e.getDataProperty())
        {
            case "nacimiento": //Columna del Identificador
                return Date.class;

            case "id_tipo_persona": //Columna de la clave
            case "es_estado_civil":
            case "tipo_sangre":
            case "es_genero":
                return Integer.class;

            case "es_activo":
                return Boolean.class;

            case "altura":
            case "peso":
                return Float.class;
        }

        return getDefaultColumnClass();
    }
}
