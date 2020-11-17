package com.syswave.swing.tree.renders;

import com.syswave.forms.common.INotifyTreeProperties;
import java.awt.Component;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeModel;

/**
 *
 * @author Victor Manuel Bucio Vargas
 * @param <E>
 */
public class POJOTreeCellRenderer<E> extends DefaultTreeCellRenderer
{
    //--------------------------------------------------------------------
    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value,
            boolean selected, boolean expanded, 
            boolean leaf, int row, boolean hasFocus) 
    {
      TreeModel model = tree.getModel();
      Component label;
      Object realValue;

      if ( model instanceof INotifyTreeProperties && value != null && value instanceof DefaultMutableTreeNode) 
      {
         //Nota: Los POJOTreeModel, pueden tener el elemento raíz como un simple String.
         if ( !(((DefaultMutableTreeNode)value).getUserObject() instanceof String))
            realValue = ((INotifyTreeProperties)model).onGetText((E)((DefaultMutableTreeNode)value).getUserObject());
           
         else
            realValue = ((DefaultMutableTreeNode)value).getUserObject();
      }

      //No debería utilizarse un render de POJOS, para otros modelos de arbol.
      else
         realValue = value;
      
      label = super.getTreeCellRendererComponent(tree, realValue, selected, expanded, leaf, row, hasFocus);

      return label;
    }
}
