/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.syswave.forms.databinding;

import com.syswave.swing.models.POJOComboBoxModel;
import com.syswave.entidades.miempresa.TipoPersona;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class TipoPersonasComboBoxModel extends POJOComboBoxModel<TipoPersona>
{

   //---------------------------------------------------------------------
   @Override
   public String onGetText(TipoPersona item)
   {
      return item.getNombre();
   }

   //---------------------------------------------------------------------
   @Override
   public void onSetText(TipoPersona item, String value)
   {
      item.setNombre(value);
   }

   //---------------------------------------------------------------------
   @Override
   public Object onGetValue(TipoPersona item)
   {
      return item.getId();
   }
}