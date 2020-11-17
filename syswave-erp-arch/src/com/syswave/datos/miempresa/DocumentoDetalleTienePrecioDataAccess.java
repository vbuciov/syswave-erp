package com.syswave.datos.miempresa;

import com.syswave.datos.common.SingletonDataAccess;
import com.syswave.entidades.miempresa.DocumentoDetalle_tiene_Precio;
import datalayer.api.IMediatorDataSource;
import datalayer.events.DataGetEvent;
import datalayer.events.DataSendToQueryEvent;
import datalayer.utils.QueryParameter;
import java.sql.SQLException;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class DocumentoDetalleTienePrecioDataAccess extends SingletonDataAccess<DocumentoDetalle_tiene_Precio>
{

    public DocumentoDetalleTienePrecioDataAccess(IMediatorDataSource mysource)
    {
        super(mysource, "documento_detalle_tiene_precios", "id_precio", "id_documento", "consecutivo",
              "precio", "cantidad", "total");
        setWithAutoID(false);
    }

    //---------------------------------------------------------------------
    @Override
    public DocumentoDetalle_tiene_Precio onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
    {
        DocumentoDetalle_tiene_Precio current = new DocumentoDetalle_tiene_Precio();

        for (int i = 1; i <= e.getColumnCount(); i++)
        {
            switch (e.getColumnName(i))
            {
                case "id_precio":
                    current.setIdPrecio(e.getInt(i));
                    break;
                case "id_documento":
                    current.setIdDocumento(e.getInt(i));
                    break;
                case "consecutivo":
                    current.setConsecutivo(e.getInt(i));
                    break;
                case "precio":
                    current.setPrecio(e.getFloat(i));
                    break;
                case "cantidad":
                    current.setCantidad(e.getFloat(i));
                    break;
                case "total":
                    current.setTotal(e.getFloat(i));
                    break;
            }
        }

        current.setSearchOnlyByPrimaryKey(true);
        current.acceptChanges();

        return current;
    }

    //---------------------------------------------------------------------
    @Override
    public void onSendValues(DataSendToQueryEvent e, DocumentoDetalle_tiene_Precio values)
    {
        if (values.getIdPrecio() != DocumentoDetalle_tiene_Precio.EMPTY_INT)
        {
            if (e.isCreateOperation())
                e.addInteger("id_precio", values.getIdPrecio());

            else if (e.isUpdateOperation())
            {
                e.addInteger("id_precio", values.getIdPrecio());
                e.addInteger("id_precio", QueryParameter.Operator.EQUAL, values.getIdPrecio_Viejo());
            }

            else
                e.addInteger("id_precio", QueryParameter.Operator.EQUAL, values.getIdPrecio_Viejo());
        }

        if (values.getIdDocumento() != DocumentoDetalle_tiene_Precio.EMPTY_INT)
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

        if (values.getConsecutivo() != DocumentoDetalle_tiene_Precio.EMPTY_INT)
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

        if (values.getPrecio() != DocumentoDetalle_tiene_Precio.EMPTY_FLOAT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addFloat("precio", values.getPrecio());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addFloat("precio", QueryParameter.Operator.EQUAL, values.getPrecio());
        }

        if (values.getCantidad() != DocumentoDetalle_tiene_Precio.EMPTY_FLOAT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addFloat("cantidad", values.getCantidad());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addFloat("cantidad", QueryParameter.Operator.EQUAL, values.getCantidad());
        }

        if (values.getTotal() != DocumentoDetalle_tiene_Precio.EMPTY_FLOAT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addFloat("total", values.getTotal());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addFloat("total", QueryParameter.Operator.EQUAL, values.getTotal());
        }
    }
}
