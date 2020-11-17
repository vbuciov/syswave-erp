package com.syswave.forms.databinding;

import com.syswave.swing.models.POJOTableModel;
import com.syswave.swing.table.events.TableModelCellFormatEvent;
import com.syswave.swing.table.events.TableModelGetValueEvent;
import com.syswave.swing.table.events.TableModelSetValueEvent;
import com.syswave.entidades.miempresa_vista.MonedaCambioVista;
import java.util.Date;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class MonedaCambioVistaTableModel extends POJOTableModel<MonedaCambioVista>
{

   //---------------------------------------------------------------------
   public MonedaCambioVistaTableModel(String[] columns)
   {
      super(columns);
   }

   //---------------------------------------------------------------------
   @Override
   public void onSetValueAt(TableModelSetValueEvent<MonedaCambioVista> e)
   {
      MonedaCambioVista actual = e.getItem();

      if (e.getNewValue() != null)
      {
         switch (e.getDataProperty())
         {
            case "consecutivo":
               actual.setConsecutivo((int) e.getNewValue());
               break;

            case "origen":
               actual.setOrigen((String) e.getNewValue());
               break;

            case "id_moneda_origen":
               actual.setIdMonedaOrigen((int) e.getNewValue());
               break;

            case "proporcion":
               actual.setProporcion((float) e.getNewValue());
               break;

            case "destino":
               actual.setDestino((String) e.getNewValue());
               break;

            case "id_moneda_destino":
               actual.setIdMonedaDestino((int) e.getNewValue());
               break;

            case "fecha_validez":
               actual.setFecha_validez((Date) e.getNewValue());
               break;

            /*case "id_moneda_cambio":
             String[] keys = ((String) e.getNewValue()).split(",");
             actual.setConsecutivo(Integer.parseInt(keys[0]));
             actual.set(Integer.parseInt(keys[1]));
             break;*/
         }
      }

   }

   //---------------------------------------------------------------------
   @Override
   public Object onGetValueAt(TableModelGetValueEvent<MonedaCambioVista> e)
   {
      MonedaCambioVista actual = e.getItem();

      switch (e.getDataProperty())
      {
         case "consecutivo":
            return actual.getConsecutivo();

         case "origen":
            return actual.getOrigen();

         case "id_moneda_origen":
            return actual.getIdMonedaOrigen();

         case "proporcion":
            return actual.getProporcion();

         case "destino":
            return actual.getDestino();

         case "id_moneda_destino":
            return actual.getIdMonedaDestino();

         case "fecha_validez":
            return actual.getFecha_validez();

         /*case "id_moneda_cambio":
          String[] keys = ((String) e.getNewValue()).split(",");
          actual.setConsecutivo(Integer.parseInt(keys[0]));
          actual.set(Integer.parseInt(keys[1]));
          break;*/
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
         case "id_moneda_origen":
         case "id_moneda_destino":
            return Integer.class;

         case "proporcion":
            return Float.class;

         case "fecha_validez":
            return Date.class;
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
