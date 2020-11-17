package com.syswave.datos.miempresa;

import com.syswave.datos.common.SingletonDataAccess;
import com.syswave.entidades.miempresa_vista.PersonaCreditoCuenta_5FN;
import datalayer.api.IMediatorDataSource;
import datalayer.events.DataGetEvent;
import datalayer.events.DataSendToQueryEvent;
import datalayer.utils.ProductRelation;
import datalayer.utils.QueryParameter;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author victor
 */
public class PersonaCreditoCuentasRetrieve extends SingletonDataAccess<PersonaCreditoCuenta_5FN>
{

    private final ProductRelation basicProd;

    public PersonaCreditoCuentasRetrieve(IMediatorDataSource mysource)
    {
        super(mysource, "persona_credito_cuentas", "consecutivo", "id_persona", "numero", "saldo_actual",
              "saldo_limite", "id_moneda", "fecha_inicial", "es_tipo",
              "es_activo", "observacion");
        setWithAutoID(false);
        basicProd = new ProductRelation("persona_credito_cuentas AS pcc",
                                        "personas",
                                        "id = id_persona",
                                        new String[]
                                        {
                                            "consecutivo",
                                            "id_persona",
                                            "nombres",
                                            "apellidos",
                                            "id_tipo_persona",
                                            "numero",
                                            "saldo_actual",
                                            "saldo_limite",
                                            "id_moneda",
                                            "fecha_inicial",
                                            "es_tipo",
                                            "pcc.es_activo",
                                            "observacion"
                                        });
    }

    //---------------------------------------------------------------------
    @Override
    public List<PersonaCreditoCuenta_5FN> Retrieve()
    {
        return submitQuery(basicProd);
    }

    //---------------------------------------------------------------------
    @Override
    public List<PersonaCreditoCuenta_5FN> Retrieve(PersonaCreditoCuenta_5FN Filter)
    {
        return submitQuery(basicProd, Filter);
    }

    //---------------------------------------------------------------------
    @Override
    public PersonaCreditoCuenta_5FN onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
    {
        PersonaCreditoCuenta_5FN current = new PersonaCreditoCuenta_5FN();

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
                case "nombres":
                    current.setNombres(e.getString(i));
                    break;
                case "apellidos":
                    current.setApellidos(e.getString(i));
                    break;
                case "id_tipo_persona":
                    current.setIdTipoPersona(e.getInt(i));
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

        //Los datos de la persona no pueden ser cambiados sin autorización.
        //current.setFk_persona_tiene_saldos_id_persona(current.getFk_persona_tiene_saldos_id_persona());
        current.setSearchOnlyByPrimaryKey(true);
        current.acceptChanges();

        return current;
    }

    //---------------------------------------------------------------------
    @Override
    public void onSendValues(DataSendToQueryEvent e, PersonaCreditoCuenta_5FN values)
    {
        if (values.getConsecutivo()!= PersonaCreditoCuenta_5FN.EMPTY_INT)
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

        if (values.getIdPersona()!= PersonaCreditoCuenta_5FN.EMPTY_INT)
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

        if (!values.getNombres().equals(PersonaCreditoCuenta_5FN.EMPTY_STRING))
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addString("nombres", values.getNombres());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addString("nombres", QueryParameter.Operator.LIKE, String.format("%%%s%%", values.getNombres()));
        }

        if (!values.getApellidos().equals(PersonaCreditoCuenta_5FN.EMPTY_STRING))
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addString("apellidos", values.getApellidos());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addString("apellidos", QueryParameter.Operator.LIKE, String.format("%%%s%%", values.getApellidos()));
        }

        if (!values.getNumero().equals(PersonaCreditoCuenta_5FN.EMPTY_STRING))
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addString("numero", values.getNumero());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addString("numero", QueryParameter.Operator.EQUAL, values.getNumero());
        }

        if (values.getIdTipoPersona() != PersonaCreditoCuenta_5FN.EMPTY_INT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addInteger("id_tipo_persona", values.getIdTipoPersona());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addInteger("id_tipo_persona", QueryParameter.Operator.EQUAL, values.getIdTipoPersona());
        }

        if (values.getSaldoActual() != PersonaCreditoCuenta_5FN.EMPTY_FLOAT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addFloat("saldo_actual", values.getSaldoActual());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addFloat("saldo_actual", QueryParameter.Operator.EQUAL, values.getSaldoActual());
        }

        if (values.getSaldoLimite() != PersonaCreditoCuenta_5FN.EMPTY_FLOAT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addFloat("saldo_limite", values.getSaldoLimite());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addFloat("saldo_limite", QueryParameter.Operator.EQUAL, values.getSaldoLimite());
        }

        if (values.getIdMoneda() != PersonaCreditoCuenta_5FN.EMPTY_INT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addInteger("id_moneda", values.getIdMoneda());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addInteger("id_moneda", QueryParameter.Operator.EQUAL, values.getIdMoneda());
        }

        if (values.getFechaInicial() != PersonaCreditoCuenta_5FN.EMPTY_DATE)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addDate("fecha_inicial", values.getFechaInicial());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addDate("fecha_inicial", QueryParameter.Operator.EQUAL, values.getFechaInicial());
        }

        if (values.getEsTipo() != PersonaCreditoCuenta_5FN.EMPTY_INT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addInteger("es_tipo", values.getEsTipo());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addInteger("es_tipo", QueryParameter.Operator.EQUAL, values.getEsTipo());
        }

        if (values.isSet())
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addBoolean("pcc.es_activo", values.esActivo());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addBoolean("pcc.es_activo", QueryParameter.Operator.EQUAL, values.esActivo());
        }

        if (!values.getNumero().equals(PersonaCreditoCuenta_5FN.EMPTY_STRING))
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addString("observacion", values.getNumero());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addString("observacion", QueryParameter.Operator.LIKE, String.format("%%%s%%", values.getNumero()));
        }
    }
}
