package com.syswave.forms.databinding;

import com.syswave.entidades.miempresa.SalarioComponente;
import com.syswave.swing.models.POJOTableModel;
import com.syswave.swing.table.events.TableModelCellFormatEvent;
import com.syswave.swing.table.events.TableModelGetValueEvent;
import com.syswave.swing.table.events.TableModelSetValueEvent;
import java.util.List;

/**
 *
 * @author sis5
 */
public class SalarioComponenteTableModel extends POJOTableModel<SalarioComponente>
{

    public SalarioComponenteTableModel()
    {
        super();
    }

    public SalarioComponenteTableModel(String[] columns)
    {
        super(columns);
    }

    public SalarioComponenteTableModel(List<SalarioComponente> dataSource)
    {
        super(dataSource);
    }

    @Override
    public void onSetValueAt(TableModelSetValueEvent<SalarioComponente> e)
    {
        SalarioComponente actual = e.getItem();

        if (e.getNewValue() != null)
        {
            switch (e.getDataProperty())
            {
                case "id":
                    actual.setId((int) e.getNewValue());
                    break;
                case "consecutivo_salario":
                    actual.setConsecutivoSalario((int) e.getNewValue());
                    break;
                case "id_persona_salario":
                    actual.setIdPersona((int) e.getNewValue());
                    break;
                case "nombre":
                    actual.setNombre((String) e.getNewValue());
                    break;
                case "cantidad":
                    actual.setCantidad((Double) e.getNewValue());
                    break;
                case "es_frecuencia":
                    actual.setEsFrecuencia((int) e.getNewValue());
                    break;
                case "comentario":
                    actual.setComentario((String) e.getNewValue());
                    break;
                case "numero_cuenta":
                    actual.setNumeroCuenta((String) e.getNewValue());
                    break;
                case "clabe":
                    actual.setClabe((String) e.getNewValue());
                    break;
                case "proveedor_cuenta":
                    actual.setProveedorCuenta((String) e.getNewValue());
                    break;
                case "es_tipo_cuenta":
                    actual.setEsTipoCuenta((int) e.getNewValue());
                    break;
                case "maximo_cuenta":
                    actual.setMaximoCuenta((Double) e.getNewValue());
                    break;
            }
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
        return true;//super.isCellEditable(rowIndex, columnIndex); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object onGetValueAt(TableModelGetValueEvent<SalarioComponente> e)
    {
        SalarioComponente actual = e.getItem();

        switch (e.getDataProperty())
        {
            case "id":
                return actual.getId();
            case "consecutivo_salario":
                return actual.getConsecutivoSalario();
            case "id_persona_salario":
                return actual.getIdPersona();
            case "nombre":
                return actual.getNombre();
            case "cantidad":
                return actual.getCantidad();
            case "es_frecuencia":
                return actual.getEsFrecuencia();
            case "comentario":
                return actual.getComentario();
            case "numero_cuenta":
                return actual.getNumeroCuenta();
            case "clabe":
                return actual.getClabe();
            case "proveedor_cuenta":
                return actual.getProveedorCuenta();
            case "es_tipo_cuenta":
                return actual.getEsTipoCuenta();
            case "maximo_cuenta":
                return actual.getMaximoCuenta();
        }
        return null;
    }

    @Override
    public Class<?> onGetColumnClass(TableModelCellFormatEvent e)
    {
        switch (e.getDataProperty())
        {
            case "id":
            case "consecutivo_salario":
            case "id_persona_salario":
            case "es_frecuencia":
            case "es_tipo_cuenta":
                return Integer.class;
            case "cantidad":
            case "maximo_cuenta":
                return Double.class;
        }
        return getDefaultColumnClass();
    }

}
