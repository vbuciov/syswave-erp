package com.syswave.datos.miempresa;

import com.syswave.datos.common.SingletonDataAccess;
import com.syswave.entidades.miempresa_vista.DocumentoComercial;
import datalayer.api.IMediatorDataSource;
import datalayer.events.DataGetEvent;
import datalayer.events.DataSendToQueryEvent;
import datalayer.utils.QueryParameter;
import java.sql.SQLException;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class DocumentoComercialRetrieve extends SingletonDataAccess<DocumentoComercial>
{

    public DocumentoComercialRetrieve(IMediatorDataSource mysource)
    {
        super(mysource, "documentos_comerciales", "id", "id_emisor", "id_receptor",
              "receptor", "condiciones", "folio", "serie", "fecha_elaboracion",
              "fecha_vigencia", "es_activo", "subtotal", "factor", "monto",
              "total", "id_moneda", "id_estatus", "id_tipo_comprobante",
              "es_aplicado", "pagado", "saldo_actual");
    }

    //--------------------------------------------------------------------    
    @Override
    public DocumentoComercial onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
    {
        DocumentoComercial current = new DocumentoComercial();

        for (int i = 1; i <= e.getColumnCount(); i++)
        {
            if (e.getColumnName(i).equals("id"))
                current.setId(e.getInt(i));

            else if (e.getColumnName(i).equals("id_emisor"))
                current.setIdEmisor(e.getInt(i));

            else if (e.getColumnName(i).equals("id_receptor"))
                current.setIdReceptor(e.getInt(i));

            else if (e.getColumnName(i).equals("receptor"))
                current.setReceptor(e.getString(i));

            else if (e.getColumnName(i).equals("condiciones"))
                current.setCondiciones(e.getString(i));

            else if (e.getColumnName(i).equals("folio"))
                current.setFolio(e.getString(i));

            else if (e.getColumnName(i).equals("serie"))
                current.setSerie(e.getString(i));

            else if (e.getColumnName(i).equals("fecha_elaboracion"))
                current.setFechaElaboracion(e.getDate(i));

            else if (e.getColumnName(i).equals("fecha_vigencia"))
                current.setFechaVigencia(e.getDate(i));

            else if (e.getColumnName(i).equals("condiciones"))
                current.setCondiciones(e.getString(i));

            else if (e.getColumnName(i).equals("es_activo"))
                current.setActivo(e.getBoolean(i));

            else if (e.getColumnName(i).equals("subtotal"))
                current.setSubtotal(e.getFloat(i));

            else if (e.getColumnName(i).equals("factor"))
                current.setFactor(e.getInt(i));

            else if (e.getColumnName(i).equals("monto"))
                current.setMonto(e.getFloat(i));

            else if (e.getColumnName(i).equals("total"))
                current.setTotal(e.getFloat(i));

            else if (e.getColumnName(i).equals("id_moneda"))
                current.setIdMoneda(e.getInt(i));

            else if (e.getColumnName(i).equals("id_estatus"))
                current.setIdEstatus(e.getInt(i));

            else if (e.getColumnName(i).equals("id_tipo_comprobante"))
                current.setIdTipoComprobante(e.getInt(i));

            else if (e.getColumnName(i).equals("es_aplicado"))
                current.setAplicado(e.getBoolean(i));

            else if (e.getColumnName(i).equals("pagado"))
                current.setPagado(e.getFloat(i));

            else if (e.getColumnName(i).equals("saldo_actual"))
                current.setSaldoActual(e.getFloat(i));
        }

        current.setSearchOnlyByPrimaryKey(true);
        current.acceptChanges();

        return current;
    }

    //---------------------------------------------------------------------
    @Override
    public void onSendValues(DataSendToQueryEvent e, DocumentoComercial values)
    {
        if (values.getId() != DocumentoComercial.EMPTY_INT)
        {
            if (e.isRetrieveOperation())
                e.addInteger("id", QueryParameter.Operator.EQUAL, values.getId_Viejo());
        }

        if (values.getIdEmisor() != DocumentoComercial.EMPTY_INT)
        {
            if (e.isRetrieveOperation())
                e.addInteger("id_emisor", QueryParameter.Operator.EQUAL, values.getIdEmisor());
        }

        if (values.getIdReceptor() != DocumentoComercial.EMPTY_INT)
        {
            if (e.isRetrieveOperation())
                e.addInteger("id_receptor", QueryParameter.Operator.EQUAL, values.getIdReceptor());
        }

        if (!values.getReceptor().equals(DocumentoComercial.EMPTY_STRING))
        {
            if (e.isRetrieveOperation())
                e.addString("receptor", QueryParameter.Operator.EQUAL, values.getReceptor());
        }

        if (!values.getCondiciones().equals(DocumentoComercial.EMPTY_STRING))
        {
            if (e.isRetrieveOperation())
                e.addString("condiciones", QueryParameter.Operator.EQUAL, values.getCondiciones());
        }

        if (!values.getFolio().equals(DocumentoComercial.EMPTY_STRING))
        {
            if (e.isRetrieveOperation())
                e.addString("folio", QueryParameter.Operator.EQUAL, values.getFolio());
        }

        if (!values.getSerie().equals(DocumentoComercial.EMPTY_STRING))
        {
            if (e.isRetrieveOperation())
                e.addString("serie", QueryParameter.Operator.EQUAL, values.getSerie());
        }

        if (values.getFechaElaboracion() != DocumentoComercial.EMPTY_DATE)
        {
            if (e.isRetrieveOperation())
                e.addDate("fecha_elaboracion", QueryParameter.Operator.EQUAL, values.getFechaElaboracion());
        }

        if (values.getFechaVigencia() != DocumentoComercial.EMPTY_DATE)
        {
            if (e.isRetrieveOperation())
                e.addDate("fecha_vigencia", QueryParameter.Operator.EQUAL, values.getFechaVigencia());
        }

        if (values.getSubtotal() != DocumentoComercial.EMPTY_FLOAT)
        {
            if (e.isRetrieveOperation())
                e.addFloat("subtotal", QueryParameter.Operator.EQUAL, values.getSubtotal());
        }

        if (values.getFactor() != DocumentoComercial.EMPTY_INT)
        {
            if (e.isRetrieveOperation())
                e.addInteger("factor", QueryParameter.Operator.EQUAL, values.getFactor());
        }

        if (values.getMonto() != DocumentoComercial.EMPTY_FLOAT)
        {
            if (e.isRetrieveOperation())
                e.addFloat("monto", QueryParameter.Operator.EQUAL, values.getMonto());
        }

        if (values.getTotal() != DocumentoComercial.EMPTY_FLOAT)
        {
            if (e.isRetrieveOperation())
                e.addFloat("total", QueryParameter.Operator.EQUAL, values.getTotal());
        }

        if (values.getIdMoneda() != DocumentoComercial.EMPTY_INT)
        {
            if (e.isRetrieveOperation())
                e.addInteger("id_moneda", QueryParameter.Operator.EQUAL, values.getIdMoneda());
        }

        if (values.getIdEstatus() != DocumentoComercial.EMPTY_INT)
        {
            if (e.isRetrieveOperation())
                e.addInteger("id_estatus", QueryParameter.Operator.EQUAL, values.getIdEstatus());
        }

        if (values.getIdTipoComprobante() != DocumentoComercial.EMPTY_INT)
        {
            if (e.isRetrieveOperation())
                e.addInteger("id_tipo_comprobante", QueryParameter.Operator.EQUAL, values.getIdTipoComprobante());
        }

        if (values.isSet())
        {
            if (e.isRetrieveOperation())
            {
                e.addBoolean("es_activo", QueryParameter.Operator.EQUAL, values.esActivo());
                e.addBoolean("es_aplicado", QueryParameter.Operator.EQUAL, values.esAplicado());
            }
        }

        if (values.getPagado() != DocumentoComercial.EMPTY_FLOAT)
        {
            if (e.isRetrieveOperation())
                e.addFloat("pagado", QueryParameter.Operator.EQUAL, values.getPagado());
        }

        if (values.getSaldoActual() != DocumentoComercial.EMPTY_FLOAT)
        {
            if (e.isRetrieveOperation())
                e.addFloat("saldo_actual", QueryParameter.Operator.EQUAL, values.getSaldoActual());
        }
    }
}