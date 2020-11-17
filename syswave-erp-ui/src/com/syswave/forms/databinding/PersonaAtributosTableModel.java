package com.syswave.forms.databinding;

import com.syswave.entidades.miempresa.PersonaAtributo;
import com.syswave.swing.models.POJOTableModel;
import com.syswave.swing.table.events.TableModelCellFormatEvent;
import com.syswave.swing.table.events.TableModelGetValueEvent;
import com.syswave.swing.table.events.TableModelSetValueEvent;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class PersonaAtributosTableModel extends POJOTableModel<PersonaAtributo>
{
   //---------------------------------------------------------------------
   public PersonaAtributosTableModel(String[] columns)
   {
      super (columns);
   }

   //---------------------------------------------------------------------
   @Override
   public void onSetValueAt(TableModelSetValueEvent<PersonaAtributo> e)
   {
      PersonaAtributo actual = e.getItem();

      if (e.getNewValue() != null)
      {
         switch (e.getDataProperty())
         {
            case "consecutivo":
               actual.setConsecutivo((int)e.getNewValue());
               break;
               
            case "id_persona":
               actual.setIdPersona((int)e.getNewValue());
               break;
               
            case "nombre":
               actual.setNombre((String)e.getNewValue());
               break;
               
            case "es_tipo":
               actual.setEsTipo((int)e.getNewValue());
               break;
                
            case "tratamiento":
                actual.setTratamiento((String)e.getNewValue());
                break;
                
            case "descripcion":
                actual.setDescripcion((String)e.getNewValue());
                break;
         }
      }
   }

   //---------------------------------------------------------------------
   @Override
   public Object onGetValueAt(TableModelGetValueEvent<PersonaAtributo> e)
   {
      PersonaAtributo actual = e.getItem();
      
         switch (e.getDataProperty())
         {
            case "consecutivo":
               return actual.getConsecutivo();
                             
            case "id_persona":
               return actual.getIdPersona();
               
            case "nombre":
               return actual.getNombre();

            case "es_tipo":
                return actual.getEsTipo();
                
            case "tratamiento":
                return actual.getTratamiento();
                
            case "descripcion":
                return actual.getDescripcion();
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
         case "id_persona":
         case "es_tipo":
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
