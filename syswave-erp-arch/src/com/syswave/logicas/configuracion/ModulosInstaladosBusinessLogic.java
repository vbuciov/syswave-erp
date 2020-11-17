package com.syswave.logicas.configuracion;

import com.syswave.datos.common.DataSourceManager;
import com.syswave.datos.configuracion.ModulosInstaladosDataAccess;
import com.syswave.entidades.configuracion.ModuloInstalado;
import datalayer.api.IMediatorDataSource;
import java.util.List;

/**
 * Esta capa le ayuda a valiara el manejo de los Modulos Instalados.
 * @author Victor Manuel Bucio Vargas
 */
public class ModulosInstaladosBusinessLogic
{
   private String mensaje;
   private ModulosInstaladosDataAccess tabla;
   
   //---------------------------------------------------------------------
   public ModulosInstaladosBusinessLogic()
   {
       IMediatorDataSource mysource = DataSourceManager.getMainDataSourceInstance();
      tabla = new ModulosInstaladosDataAccess(mysource);
   }

   //---------------------------------------------------------------------
   public ModulosInstaladosBusinessLogic(String Esquema)
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
   public boolean agregar (ModuloInstalado nuevo)
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
   public boolean agregar (List<ModuloInstalado> nuevos)
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
   public boolean actualizar (ModuloInstalado elemento)
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
   public boolean actualizar (List<ModuloInstalado> elementos)
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
   public boolean borrar (ModuloInstalado elemento)
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
   public boolean borrar (List<ModuloInstalado> elementos)
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
   public List<ModuloInstalado> obtenerLista ()
   {
      List<ModuloInstalado> resultado = tabla.Retrieve();
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   /**
    * Obtiene aquellos elementos que coinciden en información con el elemento recibido
    * @param elemento
    * @return Una lista con los elementos.
    */
    public List<ModuloInstalado> obtenerLista (ModuloInstalado elemento)
   {
      List<ModuloInstalado> resultado = tabla.Retrieve(elemento);
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
    
       //---------------------------------------------------------------------
   /**
    * Obtiene aquellos elementos que coinciden en información con el elemento recibido
    * @param elemento
    * @return Una lista con los elementos.
    */
    public List<ModuloInstalado> obtenerListaConstruccion (ModuloInstalado elemento)
   {
      List<ModuloInstalado> resultado = tabla.RetrieveIdentifierLeafs(elemento);
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
      
    //---------------------------------------------------------------------
   /**
    * Recarga la información del elemento recibido.
    * @param elemento
    * @return Indica si la operación se llevo correctamente.
    */
   public boolean recargar (ModuloInstalado elemento)
   {
      List<ModuloInstalado> resultado = tabla.Retrieve(elemento);
      if (resultado.size() > 0)
      {
         elemento.copy(resultado.get(0));
      }
      
      mensaje = tabla.getMessage();
      
      return tabla.getState() != ModulosInstaladosDataAccess.DATASOURCE_ERROR;
   }
   
   //---------------------------------------------------------------------
   public boolean valirdar (ModuloInstalado elemento)
   {
      boolean esCorrecto = true;
      
      if (!elemento.getTitulo().equals(ModuloInstalado.EMPTY_STRING))
      {
         esCorrecto = false;
         mensaje = "Es necesario especificar un titulo";
      }
      
      
      return esCorrecto;
   }
   
   //---------------------------------------------------------------------
   public boolean esCorrecto ()
   {
      return tabla.getState() != ModulosInstaladosDataAccess.DATASOURCE_ERROR;
   }
   
   //---------------------------------------------------------------------
   public String getMensaje ()
   {
      return mensaje;
   }
}