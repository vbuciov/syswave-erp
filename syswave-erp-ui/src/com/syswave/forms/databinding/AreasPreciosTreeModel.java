package com.syswave.forms.databinding;

import com.syswave.entidades.miempresa.AreaPrecio;
import com.syswave.swing.models.POJOTreeModel;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class AreasPreciosTreeModel extends POJOTreeModel<AreaPrecio>
{

   @Override
   public String onGetText(AreaPrecio item)
   {
      return item.getDescripcion();
   }

   @Override
   public void onSetText(AreaPrecio item, String value)
   {
      item.setDescripcion(value);
   }

   @Override
   public Object onGetIDValue(AreaPrecio item)
   {
      return item.getId();
   }

   @Override
   public boolean onGetChecked(AreaPrecio element)
   {
      return element.isSelected();
   }

   @Override
   public void onSetChecked(AreaPrecio element, boolean value)
   {
      element.setSelected(value);
   }

   @Override
   public Object onGetIDParentValue(AreaPrecio element)
   {
      return element.getIdPadre();
   }

   @Override
   public int onGetLevel(AreaPrecio element)
   {
      return element.getNivel();
   }

   @Override
   public void onModifiedAdded(AreaPrecio element)
   {
      element.setModified();
   }

   @Override
   public boolean canAddToModified(AreaPrecio element)
   {
      return !element.isSet();
   }
   
   
    @Override
    public Object getNullParent()
    {
        return 0;
    }
}
