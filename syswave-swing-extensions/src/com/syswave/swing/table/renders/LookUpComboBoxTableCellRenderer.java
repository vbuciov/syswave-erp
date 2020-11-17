package com.syswave.swing.table.renders;

import com.syswave.swing.renders.POJOListCellRenderer;
import com.syswave.swing.models.POJOComboBoxModel;
import java.awt.Component;
import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.MutableComboBoxModel;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Victor Manuel Bucio Vargas
 * @param <E>
 */
public class LookUpComboBoxTableCellRenderer<E> extends DefaultTableCellRenderer
{
   JComboBox comboCell;
   boolean useDropDownStyle;
   
   //---------------------------------------------------------------------
   private void initAtributes ()
   {
      useDropDownStyle = false;
      comboCell = new JComboBox();
      comboCell.setRenderer(new POJOListCellRenderer<E>());
   }
   
   //---------------------------------------------------------------------
   public LookUpComboBoxTableCellRenderer ()
   {
      initAtributes ();
   }
   
   //---------------------------------------------------------------------
   public LookUpComboBoxTableCellRenderer(ComboBoxModel dataSource)
   {
      initAtributes ();    
      comboCell.setModel(dataSource);
   }
   
   //---------------------------------------------------------------------
   public LookUpComboBoxTableCellRenderer(ComboBoxModel dataSource, boolean withDropDownStyle)
   {
      initAtributes ();
      useDropDownStyle = withDropDownStyle;
      comboCell.setModel(dataSource);
   }
   
   //---------------------------------------------------------------------
   public void setModel (ComboBoxModel dataSource)
   {
      comboCell.setModel(dataSource);
   }
   
   //---------------------------------------------------------------------
   public ComboBoxModel getModel ()
   {
      return comboCell.getModel();
   }
   
   //---------------------------------------------------------------------
   public boolean isUseDropDownStyle()
   {
      return useDropDownStyle;
   }

   //---------------------------------------------------------------------
   public void setUseDropDownStyle(boolean useDropDownStyle)
   {
      this.useDropDownStyle = useDropDownStyle;
   }
     
   //---------------------------------------------------------------------
   @Override
   public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
   {
       Object realValue;
       MutableComboBoxModel<E> model = (MutableComboBoxModel<E>) comboCell.getModel();
       Component render;
   
             
      if (model instanceof POJOComboBoxModel && value != null)
      {
         POJOComboBoxModel<E> realModel =(POJOComboBoxModel<E>)model; 
         realValue = realModel.getElementAt( realModel.indexOfValue( value));  
         realModel.setSelectedItem(realValue);
         realValue = realModel.getSelectedText();
      }
      
      else
      {
         useDropDownStyle = false;
         realValue = value;
      }
      
      render = super.getTableCellRendererComponent(table, realValue,  isSelected, hasFocus, row, column); //To change body of generated methods, choose Tools | Templates.
      
      if (useDropDownStyle)
      {
         comboCell.setForeground(render.getForeground());
         comboCell.setBackground(render.getBackground());
         comboCell.setFont(render.getFont());
         comboCell.setBorder(getBorder());
         comboCell.setComponentOrientation(render.getComponentOrientation());
         comboCell.setEnabled(render.isEnabled());
       
         render = comboCell;
      }
      
      return render;
   }
   
   //---------------------------------------------------------------------
   /*private E FindValue (E item, POJOComboBoxModel<E> model)
   {
      E searched = null;
      boolean isFind = false;
      Object toFind = model.onGetValue(item);
      int i= 0;
      
      while( !isFind && i < model.getSize())
      {
         isFind = model.onGetValue( model.getElementAt(i)).equals(toFind);
         if (isFind)
            searched = model.getElementAt(i);
         else
            i++;
      }
      
      return searched;
   }*/
}
