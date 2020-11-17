package com.syswave.forms.databinding;

import com.syswave.entidades.miempresa.MantenimientoCosto;
import com.syswave.swing.models.POJOTableModel;
import com.syswave.swing.table.events.TableModelCellFormatEvent;
import com.syswave.swing.table.events.TableModelGetValueEvent;
import com.syswave.swing.table.events.TableModelSetValueEvent;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class MantemientoCostosTableModel extends POJOTableModel<MantenimientoCosto>
{
   //---------------------------------------------------------------------
   public MantemientoCostosTableModel(String[] columns)
   {
      super (columns);
   }

   //---------------------------------------------------------------------
   @Override
   public void onSetValueAt(TableModelSetValueEvent<MantenimientoCosto> e)
   {
      MantenimientoCosto actual = e.getItem();
      
      if (e.getNewValue() != null)
      {
         switch (e.getDataProperty())
         {
             case "consecutivo":
                 actual.setConsecutivo((int)e.getNewValue());
                 break;
              
             case "id_mantenimiento":
                 actual.setIdMantenimiento((int)e.getNewValue());
                 break;
             
             case "descripcion":
                     actual.setDescripcion((String)e.getNewValue());
                 break;
                 
             case "monto":
                 actual.setMonto((Float)e.getNewValue());
                 break;
         }
      }
   }

   //---------------------------------------------------------------------
   @Override
   public Object onGetValueAt(TableModelGetValueEvent<MantenimientoCosto> e)
   {
      MantenimientoCosto actual = e.getItem();
         switch (e.getDataProperty())
         {
            case "consecutivo":
                 return actual.getConsecutivo();
                
             case "id_mantenimiento":
                 return actual.getIdMantenimiento();
             
             case "descripcion":
                     return actual.getDescripcion();
                 
             case "monto":
                 return  actual.getMonto();
         }
         
         return null;
   }

   //---------------------------------------------------------------------
   @Override
   public Class<?> onGetColumnClass(TableModelCellFormatEvent e)
   {
      switch (e.getDataProperty())
      {
         case "consecutivo":                 
         case "id_mantenimiento":
            return Integer.class;

         case "monto":
            return Float.class;
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
