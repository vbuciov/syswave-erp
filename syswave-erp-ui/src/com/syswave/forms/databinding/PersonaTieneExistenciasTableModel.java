package com.syswave.forms.databinding;

import com.syswave.swing.models.POJOTableModel;
import com.syswave.swing.table.events.TableModelCellFormatEvent;
import com.syswave.swing.table.events.TableModelGetValueEvent;
import com.syswave.swing.table.events.TableModelSetValueEvent;
import com.syswave.entidades.miempresa.Persona_tiene_Existencia;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class PersonaTieneExistenciasTableModel extends POJOTableModel<Persona_tiene_Existencia>
{

   //---------------------------------------------------------------------
   public PersonaTieneExistenciasTableModel(String[] columns)
   {
      super(columns);
   }

   //---------------------------------------------------------------------
   @Override
   public void onSetValueAt(TableModelSetValueEvent<Persona_tiene_Existencia> e)
   {
      Persona_tiene_Existencia actual = e.getItem();

      if (e.getNewValue() != null)
      {
         switch (e.getDataProperty())
         {
            case "linea":
               actual.setLinea((int) e.getNewValue());
               break;
            case "id_persona":
               actual.setIdPersona((int) e.getNewValue());
               break;
               
            case "entrada":
               actual.setEntrada((int) e.getNewValue());
               break;

            case "id_ubicacion":
               actual.setIdUbicacion((int) e.getNewValue());
               break;

            case "existencia":
               actual.setExistencia((float) e.getNewValue());
               break;
               
            case "id_serie":
               String[] keys = ((String) e.getNewValue()).split(",");
               actual.setEntrada(Integer.parseInt(keys[0]));
               actual.setIdUbicacion(Integer.parseInt(keys[1]));
               break;
         }
      }
   }

   //---------------------------------------------------------------------
   @Override
   public Object onGetValueAt(TableModelGetValueEvent<Persona_tiene_Existencia> e)
   {
      Persona_tiene_Existencia actual = e.getItem();

      switch (e.getDataProperty())
      {
         case "linea":
            return actual.getLinea();

         case "id_persona":
            return actual.getIdPersona();

         case "entrada":
            return actual.getEntrada();

         case "id_ubicacion":
            return actual.getIdUbicacion();

         case "existencia":
            return actual.getExistencia();
            
         case "id_serie":
            return actual.getIdSerie();
      }

      return null;
   }

   //---------------------------------------------------------------------
   @Override
   public Class<?> onGetColumnClass(TableModelCellFormatEvent e)
   {
      switch (e.getDataProperty())
      {
         case "linea":
         case "id_persona":
         case "entrada":
         case "id_ubicacion":
            return Integer.class;

         case "existencia":
            return Float.class;
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
