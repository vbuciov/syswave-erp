package com.syswave.forms.databinding;

import com.syswave.swing.models.POJOTableModel;
import com.syswave.swing.table.events.TableModelCellFormatEvent;
import com.syswave.swing.table.events.TableModelGetValueEvent;
import com.syswave.swing.table.events.TableModelSetValueEvent;
import com.syswave.entidades.miempresa_vista.ControlPrecioVista;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class ControlPrecioVistaTableModel extends POJOTableModel<ControlPrecioVista>
{

   //---------------------------------------------------------------------
   public ControlPrecioVistaTableModel(String[] columns)
   {
      super(columns);
   }

   //---------------------------------------------------------------------
   @Override
   public void onSetValueAt(TableModelSetValueEvent<ControlPrecioVista> e)
   {
      ControlPrecioVista actual = e.getItem();

      if (e.getNewValue() != null)
      {
         switch (e.getDataProperty())
         {
            case "id":
               actual.setId((int) e.getNewValue());
               break;

            case "presentacion":
               actual.setPresentacion((String) e.getNewValue());
               break;

            case "es_servicio":
               actual.setEsServicio((int) e.getNewValue());
               break;

            case "id_categoria":
               actual.setIdCategoria((int) e.getNewValue());
               break;

            case "categoria":
               actual.setCategoria((String) e.getNewValue());
               break;

            case "id_area_precio":
               actual.setIdAreaPrecio((int) e.getNewValue());
               break;

            case "area_precio":
               actual.setAreaPrecio((String) e.getNewValue());
               break;

            case "descripcion":
               actual.setDescripcion((String) e.getNewValue());
               break;

            case "es_tipo":
               actual.setEsTipo((int) e.getNewValue());
               break;

            case "mercado":
               actual.setPrecioFinal((float) e.getNewValue());
               break;

            case "id_moneda":
               actual.setIdMoneda((int) e.getNewValue());
               break;

            case "moneda":
               actual.setMoneda((String) e.getNewValue());
               break;

            case "id_variante":
               actual.setIdVariante((int) e.getNewValue());
               break;

            case "id_bien":
               actual.setIdBien((int) e.getNewValue());
               break;

            case "tipo_bien":
               actual.setTipoBien((String) e.getNewValue());
               break;

            /*case "id_unidad_masa":
               actual.setIdUnidadMasa((int) e.getNewValue());
               break;

            case "unidad":
               actual.setUnidad((String) e.getNewValue());
               break;*/

            case "es_activo":
               actual.setActivo((boolean) e.getNewValue());
               break;

            case "es_comercializar":
               actual.setComercializar((boolean) e.getNewValue());
               break;

            case "es_inventario":
               actual.setEsInventario((boolean) e.getNewValue());
               break;

            case "identificador":
               actual.setIdentificador_muestra((String) e.getNewValue());
               break;

            case "existencia":
               actual.setExistencia_muestra((float) e.getNewValue());
               break;

            /*case "valor":
             actual.setCostoDirecto((float) e.getNewValue());
             break;

             case "margen":
             actual.setMargen((float) e.getNewValue());
             break;

             case "factor":
             actual.setFactor((int) e.getNewValue());
             break;

             case "tiene_analisis":
             actual.setTieneAnalisis((boolean) e.getNewValue());
             break;*/
         }
      }
   }

   //---------------------------------------------------------------------
   @Override
   public Object onGetValueAt(TableModelGetValueEvent<ControlPrecioVista> e)
   {
      ControlPrecioVista actual = e.getItem();
      switch (e.getDataProperty())
      {
         case "id":
            return actual.getId();

         case "presentacion":
            return actual.getPresentacion();

         case "es_servicio":
            return actual.getEsServicio();

         case "id_categoria":
            return actual.getIdCategoria();

         case "categoria":
            return actual.getCategoria();

         case "id_area_precio":
            return actual.getIdAreaPrecio();

         case "area_precio":
            return actual.getAreaPrecio();

         case "descripcion":
            return actual.getDescripcion();

         case "es_tipo":
            return actual.getEsTipo();

         case "mercado":
            return actual.getPrecioFinal();

         case "id_moneda":
            return actual.getIdMoneda();

         case "moneda":
            return actual.getMoneda();

         case "id_variante":
            return actual.getIdVariante();

         case "id_bien":
            return actual.getIdBien();

         case "tipo_bien":
            return actual.getTipoBien();

         /* Las unidades no son una etiqueta aparte, si no que 
            son parte de la descripción de una presentación.
            case "id_unidad_masa":
            return actual.getIdUnidadMasa();

         case "unidad":
            return actual.getUnidad();*/

         case "es_activo":
            return actual.esActivo();

         case "es_comercializar":
            return actual.esComercializar();

         case "es_inventario":
            return actual.esInventario();

         case "identificador":
            return actual.getIdentificador_muestra();

         case "existencia":
            return actual.getExistencia_muestra();

         /*case "valor":
          return actual.getCostoDirecto();

          case "margen":
          return actual.getMargen();

          case "factor":
          return actual.getFactor();

          case "tiene_analisis":
          return actual.tieneAnalisis();*/
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
         case "es_servicio":
         case "id_categoria":
         case "es_tipo":
         case "id_moneda":
         case "id_variante":
         case "id_bien":
         //case "factor":
         case "id_area_precio":
         //case "id_unidad_masa":
            return Integer.class;

         case "mercado":
         case "existencia":
         //case "valor":
         //case "margen":
            return Float.class;

         case "es_activo":
         case "es_comercializar":
         case "es_inventario":
         //case "tiene_analisis":
            return Boolean.class;
      }

      return getDefaultColumnClass();
   }

   //---------------------------------------------------------------------
   @Override
   public boolean isCellEditable(int rowIndex, int columnIndex)
   {
      return false;
   }
}
