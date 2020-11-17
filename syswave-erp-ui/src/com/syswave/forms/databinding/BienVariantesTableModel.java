package com.syswave.forms.databinding;

import com.syswave.swing.models.POJOTableModel;
import com.syswave.entidades.miempresa.BienVariante;
import com.syswave.swing.table.events.TableModelCellFormatEvent;
import com.syswave.swing.table.events.TableModelGetValueEvent;
import com.syswave.swing.table.events.TableModelSetValueEvent;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class BienVariantesTableModel extends POJOTableModel<BienVariante>
{

   //---------------------------------------------------------------------
   public BienVariantesTableModel()
   {
      super();
   }

   //---------------------------------------------------------------------
   public BienVariantesTableModel(String[] columns)
   {
      super(columns);
   }

   //---------------------------------------------------------------------
   @Override
   public void onSetValueAt(TableModelSetValueEvent<BienVariante> e)
   {
      BienVariante actual = e.getItem();

      switch (e.getDataProperty())
      {
         case "descripcion":
            actual.setDescripcion((String) e.getNewValue());
            break;

         /*case "masa":
            actual.setMasa((float) e.getNewValue());
            break;

         case "id_unidad_masa":
            if (e.getNewValue() != null)
               actual.setIdUnidadMasa((int) e.getNewValue());
            break;

         case "ancho":
            actual.setAncho((float) e.getNewValue());
            break;

         case "alto":
            actual.setAlto((float) e.getNewValue());
            break;

         case "largo":
            actual.setLargo((float) e.getNewValue());
            break;

         case "id_unidad_longitud":
            if (e.getNewValue() != null)
               actual.setIdUnidadLongitud((int) e.getNewValue());
            break;*/

         case "es_activo":
            actual.setEsActivo((boolean) e.getNewValue());
            break;

         case "es_inventario":
            actual.setEsInventario((boolean) e.getNewValue());
            break;

         case "es_comercializar":
            actual.setEsComercializar((boolean) e.getNewValue());
            break;
            
         case  "id_bien":
            actual.setIdBien((int)e.getNewValue());
            break;
      }
   }

   //---------------------------------------------------------------------
   @Override
   public Object onGetValueAt(TableModelGetValueEvent<BienVariante> e)
   {
      BienVariante actual = e.getItem();
      switch (e.getDataProperty())
      {
         case "descripcion":
            return actual.getDescripcion();

         /*case "masa":
            return actual.getMasa();

         case "id_unidad_masa":
            return actual.getIdUnidadMasa();

         case "ancho":
            return actual.getAncho();

         case "alto":
            return actual.getAlto();

         case "largo":
            return actual.getLargo();

         case "id_unidad_longitud":
            return actual.getIdUnidadLongitud();*/

         case "es_activo":
            return actual.esActivo();

         case "es_inventario":
            return actual.esInventario();

         case "es_comercializar":
            return actual.EsComercializar();
            
         case "id_bien":
                 return actual.getIdBien();

         default:
            return null;
      }
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
         case "masa":
         case "ancho":
         case "alto":
         case "largo":
            return Float.class;
         
         //case "id_unidad_masa":
         //case "id_unidad_longitud":
         case "id_bien":
            return Integer.class;
            
         case "es_activo":
         case "es_inventario":
         case "es_comercializar":
            return Boolean.class;

         default:
            return getDefaultColumnClass();
      }
   }
}
