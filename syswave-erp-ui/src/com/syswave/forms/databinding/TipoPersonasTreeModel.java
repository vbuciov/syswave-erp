package com.syswave.forms.databinding;

import com.syswave.swing.models.POJOTreeModel;
import com.syswave.entidades.miempresa.TipoPersona;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class TipoPersonasTreeModel extends POJOTreeModel<TipoPersona>
{

   @Override
   public String onGetText(TipoPersona item)
   {
     return item.getNombre();
   }

   @Override
   public void onSetText(TipoPersona item, String value)
   {
      item.setNombre(value);
   }

   @Override
   public Object onGetIDValue(TipoPersona item)
   {
      return item.getId();
   }

   @Override
   public boolean onGetChecked(TipoPersona element)
   {
      return element.isSelected();
   }

   @Override
   public void onSetChecked(TipoPersona element, boolean value)
   {
      element.setSelected(value);
   }

   @Override
   public Object onGetIDParentValue(TipoPersona element)
   {
      return element.getIdPadre();
   }

   @Override
   public int onGetLevel(TipoPersona element)
   {
      return element.getNivel();
   }

   @Override
   public void onModifiedAdded(TipoPersona element)
   {
      element.setModified();
   }

   @Override
   public boolean canAddToModified(TipoPersona element)
   {
      return !element.isSet();
   }
   
   
    @Override
    public Object getNullParent()
    {
        return 0;
    }
}
