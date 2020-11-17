package com.syswave.datos.miempresa;

import com.syswave.datos.common.SingletonDataAccess;
import com.syswave.entidades.miempresa.TipoComprobante;
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
public class TiposComprobantesDataAccess extends SingletonDataAccess<TipoComprobante>
{

    final private String insertProcedure = "tipo_comprobante_insert(?,?,?,?,?,?,?,?,?,?)";
    final private String deleteProcedure = "tipo_comprobante_delete";

    public TiposComprobantesDataAccess(IMediatorDataSource mysource)
    {
        super(mysource, "tipos_comprobantes", "id", "nombre", "afecta_inventario",
              "es_entrada", "es_activo", "permite_precios", "condicion_pago",
              "afecta_saldo", "es_tipo_saldo", "es_comercial", "id_padre",
              "nivel");
        setBasicOrderBy("nivel ASC", "id_padre ASC");
        setInsertProcedure(insertProcedure);
        setUpdateProcedure(updateProcedure);
    }

    //---------------------------------------------------------------------
    @Override
    public int Delete(TipoComprobante elemento)
    {
        QueryDataTransfer parametros = new QueryDataTransfer(deleteProcedure);
        parametros.addInteger("vid", elemento.getId());
        int affectado = (int) executeFunction(parametros);

        if (affectado > 0)
            elemento.setDeleted();

        return affectado;
    }

    //---------------------------------------------------------------------
    @Override
    public int Delete(List<TipoComprobante> elemento)
    {
        QueryDataTransfer parametros = new QueryDataTransfer(deleteProcedure);
        int affectado = 0, e;

        for (TipoComprobante cada : elemento)
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
    public List<TipoComprobante> RetrieveLeafsPaths()
    {
        QueryDataTransfer parametros = new QueryDataTransfer("tipo_comprobante_hoja_select");

        parametros.addInteger("vid", TipoComprobante.EMPTY_INT);
        parametros.addString("vnombre", "");
        parametros.addBoolean("vusa_modulo_inventario", false);
        parametros.addBoolean("vafecta_inventario", false);
        parametros.addInteger("vpermite_precios", TipoComprobante.EMPTY_INT);
        parametros.addBoolean("vusa_modulo_cuentas", false);
        parametros.addInteger("vcondicion_pago", TipoComprobante.EMPTY_INT);
        parametros.addBoolean("vafecta_saldo", false);
        parametros.addBoolean("ves_tipo_saldo", false);
        parametros.addBoolean("vusa_modulo_pagos", false);
        parametros.addBoolean("ves_comercial", false);
        parametros.addInteger("vid_padre", TipoComprobante.EMPTY_INT);

        return executeReadStoredProcedure(parametros);
    }

    //---------------------------------------------------------------------
    public List<TipoComprobante> RetrieveLeafsPaths(TipoComprobante Filter, boolean usa_modulo_inventario, boolean usa_modulo_cuentas, boolean usa_modulo_pagos)
    {
        QueryDataTransfer parametros = new QueryDataTransfer("tipo_comprobante_hoja_select");

        parametros.addInteger("vid", Filter.getId());
        parametros.addString("vnombre", Filter.getNombre());
        parametros.addBoolean("vusa_modulo_inventario", usa_modulo_inventario);
        parametros.addBoolean("vafecta_inventario", Filter.esAfecta_inventario());
        parametros.addInteger("vpermite_precios", Filter.getPermitePrecios());
        parametros.addBoolean("vusa_modulo_cuentas", usa_modulo_cuentas);
        parametros.addInteger("vcondicion_pago", Filter.getCondicionPago());
        parametros.addBoolean("vafecta_saldo", Filter.esAfectaSaldos());
        parametros.addBoolean("ves_tipo_saldo", Filter.esTipoSaldo());
        parametros.addBoolean("vusa_modulo_pagos", usa_modulo_pagos);
        parametros.addBoolean("ves_comercial", Filter.esComercial());
        parametros.addInteger("vid_padre", Filter.getIdPadre());

        return executeReadStoredProcedure(parametros);
    }

    //---------------------------------------------------------------------
    @Override
    public TipoComprobante onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
    {
        TipoComprobante current = new TipoComprobante();

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
                case "afecta_inventario":
                    current.setAfecta_inventario(e.getBoolean(i));
                    break;
                case "es_entrada":
                    current.setEntrada(e.getBoolean(i));
                    break;
                case "es_activo":
                    current.setActivo(e.getBoolean(i));
                    break;
                case "permite_precios":
                    current.setPermitePrecios(e.getInt(i));
                    break;
                case "condicion_pago":
                    current.setCondicion_pago(e.getInt(i));
                    break;
                case "afecta_saldo":
                    current.setAfectaSaldos(e.getBoolean(i));
                    break;
                case "es_tipo_saldo":
                    current.setTipoSaldo(e.getBoolean(i));
                    break;
                case "es_comercial":
                    current.setComercial(e.getBoolean(i));
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

    //---------------------------------------------------------------------
    /**
     * InsertProcedure is using an executeUpdateStoredProcedure
     *
     * @throws java.sql.SQLException
     */
    @Override
    protected void onConvertTransfer(TipoComprobante values, DataSetEvent e) throws SQLException, UnsupportedOperationException
    {
        if (e.getDML() == insertProcedure)
        {
            e.setString(1, values.getNombre(), 45);//"vnombre"
            e.setBoolean(2, values.esAfecta_inventario());//"vafecta_inventario"
            e.setBoolean(3, values.esEntrada());//"ves_entrada"
            e.setBoolean(4, values.esActivo());//"ves_activo"
            e.setInt(5, values.getPermitePrecios());//"vpermite_precios"
            e.setInt(6, values.getCondicionPago());//"vcondicion_pago"
            e.setBoolean(7, values.esAfectaSaldos());//"vafecta_saldos"
            e.setBoolean(8, values.esTipoSaldo());//"ves_tipo_saldo"
            e.setBoolean(9, values.esComercial());//"ves_comercial"
            e.setInt(10, values.getIdPadre());//"vid_padre"
        }

    }

    //--------------------------------------------------------------------    
    @Override
    protected void onConvertKeyResult(DataGetEvent e, TipoComprobante value) throws SQLException, UnsupportedOperationException
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
                case "afecta_inventario":
                    value.setAfecta_inventario(e.getBoolean(i));
                    break;
                case "es_entrada":
                    value.setEntrada(e.getBoolean(i));
                    break;
                case "es_activo":
                    value.setActivo(e.getBoolean(i));
                    break;
                case "permite_precios":
                    value.setPermitePrecios(e.getInt(i));
                    break;
                case "condicion_pago":
                    value.setCondicion_pago(e.getInt(i));
                    break;
                case "afecta_saldo":
                    value.setAfectaSaldos(e.getBoolean(i));
                    break;
                case "es_tipo_saldo":
                    value.setTipoSaldo(e.getBoolean(i));
                    break;
                case "es_comercial":
                    value.setComercial(e.getBoolean(i));
                    break;
                case "id_padre":
                    value.setIdPadre(e.getInt(i));
                    break;
                case "nivel":
                    value.setNivel(e.getInt(i));
                    break;
            }
        }
        /*  if (e.getColumnCount() > 0)
            value.setId(e.getInt(1));*/
    }

    //---------------------------------------------------------------------
    @Override
    public void onSendValues(DataSendToQueryEvent e, TipoComprobante values)
    {
        if (values.getId() != TipoComprobante.EMPTY_INT)
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

        if (!values.getNombre().equals(TipoComprobante.EMPTY_STRING))
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

        if (values.getNivel() != TipoComprobante.EMPTY_INT)
        {
            /*Nota: El campo nivel nunca debe ser manipulado por la aplicación.
               if (e.isCreateOperation() || e.isUpdateOperation())
                    e.addInteger("nivel", values.getNivel());

                else*/
            if (!values.isSearchOnlyByPrimaryKey() && (e.isRetrieveOperation() || e.isDeleteOperation()))
                e.addInteger("nivel", QueryParameter.Operator.EQUAL, values.getNivel());
        }

        if (values.getPermitePrecios() != TipoComprobante.EMPTY_INT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addInteger("permite_precios", values.getPermitePrecios());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addInteger("permite_precios", QueryParameter.Operator.EQUAL, values.getPermitePrecios());
        }

        if (values.getCondicionPago() != TipoComprobante.EMPTY_INT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addInteger("condicion_pago", values.getCondicionPago());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addInteger("condicion_pago", QueryParameter.Operator.EQUAL, values.getCondicionPago());
        }

        if (values.isSet())
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
            {
                e.addBoolean("afecta_inventario", values.esAfecta_inventario());
                e.addBoolean("es_entrada", values.esEntrada());
                e.addBoolean("es_activo", values.esActivo());
                e.addBoolean("afecta_saldo", values.esAfectaSaldos());
                e.addBoolean("es_tipo_saldo", values.esTipoSaldo());
                e.addBoolean("es_comercial", values.esComercial());
            }

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
            {
                e.addBoolean("afecta_inventario", QueryParameter.Operator.EQUAL, values.esAfecta_inventario());
                e.addBoolean("es_entrada", QueryParameter.Operator.EQUAL, values.esEntrada());
                e.addBoolean("es_activo", QueryParameter.Operator.EQUAL, values.esActivo());
                e.addBoolean("afecta_saldo", QueryParameter.Operator.EQUAL, values.esAfectaSaldos());
                e.addBoolean("es_tipo_saldo", QueryParameter.Operator.EQUAL, values.esTipoSaldo());
                e.addBoolean("es_comercial", QueryParameter.Operator.EQUAL, values.esComercial());
            }
        }
    }
}
