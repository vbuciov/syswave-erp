package com.syswave.logicas.miempresa;

import com.syswave.datos.common.DataSourceManager;
import com.syswave.datos.miempresa.VarianteIdentificadoresDataAccess;
import com.syswave.datos.miempresa.VarianteIdentificadoresRetrieve;
import com.syswave.entidades.miempresa.VarianteIdentificador;
import com.syswave.entidades.miempresa_vista.VarianteIdentificadorVista;
import datalayer.api.IMediatorDataSource;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class VarianteIdentificadoresBusinessLogic
{
     private String mensaje;
   private VarianteIdentificadoresDataAccess tabla;
   private VarianteIdentificadoresRetrieve vista;
   
   //---------------------------------------------------------------------
   public VarianteIdentificadoresBusinessLogic()
   {
       IMediatorDataSource mysource = DataSourceManager.getMainDataSourceInstance();
      tabla = new VarianteIdentificadoresDataAccess(mysource);
      vista = new VarianteIdentificadoresRetrieve(mysource);
   }

   //---------------------------------------------------------------------
   public VarianteIdentificadoresBusinessLogic(String Esquema)
   {
     this();
      tabla.setEschema(Esquema);
       vista.setEschema(Esquema);
   }
   
   //---------------------------------------------------------------------
   /**
    * Agrega un elemento nuevo 
    * @param nuevo El elemento a agregar
    * @return Indica si la operación se llevo acabo correctamente.
    */
   public boolean agregar (VarianteIdentificador nuevo)
   {
      boolean resultado = tabla.Create(nuevo)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
      //---------------------------------------------------------------------
   /**
    * Agrega una lista de elementos nuevos
    * @param nuevos
    * @return Indica si la operación se llevo acabo correctamente.
    */
   public boolean agregar (List<VarianteIdentificador> nuevos)
   {
      boolean resultado = tabla.Create(nuevos)>0;
      mensaje = tabla.getMessage();

      return resultado;
   }
   
   //---------------------------------------------------------------------
   /**
    * Actualiza la información del elemento recibido
    * @param elemento 
    * @return Indica si la operación se llevo acabo correctamente..
   */
   public boolean actualizar (VarianteIdentificador elemento)
   {
      boolean resultado =  tabla.Update(elemento)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   /**
    * Actualiza la información de la lista de elementos recibidos.
    * @param elementos
    * @return Indica si la operación se llevo acabo correctamente.
    */
   public boolean actualizar (List<VarianteIdentificador> elementos)
   {
      boolean resultado = tabla.Update(elementos)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   /**
    * Borra el elemento recibido
    * @param elemento
    * @return Indica si la operación se realizó correctamente
    */
   public boolean borrar (VarianteIdentificador elemento)
   {
      boolean resultado = tabla.Delete(elemento)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   /**
    * Borra el elemento recibido
    * @param elementos
    * @return Indica si la operación se realizó correctamente
    */
   public boolean borrar (List<VarianteIdentificador> elementos)
   {
      boolean resultado = tabla.Delete(elementos)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   /**
    * Obtiene la información existente de modulos instalados.
    * @return Una lista con todos los elementos.
    */
   public List<VarianteIdentificador> obtenerLista ()
   {
      List<VarianteIdentificador> resultado = tabla.Retrieve();
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   /**
    * Obtiene aquellos elementos que coinciden en información con el elemento recibido
    * @param elemento
    * @return Una lista con los elementos.
    */
    public List<VarianteIdentificador> obtenerLista (VarianteIdentificador elemento)
   {
      List<VarianteIdentificador> resultado = tabla.Retrieve(elemento);
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   /**
    * Obtiene aquellos elementos que coinciden en información con el elemento recibido
    * @param id_variante
    * @return Una lista con los elementos.
    */
    public List<VarianteIdentificadorVista> obtenerListaVista (int id_variante)
   {
      List<VarianteIdentificadorVista> resultado = vista.Retrieve(id_variante);
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
    
    //---------------------------------------------------------------------
   /**
    * Recarga la información del elemento recibido.
    * @param elemento
    * @return Indica si la operación se llevo correctamente.
    */
   public boolean recargar (VarianteIdentificador elemento)
   {
      List<VarianteIdentificador> resultado = tabla.Retrieve(elemento);
      if (resultado.size() > 0)
      {
         elemento.copy(resultado.get(0));
      }
      
      mensaje = tabla.getMessage();
      
      return tabla.getState() != VarianteIdentificadoresDataAccess.DATASOURCE_ERROR;
   }
   
   //---------------------------------------------------------------------
   public boolean valirdar (VarianteIdentificador elemento)
   {
      boolean esCorrecto = true;
      
      if (elemento.getIdVariante()!= VarianteIdentificador.EMPTY_INT && elemento.getIdTipoIdentificador()!= VarianteIdentificador.EMPTY_INT)
      {
         esCorrecto = false;
         mensaje = "Es necesario especificar el elemento a relacionar";
      }
      
      return esCorrecto;
   }
   
   //---------------------------------------------------------------------
   public boolean esCorrecto ()
   {
      return tabla.getState() != VarianteIdentificadoresDataAccess.DATASOURCE_ERROR;
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
   public boolean guardar(List<VarianteIdentificadorVista> elementos)
   {
      List<VarianteIdentificador> nuevos = new ArrayList<>();
      List<VarianteIdentificador> modificados = new ArrayList<>();
       List<VarianteIdentificador> borrados = new ArrayList<>();
      
      for (VarianteIdentificador actual : elementos)
      {
         if (actual.isNew())
            nuevos.add(actual);

         else if (actual.isModified())
         {
            if (!actual.getValor().isEmpty())
               modificados.add(actual);
            
            else
               borrados.add(actual);
         }
      }
         
      return (nuevos.isEmpty() || agregar(nuevos)) && (modificados.isEmpty() || actualizar(modificados)) &&  (borrados.isEmpty() || borrar(borrados));
  
   }  
}