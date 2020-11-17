package com.syswave.logicas.miempresa;

import com.syswave.datos.common.DataSourceManager;
import com.syswave.datos.miempresa.DocumentoCreditoMovimientosDataAccess;
import com.syswave.entidades.miempresa.DocumentoCreditoMovimiento;
import datalayer.api.IMediatorDataSource;
import java.util.List;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class DocumentoCreditoMovimientosBusinessLogic
{
   private String mensaje;
   private DocumentoCreditoMovimientosDataAccess tabla;
   //private DocumentoCreditoMovimientoRetrieve vista;
    
   //---------------------------------------------------------------------
    public DocumentoCreditoMovimientosBusinessLogic(){
        IMediatorDataSource mysource = DataSourceManager.getMainDataSourceInstance();
        tabla = new DocumentoCreditoMovimientosDataAccess(mysource);
        //vista = new DocumentoCreditoMovimientoRetrieve();
    }
    
   //---------------------------------------------------------------------
    public DocumentoCreditoMovimientosBusinessLogic(String esquema)
    {
      this();
      tabla.setEschema(esquema);
        /*vista = new DocumentoCreditoMovimientoRetrieve();
        vista.setEschema(esquema);*/
    }
    
   //---------------------------------------------------------------------
    public boolean agregar (DocumentoCreditoMovimiento nuevo)
   {
      boolean resultado = tabla.Create(nuevo)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
    
   //---------------------------------------------------------------------
    public boolean agregar (List<DocumentoCreditoMovimiento> nuevos)
   {
      boolean resultado = tabla.Create(nuevos)>0;
      mensaje = tabla.getMessage();
     
      return resultado;
   }
    
    //---------------------------------------------------------------------
   public boolean actualizar (DocumentoCreditoMovimiento elemento)
   {
      boolean resultado =  tabla.Update(elemento)>0;
      mensaje = tabla.getMessage();
         
      return resultado;
   }
   
    //---------------------------------------------------------------------
  public boolean actualizar (List<DocumentoCreditoMovimiento> elementos)
   {
      boolean resultado = tabla.Update(elementos)>0;
      mensaje = tabla.getMessage();
       
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public boolean borrar (DocumentoCreditoMovimiento elemento)
   {
      boolean resultado = tabla.Delete(elemento)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public boolean borrar (List<DocumentoCreditoMovimiento> elementos)
   {
      boolean resultado = tabla.Delete(elementos)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public List<DocumentoCreditoMovimiento> obtenerLista ()
   {
      List<DocumentoCreditoMovimiento> resultado = tabla.Retrieve();
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public List<DocumentoCreditoMovimiento> obtenerLista (DocumentoCreditoMovimiento elemento)
   {
      List<DocumentoCreditoMovimiento> resultado = tabla.Retrieve(elemento);
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   /*public List<DocumentoCreditoMovimiento_5FN> obtenerLista5FN (int id_documento)
   {
      List<DocumentoCreditoMovimiento_5FN> resultado = vista.Retrieve(id_documento);
      
      mensaje = vista.getMessage();
      
      return resultado;
   }*/

   //---------------------------------------------------------------------
   public boolean recargar (DocumentoCreditoMovimiento elemento)
   {
      List<DocumentoCreditoMovimiento> resultado = tabla.Retrieve(elemento);
      if (resultado.size() > 0)
      {
         elemento.copy(resultado.get(0));
      }
      
      mensaje = tabla.getMessage();
      
      return tabla.getState() != DocumentoCreditoMovimientosDataAccess.DATASOURCE_ERROR;
   }
   
   
   //---------------------------------------------------------------------
   public boolean validar (DocumentoCreditoMovimiento elemento)
   {
      boolean esCorrecto = true;
      
      if (elemento.getIdDocumento()!=DocumentoCreditoMovimiento.EMPTY_INT)
      {
         esCorrecto = false;
         mensaje = "Es necesario especificar un documento valido";
      }
           
      return esCorrecto;
   }
   
      //---------------------------------------------------------------------
  /* public boolean guardar(List<DocumentoCreditoMovimiento_5FN> elementos, List<DocumentoCreditoMovimiento> borrados)
   {
      List<DocumentoCreditoMovimiento> nuevos = new ArrayList<>();
      List<DocumentoCreditoMovimiento> modificados = new ArrayList<>();
      
      for (DocumentoCreditoMovimiento actual : elementos)
      {
         if (actual.isNew())
            nuevos.add(actual);

         else if (actual.isModified())
            modificados.add(actual);
      }
         
      return (nuevos.isEmpty() || agregar(nuevos)) && (modificados.isEmpty() || actualizar(modificados)) &&  (borrados == null || borrados.isEmpty() || borrar(borrados));
   }*/
   
   //---------------------------------------------------------------------
   public boolean esCorrecto ()
   {
      return tabla.getState() != DocumentoCreditoMovimientosDataAccess.DATASOURCE_ERROR;
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
      //vista.setEschema(value);
   }  
}
