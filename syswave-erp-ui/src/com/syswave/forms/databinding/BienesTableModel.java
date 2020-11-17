package com.syswave.forms.databinding;

import com.syswave.swing.models.POJOTableModel;

import com.syswave.entidades.miempresa.Bien;
import com.syswave.swing.table.events.TableModelCellFormatEvent;
import com.syswave.swing.table.events.TableModelGetValueEvent;
import com.syswave.swing.table.events.TableModelSetValueEvent;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class BienesTableModel extends POJOTableModel<Bien>
{
 //---------------------------------------------------------------------
   public BienesTableModel()
   {
      super();
   }
   
   //---------------------------------------------------------------------
   public BienesTableModel(String[] columns)
   {
      super (columns);
   }
   
   //---------------------------------------------------------------------
   @Override
   public void onSetValueAt(TableModelSetValueEvent<Bien> e)
   {
      Bien actual = e.getItem();
      switch (e.getColIndex())
      {
         case 1:
              actual.setNombre((String)e.getNewValue());
            break;
            
         case 2:
            if (e.getNewValue() != null)
              actual.setEsTipo((int)e.getNewValue());
            break;
            
         case 0:
            if (e.getNewValue() != null)
               actual.setIdCategoria((int)e.getNewValue());
            break;
           
      }
   }

   //---------------------------------------------------------------------
   @Override
   public Object onGetValueAt(TableModelGetValueEvent<Bien> e)
   {
      Bien actual = e.getItem();
     switch (e.getColIndex())
      {
         case 1:
            return actual.getNombre();
            
         case 2:
            return actual.getEsTipo();
            
         case 0:
            return actual.getIdCategoria();
                  
         default:
            return null;
      }
   }
   
   //---------------------------------------------------------------------
   @Override
   public boolean isCellEditable(int rowIndex, int columnIndex)
   {
      return true; //super.isCellEditable(rowIndex, columnIndex); //To change body of generated methods, choose Tools | Templates.
   }

   //---------------------------------------------------------------------
   @Override
   public Class<?> onGetColumnClass(TableModelCellFormatEvent e)
   {
     switch (e.getColIndex())
      {
         case 0:
         case 2:
            return Integer.class;
                        
         default:
            return getDefaultColumnClass();
      }   
   }
}