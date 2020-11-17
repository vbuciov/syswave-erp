package com.syswave.logicas.miempresa;

import com.syswave.datos.common.DataSourceManager;
import com.syswave.datos.miempresa.DocumentoCondicionesDataAccess;
import com.syswave.datos.miempresa.DocumentoTieneCondicionesRetrieve;
import com.syswave.entidades.miempresa.Documento_tiene_Condicion;
import com.syswave.entidades.miempresa_vista.Documento_tiene_Condicion_5FN;
import datalayer.api.IMediatorDataSource;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class DocumentoCondicionesLogic
{
   private String mensaje;
   private DocumentoCondicionesDataAccess tabla;
   private DocumentoTieneCondicionesRetrieve vista;
   
   //---------------------------------------------------------------------
   public DocumentoCondicionesLogic()
   {
       IMediatorDataSource mysource = DataSourceManager.getMainDataSourceInstance();
      tabla = new DocumentoCondicionesDataAccess(mysource);
      vista = new DocumentoTieneCondicionesRetrieve(mysource);
   }

   //---------------------------------------------------------------------
   public DocumentoCondicionesLogic(String Esquema)
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
   public boolean agregar (Documento_tiene_Condicion nuevo)
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
   public boolean agregar (List<Documento_tiene_Condicion> nuevos)
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
   public boolean actualizar (Documento_tiene_Condicion elemento)
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
   public boolean actualizar (List<Documento_tiene_Condicion> elementos)
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
   public boolean borrar (Documento_tiene_Condicion elemento)
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
   public boolean borrar (List<Documento_tiene_Condicion> elementos)
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
   public List<Documento_tiene_Condicion> obtenerLista ()
   {
      List<Documento_tiene_Condicion> resultado = tabla.Retrieve();
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   /**
    * Obtiene aquellos elementos que coinciden en información con el elemento recibido
    * @param elemento
    * @return Una lista con los elementos.
    */
    public List<Documento_tiene_Condicion> obtenerLista (Documento_tiene_Condicion elemento)
   {
      List<Documento_tiene_Condicion> resultado = tabla.Retrieve(elemento);
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
    
      //---------------------------------------------------------------------
   public List<Documento_tiene_Condicion_5FN> obtenerLista5FN (int id_documento)
   {
      List<Documento_tiene_Condicion_5FN> resultado = vista.Retrieve(id_documento);
      
      mensaje = vista.getMessage();
      
      return resultado;
   }

      
    //---------------------------------------------------------------------
   /**
    * Recarga la información del elemento recibido.
    * @param elemento
    * @return Indica si la operación se llevo correctamente.
    */
   public boolean recargar (Documento_tiene_Condicion elemento)
   {
      List<Documento_tiene_Condicion> resultado = tabla.Retrieve(elemento);
      if (resultado.size() > 0)
      {
         elemento.copy(resultado.get(0));
      }
      
      mensaje = tabla.getMessage();
      
      return tabla.getState() != DocumentoCondicionesDataAccess.DATASOURCE_ERROR;
   }
   
   //---------------------------------------------------------------------
   public boolean guardar(List<Documento_tiene_Condicion_5FN> elementos, List<Documento_tiene_Condicion> borrados)
   {
      List<Documento_tiene_Condicion> nuevos = new ArrayList<>();
      List<Documento_tiene_Condicion> modificados = new ArrayList<>();
      
      for (Documento_tiene_Condicion actual : elementos)
      {
         if (actual.isNew())
            nuevos.add(actual);

         else if (actual.isModified())
            modificados.add(actual);
      }
         
      return (nuevos.isEmpty() || agregar(nuevos)) && (modificados.isEmpty() || actualizar(modificados)) &&  (borrados == null || borrados.isEmpty() || borrar(borrados));
  
   }  
   
   //---------------------------------------------------------------------
   public boolean valirdar (Documento_tiene_Condicion elemento)
   {
      boolean esCorrecto = true;
      
      if (elemento.getIdDocumento() != Documento_tiene_Condicion.EMPTY_INT)
      {
         esCorrecto = false;
         mensaje = "Es necesario especificar un documento";
      }
      
      return esCorrecto;
   }
   
   //---------------------------------------------------------------------
   public boolean esCorrecto ()
   {
      return tabla.getState() != DocumentoCondicionesDataAccess.DATASOURCE_ERROR;
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
