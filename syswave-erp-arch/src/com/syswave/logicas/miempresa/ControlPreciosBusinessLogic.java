package com.syswave.logicas.miempresa;

import com.syswave.datos.common.DataSourceManager;
import com.syswave.datos.miempresa.ControlPreciosDataAccess;
import com.syswave.datos.miempresa.PreciosRetrieve;
import com.syswave.entidades.miempresa.AreaPrecio;
import com.syswave.entidades.miempresa.Categoria;
import com.syswave.entidades.miempresa.ControlPrecio;
import com.syswave.entidades.miempresa.Valor;
import com.syswave.entidades.miempresa.VarianteIdentificador;
import com.syswave.entidades.miempresa_vista.ControlPrecioVista;
import datalayer.api.IMediatorDataSource;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class ControlPreciosBusinessLogic
{
    private String mensaje;
    private ControlPreciosDataAccess tabla;
    private PreciosRetrieve vista;
    
   //---------------------------------------------------------------------
    public ControlPreciosBusinessLogic(){
        IMediatorDataSource mysource = DataSourceManager.getMainDataSourceInstance();
        tabla = new ControlPreciosDataAccess(mysource);
        vista = new PreciosRetrieve(mysource);
    }
    
   //---------------------------------------------------------------------
    public ControlPreciosBusinessLogic(String esquema)
    {
      this();
      tabla.setEschema(esquema);
      vista.setEschema(esquema);
    }
    
   //---------------------------------------------------------------------
    public boolean agregar (ControlPrecio nuevo)
   {
      boolean resultado = tabla.Create(nuevo)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
    
   //---------------------------------------------------------------------
    public boolean agregar (List<ControlPrecio> nuevos)
   {
      boolean resultado = tabla.Create(nuevos)>0;
      mensaje = tabla.getMessage();
     
      return resultado;
   }
    
    //---------------------------------------------------------------------
   public boolean actualizar (ControlPrecio elemento)
   {
      boolean resultado =  tabla.Update(elemento)>0;
      mensaje = tabla.getMessage();
         
      return resultado;
   }
   
    //---------------------------------------------------------------------
  public boolean actualizar (List<ControlPrecio> elementos)
   {
      boolean resultado = tabla.Update(elementos)>0;
      mensaje = tabla.getMessage();
       
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public boolean borrar (ControlPrecio elemento)
   {
      boolean resultado = tabla.Delete(elemento)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public boolean borrar (List<ControlPrecio> elementos)
   {
      boolean resultado = tabla.Delete(elementos)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public List<ControlPrecio> obtenerLista ()
   {
      List<ControlPrecio> resultado = tabla.Retrieve();
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public List<ControlPrecio> obtenerLista (ControlPrecio elemento)
   {
      List<ControlPrecio> resultado = tabla.Retrieve(elemento);
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //------------------------------------------------------------------------
   /*public List<Valor> obtenerTipoPrecios ()
   {
     List<Valor> listaTipos = new ArrayList<>();
     listaTipos.add(new Valor(0, "Compra"));
     listaTipos.add(new Valor(1, "Venta"));
     return listaTipos;
   } */ 
   
   //---------------------------------------------------------------------
   public List<ControlPrecioVista> buscarPrecio (Categoria tipo, int es_venta, ControlPrecioVista generales, VarianteIdentificador identificador)
   {
      List<ControlPrecioVista> resultado = vista.PrecioBuscar(tipo, es_venta, generales, identificador);
      
      mensaje = vista.getMessage();
      
      return resultado;
   }
   
    //---------------------------------------------------------------------
   public List<ControlPrecioVista> buscarPrecio (AreaPrecio tipo, int es_venta, ControlPrecioVista generales, VarianteIdentificador identificador)
   {
      List<ControlPrecioVista> resultado = vista.PrecioBuscar(tipo, es_venta, generales, identificador);
      
      mensaje = vista.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public boolean recargar (ControlPrecio elemento)
   {
      List<ControlPrecio> resultado = tabla.Retrieve(elemento);
      if (resultado.size() > 0)
      {
         elemento.copy(resultado.get(0));
      }
      
      mensaje = tabla.getMessage();
      
      return tabla.getState() != ControlPreciosDataAccess.DATASOURCE_ERROR;
   }
   
   
   //---------------------------------------------------------------------
   public boolean validar (ControlPrecio elemento)
   {
      boolean esCorrecto = true;
      
      if (elemento.getIdVariante()!=ControlPrecio.EMPTY_INT)
      {
         esCorrecto = false;
         mensaje = "Es necesario especificar un bien";
      }
           
      return esCorrecto;
   }
   
   //---------------------------------------------------------------------
   public boolean esCorrecto ()
   {
      return tabla.getState() != ControlPreciosDataAccess.DATASOURCE_ERROR;
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
   public boolean guardar(List<ControlPrecio> elementos, List<ControlPrecio> borrados)
   {
      List<ControlPrecio> nuevos = new ArrayList<>();
      List<ControlPrecio> modificados = new ArrayList<>();
      
      for (ControlPrecio actual : elementos)
      {
         if (actual.isNew())
            nuevos.add(actual);

         else if (actual.isModified())
            modificados.add(actual);
      }
         
      return (nuevos.isEmpty() || agregar(nuevos)) && (modificados.isEmpty() || actualizar(modificados)) &&  (borrados == null || borrados.isEmpty() || borrar(borrados));
   }
}