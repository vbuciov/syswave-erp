package com.syswave.forms.common;

import java.awt.Component;
import java.awt.Rectangle;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public abstract class JTableDataView extends JDataView implements PropertyChangeListener
{

    protected JFileChooser fileChooser;
    private JScrollPane jspData;
    protected JTable jtbData;
    private String searchWord;
    private int lastRowIndex, lastColumnIndex;

    //-------------------------------------------------------------------
    private void initAttributes()
    {
        jspData = new JScrollPane();
        add(jspData, java.awt.BorderLayout.CENTER);
        fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Documentos de excel", "xls"));
    }

    //-------------------------------------------------------------------
    public JTableDataView(IWindowContainer container)
    {

        super(container);
        initAttributes();

        jtbData = new JTable();
        jtbData.setAutoCreateRowSorter(true);
        jspData.setViewportView(jtbData);
    }

    //-------------------------------------------------------------------
    public JTableDataView(IWindowContainer container, String name)
    {
        super(container, name);
        initAttributes();

        jtbData = new JTable();
        jtbData.setAutoCreateRowSorter(true);
        jspData.setViewportView(jtbData);
    }

    //-------------------------------------------------------------------
    public JTableDataView(JTable customTable, IWindowContainer container)
    {
        super(container);
        jspData = new JScrollPane();
        jtbData = customTable;

        if (jtbData != null)
            jspData.setViewportView(jtbData);

        add(jspData, java.awt.BorderLayout.CENTER);
        fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Documentos de excel", "xls"));
    }

    //-------------------------------------------------------------------
    public void setTable(JTable on)
    {
        on.setAutoCreateRowSorter(true);
        jspData.setViewportView(on);
        jspData.setAutoscrolls(true);
    }

    //-------------------------------------------------------------------
    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        //int progress = cargarOrigenesDatos.getProgress();
        if ("progress".equals(evt.getPropertyName()))
            jbProgress.setValue((Integer) evt.getNewValue());
    }

    //-------------------------------------------------------------------
    @Override
    public void goToFirst()
    {
        if (jtbData != null && jtbData.getRowCount() > 0)
        {
            jtbData.getSelectionModel().setSelectionInterval(0, 0);
            Rectangle r = jtbData.getCellRect(0, 0, false);
            jtbData.scrollRectToVisible(r);
            jtbData.repaint();
        }
    }

    //-------------------------------------------------------------------
    @Override
    public void goToLast()
    {
        if (jtbData != null && jtbData.getRowCount() > 0)
        {
            int lastIndex = jtbData.getRowCount() - 1;
            jtbData.getSelectionModel().setSelectionInterval(lastIndex, lastIndex);
            Rectangle r = jtbData.getCellRect(lastIndex, 0, false);
            jtbData.scrollRectToVisible(r);
            jtbData.repaint();
        }
    }

    //-------------------------------------------------------------------
    @Override
    public void goToNext()
    {
        if (jtbData != null && jtbData.getRowCount() > 0 && !jtbData.getSelectionModel().isSelectedIndex(jtbData.getRowCount() - 1))
        {
            int nextIndex = jtbData.getSelectedRow() + 1;
            jtbData.getSelectionModel().setSelectionInterval(nextIndex, nextIndex);
            Rectangle r = jtbData.getCellRect(nextIndex, 0, false);
            jtbData.scrollRectToVisible(r);
            jtbData.repaint();
        }
    }

    //-------------------------------------------------------------------
    @Override
    public void goToPrior()
    {
        if (jtbData != null && jtbData.getRowCount() > 0 && !jtbData.getSelectionModel().isSelectedIndex(0))
        {
            int nextIndex = jtbData.getSelectedRow() - 1;
            jtbData.getSelectionModel().setSelectionInterval(nextIndex, nextIndex);
            Rectangle r = jtbData.getCellRect(nextIndex, 0, false);
            jtbData.scrollRectToVisible(r);
            jtbData.repaint();
        }
    }

    // -------------------------------------------------------------------
    @Override
    public void doFilter(boolean value)
    {
        TableRowSorter sorter = (TableRowSorter<TableModel>) jtbData.getRowSorter();

        if (value)
        {
            String input = JOptionPane.showInputDialog("Introduzca palabra buscada:");

            if (input == null || input.isEmpty())
            {
                sorter.setRowFilter(null);
                jbMessage.setText("Filtro removido");
            }
            else
            {
                sorter.setRowFilter(RowFilter.regexFilter("(?i)" + input));
                jbMessage.setText("Filtro aplicado para:" + input);
            }
        }

        else
        {
            sorter.setRowFilter(null);
            jbMessage.setText("Filtro removido");
        }
    }

    //---------------------------------------------------------------------
    @Override
    public void selectAll()
    {
        if (jtbData != null && jtbData.getRowCount() > 0)
        {
            jtbData.getSelectionModel().setSelectionInterval(0, jtbData.getRowCount() - 1);
        }
    }

    //---------------------------------------------------------------------
    @Override
    public void doPrint()
    {
    }

    //---------------------------------------------------------------------
    @Override
    public void doStartSearch()
    {
        searchWord = "";
        doNextSearch();
    }

    //---------------------------------------------------------------------
    @Override
    public void doNextSearch()
    {
        String currentValue;
        boolean seEncontro = false;

        if (searchWord == null || searchWord.length() < 1)
            setSearchWord(JOptionPane.showInputDialog("Introduzca palabra buscada:"));

        if (searchWord != null && searchWord.length() > 0)
        {
            for (int j = jtbData.getSelectedRow() < 0
                    || jtbData.getSelectedRow() == jtbData.getRowCount() - 1
                            ? 0 : lastRowIndex; j < jtbData.getRowCount() && !seEncontro; j++)
            {
                for (int i = jtbData.getSelectedColumn() < 0
                        || jtbData.getSelectedColumn() == jtbData.getColumnCount() - 1
                                ? 0 : lastColumnIndex; i < jtbData.getColumnCount() && !seEncontro; i++)
                {
                    Component render = jtbData.prepareRenderer(jtbData.getCellRenderer(j, i), j, i);

                    if (render instanceof JLabel)
                        currentValue = ((JLabel) render).getText();
                    else
                        currentValue = jtbData.getValueAt(j, i).toString();

                    if (currentValue.toUpperCase().contains(searchWord))
                    {
                        jtbData.getSelectionModel().setSelectionInterval(j, j);
                        Rectangle r = jtbData.getCellRect(j, i, false);
                        jtbData.scrollRectToVisible(r);
                        jtbData.repaint();
                        lastRowIndex = j + 1;
                        lastColumnIndex = 0;
                        seEncontro = true;
                    }
                }
            }

            if (!seEncontro)
            {
                searchWord = "";
                JOptionPane.showMessageDialog(mainContainer.getRootFrame(), "Se llego al final");
            }
        }
    }

    //---------------------------------------------------------------------
    public void setSearchWord(String word)
    {
       if (word != null)
            searchWord = word.toUpperCase();
        lastRowIndex = 0;
        lastColumnIndex = 0;
    }

    //---------------------------------------------------------------------
    protected void printRecordCount()
    {
        jbContador.setText(jtbData.getRowCount() + " Registro(s)");
    }

    //---------------------------------------------------------------------
    @Override
    public void doExportToXLS()
    {
        if (jtbData != null)
        {
            fileChooser.setDialogTitle("Donde quiere guardar el archivo");
            //fileChooser.setCurrentDirectory(null);
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

            int userSelection = fileChooser.showSaveDialog(mainContainer.getRootFrame());

            if (userSelection == JFileChooser.APPROVE_OPTION)
            {
                File archivo = fileChooser.getSelectedFile();
                if (!archivo.getPath().endsWith(".xls"))
                    archivo = new File(archivo.getPath() + ".xls");

                Workbook ExcelWoorkBook = new HSSFWorkbook();  //WorkbookFactory.create();

                Sheet CurrentSheet = ExcelWoorkBook.createSheet("Export");
                Row CurrentRow;
                Cell CurrentCell;
                HSSFCellStyle style1;
                //HSSFCellStyle style2;
                style1 = (HSSFCellStyle) ExcelWoorkBook.createCellStyle();
                style1.setFillForegroundColor(HSSFColor.LIGHT_ORANGE.index);
                style1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                // Class current;
                boolean theRange = jtbData.getSelectedRowCount() > 1
                        && JOptionPane.showConfirmDialog(mainContainer.getRootFrame(),
                                                         "Ha seleccionado un rango\n ¿Desea exportar unicamente el rango seleccionado?",
                                                         "Pregunta", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
                int[] handlers = jtbData.getSelectedRows();

                for (int j = -1; j < (theRange ? handlers.length : jtbData.getRowCount()); j++)
                {
                    CurrentRow = CurrentSheet.createRow(j + 1);
                    for (int i = 0; i < jtbData.getColumnCount(); i++)
                    {
                        CurrentCell = CurrentRow.createCell(i);

                        if (j < 0)
                        {
                            CurrentCell.setCellValue(jtbData.getColumnName(i));
                            CurrentCell.setCellStyle(style1);
                        }

                        else
                        {
                            //jtbData.getColumnModel().getColumn(0).getC
                            //current = jtbData.getColumnClass(i);
                            Component render = jtbData.prepareRenderer(jtbData.getCellRenderer(theRange ? handlers[j] : j, i), theRange
                                                                       ? handlers[j] : j, i);

                            if (render instanceof JLabel)
                                CurrentCell.setCellValue(((JLabel) render).getText());
                            else
                                CurrentCell.setCellValue(jtbData.getValueAt(theRange ? handlers[j] : j, i).toString());
                        }
                    }
                }

                for (int i = 0; i < jtbData.getColumnCount(); i++)
                    CurrentSheet.autoSizeColumn(i);

                try (FileOutputStream salida = new FileOutputStream(archivo))
                {
                    ExcelWoorkBook.write(salida);
                    salida.close();
                }
                catch (FileNotFoundException ex)
                {
                    Logger.getLogger(JTableDataView.class.getName()).log(Level.SEVERE, null, ex);
                }
                catch (IOException ex)
                {
                    Logger.getLogger(JTableDataView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
