package com.syswave.datos.miempresa;

import com.syswave.datos.common.SingletonDataAccess;
import com.syswave.entidades.miempresa.PersonaSalario;
import datalayer.api.IMediatorDataSource;
import datalayer.events.DataGetEvent;
import datalayer.events.DataSendToQueryEvent;
import datalayer.events.DataSetEvent;
import datalayer.utils.QueryParameter;
import java.sql.SQLException;

/**
 *
 * @author sis5
 */
public class PersonaSalariosDataAccess extends SingletonDataAccess<PersonaSalario>
{

    private final String insertProcedure = "persona_salarios_insert(?,?,?,?,?)";
    private final String updateProcedure = "persona_salarios_update(?,?,?,?,?,?,?)";

    public PersonaSalariosDataAccess(IMediatorDataSource mysource)
    {
        super(mysource, "persona_salarios", "id_persona", "consecutivo",
              "fecha_vigor", "sueldo_neto", "id_moneda", "es_frecuencia");
        setInsertProcedure(insertProcedure);
        setUpdateProcedure(updateProcedure);
    }

    @Override
    public PersonaSalario onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
    {
        PersonaSalario current = new PersonaSalario();

        for (int i = 1; i <= e.getColumnCount(); i++)
        {
            switch (e.getColumnName(i))
            {
                case "id_persona":
                    current.setIdPersona(e.getInt(i));
                    break;
                case "consecutivo":
                    current.setConsecutivo(e.getInt(i));
                    break;
                case "fecha_vigor":
                    current.setFechaVigor(e.getDate(i));
                    break;
                case "sueldo_neto":
                    current.setSueldoNeto(e.getDouble(i));
                    break;
                case "id_moneda":
                    current.setIdMoneda(e.getInt(i));
                    break;
                case "es_frecuencia":
                    current.setFrecuencia(e.getInt(i));
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
    protected void onConvertTransfer(PersonaSalario values, DataSetEvent e) throws SQLException, UnsupportedOperationException
    {
        if (e.getDML() == insertProcedure)
        {
            e.setInt(1, values.getIdPersona()); //"vid_persona"
            //e.addInteger("vconsecutivo", values.getConsecutivo());
            e.setDate(2, values.getFechaVigor());//"vfecha_vigor"
            e.setDouble(3, values.getSueldoNeto());//"vsueldo_neto"
            e.setInt(4, values.getIdMoneda());//"vid_moneda"
            e.setInt(5, values.getFrecuencia());//"ves_frecuencia"
        }

        else if (e.getDML() == updateProcedure)
        {
            e.setInt(1, values.getIdPersona());//"vid_persona_new"
            e.setInt(2, values.getIdPersona_Viejo());//"vid_persona_old"
            e.setInt(3, values.getConsecutivo_Viejo());//"vconsecutivo_old"
            e.setDate(4, values.getFechaVigor());//"vfecha_vigor"
            e.setDouble(5, values.getSueldoNeto());//"vsueldo_neto"
            e.setInt(6, values.getIdMoneda());//"vid_moneda"
            e.setInt(7, values.getFrecuencia());//"ves_frecuencia"
        }
    }

    //--------------------------------------------------------------------    
    @Override
    protected void onConvertKeyResult(DataGetEvent e, PersonaSalario value) throws SQLException, UnsupportedOperationException
    {
        for (int i = 1; i <= e.getColumnCount(); i++)
        {
            switch (e.getColumnName(i))
            {
                case "id_persona":
                    value.setIdPersona(e.getInt(i));
                    break;
                case "consecutivo":
                    value.setConsecutivo(e.getInt(i));
                    break;
                case "fecha_vigor":
                    value.setFechaVigor(e.getDate(i));
                    break;
                case "sueldo_neto":
                    value.setSueldoNeto(e.getDouble(i));
                    break;
                case "id_moneda":
                    value.setIdMoneda(e.getInt(i));
                    break;
                case "es_frecuencia":
                    value.setFrecuencia(e.getInt(i));
                    break;
            }
        }
        /*  if (e.getColumnCount() > 0)
            value.setId(e.getInt(1));*/
    }

    //---------------------------------------------------------------------
    @Override
    public void onSendValues(DataSendToQueryEvent e, PersonaSalario values)
    {
        if (values.getIdPersona()!= PersonaSalario.EMPTY_INT)
        {

            if (e.isCreateOperation())
                e.addInteger("id_persona", values.getIdPersona());

            else if (e.isUpdateOperation())
            {
                e.addInteger("id_persona", values.getIdPersona());
                e.addInteger("id_persona", QueryParameter.Operator.EQUAL, values.getIdPersona_Viejo());
            }
            else
                e.addInteger("id_persona", QueryParameter.Operator.EQUAL, values.getIdPersona_Viejo());
        }

        if (values.getConsecutivo()!= PersonaSalario.EMPTY_INT)
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

        if (values.getFechaVigor() != PersonaSalario.EMPTY_DATE)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addDate("fecha_vigor", values.getFechaVigor());
            else if (!values.isSearchOnlyByPrimaryKey())
                e.addDate("fecha_vigor", QueryParameter.Operator.EQUAL, values.getFechaVigor());
        }

        if (values.getSueldoNeto() != PersonaSalario.EMPTY_DOUBLE)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addDouble("sueldo_neto", values.getSueldoNeto());
            else if (!values.isSearchOnlyByPrimaryKey())
                e.addDouble("sueldo_neto", QueryParameter.Operator.EQUAL, values.getSueldoNeto());
        }

        if (values.getIdMoneda() != PersonaSalario.EMPTY_INT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addInteger("id_moneda", values.getIdMoneda());
            else if (!values.isSearchOnlyByPrimaryKey())
                e.addInteger("id_moneda", QueryParameter.Operator.EQUAL, values.getIdMoneda());
        }

        if (values.getFrecuencia() != PersonaSalario.EMPTY_INT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addInteger("es_frecuencia", values.getFrecuencia());
            else if (!values.isSearchOnlyByPrimaryKey())
                e.addInteger("es_frecuencia", QueryParameter.Operator.EQUAL, values.getFrecuencia());
        }
    }
}
