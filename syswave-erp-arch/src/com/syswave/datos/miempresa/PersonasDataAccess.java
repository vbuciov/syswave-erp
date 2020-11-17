package com.syswave.datos.miempresa;

import com.syswave.datos.common.SingletonDataAccess;
import com.syswave.entidades.miempresa.Persona;
import com.syswave.entidades.miempresa_vista.Personal;
import com.syswave.entidades.miempresa.TipoPersona;
import datalayer.api.IMediatorDataSource;
import datalayer.events.DataGetEvent;
import datalayer.events.DataSendToQueryEvent;
import datalayer.events.DataSetEvent;
import datalayer.utils.QueryDataTransfer;
import datalayer.utils.QueryParameter;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class PersonasDataAccess extends SingletonDataAccess<Persona>
{

    private final String insertProcedure = "persona_insert(?,?,?,?,?,?)";

    public PersonasDataAccess(IMediatorDataSource mysource)
    {
        super(mysource, 
              "personas", "id", "nombres", "apellidos", "nacimiento", "es_activo",
              "id_tipo_persona", "observaciones");
        setBasicOrderBy("nombres ASC, apellidos DESC");
        setInsertProcedure(insertProcedure);
    }

    //--------------------------------------------------------------------
    public List<Persona> retrieveByType(TipoPersona filtro)
    {
        QueryDataTransfer parametros = new QueryDataTransfer("persona_por_tipo");

        if (filtro != null)
        {
            parametros.addInteger("vid_tipo_persona_base", filtro.getId());
            parametros.addString("vnombre", filtro.getNombre());
            parametros.addString("vsiglas", filtro.getSiglas());
            parametros.addBoolean("vusa_booleanos", filtro.isSet());
            parametros.addBoolean("ves_activo", filtro.esActivo());
            parametros.addBoolean("vusa_personal", filtro.esUsaPersonal());
        }

        else
        {
            parametros.addInteger("vid_tipo_persona_base", -1);
            parametros.addString("vnombre", "");
            parametros.addString("vsiglas", "");
            parametros.addBoolean("vusa_booleanos", false);
            parametros.addBoolean("ves_activo", false);
            parametros.addBoolean("vusa_personal", false);
        }

        return executeReadStoredProcedure(parametros);
    }

    //--------------------------------------------------------------------
    @Override
    public Persona onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
    {
        Persona current = new Persona();

        for (int i = 1; i <= e.getColumnCount(); i++)
        {
            switch (e.getColumnName(i))
            {
                case "id":
                    current.setId(e.getInt(i));
                    break;
                case "nombres":
                    current.setNombres(e.getString(i));
                    break;
                case "apellidos":
                    current.setApellidos(e.getString(i));
                    break;
                case "nacimiento":
                    current.setNacimiento(e.getDate(i));
                    break;
                case "es_activo":
                    current.setActivo(e.getBoolean(i));
                    break;
                case "id_tipo_persona":
                    current.setId_tipo_pesrona(e.getInt(i));
                    break;
                case "observaciones":
                    current.setObservaciones(e.getObject(i) != null ? e.getString(i) : Personal.EMPTY_STRING);
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
    protected void onConvertTransfer(Persona values, DataSetEvent e) throws SQLException, UnsupportedOperationException
    {
        if (e.getDML() == insertProcedure)
        {
            e.setString(1, values.getNombres(), 60);//"vnombres"
            e.setString(2, values.getApellidos(), 60);//"vapellidos"
            e.setDate(3, values.getNacimiento());//"vnacimiento"
            e.setInt(4, values.getId_tipo_persona());//"vid_tipo_persona"
            e.setBoolean(5, values.esActivo());//"ves_activo"
            e.setString(6, values.getObservaciones(), 255);//"vobservaciones"
        }
    }

    //--------------------------------------------------------------------    
    @Override
    protected void onConvertKeyResult(DataGetEvent e, Persona value) throws SQLException, UnsupportedOperationException
    {
        for (int i = 1; i <= e.getColumnCount(); i++)
        {
            switch (e.getColumnName(i))
            {
                case "id":
                    value.setId(e.getInt(i));
                    break;
                case "nombres":
                    value.setNombres(e.getString(i));
                    break;
                case "apellidos":
                    value.setApellidos(e.getString(i));
                    break;
                case "nacimiento":
                    value.setNacimiento(e.getDate(i));
                    break;
                case "es_activo":
                    value.setActivo(e.getBoolean(i));
                    break;
                case "id_tipo_persona":
                    value.setId_tipo_pesrona(e.getInt(i));
                    break;
                case "observaciones":
                    value.setObservaciones(e.getObject(i) != null ? e.getString(i) : Personal.EMPTY_STRING);
                    break;
            }

        }
        /*else if (e.getColumnCount() > 0)
            value.setId(e.getInt(1));*/
    }

    //---------------------------------------------------------------------
    @Override
    public void onSendValues(DataSendToQueryEvent e, Persona values)
    {

        if (e.isCreateOperation())
        {
            e.addInteger("id", values.getId());
            e.addString("nombres", values.getNombres(), 60);
            e.addString("apellidos", values.getApellidos(), 60);
            e.addDate("nacimiento", values.getNacimiento());
            e.addBoolean("es_activo", values.esActivo());
            e.addInteger("id_tipo_persona", values.getId_tipo_persona());
            e.addString("observaciones", values.getObservaciones(), 255);

        }

        else if (e.isUpdateOperation())
        {
            e.addString("nombres", values.getNombres(), 60);
            e.addString("apellidos", values.getApellidos(), 60);
            e.addDate("nacimiento", values.getNacimiento());
            e.addBoolean("es_activo", values.esActivo());
            e.addInteger("id_tipo_persona", values.getId_tipo_persona());
            e.addString("observaciones", values.getObservaciones(), 255);

            //e.SendParameters.AddInteger ("fk_pers_id", values.Pers_Id)
            e.addInteger("id", QueryParameter.Operator.EQUAL, values.getId_Viejo());

        }
        else if (e.isRetrieveOperation() || e.isDeleteOperation())
        {
            if (values.getId() != Persona.EMPTY_INT)
                e.addInteger("id", QueryParameter.Operator.EQUAL, values.getId());

            if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
            {
                if (!values.getNombres().equals(Persona.EMPTY_STRING))
                    e.addString("nombres", QueryParameter.Operator.EQUAL, values.getNombres());

                if (!values.getApellidos().equals(Persona.EMPTY_STRING))
                    e.addString("apellidos", QueryParameter.Operator.EQUAL, values.getApellidos());

                if (values.getNacimiento() != Persona.EMPTY_DATE)
                    e.addDate("nacimiento", QueryParameter.Operator.EQUAL, values.getNacimiento());

                if (values.isSet())
                    e.addBoolean("es_activo", QueryParameter.Operator.EQUAL, values.esActivo());

                if (values.getId_tipo_persona() != Persona.EMPTY_INT)
                    e.addInteger("id_tipo_persona", QueryParameter.Operator.EQUAL, values.getId_tipo_persona());

                if (values.getObservaciones() != Persona.EMPTY_STRING)
                    e.addString("observaciones", QueryParameter.Operator.EQUAL, values.getObservaciones());
            }
        }
    }
}
