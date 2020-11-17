package com.syswave.forms.databinding;

import com.syswave.swing.models.POJOComboBoxModel;
import com.syswave.entidades.miempresa.Persona;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class PersonasComboBoxModel extends POJOComboBoxModel<Persona>
{

   @Override
   public String onGetText(Persona item)
   {
     return item.getNombreCompleto();
   }

   @Override
   public void onSetText(Persona item, String value)
   {
      /*String[] parts = value.split(" ");
      
      if (parts)*/
      item.setNombres(value);
   }

   @Override
   public Object onGetValue(Persona item)
   {
      return item.getId();
   }
   
}
