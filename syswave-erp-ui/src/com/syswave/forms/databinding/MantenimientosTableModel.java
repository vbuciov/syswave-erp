package com.syswave.forms.databinding;

import com.syswave.entidades.miempresa.Mantenimiento;
import com.syswave.swing.models.POJOTableModel;
import com.syswave.swing.table.events.TableModelCellFormatEvent;
import com.syswave.swing.table.events.TableModelGetValueEvent;
import com.syswave.swing.table.events.TableModelSetValueEvent;
import java.util.Date;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class MantenimientosTableModel extends POJOTableModel<Mantenimiento>
{

    //--------------------------------------------------------------------

    public MantenimientosTableModel(String[] columns)
    {
        super(columns);
    }

    //--------------------------------------------------------------------
    @Override
    public void onSetValueAt(TableModelSetValueEvent<Mantenimiento> e)
    {
        if (e.getNewValue() != null)
        {
            Mantenimiento actual = e.getItem();
            switch (e.getDataProperty())
            {
                case "id":
                    actual.setId((int) e.getNewValue());
                    break;

                case "folio":
                    actual.setFolio((String) e.getNewValue());
                    break;

                case "fecha_elaboracion":
                    actual.setFechaCreacion((Date) e.getNewValue());
                    break;

                case "es_tipo":
                    actual.setEsTipo((int) e.getNewValue());
                    break;

                case "id_persona":
                    actual.setIdPersona((int) e.getNewValue());
                    break;

                case "id_ubicacion_serie":
                    actual.setIdUbicacionSerie((int) e.getNewValue());
                    break;

                case "entrada_serie":
                    actual.setEntradaSerie((int) e.getNewValue());
                    break;

                case "id_serie":
                    String[] key = ((String) e.getNewValue()).split(",");
                    actual.setIdUbicacionSerie(Integer.parseInt(key[1]));
                    actual.setEntradaSerie(Integer.parseInt(key[0]));
                    break;

                case "costo_total":
                    actual.setCostoTotal((float) e.getNewValue());
                    break;

                case "id_moneda":
                    actual.setIdMoneda((int) e.getNewValue());
                    break;

                case "es_activo":
                    actual.setActivo((boolean) e.getNewValue());
                    break;

                case "hora_inicio":
                    actual.setHoraInicio((Date) e.getNewValue());
                    break;

                case "hora_final":
                    actual.setHoraFinal((Date) e.getNewValue());
                    break;

                case "fecha_finalizacion":
                    actual.setFechaFinalizacion((Date) e.getNewValue());
                    break;

                case "nota":
                    actual.setNota((String) e.getNewValue());
                    break;
            }
        }
    }

    //--------------------------------------------------------------------
    @Override
    public Object onGetValueAt(TableModelGetValueEvent<Mantenimiento> e)
    {
        Mantenimiento actual = e.getItem();
        switch (e.getDataProperty())
        {
            case "id":
                return actual.getId();

            case "folio":
                return actual.getFolio();

            case "fecha_elaboracion":
                return actual.getFechaCreacion();

            case "es_tipo":
                return actual.getEsTipo();

            case "id_persona":
                return actual.getIdPersona();

            case "id_ubicacion_serie":
                return actual.getIdUbicacionSerie();

            case "entrada_serie":
                return actual.getEntradaSerie();

            case "id_serie":
                return actual.getIdSerie();

            case "costo_total":
                return actual.getCostoTotal();

            case "id_moneda":
                return actual.getIdMoneda();

            case "es_activo":
                return actual.esActivo();

            case "hora_inicio":
                return actual.getHoraInicio();

            case "hora_final":
                return actual.getHoraFinal();

            case "fecha_finalizacion":
                return actual.getFechaFinalizacion();

            case "nota":
                return actual.getNota();
        }

        return null;
    }

    //--------------------------------------------------------------------
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
        return true;
    }

    //--------------------------------------------------------------------
    @Override
    public Class<?> onGetColumnClass(TableModelCellFormatEvent e)
    {
        switch (e.getDataProperty())
        {
            case "id":
            case "es_tipo":
            case "id_persona":
            case "id_ubicacion_serie":
            case "entrada_serie":
            case "id_moneda":
                return Integer.class;

            case "fecha_elaboracion":
            case "fecha_finalizacion":
            case "hora_inicio":
            case "hora_final":
                return Date.class;

            case "costo_total":
                return Float.class;

            case "es_activo":
                return Boolean.class;
        }
        return getDefaultColumnClass();
    }
}
