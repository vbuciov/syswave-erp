package com.syswave.logicas.miempresa;

import com.syswave.datos.common.DataSourceManager;
import com.syswave.datos.miempresa.PersonaIdentificadoresDataAccess;
import com.syswave.datos.miempresa.PersonaIdentificadoresRetrieve;
import com.syswave.datos.miempresa.ValorRetrieve;
import com.syswave.entidades.miempresa.PersonaIdentificador;
import com.syswave.entidades.miempresa.Valor;
import com.syswave.entidades.miempresa_vista.PersonaIdentificadorVista;
import datalayer.api.IMediatorDataSource;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class PersonaIdentificadoresBusinessLogic
{
    private String mensaje;
    private PersonaIdentificadoresDataAccess tabla;
    private PersonaIdentificadoresRetrieve vista;
    private ValorRetrieve aux;
    
   //---------------------------------------------------------------------
    public PersonaIdentificadoresBusinessLogic(){
        IMediatorDataSource mysource = DataSourceManager.getMainDataSourceInstance();
        tabla = new PersonaIdentificadoresDataAccess(mysource);
        vista = new PersonaIdentificadoresRetrieve(mysource);
        aux = new ValorRetrieve(mysource);
    }
    
   //---------------------------------------------------------------------
    public PersonaIdentificadoresBusinessLogic(String esquema)
    {
      this();
      tabla.setEschema(esquema);
      vista.setEschema(esquema);
      aux.setEschema(esquema);
    }
    
   //---------------------------------------------------------------------
    public boolean agregar (PersonaIdentificador nuevo)
   {
      boolean resultado = tabla.Create(nuevo)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
    
   //---------------------------------------------------------------------
    public boolean agregar (List<PersonaIdentificador> nuevos)
   {
      boolean resultado = tabla.Create(nuevos)>0;
      mensaje = tabla.getMessage();
        
      return resultado;
   }
    
    //---------------------------------------------------------------------
   public boolean actualizar (PersonaIdentificador elemento)
   {
      boolean resultado =  tabla.Update(elemento)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
    //---------------------------------------------------------------------
  public boolean actualizar (List<PersonaIdentificador> elementos)
   {
      boolean resultado = tabla.Update(elementos)>0;
      mensaje = tabla.getMessage();
         
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public boolean borrar (PersonaIdentificador elemento)
   {
      boolean resultado = tabla.Delete(elemento)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public boolean borrar (List<PersonaIdentificador> elementos)
   {
      boolean resultado = tabla.Delete(elementos)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public List<PersonaIdentificador> obtenerLista ()
   {
      List<PersonaIdentificador> resultado = tabla.Retrieve();
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public List<PersonaIdentificador> obtenerLista (PersonaIdentificador elemento)
   {
      List<PersonaIdentificador> resultado = tabla.Retrieve(elemento);
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
      //---------------------------------------------------------------------
   /**
    * Obtiene aquellos elementos que coinciden en información con el elemento recibido
    * @param id_persona
    * @return Una lista con los elementos.
    */
    public List<PersonaIdentificadorVista> obtenerListaVista (int id_persona)
   {
      List<PersonaIdentificadorVista> resultado = vista.Retrieve(id_persona);
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }

   //---------------------------------------------------------------------
   public boolean recargar (PersonaIdentificador elemento)
   {
      List<PersonaIdentificador> resultado = tabla.Retrieve(elemento);
      if (resultado.size() > 0)
      {
         elemento.copy(resultado.get(0));
      }
      
      mensaje = tabla.getMessage();
      
      return tabla.getState() != PersonaIdentificadoresDataAccess.DATASOURCE_ERROR;
   }
   
   //---------------------------------------------------------------------
   /**
    * Obtiene la información existente de los medios de comunicación.
    * @param elemento
    * @return Una lista con todos los elementos.
    */
   public List<PersonaIdentificador> obtenerListaMedios (PersonaIdentificador elemento)
   {
      List<PersonaIdentificador> resultado = tabla.retrieveCommunicateOnly(elemento);
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   /**
    * Obtiene todos los tipos de medio
    */
   public List<Valor> obtenerTiposMedio ()
   {
      List<Valor> resultado = aux.retrieveTiposMedio();
      
      mensaje = aux.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   /**
    * Obtiene todos los elementos que tienen relación con otra entidad.
    */
   public List<Valor> obtenerTiposMedioUtilizados ()
   {
      List<Valor> resultado = aux.retrieveTiposMedioReferenciado();
      
      mensaje = aux.getMessage();
      
      return resultado;
   }
   
   
      //---------------------------------------------------------------------
   /**
    * Obtiene todos los tipos de medio que cumplen con los criterios.
    * @param filtro
    */
   public List<Valor> obtenerTiposMedio (Valor filtro)
   {
      List<Valor> resultado = aux.retrieveTiposMedio(filtro);
      
      mensaje = aux.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   /**
    * Obtiene todos los tipos de identificador.
    * @return 
    */
   public List<Valor> obtenerTiposIdentificador ()
   {
      List<Valor> resultado = aux.retrieveTiposIdentificador();
      
      mensaje = aux.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   /**
    * Obtiene aquellos elementos que tiene relación con otras entidades.
    * @return 
    */
   public List<Valor> obtenerTiposIdentificadorUtilizados ()
   {
      List<Valor> resultado = aux.retrieveTiposIdentificadorReferenciado();
      
      mensaje = aux.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   /**
    * Obtiene aquellos elementos que cumplen con el criterio.
    * @param filtro
    */
   public List<Valor> obtenerTiposIdentificador (Valor filtro)
   {
      List<Valor> resultado = aux.retrieveTiposIdentificador(filtro);
      
      mensaje = aux.getMessage();
      
      return resultado;
   }
   
   
   //---------------------------------------------------------------------
   public boolean validar (PersonaIdentificador elemento)
   {
      boolean esCorrecto = true;
      
      if (elemento.getIdPersona()!=PersonaIdentificador.EMPTY_INT)
      {
         esCorrecto = false;
         mensaje = "Es necesario especificar una persona";
      }
      
      else if (elemento.getClave()!=PersonaIdentificador.EMPTY_STRING)
      {
         esCorrecto = false;
         mensaje = "Es necesario especificar una localidad";
      }
      
      return esCorrecto;
   }
   
   //---------------------------------------------------------------------
   public boolean guardar(List<PersonaIdentificadorVista> elementos)
   {
      List<PersonaIdentificador> nuevos = new ArrayList<>();
      List<PersonaIdentificador> modificados = new ArrayList<>();
       List<PersonaIdentificador> borrados = new ArrayList<>();
      
      for (PersonaIdentificador actual : elementos)
      {
         if (actual.isNew())
            nuevos.add(actual);

         else if (actual.isModified())
         {
            if (!actual.getClave().isEmpty())
               modificados.add(actual);
            
            else
               borrados.add(actual);
         }
      }
         
      return (nuevos.isEmpty() || agregar(nuevos)) && (modificados.isEmpty() || actualizar(modificados)) &&  (borrados.isEmpty() || borrar(borrados));
  
   }  
   
   //---------------------------------------------------------------------
   public boolean guardar(List<PersonaIdentificador> elementos, List<PersonaIdentificador> borrados)
   {
      List<PersonaIdentificador> nuevos = new ArrayList<>();
      List<PersonaIdentificador> modificados = new ArrayList<>();
      
      for (PersonaIdentificador actual : elementos)
      {
         if (actual.isNew())
            nuevos.add(actual);

         else if (actual.isModified())
         {
            if (!actual.getClave().isEmpty())
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
      return tabla.getState() != PersonaIdentificadoresDataAccess.DATASOURCE_ERROR; 
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
       aux.setEschema(value);
   }
}
