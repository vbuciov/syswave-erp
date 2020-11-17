package com.syswave.datos.miempresa;

import com.syswave.datos.common.SingletonDataAccess;
import com.syswave.entidades.miempresa.PersonaTieneIncidencia;
import datalayer.api.IMediatorDataSource;
import datalayer.events.DataGetEvent;
import datalayer.events.DataSendToQueryEvent;
import datalayer.events.DataSetEvent;
import datalayer.utils.QueryParameter;
import java.sql.SQLException;

/**
 * @author Carlos Soto
 */
public class PersonaIncidenciasDataAccess extends SingletonDataAccess<PersonaTieneIncidencia>
{

    public final String insertProcedure = "persona_tiene_incidencias_insert(?,?,?,?,?)";

    public PersonaIncidenciasDataAccess(IMediatorDataSource mysource)
    {
        super(mysource, "persona_incidencias", "id", "fecha", "hora", "id_persona",
              "tipo_incidencia", "observaciones");
        setBasicOrderBy("fecha desc");
        setInsertProcedure(insertProcedure);
    }

    @Override
    public PersonaTieneIncidencia onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
    {
        PersonaTieneIncidencia current = new PersonaTieneIncidencia();

        for (int i = 1; i <= e.getColumnCount(); i++)
        {
            switch (e.getColumnName(i))
            {
                case "id":
                    current.setId(e.getInt(i));
                    break;
                case "fecha":
                    current.setFecha(e.getDate(i));
                    break;
                case "hora":
                    current.setHora(e.getTime(i));
                    break;
                case "id_persona":
                    current.setIdPersona(e.getInt(i));
                    break;
                case "tipo_incidencia":
                    current.setTipo_incidencia(e.getInt(i));
                    break;
                case "observaciones":
                    current.setObservaciones(e.getString(i));
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
    protected void onConvertTransfer(PersonaTieneIncidencia values, DataSetEvent e) throws SQLException, UnsupportedOperationException
    {
        if (e.getDML() == insertProcedure)
        {
            e.setDate(1, values.getFecha());//"vfecha"
            e.setTime(2, values.getHora());//"vhora"
            e.setInt(3, values.getIdPersona());//"vid_persona"
            e.setInt(4, values.getTipo_incidencia());//"vtipo_incidencia"
            e.setString(5, values.getObservaciones(), 300);//"vobservaciones"
        }
    }

    //--------------------------------------------------------------------    
    @Override
    protected void onConvertKeyResult(DataGetEvent e, PersonaTieneIncidencia value) throws SQLException, UnsupportedOperationException
    {
        for (int i = 1; i <= e.getColumnCount(); i++)
        {
            switch (e.getColumnName(i))
            {
                case "id":
                    value.setId(e.getInt(i));
                    break;
                case "fecha":
                    value.setFecha(e.getDate(i));
                    break;
                case "hora":
                    value.setHora(e.getTime(i));
                    break;
                case "id_persona":
                    value.setIdPersona(e.getInt(i));
                    break;
                case "tipo_incidencia":
                    value.setTipo_incidencia(e.getInt(i));
                    break;
                case "observaciones":
                    value.setObservaciones(e.getString(i));
                    break;
            }
        }
        /*  if (e.getColumnCount() > 0)
            value.setId(e.getInt(1));*/
    }

    //---------------------------------------------------------------------
    @Override
    public void onSendValues(DataSendToQueryEvent e, PersonaTieneIncidencia values)
    {
        if (values.getId() != PersonaTieneIncidencia.EMPTY_INT)
        {

            if (e.isCreateOperation())
                e.addInteger("id", values.getId());

            else if (e.isUpdateOperation())
            {
                e.addInteger("id", values.getId());
                e.addInteger("id", QueryParameter.Operator.EQUAL, values.getId_Viejo());
            }

            else
                e.addInteger("id", QueryParameter.Operator.EQUAL, values.getId_Viejo());
        }

        if (values.getFecha() != PersonaTieneIncidencia.EMPTY_DATE)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addDate("fecha", values.getFecha());

            else if (!values.isSearchOnlyByPrimaryKey())
                e.addDate("fecha", QueryParameter.Operator.EQUAL, values.getFecha());
        }

        if (values.getHora() != PersonaTieneIncidencia.EMPTY_DATE)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addTime("hora", values.getHora());

            else if (!values.isSearchOnlyByPrimaryKey())
                e.addTime("hora", QueryParameter.Operator.EQUAL, values.getHora());
        }

        if (values.getIdPersona()!= PersonaTieneIncidencia.EMPTY_INT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addInteger("id_persona", values.getIdPersona());

            else if (!values.isSearchOnlyByPrimaryKey())
                e.addInteger("id_persona", QueryParameter.Operator.EQUAL, values.getIdPersona());
        }

        if (values.getTipo_incidencia() != PersonaTieneIncidencia.EMPTY_INT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addInteger("tipo_incidencia", values.getTipo_incidencia());

            else if (!values.isSearchOnlyByPrimaryKey())
                e.addInteger("tipo_incidencia", QueryParameter.Operator.EQUAL, values.getTipo_incidencia());
        }

        if (values.getObservaciones() != PersonaTieneIncidencia.EMPTY_STRING)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addString("observaciones", values.getObservaciones());

            else if (!values.isSearchOnlyByPrimaryKey())
                e.addString("observaciones", QueryParameter.Operator.EQUAL, values.getObservaciones());
        }
    }
}
