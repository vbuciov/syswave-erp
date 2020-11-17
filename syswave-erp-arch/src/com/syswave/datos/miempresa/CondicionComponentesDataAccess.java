package com.syswave.datos.miempresa;

import com.syswave.datos.common.SingletonDataAccess;
import com.syswave.entidades.miempresa.Condicion_Componente;
import datalayer.api.IMediatorDataSource;
import datalayer.events.DataGetEvent;
import datalayer.events.DataSendToQueryEvent;
import datalayer.utils.QueryParameter;
import java.sql.SQLException;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class CondicionComponentesDataAccess extends SingletonDataAccess<Condicion_Componente>
{

    public CondicionComponentesDataAccess(IMediatorDataSource mysource)
    {
        super(mysource, "condicion_componentes", "id", "valor", "es_unidad", "es_tipo",
              "id_condicion");
    }

    //--------------------------------------------------------------------    
    @Override
    protected void onConvertKeyResult(DataGetEvent e, Condicion_Componente value) throws SQLException, UnsupportedOperationException
    {
        if (e.getColumnCount() > 0)
            value.setId(e.getInt(1));
    }

    //---------------------------------------------------------------------
    @Override
    public Condicion_Componente onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
    {
        Condicion_Componente current = new Condicion_Componente();

        for (int i = 1; i <= e.getColumnCount(); i++)
        {
            switch (e.getColumnName(i))
            {
                case "id":
                    current.setId(e.getInt(i));
                    break;
                case "valor":
                    current.setValor(e.getInt(i));
                    break;
                case "es_unidad":
                    current.setEs_unidad(e.getShort(i));
                    break;
                case "es_tipo":
                    current.setEs_tipo(e.getShort(i));
                    break;
                case "id_condicion":
                    current.setIdCondicion(e.getInt(i));
                    break;
            }
        }

        current.setSearchOnlyByPrimaryKey(true);
        current.acceptChanges();

        return current;
    }

    //---------------------------------------------------------------------
    @Override
    public void onSendValues(DataSendToQueryEvent e, Condicion_Componente argument)
    {
        if (argument.getId() != Condicion_Componente.EMPTY_INT)
        {
            if (e.isCreateOperation())
                e.addInteger("id", argument.getId());

            else if (e.isUpdateOperation())
            {
                e.addInteger("id", argument.getId());
                e.addInteger("id", QueryParameter.Operator.EQUAL, argument.getId_Viejo());
            }

            else
                e.addInteger("id", QueryParameter.Operator.EQUAL, argument.getId_Viejo());
        }

        if (argument.getIdCondicion() != Condicion_Componente.EMPTY_INT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addInteger("id_condicion", argument.getIdCondicion());

            else if (!argument.isSearchOnlyByPrimaryKey())
                e.addInteger("id_condicion", QueryParameter.Operator.EQUAL, argument.getIdCondicion());
        }

        if (argument.getValor() != Condicion_Componente.EMPTY_INT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addInteger("valor", argument.getValor());

            else if (!argument.isSearchOnlyByPrimaryKey())
                e.addInteger("valor", QueryParameter.Operator.EQUAL, argument.getValor());
        }

        if (argument.getEs_unidad() != Condicion_Componente.EMPTY_INT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addShort("es_unidad", argument.getEs_unidad());

            else if (!argument.isSearchOnlyByPrimaryKey())
                e.addShort("es_unidad", QueryParameter.Operator.EQUAL, argument.getEs_unidad());
        }

        if (argument.getEs_tipo() != Condicion_Componente.EMPTY_INT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addShort("es_tipo", argument.getEs_tipo());

            else if (!argument.isSearchOnlyByPrimaryKey())
                e.addShort("es_tipo", QueryParameter.Operator.EQUAL, argument.getEs_tipo());
        }
    }
}