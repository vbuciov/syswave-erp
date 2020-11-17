package com.syswave.datos.miempresa;

import com.syswave.datos.common.SingletonDataAccess;

import com.syswave.entidades.miempresa_vista.Personal;
import com.syswave.entidades.miempresa.TipoPersona;
import datalayer.api.IMediatorDataSource;
import datalayer.events.DataGetEvent;
import datalayer.events.DataSendToQueryEvent;
import datalayer.events.DataSetEvent;
import datalayer.utils.QueryDataTransfer;
import datalayer.utils.QueryParameter;
import datalayer.utils.Relation;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Gilberto Aaron Jimenez Montelongo
 */
public class PersonalDataAccess extends SingletonDataAccess<Personal>
{

    /*private final String tableB = "persona_complementos";*/
    private final String insertProcedure = "personal_insert(?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private final String updateProcedure = "personal_update(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private final Relation basicOther;

    //-------------------------------------------------------------------------------  
    public PersonalDataAccess(IMediatorDataSource mysource)
    {
        super(mysource, "personal", "id", "nombres", "apellidos", "nacimiento", "es_activo",
         "id_tipo_persona", "nacionalidad", "observaciones", "religion", "es_genero",
         "es_estado_civil", "es_tipo_sangre", "altura", "peso");
        setInsertProcedure(insertProcedure);
        setUpdateProcedure(updateProcedure);
        basicOther = new Relation("personal", new String[]
                          {
                              "id", "nombres", "apellidos", //"no_empleado",
                              "nacimiento", "es_activo", "id_tipo_persona",
                              "nacionalidad", "observaciones", "religion", "es_genero",
                              "es_estado_civil", "es_tipo_sangre", "altura", "peso"
        });
        setWithAutoID(false);
        setBasicOrderBy("nombres ASC");
    }

    //--------------------------------------------------------------------
    @Override
    public List<Personal> Retrieve()
    {
        return submitQuery(basicOther);
    }

    //--------------------------------------------------------------------
    @Override
    public List<Personal> Retrieve(Personal filtro)
    {
        return submitQuery(basicOther, filtro);
    }

    //--------------------------------------------------------------------
    public List<Personal> retrieveByType()
    {
        return retrieveByType(null);
    }

//--------------------------------------------------------------------
    public List<Personal> retrieveByType(TipoPersona filtro)
    {
        QueryDataTransfer parametros = new QueryDataTransfer("personal_por_tipo");

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

//------------------------------------------------------------------------------   
    @Override
    public Personal onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
    {
        Personal current = new Personal();

        for (int i = 1; i <= e.getColumnCount(); i++)
        {
            switch (e.getColumnName(i))
            {
                case "id":
                    current.setIdPersona(e.getInt(i));
                    break;
                case "no_empleado":
                    current.setNo_empleado(e.getString(i));
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
                    current.setId_Tipo_Persona(e.getInt(i));
                    break;
                case "nacionalidad":
                    current.setNacionalidad(e.getString(i));
                    break;
                case "religion":
                    current.setReligion(e.getObject(i) != null ? e.getString(i) : "");
                    break;
                case "es_genero":
                    current.setGenero(e.getObject(i) != null ? e.getBoolean(i) : false);
                    break; 
                case "es_estado_civil":
                    current.setEstadoCivil(e.getObject(i) != null ? e.getInt(i) : Personal.EMPTY_INT);
                    break;
                case "es_tipo_sangre":
                    current.setEsTipoSangre(e.getObject(i) != null ? e.getInt(i) : Personal.EMPTY_INT);
                    break;
                case "altura":
                    current.setAltura(e.getObject(i) != null ? e.getFloat(i) : Personal.EMPTY_FLOAT);
                    break;
                case "peso":
                    current.setPeso(e.getObject(i) != null ? e.getFloat(i) : Personal.EMPTY_FLOAT);
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
    protected void onConvertTransfer(Personal values, DataSetEvent e) throws SQLException, UnsupportedOperationException
    {
        if (e.getDML() == insertProcedure)
        {
            e.setString(1, values.getNombres(), 60);//"vnombres"
            e.setString(2, values.getApellidos(), 60);//"vapellidos"
            e.setDate(3, values.getNacimiento());//"vnacimiento"

            e.setInt(4, values.getId_Tipo_Persona());//"vid_tipo_persona"
            e.setBoolean(5, values.esActivo());//"ves_activo"
            e.setString(6, values.getNacionalidad(), 30);//"vnacionalidad"
            e.setString(7, values.getReligion(), 45);//"vreligion"
            e.setBoolean(8, values.esGenero());//"ves_genero"
            e.setInt(9, values.getEstadoCivil());//"ves_estado_civil"
            e.setInt(10, values.getEsTipoSangre());//"ves_tipo_sangre"
            e.setFloat(11, values.getAltura());//"valtura"
            e.setFloat(12, values.getPeso());//"vpeso"
            e.setString(13, values.getObservaciones());//"vobservaciones"
        }

        else if (e.getDML() == updateProcedure)
        {
            e.setInt(1, values.getIdPersona());//"vid"
            e.setInt(2, values.getIdPersona_Viejo());//"vid_old"
            e.setString(3, values.getNombres(), 60);//"vnombres"
            e.setString(4, values.getApellidos(), 60);//"vapellidos"
            e.setDate(5, values.getNacimiento());//"vnacimiento"

            e.setInt(6, values.getId_Tipo_Persona());//"vid_tipo_persona"
            e.setBoolean(7, values.esActivo());//"ves_activo"
            e.setString(8, values.getNacionalidad(), 30);//"vnacionalidad"
            e.setString(9, values.getReligion(), 45);//"vreligion"
            e.setBoolean(10, values.esGenero());//"ves_genero"
            e.setInt(11, values.getEstadoCivil());//"ves_estado_civil"
            e.setInt(12, values.getEsTipoSangre());//"ves_tipo_sangre"
            e.setFloat(13, values.getAltura());//"valtura"
            e.setFloat(14, values.getPeso());//"vpeso"
            e.setString(15, values.getObservaciones());//"vobservaciones"
        }
    }

    //--------------------------------------------------------------------    
    @Override
    protected void onConvertKeyResult(DataGetEvent e, Personal value) throws SQLException, UnsupportedOperationException
    {
        for (int i = 1; i <= e.getColumnCount(); i++)
        {
            switch (e.getColumnName(i))
            {
                case "id":
                    value.setIdPersona(e.getInt(i));
                    break;
                case "no_empleado":
                    value.setNo_empleado(e.getString(i));
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
                    value.setId_Tipo_Persona(e.getInt(i));
                    break;
                case "nacionalidad":
                    value.setNacionalidad(e.getString(i));
                    break;
                case "religion":
                    value.setReligion(e.getObject(i) != null ? e.getString(i) : "");
                    break;
                case "es_genero":
                    value.setGenero(e.getObject(i) != null ? e.getBoolean(i) : false);
                    break;
                case "es_estado_civil":
                    value.setEstadoCivil(e.getObject(i) != null ? e.getInt(i) : Personal.EMPTY_INT);
                    break;
                case "es_tipo_sangre":
                    value.setEsTipoSangre(e.getObject(i) != null ? e.getInt(i) : Personal.EMPTY_INT);
                    break;
                case "altura":
                    value.setAltura(e.getObject(i) != null ? e.getFloat(i) : Personal.EMPTY_FLOAT);
                    break;
                case "peso":
                    value.setPeso(e.getObject(i) != null ? e.getFloat(i) : Personal.EMPTY_FLOAT);
                    break;
                case "observaciones":
                    value.setObservaciones(e.getObject(i) != null ? e.getString(i) : Personal.EMPTY_STRING);
                    break;
            }
        }
        /*  if (e.getColumnCount() > 0)
            value.setId(e.getInt(1));*/
    }

    //---------------------------------------------------------------------
    @Override
    public void onSendValues(DataSendToQueryEvent e, Personal values)
    {
        if (values.getIdPersona()!= Personal.EMPTY_INT)
        {

            if (e.isCreateOperation())
                e.addInteger("id", values.getIdPersona());

            else if (e.isUpdateOperation())
            {
                e.addInteger("id", values.getIdPersona());
                e.addInteger("id", QueryParameter.Operator.EQUAL, values.getIdPersona_Viejo());
            }
            else
                e.addInteger("id", QueryParameter.Operator.EQUAL, values.getIdPersona_Viejo());
        }

        if (values.getNo_empleado() != Personal.EMPTY_STRING)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addString("no_empleado", values.getNo_empleado(), 32);
            else if (!values.isSearchOnlyByPrimaryKey())
                e.addString("no_empleado", QueryParameter.Operator.EQUAL, values.getNo_empleado());
        }

        if (values.getNombres() != Personal.EMPTY_STRING)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addString("nombres", values.getNombres(), 32);
            else if (!values.isSearchOnlyByPrimaryKey())
                e.addString("nombres", QueryParameter.Operator.EQUAL, values.getNombres());
        }

        if (values.getApellidos() != Personal.EMPTY_STRING)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addString("apellidos", values.getApellidos(), 32);
            else if (!values.isSearchOnlyByPrimaryKey())
                e.addString("apellidos", QueryParameter.Operator.EQUAL, values.getNombres());
        }

        if (values.getNacimiento() != Personal.EMPTY_DATE)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addDate("nacimiento", values.getNacimiento());
            else if (!values.isSearchOnlyByPrimaryKey())
                e.addDate("nacimiento", QueryParameter.Operator.EQUAL, values.getNacimiento());
        }
        if (values.isSet())
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addBoolean("es_activo", values.esActivo());
            else if (!values.isSearchOnlyByPrimaryKey())
                e.addBoolean("es_activo", QueryParameter.Operator.EQUAL, values.esActivo());

            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addBoolean("es_genero", values.esGenero());
            else if (!values.isSearchOnlyByPrimaryKey())
                e.addBoolean("es_genero", QueryParameter.Operator.EQUAL, values.esGenero());
        }
        if (values.getId_Tipo_Persona() != Personal.EMPTY_INT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addInteger("id_tipo_persona", values.getId_Tipo_Persona());
            else if (!values.isSearchOnlyByPrimaryKey())
                e.addInteger("id_tipo_persona", QueryParameter.Operator.EQUAL, values.getId_Tipo_Persona());
        }

        if (values.getNacionalidad() != Personal.EMPTY_STRING)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addString("nacionalidad", values.getNacionalidad(), 32);
            else if (!values.isSearchOnlyByPrimaryKey())
                e.addString("nacionalidad", QueryParameter.Operator.EQUAL, values.getNacionalidad());
        }

        if (values.getReligion() != Personal.EMPTY_STRING)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addString("religion", values.getReligion(), 255);
            else if (!values.isSearchOnlyByPrimaryKey())
                e.addString("religion", QueryParameter.Operator.LIKE, String.format("%%%s%%", values.getReligion()));
        }

        if (values.getEstadoCivil() != Personal.EMPTY_INT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addInteger("es_estado_civil", values.getEstadoCivil());
            else if (!values.isSearchOnlyByPrimaryKey())
                e.addInteger("es_estado_civil", QueryParameter.Operator.EQUAL, values.getEstadoCivil());
        }

        if (values.getEsTipoSangre() != Personal.EMPTY_INT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addInteger("es_tipo_sangre", values.getEsTipoSangre());
            else if (!values.isSearchOnlyByPrimaryKey())
                e.addInteger("es_tipo_sangre", QueryParameter.Operator.EQUAL, values.getEsTipoSangre());
        }

        if (values.getAltura() != Personal.EMPTY_FLOAT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addFloat("altura", values.getAltura());
            else if (!values.isSearchOnlyByPrimaryKey())
                e.addFloat("altura", QueryParameter.Operator.EQUAL, values.getAltura());
        }

        if (values.getPeso() != Personal.EMPTY_FLOAT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addFloat("peso", values.getPeso());
            else if (!values.isSearchOnlyByPrimaryKey())
                e.addFloat("peso", QueryParameter.Operator.EQUAL, values.getPeso());
        }

        if (values.getObservaciones() != Personal.EMPTY_STRING)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addString("observaciones", values.getObservaciones());
            else if (!values.isSearchOnlyByPrimaryKey())
                e.addString("observaciones", QueryParameter.Operator.EQUAL, values.getObservaciones());
        }
    }
}
