package com.syswave.forms.databinding;

import com.syswave.swing.models.POJOTableModel;
import com.syswave.swing.table.events.TableModelCellFormatEvent;
import com.syswave.swing.table.events.TableModelGetValueEvent;
import com.syswave.swing.table.events.TableModelSetValueEvent;
import com.syswave.entidades.miempresa.Moneda;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */ 
public class MonedasTableModel extends POJOTableModel<Moneda>
{
   public MonedasTableModel(String[] columns)
   {
      super(columns);
   }

   //---------------------------------------------------------------------
   @Override
   public void onSetValueAt(TableModelSetValueEvent<Moneda> e)
   {
      Moneda actual = e.getItem();
     
      if (e.getNewValue() != null)
      {
         switch (e.getDataProperty())
         {
            case "id":
                actual.setId((int)e.getNewValue());
               break;

            case "nombre":
               actual.setNombre((String)e.getNewValue());
               break;

            case "siglas":
               actual.setSiglas((String)e.getNewValue());
               break;
         }
      }
   }

   //---------------------------------------------------------------------
   @Override
   public Object onGetValueAt(TableModelGetValueEvent<Moneda> e)
   {
      Moneda actual = e.getItem();
    
      switch (e.getDataProperty())
      {
         case "id":
             return actual.getId();

         case "nombre":
            return actual.getNombre();

         case "siglas":
            return actual.getSiglas();
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
            return Integer.class;
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