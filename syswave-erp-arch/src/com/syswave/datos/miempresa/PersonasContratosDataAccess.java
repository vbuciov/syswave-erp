package com.syswave.datos.miempresa;

import com.syswave.datos.common.SingletonDataAccess;
import com.syswave.entidades.miempresa.PersonaContrato;
import datalayer.api.IMediatorDataSource;
import datalayer.events.DataGetEvent;
import datalayer.events.DataSendToQueryEvent;
import datalayer.events.DataSetEvent;
import datalayer.utils.QueryParameter;
import java.sql.SQLException;

/**
 *
 * @author sis5
 */
public class PersonasContratosDataAccess extends SingletonDataAccess<PersonaContrato>
{

    private final String insertProcedure = "persona_contratos_insert(?,?,?,?,?,?,?,?)";
    public final String updateProcedure = "persona_contratos_update(?,?,?,?,?,?,?,?,?,?)";

    public PersonasContratosDataAccess(IMediatorDataSource mysource)
    {
        super(mysource, "persona_contratos", "consecutivo", "id_persona", "id_area",
              "id_puesto", "fecha_inicio", "fecha_terminacion", "id_jornada",
              "es_tipo", "ruta_digital");
        setBasicOrderBy("id_persona ASC", "consecutivo ASC");
        setInsertProcedure(insertProcedure);
        setUpdateProcedure(updateProcedure);
    }

    //--------------------------------------------------------------------
    @Override
    public PersonaContrato onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
    {
        PersonaContrato current = new PersonaContrato();

        for (int i = 1; i <= e.getColumnCount(); i++)
        {
            switch (e.getColumnName(i))
            {
                case "consecutivo":
                    current.setConsecutivo(e.getInt(i));
                    break;
                case "id_persona":
                    current.setIdPersona(e.getInt(i));
                    break;
                case "id_area":
                    current.setIdArea(e.getInt(i));
                    break;
                case "id_puesto":
                    current.setIdPuesto(e.getInt(i));
                    break;
                case "fecha_inicio":
                    current.setFechaInicio(e.getDate(i));
                    break;
                case "fecha_terminacion":
                    current.setFechaTerminacion(e.getDate(i));
                    break;
                case "id_jornada":
                    current.setIdJornada(e.getInt(i));
                    break;
                case "es_tipo":
                    current.setEsTipo(e.getInt(i));
                    break;
            }
        }

        current.setSearchOnlyByPrimaryKey(true);
        current.acceptChanges();

        return current;
    }

    //---------------------------------------------------------------------
    /**
     * InsertProcedure is using an executeUpdateStoredProcedure
     *
     * @throws java.sql.SQLException
     */
    @Override
    protected void onConvertTransfer(PersonaContrato values, DataSetEvent e) throws SQLException, UnsupportedOperationException
    {
        if (e.getDML() == insertProcedure)
        {
            e.setInt(1, values.getIdPersona());//"vid_persona"
            e.setInt(2, values.getIdArea());//"vid_area"
            e.setInt(3, values.getIdPuesto());//"vid_puesto"
            e.setDate(4, values.getFechaInicio());//"vfecha_inicio"
            e.setDate(5, values.getFechaTerminacion());//"vfecha_terminacion"
            e.setInt(6, values.getIdJornada());//"vid_jornada"
            e.setInt(7, values.getEsTipo());//"ves_tipo"
            e.setString(8, values.getRutaDigital(), 255);//"vruta_digital"
        }

        else if (e.getDML() == updateProcedure)
        {
            e.setInt(1, values.getConsecutivo_Viejo());//"vconsecutivo_old"
            e.setInt(2, values.getIdPersona_Viejo());//"vid_persona_old"
            e.setInt(3, values.getIdPersona());//"vid_persona_new"
            e.setInt(4, values.getIdArea());//"vid_area"
            e.setInt(5, values.getIdPuesto());//"vid_puesto"
            e.setDate(6, values.getFechaInicio());//"vfecha_inicio"
            e.setDate(7, values.getFechaTerminacion());//"vfecha_terminacion"
            e.setInt(8, values.getIdJornada());//"vid_jornada"
            e.setInt(9, values.getEsTipo());//"ves_tipo"
            e.setString(10, values.getRutaDigital(), 255);//"vruta_digital"
        }
    }

    //--------------------------------------------------------------------    
    @Override
    protected void onConvertKeyResult(DataGetEvent e, PersonaContrato value) throws SQLException, UnsupportedOperationException
    {
        for (int i = 1; i <= e.getColumnCount(); i++)
        {
            switch (e.getColumnName(i))
            {
                case "consecutivo":
                    value.setConsecutivo(e.getInt(i));
                    break;
                case "id_persona":
                    value.setIdPersona(e.getInt(i));
                    break;
                case "id_area":
                    value.setIdArea(e.getInt(i));
                    break;
                case "id_puesto":
                    value.setIdPuesto(e.getInt(i));
                    break;
                case "fecha_inicio":
                    value.setFechaInicio(e.getDate(i));
                    break;
                case "fecha_terminacion":
                    value.setFechaTerminacion(e.getDate(i));
                    break;
                case "id_jornada":
                    value.setIdJornada(e.getInt(i));
                    break;
                case "es_tipo":
                    value.setEsTipo(e.getInt(i));
                    break;
            }
        }
        /*  if (e.getColumnCount() > 0)
            value.setId(e.getInt(1));*/
    }

    //---------------------------------------------------------------------
    @Override
    public void onSendValues(DataSendToQueryEvent e, PersonaContrato values)
    {
        if (values.getConsecutivo()!= PersonaContrato.EMPTY_INT)
        {

            if (e.isCreateOperation())
                e.addInteger("consecutivo", values.getConsecutivo());

            else if (e.isUpdateOperation())
            {
                e.addInteger("consecutivo", values.getConsecutivo());
                e.addInteger("consecutivo", QueryParameter.Operator.EQUAL, values.getConsecutivo_Viejo());
            }

            else
                e.addInteger("consecutivo", QueryParameter.Operator.EQUAL, values.getConsecutivo_Viejo());
        }

        if (values.getIdPersona()!= PersonaContrato.EMPTY_INT)
        {

            if (e.isCreateOperation())
                e.addInteger("id_persona", values.getIdPersona());

            else if (e.isUpdateOperation())
            {
                e.addInteger("id_persona", values.getIdPersona());
                e.addInteger("id_persona", QueryParameter.Operator.EQUAL, values.getIdPersona_Viejo());
            }

            else
                e.addInteger("id_persona", QueryParameter.Operator.EQUAL, values.getIdPersona_Viejo());
        }

        if (values.getIdArea() != PersonaContrato.EMPTY_INT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addInteger("id_area", values.getIdArea());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addInteger("id_area", QueryParameter.Operator.EQUAL, values.getIdArea());
        }

        if (values.getIdPuesto() != PersonaContrato.EMPTY_INT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addInteger("id_puesto", values.getIdPuesto());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addInteger("id_puesto", QueryParameter.Operator.EQUAL, values.getConsecutivo());
        }

        if (values.getFechaInicio() != PersonaContrato.EMPTY_DATE)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addDate("fecha_inicio", values.getFechaInicio());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addDate("fecha_inicio", QueryParameter.Operator.EQUAL, values.getFechaInicio());
        }

        if (!values.getFechaTerminacion().equals(PersonaContrato.EMPTY_STRING))
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addDate("fecha_terminacion", values.getFechaTerminacion());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addDate("fecha_terminacion", QueryParameter.Operator.EQUAL, values.getFechaTerminacion());
        }

        if (values.getIdJornada() != PersonaContrato.EMPTY_INT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addInteger("id_jornada", values.getIdJornada());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addInteger("id_jornada", QueryParameter.Operator.EQUAL, values.getIdJornada());
        }

        if (values.getEsTipo() != PersonaContrato.EMPTY_INT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addInteger("es_tipo", values.getEsTipo());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addInteger("es_tipo", QueryParameter.Operator.EQUAL, values.getEsTipo());
        }
    }
}
