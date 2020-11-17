package com.syswave.logicas.miempresa;

import com.syswave.datos.common.DataSourceManager;
import com.syswave.datos.miempresa.MantenimientosDataAccess;
import com.syswave.entidades.miempresa.Mantenimiento;
import datalayer.api.IMediatorDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class MantenimientosBusinessLogic
{
     private String mensaje;
    private MantenimientosDataAccess tabla;
    //private MantenimientoComercialRetrieve vista;
    
   //---------------------------------------------------------------------
    public MantenimientosBusinessLogic(){
        IMediatorDataSource mysource = DataSourceManager.getMainDataSourceInstance();
        tabla = new MantenimientosDataAccess(mysource);
       // vista = new MantenimientoComercialRetrieve();
    }
    
   //---------------------------------------------------------------------
    public MantenimientosBusinessLogic(String esquema)
    {
      this();
      tabla.setEschema(esquema);
      
       /*vista = new MantenimientoComercialRetrieve();
       vista.setEschema(esquema);*/
    }
    
   //---------------------------------------------------------------------
    public boolean agregar (Mantenimiento nuevo)
   {
      boolean resultado = tabla.Create(nuevo)>0;
      mensaje = tabla.getMessage();
           
      return resultado;
   }
    
   //---------------------------------------------------------------------
    public boolean agregar (List<Mantenimiento> nuevos)
   {
      boolean resultado = tabla.Create(nuevos)>0;
      mensaje = tabla.getMessage();

      return resultado;
   }
    
    //---------------------------------------------------------------------
   public boolean actualizar (Mantenimiento elemento)
   {
      boolean resultado =  tabla.Update(elemento)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
    //---------------------------------------------------------------------
  public boolean actualizar (List<Mantenimiento> elementos)
   {
      boolean resultado = tabla.Update(elementos)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public boolean borrar (Mantenimiento elemento)
   {
      boolean resultado = tabla.Delete(elemento)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public boolean borrar (List<Mantenimiento> elementos)
   {
      boolean resultado = tabla.Delete(elementos)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public List<Mantenimiento> obtenerLista ()
   {
      List<Mantenimiento> resultado = tabla.Retrieve();
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public List<Mantenimiento> obtenerLista (Mantenimiento elemento)
   {
      List<Mantenimiento> resultado = tabla.Retrieve(elemento);
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
      //---------------------------------------------------------------------
   /*public List<MantenimientoComercial> obtenerListaVista ()
   {
      List<MantenimientoComercial> resultado = vista.Retrieve();
      
      mensaje = vista.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public List<MantenimientoComercial> obtenerListaVista (MantenimientoComercial elemento)
   {
      List<MantenimientoComercial> resultado = vista.Retrieve(elemento);
      
      mensaje = vista.getMessage();
      
      return resultado;
   }*/

   //---------------------------------------------------------------------
   public boolean recargar (Mantenimiento elemento)
   {
      List<Mantenimiento> resultado = tabla.Retrieve(elemento);
      if (resultado.size() > 0)
      {
         elemento.copy(resultado.get(0));
      }
      
      mensaje = tabla.getMessage();
      
      return tabla.getState() != MantenimientosDataAccess.DATASOURCE_ERROR;
   }
   
   
   //---------------------------------------------------------------------
   public boolean validar (Mantenimiento elemento)
   {
      boolean esCorrecto = true;
      
      if (elemento.getFolio().equals(Mantenimiento.EMPTY_STRING))
      {
         esCorrecto = false;
         mensaje = "Es necesario especificar un folio";
      }
     
      
      else if (elemento.isSet())
      {
         esCorrecto = false;
         mensaje = "Es necesario especificar si la persona es activa";
      }
      

      return esCorrecto;
   }
   
   //---------------------------------------------------------------------
   public boolean esCorrecto ()
   {
      return tabla.getState() != MantenimientosDataAccess.DATASOURCE_ERROR;
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

 public Connection getConexion()
    {
        try
        {
            return DataSourceManager.getMainDataSourceInstance().createSession();
        }
        catch (SQLException ex)
        {
            Logger.getLogger(DocumentosBusinessLogic.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void setAvailableConextion(Connection value)
    {
        try
        {
            DataSourceManager.getMainDataSourceInstance().disconnect(value);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(DocumentosBusinessLogic.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
