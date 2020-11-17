package com.syswave.forms.miempresa;

import com.syswave.entidades.miempresa.CondicionPago;
import com.syswave.forms.databinding.CondicionPagoTableModel;
import com.syswave.logicas.miempresa.CondicionesPagoBusinessLogic;
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
public class JCondicionesPagoBusqueda extends javax.swing.JInternalFrame implements PropertyChangeListener //TableModelListener
{
    private final int opLOAD_PRICE = 0;
   ICondicionPagoBusqueda owner;
   
   CondicionPago limitado;
   CondicionesPagoBusinessLogic valores;
   CondicionPagoTableModel bsValores;

   CondicionPagoBusquedaCargarSwingWorker swCargador;
   TableColumn colValor/*, colDescripcion, colActivo*/;

   /**
    * Creates new form JCondicionesPagoBusqueda
    */
   public JCondicionesPagoBusqueda(ICondicionPagoBusqueda parent)
   {
      owner = parent;
      initAtributes(parent.getEsquema());
      initComponents();
       initEvents();

      if (jtbValores.getColumnCount() > 0)
      {
         colValor = jtbValores.getColumnModel().getColumn(0);
         colValor.setPreferredWidth(300);
         
         /*colDescripcion = jtbValores.getColumnModel().getColumn(1);
         colDescripcion.setPreferredWidth(400);

         colActivo = jtbValores.getColumnModel().getColumn(2);
         //colActivo.setPreferredWidth(300);
         
         
         //Nota: Debido a los renders que se estan utilizando es necesario tener un renglón más alto.
         //jtbPrecios.setRowHeight((int) (jtbPrecios.getRowHeight() * 1.5));*/
      }
   }
   
   //--------------------------------------------------------------------
   private void initAtributes(String esquema)
   {
      valores = new CondicionesPagoBusinessLogic(esquema);

      bsValores = new CondicionPagoTableModel(new String[]
      {
         "Nombre:{nombre}"
      });
      
      bsValores.setReadOnly(true);

      //bsValores.addTableModelListener(this);
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
    * Especifica una seccion para restringir los valores.
    */
   public void limitarSeccion(int id_tipo_condicion)
   {
      CondicionPago nuevo = new CondicionPago ();
      nuevo.setId_tipo_condicion(id_tipo_condicion);
      limitado = nuevo;
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
   
    //----------------------------------------------------------------------
   public void busquedaInicial()
   {
      if (swCargador == null || swCargador.isDone())
      {
         List<Object> parametros = new ArrayList<Object>();
         swCargador = new CondicionPagoBusquedaCargarSwingWorker();
         swCargador.addPropertyChangeListener(this);
         parametros.add(opLOAD_PRICE);
         parametros.add(limitado);
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
         swCargador = new CondicionPagoBusquedaCargarSwingWorker();
         swCargador.addPropertyChangeListener(this);
         parametros.add(opLOAD_PRICE);
         if (limitado == null)
            limitado = new CondicionPago();
         limitado.setNombre(jtfValor.getText());
         parametros.add(limitado);
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
         if (jtbValores.getSelectedRowCount()>= 0)
          {
            owner.onAcceptNewElement(bsValores.getElementAt(jtbValores.getSelectedRow()));
            close();
          }
          else
            JOptionPane.showInternalMessageDialog(this, "No se ha seleccionado un precio válido", "Error", JOptionPane.ERROR_MESSAGE);
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

   //----------------------------------------------------------------------
   public void setDisplayData(List<CondicionPago> listaPrecios)
   {
      if (listaPrecios != null)
         bsValores.setData(listaPrecios);
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
        jtfValor = new javax.swing.JTextField();
        jbBuscar = new javax.swing.JButton();
        jspValores = new javax.swing.JScrollPane();
        jtbValores = new javax.swing.JTable();
        jpAcciones = new javax.swing.JPanel();
        jbCancelar = new javax.swing.JButton();
        jbAceptar = new javax.swing.JButton();
        jpEncabezado = new javax.swing.JPanel();
        jlbIcono = new javax.swing.JLabel();
        jpAreaMensajes = new javax.swing.JPanel();
        jlbMensajes = new javax.swing.JLabel();
        jpbAvances = new javax.swing.JProgressBar();

        jtpBody.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
        jtpBody.setName(""); // NOI18N

        tabGeneral.setName("tabGeneral"); // NOI18N
        tabGeneral.setLayout(new java.awt.BorderLayout());

        jpCriteriosBusqueda.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jlbMensaje.setText("Criterio:");
        jpCriteriosBusqueda.add(jlbMensaje);

        jtfValor.setPreferredSize(new java.awt.Dimension(400, 25));
        jpCriteriosBusqueda.add(jtfValor);

        jbBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/syswave/forms/resources/buscar.png"))); // NOI18N
        jpCriteriosBusqueda.add(jbBuscar);

        tabGeneral.add(jpCriteriosBusqueda, java.awt.BorderLayout.NORTH);

        jtbValores.setModel(bsValores);
        jtbValores.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jspValores.setViewportView(jtbValores);

        tabGeneral.add(jspValores, java.awt.BorderLayout.CENTER);

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
    private javax.swing.JLabel jlbIcono;
    private javax.swing.JLabel jlbMensaje;
    private javax.swing.JLabel jlbMensajes;
    private javax.swing.JPanel jpAcciones;
    private javax.swing.JPanel jpAreaMensajes;
    private javax.swing.JPanel jpCriteriosBusqueda;
    private javax.swing.JPanel jpEncabezado;
    private javax.swing.JProgressBar jpbAvances;
    private javax.swing.JScrollPane jspValores;
    private javax.swing.JTable jtbValores;
    private javax.swing.JTextField jtfValor;
    private javax.swing.JTabbedPane jtpBody;
    private javax.swing.JPanel tabGeneral;
    // End of variables declaration//GEN-END:variables
//------------------------------------------------------------------
   private class CondicionPagoBusquedaCargarSwingWorker extends SwingWorker<List<Object>, Void>
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
               case opLOAD_PRICE:
                  arguments.add(valores.obtenerLista((CondicionPago)arguments.get(1)));
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
                  case opLOAD_PRICE:
                     if (valores.esCorrecto())
                     {
                        setDisplayData((List<CondicionPago>) results.get(2));
                        showTaskHeader("Resultados obtenidos", false);
                     }
                     else 
                     {
                        showTaskHeader("Ocurrió un error", false);
                        JOptionPane.showMessageDialog(null, valores.getMensaje(), "Error", JOptionPane.OK_OPTION);
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
