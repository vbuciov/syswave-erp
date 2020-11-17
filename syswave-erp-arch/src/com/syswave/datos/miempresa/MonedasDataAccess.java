package com.syswave.datos.miempresa;

import com.syswave.datos.common.SingletonDataAccess;
import com.syswave.entidades.miempresa.Moneda;
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
public class MonedasDataAccess extends SingletonDataAccess<Moneda>
{
    private final String insertProcedure = "moneda_insert(?,?)";

    public MonedasDataAccess(IMediatorDataSource mysource)
    {
        super(mysource, "monedas", "id", "nombre", "siglas");
        setInsertProcedure(insertProcedure);
    }

    //--------------------------------------------------------------------
    @Override
    public Moneda onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
    {
        Moneda current = new Moneda();

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
                case "siglas":
                    current.setSiglas(e.getString(i));
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
    protected void onConvertTransfer(Moneda values, DataSetEvent e) throws SQLException, UnsupportedOperationException
    {
        if (e.getDML() == insertProcedure)
        {
            e.setString(1, values.getNombre(), 45);//"vnombre"
            e.setString(2, values.getSiglas(), 45);//"vsiglas"
        }
    }

    //--------------------------------------------------------------------    
    @Override
    protected void onConvertKeyResult(DataGetEvent e, Moneda value) throws SQLException, UnsupportedOperationException
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
                case "siglas":
                    value.setSiglas(e.getString(i));
                    break;
            }

        }
        /*  if (e.getColumnCount() > 0)
            value.setId(e.getInt(1));*/
    }

    //---------------------------------------------------------------------
    @Override
    public void onSendValues(DataSendToQueryEvent e, Moneda values)
    {

        if (e.isCreateOperation())
        {
            e.addString("nombre", values.getNombre(), 45);
            e.addString("siglas", values.getSiglas(), 45);
        }

        else if (e.isUpdateOperation())
        {
            e.addString("nombre", values.getNombre(), 45);
            e.addString("siglas", values.getSiglas(), 45);

            //e.SendParameters.AddInteger ("fk_pers_id", values.Pers_Id)
            e.addInteger("id", QueryParameter.Operator.EQUAL, values.getId_Viejo());

        }

        else if (e.isRetrieveOperation() || e.isDeleteOperation())
        {
            if (values.getId() != Moneda.EMPTY_INT)
                e.addInteger("id", QueryParameter.Operator.EQUAL, values.getId());

            if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
            {
                if (!values.getNombre().equals(Moneda.EMPTY_STRING))
                    e.addString("nombre", QueryParameter.Operator.EQUAL, values.getNombre());

                if (!values.getSiglas().equals(Moneda.EMPTY_STRING))
                    e.addString("siglas", QueryParameter.Operator.EQUAL, values.getSiglas());
            }
        }
    }
}
