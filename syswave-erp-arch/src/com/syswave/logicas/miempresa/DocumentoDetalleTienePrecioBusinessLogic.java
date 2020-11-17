package com.syswave.logicas.miempresa;

import com.syswave.datos.common.DataSourceManager;
import com.syswave.datos.miempresa.DocumentoDetalleTienePrecioDataAccess;
import com.syswave.datos.miempresa.DocumentoDetalleTienePrecioRetrieve;
import com.syswave.entidades.miempresa.DocumentoDetalle_tiene_Precio;
import com.syswave.entidades.miempresa_vista.DocumentoDetalle_tiene_PrecioVista;
import datalayer.api.IMediatorDataSource;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class DocumentoDetalleTienePrecioBusinessLogic
{
   private String mensaje;
   private DocumentoDetalleTienePrecioDataAccess tabla;
   private DocumentoDetalleTienePrecioRetrieve vista;
    
   //---------------------------------------------------------------------
    public DocumentoDetalleTienePrecioBusinessLogic(){
        IMediatorDataSource mysource = DataSourceManager.getMainDataSourceInstance();
        tabla = new DocumentoDetalleTienePrecioDataAccess(mysource);
        vista = new DocumentoDetalleTienePrecioRetrieve(mysource);
    }
    
   //---------------------------------------------------------------------
    public DocumentoDetalleTienePrecioBusinessLogic(String esquema)
    {
      this();
      tabla.setEschema(esquema);
      vista.setEschema(esquema);
    }
    
   //---------------------------------------------------------------------
    public boolean agregar (DocumentoDetalle_tiene_Precio nuevo)
   {
      boolean resultado = tabla.Create(nuevo)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
    
   //---------------------------------------------------------------------
    public boolean agregar (List<DocumentoDetalle_tiene_Precio> nuevos)
   {
      boolean resultado = tabla.Create(nuevos)>0;
      mensaje = tabla.getMessage();
     
      return resultado;
   }
    
    //---------------------------------------------------------------------
   public boolean actualizar (DocumentoDetalle_tiene_Precio elemento)
   {
      boolean resultado =  tabla.Update(elemento)>0;
      mensaje = tabla.getMessage();
         
      return resultado;
   }
   
    //---------------------------------------------------------------------
  public boolean actualizar (List<DocumentoDetalle_tiene_Precio> elementos)
   {
      boolean resultado = tabla.Update(elementos)>0;
      mensaje = tabla.getMessage();
       
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public boolean borrar (DocumentoDetalle_tiene_Precio elemento)
   {
      boolean resultado = tabla.Delete(elemento)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public boolean borrar (List<DocumentoDetalle_tiene_Precio> elementos)
   {
      boolean resultado = tabla.Delete(elementos)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public List<DocumentoDetalle_tiene_Precio> obtenerLista ()
   {
      List<DocumentoDetalle_tiene_Precio> resultado = tabla.Retrieve();
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public List<DocumentoDetalle_tiene_Precio> obtenerLista (DocumentoDetalle_tiene_Precio elemento)
   {
      List<DocumentoDetalle_tiene_Precio> resultado = tabla.Retrieve(elemento);
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public List<DocumentoDetalle_tiene_PrecioVista> obtenerLista5FN (int id_documento, int consecutivo)
   {
      List<DocumentoDetalle_tiene_PrecioVista> resultado = vista.Retrieve(id_documento, consecutivo);
      
      mensaje = vista.getMessage();
      
      return resultado;
   }

   //---------------------------------------------------------------------
   public boolean recargar (DocumentoDetalle_tiene_Precio elemento)
   {
      List<DocumentoDetalle_tiene_Precio> resultado = tabla.Retrieve(elemento);
      if (resultado.size() > 0)
      {
         elemento.copy(resultado.get(0));
      }
      
      mensaje = tabla.getMessage();
      
      return tabla.getState() != DocumentoDetalleTienePrecioDataAccess.DATASOURCE_ERROR;
   }
   
   
   //---------------------------------------------------------------------
   public boolean validar (DocumentoDetalle_tiene_Precio elemento)
   {
      boolean esCorrecto = true;
      
      if (elemento.getIdPrecio()!=DocumentoDetalle_tiene_Precio.EMPTY_INT)
      {
         esCorrecto = false;
         mensaje = "Es necesario especificar un precio";
      }
           
      return esCorrecto;
   }
   
      //---------------------------------------------------------------------
   public boolean guardar(List<DocumentoDetalle_tiene_PrecioVista> elementos, List<DocumentoDetalle_tiene_Precio> borrados)
   {
      List<DocumentoDetalle_tiene_Precio> nuevos = new ArrayList<>();
      List<DocumentoDetalle_tiene_Precio> modificados = new ArrayList<>();
      
      for (DocumentoDetalle_tiene_Precio actual : elementos)
      {
         if (actual.isNew())
            nuevos.add(actual);

         else if (actual.isModified())
            modificados.add(actual);
      }
         
      return (nuevos.isEmpty() || agregar(nuevos)) && (modificados.isEmpty() || actualizar(modificados)) &&  (borrados == null || borrados.isEmpty() || borrar(borrados));
   }
   
   //---------------------------------------------------------------------
   public boolean esCorrecto ()
   {
      return tabla.getState() != DocumentoDetalleTienePrecioDataAccess.DATASOURCE_ERROR;
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
