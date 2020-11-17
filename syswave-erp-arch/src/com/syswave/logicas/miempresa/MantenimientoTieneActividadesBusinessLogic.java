package com.syswave.logicas.miempresa;

import com.syswave.datos.common.DataSourceManager;
import com.syswave.datos.miempresa.CheckListActivadesRetrieve;
import com.syswave.datos.miempresa.MantenimientoTieneActividadesDataAccess;
import com.syswave.entidades.miempresa.Mantenimiento_tiene_Actividad;
import com.syswave.entidades.miempresa_vista.MantenimientoTieneActividad_5FN;
import com.syswave.logicas.exceptions.BusinessOperationException;
import datalayer.api.IMediatorDataSource;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class MantenimientoTieneActividadesBusinessLogic
{
    private String mensaje;
    private MantenimientoTieneActividadesDataAccess tabla;
    private CheckListActivadesRetrieve vista;
    
   //---------------------------------------------------------------------
    public MantenimientoTieneActividadesBusinessLogic(){
        IMediatorDataSource mysource = DataSourceManager.getMainDataSourceInstance();
        tabla = new MantenimientoTieneActividadesDataAccess(mysource);
        vista = new CheckListActivadesRetrieve(mysource);
    }
    
   //---------------------------------------------------------------------
    public MantenimientoTieneActividadesBusinessLogic(String esquema)
    {
      this();
      tabla.setEschema(esquema);
       vista.setEschema(esquema);
    }
    
   //---------------------------------------------------------------------
    public boolean agregar (Mantenimiento_tiene_Actividad nuevo)
   {
      boolean resultado = tabla.Create(nuevo)>0;
      mensaje = tabla.getMessage();
           
      return resultado;
   }
    
   //---------------------------------------------------------------------
    public boolean agregar (List<Mantenimiento_tiene_Actividad> nuevos)
   {
      boolean resultado = tabla.Create(nuevos)>0;
      mensaje = tabla.getMessage();

      return resultado;
   }
    
    //---------------------------------------------------------------------
   public boolean actualizar (Mantenimiento_tiene_Actividad elemento)
   {
      boolean resultado =  tabla.Update(elemento)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
    //---------------------------------------------------------------------
  public boolean actualizar (List<Mantenimiento_tiene_Actividad> elementos)
   {
      boolean resultado = tabla.Update(elementos)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public boolean borrar (Mantenimiento_tiene_Actividad elemento)
   {
      boolean resultado = tabla.Delete(elemento)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public boolean borrar (List<Mantenimiento_tiene_Actividad> elementos)
   {
      boolean resultado = tabla.Delete(elementos)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public List<Mantenimiento_tiene_Actividad> obtenerLista ()
   {
      List<Mantenimiento_tiene_Actividad> resultado = tabla.Retrieve();
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public List<Mantenimiento_tiene_Actividad> obtenerLista (Mantenimiento_tiene_Actividad elemento)
   {
      List<Mantenimiento_tiene_Actividad> resultado = tabla.Retrieve(elemento);
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public List<MantenimientoTieneActividad_5FN> obtenerListaRevision (Mantenimiento_tiene_Actividad filtro)
   {
      List<MantenimientoTieneActividad_5FN> resultado = vista.Retrieve(filtro);
      
      mensaje = vista.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public boolean recargar (Mantenimiento_tiene_Actividad elemento)
   {
      List<Mantenimiento_tiene_Actividad> resultado = tabla.Retrieve(elemento);
      if (resultado.size() > 0)
      {
         elemento.copy(resultado.get(0));
      }
      
      mensaje = tabla.getMessage();
      
      return tabla.getState() != MantenimientoTieneActividadesDataAccess.DATASOURCE_ERROR;
   }
   
   
   //---------------------------------------------------------------------
   public boolean validar (Mantenimiento_tiene_Actividad elemento) throws BusinessOperationException
   {
      boolean esCorrecto = true;
      
      if (elemento.getLinea() == Mantenimiento_tiene_Actividad.EMPTY_INT)
      {
         esCorrecto = false;
         throw new BusinessOperationException ("No se ha especificado la actividad realizada", 1);
      }
     
      else if (elemento.getIdMantenimiento()== Mantenimiento_tiene_Actividad.EMPTY_INT)
      {
         esCorrecto = false;
         throw new BusinessOperationException ("No se ha especificado el mantenimiento en el que se efectua la actividad", 1);
      }
      

      return esCorrecto;
   }
   
   
   //---------------------------------------------------------------------
   public boolean guardar (List<MantenimientoTieneActividad_5FN> actividades)
   {
      List<Mantenimiento_tiene_Actividad> nuevos = new ArrayList<>();
      List<Mantenimiento_tiene_Actividad> eliminados = new ArrayList<>();
      
      for (MantenimientoTieneActividad_5FN actual : actividades)
      {
          //Revisamos si el elemento seleccionado es nuevo.
          if (actual.isSelected())
          {
              if (actual.isNew())
                   nuevos.add(actual);
          }
          
          //Revisamos si el elemento existía previamente
          else if (actual.isModified())
               eliminados.add(actual);
      }
         
      return (nuevos.isEmpty() || agregar(nuevos)) && (eliminados.isEmpty() || borrar(eliminados));
   }
   
   //---------------------------------------------------------------------
   public boolean esCorrecto ()
   {
      return tabla.getState() != MantenimientoTieneActividadesDataAccess.DATASOURCE_ERROR;
   }
   
   //---------------------------------------------------------------------
   public String getMensaje ()
   {
      return mensaje;
   }
   
   //---------------------------------------------------------------------
   public String getEsquema()
   {
      return tabla.getEschema();
   }
   
   //---------------------------------------------------------------------
   public void setEsquema (String value)
   {
      tabla.setEschema(value);
      vista.setEschema(value);
   }
}
