package com.syswave.logicas.miempresa;

import com.syswave.datos.common.DataSourceManager;
import com.syswave.datos.miempresa.MonedaCambioDataAccess;
import com.syswave.datos.miempresa.MonedaCambioRetrieve;
import com.syswave.entidades.miempresa.MonedaCambio;
import com.syswave.entidades.miempresa_vista.MonedaCambioVista;
import datalayer.api.IMediatorDataSource;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class MonedaCambioBusinessLogic
{
    private String mensaje;
    private MonedaCambioDataAccess tabla;
    private MonedaCambioRetrieve vista;
    
   //---------------------------------------------------------------------
    public MonedaCambioBusinessLogic(){
        IMediatorDataSource mysource = DataSourceManager.getMainDataSourceInstance();
        tabla = new MonedaCambioDataAccess(mysource);
        vista = new MonedaCambioRetrieve(mysource);
    }
    
   //---------------------------------------------------------------------
    public MonedaCambioBusinessLogic(String esquema)
    {
      this();
      tabla.setEschema(esquema);
      vista.setEschema(esquema);
    }
    
   //---------------------------------------------------------------------
    public boolean agregar (MonedaCambio nuevo)
   {
      boolean resultado = tabla.Create(nuevo)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
    
   //---------------------------------------------------------------------
    public boolean agregar (List<MonedaCambio> nuevos)
   {
      boolean resultado = tabla.Create(nuevos)>0;
      mensaje = tabla.getMessage();
     
      return resultado;
   }
    
    //---------------------------------------------------------------------
   public boolean actualizar (MonedaCambio elemento)
   {
      boolean resultado =  tabla.Update(elemento)>0;
      mensaje = tabla.getMessage();
         
      return resultado;
   }
   
    //---------------------------------------------------------------------
  public boolean actualizar (List<MonedaCambio> elementos)
   {
      boolean resultado = tabla.Update(elementos)>0;
      mensaje = tabla.getMessage();
       
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public boolean borrar (MonedaCambio elemento)
   {
      boolean resultado = tabla.Delete(elemento)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public boolean borrar (List<MonedaCambio> elementos)
   {
      boolean resultado = tabla.Delete(elementos)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public List<MonedaCambio> obtenerLista ()
   {
      List<MonedaCambio> resultado = tabla.Retrieve();
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public List<MonedaCambio> obtenerLista (MonedaCambio elemento)
   {
      List<MonedaCambio> resultado = tabla.Retrieve(elemento);
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public List<MonedaCambioVista> obtenerUltimosTiposCambio (boolean hoy)
   {
       List<MonedaCambioVista> resultado = vista.ultimosTiposCambio(hoy);
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
  
   //---------------------------------------------------------------------
   /**
    * Determina a través de los tipos de cambios como convertir de una moneda a otra.
    * @param datos Especificación de origen (1) y destino (N)
    * @param tiposCambio Tipos de cambios disponibles para la conversión.
    * @return La proporción necesaria para realizar la conversión de una moneda a otra.
    */
   public float obtenerProporcionRequerida (MonedaCambioVista datos, List<MonedaCambioVista> tiposCambio)
   {
      int i = 0, origen = datos.getIdMonedaOrigen(), destino = datos.getIdMonedaDestino()/*, contador = 0*/;
      boolean seEncontro = false, directo = true;
      MonedaCambioVista tipo;
      List<replica_valores> pendientes = new ArrayList<>();
      //float factor = 0.0F;
      
      datos.setProporcion(1.0F);
      
      while ( !seEncontro && i < tiposCambio.size())
      {
           tipo = tiposCambio.get(i);
           //contador ++;
           
           if (directo )
           {
              //Buscamos una conversión directa.
              if (tipo.getIdMonedaDestino() == destino && tipo.getIdMonedaOrigen() == origen)
              {
                 datos.setProporcion(tipo.getProporcion() * datos.getProporcion());
                 seEncontro = true;
              }
              
              //Si existe un registro con los valores invertidos.
              else if (tipo.getIdMonedaDestino() == origen && tipo.getIdMonedaOrigen() == destino)
              {
                 //Nota: Se invierte el númerador y el denominador.
                 datos.setProporcion( datos.getProporcion() / tipo.getProporcion() );
                 seEncontro = true;
              }
        
              //Intentemos buscando de forma indirecta antes de darse por vencidos.
              else if (i + 1 == tiposCambio.size())
              {
                 if (pendientes.size() > 0)
                 {
                     replica_valores aux = pendientes.remove(pendientes.size() - 1);// pop
                     i = aux.getI();
                     origen = aux.getOrigen();
                     destino = aux.getDestino();
                     datos.setProporcion(aux.getProporcion()); 
                 }
                 else             
                   i = 0;
                 directo = false;
              }
              
              else
                  i++;
           }
           
           else 
           {
              //Probando buscando a través de un camino indirecto.
              if (tipo.getIdMonedaOrigen()== destino)
              {
                 pendientes.add(new replica_valores(++i, origen, destino, datos.getProporcion()));  
                 i = 0;
                 directo = true;
                 destino = tipo.getIdMonedaDestino(); //Próximo destino
                 datos.setProporcion (   datos.getProporcion() / tipo.getProporcion()   );
                 //mensaje = "Para completar la operación es necesario " + datos.getOrigen()+ " a "+ tipo.getDestino();
              }  
              
              else if (tipo.getIdMonedaDestino()== destino)
              {
                 pendientes.add(new replica_valores(++i, origen, destino, datos.getProporcion()));
                 i = 0;
                 directo = true;
                 destino = tipo.getIdMonedaOrigen(); //Próximo destino
                 datos.setProporcion (  tipo.getProporcion() * datos.getProporcion()    );                                
                 //mensaje = "Para completar la operación es necesario " + datos.getDestino()+ " a "+ tipo.getOrigen();
              }       
              
              //Se siguió un camino equivocado, es necesario regresarnos a la ruta que estabamos siguiendo.
              else if (i + 1 == tiposCambio.size() && pendientes.size() > 0)
              {
                 replica_valores aux = pendientes.remove(pendientes.size() - 1);// pop
                 i = aux.getI();
                 origen = aux.getOrigen();
                 destino = aux.getDestino();
                 datos.setProporcion(aux.getProporcion()); 
              }
              
              else
                  i++;
           }
      }
      
      if (!seEncontro)
      {
         datos.setProporcion(0.0F);
         //mensaje = "No se encontro ningun tipo de cambio donde se utilice " + datos.getIdMonedaDestino();
      }
      
      pendientes.clear();
      
     /* if (seEncontro)
         factor = 1.0F / datos.getProporcion() ;
      
      return factor;*/
      
      return datos.getProporcion();
   }
   //---------------------------------------------------------------------
   public boolean recargar (MonedaCambio elemento)
   {
      List<MonedaCambio> resultado = tabla.Retrieve(elemento);
      if (resultado.size() > 0)
      {
         elemento.copy(resultado.get(0));
      }
      
      mensaje = tabla.getMessage();
      
      return tabla.getState() != MonedaCambioDataAccess.DATASOURCE_ERROR;
   }
   
   
   //---------------------------------------------------------------------
   public boolean validar (MonedaCambio elemento)
   {
      boolean esCorrecto = true;
      
      if (elemento.getIdMonedaOrigen()!=MonedaCambio.EMPTY_INT)
      {
         esCorrecto = false;
         mensaje = "Es necesario especificar una moneda de origen";
      }
      
      if (elemento.getIdMonedaDestino()!=MonedaCambio.EMPTY_INT)
      {
         esCorrecto = false;
         mensaje = "Es necesario especificar una moneda de destino";
      }
           
      return esCorrecto;
   }
   
   //---------------------------------------------------------------------
   public boolean esCorrecto ()
   {
      return tabla.getState() != MonedaCambioDataAccess.DATASOURCE_ERROR; 
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
   public boolean guardar(List<MonedaCambioVista> elementos)
   {
      List<MonedaCambio> nuevos = new ArrayList<>();
      List<MonedaCambio> modificados = new ArrayList<>();
      
      for (MonedaCambioVista actual : elementos)
      {
         if (actual.isNew())
            nuevos.add(actual);
   
         else if (actual.isModified())
            modificados.add(actual);         
      }
         
      return (nuevos.isEmpty() || agregar(nuevos)) && 
             (modificados.isEmpty() || actualizar(modificados))/* &&               
             (borrados == null || borrados.isEmpty() || borrar(borrados))*/;
   }
   
   //---------------------------------------------------------------------
   //-- Esta clase auxiliar sirve para realizar búsquedas de tipo de cambio----
   //---------------------------------------------------------------------
   
   private class replica_valores
   {
      private int i,origen, destino;
      private float proporcion;
      
      public replica_valores(int i_actual, int origen_actual, int destino_actual, float proporcion_actual)
      {
         i = i_actual;
         origen = origen_actual;
         destino = destino_actual;
         proporcion = proporcion_actual;
      }

      public int getI()
      {
         return i;
      }
      
      public int getOrigen()
      {
         return origen;
      }

      public int getDestino()
      {
         return destino;
      }

      public float getProporcion()
      {
         return proporcion;
      }
      
      
   }
}
