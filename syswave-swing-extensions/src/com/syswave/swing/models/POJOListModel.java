package com.syswave.swing.models;

import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractListModel;

/**
 *
 * @author sis5
 */
public abstract class POJOListModel<E> extends AbstractListModel
{
    protected ArrayList<E> lista = new ArrayList<>();

    public int getSize()
    {
        return lista.size();
    }
    
    public void addData(List<E> ordenes)
    {
        for (int i = 0; i < ordenes.size(); i++)
        {
            lista.add(ordenes.get(i));
            //this.fireIntervalAdded(this, getSize(), getSize() + 1);
            this.fireContentsChanged(this, getSize(), getSize() + 1);
        }
    }
    
    public void clear()
    {
        lista.clear();
    }
    
    public List<E> getData()
    {
        return lista;
    }
}
