package com.syswave.logicas.miempresa;

import com.syswave.datos.common.DataSourceManager;
import com.syswave.datos.miempresa.BienCompuestosDataAccess;
import com.syswave.datos.miempresa.BienCompuestosPartesRetrieve;
import com.syswave.entidades.miempresa.BienCompuesto;
import com.syswave.entidades.miempresa_vista.BienCompuestoVista;
import datalayer.api.IMediatorDataSource;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class BienCompuestosBusinessLogic
{
   private String mensaje;
   private BienCompuestosDataAccess tabla;
   private BienCompuestosPartesRetrieve vista;
   
   //---------------------------------------------------------------------
   public BienCompuestosBusinessLogic()
   {
       IMediatorDataSource mysource = DataSourceManager.getMainDataSourceInstance();
      tabla = new BienCompuestosDataAccess(mysource);
      vista = new BienCompuestosPartesRetrieve(mysource);
   }

   //---------------------------------------------------------------------
   public BienCompuestosBusinessLogic(String Esquema)
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
   public boolean agregar (BienCompuesto nuevo)
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
   public boolean agregar (List<BienCompuesto> nuevos)
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
   public boolean actualizar (BienCompuesto elemento)
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
   public boolean actualizar (List<BienCompuesto> elementos)
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
   public boolean borrar (BienCompuesto elemento)
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
   public boolean borrar (List<BienCompuesto> elementos)
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
   public List<BienCompuesto> obtenerLista ()
   {
      List<BienCompuesto> resultado = tabla.Retrieve();
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   /**
    * Obtiene aquellos elementos que coinciden en información con el elemento recibido
    * @param elemento
    * @return Una lista con los elementos.
    */
    public List<BienCompuesto> obtenerLista (BienCompuesto elemento)
   {
      List<BienCompuesto> resultado = tabla.Retrieve(elemento);
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
    
       //---------------------------------------------------------------------
   /**
    * Obtiene la información existente de modulos instalados.
    * @return Una lista con todos los elementos.
    */
   public List<BienCompuestoVista> obtenerListaVista ()
   {
      List<BienCompuestoVista> resultado = vista.Retrieve();
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   /**
    * Obtiene aquellos elementos que coinciden en información con el elemento recibido
    * @param elemento
    * @return Una lista con los elementos.
    */
    public List<BienCompuestoVista> obtenerListaVista (BienCompuestoVista elemento)
   {
      List<BienCompuestoVista> resultado = vista.Retrieve(elemento);
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
    
    //---------------------------------------------------------------------
   /**
    * Recarga la información del elemento recibido.
    * @param elemento
    * @return Indica si la operación se llevo correctamente.
    */
   public boolean recargar (BienCompuesto elemento)
   {
      List<BienCompuesto> resultado = tabla.Retrieve(elemento);
      if (resultado.size() > 0)
      {
         elemento.copy(resultado.get(0));
      }
      
      mensaje = tabla.getMessage();
      
      return tabla.getState() != BienCompuestosDataAccess.DATASOURCE_ERROR;
   }
   
   //---------------------------------------------------------------------
   public boolean valirdar (BienCompuesto elemento)
   {
      boolean esCorrecto = true;
      
      if (elemento.getIdBienFormal() != BienCompuesto.EMPTY_INT && elemento.getIdBienParte() != BienCompuesto.EMPTY_INT)
      {
         esCorrecto = false;
         mensaje = "Es necesario especificar el elemento a relacionar";
      }
      
      return esCorrecto;
   }
   
   //---------------------------------------------------------------------
   public boolean esCorrecto ()
   {
      return tabla.getState() != BienCompuestosDataAccess.DATASOURCE_ERROR;
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
   public boolean guardar(List<BienCompuestoVista> elementos, List<BienCompuesto> borrados)
   {
      List<BienCompuesto> nuevos = new ArrayList<>();
      List<BienCompuesto> modificados = new ArrayList<>();
      
      for (BienCompuesto actual : elementos)
      {
         if (actual.isNew())
            nuevos.add(actual);

         else if (actual.isModified())
            modificados.add(actual);
      }
         
      return (nuevos.isEmpty() || agregar(nuevos)) && (modificados.isEmpty() || actualizar(modificados))  &&  ( borrados  == null || borrados.isEmpty() || borrar(borrados));
  
   }
}
