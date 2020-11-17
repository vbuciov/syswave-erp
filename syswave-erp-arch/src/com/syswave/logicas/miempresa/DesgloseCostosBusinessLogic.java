package com.syswave.logicas.miempresa;

import com.syswave.datos.common.DataSourceManager;
import com.syswave.datos.miempresa.DesgloseCostos_5FNRetrieve;
import com.syswave.datos.miempresa.DesglosesCostosDataAccess;
import com.syswave.entidades.miempresa.DesgloseCosto;
import com.syswave.entidades.miempresa_vista.DesgloseCosto_5FN;
import datalayer.api.IMediatorDataSource;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class DesgloseCostosBusinessLogic
{
     private String mensaje;
    private DesglosesCostosDataAccess tabla;
    private DesgloseCostos_5FNRetrieve vista;
 
    
   //---------------------------------------------------------------------
    public DesgloseCostosBusinessLogic(){
        IMediatorDataSource mysource = DataSourceManager.getMainDataSourceInstance();
        tabla = new DesglosesCostosDataAccess(mysource);
        vista = new DesgloseCostos_5FNRetrieve(mysource);
    }
    
   //---------------------------------------------------------------------
    public DesgloseCostosBusinessLogic(String esquema)
    {
      this();
      tabla.setEschema(esquema);
      vista.setEschema(esquema);
    }
    
   //---------------------------------------------------------------------
    public boolean agregar (DesgloseCosto nuevo)
   {
      boolean resultado = tabla.Create(nuevo)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
    
   //---------------------------------------------------------------------
    public boolean agregar (List<DesgloseCosto> nuevos)
   {
      boolean resultado = tabla.Create(nuevos)>0;
      mensaje = tabla.getMessage();
     
      return resultado;
   }
    
    //---------------------------------------------------------------------
   public boolean actualizar (DesgloseCosto elemento)
   {
      boolean resultado =  tabla.Update(elemento)>0;
      mensaje = tabla.getMessage();
         
      return resultado;
   }
   
    //---------------------------------------------------------------------
  public boolean actualizar (List<DesgloseCosto> elementos)
   {
      boolean resultado = tabla.Update(elementos)>0;
      mensaje = tabla.getMessage();
       
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public boolean borrar (DesgloseCosto elemento)
   {
      boolean resultado = tabla.Delete(elemento)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public boolean borrar (List<DesgloseCosto> elementos)
   {
      boolean resultado = tabla.Delete(elementos)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public List<DesgloseCosto> obtenerLista ()
   {
      List<DesgloseCosto> resultado = tabla.Retrieve();
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public List<DesgloseCosto> obtenerLista (DesgloseCosto elemento)
   {
      List<DesgloseCosto> resultado = tabla.Retrieve(elemento);
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   /*   public List<DesgloseCosto_5FN> obtenerListaVista ()
   {
      List<DesgloseCosto_5FN> resultado = vista.Retrieve();
      
      mensaje = vista.getMessage();
      
      return resultado;
   }*/
   
   //---------------------------------------------------------------------
   public List<DesgloseCosto_5FN> obtenerListaVista (DesgloseCosto filtro)
   {
      List<DesgloseCosto_5FN> resultado = vista.Retrieve(filtro);
      
      mensaje = vista.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public boolean recargar (DesgloseCosto elemento)
   {
      List<DesgloseCosto> resultado = tabla.Retrieve(elemento);
      if (resultado.size() > 0)
      {
         elemento.copy(resultado.get(0));
      }
      
      mensaje = tabla.getMessage();
      
      return tabla.getState() != DesglosesCostosDataAccess.DATASOURCE_ERROR;
   }
   
   
   //---------------------------------------------------------------------
   public boolean validar (DesgloseCosto elemento)
   {
      boolean esCorrecto = true;
      
      if (elemento.getIdPrecioIndirecto()!=DesgloseCosto.EMPTY_INT)
      {
         esCorrecto = false;
         mensaje = "Es necesario especificar el precio propietario del análisis";
      }
           
      return esCorrecto;
   }
   
   //---------------------------------------------------------------------
   public boolean esCorrecto ()
   {
      return tabla.getState() != DesglosesCostosDataAccess.DATASOURCE_ERROR &&
              vista.getState() != DesgloseCostos_5FNRetrieve.DATASOURCE_ERROR; 
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
   /*public boolean guardar(List<DesgloseCosto> elementos, List<DesgloseCosto> borrados)
   {
      List<DesgloseCosto> nuevos = new ArrayList<>();
      List<DesgloseCosto> modificados = new ArrayList<>();

      
      for (DesgloseCosto actual : elementos)
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
   */
    //---------------------------------------------------------------------
   public boolean guardar(List<DesgloseCosto_5FN> elementos, List<DesgloseCosto> borrados)
   {
      List<DesgloseCosto> nuevos = new ArrayList<>();
      List<DesgloseCosto> modificados = new ArrayList<>();

      
      for (DesgloseCosto_5FN actual : elementos)
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
