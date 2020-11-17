package com.syswave.forms.miempresa;

import com.syswave.entidades.miempresa.AreaPrecio;
import com.syswave.entidades.miempresa.Categoria;
import com.syswave.entidades.miempresa.Valor;
import com.syswave.entidades.miempresa.VarianteIdentificador;
import com.syswave.entidades.miempresa_vista.ControlPrecioVista;
import com.syswave.forms.databinding.ControlPrecioVistaTableModel;
import com.syswave.forms.databinding.ValorComboBoxModel;
import com.syswave.swing.table.editors.LookUpComboBoxTableCellEditor;
import com.syswave.swing.table.renders.LookUpComboBoxTableCellRenderer;
import com.syswave.logicas.miempresa.ControlPreciosBusinessLogic;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.table.TableColumn;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class JControlPreciosBusqueda extends javax.swing.JInternalFrame implements PropertyChangeListener //TableModelListener
{

    private final int opLOAD_PRICE_CATEGORY = 0;
    private final int opSEARCH_PRICE_CATEGORY = 1;
    private final int opLOAD_PRICE_AREA = 2;
    private final int opSEARCH_PRICE_AREA = 3;

    private ControlPreciosBusinessLogic precios;
    private ControlPrecioVistaTableModel bsPrecios;
    private ValorComboBoxModel bsTiposRender;
    private ValorComboBoxModel bsTiposEditor;
    private Categoria limitadoxCat;
    private AreaPrecio limitadoXArea;
    private int es_venta;
    private final IPrecioBusquedaMediator owner;

    private TableColumn colPresentacion, colTipo, colCategoria,
            colDescripcion, colPrecio, colMoneda, colIdentificador,
            colExistencia;

    private ControlPreciosCargarSwingWorker swCargador;

    //---------------------------------------------------------------------
    /**
     * Creates new form JControlPreciosBusqueda
     * @param parent
     */
    public JControlPreciosBusqueda(IPrecioBusquedaMediator parent)
    {
        owner = parent;
        initAtributes(parent.getEsquema());
        initComponents();
        initEvents();

        if (jtbPrecios.getColumnCount() > 0)
        {
            colCategoria = jtbPrecios.getColumnModel().getColumn(0);
            /*colCategoria.setCellRenderer(new LookUpComboBoxTableCellRenderer<Categoria>(bsCategoriasRender));
             colCategoria.setCellEditor(new LookUpComboBoxTableCellEditor<Categoria>(bsCategoriasEditor));*/

            colPresentacion = jtbPrecios.getColumnModel().getColumn(1);
            colPresentacion.setPreferredWidth(300);

            colDescripcion = jtbPrecios.getColumnModel().getColumn(2);
            colDescripcion.setPreferredWidth(140);

            colPrecio = jtbPrecios.getColumnModel().getColumn(3);
            colPrecio.setPreferredWidth(80);

            colMoneda = jtbPrecios.getColumnModel().getColumn(4);
            colMoneda.setPreferredWidth(120);

            colIdentificador = jtbPrecios.getColumnModel().getColumn(5);
            colIdentificador.setPreferredWidth(120);

            colExistencia = jtbPrecios.getColumnModel().getColumn(6);
            colExistencia.setPreferredWidth(120);

            colTipo = jtbPrecios.getColumnModel().getColumn(7);
            bsTiposRender.addElement(new Valor(0, "Bien"));
            bsTiposRender.addElement(new Valor(1, "Servicio"));
            bsTiposEditor.setData(bsTiposRender.getData());
            colTipo.setCellRenderer(new LookUpComboBoxTableCellRenderer<Categoria>(bsTiposRender));
            colTipo.setCellEditor(new LookUpComboBoxTableCellEditor<Valor>(bsTiposEditor));

            //Nota: Debido a los renders que se estan utilizando es necesario tener un renglón más alto.
            jtbPrecios.setRowHeight((int) (jtbPrecios.getRowHeight() * 1.5));
        }
    }

    //--------------------------------------------------------------------
    private void initAtributes(String esquema)
    {
        precios = new ControlPreciosBusinessLogic(esquema);

        bsPrecios = new ControlPrecioVistaTableModel(new String[]
        {
            "Categoria:{categoria}", "Presentación:{presentacion}", 
            "Descripcion:{descripcion}", "Precio:{mercado}", "Moneda:{moneda}",
            "Identificadores:{identificador}", "Existencia:{existencia}",
            "Tipo:{es_servicio}"
        });

        //bsPrecios.addTableModelListener(this);
        bsTiposEditor = new ValorComboBoxModel();
        bsTiposRender = new ValorComboBoxModel();
    }

    //---------------------------------------------------------------------
    private void initEvents()
    {
        //ListSelectionModel jtPersonaSelectionModel = jtPersonas.getSelectionModel();

        ActionListener actionListenerManager
                = new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent evt)
                    {
                        finish_actionPerformed(evt);
                    }
                };

        ActionListener searchListener = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                search_actionPerformed(e);
            }
        };

        /*ChangeListener changeListenerManager = new ChangeListener()
         {

         @Override
         public void stateChanged(ChangeEvent e)
         {
         bodyTabbed_stateChanged(e);
         }
         };*/
        /*ListSelectionListener selecctionValueChangedListener = new ListSelectionListener()
         {
         @Override
         public void valueChanged(ListSelectionEvent e)
         {
         jtPersonas_selecctionValueChanged(e);
         }
         };*/
        /*ItemListener valueChangeItemListener = new ItemListener()
         {
         @Override
         public void itemStateChanged(ItemEvent e)
         {
         reiniciarPersonas();
         //if (e.getStateChange() == ItemEvent.SELECTED) {
         // Object item = e.getItem();
         // do something with object
         // } 
         }
         };*/
        jbAceptar.addActionListener(actionListenerManager);
        jbCancelar.addActionListener(actionListenerManager);
        jbBuscar.addActionListener(searchListener);
        //jtpBody.addChangeListener(changeListenerManager);
    }

    //--------------------------------------------------------------------
    /**
     * Especifica un tipo de persona base para restrigir el uso a dicha rama.
     *
     * @param limite
     * @param tipo
     */
    public void limitarPrecios(Categoria limite, int tipo)
    {
        limitadoxCat = limite;
        es_venta = tipo;
    }

    //--------------------------------------------------------------------
    /**
     * Especifica un tipo de persona base para restrigir el uso a dicha rama.
     *
     * @param limite
     * @param tipo
     */
    public void limitarPrecios(AreaPrecio limite, int tipo)
    {
        limitadoXArea = limite;
        es_venta = tipo;
    }

    //---------------------------------------------------------------------
    private void showTaskHeader(String mensaje, boolean progress)
    {
        jlbMensajes.setText(mensaje);
        jpbAvances.setVisible(progress);
        jpbAvances.setValue(jpbAvances.getMinimum());
        if (progress)
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        else
            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }

    //--------------------------------------------------------------------
    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        //int progress = cargarOrigenesDatos.getProgress();
        if ("progress".equals(evt.getPropertyName()))
            jpbAvances.setValue((Integer) evt.getNewValue());
    }

    //---------------------------------------------------------------------
   /*@Override
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
     }*/
    //----------------------------------------------------------------------
    private ControlPrecioVista obtenerGenerales()
    {
        ControlPrecioVista resultado = null;

        if (jcbCriterio.getSelectedIndex() == 1)
        {
            resultado = new ControlPrecioVista();

            resultado.setPresentacion(jtfValor.getText());
            resultado.setActivo(true);
            resultado.setDescripcion("");
            resultado.setMoneda("");
        }

        return resultado;
    }

    //----------------------------------------------------------------------
    private VarianteIdentificador obtenerIdentificador()
    {
        VarianteIdentificador resultado = null;

        if (jcbCriterio.getSelectedIndex() == 0)
        {
            resultado = new VarianteIdentificador();

            resultado.setValor(jtfValor.getText());
        }

        return resultado;
    }

    //----------------------------------------------------------------------
    public void busquedaInicial()
    {
        if (swCargador == null || swCargador.isDone())
        {
            List<Object> parametros = new ArrayList<Object>();
            swCargador = new ControlPreciosCargarSwingWorker();
            swCargador.addPropertyChangeListener(this);
            if (limitadoxCat != null)
            {
                parametros.add(opLOAD_PRICE_CATEGORY);
                parametros.add(limitadoxCat);
            }

            else
            {
                parametros.add(opLOAD_PRICE_AREA);
                parametros.add(limitadoXArea);
            }
            parametros.add(es_venta);
            showTaskHeader("Buscando, espere un momento...", true);
            swCargador.execute(parametros);
        }
    }

    //----------------------------------------------------------------------
    private void search_actionPerformed(ActionEvent evt)
    {
        if (swCargador == null || swCargador.isDone())
        {
            List<Object> parametros = new ArrayList<Object>();
            swCargador = new ControlPreciosCargarSwingWorker();
            swCargador.addPropertyChangeListener(this);
            if (limitadoxCat != null)
            {
                parametros.add(opSEARCH_PRICE_CATEGORY);
                parametros.add(limitadoxCat);
            }
            else
            {
                parametros.add(opSEARCH_PRICE_AREA);
                parametros.add(limitadoXArea);
            }
            parametros.add(es_venta);
            parametros.add(obtenerGenerales());
            parametros.add(obtenerIdentificador());
            showTaskHeader("Buscando, espere un momento...", true);

            swCargador.execute(parametros);
        }
    }

    //---------------------------------------------------------------------
    private void finish_actionPerformed(ActionEvent evt)
    {
        Object sender = evt.getSource();

        if (sender == jbAceptar)
        {
            if (jtbPrecios.getSelectedRowCount() >= 0)
            {
                if (jtbPrecios.getSelectedRowCount() < 2 )
                {
                    owner.onAcceptNewElement(bsPrecios.getElementAt(jtbPrecios.getSelectedRow()));
                    close();
                }
                else
                {
                    List<ControlPrecioVista> seleccionados = bsPrecios.getElementsAt(jtbPrecios.getSelectedRows());
                    if (owner.sonValidosVariosPrecios() || esHomogeneaMoneda(seleccionados))
                    {
                        owner.onAcceptNewElement(seleccionados);
                        close();
                    }
                    else
                        JOptionPane.showInternalMessageDialog(this, "Uno o más de los elementos no son de la misma moneda\nLa operación será válida cuando especifique la moneda principal a la\nque va a convertir los precios seleccionados", "Error", JOptionPane.ERROR_MESSAGE);
                }

            }
            else
                JOptionPane.showInternalMessageDialog(this, "No se ha seleccionado un precio válido", "Error", JOptionPane.ERROR_MESSAGE);
        }

        else
            close();
    }

    //---------------------------------------------------------------------
    private boolean esHomogeneaMoneda(List<ControlPrecioVista> seleccionados)
    {
        boolean esHomogenea = true;
        int id_moneda = seleccionados.get(0).getIdMoneda(), i = 1;

        while (esHomogenea && i < seleccionados.size())
        {
            esHomogenea = seleccionados.get(i).getIdMoneda() == id_moneda;
            i++;
        }

        return esHomogenea;
    }

    //---------------------------------------------------------------------
    public void close()
    {
        setVisible(false);
        dispose();
    }

    //----------------------------------------------------------------------
    public void setDisplayData(List<ControlPrecioVista> listaPrecios)
    {
        if (listaPrecios != null)
            bsPrecios.setData(listaPrecios);
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
        jpCriteriosBusqueda = new javax.swing.JPanel();
        jlbMensaje = new javax.swing.JLabel();
        jcbCriterio = new javax.swing.JComboBox();
        jtfValor = new javax.swing.JTextField();
        jbBuscar = new javax.swing.JButton();
        jspPrecios = new javax.swing.JScrollPane();
        jtbPrecios = new javax.swing.JTable();
        jpAcciones = new javax.swing.JPanel();
        jbCancelar = new javax.swing.JButton();
        jbAceptar = new javax.swing.JButton();
        jpEncabezado = new javax.swing.JPanel();
        jlbIcono = new javax.swing.JLabel();
        jpAreaMensajes = new javax.swing.JPanel();
        jlbMensajes = new javax.swing.JLabel();
        jpbAvances = new javax.swing.JProgressBar();

        setClosable(true);
        setMaximizable(true);
        setPreferredSize(new java.awt.Dimension(800, 500));

        jtpBody.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
        jtpBody.setName(""); // NOI18N

        tabGeneral.setName("tabGeneral"); // NOI18N
        tabGeneral.setLayout(new java.awt.BorderLayout());

        jpCriteriosBusqueda.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jlbMensaje.setText("Criterio:");
        jpCriteriosBusqueda.add(jlbMensaje);

        jcbCriterio.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Identificador", "Nombre", " " }));
        jcbCriterio.setPreferredSize(new java.awt.Dimension(200, 25));
        jpCriteriosBusqueda.add(jcbCriterio);

        jtfValor.setPreferredSize(new java.awt.Dimension(400, 25));
        jpCriteriosBusqueda.add(jtfValor);

        jbBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/syswave/forms/resources/buscar.png"))); // NOI18N
        jpCriteriosBusqueda.add(jbBuscar);

        tabGeneral.add(jpCriteriosBusqueda, java.awt.BorderLayout.NORTH);

        jtbPrecios.setModel(bsPrecios);
        jtbPrecios.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jspPrecios.setViewportView(jtbPrecios);

        tabGeneral.add(jspPrecios, java.awt.BorderLayout.CENTER);

        jtpBody.addTab("Resultados", tabGeneral);

        getContentPane().add(jtpBody, java.awt.BorderLayout.CENTER);

        jpAcciones.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jbCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/syswave/forms/resources/button-cross.png"))); // NOI18N
        jbCancelar.setMnemonic('c');
        jbCancelar.setText("Cancelar");
        jpAcciones.add(jbCancelar);

        jbAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/syswave/forms/resources/button-tick.png"))); // NOI18N
        jbAceptar.setMnemonic('e');
        jbAceptar.setText("Aceptar");
        jpAcciones.add(jbAceptar);

        getContentPane().add(jpAcciones, java.awt.BorderLayout.PAGE_END);

        jpEncabezado.setBackground(new java.awt.Color(99, 223, 223));
        jpEncabezado.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.LEADING, javax.swing.border.TitledBorder.ABOVE_TOP));
        jpEncabezado.setPreferredSize(new java.awt.Dimension(704, 55));
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

        jpAreaMensajes.setBackground(new java.awt.Color(99, 223, 223));
        jpAreaMensajes.setPreferredSize(new java.awt.Dimension(600, 30));
        jpAreaMensajes.setLayout(new java.awt.GridLayout(2, 1));

        jlbMensajes.setText("<sin mensaje>");
        jlbMensajes.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jpAreaMensajes.add(jlbMensajes);

        jpbAvances.setValue(50);
        jpbAvances.setStringPainted(true);
        jpAreaMensajes.add(jpbAvances);

        jpEncabezado.add(jpAreaMensajes);

        getContentPane().add(jpEncabezado, java.awt.BorderLayout.PAGE_START);

        setBounds(0, 0, 800, 500);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jbAceptar;
    private javax.swing.JButton jbBuscar;
    private javax.swing.JButton jbCancelar;
    private javax.swing.JComboBox jcbCriterio;
    private javax.swing.JLabel jlbIcono;
    private javax.swing.JLabel jlbMensaje;
    private javax.swing.JLabel jlbMensajes;
    private javax.swing.JPanel jpAcciones;
    private javax.swing.JPanel jpAreaMensajes;
    private javax.swing.JPanel jpCriteriosBusqueda;
    private javax.swing.JPanel jpEncabezado;
    private javax.swing.JProgressBar jpbAvances;
    private javax.swing.JScrollPane jspPrecios;
    private javax.swing.JTable jtbPrecios;
    private javax.swing.JTextField jtfValor;
    private javax.swing.JTabbedPane jtpBody;
    private javax.swing.JPanel tabGeneral;
    // End of variables declaration//GEN-END:variables

    //------------------------------------------------------------------
    private class ControlPreciosCargarSwingWorker extends SwingWorker<List<Object>, Void>
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

                setProgress(0);
                switch (opcion)
                {
                    case opLOAD_PRICE_CATEGORY:
                        arguments.add(precios.buscarPrecio((Categoria) arguments.get(1), (int) arguments.get(2), null, null));
                        break;

                    case opSEARCH_PRICE_CATEGORY:
                        arguments.add(precios.buscarPrecio((Categoria) arguments.get(1), (int) arguments.get(2), (ControlPrecioVista) arguments.get(3), (VarianteIdentificador) arguments.get(4)));
                        break;

                    case opLOAD_PRICE_AREA:
                        arguments.add(precios.buscarPrecio((AreaPrecio) arguments.get(1), (int) arguments.get(2), null, null));
                        break;

                    case opSEARCH_PRICE_AREA:
                        arguments.add(precios.buscarPrecio((AreaPrecio) arguments.get(1), (int) arguments.get(2), (ControlPrecioVista) arguments.get(3), (VarianteIdentificador) arguments.get(4)));
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
                    int opcion = (int) results.get(0); //Debe haber un entero en la primera posición

                    switch (opcion)
                    {
                        case opLOAD_PRICE_AREA:
                        case opLOAD_PRICE_CATEGORY:
                            if (precios.esCorrecto())
                            {
                                setDisplayData((List<ControlPrecioVista>) results.get(3));
                                showTaskHeader("Resultados obtenidos", false);
                            }
                            else
                            {
                                showTaskHeader("Ocurrió un error", false);
                                JOptionPane.showMessageDialog(null, precios.getMensaje(), "Error", JOptionPane.OK_OPTION);
                            }
                            break;

                        case opSEARCH_PRICE_AREA:
                        case opSEARCH_PRICE_CATEGORY:
                            if (precios.esCorrecto())
                            {
                                setDisplayData((List<ControlPrecioVista>) results.get(5));
                                showTaskHeader("Resultados obtenidos", false);
                            }
                            else
                            {
                                showTaskHeader("Ocurrió un error", false);
                                JOptionPane.showMessageDialog(null, precios.getMensaje(), "Error", JOptionPane.OK_OPTION);
                            }
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
