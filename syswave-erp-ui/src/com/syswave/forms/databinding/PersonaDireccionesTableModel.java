package com.syswave.forms.databinding;

import com.syswave.swing.models.POJOTableModel;
import com.syswave.swing.table.events.TableModelCellFormatEvent;
import com.syswave.swing.table.events.TableModelGetValueEvent;
import com.syswave.swing.table.events.TableModelSetValueEvent;
import com.syswave.entidades.miempresa.PersonaDireccion;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class PersonaDireccionesTableModel extends POJOTableModel<PersonaDireccion>
{
   
   //---------------------------------------------------------------------
   public PersonaDireccionesTableModel()
   {
      super();
   }
   
   //---------------------------------------------------------------------
   public PersonaDireccionesTableModel(String[] columns)
   {
      super (columns);
   }

   //---------------------------------------------------------------------
   @Override
   public void onSetValueAt(TableModelSetValueEvent<PersonaDireccion> e)
   {
      PersonaDireccion actual = e.getItem();
      switch (e.getDataProperty())
      {
         case "id_localidad":
            if (e.getNewValue() != null)
              actual.setIdLocalidad((int)e.getNewValue() );
            break;
            
         case "calle":
            actual.setCalle((String)e.getNewValue() );
            break;
            
         case "colonia":
            actual.setColonia((String)e.getNewValue() );
            break;
            
         case "codigo_postal":
            actual.setCodigoPostal((String)e.getNewValue() );
            break;
            
         case "no_exterior": 
            actual.setNoExterior((String)e.getNewValue() );
            break;
            
         case "no_interior": 
            actual.setNoInterior((String)e.getNewValue() );
            break;
      }      
   }

   //---------------------------------------------------------------------
   @Override
   public Object onGetValueAt(TableModelGetValueEvent<PersonaDireccion> e)
   {
      PersonaDireccion actual = e.getItem();
      switch (e.getDataProperty())
      {
         case "id_localidad":
            return actual.getIdLocalidad();
            
         case "calle":
            return actual.getCalle();
            
         case "colonia":
            return actual.getColonia();
            
         case "codigo_postal":
            return actual.getCodigoPostal();
            
         case "no_exterior": 
            return actual.getNoExterior();
            
         case "no_interior": 
            return actual.getNoInterior();
      }
      
      return null;
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
      switch (e.getDataProperty())
      {
         case "id_localidad":
            return Integer.class;
                        
         default:
            return getDefaultColumnClass();
      }   
   }
}