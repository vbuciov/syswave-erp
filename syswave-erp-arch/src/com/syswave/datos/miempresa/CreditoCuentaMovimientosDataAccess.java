package com.syswave.datos.miempresa;

import com.syswave.datos.common.SingletonDataAccess;
import com.syswave.entidades.miempresa.CreditoCuentaMovimiento;
import datalayer.api.IMediatorDataSource;
import datalayer.events.DataGetEvent;
import datalayer.events.DataSendToQueryEvent;
import datalayer.events.DataSetEvent;
import datalayer.utils.QueryParameter;
import java.sql.SQLException;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class CreditoCuentaMovimientosDataAccess extends SingletonDataAccess<CreditoCuentaMovimiento>
{

    public final String insertProcedure = "credito_cuenta_movimiento_insert(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    public final String updateProcedure = "credito_cuenta_movimiento_update(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    public CreditoCuentaMovimientosDataAccess(IMediatorDataSource mysource)
    {
        super(mysource, "credito_cuenta_movimientos", "movimiento", "consecutivo", "id_persona",
              "fecha_elaboracion", "id_tipo_comprobante", "monto", "concepto",
              "referencia", "plazo_dado", "unidad", "es_letra", "tasa_interes",
              "monto_minimo", "factor_minimo", "saldo_anterior", "saldo_aplicado",
              "es_origen");
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
    protected void onConvertTransfer(CreditoCuentaMovimiento values, DataSetEvent e) throws SQLException, UnsupportedOperationException
    {
        if (e.getDML() == insertProcedure)
        {
            e.setInt(1, values.getConsecutivo());//"vconsecutivo"
            e.setInt(2, values.getIdPersona());//"vid_persona"
            e.setDate(3, values.getFechaElaboracion());//"vfecha_elaboracion"
            e.setInt(4, values.getIdTipoComprobante());//"vid_tipo_comprobante"
            e.setFloat(5, values.getMonto());//"vmonto"
            e.setString(6, values.getConcepto(), 250);//"vconcepto"
            e.setString(7, values.getReferencia(), 20);//"vreferencia"
            e.setInt(8, values.getPlazoDado());//"vplazo_dado"
            e.setInt(9, values.getUnidad());//"vunidad"
            e.setBoolean(10, values.esLetra());//"ves_letra"
            e.setInt(11, values.getTasaInteres());//"vtasa_interes"
            e.setFloat(12, values.getMontoMinimo());//"vmonto_minimo"
            e.setInt(13, values.getFactorMinimo());//"vfactor_minimo"
            e.setFloat(14, values.getSaldoAnterior());//"vsaldo_anterior"
            e.setFloat(15, values.getSaldoAplicado());//"vsaldo_aplicado"
            e.setInt(16, values.getEsOrigen());//"ves_origen"
        }

        else if (e.getDML() == updateProcedure)
        {
            e.setInt(1, values.getMovimiento_Viejo());//"vmovimiento_old"
            e.setInt(2, values.getConsecutivo_Viejo());//"vvconsecutivo_old"
            e.setInt(3, values.getIdPersona_Viejo());//"vid_persona_old"
            e.setInt(4, values.getConsecutivo());//"vvconsecutivo_new"
            e.setInt(5, values.getIdPersona());//"vid_persona_new"
            e.setDate(6, values.getFechaElaboracion());//"vfecha_elaboracion"
            e.setInt(7, values.getIdTipoComprobante());//"vid_tipo_comprobante"
            e.setFloat(8, values.getMonto());//"vmonto"
            e.setString(9, values.getConcepto(), 250);//"vconcepto"
            e.setString(10, values.getReferencia(), 20);//"vreferencia"
            e.setInt(11, values.getPlazoDado());//"vplazo_dado"
            e.setInt(12, values.getUnidad());//"vunidad"
            e.setBoolean(13, values.esLetra());//"ves_letra"
            e.setInt(14, values.getTasaInteres());//"vtasa_interes"
            e.setFloat(15, values.getMontoMinimo());//"vmonto_minimo"
            e.setInt(16, values.getFactorMinimo());//"vfactor_minimo"
            e.setFloat(17, values.getSaldoAnterior());//"vsaldo_anterior"
            e.setFloat(18, values.getSaldoAplicado());//"vsaldo_aplicado"
            e.setInt(19, values.getEsOrigen());//"ves_origen"
        }
    }

    //--------------------------------------------------------------------    
    @Override
    protected void onConvertKeyResult(DataGetEvent e, CreditoCuentaMovimiento value) throws SQLException, UnsupportedOperationException
    {
        for (int i = 1; i <= e.getColumnCount(); i++)
        {
            switch (e.getColumnName(i))
            {
                case "movimiento":
                    value.setMovimiento(e.getInt(i));
                    break;
                case "consecutivo":
                    value.setConsecutivo(e.getInt(i));
                    break;
                case "id_persona":
                    value.setIdPersona(e.getInt(i));
                    break;
                case "fecha_elaboracion":
                    value.setFechaElaboracion(e.getDate(i));
                    break;
                case "id_tipo_comprobante":
                    value.setIdTipoComprobante(e.getInt(i));
                    break;
                case "monto":
                    value.setMonto(e.getFloat(i));
                    break;
                case "concepto":
                    value.setConcepto(e.getString(i));
                    break;
                case "referencia":
                    value.setReferencia(e.getString(i));
                    break;
                case "plazo_dado":
                    value.setPlazoDado(e.getInt(i));
                    break;
                case "unidad":
                    value.setUnidad(e.getInt(i));
                    break;
                case "es_letra":
                    value.setEsletra(e.getBoolean(i));
                    break;
                case "tasa_interes":
                    value.setTasa_interes(e.getInt(i));
                    break;
                case "monto_minimo":
                    value.setMontoMinimo(e.getFloat(i));
                    break;
                case "factor_minimo":
                    value.setFactorMinimo(e.getInt(i));
                    break;
                case "saldo_anterior":
                    value.setSaldoAnterior(e.getFloat(i));
                    break;
                case "saldo_aplicado":
                    value.setSaldoAplicado(e.getFloat(i));
                    break;
                case "es_origen":
                    value.setEsOrigen(e.getInt(i));
                    break;
            }
        }
        /*  if (e.getColumnCount() > 0)
            value.setId(e.getInt(1));*/
    }

    //---------------------------------------------------------------------
    @Override
    public void onSendValues(DataSendToQueryEvent e, CreditoCuentaMovimiento values)
    {
        if (values.getMovimiento() != CreditoCuentaMovimiento.EMPTY_INT)
        {
            if (e.isCreateOperation())
                e.addInteger("movimiento", values.getMovimiento());

            else if (e.isUpdateOperation())
            {
                e.addInteger("movimiento", values.getMovimiento());
                e.addInteger("movimiento", QueryParameter.Operator.EQUAL, values.getMovimiento_Viejo());
            }

            else
                e.addInteger("movimiento", QueryParameter.Operator.EQUAL, values.getMovimiento_Viejo());
        }

        if (values.getConsecutivo() != CreditoCuentaMovimiento.EMPTY_INT)
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

        if (values.getIdPersona() != CreditoCuentaMovimiento.EMPTY_INT)
        {
            if (e.isCreateOperation())
                e.addInteger("id_persona", values.getIdPersona());

            else if (e.isUpdateOperation())
            {
                e.addInteger("id_persona", values.getIdPersona());
                e.addInteger("id_persona", QueryParameter.Operator.EQUAL, values.getIdPersona_Viejo());
            }

            else
                e.addInteger("id_persona", QueryParameter.Operator.EQUAL, values.getIdPersona_Viejo());
        }

        if (values.getFechaElaboracion() != CreditoCuentaMovimiento.EMPTY_DATE)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addDate("fecha_elaboracion", values.getFechaElaboracion());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addDate("fecha_elaboracion", QueryParameter.Operator.EQUAL, values.getFechaElaboracion());
        }

        if (values.getIdTipoComprobante() != CreditoCuentaMovimiento.EMPTY_INT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addInteger("id_tipo_comprobante", values.getIdTipoComprobante());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addInteger("id_tipo_comprobante", QueryParameter.Operator.EQUAL, values.getIdTipoComprobante());
        }

        if (values.getMonto() != CreditoCuentaMovimiento.EMPTY_FLOAT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addFloat("monto", values.getMonto());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addFloat("monto", QueryParameter.Operator.EQUAL, values.getMonto());
        }

        if (!values.getConcepto().equals(CreditoCuentaMovimiento.EMPTY_STRING))
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addString("concepto", values.getConcepto());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addString("concepto", QueryParameter.Operator.EQUAL, values.getConcepto());
        }

        if (!values.getReferencia().equals(CreditoCuentaMovimiento.EMPTY_STRING))
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addString("referencia", values.getReferencia());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addString("referencia", QueryParameter.Operator.EQUAL, values.getReferencia());
        }

        if (values.getPlazoDado() != CreditoCuentaMovimiento.EMPTY_INT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addInteger("plazo_dado", values.getPlazoDado());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addInteger("plazo_dado", QueryParameter.Operator.EQUAL, values.getPlazoDado());
        }

        if (values.getUnidad() != CreditoCuentaMovimiento.EMPTY_INT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addInteger("unidad", values.getUnidad());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addInteger("unidad", QueryParameter.Operator.EQUAL, values.getUnidad());
        }

        if (values.isSet())
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addBoolean("es_letra", values.esLetra());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addBoolean("es_letra", QueryParameter.Operator.EQUAL, values.esLetra());
        }

        if (values.getTasaInteres() != CreditoCuentaMovimiento.EMPTY_FLOAT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addFloat("tasa_interes", values.getTasaInteres());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addFloat("tasa_interes", QueryParameter.Operator.EQUAL, values.getTasaInteres());
        }

        if (values.getMontoMinimo() != CreditoCuentaMovimiento.EMPTY_FLOAT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addFloat("monto_minimo", values.getMontoMinimo());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addFloat("monto_minimo", QueryParameter.Operator.EQUAL, values.getMontoMinimo());
        }

        if (values.getFactorMinimo() != CreditoCuentaMovimiento.EMPTY_INT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addInteger("factor_minimo", values.getFactorMinimo());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addInteger("factor_minimo", QueryParameter.Operator.EQUAL, values.getFactorMinimo());
        }

        if (values.getSaldoAnterior() != CreditoCuentaMovimiento.EMPTY_FLOAT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addFloat("saldo_anterior", values.getSaldoAnterior());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addFloat("saldo_anterior", QueryParameter.Operator.EQUAL, values.getSaldoAnterior());
        }

        if (values.getSaldoAplicado() != CreditoCuentaMovimiento.EMPTY_FLOAT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addFloat("saldo_aplicado", values.getSaldoAplicado());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addFloat("saldo_aplicado", QueryParameter.Operator.EQUAL, values.getSaldoAplicado());
        }

        if (values.getEsOrigen() != CreditoCuentaMovimiento.EMPTY_FLOAT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addFloat("es_origen", values.getEsOrigen());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addFloat("es_origen", QueryParameter.Operator.EQUAL, values.getEsOrigen());
        }
    }

    //---------------------------------------------------------------------
    @Override
    public CreditoCuentaMovimiento onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
    {
        CreditoCuentaMovimiento current = new CreditoCuentaMovimiento();

        for (int i = 1; i <= e.getColumnCount(); i++)
        {
            switch (e.getColumnName(i))
            {
                case "movimiento":
                    current.setMovimiento(e.getInt(i));
                    break;
                case "consecutivo":
                    current.setConsecutivo(e.getInt(i));
                    break;
                case "id_persona":
                    current.setIdPersona(e.getInt(i));
                    break;
                case "fecha_elaboracion":
                    current.setFechaElaboracion(e.getDate(i));
                    break;
                case "id_tipo_comprobante":
                    current.setIdTipoComprobante(e.getInt(i));
                    break;
                case "monto":
                    current.setMonto(e.getFloat(i));
                    break;
                case "concepto":
                    current.setConcepto(e.getString(i));
                    break;
                case "referencia":
                    current.setReferencia(e.getString(i));
                    break;
                case "plazo_dado":
                    current.setPlazoDado(e.getInt(i));
                    break;
                case "unidad":
                    current.setUnidad(e.getInt(i));
                    break;
                case "es_letra":
                    current.setEsletra(e.getBoolean(i));
                    break;
                case "tasa_interes":
                    current.setTasa_interes(e.getInt(i));
                    break;
                case "monto_minimo":
                    current.setMontoMinimo(e.getFloat(i));
                    break;
                case "factor_minimo":
                    current.setFactorMinimo(e.getInt(i));
                    break;
                case "saldo_anterior":
                    current.setSaldoAnterior(e.getFloat(i));
                    break;
                case "saldo_aplicado":
                    current.setSaldoAplicado(e.getFloat(i));
                    break;
                case "es_origen":
                    current.setEsOrigen(e.getInt(i));
                    break;
            }
        }

        current.setSearchOnlyByPrimaryKey(true);
        current.acceptChanges();

        return current;
    }
}