package com.syswave.logicas.miempresa;

import com.syswave.datos.common.DataSourceManager;
import com.syswave.datos.miempresa.MantenimientoDescripcionRetrieve;
import com.syswave.datos.miempresa.MantenimientoDescripcionesDataAccess;
import com.syswave.entidades.miempresa.MantenimientoDescripcion;
import com.syswave.entidades.miempresa_vista.MantenimientoDescripcion_5FN;
import com.syswave.logicas.exceptions.BusinessOperationException;
import datalayer.api.IMediatorDataSource;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class MantenimientoDescripcionesBusinessLogic
{
    private String mensaje;
    private MantenimientoDescripcionesDataAccess tabla;
    private MantenimientoDescripcionRetrieve vista;
    
   //---------------------------------------------------------------------
    public MantenimientoDescripcionesBusinessLogic(){
        IMediatorDataSource mysource = DataSourceManager.getMainDataSourceInstance();
        tabla = new MantenimientoDescripcionesDataAccess(mysource);
        vista = new MantenimientoDescripcionRetrieve(mysource);
    }
    
   //---------------------------------------------------------------------
    public MantenimientoDescripcionesBusinessLogic(String esquema)
    {
      this();
      tabla.setEschema(esquema);
       vista.setEschema(esquema);
    }
    
   //---------------------------------------------------------------------
    public boolean agregar (MantenimientoDescripcion nuevo)
   {
      boolean resultado = tabla.Create(nuevo)>0;
      mensaje = tabla.getMessage();
           
      return resultado;
   }
    
   //---------------------------------------------------------------------
    public boolean agregar (List<MantenimientoDescripcion> nuevos)
   {
      boolean resultado = tabla.Create(nuevos)>0;
      mensaje = tabla.getMessage();

      return resultado;
   }
    
    //---------------------------------------------------------------------
   public boolean actualizar (MantenimientoDescripcion elemento)
   {
      boolean resultado =  tabla.Update(elemento)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
    //---------------------------------------------------------------------
  public boolean actualizar (List<MantenimientoDescripcion> elementos)
   {
      boolean resultado = tabla.Update(elementos)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public boolean borrar (MantenimientoDescripcion elemento)
   {
      boolean resultado = tabla.Delete(elemento)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public boolean borrar (List<MantenimientoDescripcion> elementos)
   {
      boolean resultado = tabla.Delete(elementos)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public List<MantenimientoDescripcion> obtenerLista ()
   {
      List<MantenimientoDescripcion> resultado = tabla.Retrieve();
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public List<MantenimientoDescripcion> obtenerLista (MantenimientoDescripcion elemento)
   {
      List<MantenimientoDescripcion> resultado = tabla.Retrieve(elemento);
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   
   //---------------------------------------------------------------------
   public List<MantenimientoDescripcion_5FN> obtenerListaVista (int id_mantenimiento)
   {
      List<MantenimientoDescripcion_5FN> resultado = vista.Retrieve(id_mantenimiento);
      
      mensaje = vista.getMessage();
      
      return resultado;
   }

   //---------------------------------------------------------------------
   public boolean recargar (MantenimientoDescripcion elemento)
   {
      List<MantenimientoDescripcion> resultado = tabla.Retrieve(elemento);
      if (resultado.size() > 0)
      {
         elemento.copy(resultado.get(0));
      }
      
      mensaje = tabla.getMessage();
      
      return tabla.getState() != MantenimientoDescripcionesDataAccess.DATASOURCE_ERROR;
   }
   
   
   //---------------------------------------------------------------------
   public boolean validar (MantenimientoDescripcion elemento) throws BusinessOperationException
   {
      boolean esCorrecto = true;
      
      if (elemento.getTexto().equals(MantenimientoDescripcion.EMPTY_STRING))
      {
         esCorrecto = false;
         throw new BusinessOperationException ("Es necesario anotar un texto en la descripción", 1);
      }
     
      
      else if (elemento.getIdMantenimiento() == MantenimientoDescripcion.EMPTY_INT)
      {
         esCorrecto = false;
         throw new BusinessOperationException ("Es necesario relacionar la descripcion con un matenimiento", 1);
      }
      

      return esCorrecto;
   }
   
   //---------------------------------------------------------------------
   public boolean esCorrecto ()
   {
      return tabla.getState() != MantenimientoDescripcionesDataAccess.DATASOURCE_ERROR  && 
              vista.getState() != MantenimientoDescripcionesDataAccess.DATASOURCE_ERROR;
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

   //---------------------------------------------------------------------
    public boolean guardar(List<MantenimientoDescripcion_5FN> elementos)
    {
      List<MantenimientoDescripcion> nuevos = new ArrayList<>();
      List<MantenimientoDescripcion> modificados = new ArrayList<>();
      List<MantenimientoDescripcion> borrados = new ArrayList<>();
      
      for (MantenimientoDescripcion_5FN actual : elementos)
      {
         if (actual.isNew())
            nuevos.add(actual);

         else if (actual.isModified())
         {
            if (!actual.getTexto().isEmpty())
               modificados.add(actual);
            
            else
               borrados.add(actual);
         }
      }
         
      return (nuevos.isEmpty() || agregar(nuevos)) && (modificados.isEmpty() || actualizar(modificados)) &&  (borrados.isEmpty() || borrar(borrados));
  
    }
}
