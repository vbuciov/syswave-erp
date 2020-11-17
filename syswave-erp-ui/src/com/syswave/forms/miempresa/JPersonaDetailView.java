package com.syswave.forms.miempresa;

import com.orbital.AutoCompleteDocument;
import com.syswave.entidades.common.Entidad;
import com.syswave.entidades.configuracion.Localidad;
import com.syswave.entidades.miempresa.Moneda;
import com.syswave.entidades.miempresa.Persona;
import com.syswave.entidades.miempresa.PersonaCreditoCuenta;
import com.syswave.entidades.miempresa.PersonaDireccion;
import com.syswave.entidades.miempresa.PersonaFoto;
import com.syswave.entidades.miempresa.PersonaIdentificador;
import com.syswave.entidades.miempresa.TipoPersona;
import com.syswave.entidades.miempresa.Valor;
import com.syswave.entidades.miempresa_vista.PersonaIdentificadorVista;
import com.syswave.forms.databinding.LocalidadComboBoxModel;
import com.syswave.forms.databinding.MonedasComboBoxModel;
import com.syswave.swing.models.POJOTableModel;
import com.syswave.forms.databinding.PersonaCreditoCuentasTableModel;
import com.syswave.forms.databinding.PersonaDireccionesTableModel;
import com.syswave.forms.databinding.PersonaIdentificadoresTableModel;
import com.syswave.forms.databinding.PersonaIdentificadoresVistaTableModel;
import com.syswave.forms.databinding.TipoPersonasComboBoxModel;
import com.syswave.forms.databinding.ValorComboBoxModel;
import com.syswave.swing.table.editors.LookUpComboBoxTableCellEditor;
import com.syswave.swing.FileTypeFilter;
import com.syswave.swing.table.renders.LookUpComboBoxTableCellRenderer;
import com.syswave.swing.renders.POJOListCellRenderer;
import com.syswave.logicas.configuracion.LocalidadesBusinessLogic;
import com.syswave.logicas.miempresa.MonedasBusinessLogic;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.MimetypesFileTypeMap;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
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
 * @author Victor Manuel Bucio Vargas
 */
public class JPersonaDetailView extends javax.swing.JInternalFrame implements TableModelListener
{

    private final int opLOAD_ADDRESS = 0;
    private final int ONLOAD_LOCALIDADES = 1;
    private final int opLOAD_IDENTIFICADORES = 2;
    private final int opLOAD_MEDIOS_CONTACTO = 3;
    private final int opLOAD_TIPOS_MEDIO = 4;
    private final int opLOAD_MONEDAS_CUENTAS = 5;
    private final int opLOAD_MONEDAS = 6;
    private final int opLOAD_MINIATURAS = 7;

    Persona elementoActual;
    IPersonaMediator owner;
    TipoPersonasComboBoxModel bsTipoPersona;
    PersonaDireccionesTableModel bsPersonaDireccion;
    PersonaIdentificadoresVistaTableModel bsPersonaIdentificador;
    PersonaIdentificadoresTableModel bsMediosContacto;
    PersonaCreditoCuentasTableModel bsCuentas;
    LocalidadComboBoxModel bsLocalidadRender;
    LocalidadComboBoxModel bsLocalidadEditor;
    ValorComboBoxModel bsTipoMedioRender;
    ValorComboBoxModel bsTipoMedioEditor;
    ValorComboBoxModel bsTipoCuentaEditor;
    ValorComboBoxModel bsTipoCuentaRender;
    MonedasComboBoxModel bsMonedasEditor;
    MonedasComboBoxModel bsMonedasRender;

    private PersonaFoto foto, foto_borrada;

    boolean esNuevo, construidoDirecciones, construirIdentificadores, construirMedios,
            construidoCuentas;
    LocalidadesBusinessLogic localidades;
    MonedasBusinessLogic monedas;
    TableColumn colLocalidad, colValor, colLlave, colDescripcion,
            colTipoMedio, colClave, colNumero, colSaldo,
            colLimite, colMoneda, colTipo, colActivo,
            colNota;

    PersonaDetailCargarSwingWorker swCargador;
    List<PersonaDireccion> deleteds;
    List<PersonaIdentificador> medios_deleteds;
    List<PersonaCreditoCuenta> cuentas_deleteds;

    //---------------------------------------------------------------------
    /**
     * Creates new form PersonaFrame
     *
     * @param owner
     */
    public JPersonaDetailView(IPersonaMediator parent)
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

        if (jtbCuentas.getColumnCount() > 0)
        {
            colNumero = jtbCuentas.getColumnModel().getColumn(0);
            colNumero.setPreferredWidth(100);
            colSaldo = jtbCuentas.getColumnModel().getColumn(1);
            colSaldo.setPreferredWidth(100);
            colLimite = jtbCuentas.getColumnModel().getColumn(2);
            colLimite.setPreferredWidth(100);
            colMoneda = jtbCuentas.getColumnModel().getColumn(3);
            colMoneda.setCellRenderer(new LookUpComboBoxTableCellRenderer<Moneda>(bsMonedasRender));
            colMoneda.setCellEditor(new LookUpComboBoxTableCellEditor<Moneda>(bsMonedasEditor));
            colMoneda.setPreferredWidth(100);
            colTipo = jtbCuentas.getColumnModel().getColumn(4);
            colTipo.setCellRenderer(new LookUpComboBoxTableCellRenderer<Valor>(bsTipoCuentaRender));
            colTipo.setCellEditor(new LookUpComboBoxTableCellEditor<Valor>(bsTipoCuentaEditor));
            colTipo.setPreferredWidth(100);
            colActivo = jtbCuentas.getColumnModel().getColumn(5);
            colActivo.setPreferredWidth(100);
            colNota = jtbCuentas.getColumnModel().getColumn(6);
            colNota.setPreferredWidth(100);

            jtbCuentas.setRowHeight((int) (jtbCuentas.getRowHeight() * 1.5));
        }

        AutoCompleteDocument.enable(jcbTipoPersona);
    }

    //---------------------------------------------------------------------
    private void initAtributes(String esquema)
    {
        localidades = new LocalidadesBusinessLogic("configuracion");
        monedas = new MonedasBusinessLogic(esquema);

        esNuevo = true;
        construidoDirecciones = false;
        construirIdentificadores = false;
        construirMedios = false;
        construidoCuentas = false;
        bsTipoPersona = new TipoPersonasComboBoxModel();
        bsLocalidadRender = new LocalidadComboBoxModel();
        bsLocalidadEditor = new LocalidadComboBoxModel();
        bsTipoMedioRender = new ValorComboBoxModel();
        bsTipoMedioEditor = new ValorComboBoxModel();
        bsTipoCuentaEditor = new ValorComboBoxModel();
        bsTipoCuentaRender = new ValorComboBoxModel();
        bsMonedasEditor = new MonedasComboBoxModel();
        bsMonedasRender = new MonedasComboBoxModel();
        foto_borrada = null;

        construirTiposCuenta();

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

        bsCuentas = new PersonaCreditoCuentasTableModel(new String[]
        {
            "Numero:{numero}", "Saldo:{saldo_actual}", "Limite:{saldo_limite}",
            "Moneda:{id_moneda}", "Tipo:{es_tipo}", "Activo:{es_activo}",
            "Nota:{observacion}"
        });
        bsCuentas.addTableModelListener(this);

        deleteds = new ArrayList<>();
        medios_deleteds = new ArrayList<>();
        cuentas_deleteds = new ArrayList<>();
    }

    //----------------------------------------------------------------------
    private void construirTiposCuenta()
    {
        List<Valor> lstTipos = new ArrayList();
        lstTipos.add(new Valor(0, "Deudora"));
        lstTipos.add(new Valor(1, "Acredora"));

        bsTipoCuentaRender.setData(lstTipos);
        bsTipoCuentaEditor.setData(lstTipos);
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

        ActionListener accountsActionListener
                = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent evt)
            {
                accountToolBar_actionPerformed(evt);
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

        jbAceptar.addActionListener(actionListenerManager);
        jbCancelar.addActionListener(actionListenerManager);
        jtpBody.addChangeListener(changeListenerManager);
        jtoolNuevoDireccion.addActionListener(addressActionListener);
        jtoolEliminarDireccion.addActionListener(addressActionListener);
        jtoolNuevoTelefono.addActionListener(identifiersActionListener);
        jtoolEliminarTelefono.addActionListener(identifiersActionListener);
        jtoolNuevaCuenta.addActionListener(accountsActionListener);
        jtoolEliminarCuenta.addActionListener(accountsActionListener);
        jbtnNuevaImagen.addActionListener(imagesActionListener);
        jbtnEliminarImagen.addActionListener(imagesActionListener);
    }

    //---------------------------------------------------------------------
    private void bodyTabbed_stateChanged(ChangeEvent e)
    {
        if (swCargador == null || swCargador.isDone())
        {

            JTabbedPane pane = (JTabbedPane) e.getSource();
            if (pane.getSelectedComponent() == tabDirecciones && !construidoDirecciones)
            {
                List<Object> parametros = new ArrayList<Object>();
                swCargador = new PersonaDetailCargarSwingWorker();
                if (esNuevo)
                    parametros.add(ONLOAD_LOCALIDADES);
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
                swCargador = new PersonaDetailCargarSwingWorker();

                parametros.add(opLOAD_IDENTIFICADORES);
                parametros.add(elementoActual);

                swCargador.execute(parametros);
                construirIdentificadores = true;
            }
            else if (pane.getSelectedComponent() == tabMediosContacto && !construirMedios)
            {
                List<Object> parametros = new ArrayList<Object>();
                swCargador = new PersonaDetailCargarSwingWorker();

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
            else if (pane.getSelectedComponent() == tabCuentas && !construidoCuentas)
            {
                List<Object> parametros = new ArrayList<Object>();
                swCargador = new PersonaDetailCargarSwingWorker();

                if (esNuevo)
                    parametros.add(opLOAD_MONEDAS);
                else
                {
                    parametros.add(opLOAD_MONEDAS_CUENTAS);
                    parametros.add(elementoActual);
                }
                swCargador.execute(parametros);
                construidoCuentas = true;
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
                tryStopDetailCellEditor(jtbDirecciones);
                tryStopDetailCellEditor(jtbIdentificadores);
                tryStopDetailCellEditor(jtbMedios);

                if (esNuevo)
                    owner.onAcceptNewElement(elementoActual,
                                             bsPersonaDireccion.getData(),
                                             bsPersonaIdentificador.getData(),
                                             bsMediosContacto.getData(),
                                             bsCuentas.getData(),
                                             foto);

                else
                {
                    elementoActual.setModified();
                    owner.onAcceptModifyElement(elementoActual,
                                                bsPersonaDireccion.getData(),
                                                deleteds,
                                                bsPersonaIdentificador.getData(),
                                                bsMediosContacto.getData(),
                                                medios_deleteds,
                                                bsCuentas.getData(),
                                                cuentas_deleteds,
                                                foto,
                                                foto_borrada);
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
                        if (foto.getIdPersona() != elementoActual.getId())
                        {
                            foto.setHasOnePersona(elementoActual);
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
                if (foto.getIdPersona() == elementoActual.getId())
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
    private void addressToolBar_actionPerformed(ActionEvent evt)
    {
        int rowIndex;

        if (evt.getSource() == jtoolNuevoDireccion)
        {
            PersonaDireccion nuevaDireccion = new PersonaDireccion();
            nuevaDireccion.setHasOnePersona(elementoActual);
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
            nuevoTelefono.setHasOnePersona(elementoActual);
            nuevoTelefono.setClave("");

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
    private void accountToolBar_actionPerformed(ActionEvent evt)
    {
        int rowIndex;

        if (evt.getSource() == jtoolNuevaCuenta)
        {
            if (bsMonedasEditor.getData().size() > 0)
            {
                PersonaCreditoCuenta nuevaCuenta = new PersonaCreditoCuenta();
                nuevaCuenta.setHasOnePersona(elementoActual);
                nuevaCuenta.setNumero(String.valueOf(bsCuentas.getRowCount() + 1));
                nuevaCuenta.setSaldoActual(0.0F);
                nuevaCuenta.setSaldoLimite(0.0F);
                nuevaCuenta.setIdMoneda(bsMonedasEditor.getElementAt(0).getId());
                nuevaCuenta.setActivo(true);
                nuevaCuenta.setEsTipo(0);
                nuevaCuenta.setObservacion("");

                rowIndex = bsCuentas.addRow(nuevaCuenta);
                jtbCuentas.setRowSelectionInterval(rowIndex, rowIndex);
                //jtbDirecciones.editCellAt(rowIndex, 0);
            }
            else
                JOptionPane.showMessageDialog(rootPane, "Es necesario haber capturado monedas", "Información", JOptionPane.WARNING_MESSAGE);
        }
        else if (evt.getSource() == jtoolEliminarCuenta)
        {
            if (JOptionPane.showConfirmDialog(this, String.format("Ha seleccionado %d registro(s) para ser eleiminado(s)\n¿Esta seguro de continuar?", jtbCuentas.getSelectedRowCount())) == JOptionPane.OK_OPTION)
            {
                int[] rowsHandlers = jtbCuentas.getSelectedRows();
                List<PersonaCreditoCuenta> selecteds = bsCuentas.removeRows(rowsHandlers);

                for (PersonaCreditoCuenta elemento : selecteds)
                {
                    if (!elemento.isNew())
                        cuentas_deleteds.add(elemento);
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
                        if (((PersonaIdentificadorVista) data).getIdPersona() != elementoActual.getId())
                        {
                            ((PersonaIdentificadorVista) data).setHasOnePersona(elementoActual);
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
        elementoActual = new Persona();
        esNuevo = true;
        this.setTitle("Nuevo usuario");
        bsTipoPersona.setData(owner.obtenerTipoPersonas());
        foto = new PersonaFoto();
        foto.acceptChanges(); //No se ha especificado la foto
        //writeElement(elementoActual);
    }

    //---------------------------------------------------------------------
    public void prepareForModify(Persona elemento)
    {
        this.elementoActual = elemento;
        esNuevo = false;
        this.setTitle(String.format("Modificando %s", elemento.getNombreCompleto()));
        bsTipoPersona.setData(owner.obtenerTipoPersonas());
        writeElement(elemento);
        cargarImagenes(elemento);
    }

    //---------------------------------------------------------------------
    private void cargarImagenes(Persona elemento)
    {
        if (swCargador == null || swCargador.isDone())
        {
            List<Object> parametros = new ArrayList<Object>();
            swCargador = new PersonaDetailCargarSwingWorker();
            //swCargador.addPropertyChangeListener(this);

            parametros.add(opLOAD_MINIATURAS);
            parametros.add(elemento);

            //showTaskHeader("Cargando recursos, espero un momento....", true);
            swCargador.execute(parametros);
        }
    }

    //---------------------------------------------------------------------
    public void writeElement(Persona elemento)
    {
        jtfNombres.setText(elemento.getNombres());
        jtfApellidos.setText(elemento.getApellidos());
        jchkActivo.setSelected(elemento.esActivo());
        jdcNacimiento.setDate(elemento.getNacimiento());
        jcbTipoPersona.setSelectedItem(bsTipoPersona.getElementAt(bsTipoPersona.indexOfValue(elemento.getId_tipo_persona())));
        jtfObser.setText(elemento.getObservaciones());
    }

    //---------------------------------------------------------------------
    private boolean readElement(Persona elemento)
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
            elemento.setId_tipo_pesrona((Integer) bsTipoPersona.getSelectedValue());
            elemento.setActivo(jchkActivo.isSelected());
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
    public void setDisplayCuentas(List<PersonaCreditoCuenta> listaCuentas, List<Moneda> listaMonedas)
    {
        bsMonedasEditor.setData(listaMonedas);
        bsMonedasRender.setData(listaMonedas);
        if (listaCuentas != null)
            bsCuentas.setData(listaCuentas);
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
        tabMediosContacto = new javax.swing.JPanel();
        jbarTelefonos = new javax.swing.JToolBar();
        jtoolNuevoTelefono = new javax.swing.JButton();
        jtoolEliminarTelefono = new javax.swing.JButton();
        jspTelefonos = new javax.swing.JScrollPane();
        jtbMedios = new javax.swing.JTable();
        tabCuentas = new javax.swing.JPanel();
        jbarCuentas = new javax.swing.JToolBar();
        jtoolNuevaCuenta = new javax.swing.JButton();
        jtoolEliminarCuenta = new javax.swing.JButton();
        jspCuentas = new javax.swing.JScrollPane();
        jtbCuentas = new javax.swing.JTable();
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
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMaximizable(true);
        setMinimumSize(new java.awt.Dimension(634, 449));

        jtpBody.setName(""); // NOI18N

        tabGeneral.setName("tabGeneral"); // NOI18N
        tabGeneral.setLayout(new java.awt.BorderLayout());

        jpSeccionImagen.setPreferredSize(new java.awt.Dimension(200, 10));
        jpSeccionImagen.setLayout(new java.awt.BorderLayout());

        jpDimensionImagen.setPreferredSize(new java.awt.Dimension(250, 250));
        jpDimensionImagen.setLayout(new java.awt.BorderLayout());

        jlbImagen.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbImagen.setText("<Sin imagen>");
        jlbImagen.setToolTipText("Imagen");
        jlbImagen.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        jlbImagen.setPreferredSize(new java.awt.Dimension(210, 210));
        jpDimensionImagen.add(jlbImagen, java.awt.BorderLayout.CENTER);

        jpImagenAcciones.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 0));

        jbtnNuevaImagen.setText("Buscar");
        jpImagenAcciones.add(jbtnNuevaImagen);

        jbtnEliminarImagen.setText("Limpiar");
        jpImagenAcciones.add(jbtnEliminarImagen);

        jpDimensionImagen.add(jpImagenAcciones, java.awt.BorderLayout.SOUTH);

        jpSeccionImagen.add(jpDimensionImagen, java.awt.BorderLayout.NORTH);

        tabGeneral.add(jpSeccionImagen, java.awt.BorderLayout.WEST);

        jpSeccionDatos.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jpDatosGenerales.setRequestFocusEnabled(false);
        java.awt.GridBagLayout jpDatosGeneralesLayout = new java.awt.GridBagLayout();
        jpDatosGeneralesLayout.columnWidths = new int[] {0, 5, 0};
        jpDatosGeneralesLayout.rowHeights = new int[] {0, 5, 0, 5, 0, 5, 0, 5, 0};
        jpDatosGenerales.setLayout(jpDatosGeneralesLayout);

        jlbNombre.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbNombre.setLabelFor(jtfNombres);
        jlbNombre.setText("Nombre:");
        jlbNombre.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        jpDatosGenerales.add(jlbNombre, gridBagConstraints);

        jtfNombres.setName("jtfNombres"); // NOI18N
        jtfNombres.setPreferredSize(new java.awt.Dimension(250, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jpDatosGenerales.add(jtfNombres, gridBagConstraints);

        jlbApellidos.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbApellidos.setLabelFor(jtfApellidos);
        jlbApellidos.setText("Apellidos:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        jpDatosGenerales.add(jlbApellidos, gridBagConstraints);

        jtfApellidos.setName("jtfApellidos"); // NOI18N
        jtfApellidos.setPreferredSize(new java.awt.Dimension(250, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jpDatosGenerales.add(jtfApellidos, gridBagConstraints);

        jlbNacimiento.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbNacimiento.setLabelFor(jdcNacimiento);
        jlbNacimiento.setText("Fecha de nacimiento:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        jpDatosGenerales.add(jlbNacimiento, gridBagConstraints);

        jdcNacimiento.setPreferredSize(new java.awt.Dimension(200, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jpDatosGenerales.add(jdcNacimiento, gridBagConstraints);

        jlbTipoPersona.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbTipoPersona.setLabelFor(jcbTipoPersona);
        jlbTipoPersona.setText("Tipo:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        jpDatosGenerales.add(jlbTipoPersona, gridBagConstraints);

        jcbTipoPersona.setModel(bsTipoPersona);
        jcbTipoPersona.setName("jcbTipoPersona"); // NOI18N
        jcbTipoPersona.setPreferredSize(new java.awt.Dimension(250, 22));
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

        jpSeccionDatos.add(jpDatosGenerales);

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

        tabCuentas.setLayout(new java.awt.BorderLayout());

        jbarCuentas.setRollover(true);

        jtoolNuevaCuenta.setText("Nuevo");
        jtoolNuevaCuenta.setFocusable(false);
        jtoolNuevaCuenta.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jtoolNuevaCuenta.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbarCuentas.add(jtoolNuevaCuenta);

        jtoolEliminarCuenta.setText("Eliminar");
        jtoolEliminarCuenta.setToolTipText("");
        jtoolEliminarCuenta.setFocusable(false);
        jtoolEliminarCuenta.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jtoolEliminarCuenta.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbarCuentas.add(jtoolEliminarCuenta);

        tabCuentas.add(jbarCuentas, java.awt.BorderLayout.PAGE_START);

        jtbCuentas.setModel(bsCuentas);
        jtbCuentas.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jspCuentas.setViewportView(jtbCuentas);

        tabCuentas.add(jspCuentas, java.awt.BorderLayout.CENTER);

        jtpBody.addTab("Estado de cuenta", tabCuentas);

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

        getContentPane().add(jpAcciones, java.awt.BorderLayout.PAGE_END);

        jpEncabezado.setBackground(new java.awt.Color(223, 212, 99));
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

        setBounds(0, 0, 658, 490);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jbAceptar;
    private javax.swing.JButton jbCancelar;
    private javax.swing.JToolBar jbarCuentas;
    private javax.swing.JToolBar jbarDirecciones;
    private javax.swing.JToolBar jbarIdentificadores;
    private javax.swing.JToolBar jbarTelefonos;
    private javax.swing.JButton jbtnEliminarImagen;
    private javax.swing.JButton jbtnNuevaImagen;
    private javax.swing.JComboBox jcbTipoPersona;
    private javax.swing.JCheckBox jchkActivo;
    private com.toedter.calendar.JDateChooser jdcNacimiento;
    private javax.swing.JFileChooser jfcImagenes;
    private javax.swing.JLabel jlbApellidos;
    private javax.swing.JLabel jlbIcono;
    private javax.swing.JLabel jlbImagen;
    private javax.swing.JLabel jlbNacimiento;
    private javax.swing.JLabel jlbNombre;
    private javax.swing.JLabel jlbObservaciones;
    private javax.swing.JLabel jlbTipoPersona;
    private javax.swing.JPanel jpAcciones;
    private java.awt.Panel jpAreaMensajes;
    private javax.swing.JPanel jpDatosGenerales;
    private javax.swing.JPanel jpDimensionImagen;
    private javax.swing.JPanel jpEncabezado;
    private javax.swing.JPanel jpImagenAcciones;
    private javax.swing.JPanel jpSeccionDatos;
    private javax.swing.JPanel jpSeccionImagen;
    private javax.swing.JScrollPane jspCuentas;
    private javax.swing.JScrollPane jspDirecciones;
    private javax.swing.JScrollPane jspIdentificadores;
    private javax.swing.JScrollPane jspTelefonos;
    private javax.swing.JTable jtbCuentas;
    private javax.swing.JTable jtbDirecciones;
    private javax.swing.JTable jtbIdentificadores;
    private javax.swing.JTable jtbMedios;
    private javax.swing.JTextField jtfApellidos;
    private javax.swing.JTextField jtfNombres;
    private javax.swing.JTextArea jtfObser;
    private javax.swing.JButton jtoolEliminarCuenta;
    private javax.swing.JButton jtoolEliminarDireccion;
    private javax.swing.JButton jtoolEliminarTelefono;
    private javax.swing.JButton jtoolNuevaCuenta;
    private javax.swing.JButton jtoolNuevoDireccion;
    private javax.swing.JButton jtoolNuevoTelefono;
    private javax.swing.JTabbedPane jtpBody;
    private javax.swing.JPanel tabCuentas;
    private javax.swing.JPanel tabDirecciones;
    private javax.swing.JPanel tabGeneral;
    private javax.swing.JPanel tabIdentificadores;
    private javax.swing.JPanel tabMediosContacto;
    private javax.swing.JPanel tabObservaciones;
    // End of variables declaration//GEN-END:variables

    //------------------------------------------------------------------
    private class PersonaDetailCargarSwingWorker extends SwingWorker<List<Object>, Void>
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
                        arguments.add(owner.obtenerDirecciones((Persona) arguments.get(1)));
                        break;

                    case ONLOAD_LOCALIDADES:
                        arguments.add(localidades.obtenerListaHojas());
                        break;

                    case opLOAD_IDENTIFICADORES:
                        arguments.add(owner.obtenerIdentificadores((Persona) arguments.get(1)));
                        break;

                    case opLOAD_TIPOS_MEDIO:
                        arguments.add(owner.obtenerTipoMedios());
                        break;

                    case opLOAD_MEDIOS_CONTACTO:
                        arguments.add(owner.obtenerTipoMedios());
                        arguments.add(owner.obtenerMedios((Persona) arguments.get(1)));
                        break;

                    case opLOAD_MONEDAS:
                        arguments.add(monedas.obtenerLista());
                        break;

                    case opLOAD_MONEDAS_CUENTAS:
                        arguments.add(owner.obtenerCuentas((Persona) arguments.get(1)));
                        arguments.add(monedas.obtenerLista());
                        break;

                    case opLOAD_MINIATURAS:
                        arguments.add(owner.obtenerFotos((Persona) arguments.get(1)));
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

                        case ONLOAD_LOCALIDADES:
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

                        case opLOAD_MONEDAS:
                            setDisplayCuentas(null, (List<Moneda>) results.get(1));
                            break;

                        case opLOAD_MONEDAS_CUENTAS:
                            setDisplayCuentas((List<PersonaCreditoCuenta>) results.get(2), (List<Moneda>) results.get(3));
                            break;

                        case opLOAD_MINIATURAS:
                            setDisplayPictures((List<PersonaFoto>) results.get(2));
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
