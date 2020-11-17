/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.syswave.forms.databinding;

import com.syswave.entidades.miempresa.Jornada;
import com.syswave.swing.models.POJOComboBoxModel;

/**
 *
 * @author sis5
 */
public class JornadaComboBoxModel extends POJOComboBoxModel<Jornada>
{

    //---------------------------------------------------------------------
    @Override
    public String onGetText(Jornada item)
    {
        return item.getNombre();
    }

    //---------------------------------------------------------------------
    @Override
    public void onSetText(Jornada item, String value)
    {
        item.setNombre(value);
    }

    //---------------------------------------------------------------------
    @Override
    public Object onGetValue(Jornada item)
    {
        return item.getId();
    }
}
