package com.syswave.logicas.miempresa;

import com.syswave.datos.common.DataSourceManager;
import com.syswave.datos.miempresa.DocumentoDetalleTienePrecioDataAccess;
import com.syswave.datos.miempresa.DocumentoDetallesDataAccess;
import com.syswave.datos.miempresa.DocumentoDetallesNavigableRetrieve;
import com.syswave.entidades.miempresa.DocumentoDetalle;
import com.syswave.entidades.miempresa.DocumentoDetalle_tiene_Precio;
import com.syswave.entidades.miempresa_vista.DocumentoDetalleNavigable;
import com.syswave.entidades.miempresa_vista.DocumentoDetalle_tiene_PrecioVista;
import datalayer.api.IMediatorDataSource;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class DocumentoDetallesBusinessLogic
{
    private String mensaje;
    private DocumentoDetallesDataAccess tabla;
    private DocumentoDetallesNavigableRetrieve vista;
    private DocumentoDetalleTienePrecioDataAccess subDetail;
    
   //---------------------------------------------------------------------
    public DocumentoDetallesBusinessLogic(){
        IMediatorDataSource mysource = DataSourceManager.getMainDataSourceInstance();
        tabla = new DocumentoDetallesDataAccess(mysource);
        vista = new DocumentoDetallesNavigableRetrieve(mysource);
        subDetail = new DocumentoDetalleTienePrecioDataAccess(mysource);
    }
    
   //---------------------------------------------------------------------
    public DocumentoDetallesBusinessLogic(String esquema)
    {
        this();
      tabla.setEschema(esquema);
      vista.setEschema(esquema);
      subDetail.setEschema(esquema);
    }
    
   //---------------------------------------------------------------------
    public boolean agregar (DocumentoDetalle nuevo)
   {
      boolean resultado = tabla.Create(nuevo)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
    
   //---------------------------------------------------------------------
    public boolean agregar (List<DocumentoDetalle> nuevos)
   {
      boolean resultado = tabla.Create(nuevos)>0;
      mensaje = tabla.getMessage();
     
      return resultado;
   }
    
    //---------------------------------------------------------------------
   public boolean actualizar (DocumentoDetalle elemento)
   {
      boolean resultado =  tabla.Update(elemento)>0;
      mensaje = tabla.getMessage();
         
      return resultado;
   }
   
    //---------------------------------------------------------------------
  public boolean actualizar (List<DocumentoDetalle> elementos)
   {
      boolean resultado = tabla.Update(elementos)>0;
      mensaje = tabla.getMessage();
       
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public boolean borrar (DocumentoDetalle elemento)
   {
      boolean resultado = tabla.Delete(elemento)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public boolean borrar (List<DocumentoDetalle> elementos)
   {
      boolean resultado = tabla.Delete(elementos)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public List<DocumentoDetalle> obtenerLista ()
   {
      List<DocumentoDetalle> resultado = tabla.Retrieve();
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public List<DocumentoDetalle> obtenerLista (DocumentoDetalle elemento)
   {
      List<DocumentoDetalle> resultado = tabla.Retrieve(elemento);
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
      public List<DocumentoDetalleNavigable> obtenerListaVista ()
   {
      List<DocumentoDetalleNavigable> resultado = vista.Retrieve();
      
      mensaje = vista.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public List<DocumentoDetalleNavigable> obtenerListaSeries (DocumentoDetalleNavigable elemento)
   {
      List<DocumentoDetalleNavigable> resultado = vista.Retrieve(elemento);
      
      mensaje = vista.getMessage();
      
      return resultado;
   }
   
  /*       //---------------------------------------------------------------------
   public List<DocumentoDetalle> obtenerListaSeriesCorta ()
   {
      List<DocumentoDetalle> resultado = vista.SmallRetrieve();
      
      mensaje = vista.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public List<DocumentoDetalle> obtenerListaSeriesCorta (DocumentoDetalle elemento)
   {
      List<DocumentoDetalle> resultado = vista.SmallRetrieve(elemento);
      
      mensaje = vista.getMessage();
      
      return resultado;
   }*/
 

   //---------------------------------------------------------------------
   public boolean recargar (DocumentoDetalle elemento)
   {
      List<DocumentoDetalle> resultado = tabla.Retrieve(elemento);
      if (resultado.size() > 0)
      {
         elemento.copy(resultado.get(0));
      }
      
      mensaje = tabla.getMessage();
      
      return tabla.getState() != DocumentoDetallesDataAccess.DATASOURCE_ERROR;
   }
   
   
   //---------------------------------------------------------------------
   public boolean validar (DocumentoDetalle elemento)
   {
      boolean esCorrecto = true;
      
      if (elemento.getIdDocumento()!=DocumentoDetalle.EMPTY_INT)
      {
         esCorrecto = false;
         mensaje = "Es necesario especificar una ubicación";
      }
           
      return esCorrecto;
   }
   
   //---------------------------------------------------------------------
   public boolean esCorrecto ()
   {
      return tabla.getState() != DocumentoDetallesDataAccess.DATASOURCE_ERROR; 
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
      subDetail.setEschema(value);
   }  

   //---------------------------------------------------------------------
   public boolean guardar(List<DocumentoDetalleNavigable> elementos, List<DocumentoDetalle> borrados)
   {
      List<DocumentoDetalle> nuevos = new ArrayList<>();
      List<DocumentoDetalle> modificados = new ArrayList<>();
      List<DocumentoDetalle_tiene_Precio> subNuevos = new ArrayList<>();
      List<DocumentoDetalle_tiene_Precio> subModificados = new ArrayList<>();
      List<DocumentoDetalle_tiene_PrecioVista> subDetalles = new ArrayList<>();
      
      for (DocumentoDetalleNavigable actual : elementos)
      {
         if (actual.isNew())
         {
            nuevos.add(actual);
            subNuevos.addAll(actual.getPartes());
         }

         else if (actual.isModified())
         {
            modificados.add(actual);
            subDetalles = actual.getPartes();
            
            for (DocumentoDetalle_tiene_PrecioVista subActual : subDetalles)
            {
               if (subActual.isNew())
                  subNuevos.add(subActual);
               
               else if (actual.isModified())
                  subModificados.add(subActual);
            }
         }
      }
         
      return (nuevos.isEmpty() || agregar(nuevos)) && 
             (modificados.isEmpty() || actualizar(modificados)) &&  
             (borrados == null || borrados.isEmpty() || borrar(borrados)) &&
             (subNuevos.isEmpty() || agregarSub(subNuevos) ) && 
             (subModificados.isEmpty() || actualizarSub(subModificados));
   }  
    
   //---------------------------------------------------------------------
    private boolean agregarSub (List<DocumentoDetalle_tiene_Precio> nuevos)
   {
      boolean resultado = subDetail.Create(nuevos)>0;
      mensaje = subDetail.getMessage();
     
      return resultado;
   }
    
  //---------------------------------------------------------------------
  private boolean actualizarSub (List<DocumentoDetalle_tiene_Precio> elementos)
   {
      boolean resultado = subDetail.Update(elementos)>0;
      mensaje = subDetail.getMessage();
       
      return resultado;
   }
   
   //---------------------------------------------------------------------
   private boolean borrarSub (List<DocumentoDetalle_tiene_Precio> elementos)
   {
      boolean resultado = subDetail.Delete(elementos)>0;
      mensaje = subDetail.getMessage();
      
      return resultado;
   }
}