package de.hameister.treetable;

import java.awt.Component;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author sis2
 */
public final class TreeDecoratorTableCellRenderer implements TableCellRenderer {
    /** Die letzte Zeile, die gerendert wurde. */
    protected int visibleRow;
    private final JTree decorate;
     
    //private final MyTreeTable treeTable;
     
    public TreeDecoratorTableCellRenderer(JTree toDecorate) {
        decorate = toDecorate;
        //super(model);
        //this.treeTable = treeTable;
         
        // Setzen der Zeilenhoehe fuer die JTable
        // Muss explizit aufgerufen werden, weil treeTable noch
        // null ist, wenn super(model) setRowHeight aufruft!
        //setRowHeight(getRowHeight());
    }
 
    /**
     * Tree und Table muessen die gleiche Hoehe haben.
     */
    /*@Override
    public void setRowHeight(int rowHeight) {
        if (rowHeight > 0) {
            super.setRowHeight(rowHeight);
            if (treeTable != null && treeTable.getRowHeight() != rowHeight) {
                treeTable.setRowHeight(getRowHeight());
            }
        }
    }*/
 
    /**
     * Tree muss die gleiche Hoehe haben wie Table.
     */
    /*@Override
    public void setBounds(int x, int y, int w, int h) {
        super.setBounds(x, 0, w, treeTable.getHeight());
    }*/
 
    /**
     * Sorgt fuer die Einrueckung der Ordner.
     */
    /*@Override
    public void paint(Graphics g) {
        g.translate(0, -visibleRow * getRowHeight());
         
        super.paint(g);
    }*/
     
    /**
     * Liefert den Renderer mit der passenden Hintergrundfarbe zurueck.
     * @return 
     */
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (isSelected)
            decorate.setBackground(table.getSelectionBackground());
        else
            decorate.setBackground(table.getBackground());
 
        visibleRow = row;

        return decorate;
    }

    public int getVisibleRow()
    {
        return visibleRow;
    }
    
    
}
