package com.syswave.swing.table.events;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class TableModelGetValueEvent<E>
{
   private E item;
   private int rowIndex, colIndex;
   private String dataProperty;
   
   //---------------------------------------------------------------------
   public TableModelGetValueEvent()
   {
      rowIndex = -1;
      colIndex = -1;
      dataProperty = "";
   }
   
   //---------------------------------------------------------------------
   public void set (E item, int rowIndex, int colIndex, String dataProperty)
   {
      this.item = item;
      this.rowIndex = rowIndex;
      this.colIndex = colIndex;
      this.dataProperty = dataProperty;
   }

   //---------------------------------------------------------------------
   public E getItem()
   {
      return item;
   }

   //---------------------------------------------------------------------
   public int getRowIndex()
   {
      return rowIndex;
   }

   //---------------------------------------------------------------------
   public int getColIndex()
   {
      return colIndex;
   }

   //---------------------------------------------------------------------
   public String getDataProperty()
   {
      return dataProperty;
   }  
}