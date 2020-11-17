package com.syswave.datos.miempresa;

import com.syswave.datos.common.SingletonDataAccess;
import com.syswave.entidades.miempresa.ControlPrecio;
import datalayer.api.IMediatorDataSource;
import datalayer.events.DataGetEvent;
import datalayer.events.DataSendToQueryEvent;
import datalayer.events.DataSetEvent;
import datalayer.utils.QueryParameter;
import java.sql.SQLException;

/**
 * Establece conexión y permite realizar operaciones de CRUD
 *
 * @author Victor Manuel Bucio Vargas
 */
public class ControlPreciosDataAccess extends SingletonDataAccess<ControlPrecio>
{

    public final String insertProcedure = "control_precio_insert(?,?,?,?,?,?,?,?,?)";

    public ControlPreciosDataAccess(IMediatorDataSource mysource)
    {
        super(mysource, "control_precios", "id", "id_variante", "id_moneda", "descripcion",
              "costo_directo", "margen", "factor", "precio_final",
              "tiene_analisis", "id_area_precio"); //"es_tipo",
        setInsertProcedure(insertProcedure);
    }

    //---------------------------------------------------------------------
    /**
     * InsertProcedure is using an executeUpdateStoredProcedure
     *
     * @throws java.sql.SQLException
     */
    @Override
    protected void onConvertTransfer(ControlPrecio values, DataSetEvent e) throws SQLException, UnsupportedOperationException
    {
        if (e.getDML() == insertProcedure)
        {
            e.setInt(1, values.getIdVariante());//"vid_variante"
            e.setInt(2, values.getIdMoneda());//"vid_moneda"
            e.setString(3, values.getDescripcion(), 250);//"vdescripcion"
            e.setFloat(4, values.getCostoDirecto());//"vcosto_directo"
            e.setFloat(5, values.getMargen());//"vmargen"
            e.setInt(6, values.getFactor());//"vfactor"
            e.setFloat(7, values.getPrecioFinal());//"vprecio_final"
            e.setBoolean(8, values.tieneAnalisis());//"vtiene_analisis"
            //e.setInt(9, values.getEsTipo());//"ves_tipo"
            e.setInt(9, values.getIdAreaPrecio());//"vid_area_precio"
        }
    }

    //--------------------------------------------------------------------    
    @Override
    protected void onConvertKeyResult(DataGetEvent e, ControlPrecio value) throws SQLException, UnsupportedOperationException
    {
        for (int i = 1; i <= e.getColumnCount(); i++)
        {
            switch (e.getColumnName(i))
            {
                case "id":
                    value.setId(e.getInt(i));
                    break;
                case "id_variante":
                    value.setIdVariante(e.getInt(i));
                    break;
                case "id_moneda":
                    value.setIdMoneda(e.getInt(i));
                    break;
                case "descripcion":
                    value.setDescripcion(e.getString(i));
                    break;
                case "costo_directo":
                    value.setCostoDirecto(e.getFloat(i));
                    break;
                case "margen":
                    value.setMargen(e.getFloat(i));
                    break;
                case "factor":
                    value.setFactor(e.getInt(i));
                    break;
                case "precio_final":
                    value.setPrecioFinal(e.getFloat(i));
                    break;
                case "tiene_analisis":
                    value.setTieneAnalisis(e.getBoolean(i));
                    break;
                /*case "es_tipo":
                    value.setEsTipo(e.getInt(i));
                    break;*/
                case "id_area_precio":
                    value.setIdAreaPrecio(e.getInt(i));
                    break;
            }
        }
        /*  if (e.getColumnCount() > 0)
            value.setId(e.getInt(1));*/
    }

    //---------------------------------------------------------------------
    @Override
    public void onSendValues(DataSendToQueryEvent e, ControlPrecio values)
    {
        if (values.getId() != ControlPrecio.EMPTY_INT)
        {

            if (e.isCreateOperation())
                e.addInteger("id", values.getId());

            else if (e.isUpdateOperation())
            {
                //e.addInteger("id", values.getId());
                e.addInteger("id", QueryParameter.Operator.EQUAL, values.getId_Viejo());
            }

            else
                e.addInteger("id", QueryParameter.Operator.EQUAL, values.getId_Viejo());
        }

        if (values.getIdVariante() != ControlPrecio.EMPTY_INT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addInteger("id_variante", values.getIdVariante());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addInteger("id_variante", QueryParameter.Operator.EQUAL, values.getIdVariante());
        }

        if (values.getIdMoneda() != ControlPrecio.EMPTY_INT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addInteger("id_moneda", values.getIdMoneda());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addInteger("id_moneda", QueryParameter.Operator.EQUAL, values.getIdMoneda());
        }

        if (!values.getDescripcion().equals(ControlPrecio.EMPTY_STRING))
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addString("descripcion", values.getDescripcion(), 250);

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addString("descripcion", QueryParameter.Operator.EQUAL, values.getDescripcion());
        }

        if (values.getCostoDirecto() != ControlPrecio.EMPTY_FLOAT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addFloat("costo_directo", values.getCostoDirecto());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addFloat("costo_directo", QueryParameter.Operator.EQUAL, values.getCostoDirecto());
        }

        if (values.getMargen() != ControlPrecio.EMPTY_FLOAT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addFloat("margen", values.getMargen());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addFloat("margen", QueryParameter.Operator.EQUAL, values.getMargen());
        }

        if (values.getFactor() != ControlPrecio.EMPTY_INT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addInteger("factor", values.getFactor());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addInteger("factor", QueryParameter.Operator.EQUAL, values.getFactor());
        }

        if (values.getPrecioFinal() != ControlPrecio.EMPTY_FLOAT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addFloat("precio_final", values.getPrecioFinal());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addFloat("precio_final", QueryParameter.Operator.EQUAL, values.getPrecioFinal());
        }

        //Nota: Debido a la inexistencia de una representación de nulo para booleanos.
        //Es necesario solo manejarlos cuando el objeto reporta un cambio
        if (values.isSet())
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addBoolean("tiene_analisis", values.tieneAnalisis());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addBoolean("tiene_analisis", QueryParameter.Operator.EQUAL, values.tieneAnalisis());
        }

        /*if (values.getEsTipo() != ControlPrecio.EMPTY_INT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addInteger("es_tipo", values.getEsTipo());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addInteger("es_tipo", QueryParameter.Operator.EQUAL, values.getEsTipo());
        }*/

        if (values.getIdAreaPrecio() != ControlPrecio.EMPTY_INT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addInteger("id_area_precio", values.getIdAreaPrecio());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addInteger("id_area_precio", QueryParameter.Operator.EQUAL, values.getIdAreaPrecio());
        }
    }

    //---------------------------------------------------------------------
    @Override
    public ControlPrecio onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
    {
        ControlPrecio current = new ControlPrecio();

        for (int i = 1; i <= e.getColumnCount(); i++)
        {
            switch (e.getColumnName(i))
            {
                case "id":
                    current.setId(e.getInt(i));
                    break;
                case "id_variante":
                    current.setIdVariante(e.getInt(i));
                    break;
                case "id_moneda":
                    current.setIdMoneda(e.getInt(i));
                    break;
                case "descripcion":
                    current.setDescripcion(e.getString(i));
                    break;
                case "costo_directo":
                    current.setCostoDirecto(e.getFloat(i));
                    break;
                case "margen":
                    current.setMargen(e.getFloat(i));
                    break;
                case "factor":
                    current.setFactor(e.getInt(i));
                    break;
                case "precio_final":
                    current.setPrecioFinal(e.getFloat(i));
                    break;
                case "tiene_analisis":
                    current.setTieneAnalisis(e.getBoolean(i));
                    break;
                /*case "es_tipo":
                    current.setEsTipo(e.getInt(i));
                    break;*/
                case "id_area_precio":
                    current.setIdAreaPrecio(e.getInt(i));
                    break;
            }
        }

        current.setSearchOnlyByPrimaryKey(true);
        current.acceptChanges();

        return current;
    }
}
