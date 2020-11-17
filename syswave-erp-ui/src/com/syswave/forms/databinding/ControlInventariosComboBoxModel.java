package com.syswave.forms.databinding;

import com.syswave.swing.models.POJOComboBoxModel;
import com.syswave.entidades.miempresa.ControlInventario;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class ControlInventariosComboBoxModel extends POJOComboBoxModel<ControlInventario>
{
   //---------------------------------------------------------------------
   @Override
   public String onGetText(ControlInventario item)
   {
      return item.getLote();
   }

   //---------------------------------------------------------------------
   @Override
   public void onSetText(ControlInventario item, String value)
   {
      item.setLote(value);
   }

   //---------------------------------------------------------------------
   @Override
   public Object onGetValue(ControlInventario item)
   {
      return item.getCompositeKey();
   }
   
}
