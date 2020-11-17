package com.syswave.datos.miempresa;

import com.syswave.datos.common.SingletonDataAccess;
import com.syswave.entidades.miempresa.DesgloseCosto;
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
public class DesglosesCostosDataAccess extends SingletonDataAccess<DesgloseCosto>
{

    public final String insertProcedure = "desglose_costos_insert(?,?,?,?,?,?,?,?,?,?,?)";
    public final String updateProcedure = "desglose_costos_update(?,?,?,?,?,?,?,?,?,?,?)";

    public DesglosesCostosDataAccess(IMediatorDataSource mysource)
    {
        super(mysource, "desglose_costos", "linea", "id_precio_variable", 
              "id_precio_indirecto", "cantidad", "factor_cantidad", 
              "precio", "factor_precio", 
              "subtotal", "factor", "monto", "total", "observacion");
        setInsertProcedure(insertProcedure);
        setUpdateProcedure(updateProcedure);
    }

    //---------------------------------------------------------------------
    /**
     * InsertProcedure is using an executeUpdateStoredProcedure
     *
     * @throws java.sql.SQLException
     */
    @Override
    protected void onConvertTransfer(DesgloseCosto values, DataSetEvent e) throws SQLException, UnsupportedOperationException
    {
        if (e.getDML() == insertProcedure)
        {
            e.setInt(1, values.getIdPrecioVariable());//"vid_precio_variable"
            e.setInt(2, values.getIdPrecioIndirecto());//"vid_precio_indirecto"
            e.setFloat(3, values.getCantidad());//"vcantidad"
            e.setFloat(4, values.getFactor_cantidad());//vfactor_cantidad
            e.setFloat(5, values.getPrecio());//"vprecio"
            e.setFloat(6, values.getFactor_precio()); //vfactor_precio
            e.setFloat(7, values.getSubtotal());//"vsubtotal"
            e.setInt(8, values.getFactor());//"vfactor"
            e.setFloat(9, values.getMonto());//"vmonto"
            e.setFloat(10, values.getTotal());//"vtotal"
            e.setString(11, values.getObservacion(), 500);//"vobservacion"
        }

        else if (e.getDML() == updateProcedure)
        {
            e.setInt(1, values.getLinea_Viejo());//"vlinea_old"
            e.setInt(2, values.getIdPrecioVariable_Viejo());//"vid_precio_variable_old"
            e.setInt(3, values.getIdPrecioVariable());//"vid_precio_variable"
           e.setInt(4, values.getIdPrecioIndirecto());//"vid_precio_indirecto_new"
            e.setFloat(5, values.getCantidad());//"vcantidad"
            e.setFloat(6, values.getPrecio());//"vprecio"
            e.setFloat(7, values.getSubtotal());//"vsubtotal"
            e.setInt(8, values.getFactor());//"vfactor"
            e.setFloat(9, values.getMonto());//"vmonto"
            e.setFloat(10, values.getTotal());//"vtotal"
            e.setString(11, values.getObservacion(), 500);//"vobservacion"
        }
    }

    //--------------------------------------------------------------------    
    @Override
    protected void onConvertKeyResult(DataGetEvent e, DesgloseCosto value) throws SQLException, UnsupportedOperationException
    {
        for (int i = 1; i <= e.getColumnCount(); i++)
        {
            switch (e.getColumnName(i))
            {
                case "linea":
                    value.setLinea(e.getInt(i));
                    break;
                case "id_precio_variable":
                    value.setIdPrecioVariable(e.getInt(i));
                    break;
                case "id_precio_indirecto":
                    value.setIdPrecioIndirecto(e.getInt(i));
                    break;
                case "cantidad":
                    value.setCantidad(e.getFloat(i));
                    break;
                case "factor_cantidad":
                    value.setFactor_cantidad(e.getFloat(i));
                    break;
                case "precio":
                    value.setPrecio(e.getFloat(i));
                    break;
                case "factor_precio":
                    value.setFactor_precio(e.getFloat(i));
                    break;
                case "subtotal":
                    value.setSubtotal(e.getFloat(i));
                    break;
                case "factor":
                    value.setFactor(e.getInt(i));
                    break;
                case "monto":
                    value.setMonto(e.getFloat(i));
                    break;
                case "total":
                    value.setTotal(e.getFloat(i));
                    break;
                case "observacion":
                    value.setObservacion(e.getString(i));
                    break;
            }
        }
        /*  if (e.getColumnCount() > 0)
            value.setId(e.getInt(1));*/
    }

    //---------------------------------------------------------------------
    @Override
    public void onSendValues(DataSendToQueryEvent e, DesgloseCosto values)
    {
        if (values.getLinea() != DesgloseCosto.EMPTY_INT)
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

        if (values.getIdPrecioVariable()!= DesgloseCosto.EMPTY_INT)
        {
            if (e.isCreateOperation())
                e.addInteger("id_precio_variable", values.getIdPrecioVariable());

            else if (e.isUpdateOperation())
            {
                e.addInteger("id_precio_variable", values.getIdPrecioVariable());
                e.addInteger("id_precio_variable", QueryParameter.Operator.EQUAL, values.getIdPrecioVariable_Viejo());
            }

            else
                e.addInteger("id_precio_variable", QueryParameter.Operator.EQUAL, values.getIdPrecioIndirecto());
        }
        
        if (values.getCantidad() != DesgloseCosto.EMPTY_FLOAT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addInteger("id_precio_indirecto", values.getIdPrecioIndirecto());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addInteger("id_precio_indirecto", QueryParameter.Operator.EQUAL, values.getIdPrecioIndirecto());
        }

        if (values.getCantidad() != DesgloseCosto.EMPTY_FLOAT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addFloat("cantidad", values.getCantidad());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addFloat("cantidad", QueryParameter.Operator.EQUAL, values.getCantidad());
        }
        
        if (values.getFactor_cantidad() != DesgloseCosto.EMPTY_FLOAT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addFloat("factor_cantidad", values.getFactor_cantidad());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addFloat("factor_cantidad", QueryParameter.Operator.EQUAL, values.getFactor_cantidad());
        }

        if (values.getPrecio() != DesgloseCosto.EMPTY_FLOAT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addFloat("precio", values.getPrecio());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addFloat("precio", QueryParameter.Operator.EQUAL, values.getPrecio());
        }
        
        if (values.getFactor_precio()!= DesgloseCosto.EMPTY_FLOAT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addFloat("factor_precio", values.getFactor_precio());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addFloat("factor_precio", QueryParameter.Operator.EQUAL, values.getFactor_precio());
        }

        if (values.getSubtotal() != DesgloseCosto.EMPTY_FLOAT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addFloat("subtotal", values.getSubtotal());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addFloat("subtotal", QueryParameter.Operator.EQUAL, values.getSubtotal());
        }

        if (values.getFactor() != DesgloseCosto.EMPTY_INT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addInteger("factor", values.getFactor());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addInteger("factor", QueryParameter.Operator.EQUAL, values.getFactor());
        }

        if (values.getMonto() != DesgloseCosto.EMPTY_FLOAT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addFloat("monto", values.getMonto());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addFloat("monto", QueryParameter.Operator.EQUAL, values.getMonto());
        }

        if (values.getTotal() != DesgloseCosto.EMPTY_FLOAT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addFloat("total", values.getTotal());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addFloat("total", QueryParameter.Operator.EQUAL, values.getTotal());
        }

        if (!values.getObservacion().equals(DesgloseCosto.EMPTY_STRING))
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addString("observacion", values.getObservacion(), 500);

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addString("observacion", QueryParameter.Operator.EQUAL, values.getObservacion());
        }
    }

    //---------------------------------------------------------------------
    @Override
    public DesgloseCosto onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
    {
        DesgloseCosto current = new DesgloseCosto();

        for (int i = 1; i <= e.getColumnCount(); i++)
        {
            switch (e.getColumnName(i))
            {
                case "linea":
                    current.setLinea(e.getInt(i));
                    break;
                case "id_precio_variable":
                    current.setIdPrecioVariable(e.getInt(i));
                    break;
                case "id_precio_indirecto":
                    current.setIdPrecioIndirecto(e.getInt(i));
                    break;
                case "cantidad":
                    current.setCantidad(e.getFloat(i));
                    break;
                case "factor_cantidad":
                    current.setFactor_cantidad(e.getFloat(i));
                    break;
                case "precio":
                    current.setPrecio(e.getFloat(i));
                    break;
                case "factor_precio":
                    current.setPrecio(e.getFloat(i));
                    break;
                case "subtotal":
                    current.setSubtotal(e.getFloat(i));
                    break;
                case "factor":
                    current.setFactor(e.getInt(i));
                    break;
                case "monto":
                    current.setMonto(e.getFloat(i));
                    break;
                case "total":
                    current.setTotal(e.getFloat(i));
                    break;
                case "observacion":
                    current.setObservacion(e.getString(i));
                    break;
            }
        }

        current.setSearchOnlyByPrimaryKey(true);
        current.acceptChanges();

        return current;
    }
}