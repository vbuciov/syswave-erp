package com.syswave.swing.table.events;

/**
 *
 * @author victor
 */
public class TableModelCellFormatEvent
{
   
   private int colIndex;
   private String dataProperty;
   
   public TableModelCellFormatEvent()
   {
      colIndex = -1;
      dataProperty = "";
   }
   
   public void set(int columnIndex, String dataProperty)
   {
      this.colIndex = columnIndex;
      this.dataProperty = dataProperty;
   }

   public int getColIndex()
   {
      return colIndex;
   }

   public String getDataProperty()
   {
      return dataProperty;
   }
   
   
}
