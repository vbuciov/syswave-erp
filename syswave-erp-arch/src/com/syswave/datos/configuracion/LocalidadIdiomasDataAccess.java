package com.syswave.datos.configuracion;

import com.syswave.datos.common.SingletonDataAccess;
import com.syswave.entidades.configuracion.LocalidadIdioma;
import datalayer.api.IMediatorDataSource;
import datalayer.events.DataGetEvent;
import datalayer.events.DataSendToQueryEvent;
import datalayer.utils.QueryParameter;
import java.sql.SQLException;


/**
 * Esta clase brinda de acceso a los datos.
 * @author Victor Manuel Bucio Vargas
 */
public class LocalidadIdiomasDataAccess extends SingletonDataAccess<LocalidadIdioma> 
{   
    //---------------------------------------------------------------------
    public LocalidadIdiomasDataAccess(IMediatorDataSource mysource)
    {
        super(mysource, "localidad_idiomas", "idioma", "id_localidad", "descripcion");
    }
   
   //---------------------------------------------------------------------
   @Override
   public void onSendValues(DataSendToQueryEvent e, LocalidadIdioma argument)
   {
      if (!argument.getIdioma().equals( LocalidadIdioma.EMPTY_STRING))
      {
          if (e.isCreateOperation())
              e.addString("idioma", argument.getIdioma());
          
          else if (e.isUpdateOperation())
          {
              e.addString("idioma", argument.getIdioma());
              e.addString("idioma", QueryParameter.Operator.EQUAL, argument.getIdioma_Viejo());
          }

          else
              e.addString("idioma", QueryParameter.Operator.EQUAL, argument.getIdioma_Viejo());
      }
      
       if (argument.getId_localidad() != LocalidadIdioma.EMPTY_INT)
      {
          if (e.isCreateOperation())
              e.addInteger("id_localidad", argument.getId_localidad());
          
          else if (e.isUpdateOperation())
          {
              e.addInteger("id_localidad", argument.getId_localidad());
              e.addInteger("id_localidad", QueryParameter.Operator.EQUAL, argument.getId_localidad_Viejo());
          }

          else
              e.addInteger("id_localidad", QueryParameter.Operator.EQUAL, argument.getId_localidad_Viejo());
      }
      
      if (!argument.getDescripcion().equals(LocalidadIdioma.EMPTY_STRING) )
      {
          if (e.isCreateOperation() || e.isUpdateOperation())
              e.addString("descripcion", argument.getDescripcion());

          else if (!argument.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
              e.addString("descripcion", QueryParameter.Operator.EQUAL, argument.getDescripcion());
      }      
   }

   //---------------------------------------------------------------------
   @Override
   public LocalidadIdioma onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
   {
      LocalidadIdioma current = new LocalidadIdioma();

      for (int i = 1; i <= e.getColumnCount(); i++)
      {
          switch (e.getColumnName(i))
          {
              case "idioma":
                  current.setIdioma(e.getString(i));
                  break;
              case "id_localidad":
                  current.setId_localidad(e.getInt(i));
                  break;         
              case "descripcion":
                  current.setDescripcion(e.getString(i));
                  break;
          }
      }

      current.setSearchOnlyByPrimaryKey(true);
      current.acceptChanges();

      return current;
   }  
}