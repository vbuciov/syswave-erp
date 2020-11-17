package com.syswave.datos.miempresa;

import com.syswave.datos.common.SingletonDataAccess;
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
public class TipoPersonasDataAccess extends SingletonDataAccess<TipoPersona>
{

    public final String insertProcedure = "tipo_persona_insert(?,?,?,?,?,?)";
    public final String deleteProcedure = "tipo_persona_delete";

    public TipoPersonasDataAccess(IMediatorDataSource mysource)
    {
        super(mysource, "tipos_personas", "id", "nombre", "siglas", "id_padre", "nivel",
              "es_activo", "usa_mantenimiento", "usa_personal");
        setBasicOrderBy("nivel ASC", "id_padre ASC");
        setInsertProcedure(insertProcedure);
        setUpdateProcedure(updateProcedure);
    }

    //--------------------------------------------------------------------
    public int delete(List<TipoPersona> eliminados)
    {
        QueryDataTransfer parametros = new QueryDataTransfer(deleteProcedure);
        int affectado = 0, e;

        for (TipoPersona cada : eliminados)
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

    //--------------------------------------------------------------------
    public int delete(TipoPersona eliminado)
    {
        QueryDataTransfer parametros = new QueryDataTransfer(deleteProcedure);
        parametros.addInteger("vid", eliminado.getId());
        int affectado = (int) executeFunction(parametros);

        if (affectado > 0)
            eliminado.setDeleted();

        return affectado;
    }

    //---------------------------------------------------------------------
    /**
     * Devuelve todas las hojas de un árbol.
     */
    public List<TipoPersona> RetrieveLeafsPaths()
    {
        QueryDataTransfer parametros = new QueryDataTransfer("tipo_persona_hoja_select");

        parametros.addInteger("vid", TipoPersona.EMPTY_INT);
        parametros.addString("vnombre", "");

        return executeReadStoredProcedure(parametros);
    }

    //---------------------------------------------------------------------
    /**
     * Devuelve todas las hojas de un árbol que cumplan con los criterios.
     */
    public List<TipoPersona> RetrieveLeafsPaths(TipoPersona Filter)
    {
        QueryDataTransfer parametros = new QueryDataTransfer("tipo_persona_hoja_select");

        parametros.addInteger("vid", Filter.getId());
        parametros.addString("vnombre", Filter.getNombre());

        return executeReadStoredProcedure(parametros);
    }

    //--------------------------------------------------------------------
    /**
     * Devuelve todas las hojas correspondientes a una rama del árbol.
     */
    public List<TipoPersona> RetrieveLeafsBranchPaths(TipoPersona Filter)
    {
        QueryDataTransfer parametros = new QueryDataTransfer("tipo_persona_hoja_rama_select");

        parametros.addInteger("vid_base", Filter.getId());

        return executeReadStoredProcedure(parametros);
    }

    //--------------------------------------------------------------------
    @Override
    public TipoPersona onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
    {
        TipoPersona current = new TipoPersona();

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
                case "usa_mantenimiento":
                    current.setUsaMantenimiento(e.getBoolean(i));
                    break;
                case "usa_personal":
                    current.setUsaPersonal(e.getBoolean(i));
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
    protected void onConvertTransfer(TipoPersona values, DataSetEvent e) throws SQLException, UnsupportedOperationException
    {
        if (e.getDML() == insertProcedure)
        {
            e.setString(1, values.getNombre(), 45);//"vnombre"
            e.setString(2, values.getSiglas(), 45);//"vsiglas"
            e.setInt(3, values.getIdPadre());//"vid_padre"
            e.setBoolean(4, values.esActivo());//"ves_activo"
            e.setBoolean(5, values.esUsaMantenimiento());//"vusa_mantenimiento"
            e.setBoolean(6, values.esUsaPersonal());//"vusa_personal"
        }
    }

    //--------------------------------------------------------------------    
    @Override
    protected void onConvertKeyResult(DataGetEvent e, TipoPersona value) throws SQLException, UnsupportedOperationException
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
                case "usa_mantenimiento":
                    value.setUsaMantenimiento(e.getBoolean(i));
                    break;
                case "usa_personal":
                    value.setUsaPersonal(e.getBoolean(i));
                    break;
            }
        }
        /*  if (e.getColumnCount() > 0)
            value.setId(e.getInt(1));*/
    }

    //---------------------------------------------------------------------
    @Override
    public void onSendValues(DataSendToQueryEvent e, TipoPersona values)
    {
        if (e.isCreateOperation())
        {
            e.addInteger("id", values.getId());
            e.addString("nombre", values.getNombre(), 45);
            e.addString("siglas", values.getSiglas(), 45);
            if (values.getIdPadre() != TipoPersona.EMPTY_INT)
                e.addInteger("id_padre", values.getIdPadre());
            if (values.getNivel() != TipoPersona.EMPTY_INT)
                e.addInteger("nivel", values.getNivel());
            e.addBoolean("es_activo", values.esActivo());
            e.addBoolean("usa_mantenimiento", values.esUsaMantenimiento());
            e.addBoolean("usa_personal", values.esUsaPersonal());
        }

        else if (e.isUpdateOperation())
        {
            e.addString("nombre", values.getNombre(), 45);
            e.addString("siglas", values.getSiglas(), 45);
            if (values.getIdPadre() > 0) //El valor cero es puesto  para aquellos con NULL en el padre
                e.addInteger("id_padre", values.getIdPadre());
            /*if (values.getNivel()!= TipoPersona.EMPTY_INT)
                 e.addInteger("nivel", values.getNivel());*/
            e.addBoolean("es_activo", values.esActivo());
            e.addBoolean("usa_mantenimiento", values.esUsaMantenimiento());
            e.addBoolean("usa_personal", values.esUsaPersonal());

            //e.SendParameters.AddInteger ("fk_pers_id", values.Pers_Id)
            e.addInteger("id", QueryParameter.Operator.EQUAL, values.getId_Viejo());
        }

        else if (e.isRetrieveOperation() || e.isDeleteOperation())
        {
            if (values.getId() != TipoPersona.EMPTY_INT)
                e.addInteger("id", QueryParameter.Operator.EQUAL, values.getId());

            if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
            {
                if (!values.getNombre().equals(TipoPersona.EMPTY_STRING))
                    e.addString("nombre", QueryParameter.Operator.EQUAL, values.getNombre());

                if (!values.getSiglas().equals(TipoPersona.EMPTY_STRING))
                    e.addString("siglas", QueryParameter.Operator.EQUAL, values.getSiglas());

                if (values.getIdPadre() != TipoPersona.EMPTY_INT)
                    e.addInteger("id_padre", QueryParameter.Operator.EQUAL, values.getIdPadre());

                if (values.getNivel() != TipoPersona.EMPTY_INT)
                    e.addInteger("nivel", QueryParameter.Operator.EQUAL, values.getNivel());

                if (values.isSet())
                {
                    e.addBoolean("es_activo", QueryParameter.Operator.EQUAL, values.esActivo());
                    e.addBoolean("usa_mantenimiento", QueryParameter.Operator.EQUAL, values.esUsaMantenimiento());
                    e.addBoolean("usa_personal", QueryParameter.Operator.EQUAL, values.esUsaPersonal());
                }
            }
        }
    }
}
