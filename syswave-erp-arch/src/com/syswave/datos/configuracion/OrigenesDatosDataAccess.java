package com.syswave.datos.configuracion;

import com.syswave.datos.common.SingletonDataAccess;
import com.syswave.entidades.configuracion.OrigenDato;
import datalayer.api.IMediatorDataSource;
import datalayer.events.DataGetEvent;
import datalayer.events.DataSendToQueryEvent;
import datalayer.utils.QueryParameter;
import java.sql.SQLException;


/**
 * Esta clase brinda de acceso a los datos.
 * @author Victor Manuel Bucio Vargas
 */
public class OrigenesDatosDataAccess extends SingletonDataAccess<OrigenDato> 
{
    //---------------------------------------------------------------------
    public OrigenesDatosDataAccess(IMediatorDataSource mysource)
    {
        super(mysource,"origenes_datos", "id", "nombre", "es_activo");
    }
    
    //--------------------------------------------------------------------
    @Override
    protected void onConvertKeyResult(DataGetEvent e, OrigenDato value) throws SQLException, UnsupportedOperationException
    {
        value.setId(e.getInt(1));
    }

   //---------------------------------------------------------------------
   @Override
   public void onSendValues(DataSendToQueryEvent e, OrigenDato argument)
   {
        if (argument.getId() != OrigenDato.EMPTY_INT)
        {

        /*  if (e.isCreateOperation())
              e.addInteger("id", argument.getId());
          
          else*/ if (e.isUpdateOperation())
          {
              e.addInteger("id", argument.getId());
              e.addInteger("id", QueryParameter.Operator.EQUAL, argument.getId_Viejo());
          }

          else 
              e.addInteger("id", QueryParameter.Operator.EQUAL, argument.getId_Viejo());
        }
      
      
      if (!argument.getNombre().equals(OrigenDato.EMPTY_STRING))
      {
          if (e.isCreateOperation() || e.isUpdateOperation())
              e.addString("nombre", argument.getNombre());

          else if (!argument.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
              e.addString("nombre", QueryParameter.Operator.EQUAL, argument.getNombre());
      }
      
      if (!argument.isNew() || !argument.esActivo())
      {
          if (e.isCreateOperation() || e.isUpdateOperation())
              e.addBoolean("es_activo", argument.esActivo());

          else if (!argument.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
              e.addBoolean("es_activo", QueryParameter.Operator.EQUAL,  argument.esActivo());
      }
   }

   //---------------------------------------------------------------------
   @Override
   public OrigenDato onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
   {
      OrigenDato current = new OrigenDato();

      for (int i = 1; i <= e.getColumnCount(); i++)
      {
          switch (e.getColumnName(i))
          {
              case "id":
                  current.setId(e.getInt(i));
                  break;
              case "nombre":
                  current.setNombre(e.getString(i));
                  break;
              case "es_activo":
                  current.setActivo(e.getBoolean(i));
                  break;
          }
      }

      current.setSearchOnlyByPrimaryKey(true);
      current.acceptChanges();

      return current;
   }  
}