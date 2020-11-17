/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.syswave.logicas.miempresa;

import com.syswave.datos.common.DataSourceManager;
import com.syswave.datos.miempresa.AreasPreciosDataAccess;
import com.syswave.entidades.miempresa.AreaPrecio;
import com.syswave.entidades.miempresa.Valor;
import datalayer.api.IMediatorDataSource;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sis2
 */
public class AreasPreciosBusinessLogic
{
      private String mensaje;
   private AreasPreciosDataAccess tabla;
   
   //---------------------------------------------------------------------
   public AreasPreciosBusinessLogic()
   {
      IMediatorDataSource mysource = DataSourceManager.getMainDataSourceInstance();
      tabla = new AreasPreciosDataAccess(mysource);
   }

   //---------------------------------------------------------------------
   public AreasPreciosBusinessLogic(String Esquema)
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
   public boolean agregar (AreaPrecio nuevo)
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
   public boolean agregar (List<AreaPrecio> nuevos)
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
   public boolean actualizar (AreaPrecio elemento)
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
   public boolean actualizar (List<AreaPrecio> elementos)
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
   public boolean borrar (AreaPrecio elemento)
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
   public boolean borrar (List<AreaPrecio> elementos)
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
   public List<AreaPrecio> obtenerLista ()
   {
      List<AreaPrecio> resultado = tabla.Retrieve();
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   /**
    * Obtiene aquellos elementos que coinciden en información con el elemento recibido
    * @param elemento
    * @return Una lista con los elementos.
    */
    public List<AreaPrecio> obtenerLista (AreaPrecio elemento)
   {
      List<AreaPrecio> resultado = tabla.Retrieve(elemento);
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
    
       //---------------------------------------------------------------------
   /**
    * Obtiene la información existente de modulos instalados.
    * @return Una lista con todos los elementos.
    */
   public List<AreaPrecio> obtenerListaHojas ()
   {
      List<AreaPrecio> resultado = tabla.RetrieveLeafsPaths();
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   /**
    * Obtiene aquellos elementos que coinciden en información con el elemento recibido
    * @param elemento
    * @return Una lista con los elementos.
    */
    public List<AreaPrecio> obtenerListaHojas (AreaPrecio elemento)
   {
      List<AreaPrecio> resultado = tabla.RetrieveLeafsPaths(elemento);
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
      
    //---------------------------------------------------------------------
   /**
    * Recarga la información del elemento recibido.
    * @param elemento
    * @return Indica si la operación se llevo correctamente.
    */
   public boolean recargar (AreaPrecio elemento)
   {
      List<AreaPrecio> resultado = tabla.Retrieve(elemento);
      if (resultado.size() > 0)
      {
         elemento.copy(resultado.get(0));
      }
      
      mensaje = tabla.getMessage();
      
      return tabla.getState() != AreasPreciosDataAccess.DATASOURCE_ERROR;
   }
   
   //------------------------------------------------------------------------
   public List<Valor> obtenerTipoPrecios ()
   {
     List<Valor> listaTipos = new ArrayList<>();
     listaTipos.add(new Valor(0, "Compra"));
     listaTipos.add(new Valor(1, "Venta"));
     return listaTipos;
   }  
   
   //---------------------------------------------------------------------
   public boolean valirdar (AreaPrecio elemento)
   {
      boolean esCorrecto = true;
      
      if (elemento.getDescripcion().equals(AreaPrecio.EMPTY_STRING))
      {
         esCorrecto = false;
         mensaje = "Es necesario especificar un nombre";
      }
      
      return esCorrecto;
   }
   
   //---------------------------------------------------------------------
   public boolean esCorrecto ()
   {
      return tabla.getState() != AreasPreciosDataAccess.DATASOURCE_ERROR;
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
