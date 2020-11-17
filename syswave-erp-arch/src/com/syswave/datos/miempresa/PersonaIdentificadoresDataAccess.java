package com.syswave.datos.miempresa;

import com.syswave.datos.common.SingletonDataAccess;
import com.syswave.entidades.miempresa.PersonaIdentificador;
import datalayer.api.IMediatorDataSource;
import datalayer.events.DataGetEvent;
import datalayer.events.DataSendToQueryEvent;
import datalayer.utils.QueryDataTransfer;
import datalayer.utils.QueryParameter;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class PersonaIdentificadoresDataAccess extends SingletonDataAccess<PersonaIdentificador>
{

    public PersonaIdentificadoresDataAccess(IMediatorDataSource mysource)
    {
        super(mysource, "persona_identificadores", "id_persona", "clave", "id_tipo_identificador", "nota");
        setWithAutoID(false);
    }

    //---------------------------------------------------------------------
    public List<PersonaIdentificador> retrieveCommunicateOnly(PersonaIdentificador Filter)
    {
        QueryDataTransfer parametros = new QueryDataTransfer("persona_medios_contacto_select");

        parametros.addInteger("vid_persona", Filter.getIdPersona());
        parametros.addString("vclave", Filter.getClave());

        return executeReadStoredProcedure(parametros);
    }

    //---------------------------------------------------------------------
    @Override
    public PersonaIdentificador onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
    {
        PersonaIdentificador current = new PersonaIdentificador();

        for (int i = 1; i <= e.getColumnCount(); i++)
        {
            switch (e.getColumnName(i))
            {
                case "id_persona":
                    current.setIdPersona(e.getInt(i));
                    break;
                case "clave":
                    current.setClave(e.getString(i));
                    break;
                case "id_tipo_identificador":
                    current.setIdTipoIdentificador(e.getInt(i));
                    break;
                case "nota":
                    current.setNota(e.getString(i));
                    break;
            }
        }

        current.setSearchOnlyByPrimaryKey(true);
        current.acceptChanges();

        return current;
    }

    //---------------------------------------------------------------------
    @Override
    public void onSendValues(DataSendToQueryEvent e, PersonaIdentificador values)
    {
        if (e.isCreateOperation())
        {
            e.addInteger("id_persona", values.getIdPersona());
            e.addString("clave", values.getClave(), 45);
            e.addInteger("id_tipo_identificador", values.getIdTipoIdentificador());
            e.addString("nota", values.getNota(), 255);
        }

        else if (e.isUpdateOperation())
        {
            e.addInteger("id_persona", values.getIdPersona());
            e.addString("clave", values.getClave(), 45);
            e.addInteger("id_tipo_identificador", values.getIdTipoIdentificador());
            e.addString("nota", values.getNota(), 255);

            e.addString("clave", QueryParameter.Operator.EQUAL, values.getClave_Viejo());
            e.addInteger("id_persona", QueryParameter.Operator.EQUAL, values.getIdPersona_Viejo());
        }

        else if (e.isRetrieveOperation() || e.isDeleteOperation())
        {
            if (!values.getClave().equals(PersonaIdentificador.EMPTY_STRING))
                e.addString("clave", QueryParameter.Operator.EQUAL, values.getClave_Viejo());

            if (values.getIdPersona() != PersonaIdentificador.EMPTY_INT)
                e.addInteger("id_persona", QueryParameter.Operator.EQUAL, values.getIdPersona_Viejo());

            if (!values.isSearchOnlyByPrimaryKey())
            {
                if (values.getIdTipoIdentificador() != PersonaIdentificador.EMPTY_INT)
                    e.addInteger("id_tipo_identificador", QueryParameter.Operator.EQUAL, values.getIdTipoIdentificador());
                if (values.getNota() != EMTPY_STRING)
                    e.addString("nota", QueryParameter.Operator.EQUAL, values.getNota());

            }
        }
    }
}
