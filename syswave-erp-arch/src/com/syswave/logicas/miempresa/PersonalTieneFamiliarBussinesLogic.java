package com.syswave.logicas.miempresa;

import com.syswave.datos.common.DataSourceManager;
import com.syswave.datos.miempresa.PersonalFamiliaDataAccess;
import com.syswave.entidades.miempresa_vista.PersonalTieneFamiliar;
import datalayer.api.IMediatorDataSource;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Carlos Soto
 */
public class PersonalTieneFamiliarBussinesLogic
{
   private PersonalFamiliaDataAccess tabla;
   private String mensaje;

   //--------------------------------------------------------------------
   public PersonalTieneFamiliarBussinesLogic()
   {
       IMediatorDataSource mysource = DataSourceManager.getMainDataSourceInstance();
      tabla = new PersonalFamiliaDataAccess(mysource);
   }

   //--------------------------------------------------------------------
   public PersonalTieneFamiliarBussinesLogic(String esquema)
   {
      this();
      tabla.setEschema(esquema);
   }

   //--------------------------------------------------------------------
   /**
    * Agrega el elemento especificado en una transaccion.
    */
   public boolean agregar(PersonalTieneFamiliar nuevo)
   {
      boolean resultado = tabla.Create(nuevo)>0;
      mensaje = tabla.getMessage();

      return resultado;
   }

   //--------------------------------------------------------------------
   /**
    * Agrega los elementos especificados en una misma transaccion.
    * @param nuevos
    * @return 
    */
   public boolean agregar(List<PersonalTieneFamiliar> nuevos)
   {
      boolean resultado = tabla.Create(nuevos)>0;
      mensaje = tabla.getMessage();

      return resultado;
   }

   //--------------------------------------------------------------------
   /**
    * Actualiza el elemento especificado en una transaccion.
    * @param elemento
    * @return 
    */
   public boolean actualizar(PersonalTieneFamiliar elemento)
   {
      boolean resultado = tabla.Update(elemento)>0;
      mensaje = tabla.getMessage();

      return resultado;
   }

   //--------------------------------------------------------------------
   /**
    * Actualiza todos los elementos especificados en una misma transaccion.
    * @param elementos
    * @return 
    */
   public boolean actualizar(List<PersonalTieneFamiliar> elementos)
   {
      boolean resultado = tabla.Update(elementos)>0;
      mensaje = tabla.getMessage();

      return resultado;
   }

   //---------------------------------------------------------------------
   /**
    * Borra el elemento especificado en una transaccion.
    * @param elemento
    * @return 
    */
   public boolean borrar(PersonalTieneFamiliar elemento)
   {
      boolean resultado = tabla.Delete(elemento)>0;
      mensaje = tabla.getMessage();

      return resultado;
   }

   //---------------------------------------------------------------------
   /**
    * Borra los elementos especificado en una sola transaccion.
    * @param elementos
    * @return 
    */
   public boolean borrar(List<PersonalTieneFamiliar> elementos)
   {
      boolean resultado = tabla.Delete(elementos)>0;
      mensaje = tabla.getMessage();

      return resultado;
   }

   //---------------------------------------------------------------------
   /**
    * Obtiene todos los elementos que cumplan con el criterio.
    * @param elemento
    * @return 
    */
   public List<PersonalTieneFamiliar> obtenerLista(PersonalTieneFamiliar elemento)
   {
      List<PersonalTieneFamiliar> resultado = tabla.Retrieve(elemento);

      mensaje = tabla.getMessage();

      return resultado;
   }

 
   //---------------------------------------------------------------------
   /**
    * Recarga la información del elemento especificado.
    * @param elemento
    * @return 
    */
   public boolean recargar(PersonalTieneFamiliar elemento)
   {
      List<PersonalTieneFamiliar> resultado = tabla.Retrieve(elemento);
      if (resultado.size() > 0)
      {
         elemento.copy(resultado.get(0));
      }

      mensaje = tabla.getMessage();

      return tabla.getState() != PersonalFamiliaDataAccess.DATASOURCE_ERROR;
   }

   //---------------------------------------------------------------------
   public boolean guardar(List<PersonalTieneFamiliar> elementos)
   {
      List<PersonalTieneFamiliar> nuevos = new ArrayList<>();
      List<PersonalTieneFamiliar> modificados = new ArrayList<>();

      for (PersonalTieneFamiliar actual : elementos)
      {
         if (actual.isNew())
            nuevos.add(actual);

         else if (actual.isModified())
            modificados.add(actual);
      }

      return (nuevos.isEmpty() || agregar(nuevos)) && (modificados.isEmpty() || actualizar(modificados));
   }

   //---------------------------------------------------------------------
   public boolean guardar(List<PersonalTieneFamiliar> elementos, List<PersonalTieneFamiliar> borrados)
   {
      List<PersonalTieneFamiliar> nuevos = new ArrayList<>();
      List<PersonalTieneFamiliar> modificados = new ArrayList<>();

      for (PersonalTieneFamiliar actual : elementos)
      {
         if (actual.isNew())
            nuevos.add(actual);

         else if (actual.isModified())
            modificados.add(actual);
      }

      if (borrados.size() > 0 || borrados.isEmpty() == false)
         borrar(borrados);

      return (nuevos.isEmpty() || agregar(nuevos)) && (modificados.isEmpty() || actualizar(modificados)) && (borrados == null || borrados.isEmpty()
                                                                                                             || borrar(borrados));
   }

   //---------------------------------------------------------------------
   public boolean esCorrecto()
   {
      return tabla.getState() != PersonalFamiliaDataAccess.DATASOURCE_ERROR;
   }

   //---------------------------------------------------------------------
   public String getMensaje()
   {
      return mensaje;
   }

   //---------------------------------------------------------------------
   public String getEsquema()
   {
      return tabla.getEschema();
   }

   //---------------------------------------------------------------------
   public void setEsquema(String value)
   {
      tabla.setEschema(value);
   }
}