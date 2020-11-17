package com.syswave.datos.configuracion;

import com.syswave.datos.common.SingletonDataAccess;
import com.syswave.entidades.configuracion.Permiso;
import datalayer.api.IMediatorDataSource;
import datalayer.events.DataGetEvent;
import datalayer.events.DataSendToQueryEvent;
import datalayer.utils.QueryParameter;
import java.sql.SQLException;

/**
 * Esta clase brinda de acceso a los datos.
 * @author Victor Manuel Bucio Vargas
 */
public class PermisosDataAccess extends SingletonDataAccess<Permiso> 
{
    public PermisosDataAccess(IMediatorDataSource mysource)
    {
        super(mysource, "permisos", "id_modulo", "llave", "valor", "es_activo", "una_vez");
        setWithAutoID(false);
    }  
    
   //---------------------------------------------------------------------
   @Override
   public Permiso onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
   {
      Permiso current = new Permiso();

      for (int i = 1; i <= e.getColumnCount(); i++)
      {         
          switch (e.getColumnName(i))
          {
              case "id_modulo":
                  current.setId_modulo(e.getInt(i));
                  break;
              case "llave":
                  current.setLlave(e.getString(i));
                  break;
              case "valor":
                  current.setValor(e.getString(i));
                  break;
              case "es_activo":
                  current.setActivo(e.getBoolean(i));
                  break;
              case "una_vez":
                  current.setUna_vez(e.getBoolean(i));
                  break;
          }
      }

      current.setSearchOnlyByPrimaryKey(true);
      current.acceptChanges();

      return current;   
   }

   //---------------------------------------------------------------------
   @Override
    protected void onSendValues(DataSendToQueryEvent e, Permiso argument)
   {
      if (argument.getId_modulo() != Permiso.EMPTY_INT)
      {
         if (e.isCreateOperation())
            e.addInteger("id_modulo", argument.getId_modulo());
         
         else if(e.isUpdateOperation())
         {
            e.addInteger("id_modulo", argument.getId_modulo());
            e.addInteger("id_modulo",QueryParameter.Operator.EQUAL, argument.getId_modulo_Viejo());
         }
         
         else 
            e.addInteger("id_modulo",QueryParameter.Operator.EQUAL, argument.getId_modulo_Viejo());
      }

      if (!argument.getLlave().equals(Permiso.EMPTY_STRING))
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
            
      if (!argument.getValor().equals(Permiso.EMPTY_STRING))
      {
          if (e.isCreateOperation() || e.isUpdateOperation())
              e.addString("valor", argument.getValor());

          else if (!argument.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
              e.addString("valor", QueryParameter.Operator.LIKE, String.format("%%%s%%", argument.getValor()));
      }
   }  
}