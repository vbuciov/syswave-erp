package com.syswave.datos.configuracion;

import com.syswave.datos.common.SingletonDataAccess;
import com.syswave.entidades.configuracion.Unidad;
import datalayer.api.IMediatorDataSource;
import datalayer.utils.QueryParameter;
import datalayer.events.DataGetEvent;
import datalayer.events.DataSendToQueryEvent;
import datalayer.events.DataSetEvent;
import datalayer.utils.QueryDataTransfer;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class UnidadesDataAccess extends SingletonDataAccess<Unidad>
{

    public static final String insertProcedure = "unidad_insert(?,?,?,?,?,?)";
    //private final String insertProcedure = "unidad_insert";
    public final String deleteProcedure = "localidad_delete";

    //---------------------------------------------------------------------
    public UnidadesDataAccess(IMediatorDataSource mysource)
    {
        super(mysource, "unidades", "id", "nombre", "escala", "id_padre", "nivel",
              "es_activo", "es_tipo", "abreviatura");
        setBasicOrderBy("nivel ASC", "id_padre ASC");
        setInsertProcedure(insertProcedure);
    }

    //---------------------------------------------------------------------
    @Override
    public int Delete(Unidad elemento)
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
    public int Delete(List<Unidad> elemento)
    {
        QueryDataTransfer parametros = new QueryDataTransfer(deleteProcedure);
        //parametros.setReturnType(java.sql.Types.INTEGER); The return type is Integer as Default
        int affectado = 0, resultado;

        for (Unidad cada : elemento)
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
    
    //--------------------------------------------------------------------    
    @Override
    protected void onConvertKeyResult(DataGetEvent e, Unidad value) throws SQLException, UnsupportedOperationException
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
                case "escala":
                    value.setEscala(e.getInt(i));
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
                case "es_tipo":
                    value.setEsTipo(e.getInt(i));
                    break;
                case "abreviatura":
                    value.setAbreviatura(e.getString(i));
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
    protected void onConvertTransfer(Unidad values, DataSetEvent e) throws SQLException, UnsupportedOperationException
    {
        if (e.getDML() == insertProcedure)
        {
            e.setString(1, values.getNombre(), 45); //"vnombre"
            e.setInt(2, values.getEscala()); //"vescala"
            e.setInt(3, values.getIdPadre()); //"vid_padre"
            e.setBoolean(4, values.esActivo()); //"ves_activo"
            e.setInt(5, values.getEsTipo()); //"ves_tipo"
            e.setString(6, values.getAbreviatura(), 10); //"vabreviatura"
        }
    }

    //--------------------------------------------------------------------
    @Override
    public Unidad onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
    {
        Unidad current = new Unidad();

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
                case "escala":
                    current.setEscala(e.getInt(i));
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
                case "es_tipo":
                    current.setEsTipo(e.getInt(i));
                    break;
                case "abreviatura":
                    current.setAbreviatura(e.getString(i));
                    break;
            }
        }

        current.setSearchOnlyByPrimaryKey(true);
        current.acceptChanges();

        return current;

    }

    //--------------------------------------------------------------------
    @Override
    public void onSendValues(DataSendToQueryEvent e, Unidad argument)
    {
        if (e.isCreateOperation())
        {
            e.addInteger("id", argument.getId());
            e.addString("nombre", argument.getNombre(), 45);
            e.addInteger("escala", argument.getEscala());
            e.addInteger("id_padre", argument.getIdPadre());
            e.addInteger("nivel", argument.getIdPadre());
            e.addBoolean("es_activo", argument.esActivo());
            e.addInteger("es_tipo", argument.getEsTipo());
            e.addString("abreviatura", argument.getNombre(), 10);
        }

        else if (e.isUpdateOperation())
        {
            e.addString("nombre", argument.getNombre(), 45);
            e.addInteger("escala", argument.getEscala());
            e.addBoolean("es_activo", argument.esActivo());
            e.addInteger("es_tipo", argument.getEsTipo());
            e.addString("abreviatura", argument.getNombre(), 10);

            e.addInteger("id", QueryParameter.Operator.EQUAL, argument.getId_Viejo());
        }

        else if (e.isRetrieveOperation() || e.isDeleteOperation())
        {
            if (argument.getId() != Unidad.EMPTY_INT)
                e.addInteger("id", QueryParameter.Operator.EQUAL, argument.getId_Viejo());

            if (!argument.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
            {
                if (!argument.getNombre().equals(Unidad.EMPTY_STRING))
                    e.addString("nombre", QueryParameter.Operator.EQUAL, argument.getNombre());

                if (argument.getEscala() != Unidad.EMPTY_INT)
                    e.addInteger("escala", QueryParameter.Operator.EQUAL, argument.getEscala());

                if (argument.getIdPadre() != Unidad.EMPTY_INT)
                    e.addInteger("id_padre", QueryParameter.Operator.EQUAL, argument.getIdPadre());

                if (argument.getNivel() != Unidad.EMPTY_INT)
                    e.addInteger("nivel", QueryParameter.Operator.EQUAL, argument.getNivel());

                if (!argument.isSet())
                    e.addBoolean("es_activo", QueryParameter.Operator.EQUAL, argument.esActivo());

                if (argument.getEsTipo() != Unidad.EMPTY_INT)
                    e.addInteger("es_tipo", QueryParameter.Operator.EQUAL, argument.getEsTipo());

                if (!argument.getAbreviatura().equals(Unidad.EMPTY_STRING))
                    e.addString("abreviatura", QueryParameter.Operator.EQUAL, argument.getAbreviatura());
            }
        }
    }
}