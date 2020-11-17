package com.syswave.logicas.miempresa;

import com.syswave.datos.common.DataSourceManager;
import com.syswave.datos.miempresa.TipoPersonasDataAccess;
import com.syswave.entidades.miempresa.TipoPersona;
import datalayer.api.IMediatorDataSource;
import java.util.List;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class TipoPersonasBusinessLogic
{
    private String mensaje;
    private TipoPersonasDataAccess tabla;
    
    //--------------------------------------------------------------------
    /**
     * Inicializa un objeto de lógica.
     */
    public TipoPersonasBusinessLogic(){
        IMediatorDataSource mysource = DataSourceManager.getMainDataSourceInstance();
        tabla = new TipoPersonasDataAccess(mysource);
    }
    
    //--------------------------------------------------------------------
    /**
     * Inicializa la instancia especificando el esquema al que se conectara.
     */
    public TipoPersonasBusinessLogic(String esquema)
    {
      this();
      tabla.setEschema(esquema);
    }
    
    //--------------------------------------------------------------------
    /**
     * Agrega el elemento especificado en una transaccion.
     */
    public boolean agregar (TipoPersona nuevo)
   {
      boolean resultado = tabla.Create(nuevo)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
    
    //--------------------------------------------------------------------
    /**
     * Agrega los elementos especificados en una misma transaccion.
     */
    public boolean agregar (List<TipoPersona> nuevos)
   {
      boolean resultado = tabla.Create(nuevos)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
    
    //--------------------------------------------------------------------
    /**
     * Actualiza el elemento especificado en una transaccion.
     */
    public boolean actualizar (TipoPersona elemento)
   {
      boolean resultado =  tabla.Update(elemento)>0;
      mensaje = tabla.getMessage();
        
      return resultado;
   }
   
   //--------------------------------------------------------------------
    /**
     * Actualiza todos los elementos especificados en una misma transaccion.
     */
    public boolean actualizar (List<TipoPersona> elementos)
   {
      boolean resultado = tabla.Update(elementos)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
    /**
     * Borra el elemento especificado en una transaccion.
     */
   public boolean borrar (TipoPersona elemento)
   {
      boolean resultado = tabla.Delete(elemento)>0;
      mensaje = tabla.getMessage();
     
      return resultado;
   }
   
   //---------------------------------------------------------------------
   /**
    * Borra los elementos especificado en una sola transaccion.
    */
   public boolean borrar (List<TipoPersona> elementos)
   {
      boolean resultado = tabla.Delete(elementos)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   /**
    * Obtiene todos los elementos.
    */
   public List<TipoPersona> obtenerLista ()
   {
      List<TipoPersona> resultado = tabla.Retrieve();
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   /**
    * Obtiene todos los elementos que cumplan con el criterio.
    */
   public List<TipoPersona> obtenerLista (TipoPersona elemento)
   {
      List<TipoPersona> resultado = tabla.Retrieve(elemento);
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   /**
    * Obtiene todas las hojas del árbol.
    * @return Una lista con todos los elementos.
    */
   public List<TipoPersona> obtenerListaHojas ()
   {
      List<TipoPersona> resultado = tabla.RetrieveLeafsPaths();
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   /**
    * Obtiene todas las hojas correspondientes a una rama del árbol.
    * @param tipo_base La base de la rama a devolver.
    * @return Una lista con todos los elementos.
    */
   public List<TipoPersona> obtenerListaRamaHojas (TipoPersona tipo_base)
   {
      List<TipoPersona> resultado = tabla.RetrieveLeafsBranchPaths(tipo_base);
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }

   //---------------------------------------------------------------------
   /**
    * Recarga la información del elemento especificado.
    */
   public boolean recargar (TipoPersona elemento)
   {
      List<TipoPersona> resultado = tabla.Retrieve(elemento);
      if (resultado.size() > 0)
      {
         elemento.copy(resultado.get(0));
      }
      
      mensaje = tabla.getMessage();
      
      return tabla.getState() != TipoPersonasDataAccess.DATASOURCE_ERROR;
   }
   
   
   //---------------------------------------------------------------------
   public boolean validar (TipoPersona elemento)
   {
      boolean esCorrecto = true;
      
      if (!elemento.getNombre().equals(TipoPersona.EMPTY_STRING))
      {
         esCorrecto = false;
         mensaje = "Es necesario especificar los nombres";
      }
       
      return esCorrecto;
   }
   
   //---------------------------------------------------------------------
   public boolean esCorrecto ()
   {
      return tabla.getState() != TipoPersonasDataAccess.DATASOURCE_ERROR;
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
