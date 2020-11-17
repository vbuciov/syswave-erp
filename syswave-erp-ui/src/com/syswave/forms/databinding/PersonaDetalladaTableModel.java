package com.syswave.forms.databinding;

import com.syswave.swing.models.POJOTableModel;
import com.syswave.swing.table.events.TableModelCellFormatEvent;
import com.syswave.swing.table.events.TableModelGetValueEvent;
import com.syswave.swing.table.events.TableModelSetValueEvent;
import com.syswave.entidades.miempresa_vista.PersonaDetalladaVista;
import java.util.Date;

/**
 *
 * @author victor
 */
public class PersonaDetalladaTableModel extends POJOTableModel<PersonaDetalladaVista>
{
 
   //---------------------------------------------------------------------
   public PersonaDetalladaTableModel (String[] columns)
   {
      super(columns);
   }
   
   //---------------------------------------------------------------------
   @Override
   public void onSetValueAt(TableModelSetValueEvent<PersonaDetalladaVista> e)
   {
       PersonaDetalladaVista actual = e.getItem();
        switch (e.getDataProperty()) 
       {
         case "nombres": //Columna del Identificador
            actual.setNombres((String)e.getNewValue());
            break;
            
         case "apellidos": //Columna de la clave
            actual.setApellidos((String)e.getNewValue());
            break;
            
          case "nacimiento": //Columna del estatus.
            actual.setNacimiento((Date)e.getNewValue());
            break;
          
          case "id_tipo_persona": //Columna del estatus. Cuando se utiliza un combo se reciben objetos persona.
            actual.setId_tipo_pesrona((Integer)e.getNewValue());
            break;
        
          case "es_activo": //Columna del estatus.
            actual.setActivo((Boolean)e.getNewValue());
            break;
             
           case "identificador_muestra":
            actual.setIdentificador_muestra((String)e.getNewValue());
              break;
            
         case "medio_muestra":
            actual.setMedio_muestra((String)e.getNewValue());
            break;
            
         case "direccion_muestra":
            actual.setDireccion_muestra((String)e.getNewValue());
            break;

       }

   }

   //---------------------------------------------------------------------
   @Override
   public Object onGetValueAt(TableModelGetValueEvent<PersonaDetalladaVista> e)
   {
      PersonaDetalladaVista actual = e.getItem();
      
      switch (e.getDataProperty()) 
      {
         case "nombres": //Columna del Identificador
           return actual.getNombres();
            
         case "apellidos": //Columna de la clave
           return actual.getApellidos();
            
         case "nacimiento": 
            return actual.getNacimiento();
            
         case "id_tipo_persona":
            return actual.getId_tipo_persona();
            
         case "es_activo": //Columna del estatus.
           return actual.esActivo(); 
            
         case "identificador_muestra":
            return actual.getIdentificador_muestra();
            
         case "medio_muestra":
            return actual.getMedio_muestra();
            
         case "direccion_muestra":
            return actual.getDireccion_muestra();
       }
      
      return null;
   }
   
      //---------------------------------------------------------------------
   @Override
   public boolean isCellEditable(int rowIndex, int columnIndex)
   {
      return columnIndex < 4; //Todo es editable
   }

   //---------------------------------------------------------------------
   @Override
   public Class<?> onGetColumnClass(TableModelCellFormatEvent e)
   {
      switch (e.getDataProperty()) {   
         case "nacimiento":
            return Date.class.getClass();
            
         case "id_tipo_persona" :
            return Integer.class.getClass();
            
         case "es_activo": //Columna del estatus.
           return Boolean.class.getClass();
       }

      return getDefaultColumnClass();
   }   
}
