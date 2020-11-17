/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.syswave.forms.databinding;

import com.syswave.entidades.miempresa.Valor;
import com.syswave.swing.models.POJOComboBoxModel;

/**
 *
 * @author Gilberto Aaron Jimenez Montelongo
 */
public class EstadoCivilComboBoxModel extends POJOComboBoxModel<Valor>
{

   //---------------------------------------------------------------------
   @Override
   public String onGetText(Valor item)
   {
      return item.getDescripcion();
   }

   //---------------------------------------------------------------------
   @Override
   public void onSetText(Valor item, String value)
   {
      item.setDescripcion(value);
   }
   
  

   //---------------------------------------------------------------------
   @Override
   public Object onGetValue(Valor item)
   {
      return item.getId();
   }

}
