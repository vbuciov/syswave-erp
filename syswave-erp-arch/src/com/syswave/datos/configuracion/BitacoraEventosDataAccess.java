package com.syswave.datos.configuracion;

import com.syswave.datos.common.SingletonDataAccess;
import com.syswave.entidades.configuracion.BitacoraEvento;
import datalayer.api.IMediatorDataSource;
import datalayer.events.DataGetEvent;
import datalayer.events.DataSendToQueryEvent;
import datalayer.utils.QueryParameter;
import java.sql.SQLException;


/**
 * Esta clase brinda de acceso a los datos.
 * @author Victor Manuel Bucio Vargas
 */
public class BitacoraEventosDataAccess extends SingletonDataAccess<BitacoraEvento>
{  
    //--------------------------------------------------------------------
    public BitacoraEventosDataAccess(IMediatorDataSource mysource)
    {
        super(mysource, "bitacora_eventos", "id", "fecha", "mensaje", "origen", "es_tipo", "id_usuario");
    }
   
    //--------------------------------------------------------------------
    @Override
    protected void onConvertKeyResult(DataGetEvent e, BitacoraEvento value) throws SQLException, UnsupportedOperationException
    {
        value.setId(e.getInt(1));
    }

   //---------------------------------------------------------------------
     @Override
    protected void onSendValues(DataSendToQueryEvent e, BitacoraEvento argument)
    {
        if (argument.getId() != BitacoraEvento.EMPTY_INT)
        {
            /*is AutoID we don't need to send this value.
            if (e.isCreateOperation())
                e.addInteger("id", argument.getId());

            else*/ if (e.isUpdateOperation())
            {
                e.addInteger("id", argument.getId());
                e.addInteger("id", QueryParameter.Operator.EQUAL, argument.getId_Viejo());
            }

            else
                e.addInteger("id", QueryParameter.Operator.EQUAL, argument.getId_Viejo());
        }
      
      if (argument.getFecha().compareTo(BitacoraEvento.EMPTY_DATE) != 0)
      {
          if (e.isCreateOperation() || e.isUpdateOperation())
              e.addDate("fecha", argument.getFecha());

          else if (!argument.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
              e.addDate("fecha", QueryParameter.Operator.EQUAL, argument.getFecha());
      }
      
      if (!argument.getMensaje().equals(BitacoraEvento.EMPTY_STRING))
      {
          if (e.isCreateOperation() || e.isUpdateOperation())
              e.addString("mensaje", argument.getMensaje());

          else if (!argument.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
              e.addString("mensaje", QueryParameter.Operator.EQUAL, argument.getMensaje());
      }
      
      if (!argument.getOrigen().equals(BitacoraEvento.EMPTY_STRING))
      {
          if (e.isCreateOperation() || e.isUpdateOperation())
              e.addString("origen", argument.getOrigen());

          else if (!argument.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
              e.addString("origen", QueryParameter.Operator.EQUAL, argument.getOrigen());
      }
      
      if (argument.esTipo()!= BitacoraEvento.EMPTY_INT)
      {
          if (e.isCreateOperation() || e.isUpdateOperation())
              e.addInteger("es_tipo", argument.esTipo());

          else if (!argument.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
              e.addInteger("es_tipo", QueryParameter.Operator.EQUAL, argument.esTipo());
      }
      
      if (!argument.getId_usuario().equals(BitacoraEvento.EMPTY_STRING))
      {
          if (e.isCreateOperation() || e.isUpdateOperation())
              e.addString("id_usuario", argument.getId_usuario());

          else if (!argument.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
              e.addString("id_usuario", QueryParameter.Operator.EQUAL, argument.getId_usuario());
      }
   }

   //---------------------------------------------------------------------
   @Override
   public BitacoraEvento onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
   {
      BitacoraEvento current = new BitacoraEvento();

      for (int i = 1; i <= e.getColumnCount(); i++)
      {
          switch (e.getColumnName(i))
          {
              case "id":
                  current.setId(e.getInt(i));
                  break;
              case "fecha":
                  current.setFecha(e.getDate(i));
                  break;
              case "mensaje":
                  current.setMensaje(e.getString(i));
                  break;
              case "origen":
                  current.setOrigen(e.getString(i));
                  break;
              case "es_tipo":
                  current.setTipo(e.getInt(i));
                  break;
              case "id_usuario":
                  current.setId_usuario(e.getString(i));
                  break;
          }
      }

      current.setSearchOnlyByPrimaryKey(true);
      current.acceptChanges();

      return current;
   }  
}