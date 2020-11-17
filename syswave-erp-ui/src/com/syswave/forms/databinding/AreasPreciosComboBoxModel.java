package com.syswave.forms.databinding;

import com.syswave.entidades.miempresa.AreaPrecio;
import com.syswave.swing.models.POJOComboBoxModel;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class AreasPreciosComboBoxModel extends POJOComboBoxModel<AreaPrecio>
{

    //---------------------------------------------------------------------

    @Override
    public String onGetText(AreaPrecio item)
    {
        return item.getDescripcion();
    }

    //---------------------------------------------------------------------
    @Override
    public void onSetText(AreaPrecio item, String value)
    {
        item.setDescripcion(value);
    }

    //---------------------------------------------------------------------
    @Override
    public Object onGetValue(AreaPrecio item)
    {
        return item.getId();
    }
}
