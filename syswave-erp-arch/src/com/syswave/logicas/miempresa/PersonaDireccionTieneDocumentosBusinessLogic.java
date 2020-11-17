package com.syswave.logicas.miempresa;

import com.syswave.datos.common.DataSourceManager;
import com.syswave.datos.miempresa.PersonaDireccionTieneDocumentosDataAccess;
import com.syswave.datos.miempresa.PersonaDireccionTieneDocumentosRetrieve;
import com.syswave.entidades.miempresa.PersonaDireccion_tiene_Documento;
import com.syswave.entidades.miempresa_vista.PersonaDireccion_tiene_Documento_5FN;
import datalayer.api.IMediatorDataSource;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class PersonaDireccionTieneDocumentosBusinessLogic
{
   private String mensaje;
   private PersonaDireccionTieneDocumentosDataAccess tabla;
   private PersonaDireccionTieneDocumentosRetrieve vista;
    
   //---------------------------------------------------------------------
    public PersonaDireccionTieneDocumentosBusinessLogic(){
        IMediatorDataSource mysource = DataSourceManager.getMainDataSourceInstance();
        tabla = new PersonaDireccionTieneDocumentosDataAccess(mysource);
        vista = new PersonaDireccionTieneDocumentosRetrieve(mysource);
    }
    
   //---------------------------------------------------------------------
    public PersonaDireccionTieneDocumentosBusinessLogic(String esquema)
    {
      this();
      tabla.setEschema(esquema);
        vista.setEschema(esquema);
    }
    
   //---------------------------------------------------------------------
    public boolean agregar (PersonaDireccion_tiene_Documento nuevo)
   {
      boolean resultado = tabla.Create(nuevo)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
    
   //---------------------------------------------------------------------
    public boolean agregar (List<PersonaDireccion_tiene_Documento> nuevos)
   {
      boolean resultado = tabla.Create(nuevos)>0;
      mensaje = tabla.getMessage();
     
      return resultado;
   }
    
    //---------------------------------------------------------------------
   public boolean actualizar (PersonaDireccion_tiene_Documento elemento)
   {
      boolean resultado =  tabla.Update(elemento)>0;
      mensaje = tabla.getMessage();
         
      return resultado;
   }
   
    //---------------------------------------------------------------------
  public boolean actualizar (List<PersonaDireccion_tiene_Documento> elementos)
   {
      boolean resultado = tabla.Update(elementos)>0;
      mensaje = tabla.getMessage();
       
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public boolean borrar (PersonaDireccion_tiene_Documento elemento)
   {
      boolean resultado = tabla.Delete(elemento)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public boolean borrar (List<PersonaDireccion_tiene_Documento> elementos)
   {
      boolean resultado = tabla.Delete(elementos)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public List<PersonaDireccion_tiene_Documento> obtenerLista ()
   {
      List<PersonaDireccion_tiene_Documento> resultado = tabla.Retrieve();
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public List<PersonaDireccion_tiene_Documento> obtenerLista (PersonaDireccion_tiene_Documento elemento)
   {
      List<PersonaDireccion_tiene_Documento> resultado = tabla.Retrieve(elemento);
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public List<PersonaDireccion_tiene_Documento_5FN> obtenerLista5FN (int id_documento)
   {
      List<PersonaDireccion_tiene_Documento_5FN> resultado = vista.Retrieve(id_documento);
      
      mensaje = vista.getMessage();
      
      return resultado;
   }

   //---------------------------------------------------------------------
   public boolean recargar (PersonaDireccion_tiene_Documento elemento)
   {
      List<PersonaDireccion_tiene_Documento> resultado = tabla.Retrieve(elemento);
      if (resultado.size() > 0)
      {
         elemento.copy(resultado.get(0));
      }
      
      mensaje = tabla.getMessage();
      
      return tabla.getState() != PersonaDireccionTieneDocumentosDataAccess.DATASOURCE_ERROR;
   }
   
   
   //---------------------------------------------------------------------
   public boolean validar (PersonaDireccion_tiene_Documento elemento)
   {
      boolean esCorrecto = true;
      
      if (elemento.getIdPersona()!=PersonaDireccion_tiene_Documento.EMPTY_INT)
      {
         esCorrecto = false;
         mensaje = "Es necesario especificar una direccion de persona";
      }
           
      return esCorrecto;
   }
   
      //---------------------------------------------------------------------
   public boolean guardar(List<PersonaDireccion_tiene_Documento_5FN> elementos, List<PersonaDireccion_tiene_Documento> borrados)
   {
      List<PersonaDireccion_tiene_Documento> nuevos = new ArrayList<>();
      List<PersonaDireccion_tiene_Documento> modificados = new ArrayList<>();
      
      for (PersonaDireccion_tiene_Documento actual : elementos)
      {
         if (actual.isNew())
            nuevos.add(actual);

         else if (actual.isModified())
            modificados.add(actual);
      }
         
      return (nuevos.isEmpty() || agregar(nuevos)) && (modificados.isEmpty() || actualizar(modificados)) &&  (borrados == null || borrados.isEmpty() || borrar(borrados));
   }
   
   //---------------------------------------------------------------------
   public boolean esCorrecto ()
   {
      return tabla.getState() != PersonaDireccionTieneDocumentosDataAccess.DATASOURCE_ERROR;
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
