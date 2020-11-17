package com.syswave.forms.databinding;

import com.syswave.swing.models.POJOTableModel;
import com.syswave.swing.table.events.TableModelCellFormatEvent;
import com.syswave.swing.table.events.TableModelGetValueEvent;
import com.syswave.swing.table.events.TableModelSetValueEvent;
import com.syswave.entidades.miempresa.ControlPrecio;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class ControlPreciosTableModel extends POJOTableModel<ControlPrecio>
{
   //---------------------------------------------------------------------
   public ControlPreciosTableModel(String[] columns)
   {
      super (columns);
   }

   //---------------------------------------------------------------------
   @Override
   public void onSetValueAt(TableModelSetValueEvent<ControlPrecio> e)
   {
      ControlPrecio actual = e.getItem();
      
      if (e.getNewValue() != null)
      {
         switch (e.getDataProperty())
         {
            case "id":
               actual.setId((int)e.getNewValue());
               break;
               
            case "id_variante":
               actual.setIdVariante((int)e.getNewValue());
               break;
               
            case "id_moneda":
               actual.setIdMoneda((int)e.getNewValue());
               break;
               
            case "descripcion":
               actual.setDescripcion((String)e.getNewValue());
               break;
               
            case "costo_directo":
               actual.setCostoDirecto((float)e.getNewValue());
               break;
               
            case "margen":
               actual.setMargen((float)e.getNewValue());
               break;
               
            case "factor":
               actual.setFactor((int)e.getNewValue());
               break;
               
            case "precio_final":
               actual.setPrecioFinal((float)e.getNewValue());
               break;
               
            case "tiene_analisis":
               actual.setTieneAnalisis((boolean)e.getNewValue());
               break;
               
            /*case "es_tipo":
               actual.setEsTipo((int)e.getNewValue());
               break;*/
                
            case "id_area_precio":
                actual.setIdAreaPrecio((int)e.getNewValue());
               break;
         }
      }
   }

   //---------------------------------------------------------------------
   @Override
   public Object onGetValueAt(TableModelGetValueEvent<ControlPrecio> e)
   {
      ControlPrecio actual = e.getItem();
         switch (e.getDataProperty())
         {
            case "id":
               return actual.getId();
                             
            case "id_variante":
               return actual.getIdVariante();
               
            case "id_moneda":
               return actual.getIdMoneda();

            case "descripcion":
               return actual.getDescripcion();

            case "costo_directo":
               return actual.getCostoDirecto();
               
            case "margen":
               return actual.getMargen();
               
            case "factor":
               return actual.getFactor();
               
            case "precio_final":
               return actual.getPrecioFinal();

            case "tiene_analisis":
               return actual.tieneAnalisis();
               
           /* case "es_tipo":
               return actual.getEsTipo();*/
                
            case "id_area_precio":
               return actual.getIdAreaPrecio();
         }
         
         return null;
   }

   //---------------------------------------------------------------------
   @Override
   public Class<?> onGetColumnClass(TableModelCellFormatEvent e)
   {
      switch (e.getDataProperty())
      {
         case "id":                 
         case "id_variante":
         case "id_moneda":
         case "factor":
         //case "es_tipo":
         case "id_area_precio":
            return Integer.class;

         case "costo_directo":
         case "margen":
         case "precio_final":
            return Float.class;

         case "tiene_analisis":
            return Boolean.class;
      }  

      return getDefaultColumnClass();
   }

   //---------------------------------------------------------------------
   @Override
   public boolean isCellEditable(int rowIndex, int columnIndex)
   {
      return true;
   }  
}