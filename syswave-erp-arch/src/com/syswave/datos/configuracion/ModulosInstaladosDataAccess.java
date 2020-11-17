package com.syswave.datos.configuracion;

import com.syswave.datos.common.SingletonDataAccess;
import com.syswave.entidades.configuracion.ModuloInstalado;
import datalayer.api.IMediatorDataSource;
import datalayer.events.DataGetEvent;
import datalayer.events.DataSendToQueryEvent;
import datalayer.utils.QueryDataTransfer;
import datalayer.utils.QueryParameter;
import java.sql.SQLException;
import java.util.List;


/**
 * Esta clase brinda de acceso a los datos.
 * @author Victor Manuel Bucio Vargas
 */
public class ModulosInstaladosDataAccess extends SingletonDataAccess<ModuloInstalado> 
{
    //---------------------------------------------------------------------
    public ModulosInstaladosDataAccess(IMediatorDataSource mysource)
    {
        super(mysource,"modulos_instalados", "id", "titulo", "uri", "icon", 
                                    "id_padre", "nivel", "es_activo", 
                                    "es_estatico");
        setBasicOrderBy("nivel", "id_padre");
    }
     
   //---------------------------------------------------------------------
   public List<ModuloInstalado> RetrieveIdentifierLeafs (ModuloInstalado Filter)
   {
      QueryDataTransfer parametros = new QueryDataTransfer("modulo_instalado_view_select");
     
      parametros.addString("vuri", Filter.getURI());
      parametros.addBoolean("ves_activo", Filter.esActivo());
      parametros.addBoolean("ves_estatico", Filter.esEstatico());
      
      return executeReadStoredProcedure(parametros);
   }
   
    //--------------------------------------------------------------------
    @Override
    protected void onConvertKeyResult(DataGetEvent e, ModuloInstalado value) throws SQLException, UnsupportedOperationException
    {
        value.setId(e.getInt(1));
    }

   //---------------------------------------------------------------------
   @Override
   public void onSendValues(DataSendToQueryEvent e, ModuloInstalado argument)
   {
               if (argument.getId() != ModuloInstalado.EMPTY_INT)
        {
        /*is AutoID we don't need to send this value.
        if (e.isCreateOperation())
            e.addInteger("id", argument.getId());

        else */if (e.isUpdateOperation())
        {
            e.addInteger("id", argument.getId());
            e.addInteger("id", QueryParameter.Operator.EQUAL, argument.getId_Viejo());
        }

        else
            e.addInteger("id", QueryParameter.Operator.EQUAL, argument.getId_Viejo());
        }
      
      if (!argument.getTitulo().equals(ModuloInstalado.EMPTY_STRING))
      {
          if (e.isCreateOperation() || e.isUpdateOperation())
              e.addString("titulo", argument.getTitulo());

          else if (!argument.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
              e.addString("titulo", QueryParameter.Operator.EQUAL, argument.getTitulo());
      }
      
      if (!argument.getURI().equals(ModuloInstalado.EMPTY_STRING))
      {
          if (e.isCreateOperation() || e.isUpdateOperation())
              e.addString("uri", argument.getURI());

          else if (!argument.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
              e.addString("uri", QueryParameter.Operator.EQUAL, argument.getURI());
      }
      
      if (!argument.getIcon().equals(ModuloInstalado.EMPTY_STRING))
      {
          if (e.isCreateOperation() || e.isUpdateOperation())
              e.addString("icon", argument.getIcon());

          else if (!argument.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
              e.addString("icon", QueryParameter.Operator.EQUAL, argument.getIcon());
      }
      
      if (argument.getIdPadre() != ModuloInstalado.EMPTY_INT)
      {
          if (e.isCreateOperation() || e.isUpdateOperation())
              e.addInteger("id_padre", argument.getIdPadre());

          else if (!argument.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
              e.addInteger("id_padre", QueryParameter.Operator.EQUAL, argument.getIdPadre());
      }
      
      if (argument.getNivel()!= ModuloInstalado.EMPTY_INT)
      {
          if (e.isCreateOperation() || e.isUpdateOperation())
              e.addInteger("nivel", argument.getNivel());

          else if (!argument.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
              e.addInteger("nivel", QueryParameter.Operator.EQUAL, argument.getNivel());
      }
      
      if (argument.isSet())
      {
         if (e.isCreateOperation() || e.isUpdateOperation())
             e.addBoolean("es_activo", argument.esActivo());
    
         else  if (!argument.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
             e.addBoolean("es_activo", QueryParameter.Operator.EQUAL,  argument.esActivo());

         if (e.isCreateOperation() || e.isUpdateOperation())
             e.addBoolean("es_estatico", argument.esEstatico());

         else if (!argument.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
              e.addBoolean("es_estatico", QueryParameter.Operator.EQUAL,  argument.esEstatico());
       }
   }

   //---------------------------------------------------------------------
   @Override
   public ModuloInstalado onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
   {
      ModuloInstalado current = new ModuloInstalado();

      for (int i = 1; i <= e.getColumnCount(); i++)
      {
          switch (e.getColumnName(i))
          {
              case "id":
                  current.setId(e.getInt(i));
                  break;
              case "titulo":
                  current.setTitulo(e.getString(i));
                  break;
              case "uri":
                  current.setURI(e.getString(i));
                  break;
              case "icon":
                  current.setIcon(e.getString(i));
                  break;
              case "id_padre":
                  current.setIdPadre(e.getInt(i));
                  break;
              case "nivel":
                  current.setNivel(e.getInt(i));
                  break;
              case "es_activo":
                  current.setActivo(e.getBoolean(i));
                  break;
              case "es_estatico":
                  current.setEstatico(e.getBoolean(i));
                  break;
              case "is_leaf":
                  current.setLeaf(e.getBoolean(i));
                  break;
          }          
      }

      current.setSearchOnlyByPrimaryKey(true);
      current.acceptChanges();

      return current;
   }  
}