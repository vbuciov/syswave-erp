package com.syswave.forms.miempresa;

import com.syswave.entidades.common.Entidad;
import com.syswave.entidades.configuracion.Localidad;
import com.syswave.entidades.miempresa.Persona;
import com.syswave.entidades.miempresa.PersonaDireccion;
import com.syswave.entidades.miempresa.PersonaIdentificador;
import com.syswave.entidades.miempresa.TipoPersona;
import com.syswave.entidades.miempresa.Valor;
import com.syswave.entidades.miempresa_vista.PersonaDetalladaVista;
import com.syswave.forms.databinding.LocalidadComboBoxModel;
import com.syswave.swing.models.POJOTableModel;
import com.syswave.forms.databinding.PersonaDetalladaTableModel;
import com.syswave.forms.databinding.PersonaDireccionesTableModel;
import com.syswave.forms.databinding.TipoPersonasComboBoxModel;
import com.syswave.forms.databinding.ValorComboBoxModel;
import com.syswave.swing.table.editors.LookUpComboBoxTableCellEditor;
import com.syswave.swing.table.renders.LookUpComboBoxTableCellRenderer;
import com.syswave.swing.renders.POJOListCellRenderer;
import com.syswave.logicas.configuracion.LocalidadesBusinessLogic;
import com.syswave.logicas.miempresa.PersonaDireccionesBusinessLogic;
import com.syswave.logicas.miempresa.PersonaIdentificadoresBusinessLogic;
import com.syswave.logicas.miempresa.PersonasBusinessLogic;
import com.syswave.logicas.miempresa.TipoPersonasBusinessLogic;
import com.toedter.calendar.JDateChooserCellEditor;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingWorker;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class JPersonaDireccionBusqueda extends javax.swing.JInternalFrame implements TableModelListener, PropertyChangeListener
{
   private final int opLOAD_ADDRESS = 0;
   private final int opLOAD_ADDREES_AND_LOCATIONS = 1;
   private final int opLOAD_PEOPLE = 2;
   private final int opLOAD_PEOPLE_AND_TYPES = 3;
   private final int opLOAD_CRITERIES = 4;
   private final int opSAVE_PEOPLE = 5;
   private final int opSAVE_ADDRESS = 6;

   PersonaDetalladaTableModel bsPersonas;
   PersonaDireccionesTableModel bsPersonaDireccion;
   TipoPersonasComboBoxModel bsTipoPersonasRender;
   TipoPersonasComboBoxModel bsTipoPersonasEditor;
   LocalidadComboBoxModel bsLocalidadComboRender;
   LocalidadComboBoxModel bsLocalidadEditor;
   LocalidadComboBoxModel bsLocalidadRender;

   ValorComboBoxModel bsTipoIdentificadorRender;
   ValorComboBoxModel bsTipoMedioRender;

   LocalidadesBusinessLogic localidades;
   PersonasBusinessLogic personas;
   TipoPersonasBusinessLogic tiposPersonas;
   PersonaIdentificadoresBusinessLogic personaIdentificadores;
   PersonaDireccionesBusinessLogic direcciones;

   TableColumn colLocalidad, colCalle, colColonia,
           colNoExterior, colNoInterior, colCP,
           colNombre, colApellido, colIdentificador,
           colTipoMedio, colDireccion, colClave, colFechaNacimiento,
           colTipoPersona;

   boolean construidoDirecciones, construidoPersonas, guardar_persona, guardar_direccion;
   PersonaBusquedaCargarSwingWorker swCargador;
   TipoPersona limitado;
   IPersonaBusquedaMediator parent;

   Persona personaActual;
   List<Persona> deleted_people;
   PersonaDireccion direccionActual;
   List<PersonaDireccion> deleted_address;
   Localidad localidadActual;
   TipoPersona relacionActual;

   //--------------------------------------------------------------------
   /**
    * Creates new form JPersonaBusqueda
    *
    * @param owner
    */
   public JPersonaDireccionBusqueda(IPersonaBusquedaMediator owner)
   {
      parent = owner;
      initAtributes(parent.getEsquema());
      initComponents();
      initEvents();
      stateNotify(jtpBody.getSelectedIndex());

      if (jtPersonas.getColumnCount() > 0)
      {
         colNombre = jtPersonas.getColumnModel().getColumn(0);
         colNombre.setPreferredWidth(100);

         colApellido = jtPersonas.getColumnModel().getColumn(1);
         colApellido.setPreferredWidth(100);

         colFechaNacimiento = jtPersonas.getColumnModel().getColumn(2);
         colFechaNacimiento.setCellEditor(new JDateChooserCellEditor());
         colFechaNacimiento.setPreferredWidth(100);

         colTipoPersona = jtPersonas.getColumnModel().getColumn(3);
         colTipoPersona.setCellRenderer(new LookUpComboBoxTableCellRenderer<TipoPersona>(bsTipoPersonasRender));
         colTipoPersona.setCellEditor(new LookUpComboBoxTableCellEditor<TipoPersona>(bsTipoPersonasEditor));
         colTipoPersona.setPreferredWidth(250);

         colIdentificador = jtPersonas.getColumnModel().getColumn(4);
         colIdentificador.setPreferredWidth(400);

         colTipoMedio = jtPersonas.getColumnModel().getColumn(5);
         colTipoMedio.setPreferredWidth(400);

         colDireccion = jtPersonas.getColumnModel().getColumn(6);
         colDireccion.setPreferredWidth(400);

         //Nota: Debido a los renders que se estan utilizando es necesario tener un renglón más alto.
         jtPersonas.setRowHeight((int) (jtPersonas.getRowHeight() * 1.5));
      }

      if (jtbDirecciones.getColumnCount() > 0)
      {
         colLocalidad = jtbDirecciones.getColumnModel().getColumn(0);
         colLocalidad.setCellRenderer(new LookUpComboBoxTableCellRenderer<Localidad>(bsLocalidadRender));
         colLocalidad.setCellEditor(new LookUpComboBoxTableCellEditor<Localidad>(bsLocalidadEditor));
         colLocalidad.setPreferredWidth(250);

         colCalle = jtbDirecciones.getColumnModel().getColumn(1);
         colCalle.setPreferredWidth(250);

         colColonia = jtbDirecciones.getColumnModel().getColumn(2);
         colColonia.setPreferredWidth(250);

         colCP = jtbDirecciones.getColumnModel().getColumn(3);
         colCP.setPreferredWidth(80);

         colNoExterior = jtbDirecciones.getColumnModel().getColumn(4);
         colNoExterior.setPreferredWidth(100);

         colNoInterior = jtbDirecciones.getColumnModel().getColumn(5);
         colNoInterior.setPreferredWidth(100);

         jtbDirecciones.setRowHeight((int) (jtbDirecciones.getRowHeight() * 1.5));
      }

   }

   //--------------------------------------------------------------------
   private void initAtributes(String esquema)
   {
      construidoDirecciones = false;
      construidoPersonas = false;
      guardar_persona = false;
      guardar_direccion = false;
      localidades = new LocalidadesBusinessLogic(esquema);
      personas = new PersonasBusinessLogic(esquema);
      tiposPersonas = new TipoPersonasBusinessLogic(esquema);
      personaIdentificadores = new PersonaIdentificadoresBusinessLogic(esquema);
      direcciones = new PersonaDireccionesBusinessLogic(esquema);
      deleted_address = new ArrayList<>();
      deleted_people = new ArrayList<>();

      bsPersonas = new PersonaDetalladaTableModel(new String[]
      {
         "Nombre(s):{nombres}", "Apellido(s):{apellidos}", "Nacimiento:{nacimiento}",
         "Relación de negocio:{id_tipo_persona}", "Identificador:{identificador_muestra}",
         "Medio:{medio_muestra}", "Dirección:{direccion_muestra}"
      });
      bsPersonas.addTableModelListener(this);

      bsPersonaDireccion = new PersonaDireccionesTableModel(new String[]
      {
         "Localidad:{id_localidad}", "Calle:{calle}", "Colonia:{colonia}",
         "C.P:{codigo_postal}", "No. Exterior:{no_exterior}", "No. Interior:{no_interior}"
      });
      bsPersonaDireccion.addTableModelListener(this);

      bsTipoPersonasRender = new TipoPersonasComboBoxModel();
      bsTipoPersonasEditor = new TipoPersonasComboBoxModel();

      bsLocalidadComboRender = new LocalidadComboBoxModel();

      bsLocalidadRender = new LocalidadComboBoxModel();
      bsLocalidadEditor = new LocalidadComboBoxModel();

      bsTipoIdentificadorRender = new ValorComboBoxModel();

      bsTipoMedioRender = new ValorComboBoxModel();
   }

   //---------------------------------------------------------------------
   private void initEvents()
   {
      ListSelectionModel jtPersonaSelectionModel = jtPersonas.getSelectionModel();

      ActionListener actionListenerManager
                     = new ActionListener()
      {
         @Override
         public void actionPerformed(ActionEvent evt)
         {
            finish_actionPerformed(evt);
         }
      };

      ActionListener navigationListenerManager
                     = new ActionListener()
      {
         @Override
         public void actionPerformed(ActionEvent evt)
         {
            navigation_actionPerformed(evt);
         }
      };

      ActionListener addressActionListener
                     = new ActionListener()
      {
         @Override
         public void actionPerformed(ActionEvent evt)
         {
            addressToolBar_actionPerformed(evt);
         }
      };

      ActionListener personasActionListener
                     = new ActionListener()
      {
         @Override
         public void actionPerformed(ActionEvent evt)
         {
            peopleToolBar_actionPerformed(evt);
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

      ChangeListener checkChangeListener = new ChangeListener()
      {

         @Override
         public void stateChanged(ChangeEvent e)
         {
            check_stateChanged(e);
         }
      };

      ListSelectionListener selecctionValueChangedListener = new ListSelectionListener()
      {
         @Override
         public void valueChanged(ListSelectionEvent e)
         {
            jtPersonas_selecctionValueChanged(e);
         }
      };

      KeyListener valueChangeKeyListener = new KeyAdapter()
      {
         @Override
         public void keyTyped(KeyEvent e)
         {
            reiniciarPersonas();
         }
      };

      ItemListener valueChangeItemListener = new ItemListener()
      {
         @Override
         public void itemStateChanged(ItemEvent e)
         {
            reiniciarPersonas();
            /*if (e.getStateChange() == ItemEvent.SELECTED) {
             Object item = e.getItem();
             // do something with object
             } */
         }
      };

      /*KeyListener comboBoxKeyListener = new KeyAdapter()
       {
       @Override
       public void keyTyped(KeyEvent e)
       {
       comboBoxKey_keyTyped(e);
       }
       };*/
      jbAceptar.addActionListener(actionListenerManager);
      jbCancelar.addActionListener(actionListenerManager);
      jbtnSiguiente.addActionListener(navigationListenerManager);
      jbtnAnterior.addActionListener(navigationListenerManager);
      jtpBody.addChangeListener(changeListenerManager);
      jtoolNuevoDireccion.addActionListener(addressActionListener);
      jtoolEliminarDireccion.addActionListener(addressActionListener);
      jtoolNuevaPersona.addActionListener(personasActionListener);
      jtoolEliminarPersona.addActionListener(personasActionListener);
      jchkGeneral.addChangeListener(checkChangeListener);
      jchkIdentificadores.addChangeListener(checkChangeListener);
      jchkDireccion.addChangeListener(checkChangeListener);
      jchkMedioContactos.addChangeListener(checkChangeListener);
      jtPersonaSelectionModel.addListSelectionListener(selecctionValueChangedListener);
      jtfNombres.addKeyListener(valueChangeKeyListener);
      jtfApellidos.addKeyListener(valueChangeKeyListener);
      jdcNacimiento.addKeyListener(valueChangeKeyListener);
      jtfValorIdentificador.addKeyListener(valueChangeKeyListener);
      jtfValorMedio.addKeyListener(valueChangeKeyListener);
      jtfCalle.addKeyListener(valueChangeKeyListener);
      jtfColonia.addKeyListener(valueChangeKeyListener);
      jtfCodigoPostal.addKeyListener(valueChangeKeyListener);
      jtfNoExterior.addKeyListener(valueChangeKeyListener);
      jtfNoInterior.addKeyListener(valueChangeKeyListener);
      jcbLocalidad.addItemListener(valueChangeItemListener);
      jcbTipoIdentificador.addItemListener(valueChangeItemListener);
      jcbTipoMedio.addItemListener(valueChangeItemListener);
      /*jcbLocalidad.addKeyListener(comboBoxKeyListener);
       jcbTipoIdentificador.addKeyListener(comboBoxKeyListener);
       jcbTipoMedio.addKeyListener(comboBoxKeyListener);*/
   }

   //--------------------------------------------------------------------
   /**
    * Descarta los cambios producidos durante la búsqueda.
    */
   private void reiniciarPersonas()
   {
      if (construidoPersonas && guardar_persona)
      {
         deleted_people.clear();
         guardar_persona = false;
      }

      construidoPersonas = false;
   }
   
   //--------------------------------------------------------------------
   /**
    * Descarta los cambios producidos durante la búsqueda.
    */
   private void reiniciarDirecciones()
   {
      if (construidoDirecciones && guardar_direccion)
      {
         deleted_address.clear();
         guardar_direccion =false;
      }
          
      construidoDirecciones = false;
   }

   //--------------------------------------------------------------------
   /**
    * Especifica un tipo de persona base para restrigir el uso a dicha rama.
    */
   public void limitarTiposPersonas(TipoPersona limite)
   {
      limitado = limite;
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

   //-----------------------------------------------------------------------
   /**
    * Controla los botones de navegacion que se estan desplegando.
    */
   private void stateNotify(int index)
   {
      jbAceptar.setVisible(index == jtpBody.getTabCount() - 1);
      jbtnSiguiente.setVisible(!jbAceptar.isVisible());
      jbtnAnterior.setVisible(index > 0);
   }

   //---------------------------------------------------------------------
   /**
    * Carga las opciones de los Combo Boxes.
    */
   public void cargarCriteriosBusqueda()
   {
      List<Object> parametros = new ArrayList<Object>();
      swCargador = new PersonaBusquedaCargarSwingWorker();
      swCargador.addPropertyChangeListener(this);
      parametros.add(opLOAD_CRITERIES);
      showTaskHeader("Cargado recursos, espere un momento...", true);

      swCargador.execute(parametros);
   }

   //---------------------------------------------------------------------
   /**
    * Obtiene los criterios generales, que ha capturado el usuario.
    */
   private Persona obtenerGenerales()
   {
      Persona resultado = null;

      if (jchkGeneral.isSelected())
      {
         resultado = new Persona();

         resultado.setNombres(jtfNombres.getText());
         resultado.setApellidos(jtfApellidos.getText());
         resultado.setNacimiento(jdcNacimiento.getDate());
      }

      return resultado;
   }

   //---------------------------------------------------------------------
   /**
    * Obtiene el criterio de idenficador, que ha capturado el usuario.
    */
   private PersonaIdentificador obtenerIdentificador()
   {
      PersonaIdentificador resultado = null;

      if (jchkIdentificadores.isSelected())
      {
         resultado = new PersonaIdentificador();
         resultado.setClave(jtfValorIdentificador.getText());
         resultado.setIdTipoIdentificador((int) bsTipoIdentificadorRender.getSelectedValue());
      }

      return resultado;
   }

   //---------------------------------------------------------------------
   /**
    * Obtiene el criterio de Dirección, que ha capturado el usuario.
    */
   private PersonaDireccion obtenerDireccion()
   {
      PersonaDireccion resultado = null;

      if (jchkDireccion.isSelected())
      {
         resultado = new PersonaDireccion();
         resultado.setIdLocalidad((int) bsLocalidadComboRender.getSelectedValue());
         resultado.setCalle(jtfCalle.getText());
         resultado.setColonia(jtfColonia.getText());
         resultado.setCodigoPostal(jtfCodigoPostal.getText());
         resultado.setNoExterior(jtfNoExterior.getText());
         resultado.setNoInterior(jtfNoInterior.getText());
      }

      return resultado;
   }

   //---------------------------------------------------------------------
   /**
    * Obtiene el criterio de Medio de contacto, que ha capturado el usuario.
    */
   private PersonaIdentificador obtenerMedio()
   {
      PersonaIdentificador resultado = null;

      if (jchkMedioContactos.isSelected())
      {
         resultado = new PersonaIdentificador();
         resultado.setClave(jtfValorMedio.getText());
         resultado.setIdTipoIdentificador((int) bsTipoMedioRender.getSelectedValue());
      }

      return resultado;
   }

   //---------------------------------------------------------------------
   /**
    * Este evento ocurre al desplazarse por los diferentes TABS que comprenden
    * la busqueda.
    */
   private void bodyTabbed_stateChanged(ChangeEvent e)
   {
      int navigateTo = 0;
      String HeaderText = "";
      JTabbedPane pane = (JTabbedPane) e.getSource();
      tryStopDetailCellEditor(jtPersonas);
      tryStopDetailCellEditor(jtbDirecciones);
      
      if (pane.getSelectedComponent() == tabGeneral)
      {
         if (guardar_persona)
            navigateTo++;
         
         else if (guardar_direccion)
            navigateTo +=2;
      }

      else if (pane.getSelectedComponent() == tabPersonas)
      {
         
         if (guardar_persona)
         {
            if (JOptionPane.showConfirmDialog(this, "Es preciso guardar los cambios realizados para continuar\n¿Desea guardar los cambios realizados?", "Pregunta", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
               doSaveProcess();
            
            else
            {
               //guardar_persona = false; Ya lo hace el método reiniciarPersonar
               navigateTo--;
               HeaderText = "Cambios cancelados, comenzar de nuevo la busqueda";
               reiniciarPersonas();
            }
         }
         
         else if (guardar_direccion)
            navigateTo++;

         else if (!construidoPersonas)
            doLoadPeopleProcess();
      }

      else if (pane.getSelectedComponent() == tabDirecciones )
      {
         if (guardar_persona)
            navigateTo--;
         
         else if (guardar_direccion)
         {
            if (JOptionPane.showConfirmDialog(this, "Es preciso gudara los cambios realizados para continuar\n¿Desea guardar los cambios realizados?", "Pregunta", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
               doSaveAddressProcess();
            
            else
            {
               navigateTo-=2;
               HeaderText = "Cambios cancelados, comenzar de nueva la busqueda";
               reiniciarDirecciones();
            }
            
         }
        
         else if (!construidoDirecciones)
         {
            if (construidoPersonas)
            {
               int index = jtPersonas.getSelectedRow();

               if (index < 0)
               {
                  HeaderText = "Seleccione una persona";
                  navigateTo--;
               }

               else
                  doLoadAddressProcess(bsPersonas.getElementAt(index));
            }

            else
               navigateTo -= 2;
         }
      }

      else if (pane.getSelectedComponent() == tabResumen)
      {
         if (guardar_persona)
            navigateTo-=2;
         
         else if (guardar_direccion)
            navigateTo--;
   
         else if (construidoPersonas)
         {
            if (construidoDirecciones)
            {
               int index = jtbDirecciones.getSelectedRow();

               if (index < 0)
               {
                  HeaderText = "Seleccione la dirección que desea utilizar";
                  navigateTo--;
               }
               else
               {
                  showTaskHeader("Si la información es correcta, presione ACEPTAR", false);
                  generarResumen(bsPersonas.getElementAt(jtPersonas.getSelectedRow()), bsPersonaDireccion.getElementAt(index));
               }
            }
            else
               navigateTo--;
         }
         else
            navigateTo -= 3;
      }
      
      if (navigateTo != 0)
      {
         showTaskHeader(HeaderText, false);
         jtpBody.setSelectedIndex(jtpBody.getSelectedIndex() + navigateTo);
      }
      
      else
         stateNotify(jtpBody.getSelectedIndex());
   }

   //---------------------------------------------------------------------
   private void doLoadPeopleProcess()
   {
      if (swCargador == null || swCargador.isDone())
      {
         List<Object> parametros = new ArrayList<Object>();
         swCargador = new PersonaBusquedaCargarSwingWorker();
         swCargador.addPropertyChangeListener(this);
         if (bsTipoPersonasRender.getSize() > 0)
            parametros.add(opLOAD_PEOPLE);
         else
            parametros.add(opLOAD_PEOPLE_AND_TYPES);
         parametros.add(limitado);
         parametros.add(obtenerGenerales());
         parametros.add(obtenerIdentificador());
         parametros.add(obtenerMedio());
         parametros.add(obtenerDireccion());
         showTaskHeader("Buscando, espere un momento...", true);

         swCargador.execute(parametros);
         construidoPersonas = true;
      }
   }

   //---------------------------------------------------------------------
   private void doLoadAddressProcess(PersonaDetalladaVista element)
   {
      if (swCargador == null || swCargador.isDone())
      {
         List<Object> parametros = new ArrayList<Object>();
         swCargador = new PersonaBusquedaCargarSwingWorker();
         swCargador.addPropertyChangeListener(this);
         if (bsLocalidadRender.getSize() > 0)
            parametros.add(opLOAD_ADDRESS);

         else
            parametros.add(opLOAD_ADDREES_AND_LOCATIONS);
         PersonaDireccion filtro = new PersonaDireccion();
         filtro.setHasOnePersona(element);

         parametros.add(filtro);
         showTaskHeader("Buscando, espere un momento...", true);
         swCargador.execute(parametros);
         construidoDirecciones = true;
      }
   }

   //---------------------------------------------------------------------
   public void doSaveProcess()
   {
      int elementosTotales = 0;
      List<Persona> modificados = new ArrayList<>();
      List<Persona> agregados = new ArrayList<>();
      List<PersonaDetalladaVista> datos = bsPersonas.getData();

      //tryStopDetailCellEditor(jtPersonas);

      for (Persona elemento : datos)
      {
         if (elemento.isNew())
            agregados.add(elemento);

         else if (elemento.isModified())
            modificados.add(elemento);
      }

      elementosTotales = modificados.size() + agregados.size();

      if (elementosTotales > 0)
      {
         if (swCargador == null || swCargador.isDone())
         {
            List<Object> parametros = new ArrayList<Object>();
            swCargador = new PersonaBusquedaCargarSwingWorker();
            swCargador.addPropertyChangeListener(this);
            parametros.add(opSAVE_PEOPLE);
            parametros.add(agregados);
            parametros.add(modificados);
            swCargador.execute(parametros);
         }
      }
      else
         JOptionPane.showMessageDialog(this, "No existen cambios a guardar");

   }
   
   //---------------------------------------------------------------------
   public void doSaveAddressProcess()
   {
      int elementosTotales = 0;
      List<PersonaDireccion> modificados = new ArrayList<>();
      List<PersonaDireccion> agregados = new ArrayList<>();
      List<PersonaDireccion> datos = bsPersonaDireccion.getData();

      //tryStopDetailCellEditor(jtPersonas);

      for (PersonaDireccion elemento : datos)
      {
         if (elemento.isNew())
            agregados.add(elemento);

         else if (elemento.isModified())
            modificados.add(elemento);
      }

      elementosTotales = modificados.size() + agregados.size();

      if (elementosTotales > 0)
      {
         if (swCargador == null || swCargador.isDone())
         {
            List<Object> parametros = new ArrayList<Object>();
            swCargador = new PersonaBusquedaCargarSwingWorker();
            swCargador.addPropertyChangeListener(this);
            parametros.add(opSAVE_ADDRESS);
            parametros.add(agregados);
            parametros.add(modificados);
            swCargador.execute(parametros);
         }
      }
      else
         JOptionPane.showMessageDialog(this, "No existen cambios a guardar");

   }


   //---------------------------------------------------------------------
   private void check_stateChanged(ChangeEvent e)
   {
      if (swCargador == null || swCargador.isDone())
      {
         JCheckBox userComponent = (JCheckBox) e.getSource();

         if (userComponent == jchkGeneral)
            containerSetEnable(jpDatosGenerales, userComponent.isSelected());

         else if (userComponent == jchkIdentificadores)
            containerSetEnable(jpDatosIdentificadores, userComponent.isSelected());

         else if (userComponent == jchkDireccion)
            containerSetEnable(jpDatosDireccion, userComponent.isSelected());

         else if (userComponent == jchkMedioContactos)
            containerSetEnable(jpDatosMedioContacto, userComponent.isSelected());

         if (!userComponent.isSelected() && bsPersonaDireccion.getColumnCount() > 0)
            reiniciarPersonas();
      }
   }

   //---------------------------------------------------------------------
   /**
    * Genera un resumen de los elementos seleccionados como texto y lo muestra.
    */
   private void generarResumen(PersonaDetalladaVista personaSeleccionada, PersonaDireccion direccionSeleccionada)
   {
      personaActual = personaSeleccionada;
      direccionActual = direccionSeleccionada;
      localidadActual = bsLocalidadEditor.getElementAt(bsLocalidadEditor.indexOfValue(direccionSeleccionada.getIdLocalidad()));
      relacionActual = bsTipoPersonasEditor.getElementAt(bsTipoPersonasEditor.indexOfValue(personaActual.getId_tipo_persona()));

      StyleContext sc = new StyleContext();
      DefaultStyledDocument doc = new DefaultStyledDocument(sc);
      String text = String.format("%s\n %s\n %s\n %s\n Calle:%s %s, %s Colonia:%s, C.P:%s\n %s", personaSeleccionada.getNombreCompleto(),
                                  relacionActual.getNombre(),
                                  personaSeleccionada.getIdentificador_muestra(),
                                  personaSeleccionada.getMedio_muestra(),
                                  direccionSeleccionada.getCalle(),
                                  String.format("# %s", direccionSeleccionada.getNoExterior()),
                                  direccionSeleccionada.getNoInterior(),
                                  direccionSeleccionada.getColonia(),
                                  direccionSeleccionada.getCodigoPostal(),
                                  localidadActual.getNombre());

      Style heading2Style = sc.addStyle("Heading2", null);
      heading2Style.addAttribute(StyleConstants.Foreground, Color.red);
      heading2Style.addAttribute(StyleConstants.FontSize, new Integer(16));
      heading2Style.addAttribute(StyleConstants.FontFamily, "serif");
      heading2Style.addAttribute(StyleConstants.Bold, new Boolean(true));
      try
      {
         // Add the text to the document
         doc.insertString(0, text, null);
         // Finally, apply the style to the heading
         doc.setParagraphAttributes(0, 1, heading2Style, false);
      }
      catch (BadLocationException ex)
      {
         Logger.getLogger(JPersonaDireccionBusqueda.class.getName()).log(Level.SEVERE, null, ex);
      }

      jtpResumen.setStyledDocument(doc);
   }

   //---------------------------------------------------------------------
   /**
    * Habilita o deshabilita los componentes internos de un panel.
    */
   private void containerSetEnable(JPanel pane, boolean enable)
   {
      pane.setEnabled(enable);

      for (Component userUI : pane.getComponents())
      {
         userUI.setEnabled(enable);
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

            if (e.getSource() == bsPersonas)
               guardar_persona = true;
            
            else  if (e.getSource() == bsPersonaDireccion)
               guardar_direccion = true;
         }
      }
   }

   //---------------------------------------------------------------------
   /**
    * Este evento se ejecuta cuando el renglon seleccionado cambia
    */
   private void jtPersonas_selecctionValueChanged(ListSelectionEvent e)
   {
      /*String selectedData = null;

       int[] selectedRow = jtPersonas.getSelectedRows();
       int[] selectedColumns = jtPersonas.getSelectedColumns();

       for (int i = 0; i < selectedRow.length; i++) {
       for (int j = 0; j < selectedColumns.length; j++) {
       selectedData = (String) jtPersonas.getValueAt(selectedRow[i], selectedColumns[j]);
       }
       }
       System.out.println("Selected: " + selectedData);*/
      reiniciarDirecciones();

      while (bsPersonaDireccion.getRowCount() > 0)
      {
         bsPersonaDireccion.removeRow(0);
      }
   }

   //---------------------------------------------------------------------
   /**
    * Obsoleto, los combos no pueden detectar evento de teclado si no estan
    * editables. private void comboBoxKey_keyTyped(KeyEvent e) { JComboBox combo
    * = (JComboBox)e.getSource();
    *
    * if (!combo.isEditable() && e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
    * combo.setSelectedIndex(-1); } }
    */
   //---------------------------------------------------------------------
   private void finish_actionPerformed(ActionEvent evt)
   {
      Object sender = evt.getSource();

      if (sender == jbAceptar)
      {
         if (personaActual != null && direccionActual != null && localidadActual != null && relacionActual != null)
         {
            /*personaActual.setNombres(personaActual.getNombres());
            personaActual.setApellidos(personaActual.getApellidos());
            Nota: El receptor solo utiliza el campo nombre*/
            personaActual.setNombres(personaActual.getNombreCompleto());
            personaActual.setApellidos("");
            personaActual.setFk_persona_tipo(relacionActual);
            direccionActual.setHasOnePersona(personaActual);
            direccionActual.setHasOneLocalidad(localidadActual);
            parent.onAcceptNewElement(direccionActual);

            close();
         }
         else
            JOptionPane.showInternalMessageDialog(this, "No se ha seleccionado una dirección válida", "Error", JOptionPane.ERROR_MESSAGE);
      }

      else
         close();
   }

   //---------------------------------------------------------------------
   private void navigation_actionPerformed(ActionEvent evt)
   {
      Object sender = evt.getSource();

      if (sender == jbtnSiguiente)
         jtpBody.setSelectedIndex(jtpBody.getSelectedIndex() + 1);

      else
         jtpBody.setSelectedIndex(jtpBody.getSelectedIndex() - 1);
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
   private void addressToolBar_actionPerformed(ActionEvent evt)
   {
      int rowIndex;

      if (evt.getSource() == jtoolNuevoDireccion)
      {
         PersonaDireccion nuevaDireccion = new PersonaDireccion();
         nuevaDireccion.setHasOnePersona(bsPersonas.getElementAt(jtPersonas.getSelectedRow()));
         nuevaDireccion.setCodigoPostal(jtfCodigoPostal.getText());
         nuevaDireccion.setCalle(jtfCalle.getText());
         nuevaDireccion.setColonia(jtfColonia.getText());
         nuevaDireccion.setNoInterior(jtfNoInterior.getText());
         nuevaDireccion.setNoExterior(jtfNoExterior.getText());
         if (bsLocalidadComboRender.getSelectedValue() != null)
            nuevaDireccion.setIdLocalidad((int) bsLocalidadComboRender.getSelectedValue());
         rowIndex = bsPersonaDireccion.addRow(nuevaDireccion);
         jtbDirecciones.setRowSelectionInterval(rowIndex, rowIndex);
         jtbDirecciones.editCellAt(rowIndex, 0);
         guardar_direccion = true;
      }

      else if (evt.getSource() == jtoolEliminarDireccion)
      {
         if (JOptionPane.showConfirmDialog(this, String.format("Ha seleccionado %d registro(s) para ser eleiminado(s)\n¿Esta seguro de continuar?", jtbDirecciones.getSelectedRowCount())) == JOptionPane.OK_OPTION)
         {
            int[] rowsHandlers = jtbDirecciones.getSelectedRows();
            List<PersonaDireccion> selecteds = bsPersonaDireccion.removeRows(rowsHandlers);

            for (PersonaDireccion elemento : selecteds)
            {
               if (!elemento.isNew())
                  deleted_address.add(elemento);
            }
            
            revisarPorCambiosDireccion();
         }
      }
   }

   //---------------------------------------------------------------------
   private void peopleToolBar_actionPerformed(ActionEvent evt)
   {
      int rowIndex;

      if (evt.getSource() == jtoolNuevaPersona)
      {
         PersonaDetalladaVista nuevaPersona = new PersonaDetalladaVista();
         nuevaPersona.setNombres(jtfNombres.getText());
         nuevaPersona.setApellidos(jtfApellidos.getText());
         nuevaPersona.setNacimiento(jdcNacimiento.getDate());
         nuevaPersona.setIdentificador_muestra("");
         nuevaPersona.setMedio_muestra("");
         nuevaPersona.setDireccion_muestra("");
         //nuevaPersona.setActivo(true);
         rowIndex = bsPersonas.addRow(nuevaPersona);
         jtPersonas.setRowSelectionInterval(rowIndex, rowIndex);
         jtPersonas.editCellAt(rowIndex, 0);
         guardar_persona = true;
      }

      else if (evt.getSource() == jtoolEliminarPersona)
      {
         if (JOptionPane.showConfirmDialog(this, String.format("Ha seleccionado %d registro(s) para ser eleiminado(s)\n¿Esta seguro de continuar?", jtPersonas.getSelectedRowCount())) == JOptionPane.OK_OPTION)
         {
            int[] rowsHandlers = jtPersonas.getSelectedRows();
            List<PersonaDetalladaVista> selecteds = bsPersonas.removeRows(rowsHandlers);

            for (PersonaDetalladaVista elemento : selecteds)
            {
               if (!elemento.isNew())
                  deleted_people.add((Persona) elemento);
            }

            revisarPorCambiosPersona();
         }
      }
   }

   //---------------------------------------------------------------------
   /**
    * Actualiza el valor de la bandera guardar_persona.
    */
   private void revisarPorCambiosPersona()
   {
      List<PersonaDetalladaVista> selecteds;
      //Nota: Comprobamos si es necesario guardar cambios.
      guardar_persona = deleted_people.size() > 0;
      if (!guardar_persona)
      {
         selecteds = bsPersonas.getData();
         int i = 0;

         while (!guardar_persona && i < selecteds.size())
         {
            guardar_persona = selecteds.get(i++).isSet();
         }
      }
   }
   
   //---------------------------------------------------------------------
   private void revisarPorCambiosDireccion()
   {
      List<PersonaDireccion> selecteds;
      
      //Nota: Comprobamos si es necesario guardar cambios.
      guardar_direccion = deleted_address.size() > 0;
      if (!guardar_direccion)
      {
         selecteds = bsPersonaDireccion.getData();
         int i = 0;
         
         while (!guardar_direccion && i < selecteds.size())
            guardar_direccion = selecteds.get(i++).isSet();
      }
   }

   //---------------------------------------------------------------------
   public void close()
   {
      setVisible(false);
      dispose();
   }

   //----------------------------------------------------------------------
   public void setDisplayDataCriteries(List<Localidad> listaLocalidades, List<Valor> listaIdentificadores, List<Valor> listaMedios)
   {
      if (listaLocalidades != null)
      {
         bsLocalidadEditor.setData(listaLocalidades);
         bsLocalidadRender.setData(listaLocalidades);
         bsLocalidadComboRender.setData(listaLocalidades);
      }

      if (listaIdentificadores != null)
         bsTipoIdentificadorRender.setData(listaIdentificadores);

      if (listaMedios != null)
         bsTipoMedioRender.setData(listaMedios);
   }

   //----------------------------------------------------------------------
   public void setDisplayData(List<Localidad> listaLocalidades, List<PersonaDireccion> listaDirecciones)
   {
      if (listaLocalidades != null)
      {
         bsLocalidadEditor.setData(listaLocalidades);
         bsLocalidadRender.setData(listaLocalidades);
      }

      if (listaDirecciones != null)
         bsPersonaDireccion.setData(listaDirecciones);
   }

   //-----------------------------------------------------------------------
   public void setDisplayDataPeople(List<PersonaDetalladaVista> listaPersonas, List<TipoPersona> listaTipos)
   {
      if (listaTipos != null)
      {
         bsTipoPersonasRender.setData(listaTipos);
         bsTipoPersonasEditor.setData(listaTipos);
      }

      if (listaPersonas != null)
         bsPersonas.setData(listaPersonas);
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

        jtpBody = new javax.swing.JTabbedPane();
        tabGeneral = new javax.swing.JPanel();
        jpHoja1Datos = new javax.swing.JPanel();
        jchkGeneral = new javax.swing.JCheckBox();
        jpDatosGenerales = new javax.swing.JPanel();
        jlbNombre = new javax.swing.JLabel();
        jtfNombres = new javax.swing.JTextField();
        jlbApellidos = new javax.swing.JLabel();
        jtfApellidos = new javax.swing.JTextField();
        jlbNacimiento = new javax.swing.JLabel();
        jdcNacimiento = new com.toedter.calendar.JDateChooser();
        jchkIdentificadores = new javax.swing.JCheckBox();
        jpDatosIdentificadores = new javax.swing.JPanel();
        jcbTipoIdentificador = new javax.swing.JComboBox();
        jtfValorIdentificador = new javax.swing.JTextField();
        jpHoja2Datos = new javax.swing.JPanel();
        jchkDireccion = new javax.swing.JCheckBox();
        jpDatosDireccion = new javax.swing.JPanel();
        jlbLocalidad = new javax.swing.JLabel();
        jcbLocalidad = new javax.swing.JComboBox();
        jlbCalle = new javax.swing.JLabel();
        jtfCalle = new javax.swing.JTextField();
        jlbColonia = new javax.swing.JLabel();
        jtfColonia = new javax.swing.JTextField();
        jlbCodigoPostal = new javax.swing.JLabel();
        jtfCodigoPostal = new javax.swing.JTextField();
        jlbNoExterior = new javax.swing.JLabel();
        jtfNoExterior = new javax.swing.JTextField();
        jlbNoInterior = new javax.swing.JLabel();
        jtfNoInterior = new javax.swing.JTextField();
        jchkMedioContactos = new javax.swing.JCheckBox();
        jpDatosMedioContacto = new javax.swing.JPanel();
        jcbTipoMedio = new javax.swing.JComboBox();
        jtfValorMedio = new javax.swing.JTextField();
        tabPersonas = new javax.swing.JPanel();
        jbarPersonas = new javax.swing.JToolBar();
        jtoolNuevaPersona = new javax.swing.JButton();
        jtoolEliminarPersona = new javax.swing.JButton();
        jspPersonas = new javax.swing.JScrollPane();
        jtPersonas = new javax.swing.JTable();
        tabDirecciones = new javax.swing.JPanel();
        jbarDirecciones = new javax.swing.JToolBar();
        jtoolNuevoDireccion = new javax.swing.JButton();
        jtoolEliminarDireccion = new javax.swing.JButton();
        jspDirecciones = new javax.swing.JScrollPane();
        jtbDirecciones = new javax.swing.JTable();
        tabResumen = new javax.swing.JPanel();
        jspResumen = new javax.swing.JScrollPane();
        jtpResumen = new javax.swing.JTextPane();
        jpAcciones = new javax.swing.JPanel();
        jbtnAnterior = new javax.swing.JButton();
        jbtnSiguiente = new javax.swing.JButton();
        jbCancelar = new javax.swing.JButton();
        jbAceptar = new javax.swing.JButton();
        jpEncabezado = new javax.swing.JPanel();
        jlbIcono = new javax.swing.JLabel();
        jpAreaMensajes = new javax.swing.JPanel();
        jlbMensajes = new javax.swing.JLabel();
        jpbAvances = new javax.swing.JProgressBar();

        setClosable(true);
        setMaximizable(true);

        jtpBody.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
        jtpBody.setName(""); // NOI18N

        tabGeneral.setName("tabGeneral"); // NOI18N
        tabGeneral.setLayout(new java.awt.GridLayout(1, 2));

        jpHoja1Datos.setLayout(new java.awt.GridBagLayout());

        jchkGeneral.setSelected(true);
        jchkGeneral.setText("Utilizar generales");
        jchkGeneral.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jchkGeneral.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        jpHoja1Datos.add(jchkGeneral, gridBagConstraints);

        jpDatosGenerales.setBorder(javax.swing.BorderFactory.createTitledBorder("General"));
        jpDatosGenerales.setPreferredSize(new java.awt.Dimension(296, 148));
        jpDatosGenerales.setLayout(new java.awt.GridBagLayout());

        jlbNombre.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbNombre.setText("Nombre:");
        jlbNombre.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        jpDatosGenerales.add(jlbNombre, gridBagConstraints);

        jtfNombres.setName("jtfNombres"); // NOI18N
        jtfNombres.setPreferredSize(new java.awt.Dimension(250, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jpDatosGenerales.add(jtfNombres, gridBagConstraints);

        jlbApellidos.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbApellidos.setText("Apellidos:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        jpDatosGenerales.add(jlbApellidos, gridBagConstraints);

        jtfApellidos.setName("jtfApellidos"); // NOI18N
        jtfApellidos.setPreferredSize(new java.awt.Dimension(250, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jpDatosGenerales.add(jtfApellidos, gridBagConstraints);

        jlbNacimiento.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbNacimiento.setText("Nacimiento:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        jpDatosGenerales.add(jlbNacimiento, gridBagConstraints);

        jdcNacimiento.setPreferredSize(new java.awt.Dimension(250, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jpDatosGenerales.add(jdcNacimiento, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jpHoja1Datos.add(jpDatosGenerales, gridBagConstraints);

        jchkIdentificadores.setText("Utilizar identificador");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jpHoja1Datos.add(jchkIdentificadores, gridBagConstraints);

        jpDatosIdentificadores.setBorder(javax.swing.BorderFactory.createTitledBorder("Identificador"));
        jpDatosIdentificadores.setEnabled(false);
        jpDatosIdentificadores.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jcbTipoIdentificador.setModel(bsTipoIdentificadorRender);
        jcbTipoIdentificador.setEnabled(false);
        jcbTipoIdentificador.setPreferredSize(new java.awt.Dimension(120, 25));
        jcbTipoIdentificador.setRenderer(new POJOListCellRenderer<Valor>());
        jpDatosIdentificadores.add(jcbTipoIdentificador);

        jtfValorIdentificador.setEnabled(false);
        jtfValorIdentificador.setPreferredSize(new java.awt.Dimension(210, 25));
        jpDatosIdentificadores.add(jtfValorIdentificador);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jpHoja1Datos.add(jpDatosIdentificadores, gridBagConstraints);

        tabGeneral.add(jpHoja1Datos);

        jpHoja2Datos.setLayout(new java.awt.GridBagLayout());

        jchkDireccion.setText("Utilizar dirección");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jpHoja2Datos.add(jchkDireccion, gridBagConstraints);

        jpDatosDireccion.setBorder(javax.swing.BorderFactory.createTitledBorder("Dirección"));
        jpDatosDireccion.setEnabled(false);
        jpDatosDireccion.setLayout(new java.awt.GridBagLayout());

        jlbLocalidad.setText("Localidad:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        jpDatosDireccion.add(jlbLocalidad, gridBagConstraints);

        jcbLocalidad.setModel(bsLocalidadComboRender);
        jcbLocalidad.setEnabled(false);
        jcbLocalidad.setPreferredSize(new java.awt.Dimension(250, 25));
        jcbLocalidad.setRenderer(new POJOListCellRenderer<Localidad>());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jpDatosDireccion.add(jcbLocalidad, gridBagConstraints);

        jlbCalle.setText("Calle:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        jpDatosDireccion.add(jlbCalle, gridBagConstraints);

        jtfCalle.setEnabled(false);
        jtfCalle.setPreferredSize(new java.awt.Dimension(250, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jpDatosDireccion.add(jtfCalle, gridBagConstraints);

        jlbColonia.setText("Colonia:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        jpDatosDireccion.add(jlbColonia, gridBagConstraints);

        jtfColonia.setEnabled(false);
        jtfColonia.setPreferredSize(new java.awt.Dimension(250, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jpDatosDireccion.add(jtfColonia, gridBagConstraints);

        jlbCodigoPostal.setText("Código Postal:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        jpDatosDireccion.add(jlbCodigoPostal, gridBagConstraints);

        jtfCodigoPostal.setEnabled(false);
        jtfCodigoPostal.setPreferredSize(new java.awt.Dimension(105, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jpDatosDireccion.add(jtfCodigoPostal, gridBagConstraints);

        jlbNoExterior.setText("No. Exterior:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        jpDatosDireccion.add(jlbNoExterior, gridBagConstraints);

        jtfNoExterior.setEnabled(false);
        jtfNoExterior.setPreferredSize(new java.awt.Dimension(50, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jpDatosDireccion.add(jtfNoExterior, gridBagConstraints);

        jlbNoInterior.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbNoInterior.setText("No. Interior:");
        jlbNoInterior.setMinimumSize(new java.awt.Dimension(94, 15));
        jlbNoInterior.setPreferredSize(new java.awt.Dimension(94, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        jpDatosDireccion.add(jlbNoInterior, gridBagConstraints);

        jtfNoInterior.setEnabled(false);
        jtfNoInterior.setPreferredSize(new java.awt.Dimension(50, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jpDatosDireccion.add(jtfNoInterior, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jpHoja2Datos.add(jpDatosDireccion, gridBagConstraints);

        jchkMedioContactos.setText("Utilizar Medio Contacto");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jpHoja2Datos.add(jchkMedioContactos, gridBagConstraints);

        jpDatosMedioContacto.setBorder(javax.swing.BorderFactory.createTitledBorder("Medio Contacto"));
        jpDatosMedioContacto.setEnabled(false);
        jpDatosMedioContacto.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jcbTipoMedio.setModel(bsTipoMedioRender);
        jcbTipoMedio.setEnabled(false);
        jcbTipoMedio.setPreferredSize(new java.awt.Dimension(210, 25));
        jcbTipoMedio.setRenderer( new POJOListCellRenderer<Valor>());
        jpDatosMedioContacto.add(jcbTipoMedio);

        jtfValorMedio.setEnabled(false);
        jtfValorMedio.setPreferredSize(new java.awt.Dimension(120, 25));
        jpDatosMedioContacto.add(jtfValorMedio);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jpHoja2Datos.add(jpDatosMedioContacto, gridBagConstraints);

        tabGeneral.add(jpHoja2Datos);

        jtpBody.addTab("Criterios Busqueda", tabGeneral);

        tabPersonas.setName("tabDirecciones"); // NOI18N
        tabPersonas.setLayout(new java.awt.BorderLayout());

        jbarPersonas.setRollover(true);

        jtoolNuevaPersona.setText("Nuevo");
        jtoolNuevaPersona.setFocusable(false);
        jtoolNuevaPersona.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jtoolNuevaPersona.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbarPersonas.add(jtoolNuevaPersona);

        jtoolEliminarPersona.setText("Eliminar");
        jtoolEliminarPersona.setToolTipText("");
        jtoolEliminarPersona.setFocusable(false);
        jtoolEliminarPersona.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jtoolEliminarPersona.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbarPersonas.add(jtoolEliminarPersona);

        tabPersonas.add(jbarPersonas, java.awt.BorderLayout.PAGE_START);

        jtPersonas.setModel(bsPersonas);
        jtPersonas.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jspPersonas.setViewportView(jtPersonas);

        tabPersonas.add(jspPersonas, java.awt.BorderLayout.CENTER);

        jtpBody.addTab("Personas", tabPersonas);

        tabDirecciones.setName("tabDirecciones"); // NOI18N
        tabDirecciones.setLayout(new java.awt.BorderLayout());

        jbarDirecciones.setRollover(true);

        jtoolNuevoDireccion.setText("Nuevo");
        jtoolNuevoDireccion.setFocusable(false);
        jtoolNuevoDireccion.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jtoolNuevoDireccion.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbarDirecciones.add(jtoolNuevoDireccion);

        jtoolEliminarDireccion.setText("Eliminar");
        jtoolEliminarDireccion.setToolTipText("");
        jtoolEliminarDireccion.setFocusable(false);
        jtoolEliminarDireccion.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jtoolEliminarDireccion.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbarDirecciones.add(jtoolEliminarDireccion);

        tabDirecciones.add(jbarDirecciones, java.awt.BorderLayout.PAGE_START);

        jtbDirecciones.setModel(bsPersonaDireccion);
        jtbDirecciones.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jspDirecciones.setViewportView(jtbDirecciones);

        tabDirecciones.add(jspDirecciones, java.awt.BorderLayout.CENTER);

        jtpBody.addTab("Direcciones", tabDirecciones);

        tabResumen.setName("tabResumen"); // NOI18N
        tabResumen.setLayout(new java.awt.BorderLayout());

        jtpResumen.setEditable(false);
        jspResumen.setViewportView(jtpResumen);

        tabResumen.add(jspResumen, java.awt.BorderLayout.CENTER);

        jtpBody.addTab("Resumen", tabResumen);

        getContentPane().add(jtpBody, java.awt.BorderLayout.CENTER);

        jpAcciones.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jbtnAnterior.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/syswave/forms/resources/button-backward.png"))); // NOI18N
        jbtnAnterior.setMnemonic('A');
        jbtnAnterior.setText("Anterior");
        jpAcciones.add(jbtnAnterior);

        jbtnSiguiente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/syswave/forms/resources/button-forward.png"))); // NOI18N
        jbtnSiguiente.setMnemonic('s');
        jbtnSiguiente.setText("Siguiente");
        jpAcciones.add(jbtnSiguiente);

        jbCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/syswave/forms/resources/button-cross.png"))); // NOI18N
        jbCancelar.setMnemonic('c');
        jbCancelar.setText("Cancelar");
        jpAcciones.add(jbCancelar);

        jbAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/syswave/forms/resources/button-tick.png"))); // NOI18N
        jbAceptar.setMnemonic('e');
        jbAceptar.setText("Aceptar");
        jpAcciones.add(jbAceptar);

        getContentPane().add(jpAcciones, java.awt.BorderLayout.PAGE_END);

        jpEncabezado.setBackground(new java.awt.Color(223, 212, 99));
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

        jpAreaMensajes.setBackground(new java.awt.Color(223, 212, 99));
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

        setBounds(0, 0, 789, 500);
    }// </editor-fold>//GEN-END:initComponents

   //------------------------------------------------------------------
   private class PersonaBusquedaCargarSwingWorker extends SwingWorker<List<Object>, Void>
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
               case opLOAD_PEOPLE:
                  arguments.add(personas.buscarPersona((TipoPersona) arguments.get(1),
                                                       (Persona) arguments.get(2),
                                                       (PersonaIdentificador) arguments.get(3),
                                                       (PersonaIdentificador) arguments.get(4),
                                                       (PersonaDireccion) arguments.get(5)));
                  setProgress(50);
                  break;

               case opLOAD_PEOPLE_AND_TYPES:
                  TipoPersona buscar = (TipoPersona) arguments.get(1);
                  setProgress(10);
                  arguments.add(personas.buscarPersona(buscar,
                                                       (Persona) arguments.get(2),
                                                       (PersonaIdentificador) arguments.get(3),
                                                       (PersonaIdentificador) arguments.get(4),
                                                       (PersonaDireccion) arguments.get(5)));
                  setProgress(50);

                  if (buscar != null)
                     arguments.add(tiposPersonas.obtenerListaRamaHojas(buscar));
                  else
                     arguments.add(tiposPersonas.obtenerListaHojas());
                  break;

               case opLOAD_ADDRESS:
                  arguments.add(direcciones.obtenerLista((PersonaDireccion) arguments.get(1)));
                  break;

               case opLOAD_ADDREES_AND_LOCATIONS:
                  arguments.add(localidades.obtenerListaHojasUtilizadas());
                  arguments.add(direcciones.obtenerLista((PersonaDireccion) arguments.get(1)));
                  break;

               case opLOAD_CRITERIES:
                  arguments.add(localidades.obtenerListaHojasUtilizadas());
                  setProgress(30);
                  arguments.add(personaIdentificadores.obtenerTiposIdentificadorUtilizados());
                  setProgress(60);
                  arguments.add(personaIdentificadores.obtenerTiposMedioUtilizados());
                  setProgress(90);
                  break;
                  
               case opSAVE_PEOPLE:
                  List<Persona> adds = (List<Persona>)arguments.get(1);
                  List<Persona> modifieds = (List<Persona>)arguments.get(2);
                  if (adds.size() > 0)
                     arguments.add(personas.agregar(adds));
                  if (modifieds.size() > 0)
                     arguments.add(personas.actualizar(modifieds));
                  break;
                  
               case opSAVE_ADDRESS:
                  List<PersonaDireccion> addsAddress = (List<PersonaDireccion>)arguments.get(1);
                  List<PersonaDireccion> modifiedsAddress = (List<PersonaDireccion>)arguments.get(2);
                  if (addsAddress.size() > 0)
                     arguments.add(direcciones.agregar(addsAddress));
                  if (modifiedsAddress.size() > 0)
                     arguments.add(direcciones.actualizar(modifiedsAddress));
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

                  case opLOAD_PEOPLE:
                     if (personas.esCorrecto())
                     {
                        setDisplayDataPeople((List<PersonaDetalladaVista>) results.get(6), null);
                        showTaskHeader("Busqueda realizada con éxito", false);
                     }
                     else
                     {
                        showTaskHeader("", false);
                        JOptionPane.showMessageDialog(null, personas.getMensaje());

                     }
                     break;

                  case opLOAD_PEOPLE_AND_TYPES:
                     if (personas.esCorrecto())
                     {
                        setDisplayDataPeople((List<PersonaDetalladaVista>) results.get(6), (List<TipoPersona>) results.get(7));
                        showTaskHeader("Busqueda realizada con éxito", false);
                     }
                     else
                     {
                        if (tiposPersonas.esCorrecto())
                           setDisplayDataPeople(null, (List<TipoPersona>) results.get(7));
                        showTaskHeader("", false);
                        JOptionPane.showMessageDialog(null, personas.getMensaje());

                     }
                     break;

                  case opLOAD_ADDRESS:
                     if (direcciones.esCorrecto())
                     {
                        setDisplayData(null, (List<PersonaDireccion>) results.get(2));
                        showTaskHeader(String.format("Direcciones correspondientes a %s", ((PersonaDireccion) results.get(1)).getHasOnePersona().getNombreCompleto()), false);
                     }

                     else
                     {
                        showTaskHeader("", false);
                        JOptionPane.showMessageDialog(null, direcciones.getMensaje());
                     }
                     break;

                  case opLOAD_ADDREES_AND_LOCATIONS:
                     if (direcciones.esCorrecto())
                     {
                        setDisplayData((List<Localidad>) results.get(2), (List<PersonaDireccion>) results.get(3));
                        showTaskHeader(String.format("Direcciones correspondientes a %s", ((PersonaDireccion) results.get(1)).getHasOnePersona().getNombreCompleto()), false);
                     }
                     else
                     {
                        if (localidades.esCorrecto())
                          setDisplayData((List<Localidad>) results.get(2), null);
                        showTaskHeader("", false);
                        JOptionPane.showMessageDialog(null, direcciones.getMensaje());
                     }
                     break;

                  case opLOAD_CRITERIES:
                     if (personaIdentificadores.esCorrecto())
                     {
                        setDisplayDataCriteries((List<Localidad>) results.get(1), (List<Valor>) results.get(2), (List<Valor>) results.get(3));
                        showTaskHeader("", false);
                     }
                     else
                     {
                        showTaskHeader("", false);
                        JOptionPane.showMessageDialog(null, personas.getMensaje());

                     }
                     break;
                     
                   case opSAVE_PEOPLE:
                      if (personas.esCorrecto())
                      {
                        guardar_persona = false;
                        showTaskHeader("Cambios guardados correctamente", false);
                      }
                      
                      else
                         JOptionPane.showMessageDialog(null, personas.getMensaje());
                     break;
                      
                   case opSAVE_ADDRESS:
                      if (direcciones.esCorrecto())
                      {
                        guardar_direccion = false;
                        showTaskHeader("Cambios guardados correctamente", false);
                      }
                      
                      else
                         JOptionPane.showMessageDialog(null, direcciones.getMensaje());
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jbAceptar;
    private javax.swing.JButton jbCancelar;
    private javax.swing.JToolBar jbarDirecciones;
    private javax.swing.JToolBar jbarPersonas;
    private javax.swing.JButton jbtnAnterior;
    private javax.swing.JButton jbtnSiguiente;
    private javax.swing.JComboBox jcbLocalidad;
    private javax.swing.JComboBox jcbTipoIdentificador;
    private javax.swing.JComboBox jcbTipoMedio;
    private javax.swing.JCheckBox jchkDireccion;
    private javax.swing.JCheckBox jchkGeneral;
    private javax.swing.JCheckBox jchkIdentificadores;
    private javax.swing.JCheckBox jchkMedioContactos;
    private com.toedter.calendar.JDateChooser jdcNacimiento;
    private javax.swing.JLabel jlbApellidos;
    private javax.swing.JLabel jlbCalle;
    private javax.swing.JLabel jlbCodigoPostal;
    private javax.swing.JLabel jlbColonia;
    private javax.swing.JLabel jlbIcono;
    private javax.swing.JLabel jlbLocalidad;
    private javax.swing.JLabel jlbMensajes;
    private javax.swing.JLabel jlbNacimiento;
    private javax.swing.JLabel jlbNoExterior;
    private javax.swing.JLabel jlbNoInterior;
    private javax.swing.JLabel jlbNombre;
    private javax.swing.JPanel jpAcciones;
    private javax.swing.JPanel jpAreaMensajes;
    private javax.swing.JPanel jpDatosDireccion;
    private javax.swing.JPanel jpDatosGenerales;
    private javax.swing.JPanel jpDatosIdentificadores;
    private javax.swing.JPanel jpDatosMedioContacto;
    private javax.swing.JPanel jpEncabezado;
    private javax.swing.JPanel jpHoja1Datos;
    private javax.swing.JPanel jpHoja2Datos;
    private javax.swing.JProgressBar jpbAvances;
    private javax.swing.JScrollPane jspDirecciones;
    private javax.swing.JScrollPane jspPersonas;
    private javax.swing.JScrollPane jspResumen;
    private javax.swing.JTable jtPersonas;
    private javax.swing.JTable jtbDirecciones;
    private javax.swing.JTextField jtfApellidos;
    private javax.swing.JTextField jtfCalle;
    private javax.swing.JTextField jtfCodigoPostal;
    private javax.swing.JTextField jtfColonia;
    private javax.swing.JTextField jtfNoExterior;
    private javax.swing.JTextField jtfNoInterior;
    private javax.swing.JTextField jtfNombres;
    private javax.swing.JTextField jtfValorIdentificador;
    private javax.swing.JTextField jtfValorMedio;
    private javax.swing.JButton jtoolEliminarDireccion;
    private javax.swing.JButton jtoolEliminarPersona;
    private javax.swing.JButton jtoolNuevaPersona;
    private javax.swing.JButton jtoolNuevoDireccion;
    private javax.swing.JTabbedPane jtpBody;
    private javax.swing.JTextPane jtpResumen;
    private javax.swing.JPanel tabDirecciones;
    private javax.swing.JPanel tabGeneral;
    private javax.swing.JPanel tabPersonas;
    private javax.swing.JPanel tabResumen;
    // End of variables declaration//GEN-END:variables
}
