package com.syswave.forms.databinding;

import com.syswave.swing.models.POJOTreeModel;
import com.syswave.entidades.configuracion.NodoPermiso;

/**
 * Permite ligar una lista de NodoPermiso a un JTree.
 * @author Victor Manuel Bucio Vargas
 */
public class PermisosTreeModel extends POJOTreeModel<NodoPermiso>
{
   //---------------------------------------------------------------------
   @Override
   public String onGetText(NodoPermiso element)
   {
      return element.getValor();
   }

   //---------------------------------------------------------------------
   @Override
   public void onSetText(NodoPermiso item, String value)
   {
      item.setValor(value);
   }

   //---------------------------------------------------------------------
   @Override
   public boolean onGetChecked(NodoPermiso element)
   {
      return element.isSelected();
   }
 
   //---------------------------------------------------------------------
   @Override
   public void onSetChecked(NodoPermiso element, boolean value)
   {
      element.setSelected(value);
   }

   //---------------------------------------------------------------------
   @Override
   public Object onGetIDParentValue(NodoPermiso element)
   {
      return element.getIdPadre();
   }

   //---------------------------------------------------------------------
   @Override
   public Object onGetIDValue(NodoPermiso element)
   {
      return  element.getId_modulo();
   }
   
   //---------------------------------------------------------------------
   @Override
   public int onGetLevel(NodoPermiso element)
   {
      return element.getNivel();
   }

   //---------------------------------------------------------------------
   @Override
   public boolean canAddToModified(NodoPermiso element)
   {
      return !element.isSet();
   }

   //---------------------------------------------------------------------
   @Override
   public void onModifiedAdded(NodoPermiso element)
   {
      element.setModified();
   }  


    @Override
    public Object getNullParent()
    {
        return 0;
    }
}