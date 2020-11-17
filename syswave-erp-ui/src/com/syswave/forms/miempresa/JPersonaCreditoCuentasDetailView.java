package com.syswave.forms.miempresa;

import com.syswave.entidades.common.Entidad;
import com.syswave.entidades.miempresa.Moneda;
import com.syswave.entidades.miempresa.Valor;
import com.syswave.entidades.miempresa_vista.MonedaCambioVista;
import com.syswave.entidades.miempresa_vista.PersonaCreditoCuenta_5FN;
import com.syswave.swing.models.POJOTableModel;
import com.syswave.forms.databinding.MonedasComboBoxModel;
import com.syswave.forms.databinding.ValorComboBoxModel;
import com.syswave.swing.renders.POJOListCellRenderer;
import com.syswave.logicas.miempresa.MonedaCambioBusinessLogic;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.event.ChangeEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class JPersonaCreditoCuentasDetailView extends javax.swing.JInternalFrame implements TableModelListener, PropertyChangeListener
{
   PersonaCreditoCuenta_5FN elementoActual;
   IPersonaCreditoCuentaMediator owner;
   
   boolean esNuevo, esNuevoDetalle, revirtiendoMoneda;
   MonedaCambioBusinessLogic monedasCambio;
   
   ValorComboBoxModel bsTipoComprobanteRender;
   MonedasComboBoxModel bsMonedas;
   
   List<MonedaCambioVista> listaUltimosTiposCambio;
   Moneda selected_old;

   PersonaCreditoCuentaSwingWorker swCargador;   
   /**
    * Creates new form JPersonaCreditoCuentasDetailView
    */
   public JPersonaCreditoCuentasDetailView(IPersonaCreditoCuentaMediator parent)
   {
      owner = parent;
      initAtributes(parent.getEsquema());
      initComponents();
      initEvents();
      
   }
   
   //---------------------------------------------------------------------
   private void initAtributes(String Esquema)
   {
      esNuevo = true;
      revirtiendoMoneda = false;
      esNuevoDetalle = true;
      monedasCambio = new MonedaCambioBusinessLogic(Esquema);

      bsTipoComprobanteRender = new ValorComboBoxModel();
      bsMonedas = new MonedasComboBoxModel();
     
      /*bsDetalles = new DocumentoDetalleNavigableTableModel(new String[]
      {
         "No.:{#}}", "Cantidad:{cantidad}", "Descripcion:{descripcion}",
         "Precio:{precio_final}", "Gravamen:{margen}", "% Gravado:{factor}", "Importe:{importe}"
      });
      bsDetalles.addTableModelListener(this);
                
      detalles_deleteds = new ArrayList<>();  */
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

      ActionListener detailsActionListener
                     = new ActionListener()
      {
         @Override
         public void actionPerformed(ActionEvent evt)
         {
            detailsToolBar_actionPerformed(evt);
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

      ItemListener valueChangeItemListener = new ItemListener()
      {
         @Override
         public void itemStateChanged(ItemEvent e)
         {
            jcbMoneda_itemStateChanged(e);
         }
      };

      jbAceptar.addActionListener(actionListenerManager);
      jbCancelar.addActionListener(actionListenerManager);
      //jtpAreaDetalles.addChangeListener(changeListenerManager);
      jToolNuevoMovimiento.addActionListener(detailsActionListener);
      jToolEliminarMovimiento.addActionListener(detailsActionListener);
      jcbMoneda.addItemListener(valueChangeItemListener);
   }
   
      //--------------------------------------------------------------------
   /**
    * Construye una instancia de tabla con detección de cambios en celdas.
    */
   private JTable createJtbDetalles()
   {
      return new JTable()
      {
         @Override // Always selectAll()
         public void editingStopped(ChangeEvent e)
         {
            jtbDetalles_onCellValueChanged(this, getCellEditor());
            super.editingStopped(e);
         }
      };
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
   
    //---------------------------------------------------------------------
   private void detailsToolBar_actionPerformed(ActionEvent evt)
   {
      //int rowIndex;

      if (evt.getSource() == jToolNuevoMovimiento)
      {
         /*DocumentoDireccion nuevaDireccion = new DocumentoDireccion();
          nuevaDireccion.setFk_persona_direccion(elementoActual);
          nuevaDireccion.setCodigoPostal("");
          nuevaDireccion.setCalle("");
          nuevaDireccion.setColonia("");
          nuevaDireccion.setNoInterior("");
          nuevaDireccion.setNoExterior("");
          rowIndex = bsDocumentoDireccion.addRow(nuevaDireccion);
          jtbDirecciones.setRowSelectionInterval(rowIndex, rowIndex);*/
         //jtbDirecciones.editCellAt(rowIndex, 0);
         //Nota: Mostramos el popup de Tipos de personas, y la ubicamos en las coordenadas del botón especificar.
         //popupCategorias.show(jToolNuevoLinea, 0, (int) jToolNuevoLinea.getPreferredSize().getHeight());

      }

      else if (evt.getSource() == jToolEliminarMovimiento)
      {
         if (JOptionPane.showConfirmDialog(this, String.format("Ha seleccionado %d registro(s) para ser eleiminado(s)\n¿Esta seguro de continuar?", jtbCuentaMovimientos.getSelectedRowCount())) == JOptionPane.OK_OPTION)
         {
            /*int[] rowsHandlers = jtbCuentaMovimientos.getSelectedRows();
            List<DocumentoDetalleNavigable> selecteds = bsDetalles.removeRows(rowsHandlers);

            for (DocumentoDetalleNavigable elemento : selecteds)
            {
               if (!elemento.isNew())
                  detalles_deleteds.add(elemento);

               actualizarTotal(elemento.getCantidad() * elemento.getPrecio_final() * -1, elemento.getCantidad() * elemento.getMargen() * -1);
            }

            invalidateChangeOfType(bsDetalles.getRowCount() > 0);
            reescribirTotales();*/
         }
      }
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
      elementoActual = new PersonaCreditoCuenta_5FN();
      esNuevo = true;
      /*subtotal = 0.0F;
      gravamen = 0.0F;
      total = 0.0F;
      factor = 0;*/
      this.setTitle("Nuevo estado de cuenta");
      bsTipoComprobanteRender.setData(owner.obtenerTiposCuenta());
      bsMonedas.setData(owner.obtenerMonedas());
      cargarRecursos(null);
      //writeElement(elementoActual);
   }

   //---------------------------------------------------------------------
   public void prepareForModify(PersonaCreditoCuenta_5FN elemento)
   {
      this.elementoActual = elemento;
      esNuevo = false;
      this.setTitle(String.format("Modificando %s, %s", elemento.getNombreCompleto(), elemento.getNumero()));
      bsTipoComprobanteRender.setData(owner.obtenerTiposCuenta());
      bsMonedas.setData(owner.obtenerMonedas());
      writeElement(elemento);
      cargarRecursos(elemento);
   }
   
   //---------------------------------------------------------------------
   private void cargarRecursos(PersonaCreditoCuenta_5FN elemento)
   {
      if (swCargador == null || swCargador.isDone())
      {
         /*Categoria filtroCategoria = new Categoria();
         filtroCategoria.setNivel(0); //Solo el nivel 0*/
         /*List<Object> parametros = new ArrayList<Object>();
         swCargador = new PersonaCreditoCuentaSwingWorker();
         swCargador.addPropertyChangeListener(this);

         /*if (elemento != null)
         {
            //parametros.add(opLOAD_RESOURCES_AND_DETAILS);
            parametros.add(elemento);
         }
         else
            parametros.add(opLOAD_RESOURCES);
         //parametros.add(filtroCategoria);*/
         /*showTaskHeader("Cargando recursos, espero un momento....", true);
         swCargador.execute(parametros);*/
      }
   }
   
   //----------------------------------------------------------------------
   public void jtbDetalles_onCellValueChanged(JTable sender, TableCellEditor editor)
   {
      TableColumnModel columnas = sender.getColumnModel();
      int modelColIndex = sender.convertColumnIndexToModel(sender.getEditingColumn());
      int modelRowIndex = sender.convertRowIndexToModel(sender.getEditingRow());
      TableColumn actual = columnas.getColumn(modelColIndex);
      /*DocumentoDetalleNavigableTableModel dataBinding = (DocumentoDetalleNavigableTableModel) sender.getModel();
      DocumentoDetalleNavigable seleccionado = dataBinding.getElementAt(modelRowIndex);*/

     /* if (actual == colCantidad)
      {
         float precio_final_old = seleccionado.getPrecio_final();
         float cantidad_new = (float) editor.getCellEditorValue();
         float importe_new, gravamen_unitario_old = seleccionado.getMargen();

         importe_new = precio_final_old * cantidad_new;
         actualizarTotal(importe_new - seleccionado.getImporte(), cantidad_new * gravamen_unitario_old - seleccionado.getCantidad() * gravamen_unitario_old);
         dataBinding.setValueAt(importe_new, modelRowIndex, colImporte.getModelIndex());
         reescribirTotales();
      }*/

   }
   
     //---------------------------------------------------------------------
   /**
    * 
    */
   private void jcbMoneda_itemStateChanged(ItemEvent evt)
   {
      JComboBox combo = (JComboBox) evt.getSource();
      //Item previo al cambio
      if (evt.getStateChange() == ItemEvent.DESELECTED)
      {
         if (!revirtiendoMoneda)
         {
            //Nota: Si es la primera vez que se selecciona un item, este evento no se ejecuta.
            //porque no había nada previo seleccionado.
            /*Moneda selected_new = ((MonedasComboBoxModel) combo.getModel()).getCurrent();
            Moneda selected_old = (Moneda) evt.getItem();
            //JOptionPane.showMessageDialog(this, String.format("Se esta pasando de %s a %s", selected_old.getNombre(), selected_new.getNombre()));*/
            
           /* esNuevoDetalle = bsDetalles.getData().isEmpty();
           
            if (!esNuevoDetalle)
            {
               selected_old = (Moneda) evt.getItem();
               for ( DocumentoDetalleNavigable detalle : bsDetalles.getData()  )
                   pendientes_cambio.add(detalle);

               subtotal = 0.0F;
               gravamen = 0.0F;
               total = 0.0F;
               factor = 0;

               if (esNuevo || construidoRelacionPrecios)
                  revisarDetallesPendientesPrecio(esNuevoDetalle);

               else if (swCargador == null || swCargador.isDone())
               {
                  List<Object> parametros = new ArrayList<Object>();
                  swCargador = new PersonaCreditoCuentaSwingWorker();
                  swCargador.addPropertyChangeListener(this);
                  parametros.add(opLOAD_PRICE_PARTS);
                  parametros.add(false);
                  parametros.add(pendientes_cambio);
                  parametros.add(esNuevoDetalle); //No Agregar pendientes al terminar
                  showTaskHeader("Cargando recursos, espero un momento....", true);
                  swCargador.execute(parametros);
                  construidoRelacionPrecios = true;
               }
            }
         }
         else
         {
            selected_old = null;
            revirtiendoMoneda = false;
         }*/
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
            tryStopDetailCellEditor(jtbCuentaMovimientos);
            
            if (esNuevo)
               owner.onAcceptNewElement(elementoActual/*,
                                         bsDetalles.getData()*/);

            else
            {
               elementoActual.setModified();
               owner.onAcceptModifyElement(elementoActual/*,
                                            bsDetalles.getData(),
                                            detalles_deleteds*/);
            }

            close();
         }
      }

      else
         close();
   }
   
    //---------------------------------------------------------------------
   public void close()
   {
      setVisible(false);
      dispose();
   }
   
    //---------------------------------------------------------------------
   public void writeElement(PersonaCreditoCuenta_5FN elemento)
   {
      jtfNumeroCta.setText(elemento.getNumero());
      jdcFechaInicial.setDate(elemento.getFechaInicial());
      jtfPersonaNombre.setText(elemento.getNombreCompleto());
      jtfTipoPersona.setText( owner.obtenerTipoPersona(elemento.getIdTipoPersona()));
      jcbEsTipo.setSelectedItem(bsTipoComprobanteRender.getElementAt(bsTipoComprobanteRender.indexOfValue(elemento.getEsTipo())));
      jtfLimite.setText( String.valueOf(elemento.getSaldoLimite()));
      jckActivo.setSelected(elemento.esActivo());
      //jtfObservacion.setText(elemento.getObservacion());
      /*subtotal = elemento.getSubtotal();
      gravamen = elemento.getMonto();
      total = elemento.getTotal();
      factor = elemento.getFactor();
      reescribirTotales();*/
      writeCurrency(elemento.getIdMoneda());
   }

   //---------------------------------------------------------------------
   /*private void reescribirTotales()
   {
      jtfSubtotal.setValue(subtotal);
      jtfFactor.setValue(factor);
      jtfMonto.setValue(gravamen);
      jtfTotal.setValue(total);
   }*/

   //---------------------------------------------------------------------
   private void writeCurrency(int idMoneda)
   {
      if (bsMonedas.getSize() > 0)
         jcbMoneda.setSelectedIndex(bsMonedas.indexOfValue(idMoneda));
   }

   //---------------------------------------------------------------------
   private boolean readElement(PersonaCreditoCuenta_5FN elemento)
   {
      boolean resultado = false;
      String mensaje = "";

      if (!jtfNumeroCta.getText().isEmpty() && jcbEsTipo.getSelectedIndex() >= 0)
      {
         resultado = true;
         if (!elemento.isSet())
            elemento.setModified();
         elemento.setNumero(jtfNumeroCta.getText());
         elemento.setFechaInicial(jdcFechaInicial.getDate());
         elemento.setActivo(jckActivo.isSelected());
         //elemento.setObservacion(title);
         //elemento.setNombres(jtfPersonaNombre.getText());
         //elemento.setIdTipoPersona(jtfCondiciones.getText());
         elemento.setSaldoActual((float) jtfSaldoActual.getValue());
         elemento.setSaldoLimite((float) jtfLimite.getValue());
         elemento.setIdMoneda((int) bsMonedas.getSelectedValue());
      }

      else
         mensaje = "Asegurese de proporcionar el Folio y tipo del documento";

      if (!resultado)
         JOptionPane.showMessageDialog(this, mensaje, "", JOptionPane.PLAIN_MESSAGE);

      return resultado;
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

        jpContenido = new javax.swing.JPanel();
        jpAreaDocumento = new javax.swing.JPanel();
        jpDatosDocumentos = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jtfTipoPersona = new javax.swing.JTextField();
        jlbReceptor = new javax.swing.JLabel();
        jlbFecha = new javax.swing.JLabel();
        jdcFechaInicial = new com.toedter.calendar.JDateChooser();
        jtfPersonaNombre = new javax.swing.JTextField();
        jlbCondiciones = new javax.swing.JLabel();
        jtfNumeroCta = new javax.swing.JTextField();
        jlbActivo = new javax.swing.JLabel();
        jckActivo = new javax.swing.JCheckBox();
        jtpAreaDetalles = new javax.swing.JTabbedPane();
        jTabMovimientos = new javax.swing.JPanel();
        jspDocumentoDetalles = new javax.swing.JScrollPane();
        jtbCuentaMovimientos = createJtbDetalles ();
        jtoolDocumentoDetalles = new javax.swing.JToolBar();
        jToolNuevoMovimiento = new javax.swing.JButton();
        jToolEliminarMovimiento = new javax.swing.JButton();
        jpObservacion = new javax.swing.JPanel();
        jpMontosTotales = new javax.swing.JPanel();
        jpTipoCuenta = new javax.swing.JPanel();
        jlbTipoComprobante = new javax.swing.JLabel();
        jcbEsTipo = new javax.swing.JComboBox();
        jpTotal = new javax.swing.JPanel();
        jlbTotal = new javax.swing.JLabel();
        jtfSaldoActual = new JFormattedTextField(new Float(1.0000F));
        jpLimite = new javax.swing.JPanel();
        jlbLimite = new javax.swing.JLabel();
        jtfLimite = new JFormattedTextField(new Float(1.0000F));
        jpMoneda = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jcbMoneda = new javax.swing.JComboBox();
        jpEncabezado = new javax.swing.JPanel();
        jlbIcono = new javax.swing.JLabel();
        jpAreaMensajes = new javax.swing.JPanel();
        jlbMensajes = new javax.swing.JLabel();
        jpbAvances = new javax.swing.JProgressBar();
        jpAcciones = new javax.swing.JPanel();
        jbCancelar = new javax.swing.JButton();
        jbAceptar = new javax.swing.JButton();

        setClosable(true);
        setMaximizable(true);
        setResizable(true);

        jpContenido.setLayout(new java.awt.BorderLayout());

        jpAreaDocumento.setPreferredSize(new java.awt.Dimension(800, 115));
        jpAreaDocumento.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jpDatosDocumentos.setPreferredSize(new java.awt.Dimension(780, 102));
        jpDatosDocumentos.setLayout(new java.awt.GridBagLayout());

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Número:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        jpDatosDocumentos.add(jLabel1, gridBagConstraints);

        jtfTipoPersona.setEditable(false);
        jtfTipoPersona.setEnabled(false);
        jtfTipoPersona.setPreferredSize(new java.awt.Dimension(350, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 4;
        jpDatosDocumentos.add(jtfTipoPersona, gridBagConstraints);

        jlbReceptor.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbReceptor.setText("Nombre:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        jpDatosDocumentos.add(jlbReceptor, gridBagConstraints);

        jlbFecha.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbFecha.setText("Fecha:");
        jlbFecha.setPreferredSize(new java.awt.Dimension(80, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        jpDatosDocumentos.add(jlbFecha, gridBagConstraints);

        jdcFechaInicial.setPreferredSize(new java.awt.Dimension(200, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        jpDatosDocumentos.add(jdcFechaInicial, gridBagConstraints);

        jtfPersonaNombre.setEditable(false);
        jtfPersonaNombre.setEnabled(false);
        jtfPersonaNombre.setPreferredSize(new java.awt.Dimension(350, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jpDatosDocumentos.add(jtfPersonaNombre, gridBagConstraints);

        jlbCondiciones.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbCondiciones.setText("Relación con negocio:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        jpDatosDocumentos.add(jlbCondiciones, gridBagConstraints);

        jtfNumeroCta.setPreferredSize(new java.awt.Dimension(170, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jpDatosDocumentos.add(jtfNumeroCta, gridBagConstraints);

        jlbActivo.setText("Activo:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        jpDatosDocumentos.add(jlbActivo, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jpDatosDocumentos.add(jckActivo, gridBagConstraints);

        jpAreaDocumento.add(jpDatosDocumentos);

        jpContenido.add(jpAreaDocumento, java.awt.BorderLayout.PAGE_START);

        jtpAreaDetalles.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jtpAreaDetalles.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
        jtpAreaDetalles.setToolTipText("");
        jtpAreaDetalles.setPreferredSize(new java.awt.Dimension(16, 200));

        jTabMovimientos.setLayout(new java.awt.BorderLayout());

        jtbCuentaMovimientos.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jspDocumentoDetalles.setViewportView(jtbCuentaMovimientos);

        jTabMovimientos.add(jspDocumentoDetalles, java.awt.BorderLayout.CENTER);

        jtoolDocumentoDetalles.setRollover(true);

        jToolNuevoMovimiento.setText("Nuevo");
        jToolNuevoMovimiento.setFocusable(false);
        jToolNuevoMovimiento.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jToolNuevoMovimiento.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jtoolDocumentoDetalles.add(jToolNuevoMovimiento);

        jToolEliminarMovimiento.setText("Eliminar");
        jToolEliminarMovimiento.setFocusable(false);
        jToolEliminarMovimiento.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jToolEliminarMovimiento.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jtoolDocumentoDetalles.add(jToolEliminarMovimiento);

        jTabMovimientos.add(jtoolDocumentoDetalles, java.awt.BorderLayout.PAGE_START);

        jtpAreaDetalles.addTab("Movimientos", jTabMovimientos);

        javax.swing.GroupLayout jpObservacionLayout = new javax.swing.GroupLayout(jpObservacion);
        jpObservacion.setLayout(jpObservacionLayout);
        jpObservacionLayout.setHorizontalGroup(
            jpObservacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 810, Short.MAX_VALUE)
        );
        jpObservacionLayout.setVerticalGroup(
            jpObservacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 201, Short.MAX_VALUE)
        );

        jtpAreaDetalles.addTab("Observación", jpObservacion);

        jpContenido.add(jtpAreaDetalles, java.awt.BorderLayout.CENTER);

        jpMontosTotales.setPreferredSize(new java.awt.Dimension(800, 45));
        jpMontosTotales.setVerifyInputWhenFocusTarget(false);
        jpMontosTotales.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 5, 0));

        jpTipoCuenta.setLayout(new java.awt.BorderLayout());

        jlbTipoComprobante.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jlbTipoComprobante.setText("Tipo:");
        jpTipoCuenta.add(jlbTipoComprobante, java.awt.BorderLayout.NORTH);

        jcbEsTipo.setModel(bsTipoComprobanteRender);
        jcbEsTipo.setPreferredSize(new java.awt.Dimension(120, 25));
        jcbEsTipo.setRenderer(new POJOListCellRenderer<Valor>());
        jpTipoCuenta.add(jcbEsTipo, java.awt.BorderLayout.SOUTH);

        jpMontosTotales.add(jpTipoCuenta);

        jpTotal.setPreferredSize(new java.awt.Dimension(150, 40));
        jpTotal.setLayout(new java.awt.BorderLayout());

        jlbTotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jlbTotal.setText("Saldol: $");
        jlbTotal.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jpTotal.add(jlbTotal, java.awt.BorderLayout.CENTER);

        jtfSaldoActual.setEditable(false);
        jtfSaldoActual.setText("000,000,000,000");
        jtfSaldoActual.setPreferredSize(new java.awt.Dimension(120, 25));
        jpTotal.add(jtfSaldoActual, java.awt.BorderLayout.SOUTH);

        jpMontosTotales.add(jpTotal);

        jpLimite.setPreferredSize(new java.awt.Dimension(150, 40));
        jpLimite.setLayout(new java.awt.BorderLayout());

        jlbLimite.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jlbLimite.setText("Limite: $");
        jlbLimite.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jpLimite.add(jlbLimite, java.awt.BorderLayout.CENTER);

        jtfLimite.setEditable(false);
        jtfLimite.setText("000,000,000,000");
        jtfLimite.setPreferredSize(new java.awt.Dimension(120, 25));
        jpLimite.add(jtfLimite, java.awt.BorderLayout.SOUTH);

        jpMontosTotales.add(jpLimite);

        jpMoneda.setPreferredSize(new java.awt.Dimension(100, 40));
        jpMoneda.setLayout(new java.awt.BorderLayout());

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText("Moneda:");
        jLabel2.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel2.setPreferredSize(new java.awt.Dimension(24, 15));
        jLabel2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jpMoneda.add(jLabel2, java.awt.BorderLayout.CENTER);

        jcbMoneda.setModel(bsMonedas);
        jcbMoneda.setRenderer(new POJOListCellRenderer<Moneda>());
        jpMoneda.add(jcbMoneda, java.awt.BorderLayout.SOUTH);

        jpMontosTotales.add(jpMoneda);

        jpContenido.add(jpMontosTotales, java.awt.BorderLayout.SOUTH);

        getContentPane().add(jpContenido, java.awt.BorderLayout.CENTER);

        jpEncabezado.setBackground(new java.awt.Color(247, 210, 65));
        jpEncabezado.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP));
        java.awt.FlowLayout flowLayout1 = new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 20, 0);
        flowLayout1.setAlignOnBaseline(true);
        jpEncabezado.setLayout(flowLayout1);

        jlbIcono.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/syswave/forms/resources/window.png"))); // NOI18N
        jlbIcono.setAlignmentX(0.5F);
        jlbIcono.setFocusTraversalPolicyProvider(true);
        jlbIcono.setFocusable(false);
        jlbIcono.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jlbIcono.setIconTextGap(0);
        jlbIcono.setPreferredSize(new java.awt.Dimension(40, 50));
        jlbIcono.setRequestFocusEnabled(false);
        jpEncabezado.add(jlbIcono);

        jpAreaMensajes.setBackground(new java.awt.Color(247, 210, 65));
        jpAreaMensajes.setPreferredSize(new java.awt.Dimension(600, 30));
        jpAreaMensajes.setLayout(new java.awt.GridLayout(2, 1));

        jlbMensajes.setBackground(new java.awt.Color(247, 210, 65));
        jlbMensajes.setText("<sin mensaje>");
        jlbMensajes.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jpAreaMensajes.add(jlbMensajes);

        jpbAvances.setValue(50);
        jpbAvances.setStringPainted(true);
        jpAreaMensajes.add(jpbAvances);

        jpEncabezado.add(jpAreaMensajes);

        getContentPane().add(jpEncabezado, java.awt.BorderLayout.PAGE_START);

        jpAcciones.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jpAcciones.setPreferredSize(new java.awt.Dimension(215, 50));
        jpAcciones.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 5, 3));

        jbCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/syswave/forms/resources/button-cross.png"))); // NOI18N
        jbCancelar.setText("Cancelar");
        jpAcciones.add(jbCancelar);

        jbAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/syswave/forms/resources/button-tick.png"))); // NOI18N
        jbAceptar.setText("Aceptar");
        jpAcciones.add(jbAceptar);

        getContentPane().add(jpAcciones, java.awt.BorderLayout.PAGE_END);

        setBounds(0, 0, 833, 535);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jTabMovimientos;
    private javax.swing.JButton jToolEliminarMovimiento;
    private javax.swing.JButton jToolNuevoMovimiento;
    private javax.swing.JButton jbAceptar;
    private javax.swing.JButton jbCancelar;
    private javax.swing.JComboBox jcbEsTipo;
    private javax.swing.JComboBox jcbMoneda;
    private javax.swing.JCheckBox jckActivo;
    private com.toedter.calendar.JDateChooser jdcFechaInicial;
    private javax.swing.JLabel jlbActivo;
    private javax.swing.JLabel jlbCondiciones;
    private javax.swing.JLabel jlbFecha;
    private javax.swing.JLabel jlbIcono;
    private javax.swing.JLabel jlbLimite;
    private javax.swing.JLabel jlbMensajes;
    private javax.swing.JLabel jlbReceptor;
    private javax.swing.JLabel jlbTipoComprobante;
    private javax.swing.JLabel jlbTotal;
    private javax.swing.JPanel jpAcciones;
    private javax.swing.JPanel jpAreaDocumento;
    private javax.swing.JPanel jpAreaMensajes;
    private javax.swing.JPanel jpContenido;
    private javax.swing.JPanel jpDatosDocumentos;
    private javax.swing.JPanel jpEncabezado;
    private javax.swing.JPanel jpLimite;
    private javax.swing.JPanel jpMoneda;
    private javax.swing.JPanel jpMontosTotales;
    private javax.swing.JPanel jpObservacion;
    private javax.swing.JPanel jpTipoCuenta;
    private javax.swing.JPanel jpTotal;
    private javax.swing.JProgressBar jpbAvances;
    private javax.swing.JScrollPane jspDocumentoDetalles;
    private javax.swing.JTable jtbCuentaMovimientos;
    private javax.swing.JFormattedTextField jtfLimite;
    private javax.swing.JTextField jtfNumeroCta;
    private javax.swing.JTextField jtfPersonaNombre;
    private javax.swing.JFormattedTextField jtfSaldoActual;
    private javax.swing.JTextField jtfTipoPersona;
    private javax.swing.JToolBar jtoolDocumentoDetalles;
    private javax.swing.JTabbedPane jtpAreaDetalles;
    // End of variables declaration//GEN-END:variables
//------------------------------------------------------------------
   private class PersonaCreditoCuentaSwingWorker extends SwingWorker<List<Object>, Void>
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

            //setProgress(0);
            switch (opcion)
            {
               /*case opLOAD_ADDRESS:
                  setProgress(30);
                  arguments.add(parent.obtenerPersonaDireccionDocumentos((DocumentoComercial) arguments.get(1)));
                  setProgress(60);
                  arguments.add(tiposPersonas.obtenerLista((TipoPersona) arguments.get(2)));
                  break;

               case opLOAD_RESOURCES:
                  setProgress(60);
                  arguments.add(categorias.obtenerLista((Categoria) arguments.get(1)));
                  setProgress(80);
                  arguments.add(monedasCambio.obtenerUltimosTiposCambio(true));
                  break;

               case opLOAD_RESOURCES_AND_DETAILS:
                  setProgress(30);
                  arguments.add(parent.obtenerDocumentoDetalles((DocumentoComercial) arguments.get(1)));
                  setProgress(60);
                  arguments.add(categorias.obtenerLista((Categoria) arguments.get(2)));
                  setProgress(80);
                  arguments.add(monedasCambio.obtenerUltimosTiposCambio(true));
                  break;

               case opLOAD_CONDITIONS:
                  setProgress(60);
                  arguments.add(parent.obtenerCondicionesDocumento((DocumentoComercial) arguments.get(1)));
                  break;

               case opSAVE_PRICE:
                 setProgress(60);
                arguments.add(monedasCambio.guardar((List<MonedaCambioVista>)arguments.get(1)));
                break;
                  
               case opSAVE_PRICE_AND_PRICE_PARTS:
                setProgress(30);
                arguments.add(monedasCambio.guardar((List<MonedaCambioVista>)arguments.get(1)));
               case opLOAD_PRICE_PARTS:
                  List<DocumentoDetalleNavigable> pendientes = (List<DocumentoDetalleNavigable> )arguments.get(2);
                  DocumentoDetalleNavigable actual;
                  setProgress(50);
                  List<DocumentoDetalle_tiene_PrecioVista> todoEnDocumento = parent.obtenerDetalleTienePrecios(elementoActual);
        
                  for (int i= 0; i < pendientes.size(); i++)
                  {
                     setProgress(i * 50 /pendientes.size());
                     actual = pendientes.get(i);
                     if (!actual.isNew())
                        llenarDetallePartes (actual, todoEnDocumento);
                     //if (!actual.isNew() && !actual.tienePartes())
                     //   actual.getPartes().addAll(parent.obtenerDetalleTienePrecios(actual));
                     
                  }
                  if (todoEnDocumento.size() > 0)
                     todoEnDocumento.clear();
                  break;
                  
               case opLOAD_PAIDS:
                  setProgress(60);
                  arguments.add(parent.obtenerFormasPago((DocumentoComercial) arguments.get(1)));
                  break;*/
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
                  /*case opLOAD_ADDRESS:
                     if (tiposPersonas.esCorrecto())
                     {
                        setDisplayData((List<PersonaDireccion_tiene_Documento_5FN>) results.get(3),
                                       (List<TipoPersona>) results.get(4));
                        showTaskHeader("", false);
                     }
                     else
                        JOptionPane.showMessageDialog(null, tiposPersonas.getMensaje(), "Error", JOptionPane.ERROR_MESSAGE);
                     break;

                  case opLOAD_RESOURCES:
                     if (categorias.esCorrecto())
                     {
                        List<MonedaCambioVista> listaCambios = (List<MonedaCambioVista>) results.get(3);
                        setDisplayDetails((List<Categoria>) results.get(2), null, listaCambios);
                        showTaskHeader("", false);
                     }
                     else
                        JOptionPane.showMessageDialog(null, categorias.getMensaje(), "Error", JOptionPane.ERROR_MESSAGE);
                     break;

                  case opLOAD_RESOURCES_AND_DETAILS:
                     if (categorias.esCorrecto())
                     {
                        List<MonedaCambioVista> listaCambios = (List<MonedaCambioVista>) results.get(5);
                        setDisplayDetails((List<Categoria>) results.get(4), (List<DocumentoDetalleNavigable>) results.get(3), listaCambios);
                        showTaskHeader("", false);
                     }
                     else
                        JOptionPane.showMessageDialog(null, categorias.getMensaje(), "Error", JOptionPane.ERROR_MESSAGE);
                     break;

                  case opLOAD_CONDITIONS:
                     setDisplayDataConditions((List<Documento_tiene_Condicion_5FN>) results.get(2));
                     showTaskHeader("", false);
                     break;

                  case opSAVE_PRICE:
                  case opSAVE_PRICE_AND_PRICE_PARTS:
                     if (monedasCambio.esCorrecto())
                     {
                        revisarDetallesPendientesPrecio((boolean)results.get(3));
                        showTaskHeader("", false);
                     }
                      else
                        JOptionPane.showMessageDialog(null, monedasCambio.getMensaje(), "Error", JOptionPane.ERROR_MESSAGE);
                    
                  break;
                     
                  case opLOAD_PRICE_PARTS:
                     revisarDetallesPendientesPrecio((boolean)results.get(3));
                     showTaskHeader("", false);
                     break;
                     
                  case opLOAD_PAIDS:
                     setDisplayDataContadoAbonos((List<DocumentoContadoMovimiento_5FN>) results.get(2));
                     showTaskHeader("", false);
                  break;  */
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

     /* private void llenarDetallePartes(CreditoCuentaMovimiento actual, List<DocumentoDetalle_tiene_PrecioVista> todos)
      {
         boolean termino = false;
         int i=0, consecutivo = -1;
         DocumentoDetalle_tiene_PrecioVista elementoPrecio ;
        
         while (!termino && i < todos.size())
         {
            elementoPrecio = todos.get(i);
            
            //Todos los elementos deben venir odernados por el consecutivo al que pertenecen.
            if (elementoPrecio.getConsecutivo() == actual.getConsecutivo())
            {
               consecutivo = actual.getConsecutivo();
               actual.agregarParte(todos.remove(i));
            }
            
            else if (consecutivo > 0)
               termino = true;
            
            else
               i++;
         }
      }*/
   }
}
