/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.syswave.forms.miempresa;

import com.syswave.entidades.common.Entidad;
import com.syswave.entidades.miempresa.Moneda;
import com.syswave.entidades.miempresa_vista.MonedaCambioVista;
import com.syswave.forms.databinding.MonedaCambioVistaTableModel;
import com.syswave.forms.databinding.MonedasComboBoxModel;
import com.syswave.swing.models.POJOTableModel;
import com.syswave.swing.table.editors.LookUpComboBoxTableCellEditor;
import com.syswave.swing.table.renders.LookUpComboBoxTableCellRenderer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;

/**
 *
 * @author victor
 */
public class JMonedaCambioFramedDataView extends javax.swing.JInternalFrame implements TableModelListener
{
   MonedaCambioVistaTableModel bsCambioMoneda;
   MonedasComboBoxModel bsMonedaOrigenRender;
   MonedasComboBoxModel bsMonedaOrigenEditor;
   MonedasComboBoxModel bsMonedaDestinoRender;
   MonedasComboBoxModel bsMonedaDestinoEditor;

   IMonedaCambioFramedMediator owner;
   boolean esNuevo;
    TableColumn colIdMonedaOrigen, colProporcion, colIdMonedaDestino/*,
                colReceptor*/;
   
   
   /**
    * Creates new form JMonedaCambioFramedDataView
    */
   public JMonedaCambioFramedDataView(IMonedaCambioFramedMediator parent)
   {
      owner = parent;
      initAtributes();
      initComponents();
      initEvents();
      
        if (jtData.getColumnCount() > 0)
        {
           colIdMonedaOrigen = jtData.getColumnModel().getColumn(0);
           colIdMonedaOrigen.setCellRenderer(new LookUpComboBoxTableCellRenderer<Moneda>(bsMonedaOrigenRender));
            colIdMonedaOrigen.setCellEditor(new LookUpComboBoxTableCellEditor<Moneda>(bsMonedaOrigenEditor));
           colIdMonedaOrigen.setPreferredWidth(150);
           
           colProporcion = jtData.getColumnModel().getColumn(1);
           colProporcion.setPreferredWidth(60);
           
           colIdMonedaDestino = jtData.getColumnModel().getColumn(2);
           colIdMonedaDestino.setCellRenderer(new LookUpComboBoxTableCellRenderer<Moneda>(bsMonedaDestinoRender));
           colIdMonedaDestino.setCellEditor(new LookUpComboBoxTableCellEditor<Moneda>(bsMonedaDestinoEditor));
           colIdMonedaDestino.setPreferredWidth(60);
           
           /*colFecha = jtData.getColumnModel().getColumn(3);
           colFecha.setCellEditor(new JDateChooserCellEditor());
           colFecha.setPreferredWidth(150);*/
           
           //Nota: Debido a los renders que se estan utilizando es necesario tener un renglón más alto.
           jtData.setRowHeight( (int)(jtData.getRowHeight() * 1.5));
        }  
   
   }
   
     //---------------------------------------------------------------------
   private void initAtributes()
   {
      esNuevo = true;
      bsCambioMoneda = new MonedaCambioVistaTableModel(new String[]
      {
         "Un (1):{id_moneda_origen}", "Son:{proporcion}", "De (N):{id_moneda_destino}"//, "{fecha_validez}"
      });
      bsMonedaOrigenRender = new MonedasComboBoxModel();
       bsMonedaOrigenEditor = new MonedasComboBoxModel();
       bsMonedaDestinoRender = new MonedasComboBoxModel();
       bsMonedaDestinoEditor = new MonedasComboBoxModel();
      bsCambioMoneda.addTableModelListener(this);
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

      jbAceptar.addActionListener(actionListenerManager);
      jbCancelar.addActionListener(actionListenerManager);
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
            
            if (e.getColumn() == colIdMonedaOrigen.getModelIndex() &&
                !((MonedaCambioVista)data).getHasOneMonedaOrigen().isSet())
               ((MonedaCambioVista)data).getHasOneMonedaOrigen().setModified();
            
            else if (e.getColumn() == colIdMonedaDestino.getModelIndex() &&
                !((MonedaCambioVista)data).getHasOneMonedaDestino().isSet())
               ((MonedaCambioVista)data).getHasOneMonedaDestino().setModified();
         }
      }
   }
   
   
   //---------------------------------------------------------------------
   public void prepareForModify(List<MonedaCambioVista> elemento)
   {
      esNuevo = false;
      bsCambioMoneda.setData(elemento);
      bsMonedaOrigenEditor.setData(owner.obtenerMonedas());
      bsMonedaOrigenRender.setData(owner.obtenerMonedas());
      bsMonedaDestinoEditor.setData(owner.obtenerMonedas());
      bsMonedaDestinoRender.setData(owner.obtenerMonedas());
      owner.bodyEnabled(false);
      jtData.setRowSelectionInterval(0, 0);

      //cargarRecursos(elemento);
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
   private void finish_actionPerformed(ActionEvent evt)
   {
      Object sender = evt.getSource();

      if (sender == jbAceptar)
      {
            tryStopDetailCellEditor(jtData);
            
            List<MonedaCambioVista> capturados = bsCambioMoneda.getData();
            if (revisarValores(capturados))
            {
               owner.onAcceptModifyElement(capturados  );
               close();
            }
            
            else
               JOptionPane.showMessageDialog(this, "Asegurese de capturar proporciones mayores que cero (0.0)", "Error", JOptionPane.ERROR_MESSAGE);
      }

      else
      {
         owner.onCancelModifyElement(bsCambioMoneda.getData());
         close();
      }
   }
   
   //---------------------------------------------------------------------
   private boolean revisarValores(List<MonedaCambioVista> capturados)
   {
      boolean esCorrecto = true;
      int i = 0;
      MonedaCambioVista actual;
      
      while (esCorrecto && i < capturados.size())
      {
         actual = capturados.get(i++);
         esCorrecto = !actual.isSet() || actual.getProporcion() > 0;
      }
      
      return esCorrecto;
   }
   
   //---------------------------------------------------------------------
   public void close()
   {
      owner.bodyEnabled(true);
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
        jspData = new javax.swing.JScrollPane();
        jtData = new javax.swing.JTable();
        jpAcciones = new javax.swing.JPanel();
        jbCancelar = new javax.swing.JButton();
        jbAceptar = new javax.swing.JButton();

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

        jpGeneral.setLayout(new java.awt.BorderLayout());

        jtData.setModel(bsCambioMoneda);
        jtData.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jspData.setViewportView(jtData);

        jpGeneral.add(jspData, java.awt.BorderLayout.CENTER);

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

        setBounds(0, 0, 600, 400);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jbAceptar;
    private javax.swing.JButton jbCancelar;
    private javax.swing.JLabel jlbIcono;
    private javax.swing.JPanel jpAcciones;
    private java.awt.Panel jpAreaMensajes;
    private javax.swing.JPanel jpEncabezado;
    private javax.swing.JPanel jpGeneral;
    private javax.swing.JScrollPane jspData;
    private javax.swing.JTable jtData;
    private javax.swing.JTabbedPane tpCuerpo;
    // End of variables declaration//GEN-END:variables

}
