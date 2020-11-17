/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.syswave.forms.databinding;

import com.syswave.entidades.miempresa.Condicion_Componente;
import com.syswave.swing.models.POJOTableModel;
import com.syswave.swing.table.events.TableModelCellFormatEvent;
import com.syswave.swing.table.events.TableModelGetValueEvent;
import com.syswave.swing.table.events.TableModelSetValueEvent;

/**
 *
 * @author Aimee García
 */
public class CondicionComponentesTableModel extends POJOTableModel<Condicion_Componente> {

    public CondicionComponentesTableModel(String[] columnHeaders) {
        super(columnHeaders);
    }

    
    
    @Override
    public void onSetValueAt(TableModelSetValueEvent<Condicion_Componente> e) {

        if (e.getNewValue() != null) {
            Condicion_Componente actual = e.getItem();
            switch (e.getDataProperty()) {
                case "id":
                    actual.setId((int) e.getNewValue());
                    break;
                case "valor":
                    actual.setValor((int) e.getNewValue());
                    break;
                case "es_unidad":
                    actual.setEs_unidad((short) e.getNewValue());
                    break;
                case "es_tipo":
                    actual.setEs_tipo((short) e.getNewValue());
                    break;
                case "id_condicion":
                    actual.setIdCondicion((int) e.getNewValue());
                    break;
            }
        }
    }

    @Override
    public Object onGetValueAt(TableModelGetValueEvent<Condicion_Componente> e) {
        Condicion_Componente actual = e.getItem();
        switch (e.getDataProperty()) {
            case "id":
                return actual.getId();

            case "valor":
                return actual.getValor();
            case "es_unidad":
                return actual.getEs_unidad();
            case "es_tipo":
                actual.getEs_tipo();
            case "id_condicion":
                return actual.getIdCondicion();
        }

        return "";
    }

    @Override
    public Class<?> onGetColumnClass(TableModelCellFormatEvent e) {
        switch (e.getDataProperty()) {
            case "id":
            case "valor":
            case "id_condicion":
                return Integer.class;

            case "es_unidad":
            case "es_tipo":

                return Short.class;

        }

        return getDefaultColumnClass();
    }

}
