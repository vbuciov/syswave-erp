package com.syswave.datos.miempresa;

import com.syswave.datos.common.SingletonDataAccess;
import com.syswave.entidades.miempresa.Contrato;
import datalayer.api.IMediatorDataSource;
import datalayer.events.DataGetEvent;
import datalayer.events.DataSendToQueryEvent;
import datalayer.events.DataSetEvent;
import datalayer.utils.QueryParameter;
import datalayer.utils.Relation;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author sis5
 */
public class ContratosDataAccess extends SingletonDataAccess<Contrato>
{

    private final String insertProcedure = "contrato_insert(?,?,?,?)";
    private final Relation shortBasic;

    public ContratosDataAccess(IMediatorDataSource mysource)
    {
        super(mysource, "contratos", "id", "nombre", "contenido", "formato", "longitud");
        setInsertProcedure(insertProcedure);
        shortBasic = new Relation(getTable(),
                                  new String[]
                                  {
                                      "id",
                                      "nombre",
                                      "formato",
                                      "longitud"
                                  });
    }

    //--------------------------------------------------------------------
    public List<Contrato> retrieveSmall()
    {
        return submitQuery(shortBasic);
    }

    //--------------------------------------------------------------------
    public List<Contrato> retrieveSmall(Contrato consultado)
    {
        return submitQuery(shortBasic, consultado);
    }

    //---------------------------------------------------------------------
    /**
     * InsertProcedure is using an executeUpdateStoredProcedure
     *
     * @throws java.sql.SQLException
     */
    @Override
    protected void onConvertTransfer(Contrato values, DataSetEvent e) throws SQLException, UnsupportedOperationException
    {
        if (e.getDML() == insertProcedure)
        {
            e.setString(1, values.getNombre(), 45); //"vnombre"
            e.setBytes(2, values.getContenido());//"vcontenido"
            e.setString(3, values.getFormato(), 45);//"vformato"
            e.setInt(4, values.getLongitud());//"vlongitud"
        }
    }

    //--------------------------------------------------------------------    
    @Override
    protected void onConvertKeyResult(DataGetEvent e, Contrato value) throws SQLException, UnsupportedOperationException
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
                case "contenido":
                    value.setContenido(e.getBytes(i));
                    break;
                case "formato":
                    value.setFormato(e.getString(i));
                    break;
                case "longitud":
                    value.setLongitud(e.getInt(i));
                    break;
            }
        }
        /*  if (e.getColumnCount() > 0)
            value.setId(e.getInt(1));*/
    }

    //---------------------------------------------------------------------
    @Override
    public void onSendValues(DataSendToQueryEvent e, Contrato values)
    {
        if (values.getId() != Contrato.EMPTY_INT)
        {
            if (e.isCreateOperation())
                e.addInteger("id", values.getId());

            else if (e.isUpdateOperation())
            {
                e.addInteger("id", values.getId());
                e.addInteger("id", QueryParameter.Operator.EQUAL, values.getId_Viejo());
            }

            else
                e.addInteger("id", QueryParameter.Operator.EQUAL, values.getId());
        }

        if (values.getNombre() != Contrato.EMPTY_STRING)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addString("nombre", values.getNombre());

            else
                e.addString("nombre", QueryParameter.Operator.LIKE, values.getNombre());
        }

        if (values.getContenido() != null)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addBytes("contenido", values.getContenido());

        }

        if (values.getFormato() != Contrato.EMPTY_STRING)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addString("formato", values.getFormato());

            else
                e.addString("formato", QueryParameter.Operator.LIKE, values.getFormato());
        }

        if (values.getLongitud() != Contrato.EMPTY_INT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addInteger("longitud", values.getLongitud());

            else
                e.addInteger("longitud", QueryParameter.Operator.EQUAL, values.getLongitud());
        }
    }

    //--------------------------------------------------------------------
    @Override
    public Contrato onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
    {
        Contrato current = new Contrato();

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
                case "contenido":
                    current.setContenido(e.getBytes(i));
                    break;
                case "formato":
                    current.setFormato(e.getString(i));
                    break;
                case "longitud":
                    current.setLongitud(e.getInt(i));
                    break;
            }
        }

        current.setSearchOnlyByPrimaryKey(true);
        current.acceptChanges();

        return current;
    }
}
