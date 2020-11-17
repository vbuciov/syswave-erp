package com.syswave.logicas.miempresa;

import com.syswave.datos.common.DataSourceManager;
import com.syswave.datos.miempresa.MantenimientoCostoDataAcces;
import com.syswave.entidades.miempresa.MantenimientoCosto;
import datalayer.api.IMediatorDataSource;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sis2
 */
public class MantenimientoCostosBusinessLogic
{
      private String mensaje;
    private MantenimientoCostoDataAcces tabla;
    //private MantenimientoCostosNavigableRetrieve vista;
 
    
   //---------------------------------------------------------------------
    public MantenimientoCostosBusinessLogic(){
        IMediatorDataSource mysource = DataSourceManager.getMainDataSourceInstance();
        tabla = new MantenimientoCostoDataAcces(mysource);
        //vista = new MantenimientoCostosNavigableRetrieve();
    
    }
    
   //---------------------------------------------------------------------
    public MantenimientoCostosBusinessLogic(String esquema)
    {
      this();
      tabla.setEschema(esquema);
      /*vista = new MantenimientoCostosNavigableRetrieve();
      vista.setEschema(esquema);*/
      
    }
    
   //---------------------------------------------------------------------
    public boolean agregar (MantenimientoCosto nuevo)
   {
      boolean resultado = tabla.Create(nuevo)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
    
   //---------------------------------------------------------------------
    public boolean agregar (List<MantenimientoCosto> nuevos)
   {
      boolean resultado = tabla.Create(nuevos)>0;
      mensaje = tabla.getMessage();
     
      return resultado;
   }
    
    //---------------------------------------------------------------------
   public boolean actualizar (MantenimientoCosto elemento)
   {
      boolean resultado =  tabla.Update(elemento)>0;
      mensaje = tabla.getMessage();
         
      return resultado;
   }
   
    //---------------------------------------------------------------------
  public boolean actualizar (List<MantenimientoCosto> elementos)
   {
      boolean resultado = tabla.Update(elementos)>0;
      mensaje = tabla.getMessage();
       
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public boolean borrar (MantenimientoCosto elemento)
   {
      boolean resultado = tabla.Delete(elemento)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public boolean borrar (List<MantenimientoCosto> elementos)
   {
      boolean resultado = tabla.Delete(elementos)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public List<MantenimientoCosto> obtenerLista ()
   {
      List<MantenimientoCosto> resultado = tabla.Retrieve();
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public List<MantenimientoCosto> obtenerLista (MantenimientoCosto elemento)
   {
      List<MantenimientoCosto> resultado = tabla.Retrieve(elemento);
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
    /*  public List<MantenimientoCostoNavigable> obtenerListaVista ()
   {
      List<MantenimientoCostoNavigable> resultado = vista.Retrieve();
      
      mensaje = vista.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public List<MantenimientoCostoNavigable> obtenerListaSeries (MantenimientoCostoNavigable elemento)
   {
      List<MantenimientoCostoNavigable> resultado = vista.Retrieve(elemento);
      
      mensaje = vista.getMessage();
      
      return resultado;
   }*/


   //---------------------------------------------------------------------
   public boolean recargar (MantenimientoCosto elemento)
   {
      List<MantenimientoCosto> resultado = tabla.Retrieve(elemento);
      if (resultado.size() > 0)
      {
         elemento.copy(resultado.get(0));
      }
      
      mensaje = tabla.getMessage();
      
      return tabla.getState() != MantenimientoCostoDataAcces.DATASOURCE_ERROR;
   }
   
   
   //---------------------------------------------------------------------
   public boolean validar (MantenimientoCosto elemento)
   {
      boolean esCorrecto = true;
      
      if (elemento.getIdMantenimiento()!=MantenimientoCosto.EMPTY_INT)
      {
         esCorrecto = false;
         mensaje = "Es necesario relacionar un mantenimiento";
      }
           
      return esCorrecto;
   }
   
   //---------------------------------------------------------------------
   public boolean esCorrecto ()
   {
      return tabla.getState() != MantenimientoCostoDataAcces.DATASOURCE_ERROR; 
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
      /*vista.setEschema(value);
      subDetail.setEschema(value);*/
   }  

   //---------------------------------------------------------------------
   public boolean guardar(List<MantenimientoCosto> elementos, List<MantenimientoCosto> borrados)
   {
      List<MantenimientoCosto> nuevos = new ArrayList<>();
      List<MantenimientoCosto> modificados = new ArrayList<>();
    
      for (MantenimientoCosto actual : elementos)
      {
         if (actual.isNew())
            nuevos.add(actual);

         else if (actual.isModified())
            modificados.add(actual);
      }
         
      return (nuevos.isEmpty() || agregar(nuevos)) && 
             (modificados.isEmpty() || actualizar(modificados)) &&  
             (borrados == null || borrados.isEmpty() || borrar(borrados));
   }    
}
