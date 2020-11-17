package com.syswave.datos.miempresa;

import com.syswave.datos.common.SingletonDataAccess;
import com.syswave.entidades.miempresa.ControlAlmacen;
import datalayer.api.IMediatorDataSource;
import datalayer.events.DataGetEvent;
import datalayer.events.DataSendToQueryEvent;
import datalayer.events.DataSetEvent;
import datalayer.utils.Join;
import datalayer.utils.ProductRelation;
import datalayer.utils.QueryParameter;
import datalayer.utils.RelationOptions;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class ControlAlmacenesDataAccess extends SingletonDataAccess<ControlAlmacen>
{

    public final String insertProcedure = "control_almacen_insert(?,?,?,?,?,?,?)";
    public final String updateProcedure = "control_almacen_update(?,?,?,?,?,?,?,?,?)";

    public ControlAlmacenesDataAccess(IMediatorDataSource mysource)
    {
        super(mysource, "control_almacenes", "entrada", "id_ubicacion", "id_variante",
              "consecutivo", "cantidad", "serie", "valor_acumulado",
              "observaciones");
        setBasicOrderBy("id_ubicacion", "entrada");
        setInsertProcedure(insertProcedure);
        setUpdateProcedure(updateProcedure);
    }

    //-------------------------------------------------------------------
    public List<ControlAlmacen> retrieveProduct()
    {
        ProductRelation series;
        series = new ProductRelation(getTable() + " AS ca",
                                     new Join[]
                                     {
                                         new Join("control_inventarios AS ci", "ci.id_variante = ca.id_variante AND  ci.consecutivo = ca.consecutivo"),
                                         new Join("bien_variantes AS bv", "bv.id = ci.id_variante")
                                     },
                                     new String[]
                                     {
                                         "entrada",
                                         "id_ubicacion",
                                         "ca.id_variante",
                                         "ca.consecutivo",
                                         "cantidad",
                                         "serie",
                                         "valor_acumulado",
                                         "mantenimiento_como",
                                         "observaciones"
                                     },
                                     RelationOptions.TypeOption.ORDER_BY,
                                     new String[]
                                     {
                                         "id_ubicacion", "entrada"
                                     });

        return submitQuery(series);
    }

    //---------------------------------------------------------------------
    /**
     * InsertProcedure is using an executeUpdateStoredProcedure
     *
     * @throws java.sql.SQLException
     */
    @Override
    protected void onConvertTransfer(ControlAlmacen values, DataSetEvent e) throws SQLException, UnsupportedOperationException
    {
        if (e.getDML() == insertProcedure)
        {
            e.setInt(1, values.getIdUbicacion()); //"vid_ubicacion",
            e.setInt(2, values.getIdVariante());//"vid_variante"
            e.setInt(3, values.getConsecutivo()); //"vconsecutivo"
            e.setFloat(4, values.getCantidad());//"vcantidad"
            e.setString(5, values.getSerie(), 45);//"vserie"
            e.setInt(6, values.getValorAcumulado());//"vvalor_acumulado"
            e.setString(7, values.getObservaciones(), 255); //"vobservaciones"
        }

        else if (e.getDML() == updateProcedure)
        {
            e.setInt(1, values.getEntrada_Viejo()); //"vid_entrada_old"
            e.setInt(2, values.getIdUbicacion_Viejo());//"vid_ubicacion_old"
            e.setInt(3, values.getIdUbicacion()); //"vid_ubicacion_new"
            e.setInt(4, values.getIdVariante()); //"vid_variante"
            e.setInt(5, values.getConsecutivo()); //"vconsecutivo"
            e.setFloat(6, values.getCantidad()); //"vcantidad"
            e.setString(7, values.getSerie(), 45); //"vserie"
            e.setInt(8, values.getValorAcumulado()); //"vvalor_acumulado"
            e.setString(9, values.getObservaciones(), 255); //"vobservaciones"
        }
    }

    //--------------------------------------------------------------------    
    @Override
    protected void onConvertKeyResult(DataGetEvent e, ControlAlmacen value) throws SQLException, UnsupportedOperationException
    {
        for (int i = 1; i <= e.getColumnCount(); i++)
        {
            switch (e.getColumnName(i))
            {
                case "entrada":
                    value.setEntrada(e.getInt(i));
                    break;
                case "id_ubicacion":
                    value.setIdUbicacion(e.getInt(i));
                    break;
                case "id_variante":
                    value.setIdVariante(e.getInt(i));
                    break;
                case "consecutivo":
                    value.setConsecutivo(e.getInt(i));
                    break;
                case "cantidad":
                    value.setCantidad(e.getFloat(i));
                    break;
                case "serie":
                    value.setSerie(e.getString(i));
                    break;
                case "observaciones":
                    value.setObservaciones(e.getString(i));
                    break;
                case "valor_acumulado":
                    value.setValoAcumulado(e.getInt(i));
                    break;
                case "mantenimiento_como":
                    value.getHasOneControlInventario().getHasOneBienVariante().setMantenimientoComo(e.getInt(i));
                    break;
            }
        }
        /*  if (e.getColumnCount() > 0)
            value.setId(e.getInt(1));*/
    }

    //---------------------------------------------------------------------
    @Override
    public void onSendValues(DataSendToQueryEvent e, ControlAlmacen values)
    {
        if (values.getEntrada()!= ControlAlmacen.EMPTY_INT)
        {

            if (e.isCreateOperation())
                e.addInteger("entrada", values.getEntrada());

            else if (e.isUpdateOperation())
            {
                e.addInteger("entrada", values.getEntrada());
                e.addInteger("entrada", QueryParameter.Operator.EQUAL, values.getEntrada_Viejo());
            }

            else
                e.addInteger("entrada", QueryParameter.Operator.EQUAL, values.getEntrada_Viejo());
        }

        if (values.getIdUbicacion()!= ControlAlmacen.EMPTY_INT)
        {

            if (e.isCreateOperation())
                e.addInteger("id_ubicacion", values.getIdUbicacion());

            else if (e.isUpdateOperation())
            {
                e.addInteger("id_ubicacion", values.getIdUbicacion());
                e.addInteger("id_ubicacion", QueryParameter.Operator.EQUAL, values.getIdUbicacion_Viejo());
            }

            else
                e.addInteger("id_ubicacion", QueryParameter.Operator.EQUAL, values.getIdUbicacion_Viejo());
        }

        if (values.getIdVariante() != ControlAlmacen.EMPTY_INT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addInteger("id_variante", values.getIdVariante());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addInteger("id_variante", QueryParameter.Operator.EQUAL, values.getIdVariante());
        }

        if (values.getConsecutivo() != ControlAlmacen.EMPTY_INT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addInteger("consecutivo", values.getIdVariante());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addInteger("consecutivo", QueryParameter.Operator.EQUAL, values.getConsecutivo());
        }

        if (values.getCantidad() != ControlAlmacen.EMPTY_FLOAT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addFloat("cantidad", values.getCantidad());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addFloat("cantidad", QueryParameter.Operator.EQUAL, values.getCantidad());
        }

        if (!values.getSerie().equals(ControlAlmacen.EMPTY_STRING))
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addString("serie", values.getSerie(), 45);

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addString("serie", QueryParameter.Operator.EQUAL, values.getSerie());
        }

        if (values.getValorAcumulado() != ControlAlmacen.EMPTY_INT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addInteger("valor_acumulado", values.getValorAcumulado());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addInteger("valor_acumulado", QueryParameter.Operator.EQUAL, values.getValorAcumulado());
        }

        if (values.getObservaciones() != ControlAlmacen.EMPTY_STRING)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addString("observaciones", values.getObservaciones(), 255);

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addString("observaciones", QueryParameter.Operator.EQUAL, values.getObservaciones());
        }
    }

    //---------------------------------------------------------------------
    @Override
    public ControlAlmacen onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
    {
        ControlAlmacen current = new ControlAlmacen();

        for (int i = 1; i <= e.getColumnCount(); i++)
        {
            switch (e.getColumnName(i))
            {
                case "entrada":
                    current.setEntrada(e.getInt(i));
                    break;
                case "id_ubicacion":
                    current.setIdUbicacion(e.getInt(i));
                    break;
                case "id_variante":
                    current.setIdVariante(e.getInt(i));
                    break;
                case "consecutivo":
                    current.setConsecutivo(e.getInt(i));
                    break;
                case "cantidad":
                    current.setCantidad(e.getFloat(i));
                    break;
                case "serie":
                    current.setSerie(e.getString(i));
                    break;
                case "observaciones":
                    current.setObservaciones(e.getString(i));
                    break;
                case "valor_acumulado":
                    current.setValoAcumulado(e.getInt(i));
                    break;
                case "mantenimiento_como":
                    current.getHasOneControlInventario().getHasOneBienVariante().setMantenimientoComo(e.getInt(i));
                    break;
            }
        }

        current.setSearchOnlyByPrimaryKey(true);
        current.acceptChanges();

        return current;
    }
}
