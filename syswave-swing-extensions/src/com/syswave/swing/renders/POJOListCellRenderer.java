package com.syswave.swing.renders;

import com.syswave.swing.models.POJOComboBoxModel;
import java.awt.Component;
import javax.swing.AbstractListModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

/**
 * Este dibujador de celdas enriquecido permite trabajar con POJOS.
 * @author Victor Manuel Bucio Vargas
 * @param <E>
 */
public class POJOListCellRenderer<E> extends DefaultListCellRenderer
{
   //---------------------------------------------------------------------
   @Override
   public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
   {
      Object realValue;
      AbstractListModel model = (AbstractListModel) list.getModel();
            
      if (model instanceof POJOComboBoxModel && value != null)
         realValue = ((POJOComboBoxModel)model).onGetText( (E)value) ;
      
      else
         realValue = value;
               
      return super.getListCellRendererComponent(list, realValue, index, isSelected, cellHasFocus);
   }
}
