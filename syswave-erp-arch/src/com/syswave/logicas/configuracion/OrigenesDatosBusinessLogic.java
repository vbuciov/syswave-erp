package com.syswave.logicas.configuracion;

import com.syswave.datos.common.DataSourceManager;
import com.syswave.datos.configuracion.OrigenesDatosDataAccess;
import com.syswave.entidades.configuracion.OrigenDato;
import datalayer.api.IMediatorDataSource;
import java.util.List;

/**
 *
 * @author victor
 */
public class OrigenesDatosBusinessLogic
{
     private String mensaje;
   private OrigenesDatosDataAccess tabla;
   
   //---------------------------------------------------------------------
   public OrigenesDatosBusinessLogic()
   {
      IMediatorDataSource mysource = DataSourceManager.getMainDataSourceInstance();
      tabla = new OrigenesDatosDataAccess(mysource);
   }

   //---------------------------------------------------------------------
   public OrigenesDatosBusinessLogic(String Esquema)
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
   public boolean agregar (OrigenDato nuevo)
   {
      boolean resultado = tabla.Create(nuevo) > 0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
      //---------------------------------------------------------------------
   /**
    * Agrega una lista de elementos nuevos
    * @param nuevos
    * @return Indica si la operación se llevo acabo correctamente.
    */
   public boolean agregar (List<OrigenDato> nuevos)
   {
      boolean resultado = tabla.Create(nuevos) > 0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   /**
    * Actualiza la información del elemento recibido
    * @param elemento 
    * @return Indica si la operación se llevo acabo correctamente..
   */
   public boolean actualizar (OrigenDato elemento)
   {
      boolean resultado =  tabla.Update(elemento) > 0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   /**
    * Actualiza la información de la lista de elementos recibidos.
    * @param elementos
    * @return Indica si la operación se llevo acabo correctamente.
    */
   public boolean actualizar (List<OrigenDato> elementos)
   {
      boolean resultado = tabla.Update(elementos) > 0 ;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   /**
    * Borra el elemento recibido
    * @param elemento
    * @return Indica si la operación se realizó correctamente
    */
   public boolean borrar (OrigenDato elemento)
   {
      boolean resultado = tabla.Delete(elemento) > 0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   /**
    * Borra el elemento recibido
    * @param elementos
    * @return Indica si la operación se realizó correctamente
    */
   public boolean borrar (List<OrigenDato> elementos)
   {
      boolean resultado = tabla.Delete(elementos) > 0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   /**
    * Obtiene la información existente de modulos instalados.
    * @return Una lista con todos los elementos.
    */
   public List<OrigenDato> obtenerLista ()
   {
      List<OrigenDato> resultado = tabla.Retrieve();
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   /**
    * Obtiene aquellos elementos que coinciden en información con el elemento recibido
    * @param elemento
    * @return Una lista con los elementos.
    */
    public List<OrigenDato> obtenerLista (OrigenDato elemento)
   {
      List<OrigenDato> resultado = tabla.Retrieve(elemento);
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
      
    //---------------------------------------------------------------------
   /**
    * Recarga la información del elemento recibido.
    * @param elemento
    * @return Indica si la operación se llevo correctamente.
    */
   public boolean recargar (OrigenDato elemento)
   {
      List<OrigenDato> resultado = tabla.Retrieve(elemento);
      if (resultado.size() > 0)
      {
         elemento.copy(resultado.get(0));
      }
      
      mensaje = tabla.getMessage();
      
      return tabla.getState() != OrigenesDatosDataAccess.DATASOURCE_ERROR;
   }
   
   //---------------------------------------------------------------------
   public boolean valirdar (OrigenDato elemento)
   {
      boolean esCorrecto = true;
      
      if (!elemento.getNombre().equals(OrigenDato.EMPTY_STRING))
      {
         esCorrecto = false;
         mensaje = "Es necesario especificar un nombre";
      }
      
      
      return esCorrecto;
   }
   
   //---------------------------------------------------------------------
   public boolean esCorrecto ()
   {
      return tabla.getState() != OrigenesDatosDataAccess.DATASOURCE_ERROR;
   }
   
   //---------------------------------------------------------------------
   public String getMensaje ()
   {
      return tabla.getMessage();
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