package com.syswave.forms.databinding;

import com.syswave.swing.models.POJOTableModel;
import com.syswave.swing.table.events.TableModelCellFormatEvent;
import com.syswave.swing.table.events.TableModelGetValueEvent;
import com.syswave.swing.table.events.TableModelSetValueEvent;
import com.syswave.entidades.miempresa.CondicionPago;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class CondicionPagoTableModel extends POJOTableModel<CondicionPago>
{
   //---------------------------------------------------------------------
   public CondicionPagoTableModel(String[] columns)
   {
      super(columns);
   }

   //---------------------------------------------------------------------
   @Override
   public void onSetValueAt(TableModelSetValueEvent<CondicionPago> e)
   {
      if (e.getNewValue() != null)
      {
         CondicionPago actual = e.getItem();

         switch (e.getDataProperty())
         {
            case "id":
               actual.setId((int) e.getNewValue());
               break;

            case "nombre":
               actual.setNombre((String) e.getNewValue());
               break;

            case "valor":
               actual.setValor((int) e.getNewValue());
               break;

            case "unidad":
               actual.setUnidad((int) e.getNewValue());
               break;

            case "id_tipo_condicion":
               actual.setId_tipo_condicion((int) e.getNewValue());
               break;        
         }

      }
   }

   //---------------------------------------------------------------------
   @Override
   public Object onGetValueAt(TableModelGetValueEvent<CondicionPago> e)
   {
        CondicionPago actual = e.getItem();

         switch (e.getDataProperty())
         {
            case "id":
               return actual.getId();

            case "nombre":
               return actual.getNombre();
               
            case "valor":
               return actual.getValor();

            case "unidad":
               return actual.getUnidad();
               
            case "id_tipo_condicion":
               return actual.getId_tipo_condicion();
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
            case "valor":
            case "unidad":
            case "id_tipo_condicion":
               return Integer.class;
         }

         return getDefaultColumnClass();
   }

   //---------------------------------------------------------------------
   @Override
   public boolean isCellEditable(int rowIndex, int columnIndex)
   {
      return !isReadOnly();
   }
}
