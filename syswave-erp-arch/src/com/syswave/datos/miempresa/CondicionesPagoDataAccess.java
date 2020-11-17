package com.syswave.datos.miempresa;

import com.syswave.datos.common.SingletonDataAccess;
import com.syswave.entidades.miempresa.CondicionPago;
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
public class CondicionesPagoDataAccess extends SingletonDataAccess<CondicionPago>
{

    public final String insertProcedure = "condicion_pago_insert(?,?,?,?)";

    public CondicionesPagoDataAccess(IMediatorDataSource mysource)
    {
        super(mysource, "condiciones_pago", "id", "nombre", "valor", "unidad", "id_tipo_condicion");
        setInsertProcedure(insertProcedure);
    }

    //---------------------------------------------------------------------
    /**
     * InsertProcedure is using an executeUpdateStoredProcedure
     *
     * @throws java.sql.SQLException
     */
    @Override
    protected void onConvertTransfer(CondicionPago values, DataSetEvent e) throws SQLException, UnsupportedOperationException
    {
        if (e.getDML() == insertProcedure)
        {
            e.setString(1, values.getNombre(), 45); //"vnombre"
            e.setInt(2, values.getValor());//"vvalor"
            e.setInt(3, values.getUnidad());//"vunidad"
            e.setInt(4, values.getId_tipo_condicion());//"vid_tipo_condicion"
        }
    }

    //--------------------------------------------------------------------    
    @Override
    protected void onConvertKeyResult(DataGetEvent e, CondicionPago value) throws SQLException, UnsupportedOperationException
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
                case "valor":
                    value.setValor(e.getInt(i));
                    break;
                case "unidad":
                    value.setUnidad(e.getInt(i));
                    break;
                case "id_tipo_condicion":
                    value.setId_tipo_condicion(e.getInt(i));
                    break;
            }

        }
        /*  if (e.getColumnCount() > 0)
            value.setId(e.getInt(1));*/
    }

    //---------------------------------------------------------------------
    @Override
    public void onSendValues(DataSendToQueryEvent e, CondicionPago values)
    {
        if (values.getId() != CondicionPago.EMPTY_INT)
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

        if (!values.getNombre().equals(CondicionPago.EMPTY_STRING))
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addString("nombre", values.getNombre());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addString("nombre", QueryParameter.Operator.EQUAL, values.getNombre());
        }

        if (values.getValor() != CondicionPago.EMPTY_INT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addInteger("valor", values.getValor());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addInteger("valor", QueryParameter.Operator.EQUAL, values.getValor());
        }

        if (values.getUnidad() != CondicionPago.EMPTY_INT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addInteger("unidad", values.getUnidad());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addInteger("unidad", QueryParameter.Operator.EQUAL, values.getUnidad());
        }

        if (values.getId_tipo_condicion()!= CondicionPago.EMPTY_INT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addInteger("id_tipo_condicion", values.getId_tipo_condicion());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addInteger("id_tipo_condicion", QueryParameter.Operator.EQUAL, values.getId_tipo_condicion());
        }
    }

    //---------------------------------------------------------------------
    @Override
    public CondicionPago onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
    {
        CondicionPago current = new CondicionPago();

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
                case "valor":
                    current.setValor(e.getInt(i));
                    break;
                case "unidad":
                    current.setUnidad(e.getInt(i));
                    break;
                case "id_tipo_condicion":
                    current.setId_tipo_condicion(e.getInt(i));
                    break;
            }
        }

        current.setSearchOnlyByPrimaryKey(true);
        current.acceptChanges();

        return current;
    }
}
