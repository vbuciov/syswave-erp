package com.syswave.forms.databinding;

import com.syswave.entidades.miempresa.Puesto;
import com.syswave.swing.models.POJOComboBoxModel;

/**
 *
 * @author sis5
 */
public class PuestoComboBoxModel extends POJOComboBoxModel<Puesto>
{

    //------------------------------------------------------------------
    @Override
    public String onGetText(Puesto item)
    {
        return item.getNombre();
    }

    //------------------------------------------------------------------
    @Override
    public void onSetText(Puesto item, String value)
    {
        item.setNombre(value);
    }

    //------------------------------------------------------------------
    @Override
    public Object onGetValue(Puesto item)
    {
        return item.getId();
    }
    
}
