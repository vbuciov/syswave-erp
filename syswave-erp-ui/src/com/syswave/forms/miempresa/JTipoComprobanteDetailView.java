package com.syswave.forms.miempresa;

import com.syswave.entidades.miempresa.TipoComprobante;
import com.syswave.entidades.miempresa.Valor;
import com.syswave.forms.databinding.ValorComboBoxModel;
import com.syswave.swing.renders.POJOListCellRenderer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class JTipoComprobanteDetailView extends javax.swing.JInternalFrame
{

   private final int opLOAD_VARIANTES = 0;
   private final int opLOAD_UNIDADES = 1;

   ITipoComprobanteMediator parent;
   
   TipoComprobante elementoActual;
   ValorComboBoxModel bsPermitePreciosRender;
   ValorComboBoxModel bsCondicionPagoRender;
   ValorComboBoxModel bsTipoMovimientoRender;
   ValorComboBoxModel bsTipoSaldoRender;
   
   boolean esNuevo;
  
   /**
    * Creates new form JTipoComprobanteDetailView
    * @param owner
    */
   public JTipoComprobanteDetailView(ITipoComprobanteMediator owner)
   {
      initAtributes ();
      initComponents();
      initEvents();
      parent = owner;
      
      /*if (jtbPresentaciones.getColumnCount() > 0)
      {
         colDescripcion = jtbPresentaciones.getColumnModel().getColumn(0);
         colDescripcion.setPreferredWidth(250);
         
         colvalorMasa = jtbPresentaciones.getColumnModel().getColumn(1);
         colvalorMasa.setPreferredWidth(150);
         
           colUnidadMasa = jtbPresentaciones.getColumnModel().getColumn(2);
           colUnidadMasa.setCellRenderer(new LookUpComboBoxTableCellRenderer<Unidad>(bsUnidadMasaRender));
           colUnidadMasa.setCellEditor(new LookUpComboBoxTableCellEditor<Unidad>(bsUnidadMasaEditor));
           colUnidadMasa.setPreferredWidth(150);
           
           colUnidadLongitud = jtbPresentaciones.getColumnModel().getColumn(6);
           colUnidadLongitud.setCellRenderer(new LookUpComboBoxTableCellRenderer<Unidad>(bsUnidadLongitudRender));
           colUnidadLongitud.setCellEditor(new LookUpComboBoxTableCellEditor<Unidad>(bsUnidadLongitudEditor));
           colUnidadLongitud.setPreferredWidth(150);
           
           
           jtbPresentaciones.setRowHeight((int)(jtbPresentaciones.getRowHeight() * 1.5));
      }*/

   }
   
     //---------------------------------------------------------------------
   private void initAtributes ()
   {
        esNuevo = true;
        
        bsPermitePreciosRender = new ValorComboBoxModel();
        bsCondicionPagoRender= new ValorComboBoxModel();
        bsTipoMovimientoRender= new ValorComboBoxModel();
        bsTipoSaldoRender= new ValorComboBoxModel();
        
        bsPermitePreciosRender.addElement(new Valor(0, "Compra"));
         bsPermitePreciosRender.addElement(new Valor(1, "Venta"));
         bsCondicionPagoRender.addElement(new Valor(0, "Contado"));
         bsCondicionPagoRender.addElement(new Valor(1, "Crédito"));
         bsCondicionPagoRender.addElement(new Valor(2, "Consignación"));
         bsCondicionPagoRender.addElement(new Valor(3, "Trueque"));
        Valor nuevo = new Valor(0, "Salida");
        nuevo.setActivo(false);
        bsTipoMovimientoRender.addElement(nuevo);
        nuevo = new Valor(1, "Entrada");
        nuevo.setActivo(true);
         bsTipoMovimientoRender.addElement(nuevo);
        nuevo = new Valor(0, "Cargo");
        nuevo.setActivo(false);
        bsTipoSaldoRender.addElement(nuevo);
        nuevo = new Valor(1, "Abono");
        nuevo.setActivo(true);
         bsTipoSaldoRender.addElement(nuevo);
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
      
      /*ActionListener addressActionListener
       = new ActionListener ()
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
      };*/
      
      jbAceptar.addActionListener(actionListenerManager);
      jbCancelar.addActionListener(actionListenerManager);
      /*jtpBody.addChangeListener(changeListenerManager);
      jtoolNuevoDireccion.addActionListener(addressActionListener);
      jtoolEliminarDireccion.addActionListener(addressActionListener);*/
   }
   
   //---------------------------------------------------------------------
   /*private void bodyTabbed_stateChanged(ChangeEvent e)
   {
      if (swCargador == null || swCargador.isDone())
      {
         
            JTabbedPane pane = (JTabbedPane) e.getSource();
            if ( pane.getSelectedComponent() == tabPresentaciones && !construidoDirecciones)
            {
                   List<Object> parametros = new ArrayList<Object>();
                   swCargador = new TipoTipoComprobanteDetailCargarSwingWorker();
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
   }*/
 
   //---------------------------------------------------------------------
   private void finish_actionPerformed (ActionEvent evt)
   {
      Object sender = evt.getSource();
      
      if (sender == jbAceptar)
      {
         if (readElement(elementoActual))
         {
            //tryStopDetailCellEditor(jtbPresentaciones);

            if (esNuevo)
               parent.onAcceptNewElement(elementoActual);

            else 
            {
               elementoActual.setModified();
               parent.onAcceptModifyElement(elementoActual);
            }
            
            close();
         }
      }
      
      else
         close ();
   }
   
      //---------------------------------------------------------------------
   /*private void tryStopDetailCellEditor (JTable subDetailView)
   {      
      if (subDetailView.isEditing())
      {
         TableCellEditor editor;
         editor = subDetailView.getCellEditor();
         if (editor != null)
           editor.stopCellEditing();
      }
   }*/
   
   //---------------------------------------------------------------------
   /*private void presentationToolBar_actionPerformed (ActionEvent evt)
   {
      int rowIndex;
      
      if (evt.getSource() == jtoolNuevoDireccion)
      {
         if (bsUnidadMasaEditor.getData().size() > 0 && bsUnidadLongitudEditor.getData().size() > 0)
         {
            TipoComprobanteVariante nuevaPresentacion = new TipoComprobanteVariante();
            nuevaPresentacion.setFk_variante_bien(elementoActual);
            nuevaPresentacion.setNivel(0);
            nuevaPresentacion.setMasa(0);
            nuevaPresentacion.setIdUnidadMasa(bsUnidadMasaEditor.getData().get(0).getId());
            nuevaPresentacion.setAncho(0);
            nuevaPresentacion.setAlto(0);
            nuevaPresentacion.setLargo(0);
            nuevaPresentacion.setIdUnidadLongitud(bsUnidadLongitudEditor.getData().get(0).getId());
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
         if ( JOptionPane.showConfirmDialog(this,String.format("Ha seleccionado %d registro(s) para ser eleiminado(s)\n¿Esta seguro de continuar?",  jtbPresentaciones.getSelectedRowCount()) ) == JOptionPane.OK_OPTION)
         {
            int[] rowsHandlers = jtbPresentaciones.getSelectedRows();
            List<TipoComprobanteVariante> selecteds = bsVariantes.removeRows(rowsHandlers);

            for (TipoComprobanteVariante elemento : selecteds )
            {
               if ( !elemento.isNew() )
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
      if ( e.getType() == TableModelEvent.UPDATE && e.getColumn()!= TableModelEvent.ALL_COLUMNS )
      {
          int row = e.getFirstRow();
         
         if (row != TableModelEvent.HEADER_ROW)
         {
            POJOTableModel model = (POJOTableModel)e.getSource();
            //String columnName = model.getColumnName(column);
            Entidad data = (Entidad)model.getElementAt(row);

            if (!data.isSet())
               data.setModified();
         }
      }
   }*/
   
   //---------------------------------------------------------------------
   public void prepareForNew ()
   {
      elementoActual = new TipoComprobante();
      esNuevo = true;
      this.setTitle("Nuevo");
      //writeElement(elementoActual);
   }
   
   //---------------------------------------------------------------------
   public void prepareForModify (TipoComprobante elemento)
   {
      this.elementoActual = elemento;
      esNuevo = false;
      this.setTitle(String.format("Modificando %s", elemento.getNombre()));
      writeElement(elemento);
   }
   
   //---------------------------------------------------------------------
   public void writeElement (TipoComprobante elemento)
   {
      jtfNombres.setText(elemento.getNombre());
      jchkAfectaInventario.setSelected(elemento.esAfecta_inventario());
      jcbEsEntrada.setSelectedIndex(bsTipoMovimientoRender.indexOfValue(elemento.esEntrada()?1:0));
      jchkAfectaSaldos.setSelected(elemento.esAfectaSaldos());
      jcbTipoSaldo.setSelectedIndex(bsTipoSaldoRender.indexOfValue(elemento.esTipoSaldo()?1:0));
      jchkEsComercial.setSelected(elemento.esComercial());
      jcbCondicionPago.setSelectedItem ( bsCondicionPagoRender.getElementAt(bsCondicionPagoRender.indexOfValue(elemento.getCondicionPago())));
      jcbPermitePrecios.setSelectedIndex(bsPermitePreciosRender.indexOfValue(elemento.getPermitePrecios()));
      jchkActivo.setSelected(elemento.esActivo());
   }
   
   //---------------------------------------------------------------------
   private boolean readElement (TipoComprobante elemento)
   {
      boolean resultado= false;
      String mensaje = "";
      
      if (!jtfNombres.getText().isEmpty() )
      {
         resultado = true;
        
         elemento.setNombre(jtfNombres.getText());
         elemento.setAfecta_inventario(jchkAfectaInventario.isSelected());
         elemento.setEntrada(bsTipoMovimientoRender.getCurrent().esActivo());
         elemento.setAfectaSaldos(jchkAfectaSaldos.isSelected());
         elemento.setTipoSaldo(bsTipoSaldoRender.getCurrent().esActivo());
         elemento.setComercial(jchkEsComercial.isSelected());
         elemento.setPermitePrecios((int)bsPermitePreciosRender.getSelectedValue());
         elemento.setCondicion_pago((int)bsCondicionPagoRender.getSelectedValue());
         elemento.setActivo(jchkActivo.isSelected());
        
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
   public void close ()
   {
      setVisible(false);
      dispose();
   }

   //----------------------------------------------------------------------
   /*public void setDisplayData (List<Unidad> listaUnidades, List<TipoComprobanteVariante> listaVariantes)
   {
      bsUnidadMasaEditor.setData(listaUnidades);
      bsUnidadMasaRender.setData(listaUnidades);
      bsUnidadLongitudEditor.setData(listaUnidades);
      bsUnidadLongitudRender.setData(listaUnidades);
      if (listaVariantes != null)
         bsVariantes.setData(listaVariantes);
   }*/

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

        jtpBody = new javax.swing.JTabbedPane();
        tabGeneral = new javax.swing.JPanel();
        jpDatosGenerales = new javax.swing.JPanel();
        jlbNombre = new javax.swing.JLabel();
        jtfNombres = new javax.swing.JTextField();
        jlbEsActivo = new javax.swing.JLabel();
        jchkActivo = new javax.swing.JCheckBox();
        tabConfComercial = new javax.swing.JPanel();
        jpHabilidarComercial = new javax.swing.JPanel();
        jchkEsComercial = new javax.swing.JCheckBox();
        jpContenidoComercial = new javax.swing.JPanel();
        jpAlineacionCampos = new javax.swing.JPanel();
        jlbCondicionPago = new javax.swing.JLabel();
        jcbCondicionPago = new javax.swing.JComboBox();
        jlbPermitePrecios = new javax.swing.JLabel();
        jcbPermitePrecios = new javax.swing.JComboBox();
        tabMovInventario = new javax.swing.JPanel();
        jpHabilidarInventario = new javax.swing.JPanel();
        jchkAfectaInventario = new javax.swing.JCheckBox();
        jpContenidoInventario = new javax.swing.JPanel();
        jpAlineacionCampos1 = new javax.swing.JPanel();
        jlbTipoMovimiento = new javax.swing.JLabel();
        jcbEsEntrada = new javax.swing.JComboBox();
        tabMovCuenta = new javax.swing.JPanel();
        jpHabilidarSaldos = new javax.swing.JPanel();
        jchkAfectaSaldos = new javax.swing.JCheckBox();
        jpContenidoSaldos = new javax.swing.JPanel();
        jpAlineacionCampos2 = new javax.swing.JPanel();
        jlbTipoSaldo = new javax.swing.JLabel();
        jcbTipoSaldo = new javax.swing.JComboBox();
        jpAcciones = new javax.swing.JPanel();
        jbCancelar = new javax.swing.JButton();
        jbAceptar = new javax.swing.JButton();
        jpEncabezado = new javax.swing.JPanel();
        jlbIcono = new javax.swing.JLabel();
        jpAreaMensajes = new java.awt.Panel();

        setClosable(true);
        setMaximizable(true);

        jtpBody.setName(""); // NOI18N

        tabGeneral.setName("tabGeneral"); // NOI18N
        tabGeneral.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jpDatosGenerales.setPreferredSize(new java.awt.Dimension(400, 60));
        jpDatosGenerales.setRequestFocusEnabled(false);
        jpDatosGenerales.setLayout(new java.awt.GridLayout(2, 2, 5, 8));

        jlbNombre.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbNombre.setText("Nombre:");
        jlbNombre.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jpDatosGenerales.add(jlbNombre);

        jtfNombres.setName("jtfNombres"); // NOI18N
        jpDatosGenerales.add(jtfNombres);

        jlbEsActivo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbEsActivo.setText("¿Es Activo?");
        jpDatosGenerales.add(jlbEsActivo);
        jpDatosGenerales.add(jchkActivo);

        tabGeneral.add(jpDatosGenerales);

        jtpBody.addTab("General", tabGeneral);

        tabConfComercial.setLayout(new java.awt.BorderLayout());

        jpHabilidarComercial.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jchkEsComercial.setLabel("Es documento comercial");
        jpHabilidarComercial.add(jchkEsComercial);

        tabConfComercial.add(jpHabilidarComercial, java.awt.BorderLayout.NORTH);

        jpContenidoComercial.setBorder(javax.swing.BorderFactory.createTitledBorder("Configuración"));
        jpContenidoComercial.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        java.awt.GridBagLayout jPanel2Layout = new java.awt.GridBagLayout();
        jPanel2Layout.columnWidths = new int[] {0, 0, 0};
        jPanel2Layout.rowHeights = new int[] {0, 0, 0};
        jpAlineacionCampos.setLayout(jPanel2Layout);

        jlbCondicionPago.setText("Condición Pago:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        jpAlineacionCampos.add(jlbCondicionPago, gridBagConstraints);

        jcbCondicionPago.setModel(bsCondicionPagoRender);
        jcbCondicionPago.setPreferredSize(new java.awt.Dimension(200, 25));
        jcbCondicionPago.setRenderer(new POJOListCellRenderer<Valor>());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jpAlineacionCampos.add(jcbCondicionPago, gridBagConstraints);

        jlbPermitePrecios.setText("Usa precios:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        jpAlineacionCampos.add(jlbPermitePrecios, gridBagConstraints);

        jcbPermitePrecios.setModel(bsPermitePreciosRender);
        jcbPermitePrecios.setPreferredSize(new java.awt.Dimension(200, 25));
        jcbPermitePrecios.setRenderer(new POJOListCellRenderer<Valor>());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        jpAlineacionCampos.add(jcbPermitePrecios, gridBagConstraints);

        jpContenidoComercial.add(jpAlineacionCampos);

        tabConfComercial.add(jpContenidoComercial, java.awt.BorderLayout.CENTER);

        jtpBody.addTab("Documento Comercial", tabConfComercial);

        tabMovInventario.setLayout(new java.awt.BorderLayout());

        jpHabilidarInventario.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jchkAfectaInventario.setLabel("Afecta Inventario");
        jpHabilidarInventario.add(jchkAfectaInventario);

        tabMovInventario.add(jpHabilidarInventario, java.awt.BorderLayout.NORTH);

        jpContenidoInventario.setBorder(javax.swing.BorderFactory.createTitledBorder("Configuración"));
        jpContenidoInventario.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jpAlineacionCampos1.setLayout(new java.awt.GridBagLayout());

        jlbTipoMovimiento.setText("Tipo movimiento:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        jpAlineacionCampos1.add(jlbTipoMovimiento, gridBagConstraints);

        jcbEsEntrada.setModel(bsTipoMovimientoRender);
        jcbEsEntrada.setPreferredSize(new java.awt.Dimension(200, 25));
        jcbEsEntrada.setRenderer(new POJOListCellRenderer<Valor>());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jpAlineacionCampos1.add(jcbEsEntrada, gridBagConstraints);

        jpContenidoInventario.add(jpAlineacionCampos1);

        tabMovInventario.add(jpContenidoInventario, java.awt.BorderLayout.CENTER);

        jtpBody.addTab("Movimiento Inventario", tabMovInventario);

        tabMovCuenta.setLayout(new java.awt.BorderLayout());

        jpHabilidarSaldos.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jchkAfectaSaldos.setText("Afecta Saldo");
        jpHabilidarSaldos.add(jchkAfectaSaldos);

        tabMovCuenta.add(jpHabilidarSaldos, java.awt.BorderLayout.NORTH);

        jpContenidoSaldos.setBorder(javax.swing.BorderFactory.createTitledBorder("Configuración"));
        jpContenidoSaldos.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jpAlineacionCampos2.setLayout(new java.awt.GridBagLayout());

        jlbTipoSaldo.setText("Tipo movimiento");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        jpAlineacionCampos2.add(jlbTipoSaldo, gridBagConstraints);

        jcbTipoSaldo.setModel(bsTipoSaldoRender);
        jcbTipoSaldo.setPreferredSize(new java.awt.Dimension(200, 25));
        jcbTipoSaldo.setRenderer(new POJOListCellRenderer<Valor>());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jpAlineacionCampos2.add(jcbTipoSaldo, gridBagConstraints);

        jpContenidoSaldos.add(jpAlineacionCampos2);

        tabMovCuenta.add(jpContenidoSaldos, java.awt.BorderLayout.CENTER);

        jtpBody.addTab("Movimiento Cuenta", tabMovCuenta);

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

        setBounds(0, 0, 552, 359);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jbAceptar;
    private javax.swing.JButton jbCancelar;
    private javax.swing.JComboBox jcbCondicionPago;
    private javax.swing.JComboBox jcbEsEntrada;
    private javax.swing.JComboBox jcbPermitePrecios;
    private javax.swing.JComboBox jcbTipoSaldo;
    private javax.swing.JCheckBox jchkActivo;
    private javax.swing.JCheckBox jchkAfectaInventario;
    private javax.swing.JCheckBox jchkAfectaSaldos;
    private javax.swing.JCheckBox jchkEsComercial;
    private javax.swing.JLabel jlbCondicionPago;
    private javax.swing.JLabel jlbEsActivo;
    private javax.swing.JLabel jlbIcono;
    private javax.swing.JLabel jlbNombre;
    private javax.swing.JLabel jlbPermitePrecios;
    private javax.swing.JLabel jlbTipoMovimiento;
    private javax.swing.JLabel jlbTipoSaldo;
    private javax.swing.JPanel jpAcciones;
    private javax.swing.JPanel jpAlineacionCampos;
    private javax.swing.JPanel jpAlineacionCampos1;
    private javax.swing.JPanel jpAlineacionCampos2;
    private java.awt.Panel jpAreaMensajes;
    private javax.swing.JPanel jpContenidoComercial;
    private javax.swing.JPanel jpContenidoInventario;
    private javax.swing.JPanel jpContenidoSaldos;
    private javax.swing.JPanel jpDatosGenerales;
    private javax.swing.JPanel jpEncabezado;
    private javax.swing.JPanel jpHabilidarComercial;
    private javax.swing.JPanel jpHabilidarInventario;
    private javax.swing.JPanel jpHabilidarSaldos;
    private javax.swing.JTextField jtfNombres;
    private javax.swing.JTabbedPane jtpBody;
    private javax.swing.JPanel tabConfComercial;
    private javax.swing.JPanel tabGeneral;
    private javax.swing.JPanel tabMovCuenta;
    private javax.swing.JPanel tabMovInventario;
    // End of variables declaration//GEN-END:variables
}
