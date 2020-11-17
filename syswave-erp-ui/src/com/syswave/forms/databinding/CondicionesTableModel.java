/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.syswave.forms.databinding;

import com.syswave.entidades.miempresa.Condicion;
import com.syswave.swing.models.POJOTableModel;
import com.syswave.swing.table.events.TableModelCellFormatEvent;
import com.syswave.swing.table.events.TableModelGetValueEvent;
import com.syswave.swing.table.events.TableModelSetValueEvent;

/**
 *
 * @author Aimee García
 */
public class CondicionesTableModel extends POJOTableModel<Condicion>
{
   //---------------------------------------------------------------------
   public CondicionesTableModel(String[] columns)
   {
      super(columns);
   }

   //---------------------------------------------------------------------
   @Override
   public void onSetValueAt(TableModelSetValueEvent<Condicion> e)
   {
      if (e.getNewValue() != null)
      {
         Condicion actual = e.getItem();

         switch (e.getDataProperty())
         {
            case "id":
               actual.setId((int) e.getNewValue());
               break;

            case "nombre":
               actual.setNombre((String) e.getNewValue());
               break;

            case "es_activo":
               actual.setActivo((boolean) e.getNewValue());
               break;
         }

      }
   }

   //---------------------------------------------------------------------
   @Override
   public Object onGetValueAt(TableModelGetValueEvent<Condicion> e)
   {
        Condicion actual = e.getItem();

         switch (e.getDataProperty())
         {
            case "id":
               return actual.getId();

            case "nombre":
               return actual.getNombre();
               
            case "es_activo":
               return actual.isActivo();
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
                
            case "es_activo":
                return Boolean.class;
         }

         return getDefaultColumnClass();
   }
}
