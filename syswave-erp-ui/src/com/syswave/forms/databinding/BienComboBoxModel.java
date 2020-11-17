package com.syswave.forms.databinding;

import com.syswave.swing.models.POJOComboBoxModel;
import com.syswave.entidades.miempresa.Bien;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class BienComboBoxModel extends POJOComboBoxModel<Bien>
{

   //---------------------------------------------------------------------
   @Override
   public String onGetText(Bien item)
   {
      return item.getNombre();
   }

   //---------------------------------------------------------------------
   @Override
   public void onSetText(Bien item, String value)
   {
      item.setNombre(value);
   }

   //---------------------------------------------------------------------
   @Override
   public Object onGetValue(Bien item)
   {
      return item.getId();
   }
}
