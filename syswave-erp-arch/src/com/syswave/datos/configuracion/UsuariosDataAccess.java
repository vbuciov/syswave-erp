package com.syswave.datos.configuracion;

import com.syswave.datos.common.SingletonDataAccess;
import com.syswave.entidades.configuracion.Usuario;
import datalayer.api.IMediatorDataSource;
import datalayer.events.DataGetEvent;
import datalayer.events.DataSendToQueryEvent;
import datalayer.utils.QueryParameter;
import java.sql.SQLException;

/**
 * Esta clase brinda de acceso a los datos.
 *
 * @author Victor Manuel Bucio Vargas
 */
public class UsuariosDataAccess extends SingletonDataAccess<Usuario>
{
    //---------------------------------------------------------------------
    public UsuariosDataAccess(IMediatorDataSource mysource)
    {
        super(mysource, "usuarios", "identificador", "clave", "es_activo", "es_tipo");
        setWithAutoID(false);
    }

    //---------------------------------------------------------------------
    @Override
    public Usuario onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
    {
        Usuario current = new Usuario();

        for (int i = 1; i <= e.getColumnCount(); i++)
        {
            if (e.getColumnName(i).equals("identificador"))
                current.setIdentificador(e.getString(i));

            else if (e.getColumnName(i).equals("clave"))
                current.setClave(e.getString(i));

            else if (e.getColumnName(i).equals("es_activo"))
                current.setActivo(e.getBoolean(i));
            else if (e.getColumnName(i).equals("es_tipo"))
                current.setEs_tipo(e.getInt(i));
        }

        current.setSearchOnlyByPrimaryKey(true);
        current.acceptChanges();

        return current;
    }

    //---------------------------------------------------------------------
    @Override
    public void onSendValues(DataSendToQueryEvent e, Usuario argument)
    {

        if (!argument.getIdentificador().equals(Usuario.EMPTY_STRING))
        {
            if (e.isCreateOperation())
                e.addString("identificador", argument.getIdentificador());

            else if (e.isUpdateOperation())
            {
                e.addString("identificador", argument.getIdentificador());
                e.addString("identificador", QueryParameter.Operator.EQUAL, argument.getIdentificador_Viejo());
            }

            else
                e.addString("identificador", QueryParameter.Operator.EQUAL, argument.getIdentificador_Viejo());
        }

        if (!argument.getClave().equals(Usuario.EMPTY_STRING))
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addString("clave", argument.getClave());

            else if (!argument.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addString("clave", QueryParameter.Operator.EQUAL, argument.getClave());
        }

        if (argument.isSet())
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addBoolean("es_activo", argument.esActivo());

            else if (!argument.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addBoolean("es_activo", QueryParameter.Operator.EQUAL, argument.esActivo());
        }
        if (argument.getEs_tipo() != Usuario.EMPTY_INT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addInteger("es_tipo", argument.getEs_tipo());

            else if (!argument.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addInteger("es_tipo", QueryParameter.Operator.EQUAL, argument.getEs_tipo());
        }
    }
}