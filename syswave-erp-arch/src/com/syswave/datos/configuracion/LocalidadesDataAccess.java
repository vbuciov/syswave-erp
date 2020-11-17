package com.syswave.datos.configuracion;

import com.syswave.datos.common.SingletonDataAccess;
import com.syswave.entidades.configuracion.Localidad;
import datalayer.api.IMediatorDataSource;
import datalayer.events.DataGetEvent;
import datalayer.events.DataSendToQueryEvent;
import datalayer.events.DataSetEvent;
import datalayer.utils.QueryDataTransfer;
import datalayer.utils.QueryParameter;
import java.sql.SQLException;
import java.util.List;

/**
 * Esta clase brinda de acceso a los datos.
 *
 * @author Victor Manuel Bucio Vargas
 */
public class LocalidadesDataAccess extends SingletonDataAccess<Localidad>
{

    public static final String insertProcedure = "localidad_insert(?,?,?,?)";
    //public static final String insertProcedure = "localidad_insert";
    public static final String deleteProcedure = "localidad_delete";
    public static final String selectLeafProcedure = "localidad_hoja_select";

    //---------------------------------------------------------------------
    public LocalidadesDataAccess(IMediatorDataSource mysource)
    {
        super(mysource, "localidades", "id", "nombre", "siglas", "id_padre", "nivel", "es_activo");
        setBasicOrderBy("nivel ASC", "id_padre ASC");
        setInsertProcedure(insertProcedure);
    }

    //---------------------------------------------------------------------
    @Override
    public int Delete(Localidad elemento)
    {
        QueryDataTransfer parametros = new QueryDataTransfer(deleteProcedure);
        //parametros.setReturnType(java.sql.Types.INTEGER); The return type is Integer as Default
        parametros.addInteger("vid", elemento.getId());
        int affectado = (int) executeFunction(parametros);

        if (affectado > 0)
            elemento.setDeleted();

        return affectado;
    }

    //---------------------------------------------------------------------
    @Override
    public int Delete(List<Localidad> elemento)
    {
        QueryDataTransfer parametros = new QueryDataTransfer(deleteProcedure);
        //parametros.setReturnType(java.sql.Types.INTEGER); The return type is Integer as Default
        int affectado = 0, resultado;

        for (Localidad cada : elemento)
        {
            parametros.addInteger("vid", cada.getId());
            resultado = (int) executeFunction(parametros);
            affectado += resultado;
            parametros.clear();

            if (resultado > 0)
                cada.setDeleted();
        }

        return affectado;
    }

    //---------------------------------------------------------------------
    public List<Localidad> RetrieveLeafsPaths()
    {
        QueryDataTransfer parametros = new QueryDataTransfer(selectLeafProcedure);
        parametros.addInteger("vid", Localidad.EMPTY_INT);
        parametros.addString("vnombre", "");
        return executeReadStoredProcedure(parametros);
    }

    //---------------------------------------------------------------------
    public List<Localidad> RetrieveLeafsPaths(Localidad Filter)
    {
        QueryDataTransfer parametros = new QueryDataTransfer(selectLeafProcedure);
        parametros.addInteger("vid", Filter.getId());
        parametros.addString("vnombre", Filter.getNombre());
        return executeReadStoredProcedure(parametros);
    }

    //---------------------------------------------------------------------
    public List<Localidad> RetrieveReferencedLeafsPaths()
    {
        QueryDataTransfer parametros = new QueryDataTransfer("localidad_referenciada_hoja_select");
        parametros.addInteger("vid", Localidad.EMPTY_INT);
        parametros.addString("vnombre", "");
        return executeReadStoredProcedure(parametros);
    }

    //---------------------------------------------------------------------
    public List<Localidad> RetrieveReferencedLeafsPaths(Localidad Filter)
    {
        QueryDataTransfer parametros = new QueryDataTransfer("localidad_referenciada_hoja_select");
        parametros.addInteger("vid", Filter.getId());
        parametros.addString("vnombre", Filter.getNombre());
        return executeReadStoredProcedure(parametros);
    }

    //--------------------------------------------------------------------    
    /* use this when use the CallableDataAccess executeFunction.
    @Override
    protected void onPrepareProcedure(ParameteRegisterEvent e) throws SQLException, UnsupportedOperationException
    {
        if (e.getDML() == deleteProcedure)
            e.registerOutParameter(1, Types.INTEGER);
    }*/
    //--------------------------------------------------------------------    
    @Override
    protected void onConvertKeyResult(DataGetEvent e, Localidad value) throws SQLException, UnsupportedOperationException
    {
        for (int i = 1; i <= e.getColumnCount(); i++)
        {
            switch (e.getColumnName(i))
            {
                case "id":
                    value.setId(e.getInt(i));
                    break;
                case "nombre":
                    value.setNombre(e.getString(i));
                    break;
                case "siglas":
                    value.setSiglas(e.getString(i));
                    break;
                case "id_padre":
                    value.setIdPadre(e.getInt(i));
                    break;
                case "nivel":
                    value.setNivel(e.getInt(i));
                    break;
                case "es_activo":
                    value.setActivo(e.getBoolean(i));
                    break;
            }
        }
        /*  if (e.getColumnCount() > 0)
            value.setId(e.getInt(1));*/
    }

    //---------------------------------------------------------------------
    /**
     * InsertProcedure is using an executeUpdateStoredProcedure
     *
     * @throws java.sql.SQLException
     */
    @Override
    protected void onConvertTransfer(Localidad values, DataSetEvent e) throws SQLException, UnsupportedOperationException
    {
        if (e.getDML() == insertProcedure)
        {
            e.setString(1, values.getNombre(), 60); //"vnombre" (60)
            e.setString(2, values.getSiglas(), 5); //"vsiglas" (5)
            e.setInt(3, values.getIdPadre()); //"vid_padre" 
            e.setBoolean(4, values.esActivo()); //"ves_activo"
        }
    }

    //---------------------------------------------------------------------
    @Override
    public void onSendValues(DataSendToQueryEvent e, Localidad argument)
    {
        if (argument.getId() != Localidad.EMPTY_INT)
        {
            if (e.isCreateOperation())
                e.addInteger("id", argument.getId());

            else if (e.isUpdateOperation())
            {
                e.addInteger("id", argument.getId());
                e.addInteger("id", QueryParameter.Operator.EQUAL, argument.getId_Viejo());

            }
        }

        else
            e.addInteger("id", QueryParameter.Operator.EQUAL, argument.getId_Viejo());

        if (!argument.getNombre().equals(Localidad.EMPTY_STRING))
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addString("nombre", argument.getNombre());

            else if (!argument.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addString("nombre", QueryParameter.Operator.EQUAL, argument.getNombre());
        }

        if (!argument.getSiglas().equals(Localidad.EMPTY_STRING))
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addString("siglas", argument.getSiglas());

            else if (!argument.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addString("siglas", QueryParameter.Operator.EQUAL, argument.getSiglas());
        }

        if (argument.getIdPadre() > 0) //Nota: El valor cero es puesto cuando un elemento tiene NULL como padre.
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addInteger("id_padre", argument.getIdPadre());

            else if (!argument.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addInteger("id_padre", QueryParameter.Operator.EQUAL, argument.getIdPadre());
        }

        if (argument.getNivel() != Localidad.EMPTY_INT)
        {
            /*Nota: El campo nivel nunca debe ser manipulado por la aplicación.
           if (e.isCreateOperation() || e.isUpdateOperation())
                e.addInteger("nivel", argument.getNivel());

            else*/
            if (!argument.isSearchOnlyByPrimaryKey() && (e.isRetrieveOperation() || e.isDeleteOperation()))
                e.addInteger("nivel", QueryParameter.Operator.EQUAL, argument.getNivel());
        }

        if (argument.isSet())
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addBoolean("es_activo", argument.esActivo());

            else if (!argument.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addBoolean("es_activo", QueryParameter.Operator.EQUAL, argument.esActivo());
        }
    }

    //---------------------------------------------------------------------
    @Override
    public Localidad onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
    {
        Localidad current = new Localidad();

        for (int i = 1; i <= e.getColumnCount(); i++)
        {

            switch (e.getColumnName(i))
            {
                case "id":
                    current.setId(e.getInt(i));
                    break;
                case "nombre":
                    current.setNombre(e.getString(i));
                    break;
                case "siglas":
                    current.setSiglas(e.getString(i));
                    break;
                case "id_padre":
                    current.setIdPadre(e.getInt(i));
                    break;
                case "nivel":
                    current.setNivel(e.getInt(i));
                    break;
                case "es_activo":
                    current.setActivo(e.getBoolean(i));
                    break;
            }
        }

        current.setSearchOnlyByPrimaryKey(true);
        current.acceptChanges();

        return current;
    }
}
