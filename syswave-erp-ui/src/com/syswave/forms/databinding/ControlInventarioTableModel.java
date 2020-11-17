package com.syswave.forms.databinding;

import com.syswave.swing.models.POJOTableModel;
import com.syswave.swing.table.events.TableModelCellFormatEvent;
import com.syswave.swing.table.events.TableModelGetValueEvent;
import com.syswave.swing.table.events.TableModelSetValueEvent;
import com.syswave.entidades.miempresa.ControlInventario;
import java.util.Date;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class ControlInventarioTableModel extends POJOTableModel<ControlInventario>
{
   //---------------------------------------------------------------------
   public ControlInventarioTableModel()
   {
      super();
   }
   
   //---------------------------------------------------------------------
   public ControlInventarioTableModel(String[] columns)
   {
      super (columns);
   }

   //---------------------------------------------------------------------
   @Override
   public void onSetValueAt(TableModelSetValueEvent<ControlInventario> e)
   {
       ControlInventario actual = e.getItem();
       
       if (e.getNewValue() != null)
       {
         switch (e.getDataProperty())
         {
            case "consecutivo":
               actual.setConsecutivo((int)e.getNewValue());
               break;

            case "id_variante":
               actual.setIdVariante((int)e.getNewValue());
               break;

            case "lote":
               actual.setLote((String)e.getNewValue());
               break;

            case "existencia":
               actual.setExistencia((float)e.getNewValue());
               break;

            case "minimo":
               actual.setMinimo((float)e.getNewValue());
               break;

            case "maximo":
               actual.setMaximo((float)e.getNewValue());
               break;

            case "reorden":
               actual.setReorden((float)e.getNewValue());
               break;

            case "fecha_entrada":
               actual.setFecha_entrada((Date)e.getNewValue());
               break;

            case "fecha_caducidad":
               actual.setFecha_caducidad((Date)e.getNewValue());
               break;

            case "fecha_devolucion":
               actual.setFecha_devolucion((Date)e.getNewValue());
               break;
         }
       }
   }

   //---------------------------------------------------------------------
   @Override
   public Object onGetValueAt(TableModelGetValueEvent<ControlInventario> e)
   {
      ControlInventario actual = e.getItem();
       
       switch (e.getDataProperty())
       {
          case "consecutivo":
             return actual.getConsecutivo();
          
          case "id_variante":
             return actual.getIdVariante();
          
          case "lote":
             return actual.getLote();
           
          case "existencia":
             return actual.getExistencia();
          
          case "minimo":
             return actual.getMinimo();
             
          case "maximo":
             return actual.getMaximo();
           
          case "reorden":
             return actual.getReorden();
             
          case "fecha_entrada":
             return actual.getFecha_entrada();
             
          case "fecha_caducidad":
             return actual.getFecha_caducidad();
             
          case "fecha_devolucion":
             return actual.getFecha_devolucion();
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
          case "id_variante":
             return Integer.class;
           
          case "existencia":
          case "minimo":
          case "maximo":
          case "reorden":
             return Float.class;
             
          case "fecha_entrada":  
          case "fecha_caducidad":  
          case "fecha_devolucion":
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