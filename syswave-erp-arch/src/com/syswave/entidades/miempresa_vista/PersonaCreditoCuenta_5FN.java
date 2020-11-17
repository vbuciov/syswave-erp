package com.syswave.entidades.miempresa_vista;

import com.syswave.entidades.miempresa.Persona;
import com.syswave.entidades.miempresa.PersonaCreditoCuenta;

/**
 *
 * @author victor
 */
public class PersonaCreditoCuenta_5FN extends PersonaCreditoCuenta
{
   Persona fk_persona_tiene_saldos_id_persona;
    
   public PersonaCreditoCuenta_5FN(){
       super();
       fk_persona_tiene_saldos_id_persona = super.getHasOnePersona();
   }
    
   public int getIdTipoPersona ()
   {
      return fk_persona_tiene_saldos_id_persona.getId_tipo_persona();
   }
   
   public void setIdTipoPersona(int value)
   {
      fk_persona_tiene_saldos_id_persona.setId_tipo_pesrona(value);
   }
   
   public void setNombres(String value)
   {
      fk_persona_tiene_saldos_id_persona.setNombres(value);
   }
   
   public String getNombres ()
   {
     return fk_persona_tiene_saldos_id_persona.getNombres();
   }
   
   public void setApellidos(String value)
   {
      fk_persona_tiene_saldos_id_persona.setApellidos(value);
   }
   
   public String getApellidos ()
   {
      return fk_persona_tiene_saldos_id_persona.getApellidos();
   }
   
   public String getNombreCompleto()
   {
      return fk_persona_tiene_saldos_id_persona.getNombreCompleto();
   }
}