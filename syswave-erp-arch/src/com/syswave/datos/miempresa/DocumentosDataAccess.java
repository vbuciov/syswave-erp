package com.syswave.datos.miempresa;

import com.syswave.datos.common.SingletonDataAccess;
import com.syswave.entidades.miempresa.Documento;
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
public class DocumentosDataAccess extends SingletonDataAccess<Documento>
{

    private final String insertProcedure = "documento_insert(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    public DocumentosDataAccess(IMediatorDataSource mysource)
    {
        super(mysource, "documentos", "id", "folio", "serie", "fecha_elaboracion",
              "fecha_vigencia", "es_activo", "subtotal", "factor", "monto",
              "total", "id_estatus", "id_tipo_comprobante", "es_aplicado",
              "id_moneda", "pagado", "saldo_actual");
        setInsertProcedure(insertProcedure);
    }

    //--------------------------------------------------------------------
    @Override
    public Documento onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
    {
        Documento current = new Documento();

        for (int i = 1; i <= e.getColumnCount(); i++)
        {
            switch (e.getColumnName(i))
            {
                case "id":
                    current.setId(e.getInt(i));
                    break;
                case "folio":
                    current.setFolio(e.getString(i));
                    break;
                case "serie":
                    current.setSerie(e.getString(i));
                    break;
                case "fecha_elaboracion":
                    current.setFechaElaboracion(e.getDate(i));
                    break;
                case "fecha_vigencia":
                    current.setFechaVigencia(e.getDate(i));
                    break;
                case "es_activo":
                    current.setActivo(e.getBoolean(i));
                    break;
                case "subtotal":
                    current.setSubtotal(e.getFloat(i));
                    break;
                case "factor":
                    current.setFactor(e.getInt(i));
                    break;
                case "monto":
                    current.setMonto(e.getFloat(i));
                    break;
                case "total":
                    current.setTotal(e.getFloat(i));
                    break;
                case "id_estatus":
                    current.setIdEstatus(e.getInt(i));
                    break;
                case "id_tipo_comprobante":
                    current.setIdTipoComprobante(e.getInt(i));
                    break;
                case "es_aplicado":
                    current.setAplicado(e.getBoolean(i));
                    break;
                case "id_moneda":
                    current.setIdMoneda(e.getInt(i));
                    break;
                case "pagado":
                    current.setPagado(e.getFloat(i));
                    break;
                case "saldo_actual":
                    current.setSaldoActual(e.getFloat(i));
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
    protected void onConvertTransfer(Documento values, DataSetEvent e) throws SQLException, UnsupportedOperationException
    {
        if (e.getDML() == insertProcedure)
        {
            e.setString(1, values.getFolio(), 32);//"vfolio"
            e.setString(2, values.getSerie(), 10);//"vserie"
            e.setDate(3, values.getFechaElaboracion());//"vfecha_elaboracion"
            e.setDate(4, values.getFechaVigencia());//"vfecha_vigencia"
            e.setBoolean(5, values.esActivo());//"ves_activo"
            e.setFloat(6, values.getSubtotal());//"vsubtotal"
            e.setInt(7, values.getFactor());//"vfactor"
            e.setFloat(8, values.getMonto());//"vmonto"
            e.setFloat(9, values.getTotal());//"vtotal"
            e.setInt(10, values.getIdEstatus());//"vid_estatus"
            e.setInt(11, values.getIdTipoComprobante());//"vid_tipo_comprobante"
            e.setBoolean(12, values.esAplicado());//"ves_aplicado"
            e.setInt(13, values.getIdMoneda());//"vid_moneda"
            e.setFloat(14, values.getPagado());//"vpagado"
            e.setFloat(15, values.getSaldoActual());//"vsaldo_actual"
        }
    }

    //--------------------------------------------------------------------    
    @Override
    protected void onConvertKeyResult(DataGetEvent e, Documento value) throws SQLException, UnsupportedOperationException
    {
        for (int i = 1; i <= e.getColumnCount(); i++)
        {
            switch (e.getColumnName(i))
            {
                case "id":
                    value.setId(e.getInt(i));
                    break;
                case "folio":
                    value.setFolio(e.getString(i));
                    break;
                case "serie":
                    value.setSerie(e.getString(i));
                    break;
                case "fecha_elaboracion":
                    value.setFechaElaboracion(e.getDate(i));
                    break;
                case "fecha_vigencia":
                    value.setFechaVigencia(e.getDate(i));
                    break;
                case "es_activo":
                    value.setActivo(e.getBoolean(i));
                    break;
                case "subtotal":
                    value.setSubtotal(e.getFloat(i));
                    break;
                case "factor":
                    value.setFactor(e.getInt(i));
                    break;
                case "monto":
                    value.setMonto(e.getFloat(i));
                    break;
                case "total":
                    value.setTotal(e.getFloat(i));
                    break;
                case "id_estatus":
                    value.setIdEstatus(e.getInt(i));
                    break;
                case "id_tipo_comprobante":
                    value.setIdTipoComprobante(e.getInt(i));
                    break;
                case "es_aplicado":
                    value.setAplicado(e.getBoolean(i));
                    break;
                case "id_moneda":
                    value.setIdMoneda(e.getInt(i));
                    break;
                case "pagado":
                    value.setPagado(e.getFloat(i));
                    break;
                case "saldo_actual":
                    value.setSaldoActual(e.getFloat(i));
                    break;
            }
        }
        /*  if (e.getColumnCount() > 0)
            value.setId(e.getInt(1));*/
    }

    //---------------------------------------------------------------------
    @Override
    public void onSendValues(DataSendToQueryEvent e, Documento values)
    {
        if (e.isCreateOperation())
        {
            e.addInteger("id", values.getId());
            e.addString("folio", values.getFolio(), 32);
            e.addString("serie", values.getSerie(), 10);
            e.addDate("fecha_elaboracion", values.getFechaElaboracion());
            e.addDate("fecha_vigencia", values.getFechaVigencia());
            e.addBoolean("es_activo", values.esActivo());
            e.addFloat("subtotal", values.getSubtotal());
            e.addInteger("factor", values.getFactor());
            e.addFloat("monto", values.getMonto());
            e.addFloat("total", values.getTotal());
            e.addInteger("id_estatus", values.getIdEstatus());
            e.addInteger("id_tipo_comprobante", values.getIdTipoComprobante());
            e.addBoolean("es_aplicado", values.esAplicado());
            e.addInteger("id_moneda", values.getIdMoneda());
            e.addFloat("pagado", values.getPagado());
            e.addFloat("saldo_actual", values.getSaldoActual());
        }

        else if (e.isUpdateOperation())
        {
            e.addString("folio", values.getFolio(), 32);
            e.addString("serie", values.getSerie(), 10);
            e.addDate("fecha_elaboracion", values.getFechaElaboracion());
            e.addDate("fecha_vigencia", values.getFechaVigencia());
            e.addBoolean("es_activo", values.esActivo());
            e.addFloat("subtotal", values.getSubtotal());
            e.addInteger("factor", values.getFactor());
            e.addFloat("monto", values.getMonto());
            e.addFloat("total", values.getTotal());
            e.addInteger("id_estatus", values.getIdEstatus());
            e.addInteger("id_tipo_comprobante", values.getIdTipoComprobante());
            e.addBoolean("es_aplicado", values.esAplicado());
            e.addInteger("id_moneda", values.getIdMoneda());
            e.addFloat("pagado", values.getPagado());
            e.addFloat("saldo_actual", values.getSaldoActual());

            //e.SendParameters.AddInteger ("fk_pers_id", values.Pers_Id)
            e.addInteger("id", QueryParameter.Operator.EQUAL, values.getId_Viejo());

        }
        else if (e.isRetrieveOperation() || e.isDeleteOperation())
        {
            if (values.getId() != Documento.EMPTY_INT)
                e.addInteger("id", QueryParameter.Operator.EQUAL, values.getId());

            if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
            {
                if (!values.getFolio().equals(Documento.EMPTY_STRING))
                    e.addString("folio", QueryParameter.Operator.EQUAL, values.getFolio());

                if (!values.getSerie().equals(Documento.EMPTY_STRING))
                    e.addString("serie", QueryParameter.Operator.EQUAL, values.getSerie());

                if (values.getFechaElaboracion() != Documento.EMPTY_DATE)
                    e.addDate("fecha_elaboracion", QueryParameter.Operator.EQUAL, values.getFechaElaboracion());

                if (values.getFechaVigencia() != Documento.EMPTY_DATE)
                    e.addDate("fecha_vigencia", QueryParameter.Operator.EQUAL, values.getFechaVigencia());

                if (values.isSet())
                {
                    e.addBoolean("es_activo", QueryParameter.Operator.EQUAL, values.esActivo());
                    e.addBoolean("es_aplicado", QueryParameter.Operator.EQUAL, values.esAplicado());
                }

                if (values.getSubtotal() != Documento.EMPTY_FLOAT)
                    e.addFloat("subtotal", QueryParameter.Operator.LOWER_EQUAL, values.getSubtotal());

                if (values.getFactor() != Documento.EMPTY_INT)
                    e.addFloat("factor", QueryParameter.Operator.LOWER_EQUAL, values.getFactor());

                if (values.getMonto() != Documento.EMPTY_FLOAT)
                    e.addFloat("monto", QueryParameter.Operator.LOWER_EQUAL, values.getMonto());

                if (values.getTotal() != Documento.EMPTY_FLOAT)
                    e.addFloat("total", QueryParameter.Operator.LOWER_EQUAL, values.getTotal());

                if (values.getIdEstatus() != Documento.EMPTY_INT)
                    e.addInteger("id_estatus", QueryParameter.Operator.EQUAL, values.getIdEstatus());

                if (values.getIdTipoComprobante() != Documento.EMPTY_INT)
                    e.addInteger("id_tipo_comprobante", QueryParameter.Operator.EQUAL, values.getIdTipoComprobante());

                if (values.getIdMoneda() != Documento.EMPTY_INT)
                    e.addInteger("id_moneda", QueryParameter.Operator.EQUAL, values.getIdMoneda());

                if (values.getPagado() != Documento.EMPTY_FLOAT)
                    e.addFloat("pagado", QueryParameter.Operator.EQUAL, values.getPagado());

                if (values.getSaldoActual() != Documento.EMPTY_FLOAT)
                    e.addFloat("saldo_actual", QueryParameter.Operator.EQUAL, values.getSaldoActual());
            }
        }
    }
}
