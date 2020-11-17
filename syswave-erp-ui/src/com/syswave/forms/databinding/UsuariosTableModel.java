package com.syswave.forms.databinding;

import com.syswave.swing.models.POJOTableModel;
import com.syswave.swing.table.events.TableModelCellFormatEvent;
import com.syswave.swing.table.events.TableModelGetValueEvent;
import com.syswave.swing.table.events.TableModelSetValueEvent;
import com.syswave.entidades.configuracion.Usuario;
import java.util.List;

/**
 * Permite ligar una lista de usuarios a una tabla.
 * @author Victor Manuel Bucio Vargas
 */
public class UsuariosTableModel extends POJOTableModel<Usuario>
{
//   enum usuarioColumns{Identificador, Constrasenya, Activo}
   Usuario sample;
   
   //---------------------------------------------------------------------
   private void initAtributes ()
   {
      sample = new Usuario();
   }

   //---------------------------------------------------------------------
   public UsuariosTableModel ()
   {
      super();
      initAtributes();
   }
   
   //---------------------------------------------------------------------
   public UsuariosTableModel (String[] columns)
   {
      super(columns);
      initAtributes ();
   }
   
   //---------------------------------------------------------------------
   public UsuariosTableModel (List<Usuario> dataSource)
   {
      super(dataSource);
      initAtributes ();
   }
    
   //--------------------------------------------------------------------
   @Override
   public void onSetValueAt(TableModelSetValueEvent<Usuario> e)
   {
      Usuario actual = e.getItem();
       switch (e.getDataProperty()) 
       {
         case "identificador": //Columna del Identificador
           actual.setIdentificador((String)e.getNewValue());
            break;
         case "clave": //Columna de la clave
           actual.setClave((String)e.getNewValue());
            break;
         case "es_activo": //Columna del estatus.
           actual.setActivo((boolean)e.getNewValue());
            break;
       }
   
       //Indicamos el cambio de modalidad.
       actual.setModified();
   }

   //---------------------------------------------------------------------
   @Override
   public Object onGetValueAt(TableModelGetValueEvent<Usuario> e)
   {
      Usuario actual = e.getItem();
      switch (e.getDataProperty()) 
      {
         case "identificador": //Columna del Identificador
           return actual.getIdentificador();
         case "clave": //Columna de la clave
           return actual.getClave();
         case "es_activo": //Columna del estatus.
           return actual.esActivo();            
       }
      
      return null;
   }
   
   //---------------------------------------------------------------------
   @Override
   public boolean isCellEditable(int rowIndex, int columnIndex)
   {
      return true; //Todo es editable
   }
   
   //---------------------------------------------------------------------
   @Override
   public Class<?> onGetColumnClass(TableModelCellFormatEvent e)
   {
      switch (e.getDataProperty()) {
         case "es_activo": //Columna del estatus.
           return Boolean.class;    
        
       }

      return getDefaultColumnClass();
   }
}