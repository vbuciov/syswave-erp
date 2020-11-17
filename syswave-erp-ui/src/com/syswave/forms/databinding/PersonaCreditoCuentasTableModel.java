package com.syswave.forms.databinding;

import com.syswave.swing.models.POJOTableModel;
import com.syswave.swing.table.events.TableModelCellFormatEvent;
import com.syswave.swing.table.events.TableModelGetValueEvent;
import com.syswave.swing.table.events.TableModelSetValueEvent;
import com.syswave.entidades.miempresa.PersonaCreditoCuenta;
import java.util.Date;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class PersonaCreditoCuentasTableModel extends POJOTableModel<PersonaCreditoCuenta>
{   
    //---------------------------------------------------------------------
  public PersonaCreditoCuentasTableModel(String[] columns)
   {
      super(columns);
   }

   //---------------------------------------------------------------------
   @Override
   public void onSetValueAt(TableModelSetValueEvent<PersonaCreditoCuenta> e)
   {
        PersonaCreditoCuenta actual = e.getItem();

      if (e.getNewValue() != null)
      {
         switch (e.getDataProperty())
         {
            case "consecutivo":
               actual.setConsecutivo((int) e.getNewValue());
               break;

            case "id_persona":
               actual.setIdPersona((int) e.getNewValue());
               break;

            case "numero":
               actual.setNumero((String) e.getNewValue());
               break;

            case "saldo_actual":
               actual.setSaldoActual((float) e.getNewValue());
               break;

            case "saldo_limite":
               actual.setSaldoLimite((float) e.getNewValue());
               break;
               
            case "id_moneda":
               actual.setIdMoneda((int) e.getNewValue());
               break;
               
            case "fecha_inicial":
               actual.setFechaInicial((Date)e.getNewValue());
               break;
               
            case "es_tipo": 
               actual.setEsTipo((int)e.getNewValue());
               break;
               
            case "es_activo":
               actual.setActivo((boolean)e.getNewValue());
               break;
               
            case "observacion":
               actual.setObservacion((String)e.getNewValue());
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
   public Object onGetValueAt(TableModelGetValueEvent<PersonaCreditoCuenta> e)
   {
      PersonaCreditoCuenta actual = e.getItem();

       switch (e.getDataProperty())
         {
            case "consecutivo":
               return actual.getConsecutivo();

            case "id_persona":
               return actual.getIdPersona();

            case "numero":
               return actual.getNumero();
        
            case "saldo_actual":
               return actual.getSaldoActual();
        
            case "saldo_limite":
               return actual.getSaldoLimite();
               
            case "id_moneda":
               return actual.getIdMoneda();
               
            case "fecha_inicial":
               return actual.getFechaInicial();
               
            case "es_tipo": 
               return actual.getEsTipo();
               
            case "es_activo":
               return actual.esActivo();
               
            case "observacion":
               return actual.getObservacion();
               
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
            case "id_persona":
            case "id_moneda":
            case "es_tipo":
               return Integer.class;
               
            case "es_activo":
               return Boolean.class;
            
            case "saldo_actual":
            case "saldo_limite":
               return Float.class;
           
            case "fecha_inicial":
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
