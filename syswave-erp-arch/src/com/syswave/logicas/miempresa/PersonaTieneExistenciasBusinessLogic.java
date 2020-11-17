package com.syswave.logicas.miempresa;

import com.syswave.datos.common.DataSourceManager;
import com.syswave.datos.miempresa.PersonaTieneExistenciaDataAccess;
import com.syswave.entidades.miempresa.Persona_tiene_Existencia;
import datalayer.api.IMediatorDataSource;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class PersonaTieneExistenciasBusinessLogic
{
   private String mensaje;
    private PersonaTieneExistenciaDataAccess tabla;
    
   //---------------------------------------------------------------------
    public PersonaTieneExistenciasBusinessLogic(){
        IMediatorDataSource mysource = DataSourceManager.getMainDataSourceInstance();
        tabla = new PersonaTieneExistenciaDataAccess(mysource);
    }
    
   //---------------------------------------------------------------------
    public PersonaTieneExistenciasBusinessLogic(String esquema)
    {
      this();
      tabla.setEschema(esquema);
    }
    
   //---------------------------------------------------------------------
    public boolean agregar (Persona_tiene_Existencia nuevo)
   {
      boolean resultado = tabla.Create(nuevo)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
    
   //---------------------------------------------------------------------
    public boolean agregar (List<Persona_tiene_Existencia> nuevos)
   {
      boolean resultado = tabla.Create(nuevos)>0;
      mensaje = tabla.getMessage();
     
      return resultado;
   }
    
    //---------------------------------------------------------------------
   public boolean actualizar (Persona_tiene_Existencia elemento)
   {
      boolean resultado =  tabla.Update(elemento)>0;
      mensaje = tabla.getMessage();
         
      return resultado;
   }
   
    //---------------------------------------------------------------------
  public boolean actualizar (List<Persona_tiene_Existencia> elementos)
   {
      boolean resultado = tabla.Update(elementos)>0;
      mensaje = tabla.getMessage();
       
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public boolean borrar (Persona_tiene_Existencia elemento)
   {
      boolean resultado = tabla.Delete(elemento)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public boolean borrar (List<Persona_tiene_Existencia> elementos)
   {
      boolean resultado = tabla.Delete(elementos)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public List<Persona_tiene_Existencia> obtenerLista ()
   {
      List<Persona_tiene_Existencia> resultado = tabla.Retrieve();
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public List<Persona_tiene_Existencia> obtenerLista (Persona_tiene_Existencia elemento)
   {
      List<Persona_tiene_Existencia> resultado = tabla.Retrieve(elemento);
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }

   //---------------------------------------------------------------------
   public boolean recargar (Persona_tiene_Existencia elemento)
   {
      List<Persona_tiene_Existencia> resultado = tabla.Retrieve(elemento);
      if (resultado.size() > 0)
      {
         elemento.copy(resultado.get(0));
      }
      
      mensaje = tabla.getMessage();
      
      return tabla.getState() != PersonaTieneExistenciaDataAccess.DATASOURCE_ERROR;
   }
   
   
   //---------------------------------------------------------------------
   public boolean validar (Persona_tiene_Existencia elemento)
   {
      boolean esCorrecto = true;
      
      if (elemento.getIdUbicacion()!=Persona_tiene_Existencia.EMPTY_INT)
      {
         esCorrecto = false;
         mensaje = "Es necesario especificar una ubicación";
      }
           
      return esCorrecto;
   }
   
   //---------------------------------------------------------------------
   public boolean esCorrecto ()
   {
      return tabla.getState() != PersonaTieneExistenciaDataAccess.DATASOURCE_ERROR; 
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
   public boolean guardar(List<Persona_tiene_Existencia> elementos, List<Persona_tiene_Existencia> borrados)
   {
      List<Persona_tiene_Existencia> nuevos = new ArrayList<>();
      List<Persona_tiene_Existencia> modificados = new ArrayList<>();
      
      for (Persona_tiene_Existencia actual : elementos)
      {
         if (actual.isNew())
            nuevos.add(actual);

         else if (actual.isModified())
            modificados.add(actual);
      }
         
      return (nuevos.isEmpty() || agregar(nuevos)) && (modificados.isEmpty() || actualizar(modificados)) &&  (borrados == null || borrados.isEmpty() || borrar(borrados));
   }
}
