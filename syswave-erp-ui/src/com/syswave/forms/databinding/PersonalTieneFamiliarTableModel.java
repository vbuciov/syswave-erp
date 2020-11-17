package com.syswave.forms.databinding;

import com.syswave.entidades.miempresa_vista.PersonalTieneFamiliar;
import com.syswave.swing.models.POJOTableModel;
import com.syswave.swing.table.events.TableModelCellFormatEvent;
import com.syswave.swing.table.events.TableModelGetValueEvent;
import com.syswave.swing.table.events.TableModelSetValueEvent;
import java.util.Date;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class PersonalTieneFamiliarTableModel extends POJOTableModel<PersonalTieneFamiliar>
{

   public PersonalTieneFamiliarTableModel(String[] columns)
   {
      super(columns);
   }

   @Override
   public void onSetValueAt(TableModelSetValueEvent<PersonalTieneFamiliar> e)
   {
      PersonalTieneFamiliar actual = e.getItem();

      if (e.getNewValue() != null)
      {
         switch (e.getDataProperty())
         {
            case "id_founded":
               actual.setIdFounded((Integer) e.getNewValue());
               break;

            case "id_searched":
               actual.setIdSearched((Integer) e.getNewValue());
               break;

            case "prototype":
               actual.setPrototype((Integer) e.getNewValue());
               break;
         }
      }
   }

   @Override
   public Object onGetValueAt(TableModelGetValueEvent<PersonalTieneFamiliar> e)
   {
      PersonalTieneFamiliar actual = e.getItem();

      switch (e.getDataProperty())
      {
         case "id_founded":
            return actual.getIdFounded();
         case "id_searched":
            return actual.getIdSearched();
         case "prototype":
            return actual.getPrototype();
      }

      return null;
   }

   @Override
   public Class<?> onGetColumnClass(TableModelCellFormatEvent e)
   {
      switch (e.getDataProperty())
      {
         case "id_founded":
         case "id_searched":
         case "prototype":
            return Integer.class;
      }

      return getDefaultColumnClass();
   }
}
