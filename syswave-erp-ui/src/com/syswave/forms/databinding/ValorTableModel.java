package com.syswave.forms.databinding;

import com.syswave.swing.models.POJOTableModel;
import com.syswave.swing.table.events.TableModelCellFormatEvent;
import com.syswave.swing.table.events.TableModelGetValueEvent;
import com.syswave.swing.table.events.TableModelSetValueEvent;
import com.syswave.entidades.miempresa.Valor;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class ValorTableModel extends POJOTableModel<Valor>
{
   //---------------------------------------------------------------------
   public ValorTableModel(String[] columns)
   {
      super (columns);
     
   }

   //---------------------------------------------------------------------
   @Override
   public void onSetValueAt(TableModelSetValueEvent<Valor> e)
   {
      Valor actual = e.getItem();
      
      if (e.getNewValue() != null)
      {
         switch (e.getDataProperty())
         {
            case "id":
               actual.setId((int)e.getNewValue());
               break;
               
            case "valor":
               actual.setValor((String)e.getNewValue());
               break;
               
            case "seccion":
               actual.setSeccion((String)e.getNewValue());
               break;
               
            case "descripcion":
               actual.setDescripcion((String)e.getNewValue());
               break;
               
            case "formato":
               actual.setFormato((String)e.getNewValue());
               break;
               
            case "es_activo":
               actual.setActivo((boolean)e.getNewValue());
               break;
         }
      }
   }

   //---------------------------------------------------------------------
   @Override
   public Object onGetValueAt(TableModelGetValueEvent<Valor> e)
   {
      Valor actual = e.getItem();
         switch (e.getDataProperty())
         {
            case "id":
               return actual.getId();
                             
            case "valor":
               return actual.getValor();
               
            case "seccion":
               return actual.getSeccion();

            case "descripcion":
               return actual.getDescripcion();

            case "formato":
               return actual.getFormato();
               
            case "es_activo":
               return actual.esActivo();
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

   //---------------------------------------------------------------------
   @Override
   public boolean isCellEditable(int rowIndex, int columnIndex)
   {
      return !isReadOnly();
   }   
}