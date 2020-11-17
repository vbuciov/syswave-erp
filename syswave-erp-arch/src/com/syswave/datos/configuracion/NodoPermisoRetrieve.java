package com.syswave.datos.configuracion;

import com.syswave.datos.common.SingletonDataAccess;
import com.syswave.entidades.configuracion.NodoPermiso;
import datalayer.api.IMediatorDataSource;
import datalayer.events.DataGetEvent;
import datalayer.utils.QueryDataTransfer;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class NodoPermisoRetrieve extends SingletonDataAccess<NodoPermiso>
{
   //---------------------------------------------------------------------
   public NodoPermisoRetrieve(IMediatorDataSource mysource)
   {
        super(mysource, "");
        setWithAutoID(false);
   }
   
   //---------------------------------------------------------------------
   public List<NodoPermiso> Retrieve (NodoPermiso valores)
   {
      QueryDataTransfer parametros = new QueryDataTransfer("semilla_permisos");
      
      parametros.addString("videntificador", valores.getId_usuario());
      parametros.addInteger("vid_origen_dato", valores.getId_origen_dato());
   
      return executeReadStoredProcedure(parametros); //MySQL
      //return RetrieveFromFunction("semilla_permisos", parametros, this); //PostGres
   }

   //---------------------------------------------------------------------
   @Override
  public NodoPermiso onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
   {
     NodoPermiso current = new NodoPermiso();

      for (int i = 1; i <= e.getColumnCount(); i++)
      {
         switch (e.getColumnName(i))
         {
             case "tiene":
                 current.setExiste(e.getBoolean(i));
                 current.setSelected(e.getBoolean(i));
                 break;
             case "id":
                 current.setId_modulo(e.getInt(i));
                 break;
             case "llave":
                 current.setLlave(e.getString(i));
                 break;
             case "valor":
                 current.setValor(e.getString(i));
                 break;
             case "id_padre":
                 current.setIdPadre(e.getInt(i));
                 break;
             case "nivel":
                 current.setNivel(e.getInt(i));
                 break;
         }
      }
      
      current.setSearchOnlyByPrimaryKey(true);
      current.acceptChanges();

      return current;
    }  
}