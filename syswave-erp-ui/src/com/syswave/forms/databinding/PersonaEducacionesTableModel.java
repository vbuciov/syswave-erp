package com.syswave.forms.databinding;

import com.syswave.entidades.miempresa.PersonaEducacion;
import com.syswave.swing.models.POJOTableModel;
import com.syswave.swing.table.events.TableModelCellFormatEvent;
import com.syswave.swing.table.events.TableModelGetValueEvent;
import com.syswave.swing.table.events.TableModelSetValueEvent;
import java.util.Date;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class PersonaEducacionesTableModel extends POJOTableModel<PersonaEducacion>
{
   //---------------------------------------------------------------------
   public PersonaEducacionesTableModel(String[] columns)
   {
      super (columns);
   }

   //---------------------------------------------------------------------
   @Override
   public void onSetValueAt(TableModelSetValueEvent<PersonaEducacion> e)
   {
      PersonaEducacion actual = e.getItem();

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
               
            case "titulo":
               actual.setTitulo((String)e.getNewValue());
               break;
               
            case "fecha_inicio":
               actual.setFechaInicio((Date)e.getNewValue());
               break;
               
            case "fecha_fin":
               actual.setFechFin((Date)e.getNewValue());
               break;
               
            case "es_cursando":
               actual.setCursando((boolean)e.getNewValue());
               break;
                
            case "es_tipo":
               actual.setTipo((int)e.getNewValue());
               break;
         }
      }
   }

   //---------------------------------------------------------------------
   @Override
   public Object onGetValueAt(TableModelGetValueEvent<PersonaEducacion> e)
   {
      PersonaEducacion actual = e.getItem();
      
         switch (e.getDataProperty())
         {
            case "consecutivo":
               return actual.getConsecutivo();
                             
            case "id_persona":
               return actual.getIdPersona();
               
            case "nombre":
               return actual.getNombre();

            case "titulo":
               return actual.getTitulo();

            case "fecha_inicio":
               return actual.getFechaInicio();
               
            case "fecha_fin":
               return actual.getFechaFin();
               
            case "es_cursando":
               return actual.esCursando();
                
            case "es_tipo":
               return actual.getTipo();
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

         case "fecha_inicio":
         case "fecha_fin":
            return Date.class;

         case "es_cursando":
            return Boolean.class;
             
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
