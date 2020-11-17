package com.syswave.forms.databinding;

import com.syswave.swing.models.POJOComboBoxModel;
import com.syswave.entidades.miempresa.Moneda;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class MonedasComboBoxModel extends POJOComboBoxModel<Moneda>
{
   //---------------------------------------------------------------------
   @Override
   public String onGetText(Moneda item)
   {
      return item.getSiglas();
   }

   //---------------------------------------------------------------------
   @Override
   public void onSetText(Moneda item, String value)
   {
      item.setSiglas(value);
   }

   //---------------------------------------------------------------------
   @Override
   public Object onGetValue(Moneda item)
   {
      return item.getId();
   }  
}