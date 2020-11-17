package com.syswave.forms.miempresa;

import com.orbital.AutoCompleteDocument;
import com.syswave.entidades.common.Entidad;
import com.syswave.entidades.miempresa.ControlAlmacen;
import com.syswave.entidades.miempresa.ControlInventario;
import com.syswave.entidades.miempresa.Persona;
import com.syswave.entidades.miempresa.Persona_tiene_Existencia;
import com.syswave.entidades.miempresa.Ubicacion;
import com.syswave.forms.databinding.ControlInventariosComboBoxModel;
import com.syswave.swing.models.POJOTableModel;
import com.syswave.forms.databinding.PersonaTieneExistenciasTableModel;
import com.syswave.forms.databinding.PersonasComboBoxModel;
import com.syswave.forms.databinding.UbicacionesComboBoxModel;
import com.syswave.swing.table.editors.LookUpComboBoxTableCellEditor;
import com.syswave.swing.table.renders.LookUpComboBoxTableCellRenderer;
import com.syswave.swing.renders.POJOListCellRenderer;
import com.syswave.logicas.miempresa.PersonasBusinessLogic;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;
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
public class JControlAlmacenesDetailView extends javax.swing.JInternalFrame implements TableModelListener
{
   private final int opLOAD_EXISTENCIAS = 0;
   private final int opLOAD_PERSONAS = 1;
   
   private IControlAlmacenMediator parent;
   ControlAlmacen elementoActual;
    
   UbicacionesComboBoxModel bsUbicaciones;
   ControlInventariosComboBoxModel bsLotes;
   
   PersonaTieneExistenciasTableModel bsExistencias;
   PersonasBusinessLogic personas;
   PersonasComboBoxModel bsPersonasRender;
   PersonasComboBoxModel bsPersonasEditor;
   
   boolean esNuevo, construidoExistencias;
  
   ControlAlmacenDetailCargarSwingWorker swCargador;
   List<Persona_tiene_Existencia> deleteds;
   TableColumn colPersona;
   TableColumn colExistencia;
   
   /**
    * Creates new form JControlAlmacensDetailView
    * @param owner
    */
   public JControlAlmacenesDetailView(IControlAlmacenMediator owner)
   {
      initAtributes (owner.obtenerOrigenDato());
      initComponents();
      initEvents();
      parent = owner;
      
      if (jtbPersonas.getColumnCount() > 0)
      {
         colPersona = jtbPersonas.getColumnModel().getColumn(0);
         colPersona.setCellRenderer(new LookUpComboBoxTableCellRenderer<Persona>(bsPersonasRender));
         colPersona.setCellEditor(new LookUpComboBoxTableCellEditor<Persona>(bsPersonasEditor));
         colPersona.setPreferredWidth(320);
         
         colExistencia = jtbPersonas.getColumnModel().getColumn(1);
         colExistencia.setPreferredWidth(180);
             
         jtbPersonas.setRowHeight((int)(jtbPersonas.getRowHeight() * 1.5));
      }
      
      AutoCompleteDocument.enable(jcbUbicacion);
      AutoCompleteDocument.enable(jcbLote);
   }
   
    //---------------------------------------------------------------------
   private void initAtributes (String esquema)
   {
        esNuevo = true;
        construidoExistencias = false;
        personas = new PersonasBusinessLogic(esquema);
        bsUbicaciones = new UbicacionesComboBoxModel();
        bsLotes = new ControlInventariosComboBoxModel();
        bsPersonasRender = new PersonasComboBoxModel();
        bsPersonasEditor = new PersonasComboBoxModel();
        bsExistencias = new PersonaTieneExistenciasTableModel(new String[]{  "Persona:{id_persona}", "Existencia:{existencia}" });
        bsExistencias.addTableModelListener(this);
           
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
      
       ItemListener valueChangeItemListener = new ItemListener()
        {
            @Override
            public void itemStateChanged(ItemEvent e)
            {
                   jcbSeries_itemStateChanged(e);
            }
        };
          
      jbAceptar.addActionListener(actionListenerManager);
      jbCancelar.addActionListener(actionListenerManager);
      jtpBody.addChangeListener(changeListenerManager);
      jtoolNuevoAlmacen.addActionListener(composeActionListener);
      jtoolEliminarAlmacen.addActionListener(composeActionListener);
      jcbLote.addItemListener(valueChangeItemListener);
   }
   
   //---------------------------------------------------------------------
   private void bodyTabbed_stateChanged(ChangeEvent e)
   {
      if (swCargador == null || swCargador.isDone())
      { 
         JTabbedPane pane = (JTabbedPane) e.getSource();
         if ( pane.getSelectedComponent() == tabResponsables && !construidoExistencias)
         {
                List<Object> parametros = new ArrayList<Object>();
                swCargador = new ControlAlmacenDetailCargarSwingWorker();
                if (esNuevo)
                   parametros.add(opLOAD_PERSONAS);
                else
                {
                  parametros.add(opLOAD_EXISTENCIAS);
                  parametros.add(elementoActual);
                }
                swCargador.execute(parametros);
                construidoExistencias = true;
         }
         
      }
   }
   
      //---------------------------------------------------------------------
    private void jcbSeries_itemStateChanged(ItemEvent evt)
    {
      //JComboBox combo = (JComboBox) evt.getSource();
        //Item previo al cambio
     /* if (evt.getStateChange() == ItemEvent.DESELECTED)
         {
            //Nota: Si es la primera vez que se selecciona un item, este evento no se ejecuta.
            //porque no había nada previo seleccionado.
            //Se hizo cambio en la serie, por tanto las actividades ya no aplican.
            if (construidoActividades && bsActividades.getRowCount() > 0)
            {
                bsActividades.clear();
                construidoActividades = false;
            }
         }*/

        //Item después del cambio
         if (evt.getStateChange() == ItemEvent.SELECTED && evt.getItem() != null)
        {
            ControlInventario actual = (ControlInventario) evt.getItem();
            //jlbMantenimientoComo.setText(parent.obtenerEtiqueta(actual.getFk_inventario_variante_id().getMantenimientoComo()) + ":");
            jlbMantenimientoComo.setText(actual.getHasOneBienVariante().obtenerEtiquetaMantenimiento()+ ":");
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
            if (jtbPersonas.isEditing())
            {
               TableCellEditor editor = jtbPersonas.getCellEditor();
               if (editor != null)
                 editor.stopCellEditing();
            }
            
            if (esNuevo)
               parent.onAcceptNewElement(elementoActual, bsExistencias.getData());

            else 
            {
               elementoActual.setModified();
               parent.onAcceptModifyElement(elementoActual, bsExistencias.getData(), deleteds);
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
         if (bsPersonasEditor.getData().size() > 0)
         {
            Persona_tiene_Existencia nuevaPersona = new Persona_tiene_Existencia();
            nuevaPersona.setHasOneControlAlmacen(elementoActual);
            nuevaPersona.setIdPersona(bsPersonasEditor.getData().get(0).getId());
            nuevaPersona.setExistencia(1);
            rowIndex = bsExistencias.addRow(nuevaPersona);
            jtbPersonas.setRowSelectionInterval(rowIndex, rowIndex);
            //jtbDirecciones.editCellAt(rowIndex, 0);
         }
         
         else
            JOptionPane.showMessageDialog(rootPane, "Es necesario haber capturado personas", "Información", JOptionPane.WARNING_MESSAGE);
      }
      
      else if (evt.getSource() == jtoolEliminarAlmacen)
      {
         if ( JOptionPane.showConfirmDialog(this,String.format("Ha seleccionado %d registro(s) para ser eleiminado(s)\n¿Esta seguro de continuar?",  jtbPersonas.getSelectedRowCount()) ) == JOptionPane.OK_OPTION)
         {
            int[] rowsHandlers = jtbPersonas.getSelectedRows();
            List<Persona_tiene_Existencia> selecteds = bsExistencias.removeRows(rowsHandlers);

            for (Persona_tiene_Existencia elemento : selecteds )
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
            
            //Se esta modificando la llave foranea primaria.
            if (e.getColumn() == colPersona.getModelIndex())
               ((Persona_tiene_Existencia)data).getHasOnePersona().setModified();
         }
      }
   }
   
   //---------------------------------------------------------------------
   public void prepareForNew ()
   {
      elementoActual = new ControlAlmacen();
      elementoActual.setObservaciones("");
      esNuevo = true;
      this.setTitle("Nuevo");
      bsLotes.setData(parent.obtenerLotes());
      bsUbicaciones.setData(parent.obtenerUbicaciones());
   }
   
   //---------------------------------------------------------------------
   public void prepareForModify (ControlAlmacen elemento)
   {
      this.elementoActual = elemento;
      esNuevo = false;
      this.setTitle(String.format("Modificando %s", elemento.getSerie()));
     bsLotes.setData(parent.obtenerLotes());
      bsUbicaciones.setData(parent.obtenerUbicaciones());
      writeElement(elemento);
   }
   
   //---------------------------------------------------------------------
   public void writeElement (ControlAlmacen elemento)
   {
      jcbUbicacion.setSelectedItem(bsUbicaciones.getElementAt(bsUbicaciones.indexOfValue(elemento.getIdUbicacion())));
      jtfSerie.setText(elemento.getSerie());
      jtfCantidad.setValue(elemento.getCantidad());
      jcbLote.setSelectedItem(bsLotes.getElementAt(bsLotes.indexOfValue(elemento.getIdLote())));
      jftValorAcumulado.setValue(elemento.getValorAcumulado());
      //jlbMantenimientoComo.setText(parent.obtenerEtiqueta(elemento.getFk_control_almacen_id_inventario().getFk_inventario_variante_id().getMantenimientoComo()));
      jlbMantenimientoComo.setText(elemento.getHasOneControlInventario().getHasOneBienVariante().obtenerEtiquetaMantenimiento() + ":");
      jtfObservaciones.setText(elemento.getObservaciones());
   }
   
   //---------------------------------------------------------------------
   private boolean readElement (ControlAlmacen elemento)
   {
      boolean resultado= false;
      String mensaje = "";
      
      if (jcbUbicacion.getSelectedIndex() > 0 && jcbLote.getSelectedIndex() > 0 && !jtfCantidad.getText().isEmpty())
      {
         resultado = true;     
         elemento.setIdUbicacion((int)bsUbicaciones.getSelectedValue());
         if (!elemento.isSet())
            elemento.setModified();
         elemento.setSerie(jtfSerie.getText());
         elemento.setCantidad((float)jtfCantidad.getValue());
         elemento.setIdVariante(bsLotes.getCurrent().getIdVariante());
         elemento.setConsecutivo(bsLotes.getCurrent().getConsecutivo());
         elemento.getHasOneControlInventario().getHasOneBienVariante().setMantenimientoComo(bsLotes.getCurrent().getHasOneBienVariante().getMantenimientoComo());
         elemento.setObservaciones(jtfObservaciones.getText());
      }
      
      else
         mensaje = "Asegurese de proporcionar Ubicación, Lote y Cantidad";
      
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
   public void setDisplayData (List<Persona> listaPersonas, List<Persona_tiene_Existencia> listaExistencias)
   {
      bsPersonasEditor.setData(listaPersonas);
      bsPersonasRender.setData(listaPersonas);
      if (listaExistencias != null)
         bsExistencias.setData(listaExistencias);
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
        jlbUbicacion = new javax.swing.JLabel();
        jcbUbicacion = new javax.swing.JComboBox();
        jlbLote = new javax.swing.JLabel();
        jcbLote = new javax.swing.JComboBox();
        jlbCantidad = new javax.swing.JLabel();
        jtfCantidad = new JFormattedTextField(new Float(1.0000F));
        jlbSerie = new javax.swing.JLabel();
        jtfSerie = new javax.swing.JTextField();
        jlbMantenimientoComo = new javax.swing.JLabel();
        jftValorAcumulado = new JFormattedTextField(0);
        tabResponsables = new javax.swing.JPanel();
        jbarDirecciones = new javax.swing.JToolBar();
        jtoolNuevoAlmacen = new javax.swing.JButton();
        jtoolEliminarAlmacen = new javax.swing.JButton();
        jspDirecciones = new javax.swing.JScrollPane();
        jtbPersonas = new javax.swing.JTable();
        tabObservaciones = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtfObservaciones = new javax.swing.JTextArea();
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

        jpDatos1.setPreferredSize(new java.awt.Dimension(500, 158));
        jpDatos1.setLayout(new java.awt.GridLayout(5, 2, 3, 3));

        jlbUbicacion.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbUbicacion.setText("Ubicación:");
        jpDatos1.add(jlbUbicacion);

        jcbUbicacion.setModel(bsUbicaciones);
        jcbUbicacion.setRenderer(new POJOListCellRenderer<Ubicacion>());
        jpDatos1.add(jcbUbicacion);

        jlbLote.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbLote.setText("Lote y/o Marca:");
        jlbLote.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jpDatos1.add(jlbLote);

        jcbLote.setModel(bsLotes);
        jcbLote.setRenderer(new POJOListCellRenderer<ControlInventario>());
        jpDatos1.add(jcbLote);

        jlbCantidad.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbCantidad.setText("Cantidad:");
        jpDatos1.add(jlbCantidad);
        jpDatos1.add(jtfCantidad);

        jlbSerie.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbSerie.setText("Serie:");
        jpDatos1.add(jlbSerie);
        jpDatos1.add(jtfSerie);

        jlbMantenimientoComo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbMantenimientoComo.setText("Desconocido:");
        jpDatos1.add(jlbMantenimientoComo);
        jpDatos1.add(jftValorAcumulado);

        tabGeneral.add(jpDatos1);

        jtpBody.addTab("General", tabGeneral);

        tabResponsables.setName("tabResponsables"); // NOI18N
        tabResponsables.setLayout(new java.awt.BorderLayout());

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

        tabResponsables.add(jbarDirecciones, java.awt.BorderLayout.PAGE_START);

        jtbPersonas.setModel(bsExistencias);
        jtbPersonas.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jspDirecciones.setViewportView(jtbPersonas);

        tabResponsables.add(jspDirecciones, java.awt.BorderLayout.CENTER);

        jtpBody.addTab("Responsables", tabResponsables);

        tabObservaciones.setLayout(new java.awt.BorderLayout());

        jtfObservaciones.setColumns(20);
        jtfObservaciones.setRows(5);
        jScrollPane1.setViewportView(jtfObservaciones);

        tabObservaciones.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jtpBody.addTab("Observaciones", tabObservaciones);

        getContentPane().add(jtpBody, java.awt.BorderLayout.CENTER);

        jpAcciones.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jbCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/syswave/forms/resources/button-cross.png"))); // NOI18N
        jbCancelar.setText("Cancelar");
        jpAcciones.add(jbCancelar);

        jbAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/syswave/forms/resources/button-tick.png"))); // NOI18N
        jbAceptar.setText("Aceptar");
        jpAcciones.add(jbAceptar);

        getContentPane().add(jpAcciones, java.awt.BorderLayout.PAGE_END);

        jpEncabezado.setBackground(new java.awt.Color(233, 210, 20));
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

        setBounds(0, 0, 567, 434);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbAceptar;
    private javax.swing.JButton jbCancelar;
    private javax.swing.JToolBar jbarDirecciones;
    private javax.swing.JComboBox jcbLote;
    private javax.swing.JComboBox jcbUbicacion;
    private javax.swing.JFormattedTextField jftValorAcumulado;
    private javax.swing.JLabel jlbCantidad;
    private javax.swing.JLabel jlbIcono;
    private javax.swing.JLabel jlbLote;
    private javax.swing.JLabel jlbMantenimientoComo;
    private javax.swing.JLabel jlbSerie;
    private javax.swing.JLabel jlbUbicacion;
    private javax.swing.JPanel jpAcciones;
    private java.awt.Panel jpAreaMensajes;
    private javax.swing.JPanel jpDatos1;
    private javax.swing.JPanel jpEncabezado;
    private javax.swing.JScrollPane jspDirecciones;
    private javax.swing.JTable jtbPersonas;
    private javax.swing.JFormattedTextField jtfCantidad;
    private javax.swing.JTextArea jtfObservaciones;
    private javax.swing.JTextField jtfSerie;
    private javax.swing.JButton jtoolEliminarAlmacen;
    private javax.swing.JButton jtoolNuevoAlmacen;
    private javax.swing.JTabbedPane jtpBody;
    private javax.swing.JPanel tabGeneral;
    private javax.swing.JPanel tabObservaciones;
    private javax.swing.JPanel tabResponsables;
    // End of variables declaration//GEN-END:variables
//------------------------------------------------------------------
   private class ControlAlmacenDetailCargarSwingWorker extends SwingWorker<List<Object>, Void>
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
               case opLOAD_EXISTENCIAS:
                  arguments.add(personas.obtenerLista());
                  arguments.add(parent.obtenerPersonaExistencias((ControlAlmacen)arguments.get(1)));
                  break;
                  
               case opLOAD_PERSONAS:
                  arguments.add(personas.obtenerLista());
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
                  case opLOAD_EXISTENCIAS:
                     setDisplayData((List<Persona>)results.get(2), (List<Persona_tiene_Existencia>)results.get(3));
                     break;
                     
                  case opLOAD_PERSONAS:
                     setDisplayData((List<Persona>)results.get(1), null);
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
