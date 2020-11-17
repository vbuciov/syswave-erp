package com.syswave.forms.databinding;

import com.syswave.swing.models.POJOTableModel;
import com.syswave.swing.table.events.TableModelCellFormatEvent;
import com.syswave.swing.table.events.TableModelGetValueEvent;
import com.syswave.swing.table.events.TableModelSetValueEvent;
import com.syswave.entidades.miempresa_vista.DocumentoDetalleNavigable;

/**
 *
 * @author victor
 */
public class DocumentoDetalleNavigableTableModel extends POJOTableModel<DocumentoDetalleNavigable>
{
   
   //---------------------------------------------------------------------
   public DocumentoDetalleNavigableTableModel(String[] columns)
   {
      super(columns);
   }

   @Override
   public void onSetValueAt(TableModelSetValueEvent<DocumentoDetalleNavigable> e)
   {
       if (e.getNewValue() != null)
      {
         DocumentoDetalleNavigable actual = e.getItem();

         switch (e.getDataProperty())
         {
            case "id_documento":
               actual.setIdDocumento((int) e.getNewValue());
               break;

            case "consecutivo":
               actual.setConsecutivo((int) e.getNewValue());
               break;

            case "descripcion":
               actual.setDescripcion((String) e.getNewValue());
               break;

            case "cantidad":
               actual.setCantidad((float) e.getNewValue());
               break;

            case "precio":
               actual.setPrecio((float) e.getNewValue());
               break;

            case "importe":
               actual.setImporte((float) e.getNewValue());
               break;

            case "monto":
               actual.setMonto((float) e.getNewValue());
               break;

            case "factor":
               actual.setFactor((int) e.getNewValue());
               break;

            case "importe_neto":
               actual.setImporte_neto((float) e.getNewValue());
               break;
         }

      }
   }

   @Override
   public Object onGetValueAt(TableModelGetValueEvent<DocumentoDetalleNavigable> e)
   {
      DocumentoDetalleNavigable actual = e.getItem();

         switch (e.getDataProperty())
         {
            case "id_documento":
               return actual.getIdDocumento();

            case "consecutivo":
               return actual.getConsecutivo();

            case "descripcion":
               return actual.getDescripcion();
               
            case "cantidad":
               return actual.getCantidad();
               
            case "precio":
               return actual.getPrecio();

            case "importe":
               return actual.getImporte();

            case "monto":
               return actual.getMonto();
               
            case "factor":
               return actual.getFactor();
               
            case "importe_neto":
               return actual.getImporte_neto();
               
            case "#":
               return e.getRowIndex() + 1;
         }

         return null;
   }

   @Override
   public Class<?> onGetColumnClass(TableModelCellFormatEvent e)
   {
         switch (e.getDataProperty())
         {
            case "id_documento":
            case "consecutivo":
            case "factor":
            case "#":
               return Integer.class;

            case "cantidad":
            case "precio":
            case "importe":
            case "monto":
            case "importe_neto":
               return Float.class;
         }
         
         return getDefaultColumnClass();
   }
  
   //---------------------------------------------------------------------
   @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex > 0;
    }
}
