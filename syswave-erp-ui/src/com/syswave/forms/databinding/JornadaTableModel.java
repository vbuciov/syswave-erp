package com.syswave.forms.databinding;

import com.syswave.entidades.miempresa.Jornada;
import com.syswave.swing.models.POJOTableModel;
import com.syswave.swing.table.events.TableModelCellFormatEvent;
import com.syswave.swing.table.events.TableModelGetValueEvent;
import com.syswave.swing.table.events.TableModelSetValueEvent;
import java.util.List;

/**
 *
 * @author sis5
 */
public class JornadaTableModel extends POJOTableModel<Jornada> {
    
    Jornada sample;
    
    private void initAtributes() {
        sample = new Jornada();
    }
    
    public JornadaTableModel() {
        super();
        initAtributes();
    }
    
    public JornadaTableModel(String[] columns) {
        super(columns);
        initAtributes();
    }
    
    public JornadaTableModel(List<Jornada> dataSource) {
        super(dataSource);
        initAtributes();
    }
    
    @Override
   public boolean isCellEditable(int rowIndex, int columnIndex)
   {
      return true; //Todo es editable
   }

    @Override
    public void onSetValueAt(TableModelSetValueEvent<Jornada> e) {
        Jornada actual = e.getItem();
        switch(e.getDataProperty()) {
            case "nombre":
                actual.setNombre((String)e.getNewValue());
                break;
            case "tiempo_efectivo":
                actual.setTiempo_efectivo((int)e.getNewValue());
                break;
            case "descripcion":
                actual.setDescripcion((String)e.getNewValue());
                break;
        }
    }

    @Override
    public Object onGetValueAt(TableModelGetValueEvent<Jornada> e) {
        Jornada actual = e.getItem();
        switch(e.getDataProperty()) {
            case "nombre":
                return actual.getNombre();
            case "tiempo_efectivo":
                return actual.getTiempo_efectivo();
            case "descripcion":
                return actual.getDescripcion();
        }
        return null;
    }

    @Override
    public Class<?> onGetColumnClass(TableModelCellFormatEvent e) {
        switch(e.getColIndex()) {
            case 0:
                return sample.getNombre().getClass();
            case 1:
                return sample.getTiempo_efectivo().getClass();
            case 2:
                return sample.getDescripcion().getClass();
        }
        
        return getDefaultColumnClass();
    }
    
}
