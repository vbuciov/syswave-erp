package com.syswave.forms.databinding;

import com.syswave.swing.models.POJOComboBoxModel;
import com.syswave.entidades.miempresa.Ubicacion;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class UbicacionesComboBoxModel extends POJOComboBoxModel<Ubicacion>
{
   //---------------------------------------------------------------------
   @Override
   public String onGetText(Ubicacion item)
   {
      return item.getNombre();
   }

   //---------------------------------------------------------------------
   @Override
   public void onSetText(Ubicacion item, String value)
   {
      item.setNombre(value);
   }

   //---------------------------------------------------------------------
   @Override
   public Object onGetValue(Ubicacion item)
   {
      return item.getId();
   }
}
