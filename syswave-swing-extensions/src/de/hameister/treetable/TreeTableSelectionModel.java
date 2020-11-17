package de.hameister.treetable;

import javax.swing.ListSelectionModel;
import javax.swing.tree.DefaultTreeSelectionModel;

/**
 * Adds only to get a ListSelectionModel property
 * @author Victor Manuel Bucio Vargas
 */
public class TreeTableSelectionModel extends DefaultTreeSelectionModel
{
    /*TreeTableSelectionModel ()
    {
        super();
    }
    
    TreeTableSelectionModel( DefaultListSelectionModel value)
    {
        listSelectionModel = value;
         selectionMode = DISCONTIGUOUS_TREE_SELECTION;
        leadIndex = leadRow = -1;
        uniquePaths = new Hashtable<TreePath, Boolean>();
        lastPaths = new Hashtable<TreePath, Boolean>();
        tempPaths = new TreePath[1];
    }*/
    
    ListSelectionModel getListSelectionModel()
    {
        return listSelectionModel;
    }
}
