package com.syswave.forms.miempresa;

import com.orbital.AutoCompleteDocument;
import com.syswave.entidades.miempresa.ControlAlmacen;
import com.syswave.entidades.miempresa.Persona;
import com.syswave.entidades.miempresa.Persona_tiene_Existencia;
import com.syswave.forms.databinding.ControlAlmacenesComboBoxModel;
import com.syswave.forms.databinding.PersonasComboBoxModel;
import com.syswave.swing.renders.POJOListCellRenderer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class JPersonaTieneExistenciaDetailView extends javax.swing.JInternalFrame
{

/* private final int opLOAD_VARIANTES = 0;
   private final int opLOAD_UNIDADES = 1;*/

   IPersonaTieneExistenciaMediator parent;
   
   Persona_tiene_Existencia elementoActual;
   PersonasComboBoxModel bsPersonas;
   ControlAlmacenesComboBoxModel bsSeries;
   
   /*UnidadesBusinessLogic unidades;
   UnidadComboBoxModel bsUnidadMasaRender;
   UnidadComboBoxModel bsUnidadMasaEditor;
   UnidadComboBoxModel bsUnidadLongitudRender;
   UnidadComboBoxModel bsUnidadLongitudEditor;*/
   
   boolean esNuevo, construidoDirecciones;
   /*TableColumn colDescripcion;
   TableColumn colvalorMasa;
   
   TableColumn colUnidadMasa;
   TableColumn colUnidadLongitud;
   
   TipoBienDetailCargarSwingWorker swCargador;
   List<BienVariante> deleteds;*/
  
   /**
    * Creates new form JBienDetailView
    * @param owner
    */
   public JPersonaTieneExistenciaDetailView(IPersonaTieneExistenciaMediator owner)
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
      
      AutoCompleteDocument.enable(jcbPersonas);
      AutoCompleteDocument.enable(jcbSeries);

   }
   
     //---------------------------------------------------------------------
   private void initAtributes ()
   {
        esNuevo = true;
        construidoDirecciones = false;
        bsPersonas = new PersonasComboBoxModel();
        bsSeries = new ControlAlmacenesComboBoxModel();
        
        /*unidades = new UnidadesBusinessLogic("configuracion");
        bsUnidadMasaRender = new UnidadComboBoxModel();
        bsUnidadMasaEditor = new UnidadComboBoxModel();
        bsUnidadLongitudRender = new UnidadComboBoxModel();
        bsUnidadLongitudEditor = new UnidadComboBoxModel();

        bsVariantes = new BienVariantesTableModel(new String[]{  "Descripción:{descripcion}", "Valor de Medida:{masa}", "Unidad de Medida:{id_unidad_masa}", 
                                                                 "Ancho:{ancho}", "Alto:{alto}", "Largo:{largo}", "Unidad de Longitud:{id_unidad_longitud}", 
                                                                 "Activo:{es_activo}", "Controlar Inventario:{es_inventario}", "Comercializar:{es_comercializar}"});
        bsVariantes.addTableModelListener(this);
        
        deleteds  = new ArrayList<>();*/
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
       };*/
      
      /*ChangeListener changeListenerManager = new ChangeListener()
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
   }*/
 
   //---------------------------------------------------------------------
   private void finish_actionPerformed (ActionEvent evt)
   {
      Object sender = evt.getSource();
      
      if (sender == jbAceptar)
      {
         if (readElement(elementoActual))
         {       
            if (esNuevo)
               parent.onAcceptNewElement(elementoActual/*, bsVariantes.getData(), deleteds*/);

            else 
            {
               elementoActual.setModified();
               parent.onAcceptModifyElement(elementoActual/*, bsVariantes.getData(), deleteds*/);
            }
            
            close();
         }
      }
      
      else
         close ();
   }
   
   //---------------------------------------------------------------------
   /*private void presentationToolBar_actionPerformed (ActionEvent evt)
   {
      int rowIndex;
      
      if (evt.getSource() == jtoolNuevoDireccion)
      {
         if (bsUnidadMasaEditor.getData().size() > 0 && bsUnidadLongitudEditor.getData().size() > 0)
         {
            BienVariante nuevaPresentacion = new BienVariante();
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
            List<BienVariante> selecteds = bsVariantes.removeRows(rowsHandlers);

            for (BienVariante elemento : selecteds )
            {
               if ( !elemento.isNew() )
                  deleteds.add(elemento);
            }
        }
      }
   }*/
   //---------------------------------------------------------------------
   /*@Override
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
      elementoActual = new Persona_tiene_Existencia();
      esNuevo = true;
      this.setTitle("Nuevo");
      bsPersonas.setData(parent.obtenerPersonas());
      bsSeries.setData(parent.obtenerSeries());
      //writeElement(elementoActual);
   }
   
   //---------------------------------------------------------------------
   public void prepareForModify (Persona_tiene_Existencia elemento)
   {
      this.elementoActual = elemento;
      esNuevo = false;
      //this.setTitle(String.format("Modificando %s", elemento.getNombre()));
      bsPersonas.setData(parent.obtenerPersonas());
      bsSeries.setData(parent.obtenerSeries());
      writeElement(elemento);
   }
   
   //---------------------------------------------------------------------
   public void writeElement (Persona_tiene_Existencia elemento)
   {
      jtfExistencia.setValue(elemento.getExistencia());
      jcbPersonas.setSelectedItem( bsPersonas.getElementAt( bsPersonas.indexOfValue(elemento.getIdPersona())) );
      jcbSeries.setSelectedItem( bsSeries.getElementAt( bsSeries.indexOfValue(elemento.getIdSerie())) );
   }
   
   //---------------------------------------------------------------------
   private boolean readElement (Persona_tiene_Existencia elemento)
   {
     boolean resultado= false;
      String mensaje = "";
      
      if (!jtfExistencia.getText().isEmpty() )
      {
         resultado = true;
        
         elemento.setExistencia((float)jtfExistencia.getValue());
         elemento.setIdPersona((Integer)bsPersonas.getSelectedValue());
         if (!elemento.getHasOnePersona().isSet())
            elemento.getHasOnePersona().setModified();
         
         elemento.setEntrada(bsSeries.getCurrent().getEntrada());
         elemento.setIdUbicacion(bsSeries.getCurrent().getIdUbicacion());
         
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
   /*public void setDisplayData (List<Unidad> listaUnidades, List<BienVariante> listaVariantes)
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
        jlbPersona = new javax.swing.JLabel();
        jcbPersonas = new javax.swing.JComboBox();
        jlbSerie = new javax.swing.JLabel();
        jcbSeries = new javax.swing.JComboBox();
        jlbExistencia = new javax.swing.JLabel();
        jtfExistencia = new JFormattedTextField(new Float(1.0));
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
        tabGeneral.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));

        jpDatosGenerales.setPreferredSize(new java.awt.Dimension(580, 120));
        jpDatosGenerales.setRequestFocusEnabled(false);
        java.awt.GridBagLayout jpDatosGeneralesLayout = new java.awt.GridBagLayout();
        jpDatosGeneralesLayout.columnWidths = new int[] {0, 3, 0};
        jpDatosGeneralesLayout.rowHeights = new int[] {0, 3, 0, 3, 0};
        jpDatosGenerales.setLayout(jpDatosGeneralesLayout);

        jlbPersona.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbPersona.setText("Persona:");
        jlbPersona.setMaximumSize(new java.awt.Dimension(13, 15));
        jlbPersona.setMinimumSize(new java.awt.Dimension(13, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jpDatosGenerales.add(jlbPersona, gridBagConstraints);

        jcbPersonas.setModel(bsPersonas);
        jcbPersonas.setAutoscrolls(true);
        jcbPersonas.setPreferredSize(new java.awt.Dimension(500, 25));
        jcbPersonas.setRenderer(new POJOListCellRenderer<Persona>());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jpDatosGenerales.add(jcbPersonas, gridBagConstraints);

        jlbSerie.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbSerie.setText("Serie:");
        jlbSerie.setPreferredSize(new java.awt.Dimension(56, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        jpDatosGenerales.add(jlbSerie, gridBagConstraints);

        jcbSeries.setModel(bsSeries);
        jcbSeries.setAutoscrolls(true);
        jcbSeries.setName("jcbSeries"); // NOI18N
        jcbSeries.setPreferredSize(new java.awt.Dimension(500, 25));
        jcbSeries.setRenderer(new POJOListCellRenderer<ControlAlmacen>()
        );
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        jpDatosGenerales.add(jcbSeries, gridBagConstraints);

        jlbExistencia.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbExistencia.setText("Existencia:");
        jlbExistencia.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jlbExistencia.setPreferredSize(new java.awt.Dimension(70, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        jpDatosGenerales.add(jlbExistencia, gridBagConstraints);

        jtfExistencia.setPreferredSize(new java.awt.Dimension(100, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jpDatosGenerales.add(jtfExistencia, gridBagConstraints);

        tabGeneral.add(jpDatosGenerales);

        jtpBody.addTab("General", tabGeneral);

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

        setBounds(0, 0, 617, 362);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jbAceptar;
    private javax.swing.JButton jbCancelar;
    private javax.swing.JComboBox jcbPersonas;
    private javax.swing.JComboBox jcbSeries;
    private javax.swing.JLabel jlbExistencia;
    private javax.swing.JLabel jlbIcono;
    private javax.swing.JLabel jlbPersona;
    private javax.swing.JLabel jlbSerie;
    private javax.swing.JPanel jpAcciones;
    private java.awt.Panel jpAreaMensajes;
    private javax.swing.JPanel jpDatosGenerales;
    private javax.swing.JPanel jpEncabezado;
    private javax.swing.JFormattedTextField jtfExistencia;
    private javax.swing.JTabbedPane jtpBody;
    private javax.swing.JPanel tabGeneral;
    // End of variables declaration//GEN-END:variables
}
