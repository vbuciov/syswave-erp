package com.syswave.datos.miempresa;

import com.syswave.datos.common.SingletonRetrieveDataAccess;
import com.syswave.entidades.miempresa.Mantenimiento_tiene_Actividad;
import com.syswave.entidades.miempresa_vista.MantenimientoTieneActividad_5FN;
import datalayer.api.IMediatorDataSource;
import datalayer.events.DataGetEvent;
import datalayer.utils.QueryDataTransfer;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class CheckListActivadesRetrieve extends SingletonRetrieveDataAccess<MantenimientoTieneActividad_5FN> 
{
    public CheckListActivadesRetrieve(IMediatorDataSource mysource)
    {
        /*super("mantenimiento_tiene_actividades", "actividad", "id_mantenimiento", 
                                                 "linea_plan", "id_variante_plan");*/
        super(mysource);
    }
    
   //---------------------------------------------------------------------
   public List<MantenimientoTieneActividad_5FN> Retrieve (Mantenimiento_tiene_Actividad valores)
   {
      QueryDataTransfer parametros = new QueryDataTransfer("mantenimiento_tiene_actividades_5FN");
      parametros.addInteger("vid_mantenimiento", valores.getIdMantenimiento());
      parametros.addInteger("vid_variante", valores.getIdVariante());
   
      return executeReadStoredProcedure(parametros); //MySQL
      //return RetrieveFromFunction("mantenimiento_tiene_actividades_5FN", parametros, this); //PostGres
   }

   //--------------------------------------------------------------------    
    @Override
    public MantenimientoTieneActividad_5FN onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
    {
     MantenimientoTieneActividad_5FN current = new MantenimientoTieneActividad_5FN();
    
      for (int i = 1; i <= e.getColumnCount(); i++)
      {
          if (e.getColumnName(i).equals("tiene"))
          {
              current.setSelected(e.getBoolean(i));
              
              if (current.isSelected())
                current.acceptChanges();
          }
          
          else if (e.getColumnName(i).equals("actividad"))
              current.setActividad(e.getString(i));
          
          else if (e.getColumnName(i).equals("id_mantenimiento"))
              current.setIdMantenimiento(e.getInt(i));
             
          else if (e.getColumnName(i).equals("linea_plan"))
              current.setLinea(e.getInt(i));
          
           else if (e.getColumnName(i).equals("id_variante_plan"))
              current.setIdVariante(e.getInt(i));
      }
      
      current.setSearchOnlyByPrimaryKey(true);
      //current.acceptChanges(); Es otra lógica para esta operación.

      return current;
    }
}