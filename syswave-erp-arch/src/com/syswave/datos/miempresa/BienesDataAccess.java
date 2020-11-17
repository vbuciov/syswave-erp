package com.syswave.datos.miempresa;

import com.syswave.datos.common.SingletonDataAccess;
import com.syswave.entidades.miempresa.Bien;
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
public class BienesDataAccess extends SingletonDataAccess<Bien>
{
    private final String insertProcedure = "bien_insert(?,?,?)";

    //--------------------------------------------------------------------
    public BienesDataAccess(IMediatorDataSource mysource)
    {
        super(mysource, "bienes", "id", "nombre", "es_tipo", "id_categoria");
        setInsertProcedure(insertProcedure);
    }

    //---------------------------------------------------------------------
    /**
     * InsertProcedure is using an executeUpdateStoredProcedure
     *
     * @throws java.sql.SQLException
     */
    @Override
    protected void onConvertTransfer(Bien values, DataSetEvent e) throws SQLException, UnsupportedOperationException
    {
        if (e.getDML() == insertProcedure)
        {
            e.setString(1, values.getNombre(), 45);//"vnombre"
            e.setInt(2, values.getEsTipo());//"ves_tipo"
            e.setInt(3, values.getIdCategoria());//"vid_categoria"
        }
    }

    //--------------------------------------------------------------------    
    @Override
    protected void onConvertKeyResult(DataGetEvent e, Bien value) throws SQLException, UnsupportedOperationException
    {
        if (e.getDML() == insertProcedure)
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
                    case "es_tipo":
                        value.setEsTipo(e.getInt(i));
                        break;
                    case "id_categoria":
                        value.setIdCategoria(e.getInt(i));
                        break;
                }
            }
            /*else
            if (e.getColumnCount() > 0)
                value.setId(e.getInt(1));*/
        }
    }


    //--------------------------------------------------------------------
    /**
     * Este método sirve para convertir un objeto a valores de transferencia.
     *
     * @param values Es el objeto base.
     */
    @Override
    public void onSendValues(DataSendToQueryEvent e, Bien values)
    {
        if (values.getId() != Bien.EMPTY_INT)
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

        if (!values.getNombre().equals(Bien.EMPTY_STRING))
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addString("nombre", values.getNombre(), 45);

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addString("nombre", QueryParameter.Operator.EQUAL, values.getNombre());
        }

        if (values.getEsTipo() != Bien.EMPTY_INT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addInteger("es_tipo", values.getEsTipo());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addInteger("es_tipo", QueryParameter.Operator.EQUAL, values.getEsTipo());
        }

        if (values.getIdCategoria() != Bien.EMPTY_INT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addInteger("id_categoria", values.getIdCategoria());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addInteger("id_categoria", QueryParameter.Operator.EQUAL, values.getIdCategoria());
        }
    }
    
    //--------------------------------------------------------------------
    @Override
    public Bien onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
    {
        Bien current = new Bien();

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
                case "es_tipo":
                    current.setEsTipo(e.getInt(i));
                    break;
                case "id_categoria":
                    current.setIdCategoria(e.getInt(i));
                    break;
            }
        }

        current.setSearchOnlyByPrimaryKey(true);
        current.acceptChanges();

        return current;

    }

}