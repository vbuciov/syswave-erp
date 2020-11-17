package com.syswave.forms.databinding;

import com.syswave.swing.models.POJOTableModel;
import com.syswave.swing.table.events.TableModelCellFormatEvent;
import com.syswave.swing.table.events.TableModelGetValueEvent;
import com.syswave.swing.table.events.TableModelSetValueEvent;
import com.syswave.entidades.miempresa.ControlAlmacen;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class ControlAlmacenTableModel extends POJOTableModel<ControlAlmacen>
{

    //---------------------------------------------------------------------

    public ControlAlmacenTableModel()
    {
        super();
    }

    //---------------------------------------------------------------------
    public ControlAlmacenTableModel(String[] columns)
    {
        super(columns);
    }

    //---------------------------------------------------------------------
    @Override
    public void onSetValueAt(TableModelSetValueEvent<ControlAlmacen> e)
    {
        ControlAlmacen actual = e.getItem();

        if (e.getNewValue() != null)
        {
            switch (e.getDataProperty())
            {
                case "entrada":
                    actual.setEntrada((int) e.getNewValue());
                    break;

                case "id_ubicacion":
                    actual.setIdUbicacion((int) e.getNewValue());
                    break;

                case "id_variante":
                    actual.setIdVariante((int) e.getNewValue());
                    break;

                case "consecutivo":
                    actual.setConsecutivo((int) e.getNewValue());
                    break;

                case "cantidad":
                    actual.setCantidad((float) e.getNewValue());
                    break;

                case "serie":
                    actual.setSerie((String) e.getNewValue());
                    break;
                    
                case "observaciones":
                    actual.setObservaciones((String)e.getNewValue());
                    break;

                case "id_lote":
                    String[] keys = ((String) e.getNewValue()).split(",");
                    actual.setConsecutivo(Integer.parseInt(keys[0]));
                    actual.setIdVariante(Integer.parseInt(keys[1]));
                    break;

                case "valor_acumulado":
                    actual.setValoAcumulado((int) e.getNewValue());
                    break;
            }
        }

    }

    //---------------------------------------------------------------------
    @Override
    public Object onGetValueAt(TableModelGetValueEvent<ControlAlmacen> e)
    {
        ControlAlmacen actual = e.getItem();

        switch (e.getDataProperty())
        {
            case "entrada":
                return actual.getEntrada();

            case "id_ubicacion":
                return actual.getIdUbicacion();

            case "id_variante":
                return actual.getIdVariante();

            case "consecutivo":
                return actual.getConsecutivo();

            case "cantidad":
                return actual.getCantidad();

            case "serie":
                return actual.getSerie();
               
            case "observaciones":
                return actual.getObservaciones();

            case "id_lote":
                return actual.getIdLote();

            case "valor_acumulado":
                return actual.getValorAcumulado();

            case "etiqueta_mantenimiento":
                return actual.getHasOneControlInventario().getHasOneBienVariante().obtenerEtiquetaMantenimiento();
                //return calcularEtiqueta(actual.getFk_control_almacen_id_inventario().getFk_inventario_variante_id().getMantenimientoComo());
        }

        return null;
    }

    //---------------------------------------------------------------------
    /*public String calcularEtiqueta(int mantenimiento_como)
    {
        switch (mantenimiento_como)
        {
            case 0:
                return "Tiempo(Días)";

            case 1:
                return "Kilometraje";

            case 2:
                return "Usos";

            default:
                return "Desconocido";
        }
    }*/

    //---------------------------------------------------------------------
    @Override
    public Class<?> onGetColumnClass(TableModelCellFormatEvent e)
    {
        switch (e.getDataProperty())
        {
            case "entrada":
            case "id_ubicacion":
            case "id_variante":
            case "consecutivo":
            case "valor_acumulado":
                return Integer.class;

            case "cantidad":
                return Float.class;
        }

        return getDefaultColumnClass();
    }

    //---------------------------------------------------------------------
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
        return columnIndex < 6;
    }
}
