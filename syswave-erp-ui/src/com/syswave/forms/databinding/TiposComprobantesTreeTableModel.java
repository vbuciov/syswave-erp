package com.syswave.forms.databinding;

import com.syswave.entidades.miempresa.TipoComprobante;
import com.syswave.swing.models.POJOTreeTableModel;
import com.syswave.swing.table.events.TableModelCellFormatEvent;
import com.syswave.swing.table.events.TableModelGetValueEvent;
import com.syswave.swing.table.events.TableModelSetValueEvent;
import javax.swing.tree.TreeModel;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class TiposComprobantesTreeTableModel extends POJOTreeTableModel<TipoComprobante>
{

    //---------------------------------------------------------------------
    public TiposComprobantesTreeTableModel(String[] columns)
    {
        super(columns);
    }

    @Override
    public void onSetValueAt(TableModelSetValueEvent<TipoComprobante> e)
    {
        TipoComprobante actual = e.getItem();

        if (e.getNewValue() != null)
        {
            switch (e.getDataProperty())
            {
                case "id":
                    actual.setId((int) e.getNewValue());
                    break;

                case "nombre":
                    actual.setNombre((String) e.getNewValue());
                    break;

                case "afecta_inventario":
                    actual.setAfecta_inventario((Boolean) e.getNewValue());
                    break;

                case "es_entrada":
                    actual.setEntrada((Boolean) e.getNewValue());
                    break;

                case "es_activo":
                    actual.setActivo((Boolean) e.getNewValue());
                    break;

                case "permite_precios":
                    actual.setPermitePrecios((int) e.getNewValue());
                    break;

                case "condicion_pago":
                    actual.setCondicion_pago((int) e.getNewValue());
                    break;

                case "afecta_saldos":
                    actual.setAfectaSaldos((boolean) e.getNewValue());
                    break;

                case "es_tipo_saldo":
                    actual.setTipoSaldo((boolean) e.getNewValue());
                    break;

                case "es_comercial":
                    actual.setComercial((boolean) e.getNewValue());
                    break;

                /*case "usa_tipo_precio":
                 actual.setUsaTipoPrecio((boolean) e.getNewValue());
                 break;*/
                case "id_padre":
                    actual.setIdPadre((int) e.getNewValue());
                    break;

                case "nivel":
                    actual.setNivel((int) e.getNewValue());
                    break;
            }
        }
    }

    @Override
    public Object onGetValueAt(TableModelGetValueEvent<TipoComprobante> e)
    {
        TipoComprobante actual = e.getItem();

        switch (e.getDataProperty())
        {
            case "id":
                return actual.getId();

            case "nombre":
                return actual.getNombre();

            case "afecta_inventario":
                return actual.esAfecta_inventario();

            case "es_entrada":
                return actual.esEntrada();

            case "es_activo":
                return actual.esActivo();

            case "permite_precios":
                return actual.getPermitePrecios();

            case "condicion_pago":
                return actual.getCondicionPago();

            case "afecta_saldos":
                return actual.esAfectaSaldos();

            case "es_tipo_saldo":
                return actual.esTipoSaldo();

            case "es_comercial":
                return actual.esComercial();

            /*case "usa_tipo_precio":
             return actual.esUsaTipoPrecio();*/
            case "id_padre":
                return actual.getIdPadre();

            case "nivel":
                return actual.getNivel();

            case "node":
                return "";
        }

        return null;

    }

    @Override
    public Class<?> onGetColumnClass(TableModelCellFormatEvent e)
    {
        switch (e.getDataProperty())
        {
            case "id":
            case "permite_precios":
            case "condicion_pago":
            case "id_padre":
            case "nivel":
                return Integer.class;

            case "afecta_inventario":
            case "es_entrada":
            case "es_activo":
            case "afecta_saldos":
            case "es_tipo_saldo":
            case "es_comercial":
                //case "usa_tipo_precio":
                return Boolean.class;

            case "node":
                return TreeModel.class;
        }

        return getDefaultColumnClass();
    }

    @Override
    public String onGetText(TipoComprobante item)
    {
        return item.getNombre();
    }

    @Override
    public void onSetText(TipoComprobante item, String value)
    {
        item.setNombre(value);
    }

    @Override
    public Object onGetIDValue(TipoComprobante item)
    {
        return item.getId();
    }

    @Override
    public boolean onGetChecked(TipoComprobante element)
    {
        return element.isSelected();
    }

    @Override
    public void onSetChecked(TipoComprobante element, boolean value)
    {
        element.setSelected(value);
    }

    @Override
    public Object onGetIDParentValue(TipoComprobante element)
    {
        return element.getIdPadre();
    }

    @Override
    public int onGetLevel(TipoComprobante element)
    {
        return element.getNivel();
    }

    @Override
    public Object getNullParent()
    {
        return 0;
    }

    @Override
    public void onModifiedAdded(TipoComprobante element)
    {
        if (!element.isSet())
            element.setModified();
    }

    @Override
    public boolean canAddToModified(TipoComprobante element)
    {
        return false;
    }
}
