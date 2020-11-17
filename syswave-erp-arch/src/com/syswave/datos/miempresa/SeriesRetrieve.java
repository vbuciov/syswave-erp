package com.syswave.datos.miempresa;

import com.syswave.datos.common.SingletonDataAccess;
import com.syswave.entidades.miempresa.Bien;
import com.syswave.entidades.miempresa.BienVariante;
import com.syswave.entidades.miempresa.ControlAlmacen;
import com.syswave.entidades.miempresa.ControlInventario;
import com.syswave.entidades.miempresa.Ubicacion;
import datalayer.api.IMediatorDataSource;
import datalayer.events.DataGetEvent;
import datalayer.events.DataSendToQueryEvent;
import datalayer.utils.QueryParameter;
import datalayer.utils.Relation;
import datalayer.utils.RelationOptions;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class SeriesRetrieve extends SingletonDataAccess<ControlAlmacen>
{

    private final Relation shortBasic;

    public SeriesRetrieve(IMediatorDataSource mysource)
    {
        super(mysource, "series", "entrada", "id_ubicacion", "ubicacion", "descripcion",
              "consecutivo", "id_variante", "id_bien", "tipo_bien",
              "id_categoria", "es_activo",
              "es_comercializar", "nivel", "mantenimiento_como", "valor_esperado",
              "valor_acumulado");
        setWithAutoID(false);
        shortBasic = new Relation(getTable(),
                                  new String[]
                                  {
                                      "entrada",
                                      "id_ubicacion",
                                      "ubicacion",
                                      "descripcion",
                                      "id_variante",
                                      "id_bien",
                                      "tipo_bien",
                                      "es_activo",
                                      "es_comercializar",
                                      "nivel",
                                      "mantenimiento_como",
                                      "valor_esperado",
                                      "valor_acumulado"
                                  },
                                  RelationOptions.TypeOption.ORDER_BY, new String[]
                                  {
                                      "id_categoria", "tipo_bien", "descripcion"
                });
    }

    //---------------------------------------------------------------------
    public List<ControlAlmacen> SmallRetrieve()
    {
        return submitQuery(shortBasic);
    }

    //---------------------------------------------------------------------
    public List<ControlAlmacen> SmallRetrieve(ControlAlmacen Filter)
    {
        return submitQuery(shortBasic, Filter);
    }

    //---------------------------------------------------------------------
    @Override
    public void onSendValues(DataSendToQueryEvent e, ControlAlmacen values)
    {
        if (values.getEntrada() != ControlAlmacen.EMPTY_INT)
        {
            if (e.isCreateOperation())
                e.addInteger("entrada", values.getEntrada());

            else if (e.isUpdateOperation())
            {
                e.addInteger("entrada", values.getEntrada());
                e.addInteger("entrada", QueryParameter.Operator.EQUAL, values.getEntrada_Viejo());
            }

            else
                e.addInteger("entrada", QueryParameter.Operator.EQUAL, values.getEntrada());
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
                e.addInteger("id_ubicacion", QueryParameter.Operator.EQUAL, values.getIdUbicacion());
        }

        if (!values.getSerie().equals(ControlAlmacen.EMPTY_STRING))
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addString("descripcion", values.getSerie(), 45);

            else
                e.addString("descripcion", QueryParameter.Operator.EQUAL, values.getSerie());
        }

        if (values.getConsecutivo() != ControlAlmacen.EMPTY_INT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addInteger("consecutivo", values.getConsecutivo());

            else
                e.addInteger("consecutivo", QueryParameter.Operator.EQUAL, values.getConsecutivo());
        }

        if (values.getIdVariante() != ControlAlmacen.EMPTY_INT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addInteger("id_variante", values.getIdVariante());

            else
                e.addInteger("id_variante", QueryParameter.Operator.EQUAL, values.getIdVariante());
        }

        /* if (values.getCantidad()!= ControlAlmacen.EMPTY_FLOAT)
      {
          if (e.isCreateOperation() || e.isUpdateOperation())
              e.addFloat("existencia", values.getCantidad());

          else
              e.addFloat("1a", QueryParameter.Operator.EQUAL, values.getCantidad());
      }*/
    }

    //---------------------------------------------------------------------
    @Override
    public ControlAlmacen onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
    {
        ControlAlmacen current = new ControlAlmacen();
        Ubicacion ubicacionActual = current.getHasOneUbicacion();
        ControlInventario inventario = current.getHasOneControlInventario();
        BienVariante bienactual = inventario.getHasOneBienVariante();
        Bien tipo_bien = bienactual.getHasOneGrupo();

        for (int i = 1; i <= e.getColumnCount(); i++)
        {
            if (e.getColumnName(i).equals("entrada"))
                current.setEntrada(e.getInt(i));

            else if (e.getColumnName(i).equals("id_ubicacion"))
                current.setIdUbicacion(e.getInt(i));

            else if (e.getColumnName(i).equals("ubicacion"))
                ubicacionActual.setNombre(e.getString(i));

            else if (e.getColumnName(i).equals("id_bien"))
                bienactual.setIdBien(e.getInt(i));

            else if (e.getColumnName(i).equals("descripcion"))
                current.setSerie(e.getString(i));

            else if (e.getColumnName(i).equals("consecutivo"))
                current.setConsecutivo(e.getInt(i));

            else if (e.getColumnName(i).equals("id_variante"))
                current.setIdVariante(e.getInt(i));

            /*else if (e.getColumnName(i).equals("existencia"))
              inventario.setExistencia(e.getFloat(i));*/
            else if (e.getColumnName(i).equals("id_bien"))
                bienactual.setIdBien(e.getInt(i));

            else if (e.getColumnName(i).equals("tipo_bien"))
                tipo_bien.setNombre(e.getString(i));

            else if (e.getColumnName(i).equals("es_inventario"))
                bienactual.setEsInventario(e.getBoolean(i));

            else if (e.getColumnName(i).equals("es_activo"))
                bienactual.setEsActivo(e.getBoolean(i));

            else if (e.getColumnName(i).equals("es_comercializar"))
                bienactual.setEsComercializar(e.getBoolean(i));

            else if (e.getColumnName(i).equals("nivel"))
                bienactual.setNivel(e.getInt(i));

            else if (e.getColumnName(i).equals("mantenimiento_como"))
                bienactual.setMantenimientoComo(e.getInt(i));

            else if (e.getColumnName(i).equals("valor_esperado"))
                bienactual.setValorEsperado(e.getInt(i));

            else if (e.getColumnName(i).equals("valor_acumulado"))
                current.setValoAcumulado(e.getInt(i));
        }

        current.setSearchOnlyByPrimaryKey(true);
        current.acceptChanges();

        return current;
    }
}