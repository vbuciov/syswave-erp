/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.syswave.logicas.miempresa;

import com.syswave.datos.common.DataSourceManager;
import com.syswave.datos.miempresa.CondicionComponentesDataAccess;
import com.syswave.datos.miempresa.CondicionesDataAccess;
import com.syswave.entidades.miempresa.Condicion;
import com.syswave.entidades.miempresa.Condicion_Componente;
import datalayer.api.IMediatorDataSource;
import java.util.List;

/**
 *
 * @author Aimee García
 */
public class CondicionesBusinessLogic {
   private String mensaje;
    private CondicionesDataAccess tabla;
    private CondicionComponentesDataAccess componentes;
    
   //---------------------------------------------------------------------
    public CondicionesBusinessLogic(){
        IMediatorDataSource mysource = DataSourceManager.getMainDataSourceInstance();
        tabla = new CondicionesDataAccess(mysource);
        componentes = new CondicionComponentesDataAccess(mysource);
    }
    
   //---------------------------------------------------------------------
    public CondicionesBusinessLogic(String esquema)
    {
       this();
      tabla.setEschema(esquema);
      componentes.setEschema(esquema);
    }
    
   //---------------------------------------------------------------------
    public boolean agregar (Condicion nuevo)
   {
      boolean resultado = tabla.Create(nuevo)>0;
      mensaje = tabla.getMessage();
           
      return resultado;
   }
    
   //---------------------------------------------------------------------
    public boolean agregar (List<Condicion> nuevos)
   {
      boolean resultado = tabla.Create(nuevos)>0;
      mensaje = tabla.getMessage();

      return resultado;
   }
    
    //---------------------------------------------------------------------
   public boolean actualizar (Condicion elemento)
   {
      boolean resultado =  tabla.Update(elemento)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
    //---------------------------------------------------------------------
  public boolean actualizar (List<Condicion> elementos)
   {
      boolean resultado = tabla.Update(elementos)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public boolean borrar (Condicion elemento)
   {
      boolean resultado = tabla.Delete(elemento)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public boolean borrar (List<Condicion> elementos)
   {
      boolean resultado = tabla.Delete(elementos)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
    //---------------------------------------------------------------------
    public boolean agregar (Condicion_Componente nuevo)
   {
      boolean resultado = componentes.Create(nuevo)>0;
      mensaje = componentes.getMessage();
           
      return resultado;
   }
    
   //---------------------------------------------------------------------
    public boolean agregarC (List<Condicion_Componente> nuevos)
   {
      boolean resultado = componentes.Create(nuevos)>0;
      mensaje = componentes.getMessage();

      return resultado;
   }
    
    //---------------------------------------------------------------------
   public boolean actualizar (Condicion_Componente elemento)
   {
      boolean resultado =  componentes.Update(elemento)>0;
      mensaje = componentes.getMessage();
      
      return resultado;
   }
   
    //---------------------------------------------------------------------
  public boolean actualizarC (List<Condicion_Componente> elementos)
   {
      boolean resultado = componentes.Update(elementos)>0;
      mensaje = componentes.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public boolean borrar (Condicion_Componente elemento)
   {
      boolean resultado = componentes.Delete(elemento)>0;
      mensaje = componentes.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public boolean borrarC (List<Condicion_Componente> elementos)
   {
      boolean resultado = componentes.Delete(elementos)>0;
      mensaje = componentes.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public List<Condicion> obtenerLista ()
   {
      List<Condicion> resultado = tabla.Retrieve();
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public List<Condicion> obtenerLista (Condicion elemento)
   {
      List<Condicion> resultado = tabla.Retrieve(elemento);
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
     //---------------------------------------------------------------------
    public List<Condicion_Componente> obtenerListaComponentes ()
   {
      List<Condicion_Componente> resultado = componentes.Retrieve();
      
      mensaje = componentes.getMessage();
      
      return resultado;
   }
   
    //---------------------------------------------------------------------
   public List<Condicion_Componente> obtenerListaComponentes (Condicion_Componente filtro)
   {
      List<Condicion_Componente> resultado = componentes.Retrieve(filtro);
      
      mensaje = componentes.getMessage();
      
      return resultado;
   }

   //---------------------------------------------------------------------
   public boolean recargar (Condicion elemento)
   {
      List<Condicion> resultado = tabla.Retrieve(elemento);
      if (resultado.size() > 0)
      {
         elemento.copy(resultado.get(0));
      }
      
      mensaje = tabla.getMessage();
      
      return tabla.getState() != CondicionesDataAccess.DATASOURCE_ERROR;
   }
   
   
   //---------------------------------------------------------------------
   public boolean validar (Condicion elemento)
   {
      boolean esCorrecto = true;
      
      
      if (!elemento.getNombre().equals(Condicion.EMPTY_STRING))
      {
         esCorrecto = false;
         mensaje = "Es necesario especificar el nombre";
      }
      
      return esCorrecto;
   }
   //---------------------------------------------------------------------
   public boolean recargarC (Condicion_Componente elemento)
   {
      List<Condicion_Componente> resultado = componentes.Retrieve(elemento);
      if (resultado.size() > 0)
      {
         elemento.copy(resultado.get(0));
      }
      
      mensaje = componentes.getMessage();
      
      return tabla.getState() != CondicionesDataAccess.DATASOURCE_ERROR;
   }
   
   
   //---------------------------------------------------------------------
   public boolean validarC (Condicion_Componente elemento)
   {
      boolean esCorrecto = true;
      
      
      if (elemento.getIdCondicion() == Condicion_Componente.EMPTY_INT)
      {
         esCorrecto = false;
         mensaje = "Es necesario especificar el nombre";
      }
      
      else if (elemento.getValor() == Condicion_Componente.EMPTY_INT)
      {
         esCorrecto = false;
         mensaje = "Es necesario especificar el valor";
      }
      
    
      else if (elemento.getEs_unidad() == Condicion.EMPTY_INT)
      {
         esCorrecto = false;
         mensaje = "Es necesario especificar una unidad";
      }
      
       else if (elemento.getEs_tipo()== Condicion.EMPTY_INT)
      {
         esCorrecto = false;
         mensaje = "Es necesario especificar un tipo";
      }
      
      
      return esCorrecto;
   }
   
   //---------------------------------------------------------------------
   public boolean esCorrecto ()
   {
      return tabla.getState() != CondicionesDataAccess.DATASOURCE_ERROR;
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
      componentes.setEschema(value);
   } 
}
