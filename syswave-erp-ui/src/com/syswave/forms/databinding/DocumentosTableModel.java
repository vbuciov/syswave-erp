package com.syswave.forms.databinding;

import com.syswave.swing.models.POJOTableModel;
import com.syswave.swing.table.events.TableModelCellFormatEvent;
import com.syswave.swing.table.events.TableModelGetValueEvent;
import com.syswave.swing.table.events.TableModelSetValueEvent;
import com.syswave.entidades.miempresa.Documento;
import java.util.Date;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class DocumentosTableModel extends POJOTableModel<Documento>
{
   //---------------------------------------------------------------------
   public DocumentosTableModel(String[] columns)
   {
      super(columns);
   }

   //---------------------------------------------------------------------
   @Override
   public void onSetValueAt(TableModelSetValueEvent<Documento> e)
   {
      if (e.getNewValue() != null)
      {
         Documento actual = e.getItem();

         switch (e.getDataProperty())
         {
            case "id":
               actual.setId((int) e.getNewValue());
               break;

            case "folio":
               actual.setFolio((String) e.getNewValue());
               break;

            case "serie":
               actual.setSerie((String) e.getNewValue());
               break;

            case "fecha_elaboracion":
               actual.setFechaElaboracion((Date) e.getNewValue());
               break;

            case "fecha_vigencia":
               actual.setFechaVigencia((Date) e.getNewValue());
               break;

            case "es_activo":
               actual.setActivo((boolean) e.getNewValue());
               break;

            case "subtotal":
               actual.setSubtotal((float) e.getNewValue());
               break;

            case "factor":
               actual.setFactor((int) e.getNewValue());
               break;

            case "monto":
               actual.setMonto((float) e.getNewValue());
               break;

            case "total":
               actual.setTotal((float) e.getNewValue());
               break;

            case "id_estatus":
               actual.setIdEstatus((int) e.getNewValue());
               break;

            case "id_tipo_comprobante":
               actual.setIdTipoComprobante((int) e.getNewValue());
               break;

            case "es_aplicado":
               actual.setAplicado((boolean) e.getNewValue());
               break;               
         }

      }
   }

   //---------------------------------------------------------------------
   @Override
   public Object onGetValueAt(TableModelGetValueEvent<Documento> e)
   {
        Documento actual = e.getItem();

         switch (e.getDataProperty())
         {
            case "id":
               return actual.getId();

            case "folio":
               return actual.getFolio();

            case "serie":
               return actual.getSerie();
               
            case "fecha_elaboracion":
               return actual.getFechaElaboracion();
               
            case "fecha_vigencia":
               return actual.getFechaVigencia();

            case "es_activo":
               return actual.esActivo();

            case "subtotal":
               return actual.getSubtotal();
               
            case "factor":
               return actual.getFactor();
               
            case "monto":
               return actual.getMonto();

            case "total":
               return actual.getTotal();
               
            case "id_estatus":
               return actual.getIdEstatus();

            case "id_tipo_comprobante":
               return actual.getIdTipoComprobante();
      
            case "es_aplicado":
               return actual.esAplicado();
         }

         return null;
   }

   //---------------------------------------------------------------------
   @Override
   public Class<?> onGetColumnClass(TableModelCellFormatEvent e)
   {
         switch (e.getDataProperty())
         {
            case "id":
            case "factor":
            case "id_estatus":
            case "id_tipo_comprobante":
               return Integer.class;

            case "fecha_elaboracion":
            case "fecha_vigencia":
               return Date.class;

            case "es_activo":
            case "es_aplicado":
               return Boolean.class;

            case "subtotal":
            case "monto":
            case "total":
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