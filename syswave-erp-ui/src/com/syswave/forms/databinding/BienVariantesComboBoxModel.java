package com.syswave.forms.databinding;

import com.syswave.swing.models.POJOComboBoxModel;
import com.syswave.entidades.miempresa.BienVariante;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class BienVariantesComboBoxModel extends POJOComboBoxModel<BienVariante>
{
   //---------------------------------------------------------------------
   @Override
   public String onGetText(BienVariante item)
   {
      return item.getDescripcion();
   }

   //---------------------------------------------------------------------
   @Override
   public void onSetText(BienVariante item, String value)
   {
      item.setDescripcion(value);
   }

   //---------------------------------------------------------------------
   @Override
   public Object onGetValue(BienVariante item)
   {
     return item.getId();
   }
}
