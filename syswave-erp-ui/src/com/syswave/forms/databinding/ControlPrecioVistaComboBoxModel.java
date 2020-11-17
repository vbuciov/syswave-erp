package com.syswave.forms.databinding;

import com.syswave.entidades.miempresa_vista.ControlPrecioVista;
import com.syswave.swing.models.POJOComboBoxModel;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class ControlPrecioVistaComboBoxModel extends POJOComboBoxModel<ControlPrecioVista>
{
   //---------------------------------------------------------------------
   @Override
   public String onGetText(ControlPrecioVista item)
   {
      return item.getPresentacion();
   }

   //---------------------------------------------------------------------
   @Override
   public void onSetText(ControlPrecioVista item, String value)
   {
      item.setPresentacion(value);
   }

   //---------------------------------------------------------------------
   @Override
   public Object onGetValue(ControlPrecioVista item)
   {
     return item.getId();
   }
}
