package com.syswave.forms.databinding;

import com.syswave.swing.models.POJOComboBoxModel;
import com.syswave.entidades.configuracion.Unidad;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class UnidadComboBoxModel extends POJOComboBoxModel<Unidad>
{
   //---------------------------------------------------------------------
   @Override
   public String onGetText(Unidad item)
   {
      return item.getNombre();
   }

   //---------------------------------------------------------------------
   @Override
   public void onSetText(Unidad item, String value)
   {
      item.setNombre(value);
   }

   //---------------------------------------------------------------------
   @Override
   public Object onGetValue(Unidad item)
   {
      return item.getId();
   }
}
