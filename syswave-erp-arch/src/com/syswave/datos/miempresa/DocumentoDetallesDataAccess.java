package com.syswave.datos.miempresa;

import com.syswave.datos.common.SingletonDataAccess;
import com.syswave.entidades.miempresa.DocumentoDetalle;
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
public class DocumentoDetallesDataAccess extends SingletonDataAccess<DocumentoDetalle>
{

    public final String insertProcedure = "documento_detalles_insert(?,?,?,?,?,?,?,?)";
    public final String updateProcedure = "documento_detalles_update(?,?,?,?,?,?,?,?,?,?)";

    public DocumentoDetallesDataAccess(IMediatorDataSource mysource)
    {
        super(mysource, "documento_detalles", "consecutivo", "id_documento",
              "descripcion", "cantidad", "precio", "importe",
              "monto", "factor", "importe_neto");
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
    protected void onConvertTransfer(DocumentoDetalle values, DataSetEvent e) throws SQLException, UnsupportedOperationException
    {
        if (e.getDML() == insertProcedure)
        {
            e.setInt(1, values.getIdDocumento());//"vid_documento"
            e.setString(2, values.getDescripcion(), 500);//"vdescripcion"
            e.setFloat(3, values.getCantidad());//"vcantidad"
            e.setFloat(4, values.getPrecio());//"vprecio"
            e.setFloat(5, values.getImporte());//"vimporte"
            e.setFloat(6, values.getMonto());//"vmonto"
            e.setInt(7, values.getFactor());//"vfactor"
            e.setFloat(8, values.getImporte_neto());//"vimporte_neto"
        }

        else if (e.getDML() == updateProcedure)
        {
            e.setInt(1, values.getConsecutivo_Viejo());//"vconsecutivo_old"
            e.setInt(2, values.getIdDocumento_Viejo());//"vid_documento_old"
            e.setInt(3, values.getIdDocumento());//"vid_documento_new"
            e.setString(4, values.getDescripcion(), 500);//"vdescripcion"
            e.setFloat(5, values.getCantidad());//"vcantidad"
            e.setFloat(6, values.getPrecio());//"vprecio"
            e.setFloat(7, values.getImporte());//"vimporte"
            e.setFloat(8, values.getMonto());//"vmonto"
            e.setInt(9, values.getFactor());//"vfactor"
            e.setFloat(10, values.getImporte_neto());//"vimporte_neto"
        }
    }

    //--------------------------------------------------------------------    
    @Override
    protected void onConvertKeyResult(DataGetEvent e, DocumentoDetalle value) throws SQLException, UnsupportedOperationException
    {
        for (int i = 1; i <= e.getColumnCount(); i++)
        {
            switch (e.getColumnName(i))
            {
                case "consecutivo":
                    value.setConsecutivo(e.getInt(i));
                    break;
                case "id_documento":
                    value.setIdDocumento(e.getInt(i));
                    break;
                case "descripcion":
                    value.setDescripcion(e.getString(i));
                    break;
                case "cantidad":
                    value.setCantidad(e.getFloat(i));
                    break;
                case "precio":
                    value.setPrecio(e.getFloat(i));
                    break;
                case "importe":
                    value.setImporte(e.getFloat(i));
                    break;
                case "monto":
                    value.setMonto(e.getFloat(i));
                    break;
                case "factor":
                    value.setFactor(e.getInt(i));
                    break;
                case "importe_neto":
                    value.setImporte_neto(e.getFloat(i));
                    break;
            }
        }
        /*  if (e.getColumnCount() > 0)
            value.setId(e.getInt(1));*/
    }

    //---------------------------------------------------------------------
    @Override
    public void onSendValues(DataSendToQueryEvent e, DocumentoDetalle values)
    {
        if (values.getConsecutivo()!= DocumentoDetalle.EMPTY_INT)
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

        if (values.getIdDocumento()!= DocumentoDetalle.EMPTY_INT)
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

        if (!values.getDescripcion().equals(DocumentoDetalle.EMPTY_STRING))
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addString("descripcion", values.getDescripcion(), 500);

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addString("descripcion", QueryParameter.Operator.EQUAL, values.getDescripcion());
        }

        if (values.getCantidad() != DocumentoDetalle.EMPTY_FLOAT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addFloat("cantidad", values.getCantidad());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addFloat("cantidad", QueryParameter.Operator.EQUAL, values.getCantidad());
        }

        if (values.getPrecio() != DocumentoDetalle.EMPTY_FLOAT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addFloat("precio", values.getPrecio());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addFloat("precio", QueryParameter.Operator.EQUAL, values.getPrecio());
        }

        if (values.getImporte() != DocumentoDetalle.EMPTY_FLOAT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addFloat("importe", values.getImporte());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addFloat("importe", QueryParameter.Operator.EQUAL, values.getImporte());
        }

        if (values.getMonto() != DocumentoDetalle.EMPTY_FLOAT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addFloat("monto", values.getMonto());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addFloat("monto", QueryParameter.Operator.EQUAL, values.getMonto());
        }

        if (values.getFactor() != DocumentoDetalle.EMPTY_INT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addInteger("factor", values.getFactor());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addInteger("factor", QueryParameter.Operator.EQUAL, values.getFactor());
        }

        if (values.getImporte_neto() != DocumentoDetalle.EMPTY_FLOAT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addFloat("importe_neto", values.getImporte_neto());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addFloat("importe_neto", QueryParameter.Operator.EQUAL, values.getImporte_neto());
        }
    }

    //---------------------------------------------------------------------
    @Override
    public DocumentoDetalle onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
    {
        DocumentoDetalle current = new DocumentoDetalle();

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
