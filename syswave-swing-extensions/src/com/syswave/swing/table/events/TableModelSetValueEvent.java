package com.syswave.swing.table.events;

/**
 *
 * @author Victor Manuel Bucio Vargas
 * @param <E>
 */
public class TableModelSetValueEvent<E> extends TableModelGetValueEvent<E> 
{
   private Object anValue;
   
   //---------------------------------------------------------------------
   public TableModelSetValueEvent()
   {
      super();
   }
   
   //---------------------------------------------------------------------
   public void set ( Object anValue, E item, int rowIndex, int colIndex, String dataProperty)
   {
      super.set(item, rowIndex, colIndex, dataProperty);
      this.anValue = anValue;
   }
   
   //---------------------------------------------------------------------
   public Object getNewValue()
   {
      return anValue;
   }
}
