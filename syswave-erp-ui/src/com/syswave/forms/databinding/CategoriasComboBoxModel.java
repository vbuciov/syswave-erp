package com.syswave.forms.databinding;

import com.syswave.swing.models.POJOComboBoxModel;
import com.syswave.entidades.miempresa.Categoria;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class CategoriasComboBoxModel extends POJOComboBoxModel<Categoria>
{
   //---------------------------------------------------------------------
   @Override
   public String onGetText(Categoria item)
   {
     return item.getNombre();
   }

   //---------------------------------------------------------------------
   @Override
   public void onSetText(Categoria item, String value)
   {
      item.setNombre(value);
   }

   //---------------------------------------------------------------------
   @Override
   public Object onGetValue(Categoria item)
   {
    return item.getId();
   }
   
}
