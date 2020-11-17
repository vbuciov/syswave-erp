package com.syswave.datos.miempresa;

import com.syswave.datos.common.SingletonDataAccess;
import com.syswave.entidades.miempresa.AreaPrecio;
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
public class AreasPreciosDataAccess extends SingletonDataAccess<AreaPrecio>
{

    public final String insertProcedure = "area_precio_insert(?,?,?,?,?)";
    public final String deleteProcedure = "area_precio_delete";

    public AreasPreciosDataAccess(IMediatorDataSource mysource)
    {
        super(mysource, "areas_precios", "id", "descripcion", "es_tipo",
              "es_costo_variable", "es_costo_directo", "id_padre", "nivel");
        setBasicOrderBy("nivel ASC", "id_padre ASC");
        setInsertProcedure(insertProcedure);
    }

    //---------------------------------------------------------------------
    @Override
    public int Delete(AreaPrecio elemento)
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
    public int Delete(List<AreaPrecio> elemento)
    {
        QueryDataTransfer parametros = new QueryDataTransfer(deleteProcedure);
        //parametros.setReturnType(java.sql.Types.INTEGER); The return type is Integer as Default
        int affectado = 0, resultado;

        for (AreaPrecio cada : elemento)
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
    public List<AreaPrecio> RetrieveLeafsPaths()
    {
        QueryDataTransfer parametros = new QueryDataTransfer("areas_precios_hoja_select");
        parametros.addInteger("vid", AreaPrecio.EMPTY_INT);
        parametros.addString("vdescripcion", "");
        return executeReadStoredProcedure(parametros);
    }

    //---------------------------------------------------------------------
    public List<AreaPrecio> RetrieveLeafsPaths(AreaPrecio Filter)
    {
        QueryDataTransfer parametros = new QueryDataTransfer("areas_precios_hoja_select");
        parametros.addInteger("vid", Filter.getId());
        parametros.addString("vdescripcion", Filter.getDescripcion());
        return executeReadStoredProcedure(parametros);
    }

    //---------------------------------------------------------------------
    /**
     * InsertProcedure is using an executeUpdateStoredProcedure
     *
     * @throws java.sql.SQLException
     */
    @Override
    protected void onConvertTransfer(AreaPrecio values, DataSetEvent e) throws SQLException, UnsupportedOperationException
    {
        if (e.getDML() == insertProcedure)
        {
            e.setString(1, values.getDescripcion(), 60); //"vdescripcion"
            e.setInt(2, values.getEsTipo()); //"ves_tipo"
            e.setBoolean(3, values.esCostoDirecto()); //"ves_costo_directo"
            e.setBoolean(4, values.esCostoVariable()); //"ves_costo_variable"
            e.setInt(5, values.getIdPadre()); //"vid_padre"
        }
    }

    //--------------------------------------------------------------------    
    @Override
    protected void onConvertKeyResult(DataGetEvent e, AreaPrecio value) throws SQLException, UnsupportedOperationException
    {
        if (e.getDML() == insertProcedure)
        {
            for (int i = 1; i <= e.getColumnCount(); i++)
            {
                switch (e.getColumnName(i))
                {
                    case "id":
                        value.setId(e.getInt(i));
                        break;
                    case "descripcion":
                        value.setDescripcion(e.getString(i));
                        break;
                    case "es_tipo":
                        value.setEsTipo(e.getInt(i));
                        break;
                    case "es_costo_directo":
                        value.setCostoDirecto(e.getBoolean(i));
                        break;
                    case "es_costo_variable":
                        value.setEsCostoVariable(e.getBoolean(i));
                        break;
                    case "id_padre":
                        value.setIdPadre(e.getInt(i));
                        break;
                    case "nivel":
                        value.setNivel(e.getInt(i));
                        break;
                }
            }
        }
        else
            if (e.getColumnCount() > 0)
                value.setId(e.getInt(1));
    }

    //--------------------------------------------------------------------
    @Override
    public void onSendValues(DataSendToQueryEvent e, AreaPrecio argument)
    {
        if (argument.getId() != AreaPrecio.EMPTY_INT)
        {
            if (e.isCreateOperation())
                e.addInteger("id", argument.getId());

            else if (e.isUpdateOperation())
            {
                e.addInteger("id", argument.getId());
                e.addInteger("id", QueryParameter.Operator.EQUAL, argument.getId_Viejo());
            }

            else
                e.addInteger("id", QueryParameter.Operator.EQUAL, argument.getId_Viejo());
        }

        if (!argument.getDescripcion().equals(AreaPrecio.EMPTY_STRING))
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addString("descripcion", argument.getDescripcion());

            else if (!argument.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addString("descripcion", QueryParameter.Operator.EQUAL, argument.getDescripcion());
        }
        
        if (argument.getEsTipo() != AreaPrecio.EMPTY_INT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addInteger("es_tipo", argument.getEsTipo());

            else if (!argument.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addInteger("es_tipo", QueryParameter.Operator.EQUAL, argument.getEsTipo());
        }

        //Nota: Debido a la inexistencia de una reprentación de nulos en booleanos
        //Estos solo son trabajados cuando el objeto reporta cambios.
        if (argument.isSet())
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addBoolean("es_costo_directo", argument.esCostoDirecto());

            else if (!argument.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addBoolean("es_costo_directo", QueryParameter.Operator.EQUAL, argument.esCostoDirecto());
            
            
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addBoolean("es_costo_variable", argument.esCostoVariable());

            else if (!argument.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addBoolean("es_costo_variable", QueryParameter.Operator.EQUAL, argument.esCostoVariable());
        }

        if (argument.getIdPadre() > 0) //Nota: El valor cero es puesto cuando un elemento tiene NULL como padre.
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addInteger("id_padre", argument.getIdPadre());

            else if (!argument.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addInteger("id_padre", QueryParameter.Operator.EQUAL, argument.getIdPadre());
        }

        if (argument.getNivel() != AreaPrecio.EMPTY_INT)
        {
            /*Nota: El campo nivel nunca debe ser manipulado por la aplicación.
                 if (e.isCreateOperation() || e.isUpdateOperation())
                 e.addInteger("nivel", argument.getNivel());

                 else*/
            if (!argument.isSearchOnlyByPrimaryKey() && (e.isRetrieveOperation() || e.isDeleteOperation()))
                e.addInteger("nivel", QueryParameter.Operator.EQUAL, argument.getNivel());
        }
    }

    //---------------------------------------------------------------------
    @Override
    public AreaPrecio onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
    {
        AreaPrecio current = new AreaPrecio();

        for (int i = 1; i <= e.getColumnCount(); i++)
        {

            switch (e.getColumnName(i))
            {
                case "id":
                    current.setId(e.getInt(i));
                    break;
                case "descripcion":
                    current.setDescripcion(e.getString(i));
                    break;
                case "es_tipo":
                    current.setEsTipo(e.getInt(i));
                    break;
                case "es_costo_directo":
                    current.setCostoDirecto(e.getBoolean(i));
                    break;
                case "es_costo_variable":
                    current.setEsCostoVariable(e.getBoolean(i));
                    break;
                case "id_padre":
                    current.setIdPadre(e.getInt(i));
                    break;
                case "nivel":
                    current.setNivel(e.getInt(i));
                    break;
            }

        }

        current.setSearchOnlyByPrimaryKey(true);
        current.acceptChanges();

        return current;
    }
}
