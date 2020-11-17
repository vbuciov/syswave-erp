package com.syswave.forms.miempresa;

import com.orbital.AutoCompleteDocument;
import com.syswave.entidades.common.Entidad;
import com.syswave.entidades.miempresa.Moneda;
import com.syswave.entidades.miempresa.Persona;
import com.syswave.entidades.miempresa.PersonaSalario;
import com.syswave.entidades.miempresa.SalarioComponente;
import com.syswave.entidades.miempresa.Valor;
import com.syswave.swing.models.POJOTableModel;
import com.syswave.forms.databinding.MonedasComboBoxModel;
import com.syswave.forms.databinding.PersonasComboBoxModel;
import com.syswave.forms.databinding.SalarioComponenteTableModel;
import com.syswave.forms.databinding.ValorComboBoxModel;
import com.syswave.swing.table.editors.LookUpComboBoxTableCellEditor;
import com.syswave.swing.table.renders.LookUpComboBoxTableCellRenderer;
import com.syswave.swing.renders.POJOListCellRenderer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingWorker;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;

/**
 *
 * @author sis5
 */
public class JPersonaSalariosDetailView extends javax.swing.JInternalFrame implements TableModelListener
{

    private final int opLOAD_COMPONENTES = 0;
    private final int opLOAD_FRECTIPO = 1;

    IPersonaSalarioMediator parent;
    PersonaSalario current;

    SalarioComponenteTableModel bsComponente;
    PersonasComboBoxModel bsPersonas;
    MonedasComboBoxModel bsMonedas;
    ValorComboBoxModel bsFrecuencia;

    ValorComboBoxModel bsFrecuenciaRender;
    ValorComboBoxModel bsFrecuenciaEditor;

    ValorComboBoxModel bsTipoRender;
    ValorComboBoxModel bsTipoEditor;

    List<SalarioComponente> eliminados;

    boolean esNuevo, construirComponentes;

    TableColumn colNombre, colCantidad, colFrecuencia,
            colComentario, colNumeroCuenta, colClabe, colProveedorCuenta,
            colTipoCuenta, colMaximoCuenta;

    SwingPersonaSalario swSecondPlane;

    /* public JPersonaSalariosDetailView()
     {
     initComponents();
     initAtributes();
     initEvents();
        
     if (jtComponentes.getColumnCount() > 0)
     {
     colFrecuencia = jtComponentes.getColumnModel().getColumn(1);
     colFrecuencia.setCellRenderer(new LookUpComboBoxTableCellRenderer<Valor>(bsFrecuenciaRender));
     colFrecuencia.setCellEditor(new LookUpComboBoxTableCellEditor<Valor>(bsFrecuenciaEditor));
            
     colTipoCuenta = jtComponentes.getColumnModel().getColumn(7);
     colTipoCuenta.setCellRenderer(new LookUpComboBoxTableCellRenderer<Valor>(bsTipoRender));
     colTipoCuenta.setCellEditor(new LookUpComboBoxTableCellEditor<Valor>(bsTipoEditor));
     }
        
     AutoCompleteDocument.enable(jcbEmpleado);
     AutoCompleteDocument.enable(jcbMoneda);
     AutoCompleteDocument.enable(jcbFrecuencia);
     }*/
    JPersonaSalariosDetailView(IPersonaSalarioMediator owner)
    {
        initComponents();
        initAtributes();
        initEvents();
        parent = owner;

        if (jtComponentes.getColumnCount() > 0)
        {
            colNombre = jtComponentes.getColumnModel().getColumn(0);
            colNombre.setPreferredWidth(200);

            colCantidad = jtComponentes.getColumnModel().getColumn(1);
            colCantidad.setPreferredWidth(150);

            colFrecuencia = jtComponentes.getColumnModel().getColumn(2);
            colFrecuencia.setCellRenderer(new LookUpComboBoxTableCellRenderer<Valor>(bsFrecuenciaRender));
            colFrecuencia.setCellEditor(new LookUpComboBoxTableCellEditor<Valor>(bsFrecuenciaEditor));
            colFrecuencia.setPreferredWidth(150);

            colComentario = jtComponentes.getColumnModel().getColumn(3);
            colComentario.setPreferredWidth(300);

            colNumeroCuenta = jtComponentes.getColumnModel().getColumn(4);
            colNumeroCuenta.setPreferredWidth(200);

            colClabe = jtComponentes.getColumnModel().getColumn(5);
            colClabe.setPreferredWidth(200);

            colProveedorCuenta = jtComponentes.getColumnModel().getColumn(6);
            colProveedorCuenta.setPreferredWidth(200);

            colTipoCuenta = jtComponentes.getColumnModel().getColumn(7);
            colTipoCuenta.setCellRenderer(new LookUpComboBoxTableCellRenderer<Valor>(bsTipoRender));
            colTipoCuenta.setCellEditor(new LookUpComboBoxTableCellEditor<Valor>(bsTipoEditor));

            jtComponentes.setRowHeight((int) (jtComponentes.getRowHeight() * 1.5));
        }

        AutoCompleteDocument.enable(jcbEmpleado);
        AutoCompleteDocument.enable(jcbMoneda);
        AutoCompleteDocument.enable(jcbFrecuencia);
    }

    private void initAtributes()
    {
        esNuevo = false;
        construirComponentes = false;

        bsFrecuenciaRender = new ValorComboBoxModel();
        bsFrecuenciaEditor = new ValorComboBoxModel();
        bsTipoRender = new ValorComboBoxModel();
        bsTipoEditor = new ValorComboBoxModel();

        bsComponente = new SalarioComponenteTableModel(new String[]
        {
            "Nombre:{nombre}", "Cantidad:{cantidad}", "Frecuencia:{es_frecuencia}",
            "Comentario:{comentario}", "Número cuenta:{numero_cuenta}",
            "Clabe:{clabe}", "Proveedor cuenta:{proveedor_cuenta}",
            "Tipo cuenta:{es_tipo_cuenta}", "Máximo cuenta:{maximo_cuenta}"
        });
        bsComponente.addTableModelListener(this);
        jtComponentes.setModel(bsComponente);

        bsPersonas = new PersonasComboBoxModel();
        jcbEmpleado.setModel(bsPersonas);
        jcbEmpleado.setRenderer(new POJOListCellRenderer<Persona>());

        bsMonedas = new MonedasComboBoxModel();
        jcbMoneda.setModel(bsMonedas);
        jcbMoneda.setRenderer(new POJOListCellRenderer<Moneda>());

        bsFrecuencia = new ValorComboBoxModel();
        jcbFrecuencia.setModel(bsFrecuencia);
        jcbFrecuencia.setRenderer(new POJOListCellRenderer<Valor>());

        eliminados = new ArrayList<>();
    }

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

        ActionListener toolBarListenerManager
                = new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent evt)
                    {
                        toolBar_actionPerformed(evt);
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
        jbNuevo.addActionListener(toolBarListenerManager);
        jbEliminar.addActionListener(toolBarListenerManager);
        jpPrincipal.addChangeListener(changeListenerManager);
    }

    //---------------------------------------------------------------------
    private void finish_actionPerformed(ActionEvent evt)
    {
        Object sender = evt.getSource();

        if (sender == jbAceptar)
        {
            if (readElement(current))
                if (esNuevo)
                {
                    parent.onAcceptNewElement(current, bsComponente.getData());
                    close();
                }
                else
                {
                    parent.onAcceptModifyElement(current, bsComponente.getData(), eliminados);
                    close();
                };
        }
        else
            close();
    }

    private void toolBar_actionPerformed(ActionEvent evt)
    {
        int rowIndex;

        if (evt.getSource() == jbNuevo)
        {
            SalarioComponente nuevoComponente = new SalarioComponente();
            nuevoComponente.setNombre("");
            nuevoComponente.setCantidad(0.0);
            nuevoComponente.setComentario("");
            nuevoComponente.setNumeroCuenta("");
            nuevoComponente.setClabe("");
            nuevoComponente.setProveedorCuenta("");
            nuevoComponente.setMaximoCuenta(0.0);
            nuevoComponente.setHasOnePersonaSalario(current);
            if (bsTipoEditor.getSize() > 0)
                nuevoComponente.setEsTipoCuenta(bsTipoEditor.getElementAt(0).getId());
            rowIndex = bsComponente.addRow(nuevoComponente);
            jtComponentes.setRowSelectionInterval(rowIndex, rowIndex);
        }
        else if (evt.getSource() == jbEliminar)
        {
            if (JOptionPane.showConfirmDialog(this, String.format("Ha seleccionado %d registro(s) para ser eleiminado(s)\n¿Esta seguro de continuar?", jtComponentes.getSelectedRowCount())) == JOptionPane.OK_OPTION)
            {
                int[] rowsHandlers = jtComponentes.getSelectedRows();
                List<SalarioComponente> selecteds = bsComponente.removeRows(rowsHandlers);

                for (SalarioComponente elemento : selecteds)
                {
                    if (!elemento.isNew())
                        eliminados.add(elemento);
                }
            }
        }
    }

    private void bodyTabbed_stateChanged(ChangeEvent e)
    {
        if (swSecondPlane == null || swSecondPlane.isDone())
        {
            JTabbedPane pane = (JTabbedPane) e.getSource();
            if (pane.getSelectedComponent() == tabComponentes && !construirComponentes)
            {
                List<Object> parametros = new ArrayList<Object>();
                swSecondPlane = new SwingPersonaSalario();
                if (esNuevo)
                {
                    parametros.add(opLOAD_FRECTIPO);
                }
                else
                {
                    parametros.add(opLOAD_COMPONENTES);
                    SalarioComponente filtro = new SalarioComponente();
                    filtro.setIdPersona(current.getIdPersona());
                    parametros.add(filtro);
                }

                swSecondPlane.execute(parametros);
                construirComponentes = true;
            }
        }
    }

    private void crearFrecuencia()
    {
        List<Valor> SonTipos = new ArrayList<>();

        SonTipos.add(new Valor(0, "Semanal"));
        SonTipos.add(new Valor(1, "Quincenal"));
        SonTipos.add(new Valor(2, "Mensual"));
        SonTipos.add(new Valor(3, "Anual"));

        bsFrecuencia.setData(SonTipos);
    }

    private List<Valor> obtieneFrecuencia()
    {
        List<Valor> SonFrecuencia = new ArrayList<>();

        SonFrecuencia.add(new Valor(0, "Semanal"));
        SonFrecuencia.add(new Valor(1, "Quincenal"));
        SonFrecuencia.add(new Valor(2, "Mensual"));
        SonFrecuencia.add(new Valor(3, "Anual"));

        return SonFrecuencia;
    }

    private List<Valor> obtieneTipos()
    {
        List<Valor> SonTipos = new ArrayList<>();

        SonTipos.add(new Valor(0, "Débito"));
        SonTipos.add(new Valor(1, "Nómina"));

        return SonTipos;
    }

    private void setDisplayData(List<Valor> frecuencias, List<Valor> tipos)
    {
        bsFrecuenciaRender.setData(frecuencias);
        bsFrecuenciaEditor.setData(frecuencias);

        bsTipoRender.setData(tipos);
        bsTipoEditor.setData(tipos);
    }

    private void setDisplayData(List<SalarioComponente> dataSource)
    {
        setDisplayData(obtieneFrecuencia(), obtieneTipos());
        if (dataSource.size() > 0)
            bsComponente.setData(dataSource);
    }

    void prepareForModify(PersonaSalario elemento)
    {
        current = elemento;
        esNuevo = false;
        this.setTitle("Modificando salario");
        bsPersonas.setData(parent.obtenerListaPersonas());
        bsMonedas.setData(parent.obtenerListaMonedas());
        crearFrecuencia();
        writeElement(current);
    }

    void prepareForNew()
    {
        current = new PersonaSalario();
        esNuevo = true;
        this.setTitle("Nuevo");
        bsPersonas.setData(parent.obtenerListaPersonas());
        bsMonedas.setData(parent.obtenerListaMonedas());
        crearFrecuencia();
    }

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

    private void writeElement(PersonaSalario elemento)
    {
        jcbEmpleado.setSelectedItem(bsPersonas.getElementAt(bsPersonas.indexOfValue(elemento.getIdPersona())));
        jdcFecha.setDate(elemento.getFechaVigor());
        jtfCantidad.setValue((double) elemento.getSueldoNeto());
        jcbMoneda.setSelectedItem(bsMonedas.getElementAt(bsMonedas.indexOfValue(elemento.getIdMoneda())));
        jcbFrecuencia.setSelectedIndex(elemento.getFrecuencia());//jcbFrecuencia.setSelectedItem(bsFrecuencia.getElementAt(bsFrecuencia.indexOfValue(elemento.getIdMoneda())));
    }

    public boolean readElement(PersonaSalario elemento)
    {
        boolean resultado = false;
        if (jcbEmpleado.getSelectedIndex() > -1 && jdcFecha.getDate() != null
                && jtfCantidad.getValue() != null && jcbMoneda.getSelectedIndex() > -1
                && jcbFrecuencia.getSelectedIndex() > -1)
        {
            elemento.setIdPersona((int) bsPersonas.getSelectedValue());
            elemento.setFechaVigor(jdcFecha.getDate());
            elemento.setSueldoNeto((double) jtfCantidad.getValue());
            elemento.setIdMoneda((int) bsMonedas.getSelectedValue());
            elemento.setFrecuencia((int) bsFrecuencia.getSelectedValue());
            resultado = true;
        }
        return resultado;
    }

    //---------------------------------------------------------------------
    public void close()
    {
        setVisible(false);
        dispose();
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
        jlbIcon = new javax.swing.JLabel();
        jpActions = new javax.swing.JPanel();
        jbCancelar = new javax.swing.JButton();
        jbAceptar = new javax.swing.JButton();
        jpPrincipal = new javax.swing.JTabbedPane();
        tabGeneral = new javax.swing.JPanel();
        jpDatos = new javax.swing.JPanel();
        jlbFrecuencia = new javax.swing.JLabel();
        jlbEmpleado = new javax.swing.JLabel();
        jlbFechaVigor = new javax.swing.JLabel();
        jlbSueldo = new javax.swing.JLabel();
        jlbMoneda = new javax.swing.JLabel();
        jcbEmpleado = new javax.swing.JComboBox();
        jdcFecha = new com.toedter.calendar.JDateChooser();
        jtfCantidad = new JFormattedTextField(0.0);
        jcbMoneda = new javax.swing.JComboBox();
        jcbFrecuencia = new javax.swing.JComboBox();
        tabComponentes = new javax.swing.JPanel();
        jtbBotones = new javax.swing.JToolBar();
        jbNuevo = new javax.swing.JButton();
        jbEliminar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtComponentes = new javax.swing.JTable();

        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setPreferredSize(new java.awt.Dimension(660, 500));

        jpEncabezado.setBackground(new java.awt.Color(232, 171, 236));
        jpEncabezado.setPreferredSize(new java.awt.Dimension(80, 100));

        jlbIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/syswave/forms/resources/window.png"))); // NOI18N

        javax.swing.GroupLayout jpEncabezadoLayout = new javax.swing.GroupLayout(jpEncabezado);
        jpEncabezado.setLayout(jpEncabezadoLayout);
        jpEncabezadoLayout.setHorizontalGroup(
            jpEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpEncabezadoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlbIcon)
                .addContainerGap(516, Short.MAX_VALUE))
        );
        jpEncabezadoLayout.setVerticalGroup(
            jpEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpEncabezadoLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jlbIcon)
                .addContainerGap(36, Short.MAX_VALUE))
        );

        getContentPane().add(jpEncabezado, java.awt.BorderLayout.NORTH);

        jpActions.setPreferredSize(new java.awt.Dimension(100, 50));
        jpActions.setRequestFocusEnabled(false);
        jpActions.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jbCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/syswave/forms/resources/button-cross.png"))); // NOI18N
        jbCancelar.setText("Cancelar");
        jpActions.add(jbCancelar);

        jbAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/syswave/forms/resources/button-tick.png"))); // NOI18N
        jbAceptar.setText("Aceptar");
        jpActions.add(jbAceptar);

        getContentPane().add(jpActions, java.awt.BorderLayout.SOUTH);

        jpPrincipal.setPreferredSize(new java.awt.Dimension(158, 222));

        tabGeneral.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 25));

        java.awt.GridBagLayout jpDatosLayout = new java.awt.GridBagLayout();
        jpDatosLayout.columnWidths = new int[] {0, 5, 0, 5, 0};
        jpDatosLayout.rowHeights = new int[] {0, 12, 0, 12, 0, 12, 0, 12, 0};
        jpDatos.setLayout(jpDatosLayout);

        jlbFrecuencia.setText("Frecuencia:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        jpDatos.add(jlbFrecuencia, gridBagConstraints);

        jlbEmpleado.setText("Empleado:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        jpDatos.add(jlbEmpleado, gridBagConstraints);

        jlbFechaVigor.setText("Fecha vigor:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        jpDatos.add(jlbFechaVigor, gridBagConstraints);

        jlbSueldo.setText("Sueldo:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        jpDatos.add(jlbSueldo, gridBagConstraints);

        jlbMoneda.setText("Moneda:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        jpDatos.add(jlbMoneda, gridBagConstraints);

        jcbEmpleado.setName(""); // NOI18N
        jcbEmpleado.setPreferredSize(new java.awt.Dimension(400, 27));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jpDatos.add(jcbEmpleado, gridBagConstraints);

        jdcFecha.setPreferredSize(new java.awt.Dimension(200, 27));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jpDatos.add(jdcFecha, gridBagConstraints);

        jtfCantidad.setPreferredSize(new java.awt.Dimension(200, 27));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jpDatos.add(jtfCantidad, gridBagConstraints);

        jcbMoneda.setPreferredSize(new java.awt.Dimension(200, 27));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jpDatos.add(jcbMoneda, gridBagConstraints);

        jcbFrecuencia.setPreferredSize(new java.awt.Dimension(200, 27));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jpDatos.add(jcbFrecuencia, gridBagConstraints);

        tabGeneral.add(jpDatos);

        jpPrincipal.addTab("General", tabGeneral);

        tabComponentes.setLayout(new java.awt.BorderLayout());

        jtbBotones.setRollover(true);

        jbNuevo.setText("Nuevo");
        jbNuevo.setFocusable(false);
        jbNuevo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbNuevo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jtbBotones.add(jbNuevo);

        jbEliminar.setText("Eliminar");
        jbEliminar.setFocusable(false);
        jbEliminar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbEliminar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jtbBotones.add(jbEliminar);

        tabComponentes.add(jtbBotones, java.awt.BorderLayout.NORTH);

        jtComponentes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {

            },
            new String []
            {

            }
        ));
        jtComponentes.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane1.setViewportView(jtComponentes);

        tabComponentes.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jpPrincipal.addTab("Componentes salariales", tabComponentes);

        getContentPane().add(jpPrincipal, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbAceptar;
    private javax.swing.JButton jbCancelar;
    private javax.swing.JButton jbEliminar;
    private javax.swing.JButton jbNuevo;
    private javax.swing.JComboBox jcbEmpleado;
    private javax.swing.JComboBox jcbFrecuencia;
    private javax.swing.JComboBox jcbMoneda;
    private com.toedter.calendar.JDateChooser jdcFecha;
    private javax.swing.JLabel jlbEmpleado;
    private javax.swing.JLabel jlbFechaVigor;
    private javax.swing.JLabel jlbFrecuencia;
    private javax.swing.JLabel jlbIcon;
    private javax.swing.JLabel jlbMoneda;
    private javax.swing.JLabel jlbSueldo;
    private javax.swing.JPanel jpActions;
    private javax.swing.JPanel jpDatos;
    private javax.swing.JPanel jpEncabezado;
    private javax.swing.JTabbedPane jpPrincipal;
    private javax.swing.JTable jtComponentes;
    private javax.swing.JToolBar jtbBotones;
    private javax.swing.JFormattedTextField jtfCantidad;
    private javax.swing.JPanel tabComponentes;
    private javax.swing.JPanel tabGeneral;
    // End of variables declaration//GEN-END:variables
    /*
     ============================================================================
     */

    private class SwingPersonaSalario extends SwingWorker<List<Object>, Void>
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
        public List<Object> doInBackground()
        {
            setProgress(50);
            if (!isCancelled() && arguments != null && arguments.size() > 0)
            {
                int opcion = (int) arguments.get(0); //Debe haber un entero en la primera posición
                switch (opcion)
                {
                    case opLOAD_COMPONENTES:
                        arguments.add(parent.obtenerSalarioComponentes((SalarioComponente) arguments.get(1)));
                        break;

                    case opLOAD_FRECTIPO:
                        arguments.add(obtieneFrecuencia());
                        arguments.add(obtieneTipos());
                        break;
                }

                setProgress(100);
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

                    if (results.size() > 0)
                    {
                        int opcion = (int) results.get(0);

                        switch (opcion)
                        {
                            case opLOAD_COMPONENTES:
                                List<SalarioComponente> listCompo = (List<SalarioComponente>) arguments.get(2);
                                setDisplayData(listCompo);
                                break;

                            case opLOAD_FRECTIPO:
                                List<Valor> listFrecuencias = (List<Valor>) arguments.get(1);
                                List<Valor> listTipos = (List<Valor>) arguments.get(2);
                                setDisplayData(listFrecuencias, listTipos);
                                break;
                        }

                        results.clear();
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
