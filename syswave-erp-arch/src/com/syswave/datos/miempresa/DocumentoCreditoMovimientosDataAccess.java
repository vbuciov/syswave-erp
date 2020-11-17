package com.syswave.datos.miempresa;

import com.syswave.datos.common.SingletonDataAccess;
import com.syswave.entidades.miempresa.DocumentoCreditoMovimiento;
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
public class DocumentoCreditoMovimientosDataAccess extends SingletonDataAccess<DocumentoCreditoMovimiento>
{

    public final String insertProcedure = "documento_credito_movimiento_insert(?,?,?,?,?,?,?)";

    public DocumentoCreditoMovimientosDataAccess(IMediatorDataSource mysource)
    {
        super(mysource, "documento_credito_movimientos", "linea", "id_documento",
              "movimiento", "consecutivo", "id_persona", "monto", "saldo_anterior",
              "saldo_final");
        setInsertProcedure(insertProcedure);
    }

    //---------------------------------------------------------------------
    /**
     * InsertProcedure is using an executeUpdateStoredProcedure
     *
     * @throws java.sql.SQLException
     */
    @Override
    protected void onConvertTransfer(DocumentoCreditoMovimiento values, DataSetEvent e) throws SQLException, UnsupportedOperationException
    {
        if (e.getDML() == insertProcedure)
        {
            e.setInt(1, values.getIdDocumento());//"vid_documento"
            e.setInt(2, values.getMovimiento());//"vmovimiento"
            e.setInt(3, values.getConsecutivo());//"vconsecutivo"
            e.setInt(4, values.getIdPersona());//"vid_persona"
            e.setFloat(5, values.getMonto());//"vmonto"
            e.setFloat(6, values.getSaldoAnterior());//"vsaldo_anterior"
            e.setFloat(7, values.getSaldoFinal());//"vsaldo_final"
        }
    }

    //--------------------------------------------------------------------    
    @Override
    protected void onConvertKeyResult(DataGetEvent e, DocumentoCreditoMovimiento value) throws SQLException, UnsupportedOperationException
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
                case "movimiento":
                    value.setMovimiento(e.getInt(i));
                    break;
                case "consecutivo":
                    value.setConsecutivo(e.getInt(i));
                    break;
                case "id_persona":
                    value.setIdPersona(e.getInt(i));
                    break;
                case "monto":
                    value.setMonto(e.getFloat(i));
                    break;
                case "saldo_anterior":
                    value.setSaldoAnterior(e.getFloat(i));
                    break;
                case "saldo_final":
                    value.setSaldoFinal(e.getFloat(i));
                    break;
            }
        }
        /*  if (e.getColumnCount() > 0)
            value.setId(e.getInt(1));*/
    }

    //---------------------------------------------------------------------
    @Override
    public void onSendValues(DataSendToQueryEvent e, DocumentoCreditoMovimiento values)
    {
        if (values.getLinea() != DocumentoCreditoMovimiento.EMPTY_INT)
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

        if (values.getIdDocumento() != DocumentoCreditoMovimiento.EMPTY_INT)
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

        if (values.getMovimiento() != DocumentoCreditoMovimiento.EMPTY_INT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addInteger("movimiento", values.getMovimiento());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addInteger("movimiento", QueryParameter.Operator.EQUAL, values.getMovimiento());
        }

        if (values.getConsecutivo() != DocumentoCreditoMovimiento.EMPTY_INT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addInteger("consecutivo", values.getConsecutivo());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addInteger("consecutivo", QueryParameter.Operator.EQUAL, values.getConsecutivo());
        }

        if (values.getIdPersona() != DocumentoCreditoMovimiento.EMPTY_INT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addInteger("id_persona", values.getIdPersona());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addInteger("id_persona", QueryParameter.Operator.EQUAL, values.getIdPersona());
        }

        if (values.getMonto() != DocumentoCreditoMovimiento.EMPTY_FLOAT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addFloat("monto", values.getMonto());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addFloat("monto", QueryParameter.Operator.EQUAL, values.getMonto());
        }

        if (values.getSaldoAnterior() != DocumentoCreditoMovimiento.EMPTY_FLOAT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addFloat("saldo_anterior", values.getSaldoAnterior());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addFloat("saldo_anterior", QueryParameter.Operator.EQUAL, values.getSaldoAnterior());
        }

        if (values.getSaldoFinal() != DocumentoCreditoMovimiento.EMPTY_FLOAT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addFloat("saldo_final", values.getSaldoFinal());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addFloat("saldo_final", QueryParameter.Operator.EQUAL, values.getSaldoFinal());
        }
    }

    //---------------------------------------------------------------------
    @Override
    public DocumentoCreditoMovimiento onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
    {
        DocumentoCreditoMovimiento current = new DocumentoCreditoMovimiento();

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
                case "movimiento":
                    current.setMovimiento(e.getInt(i));
                    break;
                case "consecutivo":
                    current.setConsecutivo(e.getInt(i));
                    break;
                case "id_persona":
                    current.setIdPersona(e.getInt(i));
                    break;
                case "monto":
                    current.setMonto(e.getFloat(i));
                    break;
                case "saldo_anterior":
                    current.setSaldoAnterior(e.getFloat(i));
                    break;
                case "saldo_final":
                    current.setSaldoFinal(e.getFloat(i));
                    break;
            }
        }

        current.setSearchOnlyByPrimaryKey(true);
        current.acceptChanges();

        return current;
    }
}
