package com.syswave.logicas.miempresa;

import com.syswave.datos.common.DataSourceManager;
import com.syswave.datos.miempresa.ControlInventariosDataAccess;
import com.syswave.datos.miempresa.LotesRetrieve;
import com.syswave.entidades.miempresa.ControlInventario;
import datalayer.api.IMediatorDataSource;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class ControlInventariosBusinessLogic
{
    private String mensaje;
    private ControlInventariosDataAccess tabla;
    private LotesRetrieve vista;
    
   //---------------------------------------------------------------------
    public ControlInventariosBusinessLogic(){
        IMediatorDataSource mysource = DataSourceManager.getMainDataSourceInstance();
        tabla = new ControlInventariosDataAccess(mysource);
        vista = new LotesRetrieve(mysource);
    }
    
   //---------------------------------------------------------------------
    public ControlInventariosBusinessLogic(String esquema)
    {
       this();
      tabla.setEschema(esquema);
      vista.setEschema(esquema);
    }
    
   //---------------------------------------------------------------------
    public boolean agregar (ControlInventario nuevo)
   {
      boolean resultado = tabla.Create(nuevo)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
    
   //---------------------------------------------------------------------
    public boolean agregar (List<ControlInventario> nuevos)
   {
      boolean resultado = tabla.Create(nuevos)>0;
      mensaje = tabla.getMessage();
     
      return resultado;
   }
    
    //---------------------------------------------------------------------
   public boolean actualizar (ControlInventario elemento)
   {
      boolean resultado =  tabla.Update(elemento)>0;
      mensaje = tabla.getMessage();
         
      return resultado;
   }
   
    //---------------------------------------------------------------------
  public boolean actualizar (List<ControlInventario> elementos)
   {
      boolean resultado = tabla.Update(elementos)>0;
      mensaje = tabla.getMessage();
       
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public boolean borrar (ControlInventario elemento)
   {
      boolean resultado = tabla.Delete(elemento)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public boolean borrar (List<ControlInventario> elementos)
   {
      boolean resultado = tabla.Delete(elementos)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public List<ControlInventario> obtenerLista ()
   {
      List<ControlInventario> resultado = tabla.Retrieve();
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public List<ControlInventario> obtenerLista (ControlInventario elemento)
   {
      List<ControlInventario> resultado = tabla.Retrieve(elemento);
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
      //---------------------------------------------------------------------
   public List<ControlInventario> obtenerListaLotes ()
   {
      List<ControlInventario> resultado = vista.Retrieve();
      
      mensaje = vista.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public List<ControlInventario> obtenerListaLotes (ControlInventario elemento)
   {
      List<ControlInventario> resultado = vista.Retrieve(elemento);
      
      mensaje = vista.getMessage();
      
      return resultado;
   }
   
         //---------------------------------------------------------------------
   public List<ControlInventario> obtenerListaLotesCorta ()
   {
      List<ControlInventario> resultado = vista.SmallRetrieve();
      
      mensaje = vista.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public List<ControlInventario> obtenerListaLotesCorta (ControlInventario elemento)
   {
      List<ControlInventario> resultado = vista.SmallRetrieve(elemento);
      
      mensaje = vista.getMessage();
      
      return resultado;
   }
  

   //---------------------------------------------------------------------
   public boolean recargar (ControlInventario elemento)
   {
      List<ControlInventario> resultado = tabla.Retrieve(elemento);
      if (resultado.size() > 0)
      {
         elemento.copy(resultado.get(0));
      }
      
      mensaje = tabla.getMessage();
      
      return tabla.getState() != ControlInventariosDataAccess.DATASOURCE_ERROR;
   }
   
   
   //---------------------------------------------------------------------
   public boolean validar (ControlInventario elemento)
   {
      boolean esCorrecto = true;
      
      if (elemento.getIdVariante()!=ControlInventario.EMPTY_INT)
      {
         esCorrecto = false;
         mensaje = "Es necesario especificar un bien";
      }
           
      return esCorrecto;
   }
   
   //---------------------------------------------------------------------
   public boolean esCorrecto ()
   {
      return tabla.getState() != ControlInventariosDataAccess.DATASOURCE_ERROR;
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

   //---------------------------------------------------------------------
   public boolean guardar(List<ControlInventario> elementos, List<ControlInventario> borrados)
   {
      List<ControlInventario> nuevos = new ArrayList<>();
      List<ControlInventario> modificados = new ArrayList<>();
      
      for (ControlInventario actual : elementos)
      {
         if (actual.isNew())
            nuevos.add(actual);

         else if (actual.isModified())
            modificados.add(actual);
      }
         
      return (nuevos.isEmpty() || agregar(nuevos)) && (modificados.isEmpty() || actualizar(modificados)) &&  (borrados == null || borrados.isEmpty() || borrar(borrados));
   }
}