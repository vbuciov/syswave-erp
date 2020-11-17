package com.syswave.datos.miempresa;

import com.syswave.datos.common.SingletonDataAccess;
import com.syswave.entidades.miempresa.Categoria;
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
public class CategoriasDataAccess extends SingletonDataAccess<Categoria>
{

    public final String insertProcedure = "categoria_insert(?,?,?,?)";
    public final String deleteProcedure = "categoria_delete";

    public CategoriasDataAccess(IMediatorDataSource mysource)
    {
        super(mysource, "categorias", "id", "nombre", "siglas", "id_padre", "nivel", "es_activo");
        setBasicOrderBy("nivel ASC", "id_padre ASC");
        setInsertProcedure(insertProcedure);
    }

    //---------------------------------------------------------------------
    @Override
    public int Delete(Categoria elemento)
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
    public int Delete(List<Categoria> elemento)
    {
        QueryDataTransfer parametros = new QueryDataTransfer(deleteProcedure);
        //parametros.setReturnType(java.sql.Types.INTEGER); The return type is Integer as Default
        int affectado = 0, resultado;

        for (Categoria cada : elemento)
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
    public List<Categoria> RetrieveLeafsPaths()
    {
        QueryDataTransfer parametros = new QueryDataTransfer("categoria_hoja_select");

        parametros.addInteger("vid", Categoria.EMPTY_INT);
        parametros.addString("vnombre", "");

        return executeReadStoredProcedure(parametros);
    }

    //---------------------------------------------------------------------
    public List<Categoria> RetrieveLeafsPaths(Categoria Filter)
    {
        QueryDataTransfer parametros = new QueryDataTransfer("categoria_hoja_select");

        parametros.addInteger("vid", Filter.getId());
        parametros.addString("vnombre", Filter.getNombre());

        return executeReadStoredProcedure(parametros);
    }

    //---------------------------------------------------------------------
    /**
     * InsertProcedure is using an executeUpdateStoredProcedure
     *
     * @throws java.sql.SQLException
     */
    @Override
    protected void onConvertTransfer(Categoria values, DataSetEvent e) throws SQLException, UnsupportedOperationException
    {
        if (e.getDML() == insertProcedure)
        {
            e.setString(1, values.getNombre(), 60); //"vnombre" (60)
            e.setString(2, values.getSiglas(), 5); //"vsiglas" (5)
            e.setInt(3, values.getIdPadre()); //"vid_padre" 
            e.setBoolean(4, values.esActivo()); //"ves_activo"
        }
    }

    //--------------------------------------------------------------------    
    @Override
    protected void onConvertKeyResult(DataGetEvent e, Categoria value) throws SQLException, UnsupportedOperationException
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
                    value.setEsActivo(e.getBoolean(i));
                    break;
            }
        }
        /*  if (e.getColumnCount() > 0)
            value.setId(e.getInt(1));*/
    }

    //---------------------------------------------------------------------
    @Override
    public void onSendValues(DataSendToQueryEvent e, Categoria argument)
    {

        if (argument.getId() != Categoria.EMPTY_INT)
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

        if (!argument.getNombre().equals(Categoria.EMPTY_STRING))
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addString("nombre", argument.getNombre());

            else if (!argument.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addString("nombre", QueryParameter.Operator.EQUAL, argument.getNombre());
        }

        if (!argument.getSiglas().equals(Categoria.EMPTY_STRING))
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

        if (argument.getNivel() != Categoria.EMPTY_INT)
        {
            /*Nota: El campo nivel nunca debe ser manipulado por la aplicación.
               if (e.isCreateOperation() || e.isUpdateOperation())
                    e.addInteger("nivel", argument.getNivel());

                else*/
            if (!argument.isSearchOnlyByPrimaryKey() && (e.isRetrieveOperation() || e.isDeleteOperation()))
                e.addInteger("nivel", QueryParameter.Operator.EQUAL, argument.getNivel());
        }

        //Nota: Debido a la inexistencia de una reprentación de nulos en booleanos
        //Estos solo son trabajados cuando el objeto reporta cambios.
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
    public Categoria onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
    {
        Categoria current = new Categoria();

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
                    current.setEsActivo(e.getBoolean(i));
                    break;
            }
        }

        current.setSearchOnlyByPrimaryKey(true);
        current.acceptChanges();

        return current;
    }
}
