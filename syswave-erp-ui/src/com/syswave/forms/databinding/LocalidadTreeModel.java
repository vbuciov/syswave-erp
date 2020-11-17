package com.syswave.forms.databinding;

import com.syswave.swing.models.POJOTreeModel;
import com.syswave.entidades.configuracion.Localidad;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class LocalidadTreeModel extends POJOTreeModel<Localidad>
{
   //---------------------------------------------------------------------
   @Override
   public String onGetText(Localidad element)
   {
      return element.getNombre();
   }

   //---------------------------------------------------------------------
   @Override
   public void onSetText(Localidad item, String value)
   {
      item.setNombre(value);
   }

   //---------------------------------------------------------------------
   @Override
   public boolean onGetChecked(Localidad element)
   {
      return element.isSelected();
   }
   
    //---------------------------------------------------------------------
   @Override
   public void onSetChecked(Localidad element, boolean value)
   {
      element.setSelected(value);
   }

   //---------------------------------------------------------------------
   @Override
   public Object onGetIDParentValue(Localidad element)
   {
      return element.getIdPadre();
   }

   //---------------------------------------------------------------------
   @Override
   public Object onGetIDValue(Localidad element)
   {
      return element.getId();
   }

   //---------------------------------------------------------------------
   @Override
   public boolean canAddToModified(Localidad element)
   {
      return !element.isSet();
   }

   //---------------------------------------------------------------------
   @Override
   public void onModifiedAdded(Localidad element)
   {
      element.setModified();
   }

   //---------------------------------------------------------------------
   @Override
   public int onGetLevel(Localidad element)
   {
      return element.getNivel();
   }

    @Override
    public Object getNullParent()
    {
        return 0;
    }
}