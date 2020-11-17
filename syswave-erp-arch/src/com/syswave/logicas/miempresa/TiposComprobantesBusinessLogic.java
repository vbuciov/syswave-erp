package com.syswave.logicas.miempresa;

import com.syswave.datos.common.DataSourceManager;
import com.syswave.datos.miempresa.TiposComprobantesDataAccess;
import com.syswave.entidades.miempresa.TipoComprobante;
import datalayer.api.IMediatorDataSource;
import java.util.List;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class TiposComprobantesBusinessLogic
{
      private String mensaje;
    private TiposComprobantesDataAccess tabla;
    
    //--------------------------------------------------------------------
    public TiposComprobantesBusinessLogic(){
        IMediatorDataSource mysource = DataSourceManager.getMainDataSourceInstance();
        tabla = new TiposComprobantesDataAccess(mysource);
    }
    
    //--------------------------------------------------------------------
    public TiposComprobantesBusinessLogic(String esquema)
    {
      this();
      tabla.setEschema(esquema);
    }
    
    //--------------------------------------------------------------------
    public boolean agregar (TipoComprobante nuevo)
   {
      boolean resultado = tabla.Create(nuevo)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
    
    //--------------------------------------------------------------------
    public boolean agregar (List<TipoComprobante> nuevos)
   {
      boolean resultado = tabla.Create(nuevos)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
    
    //--------------------------------------------------------------------
    public boolean actualizar (TipoComprobante elemento)
   {
      boolean resultado =  tabla.Update(elemento)>0;
      mensaje = tabla.getMessage();
        
      return resultado;
   }
   
   //--------------------------------------------------------------------
    public boolean actualizar (List<TipoComprobante> elementos)
   {
      boolean resultado = tabla.Update(elementos)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public boolean borrar (TipoComprobante elemento)
   {
      boolean resultado = tabla.Delete(elemento)>0;
      mensaje = tabla.getMessage();
     
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public boolean borrar (List<TipoComprobante> elementos)
   {
      boolean resultado = tabla.Delete(elementos)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public List<TipoComprobante> obtenerLista ()
   {
      List<TipoComprobante> resultado = tabla.Retrieve();
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public List<TipoComprobante> obtenerLista (TipoComprobante elemento)
   {
      List<TipoComprobante> resultado = tabla.Retrieve(elemento);
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   /**
    * Obtiene la información existente de modulos instalados.
    * @param elemento
    * @return Una lista con todos los elementos.
    */
   public List<TipoComprobante> obtenerListaHojas ()
   {
      List<TipoComprobante> resultado = tabla.RetrieveLeafsPaths();
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   
     //---------------------------------------------------------------------
   /**
    * Obtiene la información existente de modulos instalados.
    * @param elemento
    * @return Una lista con todos los elementos.
    */
   public List<TipoComprobante> obtenerListaHojasInventario (TipoComprobante Filter)
   {
      List<TipoComprobante> resultado = tabla.RetrieveLeafsPaths(Filter, true,false,false);
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
     //---------------------------------------------------------------------
   /**
    * Obtiene la información existente de modulos instalados.
    * @param elemento
    * @return Una lista con todos los elementos.
    */
   public List<TipoComprobante> obtenerListaHojasSaldo (TipoComprobante Filter)
   {
      List<TipoComprobante> resultado = tabla.RetrieveLeafsPaths(Filter, false,true,false);
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
     //---------------------------------------------------------------------
   /**
    * Obtiene la información existente de modulos instalados.
    * @param elemento
    * @return Una lista con todos los elementos.
    */
   public List<TipoComprobante> obtenerListaHojasComerciales (TipoComprobante Filter)
   {
      List<TipoComprobante> resultado = tabla.RetrieveLeafsPaths(Filter, false,false,true);
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }


   //---------------------------------------------------------------------
    /**
    * Recarga la información del elemento especificado
    * @param elemento
    * @return true
    */
   public boolean recargar (TipoComprobante elemento)
   {
      List<TipoComprobante> resultado = tabla.Retrieve(elemento);
      if (resultado.size() > 0)
      {
         elemento.copy(resultado.get(0));
      }
      
      mensaje = tabla.getMessage();
      
      return tabla.getState() != TiposComprobantesDataAccess.DATASOURCE_ERROR;
   }
   
   
   //---------------------------------------------------------------------
   public boolean validar (TipoComprobante elemento)
   {
      boolean esCorrecto = true;
      
      if (!elemento.getNombre().equals(TipoComprobante.EMPTY_STRING))
      {
         esCorrecto = false;
         mensaje = "Es necesario especificar los nombres";
      }
       
      return esCorrecto;
   }
   
   //---------------------------------------------------------------------
   public boolean esCorrecto ()
   {
      return tabla.getState() != TiposComprobantesDataAccess.DATASOURCE_ERROR; 
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
