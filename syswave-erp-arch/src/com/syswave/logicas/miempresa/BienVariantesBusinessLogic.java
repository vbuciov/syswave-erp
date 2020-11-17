package com.syswave.logicas.miempresa;

import com.syswave.datos.common.DataSourceManager;
import com.syswave.datos.miempresa.BienVariantesDataAccess;
import com.syswave.datos.miempresa.PartesRetrieve;
import com.syswave.entidades.miempresa.BienVariante;
import datalayer.api.IMediatorDataSource;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author victor
 */
public class BienVariantesBusinessLogic
{
   private String mensaje;
   private BienVariantesDataAccess tabla;
   private PartesRetrieve vista;
   
   //---------------------------------------------------------------------
   public BienVariantesBusinessLogic()
   {
       IMediatorDataSource mysource = DataSourceManager.getMainDataSourceInstance();
      tabla = new BienVariantesDataAccess(mysource);
      vista = new PartesRetrieve(mysource);
   }

   //---------------------------------------------------------------------
   public BienVariantesBusinessLogic(String Esquema)
   {
      this();
      tabla.setEschema(Esquema);
      vista.setEschema(Esquema);
      
   }
   
   //---------------------------------------------------------------------
   /**
    * Agrega un elemento nuevo 
    * @param nuevo El elemento a agregar
    * @return Indica si la operación se llevo acabo correctamente.
    */
   public boolean agregar (BienVariante nuevo)
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
   public boolean agregar (List<BienVariante> nuevos)
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
   public boolean actualizar (BienVariante elemento)
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
   public boolean actualizar (List<BienVariante> elementos)
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
   public boolean borrar (BienVariante elemento)
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
   public boolean borrar (List<BienVariante> elementos)
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
   public List<BienVariante> obtenerLista ()
   {
      List<BienVariante> resultado = tabla.Retrieve();
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   /**
    * Obtiene aquellos elementos que coinciden en información con el elemento recibido
    * @param elemento
    * @return Una lista con los elementos.
    */
    public List<BienVariante> obtenerLista (BienVariante elemento)
   {
      List<BienVariante> resultado = tabla.Retrieve(elemento);
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
    
       //---------------------------------------------------------------------
   /**
    * Obtiene aquellos elementos que coinciden en información con el elemento recibido
    * @return Una lista con los elementos.
    */
    public List<BienVariante> obtenerListaPartes ()
   {
      List<BienVariante> resultado = vista.Retrieve();
      
      mensaje = vista.getMessage();
      
      return resultado;
   }
    
   //---------------------------------------------------------------------
   /**
    * Obtiene aquellos elementos que coinciden en información con el elemento recibido
    * @param elemento
    * @return Una lista con los elementos.
    */
    public List<BienVariante> obtenerListaPartes (BienVariante elemento)
   {
      List<BienVariante> resultado = vista.Retrieve(elemento);
      
      mensaje = vista.getMessage();
      
      return resultado;
   }
    
    //---------------------------------------------------------------------
   /**
    * Recarga la información del elemento recibido.
    * @param elemento
    * @return Indica si la operación se llevo correctamente.
    */
   public boolean recargar (BienVariante elemento)
   {
      List<BienVariante> resultado = tabla.Retrieve(elemento);
      if (resultado.size() > 0)
      {
         elemento.copy(resultado.get(0));
      }
      
      mensaje = tabla.getMessage();
      
      return tabla.getState() != BienVariantesDataAccess.DATASOURCE_ERROR;
   }
   
   //---------------------------------------------------------------------
   public boolean valirdar (BienVariante elemento)
   {
      boolean esCorrecto = true;
      
      if (elemento.getDescripcion().equals(BienVariante.EMPTY_STRING))
      {
         esCorrecto = false;
         mensaje = "Es necesario especificar la descripción";
      }
      
      return esCorrecto;
   }
   
   //---------------------------------------------------------------------
   public boolean esCorrecto ()
   {
      return tabla.getState() != BienVariantesDataAccess.DATASOURCE_ERROR;
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
   public boolean guardar(List<BienVariante> elementos, List<BienVariante> borrados)
   {
      List<BienVariante> nuevos = new ArrayList<>();
      List<BienVariante> modificados = new ArrayList<>();
      
      for (BienVariante actual : elementos)
      {
         if (actual.isNew())
            nuevos.add(actual);

         else if (actual.isModified())
            modificados.add(actual);
      }
         
      return (nuevos.isEmpty() || agregar(nuevos)) && (modificados.isEmpty() || actualizar(modificados)) &&  (borrados == null || borrados.isEmpty() || borrar(borrados));
  
   }
}