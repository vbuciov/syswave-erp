package com.syswave.swing.tree.editors;

import com.syswave.swing.models.POJOTreeModel;
import com.syswave.swing.tree.renders.POJOCheckBoxTreeCellRenderer;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.EventObject;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellEditor;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

/**
 * Permite editar las celdas de un JTree con un Checkbox.
 * @author Victor Manuel Bucio Vargas
 */
public class POJOCheckBoxTreeCellEditor<E> extends DefaultTreeCellEditor 
{
   DefaultMutableTreeNode editedNode;

   //---------------------------------------------------------------------
   public POJOCheckBoxTreeCellEditor()
   {
      super(null,  new POJOCheckBoxTreeCellRenderer(), new DefaultCellEditor(new JCheckBox()));      
      //Nota: El CellRender se pasa, para que le sea más sencillo al editor copiar los colores e iconos que se manejan.
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
               realValue = ((POJOTreeModel)model).onGetChecked((E)((DefaultMutableTreeNode)value).getUserObject());
            
            else 
               realValue = ((DefaultMutableTreeNode)value).getUserObject();
            
            editor = super.getTreeCellEditorComponent(tree, realValue, selected, expanded, leaf, row);
         
            if (hasData && editor instanceof EditorContainer)
            {
                EditorContainer content = (EditorContainer)editor;
                getCheckBoxFrom(content).setText( ((POJOTreeModel)model).onGetText( (E)  ((DefaultMutableTreeNode)value).getUserObject()));
            }
      }
      
      else
      {
         realValue = value;
         editor = super.getTreeCellEditorComponent(tree, realValue, selected, expanded, leaf, row);
      }
      
      return editor;
   }
   
   //---------------------------------------------------------------------
   public JCheckBox getCheckBoxFrom (EditorContainer content)
   {
      boolean seEncontro = false;
      JCheckBox checkPart = null;
      int i = 0;
      
      while (!seEncontro && i < content.getComponentCount())
      {
         seEncontro = content.getComponent(i) instanceof JCheckBox;
         
         if (seEncontro )
            checkPart = (JCheckBox)content.getComponent(i);
         else
            i++;
      }
      
      
      return checkPart;
   }
 

   //---------------------------------------------------------------------
   /**
    * Debido a que intencionalmente el DefaultTreeCellEditor se toma su tiempo
    * para entrar en modo de edición, y requerimos entrar a esta modalidad de forma
    * inmediata, fué necesario sobreescribir el método.
    * @param event
    * @return 
    */
   @Override
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
          
         /*editable = (lastPath != null && path != null &&
                               lastPath.equals(path));*/
          
          if (path!=null) 
          {
                        lastRow = tree.getRowForPath(path);
                        Object value = path.getLastPathComponent();
                        boolean isSelected = tree.isRowSelected(lastRow);
                        boolean expanded = tree.isExpanded(path);
                        TreeModel treeModel = tree.getModel();
                        boolean leaf = treeModel.isLeaf(value);
                        determineOffset(tree, value, isSelected,
                                        expanded, leaf, lastRow);
          }
                
         if(!realEditor.isCellEditable(event))
            return false;

         else
            returnValue = true;

         if(returnValue)
            prepareForEditing();
      }

      
      return returnValue;
   }
}