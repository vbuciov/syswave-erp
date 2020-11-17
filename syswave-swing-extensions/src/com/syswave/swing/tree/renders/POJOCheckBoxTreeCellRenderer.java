package com.syswave.swing.tree.renders;

import com.syswave.swing.models.POJOTreeModel;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeModel;

//------------------------------------------------------------------
/**
 * Dibuja las celdas como si fueran un CheckBox acompañados de label.
 * @author Victor Manuel Bucio Vargas
 */
public class POJOCheckBoxTreeCellRenderer<E> extends DefaultTreeCellRenderer 
{
    JPanel cell;
    Dimension labelSize;
    FlowLayout flyCell;
    JCheckBox CheckBoxLeaf;    
    
    //--------------------------------------------------------------------
    public POJOCheckBoxTreeCellRenderer() 
    {
       flyCell = new FlowLayout();
       flyCell.setAlignment(FlowLayout.LEFT);
       flyCell.setHgap(0);
       flyCell.setVgap(0);
       flyCell.setAlignOnBaseline(true);
       cell = new JPanel();
       cell.setFocusable(true);
       cell.setOpaque(false);
       cell.setDoubleBuffered(true);
       cell.setLayout(flyCell);
       cell.add((JLabel)this);
       CheckBoxLeaf = new JCheckBox();
       cell.add(CheckBoxLeaf);
       this.setOpaque(false);
       labelSize = new Dimension();
    }
   
     //--------------------------------------------------------------------
    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value,
            boolean selected, boolean expanded, 
            boolean leaf, int row, boolean hasFocus) 
    {
         TreeModel model = tree.getModel();
         Component label;
         Object realValue;

         if ( model instanceof POJOTreeModel && value != null && value instanceof DefaultMutableTreeNode) 
         {
            //Nota: Los POJOTreeModel, pueden tener el elemento raíz como un simple String.
            if ( !(((DefaultMutableTreeNode)value).getUserObject() instanceof String))
            {
               realValue = ((POJOTreeModel)model).onGetText((E)((DefaultMutableTreeNode)value).getUserObject());
               CheckBoxLeaf.setSelected( ((POJOTreeModel)model).onGetChecked((E) ((DefaultMutableTreeNode)value).getUserObject()  ));
            }
            else
            {
               realValue = ((DefaultMutableTreeNode)value).getUserObject();
               CheckBoxLeaf.setSelected(false);
            }
         }

         //No debería utilizarse un render de POJOS, para otros modelos de arbol.
         else
         {
            realValue = value;
            CheckBoxLeaf.setSelected(false);
         }

         label = super.getTreeCellRendererComponent(tree, realValue, selected, expanded, leaf, row, hasFocus);
         CheckBoxLeaf.setText(getText());
         CheckBoxLeaf.setEnabled(label.isEnabled());
         CheckBoxLeaf.setBorder(getBorder());
         cell.setComponentOrientation(label.getComponentOrientation());
         cell.setForeground(label.getForeground());
         cell.setBackground(label.getBackground());
         setText("");

         labelSize.setSize( getIcon().getIconWidth(), getIcon().getIconHeight());
         this.setPreferredSize(labelSize);

         return cell;
    }
}