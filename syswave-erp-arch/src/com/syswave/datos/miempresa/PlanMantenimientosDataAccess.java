package com.syswave.datos.miempresa;

import com.syswave.datos.common.SingletonDataAccess;
import com.syswave.entidades.miempresa.PlanMantenimiento;
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
public class PlanMantenimientosDataAccess extends SingletonDataAccess<PlanMantenimiento>
{

    private final String insertProcedure = "plan_mantenimiento_insert(?,?,?)";
    private final String updateProcedure = "plan_mantenimiento_update(?,?,?,?,?)";

    public PlanMantenimientosDataAccess(IMediatorDataSource mysource)
    {
        super(mysource, "plan_mantenimientos", "linea", "id_variante", "actividad", "es_activo");
        setInsertProcedure(insertProcedure);
        setUpdateProcedure(updateProcedure);
    }

    //--------------------------------------------------------------------
    @Override
    public PlanMantenimiento onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
    {
        PlanMantenimiento current = new PlanMantenimiento();

        for (int i = 1; i <= e.getColumnCount(); i++)
        {
            switch (e.getColumnName(i))
            {
                case "linea":
                    current.setLinea(e.getInt(i));
                    break;
                case "id_variante":
                    current.setIdVariante(e.getInt(i));
                    break;
                case "actividad":
                    current.setActividad(e.getString(i));
                    break;
                case "es_activo":
                    current.setActivo(e.getBoolean(i));
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
    protected void onConvertTransfer(PlanMantenimiento values, DataSetEvent e) throws SQLException, UnsupportedOperationException
    {
        if (e.getDML() == insertProcedure)
        {
            e.setInt(1, values.getIdVariante());//"vid_variante"
            e.setString(2, values.getActividad(), 255);//"vactividad"
            e.setBoolean(3, values.esActivo());//"ves_activo"
        }

        else if (e.getDML() == updateProcedure)
        {
            e.setInt(1, values.getLinea_Viejo());//"vlinea_old"
            e.setInt(2, values.getIdVariante_Viejo());//"vid_variante_old"
            e.setInt(3, values.getIdVariante());//"vid_variante_new"
            e.setString(4, values.getActividad(), 255);//"vactividad"
            e.setBoolean(5, values.esActivo());//"ves_activo"
        }
    }

    //--------------------------------------------------------------------    
    @Override
    protected void onConvertKeyResult(DataGetEvent e, PlanMantenimiento value) throws SQLException, UnsupportedOperationException
    {
        for (int i = 1; i <= e.getColumnCount(); i++)
        {
            switch (e.getColumnName(i))
            {
                case "linea":
                    value.setLinea(e.getInt(i));
                    break;
                case "id_variante":
                    value.setIdVariante(e.getInt(i));
                    break;
                case "actividad":
                    value.setActividad(e.getString(i));
                    break;
                case "es_activo":
                    value.setActivo(e.getBoolean(i));
                    break;
            }
        }
        /*  if (e.getColumnCount() > 0)
            value.setId(e.getInt(1));*/
    }

    //---------------------------------------------------------------------
    @Override
    public void onSendValues(DataSendToQueryEvent e, PlanMantenimiento values)
    {
        if (values.getLinea()!= PlanMantenimiento.EMPTY_INT)
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

        if (values.getIdVariante()!= PlanMantenimiento.EMPTY_INT)
        {

            if (e.isCreateOperation())
                e.addInteger("id_variante", values.getIdVariante());

            else if (e.isUpdateOperation())
            {
                e.addInteger("id_variante", values.getIdVariante());
                e.addInteger("id_variante", QueryParameter.Operator.EQUAL, values.getIdVariante_Viejo());
            }

            else
                e.addInteger("id_variante", QueryParameter.Operator.EQUAL, values.getIdVariante_Viejo());
        }

        if (values.getActividad() != PlanMantenimiento.EMPTY_STRING)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addString("actividad", values.getActividad(), 32);

            else if (!values.isSearchOnlyByPrimaryKey())
                e.addString("actividad", QueryParameter.Operator.EQUAL, values.getActividad());
        }

        if (values.isSet())
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addBoolean("es_activo", values.esActivo());

            else if (!values.isSearchOnlyByPrimaryKey())
                e.addBoolean("es_activo", QueryParameter.Operator.EQUAL, values.esActivo());
        }
    }
}
