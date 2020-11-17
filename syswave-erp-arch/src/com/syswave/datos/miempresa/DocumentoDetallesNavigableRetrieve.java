package com.syswave.datos.miempresa;

import com.syswave.datos.common.SingletonDataAccess;
import com.syswave.entidades.miempresa_vista.DocumentoDetalleNavigable;
import datalayer.api.IMediatorDataSource;
import datalayer.events.DataGetEvent;
import datalayer.events.DataSendToQueryEvent;
import datalayer.utils.QueryParameter;
import java.sql.SQLException;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class DocumentoDetallesNavigableRetrieve extends SingletonDataAccess<DocumentoDetalleNavigable>
{

    public DocumentoDetallesNavigableRetrieve(IMediatorDataSource mysource)
    {
        super(mysource, "documento_detalles", "consecutivo", "id_documento", "descripcion",
              "cantidad", "precio", "importe", "monto", "factor", "importe_neto");
        setWithAutoID(false);
    }

    //---------------------------------------------------------------------
    @Override
    public void onSendValues(DataSendToQueryEvent e, DocumentoDetalleNavigable values)
    {
        if (values.getConsecutivo()!= DocumentoDetalleNavigable.EMPTY_INT)
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

        if (values.getIdDocumento()!= DocumentoDetalleNavigable.EMPTY_INT)
        {

            if (e.isCreateOperation())
                e.addInteger("id_documento", values.getIdDocumento());

            else if (e.isUpdateOperation())
            {
                e.addInteger("id_documento", values.getIdDocumento());
                e.addInteger("id_documento", QueryParameter.Operator.EQUAL, values.getIdDocumento_Viejo());
            }

            else
                e.addInteger("id_documento", QueryParameter.Operator.EQUAL, values.getIdDocumento());
        }

        if (!values.getDescripcion().equals(DocumentoDetalleNavigable.EMPTY_STRING))
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addString("descripcion", values.getDescripcion(), 500);

            else
                e.addString("descripcion", QueryParameter.Operator.EQUAL, values.getDescripcion());
        }

        if (values.getCantidad() != DocumentoDetalleNavigable.EMPTY_FLOAT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addFloat("cantidad", values.getCantidad());

            else
                e.addFloat("cantidad", QueryParameter.Operator.EQUAL, values.getCantidad());
        }

        if (values.getPrecio() != DocumentoDetalleNavigable.EMPTY_FLOAT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addFloat("precio", values.getPrecio());

            else
                e.addFloat("precio", QueryParameter.Operator.EQUAL, values.getPrecio());
        }

        if (values.getImporte() != DocumentoDetalleNavigable.EMPTY_FLOAT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addFloat("importe", values.getImporte());

            else
                e.addFloat("importe", QueryParameter.Operator.EQUAL, values.getImporte());
        }

        if (values.getMonto() != DocumentoDetalleNavigable.EMPTY_FLOAT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addFloat("monto", values.getMonto());

            else
                e.addFloat("monto", QueryParameter.Operator.EQUAL, values.getMonto());
        }

        if (values.getImporte_neto() != DocumentoDetalleNavigable.EMPTY_INT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addInteger("factor", values.getFactor());

            else
                e.addInteger("factor", QueryParameter.Operator.EQUAL, values.getFactor());
        }

        if (values.getImporte_neto() != DocumentoDetalleNavigable.EMPTY_FLOAT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addFloat("importe_neto", values.getImporte_neto());

            else
                e.addFloat("importe_neto", QueryParameter.Operator.EQUAL, values.getImporte_neto());
        }

    }

    //--------------------------------------------------------------------    
    @Override
    public DocumentoDetalleNavigable onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
    {
        DocumentoDetalleNavigable current = new DocumentoDetalleNavigable();

        for (int i = 1; i <= e.getColumnCount(); i++)
        {
            switch (e.getColumnName(i))
            {
                case "consecutivo":
                    current.setConsecutivo(e.getInt(i));
                    break;
                case "id_documento":
                    current.setIdDocumento(e.getInt(i));
                    break;
                case "descripcion":
                    current.setDescripcion(e.getString(i));
                    break;
                case "cantidad":
                    current.setCantidad(e.getFloat(i));
                    break;
                case "precio":
                    current.setPrecio(e.getFloat(i));
                    break;
                case "importe":
                    current.setImporte(e.getFloat(i));
                    break;
                case "monto":
                    current.setMonto(e.getFloat(i));
                    break;
                case "factor":
                    current.setFactor(e.getInt(i));
                    break;
                case "importe_neto":
                    current.setImporte_neto(e.getFloat(i));
                    break;
            }
        }

        current.setSearchOnlyByPrimaryKey(true);
        current.acceptChanges();

        return current;
    }
}
