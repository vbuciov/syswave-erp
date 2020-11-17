package com.syswave.forms.miempresa;

import com.orbital.AutoCompleteDocument;
import com.syswave.entidades.common.Entidad;
import com.syswave.entidades.miempresa.BienVariante;
import com.syswave.entidades.miempresa.ControlAlmacen;
import com.syswave.entidades.miempresa.ControlInventario;
import com.syswave.entidades.miempresa.Ubicacion;
import com.syswave.forms.databinding.BienVariantesComboBoxModel;
import com.syswave.forms.databinding.ControlAlmacenTableModel;
import com.syswave.swing.models.POJOTableModel;
import com.syswave.forms.databinding.UbicacionesComboBoxModel;
import com.syswave.swing.table.editors.LookUpComboBoxTableCellEditor;
import com.syswave.swing.table.renders.LookUpComboBoxTableCellRenderer;
import com.syswave.swing.renders.POJOListCellRenderer;
import com.syswave.logicas.miempresa.UbicacionesBusinessLogic;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
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
public class JControlInventariosDetailView extends javax.swing.JInternalFrame implements TableModelListener
{
   private final int opLOAD_ALMACENES = 0;
   private final int opLOAD_UBICACIONES = 1;
   
   private IControlInventarioMediator parent;
   ControlInventario elementoActual;
   
   
   UbicacionesBusinessLogic ubicaciones;
   BienVariantesComboBoxModel bsPartes;
   ControlAlmacenTableModel bsAlmacenes;
   UbicacionesComboBoxModel bsUbicacionesRender;
   UbicacionesComboBoxModel bsUbicacionesEditor;
   
   boolean esNuevo, construidoAlmacenes;
  
   ControlInventarioDetailCargarSwingWorker swCargador;
   List<ControlAlmacen> deleteds;
   TableColumn colUbicacion;
   TableColumn colCantidad;
   TableColumn colSerie;
   
   /**
    * Creates new form JControlInventariosDetailView
    * @param owner
    */
   public JControlInventariosDetailView(IControlInventarioMediator owner)
   {
      initAtributes (owner.obtenerOrigenDato());
      initComponents();
      initEvents();
      parent = owner;
      
      if (jtbAlmacenes.getColumnCount() > 0)
      {
         colUbicacion = jtbAlmacenes.getColumnModel().getColumn(0);
         colUbicacion.setCellRenderer(new LookUpComboBoxTableCellRenderer<Ubicacion>(bsUbicacionesRender));
         colUbicacion.setCellEditor(new LookUpComboBoxTableCellEditor<Ubicacion>(bsUbicacionesEditor));
         colUbicacion.setPreferredWidth(320);
         
         colCantidad = jtbAlmacenes.getColumnModel().getColumn(1);
         colCantidad.setPreferredWidth(80);
         
         colSerie = jtbAlmacenes.getColumnModel().getColumn(2);
         colSerie.setPreferredWidth(180);
             
         jtbAlmacenes.setRowHeight((int)(jtbAlmacenes.getRowHeight() * 1.5));
      }
      
      AutoCompleteDocument.enable(jcbBienVariante);
   }
   
    //---------------------------------------------------------------------
   private void initAtributes (String esquema)
   {
        ubicaciones = new UbicacionesBusinessLogic(esquema);
        esNuevo = true;
        construidoAlmacenes = false;
        bsPartes = new BienVariantesComboBoxModel();
        bsUbicacionesRender = new UbicacionesComboBoxModel();
        bsUbicacionesEditor = new UbicacionesComboBoxModel();
        bsAlmacenes = new ControlAlmacenTableModel(new String[]{  "Ubicación:{id_ubicacion}", "cantidad:{cantidad}", "Serie:{serie}" });
        bsAlmacenes.addTableModelListener(this);
           
        deleteds  = new ArrayList<>();
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
       = new ActionListener ()
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
      
       ChangeListener changeUsesManager = new ChangeListener()
      {

         @Override
         public void stateChanged(ChangeEvent e)
         {
            checkBox_stateChanged(e);
         }
      };
      
      jbAceptar.addActionListener(actionListenerManager);
      jbCancelar.addActionListener(actionListenerManager);
      jtpBody.addChangeListener(changeListenerManager);
      jtoolNuevoAlmacen.addActionListener(composeActionListener);
      jtoolEliminarAlmacen.addActionListener(composeActionListener);
      jchkCaducidad.addChangeListener(changeUsesManager);
      jchkDevolucion.addChangeListener(changeUsesManager);
   }
   
   //---------------------------------------------------------------------
   private void bodyTabbed_stateChanged(ChangeEvent e)
   {
      if (swCargador == null || swCargador.isDone())
      { 
         JTabbedPane pane = (JTabbedPane) e.getSource();
         if ( pane.getSelectedComponent() == tabIngredientes && !construidoAlmacenes)
         {
                List<Object> parametros = new ArrayList<Object>();
                swCargador = new ControlInventarioDetailCargarSwingWorker();
                if (esNuevo)
                   parametros.add(opLOAD_UBICACIONES);
                else
                {
                  parametros.add(opLOAD_ALMACENES);
                  parametros.add(elementoActual);
                }
                swCargador.execute(parametros);
                construidoAlmacenes = true;
         }
         
      }
   }
   
    //---------------------------------------------------------------------
   private void checkBox_stateChanged(ChangeEvent e)
   {
      JCheckBox check = (JCheckBox)e.getSource();
     if (check == jchkDevolucion)
     {
        jdcDevolucion.setEnabled(check.isSelected());
       /* if(!jdcDevolucion.isEnabled())
           jdcDevolucion.setDate(null);*/
           
     }
     
     else if (check == jchkCaducidad)
     {
        jdcCaducidad.setEnabled(check.isSelected());
        /*if (jdcCaducidad.isEnabled())
           jdcCaducidad.setDate(null);*/
     }
   }
 
   //---------------------------------------------------------------------
   private void finish_actionPerformed (ActionEvent evt)
   {
      Object sender = evt.getSource();
      
      if (sender == jbAceptar)
      {
         if (readElement(elementoActual))
         {
            if (jtbAlmacenes.isEditing())
            {
               TableCellEditor editor = jtbAlmacenes.getCellEditor();
               if (editor != null)
                 editor.stopCellEditing();
            }
            
            if (esNuevo)
               parent.onAcceptNewElement(elementoActual, bsAlmacenes.getData(), deleteds);

            else 
            {
               elementoActual.setModified();
               parent.onAcceptModifyElement(elementoActual, bsAlmacenes.getData(), deleteds);
            }
            
            close();
         }
      }
      
      else
         close ();
   }
   
   //---------------------------------------------------------------------
   private void composeToolBar_actionPerformed (ActionEvent evt)
   {
      int rowIndex;
      
      if (evt.getSource() == jtoolNuevoAlmacen)
      {
         if (bsUbicacionesEditor.getData().size() > 0)
         {
            ControlAlmacen nuevoAlmacen = new ControlAlmacen();
            nuevoAlmacen.setHasOneControlInventario(elementoActual);
            nuevoAlmacen.setIdUbicacion(bsUbicacionesEditor.getData().get(0).getId());
            nuevoAlmacen.setCantidad(1);
            nuevoAlmacen.setValoAcumulado(0);
            nuevoAlmacen.setSerie("");
            nuevoAlmacen.setObservaciones("");
            rowIndex = bsAlmacenes.addRow(nuevoAlmacen);
            jtbAlmacenes.setRowSelectionInterval(rowIndex, rowIndex);
            //jtbDirecciones.editCellAt(rowIndex, 0);
         }
         
         else
            JOptionPane.showMessageDialog(rootPane, "Es necesario haber capturado ubicaciones", "Información", JOptionPane.WARNING_MESSAGE);
      }
      
      else if (evt.getSource() == jtoolEliminarAlmacen)
      {
         if ( JOptionPane.showConfirmDialog(this,String.format("Ha seleccionado %d registro(s) para ser eleiminado(s)\n¿Esta seguro de continuar?",  jtbAlmacenes.getSelectedRowCount()) ) == JOptionPane.OK_OPTION)
         {
            int[] rowsHandlers = jtbAlmacenes.getSelectedRows();
            List<ControlAlmacen> selecteds = bsAlmacenes.removeRows(rowsHandlers);

            for (ControlAlmacen elemento : selecteds )
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
   }
   
   //---------------------------------------------------------------------
   public void prepareForNew ()
   {
      elementoActual = new ControlInventario();
      esNuevo = true;
      this.setTitle("Nuevo");
      bsPartes.setData(parent.obtenerPartes());
   }
   
   //---------------------------------------------------------------------
   public void prepareForModify (ControlInventario elemento)
   {
      this.elementoActual = elemento;
      esNuevo = false;
      this.setTitle(String.format("Modificando %s", elemento.getLote()));
      bsPartes.setData(parent.obtenerPartes());
      writeElement(elemento);
   }
   
   //---------------------------------------------------------------------
   public void writeElement (ControlInventario elemento)
   {
      jcbBienVariante.setSelectedItem(bsPartes.getElementAt(bsPartes.indexOfValue(elemento.getIdVariante())));
      jtfLote.setText(elemento.getLote());
      jtfExistencia.setValue(elemento.getExistencia());
      jdcFechaEntrada.setDate(elemento.getFecha_entrada());
       
      jtfMinimo.setValue(elemento.getMinimo());
      jtfMaximo.setValue(elemento.getMaximo());
      jtfReorden.setValue(elemento.getReorden());
      
      jchkCaducidad.setSelected(elemento.getFecha_caducidad().compareTo(ControlInventario.EMPTY_DATE) > 0);
      if (jchkCaducidad.isSelected())
         jdcCaducidad.setDate(elemento.getFecha_caducidad());
      
      jchkDevolucion.setSelected(elemento.getFecha_devolucion().compareTo(ControlInventario.EMPTY_DATE) > 0);
      if (jchkDevolucion.isSelected())
         jdcDevolucion.setDate(elemento.getFecha_devolucion());
   }
   
   //---------------------------------------------------------------------
   private boolean readElement (ControlInventario elemento)
   {
      boolean resultado= false;
      String mensaje = "";
      
      if (!jtfLote.getText().isEmpty() )
      {
         resultado = true; 
         
         elemento.setIdVariante((int)bsPartes.getSelectedValue());
        /* if (!elemento.getFk_inventario_variante_id().isSet())
            elemento.getFk_inventario_variante_id().setModified();*/
         
         elemento.setLote(jtfLote.getText());
         elemento.setExistencia((float)jtfExistencia.getValue());
         elemento.setFecha_entrada(jdcFechaEntrada.getDate());
         
         elemento.setMinimo((float)jtfMinimo.getValue());
         elemento.setMaximo((float)jtfMaximo.getValue());
         elemento.setReorden((float)jtfReorden.getValue());
       
         if (jchkCaducidad.isSelected())
            elemento.setFecha_caducidad(jdcCaducidad.getDate());
         
         else 
            elemento.setFecha_caducidad(ControlInventario.EMPTY_DATE);
         
         if (jchkDevolucion.isSelected())
            elemento.setFecha_devolucion(jdcDevolucion.getDate());
         else 
            elemento.setFecha_devolucion(ControlInventario.EMPTY_DATE);
         
         if (!elemento.isSet())
            elemento.setModified();
      }
      
      else
         mensaje = "Asegurese de proporcionar la descripcion";
      
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
   public void setDisplayData (List<Ubicacion> listaUbicaciones, List<ControlAlmacen> listaAlmacenes)
   {
      bsUbicacionesEditor.setData(listaUbicaciones);
      bsUbicacionesRender.setData(listaUbicaciones);
      if (listaAlmacenes != null)
         bsAlmacenes.setData(listaAlmacenes);
   }
   
 
  
   //----------------------------------------------------------------------
   /*public void jtbPartes_onCellValueChanged(ChangeEvent e, TableCellEditor editor)
   {
      if (jtbAlmacenes.getEditingColumn() == 0 && editor instanceof LookUpComboBoxTableCellEditor)
      {
          ComboBoxModel model = ((LookUpComboBoxTableCellEditor)editor).getModel();
          if (model instanceof POJOComboBoxModel)
          {
             POJOComboBoxModel<ControlAlmacen> POJOModel = (POJOComboBoxModel<ControlAlmacen>)model;
             
             if (POJOModel.getCurrent() != null)
                bsAlmacenes.getElementAt( jtbAlmacenes.getEditingRow()).setC
               jtbAlmacenes.setValueAt(POJOModel.getCurrent().getFk_variante_id_unidad_masa().getNombre(), jtbAlmacenes.getEditingRow(),  jtbAlmacenes.getColumnCount() - 1);
          }
      }
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

        jtpBody = new javax.swing.JTabbedPane();
        tabGeneral = new javax.swing.JPanel();
        jpDatos1 = new javax.swing.JPanel();
        jlbBien = new javax.swing.JLabel();
        jcbBienVariante = new javax.swing.JComboBox();
        jlbLote = new javax.swing.JLabel();
        jtfLote = new javax.swing.JTextField();
        jlbExistencia = new javax.swing.JLabel();
        jtfExistencia = new JFormattedTextField(new Float(1.0000F));
        jlbFechaEntrega = new javax.swing.JLabel();
        jdcFechaEntrada = new com.toedter.calendar.JDateChooser();
        tabVolumetria = new javax.swing.JPanel();
        jpDatos2 = new javax.swing.JPanel();
        jlbMinimo = new javax.swing.JLabel();
        jtfMinimo = new JFormattedTextField(new Float(1.0000F));
        jlbMaximo = new javax.swing.JLabel();
        jtfMaximo = new JFormattedTextField(new Float(1.0000F));
        jlbReorden = new javax.swing.JLabel();
        jtfReorden = new JFormattedTextField(new Float(1.0000F));
        jlbCaducidad = new javax.swing.JLabel();
        jchkCaducidad = new javax.swing.JCheckBox();
        jlbValCaducidad = new javax.swing.JLabel();
        jdcCaducidad = new com.toedter.calendar.JDateChooser();
        jlbMsjDevolucion = new javax.swing.JLabel();
        jchkDevolucion = new javax.swing.JCheckBox();
        jlbValDevolucion = new javax.swing.JLabel();
        jdcDevolucion = new com.toedter.calendar.JDateChooser();
        tabIngredientes = new javax.swing.JPanel();
        jbarDirecciones = new javax.swing.JToolBar();
        jtoolNuevoAlmacen = new javax.swing.JButton();
        jtoolEliminarAlmacen = new javax.swing.JButton();
        jspDirecciones = new javax.swing.JScrollPane();
        jtbAlmacenes = new javax.swing.JTable();
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

        jpDatos1.setPreferredSize(new java.awt.Dimension(500, 128));
        jpDatos1.setLayout(new java.awt.GridLayout(4, 2, 3, 3));

        jlbBien.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbBien.setLabelFor(jcbBienVariante);
        jlbBien.setText("Presentación:");
        jpDatos1.add(jlbBien);

        jcbBienVariante.setModel(bsPartes);
        jcbBienVariante.setRenderer(new POJOListCellRenderer<BienVariante>());
        jpDatos1.add(jcbBienVariante);

        jlbLote.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbLote.setLabelFor(jtfLote);
        jlbLote.setText("Lote:");
        jlbLote.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jpDatos1.add(jlbLote);

        jtfLote.setName("jtfLote"); // NOI18N
        jpDatos1.add(jtfLote);

        jlbExistencia.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbExistencia.setLabelFor(jtfExistencia);
        jlbExistencia.setText("Existencia:");
        jpDatos1.add(jlbExistencia);
        jpDatos1.add(jtfExistencia);

        jlbFechaEntrega.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbFechaEntrega.setLabelFor(jdcFechaEntrada);
        jlbFechaEntrega.setText("Fecha Entrada:");
        jpDatos1.add(jlbFechaEntrega);
        jpDatos1.add(jdcFechaEntrada);

        tabGeneral.add(jpDatos1);

        jtpBody.addTab("General", tabGeneral);

        tabVolumetria.setPreferredSize(new java.awt.Dimension(300, 200));

        jpDatos2.setPreferredSize(new java.awt.Dimension(450, 238));
        jpDatos2.setRequestFocusEnabled(false);
        jpDatos2.setLayout(new java.awt.GridLayout(8, 2, 3, 3));

        jlbMinimo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbMinimo.setText("Minímo:");
        jpDatos2.add(jlbMinimo);
        jpDatos2.add(jtfMinimo);

        jlbMaximo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbMaximo.setText("Máximo:");
        jpDatos2.add(jlbMaximo);
        jpDatos2.add(jtfMaximo);

        jlbReorden.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbReorden.setText("Reorden:");
        jpDatos2.add(jlbReorden);
        jpDatos2.add(jtfReorden);

        jlbCaducidad.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbCaducidad.setText("Utilizar caducidad:");
        jpDatos2.add(jlbCaducidad);
        jpDatos2.add(jchkCaducidad);

        jlbValCaducidad.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbValCaducidad.setText("Fecha Caducidad:");
        jpDatos2.add(jlbValCaducidad);
        jpDatos2.add(jdcCaducidad);

        jlbMsjDevolucion.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbMsjDevolucion.setText("Permite Devolución:");
        jpDatos2.add(jlbMsjDevolucion);
        jpDatos2.add(jchkDevolucion);

        jlbValDevolucion.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbValDevolucion.setText("Fecha Deolución:");
        jpDatos2.add(jlbValDevolucion);
        jpDatos2.add(jdcDevolucion);

        tabVolumetria.add(jpDatos2);

        jtpBody.addTab("Avanzado", tabVolumetria);

        tabIngredientes.setName("tabIngredientes"); // NOI18N
        tabIngredientes.setLayout(new java.awt.BorderLayout());

        jbarDirecciones.setRollover(true);

        jtoolNuevoAlmacen.setText("Nuevo");
        jtoolNuevoAlmacen.setFocusable(false);
        jtoolNuevoAlmacen.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jtoolNuevoAlmacen.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbarDirecciones.add(jtoolNuevoAlmacen);

        jtoolEliminarAlmacen.setText("Eliminar");
        jtoolEliminarAlmacen.setToolTipText("");
        jtoolEliminarAlmacen.setFocusable(false);
        jtoolEliminarAlmacen.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jtoolEliminarAlmacen.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbarDirecciones.add(jtoolEliminarAlmacen);

        tabIngredientes.add(jbarDirecciones, java.awt.BorderLayout.PAGE_START);

        jtbAlmacenes.setModel(bsAlmacenes);
        jtbAlmacenes.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jspDirecciones.setViewportView(jtbAlmacenes);

        tabIngredientes.add(jspDirecciones, java.awt.BorderLayout.CENTER);

        jtpBody.addTab("Control de Series", tabIngredientes);

        getContentPane().add(jtpBody, java.awt.BorderLayout.CENTER);

        jpAcciones.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jbCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/syswave/forms/resources/button-cross.png"))); // NOI18N
        jbCancelar.setText("Cancelar");
        jpAcciones.add(jbCancelar);

        jbAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/syswave/forms/resources/button-tick.png"))); // NOI18N
        jbAceptar.setText("Aceptar");
        jpAcciones.add(jbAceptar);

        getContentPane().add(jpAcciones, java.awt.BorderLayout.PAGE_END);

        jpEncabezado.setBackground(new java.awt.Color(220, 34, 163));
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

        setBounds(0, 0, 589, 413);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jbAceptar;
    private javax.swing.JButton jbCancelar;
    private javax.swing.JToolBar jbarDirecciones;
    private javax.swing.JComboBox jcbBienVariante;
    private javax.swing.JCheckBox jchkCaducidad;
    private javax.swing.JCheckBox jchkDevolucion;
    private com.toedter.calendar.JDateChooser jdcCaducidad;
    private com.toedter.calendar.JDateChooser jdcDevolucion;
    private com.toedter.calendar.JDateChooser jdcFechaEntrada;
    private javax.swing.JLabel jlbBien;
    private javax.swing.JLabel jlbCaducidad;
    private javax.swing.JLabel jlbExistencia;
    private javax.swing.JLabel jlbFechaEntrega;
    private javax.swing.JLabel jlbIcono;
    private javax.swing.JLabel jlbLote;
    private javax.swing.JLabel jlbMaximo;
    private javax.swing.JLabel jlbMinimo;
    private javax.swing.JLabel jlbMsjDevolucion;
    private javax.swing.JLabel jlbReorden;
    private javax.swing.JLabel jlbValCaducidad;
    private javax.swing.JLabel jlbValDevolucion;
    private javax.swing.JPanel jpAcciones;
    private java.awt.Panel jpAreaMensajes;
    private javax.swing.JPanel jpDatos1;
    private javax.swing.JPanel jpDatos2;
    private javax.swing.JPanel jpEncabezado;
    private javax.swing.JScrollPane jspDirecciones;
    private javax.swing.JTable jtbAlmacenes;
    private javax.swing.JFormattedTextField jtfExistencia;
    private javax.swing.JTextField jtfLote;
    private javax.swing.JFormattedTextField jtfMaximo;
    private javax.swing.JFormattedTextField jtfMinimo;
    private javax.swing.JFormattedTextField jtfReorden;
    private javax.swing.JButton jtoolEliminarAlmacen;
    private javax.swing.JButton jtoolNuevoAlmacen;
    private javax.swing.JTabbedPane jtpBody;
    private javax.swing.JPanel tabGeneral;
    private javax.swing.JPanel tabIngredientes;
    private javax.swing.JPanel tabVolumetria;
    // End of variables declaration//GEN-END:variables
//------------------------------------------------------------------
   private class ControlInventarioDetailCargarSwingWorker extends SwingWorker<List<Object>, Void>
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
               case opLOAD_ALMACENES:
                  arguments.add(ubicaciones.obtenerListaHojas());
                  arguments.add(parent.obtenerControlAlmacenes((ControlInventario)arguments.get(1)));
                  break;
                  
               case opLOAD_UBICACIONES:
                  arguments.add(ubicaciones.obtenerLista());
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
                  case opLOAD_ALMACENES:
                     setDisplayData((List<Ubicacion>)results.get(2), (List<ControlAlmacen>)results.get(3));
                     break;
                     
                  case opLOAD_UBICACIONES:
                     setDisplayData((List<Ubicacion>)results.get(1), null);
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
            if (arguments != null && arguments.size()>0)
               arguments.clear();
         }
      }
   }
}
