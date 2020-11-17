package com.syswave.forms.miempresa;

import com.orbital.AutoCompleteDocument;
import com.syswave.entidades.common.Entidad;
import com.syswave.entidades.miempresa.Bien;
import com.syswave.entidades.miempresa.BienVariante;
import com.syswave.entidades.miempresa.Categoria;
import com.syswave.entidades.configuracion.Unidad;
import com.syswave.entidades.miempresa.Valor;
import com.syswave.forms.databinding.BienVariantesTableModel;
import com.syswave.forms.databinding.CategoriasComboBoxModel;
import com.syswave.swing.models.POJOTableModel;
import com.syswave.forms.databinding.UnidadComboBoxModel;
import com.syswave.forms.databinding.ValorComboBoxModel;
import com.syswave.swing.table.editors.LookUpComboBoxTableCellEditor;
import com.syswave.swing.table.renders.LookUpComboBoxTableCellRenderer;
import com.syswave.swing.renders.POJOListCellRenderer;
import com.syswave.logicas.configuracion.UnidadesBusinessLogic;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingWorker;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class JGrupoBienesDetailView extends javax.swing.JInternalFrame implements TableModelListener
{

    private final int opLOAD_VARIANTES = 0;
    private final int opLOAD_UNIDADES = 1;

    UnidadesBusinessLogic unidades;
    ITipoBienMediator parent;

    Bien elementoActual;
    BienVariantesTableModel bsVariantes;
    CategoriasComboBoxModel bsCategorias;
    ValorComboBoxModel bsTipos;
    UnidadComboBoxModel bsUnidadMasaRender;
    UnidadComboBoxModel bsUnidadMasaEditor;
    UnidadComboBoxModel bsUnidadLongitudRender;
    UnidadComboBoxModel bsUnidadLongitudEditor;

    boolean esNuevo, construidoDirecciones;
    TableColumn colDescripcion;
    TableColumn colvalorMasa;

    /*TableColumn colUnidadMasa;
    TableColumn colUnidadLongitud;*/

    TipoBienDetailCargarSwingWorker swCargador;
    List<BienVariante> deleteds;

    /**
     * Creates new form JBienDetailView
     *
     * @param owner
     */
    public JGrupoBienesDetailView(ITipoBienMediator owner)
    {
        initAtributes();
        initComponents();
        initEvents();
        parent = owner;

        if (jtbPresentaciones.getColumnCount() > 0)
        {
            colDescripcion = jtbPresentaciones.getColumnModel().getColumn(0);
            colDescripcion.setPreferredWidth(250);

            colvalorMasa = jtbPresentaciones.getColumnModel().getColumn(1);
            colvalorMasa.setPreferredWidth(150);

            /*colUnidadMasa = jtbPresentaciones.getColumnModel().getColumn(5);
            colUnidadMasa.setCellRenderer(new LookUpComboBoxTableCellRenderer<Unidad>(bsUnidadMasaRender));
            colUnidadMasa.setCellEditor(new LookUpComboBoxTableCellEditor<Unidad>(bsUnidadMasaEditor));
            colUnidadMasa.setPreferredWidth(150);

            colUnidadLongitud = jtbPresentaciones.getColumnModel().getColumn(9);
            colUnidadLongitud.setCellRenderer(new LookUpComboBoxTableCellRenderer<Unidad>(bsUnidadLongitudRender));
            colUnidadLongitud.setCellEditor(new LookUpComboBoxTableCellEditor<Unidad>(bsUnidadLongitudEditor));
            colUnidadLongitud.setPreferredWidth(150);*/

            jtbPresentaciones.setRowHeight((int) (jtbPresentaciones.getRowHeight() * 1.5));
        }
        
        AutoCompleteDocument.enable(jcbCategorias);
        AutoCompleteDocument.enable(jcbTipos);

    }

    //---------------------------------------------------------------------
    private void initAtributes()
    {
        unidades = new UnidadesBusinessLogic("configuracion");

        esNuevo = true;
        construidoDirecciones = false;
        bsTipos = new ValorComboBoxModel();
        bsCategorias = new CategoriasComboBoxModel();
        bsUnidadMasaRender = new UnidadComboBoxModel();
        bsUnidadMasaEditor = new UnidadComboBoxModel();
        bsUnidadLongitudRender = new UnidadComboBoxModel();
        bsUnidadLongitudEditor = new UnidadComboBoxModel();

        bsVariantes = new BienVariantesTableModel(new String[]
        {
            "Descripción:{descripcion}", 
            "Activo:{es_activo}", 
            "Controlar Inventario:{es_inventario}", 
            "Comercializar:{es_comercializar}"
        });
        bsVariantes.addTableModelListener(this);

        deleteds = new ArrayList<>();
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

        ActionListener addressActionListener
                = new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent evt)
                    {
                        presentationToolBar_actionPerformed(evt);
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
        jtoolNuevoDireccion.addActionListener(addressActionListener);
        jtoolEliminarDireccion.addActionListener(addressActionListener);
    }

    //---------------------------------------------------------------------
    private void bodyTabbed_stateChanged(ChangeEvent e)
    {
        if (swCargador == null || swCargador.isDone())
        {

            JTabbedPane pane = (JTabbedPane) e.getSource();
            if (pane.getSelectedComponent() == tabPresentaciones && !construidoDirecciones)
            {
                List<Object> parametros = new ArrayList<Object>();
                swCargador = new TipoBienDetailCargarSwingWorker();
                if (esNuevo)
                    parametros.add(opLOAD_UNIDADES);
                else
                {
                    parametros.add(opLOAD_VARIANTES);
                    parametros.add(elementoActual);
                }
                swCargador.execute(parametros);
                construidoDirecciones = true;
            }

        }
    }

    //---------------------------------------------------------------------
    private void finish_actionPerformed(ActionEvent evt)
    {
        Object sender = evt.getSource();

        if (sender == jbAceptar)
        {
            if (readElement(elementoActual))
            {
                if (jtbPresentaciones.isEditing())
                {
                    TableCellEditor editor = jtbPresentaciones.getCellEditor();
                    if (editor != null)
                        editor.stopCellEditing();

                }

                if (esNuevo)
                    parent.onAcceptNewElement(elementoActual, bsVariantes.getData(), deleteds);

                else
                {
                    elementoActual.setModified();
                    parent.onAcceptModifyElement(elementoActual, bsVariantes.getData(), deleteds);
                }

                close();
            }
        }

        else
            close();
    }

    //---------------------------------------------------------------------
    private void presentationToolBar_actionPerformed(ActionEvent evt)
    {
        int rowIndex;

        if (evt.getSource() == jtoolNuevoDireccion)
        {
            if (bsUnidadMasaEditor.getData().size() > 0 && bsUnidadLongitudEditor.getData().size() > 0)
            {
                BienVariante nuevaPresentacion = new BienVariante();
                nuevaPresentacion.setHasOneGrupo(elementoActual);
                nuevaPresentacion.setNivel(0);
                /*nuevaPresentacion.setMasa(0);
                nuevaPresentacion.setIdUnidadMasa(bsUnidadMasaEditor.getData().get(0).getId());
                nuevaPresentacion.setAncho(0);
                nuevaPresentacion.setAlto(0);
                nuevaPresentacion.setLargo(0);
                nuevaPresentacion.setIdUnidadLongitud(bsUnidadLongitudEditor.getData().get(0).getId());*/
                nuevaPresentacion.setInventarioCcomo(0);
                nuevaPresentacion.setDescripcion("");
                rowIndex = bsVariantes.addRow(nuevaPresentacion);
                jtbPresentaciones.setRowSelectionInterval(rowIndex, rowIndex);
                //jtbDirecciones.editCellAt(rowIndex, 0);
            }

            else
                JOptionPane.showMessageDialog(rootPane, "Para continuar requiere tener capturadas unidades de medida", "Información", JOptionPane.WARNING_MESSAGE);
        }

        else if (evt.getSource() == jtoolEliminarDireccion)
        {
            if (JOptionPane.showConfirmDialog(this, String.format("Ha seleccionado %d registro(s) para ser eleiminado(s)\n¿Esta seguro de continuar?", jtbPresentaciones.getSelectedRowCount())) == JOptionPane.OK_OPTION)
            {
                int[] rowsHandlers = jtbPresentaciones.getSelectedRows();
                List<BienVariante> selecteds = bsVariantes.removeRows(rowsHandlers);

                for (BienVariante elemento : selecteds)
                {
                    if (!elemento.isNew())
                        deleteds.add(elemento);
                }
            }
        }
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

    //---------------------------------------------------------------------
    public void prepareForNew()
    {
        elementoActual = new Bien();
        esNuevo = true;
        this.setTitle("Nuevo");
        bsCategorias.setData(parent.obtenerCategorias());
        bsTipos.setData(parent.obtenerTipos());
        //writeElement(elementoActual);
    }

    //---------------------------------------------------------------------
    public void prepareForModify(Bien elemento)
    {
        this.elementoActual = elemento;
        esNuevo = false;
        this.setTitle(String.format("Modificando %s", elemento.getNombre()));
        bsCategorias.setData(parent.obtenerCategorias());
        bsTipos.setData(parent.obtenerTipos());
        writeElement(elemento);
    }

    //---------------------------------------------------------------------
    public void writeElement(Bien elemento)
    {
        jtfNombres.setText(elemento.getNombre());
        jcbTipos.setSelectedItem(bsTipos.getElementAt(bsTipos.indexOfValue(elemento.getEsTipo())));
        jcbCategorias.setSelectedItem(bsCategorias.getElementAt(bsCategorias.indexOfValue(elemento.getIdCategoria())));
    }

    //---------------------------------------------------------------------
    private boolean readElement(Bien elemento)
    {
        boolean resultado = false;
        String mensaje = "";

        if (!jtfNombres.getText().isEmpty())
        {
            resultado = true;

            elemento.setNombre(jtfNombres.getText());
            elemento.setEsTipo((Integer) bsTipos.getSelectedValue());
            elemento.setIdCategoria((Integer) bsCategorias.getSelectedValue());
            if (!elemento.isSet())
                elemento.setModified();
        }

        else
            mensaje = "Asegurese de proporcionar el Nombre";

        if (!resultado)
            JOptionPane.showMessageDialog(this, mensaje, "", JOptionPane.PLAIN_MESSAGE);

        return resultado;
    }

    //---------------------------------------------------------------------
    public void close()
    {
        setVisible(false);
        dispose();
    }

    //----------------------------------------------------------------------
    public void setDisplayData(List<Unidad> listaUnidades, List<BienVariante> listaVariantes)
    {
        bsUnidadMasaEditor.setData(listaUnidades);
        bsUnidadMasaRender.setData(listaUnidades);
        bsUnidadLongitudEditor.setData(listaUnidades);
        bsUnidadLongitudRender.setData(listaUnidades);
        if (listaVariantes != null)
            bsVariantes.setData(listaVariantes);
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

        jtpBody = new javax.swing.JTabbedPane();
        tabGeneral = new javax.swing.JPanel();
        jpDatosGenerales = new javax.swing.JPanel();
        jlbNombre = new javax.swing.JLabel();
        jtfNombres = new javax.swing.JTextField();
        jlbTipoPersona = new javax.swing.JLabel();
        jcbTipos = new javax.swing.JComboBox();
        jlbCategorias = new javax.swing.JLabel();
        jcbCategorias = new javax.swing.JComboBox();
        tabPresentaciones = new javax.swing.JPanel();
        jbarDirecciones = new javax.swing.JToolBar();
        jtoolNuevoDireccion = new javax.swing.JButton();
        jtoolEliminarDireccion = new javax.swing.JButton();
        jspDirecciones = new javax.swing.JScrollPane();
        jtbPresentaciones = new javax.swing.JTable();
        jpAcciones = new javax.swing.JPanel();
        jbCancelar = new javax.swing.JButton();
        jbAceptar = new javax.swing.JButton();
        jpEncabezado = new javax.swing.JPanel();
        jlbIcono = new javax.swing.JLabel();
        jpAreaMensajes = new java.awt.Panel();

        setClosable(true);
        setMaximizable(true);
        setResizable(true);

        jtpBody.setName(""); // NOI18N

        tabGeneral.setName("tabGeneral"); // NOI18N
        tabGeneral.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jpDatosGenerales.setPreferredSize(new java.awt.Dimension(480, 180));
        jpDatosGenerales.setRequestFocusEnabled(false);
        jpDatosGenerales.setLayout(new java.awt.GridLayout(5, 2, 5, 8));

        jlbNombre.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbNombre.setText("Nombre:");
        jlbNombre.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jpDatosGenerales.add(jlbNombre);

        jtfNombres.setName("jtfNombres"); // NOI18N
        jpDatosGenerales.add(jtfNombres);

        jlbTipoPersona.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbTipoPersona.setText("Tipo:");
        jpDatosGenerales.add(jlbTipoPersona);

        jcbTipos.setModel(bsTipos);
        jcbTipos.setName("jcbTipos"); // NOI18N
        jcbTipos.setRenderer(new POJOListCellRenderer<Valor>()
        );
        jpDatosGenerales.add(jcbTipos);

        jlbCategorias.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbCategorias.setLabelFor(jcbCategorias);
        jlbCategorias.setText("Categoria:");
        jpDatosGenerales.add(jlbCategorias);

        jcbCategorias.setModel(bsCategorias);
        jcbCategorias.setRenderer(new POJOListCellRenderer<Categoria>());
        jpDatosGenerales.add(jcbCategorias);

        tabGeneral.add(jpDatosGenerales);

        jtpBody.addTab("General", tabGeneral);

        tabPresentaciones.setName("tabPresentaciones"); // NOI18N
        tabPresentaciones.setLayout(new java.awt.BorderLayout());

        jbarDirecciones.setRollover(true);

        jtoolNuevoDireccion.setText("Nuevo");
        jtoolNuevoDireccion.setFocusable(false);
        jtoolNuevoDireccion.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jtoolNuevoDireccion.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbarDirecciones.add(jtoolNuevoDireccion);

        jtoolEliminarDireccion.setText("Eliminar");
        jtoolEliminarDireccion.setToolTipText("");
        jtoolEliminarDireccion.setFocusable(false);
        jtoolEliminarDireccion.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jtoolEliminarDireccion.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbarDirecciones.add(jtoolEliminarDireccion);

        tabPresentaciones.add(jbarDirecciones, java.awt.BorderLayout.PAGE_START);

        jtbPresentaciones.setModel(bsVariantes);
        jtbPresentaciones.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jspDirecciones.setViewportView(jtbPresentaciones);

        tabPresentaciones.add(jspDirecciones, java.awt.BorderLayout.CENTER);

        jtpBody.addTab("Presentaciones", tabPresentaciones);

        getContentPane().add(jtpBody, java.awt.BorderLayout.CENTER);

        jpAcciones.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jbCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/syswave/forms/resources/button-cross.png"))); // NOI18N
        jbCancelar.setText("Cancelar");
        jpAcciones.add(jbCancelar);

        jbAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/syswave/forms/resources/button-tick.png"))); // NOI18N
        jbAceptar.setText("Aceptar");
        jpAcciones.add(jbAceptar);

        getContentPane().add(jpAcciones, java.awt.BorderLayout.PAGE_END);

        jpEncabezado.setBackground(new java.awt.Color(184, 60, 217));
        jpEncabezado.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jpEncabezado.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 20, 5));

        jlbIcono.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/syswave/forms/resources/window.png"))); // NOI18N
        jlbIcono.setAlignmentX(0.5F);
        jlbIcono.setFocusTraversalPolicyProvider(true);
        jlbIcono.setFocusable(false);
        jlbIcono.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jlbIcono.setIconTextGap(0);
        jlbIcono.setPreferredSize(new java.awt.Dimension(40, 50));
        jlbIcono.setRequestFocusEnabled(false);
        jpEncabezado.add(jlbIcono);

        jpAreaMensajes.setBackground(java.awt.SystemColor.desktop);
        jpAreaMensajes.setLayout(new java.awt.GridLayout(2, 1));
        jpEncabezado.add(jpAreaMensajes);

        getContentPane().add(jpEncabezado, java.awt.BorderLayout.PAGE_START);

        setBounds(0, 0, 603, 402);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jbAceptar;
    private javax.swing.JButton jbCancelar;
    private javax.swing.JToolBar jbarDirecciones;
    private javax.swing.JComboBox jcbCategorias;
    private javax.swing.JComboBox jcbTipos;
    private javax.swing.JLabel jlbCategorias;
    private javax.swing.JLabel jlbIcono;
    private javax.swing.JLabel jlbNombre;
    private javax.swing.JLabel jlbTipoPersona;
    private javax.swing.JPanel jpAcciones;
    private java.awt.Panel jpAreaMensajes;
    private javax.swing.JPanel jpDatosGenerales;
    private javax.swing.JPanel jpEncabezado;
    private javax.swing.JScrollPane jspDirecciones;
    private javax.swing.JTable jtbPresentaciones;
    private javax.swing.JTextField jtfNombres;
    private javax.swing.JButton jtoolEliminarDireccion;
    private javax.swing.JButton jtoolNuevoDireccion;
    private javax.swing.JTabbedPane jtpBody;
    private javax.swing.JPanel tabGeneral;
    private javax.swing.JPanel tabPresentaciones;
    // End of variables declaration//GEN-END:variables

    //------------------------------------------------------------------
    private class TipoBienDetailCargarSwingWorker extends SwingWorker<List<Object>, Void>
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
                    case opLOAD_VARIANTES:
                        arguments.add(unidades.obtenerLista());
                        arguments.add(parent.obtenerVariantes((Bien) arguments.get(1)));
                        break;

                    case opLOAD_UNIDADES:
                        arguments.add(unidades.obtenerLista());
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
                        case opLOAD_VARIANTES:
                            setDisplayData((List<Unidad>) results.get(2), (List<BienVariante>) results.get(3));
                            break;

                        case opLOAD_UNIDADES:
                            setDisplayData((List<Unidad>) results.get(1), null);
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
