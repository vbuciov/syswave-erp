package com.syswave.logicas.miempresa;

import com.syswave.datos.common.DataSourceManager;
import com.syswave.datos.miempresa.CondicionesPagoDataAccess;
import com.syswave.datos.miempresa.ValorRetrieve;
import com.syswave.entidades.miempresa.CondicionPago;
import com.syswave.entidades.miempresa.Valor;
import datalayer.api.IMediatorDataSource;
import java.util.List;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class CondicionesPagoBusinessLogic
{
     private String mensaje;
    private CondicionesPagoDataAccess tabla;
    private ValorRetrieve tiposCondicion;
    
   //---------------------------------------------------------------------
    public CondicionesPagoBusinessLogic(){
        IMediatorDataSource mysource = DataSourceManager.getMainDataSourceInstance();
        tabla = new CondicionesPagoDataAccess(mysource);
        tiposCondicion = new ValorRetrieve(mysource);
    }
    
   //---------------------------------------------------------------------
    public CondicionesPagoBusinessLogic(String esquema)
    {
      this();
      tabla.setEschema(esquema);
      tiposCondicion.setEschema(esquema);
    }
    
   //---------------------------------------------------------------------
    public boolean agregar (CondicionPago nuevo)
   {
      boolean resultado = tabla.Create(nuevo)>0;
      mensaje = tabla.getMessage();
           
      return resultado;
   }
    
   //---------------------------------------------------------------------
    public boolean agregar (List<CondicionPago> nuevos)
   {
      boolean resultado = tabla.Create(nuevos)>0;
      mensaje = tabla.getMessage();

      return resultado;
   }
    
    //---------------------------------------------------------------------
   public boolean actualizar (CondicionPago elemento)
   {
      boolean resultado =  tabla.Update(elemento)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
    //---------------------------------------------------------------------
  public boolean actualizar (List<CondicionPago> elementos)
   {
      boolean resultado = tabla.Update(elementos)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public boolean borrar (CondicionPago elemento)
   {
      boolean resultado = tabla.Delete(elemento)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public boolean borrar (List<CondicionPago> elementos)
   {
      boolean resultado = tabla.Delete(elementos)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public List<CondicionPago> obtenerLista ()
   {
      List<CondicionPago> resultado = tabla.Retrieve();
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public List<CondicionPago> obtenerLista (CondicionPago elemento)
   {
      List<CondicionPago> resultado = tabla.Retrieve(elemento);
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
     //---------------------------------------------------------------------
   public List<Valor> obtenerListaTiposCondicion ()
   {
      List<Valor> resultado = tiposCondicion.retrieveTiposCondicion();
      
      mensaje = tiposCondicion.getMessage();
      
      return resultado;
   }
   
    //---------------------------------------------------------------------
   public List<Valor> obtenerListaTiposCondicion (Valor elemento)
   {
      List<Valor> resultado = tiposCondicion.retrieveTiposCondicion(elemento);
      
      mensaje = tiposCondicion.getMessage();
      
      return resultado;
   }

   //---------------------------------------------------------------------
   public boolean recargar (CondicionPago elemento)
   {
      List<CondicionPago> resultado = tabla.Retrieve(elemento);
      if (resultado.size() > 0)
      {
         elemento.copy(resultado.get(0));
      }
      
      mensaje = tabla.getMessage();
      
      return tabla.getState() != CondicionesPagoDataAccess.DATASOURCE_ERROR;
   }
   
   
   //---------------------------------------------------------------------
   public boolean validar (CondicionPago elemento)
   {
      boolean esCorrecto = true;
      
      
      if (!elemento.getNombre().equals(CondicionPago.EMPTY_STRING))
      {
         esCorrecto = false;
         mensaje = "Es necesario especificar el nombre";
      }
      
      else if (elemento.getValor()!= CondicionPago.EMPTY_INT)
      {
         esCorrecto = false;
         mensaje = "Es necesario especificar el valor";
      }
      
    
      else if (elemento.getId_tipo_condicion()!=CondicionPago.EMPTY_INT)
      {
         esCorrecto = false;
         mensaje = "Es necesario especificar un tipo";
      }
      
      return esCorrecto;
   }
   
   //---------------------------------------------------------------------
   public boolean esCorrecto ()
   {
      return tabla.getState() != CondicionesPagoDataAccess.DATASOURCE_ERROR;
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
      tiposCondicion.setEschema(value);
   }
   
}
