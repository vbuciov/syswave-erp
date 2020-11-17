package com.syswave.forms.miempresa;

import com.syswave.entidades.miempresa.CondicionPago;
import com.syswave.entidades.miempresa.Valor;
import com.syswave.forms.databinding.ValorComboBoxModel;
import com.syswave.swing.renders.POJOListCellRenderer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;

/**
 *
 * @author victor
 */
public class JCondicionPagoDetailView extends javax.swing.JInternalFrame
{
   ICondicionPagoMediator owner;
   CondicionPago elementoActual;
   ValorComboBoxModel bsTipoCondiciones;
   ValorComboBoxModel bsUnidadMasa;
   
   boolean esNuevo;

   /**
    * Creates new form JCondicionPagoDetailView
    */
   public JCondicionPagoDetailView(ICondicionPagoMediator parent)
   {
      owner = parent;
      initAtributes ();
      initComponents();     
      initEvents();
   }
   
      //---------------------------------------------------------------------
   private void initAtributes ()
   {
        esNuevo = true;
        bsTipoCondiciones = new ValorComboBoxModel();
        bsUnidadMasa = new ValorComboBoxModel();
        
        bsUnidadMasa.addElement(new Valor (0, "Ninguna"));
         bsUnidadMasa.addElement(new Valor (1, "Día"));
          bsUnidadMasa.addElement(new Valor (2, "Mes"));
           bsUnidadMasa.addElement(new Valor (3, "Año"));
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
      
      
     /* ChangeListener changeListenerManager = new ChangeListener()
      {

         @Override
         public void stateChanged(ChangeEvent e)
         {
            bodyTabbed_stateChanged(e);
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
         if ( pane.getSelectedComponent() == tabIngredientes && !construidoCompuestos)
         {
                List<Object> parametros = new ArrayList<Object>();
                swCargador = new BienDetailCargarSwingWorker();
                if (esNuevo)
                   parametros.add(opLOAD_PARTES);
                else
                {
                  parametros.add(opLOAD_COMPUESTOS);
                   parametros.add(elementoActual);
                }
                swCargador.execute(parametros);
                construidoCompuestos = true;
         }
         
         else if (pane.getSelectedComponent() == tabIdentificadores && !construirIdentificadores)
         {
             List<Object> parametros = new ArrayList<Object>();
                swCargador = new BienDetailCargarSwingWorker();
    
                parametros.add(opLOAD_IDENTIFICADORES);
                parametros.add(elementoActual);
                
                swCargador.execute(parametros);
                construirIdentificadores = true;
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
      elementoActual = new CondicionPago();
      esNuevo = true;
      this.setTitle("Nuevo");   
      
      bsTipoCondiciones.setData(owner.obtenerTiposCondicion());
      
      //Valores iniciales
      elementoActual.setId(0); //Marcamos un id temporal, menor que 1, y distinto al valor vacio.
     
      if (bsUnidadMasa.getData().size() > 0)
         elementoActual.setUnidad(bsUnidadMasa.getData().get(0).getId());
    
      elementoActual.setNombre("");
      elementoActual.setValor(1);
      writeElement(elementoActual);
   }
   
   //---------------------------------------------------------------------
   public void prepareForModify (CondicionPago elemento)
   {
      this.elementoActual = elemento;
      esNuevo = false;
      this.setTitle(String.format("Modificando %s", elemento.getNombre()));
      bsTipoCondiciones.setData(owner.obtenerTiposCondicion());
    
      writeElement(elemento);
   }
   
   //---------------------------------------------------------------------
   public void writeElement (CondicionPago elemento)
   {
      jcbTipoCondicion.setSelectedItem(bsTipoCondiciones.getElementAt(bsTipoCondiciones.indexOfValue(elemento.getId_tipo_condicion())));
      jtfNombre.setText(elemento.getNombre());
      jtfValorMedida.setValue(elemento.getValor());
      jcbUnidad.setSelectedItem(bsUnidadMasa.getElementAt(bsUnidadMasa.indexOfValue(elemento.getUnidad())));
   }
   
   //---------------------------------------------------------------------
   private boolean readElement (CondicionPago elemento)
   {
      boolean resultado= false;
      String mensaje = "";
      
      if (!jtfNombre.getText().isEmpty() )
      {
         resultado = true;         
         elemento.setId_tipo_condicion((int)bsTipoCondiciones.getSelectedValue());
         elemento.setNombre(jtfNombre.getText());
         elemento.setValor((int)jtfValorMedida.getValue());
         elemento.setUnidad((int)bsUnidadMasa.getSelectedValue());
      
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
        jlbTipoCondicion = new javax.swing.JLabel();
        jcbTipoCondicion = new javax.swing.JComboBox();
        jlbNombre = new javax.swing.JLabel();
        jtfNombre = new javax.swing.JTextField();
        jlbValor = new javax.swing.JLabel();
        jtfValorMedida = new JFormattedTextField(new Integer(1));
        jlbUnidad = new javax.swing.JLabel();
        jcbUnidad = new javax.swing.JComboBox();
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

        jpDatos1.setPreferredSize(new java.awt.Dimension(500, 238));
        jpDatos1.setLayout(new java.awt.GridLayout(7, 2, 5, 8));

        jlbTipoCondicion.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbTipoCondicion.setText("Tipo Condición:");
        jpDatos1.add(jlbTipoCondicion);

        jcbTipoCondicion.setModel(bsTipoCondiciones);
        jcbTipoCondicion.setRenderer(new POJOListCellRenderer<Valor>());
        jpDatos1.add(jcbTipoCondicion);

        jlbNombre.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbNombre.setText("Nombre:");
        jlbNombre.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jpDatos1.add(jlbNombre);

        jtfNombre.setName("jtfNombre"); // NOI18N
        jpDatos1.add(jtfNombre);

        jlbValor.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbValor.setText("Valor:");
        jpDatos1.add(jlbValor);
        jpDatos1.add(jtfValorMedida);

        jlbUnidad.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbUnidad.setText("Unidad de Medida:");
        jpDatos1.add(jlbUnidad);

        jcbUnidad.setModel(bsUnidadMasa);
        jcbUnidad.setName("jcbUnidad"); // NOI18N
        jcbUnidad.setRenderer(new POJOListCellRenderer<Valor>()
        );
        jpDatos1.add(jcbUnidad);

        tabGeneral.add(jpDatos1);

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

        setBounds(0, 0, 597, 439);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jbAceptar;
    private javax.swing.JButton jbCancelar;
    private javax.swing.JComboBox jcbTipoCondicion;
    private javax.swing.JComboBox jcbUnidad;
    private javax.swing.JLabel jlbIcono;
    private javax.swing.JLabel jlbNombre;
    private javax.swing.JLabel jlbTipoCondicion;
    private javax.swing.JLabel jlbUnidad;
    private javax.swing.JLabel jlbValor;
    private javax.swing.JPanel jpAcciones;
    private java.awt.Panel jpAreaMensajes;
    private javax.swing.JPanel jpDatos1;
    private javax.swing.JPanel jpEncabezado;
    private javax.swing.JTextField jtfNombre;
    private javax.swing.JFormattedTextField jtfValorMedida;
    private javax.swing.JTabbedPane jtpBody;
    private javax.swing.JPanel tabGeneral;
    // End of variables declaration//GEN-END:variables
}
