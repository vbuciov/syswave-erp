package com.syswave.datos.miempresa;

import com.syswave.datos.common.SingletonDataAccess;
import com.syswave.entidades.miempresa.Bien;
import com.syswave.entidades.miempresa.BienVariante;
import com.syswave.entidades.miempresa.ControlInventario;
import datalayer.api.IMediatorDataSource;
import datalayer.events.DataGetEvent;
import datalayer.events.DataSendToQueryEvent;
import datalayer.utils.QueryParameter;
import datalayer.utils.Relation;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class LotesRetrieve extends SingletonDataAccess<ControlInventario>
{

    private final Relation shortBasic;

    public LotesRetrieve(IMediatorDataSource mysource)
    {
        super(mysource, "lotes", "consecutivo", "id_variante", "descripcion",
              "existencia", "minimo", "maximo", "reorden",
              "fecha_entrada", "fecha_caducidad", "fecha_devolucion",
              "id_bien", "tipo_bien", 
              "es_activo", "es_comercializar", "nivel", "mantenimiento_como",
              "valor_esperado");
        setWithAutoID(false);
        shortBasic = new Relation(getTable(), new String[]
                          {
                              "consecutivo",
                              "id_variante",
                              "descripcion",
                              "existencia",
                              "es_activo",
                              "es_comercializar",
                              "nivel",
                              "mantenimiento_como",
                              "valor_esperado"
        });
    }

    //---------------------------------------------------------------------
    public List<ControlInventario> SmallRetrieve()
    {
        return submitQuery(shortBasic);
    }

    //---------------------------------------------------------------------
    public List<ControlInventario> SmallRetrieve(ControlInventario Filter)
    {
        return submitQuery(shortBasic, Filter);
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
                e.addString("descripcion", values.getLote(), 45);

            else
                e.addString("descripcion", QueryParameter.Operator.EQUAL, values.getLote());
        }

        if (values.getExistencia() != ControlInventario.EMPTY_FLOAT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addFloat("existencia", values.getExistencia());

            else
                e.addFloat("existencia", QueryParameter.Operator.EQUAL, values.getExistencia());
        }

        if (values.getMinimo() != ControlInventario.EMPTY_FLOAT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addFloat("minimo", values.getMinimo());

            else
                e.addFloat("minimo", QueryParameter.Operator.EQUAL, values.getMinimo());
        }

        if (values.getMaximo() != ControlInventario.EMPTY_FLOAT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addFloat("maximo", values.getMaximo());

            else
                e.addFloat("maximo", QueryParameter.Operator.EQUAL, values.getMaximo());
        }

        if (values.getReorden() != ControlInventario.EMPTY_FLOAT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addFloat("reorden", values.getReorden());

            else
                e.addFloat("reorden", QueryParameter.Operator.EQUAL, values.getReorden());
        }

        if (values.getFecha_entrada() != ControlInventario.EMPTY_DATE)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addDate("fecha_entrada", values.getFecha_entrada());

            else
                e.addDate("fecha_entrada", QueryParameter.Operator.EQUAL, values.getFecha_entrada());
        }

        if (values.getFecha_caducidad() != ControlInventario.EMPTY_DATE)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addDate("fecha_caducidad", values.getFecha_caducidad());

            else
                e.addDate("fecha_caducidad", QueryParameter.Operator.EQUAL, values.getFecha_caducidad());
        }

        if (values.getFecha_devolucion() != ControlInventario.EMPTY_DATE)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addDate("fecha_devolucion", values.getFecha_devolucion());

            else
                e.addDate("fecha_devolucion", QueryParameter.Operator.EQUAL, values.getFecha_devolucion());
        }
    }

    //--------------------------------------------------------------------
    @Override
    public ControlInventario onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
    {
        ControlInventario current = new ControlInventario();
        BienVariante bienactual = current.getHasOneBienVariante();
        Bien tipo_bien = bienactual.getHasOneGrupo();

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
                case "id_bien":
                    bienactual.setIdBien(e.getInt(i));
                    break;
                case "descripcion":
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
                case "tipo_bien":
                    tipo_bien.setNombre(e.getString(i));
                    break;
                case "es_inventario":
                    bienactual.setEsInventario(e.getBoolean(i));
                    break;
                case "es_activo":
                    bienactual.setEsActivo(e.getBoolean(i));
                    break;
                case "es_comercializar":
                    bienactual.setEsComercializar(e.getBoolean(i));
                    break;
                case "nivel":
                    bienactual.setNivel(e.getInt(i));
                    break;
                case "mantenimiento_como":
                    bienactual.setMantenimientoComo(e.getInt(i));
                    break;
                case "valor_esperado":
                    bienactual.setValorEsperado(e.getInt(i));
                    break;
            }
        }

        current.setSearchOnlyByPrimaryKey(true);
        current.acceptChanges();

        return current;
    }
}
