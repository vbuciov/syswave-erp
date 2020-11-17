package com.syswave.forms.databinding;

import com.syswave.swing.models.POJOTreeModel;
import com.syswave.entidades.miempresa.Categoria;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class CategoriasTreeModel extends POJOTreeModel<Categoria>
{

   @Override
   public String onGetText(Categoria item)
   {
      return item.getNombre();
   }

   @Override
   public void onSetText(Categoria item, String value)
   {
      item.setNombre(value);
   }

   @Override
   public Object onGetIDValue(Categoria item)
   {
      return item.getId();
   }

   @Override
   public boolean onGetChecked(Categoria element)
   {
      return element.isSelected();
   }

   @Override
   public void onSetChecked(Categoria element, boolean value)
   {
      element.setSelected(value);
   }

   @Override
   public Object onGetIDParentValue(Categoria element)
   {
      return element.getIdPadre();
   }

   @Override
   public int onGetLevel(Categoria element)
   {
      return element.getNivel();
   }

   @Override
   public void onModifiedAdded(Categoria element)
   {
      element.setModified();
   }

   @Override
   public boolean canAddToModified(Categoria element)
   {
      return !element.isSet();
   }
   
   
    @Override
    public Object getNullParent()
    {
        return 0;
    }
}