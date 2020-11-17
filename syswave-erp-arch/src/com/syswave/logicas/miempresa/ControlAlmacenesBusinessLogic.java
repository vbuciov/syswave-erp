package com.syswave.logicas.miempresa;

import com.syswave.datos.common.DataSourceManager;
import com.syswave.datos.miempresa.ControlAlmacenesDataAccess;
import com.syswave.datos.miempresa.SeriesRetrieve;
import com.syswave.entidades.miempresa.ControlAlmacen;
import datalayer.api.IMediatorDataSource;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class ControlAlmacenesBusinessLogic
{
    private String mensaje;
    private ControlAlmacenesDataAccess tabla;
    private SeriesRetrieve vista;
    
   //---------------------------------------------------------------------
    public ControlAlmacenesBusinessLogic(){
        IMediatorDataSource mysource = DataSourceManager.getMainDataSourceInstance();
        tabla = new ControlAlmacenesDataAccess(mysource);
        vista = new SeriesRetrieve(mysource);
    }
    
   //---------------------------------------------------------------------
    public ControlAlmacenesBusinessLogic(String esquema)
    {
      this();
      tabla.setEschema(esquema);
      vista.setEschema(esquema);
    }
    
   //---------------------------------------------------------------------
    public boolean agregar (ControlAlmacen nuevo)
   {
      boolean resultado = tabla.Create(nuevo)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
    
   //---------------------------------------------------------------------
    public boolean agregar (List<ControlAlmacen> nuevos)
   {
      boolean resultado = tabla.Create(nuevos)>0;
      mensaje = tabla.getMessage();
     
      return resultado;
   }
    
    //---------------------------------------------------------------------
   public boolean actualizar (ControlAlmacen elemento)
   {
      boolean resultado =  tabla.Update(elemento)>0;
      mensaje = tabla.getMessage();
         
      return resultado;
   }
   
    //---------------------------------------------------------------------
  public boolean actualizar (List<ControlAlmacen> elementos)
   {
      boolean resultado = tabla.Update(elementos)>0;
      mensaje = tabla.getMessage();
       
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public boolean borrar (ControlAlmacen elemento)
   {
      boolean resultado = tabla.Delete(elemento)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public boolean borrar (List<ControlAlmacen> elementos)
   {
      boolean resultado = tabla.Delete(elementos)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public List<ControlAlmacen> obtenerLista ()
   {
      List<ControlAlmacen> resultado = tabla.Retrieve();
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public List<ControlAlmacen> obtenerListaConInfoSeries ()
   {
        List<ControlAlmacen> resultado = tabla.retrieveProduct();
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public List<ControlAlmacen> obtenerLista (ControlAlmacen elemento)
   {
      List<ControlAlmacen> resultado = tabla.Retrieve(elemento);
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public List<ControlAlmacen> obtenerListaSeries ()
   {
      List<ControlAlmacen> resultado = vista.Retrieve();
      
      mensaje = vista.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public List<ControlAlmacen> obtenerListaSeries (ControlAlmacen elemento)
   {
      List<ControlAlmacen> resultado = vista.Retrieve(elemento);
      
      mensaje = vista.getMessage();
      
      return resultado;
   }
   
         //---------------------------------------------------------------------
   public List<ControlAlmacen> obtenerListaSeriesCorta ()
   {
      List<ControlAlmacen> resultado = vista.SmallRetrieve();
      
      mensaje = vista.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public List<ControlAlmacen> obtenerListaSeriesCorta (ControlAlmacen elemento)
   {
      List<ControlAlmacen> resultado = vista.SmallRetrieve(elemento);
      
      mensaje = vista.getMessage();
      
      return resultado;
   }
 

   //---------------------------------------------------------------------
   public boolean recargar (ControlAlmacen elemento)
   {
      List<ControlAlmacen> resultado = tabla.Retrieve(elemento);
      if (resultado.size() > 0)
      {
         elemento.copy(resultado.get(0));
      }
      
      mensaje = tabla.getMessage();
      
      return tabla.getState() != ControlAlmacenesDataAccess.DATASOURCE_ERROR;
   }
   
   
   //---------------------------------------------------------------------
   public boolean validar (ControlAlmacen elemento)
   {
      boolean esCorrecto = true;
      
      if (elemento.getIdUbicacion()!=ControlAlmacen.EMPTY_INT)
      {
         esCorrecto = false;
         mensaje = "Es necesario especificar una ubicación";
      }
           
      return esCorrecto;
   }
   
   //---------------------------------------------------------------------
   public boolean esCorrecto ()
   {
      return tabla.getState() != ControlAlmacenesDataAccess.DATASOURCE_ERROR;
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
   public boolean guardar(List<ControlAlmacen> elementos, List<ControlAlmacen> borrados)
   {
      List<ControlAlmacen> nuevos = new ArrayList<>();
      List<ControlAlmacen> modificados = new ArrayList<>();
      
      for (ControlAlmacen actual : elementos)
      {
         if (actual.isNew())
            nuevos.add(actual);

         else if (actual.isModified())
            modificados.add(actual);
      }
         
      return (nuevos.isEmpty() || agregar(nuevos)) && (modificados.isEmpty() || actualizar(modificados)) &&  (borrados.isEmpty() || borrar(borrados));
   }
}