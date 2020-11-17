package com.syswave.forms.databinding;

import com.syswave.entidades.miempresa.Valor;
import com.syswave.swing.models.POJOComboBoxModel;

/**
 *
 * @author sis5
 */
public class AreaComboBoxModel extends POJOComboBoxModel<Valor>
{

    //------------------------------------------------------------------
    @Override
    public String onGetText(Valor item)
    {
        return item.getValor();
    }

    //------------------------------------------------------------------
    @Override
    public void onSetText(Valor item, String value)
    {
        item.setValor(value);
    }

    //------------------------------------------------------------------
    @Override
    public Object onGetValue(Valor item)
    {
        return item.getId();
    }
    
}
