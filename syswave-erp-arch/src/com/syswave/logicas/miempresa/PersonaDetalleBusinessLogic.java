package com.syswave.logicas.miempresa;

import com.syswave.datos.common.DataSourceManager;
import com.syswave.datos.miempresa.PersonaDetallesDataAccess;
import com.syswave.datos.miempresa.PersonaDetallesRetrieve;
import com.syswave.entidades.miempresa.PersonaDetalle;
import com.syswave.entidades.miempresa_vista.PersonaDetalle_5FN;
import datalayer.api.IMediatorDataSource;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class PersonaDetalleBusinessLogic
{
     private String mensaje;
    private PersonaDetallesDataAccess tabla;
    private PersonaDetallesRetrieve vista;
   // private ValorRetrieve aux;*/
    
   //---------------------------------------------------------------------
    public PersonaDetalleBusinessLogic(){
        IMediatorDataSource mysource = DataSourceManager.getMainDataSourceInstance();
        tabla = new PersonaDetallesDataAccess(mysource);
        vista = new PersonaDetallesRetrieve(mysource);
        /*aux = new ValorRetrieve();*/
    }
    
   //---------------------------------------------------------------------
    public PersonaDetalleBusinessLogic(String esquema)
    {
      this();
      tabla.setEschema(esquema);
      vista.setEschema(esquema);
      /* aux = new ValorRetrieve();
       aux.setEschema(esquema);*/
    }
    
   //---------------------------------------------------------------------
    public boolean agregar (PersonaDetalle nuevo)
   {
      boolean resultado = tabla.Create(nuevo)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
    
   //---------------------------------------------------------------------
    public boolean agregar (List<PersonaDetalle> nuevos)
   {
      boolean resultado = tabla.Create(nuevos)>0;
      mensaje = tabla.getMessage();
        
      return resultado;
   }
    
    //---------------------------------------------------------------------
   public boolean actualizar (PersonaDetalle elemento)
   {
      boolean resultado =  tabla.Update(elemento)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
    //---------------------------------------------------------------------
  public boolean actualizar (List<PersonaDetalle> elementos)
   {
      boolean resultado = tabla.Update(elementos)>0;
      mensaje = tabla.getMessage();
         
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public boolean borrar (PersonaDetalle elemento)
   {
      boolean resultado = tabla.Delete(elemento)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public boolean borrar (List<PersonaDetalle> elementos)
   {
      boolean resultado = tabla.Delete(elementos)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public List<PersonaDetalle> obtenerLista ()
   {
      List<PersonaDetalle> resultado = tabla.Retrieve();
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public List<PersonaDetalle> obtenerLista (PersonaDetalle elemento)
   {
      List<PersonaDetalle> resultado = tabla.Retrieve(elemento);
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
      //---------------------------------------------------------------------
   /**
    * Obtiene aquellos elementos que coinciden en información con el elemento recibido
    * @param id_persona
    * @return Una lista con los elementos.
    */
    public List<PersonaDetalle_5FN> obtenerListaVista (int id_persona)
   {
      List<PersonaDetalle_5FN> resultado = vista.Retrieve(id_persona);
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }

   //---------------------------------------------------------------------
   public boolean recargar (PersonaDetalle elemento)
   {
      List<PersonaDetalle> resultado = tabla.Retrieve(elemento);
      if (resultado.size() > 0)
      {
         elemento.copy(resultado.get(0));
      }
      
      mensaje = tabla.getMessage();
      
      return tabla.getState() != PersonaDetallesDataAccess.DATASOURCE_ERROR;
   }
   
   
   //---------------------------------------------------------------------
   /**
    * Obtiene todos los tipos de medio
    */
   /*public List<Valor> obtenerTiposDetalle ()
   {
      List<Valor> resultado = aux.retrieveTiposMedio();
      
      mensaje = aux.getMessage();
      
      return resultado;
   }*/
   
   //---------------------------------------------------------------------
   /**
    * Obtiene todos los elementos que tienen relación con otra entidad.
    */
   /*public List<Valor> obtenerTiposDetalleUtilizados ()
   {
      List<Valor> resultado = aux.retrieveTiposMedioReferenciado();
      
      mensaje = aux.getMessage();
      
      return resultado;
   }*/
   
   
      //---------------------------------------------------------------------
   /**
    * Obtiene todos los tipos de medio que cumplen con los criterios.
    * @param filtro
    */
   /*public List<Valor> obtenerTiposDetalle (Valor filtro)
   {
      List<Valor> resultado = aux.retrieveTiposMedio(filtro);
      
      mensaje = aux.getMessage();
      
      return resultado;
   }*/
   
   
   //---------------------------------------------------------------------
   public boolean validar (PersonaDetalle elemento)
   {
      boolean esCorrecto = true;
      
      if (elemento.getIdPersona()!=PersonaDetalle.EMPTY_INT)
      {
         esCorrecto = false;
         mensaje = "Es necesario especificar una persona";
      }
      
      else if (elemento.getValor()!=PersonaDetalle.EMPTY_STRING)
      {
         esCorrecto = false;
         mensaje = "Es necesario especificar el valor";
      }
      
      return esCorrecto;
   }
   
   //---------------------------------------------------------------------
   public boolean guardar(List<PersonaDetalle_5FN> elementos)
   {
      List<PersonaDetalle> nuevos = new ArrayList<>();
      List<PersonaDetalle> modificados = new ArrayList<>();
       List<PersonaDetalle> borrados = new ArrayList<>();
      
      for (PersonaDetalle actual : elementos)
      {
         if (actual.isNew())
            nuevos.add(actual);

         else if (actual.isModified())
         {
            if (!actual.getValor().isEmpty())
               modificados.add(actual);
            
            else
               borrados.add(actual);
         }
      }
         
      return (nuevos.isEmpty() || agregar(nuevos)) && (modificados.isEmpty() || actualizar(modificados)) &&  (borrados.isEmpty() || borrar(borrados));
  
   }  
   
   //---------------------------------------------------------------------
   public boolean guardar(List<PersonaDetalle> elementos, List<PersonaDetalle> borrados)
   {
      List<PersonaDetalle> nuevos = new ArrayList<>();
      List<PersonaDetalle> modificados = new ArrayList<>();
      
      for (PersonaDetalle actual : elementos)
      {
         if (actual.isNew())
            nuevos.add(actual);

         else if (actual.isModified())
         {
            if (!actual.getValor().isEmpty())
               modificados.add(actual);
            
            else
               borrados.add(actual);
         }
      }
         
      return (nuevos.isEmpty() || agregar(nuevos)) && (modificados.isEmpty() || actualizar(modificados)) &&  (borrados == null || borrados.isEmpty() || borrar(borrados));
  
   }  
   
   //---------------------------------------------------------------------
   public boolean esCorrecto ()
   {
      return tabla.getState() != PersonaDetallesDataAccess.DATASOURCE_ERROR; 
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
       aux.setEschema(value);*/
   }
}
