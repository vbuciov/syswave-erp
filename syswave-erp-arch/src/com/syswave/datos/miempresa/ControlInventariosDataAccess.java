package com.syswave.datos.miempresa;

import com.syswave.datos.common.SingletonDataAccess;
import com.syswave.entidades.miempresa.ControlInventario;
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
public class ControlInventariosDataAccess extends SingletonDataAccess<ControlInventario>
{

    public final String insertProcedure = "control_inventario_insert(?,?,?,?,?,?,?,?,?)";
    public final String updateProcedure = "control_inventario_update(?,?,?,?,?,?,?,?,?,?,?)";

    public ControlInventariosDataAccess(IMediatorDataSource mysource)
    {
        super(mysource, "control_inventarios", "consecutivo", "id_variante", "lote",
              "existencia", "minimo", "maximo", "reorden",
              "fecha_entrada", "fecha_caducidad", "fecha_devolucion");
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
    protected void onConvertTransfer(ControlInventario values, DataSetEvent e) throws SQLException, UnsupportedOperationException
    {
        if (e.getDML() == insertProcedure)
        {
            e.setInt(1, values.getIdVariante()); //"vid_variante"
            e.setString(2, values.getLote(), 45);//"vlote"
            e.setFloat(3, values.getExistencia());//"vexistencia"
            e.setFloat(4, values.getMinimo());//"vminimo"
            e.setFloat(5, values.getMaximo());//"vmaximo"
            e.setFloat(6, values.getReorden());//"vreorden"
            e.setDate(7, values.getFecha_entrada());//"vfecha_entrada"
            e.setDate(8, values.getFecha_caducidad());//"vfecha_caducidad"
            e.setDate(9, values.getFecha_devolucion());//"vfecha_devolucion"
        }

        else if (e.getDML() == updateProcedure)
        {
            e.setInt(1, values.getConsecutivo_Viejo());//"vconsecutivo_old"
            e.setInt(2, values.getIdVariante_Viejo());//"vid_variante_old"
            e.setInt(3, values.getIdVariante());//"vid_variante_new"
            e.setString(4, values.getLote(), 45);//"vlote"
            e.setFloat(5, values.getExistencia());//"vexistencia"
            e.setFloat(6, values.getMinimo());//"vminimo"
            e.setFloat(7, values.getMaximo()); //"vmaximo"
            e.setFloat(8, values.getReorden());//"vreorden"
            e.setDate(9, values.getFecha_entrada());//"vfecha_entrada"
            e.setDate(10, values.getFecha_caducidad());//"vfecha_caducidad"
            e.setDate(11, values.getFecha_devolucion());//"vfecha_devolucion"
        }
    }

    //--------------------------------------------------------------------    
    @Override
    protected void onConvertKeyResult(DataGetEvent e, ControlInventario value) throws SQLException, UnsupportedOperationException
    {
        for (int i = 1; i <= e.getColumnCount(); i++)
        {
            switch (e.getColumnName(i))
            {
                case "consecutivo":
                    value.setConsecutivo(e.getInt(i));
                    break;
                case "id_variante":
                    value.setIdVariante(e.getInt(i));
                    break;
                case "lote":
                    value.setLote(e.getString(i));
                    break;
                case "existencia":
                    value.setExistencia(e.getFloat(i));
                    break;
                case "minimo":
                    value.setMinimo(e.getFloat(i));
                    break;
                case "maximo":
                    value.setMaximo(e.getFloat(i));
                    break;
                case "reorden":
                    value.setReorden(e.getFloat(i));
                    break;
                case "fecha_entrada":
                    value.setFecha_entrada(e.getDate(i));
                    break;
                case "fecha_caducidad":
                    value.setFecha_caducidad(e.getDate(i));
                    break;
                case "fecha_devolucion":
                    value.setFecha_devolucion(e.getDate(i));
                    break;
            }
        }
        /*  if (e.getColumnCount() > 0)
            value.setId(e.getInt(1));*/
    }

    //---------------------------------------------------------------------
    @Override
    public void onSendValues(DataSendToQueryEvent e, ControlInventario values)
    {
        if (values.getConsecutivo()!= ControlInventario.EMPTY_INT)
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

        if (values.getIdVariante()!= ControlInventario.EMPTY_INT)
        {

            if (e.isCreateOperation())
                e.addInteger("id_variante", values.getIdVariante());

            else if (e.isUpdateOperation())
            {
                e.addInteger("id_variante", values.getIdVariante());
                e.addInteger("id_variante", QueryParameter.Operator.EQUAL, values.getIdVariante_Viejo());
            }

            else
                e.addInteger("id_variante", QueryParameter.Operator.EQUAL, values.getIdVariante());
        }

        if (!values.getLote().equals(ControlInventario.EMPTY_STRING))
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addString("lote", values.getLote(), 45);

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addString("lote", QueryParameter.Operator.EQUAL, values.getLote());
        }

        if (values.getExistencia() != ControlInventario.EMPTY_FLOAT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addFloat("existencia", values.getExistencia());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addFloat("existencia", QueryParameter.Operator.EQUAL, values.getExistencia());
        }

        if (values.getMinimo() != ControlInventario.EMPTY_FLOAT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addFloat("minimo", values.getMinimo());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addFloat("minimo", QueryParameter.Operator.EQUAL, values.getMinimo());
        }

        if (values.getMaximo() != ControlInventario.EMPTY_FLOAT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addFloat("maximo", values.getMaximo());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addFloat("maximo", QueryParameter.Operator.EQUAL, values.getMaximo());
        }

        if (values.getReorden() != ControlInventario.EMPTY_FLOAT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addFloat("reorden", values.getReorden());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addFloat("reorden", QueryParameter.Operator.EQUAL, values.getReorden());
        }

        if (values.getFecha_entrada() != ControlInventario.EMPTY_DATE)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addDate("fecha_entrada", values.getFecha_entrada());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addDate("fecha_entrada", QueryParameter.Operator.EQUAL, values.getFecha_entrada());
        }

        if (values.getFecha_caducidad() != ControlInventario.EMPTY_DATE)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addDate("fecha_caducidad", values.getFecha_caducidad());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addDate("fecha_caducidad", QueryParameter.Operator.EQUAL, values.getFecha_caducidad());
        }

        if (values.getFecha_devolucion() != ControlInventario.EMPTY_DATE)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addDate("fecha_devolucion", values.getFecha_devolucion());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addDate("fecha_devolucion", QueryParameter.Operator.EQUAL, values.getFecha_devolucion());
        }
    }

    //---------------------------------------------------------------------
    @Override
    public ControlInventario onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
    {
        ControlInventario current = new ControlInventario();

        for (int i = 1; i <= e.getColumnCount(); i++)
        {
            switch (e.getColumnName(i))
            {
                case "consecutivo":
                    current.setConsecutivo(e.getInt(i));
                    break;
                case "id_variante":
                    current.setIdVariante(e.getInt(i));
                    break;
                case "lote":
                    current.setLote(e.getString(i));
                    break;
                case "existencia":
                    current.setExistencia(e.getFloat(i));
                    break;
                case "minimo":
                    current.setMinimo(e.getFloat(i));
                    break;
                case "maximo":
                    current.setMaximo(e.getFloat(i));
                    break;
                case "reorden":
                    current.setReorden(e.getFloat(i));
                    break;
                case "fecha_entrada":
                    current.setFecha_entrada(e.getDate(i));
                    break;
                case "fecha_caducidad":
                    current.setFecha_caducidad(e.getDate(i));
                    break;
                case "fecha_devolucion":
                    current.setFecha_devolucion(e.getDate(i));
                    break;
            }
        }

        current.setSearchOnlyByPrimaryKey(true);
        current.acceptChanges();

        return current;
    }
}
