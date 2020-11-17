package com.syswave.forms.databinding;

import com.syswave.swing.models.POJOTableModel;
import com.syswave.swing.table.events.TableModelCellFormatEvent;
import com.syswave.swing.table.events.TableModelGetValueEvent;
import com.syswave.swing.table.events.TableModelSetValueEvent;
import com.syswave.entidades.miempresa_vista.VarianteIdentificadorVista;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class VarianteIdentificadorVistaTableModel extends POJOTableModel<VarianteIdentificadorVista>
{
   
   public VarianteIdentificadorVistaTableModel()
   {
      super();
   }
   
   public VarianteIdentificadorVistaTableModel(String[] columns)
   {
      super(columns);
   }
   
   @Override
   public void onSetValueAt(TableModelSetValueEvent<VarianteIdentificadorVista> e)
   {
      VarianteIdentificadorVista actual = e.getItem();
      
      switch (e.getDataProperty())
      {
         case "valor":
            actual.setValor((String)e.getNewValue());
            break;
            
         case "id_variante":
             actual.setIdVariante((int)e.getNewValue());
            break;
            
         case "id_tipo_identificador":
            actual.setIdTipoIdentificador((int)e.getNewValue());
            break;
      }
      
   }

   @Override
   public Object onGetValueAt(TableModelGetValueEvent<VarianteIdentificadorVista> e)
   {
      VarianteIdentificadorVista actual = e.getItem();
      
      switch (e.getDataProperty())
      {
         case "valor":
            return actual.getValor();

         case "id_variante":
           return actual.getIdVariante();
            
         case "id_tipo_identificador":
            return actual.getIdTipoIdentificador();
            
           case "llave":
            return actual.getLlave();
              
          case "descripcion":
            return actual.getDescripcion();
             
          case "es_activo":
            return actual.esActivo();
      }   
      
      return null;
   }

   @Override
   public Class<?> onGetColumnClass(TableModelCellFormatEvent e)
   {
      switch (e.getDataProperty())
      {
         case "id_variante":
                 
         case "id_tipo_identificador":
           return Integer.class;
            
          case "es_activo":
            return Boolean.class;
      }   
           
      return getDefaultColumnClass();
   }  

   @Override
   public boolean isCellEditable(int rowIndex, int columnIndex)
   {
      return columnIndex < 1; //To change body of generated methods, choose Tools | Templates.
   }
   
   
}