package com.syswave.forms.databinding;

import com.syswave.entidades.miempresa.ControlPrecio_tiene_Tipo;
import com.syswave.swing.models.POJOTableModel;
import com.syswave.swing.table.events.TableModelCellFormatEvent;
import com.syswave.swing.table.events.TableModelGetValueEvent;
import com.syswave.swing.table.events.TableModelSetValueEvent;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class ControlPrecioTipoTableModel extends POJOTableModel<ControlPrecio_tiene_Tipo>
{
   //---------------------------------------------------------------------
   public ControlPrecioTipoTableModel(String[] columns)
   {
      super (columns);
   }

   //---------------------------------------------------------------------
   @Override
   public void onSetValueAt(TableModelSetValueEvent<ControlPrecio_tiene_Tipo> e)
   {
      ControlPrecio_tiene_Tipo actual = e.getItem();
      
      if (e.getNewValue() != null)
      {
         switch (e.getDataProperty())
         {
            case "id_bien":
               actual.setIdBien((int)e.getNewValue());
               break;
                
            case "cantidad":
                actual.setCantidad((float)e.getNewValue());
                break;
               
            case "subtotal":
               actual.setSubtotal((float)e.getNewValue());
               break;
               
            case "monto":
               actual.setMonto((float)e.getNewValue());
               break;
               
            case "factor":
               actual.setFactor((int)e.getNewValue());
               break;
               
            case "total":
               actual.setTotal((float)e.getNewValue());
               break;
                
            case "descripcion":
                actual.getHasOneBien().setNombre((String)e.getNewValue());
                break;
         }
      }
   }

   //---------------------------------------------------------------------
   @Override
   public Object onGetValueAt(TableModelGetValueEvent<ControlPrecio_tiene_Tipo> e)
   {
      ControlPrecio_tiene_Tipo actual = e.getItem();
         switch (e.getDataProperty())
         {
            case "id_bien":
               return actual.getIdBien();
                
            case "cantidad":
                return actual.getCantidad();
 
            case "subtotal":
               return actual.getSubtotal();
               
            case "monto":
               return actual.getMonto();
               
            case "factor":
               return actual.getFactor();
               
            case "total":
               return actual.getTotal();
                
            case "descripcion":
                return actual.getHasOneBien().getNombre();
         }
         
         return null;
   }

   //---------------------------------------------------------------------
   @Override
   public Class<?> onGetColumnClass(TableModelCellFormatEvent e)
   {
      switch (e.getDataProperty())
      {
         case "factor":
         case "id_bien":
            return Integer.class;

         case "cantidad":
         case "subtotal":
         case "monto":
         case "total":
            return Float.class;
      }  

      return getDefaultColumnClass();
   }

   //---------------------------------------------------------------------
    @Override
    public boolean isCellEditable(int row, int column)
    {
        return column > 2;
    }
   
}
