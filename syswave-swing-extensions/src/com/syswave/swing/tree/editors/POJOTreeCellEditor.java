package com.syswave.swing.tree.editors;

import com.syswave.swing.models.POJOTreeModel;
import com.syswave.swing.tree.renders.POJOTreeCellRenderer;
import java.awt.Component;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellEditor;
import javax.swing.tree.TreeModel;

/**
 *
 * @author Victor Manuel Bucio Vargas
 * @param <E>
 */
public class POJOTreeCellEditor<E> extends DefaultTreeCellEditor 
{
   
   public POJOTreeCellEditor ()
   {
     super(null,  new POJOTreeCellRenderer(), null);  
 
   }
   
     //---------------------------------------------------------------------
   @Override
   public Component getTreeCellEditorComponent(final JTree tree, Object value,
                                               boolean selected, boolean expanded, boolean leaf, int row)
   {
      TreeModel model = tree.getModel();
      Component editor = null;
      Object realValue;
      
      if ( model instanceof POJOTreeModel && value != null && value instanceof DefaultMutableTreeNode)
      {
            boolean hasData =  !(((DefaultMutableTreeNode)value).getUserObject() instanceof String);
        
            if ( hasData )
               realValue = ((POJOTreeModel)model).onGetText((E)((DefaultMutableTreeNode)value).getUserObject());
            
            else 
               realValue = ((DefaultMutableTreeNode)value).getUserObject();
            
            editor = super.getTreeCellEditorComponent(tree, realValue, selected, expanded, leaf, row);         
      }
      
      else
      {
         realValue = value;
         editor = super.getTreeCellEditorComponent(tree, realValue, selected, expanded, leaf, row);
      }
      
      return editor;
   }
   
   //---------------------------------------------------------------------
   /**
    * Debido a que intencionalmente el DefaultTreeCellEditor se toma su tiempo
    * para entrar en modo de edición, y requerimos entrar a esta modalidad de forma
    * inmediata, fué necesario sobreescribir el método.
    * @param event
    * @return 
    */
   /*@Override
   public boolean isCellEditable(EventObject event)
   {
      boolean returnValue = false;
 
      //Cuando la selección es dada por un evento de mouse.
      if (event instanceof MouseEvent && event.getSource() instanceof JTree)
      {
         MouseEvent mouseEvent = (MouseEvent) event;
           
         JTree treeOwner = (JTree)mouseEvent.getSource();
         TreePath path = treeOwner.getPathForLocation(mouseEvent.getX(),
                                                  mouseEvent.getY());
         
         setTree(treeOwner);
          
        //boolean editable = (lastPath != null && path != null &&
        //                       lastPath.equals(path));
          
          if (path!=null) 
          {
            lastRow = tree.getRowForPath(path);
            Object value = path.getLastPathComponent();
            boolean isSelected = tree.isRowSelected(lastRow);
            boolean expanded = tree.isExpanded(path);
            TreeModel treeModel = tree.getModel();
            boolean leaf = treeModel.isLeaf(value);

            //Determinate icons.
            determineOffset(tree, value, isSelected,
                            expanded, leaf, lastRow);
          }
                
         if(!realEditor.isCellEditable(event))
            return false;

         else
            returnValue = true  ;

         if(returnValue)
            prepareForEditing();
      }

      
      return returnValue;
   }*/
   
}
