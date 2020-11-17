package com.syswave.forms.miempresa;

import com.syswave.entidades.common.Entidad;
import com.syswave.entidades.miempresa.Horario;
import com.syswave.entidades.miempresa.Jornada;
import com.syswave.swing.models.POJOTableModel;
import com.syswave.forms.databinding.HorarioTableModel;
import com.syswave.swing.table.editors.SpinnerTableCellEditor;
import com.syswave.swing.table.renders.DateTimeTableCellRenderer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingWorker;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;

/**
 *
 * @author sis5
 */
public class JJornadaDetailView extends javax.swing.JInternalFrame implements TableModelListener
{

    private final int opLOAD_HORARIOS = 0;
    Jornada elementoActual;
    boolean esNuevo, construirHorarios;
    IJornadaMediator parent;
    HorarioTableModel bsHorario;
    JornadaDetailSwingWorker swSecondPlane;
    List<Horario> eliminados;

    TableColumn colHoraInicio, colHoraFin;

    //---------------------------------------------------------------------
    public JJornadaDetailView(IJornadaMediator owner)
    {
        parent = owner;
        initAtributes();
        initComponents();
        initEvents();

        if (jtbHorarios.getColumnCount() > 0)
        {
            colHoraInicio = jtbHorarios.getColumnModel().getColumn(1);
            colHoraInicio.setCellRenderer(new DateTimeTableCellRenderer("HH:mm"));
            colHoraInicio.setCellEditor(new SpinnerTableCellEditor(new SpinnerDateModel(new Date(), null, null, java.util.Calendar.HOUR_OF_DAY), "HH:mm"));
            colHoraInicio.setPreferredWidth(70);

            colHoraFin = jtbHorarios.getColumnModel().getColumn(2);
            colHoraFin.setCellRenderer(new DateTimeTableCellRenderer("HH:mm"));
            colHoraFin.setCellEditor(new SpinnerTableCellEditor(new SpinnerDateModel(new Date(), null, null, java.util.Calendar.HOUR_OF_DAY), "HH:mm"));
            colHoraFin.setPreferredWidth(70);

            //Nota: Debido a los renders que se estan utilizando es necesario tener un renglón más alto.
            jtbHorarios.setRowHeight((int) (jtbHorarios.getRowHeight() * 1.5));
        }

        eliminados = new ArrayList<>();
    }

    //---------------------------------------------------------------------
    private void initAtributes()
    {
        esNuevo = true;
        construirHorarios = false;
        bsHorario = new HorarioTableModel(new String[]
        {
            "Nombre:{nombre}", "Hora Inicio:{hora_inicio}", "Hora Fin:{hora_fin}"
        });

        bsHorario.addTableModelListener(this);
    }

    //---------------------------------------------------------------------
    private void initEvents()
    {
        ActionListener actionListenerManager
                = new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent evt)
                    {
                        finish_actionPerformed(evt);
                    }
                };

        ActionListener composeActionListener
                = new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent evt)
                    {
                        composeToolBar_actionPerformed(evt);
                    }
                };

        ChangeListener changeListenerManager = new ChangeListener()
        {

            @Override
            public void stateChanged(ChangeEvent e)
            {
                bodyTabbed_stateChanged(e);
            }
        };

        jbAceptar.addActionListener(actionListenerManager);
        jbCancelar.addActionListener(actionListenerManager);
        jtpBody.addChangeListener(changeListenerManager);
        jbNuevo.addActionListener(composeActionListener);
        jbEliminar.addActionListener(composeActionListener);
    }

    //---------------------------------------------------------------------
    private void bodyTabbed_stateChanged(ChangeEvent e)
    {
        if (swSecondPlane == null || swSecondPlane.isDone())
        {
            JTabbedPane pane = (JTabbedPane) e.getSource();

            if (!esNuevo)
                if (pane.getSelectedComponent() == tabHorario && !construirHorarios)
                {
                    List<Object> parametros = new ArrayList<Object>();
                    swSecondPlane = new JornadaDetailSwingWorker();
                    parametros.add(opLOAD_HORARIOS);
                    parametros.add(elementoActual);
                    swSecondPlane.execute(parametros);
                    construirHorarios = true;
                }
        }
    }

    //---------------------------------------------------------------------
    private void composeToolBar_actionPerformed(ActionEvent evt)
    {
        int rowIndex;

        if (evt.getSource() == jbNuevo)
        {
            Horario nuevoHorario = new Horario();
            nuevoHorario.setNombre("");
            nuevoHorario.setHasOneJornada(elementoActual);
            rowIndex = bsHorario.addRow(nuevoHorario);
            jtbHorarios.setRowSelectionInterval(rowIndex, rowIndex);
        }
        else if (evt.getSource() == jbEliminar)
        {
            if (JOptionPane.showConfirmDialog(this, String.format("Ha seleccionado %d registro(s) para ser eleiminado(s)\n¿Esta seguro de continuar?", jtbHorarios.getSelectedRowCount())) == JOptionPane.OK_OPTION)
            {
                int[] rowsHandlers = jtbHorarios.getSelectedRows();
                List<Horario> selecteds = bsHorario.removeRows(rowsHandlers);

                for (Horario elemento : selecteds)
                {
                    if (!elemento.isNew())
                        eliminados.add(elemento);
                }
            }
        }
    }

    //---------------------------------------------------------------------
    private boolean validaNombresHorarios()
    {
        boolean resul = true;
        int rows = bsHorario.getRowCount();
        for (int i = 0; i < rows; i++)
        {
            String nom = (String) bsHorario.getValueAt(i, 0);
            if (nom.equals(""))
                resul = false;
        }
        return resul;
    }

    //---------------------------------------------------------------------
    private void finish_actionPerformed(ActionEvent evt)
    {
        Object sender = evt.getSource();

        if (sender == jbAceptar)
        {
            if (readElement(elementoActual))
            {
                tryStopDetailCellEditor(jtbHorarios);
                if (validaNombresHorarios())
                {
                    if (esNuevo)
                    {
                        parent.onAcceptNewElement(elementoActual, bsHorario.getData());
                        close();
                    }
                    else
                    {
                        parent.onAcceptModifyElement(elementoActual, bsHorario.getData(), eliminados);
                        close();
                    }
                }
                else
                    JOptionPane.showMessageDialog(this, "El nombre de algún horario está en blanco.", "", JOptionPane.PLAIN_MESSAGE);

            }
        }
        else
            close();
    }

    //---------------------------------------------------------------------
    private void tryStopDetailCellEditor(JTable subDetailView)
    {
        if (subDetailView.isEditing())
        {
            TableCellEditor editor;
            editor = subDetailView.getCellEditor();
            if (editor != null)
                editor.stopCellEditing();
        }
    }

    //---------------------------------------------------------------------
    public void close()
    {
        setVisible(false);
        dispose();
    }

    //---------------------------------------------------------------------
    void prepareForModify(Jornada elemento)
    {
        this.elementoActual = elemento;
        esNuevo = false;
        this.setTitle("Modificando jornada " + elemento.getNombre().toString());
        writeElement(elemento);
    }

    //---------------------------------------------------------------------
    void prepareForNew()
    {
        elementoActual = new Jornada();
        esNuevo = true;
        this.setTitle("Nuevo");
    }

    //---------------------------------------------------------------------
    private void writeElement(Jornada elemento)
    {
        jtfNombre.setText(elemento.getNombre());
        jtfTiempoEfectivo.setText(elemento.getTiempo_efectivo().toString());
        jtfDescripcion.setText(elemento.getDescripcion());
    }

    //---------------------------------------------------------------------
    private boolean readElement(Jornada elemento)
    {
        boolean resultado = false;

        if (!jtfNombre.getText().isEmpty() && !jtfTiempoEfectivo.getText().isEmpty())
        {
            elemento.setNombre(jtfNombre.getText());
            elemento.setTiempo_efectivo(Integer.parseInt(jtfTiempoEfectivo.getText()));
            elemento.setDescripcion(jtfDescripcion.getText());
            resultado = true;
        }
        else
            JOptionPane.showMessageDialog(this, "Asegúrese de proporcionar el nombre y tiempo efectivo de la jornada.", "", JOptionPane.PLAIN_MESSAGE);
        return resultado;
    }

    //---------------------------------------------------------------------
    @Override
    public void tableChanged(TableModelEvent e)
    {
        //Nota: Cuando una columna comienza a editarse, también se dispara el evento... pero
        //no sirve de nada porque no marca la columna exacta.
        if (e.getType() == TableModelEvent.UPDATE && e.getColumn() != TableModelEvent.ALL_COLUMNS)
        {
            int row = e.getFirstRow();

            if (row != TableModelEvent.HEADER_ROW)
            {
                POJOTableModel model = (POJOTableModel) e.getSource();
                //String columnName = model.getColumnName(column);
                Entidad data = (Entidad) model.getElementAt(row);

                if (!data.isSet())
                    data.setModified();
            }
        }
    }

    private void setDisplayData(List<Horario> values)
    {
        bsHorario.setData(values);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {
        java.awt.GridBagConstraints gridBagConstraints;

        jpEncabezado = new javax.swing.JPanel();
        jlbIcono = new javax.swing.JLabel();
        jtpBody = new javax.swing.JTabbedPane();
        tabGeneral = new javax.swing.JPanel();
        jpDatos = new javax.swing.JPanel();
        jlbNombre = new javax.swing.JLabel();
        jtfNombre = new javax.swing.JTextField();
        jlbTiempoEfectivo = new javax.swing.JLabel();
        jtfTiempoEfectivo = new javax.swing.JFormattedTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtfDescripcion = new javax.swing.JTextArea();
        tabHorario = new javax.swing.JPanel();
        jtbHorario = new javax.swing.JToolBar();
        jbNuevo = new javax.swing.JButton();
        jbEliminar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtbHorarios = new javax.swing.JTable();
        jpActions = new javax.swing.JPanel();
        jbCancelar = new javax.swing.JButton();
        jbAceptar = new javax.swing.JButton();

        setClosable(true);
        setResizable(true);
        setTitle("Jornada");
        setVisible(false);

        jpEncabezado.setBackground(new java.awt.Color(92, 99, 128));
        jpEncabezado.setName(""); // NOI18N

        jlbIcono.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/syswave/forms/resources/window.png"))); // NOI18N

        javax.swing.GroupLayout jpEncabezadoLayout = new javax.swing.GroupLayout(jpEncabezado);
        jpEncabezado.setLayout(jpEncabezadoLayout);
        jpEncabezadoLayout.setHorizontalGroup(
            jpEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpEncabezadoLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(jlbIcono)
                .addContainerGap(437, Short.MAX_VALUE))
        );
        jpEncabezadoLayout.setVerticalGroup(
            jpEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpEncabezadoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlbIcono, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(jpEncabezado, java.awt.BorderLayout.NORTH);

        jpDatos.setLayout(new java.awt.GridBagLayout());

        jlbNombre.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbNombre.setText("Nombre:");
        jlbNombre.setToolTipText("");
        jlbNombre.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        jpDatos.add(jlbNombre, gridBagConstraints);

        jtfNombre.setPreferredSize(new java.awt.Dimension(200, 27));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jpDatos.add(jtfNombre, gridBagConstraints);

        jlbTiempoEfectivo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbTiempoEfectivo.setText("Tiempo efectivo:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        jpDatos.add(jlbTiempoEfectivo, gridBagConstraints);

        jtfTiempoEfectivo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        jtfTiempoEfectivo.setPreferredSize(new java.awt.Dimension(120, 27));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jpDatos.add(jtfTiempoEfectivo, gridBagConstraints);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Descripción:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_END;
        jpDatos.add(jLabel3, gridBagConstraints);

        jtfDescripcion.setColumns(20);
        jtfDescripcion.setLineWrap(true);
        jtfDescripcion.setRows(5);
        jtfDescripcion.setWrapStyleWord(true);
        jScrollPane2.setViewportView(jtfDescripcion);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        jpDatos.add(jScrollPane2, gridBagConstraints);

        tabGeneral.add(jpDatos);

        jtpBody.addTab("General", tabGeneral);

        tabHorario.setLayout(new java.awt.BorderLayout());

        jtbHorario.setRollover(true);

        jbNuevo.setText("Nuevo");
        jbNuevo.setFocusable(false);
        jbNuevo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbNuevo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jtbHorario.add(jbNuevo);

        jbEliminar.setText("Eliminar");
        jbEliminar.setFocusable(false);
        jbEliminar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbEliminar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jtbHorario.add(jbEliminar);

        tabHorario.add(jtbHorario, java.awt.BorderLayout.PAGE_START);

        jtbHorarios.setModel(bsHorario);
        jScrollPane1.setViewportView(jtbHorarios);

        tabHorario.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jtpBody.addTab("Horarios", tabHorario);

        getContentPane().add(jtpBody, java.awt.BorderLayout.CENTER);

        jpActions.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jbCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/syswave/forms/resources/button-cross.png"))); // NOI18N
        jbCancelar.setText("Cancelar");
        jpActions.add(jbCancelar);

        jbAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/syswave/forms/resources/button-tick.png"))); // NOI18N
        jbAceptar.setText("Aceptar");
        jpActions.add(jbAceptar);

        getContentPane().add(jpActions, java.awt.BorderLayout.SOUTH);

        setBounds(0, 0, 524, 349);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton jbAceptar;
    private javax.swing.JButton jbCancelar;
    private javax.swing.JButton jbEliminar;
    private javax.swing.JButton jbNuevo;
    private javax.swing.JLabel jlbIcono;
    private javax.swing.JLabel jlbNombre;
    private javax.swing.JLabel jlbTiempoEfectivo;
    private javax.swing.JPanel jpActions;
    private javax.swing.JPanel jpDatos;
    private javax.swing.JPanel jpEncabezado;
    private javax.swing.JToolBar jtbHorario;
    private javax.swing.JTable jtbHorarios;
    private javax.swing.JTextArea jtfDescripcion;
    private javax.swing.JTextField jtfNombre;
    private javax.swing.JFormattedTextField jtfTiempoEfectivo;
    private javax.swing.JTabbedPane jtpBody;
    private javax.swing.JPanel tabGeneral;
    private javax.swing.JPanel tabHorario;
    // End of variables declaration//GEN-END:variables

    private class JornadaDetailSwingWorker extends SwingWorker<List<Object>, Void>
    {

        private List<Object> arguments;

        //------------------------------------------------------------------
        public void execute(List<Object> values)
        {
            arguments = values;
            execute();
        }

        //------------------------------------------------------------------
        @Override
        protected List<Object> doInBackground() throws Exception
        {
            if (!isCancelled() && arguments != null)
            {
                int opcion = (int) arguments.get(0); //Debe haber un entero en la primera posición

                switch (opcion)
                {
                    case opLOAD_HORARIOS:
                        arguments.add(parent.obtenerHorarios((Jornada) arguments.get(1)));
                        break;

                }

                return arguments;
            }

            else
                return null;
        }

        //------------------------------------------------------------------
        @Override
        public void done()
        {
            try
            {
                if (!isCancelled())
                {
                    List<Object> results = get();
                    int opcion = (int) results.get(0); //Debe haber un entero en la primera posición

                    switch (opcion)
                    {
                        case opLOAD_HORARIOS:
                            setDisplayData((List<Horario>) results.get(2));
                            break;
                    }

                }
            }
            catch (InterruptedException ignore)
            {
            }
            catch (java.util.concurrent.ExecutionException e)
            {
                String why;
                Throwable cause = e.getCause();
                if (cause != null)
                {
                    why = cause.getMessage();
                }
                else
                {
                    why = e.getMessage();
                }
                System.err.println("Error retrieving file: " + why);
            }
            finally
            {
                if (arguments != null && arguments.size() > 0)
                    arguments.clear();
            }
        }
    }
}
