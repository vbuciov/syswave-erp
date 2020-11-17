package com.syswave.logicas.miempresa;

import com.syswave.datos.common.DataSourceManager;
import com.syswave.datos.miempresa.PersonaDireccionesDataAccess;
import com.syswave.entidades.miempresa.PersonaDireccion;
import datalayer.api.IMediatorDataSource;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class PersonaDireccionesBusinessLogic
{
    private String mensaje;
    private PersonaDireccionesDataAccess tabla;
    
   //---------------------------------------------------------------------
    public PersonaDireccionesBusinessLogic(){
        IMediatorDataSource mysource = DataSourceManager.getMainDataSourceInstance();
        tabla = new PersonaDireccionesDataAccess(mysource);
    }
    
   //---------------------------------------------------------------------
    public PersonaDireccionesBusinessLogic(String esquema)
    {
      this();
      tabla.setEschema(esquema);
    }
    
   //---------------------------------------------------------------------
    public boolean agregar (PersonaDireccion nuevo)
   {
      boolean resultado = tabla.Create(nuevo)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
    
   //---------------------------------------------------------------------
    public boolean agregar (List<PersonaDireccion> nuevos)
   {
      boolean resultado = tabla.Create(nuevos)>0;
      mensaje = tabla.getMessage();
     
      return resultado;
   }
    
    //---------------------------------------------------------------------
   public boolean actualizar (PersonaDireccion elemento)
   {
      boolean resultado =  tabla.Update(elemento)>0;
      mensaje = tabla.getMessage();
         
      return resultado;
   }
   
    //---------------------------------------------------------------------
  public boolean actualizar (List<PersonaDireccion> elementos)
   {
      boolean resultado = tabla.Update(elementos)>0;
      mensaje = tabla.getMessage();
       
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public boolean borrar (PersonaDireccion elemento)
   {
      boolean resultado = tabla.Delete(elemento)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public boolean borrar (List<PersonaDireccion> elementos)
   {
      boolean resultado = tabla.Delete(elementos)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public List<PersonaDireccion> obtenerLista ()
   {
      List<PersonaDireccion> resultado = tabla.Retrieve();
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public List<PersonaDireccion> obtenerLista (PersonaDireccion elemento)
   {
      List<PersonaDireccion> resultado = tabla.Retrieve(elemento);
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }

   //---------------------------------------------------------------------
   public boolean recargar (PersonaDireccion elemento)
   {
      List<PersonaDireccion> resultado = tabla.Retrieve(elemento);
      if (resultado.size() > 0)
      {
         elemento.copy(resultado.get(0));
      }
      
      mensaje = tabla.getMessage();
      
      return tabla.getState() != PersonaDireccionesDataAccess.DATASOURCE_ERROR;
   }
   
   
   //---------------------------------------------------------------------
   public boolean validar (PersonaDireccion elemento)
   {
      boolean esCorrecto = true;
      
      if (elemento.getIdPersona()!=PersonaDireccion.EMPTY_INT)
      {
         esCorrecto = false;
         mensaje = "Es necesario especificar una persona";
      }
      
      else if (elemento.getIdLocalidad()!=PersonaDireccion.EMPTY_INT)
      {
         esCorrecto = false;
         mensaje = "Es necesario especificar una localidad";
      }
      
      return esCorrecto;
   }
   
   //---------------------------------------------------------------------
   public boolean esCorrecto ()
   {
      return tabla.getState() != PersonaDireccionesDataAccess.DATASOURCE_ERROR;
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

   //---------------------------------------------------------------------
   public boolean guardar(List<PersonaDireccion> elementos, List<PersonaDireccion> borrados)
   {
      List<PersonaDireccion> nuevos = new ArrayList<>();
      List<PersonaDireccion> modificados = new ArrayList<>();
      
      for (PersonaDireccion actual : elementos)
      {
         if (actual.isNew())
            nuevos.add(actual);

         else if (actual.isModified())
            modificados.add(actual);
      }
         
      return (nuevos.isEmpty() || agregar(nuevos)) && (modificados.isEmpty() || actualizar(modificados)) &&  (borrados == null || borrados.isEmpty() || borrar(borrados));
  
   }

}