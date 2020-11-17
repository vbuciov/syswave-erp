package com.syswave.forms.databinding;

import com.syswave.swing.models.POJOComboBoxModel;
import com.syswave.entidades.configuracion.Localidad;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class LocalidadComboBoxModel extends POJOComboBoxModel<Localidad>
{
   //---------------------------------------------------------------------
   @Override
   public String onGetText(Localidad item)
   {
      return item.getNombre();
   }

   //---------------------------------------------------------------------
   @Override
   public void onSetText(Localidad item, String value)
   {
      item.setNombre(value);
   }

   //---------------------------------------------------------------------
   @Override
   public Object onGetValue(Localidad item)
   {
      return item.getId();
   }  
}