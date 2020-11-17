package com.syswave.datos.miempresa;

import com.syswave.datos.common.SingletonDataAccess;
import com.syswave.entidades.miempresa.PersonaEducacion;
import datalayer.api.IMediatorDataSource;
import datalayer.events.DataGetEvent;
import datalayer.events.DataSendToQueryEvent;
import datalayer.events.DataSetEvent;
import datalayer.utils.QueryParameter;
import java.sql.SQLException;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class PersonaEducacionesDataAccess extends SingletonDataAccess<PersonaEducacion>
{

    private final String insertProcedure = "persona_educacion_insert(?,?,?,?,?,?,?)";

    public PersonaEducacionesDataAccess(IMediatorDataSource mysource)
    {
        super(mysource, "persona_educaciones", "consecutivo", "id_persona", "nombre",
              "titulo", "fecha_inicio", "fecha_fin", "es_cursando",
              "es_tipo");
        setInsertProcedure(insertProcedure);
    }

    //--------------------------------------------------------------------
    @Override
    public PersonaEducacion onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
    {
        PersonaEducacion current = new PersonaEducacion();

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
                case "nombre":
                    current.setNombre(e.getString(i));
                    break;
                case "titulo":
                    current.setTitulo(e.getString(i));
                    break;
                case "fecha_inicio":
                    current.setFechaInicio(e.getDate(i));
                    break;
                case "fecha_fin":
                    current.setFechFin(e.getDate(i));
                    break;
                case "es_cursando":
                    current.setCursando(e.getBoolean(i));
                    break;
                case "es_tipo":
                    current.setTipo(e.getInt(i));
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
    protected void onConvertTransfer(PersonaEducacion values, DataSetEvent e) throws SQLException, UnsupportedOperationException
    {
        if (e.getDML() == insertProcedure)
        {
            //e.addInteger("vconsecutivo", values.getConsecutivo());
            e.setInt(1, values.getIdPersona());//"vid_persona"
            e.setString(2, values.getNombre());//"vnombre"
            e.setString(3, values.getTitulo());//"vtitulo"
            e.setDate(4, values.getFechaInicio());//"vfecha_inicio"
            e.setDate(5, values.getFechaFin());//"vfecha_fin"
            e.setBoolean(6, values.esCursando());//"ves_cursando"
            e.setInt(7, values.getTipo());//"ves_tipo"
        }
    }

    //--------------------------------------------------------------------    
    @Override
    protected void onConvertKeyResult(DataGetEvent e, PersonaEducacion value) throws SQLException, UnsupportedOperationException
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
                case "nombre":
                    value.setNombre(e.getString(i));
                    break;
                case "titulo":
                    value.setTitulo(e.getString(i));
                    break;
                case "fecha_inicio":
                    value.setFechaInicio(e.getDate(i));
                    break;
                case "fecha_fin":
                    value.setFechFin(e.getDate(i));
                    break;
                case "es_cursando":
                    value.setCursando(e.getBoolean(i));
                    break;
                case "es_tipo":
                    value.setTipo(e.getInt(i));
                    break;
            }
        }
        /*  if (e.getColumnCount() > 0)
            value.setId(e.getInt(1));*/
    }

    //---------------------------------------------------------------------
    @Override
    public void onSendValues(DataSendToQueryEvent e, PersonaEducacion values)
    {
        if (values.getConsecutivo() != PersonaEducacion.EMPTY_INT)
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

        if (values.getIdPersona() != PersonaEducacion.EMPTY_INT)
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

        if (values.getNombre() != PersonaEducacion.EMPTY_STRING)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addString("nombre", values.getNombre());

            else if (!values.isSearchOnlyByPrimaryKey())
                e.addString("nombre", QueryParameter.Operator.EQUAL, values.getNombre());
        }

        if (values.getTitulo() != PersonaEducacion.EMPTY_STRING)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addString("titulo", values.getTitulo());

            else if (!values.isSearchOnlyByPrimaryKey())
                e.addString("titulo", QueryParameter.Operator.EQUAL, values.getTitulo());
        }

        if (values.getFechaInicio() != PersonaEducacion.EMPTY_DATE)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addDate("fecha_inicio", values.getFechaInicio());

            else if (!values.isSearchOnlyByPrimaryKey())
                e.addDate("fecha_inicio", QueryParameter.Operator.EQUAL, values.getFechaFin());
        }

        if (values.getFechaFin() != PersonaEducacion.EMPTY_DATE)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addDate("fecha_fin", values.getFechaFin());

            else if (!values.isSearchOnlyByPrimaryKey())
                e.addDate("fecha_fin", QueryParameter.Operator.EQUAL, values.getFechaFin());
        }

        if (values.isSet())
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addBoolean("es_cursando", values.esCursando());

            else if (!values.isSearchOnlyByPrimaryKey())
                e.addBoolean("es_cursando", QueryParameter.Operator.EQUAL, values.esCursando());
        }

        if (values.getTipo() != PersonaEducacion.EMPTY_INT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addInteger("es_tipo", values.getTipo());

            else if (!values.isSearchOnlyByPrimaryKey())
                e.addInteger("es_tipo", QueryParameter.Operator.EQUAL, values.getTipo());
        }
    }
}
