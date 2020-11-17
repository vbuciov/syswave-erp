package com.syswave.datos.miempresa;

import com.syswave.datos.common.SingletonDataAccess;
import com.syswave.entidades.miempresa.Jornada;
import datalayer.api.IMediatorDataSource;
import datalayer.events.DataGetEvent;
import datalayer.events.DataSendToQueryEvent;
import datalayer.events.DataSetEvent;
import datalayer.utils.QueryParameter;
import java.sql.SQLException;

/**
 *
 * @author sis5
 */
public class JornadaDataAccess extends SingletonDataAccess<Jornada>
{

    private final String insertProcedure = "jornada_insert(?,?,?)";

    public JornadaDataAccess(IMediatorDataSource mysource)
    {
        super(mysource, "jornadas", "id", "nombre", "tiempo_efectivo", "descripcion");
        setInsertProcedure(insertProcedure);
    }

    //--------------------------------------------------------------------
    @Override
    public Jornada onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
    {
        Jornada current = new Jornada();

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
                case "tiempo_efectivo":
                    current.setTiempo_efectivo(e.getInt(i));
                    break;
                case "descripcion":
                    current.setDescripcion(e.getString(i));
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
    protected void onConvertTransfer(Jornada values, DataSetEvent e) throws SQLException, UnsupportedOperationException
    {
        if (e.getDML() == insertProcedure)
        {
            e.setString(1, values.getNombre(), 45);//"vnombre"
            e.setInt(2, values.getTiempo_efectivo());//"vtiempo_efectivo"
            e.setString(3, values.getDescripcion(), 255);//"vdescripcion"
        }
    }

    //--------------------------------------------------------------------    
    @Override
    protected void onConvertKeyResult(DataGetEvent e, Jornada value) throws SQLException, UnsupportedOperationException
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
                case "tiempo_efectivo":
                    value.setTiempo_efectivo(e.getInt(i));
                    break;
                case "descripcion":
                    value.setDescripcion(e.getString(i));
                    break;
            }
        }
        /*  if (e.getColumnCount() > 0)
            value.setId(e.getInt(1));*/
    }

    //---------------------------------------------------------------------
    @Override
    public void onSendValues(DataSendToQueryEvent e, Jornada values)
    {
        if (values.getId() != Jornada.EMPTY_INT)
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

        if (values.getNombre() != Jornada.EMPTY_STRING)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addString("nombre", values.getNombre());
            else if (!values.isSearchOnlyByPrimaryKey())
                e.addString("nombre", QueryParameter.Operator.EQUAL, values.getNombre());
        }

        if (values.getTiempo_efectivo() != Jornada.EMPTY_INT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addFloat("tiempo_efectivo", values.getTiempo_efectivo());
            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addFloat("tiempo_efectivo", QueryParameter.Operator.EQUAL, values.getTiempo_efectivo());
        }

        if (values.getDescripcion() != Jornada.EMPTY_STRING)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addString("descripcion", values.getDescripcion());
            else if (!values.isSearchOnlyByPrimaryKey())
                e.addString("descripcion", QueryParameter.Operator.EQUAL, values.getDescripcion());
        }
    }
}
