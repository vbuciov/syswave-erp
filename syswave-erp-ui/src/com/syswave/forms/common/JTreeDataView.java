package com.syswave.forms.common;

import com.syswave.swing.models.POJOTreeModel;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public abstract class JTreeDataView extends JDataView implements PropertyChangeListener
{

    private javax.swing.JScrollPane jspData;
    protected javax.swing.JTree jtData;
    private String searchWord;
    private List<TreeNode> stack;

    public JTreeDataView(IWindowContainer container)
    {
        super(container);
        jspData = new javax.swing.JScrollPane();
        jtData = new javax.swing.JTree((TreeModel) null);
        jspData.setViewportView(jtData);
        stack = new ArrayList<>();

        add(jspData, java.awt.BorderLayout.CENTER);
    }

    //-------------------------------------------------------------------
    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        //int progress = cargarOrigenesDatos.getProgress();
        if ("progress".equals(evt.getPropertyName()))
            jbProgress.setValue((Integer) evt.getNewValue());
    }

    //-------------------------------------------------------------------
    @Override
    public void goToFirst()
    {
        if (jtData != null && jtData.getRowCount() > 0)
        {
            jtData.setSelectionRow(0);
        }
    }

    //-------------------------------------------------------------------
    @Override
    public void goToLast()
    {
        if (jtData != null && jtData.getRowCount() > 0)
        {
            int lastIndex = jtData.getRowCount() - 1;
            jtData.setSelectionRow(lastIndex);
        }
    }

    //-------------------------------------------------------------------
    @Override
    public void goToNext()
    {
        if (jtData != null && jtData.getRowCount() > 0 && !jtData.getSelectionModel().isRowSelected(jtData.getRowCount() - 1))
        {
            int nextIndex = jtData.getMinSelectionRow() + 1;
            jtData.setSelectionRow(nextIndex);
        }
    }

    //-------------------------------------------------------------------
    @Override
    public void goToPrior()
    {
        if (jtData != null && jtData.getRowCount() > 0 && !jtData.getSelectionModel().isRowSelected(0))
        {
            int nextIndex = jtData.getMaxSelectionRow() - 1;
            jtData.setSelectionRow(nextIndex);
        }
    }

    //-------------------------------------------------------------------
    @Override
    public void selectAll()
    {
        if (jtData != null && jtData.getRowCount() > 0)
        {
            jtData.setSelectionInterval(0, jtData.getRowCount() - 1);
        }
    }

    // -------------------------------------------------------------------
    @Override
    public void doFilter(boolean value)
    {

        /* String input = JOptionPane.showInputDialog("Introduzca numero ó descripción:");
         TreeRowSorter sorter = (TreeRowSorter<TableModel>) jtbData.getRowSorter();
         
         if (input == null || input.isEmpty())
         {
         sorter.setRowFilter(null);
         jbMessage.setText("Busqueda deshecha");
         }
         else
         {
         sorter.setRowFilter(RowFilter.regexFilter("(?i)" + input));
         jbMessage.setText("Resultado de buscar:" + input);
         }*/
    }

    //---------------------------------------------------------------------
    @Override
    public void doPrint()
    {
    }

    //---------------------------------------------------------------------
    protected void printRecordCount()
    {
        TreeModel current = jtData.getModel();
        if (current instanceof POJOTreeModel)
            jbContador.setText(((POJOTreeModel) current).size() + " Registro(s)");

        else
            jbContador.setText(jtData.getRowCount() + " Registro(s)");
    }

    //---------------------------------------------------------------------
    @Override
    public void doExportToXLS()
    {
        if (jtData != null)
        {
        }
    }
    
    //---------------------------------------------------------------------
    @Override
    public void doStartSearch()
    {
        searchWord = "";
        doNextSearch();
    }

    //---------------------------------------------------------------------
    @Override
    public void doNextSearch()
    {
        if (searchWord == null || searchWord.length() < 1)
            setSearchWord(JOptionPane.showInputDialog("Introduzca palabra buscada:"));

        if (searchWord != null && searchWord.length() > 0)
        {
            //Recorremos el árbol a profundidad DFS
            String currentValue;
            boolean seEncontro = false;
            TreeNode node;
            TreeModel model = jtData.getModel();

            while (stack.size() > 0 && !seEncontro)
            {
                node = stack.remove(0); //FIFO

                if (model instanceof POJOTreeModel && node instanceof DefaultMutableTreeNode)
                {
                    Object UserObject = ((DefaultMutableTreeNode) node).getUserObject();

                    if (UserObject instanceof String)
                        currentValue = (String) UserObject;
                    else
                        currentValue = ((POJOTreeModel) model).onGetText(UserObject);
                }

                else
                    currentValue = node.toString();

                if (currentValue.toUpperCase().contains(searchWord))
                {
                    setSelectedNode(node);
                    seEncontro = true;
                }
                
                if (node.getChildCount() > 0)
                {
                    for (int i = 0; i<node.getChildCount(); i++)
                        stack.add(node.getChildAt(i));
                }

            }

            if (!seEncontro)
            {
                searchWord = "";
                JOptionPane.showMessageDialog(mainContainer.getRootFrame(), "Se llego al final");
            }
        }
    }

    //---------------------------------------------------------------------
    public void setSearchWord(String word)
    {
        if (word != null)
            searchWord = word.toUpperCase();
        stack.clear();
        stack.add((TreeNode) jtData.getModel().getRoot());
    }

    //---------------------------------------------------------------------
    private void setSelectedNode(TreeNode node)
    {
        TreePath path = getPath(node);
        jtData.scrollPathToVisible(path);
        jtData.setSelectionPath(path);
    }

    //---------------------------------------------------------------------
    TreePath getPath(TreeNode current)
    {
        // Get node depth
        int depth = 0;
        for (TreeNode node = current; node != null; node = node.getParent())
            depth++;

        TreeNode[] path = new TreeNode[depth];
        for (TreeNode node = current; node != null; node = node.getParent())
            path[--depth] = node; // reverse fill array

        return new TreePath(path);
    }

}
