package com.syswave.datos.miempresa;

import com.syswave.datos.common.SingletonDataAccess;
import com.syswave.entidades.miempresa.VarianteIdentificador;
import datalayer.api.IMediatorDataSource;
import datalayer.events.DataGetEvent;
import datalayer.events.DataSendToQueryEvent;
import datalayer.utils.QueryParameter;
import java.sql.SQLException;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class VarianteIdentificadoresDataAccess extends SingletonDataAccess<VarianteIdentificador>
{

    public VarianteIdentificadoresDataAccess(IMediatorDataSource mysource)
    {
        super(mysource,"variante_identificadores", "valor", "id_variante", "id_tipo_identificador");
        setWithAutoID(false);
    }

    //---------------------------------------------------------------------
    @Override
    public VarianteIdentificador onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
    {
        VarianteIdentificador current = new VarianteIdentificador();

        for (int i = 1; i <= e.getColumnCount(); i++)
        {
            switch (e.getColumnName(i))
            {
                case "valor":
                    current.setValor(e.getString(i));
                    break;
                case "id_variante":
                    current.setIdVariante(e.getInt(i));
                    break;
                case "id_tipo_identificador":
                    current.setIdTipoIdentificador(e.getInt(i));
                    break;
            }
        }

        current.setSearchOnlyByPrimaryKey(true);
        current.acceptChanges();

        return current;
    }

    //---------------------------------------------------------------------
    @Override
    public void onSendValues(DataSendToQueryEvent e, VarianteIdentificador values)
    {
        if (e.isCreateOperation())
        {
            e.addString("valor", values.getValor());
            e.addInteger("id_variante", values.getIdVariante());
            e.addInteger("id_tipo_identificador", values.getIdTipoIdentificador());
        }

        else if (e.isUpdateOperation())
        {
            e.addString("valor", values.getValor(), 45);
            e.addInteger("id_variante", values.getIdVariante());
            e.addInteger("id_tipo_identificador", values.getIdTipoIdentificador());

            e.addString("valor", QueryParameter.Operator.EQUAL, values.getValor_Viejo());
            //e.addInteger("id_variante", QueryParameter.Operator.EQUAL, values.getIdVariante_Viejo());
        }

        else if (e.isRetrieveOperation() || e.isDeleteOperation())
        {
            if (!values.getValor().equals(VarianteIdentificador.EMPTY_STRING))
                e.addString("valor", QueryParameter.Operator.EQUAL, values.getValor_Viejo());

            if (!values.isSearchOnlyByPrimaryKey())
            {
                if (values.getIdVariante() != VarianteIdentificador.EMPTY_INT)
                    e.addInteger("id_variante", QueryParameter.Operator.EQUAL, values.getIdVariante());

                if (values.getIdTipoIdentificador() != VarianteIdentificador.EMPTY_INT)
                    e.addInteger("id_tipo_identificador", QueryParameter.Operator.EQUAL, values.getIdTipoIdentificador());
            }
        }
    }
}
