package com.syswave.forms.databinding;

import com.syswave.entidades.miempresa_vista.Personal;
import com.syswave.swing.models.POJOComboBoxModel;

/**
 * 
 * @author Victor Manuel Bucio Vargas
 */
public class PersonalComboBoxModel extends POJOComboBoxModel<Personal>
{

   @Override
   public String onGetText(Personal item)
   {
     return item.getNombreCompleto();
   }

   @Override
   public void onSetText(Personal item, String value)
   {
      /*String[] parts = value.split(" ");
      
      if (parts)*/
      item.setNombres(value);
   }

   @Override
   public Object onGetValue(Personal item)
   {
      return item.getIdPersona();
   }
}
