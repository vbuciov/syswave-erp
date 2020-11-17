package com.syswave.datos.configuracion;

import com.syswave.datos.common.SingletonDataAccess;
import com.syswave.entidades.configuracion.Propiedad;
import datalayer.api.IMediatorDataSource;
import datalayer.utils.QueryParameter;
import datalayer.events.DataGetEvent;
import datalayer.events.DataSendToQueryEvent;
import java.sql.SQLException;

/**
 * Esta clase brinda de acceso a los datos.
 * @author Victor Manuel Bucio Vargas
 */
public class PropiedadesDataAccess extends SingletonDataAccess<Propiedad> 
{
   //---------------------------------------------------------------------
   public PropiedadesDataAccess(IMediatorDataSource mysource)
   {
        super(mysource, "propiedades", "llave", "seccion", "valor");
        setWithAutoID(false);
   }
     
   //---------------------------------------------------------------------
   @Override
   public Propiedad onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
   {
      Propiedad current = new Propiedad();
 
      for (int i = 1; i <= e.getColumnCount(); i++)
      {
          switch (e.getColumnName(i))
          {
              case "llave":
                  current.setLlave(e.getString(i));
                  break;
              case "seccion":
                  current.setSeccion(e.getString(i));
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
    protected void onSendValues(DataSendToQueryEvent e, Propiedad argument)
    {

      if (!argument.getLlave().equals(Propiedad.EMPTY_STRING))
      {
          if (e.isCreateOperation())
              e.addString("llave", argument.getLlave());
          
          else if (e.isUpdateOperation())
          {
              e.addString("llave", argument.getLlave());
              e.addString("llave", QueryParameter.Operator.EQUAL, argument.getLlave_Viejo());
          }

          else
              e.addString("llave", QueryParameter.Operator.EQUAL, argument.getLlave_Viejo());
      }
      
      if (!argument.getSeccion().equals(Propiedad.EMPTY_STRING))
      {
          if (e.isCreateOperation())
              e.addString("seccion", argument.getSeccion());
          
          else if (e.isUpdateOperation())
          {
              e.addString("seccion", argument.getSeccion());
              e.addString("seccion", QueryParameter.Operator.EQUAL, argument.getSeccion_Viejo());
          }

          else
              e.addString("seccion", QueryParameter.Operator.EQUAL, argument.getSeccion_Viejo());
      }
      
      if (!argument.getValor().equals(Propiedad.EMPTY_STRING))
      {
          if (e.isCreateOperation() || e.isUpdateOperation())
              e.addString("valor", argument.getValor());

          else if (!argument.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
              e.addString("valor", QueryParameter.Operator.LIKE, String.format("%%%s%%", argument.getValor()));
      }
   }  
}