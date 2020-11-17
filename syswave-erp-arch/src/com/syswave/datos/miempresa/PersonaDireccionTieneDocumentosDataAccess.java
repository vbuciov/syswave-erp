package com.syswave.datos.miempresa;

import com.syswave.datos.common.SingletonDataAccess;
import com.syswave.entidades.miempresa.PersonaDireccion_tiene_Documento;
import datalayer.api.IMediatorDataSource;
import datalayer.events.DataGetEvent;
import datalayer.events.DataSendToQueryEvent;
import datalayer.utils.QueryParameter;
import java.sql.SQLException;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class PersonaDireccionTieneDocumentosDataAccess extends SingletonDataAccess<PersonaDireccion_tiene_Documento>
{

    public PersonaDireccionTieneDocumentosDataAccess(IMediatorDataSource mysource)
    {
        super(mysource, "persona_direccion_tiene_documentos", "id_persona", "consecutivo", "id_documento", "es_rol");
        setWithAutoID(false);
    }

    //---------------------------------------------------------------------
    @Override
    public PersonaDireccion_tiene_Documento onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
    {
        PersonaDireccion_tiene_Documento current = new PersonaDireccion_tiene_Documento();

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
                case "id_documento":
                    current.setIdDocumento(e.getInt(i));
                    break;
                case "es_rol":
                    current.setRol(e.getInt(i));
                    break;
            }
        }

        //Los objetos recuperados promueven busquedas solo por la llave.
        current.setSearchOnlyByPrimaryKey(true);
        current.acceptChanges();

        return current;
    }

    //---------------------------------------------------------------------
    @Override
    public void onSendValues(DataSendToQueryEvent e, PersonaDireccion_tiene_Documento values)
    {
        if (values.getIdPersona()!= PersonaDireccion_tiene_Documento.EMPTY_INT)
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

        if (values.getConsecutivo()!= PersonaDireccion_tiene_Documento.EMPTY_INT)
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

        if (values.getIdDocumento() != PersonaDireccion_tiene_Documento.EMPTY_INT)
        {
            if (e.isCreateOperation())
                e.addInteger("id_documento", values.getIdDocumento());

            else if (e.isUpdateOperation())
            {
                e.addInteger("id_documento", values.getIdDocumento());
                e.addInteger("id_documento", QueryParameter.Operator.EQUAL, values.getIdDocumento_Viejo());
            }

            else
                e.addInteger("id_documento", QueryParameter.Operator.EQUAL, values.getIdDocumento());
        }

        if (values.getEsRol() != PersonaDireccion_tiene_Documento.EMPTY_INT)
        {
            if (e.isCreateOperation())
                e.addInteger("es_rol", values.getEsRol());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addInteger("es_rol", QueryParameter.Operator.EQUAL, values.getEsRol());
        }
    }
}
