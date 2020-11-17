package com.syswave.forms.miempresa;

import com.orbital.AutoCompleteDocument;
import com.syswave.entidades.common.Entidad;
import com.syswave.entidades.configuracion.Localidad;
import com.syswave.entidades.miempresa.PersonaAtributo;
import com.syswave.entidades.miempresa.PersonaDireccion;
import com.syswave.entidades.miempresa.PersonaEducacion;
import com.syswave.entidades.miempresa.PersonaFoto;
import com.syswave.entidades.miempresa.PersonaIdentificador;
import com.syswave.entidades.miempresa.PersonaTieneIncidencia;
import com.syswave.entidades.miempresa_vista.Personal;
import com.syswave.entidades.miempresa_vista.PersonalTieneFamiliar;
import com.syswave.entidades.miempresa.TipoPersona;
import com.syswave.entidades.miempresa.Valor;
import com.syswave.entidades.miempresa_vista.PersonaDetalle_5FN;
import com.syswave.entidades.miempresa_vista.PersonaIdentificadorVista;
import com.syswave.swing.models.POJOTableModel;
import com.syswave.forms.databinding.LocalidadComboBoxModel;
import com.syswave.forms.databinding.PersonaAtributosTableModel;
import com.syswave.forms.databinding.PersonaDetalles5FNTableModel;
import com.syswave.forms.databinding.PersonaDireccionesTableModel;
import com.syswave.forms.databinding.PersonaEducacionesTableModel;
import com.syswave.forms.databinding.PersonaIdentificadoresTableModel;
import com.syswave.forms.databinding.PersonaIdentificadoresVistaTableModel;
import com.syswave.forms.databinding.PersonaTieneIncidenciasTableModel;
import com.syswave.forms.databinding.PersonalComboBoxModel;
import com.syswave.forms.databinding.PersonalTieneFamiliarTableModel;
import com.syswave.forms.databinding.TipoPersonasComboBoxModel;
import com.syswave.forms.databinding.ValorComboBoxModel;
import com.syswave.swing.table.editors.LookUpComboBoxTableCellEditor;
import com.syswave.swing.table.editors.SpinnerTableCellEditor;
import com.syswave.swing.FileTypeFilter;
import com.syswave.swing.table.renders.DateTimeTableCellRenderer;
import com.syswave.swing.table.renders.LookUpComboBoxTableCellRenderer;
import com.syswave.swing.renders.POJOListCellRenderer;
import com.syswave.logicas.configuracion.LocalidadesBusinessLogic;
import com.toedter.calendar.JDateChooserCellEditor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.MimetypesFileTypeMap;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingWorker;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import org.imgscalr.Scalr;

/**
 *
 * @author Gilberto Aarón Jimenez Montelongo
 */
public class JPersonalDetailView extends javax.swing.JInternalFrame implements TableModelListener
{

   private final int opLOAD_ADDRESS = 0;
   private final int opLOAD_LOCALIDADES = 1;
   private final int opLOAD_IDENTIFICADORES = 2;
   private final int opLOAD_MEDIOS_CONTACTO = 3;
   private final int opLOAD_TIPOS_MEDIO = 4;
   private final int opLOAD_MINIATURAS = 5;
   private final int opLOAD_FORMACION = 6;
   private final int opLOAD_ATRIBUTOS = 7;
   private final int opLOAD_PADECIMIENTOS = 8;
   private final int opLOAD_INCIDENCIAS = 9;
   private final int opLOAD_FAMILIARES = 10;
   private final int opLOAD_PERSONAL = 11;

   private Personal elementoActual;
   private IPersonalMediator owner;
   private TipoPersonasComboBoxModel bsTipoPersona;
   private PersonaDireccionesTableModel bsPersonaDireccion;
   private PersonaIdentificadoresVistaTableModel bsPersonaIdentificador;
   private PersonaIdentificadoresTableModel bsMediosContacto;
   private PersonaEducacionesTableModel bsFormacion;
   private PersonaAtributosTableModel bsAtributos;
   private PersonaAtributosTableModel bsPadecimientos;
   private PersonaTieneIncidenciasTableModel bsIncidencias;
   private PersonalTieneFamiliarTableModel bsFamiliar;
   private LocalidadComboBoxModel bsLocalidadRender;
   private LocalidadComboBoxModel bsLocalidadEditor;
   private ValorComboBoxModel bsTipoMedioRender;
   private ValorComboBoxModel bsTipoMedioEditor;
   private ValorComboBoxModel bsTipoEducacionRender;
   private ValorComboBoxModel bsTipoEducacionEditor;
   private ValorComboBoxModel bsTipoAtributoRender;
   private ValorComboBoxModel bsTipoAtributoEditor;
   private PersonaDetalles5FNTableModel bsCaracteristicas;
   private ValorComboBoxModel bsTipoPadecimientoRender;
   private ValorComboBoxModel bsTipoPadecimientoEditor;
   private ValorComboBoxModel bsEstadoCivilRender;
   private ValorComboBoxModel bsGrupoSanguineoRender;
   private ValorComboBoxModel bsTipoIncidenciaRender;
   private ValorComboBoxModel bsTipoIncidenciaEditor;
   private ValorComboBoxModel bsPrototipoFamiliarEditor;
   private ValorComboBoxModel bsPrototipoFamiliarRender;
   private PersonalComboBoxModel bsPersonalEditor;
   private PersonalComboBoxModel bsPersonalRender;

   private PersonaFoto foto, foto_borrada;

   private boolean esNuevo, construidoDirecciones, construirIdentificadores, construirMedios,
           construirFormacion, construirAtributos, constuirPadecimientos,
           construirIncidencias, construirFamiliares;
   private LocalidadesBusinessLogic localidades;
   private TableColumn colLocalidad, colValor, colLlave, colDescripcion,
           colTipoMedio, colClave, colNombreFormacion, colTituloFormacion,
           colFechaInicio, colFechaFin, colCursando, colTipoEducacion, colNombreAtributo,
           colEsTipo, colNombreMedicacion, colTipoMedicacion, colTratamiento,
           colDescMedicacion, colValorCaracteristica, colLlaveCaracteristica,
           colDescripcionCaracteristica, colFechaIncidencia, colHoraIncidencia, colTipoIncidencia,
           colFamiliar, colTipoFamiliar;

   private PersonalDetailCargarSwingWorker swCargador;
   private List<PersonaDireccion> deleteds;
   private List<PersonaIdentificador> medios_deleteds;
   private List<PersonaEducacion> formacion_deleteds;
   private List<PersonaAtributo> atributos_deleteds;
   private List<PersonaTieneIncidencia> indicencias_deleteds;
   private List<PersonalTieneFamiliar> familiar_deleteds;
   private String no_empleado_new;

   //---------------------------------------------------------------------
   /**
    * Creates new form PersonaFrame
    *
    * @param owner
    */
   public JPersonalDetailView(IPersonalMediator parent)
   {
      initAtributes(parent.getEsquema());
      initComponents();
      initEvents();
      this.owner = parent;
      
      jfcImagenes.setFileFilter(new FileTypeFilter(".jpg", "JPG"));
      jfcImagenes.setFileFilter(new FileTypeFilter(".gif", "GIF"));
      jfcImagenes.setFileFilter(new FileTypeFilter(".png", "PNG"));

      if (jtbDirecciones.getColumnCount() > 0)
      {
         colLocalidad = jtbDirecciones.getColumnModel().getColumn(0);
         colLocalidad.setCellRenderer(new LookUpComboBoxTableCellRenderer<Localidad>(bsLocalidadRender));
         colLocalidad.setCellEditor(new LookUpComboBoxTableCellEditor<Localidad>(bsLocalidadEditor));
         colLocalidad.setPreferredWidth(250);

         jtbDirecciones.setRowHeight((int) (jtbDirecciones.getRowHeight() * 1.5));
      }

      if (jtbIdentificadores.getColumnCount() > 0)
      {
         colValor = jtbIdentificadores.getColumnModel().getColumn(0);
         colValor.setPreferredWidth(200);

         colLlave = jtbIdentificadores.getColumnModel().getColumn(1);
         colLlave.setPreferredWidth(100);

         colDescripcion = jtbIdentificadores.getColumnModel().getColumn(2);
         colDescripcion.setPreferredWidth(280);
      }

      if (jtbMedios.getColumnCount() > 0)
      {
         colTipoMedio = jtbMedios.getColumnModel().getColumn(0);
         colTipoMedio.setCellRenderer(new LookUpComboBoxTableCellRenderer<Valor>(bsTipoMedioRender));
         colTipoMedio.setCellEditor(new LookUpComboBoxTableCellEditor<Valor>(bsTipoMedioEditor));
         colTipoMedio.setPreferredWidth(150);

         jtbMedios.setRowHeight((int) (jtbMedios.getRowHeight() * 1.5));
      }

      if (jtbFormacion.getColumnCount() > 0)
      {
         colNombreFormacion = jtbFormacion.getColumnModel().getColumn(0);
         colNombreFormacion.setPreferredWidth(250);

         colTituloFormacion = jtbFormacion.getColumnModel().getColumn(1);
         colTituloFormacion.setPreferredWidth(250);

         colFechaInicio = jtbFormacion.getColumnModel().getColumn(2);
         colFechaInicio.setCellEditor(new JDateChooserCellEditor());
         colFechaInicio.setPreferredWidth(120);

         colFechaFin = jtbFormacion.getColumnModel().getColumn(3);
         colFechaFin.setPreferredWidth(120);
         colFechaFin.setCellEditor(new JDateChooserCellEditor());

         colCursando = jtbFormacion.getColumnModel().getColumn(4);
         colCursando.setPreferredWidth(80);

         colTipoEducacion = jtbFormacion.getColumnModel().getColumn(5);
         colTipoEducacion.setCellRenderer(new LookUpComboBoxTableCellRenderer<Valor>(bsTipoEducacionRender));
         colTipoEducacion.setCellEditor(new LookUpComboBoxTableCellEditor<Valor>(bsTipoEducacionEditor));
         colTipoEducacion.setPreferredWidth(120);

         jtbFormacion.setRowHeight((int) (jtbFormacion.getRowHeight() * 1.5));
      }

      if (jtbAtributos.getColumnCount() > 0)
      {
         colNombreAtributo = jtbAtributos.getColumnModel().getColumn(0);
         colNombreAtributo.setPreferredWidth(250);

         colEsTipo = jtbAtributos.getColumnModel().getColumn(1);
         colEsTipo.setCellRenderer(new LookUpComboBoxTableCellRenderer<Valor>(bsTipoAtributoRender));
         colEsTipo.setCellEditor(new LookUpComboBoxTableCellEditor<Valor>(bsTipoAtributoEditor));
         colEsTipo.setPreferredWidth(250);

         jtbAtributos.setRowHeight((int) (jtbAtributos.getRowHeight() * 1.5));
      }

      if (jtbPersonaDetalles.getColumnCount() > 0)
      {
         colValorCaracteristica = jtbPersonaDetalles.getColumnModel().getColumn(0);
         colValorCaracteristica.setPreferredWidth(80);

         colLlaveCaracteristica = jtbPersonaDetalles.getColumnModel().getColumn(1);
         colLlaveCaracteristica.setPreferredWidth(100);

         colDescripcionCaracteristica = jtbPersonaDetalles.getColumnModel().getColumn(2);
         colDescripcionCaracteristica.setPreferredWidth(280);
      }

      if (jtbPadecimientos.getColumnCount() > 0)
      {
         colTipoMedicacion = jtbPadecimientos.getColumnModel().getColumn(0);
         colTipoMedicacion.setCellRenderer(new LookUpComboBoxTableCellRenderer<Valor>(bsTipoPadecimientoRender));
         colTipoMedicacion.setCellEditor(new LookUpComboBoxTableCellEditor<Valor>(bsTipoPadecimientoEditor));
         colTipoMedicacion.setPreferredWidth(85);

         colNombreMedicacion = jtbPadecimientos.getColumnModel().getColumn(1);
         colNombreMedicacion.setPreferredWidth(250);

         colTratamiento = jtbPadecimientos.getColumnModel().getColumn(2);
         colTratamiento.setPreferredWidth(250);

         colDescMedicacion = jtbPadecimientos.getColumnModel().getColumn(3);
         colDescMedicacion.setPreferredWidth(250);

         jtbPadecimientos.setRowHeight((int) (jtbAtributos.getRowHeight() * 1.5));
      }

      if (jtbIncidencia.getColumnCount() > 0)
      {
         jtbIncidencia.setRowHeight((int) (jtbIncidencia.getRowHeight() * 1.5));

         colFechaIncidencia = jtbIncidencia.getColumnModel().getColumn(0);
         colFechaIncidencia.setCellRenderer(new DateTimeTableCellRenderer("dd/MM/yyyy")); // estado de reposo
         colFechaIncidencia.setCellEditor(new JDateChooserCellEditor());
         colFechaIncidencia.setPreferredWidth(125);

         colHoraIncidencia = jtbIncidencia.getColumnModel().getColumn(1);
         colHoraIncidencia.setCellRenderer(new DateTimeTableCellRenderer("HH:mm"));
         colHoraIncidencia.setCellEditor(new SpinnerTableCellEditor(new SpinnerDateModel(new Date(), null, null, java.util.Calendar.HOUR_OF_DAY), "HH:mm"));
         colHoraIncidencia.setPreferredWidth(125);

         colTipoIncidencia = jtbIncidencia.getColumnModel().getColumn(2);
         colTipoIncidencia.setCellRenderer(new LookUpComboBoxTableCellRenderer(bsTipoIncidenciaRender));
         colTipoIncidencia.setCellEditor(new LookUpComboBoxTableCellEditor(bsTipoIncidenciaEditor));
         colTipoIncidencia.setPreferredWidth(200);
      }

      if (jtbFamiliar.getColumnCount() > 0)
      {
         colFamiliar = jtbFamiliar.getColumnModel().getColumn(0);
         colFamiliar.setCellRenderer(new LookUpComboBoxTableCellRenderer<Personal>(bsPersonalRender));
         colFamiliar.setCellEditor(new LookUpComboBoxTableCellEditor<Personal>(bsPersonalEditor));
         colFamiliar.setPreferredWidth(300);

         colTipoFamiliar = jtbFamiliar.getColumnModel().getColumn(1);
         colTipoFamiliar.setCellRenderer(new LookUpComboBoxTableCellRenderer(bsPrototipoFamiliarRender));
         colTipoFamiliar.setCellEditor(new LookUpComboBoxTableCellEditor(bsPrototipoFamiliarEditor));

         jtbFamiliar.setRowHeight((int) (jtbFamiliar.getRowHeight() * 1.5));
      }

      AutoCompleteDocument.enable(jcbTipoPersona);
   }

   //---------------------------------------------------------------------
   private void initAtributes(String esquema)
   {
      localidades = new LocalidadesBusinessLogic("configuracion");

      esNuevo = true;
      construidoDirecciones = false;
      construirIdentificadores = false;
      construirMedios = false;
      construirFormacion = false;
      construirAtributos = false;
      construirIncidencias = false;
      construirFamiliares = false;
      bsTipoPersona = new TipoPersonasComboBoxModel();
      bsLocalidadRender = new LocalidadComboBoxModel();
      bsLocalidadEditor = new LocalidadComboBoxModel();
      bsTipoMedioRender = new ValorComboBoxModel();
      bsTipoMedioEditor = new ValorComboBoxModel();
      bsTipoEducacionRender = new ValorComboBoxModel();
      bsTipoEducacionEditor = new ValorComboBoxModel();
      bsTipoAtributoRender = new ValorComboBoxModel();
      bsTipoAtributoEditor = new ValorComboBoxModel();
      bsTipoPadecimientoRender = new ValorComboBoxModel();
      bsTipoPadecimientoEditor = new ValorComboBoxModel();
      bsEstadoCivilRender = new ValorComboBoxModel();
      bsGrupoSanguineoRender = new ValorComboBoxModel();
      bsTipoIncidenciaRender = new ValorComboBoxModel();
      bsTipoIncidenciaEditor = new ValorComboBoxModel();
      bsPrototipoFamiliarEditor = new ValorComboBoxModel();
      bsPrototipoFamiliarRender = new ValorComboBoxModel();
      bsPersonalEditor = new PersonalComboBoxModel();
      bsPersonalRender = new PersonalComboBoxModel();
      foto_borrada = null;

      bsPersonaDireccion = new PersonaDireccionesTableModel(new String[]
      {
         "Localidad:{id_localidad}", "Calle:{calle}", "Colonia:{colonia}",
         "C.P:{codigo_postal}", "No. Exterior:{no_exterior}",
         "No. Interior:{no_interior}"
      });
      bsPersonaDireccion.addTableModelListener(this);

      bsPersonaIdentificador = new PersonaIdentificadoresVistaTableModel(new String[]
      {
         "Valor:{clave}", "Tipo:{llave}", "Descripción:{descripcion}"
      });
      bsPersonaIdentificador.addTableModelListener(this);

      bsMediosContacto = new PersonaIdentificadoresTableModel(new String[]
      {
         "Tipo:{id_tipo_identificador}", "Valor:{clave}", "Observación:{nota}"
      });
      bsMediosContacto.addTableModelListener(this);

      bsFormacion = new PersonaEducacionesTableModel(new String[]
      {
         "Institución/Taller/Curso:{nombre}", "Titulo obtenido:{titulo}",
         "Fecha Inicio:{fecha_inicio}", "Fecha Terminación:{fecha_fin}",
         "Cursando:{es_cursando}", "Tipo:{es_tipo}"
      });
      bsFormacion.addTableModelListener(this);

      bsAtributos = new PersonaAtributosTableModel(new String[]
      {
         "Nombre:{nombre}", "Tipo:{es_tipo}"
      });
      bsAtributos.addTableModelListener(this);

      bsCaracteristicas = new PersonaDetalles5FNTableModel(new String[]
      {
         "Valor:{valor}", "Tipo:{llave}", "Descripción:{descripcion}"
      });
      bsCaracteristicas.addTableModelListener(this);

      bsPadecimientos = new PersonaAtributosTableModel(new String[]
      {
         "Tipo:{es_tipo}", "Nombre:{nombre}", "Tratamiento:{tratamiento}",
         "Descripción:{descripcion}"
      });
      bsPadecimientos.addTableModelListener(this);

      bsIncidencias = new PersonaTieneIncidenciasTableModel(new String[]
      {
         "Fecha:{fecha}", "Hora:{hora}", "Incidencia:{tipo_incidencia}",
         "Observaciones:{observaciones}"
      });
      bsIncidencias.addTableModelListener(this);

      bsFamiliar = new PersonalTieneFamiliarTableModel(new String[]
      {
         "Familiar:{id_founded}",
         "Tipo:{prototype}"
      });
      bsFamiliar.addTableModelListener(this);

      deleteds = new ArrayList<>();
      medios_deleteds = new ArrayList<>();
      formacion_deleteds = new ArrayList<>();
      atributos_deleteds = new ArrayList<>();
      indicencias_deleteds = new ArrayList<>();
      familiar_deleteds = new ArrayList<>();

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

      ActionListener addressActionListener
                     = new ActionListener()
      {
         @Override
         public void actionPerformed(ActionEvent evt)
         {
            addressToolBar_actionPerformed(evt);
         }
      };

      ActionListener identifiersActionListener
                     = new ActionListener()
      {
         @Override
         public void actionPerformed(ActionEvent evt)
         {
            identifiersToolBar_actionPerformed(evt);
         }
      };

      ActionListener formationActionListener
                     = new ActionListener()
      {
         @Override
         public void actionPerformed(ActionEvent evt)
         {
            formationToolBar_actionPerformed(evt);
         }
      };

      ActionListener attributesActionListener
                     = new ActionListener()
      {
         @Override
         public void actionPerformed(ActionEvent evt)
         {
            attributesToolBar_actionPerformed(evt);
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

      ActionListener imagesActionListener
                     = new ActionListener()
      {
         @Override
         public void actionPerformed(ActionEvent evt)
         {
            images_actionPerformed(evt);
         }
      };

      ActionListener diseasesActionListener = new ActionListener()
      {
         @Override
         public void actionPerformed(ActionEvent e)
         {
            diseasesToolBar_actionPerformed(e);
         }
      };

      ActionListener incidenciasActionListener = new ActionListener()
      {

         @Override
         public void actionPerformed(ActionEvent e)
         {
            incidenciasToolBar_actionPerformed(e);
         }
      };

      ActionListener famiActionListener = new ActionListener()
      {

         @Override
         public void actionPerformed(ActionEvent e)
         {
            familiaresToolBar_actionPerformed(e);
         }
      };

      jbAceptar.addActionListener(actionListenerManager);
      jbCancelar.addActionListener(actionListenerManager);
      jtpBody.addChangeListener(changeListenerManager);
      jtoolNuevoDireccion.addActionListener(addressActionListener);
      jtoolEliminarDireccion.addActionListener(addressActionListener);
      jtoolNuevoTelefono.addActionListener(identifiersActionListener);
      jtoolEliminarTelefono.addActionListener(identifiersActionListener);
      jtoolNuevaFormacion.addActionListener(formationActionListener);
      jtoolEliminarFormacion.addActionListener(formationActionListener);
      jbtnNuevaImagen.addActionListener(imagesActionListener);
      jbtnEliminarImagen.addActionListener(imagesActionListener);
      jtoolNuevoAtributo.addActionListener(attributesActionListener);
      jtoolEliminarAtributo.addActionListener(attributesActionListener);
      jtoolNuevoPadecimiento.addActionListener(diseasesActionListener);
      jtoolEliminarPadecimiento.addActionListener(diseasesActionListener);
      jtoolNuevoIncidencia.addActionListener(incidenciasActionListener);
      jtoolEliminarIncidencia.addActionListener(incidenciasActionListener);
      jbNuevoFamiliar.addActionListener(famiActionListener);
      jbEliminarFamiliar.addActionListener(famiActionListener);
   }

   //---------------------------------------------------------------------
    /*
    *  DETECTA EL CAMBIO ENTRE TABS
    */
   private void bodyTabbed_stateChanged(ChangeEvent e)
   {
      if (swCargador == null || swCargador.isDone())
      {

         JTabbedPane pane = (JTabbedPane) e.getSource();
         if (pane.getSelectedComponent() == tabDirecciones && !construidoDirecciones)
         {
            List<Object> parametros = new ArrayList<Object>();
            swCargador = new PersonalDetailCargarSwingWorker();
            if (esNuevo)
               parametros.add(opLOAD_LOCALIDADES);
            else
            {
               parametros.add(opLOAD_ADDRESS);
               parametros.add(elementoActual);
            }
            swCargador.execute(parametros);
            construidoDirecciones = true;
         }

         else if (pane.getSelectedComponent() == tabIdentificadores && !construirIdentificadores)
         {
            List<Object> parametros = new ArrayList<Object>();
            swCargador = new PersonalDetailCargarSwingWorker();

            parametros.add(opLOAD_IDENTIFICADORES);
            parametros.add(elementoActual);

            swCargador.execute(parametros);
            construirIdentificadores = true;
         }

         else if (pane.getSelectedComponent() == tabMediosContacto && !construirMedios)
         {
            List<Object> parametros = new ArrayList<Object>();
            swCargador = new PersonalDetailCargarSwingWorker();

            if (esNuevo)
               parametros.add(opLOAD_TIPOS_MEDIO);

            else
            {
               parametros.add(opLOAD_MEDIOS_CONTACTO);
               parametros.add(elementoActual);
            }

            swCargador.execute(parametros);
            construirMedios = true;
         }

         else if (pane.getSelectedComponent() == tabFormacion && !construirFormacion)
         {
            if (!esNuevo)
            {
               List<Object> parametros = new ArrayList<Object>();
               swCargador = new PersonalDetailCargarSwingWorker();

               parametros.add(opLOAD_FORMACION);
               parametros.add(elementoActual);

               swCargador.execute(parametros);
            }
            construirFormacion = true;
         }

         else if (pane.getSelectedComponent() == tabAtributos && !construirAtributos)
         {
            if (!esNuevo)
            {
               List<Object> parametros = new ArrayList<Object>();
               swCargador = new PersonalDetailCargarSwingWorker();

               parametros.add(opLOAD_ATRIBUTOS);
               parametros.add(elementoActual);

               swCargador.execute(parametros);
            }
            construirAtributos = true;
         }

         else if (pane.getSelectedComponent() == tabMedicaciones && !constuirPadecimientos)
         {
            crearTipoPadecimientos();

            if (!esNuevo)
            {
               List<Object> parametros = new ArrayList<Object>();
               swCargador = new PersonalDetailCargarSwingWorker();

               parametros.add(opLOAD_PADECIMIENTOS);
               parametros.add(elementoActual);

               swCargador.execute(parametros);
            }
            constuirPadecimientos = true;
         }

         else if (pane.getSelectedComponent() == tabIncidencias && !construirIncidencias)
         {
            crearTiposIncidencias();

            if (!esNuevo)
            {
               List<Object> parametros = new ArrayList<Object>();
               swCargador = new PersonalDetailCargarSwingWorker();

               parametros.add(opLOAD_INCIDENCIAS);
               parametros.add(elementoActual);

               swCargador.execute(parametros);
            }

            construirIncidencias = true;
         }

         else if (pane.getSelectedComponent() == tabFamiliar && !construirFamiliares)
         {
            crearTipoFamiliar();

            List<Object> parametros = new ArrayList<Object>();
            swCargador = new PersonalDetailCargarSwingWorker();
            Personal excluir = new Personal(elementoActual);
            excluir.acceptChanges();
            excluir.setSearchOnlyByPrimaryKey(true);

            if (!esNuevo)
            {
               PersonalTieneFamiliar filtro = new PersonalTieneFamiliar();
               filtro.setIdSearched(elementoActual.getIdPersona());

               parametros.add(opLOAD_FAMILIARES);
               parametros.add(filtro);
            }

            else
            {
               parametros.add(opLOAD_PERSONAL);
            }

            parametros.add(excluir);
            swCargador.execute(parametros);

            construirFamiliares = true;
         }
      }
   }

   //---------------------------------------------------------------------
   private void finish_actionPerformed(ActionEvent evt)
   {
      Object sender = evt.getSource();

      if (sender == jbAceptar)
      {
         tryStopDetailCellEditor(jtbIdentificadores); //Requerimos antes de leer que ya no este en edicicón 
         if (readElement(elementoActual))
         {
            tryStopDetailCellEditor(jtbDirecciones);
            tryStopDetailCellEditor(jtbMedios);
            tryStopDetailCellEditor(jtbFormacion);
            tryStopDetailCellEditor(jtbAtributos);
            tryStopDetailCellEditor(jtbPadecimientos);
            tryStopDetailCellEditor(jtbPersonaDetalles);
            tryStopDetailCellEditor(jtbIncidencia);
            tryStopDetailCellEditor(jtbFamiliar);

            bsAtributos.getData().addAll(bsPadecimientos.getData());
            if (esNuevo)
               owner.onAcceptNewElement(elementoActual,
                                        bsPersonaDireccion.getData(),
                                        bsPersonaIdentificador.getData(),
                                        bsMediosContacto.getData(),
                                        foto,
                                        bsFormacion.getData(),
                                        bsAtributos.getData(),
                                        bsCaracteristicas.getData(),
                                        bsIncidencias.getData(),
                                        bsFamiliar.getData()
               );

            else
            {

               owner.onAcceptModifyElement(elementoActual,
                                           bsPersonaDireccion.getData(),
                                           deleteds,
                                           bsPersonaIdentificador.getData(),
                                           bsMediosContacto.getData(),
                                           medios_deleteds, foto, foto_borrada,
                                           bsFormacion.getData(),
                                           formacion_deleteds,
                                           bsAtributos.getData(),
                                           atributos_deleteds,
                                           bsCaracteristicas.getData(),
                                           bsIncidencias.getData(),
                                           indicencias_deleteds,
                                           bsFamiliar.getData(),
                                           familiar_deleteds);
            }

            close();
         }
      }

      else
         close();
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
   private void images_actionPerformed(ActionEvent evt)
   {
      if (evt.getSource() == jbtnNuevaImagen)
      {
         jfcImagenes.setDialogTitle("Escoja una imagen");
         if (foto != null && jfcImagenes.showOpenDialog(this) == JFileChooser.APPROVE_OPTION && jfcImagenes.getSelectedFile() != null)
         {
            try
            {
               BufferedImage realPicture = ImageIO.read(jfcImagenes.getSelectedFile());
               BufferedImage thumbnail
                             = Scalr.resize(realPicture, Scalr.Method.SPEED,
                                            Scalr.Mode.FIT_TO_WIDTH, 180, 180,
                                            Scalr.OP_ANTIALIAS);
               //thumbnail = createResizedCopy( ImageIO.read(jfcImagenes.getSelectedFile()), 200, 200, true);

               if (!foto.isSet())
               {
                  if (foto.getIdPersona() != elementoActual.getIdPersona())
                  {
                     foto.setHasOnePersona(elementoActual.getHasOnePersona());
                     foto.setNew();
                  }

                  else
                     foto.setModified();
               }
               foto.setAcho(realPicture.getWidth());
               foto.setAlto(realPicture.getHeight());
               foto.setLongitud((int) jfcImagenes.getSelectedFile().length());
               foto.setImagen(extractBytes(realPicture));
               foto.setMiniatura(convertBytes(thumbnail));
               foto.setFormato(new MimetypesFileTypeMap().getContentType(jfcImagenes.getSelectedFile()));
               foto.setObservacion("Main picture");
               jlbImagen.setText("");
               jlbImagen.setIcon(new ImageIcon(thumbnail));
            }
            catch (IOException ex)
            {
               Logger.getLogger(JPersonalDetailView.class.getName()).log(Level.SEVERE, null, ex);
            }
         }
      }

      else if (evt.getSource() == jbtnEliminarImagen)
      {
         if (foto != null && JOptionPane.showConfirmDialog(this, "Será eliminada la imagen\n¿Esta seguro de continuar?") == JOptionPane.OK_OPTION)
         {
            if (foto.getIdPersona() == elementoActual.getIdPersona())
               foto_borrada = foto;
            jlbImagen.setIcon(null);
            jlbImagen.setText("<Sin imagen>");
         }
      }
   }

   //---------------------------------------------------------------------
   private byte[] extractBytes(BufferedImage value)
   {
      WritableRaster raster = value.getRaster();
      DataBufferByte data = (DataBufferByte) raster.getDataBuffer();

      return data.getData();
   }

   //---------------------------------------------------------------------
   public byte[] convertBytes(BufferedImage value)
   {
      try
      {
         ByteArrayOutputStream baos = new ByteArrayOutputStream();
         ImageIO.write(value, "jpg", baos);
         byte[] bytes = baos.toByteArray();

         return bytes;
      }
      catch (IOException ex)
      {
         Logger.getLogger(JPersonalDetailView.class.getName()).log(Level.SEVERE, null, ex);
      }

      return null;
   }

    //---------------------------------------------------------------------
    /*BufferedImage createResizedCopy(Image originalImage, 
    int scaledWidth, int scaledHeight, 
    boolean preserveAlpha)
    {
    System.out.println("resizing...");
    int imageType = preserveAlpha ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
    BufferedImage scaledBI = new BufferedImage(scaledWidth, scaledHeight, imageType);
    Graphics2D g = scaledBI.createGraphics();
    if (preserveAlpha) {
    g.setComposite(AlphaComposite.Src);
    }
    g.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null); 
    g.dispose();
    return scaledBI;
    }*/
   //---------------------------------------------------------------------
   private void addressToolBar_actionPerformed(ActionEvent evt)
   {
      int rowIndex;

      if (evt.getSource() == jtoolNuevoDireccion)
      {
         PersonaDireccion nuevaDireccion = new PersonaDireccion();
         nuevaDireccion.setHasOnePersona(elementoActual.getHasOnePersona());
         nuevaDireccion.setCodigoPostal("");
         nuevaDireccion.setCalle("");
         nuevaDireccion.setColonia("");
         nuevaDireccion.setNoInterior("");
         nuevaDireccion.setNoExterior("");
         rowIndex = bsPersonaDireccion.addRow(nuevaDireccion);
         jtbDirecciones.setRowSelectionInterval(rowIndex, rowIndex);
         //jtbDirecciones.editCellAt(rowIndex, 0);
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
                  deleteds.add(elemento);
            }
         }
      }
   }

   //---------------------------------------------------------------------
   private void identifiersToolBar_actionPerformed(ActionEvent evt)
   {
      int rowIndex;

      if (evt.getSource() == jtoolNuevoTelefono)
      {
         PersonaIdentificador nuevoTelefono = new PersonaIdentificador();
         nuevoTelefono.setHasOnePersona(elementoActual.getHasOnePersona());
         nuevoTelefono.setClave("");
         nuevoTelefono.setNota("");

         rowIndex = bsMediosContacto.addRow(nuevoTelefono);
         jtbMedios.setRowSelectionInterval(rowIndex, rowIndex);
         //jtbDirecciones.editCellAt(rowIndex, 0);
      }

      else if (evt.getSource() == jtoolEliminarTelefono)
      {
         if (JOptionPane.showConfirmDialog(this, String.format("Ha seleccionado %d registro(s) para ser eleiminado(s)\n¿Esta seguro de continuar?", jtbMedios.getSelectedRowCount())) == JOptionPane.OK_OPTION)
         {
            int[] rowsHandlers = jtbMedios.getSelectedRows();
            List<PersonaIdentificador> selecteds = bsMediosContacto.removeRows(rowsHandlers);

            for (PersonaIdentificador elemento : selecteds)
            {
               if (!elemento.isNew())
                  medios_deleteds.add(elemento);
            }
         }
      }
   }

   //---------------------------------------------------------------------
   private void formationToolBar_actionPerformed(ActionEvent evt)
   {
      int rowIndex;

      if (evt.getSource() == jtoolNuevaFormacion)
      {
         PersonaEducacion nuevaCuenta = new PersonaEducacion();
         nuevaCuenta.setHasOnePersona(elementoActual.getHasOnePersona());
         nuevaCuenta.setNombre("");
         nuevaCuenta.setTitulo("Certificado");
         nuevaCuenta.setFechaInicio(new Date());
         nuevaCuenta.setFechFin(nuevaCuenta.getFechaInicio());
         nuevaCuenta.setCursando(false);
         nuevaCuenta.setTipo(0);

         rowIndex = bsFormacion.addRow(nuevaCuenta);
         jtbFormacion.setRowSelectionInterval(rowIndex, rowIndex);
         //jtbFormacion.editCellAt(rowIndex, 0);
      }

      else if (evt.getSource() == jtoolEliminarFormacion)
      {
         if (JOptionPane.showConfirmDialog(this, String.format("Ha seleccionado %d registro(s) para ser eleiminado(s)\n¿Esta seguro de continuar?", jtbFormacion.getSelectedRowCount())) == JOptionPane.OK_OPTION)
         {
            int[] rowsHandlers = jtbFormacion.getSelectedRows();
            List<PersonaEducacion> selecteds = bsFormacion.removeRows(rowsHandlers);

            for (PersonaEducacion elemento : selecteds)
            {
               if (!elemento.isNew())
                  formacion_deleteds.add(elemento);
            }
         }
      }
   }

   //---------------------------------------------------------------------
   private void attributesToolBar_actionPerformed(ActionEvent evt)
   {
      int rowIndex;

      if (evt.getSource() == jtoolNuevoAtributo)
      {
         PersonaAtributo nuevoAtributo = new PersonaAtributo();
         nuevoAtributo.setHasOnePersona(elementoActual.getHasOnePersona());
         nuevoAtributo.setNombre("");
         nuevoAtributo.setEsTipo(0);
         nuevoAtributo.setTratamiento("");
         nuevoAtributo.setDescripcion("");

         rowIndex = bsAtributos.addRow(nuevoAtributo);
         jtbAtributos.setRowSelectionInterval(rowIndex, rowIndex);
         //jtbAtributos.editCellAt(rowIndex, 0);
      }

      else if (evt.getSource() == jtoolEliminarAtributo)
      {
         if (JOptionPane.showConfirmDialog(this, String.format("Ha seleccionado %d registro(s) para ser eleiminado(s)\n¿Esta seguro de continuar?", jtbAtributos.getSelectedRowCount())) == JOptionPane.OK_OPTION)
         {
            int[] rowsHandlers = jtbAtributos.getSelectedRows();
            List<PersonaAtributo> selecteds = bsAtributos.removeRows(rowsHandlers);

            for (PersonaAtributo elemento : selecteds)
            {
               if (!elemento.isNew())
                  atributos_deleteds.add(elemento);
            }
         }
      }
   }

   //---------------------------------------------------------------------
   private void diseasesToolBar_actionPerformed(ActionEvent evt)
   {
      int rowIndex;

      if (evt.getSource() == jtoolNuevoPadecimiento)
      {
         PersonaAtributo nuevoAtributo = new PersonaAtributo();
         nuevoAtributo.setHasOnePersona(elementoActual.getHasOnePersona());
         nuevoAtributo.setNombre("");
         nuevoAtributo.setEsTipo(0);
         nuevoAtributo.setTratamiento("");
         nuevoAtributo.setDescripcion("");

         rowIndex = bsPadecimientos.addRow(nuevoAtributo);
         jtbPadecimientos.setRowSelectionInterval(rowIndex, rowIndex);
         //jtbAtributos.editCellAt(rowIndex, 0);
      }

      else if (evt.getSource() == jtoolEliminarPadecimiento)
      {
         if (JOptionPane.showConfirmDialog(this, String.format("Ha seleccionado %d registro(s) para ser eleiminado(s)\n¿Esta seguro de continuar?", jtbPadecimientos.getSelectedRowCount())) == JOptionPane.OK_OPTION)
         {
            int[] rowsHandlers = jtbPadecimientos.getSelectedRows();
            List<PersonaAtributo> selecteds = bsPadecimientos.removeRows(rowsHandlers);

            for (PersonaAtributo elemento : selecteds)
            {
               if (!elemento.isNew())
                  atributos_deleteds.add(elemento);
            }
         }
      }
   }

   //---------------------------------------------------------------------
   private void incidenciasToolBar_actionPerformed(ActionEvent evt)
   {
      int rowIndex;

      if (evt.getSource() == jtoolNuevoIncidencia)
      {
         PersonaTieneIncidencia nuevaIncidencia = new PersonaTieneIncidencia();
         nuevaIncidencia.setHasOnePersona(elementoActual);
         nuevaIncidencia.setFecha(new Date());
         nuevaIncidencia.setHora(new Date());
         nuevaIncidencia.setTipo_incidencia(0);
         nuevaIncidencia.setObservaciones("");

         rowIndex = bsIncidencias.addRow(nuevaIncidencia);
         jtbIncidencia.setRowSelectionInterval(rowIndex, rowIndex);
      }

      else if (evt.getSource() == jtoolEliminarIncidencia)
      {
         if (JOptionPane.showConfirmDialog(this, String.format("Ha seleccionado %d registro(s) para ser eleiminado(s)\n¿Esta seguro de continuar?", jtbIncidencia.getSelectedRowCount())) == JOptionPane.OK_OPTION)
         {
            int[] rowsHandlers = jtbIncidencia.getSelectedRows();
            List<PersonaTieneIncidencia> selecteds = bsIncidencias.removeRows(rowsHandlers);

            for (PersonaTieneIncidencia elemento : selecteds)
            {
               if (!elemento.isNew())
                  indicencias_deleteds.add(elemento);
            }
         }
      }
   }

   //---------------------------------------------------------------------
   private void familiaresToolBar_actionPerformed(ActionEvent evt)
   {
      int rowIndex;

      if (evt.getSource() == jbNuevoFamiliar)
      {
         PersonalTieneFamiliar nuevoFamiliar = new PersonalTieneFamiliar();
         nuevoFamiliar.setFk_searched(elementoActual);
         nuevoFamiliar.setPrototype(0);

         rowIndex = bsFamiliar.addRow(nuevoFamiliar);
         jtbFamiliar.setRowSelectionInterval(rowIndex, rowIndex);
      }

      else if (evt.getSource() == jbEliminarFamiliar)
      {
         if (JOptionPane.showConfirmDialog(this, String.format("Ha seleccionado %d registro(s) para ser eleiminado(s)\n¿Esta seguro de continuar?", jtbFamiliar.getSelectedRowCount())) == JOptionPane.OK_OPTION)
         {
            int[] rowsHandlers = jtbFamiliar.getSelectedRows();
            List<PersonalTieneFamiliar> selecteds = bsFamiliar.removeRows(rowsHandlers);

            for (PersonalTieneFamiliar elemento : selecteds)
            {
               if (!elemento.isNew())
                  familiar_deleteds.add(elemento);
            }
         }
      }
   }

   //------------------------------------------------------------------------------------
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
            {
               if (model == bsPersonaIdentificador)
               {
                  if (((PersonaIdentificadorVista) data).getIdPersona() != elementoActual.getIdPersona())
                  {
                     ((PersonaIdentificadorVista) data).setHasOnePersona(elementoActual.getHasOnePersona());
                     data.setNew();
                  }

                  else
                     data.setModified();

                  if (((PersonaIdentificadorVista) data).getLlave().equals("Clave"))
                     no_empleado_new = ((PersonaIdentificadorVista) data).getClave();
               }

               else if (model == bsCaracteristicas)
               {
                  if (((PersonaDetalle_5FN) data).getIdPersona() != elementoActual.getIdPersona())
                  {
                     ((PersonaDetalle_5FN) data).setHasOnePersona(elementoActual.getHasOnePersona());
                     data.setNew();
                  }

                  else
                     data.setModified();
               }

               else
                  data.setModified();
            }
         }
      }
   }

   //---------------------------------------------------------------------
   public void prepareForNew()
   {
      elementoActual = new Personal();
      elementoActual.setIdPersona(0); //Id de prueba
      esNuevo = true;
      this.setTitle("Nuevo usuario");
      bsTipoPersona.setData(owner.obtenerTipoPersonas());
      bsEstadoCivilRender.setData(owner.obtenerEstadoCivil());
      crearGruposSanguineos();
      crearTipoAtributos();
      //writeElement(elementoActual);
      foto = new PersonaFoto();
      foto.acceptChanges(); //No se ha especificado la foto
      no_empleado_new = "";
   }

   //---------------------------------------------------------------------
   public void prepareForModify(Personal elemento)
   {
      this.elementoActual = elemento;
      esNuevo = false;
      this.setTitle(String.format("Modificando %s", elemento.getNombreCompleto()));
      bsTipoPersona.setData(owner.obtenerTipoPersonas());
      bsEstadoCivilRender.setData(owner.obtenerEstadoCivil());
      crearGruposSanguineos();
      crearTipoAtributos();
      writeElement(elemento);
      cargarImagenes(elemento);
      crearTipoEducacion();
      no_empleado_new = elemento.getNo_empleado();
   }

   //---------------------------------------------------------------------
   private void cargarImagenes(Personal elemento)
   {
      if (swCargador == null || swCargador.isDone())
      {
         List<Object> parametros = new ArrayList<Object>();
         swCargador = new PersonalDetailCargarSwingWorker();
         //swCargador.addPropertyChangeListener(this);

         parametros.add(opLOAD_MINIATURAS);
         parametros.add(elemento);

         //showTaskHeader("Cargando recursos, espero un momento....", true);
         swCargador.execute(parametros);
      }
   }

   //---------------------------------------------------------------------
   /**
    * Escribe la informacion del elemento en los componentes de la interfaz
    */
   public void writeElement(Personal elemento)
   {
      jtfNombres.setText(elemento.getNombres());
      jtfApellidos.setText(elemento.getApellidos());
      jchkActivo.setSelected(elemento.esActivo());
      jdcNacimiento.setDate(elemento.getNacimiento());
      jcbTipoPersona.setSelectedItem(bsTipoPersona.getElementAt(bsTipoPersona.indexOfValue(elemento.getId_Tipo_Persona())));
      if (!elemento.esGenero()) //False: Es masculino
         jrdMasculino.setSelected(!elemento.esGenero());
      else  //True: Femenino.
         jrdFemenino.setSelected(elemento.esGenero());
      jtfNacionalidad.setText(elemento.getNacionalidad());
      jtfReligion.setText(elemento.getReligion());
      //jcbEstadoCivil.setSelectedItem( bsEstadoCivilRender.getElementAt( bsEstadoCivilRender.indexOfValue(elemento.getEstadoCivil())));
      jcbEstadoCivil.setSelectedIndex(bsEstadoCivilRender.indexOfValue(elemento.getEstadoCivil()));
      jcbEsTipoSangre.setSelectedIndex(bsGrupoSanguineoRender.indexOfValue(elemento.getEsTipoSangre()));
      jftAltura.setValue(elemento.getAltura());
      jftPeso.setValue(elemento.getPeso());
      // jftHijos.setText(elemento.getFk_Persona_Complemento().;
      jtfObser.setText(elemento.getObservaciones());

   }

   //---------------------------------------------------------------------
   private boolean readElement(Personal elemento)
   {
      boolean resultado = false;
      String mensaje = "";

      if (!jtfNombres.getText().isEmpty() && !jtfApellidos.getText().isEmpty())
      {
         resultado = true;
         if (!elemento.isSet())
            elemento.setModified();
         elemento.setNombres(jtfNombres.getText());
         elemento.setApellidos(jtfApellidos.getText());
         elemento.setNacimiento(jdcNacimiento.getDate());
         elemento.setId_Tipo_Persona((Integer) bsTipoPersona.getSelectedValue());
         elemento.setActivo(jchkActivo.isSelected());
         elemento.setGenero(!jrdMasculino.isSelected()); //False: Masculino, True: Femenino
         elemento.setNacionalidad(jtfNacionalidad.getText());
         if (bsEstadoCivilRender.getSelectedItem() != null)
            elemento.setEstadoCivil((Integer) bsEstadoCivilRender.getSelectedValue());
         elemento.setReligion(jtfReligion.getText());
         if (bsGrupoSanguineoRender.getSelectedItem() != null)
            elemento.setEsTipoSangre(bsGrupoSanguineoRender.getCurrent().getId());
         elemento.setAltura((float) jftAltura.getValue());
         elemento.setPeso((float) jftPeso.getValue());

         if (!elemento.getNo_empleado().equals(no_empleado_new))
            elemento.setNo_empleado(no_empleado_new);

         elemento.setObservaciones(jtfObser.getText());
      }

      else
         mensaje = "Asegurese de proporcionar el Nombre  y Apellidos de la persona";

      if (!resultado)
         JOptionPane.showMessageDialog(this, mensaje, "", JOptionPane.PLAIN_MESSAGE);

      return resultado;
   }

   //---------------------------------------------------------------------
   public void close()
   {
      setVisible(false);
      dispose();
   }

   //----------------------------------------------------------------------
   public void setDisplayData(List<Localidad> listaLocalidades, List<PersonaDireccion> listaDirecciones)
   {
      bsLocalidadEditor.setData(listaLocalidades);
      bsLocalidadRender.setData(listaLocalidades);
      if (listaDirecciones != null)
         bsPersonaDireccion.setData(listaDirecciones);
   }

   //----------------------------------------------------------------------
   public void setDisplayData(List<PersonaIdentificadorVista> listaIdentificadores)
   {
      if (listaIdentificadores != null)
         bsPersonaIdentificador.setData(listaIdentificadores);
   }

   //----------------------------------------------------------------------
   public void setDisplayMedios(List<Valor> listaTipos, List<PersonaIdentificador> listaMedios)
   {
      bsTipoMedioEditor.setData(listaTipos);
      bsTipoMedioRender.setData(listaTipos);
      if (listaMedios != null)
         bsMediosContacto.setData(listaMedios);
   }

   //----------------------------------------------------------------------
   public void setDisplayFormacion(List<PersonaEducacion> listaEducacion)
   {
      //bsFormacion.setData(listaMonedas);
      //bsFormacionEditor.setData(listaMonedas);
      if (listaEducacion != null)
         bsFormacion.setData(listaEducacion);
   }

   //-----------------------------------------------------------------------
   public void setDisplayPictures(List<PersonaFoto> listaFotos)
   {
      if (listaFotos.size() > 0)
      {
         foto = listaFotos.get(0);
         jlbImagen.setIcon(new ImageIcon(foto.getMiniatura()));
         jlbImagen.setText("");
      }
      else
         foto = new PersonaFoto();

      foto.acceptChanges();
   }

   //--------------------------------------------------------------------
   public void setDisplayFormation(List<PersonaEducacion> listaFormacion)
   {
      if (listaFormacion != null)
         bsFormacion.setData(listaFormacion);
   }

   //--------------------------------------------------------------------
   public void setDisplayAttributes(List<PersonaAtributo> listaAtributos)
   {
      if (listaAtributos != null)
         bsAtributos.setData(listaAtributos);
   }

   //--------------------------------------------------------------------
   public void setDisplayDiseases(List<PersonaDetalle_5FN> listaCaracteristicas, List<PersonaAtributo> listaPadecimientos)
   {
      if (listaCaracteristicas != null)
         bsCaracteristicas.setData(listaCaracteristicas);

      if (listaPadecimientos != null)
         bsPadecimientos.setData(listaPadecimientos);
   }

   // -------------------------------------------------------------------
   private void setDisplayIncidencias(List<PersonaTieneIncidencia> incidencias)
   {
      if (incidencias != null)
         bsIncidencias.setData(incidencias);
   }

   //---------------------------------------------------------------------
   private void setDisplayFamiliares(List<PersonalTieneFamiliar> familiares, List<Personal> personales)
   {
      if (familiares != null)
         bsFamiliar.setData(familiares);
      
      bsPersonalEditor.setData(personales);
      bsPersonalRender.setData(personales);
   }

   //--------------------------------------------------------------------
   private void crearTipoAtributos()
   {
      List<Valor> listaValores = new ArrayList<>();

      listaValores.add(new Valor(0, "Intereses"));
      listaValores.add(new Valor(1, "Habilidades"));
      listaValores.add(new Valor(2, "Aficiones"));

      bsTipoAtributoEditor.setData(listaValores);
      bsTipoAtributoRender.setData(listaValores);
   }

   //--------------------------------------------------------------------
   private void crearTiposIncidencias()
   {
      List<Valor> SonTipos = new ArrayList<>();

      SonTipos.add(new Valor(0, "Retardo"));
      SonTipos.add(new Valor(1, "Falta Injustificada"));
      SonTipos.add(new Valor(2, "Falta Justificada"));
      SonTipos.add(new Valor(3, "Incapacidad"));
      SonTipos.add(new Valor(4, "Vacaciones"));
      SonTipos.add(new Valor(5, "Sancion por retardos"));
      SonTipos.add(new Valor(6, "Sancion por falta justificada"));

      bsTipoIncidenciaRender.setData(SonTipos);
      bsTipoIncidenciaEditor.setData(SonTipos);
   }

   //--------------------------------------------------------------------
   public void crearTipoFamiliar()
   {
      List<Valor> listaValores = new ArrayList<>();

      listaValores.add(new Valor(0, "Padre"));
      listaValores.add(new Valor(1, "Hijo"));
      listaValores.add(new Valor(2, "Conyugue"));

      bsPrototipoFamiliarEditor.setData(listaValores);
      bsPrototipoFamiliarRender.setData(listaValores);
   }

   //--------------------------------------------------------------------
   private void crearTipoPadecimientos()
   {
      List<Valor> listaValores = new ArrayList<>();

      listaValores.add(new Valor(3, "Padecimiento"));
      listaValores.add(new Valor(4, "Alergía"));

      bsTipoPadecimientoRender.setData(listaValores);
      bsTipoPadecimientoEditor.setData(listaValores);
   }

   //--------------------------------------------------------------------
   private void crearGruposSanguineos()
   {
      List<Valor> listaValores = new ArrayList<>();

      listaValores.add(new Valor(0, "A−"));
      listaValores.add(new Valor(1, "A+"));
      listaValores.add(new Valor(2, "B−"));
      listaValores.add(new Valor(3, "B+"));
      listaValores.add(new Valor(4, "O-"));
      listaValores.add(new Valor(5, "O+"));
      listaValores.add(new Valor(6, "AB−"));
      listaValores.add(new Valor(7, "AB+"));

      bsGrupoSanguineoRender.setData(listaValores);
   }

   //--------------------------------------------------------------------
   private void crearTipoEducacion()
   {
      List<Valor> listaValores = new ArrayList<>();

      listaValores.add(new Valor(0, "Primaria"));
      listaValores.add(new Valor(1, "Secundaria"));
      listaValores.add(new Valor(2, "Preparatoria"));
      listaValores.add(new Valor(3, "Curso"));
      listaValores.add(new Valor(4, "Taller"));
      listaValores.add(new Valor(5, "Diplomado"));
      listaValores.add(new Valor(6, "Licenciatura"));
      listaValores.add(new Valor(7, "Maestria"));
      listaValores.add(new Valor(8, "Doctorado"));

      bsTipoEducacionEditor.setData(listaValores);
      bsTipoEducacionRender.setData(listaValores);
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

        jbtGenero = new javax.swing.ButtonGroup();
        jfcImagenes = new javax.swing.JFileChooser();
        jtpBody = new javax.swing.JTabbedPane();
        tabGeneral = new javax.swing.JPanel();
        jpSeccionImagen = new javax.swing.JPanel();
        jpDimensionImagen = new javax.swing.JPanel();
        jlbImagen = new javax.swing.JLabel();
        jpImagenAcciones = new javax.swing.JPanel();
        jbtnNuevaImagen = new javax.swing.JButton();
        jbtnEliminarImagen = new javax.swing.JButton();
        jpSeccionDatos = new javax.swing.JPanel();
        jpDimensionDatos = new javax.swing.JPanel();
        jpDatosComplementarios = new javax.swing.JPanel();
        jpGenero = new javax.swing.JPanel();
        jrdMasculino = new javax.swing.JRadioButton();
        jrdFemenino = new javax.swing.JRadioButton();
        jlbNacionalidad = new javax.swing.JLabel();
        jtfNacionalidad = new javax.swing.JTextField();
        jtfEstadoCivil = new javax.swing.JLabel();
        jcbEstadoCivil = new javax.swing.JComboBox();
        jlbReligion = new javax.swing.JLabel();
        jtfReligion = new javax.swing.JTextField();
        jblHijos = new javax.swing.JLabel();
        jftHijos = new javax.swing.JFormattedTextField();
        jpDatos1 = new javax.swing.JPanel();
        jpDatosGenerales = new javax.swing.JPanel();
        jlbNombre = new javax.swing.JLabel();
        jtfNombres = new javax.swing.JTextField();
        jlbApellidos = new javax.swing.JLabel();
        jtfApellidos = new javax.swing.JTextField();
        jlbNacimiento = new javax.swing.JLabel();
        jdcNacimiento = new com.toedter.calendar.JDateChooser();
        jlbTipoPersona = new javax.swing.JLabel();
        jcbTipoPersona = new javax.swing.JComboBox();
        jchkActivo = new javax.swing.JCheckBox();
        tabDirecciones = new javax.swing.JPanel();
        jbarDirecciones = new javax.swing.JToolBar();
        jtoolNuevoDireccion = new javax.swing.JButton();
        jtoolEliminarDireccion = new javax.swing.JButton();
        jspDirecciones = new javax.swing.JScrollPane();
        jtbDirecciones = new javax.swing.JTable();
        tabIdentificadores = new javax.swing.JPanel();
        jbarIdentificadores = new javax.swing.JToolBar();
        jspIdentificadores = new javax.swing.JScrollPane();
        jtbIdentificadores = new javax.swing.JTable();
        tabFamiliar = new javax.swing.JPanel();
        jspFamiliar = new javax.swing.JScrollPane();
        jtbFamiliar = new javax.swing.JTable();
        jbarFamiliar = new javax.swing.JToolBar();
        jbNuevoFamiliar = new javax.swing.JButton();
        jbEliminarFamiliar = new javax.swing.JButton();
        tabMediosContacto = new javax.swing.JPanel();
        jbarTelefonos = new javax.swing.JToolBar();
        jtoolNuevoTelefono = new javax.swing.JButton();
        jtoolEliminarTelefono = new javax.swing.JButton();
        jspTelefonos = new javax.swing.JScrollPane();
        jtbMedios = new javax.swing.JTable();
        tabFormacion = new javax.swing.JPanel();
        jbarFormacion = new javax.swing.JToolBar();
        jtoolNuevaFormacion = new javax.swing.JButton();
        jtoolEliminarFormacion = new javax.swing.JButton();
        jspFormacion = new javax.swing.JScrollPane();
        jtbFormacion = new javax.swing.JTable();
        tabAtributos = new javax.swing.JPanel();
        jbarAtributos = new javax.swing.JToolBar();
        jtoolNuevoAtributo = new javax.swing.JButton();
        jtoolEliminarAtributo = new javax.swing.JButton();
        jspAtributos = new javax.swing.JScrollPane();
        jtbAtributos = new javax.swing.JTable();
        tabMedicaciones = new javax.swing.JPanel();
        jpSeccionDetalles = new javax.swing.JPanel();
        jpDatosDetalle = new javax.swing.JPanel();
        jspPersonaDetalles = new javax.swing.JScrollPane();
        jtbPersonaDetalles = new javax.swing.JTable();
        jpDatosPadecimientos = new javax.swing.JPanel();
        jbarPadecimientos = new javax.swing.JToolBar();
        jtoolNuevoPadecimiento = new javax.swing.JButton();
        jtoolEliminarPadecimiento = new javax.swing.JButton();
        jspPadecimientos = new javax.swing.JScrollPane();
        jtbPadecimientos = new javax.swing.JTable();
        jpSeccionComplemento = new javax.swing.JPanel();
        jlbEsTipoSangre = new javax.swing.JLabel();
        jcbEsTipoSangre = new javax.swing.JComboBox();
        jlbAltura = new javax.swing.JLabel();
        jftAltura = new JFormattedTextField(0.0F);
        jlbPeso = new javax.swing.JLabel();
        jftPeso = new JFormattedTextField(0.0F);
        tabIncidencias = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtbIncidencia = new javax.swing.JTable();
        jbarIncidencias = new javax.swing.JToolBar();
        jtoolNuevoIncidencia = new javax.swing.JButton();
        jtoolEliminarIncidencia = new javax.swing.JButton();
        tabObservaciones = new javax.swing.JPanel();
        jlbObservaciones = new javax.swing.JLabel();
        jtfObser = new javax.swing.JTextArea();
        jpAcciones = new javax.swing.JPanel();
        jbCancelar = new javax.swing.JButton();
        jbAceptar = new javax.swing.JButton();
        jpEncabezado = new javax.swing.JPanel();
        jlbIcono = new javax.swing.JLabel();
        jpAreaMensajes = new java.awt.Panel();

        jfcImagenes.setDialogTitle("");

        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setMinimumSize(new java.awt.Dimension(700, 450));

        jtpBody.setToolTipText("Datos Generales y complementarios del Personal");

        tabGeneral.setMinimumSize(new java.awt.Dimension(357, 157));
        tabGeneral.setName("tabGeneral"); // NOI18N
        tabGeneral.setLayout(new java.awt.BorderLayout());

        jpSeccionImagen.setPreferredSize(new java.awt.Dimension(200, 200));
        jpSeccionImagen.setLayout(new java.awt.BorderLayout());

        jpDimensionImagen.setPreferredSize(new java.awt.Dimension(250, 250));
        jpDimensionImagen.setLayout(new java.awt.BorderLayout());

        jlbImagen.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbImagen.setText("<Sin imagen>");
        jlbImagen.setToolTipText("Imagen");
        jlbImagen.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        jlbImagen.setPreferredSize(new java.awt.Dimension(210, 210));
        jpDimensionImagen.add(jlbImagen, java.awt.BorderLayout.NORTH);

        jbtnNuevaImagen.setText("Buscar");
        jpImagenAcciones.add(jbtnNuevaImagen);

        jbtnEliminarImagen.setText("Limpiar");
        jpImagenAcciones.add(jbtnEliminarImagen);

        jpDimensionImagen.add(jpImagenAcciones, java.awt.BorderLayout.CENTER);

        jpSeccionImagen.add(jpDimensionImagen, java.awt.BorderLayout.NORTH);

        tabGeneral.add(jpSeccionImagen, java.awt.BorderLayout.WEST);

        jpSeccionDatos.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jpDimensionDatos.setLayout(new java.awt.BorderLayout());

        java.awt.GridBagLayout jpDatosComplementariosLayout = new java.awt.GridBagLayout();
        jpDatosComplementariosLayout.columnWidths = new int[] {0, 5, 0, 5, 0, 5, 0, 5, 0};
        jpDatosComplementariosLayout.rowHeights = new int[] {0, 5, 0, 5, 0};
        jpDatosComplementarios.setLayout(jpDatosComplementariosLayout);

        jpGenero.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED, null, new java.awt.Color(102, 102, 102)));

        jbtGenero.add(jrdMasculino);
        jrdMasculino.setText("Masculino");
        jpGenero.add(jrdMasculino);

        jbtGenero.add(jrdFemenino);
        jrdFemenino.setText("Femenino");
        jpGenero.add(jrdFemenino);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        jpDatosComplementarios.add(jpGenero, gridBagConstraints);

        jlbNacionalidad.setText("Nacionalidad:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        jpDatosComplementarios.add(jlbNacionalidad, gridBagConstraints);

        jtfNacionalidad.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jpDatosComplementarios.add(jtfNacionalidad, gridBagConstraints);

        jtfEstadoCivil.setText("Estado Civil:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        jpDatosComplementarios.add(jtfEstadoCivil, gridBagConstraints);

        jcbEstadoCivil.setModel(bsEstadoCivilRender);
        jcbEstadoCivil.setPreferredSize(new java.awt.Dimension(120, 25));
        jcbEstadoCivil.setRenderer(new POJOListCellRenderer<Valor>());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jpDatosComplementarios.add(jcbEstadoCivil, gridBagConstraints);

        jlbReligion.setText("Religion:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        jpDatosComplementarios.add(jlbReligion, gridBagConstraints);

        jtfReligion.setToolTipText("");
        jtfReligion.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jpDatosComplementarios.add(jtfReligion, gridBagConstraints);

        jblHijos.setText("Cantidad de Hijos:");
        jblHijos.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        jpDatosComplementarios.add(jblHijos, gridBagConstraints);

        jftHijos.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        jftHijos.setEnabled(false);
        jftHijos.setPreferredSize(new java.awt.Dimension(70, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jpDatosComplementarios.add(jftHijos, gridBagConstraints);

        jpDimensionDatos.add(jpDatosComplementarios, java.awt.BorderLayout.SOUTH);

        jpDatos1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jpDatosGenerales.setRequestFocusEnabled(false);
        java.awt.GridBagLayout jpDatosGeneralesLayout = new java.awt.GridBagLayout();
        jpDatosGeneralesLayout.columnWidths = new int[] {0, 5, 0};
        jpDatosGeneralesLayout.rowHeights = new int[] {0, 5, 0, 5, 0, 5, 0, 5, 0};
        jpDatosGenerales.setLayout(jpDatosGeneralesLayout);

        jlbNombre.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbNombre.setText("Nombre:");
        jlbNombre.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        jpDatosGenerales.add(jlbNombre, gridBagConstraints);

        jtfNombres.setToolTipText("");
        jtfNombres.setName("jtfNombres"); // NOI18N
        jtfNombres.setPreferredSize(new java.awt.Dimension(270, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jpDatosGenerales.add(jtfNombres, gridBagConstraints);

        jlbApellidos.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbApellidos.setText("Apellidos:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        jpDatosGenerales.add(jlbApellidos, gridBagConstraints);

        jtfApellidos.setName("jtfApellidos"); // NOI18N
        jtfApellidos.setPreferredSize(new java.awt.Dimension(270, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jpDatosGenerales.add(jtfApellidos, gridBagConstraints);

        jlbNacimiento.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbNacimiento.setText("Fecha de nacimiento:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        jpDatosGenerales.add(jlbNacimiento, gridBagConstraints);

        jdcNacimiento.setPreferredSize(new java.awt.Dimension(200, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jpDatosGenerales.add(jdcNacimiento, gridBagConstraints);

        jlbTipoPersona.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbTipoPersona.setText("Tipo:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        jpDatosGenerales.add(jlbTipoPersona, gridBagConstraints);

        jcbTipoPersona.setModel(bsTipoPersona);
        jcbTipoPersona.setName("jcbTipoPersona"); // NOI18N
        jcbTipoPersona.setPreferredSize(new java.awt.Dimension(270, 25));
        jcbTipoPersona.setRenderer(new POJOListCellRenderer<TipoPersona>()
        );
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jpDatosGenerales.add(jcbTipoPersona, gridBagConstraints);

        jchkActivo.setText("Activo");
        jchkActivo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jchkActivo.setName("checkActivo"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jpDatosGenerales.add(jchkActivo, gridBagConstraints);

        jpDatos1.add(jpDatosGenerales);

        jpDimensionDatos.add(jpDatos1, java.awt.BorderLayout.PAGE_START);

        jpSeccionDatos.add(jpDimensionDatos);

        tabGeneral.add(jpSeccionDatos, java.awt.BorderLayout.CENTER);

        jtpBody.addTab("General", tabGeneral);

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

        tabIdentificadores.setName("tabIdentificadores"); // NOI18N
        tabIdentificadores.setLayout(new java.awt.BorderLayout());

        jbarIdentificadores.setRollover(true);
        tabIdentificadores.add(jbarIdentificadores, java.awt.BorderLayout.PAGE_START);

        jtbIdentificadores.setModel(bsPersonaIdentificador);
        jspIdentificadores.setViewportView(jtbIdentificadores);

        tabIdentificadores.add(jspIdentificadores, java.awt.BorderLayout.CENTER);

        jtpBody.addTab("Identificadores", tabIdentificadores);

        tabFamiliar.setLayout(new java.awt.BorderLayout());

        jtbFamiliar.setModel(bsFamiliar);
        jtbFamiliar.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jspFamiliar.setViewportView(jtbFamiliar);

        tabFamiliar.add(jspFamiliar, java.awt.BorderLayout.CENTER);

        jbarFamiliar.setRollover(true);

        jbNuevoFamiliar.setText("Nuevo");
        jbNuevoFamiliar.setFocusable(false);
        jbNuevoFamiliar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbNuevoFamiliar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbarFamiliar.add(jbNuevoFamiliar);

        jbEliminarFamiliar.setText("Eliminar");
        jbEliminarFamiliar.setFocusable(false);
        jbEliminarFamiliar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbEliminarFamiliar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbarFamiliar.add(jbEliminarFamiliar);

        tabFamiliar.add(jbarFamiliar, java.awt.BorderLayout.PAGE_START);

        jtpBody.addTab("Familia", tabFamiliar);

        tabMediosContacto.setName("tabMediosContacto"); // NOI18N
        tabMediosContacto.setLayout(new java.awt.BorderLayout());

        jbarTelefonos.setRollover(true);

        jtoolNuevoTelefono.setText("Nuevo");
        jtoolNuevoTelefono.setFocusable(false);
        jtoolNuevoTelefono.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jtoolNuevoTelefono.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbarTelefonos.add(jtoolNuevoTelefono);

        jtoolEliminarTelefono.setText("Eliminar");
        jtoolEliminarTelefono.setToolTipText("");
        jtoolEliminarTelefono.setFocusable(false);
        jtoolEliminarTelefono.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jtoolEliminarTelefono.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbarTelefonos.add(jtoolEliminarTelefono);

        tabMediosContacto.add(jbarTelefonos, java.awt.BorderLayout.PAGE_START);

        jtbMedios.setModel(bsMediosContacto);
        jspTelefonos.setViewportView(jtbMedios);

        tabMediosContacto.add(jspTelefonos, java.awt.BorderLayout.CENTER);

        jtpBody.addTab("Medios de contacto", tabMediosContacto);

        tabFormacion.setLayout(new java.awt.BorderLayout());

        jbarFormacion.setRollover(true);

        jtoolNuevaFormacion.setText("Nuevo");
        jtoolNuevaFormacion.setFocusable(false);
        jtoolNuevaFormacion.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jtoolNuevaFormacion.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbarFormacion.add(jtoolNuevaFormacion);

        jtoolEliminarFormacion.setText("Eliminar");
        jtoolEliminarFormacion.setToolTipText("");
        jtoolEliminarFormacion.setFocusable(false);
        jtoolEliminarFormacion.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jtoolEliminarFormacion.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbarFormacion.add(jtoolEliminarFormacion);

        tabFormacion.add(jbarFormacion, java.awt.BorderLayout.PAGE_START);

        jtbFormacion.setModel(bsFormacion);
        jtbFormacion.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jspFormacion.setViewportView(jtbFormacion);

        tabFormacion.add(jspFormacion, java.awt.BorderLayout.CENTER);

        jtpBody.addTab("Formacion", tabFormacion);

        tabAtributos.setLayout(new java.awt.BorderLayout());

        jbarAtributos.setRollover(true);

        jtoolNuevoAtributo.setText("Nuevo");
        jtoolNuevoAtributo.setFocusable(false);
        jtoolNuevoAtributo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jtoolNuevoAtributo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbarAtributos.add(jtoolNuevoAtributo);

        jtoolEliminarAtributo.setText("Eliminar");
        jtoolEliminarAtributo.setToolTipText("");
        jtoolEliminarAtributo.setFocusable(false);
        jtoolEliminarAtributo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jtoolEliminarAtributo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbarAtributos.add(jtoolEliminarAtributo);

        tabAtributos.add(jbarAtributos, java.awt.BorderLayout.PAGE_START);

        jtbAtributos.setModel(bsAtributos);
        jtbAtributos.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jspAtributos.setViewportView(jtbAtributos);

        tabAtributos.add(jspAtributos, java.awt.BorderLayout.CENTER);

        jtpBody.addTab("Atributos", tabAtributos);

        tabMedicaciones.setLayout(new java.awt.BorderLayout());

        jpSeccionDetalles.setLayout(new java.awt.GridLayout(1, 2));

        jpDatosDetalle.setBorder(javax.swing.BorderFactory.createTitledBorder("Otras Características "));
        jpDatosDetalle.setLayout(new java.awt.BorderLayout());

        jtbPersonaDetalles.setModel(bsCaracteristicas);
        jtbPersonaDetalles.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jspPersonaDetalles.setViewportView(jtbPersonaDetalles);

        jpDatosDetalle.add(jspPersonaDetalles, java.awt.BorderLayout.CENTER);

        jpSeccionDetalles.add(jpDatosDetalle);

        jpDatosPadecimientos.setBorder(javax.swing.BorderFactory.createTitledBorder("Padecimientos / Alergías"));
        jpDatosPadecimientos.setLayout(new java.awt.BorderLayout());

        jbarPadecimientos.setRollover(true);

        jtoolNuevoPadecimiento.setText("Nuevo");
        jtoolNuevoPadecimiento.setFocusable(false);
        jtoolNuevoPadecimiento.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jtoolNuevoPadecimiento.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbarPadecimientos.add(jtoolNuevoPadecimiento);

        jtoolEliminarPadecimiento.setText("Eliminar");
        jtoolEliminarPadecimiento.setToolTipText("");
        jtoolEliminarPadecimiento.setFocusable(false);
        jtoolEliminarPadecimiento.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jtoolEliminarPadecimiento.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbarPadecimientos.add(jtoolEliminarPadecimiento);

        jpDatosPadecimientos.add(jbarPadecimientos, java.awt.BorderLayout.PAGE_START);

        jtbPadecimientos.setModel(bsPadecimientos);
        jtbPadecimientos.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jspPadecimientos.setViewportView(jtbPadecimientos);

        jpDatosPadecimientos.add(jspPadecimientos, java.awt.BorderLayout.CENTER);

        jpSeccionDetalles.add(jpDatosPadecimientos);

        tabMedicaciones.add(jpSeccionDetalles, java.awt.BorderLayout.CENTER);

        jlbEsTipoSangre.setText("Tipo de Sangre:");
        jpSeccionComplemento.add(jlbEsTipoSangre);

        jcbEsTipoSangre.setModel(bsGrupoSanguineoRender);
        jcbEsTipoSangre.setPreferredSize(new java.awt.Dimension(150, 22));
        jcbEsTipoSangre.setRenderer(new POJOListCellRenderer<Valor>());
        jpSeccionComplemento.add(jcbEsTipoSangre);

        jlbAltura.setText("Altura(mts):");
        jpSeccionComplemento.add(jlbAltura);

        jftAltura.setText("0.0");
        jftAltura.setPreferredSize(new java.awt.Dimension(80, 22));
        jpSeccionComplemento.add(jftAltura);

        jlbPeso.setText("Peso(kgs):");
        jpSeccionComplemento.add(jlbPeso);

        jftPeso.setText("0.0");
        jftPeso.setPreferredSize(new java.awt.Dimension(80, 22));
        jpSeccionComplemento.add(jftPeso);

        tabMedicaciones.add(jpSeccionComplemento, java.awt.BorderLayout.PAGE_START);

        jtpBody.addTab("Información Medica", tabMedicaciones);

        tabIncidencias.setLayout(new java.awt.BorderLayout());

        jtbIncidencia.setModel(bsIncidencias);
        jtbIncidencia.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane1.setViewportView(jtbIncidencia);

        tabIncidencias.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jbarIncidencias.setRollover(true);

        jtoolNuevoIncidencia.setText("Nuevo");
        jtoolNuevoIncidencia.setFocusable(false);
        jtoolNuevoIncidencia.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jtoolNuevoIncidencia.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbarIncidencias.add(jtoolNuevoIncidencia);

        jtoolEliminarIncidencia.setText("Eliminar");
        jtoolEliminarIncidencia.setFocusable(false);
        jtoolEliminarIncidencia.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jtoolEliminarIncidencia.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbarIncidencias.add(jtoolEliminarIncidencia);

        tabIncidencias.add(jbarIncidencias, java.awt.BorderLayout.PAGE_START);

        jtpBody.addTab("Incidencias", tabIncidencias);

        tabObservaciones.setLayout(new java.awt.BorderLayout());

        jlbObservaciones.setText("Observaciones:");
        tabObservaciones.add(jlbObservaciones, java.awt.BorderLayout.NORTH);

        jtfObser.setColumns(20);
        jtfObser.setLineWrap(true);
        jtfObser.setRows(5);
        jtfObser.setPreferredSize(new java.awt.Dimension(320, 180));
        tabObservaciones.add(jtfObser, java.awt.BorderLayout.CENTER);

        jtpBody.addTab("Observaciones", tabObservaciones);

        getContentPane().add(jtpBody, java.awt.BorderLayout.CENTER);

        jpAcciones.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jbCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/syswave/forms/resources/button-cross.png"))); // NOI18N
        jbCancelar.setText("Cancelar");
        jpAcciones.add(jbCancelar);

        jbAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/syswave/forms/resources/button-tick.png"))); // NOI18N
        jbAceptar.setText("Aceptar");
        jpAcciones.add(jbAceptar);

        getContentPane().add(jpAcciones, java.awt.BorderLayout.SOUTH);

        jpEncabezado.setBackground(new java.awt.Color(255, 153, 255));
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

        setBounds(0, 0, 780, 477);
    }// </editor-fold>//GEN-END:initComponents

// <editor-fold defaultstate="collapsed" desc="more generated code"> 

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbAceptar;
    private javax.swing.JButton jbCancelar;
    private javax.swing.JButton jbEliminarFamiliar;
    private javax.swing.JButton jbNuevoFamiliar;
    private javax.swing.JToolBar jbarAtributos;
    private javax.swing.JToolBar jbarDirecciones;
    private javax.swing.JToolBar jbarFamiliar;
    private javax.swing.JToolBar jbarFormacion;
    private javax.swing.JToolBar jbarIdentificadores;
    private javax.swing.JToolBar jbarIncidencias;
    private javax.swing.JToolBar jbarPadecimientos;
    private javax.swing.JToolBar jbarTelefonos;
    private javax.swing.JLabel jblHijos;
    private javax.swing.ButtonGroup jbtGenero;
    private javax.swing.JButton jbtnEliminarImagen;
    private javax.swing.JButton jbtnNuevaImagen;
    private javax.swing.JComboBox jcbEsTipoSangre;
    private javax.swing.JComboBox jcbEstadoCivil;
    private javax.swing.JComboBox jcbTipoPersona;
    private javax.swing.JCheckBox jchkActivo;
    private com.toedter.calendar.JDateChooser jdcNacimiento;
    private javax.swing.JFileChooser jfcImagenes;
    private javax.swing.JFormattedTextField jftAltura;
    private javax.swing.JFormattedTextField jftHijos;
    private javax.swing.JFormattedTextField jftPeso;
    private javax.swing.JLabel jlbAltura;
    private javax.swing.JLabel jlbApellidos;
    private javax.swing.JLabel jlbEsTipoSangre;
    private javax.swing.JLabel jlbIcono;
    private javax.swing.JLabel jlbImagen;
    private javax.swing.JLabel jlbNacimiento;
    private javax.swing.JLabel jlbNacionalidad;
    private javax.swing.JLabel jlbNombre;
    private javax.swing.JLabel jlbObservaciones;
    private javax.swing.JLabel jlbPeso;
    private javax.swing.JLabel jlbReligion;
    private javax.swing.JLabel jlbTipoPersona;
    private javax.swing.JPanel jpAcciones;
    private java.awt.Panel jpAreaMensajes;
    private javax.swing.JPanel jpDatos1;
    private javax.swing.JPanel jpDatosComplementarios;
    private javax.swing.JPanel jpDatosDetalle;
    private javax.swing.JPanel jpDatosGenerales;
    private javax.swing.JPanel jpDatosPadecimientos;
    private javax.swing.JPanel jpDimensionDatos;
    private javax.swing.JPanel jpDimensionImagen;
    private javax.swing.JPanel jpEncabezado;
    private javax.swing.JPanel jpGenero;
    private javax.swing.JPanel jpImagenAcciones;
    private javax.swing.JPanel jpSeccionComplemento;
    private javax.swing.JPanel jpSeccionDatos;
    private javax.swing.JPanel jpSeccionDetalles;
    private javax.swing.JPanel jpSeccionImagen;
    private javax.swing.JRadioButton jrdFemenino;
    private javax.swing.JRadioButton jrdMasculino;
    private javax.swing.JScrollPane jspAtributos;
    private javax.swing.JScrollPane jspDirecciones;
    private javax.swing.JScrollPane jspFamiliar;
    private javax.swing.JScrollPane jspFormacion;
    private javax.swing.JScrollPane jspIdentificadores;
    private javax.swing.JScrollPane jspPadecimientos;
    private javax.swing.JScrollPane jspPersonaDetalles;
    private javax.swing.JScrollPane jspTelefonos;
    private javax.swing.JTable jtbAtributos;
    private javax.swing.JTable jtbDirecciones;
    private javax.swing.JTable jtbFamiliar;
    private javax.swing.JTable jtbFormacion;
    private javax.swing.JTable jtbIdentificadores;
    private javax.swing.JTable jtbIncidencia;
    private javax.swing.JTable jtbMedios;
    private javax.swing.JTable jtbPadecimientos;
    private javax.swing.JTable jtbPersonaDetalles;
    private javax.swing.JTextField jtfApellidos;
    private javax.swing.JLabel jtfEstadoCivil;
    private javax.swing.JTextField jtfNacionalidad;
    private javax.swing.JTextField jtfNombres;
    private javax.swing.JTextArea jtfObser;
    private javax.swing.JTextField jtfReligion;
    private javax.swing.JButton jtoolEliminarAtributo;
    private javax.swing.JButton jtoolEliminarDireccion;
    private javax.swing.JButton jtoolEliminarFormacion;
    private javax.swing.JButton jtoolEliminarIncidencia;
    private javax.swing.JButton jtoolEliminarPadecimiento;
    private javax.swing.JButton jtoolEliminarTelefono;
    private javax.swing.JButton jtoolNuevaFormacion;
    private javax.swing.JButton jtoolNuevoAtributo;
    private javax.swing.JButton jtoolNuevoDireccion;
    private javax.swing.JButton jtoolNuevoIncidencia;
    private javax.swing.JButton jtoolNuevoPadecimiento;
    private javax.swing.JButton jtoolNuevoTelefono;
    private javax.swing.JTabbedPane jtpBody;
    private javax.swing.JPanel tabAtributos;
    private javax.swing.JPanel tabDirecciones;
    private javax.swing.JPanel tabFamiliar;
    private javax.swing.JPanel tabFormacion;
    private javax.swing.JPanel tabGeneral;
    private javax.swing.JPanel tabIdentificadores;
    private javax.swing.JPanel tabIncidencias;
    private javax.swing.JPanel tabMedicaciones;
    private javax.swing.JPanel tabMediosContacto;
    private javax.swing.JPanel tabObservaciones;
    // End of variables declaration//GEN-END:variables
//  </editor-fold>
   //---------------------------------------------------------------------
   //--  Esta clase controla el cargado en segundo plano de los origenes de datos.
   //---------------------------------------------------------------------
   private class PersonalDetailCargarSwingWorker extends SwingWorker<List<Object>, Void>
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
               case opLOAD_ADDRESS:
                  arguments.add(localidades.obtenerListaHojas());
                  arguments.add(owner.obtenerDirecciones((Personal) arguments.get(1)));
                  break;

               case opLOAD_LOCALIDADES:
                  arguments.add(localidades.obtenerListaHojas());
                  break;

               case opLOAD_IDENTIFICADORES:
                  arguments.add(owner.obtenerIdentificadores((Personal) arguments.get(1)));
                  break;

               case opLOAD_TIPOS_MEDIO:
                  arguments.add(owner.obtenerTipoMedios());
                  break;

               case opLOAD_MEDIOS_CONTACTO:
                  arguments.add(owner.obtenerTipoMedios());
                  arguments.add(owner.obtenerMedios((Personal) arguments.get(1)));
                  break;

               case opLOAD_MINIATURAS:
                  arguments.add(owner.obtenerFotos((Personal) arguments.get(1)));
                  break;

               case opLOAD_FORMACION:
                  arguments.add(owner.obtenerFormacion((Personal) arguments.get(1)));
                  break;

               case opLOAD_ATRIBUTOS:
                  arguments.add(owner.obtenerAtributos((Personal) arguments.get(1)));
                  break;

               case opLOAD_PADECIMIENTOS:
                  arguments.add(owner.obtenerCaracteristicas((Personal) arguments.get(1)));
                  arguments.add(owner.obtenerPadecimientos((Personal) arguments.get(1)));
                  break;

               case opLOAD_INCIDENCIAS:
                  arguments.add(owner.obtenerIncidencias((Personal) arguments.get(1)));
                  //arguments.add(owner.obtenerPadecimientos((Personal)arguments.get(1)));
                  break;

               case opLOAD_FAMILIARES:
                  arguments.add(owner.obtenerFamiliares((PersonalTieneFamiliar) arguments.get(1)));
                  arguments.add(owner.obtenerSoloPersonal((Personal)arguments.get(2)));
                  break;
                  
               case opLOAD_PERSONAL:
                  arguments.add(owner.obtenerSoloPersonal((Personal)arguments.get(1)));
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
                  case opLOAD_ADDRESS:
                     setDisplayData((List<Localidad>) results.get(2), (List<PersonaDireccion>) results.get(3));
                     break;

                  case opLOAD_LOCALIDADES:
                     setDisplayData((List<Localidad>) results.get(1), null);
                     break;

                  case opLOAD_IDENTIFICADORES:
                     setDisplayData((List<PersonaIdentificadorVista>) results.get(2));
                     break;

                  case opLOAD_TIPOS_MEDIO:
                     setDisplayMedios((List<Valor>) results.get(1), null);
                     break;

                  case opLOAD_MEDIOS_CONTACTO:
                     setDisplayMedios((List<Valor>) results.get(2), (List<PersonaIdentificador>) results.get(3));
                     break;

                  case opLOAD_MINIATURAS:
                     setDisplayPictures((List<PersonaFoto>) results.get(2));
                     break;

                  case opLOAD_FORMACION:
                     setDisplayFormation((List<PersonaEducacion>) results.get(2));
                     break;

                  case opLOAD_ATRIBUTOS:
                     setDisplayAttributes((List<PersonaAtributo>) results.get(2));
                     break;

                  case opLOAD_PADECIMIENTOS:
                     setDisplayDiseases((List<PersonaDetalle_5FN>) results.get(2), (List<PersonaAtributo>) results.get(3));
                     break;

                  case opLOAD_INCIDENCIAS:
                     setDisplayIncidencias((List<PersonaTieneIncidencia>) results.get(2));
                     break;

                  case opLOAD_FAMILIARES:
                     setDisplayFamiliares((List<PersonalTieneFamiliar>) results.get(3), 
                                          (List<Personal>) results.get(4));
                     break;
                     
                  case opLOAD_PERSONAL:
                     setDisplayFamiliares(null, (List<Personal>) results.get(2));
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
