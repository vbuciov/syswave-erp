package com.syswave.forms.databinding;

import com.syswave.entidades.miempresa_vista.MantenimientoDescripcion_5FN;
import com.syswave.swing.models.POJOTableModel;
import com.syswave.swing.table.events.TableModelCellFormatEvent;
import com.syswave.swing.table.events.TableModelGetValueEvent;
import com.syswave.swing.table.events.TableModelSetValueEvent;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class MantenimientoDescripcion5FNTableModel extends POJOTableModel<MantenimientoDescripcion_5FN>
{

    //---------------------------------------------------------------------
    public MantenimientoDescripcion5FNTableModel(String[] columns)
    {
        super(columns);
    }

    //---------------------------------------------------------------------
    @Override
    public void onSetValueAt(TableModelSetValueEvent<MantenimientoDescripcion_5FN> e)
    {
        if (e.getNewValue() != null)
        {
            MantenimientoDescripcion_5FN actual = e.getItem();

            switch (e.getDataProperty())
            {
                case "valor":
                    actual.setValor((String) e.getNewValue());
                    break;

                case "descripcion":
                    actual.setDescripcion((String) e.getNewValue());
                    break;

                case "formato":
                    actual.setFormato((String) e.getNewValue());
                    break;

                case "es_activo":
                    actual.setActivo((boolean) e.getNewValue());
                    break;

                case "linea":
                    actual.setLinea((int) e.getNewValue());
                    break;

                case "id_mantenimiento":
                    actual.setIdMantenimiento((int) e.getNewValue());
                    break;

                case "id_tipo_descripcion":
                    actual.setIdTipoDescripcion((int) e.getNewValue());
                    break;

                case "texto":
                    actual.setTexto((String) e.getNewValue());
                    break;
            }
        }
    }

    //---------------------------------------------------------------------
    @Override
    public Object onGetValueAt(TableModelGetValueEvent<MantenimientoDescripcion_5FN> e)
    {
        MantenimientoDescripcion_5FN actual = e.getItem();

        switch (e.getDataProperty())
        {
            case "valor":
                return actual.getValor();

            case "descripcion":
                return actual.getDescripcion();

            case "formato":
                return actual.getFormato();

            case "es_activo":
                return actual.esActivo();

            case "linea":
                return actual.getLinea();

            case "id_mantenimiento":
                return actual.getIdMantenimiento();

            case "id_tipo_descripcion":
                return actual.getIdTipoDescripcion();

            case "texto":
                return actual.getTexto();
        }

        return null;
    }

    //---------------------------------------------------------------------
    @Override
    public Class<?> onGetColumnClass(TableModelCellFormatEvent e)
    {
        switch (e.getDataProperty())
        {
            case "linea":
            case "id_mantenimiento":
            case "id_tipo_descripcion":
                return Integer.class;

            case "es_activo":
                return Boolean.class;
        }
        
        return getDefaultColumnClass();
    }

    //---------------------------------------------------------------------
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
        return columnIndex < 1;
    }

}
