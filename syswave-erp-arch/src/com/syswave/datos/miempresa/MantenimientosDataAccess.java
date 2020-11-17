package com.syswave.datos.miempresa;

import com.syswave.datos.common.SingletonDataAccess;
import com.syswave.entidades.miempresa.Mantenimiento;
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
public class MantenimientosDataAccess extends SingletonDataAccess<Mantenimiento>
{

    private final String insertProcedure = "mantenimiento_insert(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    public MantenimientosDataAccess(IMediatorDataSource mysource)
    {
        super(mysource, "mantenimientos", "id", "folio", "fecha_elaboracion", "es_tipo",
              "id_persona", "id_ubicacion_serie", "entrada_serie", "costo_total",
              "id_moneda", "es_activo", "hora_inicio", "hora_final", "nota",
              "valor_planeado", "valor_usado", "fecha_finalizacion");
        setInsertProcedure(insertProcedure);
    }

    //--------------------------------------------------------------------
    @Override
    public Mantenimiento onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
    {
        Mantenimiento value = new Mantenimiento();

        for (int i = 1; i <= e.getColumnCount(); i++)
        {
            switch (e.getColumnName(i))
            {
                case "id":
                    value.setId(e.getInt(i));
                    break;
                case "folio":
                    value.setFolio(e.getString(i));
                    break;
                case "fecha_elaboracion":
                    value.setFechaCreacion(e.getDate(i));
                    break;
                case "es_tipo":
                    value.setEsTipo(e.getInt(i));
                    break;
                case "id_persona":
                    value.setIdPersona(e.getInt(i));
                    break;
                case "id_ubicacion_serie":
                    value.setIdUbicacionSerie(e.getInt(i));
                    break;
                case "entrada_serie":
                    value.setEntradaSerie(e.getInt(i));
                    break;
                case "costo_total":
                    value.setCostoTotal(e.getFloat(i));
                    break;
                case "id_moneda":
                    value.setIdMoneda(e.getInt(i));
                    break;
                case "es_activo":
                    value.setActivo(e.getBoolean(i));
                    break;
                case "hora_inicio":
                    value.setHoraInicio(e.getTime(i));
                    break;
                case "hora_final":
                    value.setHoraFinal(e.getTime(i));
                    break;
                case "nota":
                    value.setNota(e.getString(i));
                    break;
                case "valor_planeado":
                    value.setValorPlaneado(e.getInt(i));
                    break;
                case "valor_usado":
                    value.setValorUsado(e.getInt(i));
                    break;
                case "fecha_finalizacion":
                    value.setFechaFinalizacion(e.getDate(i));
                    break;
            }
        }

        value.setSearchOnlyByPrimaryKey(true);
        value.acceptChanges();

        return value;
    }

    //---------------------------------------------------------------------
    /**
     * InsertProcedure is using an executeUpdateStoredProcedure
     *
     * @throws java.sql.SQLException
     */
    @Override
    protected void onConvertTransfer(Mantenimiento values, DataSetEvent e) throws SQLException, UnsupportedOperationException
    {
        if (e.getDML() == insertProcedure)
        {
            e.setString(1, values.getFolio(), 32);//"vfolio"
            e.setDate(2, values.getFechaCreacion());//"vfecha_elaboracion"
            e.setInt(3, values.getEsTipo());//"ves_tipo"
            e.setInt(4, values.getIdPersona());//"vid_persona"
            e.setInt(5, values.getIdUbicacionSerie());//"vid_ubicacion_serie"
            e.setInt(6, values.getEntradaSerie());//"ventrada_serie"
            e.setFloat(7, values.getCostoTotal());//"vcosto_total"
            e.setInt(8, values.getIdMoneda());//"vid_moneda"
            e.setBoolean(9, values.esActivo());//"ves_activo"
            e.setTime(10, values.getHoraInicio());//"vhora_inicio"
            e.setTime(11, values.getHoraFinal());//"vhora_final"
            e.setString(12, values.getNota(), 255);//"vnota"
            e.setInt(13, values.getValorPlaneado());//"vvalor_planeado"
            e.setInt(14, values.getValorUsado());//"vvalor_usado"
            e.setDate(15, values.getFechaFinalizacion());//"vfecha_finalizacion"
        }
    }

    //--------------------------------------------------------------------    
    @Override
    protected void onConvertKeyResult(DataGetEvent e, Mantenimiento value) throws SQLException, UnsupportedOperationException
    {
        for (int i = 1; i <= e.getColumnCount(); i++)
        {
            switch (e.getColumnName(i))
            {
                case "id":
                    value.setId(e.getInt(i));
                    break;
                case "folio":
                    value.setFolio(e.getString(i));
                    break;
                case "fecha_elaboracion":
                    value.setFechaCreacion(e.getDate(i));
                    break;
                case "es_tipo":
                    value.setEsTipo(e.getInt(i));
                    break;
                case "id_persona":
                    value.setIdPersona(e.getInt(i));
                    break;
                case "id_ubicacion_serie":
                    value.setIdUbicacionSerie(e.getInt(i));
                    break;
                case "entrada_serie":
                    value.setEntradaSerie(e.getInt(i));
                    break;
                case "costo_total":
                    value.setCostoTotal(e.getFloat(i));
                    break;
                case "id_moneda":
                    value.setIdMoneda(e.getInt(i));
                    break;
                case "es_activo":
                    value.setActivo(e.getBoolean(i));
                    break;
                case "hora_inicio":
                    value.setHoraInicio(e.getTime(i));
                    break;
                case "hora_final":
                    value.setHoraFinal(e.getTime(i));
                    break;
                case "nota":
                    value.setNota(e.getString(i));
                    break;
                case "valor_planeado":
                    value.setValorPlaneado(e.getInt(i));
                    break;
                case "valor_usado":
                    value.setValorUsado(e.getInt(i));
                    break;
                case "fecha_finalizacion":
                    value.setFechaFinalizacion(e.getDate(i));
                    break;
            }
        }
        /*  if (e.getColumnCount() > 0)
            value.setId(e.getInt(1));*/
    }

    //---------------------------------------------------------------------
    @Override
    public void onSendValues(DataSendToQueryEvent e, Mantenimiento values)
    {
        if (values.getId() != Mantenimiento.EMPTY_INT)
        {

            if (e.isCreateOperation())
                e.addInteger("id", values.getId());

            else if (e.isUpdateOperation())
            {
                e.addInteger("id", values.getId());
                e.addInteger("id", QueryParameter.Operator.EQUAL, values.getId_Viejo());
            }

            else
                e.addInteger("id", QueryParameter.Operator.EQUAL, values.getId_Viejo());
        }

        if (values.getFolio() != Mantenimiento.EMPTY_STRING)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addString("folio", values.getFolio(), 32);

            else if (!values.isSearchOnlyByPrimaryKey())
                e.addString("folio", QueryParameter.Operator.EQUAL, values.getFolio());
        }

        if (values.getFechaCreacion() != Mantenimiento.EMPTY_DATE)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addDate("fecha_elaboracion", values.getFechaCreacion());

            else if (!values.isSearchOnlyByPrimaryKey())
                e.addDate("fecha_elaboracion", QueryParameter.Operator.EQUAL, values.getFechaCreacion());
        }

        if (values.getEsTipo() != Mantenimiento.EMPTY_INT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addInteger("es_tipo", values.getEsTipo());

            else if (!values.isSearchOnlyByPrimaryKey())
                e.addInteger("es_tipo", QueryParameter.Operator.EQUAL, values.getEsTipo());
        }

        if (values.getIdPersona() != Mantenimiento.EMPTY_INT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addInteger("id_persona", values.getIdPersona());

            else if (!values.isSearchOnlyByPrimaryKey())
                e.addInteger("id_persona", QueryParameter.Operator.EQUAL, values.getIdPersona());
        }

        if (values.getIdUbicacionSerie() != Mantenimiento.EMPTY_INT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addInteger("id_ubicacion_serie", values.getIdUbicacionSerie());

            else if (!values.isSearchOnlyByPrimaryKey())
                e.addInteger("id_ubicacion_serie", QueryParameter.Operator.EQUAL, values.getIdUbicacionSerie());
        }

        if (values.getEntradaSerie() != Mantenimiento.EMPTY_INT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addInteger("entrada_serie", values.getEntradaSerie());

            else if (!values.isSearchOnlyByPrimaryKey())
                e.addInteger("entrada_serie", QueryParameter.Operator.EQUAL, values.getEntradaSerie());
        }

        if (values.getCostoTotal() != Mantenimiento.EMPTY_FLOAT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addFloat("costo_total", values.getCostoTotal());

            else if (!values.isSearchOnlyByPrimaryKey())
                e.addFloat("costo_total", QueryParameter.Operator.EQUAL, values.getCostoTotal());
        }

        if (values.getIdMoneda() != Mantenimiento.EMPTY_INT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addInteger("id_moneda", values.getIdMoneda());

            else if (!values.isSearchOnlyByPrimaryKey())
                e.addInteger("id_moneda", QueryParameter.Operator.EQUAL, values.getIdMoneda());
        }

        if (values.isSet())
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addBoolean("es_activo", values.esActivo());

            else if (!values.isSearchOnlyByPrimaryKey())
                e.addBoolean("es_activo", QueryParameter.Operator.EQUAL, values.esActivo());
        }

        if (values.getHoraInicio() != Mantenimiento.EMPTY_DATE)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addTime("hora_inicio", values.getHoraInicio());

            else if (!values.isSearchOnlyByPrimaryKey())
                e.addTime("hora_inicio", QueryParameter.Operator.GREATER_EQUAL, values.getHoraInicio());
        }

        if (values.getHoraFinal() != Mantenimiento.EMPTY_DATE)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addTime("hora_final", values.getHoraFinal());

            else if (!values.isSearchOnlyByPrimaryKey())
                e.addTime("hora_final", QueryParameter.Operator.GREATER_EQUAL, values.getHoraFinal());
        }

        if (values.getNota() != Mantenimiento.EMPTY_STRING)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addString("nota", values.getNota(), 255);

            else if (!values.isSearchOnlyByPrimaryKey())
                e.addString("nota", QueryParameter.Operator.LIKE, String.format("%%%s%%", values.getNota()));
        }

        if (values.getValorPlaneado() != Mantenimiento.EMPTY_INT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addInteger("valor_planeado", values.getValorPlaneado());

            else if (!values.isSearchOnlyByPrimaryKey())
                e.addInteger("valor_planeado", QueryParameter.Operator.EQUAL, values.getValorPlaneado());
        }

        if (values.getValorUsado() != Mantenimiento.EMPTY_INT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addInteger("valor_usado", values.getValorUsado());

            else if (!values.isSearchOnlyByPrimaryKey())
                e.addInteger("valor_usado", QueryParameter.Operator.EQUAL, values.getValorUsado());
        }
        if (values.getFechaFinalizacion() != Mantenimiento.EMPTY_DATE)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addDate("fecha_finalizacion", values.getFechaFinalizacion());

            else if (!values.isSearchOnlyByPrimaryKey())
                e.addDate("fecha_finalizacion", QueryParameter.Operator.EQUAL, values.getFechaFinalizacion());
        }
    }
}
