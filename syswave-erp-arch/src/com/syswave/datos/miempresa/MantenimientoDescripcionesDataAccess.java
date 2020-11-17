package com.syswave.datos.miempresa;

import com.syswave.datos.common.SingletonDataAccess;
import com.syswave.entidades.miempresa.MantenimientoDescripcion;
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
public class MantenimientoDescripcionesDataAccess extends SingletonDataAccess<MantenimientoDescripcion>
{

    private final String insertProcedure = "mantenimiento_descripcion_insert(?,?,?)";
    private final String updateProcedure = "mantenimiento_descripcion_update(?,?,?,?,?)";

    public MantenimientoDescripcionesDataAccess(IMediatorDataSource mysource)
    {
        super(mysource, "mantenimiento_descripciones", "linea", "id_mantenimiento",
              "id_tipo_descripcion", "texto");
        setInsertProcedure(insertProcedure);
        setUpdateProcedure(updateProcedure);
    }

    //---------------------------------------------------------------------
    @Override
    public MantenimientoDescripcion onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
    {
        MantenimientoDescripcion current = new MantenimientoDescripcion();

        for (int i = 1; i <= e.getColumnCount(); i++)
        {
            switch (e.getColumnName(i))
            {
                case "linea":
                    current.setLinea(e.getInt(i));
                    break;
                case "id_mantenimiento":
                    current.setIdMantenimiento(e.getInt(i));
                    break;
                case "id_tipo_descripcion":
                    current.setIdTipoDescripcion(e.getInt(i));
                    break;
                case "texto":
                    current.setTexto(e.getString(i));
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
    protected void onConvertTransfer(MantenimientoDescripcion values, DataSetEvent e) throws SQLException, UnsupportedOperationException
    {
        if (e.getDML() == insertProcedure)
        {
            e.setInt(1, values.getIdMantenimiento());//"vid_mantenimiento"
            e.setInt(2, values.getIdTipoDescripcion());//"vid_tipo_descripcion"
            e.setString(3, values.getTexto(), 255);//"vtexto"
        }

        else if (e.getDML() == updateProcedure)
        {
            e.setInt(1, values.getLinea_Viejo());//"vlinea_old"
            e.setInt(2, values.getIdMantenimiento_Viejo());//"vid_mantenimiento_old"
            e.setInt(3, values.getIdMantenimiento());//"vid_mantenimiento_new"
            e.setInt(4, values.getIdTipoDescripcion());//"vid_tipo_descripcion"
            e.setString(5, values.getTexto(), 255);//"vtexto"
        }
    }

    //--------------------------------------------------------------------    
    @Override
    protected void onConvertKeyResult(DataGetEvent e, MantenimientoDescripcion value) throws SQLException, UnsupportedOperationException
    {
        for (int i = 1; i <= e.getColumnCount(); i++)
        {
            switch (e.getColumnName(i))
            {
                case "linea":
                    value.setLinea(e.getInt(i));
                    break;
                case "id_mantenimiento":
                    value.setIdMantenimiento(e.getInt(i));
                    break;
                case "id_tipo_descripcion":
                    value.setIdTipoDescripcion(e.getInt(i));
                    break;
                case "texto":
                    value.setTexto(e.getString(i));
                    break;
            }
        }
        /*  if (e.getColumnCount() > 0)
            value.setId(e.getInt(1));*/
    }

    //---------------------------------------------------------------------
    @Override
    public void onSendValues(DataSendToQueryEvent e, MantenimientoDescripcion values)
    {
        if (values.getLinea()!= MantenimientoDescripcion.EMPTY_INT)
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

        if (values.getIdMantenimiento()!= MantenimientoDescripcion.EMPTY_INT)
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

        if (values.getIdTipoDescripcion() != MantenimientoDescripcion.EMPTY_INT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addInteger("id_tipo_descripcion", values.getIdTipoDescripcion());

            else if (!values.isSearchOnlyByPrimaryKey())
                e.addInteger("id_tipo_descripcion", QueryParameter.Operator.EQUAL, values.getIdTipoDescripcion());
        }

        if (values.getTexto() != MantenimientoDescripcion.EMPTY_STRING)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addString("texto", values.getTexto(), 255);

            else if (!values.isSearchOnlyByPrimaryKey())
                e.addString("texto", QueryParameter.Operator.EQUAL, values.getTexto());
        }
    }
}
