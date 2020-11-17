package com.syswave.datos.miempresa;

import com.syswave.datos.common.SingletonDataAccess;
import com.syswave.entidades.miempresa.PersonaCreditoCuenta;
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
public class PersonaCreditoCuentasDataAccess extends SingletonDataAccess<PersonaCreditoCuenta>
{

    public final String insertProcedure = "persona_credito_cuenta_insert(?,?,?,?,?,?,?,?)";
    public final String updateProcedure = "persona_credito_cuenta_update(?,?,?,?,?,?,?,?,?,?)";

    public PersonaCreditoCuentasDataAccess(IMediatorDataSource mysource)
    {
        super(mysource, "persona_credito_cuentas", "consecutivo", "id_persona", "numero", "saldo_actual",
              "saldo_limite", "id_moneda", "fecha_inicial", "es_tipo",
              "es_activo", "observacion");
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
    protected void onConvertTransfer(PersonaCreditoCuenta values, DataSetEvent e) throws SQLException, UnsupportedOperationException
    {
        if (e.getDML() == insertProcedure)
        {
            e.setInt(1, values.getIdPersona());//"vid_persona"
            e.setString(2, values.getNumero(), 15);//"vnumero"
            e.setFloat(3, values.getSaldoActual());//"vsaldo_actual"
            e.setFloat(4, values.getSaldoLimite());//"vsaldo_limite"
            e.setInt(5, values.getIdMoneda());//"vid_moneda"
            e.setInt(6, values.getEsTipo());//"ves_tipo"
            e.setBoolean(7, values.esActivo());//"ves_activo"
            e.setString(8, values.getObservacion(), 150);//"vobservacion"
        }

        else if (e.getDML() == updateProcedure)
        {
            e.setInt(1, values.getConsecutivo_Viejo());//"vconsecutivo_old"
            e.setInt(2, values.getIdPersona_Viejo());//"vid_persona_old"
            e.setInt(3, values.getIdPersona());//"vid_persona_new"
            e.setString(4, values.getNumero(), 15);//"vnumero"
            e.setFloat(5, values.getSaldoActual());//"vsaldo_actual"
            e.setFloat(6, values.getSaldoLimite());//"vsaldo_limite"
            e.setInt(7, values.getIdMoneda());//"vid_moneda"
            e.setInt(8, values.getEsTipo());//"ves_tipo"
            e.setBoolean(9, values.esActivo());//"ves_activo"
            e.setString(10, values.getObservacion(), 150);//"vobservacion"
        }
    }

    //--------------------------------------------------------------------    
    @Override
    protected void onConvertKeyResult(DataGetEvent e, PersonaCreditoCuenta value) throws SQLException, UnsupportedOperationException
    {
        for (int i = 1; i <= e.getColumnCount(); i++)
        {
            switch (e.getColumnName(i))
            {
                case "consecutivo":
                    value.setConsecutivo(e.getInt(i));
                    break;
                case "id_persona":
                    value.setIdPersona(e.getInt(i));
                    break;
                case "numero":
                    value.setNumero(e.getString(i));
                    break;
                case "saldo_actual":
                    value.setSaldoActual(e.getFloat(i));
                    break;
                case "saldo_limite":
                    value.setSaldoLimite(e.getFloat(i));
                    break;
                case "id_moneda":
                    value.setIdMoneda(e.getInt(i));
                    break;
                case "fecha_inicial":
                    value.setFechaInicial(e.getDate(i));
                    break;
                case "es_tipo":
                    value.setEsTipo(e.getInt(i));
                    break;
                case "es_activo":
                    value.setActivo(e.getBoolean(i));
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
    public void onSendValues(DataSendToQueryEvent e, PersonaCreditoCuenta values)
    {
        if (values.getConsecutivo()!= PersonaCreditoCuenta.EMPTY_INT)
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

        if (values.getIdPersona()!= PersonaCreditoCuenta.EMPTY_INT)
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

        if (!values.getNumero().equals(PersonaCreditoCuenta.EMPTY_STRING))
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addString("numero", values.getNumero());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addString("numero", QueryParameter.Operator.EQUAL, values.getNumero());
        }

        if (values.getSaldoActual() != PersonaCreditoCuenta.EMPTY_FLOAT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addFloat("saldo_actual", values.getSaldoActual());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addFloat("saldo_actual", QueryParameter.Operator.EQUAL, values.getSaldoActual());
        }

        if (values.getSaldoLimite() != PersonaCreditoCuenta.EMPTY_FLOAT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addFloat("saldo_limite", values.getSaldoLimite());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addFloat("saldo_limite", QueryParameter.Operator.EQUAL, values.getSaldoLimite());
        }

        if (values.getIdMoneda() != PersonaCreditoCuenta.EMPTY_INT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addInteger("id_moneda", values.getIdMoneda());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addInteger("id_moneda", QueryParameter.Operator.EQUAL, values.getIdMoneda());
        }

        if (values.getFechaInicial() != PersonaCreditoCuenta.EMPTY_DATE)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addDate("fecha_inicial", values.getFechaInicial());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addDate("fecha_inicial", QueryParameter.Operator.EQUAL, values.getFechaInicial());
        }

        if (values.getEsTipo() != PersonaCreditoCuenta.EMPTY_INT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addInteger("es_tipo", values.getEsTipo());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addInteger("es_tipo", QueryParameter.Operator.EQUAL, values.getEsTipo());
        }

        if (values.isSet())
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addBoolean("es_activo", values.esActivo());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addBoolean("es_activo", QueryParameter.Operator.EQUAL, values.esActivo());
        }

        if (!values.getNumero().equals(PersonaCreditoCuenta.EMPTY_STRING))
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addString("observacion", values.getNumero());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addString("observacion", QueryParameter.Operator.LIKE, String.format("%%%s%%", values.getNumero()));
        }
    }

    //---------------------------------------------------------------------
    @Override
    public PersonaCreditoCuenta onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
    {
        PersonaCreditoCuenta current = new PersonaCreditoCuenta();

        for (int i = 1; i <= e.getColumnCount(); i++)
        {
            switch (e.getColumnName(i))
            {
                case "consecutivo":
                    current.setConsecutivo(e.getInt(i));
                    break;
                case "id_persona":
                    current.setIdPersona(e.getInt(i));
                    break;
                case "numero":
                    current.setNumero(e.getString(i));
                    break;
                case "saldo_actual":
                    current.setSaldoActual(e.getFloat(i));
                    break;
                case "saldo_limite":
                    current.setSaldoLimite(e.getFloat(i));
                    break;
                case "id_moneda":
                    current.setIdMoneda(e.getInt(i));
                    break;
                case "fecha_inicial":
                    current.setFechaInicial(e.getDate(i));
                    break;
                case "es_tipo":
                    current.setEsTipo(e.getInt(i));
                    break;
                case "es_activo":
                    current.setActivo(e.getBoolean(i));
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
