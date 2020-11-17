package com.syswave.logicas.miempresa;

import com.syswave.datos.common.DataSourceManager;
import com.syswave.datos.miempresa.DocumentoContadoMovimientoRetrieve;
import com.syswave.datos.miempresa.DocumentoContadoMovimientosDataAccess;
import com.syswave.entidades.miempresa.DocumentoContadoMovimiento;
import com.syswave.entidades.miempresa_vista.DocumentoContadoMovimiento_5FN;
import datalayer.api.IMediatorDataSource;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class DocumentoContadoMovimientosBusinessLogic 
{
   private String mensaje;
   private DocumentoContadoMovimientosDataAccess tabla;
   private DocumentoContadoMovimientoRetrieve vista;
    
   //---------------------------------------------------------------------
    public DocumentoContadoMovimientosBusinessLogic(){
        IMediatorDataSource mysource = DataSourceManager.getMainDataSourceInstance();
        tabla = new DocumentoContadoMovimientosDataAccess(mysource);
        vista = new DocumentoContadoMovimientoRetrieve(mysource);
    }
    
   //---------------------------------------------------------------------
    public DocumentoContadoMovimientosBusinessLogic(String esquema)
    {
      this();
      tabla.setEschema(esquema);
      vista.setEschema(esquema);
    }
    
   //---------------------------------------------------------------------
    public boolean agregar (DocumentoContadoMovimiento nuevo)
   {
      boolean resultado = tabla.Create(nuevo)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
    
   //---------------------------------------------------------------------
    public boolean agregar (List<DocumentoContadoMovimiento> nuevos)
   {
      boolean resultado = tabla.Create(nuevos)>0;
      mensaje = tabla.getMessage();
     
      return resultado;
   }
    
    //---------------------------------------------------------------------
   public boolean actualizar (DocumentoContadoMovimiento elemento)
   {
      boolean resultado =  tabla.Update(elemento)>0;
      mensaje = tabla.getMessage();
         
      return resultado;
   }
   
    //---------------------------------------------------------------------
  public boolean actualizar (List<DocumentoContadoMovimiento> elementos)
   {
      boolean resultado = tabla.Update(elementos)>0;
      mensaje = tabla.getMessage();
       
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public boolean borrar (DocumentoContadoMovimiento elemento)
   {
      boolean resultado = tabla.Delete(elemento)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public boolean borrar (List<DocumentoContadoMovimiento> elementos)
   {
      boolean resultado = tabla.Delete(elementos)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public List<DocumentoContadoMovimiento> obtenerLista ()
   {
      List<DocumentoContadoMovimiento> resultado = tabla.Retrieve();
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public List<DocumentoContadoMovimiento> obtenerLista (DocumentoContadoMovimiento elemento)
   {
      List<DocumentoContadoMovimiento> resultado = tabla.Retrieve(elemento);
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public List<DocumentoContadoMovimiento_5FN> obtenerLista5FN (int id_documento)
   {
      List<DocumentoContadoMovimiento_5FN> resultado = vista.Retrieve(id_documento);
      
      mensaje = vista.getMessage();
      
      return resultado;
   }

   //---------------------------------------------------------------------
   public boolean recargar (DocumentoContadoMovimiento elemento)
   {
      List<DocumentoContadoMovimiento> resultado = tabla.Retrieve(elemento);
      if (resultado.size() > 0)
      {
         elemento.copy(resultado.get(0));
      }
      
      mensaje = tabla.getMessage();
      
      return tabla.getState() != DocumentoContadoMovimientosDataAccess.DATASOURCE_ERROR;
   }
   
   
   //---------------------------------------------------------------------
   public boolean validar (DocumentoContadoMovimiento elemento)
   {
      boolean esCorrecto = true;
      
      if (elemento.getIdDocumento()!=DocumentoContadoMovimiento.EMPTY_INT)
      {
         esCorrecto = false;
         mensaje = "Es necesario especificar un documento valido";
      }
           
      return esCorrecto;
   }
   
      //---------------------------------------------------------------------
   public boolean guardar(List<DocumentoContadoMovimiento_5FN> elementos, List<DocumentoContadoMovimiento> borrados)
   {
      List<DocumentoContadoMovimiento> nuevos = new ArrayList<>();
      List<DocumentoContadoMovimiento> modificados = new ArrayList<>();
      
      for (DocumentoContadoMovimiento actual : elementos)
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
      return tabla.getState() != DocumentoContadoMovimientosDataAccess.DATASOURCE_ERROR;
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
