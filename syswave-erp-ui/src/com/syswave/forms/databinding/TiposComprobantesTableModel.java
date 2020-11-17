package com.syswave.forms.databinding;

import com.syswave.swing.models.POJOTableModel;
import com.syswave.swing.table.events.TableModelCellFormatEvent;
import com.syswave.swing.table.events.TableModelGetValueEvent;
import com.syswave.swing.table.events.TableModelSetValueEvent;
import com.syswave.entidades.miempresa.TipoComprobante;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class TiposComprobantesTableModel extends POJOTableModel<TipoComprobante>
{

   //---------------------------------------------------------------------
   public TiposComprobantesTableModel(String[] columns)
   {
      super(columns);
   }

   //---------------------------------------------------------------------
   @Override
   public void onSetValueAt(TableModelSetValueEvent<TipoComprobante> e)
   {
      TipoComprobante actual = e.getItem();

      if (e.getNewValue() != null)
      {
         switch (e.getDataProperty())
         {
            case "id":
               actual.setId((int) e.getNewValue());
               break;

            case "nombre":
               actual.setNombre((String) e.getNewValue());
               break;

            case "afecta_inventario":
               actual.setAfecta_inventario((Boolean) e.getNewValue());
               break;

            case "es_entrada":
               actual.setEntrada((Boolean) e.getNewValue());
               break;

            case "es_activo":
               actual.setActivo((Boolean) e.getNewValue());
               break;

            case "permite_precios":
               actual.setPermitePrecios((int) e.getNewValue());
               break;

            case "condicion_pago":
               actual.setCondicion_pago((int) e.getNewValue());
               break;

            case "afecta_saldos":
               actual.setAfectaSaldos((boolean) e.getNewValue());
               break;

            case "es_tipo_saldo":
               actual.setTipoSaldo((boolean) e.getNewValue());
               break;

            case "es_comercial":
               actual.setComercial((boolean) e.getNewValue());
               break;

            /*case "usa_tipo_precio":
               actual.setUsaTipoPrecio((boolean) e.getNewValue());
               break;*/

            case "id_padre":
               actual.setIdPadre((int) e.getNewValue());
               break;

            case "nivel":
               actual.setNivel((int) e.getNewValue());
               break;
         }
      }
   }

   //---------------------------------------------------------------------
   @Override
   public Object onGetValueAt(TableModelGetValueEvent<TipoComprobante> e)
   {
      TipoComprobante actual = e.getItem();

      switch (e.getDataProperty())
      {
         case "id":
            return actual.getId();

         case "nombre":
            return actual.getNombre();

         case "afecta_inventario":
            return actual.esAfecta_inventario();

         case "es_entrada":
            return actual.esEntrada();

         case "es_activo":
            return actual.esActivo();

         case "permite_precios":
            return actual.getPermitePrecios();

         case "condicion_pago":
            return actual.getCondicionPago();

         case "afecta_saldos":
            return actual.esAfectaSaldos();

         case "es_tipo_saldo":
            return actual.esTipoSaldo();

         case "es_comercial":
            return actual.esComercial();

         /*case "usa_tipo_precio":
            return actual.esUsaTipoPrecio();*/

         case "id_padre":
            return actual.getIdPadre();

         case "nivel":
            return actual.getNivel();
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
         case "permite_precios":
         case "condicion_pago":
         case "id_padre":
         case "nivel":
            return Integer.class;

         case "afecta_inventario":
         case "es_entrada":
         case "es_activo":
         case "afecta_saldos":
         case "es_tipo_saldo":
         case "es_comercial":
         //case "usa_tipo_precio":
            return Boolean.class;
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
