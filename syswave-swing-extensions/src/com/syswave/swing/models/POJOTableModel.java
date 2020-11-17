package com.syswave.swing.models;

import com.syswave.forms.common.INotifyTableProperties;
import com.syswave.swing.table.events.TableModelCellFormatEvent;
import com.syswave.swing.table.events.TableModelGetValueEvent;
import com.syswave.swing.table.events.TableModelSetValueEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;

/**
 * Representa un origen de datos para una lista generica de POJO's
 *
 * @author Victor Manuel Bucio Vargas
 * @param <E> La entidad que se requiere utilizar.
 */
public abstract class POJOTableModel<E> extends AbstractTableModel implements Serializable, INotifyTableProperties<E>
{

    final private String NOT_BINDING = "";

    protected List<E> data;
    protected Vector dataVector;
    protected List<String> columnsNames;
    private List<Integer> BindingColumns;
    private List<Integer> DataColumns;
    private List<String> BindingNames;
    private TableModelGetValueEvent<E> getEvent;
    private TableModelSetValueEvent<E> setEvent;
    private TableModelCellFormatEvent formatEvent;
    private Pattern pattern;
    private boolean read_only;

    //----------------------------------------------------------------------
    public POJOTableModel()
    {
        initAtributes();
        data = new ArrayList<E>();
        columnsNames = new ArrayList<String>();
    }

    //----------------------------------------------------------------------
    /**
     * Inicializa el modelo especificando las columnas en formtato
     * HeaderText:{Binding} o HeaderText
     */
    public POJOTableModel(String[] columnHeaders)
    {
        initAtributes();
        data = new ArrayList<E>();
        columnsNames = new ArrayList<String>(Arrays.asList(columnHeaders));

        buildBindings(columnsNames);
    }

    //----------------------------------------------------------------------
    /**
     * Construye una instancia del modelo indicando el origen de datos.
     *
     * @param dataSource Origen de datos.
     */
    public POJOTableModel(List<E> dataSource)
    {
        initAtributes();
        data = dataSource;
        columnsNames = new ArrayList<String>();

        //dataVector.setSize(data.size());
        justifyRows(0, getRowCount());
    }

    //----------------------------------------------------------------------
    private void initAtributes()
    {
        pattern = Pattern.compile(":\\{(.*?)\\}");
        getEvent = new TableModelGetValueEvent<E>();
        setEvent = new TableModelSetValueEvent<E>();
        formatEvent = new TableModelCellFormatEvent();
        BindingColumns = new ArrayList<Integer>();
        DataColumns = new ArrayList<Integer>();
        BindingNames = new ArrayList<String>();
        dataVector = new Vector();
        read_only = false;
    }

    //----------------------------------------------------------------------
    private void buildBindings(List<String> formatNames)
    {
        String result;
        BindingColumns.clear();
        BindingNames.clear();
        DataColumns.clear();
        for (int i = 0; i < formatNames.size(); i++)
        {
            result = getBinding(formatNames.get(i));

            if (result != NOT_BINDING)
            {
                BindingColumns.add(i);
                BindingNames.add(result);
            }

            else
                DataColumns.add(i);
        }
    }

    //---------------------------------------------------------------------
    /**
     * Obtiene el número de renglones que existen en el modelo de datos.
     *
     * @return int
     */
    @Override
    public int getRowCount()
    {
        return data.size(); //return data != null? data.size():0;
    }

    //---------------------------------------------------------------------
    private void justifyRows(int from, int to)
    {
        int notBindingColumnsSize = getColumnCount() - BindingColumns.size();

        if (notBindingColumnsSize > 0)
        {
            dataVector.setSize(to);
            for (int i = from; i < to; i++)
            {
                if (dataVector.elementAt(i) == null)
                    dataVector.setElementAt(new Vector(), i);

                ((Vector) dataVector.elementAt(i)).setSize(notBindingColumnsSize);
            }
        }
    }

    //---------------------------------------------------------------------
    /**
     * Agrega un nuevo renglón al modelo de datos.
     *
     * @param value El valor a agregar
     * @return
     */
    public int addRow(E value)
    {
        int index;

        if (data == null)
            data = new ArrayList<E>();

        data.add(value);
        index = data.size() - 1;

        //Not Binding columns
        justifyRows(index, index + 1);

        fireTableRowsInserted(index, index);

        return index;
    }

    //---------------------------------------------------------------------
    /**
     * Agrega varios renglónes nuevos al modelo de datos.
     *
     * @param values La lsita de valores a agregar.
     * @return
     */
    public boolean addRows(List<E> values)
    {
        boolean success;

        int firts;

        if (data == null)
            data = new ArrayList<E>();

        firts = data.size() - 1;

        success = data.addAll(values);

        //Not Binding columns
        justifyRows(firts, getRowCount());

        fireTableRowsInserted(firts, data.size() - 1);

        return success;
    }

    //---------------------------------------------------------------------
    /**
     * Agrega un núevo renglón al modelo de datos.
     *
     * @return Referencia al nuevo renglón.
     */
    /*public E addNewRow() 
     {
     E current = null;
     try
     {
         
     current = (E) getClass().newInstance();
     data.add(current);
     }
     catch (InstantiationException ex)
     {
     Logger.getLogger(POJOTableModel.class.getName()).log(Level.SEVERE, null, ex);
     }
     catch (IllegalAccessException ex)
     {
     Logger.getLogger(POJOTableModel.class.getName()).log(Level.SEVERE, null, ex);
     }
     finally
     {
     fireTableRowsInserted(data.size() -1, data.size() - 1);
     }
      
     return current;
     }*/
    //---------------------------------------------------------------------
    /**
     * Agrega un nuevo elemento al modelo de datos en la posición especificada.
     *
     * @param row Renglón en donde se agregará.
     * @param value El valor que se agregara
     */
    public void insertRow(int row, E value)
    {
        data.add(row, value);

        //Not Binding columns
        justifyRows(row, row + 1);

        fireTableRowsInserted(row, row);
    }

    //---------------------------------------------------------------------
    /**
     * Agrega un nuevo elemento al modelo de datos en la posición especificada.
     *
     * @param row Renglón en donde se agregará.
     * @param values Colección de valores a agregar
     */
    public void insertRows(int row, List<E> values)
    {
        data.addAll(row, values);

        //Not Binding columns
        for (int i = 0; i < values.size(); i++)
        {
            justifyRows(row, row + 1);
        }

        fireTableRowsInserted(row, row + values.size() - 1);
    }

    //---------------------------------------------------------------------
    /**
     * Remueve el elemento en el renglón especificado.
     *
     * @param row Indice del renglón a remover.
     * @return una referencia al elemento removido.
     */
    public E removeRow(int row)
    {
        E element = data.remove(row);

        dataVector.remove(row);

        fireTableRowsDeleted(row, row);
        return element;
    }

    //---------------------------------------------------------------------
    /**
     * Remueve los elementos especificados por la colección de indices.
     *
     * @param rowsIndex Colección de los indices a remover
     * @return una colección de referencias hacia los elementos removidos.
     */
    public List<E> removeRows(int[] rowsIndex)
    {
        int min, max;
        List<E> selecteds = new ArrayList<E>();
        List<Object> dataSelecteds = new ArrayList<>();
        int notBindingColumnsSize = getColumnCount() - BindingColumns.size();

        //1.- Inicializamos las variables del mínimo y máximo índice.
        if (rowsIndex.length > 0)
        {
            min = rowsIndex[0];
            max = rowsIndex[0];
        }
        else
        {
            min = 0;
            max = 0;
        }

        //2.- Recorremos los elementos seleccionados.
        for (int i = 0; i < rowsIndex.length; i++)
        {
            if (min > rowsIndex[i])
                min = rowsIndex[i];

            else if (max < rowsIndex[i])
                max = rowsIndex[i];

            selecteds.add(data.get(rowsIndex[i]));
            if (notBindingColumnsSize > 0)
                dataSelecteds.add(dataVector.get(rowsIndex[i]));
        }

        //3.- Removemos todos los elementos seleccionados.
        data.removeAll(selecteds);
        if (!dataSelecteds.isEmpty())
            dataVector.removeAll(dataSelecteds);
        fireTableRowsDeleted(min, max);

        return selecteds;
    }

    //---------------------------------------------------------------------
    /**
     * Moves one or more rows from the inclusive range <code>start</code> to
     * <code>end</code> to the <code>to</code> position in the model. After the
     * move, the row that was at index <code>start</code> will be at index
     * <code>to</code>. This method will send a <code>tableChanged</code>
     * notification message to all the listeners.
     *
     * <pre>
     *  Examples of moves:
     *
     *  1. moveRow(1,3,5);
     *          a|B|C|D|e|f|g|h|i|j|k   - before
     *          a|e|f|g|h|B|C|D|i|j|k   - after
     *
     *  2. moveRow(6,7,1);
     *          a|b|c|d|e|f|G|H|i|j|k   - before
     *          a|G|H|b|c|d|e|f|i|j|k   - after
     * </pre>
     *
     * @param start the starting row index to be moved
     * @param end the ending row index to be moved
     * @param to the destination of the rows to be moved
     * @exception ArrayIndexOutOfBoundsException if any of the elements would be
     * moved out of the table's range
     *
     */
    /*public void moveRow(int start, int end, int to) {
     int shift = to - start;
     int first, last;
     if (shift < 0) {
     first = to;
     last = end;
     }
     else {
     first = start;
     last = to + end - start;
     }
     rotate(dataVector, first, last + 1, shift);

     fireTableRowsUpdated(first, last);
     }*/
    //---------------------------------------------------------------------
    /**
     * Limpia toda la información desplegada.
     */
    public void clear()
    {
        int min = 0, max = data.size() - 1;
         data.clear();
        dataVector.clear();

        if (max >= 0)
            fireTableRowsDeleted(min, max);
    }

   //---------------------------------------------------------------------
   /*public void removeRows (List<Usuario> selecteds )
     {
     data.removeAll(selecteds);
     }*/
    //---------------------------------------------------------------------
    /**
     * Obtiene el elemento en el renglón especificado.
     *
     * @param row Numero de renglón.
     * @return Referencia al elemento
     */
    public E getElementAt(int row)
    {
        return data.get(row);
    }
    
    

    //---------------------------------------------------------------------
    /**
     * Obtiene los elementos especificados en la colección de indices.
     *
     * @param rowsIndex Colección con los indices de los elementos a obtener.
     * @return Referencias a los elementos
     */
    public List<E> getElementsAt(int[] rowsIndex)
    {
        List<E> selecteds = new ArrayList<E>();

        for (int i = 0; i < rowsIndex.length; i++)
        {
            selecteds.add(data.get(rowsIndex[i]));
        }

        return selecteds;
    }
    
    //---------------------------------------------------------------------
    /**
     * Obtiene el indice de un elemento dado
     * @param value The elemento a conocer su indice.
     * @return El indice del elemento.
     */
    public int indexOf(E value)
    {
        return data.indexOf(value);
    }

    //---------------------------------------------------------------------
    /**
     * Obtiene la colección de elementos
     *
     * @return Lista de elementos
     *
     */
    public List<E> getData()
    {
        return data;
    }

    //---------------------------------------------------------------------
    /**
     * Establece los elementos. Es el equivalente a setNumRows en el
     * DefaultTableModel
     *
     * @param dataSource Datos
     */
    public void setData(List<E> dataSource)
    {
        int old_rowcount = this.data != null && this.data.size() > 0 ? this.data.size() : 0;
        int new_rowcount = dataSource != null && dataSource.size() > 0 ? dataSource.size() : 0;
        int difference = new_rowcount - old_rowcount;
        this.data = dataSource;
        //dataVector.setSize(data.size());
        justifyRows(0, getRowCount());
        if (!data.isEmpty())
        {
            //The new data is less than previous
            if (difference < 0)
            {
                fireTableRowsDeleted(new_rowcount, old_rowcount - 1);
                if (new_rowcount > 0)
                    fireTableRowsUpdated(0, new_rowcount - 1);
            }

            //The new data is greather than previous
            else if (difference > 0)
            {
                fireTableRowsInserted(old_rowcount, new_rowcount - 1);
                if (old_rowcount > 0)
                    fireTableRowsUpdated(0, old_rowcount - 1);
            }

            //The new data is equal than previous
            else
                fireTableRowsUpdated(0, new_rowcount - 1);
            //fireTableDataChanged(); //Indica que el renglón y columna 0, .1 han cambiado.
            //fireTableStructureChanged(); //Indica el renglon y columna -1, -1 han cambiado.
            //
        }
    }

    //---------------------------------------------------------------------
    public String[] getColumnsNames()
    {
        return columnsNames.toArray(new String[columnsNames.size()]);
    }

    //---------------------------------------------------------------------
    public void setColumnsNames(String[] columnsHeaders)
    {
        if (columnsNames.size() > 0)
            columnsNames.clear();

        columnsNames = new ArrayList<String>(Arrays.asList(columnsHeaders));

        buildBindings(columnsNames);
        justifyRows(0, getRowCount());
        fireTableStructureChanged();
    }

    //---------------------------------------------------------------------   
    @Override
    public int getColumnCount()
    {
        return columnsNames.size();
    }

    //---------------------------------------------------------------------
    public void setColumnCount(int columnCount)
    {
        int dif;

        if (columnCount > columnsNames.size())
        {
            dif = columnCount - columnsNames.size();
            for (int i = columnsNames.size(); i < dif; i++)
            {
                columnsNames.add(super.getColumnName(i));
            }
        }

        else
        {
            dif = columnsNames.size() - columnCount;
            for (int j = 0; j < dif; j++)
            {
                columnsNames.remove(columnsNames.size() - 1);
            }
        }

        justifyRows(0, getRowCount());
        fireTableStructureChanged();
    }

    //----------------------------------------------------------------------
    private String getHeaderText(String formatValue)
    {
        String[] parts = formatValue.split(":\\{");

        return parts[0];
    }

    //----------------------------------------------------------------------
    private String getBinding(String formatValue)
    {
        String result;

        Matcher matcher = pattern.matcher(formatValue);
        if (matcher.find())
        {
            result = matcher.group();
            result = result.substring(2, result.length() - 1);
        }

        else
            result = NOT_BINDING;

        return result;
    }

    //---------------------------------------------------------------------
    /**
     * Agrega una columna nueva al modelo.
     *
     * @param formatValue HeaderText:{Binding} o HeaderText
     */
    public void addColumnName(String formatValue)
    {
        columnsNames.add(formatValue);

        int newIndex = columnsNames.size() - 1;
        String result = getBinding(columnsNames.get(newIndex));

        if (result != NOT_BINDING)
        {
            BindingColumns.add(newIndex);
            BindingNames.add(result);
        }

        else
            DataColumns.add(newIndex);

        justifyRows(0, getRowCount());
        fireTableStructureChanged();
    }

    //---------------------------------------------------------------------
    /**
     * Returns the column name.
     *
     * @return a name for this column using the string value of the appropriate
     * member in <code>columnIdentifiers</code>. If
     * <code>columnIdentifiers</code> does not have an entry for this index,
     * returns the default name provided by the superclass.
     */
    @Override
    public String getColumnName(int column)
    {
        Object id = null;
        // This test is to cover the case when
        // getColumnCount has been subclassed by mistake ...
        if (column < columnsNames.size() && (column >= 0))
        {
            id = getHeaderText(columnsNames.get(column));
        }

        return id == null ? super.getColumnName(column) : (String) id;
    }

    //---------------------------------------------------------------------
    /**
     * Returns the column index.
     *
     * @return a index for this column using the string value of the appropriate
     * member in <code>columnIdentifiers</code>. If
     * <code>columnIdentifiers</code> does not have an entry for this index,
     * returns the default name provided by the superclass.
     */
    @Override
    public int findColumn(String columnName)
    {
        int i = 0, colIndex = -1;
        boolean find = false;
        while (!find && i < columnsNames.size())
        {
            find = columnName.equals(getHeaderText(columnsNames.get(i)));
            if (find)
                colIndex = i;
            else
                i++;
        }
        return colIndex;
    }

    //--------------------------------------------------------------------
    /**
     * Sets the object value for the cell at <code>column</code> and
     * <code>row</code>.  <code>aValue</code> is the new value. This method will
     * generate a <code>tableChanged</code> notification.
     *
     * @param aValue the new value; this can be null
     * @param rowIndex the row whose value is to be changed
     * @param columnIndex the column whose value is to be changed
     * @exception ArrayIndexOutOfBoundsException if an invalid row or column was
     * given
     */
    @Override
    final public void setValueAt(Object aValue, int rowIndex, int columnIndex)
    {
        int bindingIndex = BindingColumns.indexOf(columnIndex);

        if (bindingIndex >= 0)
        {
            setEvent.set(aValue, data.get(rowIndex), rowIndex, columnIndex, BindingNames.get(bindingIndex));
            onSetValueAt(setEvent);
        }

        else
        {
            Vector rowVector = (Vector) dataVector.elementAt(rowIndex);
            rowVector.setElementAt(aValue, DataColumns.indexOf(columnIndex));
        }

        fireTableCellUpdated(rowIndex, columnIndex);
    }

    //---------------------------------------------------------------------
    /**
     * Returns an attribute value for the cell at <code>row</code> and
     * <code>column</code>.
     *
     * @param rowIndex the row whose value is to be queried
     * @param columnIndex the column whose value is to be queried
     * @return the value Object at the specified cell
     * @exception ArrayIndexOutOfBoundsException if an invalid row or column was
     * given
     */
    @Override
    final public Object getValueAt(int rowIndex, int columnIndex)
    {
        Object value = null;
        int bindingIndex = BindingColumns.indexOf(columnIndex);
        String property = BindingNames.get(bindingIndex);

        if (bindingIndex >= 0)
        {
            getEvent.set(data.get(rowIndex), rowIndex, columnIndex, property);
            value = onGetValueAt(getEvent);
        }

        else
        {
            Vector rowVector = (Vector) dataVector.elementAt(rowIndex);
            value = rowVector.elementAt(DataColumns.indexOf(columnIndex));
            if (value == null)
            {
                formatEvent.set(columnIndex, NOT_BINDING);

                Class current = onGetColumnClass(formatEvent);
                value = getDefaultValueFor(current);
            }

        }

        if (value == null)
            throw new IllegalArgumentException(String.format("Null value not permitted on property {%s} at cell [%d, %d]", property, rowIndex, columnIndex ) );

        return value;
    }

    //---------------------------------------------------------------------
   /*
     * Bajo ninguna circunstancia invoque este método desde onGetColumnClass
     */
    @Override
    final public Class<?> getColumnClass(int columnIndex)
    {
        int bindingIndex = BindingColumns.indexOf(columnIndex);

        if (bindingIndex >= 0)
            formatEvent.set(columnIndex, BindingNames.get(bindingIndex));

        else
            formatEvent.set(columnIndex, NOT_BINDING);

        return onGetColumnClass(formatEvent);
    }

    //---------------------------------------------------------------------
    public final Class<?> getDefaultColumnClass()
    {
        return super.getColumnClass(0);
    }

    //---------------------------------------------------------------------
    /**
     * Regresa verdadero solamente si el modelo no es para lectura
     *
     * @param row the row whose value is to be queried
     * @param column the column whose value is to be queried
     * @return true
     * @see #setValueAt
     */
    @Override
    public boolean isCellEditable(int row, int column)
    {
        return !read_only;
    }

    //---------------------------------------------------------------------
    /**
     * Indica si el modelo es solamente para lectura.
     *
     * @return un valor que indica si es de solo lectura
     */
    public final boolean isReadOnly()
    {
        return read_only;
    }

    //---------------------------------------------------------------------
    /**
     * Establece si el modelo es solamente para lectura
     *
     * @param value un valor para indicar el comportamiento
     */
    public final void setReadOnly(boolean value)
    {
        this.read_only = value;
    }

    //--------------------------------------------------------------------
    /**
     * Indica a los componentes asociados a este modelo que ha ocurrido un
     * cambio en el elemento.
     *
     * @param value elemento.
     */
    public void resetElement(E value)
    {
        fireTableCellUpdated(data.indexOf(value), TableModelEvent.ALL_COLUMNS);
    }

    private Object getDefaultValueFor(Class current)
    {
       if (current == Boolean.class)
           return false;

       else if (current == Integer.class)
           return 0;

       else if (current == Float.class)
           return 0.0F;

       else if (current == Double.class)
           return 0.0D;

       else if (current == Date.class)
           return Calendar.getInstance().getTime();

       else
           return "";   
    }
}
