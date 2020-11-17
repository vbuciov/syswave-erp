package com.syswave.datos.miempresa;

import com.syswave.datos.common.SingletonDataAccess;
import com.syswave.entidades.miempresa.MantenimientoCosto;
import datalayer.api.IMediatorDataSource;
import datalayer.events.DataGetEvent;
import datalayer.events.DataSendToQueryEvent;
import datalayer.events.DataSetEvent;
import datalayer.utils.QueryParameter;
import java.sql.SQLException;

/**
 *
 * @author sis2
 */
public class MantenimientoCostoDataAcces extends SingletonDataAccess<MantenimientoCosto>
{

    public final String insertProcedure = "mantenimiento_costo_insert(?,?,?)";

    public MantenimientoCostoDataAcces(IMediatorDataSource mysource)
    {
        super(mysource, "mantenimiento_costos", "consecutivo", "id_mantenimiento",
              "descripcion", "monto");
        setInsertProcedure(insertProcedure);
    }

    //---------------------------------------------------------------------------
    @Override
    public MantenimientoCosto onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
    {
        MantenimientoCosto current = new MantenimientoCosto();

        for (int i = 1; i <= e.getColumnCount(); i++)
        {
            switch (e.getColumnName(i))
            {
                case "consecutivo":
                    current.setConsecutivo(e.getInt(i));
                    break;
                case "id_mantenimiento":
                    current.setIdMantenimiento(e.getInt(i));
                    break;
                case "descripcion":
                    current.setDescripcion(e.getString(i));
                    break;
                case "monto":
                    current.setMonto(e.getFloat(i));
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
    protected void onConvertTransfer(MantenimientoCosto values, DataSetEvent e) throws SQLException, UnsupportedOperationException
    {
        if (e.getDML() == insertProcedure)
        {
            e.setInt(1, values.getIdMantenimiento());//"vid_mantemiento"
            e.setString(2, values.getDescripcion());//"vdescripcion"
            e.setFloat(3, values.getMonto());//"vmonto"
        }
    }

    //--------------------------------------------------------------------    
    @Override
    protected void onConvertKeyResult(DataGetEvent e, MantenimientoCosto value) throws SQLException, UnsupportedOperationException
    {
        for (int i = 1; i <= e.getColumnCount(); i++)
        {
            switch (e.getColumnName(i))
            {
                case "consecutivo":
                    value.setConsecutivo(e.getInt(i));
                    break;
                case "id_mantenimiento":
                    value.setIdMantenimiento(e.getInt(i));
                    break;
                case "descripcion":
                    value.setDescripcion(e.getString(i));
                    break;
                case "monto":
                    value.setMonto(e.getFloat(i));
                    break;
            }
        }
        /*  if (e.getColumnCount() > 0)
            value.setId(e.getInt(1));*/
    }

    //---------------------------------------------------------------------
    @Override
    public void onSendValues(DataSendToQueryEvent e, MantenimientoCosto values)
    {
        if (values.getConsecutivo()!= MantenimientoCosto.EMPTY_INT)
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

        if (values.getIdMantenimiento()!= MantenimientoCosto.EMPTY_INT)
        {

            if (e.isCreateOperation())
                e.addInteger("id_mantenimiento", values.getIdMantenimiento());

            else if (e.isUpdateOperation())
            {
                e.addInteger("id_mantenimeinto", values.getIdMantenimiento());
                e.addInteger("id_mantenimeinto", QueryParameter.Operator.EQUAL, values.getIdMantenimiento_Viejo());
            }

            else
                e.addInteger("id_mantenimiento", QueryParameter.Operator.EQUAL, values.getIdMantenimiento_Viejo());
        }

        if (values.getDescripcion() != MantenimientoCosto.EMPTY_STRING)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addString("descripcion", values.getDescripcion());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addString("descripcion", QueryParameter.Operator.EQUAL, values.getDescripcion());
        }

        if (values.getMonto() != MantenimientoCosto.EMPTY_FLOAT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addFloat("monto", values.getMonto());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addFloat("monto", QueryParameter.Operator.EQUAL, values.getMonto());
        }
    }
}
