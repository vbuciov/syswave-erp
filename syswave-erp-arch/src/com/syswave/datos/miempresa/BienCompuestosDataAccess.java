package com.syswave.datos.miempresa;

import com.syswave.datos.common.SingletonDataAccess;
import com.syswave.entidades.miempresa.BienCompuesto;
import datalayer.api.IMediatorDataSource;
import datalayer.events.DataGetEvent;
import datalayer.events.DataSendToQueryEvent;
import datalayer.utils.QueryParameter;
import java.sql.SQLException;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class BienCompuestosDataAccess extends SingletonDataAccess<BienCompuesto>
{
    public BienCompuestosDataAccess(IMediatorDataSource mysource)
    {
        super(mysource, "bien_compuestos", "id_bien_formal", "id_bien_parte", "cantidad");
        setWithAutoID(false);
    }

     //---------------------------------------------------------------------
    @Override
    public BienCompuesto onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
    {
      BienCompuesto current = new BienCompuesto();

      for (int i = 1; i <= e.getColumnCount(); i++)
      {
          switch (e.getColumnName(i))
          {
              case "id_bien_formal":
                  current.setIdBienFormal(e.getInt(i));
                  break;
              case "id_bien_parte":
                  current.setIdBienParte(e.getInt(i));
                  break;
              case "cantidad":
                  current.setCantidad(e.getFloat(i));
                  break;
          }
      }

      current.setSearchOnlyByPrimaryKey(true);
      current.acceptChanges();

      return current;
   }

   //---------------------------------------------------------------------
    @Override
    public void onSendValues(DataSendToQueryEvent e, BienCompuesto argument)
    {

      if (argument.getIdBienFormal() != BienCompuesto.EMPTY_INT)
      {
         if (e.isCreateOperation())
         {
            e.addInteger("id_bien_formal", argument.getIdBienFormal());
            if (argument.getIdBienParte() != BienCompuesto.EMPTY_INT)
               e.addInteger("id_bien_parte", argument.getIdBienParte());
         }

         else if (e.isUpdateOperation())
         {
            e.addInteger("id_bien_formal", argument.getIdBienFormal());
            
            if (argument.getIdBienParte() != BienCompuesto.EMPTY_INT)
               e.addInteger("id_bien_parte", argument.getIdBienParte());

            e.addInteger("id_bien_formal", QueryParameter.Operator.EQUAL, argument.getIdBienFormal_Viejo());
            e.addInteger("id_bien_parte", QueryParameter.Operator.EQUAL, argument.getIdBienParte_Viejo());
         }

         else
         {
            e.addInteger("id_bien_formal", QueryParameter.Operator.EQUAL, argument.getIdBienFormal_Viejo());
            e.addInteger("id_bien_parte", QueryParameter.Operator.EQUAL, argument.getIdBienParte_Viejo());
         }
      }

      if (argument.getCantidad() != BienCompuesto.EMPTY_FLOAT)
      {
         if (e.isCreateOperation() || e.isUpdateOperation())
            e.addFloat("cantidad", argument.getCantidad());

         else if (!argument.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
            e.addFloat("cantidad", QueryParameter.Operator.EQUAL, argument.getCantidad());
      }
   }
}