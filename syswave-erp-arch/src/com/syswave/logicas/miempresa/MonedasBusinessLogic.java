package com.syswave.logicas.miempresa;

import com.syswave.datos.common.DataSourceManager;
import com.syswave.datos.miempresa.MonedasDataAccess;
import com.syswave.entidades.miempresa.Moneda;
import datalayer.api.IMediatorDataSource;
import java.util.List;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class MonedasBusinessLogic
{
    private String mensaje;
    private MonedasDataAccess tabla;
    
   //---------------------------------------------------------------------
    public MonedasBusinessLogic(){
        IMediatorDataSource mysource = DataSourceManager.getMainDataSourceInstance();
        tabla = new MonedasDataAccess(mysource);
    }
    
   //---------------------------------------------------------------------
    public MonedasBusinessLogic(String esquema)
    {
      this();
      tabla.setEschema(esquema);
    }
    
   //---------------------------------------------------------------------
    public boolean agregar (Moneda nuevo)
   {
      boolean resultado = tabla.Create(nuevo)>0;
      mensaje = tabla.getMessage();
           
      return resultado;
   }
    
   //---------------------------------------------------------------------
    public boolean agregar (List<Moneda> nuevos)
   {
      boolean resultado = tabla.Create(nuevos)>0;
      mensaje = tabla.getMessage();

      return resultado;
   }
    
    //---------------------------------------------------------------------
   public boolean actualizar (Moneda elemento)
   {
      boolean resultado =  tabla.Update(elemento)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
    //---------------------------------------------------------------------
  public boolean actualizar (List<Moneda> elementos)
   {
      boolean resultado = tabla.Update(elementos)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public boolean borrar (Moneda elemento)
   {
      boolean resultado = tabla.Delete(elemento)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public boolean borrar (List<Moneda> elementos)
   {
      boolean resultado = tabla.Delete(elementos)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public List<Moneda> obtenerLista ()
   {
      List<Moneda> resultado = tabla.Retrieve();
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public List<Moneda> obtenerLista (Moneda elemento)
   {
      List<Moneda> resultado = tabla.Retrieve(elemento);
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }

   //---------------------------------------------------------------------
   public boolean recargar (Moneda elemento)
   {
      List<Moneda> resultado = tabla.Retrieve(elemento);
      if (resultado.size() > 0)
      {
         elemento.copy(resultado.get(0));
      }
      
      mensaje = tabla.getMessage();
      
      return tabla.getState() != MonedasDataAccess.DATASOURCE_ERROR;
   }  
   
   //---------------------------------------------------------------------
   public boolean validar (Moneda elemento)
   {
      boolean esCorrecto = true;
      
      if (elemento.getId()!=Moneda.EMPTY_INT)
      {
         esCorrecto = false;
         mensaje = "Es necesario especificar una persona";
      }
      
      else if (!elemento.getNombre().equals(Moneda.EMPTY_STRING))
      {
         esCorrecto = false;
         mensaje = "Es necesario especificar los nombres";
      }
      
      return esCorrecto;
   }
   
   //---------------------------------------------------------------------
   public boolean esCorrecto ()
   {
      return tabla.getState() != MonedasDataAccess.DATASOURCE_ERROR;
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
   }
}