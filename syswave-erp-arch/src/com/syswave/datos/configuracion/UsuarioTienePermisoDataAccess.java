package com.syswave.datos.configuracion;

import com.syswave.datos.common.SingletonDataAccess;
import com.syswave.entidades.configuracion.Usuario_tiene_Permiso;
import datalayer.api.IMediatorDataSource;
import datalayer.events.DataGetEvent;
import datalayer.events.DataSendToQueryEvent;
import datalayer.utils.QueryParameter;
import java.sql.SQLException;


/**
 * Esta clase brinda de acceso a los datos.
 * @author Victor Manuel Bucio Vargas
 */
public class UsuarioTienePermisoDataAccess extends SingletonDataAccess<Usuario_tiene_Permiso> 
{
    public UsuarioTienePermisoDataAccess(IMediatorDataSource mysource)
    {
        super(mysource, "usuario_tiene_permisos", "id_usuario", "id_origen_dato", "id_modulo", 
                                        "llave_permiso", "es_usado");
        setWithAutoID(false);
    }
   
   //---------------------------------------------------------------------
   @Override
   public void onSendValues(DataSendToQueryEvent e, Usuario_tiene_Permiso argument)
   {
      if (!argument.getId_usuario().equals(Usuario_tiene_Permiso.EMPTY_STRING))
      {
          if (e.isCreateOperation())
              e.addString("id_usuario", argument.getId_usuario());
          
          else if (e.isUpdateOperation())
          {
              e.addString("id_usuario", argument.getId_usuario());
              e.addString("id_usuario", QueryParameter.Operator.EQUAL, argument.getId_usuario_Viejo());
          }

          else
              e.addString("id_usuario", QueryParameter.Operator.EQUAL, argument.getId_usuario_Viejo());
      }
      
      if (argument.getId_origen_dato() != Usuario_tiene_Permiso.EMPTY_INT)
      {
          if (e.isCreateOperation())
              e.addInteger("id_origen_dato", argument.getId_origen_dato());
          
          else if (e.isUpdateOperation())
          {
              e.addInteger("id_origen_dato", argument.getId_origen_dato());
              e.addInteger("id_origen_dato", QueryParameter.Operator.EQUAL, argument.getId_origen_dato_Viejo());
          }

          else
              e.addInteger("id_origen_dato", QueryParameter.Operator.EQUAL, argument.getId_origen_dato_Viejo());
      }
      
      if (argument.getId_modulo_Viejo() != Usuario_tiene_Permiso.EMPTY_INT)
      {
          if (e.isCreateOperation())
              e.addInteger("id_modulo", argument.getId_modulo());
          
          else if (e.isUpdateOperation())
          {
              e.addInteger("id_modulo", argument.getId_modulo());
              e.addInteger("id_modulo", QueryParameter.Operator.EQUAL, argument.getId_modulo_Viejo());
          }

          else
              e.addInteger("id_modulo", QueryParameter.Operator.EQUAL, argument.getId_modulo_Viejo());
      }
      
      if (!argument.getLlave().equals( Usuario_tiene_Permiso.EMPTY_STRING))
      {
          if (e.isCreateOperation())
              e.addString("llave_permiso", argument.getLlave());
          
          else if (e.isUpdateOperation())
          {
              e.addString("llave_permiso", argument.getLlave());
              e.addString("llave_permiso", QueryParameter.Operator.EQUAL, argument.getLlave_Viejo());
          }

          else
              e.addString("llave_permiso", QueryParameter.Operator.EQUAL, argument.getLlave_Viejo());
      }
            
      if (argument.isSet())
      {
          if (e.isCreateOperation() || e.isUpdateOperation())
              e.addBoolean("es_usado", argument.esUsado());

          else if (!argument.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
              e.addBoolean("es_usado", QueryParameter.Operator.EQUAL,  argument.esUsado());
      }
 
   }

   //---------------------------------------------------------------------
   @Override
    public Usuario_tiene_Permiso onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
   {
      Usuario_tiene_Permiso current = new Usuario_tiene_Permiso();


      for (int i = 1; i <= e.getColumnCount(); i++)
      {
          switch (e.getColumnName(i))
          {
              case "id_usuario":
                  current.setId_usuario(e.getString(i));
                  break;
              case "id_origen_dato":
                  current.setId_origen_dato(e.getInt(i));
                  break;
              case "id_modulo":
                  current.setId_modulo(e.getInt(i));
                  break;
              case "llave_permiso":
                  current.setLlave(e.getString(i));
                  break;
              case "es_usado":
                  current.setUsado(e.getBoolean(i));
                  break;
          }
      }

      current.setSearchOnlyByPrimaryKey(true);
      current.acceptChanges();

      return current;
   }  
}
