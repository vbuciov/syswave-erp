package com.syswave.datos.miempresa;

import com.syswave.datos.common.SingletonDataAccess;
import com.syswave.entidades.miempresa.Documento_tiene_Condicion;
import datalayer.api.IMediatorDataSource;
import datalayer.events.DataGetEvent;
import datalayer.events.DataSendToQueryEvent;
import datalayer.utils.QueryParameter;
import java.sql.SQLException;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class DocumentoCondicionesDataAccess extends SingletonDataAccess<Documento_tiene_Condicion>
{

    public DocumentoCondicionesDataAccess(IMediatorDataSource mysource)
    {
        super(mysource, "documento_condiciones", "id_documento", "id_condicion");
        setWithAutoID(false);
    }

    //---------------------------------------------------------------------
    @Override
    public Documento_tiene_Condicion onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
    {
        Documento_tiene_Condicion current = new Documento_tiene_Condicion();

        for (int i = 1; i <= e.getColumnCount(); i++)
        {
            if (e.getColumnName(i).equals("id_documento"))
                current.setIdDocumento(e.getInt(i));

            else if (e.getColumnName(i).equals("id_condicion"))
                current.setIdCondicionPago(e.getInt(i));
        }

        current.setSearchOnlyByPrimaryKey(true);
        current.acceptChanges();

        return current;
    }

    //---------------------------------------------------------------------
    @Override
    public void onSendValues(DataSendToQueryEvent e, Documento_tiene_Condicion values)
    {
        if (values.getIdDocumento() != Documento_tiene_Condicion.EMPTY_INT)
        {
            if (e.isCreateOperation())
                e.addInteger("id_documento", values.getIdDocumento());

            else if (e.isUpdateOperation())
            {
                e.addInteger("id_documento", values.getIdDocumento());
                e.addInteger("id_documento", QueryParameter.Operator.EQUAL, values.getIdDocumento_Viejo());
            }

            else
                e.addInteger("id_documento", QueryParameter.Operator.EQUAL, values.getIdDocumento_Viejo());
        }

        if (values.getIdCondicionPago() != Documento_tiene_Condicion.EMPTY_INT)
        {
            if (e.isCreateOperation())
                e.addInteger("id_condicion", values.getIdCondicionPago());

            else if (e.isUpdateOperation())
            {
                e.addInteger("id_condicion", values.getIdCondicionPago());
                e.addInteger("id_condicion", QueryParameter.Operator.EQUAL, values.getIdCondicionPago_Viejo());
            }

            else
                e.addInteger("id_condicion", QueryParameter.Operator.EQUAL, values.getIdCondicionPago_Viejo());
        }

        /*if (values.isSet())
      {
          if (e.isCreateOperation() || e.isUpdateOperation())
              e.addBoolean("es_activo", values.esActivo());

          else
              e.addBoolean("es_activo", QueryParameter.Operator.EQUAL,  values.esActivo());
      }*/
    }
}
