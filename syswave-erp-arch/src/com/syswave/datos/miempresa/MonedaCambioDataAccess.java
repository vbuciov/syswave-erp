package com.syswave.datos.miempresa;

import com.syswave.datos.common.SingletonDataAccess;
import com.syswave.entidades.miempresa.MonedaCambio;
import datalayer.api.IMediatorDataSource;
import datalayer.events.DataGetEvent;
import datalayer.events.DataSendToQueryEvent;
import datalayer.events.DataSetEvent;
import datalayer.utils.QueryParameter;
import java.sql.SQLException;

/**
 *
 * @author Victor Manuel Bucio Varags
 */
public class MonedaCambioDataAccess extends SingletonDataAccess<MonedaCambio>
{

    public final String insertProcedure = "moneda_cambio_insert(?,?,?)";
    public final String updateProcedure = "moneda_cambio_update(?,?,?,?,?,?,?)";

    public MonedaCambioDataAccess(IMediatorDataSource mysource)
    {
        super(mysource, "moneda_cambio", "consecutivo", "id_moneda_origen",
              "proporcion", "id_moneda_destino", "fecha_validez");
        setInsertProcedure(insertProcedure);
        setUpdateProcedure(updateProcedure);
    }

    //---------------------------------------------------------------------
    /**
     * InsertProcedure is using an executeUpdateStoredProcedure
     *
     * @throws java.sql.SQLException
     */
    @Override
    protected void onConvertTransfer(MonedaCambio values, DataSetEvent e) throws SQLException, UnsupportedOperationException
    {
        if (e.getDML() == insertProcedure)
        {
            e.setInt(1, values.getIdMonedaOrigen());//"vid_moneda_origen"
            e.setFloat(2, values.getProporcion());//"vproporcion"
            e.setInt(3, values.getIdMonedaDestino());//"vid_moneda_destino"
        }

        else if (e.getDML() == updateProcedure)
        {
            e.setInt(1, values.getConsecutivo_Viejo());//"vconsecutivo_old"
            e.setInt(2, values.getIdMonedaOrigen_Viejo());//"vid_moneda_origen_old"
            e.setInt(3, values.getIdMonedaOrigen());//"vid_moneda_origen_new"
            e.setFloat(4, values.getProporcion());//"vproporcion"
            e.setInt(5, values.getIdMonedaDestino_Viejo());//"vid_moneda_destino_old"
            e.setInt(6, values.getIdMonedaDestino());//"vid_moneda_destino_new"
            e.setDate(7, values.getFecha_validez());//"vfecha_validez"
        }
    }

    //--------------------------------------------------------------------    
    @Override
    protected void onConvertKeyResult(DataGetEvent e, MonedaCambio value) throws SQLException, UnsupportedOperationException
    {
        for (int i = 1; i <= e.getColumnCount(); i++)
        {
            switch (e.getColumnName(i))
            {
                case "consecutivo":
                    value.setConsecutivo(e.getInt(i));
                    break;
                case "id_moneda_origen":
                    value.setIdMonedaOrigen(e.getInt(i));
                    break;
                case "proporcion":
                    value.setProporcion(e.getFloat(i));
                    break;
                case "id_moneda_destino":
                    value.setIdMonedaDestino(e.getInt(i));
                    break;
                case "fecha_validez":
                    value.setFecha_validez(e.getDate(i));
                    break;
            }
        }
        /*  if (e.getColumnCount() > 0)
            value.setId(e.getInt(1));*/
    }

    //---------------------------------------------------------------------
    @Override
    public void onSendValues(DataSendToQueryEvent e, MonedaCambio values)
    {
        if (values.getConsecutivo()!= MonedaCambio.EMPTY_INT)
        {

            if (e.isCreateOperation())
                e.addInteger("consecutivo", values.getConsecutivo());

            else if (e.isUpdateOperation())
            {
                e.addInteger("consecutivo", values.getConsecutivo());
                e.addInteger("consecutivo", QueryParameter.Operator.EQUAL, values.getConsecutivo_Viejo());
            }

            else
                e.addInteger("consecutivo", QueryParameter.Operator.EQUAL, values.getConsecutivo_Viejo());
        }

        if (values.getIdMonedaOrigen()!= MonedaCambio.EMPTY_INT)
        {

            if (e.isCreateOperation())
                e.addInteger("id_moneda_origen", values.getIdMonedaOrigen());

            else if (e.isUpdateOperation())
            {
                e.addInteger("id_moneda_origen", values.getIdMonedaOrigen());
                e.addInteger("id_moneda_origen", QueryParameter.Operator.EQUAL, values.getIdMonedaOrigen_Viejo());
            }

            else
                e.addInteger("id_moneda_origen", QueryParameter.Operator.EQUAL, values.getIdMonedaOrigen_Viejo());
        }

        if (values.getProporcion() != MonedaCambio.EMPTY_FLOAT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addFloat("proporcion", values.getProporcion());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addFloat("proporcion", QueryParameter.Operator.EQUAL, values.getProporcion());
        }

        if (values.getIdMonedaDestino() != MonedaCambio.EMPTY_INT)
        {
            if (e.isCreateOperation())
                e.addInteger("id_moneda_destino", values.getIdMonedaDestino());

            else if (e.isUpdateOperation())
            {
                e.addInteger("id_moneda_destino", values.getIdMonedaDestino());
                e.addInteger("id_moneda_destino", QueryParameter.Operator.EQUAL, values.getIdMonedaDestino_Viejo());
            }

            else
                e.addInteger("id_moneda_destino", QueryParameter.Operator.EQUAL, values.getIdMonedaDestino_Viejo());
        }

        if (values.getFecha_validez() != MonedaCambio.EMPTY_DATE)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addDate("fecha_validez", values.getFecha_validez());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addDate("fecha_validez", QueryParameter.Operator.EQUAL, values.getFecha_validez());
        }
    }

    //---------------------------------------------------------------------
    @Override
    public MonedaCambio onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
    {
        MonedaCambio value = new MonedaCambio();

        for (int i = 1; i <= e.getColumnCount(); i++)
        {
            switch (e.getColumnName(i))
            {
                case "consecutivo":
                    value.setConsecutivo(e.getInt(i));
                    break;
                case "id_moneda_origen":
                    value.setIdMonedaOrigen(e.getInt(i));
                    break;
                case "proporcion":
                    value.setProporcion(e.getFloat(i));
                    break;
                case "id_moneda_destino":
                    value.setIdMonedaDestino(e.getInt(i));
                    break;
                case "fecha_validez":
                    value.setFecha_validez(e.getDate(i));
                    break;
            }
        }

        value.setSearchOnlyByPrimaryKey(true);
        value.acceptChanges();

        return value;
    }
}
