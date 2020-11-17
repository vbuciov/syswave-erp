package com.syswave.logicas.miempresa;

import com.syswave.datos.common.DataSourceManager;
import com.syswave.datos.miempresa.PersonaCreditoCuentasDataAccess;
import com.syswave.datos.miempresa.PersonaCreditoCuentasRetrieve;
import com.syswave.entidades.miempresa.PersonaCreditoCuenta;
import com.syswave.entidades.miempresa_vista.PersonaCreditoCuenta_5FN;
import datalayer.api.IMediatorDataSource;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class PersonaCreditoCuentasBusinessLogic
{
   private String mensaje;
    private PersonaCreditoCuentasDataAccess tabla;
    private PersonaCreditoCuentasRetrieve vista;
    
   //---------------------------------------------------------------------
    public PersonaCreditoCuentasBusinessLogic(){
        IMediatorDataSource mysource = DataSourceManager.getMainDataSourceInstance();
        tabla = new PersonaCreditoCuentasDataAccess(mysource);
        vista = new PersonaCreditoCuentasRetrieve(mysource);
    }
    
   //---------------------------------------------------------------------
    public PersonaCreditoCuentasBusinessLogic(String esquema)
    {
      this();
      tabla.setEschema(esquema);
      vista.setEschema(esquema);
    }
    
   //---------------------------------------------------------------------
    public boolean agregar (PersonaCreditoCuenta nuevo)
   {
      boolean resultado = tabla.Create(nuevo)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
    
   //---------------------------------------------------------------------
    public boolean agregar (List<PersonaCreditoCuenta> nuevos)
   {
      boolean resultado = tabla.Create(nuevos)>0;
      mensaje = tabla.getMessage();
     
      return resultado;
   }
    
    //---------------------------------------------------------------------
   public boolean actualizar (PersonaCreditoCuenta elemento)
   {
      boolean resultado =  tabla.Update(elemento)>0;
      mensaje = tabla.getMessage();
         
      return resultado;
   }
   
    //---------------------------------------------------------------------
  public boolean actualizar (List<PersonaCreditoCuenta> elementos)
   {
      boolean resultado = tabla.Update(elementos)>0;
      mensaje = tabla.getMessage();
       
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public boolean borrar (PersonaCreditoCuenta elemento)
   {
      boolean resultado = tabla.Delete(elemento)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public boolean borrar (List<PersonaCreditoCuenta> elementos)
   {
      boolean resultado = tabla.Delete(elementos)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public List<PersonaCreditoCuenta> obtenerLista ()
   {
      List<PersonaCreditoCuenta> resultado = tabla.Retrieve();
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public List<PersonaCreditoCuenta> obtenerLista (PersonaCreditoCuenta elemento)
   {
      List<PersonaCreditoCuenta> resultado = tabla.Retrieve(elemento);
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public List<PersonaCreditoCuenta_5FN> obtenerLista5FN ()
   {
      List<PersonaCreditoCuenta_5FN> resultado = vista.Retrieve();
      
      mensaje = vista.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public List<PersonaCreditoCuenta_5FN> obtenerLista5FN (PersonaCreditoCuenta_5FN elemento)
   {
      List<PersonaCreditoCuenta_5FN> resultado = vista.Retrieve(elemento);
      
      mensaje = vista.getMessage();
      
      return resultado;
   }
   
         //---------------------------------------------------------------------
   /*public List<PersonaCreditoCuenta> obtenerListaSeriesCorta ()
   {
      List<PersonaCreditoCuenta> resultado = vista.SmallRetrieve();
      
      mensaje = vista.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public List<PersonaCreditoCuenta> obtenerListaSeriesCorta (PersonaCreditoCuenta elemento)
   {
      List<PersonaCreditoCuenta> resultado = vista.SmallRetrieve(elemento);
      
      mensaje = vista.getMessage();
      
      return resultado;
   }*/
 

   //---------------------------------------------------------------------
   public boolean recargar (PersonaCreditoCuenta elemento)
   {
      List<PersonaCreditoCuenta> resultado = tabla.Retrieve(elemento);
      if (resultado.size() > 0)
      {
         elemento.copy(resultado.get(0));
      }
      
      mensaje = tabla.getMessage();
      
      return tabla.getState() != PersonaCreditoCuentasDataAccess.DATASOURCE_ERROR;
   }
   
   
   //---------------------------------------------------------------------
   public boolean validar (PersonaCreditoCuenta elemento)
   {
      boolean esCorrecto = true;
      
      if (elemento.getIdPersona()!=PersonaCreditoCuenta.EMPTY_INT)
      {
         esCorrecto = false;
         mensaje = "Es necesario especificar una persona";
      }
           
      return esCorrecto;
   }
   
   //---------------------------------------------------------------------
   public boolean esCorrecto ()
   {
      return tabla.getState() != PersonaCreditoCuentasDataAccess.DATASOURCE_ERROR; 
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
   public boolean guardar(List<PersonaCreditoCuenta> elementos, List<PersonaCreditoCuenta> borrados)
   {
      List<PersonaCreditoCuenta> nuevos = new ArrayList<>();
      List<PersonaCreditoCuenta> modificados = new ArrayList<>();
      
      for (PersonaCreditoCuenta actual : elementos)
      {
         if (actual.isNew())
            nuevos.add(actual);

         else if (actual.isModified())
            modificados.add(actual);
      }
         
      return (nuevos.isEmpty() || agregar(nuevos)) && (modificados.isEmpty() || actualizar(modificados)) &&  (borrados == null || borrados.isEmpty() || borrar(borrados));
   }
}
