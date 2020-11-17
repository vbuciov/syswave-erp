package com.syswave.forms.databinding;

import com.syswave.swing.models.POJOTableModel;
import com.syswave.swing.table.events.TableModelCellFormatEvent;
import com.syswave.swing.table.events.TableModelGetValueEvent;
import com.syswave.swing.table.events.TableModelSetValueEvent;
import com.syswave.entidades.miempresa_vista.PersonaDireccion_tiene_Documento_5FN;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class PersonaDireccionDocumentos5FNTableModel extends POJOTableModel<PersonaDireccion_tiene_Documento_5FN>
{
   //---------------------------------------------------------------------
   public PersonaDireccionDocumentos5FNTableModel (String[] columns)
   {
      super (columns);
   }

   //---------------------------------------------------------------------
   @Override
   public void onSetValueAt(TableModelSetValueEvent<PersonaDireccion_tiene_Documento_5FN> e)
   {
      if (e.getNewValue() != null)
      {
         PersonaDireccion_tiene_Documento_5FN actual = e.getItem();
         
         switch (e.getDataProperty())
         {
            case "rol":
               actual.setRol((String)e.getNewValue());
               break;
               
            case "id_persona":
               actual.setIdPersona((int)e.getNewValue());
               break;
               
            case "cosecutivo":
               actual.setConsecutivo((int)e.getNewValue());
               break;
               
            case "id_direccion":
                String[] keys = ((String) e.getNewValue()).split(",");
                actual.setConsecutivo(Integer.parseInt(keys[0]));
                actual.setIdPersona(Integer.parseInt(keys[1]));
               break;
               
            case "id_documento":
               actual.setIdDocumento((int)e.getNewValue());
               break;
               
            case "es_rol":
               actual.setRol((int)e.getNewValue());
               break;
               
            case "nombre":
               actual.setNombre((String)e.getNewValue());
               break;
               
            case "tipo_persona":
               actual.setTipo_persona((String)e.getNewValue());
               break;
               
            case "codigo_postal":
               actual.setCodigoPostal((String)e.getNewValue());
               break;
               
            case "localidad":
               actual.setLocalidad((String)e.getNewValue());
               break;
               
            case "calle":
               actual.setCalle((String)e.getNewValue());
               break;
               
            case "colonia":
               actual.setColonia((String)e.getNewValue());
               break;
               
            case "no_exterior":
               actual.setNoExterior((String)e.getNewValue());
               break;
               
            case "no_interior":
               actual.setNoInterior((String)e.getNewValue());
               break;
         }
      }
   }

   //---------------------------------------------------------------------
   @Override
   public Object onGetValueAt(TableModelGetValueEvent<PersonaDireccion_tiene_Documento_5FN> e)
   {
         PersonaDireccion_tiene_Documento_5FN actual = e.getItem();
         
         switch (e.getDataProperty())
         {
            case "rol":
               return actual.getRol();
               
            case "id_persona":
               return actual.getIdPersona();
               
            case "cosecutivo":
               return actual.getConsecutivo();
               
            case "id_direccion":
               return actual.getIdDireccion();
               
            case "id_documento":
               return actual.getIdDocumento();

            case "es_rol":
               return actual.getEsRol();
     
            case "nombre":
               return actual.getNombre();
               
            case "tipo_persona":
               return actual.getTipo_persona();
               
            case "codigo_postal":
               return actual.getCodigoPostal();

            case "localidad":
               return actual.getLocalidad();
               
            case "calle":
               return actual.getCalle();
               
            case "colonia":
               return actual.getColonia();

            case "no_exterior":
               return actual.getNoExterior();
               
            case "no_interior":
               return actual.getNoInterior();
         }
         
         return null;
   }

   //---------------------------------------------------------------------
   @Override
   public Class<?> onGetColumnClass(TableModelCellFormatEvent e)
   {         
      switch (e.getDataProperty())
      { 
         case "id_persona":
         case "cosecutivo":
         case "id_documento":
         case "es_rol":
            return Integer.class;
      }

      return getDefaultColumnClass();
   }  
}