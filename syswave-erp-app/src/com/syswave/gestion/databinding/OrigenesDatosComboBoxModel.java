package com.syswave.gestion.databinding;

import com.syswave.entidades.configuracion.OrigenDato;
import com.syswave.swing.models.POJOComboBoxModel;
import java.util.List;

/**
 * Enlaza una lista de elementos Origen Datos a un componente de lista
 * @author Victor Manuel Bucio Vargas
 */
public class OrigenesDatosComboBoxModel extends POJOComboBoxModel<OrigenDato>
{
   //--------------------------------------------------------------------- 
   public OrigenesDatosComboBoxModel()
   {
      super();
   }
   
   //---------------------------------------------------------------------
   public OrigenesDatosComboBoxModel(List<OrigenDato> items)
   {
      super(items);
   }

   //---------------------------------------------------------------------
   @Override
   public String onGetText(OrigenDato item)
   {
      return item.getNombre();
   }

   //---------------------------------------------------------------------
   @Override
   public void onSetText(OrigenDato item, String value)
   {
      item.setNombre(value);
   }

   //---------------------------------------------------------------------
   @Override
   public Object onGetValue(OrigenDato item)
   {
      return item.getId();
   }
}