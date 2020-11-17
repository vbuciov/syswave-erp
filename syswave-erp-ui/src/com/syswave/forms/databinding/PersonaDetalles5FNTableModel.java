package com.syswave.forms.databinding;

import com.syswave.entidades.miempresa_vista.PersonaDetalle_5FN;
import com.syswave.swing.models.POJOTableModel;
import com.syswave.swing.table.events.TableModelCellFormatEvent;
import com.syswave.swing.table.events.TableModelGetValueEvent;
import com.syswave.swing.table.events.TableModelSetValueEvent;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class PersonaDetalles5FNTableModel extends POJOTableModel<PersonaDetalle_5FN>
{
   //---------------------------------------------------------------------
   public PersonaDetalles5FNTableModel(String[] columns)
   {
      super(columns);
   }
   
   //---------------------------------------------------------------------
   @Override
   public void onSetValueAt(TableModelSetValueEvent<PersonaDetalle_5FN> e)
   {
      PersonaDetalle_5FN actual = e.getItem();
      
      switch (e.getDataProperty())
      {
         case "valor":
            actual.setValor((String)e.getNewValue());
            break;
            
         case "id_persona":
             actual.setIdPersona((int)e.getNewValue());
            break;
            
         case "id_tipo_detalle":
            actual.setIdTipoDetalle((int)e.getNewValue());
            break;
      }
      
   }

   //---------------------------------------------------------------------
   @Override
   public Object onGetValueAt(TableModelGetValueEvent<PersonaDetalle_5FN> e)
   {
      PersonaDetalle_5FN actual = e.getItem();
      
      switch (e.getDataProperty())
      {
         case "valor":
            return actual.getValor();

         case "id_persona":
           return actual.getIdPersona();
            
         case "id_tipo_detalle":
            return actual.getIdTipoDetalle();
            
           case "llave":
            return actual.getLlave();
              
          case "descripcion":
            return actual.getDescripcion();
             
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
         case "id_persona":
         case "id_tipo_detalle":
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
      return columnIndex < 1; //To change body of generated methods, choose Tools | Templates.
   }
   
}
