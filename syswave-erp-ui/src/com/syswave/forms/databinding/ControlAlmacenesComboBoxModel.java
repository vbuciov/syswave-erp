package com.syswave.forms.databinding;

import com.syswave.swing.models.POJOComboBoxModel;
import com.syswave.entidades.miempresa.ControlAlmacen;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class ControlAlmacenesComboBoxModel extends POJOComboBoxModel<ControlAlmacen>
{

   //---------------------------------------------------------------------
   @Override
   public String onGetText(ControlAlmacen item)
   {
     return item.getSerie();
   }

   //---------------------------------------------------------------------
   @Override
   public void onSetText(ControlAlmacen item, String value)
   {
      item.setSerie(value);
   }

   //---------------------------------------------------------------------
   @Override
   public Object onGetValue(ControlAlmacen item)
   {
      return item.getCompositeKey();
   }
   
}
