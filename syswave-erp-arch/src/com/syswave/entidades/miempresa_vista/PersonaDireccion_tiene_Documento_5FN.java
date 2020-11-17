package com.syswave.entidades.miempresa_vista;

import com.syswave.entidades.miempresa.PersonaDireccion;
import com.syswave.entidades.miempresa.PersonaDireccion_tiene_Documento;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class PersonaDireccion_tiene_Documento_5FN extends PersonaDireccion_tiene_Documento
{
   public String nombre_rol, nombres, tipoPersona, localidad;
   private PersonaDireccion fk_documento_persona_direccion_id;
   
   public PersonaDireccion_tiene_Documento_5FN()
   {
      nombre_rol = EMPTY_STRING;
      nombres = EMPTY_STRING;
      tipoPersona = EMPTY_STRING;
      localidad = EMPTY_STRING;
      fk_documento_persona_direccion_id = super.getHasOnePersonaDireccion();
   }
   
   //---------------------------------------------------------------------
   public String getRol ()
   {
      return nombre_rol;
   }
  
   //---------------------------------------------------------------------
   public void setRol (String value)
   {
      nombre_rol = value;
   }
  
   //---------------------------------------------------------------------
   public String getNombre()
   {
      return nombres;
   }

   //---------------------------------------------------------------------
   public void setNombre(String value)
   {
     nombres = value;
   }

   //---------------------------------------------------------------------
   public String getTipo_persona()
   {
       return tipoPersona;
   }

   //---------------------------------------------------------------------
   public void setTipo_persona(String value)
   {
       tipoPersona = value;
   }
   
   //---------------------------------------------------------------------
   public String getCodigoPostal ()
   {
      return fk_documento_persona_direccion_id.getCodigoPostal();
   }
   
   //---------------------------------------------------------------------
   public void setCodigoPostal (String value)
   {
      fk_documento_persona_direccion_id.setCodigoPostal(value);
   }
   
   //---------------------------------------------------------------------
   public String getLocalidad ()
   {
      return localidad;
   }
   
   //---------------------------------------------------------------------
   public void setLocalidad (String value)
   {
      localidad = value;
   }
   
   //---------------------------------------------------------------------
   public String getCalle ()
   {
      return fk_documento_persona_direccion_id.getCalle();
   }
   
   //---------------------------------------------------------------------
   public void setCalle (String value)
   {
      fk_documento_persona_direccion_id.setCalle(value);
   }
   
   //---------------------------------------------------------------------
   public String getColonia ()
   {
      return fk_documento_persona_direccion_id.getColonia();
   }
   
   //---------------------------------------------------------------------
   public void setColonia (String value)
   {
      fk_documento_persona_direccion_id.setColonia(value);
   }
   
   //---------------------------------------------------------------------
   public String getNoExterior ()
   {
      return fk_documento_persona_direccion_id.getNoExterior();
   }
   
   //---------------------------------------------------------------------
    public void setNoExterior (String value)
   {
      fk_documento_persona_direccion_id.setNoExterior(value);
   }
    
   //---------------------------------------------------------------------
    public String getNoInterior()
   {
      return fk_documento_persona_direccion_id.getNoInterior();
   }
   
   //---------------------------------------------------------------------
   public void setNoInterior (String value)
   {
      fk_documento_persona_direccion_id.setNoInterior(value);
   }
   
    //---------------------------------------------------------------------
   public String getIdDireccion ()
   {
      return fk_documento_persona_direccion_id.getCompositeKey();
   }
}