package com.syswave.entidades.miempresa_vista;

import com.syswave.entidades.miempresa.Persona;

/**
 * Estructura formada entre Persona y todos sus niveles de detalle como columnas.
 * @author Victor Manuel Bucio Vargas
 */
public class PersonaDetalladaVista extends Persona
{
   private String identificador_muestra, medio_muestra, direccion_muestra;
   
   //---------------------------------------------------------------------
   public PersonaDetalladaVista()
   {
     initAtributes ();
   }
   
   //---------------------------------------------------------------------
   private void initAtributes ()
   {
       identificador_muestra = EMPTY_STRING;
      medio_muestra = EMPTY_STRING;
      direccion_muestra = EMPTY_STRING;
   }

   //---------------------------------------------------------------------
         /**
    * Obtiene un texto que es un ejemplo de los identificadores asociados a esta persona.
    */
   public String getIdentificador_muestra()
   {
      return identificador_muestra;
   }

    //---------------------------------------------------------------------
      /**
    * Establece un texto que es un ejemplo de los identificadores asociados a esta persona.
    */
   public void setIdentificador_muestra(String value)
   {
      this.identificador_muestra = value;
   }

    //---------------------------------------------------------------------
      /**
    * Obtiene un texto que es un ejemplo de los medios de contacto asociados a esta persona.
    */
   public String getMedio_muestra()
   {
      return medio_muestra;
   }

    //---------------------------------------------------------------------
   /**
    * Establece un texto que es un ejemplo de los medios de contacto asociados a esta persona.
    */
   public void setMedio_muestra(String value)
   {
      this.medio_muestra = value;
   }

  //---------------------------------------------------------------------
   /**
    * Obtiene un texto que es un ejemplo de las direcciones asociadas a esta persona.
    */
   public String getDireccion_muestra()
   {
      return direccion_muestra;
   }

    //---------------------------------------------------------------------
   /**
    * Establece un texto que es un ejemplo de las direcciones asociadas a esta persona.
    * @param value El valor a mostrar
    */
   public void setDireccion_muestra(String value)
   {
      this.direccion_muestra = value;
   }
}
