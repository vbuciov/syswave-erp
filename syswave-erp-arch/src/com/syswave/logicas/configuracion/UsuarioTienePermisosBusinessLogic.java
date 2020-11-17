package com.syswave.logicas.configuracion;

import com.syswave.datos.common.DataSourceManager;
import com.syswave.datos.configuracion.NodoPermisoRetrieve;
import com.syswave.datos.configuracion.UsuarioTienePermisoDataAccess;
import com.syswave.entidades.configuracion.NodoPermiso;
import com.syswave.entidades.configuracion.Usuario;
import com.syswave.entidades.configuracion.Usuario_tiene_Permiso;
import datalayer.api.IMediatorDataSource;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author victor
 */
public class UsuarioTienePermisosBusinessLogic
{
     private String mensaje;
   private UsuarioTienePermisoDataAccess tabla;
   private NodoPermisoRetrieve vista;
   
   //---------------------------------------------------------------------
   public UsuarioTienePermisosBusinessLogic()
   {
       IMediatorDataSource mysource = DataSourceManager.getMainDataSourceInstance();
      tabla = new UsuarioTienePermisoDataAccess(mysource);
      vista = new NodoPermisoRetrieve(mysource);
   }

   //---------------------------------------------------------------------
   public UsuarioTienePermisosBusinessLogic(String Esquema)
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
   public boolean agregar (Usuario_tiene_Permiso nuevo)
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
   public boolean agregar (List<Usuario_tiene_Permiso> nuevos)
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
   public boolean actualizar (Usuario_tiene_Permiso elemento)
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
   public boolean actualizar (List<Usuario_tiene_Permiso> elementos)
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
   public boolean borrar (Usuario_tiene_Permiso elemento)
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
   public boolean borrar (List<Usuario_tiene_Permiso> elementos)
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
   public List<Usuario_tiene_Permiso> obtenerLista ()
   {
      List<Usuario_tiene_Permiso> resultado = tabla.Retrieve();
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   /**
    * Obtiene aquellos elementos que coinciden en información con el elemento recibido
    * @param elemento
    * @return Una lista con los elementos.
    */
    public List<Usuario_tiene_Permiso> obtenerLista (Usuario_tiene_Permiso elemento)
   {
      List<Usuario_tiene_Permiso> resultado = tabla.Retrieve(elemento);
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }

       //---------------------------------------------------------------------
   /**
    * Obtiene una vista de ModulosInstalados X Permisos x Usuario_tiene_Permiso
    * @param elemento
    * @return Una lista con los elementos.
    */
    public List<NodoPermiso> obtenerSemilla (NodoPermiso elemento)
    {
       List<NodoPermiso> resultado = vista.Retrieve(elemento);
       
       mensaje = vista.getMessage();
       
       return resultado;
    }
      
    //---------------------------------------------------------------------
   /**
    * Recarga la información del elemento recibido.
    * @param elemento
    * @return Indica si la operación se llevo correctamente.
    */
   public boolean recargar (Usuario_tiene_Permiso elemento)
   {
      List<Usuario_tiene_Permiso> resultado = tabla.Retrieve(elemento);
      if (resultado.size() > 0)
      {
         elemento.copy(resultado.get(0));
      }
      
      mensaje = tabla.getMessage();
      
      return tabla.getState() != UsuarioTienePermisoDataAccess.DATASOURCE_ERROR;
   }
   
   //---------------------------------------------------------------------
   public boolean guardar (Usuario elemento, List<NodoPermiso> permisos, int id_origen_dato)
   {
      List<Usuario_tiene_Permiso> nuevos = new ArrayList<>();
      List<Usuario_tiene_Permiso> eliminados = new ArrayList<>();
      NodoPermiso permiso;
      
      for (NodoPermiso actual : permisos)
      {
         if (actual instanceof NodoPermiso && actual.isSet())  
         {
            permiso = (NodoPermiso)actual;
            permiso.setId_usuario(elemento.getIdentificador());
            permiso.setId_origen_dato(id_origen_dato);
    
            if (permiso.esExiste())
            {
               //Revisamos si el elemento seleccionado es una hoja de permiso.
               if (!actual.isSelected()&& permiso.getId_modulo()== permiso.getIdPadre())
                  eliminados.add((Usuario_tiene_Permiso)actual);
            }
            
            //Revisamos si el elemento seleccionado es una hoja de permiso.
            else if (permiso.isSelected() && permiso.getId_modulo() == permiso.getIdPadre())
               nuevos.add((Usuario_tiene_Permiso)actual);
         }
      }
         
      return (nuevos.isEmpty() || agregar(nuevos)) && (eliminados.isEmpty() || borrar(eliminados));
   }
   
   //---------------------------------------------------------------------
   public boolean valirdar (Usuario_tiene_Permiso elemento)
   {
      boolean esCorrecto = true;
      
      if (!elemento.getId_usuario().equals(Usuario_tiene_Permiso.EMPTY_STRING))
      {
         esCorrecto = false;
         mensaje = "Es necesario especificar un usuario";
      }
      
      else if (elemento.getId_origen_dato() != Usuario_tiene_Permiso.EMPTY_INT)
      {
         esCorrecto = false;
         mensaje = "Es necesario especificar el origen de los datos";
      }
      
      else if (elemento.getId_modulo()!= Usuario_tiene_Permiso.EMPTY_INT)
      {
         esCorrecto = false;
         mensaje = "Es necesario especificar el módulo";
      }
      
      if (!elemento.getLlave().equals(Usuario_tiene_Permiso.EMPTY_STRING))
      {
         esCorrecto = false;
         mensaje = "Es necesario especificar el permiso";
      }
      
      return esCorrecto;
   }
   
   //---------------------------------------------------------------------
   public boolean esCorrecto ()
   {
      return tabla.getState() != UsuarioTienePermisoDataAccess.DATASOURCE_ERROR;
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
}
