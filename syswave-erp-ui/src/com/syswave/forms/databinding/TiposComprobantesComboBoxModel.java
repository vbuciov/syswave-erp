package com.syswave.forms.databinding;

import com.syswave.swing.models.POJOComboBoxModel;
import com.syswave.entidades.miempresa.TipoComprobante;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class TiposComprobantesComboBoxModel extends POJOComboBoxModel<TipoComprobante>
{
   @Override
   public String onGetText(TipoComprobante item)
   {
      return item.getNombre();
   }

   @Override
   public void onSetText(TipoComprobante item, String value)
   {
      item.setNombre(value);
   }

   @Override
   public Object onGetValue(TipoComprobante item)
   {
     return item.getId();
   }
}