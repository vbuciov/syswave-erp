package com.syswave.forms.databinding;

import com.syswave.swing.models.POJOTreeModel;
import com.syswave.entidades.configuracion.Unidad;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class UnidadesTreeModel extends POJOTreeModel<Unidad>
{

    @Override
    public String onGetText(Unidad item)
    {
        return item.getNombre();
    }

    @Override
    public void onSetText(Unidad item, String value)
    {
        item.setNombre(value);
    }

    @Override
    public Object onGetIDValue(Unidad item)
    {
        return item.getId();
    }

    @Override
    public boolean onGetChecked(Unidad element)
    {
        return element.isSelected();
    }

    @Override
    public void onSetChecked(Unidad element, boolean value)
    {
        element.setSelected(value);
    }

    @Override
    public Object onGetIDParentValue(Unidad element)
    {
        return element.getIdPadre();
    }

    @Override
    public int onGetLevel(Unidad element)
    {
        return element.getNivel();
    }

    @Override
    public void onModifiedAdded(Unidad element)
    {
        element.setModified();
    }

    @Override
    public boolean canAddToModified(Unidad element)
    {
        return !element.isSet();
    }

    @Override
    public Object getNullParent()
    {
        return 0;
    }

}
