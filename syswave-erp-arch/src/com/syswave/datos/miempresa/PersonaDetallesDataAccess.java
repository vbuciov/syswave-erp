package com.syswave.datos.miempresa;

import com.syswave.datos.common.SingletonDataAccess;
import com.syswave.entidades.miempresa.PersonaDetalle;
import datalayer.api.IMediatorDataSource;
import datalayer.events.DataGetEvent;
import datalayer.events.DataSendToQueryEvent;
import datalayer.utils.QueryParameter;
import java.sql.SQLException;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class PersonaDetallesDataAccess extends SingletonDataAccess<PersonaDetalle>
{

    public PersonaDetallesDataAccess(IMediatorDataSource mysource)
    {
        super(mysource, "persona_detalles", "id_persona", "id_tipo_detalle", "valor");
        setWithAutoID(false);
    }

    //---------------------------------------------------------------------
    @Override
    public PersonaDetalle onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
    {
        PersonaDetalle current = new PersonaDetalle();

        for (int i = 1; i <= e.getColumnCount(); i++)
        {
            switch (e.getColumnName(i))
            {
                case "id_persona":
                    current.setIdPersona(e.getInt(i));
                    break;
                case "id_tipo_detalle":
                    current.setIdTipoDetalle(e.getInt(i));
                    break;
                case "valor":
                    current.setValor(e.getString(i));
                    break;
            }
        }

        current.setSearchOnlyByPrimaryKey(true);
        current.acceptChanges();

        return current;
    }

    //---------------------------------------------------------------------
    @Override
    public void onSendValues(DataSendToQueryEvent e, PersonaDetalle values)
    {
        if (values.getIdPersona() != PersonaDetalle.EMPTY_INT)
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

        if (values.getIdTipoDetalle() != PersonaDetalle.EMPTY_INT)
        {

            if (e.isCreateOperation())
                e.addInteger("id_tipo_detalle", values.getIdTipoDetalle());

            else if (e.isUpdateOperation())
            {
                e.addInteger("id_tipo_detalle", values.getIdTipoDetalle());
                e.addInteger("id_tipo_detalle", QueryParameter.Operator.EQUAL, values.getIdTipoDetalle_Viejo());
            }

            else
                e.addInteger("id_tipo_detalle", QueryParameter.Operator.EQUAL, values.getIdTipoDetalle_Viejo());
        }

        if (values.getValor() != PersonaDetalle.EMPTY_STRING)
        {
            if (e.isUpdateOperation() || e.isCreateOperation())
                e.addString("valor", values.getValor(), 45);

            else if (!values.isSearchOnlyByPrimaryKey())
                e.addString("valor", QueryParameter.Operator.LIKE, String.format("%%%s%%", values.getValor()));
        }
    }
}
