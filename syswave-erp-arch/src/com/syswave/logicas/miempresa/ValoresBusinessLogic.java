package com.syswave.logicas.miempresa;

import com.syswave.datos.common.DataSourceManager;
import com.syswave.datos.miempresa.ValoresDataAccess;
import com.syswave.entidades.miempresa.Valor;
import datalayer.api.IMediatorDataSource;
import java.util.List;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class ValoresBusinessLogic
{
  private String mensaje;
   private ValoresDataAccess tabla;
   
   //---------------------------------------------------------------------
   public ValoresBusinessLogic()
   {
       IMediatorDataSource mysource = DataSourceManager.getMainDataSourceInstance();
      tabla = new ValoresDataAccess(mysource);
   }

   //---------------------------------------------------------------------
   public ValoresBusinessLogic(String Esquema)
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
   public boolean agregar (Valor nuevo)
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
   public boolean agregar (List<Valor> nuevos)
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
   public boolean actualizar (Valor elemento)
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
   public boolean actualizar (List<Valor> elementos)
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
   public boolean borrar (Valor elemento)
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
   public boolean borrar (List<Valor> elementos)
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
   public List<Valor> obtenerLista ()
   {
      List<Valor> resultado = tabla.Retrieve();
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   /**
    * Obtiene aquellos elementos que coinciden en información con el elemento recibido
    * @param elemento
    * @return Una lista con los elementos.
    */
    public List<Valor> obtenerLista (Valor elemento)
   {
      List<Valor> resultado = tabla.Retrieve(elemento);
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
    
     
    //---------------------------------------------------------------------
   /**
    * Recarga la información del elemento recibido.
    * @param elemento
    * @return Indica si la operación se llevo correctamente.
    */
   public boolean recargar (Valor elemento)
   {
      List<Valor> resultado = tabla.Retrieve(elemento);
      if (resultado.size() > 0)
      {
         elemento.copy(resultado.get(0));
      }
      
      mensaje = tabla.getMessage();
      
      return tabla.getState() != ValoresDataAccess.DATASOURCE_ERROR;
   }
   
   //---------------------------------------------------------------------
   public boolean valirdar (Valor elemento)
   {
      boolean esCorrecto = true;
      
      if (elemento.getValor().equals(Valor.EMPTY_STRING))
      {
         esCorrecto = false;
         mensaje = "Es necesario especificar un valor";
      }
      
      return esCorrecto;
   }
   
   //---------------------------------------------------------------------
   public boolean esCorrecto ()
   {
      return tabla.getState() != ValoresDataAccess.DATASOURCE_ERROR;
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