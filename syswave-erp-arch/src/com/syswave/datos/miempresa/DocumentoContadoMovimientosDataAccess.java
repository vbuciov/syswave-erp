package com.syswave.datos.miempresa;

import com.syswave.datos.common.SingletonDataAccess;
import com.syswave.entidades.miempresa.DocumentoContadoMovimiento;
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
public class DocumentoContadoMovimientosDataAccess extends SingletonDataAccess<DocumentoContadoMovimiento>
{

    public final String insertProcedure = "documento_contado_movimiento_insert(?,?,?,?)";

    public DocumentoContadoMovimientosDataAccess(IMediatorDataSource mysource)
    {
        super(mysource, "documento_contado_movimientos", "linea", "id_documento",
              "monto", "concepto", "id_tipo_comprobante");
        setInsertProcedure(insertProcedure);
    }

    //---------------------------------------------------------------------
    /**
     * InsertProcedure is using an executeUpdateStoredProcedure
     *
     * @throws java.sql.SQLException
     */
    @Override
    protected void onConvertTransfer(DocumentoContadoMovimiento values, DataSetEvent e) throws SQLException, UnsupportedOperationException
    {
        if (e.getDML() == insertProcedure)
        {
            e.setInt(1, values.getIdDocumento());//"vid_documento"
            e.setFloat(2, values.getMonto());//"vmonto"
            e.setString(3, values.getConcepto(), 100);//"vconcepto"
            e.setInt(4, values.getIdTipoComprobante());//"vid_tipo_comprobante"
        }
    }

    //--------------------------------------------------------------------    
    @Override
    protected void onConvertKeyResult(DataGetEvent e, DocumentoContadoMovimiento value) throws SQLException, UnsupportedOperationException
    {
        for (int i = 1; i <= e.getColumnCount(); i++)
        {
            switch (e.getColumnName(i))
            {
                case "linea":
                    value.setLinea(e.getInt(i));
                    break;
                case "id_documento":
                    value.setIdDocumento(e.getInt(i));
                    break;
                case "monto":
                    value.setMonto(e.getFloat(i));
                    break;
                case "concepto":
                    value.setConcepto(e.getString(i));
                    break;
                case "id_tipo_comprobante":
                    value.setIdTipoComprobante(e.getInt(i));
                    break;
            }
        }
        /*  if (e.getColumnCount() > 0)
            value.setId(e.getInt(1));*/
    }

    //---------------------------------------------------------------------
    @Override
    public void onSendValues(DataSendToQueryEvent e, DocumentoContadoMovimiento values)
    {
        if (values.getLinea()!= DocumentoContadoMovimiento.EMPTY_INT)
        {

            if (e.isCreateOperation())
                e.addInteger("linea", values.getLinea());

            else if (e.isUpdateOperation())
            {
                e.addInteger("linea", values.getLinea());
                e.addInteger("linea", QueryParameter.Operator.EQUAL, values.getLinea_Viejo());
            }

            else
                e.addInteger("linea", QueryParameter.Operator.EQUAL, values.getLinea_Viejo());
        }

        if (values.getIdDocumento() != DocumentoContadoMovimiento.EMPTY_INT)
        {

            if (e.isCreateOperation())
                e.addInteger("id_documento", values.getIdDocumento());

            else if (e.isUpdateOperation())
            {
                e.addInteger("id_documento", values.getIdDocumento());
                e.addInteger("id_documento", QueryParameter.Operator.EQUAL, values.getIdDocumento_Viejo());
            }

            else
                e.addInteger("id_documento", QueryParameter.Operator.EQUAL, values.getIdDocumento_Viejo());
        }

        if (values.getMonto() != DocumentoContadoMovimiento.EMPTY_FLOAT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addFloat("monto", values.getMonto());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addFloat("monto", QueryParameter.Operator.EQUAL, values.getMonto());
        }

        if (!values.getConcepto().equals(DocumentoContadoMovimiento.EMPTY_STRING))
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addString("concepto", values.getConcepto());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addString("concepto", QueryParameter.Operator.EQUAL, values.getConcepto());
        }

        if (values.getIdTipoComprobante() != DocumentoContadoMovimiento.EMPTY_INT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addInteger("id_tipo_comprobante", values.getIdTipoComprobante());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addInteger("id_tipo_comprobante", QueryParameter.Operator.EQUAL, values.getIdTipoComprobante());
        }
    }

    //---------------------------------------------------------------------
    @Override
    public DocumentoContadoMovimiento onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
    {
        DocumentoContadoMovimiento current = new DocumentoContadoMovimiento();

        for (int i = 1; i <= e.getColumnCount(); i++)
        {
            switch (e.getColumnName(i))
            {
                case "linea":
                    current.setLinea(e.getInt(i));
                    break;
                case "id_documento":
                    current.setIdDocumento(e.getInt(i));
                    break;
                case "monto":
                    current.setMonto(e.getFloat(i));
                    break;
                case "concepto":
                    current.setConcepto(e.getString(i));
                    break;
                case "id_tipo_comprobante":
                    current.setIdTipoComprobante(e.getInt(i));
                    break;
            }
        }

        current.setSearchOnlyByPrimaryKey(true);
        current.acceptChanges();

        return current;
    }
}
