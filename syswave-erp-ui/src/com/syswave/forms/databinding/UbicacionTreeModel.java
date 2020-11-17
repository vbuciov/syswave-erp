package com.syswave.forms.databinding;

import com.syswave.swing.models.POJOTreeModel;
import com.syswave.entidades.miempresa.Ubicacion;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class UbicacionTreeModel extends POJOTreeModel<Ubicacion>
{
   //---------------------------------------------------------------------
   @Override
   public String onGetText(Ubicacion element)
   {
      return element.getNombre();
   }

   //---------------------------------------------------------------------
   @Override
   public void onSetText(Ubicacion item, String value)
   {
      item.setNombre(value);
   }

   //---------------------------------------------------------------------
   @Override
   public boolean onGetChecked(Ubicacion element)
   {
      return element.isSelected();
   }
   
    //---------------------------------------------------------------------
   @Override
   public void onSetChecked(Ubicacion element, boolean value)
   {
      element.setSelected(value);
   }

   //---------------------------------------------------------------------
   @Override
   public Object onGetIDParentValue(Ubicacion element)
   {
      return element.getIdPadre();
   }

   //---------------------------------------------------------------------
   @Override
   public Object onGetIDValue(Ubicacion element)
   {
      return element.getId();
   }

   //---------------------------------------------------------------------
   @Override
   public boolean canAddToModified(Ubicacion element)
   {
      return !element.isSet();
   }

   //---------------------------------------------------------------------
   @Override
   public void onModifiedAdded(Ubicacion element)
   {
      element.setModified();
   }

   //---------------------------------------------------------------------
   @Override
   public int onGetLevel(Ubicacion element)
   {
      return element.getNivel();
   }
   
    @Override
    public Object getNullParent()
    {
        return 0;
    }
}