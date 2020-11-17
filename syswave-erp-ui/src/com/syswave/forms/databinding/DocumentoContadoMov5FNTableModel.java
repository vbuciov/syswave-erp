package com.syswave.forms.databinding;

import com.syswave.swing.models.POJOTableModel;
import com.syswave.swing.table.events.TableModelCellFormatEvent;
import com.syswave.swing.table.events.TableModelGetValueEvent;
import com.syswave.swing.table.events.TableModelSetValueEvent;
import com.syswave.entidades.miempresa_vista.DocumentoContadoMovimiento_5FN;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class DocumentoContadoMov5FNTableModel extends POJOTableModel<DocumentoContadoMovimiento_5FN>
{
   //---------------------------------------------------------------------
   public DocumentoContadoMov5FNTableModel (String[] columns)
   {
      super (columns);
   }

   //---------------------------------------------------------------------
   @Override
   public void onSetValueAt(TableModelSetValueEvent<DocumentoContadoMovimiento_5FN> e)
   {
      if (e.getNewValue() != null)
      {
         DocumentoContadoMovimiento_5FN actual = e.getItem();
         
         switch (e.getDataProperty())
         {  
            case "linea":
               actual.setLinea((int)e.getNewValue());
               break;
               
            case "id_documento":
               actual.setIdDocumento((int)e.getNewValue());
               break;
               
            case "monto":
               actual.setMonto((float)e.getNewValue());
               break;
               
            case "concepto":
               actual.setConcepto((String)e.getNewValue());
               break;
               
            case "id_tipo_comprobante":
               actual.setIdTipoComprobante((int)e.getNewValue());
               break;
               
            case "nombre":
               actual.setNombre((String)e.getNewValue());
               break;
         }
      }
   }

   //---------------------------------------------------------------------
   @Override
   public Object onGetValueAt(TableModelGetValueEvent<DocumentoContadoMovimiento_5FN> e)
   {
         DocumentoContadoMovimiento_5FN actual = e.getItem();
         
         switch (e.getDataProperty())
         {
            case "linea":
               return actual.getLinea();
               
            case "id_documento":
               return actual.getIdDocumento();
               
            case "monto":
               return actual.getMonto();
               
            case "concepto":
               return actual.getConcepto();

            case "id_tipo_comprobante":
               return actual.getIdTipoComprobante();
               
            case "nombre":
               return actual.getNombre();
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
         case "id_documento":
         case "id_tipo_comprobante":
            return Integer.class;
            
         case "monto":
            return Float.class;
      }

      return getDefaultColumnClass();
   }  

   @Override
   public boolean isCellEditable(int rowIndex, int columnIndex)
   {
      return columnIndex > 0;
   }
   
   
}
