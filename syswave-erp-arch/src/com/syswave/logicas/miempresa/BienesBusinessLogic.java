package com.syswave.logicas.miempresa;

import com.syswave.datos.common.DataSourceManager;
import com.syswave.datos.miempresa.BienesDataAccess;
import com.syswave.datos.miempresa.ClaseInventarioDataAccess;
import com.syswave.entidades.miempresa.Bien;
import com.syswave.entidades.miempresa.ClaseInventario;
import datalayer.api.IMediatorDataSource;
import java.util.List;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class BienesBusinessLogic
{
    private String mensaje;
    private BienesDataAccess tabla;
    private ClaseInventarioDataAccess relaciones;
    
   //---------------------------------------------------------------------
    public BienesBusinessLogic(){
        IMediatorDataSource mysource = DataSourceManager.getMainDataSourceInstance();
        tabla = new BienesDataAccess(mysource);
        relaciones = new ClaseInventarioDataAccess(mysource);
    }
    
   //---------------------------------------------------------------------
    public BienesBusinessLogic(String esquema)
    {
      this();
      tabla.setEschema(esquema);
      relaciones.setEschema(esquema);
    }
    
   //---------------------------------------------------------------------
    public boolean agregar (Bien nuevo)
   {
      boolean resultado = tabla.Create(nuevo)>0;
      mensaje = tabla.getMessage();
           
      return resultado;
   }
    
   //---------------------------------------------------------------------
    public boolean agregar (List<Bien> nuevos)
   {
      boolean resultado = tabla.Create(nuevos)>0;
      mensaje = tabla.getMessage();

      return resultado;
   }
    
    //---------------------------------------------------------------------
   public boolean actualizar (Bien elemento)
   {
      boolean resultado =  tabla.Update(elemento)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
    //---------------------------------------------------------------------
  public boolean actualizar (List<Bien> elementos)
   {
      boolean resultado = tabla.Update(elementos)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public boolean borrar (Bien elemento)
   {
      boolean resultado = tabla.Delete(elemento)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public boolean borrar (List<Bien> elementos)
   {
      boolean resultado = tabla.Delete(elementos)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public List<Bien> obtenerLista ()
   {
      List<Bien> resultado = tabla.Retrieve();
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public List<Bien> obtenerLista (Bien elemento)
   {
      List<Bien> resultado = tabla.Retrieve(elemento);
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public List<ClaseInventario> obtenerClasesInventarioLista ()
   {
       
             List<ClaseInventario> resultado = relaciones.Retrieve();
      
      mensaje = relaciones.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public List<ClaseInventario> obtenerClasesInventarioLista (ClaseInventario elemento)
   {
       
      List<ClaseInventario> resultado = relaciones.Retrieve(elemento);
      
      mensaje = relaciones.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public boolean recargar (Bien elemento)
   {
      List<Bien> resultado = tabla.Retrieve(elemento);
      if (resultado.size() > 0)
      {
         elemento.copy(resultado.get(0));
      }
      
      mensaje = tabla.getMessage();
      
      return tabla.getState() != BienesDataAccess.DATASOURCE_ERROR;
   }
   
   
   //---------------------------------------------------------------------
   public boolean validar (Bien elemento)
   {
      boolean esCorrecto = true;
      
      
      if (!elemento.getNombre().equals(Bien.EMPTY_STRING))
      {
         esCorrecto = false;
         mensaje = "Es necesario especificar el nombre";
      }
      
      else if (elemento.getEsTipo() != Bien.EMPTY_INT)
      {
         esCorrecto = false;
         mensaje = "Es necesario especificar el tipo de bien";
      }
      
    
      else if (elemento.getIdCategoria()!=Bien.EMPTY_INT)
      {
         esCorrecto = false;
         mensaje = "Es necesario especificar una categoria";
      }
      
      return esCorrecto;
   }
   
   //---------------------------------------------------------------------
   public boolean esCorrecto ()
   {
      return tabla.getState() != BienesDataAccess.DATASOURCE_ERROR &&
             relaciones.getState() != ClaseInventarioDataAccess.DATASOURCE_ERROR;
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