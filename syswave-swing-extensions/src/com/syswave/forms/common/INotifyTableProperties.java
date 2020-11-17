package com.syswave.forms.common;

import com.syswave.swing.table.events.TableModelSetValueEvent;
import com.syswave.swing.table.events.TableModelCellFormatEvent;
import com.syswave.swing.table.events.TableModelGetValueEvent;

/**
 * Permite indicar las propiedades que serán utilizadas de un POJO, en un JTable.
 * @author Victor Manuel Bucio Vargas
 */
public interface INotifyTableProperties<E>
{
   void onSetValueAt(TableModelSetValueEvent<E> e);
   Object onGetValueAt(TableModelGetValueEvent<E> e);
   Class<?> onGetColumnClass(TableModelCellFormatEvent e);
}
