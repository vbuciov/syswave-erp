package com.syswave.logicas.configuracion;

import com.syswave.datos.common.DataSourceManager;
import com.syswave.datos.configuracion.LocalidadesDataAccess;
import com.syswave.entidades.configuracion.Localidad;
import datalayer.api.IMediatorDataSource;
import java.util.List;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class LocalidadesBusinessLogic
{
     private String mensaje;
   private LocalidadesDataAccess tabla;
   
   //---------------------------------------------------------------------
   public LocalidadesBusinessLogic()
   {
      IMediatorDataSource mysource = DataSourceManager.getMainDataSourceInstance();
      tabla = new LocalidadesDataAccess(mysource);
   }

   //---------------------------------------------------------------------
   public LocalidadesBusinessLogic(String Esquema)
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
   public boolean agregar (Localidad nuevo)
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
   public boolean agregar (List<Localidad> nuevos)
   {
      boolean resultado = tabla.Create(nuevos) >0;
      mensaje = tabla.getMessage();

      return resultado;
   }
   
   //---------------------------------------------------------------------
   /**
    * Actualiza la información del elemento recibido
    * @param elemento 
    * @return Indica si la operación se llevo acabo correctamente..
   */
   public boolean actualizar (Localidad elemento)
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
   public boolean actualizar (List<Localidad> elementos)
   {
      boolean resultado = tabla.Update(elementos) > 0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   /**
    * Borra el elemento recibido
    * @param elemento
    * @return Indica si la operación se realizó correctamente
    */
   public boolean borrar (Localidad elemento)
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
   public boolean borrar (List<Localidad> elementos)
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
   public List<Localidad> obtenerLista ()
   {
      List<Localidad> resultado = tabla.Retrieve();
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   /**
    * Obtiene aquellos elementos que coinciden en información con el elemento recibido
    * @param elemento
    * @return Una lista con los elementos.
    */
    public List<Localidad> obtenerLista (Localidad elemento)
   {
      List<Localidad> resultado = tabla.Retrieve(elemento);
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
    
       //---------------------------------------------------------------------
   /**
    * Obtiene la información existente de modulos instalados.
    * @return Una lista con todos los elementos.
    */
   public List<Localidad> obtenerListaHojas ()
   {
      List<Localidad> resultado = tabla.RetrieveLeafsPaths();
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   /**
    * Obtiene aquellos elementos que coinciden en información con el elemento recibido
    * @param elemento
    * @return Una lista con los elementos.
    */
    public List<Localidad> obtenerListaHojas (Localidad elemento)
   {
      List<Localidad> resultado = tabla.RetrieveLeafsPaths(elemento);
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
    
   //---------------------------------------------------------------------
   /**
    * Obtiene aquellas hojas del árbol que estan siendo utilizadas en otras entidades.<br>
    * Nota: Es necesario conectarse a un esquema de empresa.
    * @return Una lista con todos los elementos.
    */
   public List<Localidad> obtenerListaHojasUtilizadas ()
   {
      List<Localidad> resultado = tabla.RetrieveReferencedLeafsPaths();
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   /**
    * Obtiene aquellas hojas del árbol que cumplen los criterios y que estan <br>
    * siendo utilizadas en otras entidades.<br>
    * Nota: Es necesario conectarse a un esquema de empresa.
    * @param elemento
    * @return Una lista con los elementos.
    */
    public List<Localidad> obtenerListaHojasUtilizadas (Localidad elemento)
   {
      List<Localidad> resultado = tabla.RetrieveReferencedLeafsPaths(elemento);
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
      
    //---------------------------------------------------------------------
   /**
    * Recarga la información del elemento recibido.
    * @param elemento
    * @return Indica si la operación se llevo correctamente.
    */
   public boolean recargar (Localidad elemento)
   {
      List<Localidad> resultado = tabla.Retrieve(elemento);
      if (resultado.size() > 0)
      {
         elemento.copy(resultado.get(0));
      }
      
      mensaje = tabla.getMessage();
      
      return tabla.getState() != LocalidadesDataAccess.DATASOURCE_ERROR;
   }
   
   //---------------------------------------------------------------------
   public boolean valirdar (Localidad elemento)
   {
      boolean esCorrecto = true;
      
      if (elemento.getNombre().equals(Localidad.EMPTY_STRING))
      {
         esCorrecto = false;
         mensaje = "Es necesario especificar un nombre";
      }
      
      return esCorrecto;
   }
   
   //---------------------------------------------------------------------
   public boolean esCorrecto ()
   {
      return tabla.getState() != LocalidadesDataAccess.DATASOURCE_ERROR;
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