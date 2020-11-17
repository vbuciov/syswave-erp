package com.syswave.logicas.miempresa;

import com.syswave.datos.common.DataSourceManager;
import com.syswave.datos.miempresa.CategoriasDataAccess;
import com.syswave.entidades.miempresa.Categoria;
import datalayer.api.IMediatorDataSource;
import java.util.List;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class CategoriasBusinessLogic
{
     private String mensaje;
   private CategoriasDataAccess tabla;
   
   //---------------------------------------------------------------------
   public CategoriasBusinessLogic()
   {
       IMediatorDataSource mysource = DataSourceManager.getMainDataSourceInstance();
      tabla = new CategoriasDataAccess(mysource);
   }

   //---------------------------------------------------------------------
   public CategoriasBusinessLogic(String Esquema)
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
   public boolean agregar (Categoria nuevo)
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
   public boolean agregar (List<Categoria> nuevos)
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
   public boolean actualizar (Categoria elemento)
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
   public boolean actualizar (List<Categoria> elementos)
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
   public boolean borrar (Categoria elemento)
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
   public boolean borrar (List<Categoria> elementos)
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
   public List<Categoria> obtenerLista ()
   {
      List<Categoria> resultado = tabla.Retrieve();
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   /**
    * Obtiene aquellos elementos que coinciden en información con el elemento recibido
    * @param elemento
    * @return Una lista con los elementos.
    */
    public List<Categoria> obtenerLista (Categoria elemento)
   {
      List<Categoria> resultado = tabla.Retrieve(elemento);
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
    
       //---------------------------------------------------------------------
   /**
    * Obtiene la información existente de modulos instalados.
    * @return Una lista con todos los elementos.
    */
   public List<Categoria> obtenerListaHojas ()
   {
      List<Categoria> resultado = tabla.RetrieveLeafsPaths();
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   /**
    * Obtiene aquellos elementos que coinciden en información con el elemento recibido
    * @param elemento
    * @return Una lista con los elementos.
    */
    public List<Categoria> obtenerListaHojas (Categoria elemento)
   {
      List<Categoria> resultado = tabla.RetrieveLeafsPaths(elemento);
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
      
    //---------------------------------------------------------------------
   /**
    * Recarga la información del elemento recibido.
    * @param elemento
    * @return Indica si la operación se llevo correctamente.
    */
   public boolean recargar (Categoria elemento)
   {
      List<Categoria> resultado = tabla.Retrieve(elemento);
      if (resultado.size() > 0)
      {
         elemento.copy(resultado.get(0));
      }
      
      mensaje = tabla.getMessage();
      
      return tabla.getState() != CategoriasDataAccess.DATASOURCE_ERROR;
   }
   
   //---------------------------------------------------------------------
   public boolean valirdar (Categoria elemento)
   {
      boolean esCorrecto = true;
      
      if (elemento.getNombre().equals(Categoria.EMPTY_STRING))
      {
         esCorrecto = false;
         mensaje = "Es necesario especificar un nombre";
      }
      
      return esCorrecto;
   }
   
   //---------------------------------------------------------------------
   public boolean esCorrecto ()
   {
      return tabla.getState() != CategoriasDataAccess.DATASOURCE_ERROR;
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
