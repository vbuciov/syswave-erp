/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.syswave.forms.miempresa;

import com.syswave.entidades.miempresa.Moneda;
import com.syswave.entidades.miempresa.MonedaCambio;
import com.syswave.forms.databinding.MonedasComboBoxModel;
import com.syswave.swing.renders.POJOListCellRenderer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;

/**
 *
 * @author victor
 */
public class JMonedaCambioDetailView extends javax.swing.JInternalFrame
{  
   private IMonedaCambioMediator owner;
   MonedaCambio elementoActual;
   
   
   MonedasComboBoxModel bsMonedaDestino;
   MonedasComboBoxModel bsMonedaOrigen;
   
   boolean esNuevo;


   /**
    * Creates new form JMonedaCambioDetailView
    */
   public JMonedaCambioDetailView(IMonedaCambioMediator parent)
   {
      owner = parent;
       initAtributes ();
      initComponents();
      initEvents();
      parent = owner;
   }
   
     //---------------------------------------------------------------------
   private void initAtributes ()
   {
        esNuevo = true;
        bsMonedaDestino = new MonedasComboBoxModel();
        bsMonedaOrigen = new MonedasComboBoxModel();
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
      
      /*ActionListener composeActionListener
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
      };*/
      
      jbAceptar.addActionListener(actionListenerManager);
      jbCancelar.addActionListener(actionListenerManager);
      //jtpBody.addChangeListener(changeListenerManager);
   }
   
   //---------------------------------------------------------------------
   /*private void bodyTabbed_stateChanged(ChangeEvent e)
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
               owner.onAcceptNewElement(elementoActual);

            else 
            {
               elementoActual.setModified();
               owner.onAcceptModifyElement(elementoActual);
            }
            
            close();
         }
      }
      
      else
         close ();
   }
   
    //---------------------------------------------------------------------
   public void prepareForNew ()
   {
      elementoActual = new MonedaCambio();
      esNuevo = true;
      jdcFechaValidez.setEnabled(false);
      this.setTitle("Nuevo");
      bsMonedaOrigen.setData(owner.obtenerMonedas());
      bsMonedaDestino.setData(owner.obtenerMonedas());
   }
   
   //---------------------------------------------------------------------
   public void prepareForModify (MonedaCambio elemento)
   {
      this.elementoActual = elemento;
      esNuevo = false;
      this.setTitle("Modificando");
      bsMonedaOrigen.setData(owner.obtenerMonedas());
      bsMonedaDestino.setData(owner.obtenerMonedas());
      writeElement(elemento);
   }
   
   //---------------------------------------------------------------------
   public void writeElement (MonedaCambio elemento)
   {
      jcbMonedaOrigen.setSelectedItem(bsMonedaOrigen.getElementAt(bsMonedaOrigen.indexOfValue(elemento.getIdMonedaOrigen())));
      jtfProporcion.setValue(elemento.getProporcion());
      jcbMonedaDestino.setSelectedItem(bsMonedaDestino.getElementAt(bsMonedaDestino.indexOfValue(elemento.getIdMonedaDestino())));
    
      jdcFechaValidez.setDate(elemento.getFecha_validez());    
   }
   
   //---------------------------------------------------------------------
   private boolean readElement (MonedaCambio elemento)
   {
      boolean resultado= false;
      String mensaje = "";
      
      if (!jtfProporcion.getText().isEmpty() )
      {
         resultado = true;     
         elemento.setIdMonedaOrigen((int)bsMonedaOrigen.getSelectedValue());
         elemento.setProporcion((float)jtfProporcion.getValue());
         elemento.setIdMonedaDestino((int)bsMonedaDestino.getSelectedValue());
         elemento.setFecha_validez(jdcFechaValidez.getDate());
              
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


   /**
    * This method is called from within the constructor to initialize the form.
    * WARNING: Do NOT modify this code. The content of this method is always
    * regenerated by the Form Editor.
    */
   @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jpEncabezado = new javax.swing.JPanel();
        jlbIcono = new javax.swing.JLabel();
        jpAreaMensajes = new java.awt.Panel();
        tpCuerpo = new javax.swing.JTabbedPane();
        jpGeneral = new javax.swing.JPanel();
        jpDatos1 = new javax.swing.JPanel();
        jlbMonedaOrigen = new javax.swing.JLabel();
        jcbMonedaOrigen = new javax.swing.JComboBox();
        jlbProporcion = new javax.swing.JLabel();
        jtfProporcion = new JFormattedTextField(new Float(1.0000F));
        jlbMonedaDestino = new javax.swing.JLabel();
        jcbMonedaDestino = new javax.swing.JComboBox();
        jlbFechaValidez = new javax.swing.JLabel();
        jdcFechaValidez = new com.toedter.calendar.JDateChooser();
        jpAcciones = new javax.swing.JPanel();
        jbCancelar = new javax.swing.JButton();
        jbAceptar = new javax.swing.JButton();

        setClosable(true);
        setMaximizable(true);
        setResizable(true);

        jpEncabezado.setBackground(new java.awt.Color(70, 123, 152));
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

        jpDatos1.setPreferredSize(new java.awt.Dimension(500, 128));
        jpDatos1.setLayout(new java.awt.GridLayout(4, 2, 3, 3));

        jlbMonedaOrigen.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbMonedaOrigen.setText("Un(1):");
        jpDatos1.add(jlbMonedaOrigen);

        jcbMonedaOrigen.setModel(bsMonedaOrigen);
        jcbMonedaOrigen.setRenderer(new POJOListCellRenderer<Moneda>());
        jpDatos1.add(jcbMonedaOrigen);

        jlbProporcion.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbProporcion.setText("Son:");
        jpDatos1.add(jlbProporcion);
        jpDatos1.add(jtfProporcion);

        jlbMonedaDestino.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbMonedaDestino.setText("De(N):");
        jpDatos1.add(jlbMonedaDestino);

        jcbMonedaDestino.setModel(bsMonedaDestino);
        jcbMonedaDestino.setRenderer(new POJOListCellRenderer<Moneda>());
        jpDatos1.add(jcbMonedaDestino);

        jlbFechaValidez.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbFechaValidez.setText("Fecha Validez:");
        jpDatos1.add(jlbFechaValidez);
        jpDatos1.add(jdcFechaValidez);

        jpGeneral.add(jpDatos1);

        tpCuerpo.addTab("General", jpGeneral);

        getContentPane().add(tpCuerpo, java.awt.BorderLayout.CENTER);

        jpAcciones.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jbCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/syswave/forms/resources/button-cross.png"))); // NOI18N
        jbCancelar.setText("Cancelar");
        jpAcciones.add(jbCancelar);

        jbAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/syswave/forms/resources/button-tick.png"))); // NOI18N
        jbAceptar.setText("Aceptar");
        jpAcciones.add(jbAceptar);

        getContentPane().add(jpAcciones, java.awt.BorderLayout.SOUTH);

        setBounds(0, 0, 569, 399);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jbAceptar;
    private javax.swing.JButton jbCancelar;
    private javax.swing.JComboBox jcbMonedaDestino;
    private javax.swing.JComboBox jcbMonedaOrigen;
    private com.toedter.calendar.JDateChooser jdcFechaValidez;
    private javax.swing.JLabel jlbFechaValidez;
    private javax.swing.JLabel jlbIcono;
    private javax.swing.JLabel jlbMonedaDestino;
    private javax.swing.JLabel jlbMonedaOrigen;
    private javax.swing.JLabel jlbProporcion;
    private javax.swing.JPanel jpAcciones;
    private java.awt.Panel jpAreaMensajes;
    private javax.swing.JPanel jpDatos1;
    private javax.swing.JPanel jpEncabezado;
    private javax.swing.JPanel jpGeneral;
    private javax.swing.JFormattedTextField jtfProporcion;
    private javax.swing.JTabbedPane tpCuerpo;
    // End of variables declaration//GEN-END:variables
}
