package com.syswave.forms.databinding;

import com.syswave.swing.models.POJOTableModel;
import com.syswave.swing.table.events.TableModelCellFormatEvent;
import com.syswave.swing.table.events.TableModelGetValueEvent;
import com.syswave.swing.table.events.TableModelSetValueEvent;

import com.syswave.entidades.miempresa_vista.BienCompuestoVista;


/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class BienCompuestosTableModel extends POJOTableModel<BienCompuestoVista>
{
   //---------------------------------------------------------------------
   public BienCompuestosTableModel ()
   {
      super();
   }
   
   //---------------------------------------------------------------------
   public BienCompuestosTableModel(String[] columns)
   {
      super (columns);
   }

   //---------------------------------------------------------------------
   @Override
   public void onSetValueAt(TableModelSetValueEvent<BienCompuestoVista> e)
   {
      BienCompuestoVista actual = e.getItem();

      switch (e.getDataProperty())
      {
         case "id_bien_parte":
            if (e.getNewValue() != null)
               actual.setIdBienParte((int) e.getNewValue());
            break;

         case "cantidad":
            actual.setCantidad((float) e.getNewValue());
            break;

         case "unidad":
              actual.setNombreUnidad((String) e.getNewValue());
            break;
      }
   }

   //---------------------------------------------------------------------
   @Override
   public Object onGetValueAt(TableModelGetValueEvent<BienCompuestoVista> e)
   {
      BienCompuestoVista actual = e.getItem();

      switch (e.getDataProperty())
      {
         case "id_bien_parte":
            return actual.getIdBienParte();
           
         case "cantidad":
            return actual.getCantidad();

         case "unidad":
              return actual.getNombreUnidad();
      }
      
      return null;
   }

   //---------------------------------------------------------------------
   @Override
   public boolean isCellEditable(int rowIndex, int columnIndex)
   {
      return columnIndex < 2;
   }
   
   //---------------------------------------------------------------------
   @Override
   public Class<?> onGetColumnClass(TableModelCellFormatEvent e)
   {
      switch (e.getDataProperty())
      {
         case "cantidad":
                 return Float.class;
      }
      return getDefaultColumnClass();
   }
}
