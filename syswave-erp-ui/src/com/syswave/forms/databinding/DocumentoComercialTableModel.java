package com.syswave.forms.databinding;

import com.syswave.swing.models.POJOTableModel;
import com.syswave.swing.table.events.TableModelCellFormatEvent;
import com.syswave.swing.table.events.TableModelGetValueEvent;
import com.syswave.swing.table.events.TableModelSetValueEvent;
import com.syswave.entidades.miempresa_vista.DocumentoComercial;
import java.util.Date;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class DocumentoComercialTableModel extends POJOTableModel<DocumentoComercial>
{
   public DocumentoComercialTableModel (String[] columns)
   {
      super (columns);
   }

   //---------------------------------------------------------------------
   @Override
   public void onSetValueAt(TableModelSetValueEvent<DocumentoComercial> e)
   {
       if (e.getNewValue() != null)
      {
         DocumentoComercial actual = e.getItem();

         switch (e.getDataProperty())
         {
            case "id":
               actual.setId((int) e.getNewValue());
               break;
               
            case "id_emisor":
               actual.setIdEmisor((int) e.getNewValue());
               break;
               
            case "id_receptor":
               actual.setIdReceptor((int) e.getNewValue());
               break;
               
            case "receptor":
               actual.setReceptor((String)e.getNewValue());
               break;
             
            case "condiciones":
               actual.setCondiciones((String)e.getNewValue());
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
               
            case "id_moneda":
               actual.setIdMoneda((int)e.getNewValue());
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
               
            case "pagado":
               actual.setPagado((float)e.getNewValue());
               break;
               
            case "saldo_actual":
               actual.setSaldoActual((float)e.getNewValue());
               break;
                 
         }
      }
   }

   //---------------------------------------------------------------------
   @Override
   public Object onGetValueAt(TableModelGetValueEvent<DocumentoComercial> e)
   {
      DocumentoComercial actual = e.getItem();

         switch (e.getDataProperty())
         {
            case "id":
               return actual.getId();
               
             case "id_emisor":
               return actual.getIdEmisor();

            case "id_receptor":
               return actual.getIdReceptor();
    
            case "receptor":
               return actual.getReceptor();

            case "condiciones":
               return actual.getCondiciones();

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
               
            case "id_moneda":
               return actual.getIdMoneda();

            case "id_estatus":
               return actual.getIdEstatus();

            case "id_tipo_comprobante":
               return actual.getIdTipoComprobante();
      
            case "es_aplicado":
               return actual.esAplicado();
              
            case "pagado":
               return actual.getPagado();
               
            case "saldo_actual":
               return actual.getSaldoActual();
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
            case "id_emisor":
            case "id_receptor":
            case "id_moneda":
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
            case "pagado":
            case "saldo_actual":
               return Float.class;
         }

         return getDefaultColumnClass();
   }
}