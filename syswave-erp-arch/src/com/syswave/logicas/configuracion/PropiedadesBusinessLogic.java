package com.syswave.logicas.configuracion;

import com.syswave.datos.common.DataSourceManager;
import com.syswave.datos.configuracion.PropiedadesDataAccess;
import com.syswave.entidades.configuracion.Propiedad;
import datalayer.api.IMediatorDataSource;
import java.util.List;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class PropiedadesBusinessLogic
{
     private String mensaje;
   private PropiedadesDataAccess tabla;
   
   //---------------------------------------------------------------------
   public PropiedadesBusinessLogic()
   {
       IMediatorDataSource mysource = DataSourceManager.getMainDataSourceInstance();
      tabla = new PropiedadesDataAccess(mysource);
   }

   //---------------------------------------------------------------------
   public PropiedadesBusinessLogic(String Esquema)
   {
      this();
      tabla.setEschema(Esquema);
   }
   
   //---------------------------------------------------------------------
   /**
    * Agrega un elemento nuevo 
    * @param nuevo El elemento a agregar
    * @return Indica si la operación se llevo acabo correctamente.
    */
   public boolean agregar (Propiedad nuevo)
   {
      boolean resultado = tabla.Create(nuevo)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
      //---------------------------------------------------------------------
   /**
    * Agrega una lista de elementos nuevos
    * @param nuevos
    * @return Indica si la operación se llevo acabo correctamente.
    */
   public boolean agregar (List<Propiedad> nuevos)
   {
      boolean resultado = tabla.Create(nuevos)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   /**
    * Actualiza la información del elemento recibido
    * @param elemento 
    * @return Indica si la operación se llevo acabo correctamente..
   */
   public boolean actualizar (Propiedad elemento)
   {
      boolean resultado =  tabla.Update(elemento)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   /**
    * Actualiza la información de la lista de elementos recibidos.
    * @param elementos
    * @return Indica si la operación se llevo acabo correctamente.
    */
   public boolean actualizar (List<Propiedad> elementos)
   {
      boolean resultado = tabla.Update(elementos)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   /**
    * Borra el elemento recibido
    * @param elemento
    * @return Indica si la operación se realizó correctamente
    */
   public boolean borrar (Propiedad elemento)
   {
      boolean resultado = tabla.Delete(elemento)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   /**
    * Borra el elemento recibido
    * @param elementos
    * @return Indica si la operación se realizó correctamente
    */
   public boolean borrar (List<Propiedad> elementos)
   {
      boolean resultado = tabla.Delete(elementos)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   /**
    * Obtiene la información existente de modulos instalados.
    * @return Una lista con todos los elementos.
    */
   public List<Propiedad> obtenerLista ()
   {
      List<Propiedad> resultado = tabla.Retrieve();
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   /**
    * Obtiene aquellos elementos que coinciden en información con el elemento recibido
    * @param elemento
    * @return Una lista con los elementos.
    */
    public List<Propiedad> obtenerLista (Propiedad elemento)
   {
      List<Propiedad> resultado = tabla.Retrieve(elemento);
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
      
    //---------------------------------------------------------------------
   /**
    * Recarga la información del elemento recibido.
    * @param elemento
    * @return Indica si la operación se llevo correctamente.
    */
   public boolean recargar (Propiedad elemento)
   {
      List<Propiedad> resultado = tabla.Retrieve(elemento);
      if (resultado.size() > 0)
      {
         elemento.copy(resultado.get(0));
      }
      
      mensaje = tabla.getMessage();
      
      return tabla.getState() != PropiedadesDataAccess.DATASOURCE_ERROR;
   }
   
   //---------------------------------------------------------------------
   public boolean valirdar (Propiedad elemento)
   {
      boolean esCorrecto = true;
      
      if (!elemento.getLlave().equals(Propiedad.EMPTY_STRING))
      {
         esCorrecto = false;
         mensaje = "Es necesario especificar un usuario";
      }
      
      else if (!elemento.getSeccion().equals(Propiedad.EMPTY_STRING))
      {
         esCorrecto = false;
         mensaje = "Es necesario especificar un usuario";
      }
           
      if (!elemento.getValor().equals(Propiedad.EMPTY_STRING))
      {
         esCorrecto = false;
         mensaje = "Es necesario especificar el permiso";
      }
      
      return esCorrecto;
   }
   
   //---------------------------------------------------------------------
   public boolean esCorrecto ()
   {
      return tabla.getState() != PropiedadesDataAccess.DATASOURCE_ERROR;
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