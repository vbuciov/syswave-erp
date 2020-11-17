package com.syswave.datos.miempresa;

import com.syswave.datos.common.SingletonDataAccess;
import com.syswave.entidades.miempresa.Puesto;
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
 * @author sis5
 */
public class PuestosDataAccess extends SingletonDataAccess<Puesto>
{

    public final String insertProcedure = "puestos_insert(?,?,?)";
    public final String deleteProcedure = "puestos_delete";

    public PuestosDataAccess(IMediatorDataSource mysource)
    {
        super(mysource, "puestos", "id", "nombre", "id_padre", "nivel", "es_activo");
        setBasicOrderBy("nivel ASC", "id_padre ASC");
        setInsertProcedure(insertProcedure);
    }

    //---------------------------------------------------------------------
    @Override
    public int Delete(Puesto elemento)
    {
        //return Delete(nombreTabla, elemento, this);
        QueryDataTransfer parametros = new QueryDataTransfer(deleteProcedure);
        parametros.addInteger("vid", elemento.getId());
        int affectado = (int) executeFunction(parametros);

        if (affectado > 0)
            elemento.setDeleted();

        return affectado;
    }

    //---------------------------------------------------------------------
    @Override
    public int Delete(List<Puesto> elemento)
    {
        QueryDataTransfer parametros = new QueryDataTransfer(deleteProcedure);
        int affectado = 0, e;

        for (Puesto cada : elemento)
        {
            parametros.addInteger("vid", cada.getId());
            e = (int) executeFunction(parametros);
            affectado += e;
            parametros.clear();

            if (e > 0)
                cada.setDeleted();
        }

        return affectado;
    }

    //---------------------------------------------------------------------
    public List<Puesto> RetrieveLeafsPaths()
    {
        QueryDataTransfer parametros = new QueryDataTransfer("categoria_hoja_select");

        parametros.addInteger("vid", Puesto.EMPTY_INT);
        parametros.addString("vnombre", "");

        return executeReadStoredProcedure(parametros);
    }

    //---------------------------------------------------------------------
    public List<Puesto> RetrieveLeafsPaths(Puesto Filter)
    {
        QueryDataTransfer parametros = new QueryDataTransfer("categoria_hoja_select");

        parametros.addInteger("vid", Filter.getId());
        parametros.addString("vnombre", Filter.getNombre());

        return executeReadStoredProcedure(parametros);
    }

    //---------------------------------------------------------------------
    @Override
    public Puesto onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
    {
        Puesto current = new Puesto();

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

    //---------------------------------------------------------------------
    /**
     * InsertProcedure is using an executeUpdateStoredProcedure
     *
     * @throws java.sql.SQLException
     */
    @Override
    protected void onConvertTransfer(Puesto values, DataSetEvent e) throws SQLException, UnsupportedOperationException
    {
        if (e.getDML() == insertProcedure)
        {
            e.setString(1, values.getNombre(), 45);//"vnombre"
            e.setInt(2, values.getIdPadre());//"vid_padre"
            e.setBoolean(3, values.isActivo());//"ves_activo"
        }
    }

    //--------------------------------------------------------------------    
    @Override
    protected void onConvertKeyResult(DataGetEvent e, Puesto value) throws SQLException, UnsupportedOperationException
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
    @Override
    public void onSendValues(DataSendToQueryEvent e, Puesto values)
    {
         if (values.getId() != Puesto.EMPTY_INT)
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

        if (!values.getNombre().equals(Puesto.EMPTY_STRING))
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addString("nombre", values.getNombre());
            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addString("nombre", QueryParameter.Operator.EQUAL, values.getNombre());
        }

        if (values.getIdPadre() > 0) //Nota: El valor cero es puesto cuando un elemento tiene NULL como padre.
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addInteger("id_padre", values.getIdPadre());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addInteger("id_padre", QueryParameter.Operator.EQUAL, values.getIdPadre());
        }

        if (values.getNivel() != Puesto.EMPTY_INT)
        {
            /*Nota: El campo nivel nunca debe ser manipulado por la aplicación.
                 if (e.isCreateOperation() || e.isUpdateOperation())
                 e.addInteger("nivel", values.getNivel());

                 else*/
            if (!values.isSearchOnlyByPrimaryKey() && (e.isRetrieveOperation() || e.isDeleteOperation()))
                e.addInteger("nivel", QueryParameter.Operator.EQUAL, values.getNivel());
        }

        //Nota: Debido a la inexistencia de una reprentación de nulos en booleanos
        //Estos solo son trabajados cuando el objeto reporta cambios.
        if (values.isSet())
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addBoolean("es_activo", values.isActivo());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addBoolean("es_activo", QueryParameter.Operator.EQUAL, values.isActivo());
        }
    }
}