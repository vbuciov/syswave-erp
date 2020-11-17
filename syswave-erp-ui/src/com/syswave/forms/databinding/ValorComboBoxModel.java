package com.syswave.forms.databinding;

import com.syswave.swing.models.POJOComboBoxModel;
import com.syswave.entidades.miempresa.Valor;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class ValorComboBoxModel extends POJOComboBoxModel<Valor>
{
   //---------------------------------------------------------------------
   @Override
   public String onGetText(Valor item)
   {
      return item.getDescripcion();
   }

   //---------------------------------------------------------------------
   @Override
   public void onSetText(Valor item, String value)
   {
     item.setDescripcion(value);
   }

   //---------------------------------------------------------------------
   @Override
   public Object onGetValue(Valor item)
   {
      return item.getId();
   }  
}