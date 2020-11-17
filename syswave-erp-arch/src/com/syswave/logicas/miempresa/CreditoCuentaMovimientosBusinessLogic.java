package com.syswave.logicas.miempresa;

import com.syswave.datos.common.DataSourceManager;
import com.syswave.datos.miempresa.CreditoCuentaMovimientosDataAccess;
import com.syswave.entidades.miempresa.CreditoCuentaMovimiento;
import datalayer.api.IMediatorDataSource;
import java.util.List;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class CreditoCuentaMovimientosBusinessLogic
{
   private String mensaje;
   private CreditoCuentaMovimientosDataAccess tabla;
   //private CreditoCuentaMovimientosRetrieve vista;
    
   //---------------------------------------------------------------------
    public CreditoCuentaMovimientosBusinessLogic(){
        IMediatorDataSource mysource = DataSourceManager.getMainDataSourceInstance();
        tabla = new CreditoCuentaMovimientosDataAccess(mysource);
        //vista = new CreditoCuentaMovimientosRetrieve();
    }
    
   //---------------------------------------------------------------------
    public CreditoCuentaMovimientosBusinessLogic(String esquema)
    {
      this();
      tabla.setEschema(esquema);
        /*vista = new CreditoCuentaMovimientosRetrieve();
        vista.setEschema(esquema);*/
    }
    
   //---------------------------------------------------------------------
    public boolean agregar (CreditoCuentaMovimiento nuevo)
   {
      boolean resultado = tabla.Create(nuevo)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
    
   //---------------------------------------------------------------------
    public boolean agregar (List<CreditoCuentaMovimiento> nuevos)
   {
      boolean resultado = tabla.Create(nuevos)>0;
      mensaje = tabla.getMessage();
     
      return resultado;
   }
    
    //---------------------------------------------------------------------
   public boolean actualizar (CreditoCuentaMovimiento elemento)
   {
      boolean resultado =  tabla.Update(elemento)>0;
      mensaje = tabla.getMessage();
         
      return resultado;
   }
   
    //---------------------------------------------------------------------
  public boolean actualizar (List<CreditoCuentaMovimiento> elementos)
   {
      boolean resultado = tabla.Update(elementos)>0;
      mensaje = tabla.getMessage();
       
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public boolean borrar (CreditoCuentaMovimiento elemento)
   {
      boolean resultado = tabla.Delete(elemento)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public boolean borrar (List<CreditoCuentaMovimiento> elementos)
   {
      boolean resultado = tabla.Delete(elementos)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public List<CreditoCuentaMovimiento> obtenerLista ()
   {
      List<CreditoCuentaMovimiento> resultado = tabla.Retrieve();
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public List<CreditoCuentaMovimiento> obtenerLista (CreditoCuentaMovimiento elemento)
   {
      List<CreditoCuentaMovimiento> resultado = tabla.Retrieve(elemento);
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   /*public List<CreditoCuentaMovimiento_5FN> obtenerLista5FN (int id_documento)
   {
      List<CreditoCuentaMovimiento_5FN> resultado = vista.Retrieve(id_documento);
      
      mensaje = vista.getMessage();
      
      return resultado;
   }*/

   //---------------------------------------------------------------------
   public boolean recargar (CreditoCuentaMovimiento elemento)
   {
      List<CreditoCuentaMovimiento> resultado = tabla.Retrieve(elemento);
      if (resultado.size() > 0)
      {
         elemento.copy(resultado.get(0));
      }
      
      mensaje = tabla.getMessage();
      
      return tabla.getState() != CreditoCuentaMovimientosDataAccess.DATASOURCE_ERROR;
   }
   
   
   //---------------------------------------------------------------------
   public boolean validar (CreditoCuentaMovimiento elemento)
   {
      boolean esCorrecto = true;
      
      if (elemento.getConsecutivo()!=CreditoCuentaMovimiento.EMPTY_INT &&
          elemento.getIdPersona() != CreditoCuentaMovimiento.EMPTY_INT)
      {
         esCorrecto = false;
         mensaje = "Es necesario especificar un cuenta";
      }
           
      return esCorrecto;
   }
   
      //---------------------------------------------------------------------
  /* public boolean guardar(List<CreditoCuentaMovimiento_5FN> elementos, List<CreditoCuentaMovimiento> borrados)
   {
      List<CreditoCuentaMovimiento> nuevos = new ArrayList<>();
      List<CreditoCuentaMovimiento> modificados = new ArrayList<>();
      
      for (CreditoCuentaMovimiento actual : elementos)
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
      return tabla.getState() != CreditoCuentaMovimientosDataAccess.DATASOURCE_ERROR;
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