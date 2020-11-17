package com.syswave.forms.databinding;

import com.syswave.entidades.miempresa.Puesto;
import com.syswave.swing.models.POJOTreeModel;

/**
 *
 * @author sis5
 */
public class PuestosTreeModel  extends POJOTreeModel<Puesto>
{

    @Override
   public String onGetText(Puesto item)
   {
      return item.getNombre();
   }

   @Override
   public void onSetText(Puesto item, String value)
   {
      item.setNombre(value);
   }

   @Override
   public Object onGetIDValue(Puesto item)
   {
      return item.getId();
   }

   @Override
   public boolean onGetChecked(Puesto element)
   {
      return element.isSelected();
   }

   @Override
   public void onSetChecked(Puesto element, boolean value)
   {
      element.setSelected(value);
   }

   @Override
   public Object onGetIDParentValue(Puesto element)
   {
      return element.getIdPadre();
   }

   @Override
   public int onGetLevel(Puesto element)
   {
      return element.getNivel();
   }

   @Override
   public void onModifiedAdded(Puesto element)
   {
      element.setModified();
   }

   @Override
   public boolean canAddToModified(Puesto element)
   {
      return !element.isSet();
   }
   
   
    @Override
    public Object getNullParent()
    {
        return 0;
    }
    
}
