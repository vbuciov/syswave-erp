package com.syswave.datos.miempresa;

import com.syswave.datos.common.SingletonDataAccess;
import com.syswave.entidades.miempresa.Mantenimiento_tiene_Actividad;
import datalayer.api.IMediatorDataSource;
import datalayer.events.DataGetEvent;
import datalayer.events.DataSendToQueryEvent;
import datalayer.utils.QueryParameter;
import java.sql.SQLException;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class MantenimientoTieneActividadesDataAccess extends SingletonDataAccess<Mantenimiento_tiene_Actividad>
{

    public MantenimientoTieneActividadesDataAccess(IMediatorDataSource mysource)
    {
        super(mysource, "mantenimiento_tiene_actividades", "id_mantenimiento", "linea_plan", "id_variante_plan");
        setWithAutoID(false);
    }

    //---------------------------------------------------------------------
    @Override
    public Mantenimiento_tiene_Actividad onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
    {
        Mantenimiento_tiene_Actividad current = new Mantenimiento_tiene_Actividad();

        for (int i = 1; i <= e.getColumnCount(); i++)
        {
            switch (e.getColumnName(i))
            {
                case "id_mantenimiento":
                    current.setIdMantenimiento(e.getInt(i));
                    break;
                case "linea_plan":
                    current.setLinea(e.getInt(i));
                    break;
                case "id_variante_plan":
                    current.setIdVariante(e.getInt(i));
                    break;
            }
        }

        //Los objetos recuperados promueven busquedas solo por la llave.
        current.setSearchOnlyByPrimaryKey(true);
        current.acceptChanges();

        return current;
    }

    //---------------------------------------------------------------------
    @Override
    public void onSendValues(DataSendToQueryEvent e, Mantenimiento_tiene_Actividad values)
    {
        if (values.getIdMantenimiento()!= Mantenimiento_tiene_Actividad.EMPTY_INT)
        {

            if (e.isCreateOperation())
                e.addInteger("id_mantenimiento", values.getIdMantenimiento());

            else if (e.isUpdateOperation())
            {
                e.addInteger("id_mantenimiento", values.getIdMantenimiento());
                e.addInteger("id_mantenimiento", QueryParameter.Operator.EQUAL, values.getIdMantenimiento_Viejo());
            }

            else
                e.addInteger("id_mantenimiento", QueryParameter.Operator.EQUAL, values.getIdMantenimiento_Viejo());
        }

        if (values.getLinea()!= Mantenimiento_tiene_Actividad.EMPTY_INT)
        {

            if (e.isCreateOperation())
                e.addInteger("linea_plan", values.getLinea());

            else if (e.isUpdateOperation())
            {
                e.addInteger("linea_plan", values.getLinea());
                e.addInteger("linea_plan", QueryParameter.Operator.EQUAL, values.getLinea_Viejo());
            }

            else
                e.addInteger("linea_plan", QueryParameter.Operator.EQUAL, values.getLinea_Viejo());
        }

        if (values.getIdVariante() != Mantenimiento_tiene_Actividad.EMPTY_INT)
        {
            if (e.isCreateOperation())
                e.addInteger("id_variante_plan", values.getIdVariante());

            else if (e.isUpdateOperation())
            {
                e.addInteger("id_variante_plan", values.getIdVariante());
                e.addInteger("id_variante_plan", QueryParameter.Operator.EQUAL, values.getIdVariante_Viejo());
            }

            else
                e.addInteger("id_variante_plan", QueryParameter.Operator.EQUAL, values.getIdVariante_Viejo());
        }
    }
}
