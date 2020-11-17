package com.syswave.forms.databinding;

import com.syswave.swing.models.POJOTableModel;
import com.syswave.swing.table.events.TableModelCellFormatEvent;
import com.syswave.swing.table.events.TableModelGetValueEvent;
import com.syswave.swing.table.events.TableModelSetValueEvent;
import com.syswave.entidades.miempresa_vista.Documento_tiene_Condicion_5FN;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class DocumentoCondicion5FNTableModel extends POJOTableModel<Documento_tiene_Condicion_5FN>
{
   //---------------------------------------------------------------------
   public DocumentoCondicion5FNTableModel (String[] columns)
   {
      super (columns);
   }

   //---------------------------------------------------------------------
   @Override
   public void onSetValueAt(TableModelSetValueEvent<Documento_tiene_Condicion_5FN> e)
   {
      if (e.getNewValue() != null)
      {
         Documento_tiene_Condicion_5FN actual = e.getItem();
         
         switch (e.getDataProperty())
         {
            case "seccion":
               actual.setSeccion((String)e.getNewValue());
               break;
               
            case "descripcion":
               actual.setDescripcion((String)e.getNewValue());
               break;
               
            case "es_activo":
               actual.setActivo((boolean)e.getNewValue());
               break;
               
            case "id_documento":
               actual.setIdDocumento((int)e.getNewValue());
               break;
               
            case "id_condicion":
               actual.setIdCondicionPago((int)e.getNewValue());
               break;
               
            case "condicion":
               actual.setCondicion((String)e.getNewValue());
               break;  
         }
      }
   }

   //---------------------------------------------------------------------
   @Override
   public Object onGetValueAt(TableModelGetValueEvent<Documento_tiene_Condicion_5FN> e)
   {
         Documento_tiene_Condicion_5FN actual = e.getItem();
         
         switch (e.getDataProperty())
         {
            case "seccion":
               return actual.getSeccion();
               
            case "descripcion":
               return actual.getDescripcion();
               
            case "es_activo":
               return actual.esActivo();
               
            case "id_documento":
               return actual.getIdDocumento();

            case "id_condicion":
               return actual.getIdCondicionPago();
     
            case "condicion":
               return actual.getCondicion();  
         }
         
         return null;
   }

   //---------------------------------------------------------------------
   @Override
   public Class<?> onGetColumnClass(TableModelCellFormatEvent e)
   {         
      switch (e.getDataProperty())
      { 
         case "id_documento":
         case "id_condicion":
            return Integer.class;
            
         case "es_activo":
            return Boolean.class;
      }

      return getDefaultColumnClass();
   }  
   
}
