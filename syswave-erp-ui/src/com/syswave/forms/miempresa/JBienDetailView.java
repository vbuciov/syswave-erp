package com.syswave.forms.miempresa;

import com.syswave.entidades.common.Entidad;
import com.syswave.entidades.configuracion.Unidad;
import com.syswave.entidades.miempresa.AreaPrecio;
import com.syswave.entidades.miempresa.Bien;
import com.syswave.entidades.miempresa.BienCompuesto;
import com.syswave.entidades.miempresa.BienVariante;
import com.syswave.entidades.miempresa.BienVarianteFoto;
import com.syswave.entidades.miempresa.ControlInventario;
import com.syswave.entidades.miempresa.ControlPrecio;
import com.syswave.entidades.miempresa.Moneda;
import com.syswave.entidades.miempresa.PlanMantenimiento;
import com.syswave.entidades.miempresa.Valor;
import com.syswave.entidades.miempresa_vista.BienCompuestoVista;
import com.syswave.entidades.miempresa_vista.VarianteIdentificadorVista;
import com.syswave.forms.databinding.BienComboBoxModel;
import com.syswave.forms.databinding.BienCompuestosTableModel;
import com.syswave.forms.databinding.BienVariantesComboBoxModel;
import com.syswave.forms.databinding.ControlInventarioTableModel;
import com.syswave.forms.databinding.ControlPreciosTableModel;
import com.syswave.forms.databinding.MonedasComboBoxModel;
import com.syswave.swing.models.POJOTableModel;
import com.syswave.forms.databinding.AreasPreciosComboBoxModel;
import com.syswave.forms.databinding.PlanMantenimientosTableModel;
import com.syswave.forms.databinding.UnidadComboBoxModel;
import com.syswave.forms.databinding.ValorComboBoxModel;
import com.syswave.forms.databinding.VarianteIdentificadorVistaTableModel;
import com.syswave.swing.table.editors.LookUpComboBoxTableCellEditor;
import com.syswave.swing.table.renders.LookUpComboBoxTableCellRenderer;
import com.syswave.swing.renders.POJOListCellRenderer;
import com.syswave.logicas.miempresa.AreasPreciosBusinessLogic;
import com.syswave.logicas.miempresa.BienVariantesBusinessLogic;
import com.syswave.logicas.miempresa.MonedasBusinessLogic;
import com.syswave.swing.FileTypeFilter;
import com.toedter.calendar.JDateChooserCellEditor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
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
import javax.swing.SwingWorker;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import org.imgscalr.Scalr;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class JBienDetailView extends javax.swing.JInternalFrame implements TableModelListener
{

    private final int opLOAD_COMPUESTOS = 0;
    private final int opLOAD_PARTES = 1;
    private final int opLOAD_IDENTIFICADORES = 2;
    private final int opLOAD_LOTES = 3;
    private final int opLOAD_MONEDAS = 4;
    private final int opLOAD_PRECIOS = 5;
    private final int opLOAD_ACTIVIDADES = 6;
    private final int opLOAD_MINIATURAS = 7;

    IBienMediator parent;
    BienVariante elementoActual;
    BienCompuestosTableModel bsCompuestos;
    VarianteIdentificadorVistaTableModel bsIdentificadores;
    ValorComboBoxModel bsFormasInventario;
    ValorComboBoxModel bsFormasMantenimiento;
    BienComboBoxModel bsTipoBien;
    UnidadComboBoxModel bsUnidadMasa;
    UnidadComboBoxModel bsUnidadLongitud;

    BienVariantesBusinessLogic partes;
    BienVariantesComboBoxModel bsPartesRender;
    BienVariantesComboBoxModel bsPartesEditor;

    ControlInventarioTableModel bsLotes;
    PlanMantenimientosTableModel bsActividades;

    MonedasBusinessLogic monedas;
    MonedasComboBoxModel bsMonedasRender;
    MonedasComboBoxModel bsMonedasEditor;
    /*ValorComboBoxModel bsTipoPrecioRender;
    ValorComboBoxModel bsTipoPrecioEditor;*/
    private AreasPreciosBusinessLogic areas_precios;
    private AreasPreciosComboBoxModel bsAreasPreciosEditor;
    private AreasPreciosComboBoxModel bsAreasPreciosRender;

    ControlPreciosTableModel bsPrecios;

    private BienVarianteFoto foto, foto_borrada;

    boolean esNuevo, construidoCompuestos, construirIdentificadores,
            construirLotes, construirPrecios, construidoActividades;

    BienDetailCargarSwingWorker swCargador;
    List<BienCompuesto> deleteds;
    List<ControlInventario> lote_deleteds;
    List<ControlPrecio> precio_deleted;
    List<PlanMantenimiento> actividades_deleted;
    TableColumn colParteFormal, colCantidad, colValor,
            colLlave, colDescripcion, colLote,
            colExistencia, colFechaEntrada, colMonedas,
            colDescripcionPrecio, colTipoPrecio, colBase,
            colMargen, colFactor, colFinal, colActividad,
            colActivo, colAreaPrecio;

    /**
     * Creates new form JBienDetailView
     *
     * @param owner
     */
    public JBienDetailView(IBienMediator owner)
    {
        initAtributes(owner.obtenerOrigenDato());
        initComponents();
        initEvents();
        parent = owner;
        
        jfcImagenes.setFileFilter(new FileTypeFilter(".jpg", "JPG"));
        jfcImagenes.setFileFilter(new FileTypeFilter(".gif", "GIF"));
        jfcImagenes.setFileFilter(new FileTypeFilter(".png", "PNG"));

        if (jtbPartes.getColumnCount() > 0)
        {
            colParteFormal = jtbPartes.getColumnModel().getColumn(0);
            colParteFormal.setCellRenderer(new LookUpComboBoxTableCellRenderer<BienVariante>(bsPartesRender));
            colParteFormal.setCellEditor(new LookUpComboBoxTableCellEditor<BienVariante>(bsPartesEditor));
            colParteFormal.setPreferredWidth(250);

            colCantidad = jtbPartes.getColumnModel().getColumn(1);
            colCantidad.setPreferredWidth(150);

            jtbPartes.setRowHeight((int) (jtbPartes.getRowHeight() * 1.5));
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

        if (jtbLotes.getColumnCount() > 0)
        {
            colLote = jtbLotes.getColumnModel().getColumn(0);
            colLote.setPreferredWidth(250);

            colExistencia = jtbLotes.getColumnModel().getColumn(1);

            colFechaEntrada = jtbLotes.getColumnModel().getColumn(2);
            colFechaEntrada.setCellEditor(new JDateChooserCellEditor());
            colFechaEntrada.setPreferredWidth(150);

            jtbLotes.setRowHeight((int) (jtbLotes.getRowHeight() * 1.5));
        }

        if (jtbPrecios.getColumnCount() > 0)
        {
            colDescripcionPrecio = jtbPrecios.getColumnModel().getColumn(0);
            colDescripcionPrecio.setPreferredWidth(130);

            colBase = jtbPrecios.getColumnModel().getColumn(1);

            colMargen = jtbPrecios.getColumnModel().getColumn(2);

            colFactor = jtbPrecios.getColumnModel().getColumn(3);

            colFinal = jtbPrecios.getColumnModel().getColumn(4);

            colMonedas = jtbPrecios.getColumnModel().getColumn(5);
            colMonedas.setCellEditor(new LookUpComboBoxTableCellEditor<Moneda>(bsMonedasEditor));
            colMonedas.setCellRenderer(new LookUpComboBoxTableCellRenderer<Moneda>(bsMonedasRender));
            colMonedas.setPreferredWidth(80);

            /*colTipoPrecio = jtbPrecios.getColumnModel().getColumn(6);
            colTipoPrecio.setCellEditor(new LookUpComboBoxTableCellEditor<Valor>(bsTipoPrecioEditor));
            colTipoPrecio.setCellRenderer(new LookUpComboBoxTableCellRenderer<Valor>(bsTipoPrecioRender));
            colTipoPrecio.setPreferredWidth(80);*/

            colAreaPrecio = jtbPrecios.getColumnModel().getColumn(6);
            colAreaPrecio.setCellEditor(new LookUpComboBoxTableCellEditor<AreaPrecio>(bsAreasPreciosEditor));
            colAreaPrecio.setCellRenderer(new LookUpComboBoxTableCellRenderer<AreaPrecio>(bsAreasPreciosRender));
            colAreaPrecio.setPreferredWidth(200);

            jtbPrecios.setRowHeight((int) (jtbPrecios.getRowHeight() * 1.5));
        }

        if (jtbPlanMantenimiento.getColumnCount() > 0)
        {
            colActividad = jtbPlanMantenimiento.getColumnModel().getColumn(0);
            colActividad.setPreferredWidth(400);

            colActivo = jtbPlanMantenimiento.getColumnModel().getColumn(1);
            colActivo.setPreferredWidth(70);
        }
    }

    //---------------------------------------------------------------------
    private void initAtributes(String esquema)
    {
        partes = new BienVariantesBusinessLogic(esquema);
        monedas = new MonedasBusinessLogic(esquema);
        areas_precios = new AreasPreciosBusinessLogic(esquema);

        esNuevo = true;
        construidoCompuestos = false;
        construirIdentificadores = false;
        construirLotes = false;
        construirPrecios = false;
        construidoActividades = false;
        bsFormasInventario = new ValorComboBoxModel();
        bsFormasMantenimiento = new ValorComboBoxModel();
        bsTipoBien = new BienComboBoxModel();
        bsUnidadMasa = new UnidadComboBoxModel();
        bsUnidadLongitud = new UnidadComboBoxModel();
        bsPartesRender = new BienVariantesComboBoxModel();
        bsPartesEditor = new BienVariantesComboBoxModel();
        bsMonedasEditor = new MonedasComboBoxModel();
        bsMonedasRender = new MonedasComboBoxModel();
        /*bsTipoPrecioRender = new ValorComboBoxModel();
        bsTipoPrecioEditor = new ValorComboBoxModel();*/
        bsAreasPreciosRender = new AreasPreciosComboBoxModel();
        bsAreasPreciosEditor = new AreasPreciosComboBoxModel();
        foto_borrada = null;

        bsPrecios = new ControlPreciosTableModel(new String[]
        {
            "Descripción:{descripcion}", "Costo Directo:{costo_directo}", "Margen:{margen}",
            "Factor %:{factor}", "Final:{precio_final}", "Moneda:{id_moneda}",
            "Area Precio:{id_area_precio}"
        });
        bsPrecios.addTableModelListener(this);

        bsLotes = new ControlInventarioTableModel(new String[]
        {
            "Lote y/o Marca:{lote}", "Existencia:{existencia}",
            "Fecha Entrada:{fecha_entrada}"
        });
        bsLotes.addTableModelListener(this);

        bsActividades = new PlanMantenimientosTableModel(new String[]
        {
            "Actividad:{actividad}", "Activo:{es_activo}"
        });
        bsActividades.addTableModelListener(this);

        bsCompuestos = new BienCompuestosTableModel(new String[]
        {
            "Parte:{id_bien_parte}", "contiene:{cantidad}"
        });
        bsCompuestos.addTableModelListener(this);

        bsIdentificadores = new VarianteIdentificadorVistaTableModel(new String[]
        {
            "Valor:{valor}", "Tipo:{llave}", "Descripción:{descripcion}"
        });
        bsIdentificadores.addTableModelListener(this);

        deleteds = new ArrayList<>();
        lote_deleteds = new ArrayList<>();
        precio_deleted = new ArrayList<>();
        actividades_deleted = new ArrayList<>();
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
                = new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent evt)
                    {
                        composeToolBar_actionPerformed(evt);
                    }
                };

        ActionListener lotesActionListener
                = new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent evt)
                    {
                        lotesToolBar_actionPerformed(evt);
                    }
                };

        ActionListener preciosActionListener
                = new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent evt)
                    {
                        preciosToolBar_actionPerformed(evt);
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

        ActionListener actidavesActionListener
                = new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent evt)
                    {
                        actividadesToolBar_actionPerformed(evt);
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
        jtoolNuevoCompuesto.addActionListener(composeActionListener);
        jtoolEliminarCompuesto.addActionListener(composeActionListener);
        jtoolNuevoLote.addActionListener(lotesActionListener);
        jtoolEliminarLote.addActionListener(lotesActionListener);
        jtoolNuevoPrecio.addActionListener(preciosActionListener);
        jtoolEliminarPrecio.addActionListener(preciosActionListener);
        jtoolNuevaActividad.addActionListener(actidavesActionListener);
        jtoolEliminarActividad.addActionListener(actidavesActionListener);
        jbtnNuevaImagen.addActionListener(imagesActionListener);
        jbtnEliminarImagen.addActionListener(imagesActionListener);
    }

    //---------------------------------------------------------------------
    private void bodyTabbed_stateChanged(ChangeEvent e)
    {
        if (swCargador == null || swCargador.isDone())
        {
            JTabbedPane pane = (JTabbedPane) e.getSource();
            if (pane.getSelectedComponent() == tabIngredientes && !construidoCompuestos)
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

            else if (pane.getSelectedComponent() == tabLotes && !construirLotes)
            {
                List<Object> parametros = new ArrayList<Object>();
                swCargador = new BienDetailCargarSwingWorker();

                parametros.add(opLOAD_LOTES);
                parametros.add(elementoActual);

                swCargador.execute(parametros);
                construirLotes = true;
            }

            else if (pane.getSelectedComponent() == tabPrecios && !construirPrecios)
            {
                List<Object> parametros = new ArrayList<Object>();
                swCargador = new BienDetailCargarSwingWorker();

                if (esNuevo)
                    parametros.add(opLOAD_MONEDAS);
                else
                {
                    parametros.add(opLOAD_PRECIOS);
                    parametros.add(elementoActual);
                }

                swCargador.execute(parametros);
                construirPrecios = true;
            }

            else if (pane.getSelectedComponent() == tabPlanMantenimiento && !construidoActividades)
            {
                List<Object> parametros = new ArrayList<Object>();
                swCargador = new BienDetailCargarSwingWorker();

                parametros.add(opLOAD_ACTIVIDADES);
                parametros.add(elementoActual);

                swCargador.execute(parametros);
                construidoActividades = true;
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
                tryStopDetailCellEditor(jtbPartes);
                tryStopDetailCellEditor(jtbIdentificadores);
                tryStopDetailCellEditor(jtbLotes);
                tryStopDetailCellEditor(jtbPlanMantenimiento);
                tryStopDetailCellEditor(jtbPrecios);

                if (esNuevo)
                    parent.onAcceptNewElement(elementoActual, bsCompuestos.getData(),
                                              bsIdentificadores.getData(), bsLotes.getData(),
                                              bsActividades.getData(), bsPrecios.getData(),
                                              foto);

                else
                {
                    elementoActual.setModified();
                    parent.onAcceptModifyElement(elementoActual, bsCompuestos.getData(), deleteds,
                                                 bsIdentificadores.getData(), bsLotes.getData(), lote_deleteds,
                                                 bsActividades.getData(), actividades_deleted,
                                                 bsPrecios.getData(), precio_deleted, foto, foto_borrada);
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
                        if (foto.getIdVariante() != elementoActual.getId())
                        {
                            foto.setHasOnePresentacion(elementoActual);
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
                if (foto.getIdVariante() == elementoActual.getId())
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
    private void composeToolBar_actionPerformed(ActionEvent evt)
    {
        int rowIndex;

        if (evt.getSource() == jtoolNuevoCompuesto)
        {

            BienCompuestoVista nuevaDireccion = new BienCompuestoVista();
            nuevaDireccion.setHasOneBienVarianteFormal(elementoActual);
            nuevaDireccion.setCantidad(1);
            nuevaDireccion.setNombreUnidad("");
            rowIndex = bsCompuestos.addRow(nuevaDireccion);
            jtbPartes.setRowSelectionInterval(rowIndex, rowIndex);
            //jtbDirecciones.editCellAt(rowIndex, 0);
        }

        else if (evt.getSource() == jtoolEliminarCompuesto)
        {
            if (JOptionPane.showConfirmDialog(this, String.format("Ha seleccionado %d registro(s) para ser eleiminado(s)\n¿Esta seguro de continuar?", jtbPartes.getSelectedRowCount())) == JOptionPane.OK_OPTION)
            {
                int[] rowsHandlers = jtbPartes.getSelectedRows();
                List<BienCompuestoVista> selecteds = bsCompuestos.removeRows(rowsHandlers);

                for (BienCompuestoVista elemento : selecteds)
                {
                    if (!elemento.isNew())
                        deleteds.add(elemento);
                }
            }
        }
    }

    //---------------------------------------------------------------------
    private void lotesToolBar_actionPerformed(ActionEvent evt)
    {
        int rowIndex;

        if (evt.getSource() == jtoolNuevoLote)
        {
            ControlInventario nuevoLote = new ControlInventario();
            nuevoLote.setHasOneBienVariante(elementoActual);
            nuevoLote.setExistencia(1);
            nuevoLote.setLote("N/A");
            nuevoLote.setMinimo(1);
            nuevoLote.setMaximo(1000);
            nuevoLote.setReorden(500);
            nuevoLote.setFecha_entrada(Calendar.getInstance().getTime());
            rowIndex = bsLotes.addRow(nuevoLote);
            jtbLotes.setRowSelectionInterval(rowIndex, rowIndex);
            //jtbDirecciones.editCellAt(rowIndex, 0);
        }

        else if (evt.getSource() == jtoolEliminarLote)
        {
            if (JOptionPane.showConfirmDialog(this, String.format("Ha seleccionado %d registro(s) para ser eleiminado(s)\n¿Esta seguro de continuar?", jtbLotes.getSelectedRowCount())) == JOptionPane.OK_OPTION)
            {
                int[] rowsHandlers = jtbLotes.getSelectedRows();
                List<ControlInventario> selecteds = bsLotes.removeRows(rowsHandlers);

                for (ControlInventario elemento : selecteds)
                {
                    if (!elemento.isNew())
                        lote_deleteds.add(elemento);
                }
            }
        }
    }

    //---------------------------------------------------------------------
    private void actividadesToolBar_actionPerformed(ActionEvent evt)
    {
        int rowIndex;

        if (evt.getSource() == jtoolNuevaActividad)
        {
            PlanMantenimiento nuevoLote = new PlanMantenimiento();
            nuevoLote.setHasOnePresentacion(elementoActual);
            nuevoLote.setActividad("");
            nuevoLote.setActivo(true);

            rowIndex = bsActividades.addRow(nuevoLote);
            jtbPlanMantenimiento.setRowSelectionInterval(rowIndex, rowIndex);
            //jtbDirecciones.editCellAt(rowIndex, 0);
        }

        else if (evt.getSource() == jtoolEliminarActividad)
        {
            if (JOptionPane.showConfirmDialog(this, String.format("Ha seleccionado %d registro(s) para ser eleiminado(s)\n¿Esta seguro de continuar?", jtbPlanMantenimiento.getSelectedRowCount())) == JOptionPane.OK_OPTION)
            {
                int[] rowsHandlers = jtbPlanMantenimiento.getSelectedRows();
                List<PlanMantenimiento> selecteds = bsActividades.removeRows(rowsHandlers);

                for (PlanMantenimiento elemento : selecteds)
                {
                    if (!elemento.isNew())
                        actividades_deleted.add(elemento);
                }
            }
        }
    }

    //---------------------------------------------------------------------
    private void preciosToolBar_actionPerformed(ActionEvent evt)
    {
        int rowIndex;

        if (evt.getSource() == jtoolNuevoPrecio)
        {
            if (bsMonedasEditor.getData().size() > 0)
            {
                ControlPrecio nuevoLote = new ControlPrecio();
                nuevoLote.setHasOneBienVariante(elementoActual);
                nuevoLote.setIdMoneda(bsMonedasEditor.getElementAt(0).getId());
                nuevoLote.setCostoDirecto(1);
                nuevoLote.setDescripcion("PRECIO REGULAR");
                //nuevoLote.setEsTipo(1);
                nuevoLote.setFactor(100);
                nuevoLote.setMargen(1);
                nuevoLote.setPrecioFinal(2);
                rowIndex = bsPrecios.addRow(nuevoLote);
                jtbPrecios.setRowSelectionInterval(rowIndex, rowIndex);
                //jtbDirecciones.editCellAt(rowIndex, 0);
            }
            else
                JOptionPane.showMessageDialog(rootPane, "Es necesario haber capturado monedas", "Información", JOptionPane.WARNING_MESSAGE);
        }

        else if (evt.getSource() == jtoolEliminarPrecio)
        {
            if (JOptionPane.showConfirmDialog(this, String.format("Ha seleccionado %d registro(s) para ser eleiminado(s)\n¿Esta seguro de continuar?", jtbPrecios.getSelectedRowCount())) == JOptionPane.OK_OPTION)
            {
                int[] rowsHandlers = jtbPrecios.getSelectedRows();
                List<ControlPrecio> selecteds = bsPrecios.removeRows(rowsHandlers);

                for (ControlPrecio elemento : selecteds)
                {
                    if (!elemento.isNew())
                        precio_deleted.add(elemento);
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
                    if (model == bsIdentificadores)
                    {
                        if (((VarianteIdentificadorVista) data).getIdVariante() != elementoActual.getId())
                        {
                            ((VarianteIdentificadorVista) data).setHasOneVariante(elementoActual);
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
        elementoActual = new BienVariante();
        esNuevo = true;
        this.setTitle("Nuevo");
        bsTipoBien.setData(parent.obtenerTiposBienes());
       /* bsUnidadMasa.setData(parent.obtenerUnidadesMasa());
        bsUnidadLongitud.setData(parent.obtenerUnidadesLongitud());*/

        //Valores iniciales
        elementoActual.setId(0); //Marcamos un id temporal, menor que 1, y distinto al valor vacio.
        elementoActual.setNivel(0);
        /*elementoActual.setMasa(0);
        if (bsUnidadMasa.getData().size() > 0)
            elementoActual.setIdUnidadMasa(bsUnidadMasa.getData().get(0).getId());
        elementoActual.setAncho(0);
        elementoActual.setAlto(0);
        elementoActual.setLargo(0);
        if (bsUnidadLongitud.getData().size() > 0)
            elementoActual.setIdUnidadLongitud(bsUnidadLongitud.getData().get(0).getId());*/
        if (bsTipoBien.getData().size() > 0)
            elementoActual.setIdBien(bsTipoBien.getData().get(0).getId());
        elementoActual.setInventarioCcomo(0);
        elementoActual.setMantenimientoComo(0);
        elementoActual.setDescripcion("");
        elementoActual.setValorEsperado(0);
        foto = new BienVarianteFoto();
        foto.acceptChanges(); //No se ha especificado la foto
        writeElement(elementoActual);
    }

    //---------------------------------------------------------------------
    public void prepareForModify(BienVariante elemento)
    {
        this.elementoActual = elemento;
        esNuevo = false;
        this.setTitle(String.format("Modificando %s", elemento.getDescripcion()));
        bsTipoBien.setData(parent.obtenerTiposBienes());
        /*bsUnidadMasa.setData(parent.obtenerUnidadesMasa());
        bsUnidadLongitud.setData(parent.obtenerUnidadesLongitud());*/
        writeElement(elemento);
        cargarImagenes(elemento);
    }

    //---------------------------------------------------------------------
    private void cargarImagenes(BienVariante elemento)
    {
        if (swCargador == null || swCargador.isDone())
        {
            List<Object> parametros = new ArrayList<Object>();
            swCargador = new BienDetailCargarSwingWorker();
            //swCargador.addPropertyChangeListener(this);

            parametros.add(opLOAD_MINIATURAS);
            parametros.add(elemento);

            //showTaskHeader("Cargando recursos, espero un momento....", true);
            swCargador.execute(parametros);
        }
    }

    //---------------------------------------------------------------------
    public void writeElement(BienVariante elemento)
    {
        jcbTipoBien.setSelectedItem(bsTipoBien.getElementAt(bsTipoBien.indexOfValue(elemento.getIdBien())));
        jtfDescripcion.setText(elemento.getDescripcion());
        //jtfMasaPeso.setValue(elemento.getMasa());
        //jcbUnidadMasa.setSelectedItem(bsUnidadMasa.getElementAt(bsUnidadMasa.indexOfValue(elemento.getIdUnidadMasa())));
        jchkActivo.setSelected(elemento.esActivo());
        jchkInventario.setSelected(elemento.esInventario());
        jchkComercializar.setSelected(elemento.EsComercializar());
        //jtfAncho.setValue(elemento.getAncho());
        //jtfAlto.setValue(elemento.getAlto());
        //jtfLargo.setValue(elemento.getLargo());
        //jcbUnidadLongitud.setSelectedItem(bsUnidadLongitud.getElementAt(bsUnidadLongitud.indexOfValue(elemento.getIdUnidadLongitud())));
        jftValorEsperado.setValue(elemento.getValorEsperado());
    }

    //---------------------------------------------------------------------
    private boolean readElement(BienVariante elemento)
    {
        boolean resultado = false;
        String mensaje = "";

        if (!jtfDescripcion.getText().isEmpty())
        {
            resultado = true;
            elemento.setIdBien((int) bsTipoBien.getSelectedValue());
            elemento.setDescripcion(jtfDescripcion.getText());
            /*elemento.setMasa((float) jtfMasaPeso.getValue());
            elemento.setIdUnidadMasa((int) bsUnidadMasa.getSelectedValue());*/
            elemento.setEsActivo(jchkActivo.isSelected());
            elemento.setEsInventario(jchkInventario.isSelected());
            elemento.setEsComercializar(jchkComercializar.isSelected());
            /*elemento.setAncho((float) jtfAncho.getValue());
            elemento.setAlto((float) jtfAlto.getValue());
            elemento.setLargo((float) jtfLargo.getValue());
            elemento.setIdUnidadLongitud((int) bsUnidadLongitud.getSelectedValue());*/
            if (construirLotes)
                elemento.setInventarioCcomo((int) bsFormasInventario.getSelectedValue());

            if (!elemento.isSet())
                elemento.setModified();

            if (construidoActividades)
                elemento.setMantenimientoComo((int) bsFormasMantenimiento.getSelectedValue());

            elemento.setValorEsperado((int) jftValorEsperado.getValue());
        }

        else
            mensaje = "Asegurese de proporcionar la descripcion";

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
    public void setDisplayData(List<BienVariante> listPartes, List<BienCompuestoVista> listaVariantes)
    {
        bsPartesEditor.setData(listPartes);
        bsPartesRender.setData(listPartes);
        if (listaVariantes != null)
            bsCompuestos.setData(listaVariantes);
    }

    //----------------------------------------------------------------------
    public void setDisplayData(List<VarianteIdentificadorVista> listaIdentificadores)
    {
        if (listaIdentificadores != null)
            bsIdentificadores.setData(listaIdentificadores);
    }

    //----------------------------------------------------------------------
    public void setDisplayLote(List<Valor> listaFormas, List<ControlInventario> listaLotes)
    {
        bsFormasInventario.setData(listaFormas);

        jcbInventarioComo.setSelectedItem(bsFormasInventario.getElementAt(bsFormasInventario.indexOfValue(elementoActual.getInventarioCcomo())));

        if (listaLotes != null)
            bsLotes.setData(listaLotes);
    }

    //----------------------------------------------------------------------
    public void setDisplayActividades(List<Valor> listaFormas, List<PlanMantenimiento> listaActividades)
    {
        bsFormasMantenimiento.setData(listaFormas);

        jcbMantenimientoComo.setSelectedItem(bsFormasMantenimiento.getElementAt(bsFormasMantenimiento.indexOfValue(elementoActual.getMantenimientoComo())));

        if (listaActividades != null)
            bsActividades.setData(listaActividades);
    }

    //----------------------------------------------------------------------
    public void setDisplayPrecio(/*List<Valor> listaTipos,*/
                                 List<Moneda> listaMonedas, 
                                 List<AreaPrecio> listaAreas,
                                 List<ControlPrecio> listaPrecios)
    {
        /*bsTipoPrecioEditor.setData(listaTipos);
        bsTipoPrecioRender.setData(listaTipos);*/

        bsMonedasEditor.setData(listaMonedas);
        bsMonedasRender.setData(listaMonedas);
        
        bsAreasPreciosEditor.setData(listaAreas);
        bsAreasPreciosRender.setData(listaAreas);

        if (listaPrecios != null)
            bsPrecios.setData(listaPrecios);
    }

    //-----------------------------------------------------------------------
    public void setDisplayPictures(List<BienVarianteFoto> listaFotos)
    {
        if (listaFotos.size() > 0)
        {
            foto = listaFotos.get(0);
            jlbImagen.setIcon(new ImageIcon(foto.getMiniatura()));
            jlbImagen.setText("");
        }
        else
            foto = new BienVarianteFoto();

        foto.acceptChanges();
    }

   //----------------------------------------------------------------------
   /*public void jtbPartes_onCellValueChanged(ChangeEvent e, TableCellEditor editor)
     {
     if (jtbPartes.getEditingColumn() == 0 && editor instanceof LookUpComboBoxTableCellEditor)
     {
     ComboBoxModel model = ((LookUpComboBoxTableCellEditor)editor).getModel();
     if (model instanceof POJOComboBoxModel)
     {
     POJOComboBoxModel<BienVariante> POJOModel = (POJOComboBoxModel<BienVariante>)model;
             
     if (POJOModel.getCurrent() != null)
     jtbPartes.setValueAt(POJOModel.getCurrent().getFk_variante_id_unidad_masa().getNombre(), jtbPartes.getEditingRow(),  jtbPartes.getColumnCount() - 1);
     }
     }
     }*/
    //----------------------------------------------------------------------
    public void jtbPrecios_onCellValueChanged(ChangeEvent e, TableCellEditor editor)
    {
        TableColumnModel columnas = jtbPrecios.getColumnModel();
        int modelColIndex = jtbPrecios.convertColumnIndexToModel(jtbPrecios.getEditingColumn());
        int modelRowIndex = jtbPrecios.convertRowIndexToModel(jtbPrecios.getEditingRow());
        TableColumn actual = columnas.getColumn(modelColIndex);
        ControlPrecio seleccionado = bsPrecios.getElementAt(modelRowIndex);

        if (actual == colBase)
        {
            float base = (float) editor.getCellEditorValue();

            bsPrecios.setValueAt(base * seleccionado.getFactor() / 100.0000F, modelRowIndex, colMargen.getModelIndex());
            //seleccionado.setMargen(base * seleccionado.getFactor() / 100.0000F);

            bsPrecios.setValueAt(base + seleccionado.getMargen(), modelRowIndex, colFinal.getModelIndex());
            //seleccionado.setMercado(base + seleccionado.getMargen());
        }

        else if (actual == colMargen)
        {
            float margen = (float) editor.getCellEditorValue();
            float base_temp = seleccionado.getCostoDirecto();

            if (base_temp > 0)
                bsPrecios.setValueAt((int) (margen * 100.0000F / base_temp), modelRowIndex, colFactor.getModelIndex());
            //seleccionado.setFactor((int)(margen * 100.0000F / base_temp));

            else
                bsPrecios.setValueAt(0, modelRowIndex, colFactor.getModelIndex());
            //seleccionado.setFactor(0);

            //seleccionado.setMercado(base_temp + margen);
            bsPrecios.setValueAt(base_temp + margen, modelRowIndex, colFinal.getModelIndex());

        }

        else if (actual == colFactor)
        {
            int factor = (int) editor.getCellEditorValue();

            //seleccionado.setMargen(factor / 100.0000F * seleccionado.getValor());
            bsPrecios.setValueAt(factor / 100.0000F * seleccionado.getCostoDirecto(), modelRowIndex, colMargen.getModelIndex());
            //seleccionado.setMercado(seleccionado.getMargen() + seleccionado.getValor());
            bsPrecios.setValueAt(seleccionado.getMargen() + seleccionado.getCostoDirecto(), modelRowIndex, colFinal.getModelIndex());
        }

        else if (actual == colFinal)
        {
            float pfinal = (float) editor.getCellEditorValue();
            float base_temp = seleccionado.getCostoDirecto();

            bsPrecios.setValueAt(pfinal - base_temp, modelRowIndex, colMargen.getModelIndex());
            //seleccionado.setMargen(pfinal - base_temp);
            if (base_temp > 0)
                bsPrecios.setValueAt((int) (seleccionado.getMargen() * 100.0000F / base_temp), modelRowIndex, colFactor.getModelIndex());
            //seleccionado.setFactor((int)(seleccionado.getMargen() * 100.0000F / base_temp));
            else
                bsPrecios.setValueAt(0, modelRowIndex, colFactor.getModelIndex());
            //seleccionado.setFactor(0);
        }

        //  grid.setValueAt(POJOModel.getCurrent().getFk_variante_id_unidad_masa().getNombre(), jtbPartes.getEditingRow(),  jtbPartes.getColumnCount() - 1);
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
        jpDatos1 = new javax.swing.JPanel();
        jlbTipoBien = new javax.swing.JLabel();
        jcbTipoBien = new javax.swing.JComboBox();
        jlbDescripcion = new javax.swing.JLabel();
        jtfDescripcion = new javax.swing.JTextField();
        jlbActivo = new javax.swing.JLabel();
        jchkActivo = new javax.swing.JCheckBox();
        jlbInventario = new javax.swing.JLabel();
        jchkInventario = new javax.swing.JCheckBox();
        jlbComercializar = new javax.swing.JLabel();
        jchkComercializar = new javax.swing.JCheckBox();
        tabVolumetria = new javax.swing.JPanel();
        jpDatos2 = new javax.swing.JPanel();
        jlbMasaPeso = new javax.swing.JLabel();
        jtfMasaPeso = new JFormattedTextField(new Float(1.0000F));
        jlbUnidadMasa = new javax.swing.JLabel();
        jcbUnidadMasa = new javax.swing.JComboBox();
        jlbAncho = new javax.swing.JLabel();
        jtfAncho = new JFormattedTextField(new Float(1.0000F));
        jlbAlto = new javax.swing.JLabel();
        jtfAlto = new JFormattedTextField(new Float(1.0000F));
        jlbLargo = new javax.swing.JLabel();
        jtfLargo = new JFormattedTextField(new Float(1.0000F));
        jlbUnidadLongitud = new javax.swing.JLabel();
        jcbUnidadLongitud = new javax.swing.JComboBox();
        tabIngredientes = new javax.swing.JPanel();
        jbarDirecciones = new javax.swing.JToolBar();
        jtoolNuevoCompuesto = new javax.swing.JButton();
        jtoolEliminarCompuesto = new javax.swing.JButton();
        jspDirecciones = new javax.swing.JScrollPane();
        jtbPartes = new javax.swing.JTable();
        tabIdentificadores = new javax.swing.JPanel();
        jbarIdentificadores = new javax.swing.JToolBar();
        jspIdentificadores = new javax.swing.JScrollPane();
        jtbIdentificadores = new javax.swing.JTable();
        tabLotes = new javax.swing.JPanel();
        jbarControlInventario = new javax.swing.JToolBar();
        jtoolNuevoLote = new javax.swing.JButton();
        jtoolEliminarLote = new javax.swing.JButton();
        jspControlInventario = new javax.swing.JScrollPane();
        jtbLotes = new javax.swing.JTable();
        jpInventarioComo = new javax.swing.JPanel();
        jlbInventarioComo = new javax.swing.JLabel();
        jcbInventarioComo = new javax.swing.JComboBox();
        tabPrecios = new javax.swing.JPanel();
        jbarPrecios = new javax.swing.JToolBar();
        jtoolNuevoPrecio = new javax.swing.JButton();
        jtoolEliminarPrecio = new javax.swing.JButton();
        jspDirecciones2 = new javax.swing.JScrollPane();
        jtbPrecios = new JTable(bsPrecios)
        {
            @Override // Always selectAll()
            public void editingStopped(ChangeEvent e)
            {
                jtbPrecios_onCellValueChanged(e, getCellEditor());
                super.editingStopped(e);
            }
        };
        tabPlanMantenimiento = new javax.swing.JPanel();
        jbarPlanMantenimiento = new javax.swing.JToolBar();
        jtoolNuevaActividad = new javax.swing.JButton();
        jtoolEliminarActividad = new javax.swing.JButton();
        jspPlanMantenimiento = new javax.swing.JScrollPane();
        jtbPlanMantenimiento = new javax.swing.JTable();
        jpMantenimientoComo = new javax.swing.JPanel();
        jlbMantenimientoComo = new javax.swing.JLabel();
        jcbMantenimientoComo = new javax.swing.JComboBox();
        jlbEsperar = new javax.swing.JLabel();
        jftValorEsperado = new JFormattedTextField(0);
        jpAcciones = new javax.swing.JPanel();
        jbCancelar = new javax.swing.JButton();
        jbAceptar = new javax.swing.JButton();
        jpEncabezado = new javax.swing.JPanel();
        jlbIcono = new javax.swing.JLabel();
        jpAreaMensajes = new java.awt.Panel();

        jfcImagenes.setDialogTitle("");

        setClosable(true);
        setMaximizable(true);
        setMinimumSize(new java.awt.Dimension(650, 450));

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

        java.awt.GridBagLayout jpDatos1Layout = new java.awt.GridBagLayout();
        jpDatos1Layout.columnWidths = new int[] {0, 5, 0};
        jpDatos1Layout.rowHeights = new int[] {0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0};
        jpDatos1.setLayout(jpDatos1Layout);

        jlbTipoBien.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbTipoBien.setLabelFor(jcbTipoBien);
        jlbTipoBien.setText("Grupo:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        jpDatos1.add(jlbTipoBien, gridBagConstraints);

        jcbTipoBien.setModel(bsTipoBien);
        jcbTipoBien.setPreferredSize(new java.awt.Dimension(250, 25));
        jcbTipoBien.setRenderer(new POJOListCellRenderer<Bien>());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jpDatos1.add(jcbTipoBien, gridBagConstraints);

        jlbDescripcion.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbDescripcion.setLabelFor(jtfDescripcion);
        jlbDescripcion.setText("Descripción:");
        jlbDescripcion.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        jpDatos1.add(jlbDescripcion, gridBagConstraints);

        jtfDescripcion.setName("jtfDescripcion"); // NOI18N
        jtfDescripcion.setPreferredSize(new java.awt.Dimension(250, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jpDatos1.add(jtfDescripcion, gridBagConstraints);

        jlbActivo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbActivo.setLabelFor(jchkActivo);
        jlbActivo.setText("Activo:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        jpDatos1.add(jlbActivo, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jpDatos1.add(jchkActivo, gridBagConstraints);

        jlbInventario.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbInventario.setLabelFor(jchkInventario);
        jlbInventario.setText("Controlar Inventario:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        jpDatos1.add(jlbInventario, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jpDatos1.add(jchkInventario, gridBagConstraints);

        jlbComercializar.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbComercializar.setLabelFor(jchkComercializar);
        jlbComercializar.setText("Comercializar:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        jpDatos1.add(jlbComercializar, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jpDatos1.add(jchkComercializar, gridBagConstraints);

        jpSeccionDatos.add(jpDatos1);

        tabGeneral.add(jpSeccionDatos, java.awt.BorderLayout.CENTER);

        jtpBody.addTab("General", tabGeneral);

        tabVolumetria.setPreferredSize(new java.awt.Dimension(300, 200));

        jpDatos2.setPreferredSize(new java.awt.Dimension(450, 188));
        jpDatos2.setRequestFocusEnabled(false);
        jpDatos2.setLayout(new java.awt.GridLayout(6, 2, 5, 8));

        jlbMasaPeso.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbMasaPeso.setLabelFor(jtfMasaPeso);
        jlbMasaPeso.setText("Peso:");
        jpDatos2.add(jlbMasaPeso);

        jtfMasaPeso.setPreferredSize(new java.awt.Dimension(250, 22));
        jpDatos2.add(jtfMasaPeso);

        jlbUnidadMasa.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbUnidadMasa.setLabelFor(jlbUnidadMasa);
        jlbUnidadMasa.setText("Unidad Medida en Peso:");
        jpDatos2.add(jlbUnidadMasa);

        jcbUnidadMasa.setModel(bsUnidadMasa);
        jcbUnidadMasa.setName("jcbUnidadMasa"); // NOI18N
        jcbUnidadMasa.setPreferredSize(new java.awt.Dimension(250, 22));
        jcbUnidadMasa.setRenderer(new POJOListCellRenderer<Unidad>()
        );
        jpDatos2.add(jcbUnidadMasa);

        jlbAncho.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbAncho.setLabelFor(jtfAncho);
        jlbAncho.setText("Ancho:");
        jpDatos2.add(jlbAncho);
        jpDatos2.add(jtfAncho);

        jlbAlto.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbAlto.setLabelFor(jtfAlto);
        jlbAlto.setText("Alto:");
        jpDatos2.add(jlbAlto);
        jpDatos2.add(jtfAlto);

        jlbLargo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbLargo.setLabelFor(jtfLargo);
        jlbLargo.setText("Largo:");
        jpDatos2.add(jlbLargo);
        jpDatos2.add(jtfLargo);

        jlbUnidadLongitud.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbUnidadLongitud.setLabelFor(jcbUnidadLongitud);
        jlbUnidadLongitud.setText("Unidad Medida en longitud:");
        jpDatos2.add(jlbUnidadLongitud);

        jcbUnidadLongitud.setModel(bsUnidadLongitud);
        jcbUnidadLongitud.setRenderer(new POJOListCellRenderer<Unidad>());
        jpDatos2.add(jcbUnidadLongitud);

        tabVolumetria.add(jpDatos2);

        jtpBody.addTab("Volumetria", tabVolumetria);

        tabIngredientes.setName("tabIngredientes"); // NOI18N
        tabIngredientes.setLayout(new java.awt.BorderLayout());

        jbarDirecciones.setRollover(true);

        jtoolNuevoCompuesto.setText("Nuevo");
        jtoolNuevoCompuesto.setFocusable(false);
        jtoolNuevoCompuesto.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jtoolNuevoCompuesto.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbarDirecciones.add(jtoolNuevoCompuesto);

        jtoolEliminarCompuesto.setText("Eliminar");
        jtoolEliminarCompuesto.setToolTipText("");
        jtoolEliminarCompuesto.setFocusable(false);
        jtoolEliminarCompuesto.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jtoolEliminarCompuesto.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbarDirecciones.add(jtoolEliminarCompuesto);

        tabIngredientes.add(jbarDirecciones, java.awt.BorderLayout.PAGE_START);

        jtbPartes.setModel(bsCompuestos);
        jtbPartes.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jspDirecciones.setViewportView(jtbPartes);

        tabIngredientes.add(jspDirecciones, java.awt.BorderLayout.CENTER);

        jtpBody.addTab("Partes", tabIngredientes);

        tabIdentificadores.setName("tabIngredientes"); // NOI18N
        tabIdentificadores.setLayout(new java.awt.BorderLayout());

        jbarIdentificadores.setRollover(true);
        tabIdentificadores.add(jbarIdentificadores, java.awt.BorderLayout.PAGE_START);

        jtbIdentificadores.setModel(bsIdentificadores);
        jtbIdentificadores.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jspIdentificadores.setViewportView(jtbIdentificadores);

        tabIdentificadores.add(jspIdentificadores, java.awt.BorderLayout.CENTER);

        jtpBody.addTab("Identificadores", tabIdentificadores);

        tabLotes.setName("tabIngredientes"); // NOI18N
        tabLotes.setLayout(new java.awt.BorderLayout());

        jbarControlInventario.setRollover(true);

        jtoolNuevoLote.setText("Nuevo");
        jtoolNuevoLote.setFocusable(false);
        jtoolNuevoLote.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jtoolNuevoLote.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbarControlInventario.add(jtoolNuevoLote);

        jtoolEliminarLote.setText("Eliminar");
        jtoolEliminarLote.setToolTipText("");
        jtoolEliminarLote.setFocusable(false);
        jtoolEliminarLote.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jtoolEliminarLote.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbarControlInventario.add(jtoolEliminarLote);

        tabLotes.add(jbarControlInventario, java.awt.BorderLayout.PAGE_START);

        jtbLotes.setModel(bsLotes);
        jtbLotes.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jspControlInventario.setViewportView(jtbLotes);

        tabLotes.add(jspControlInventario, java.awt.BorderLayout.CENTER);

        jlbInventarioComo.setText("Utilizar lotes como:");
        jpInventarioComo.add(jlbInventarioComo);

        jcbInventarioComo.setModel(bsFormasInventario);
        jcbInventarioComo.setPreferredSize(new java.awt.Dimension(400, 25));
        jcbInventarioComo.setRenderer(new POJOListCellRenderer<Valor>());
        jpInventarioComo.add(jcbInventarioComo);

        tabLotes.add(jpInventarioComo, java.awt.BorderLayout.PAGE_END);

        jtpBody.addTab("Existencias y Lotes", tabLotes);

        tabPrecios.setName("tabIngredientes"); // NOI18N
        tabPrecios.setLayout(new java.awt.BorderLayout());

        jbarPrecios.setRollover(true);

        jtoolNuevoPrecio.setText("Nuevo");
        jtoolNuevoPrecio.setFocusable(false);
        jtoolNuevoPrecio.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jtoolNuevoPrecio.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbarPrecios.add(jtoolNuevoPrecio);

        jtoolEliminarPrecio.setText("Eliminar");
        jtoolEliminarPrecio.setToolTipText("");
        jtoolEliminarPrecio.setFocusable(false);
        jtoolEliminarPrecio.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jtoolEliminarPrecio.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbarPrecios.add(jtoolEliminarPrecio);

        tabPrecios.add(jbarPrecios, java.awt.BorderLayout.PAGE_START);

        jtbPrecios.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jspDirecciones2.setViewportView(jtbPrecios);

        tabPrecios.add(jspDirecciones2, java.awt.BorderLayout.CENTER);

        jtpBody.addTab("Precios", tabPrecios);

        tabPlanMantenimiento.setLayout(new java.awt.BorderLayout());

        jbarPlanMantenimiento.setRollover(true);

        jtoolNuevaActividad.setText("Nuevo");
        jtoolNuevaActividad.setFocusable(false);
        jtoolNuevaActividad.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jtoolNuevaActividad.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbarPlanMantenimiento.add(jtoolNuevaActividad);

        jtoolEliminarActividad.setText("Eliminar");
        jtoolEliminarActividad.setToolTipText("");
        jtoolEliminarActividad.setFocusable(false);
        jtoolEliminarActividad.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jtoolEliminarActividad.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbarPlanMantenimiento.add(jtoolEliminarActividad);

        tabPlanMantenimiento.add(jbarPlanMantenimiento, java.awt.BorderLayout.PAGE_START);

        jtbPlanMantenimiento.setModel(bsActividades);
        jtbPlanMantenimiento.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jspPlanMantenimiento.setViewportView(jtbPlanMantenimiento);

        tabPlanMantenimiento.add(jspPlanMantenimiento, java.awt.BorderLayout.CENTER);

        jpMantenimientoComo.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jlbMantenimientoComo.setText("Validar mantenimientos por:");
        jpMantenimientoComo.add(jlbMantenimientoComo);

        jcbMantenimientoComo.setModel(bsFormasMantenimiento);
        jcbMantenimientoComo.setPreferredSize(new java.awt.Dimension(200, 25));
        jcbMantenimientoComo.setRenderer(new POJOListCellRenderer<Valor>());
        jpMantenimientoComo.add(jcbMantenimientoComo);

        jlbEsperar.setText("Esperar cada:");
        jpMantenimientoComo.add(jlbEsperar);

        jftValorEsperado.setPreferredSize(new java.awt.Dimension(120, 22));
        jpMantenimientoComo.add(jftValorEsperado);

        tabPlanMantenimiento.add(jpMantenimientoComo, java.awt.BorderLayout.PAGE_END);

        jtpBody.addTab("Plan de Mantenimiento", tabPlanMantenimiento);

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

        setBounds(0, 0, 671, 462);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jbAceptar;
    private javax.swing.JButton jbCancelar;
    private javax.swing.JToolBar jbarControlInventario;
    private javax.swing.JToolBar jbarDirecciones;
    private javax.swing.JToolBar jbarIdentificadores;
    private javax.swing.JToolBar jbarPlanMantenimiento;
    private javax.swing.JToolBar jbarPrecios;
    private javax.swing.JButton jbtnEliminarImagen;
    private javax.swing.JButton jbtnNuevaImagen;
    private javax.swing.JComboBox jcbInventarioComo;
    private javax.swing.JComboBox jcbMantenimientoComo;
    private javax.swing.JComboBox jcbTipoBien;
    private javax.swing.JComboBox jcbUnidadLongitud;
    private javax.swing.JComboBox jcbUnidadMasa;
    private javax.swing.JCheckBox jchkActivo;
    private javax.swing.JCheckBox jchkComercializar;
    private javax.swing.JCheckBox jchkInventario;
    private javax.swing.JFileChooser jfcImagenes;
    private javax.swing.JFormattedTextField jftValorEsperado;
    private javax.swing.JLabel jlbActivo;
    private javax.swing.JLabel jlbAlto;
    private javax.swing.JLabel jlbAncho;
    private javax.swing.JLabel jlbComercializar;
    private javax.swing.JLabel jlbDescripcion;
    private javax.swing.JLabel jlbEsperar;
    private javax.swing.JLabel jlbIcono;
    private javax.swing.JLabel jlbImagen;
    private javax.swing.JLabel jlbInventario;
    private javax.swing.JLabel jlbInventarioComo;
    private javax.swing.JLabel jlbLargo;
    private javax.swing.JLabel jlbMantenimientoComo;
    private javax.swing.JLabel jlbMasaPeso;
    private javax.swing.JLabel jlbTipoBien;
    private javax.swing.JLabel jlbUnidadLongitud;
    private javax.swing.JLabel jlbUnidadMasa;
    private javax.swing.JPanel jpAcciones;
    private java.awt.Panel jpAreaMensajes;
    private javax.swing.JPanel jpDatos1;
    private javax.swing.JPanel jpDatos2;
    private javax.swing.JPanel jpDimensionImagen;
    private javax.swing.JPanel jpEncabezado;
    private javax.swing.JPanel jpImagenAcciones;
    private javax.swing.JPanel jpInventarioComo;
    private javax.swing.JPanel jpMantenimientoComo;
    private javax.swing.JPanel jpSeccionDatos;
    private javax.swing.JPanel jpSeccionImagen;
    private javax.swing.JScrollPane jspControlInventario;
    private javax.swing.JScrollPane jspDirecciones;
    private javax.swing.JScrollPane jspDirecciones2;
    private javax.swing.JScrollPane jspIdentificadores;
    private javax.swing.JScrollPane jspPlanMantenimiento;
    private javax.swing.JTable jtbIdentificadores;
    private javax.swing.JTable jtbLotes;
    private javax.swing.JTable jtbPartes;
    private javax.swing.JTable jtbPlanMantenimiento;
    private javax.swing.JTable jtbPrecios;
    private javax.swing.JFormattedTextField jtfAlto;
    private javax.swing.JFormattedTextField jtfAncho;
    private javax.swing.JTextField jtfDescripcion;
    private javax.swing.JFormattedTextField jtfLargo;
    private javax.swing.JFormattedTextField jtfMasaPeso;
    private javax.swing.JButton jtoolEliminarActividad;
    private javax.swing.JButton jtoolEliminarCompuesto;
    private javax.swing.JButton jtoolEliminarLote;
    private javax.swing.JButton jtoolEliminarPrecio;
    private javax.swing.JButton jtoolNuevaActividad;
    private javax.swing.JButton jtoolNuevoCompuesto;
    private javax.swing.JButton jtoolNuevoLote;
    private javax.swing.JButton jtoolNuevoPrecio;
    private javax.swing.JTabbedPane jtpBody;
    private javax.swing.JPanel tabGeneral;
    private javax.swing.JPanel tabIdentificadores;
    private javax.swing.JPanel tabIngredientes;
    private javax.swing.JPanel tabLotes;
    private javax.swing.JPanel tabPlanMantenimiento;
    private javax.swing.JPanel tabPrecios;
    private javax.swing.JPanel tabVolumetria;
    // End of variables declaration//GEN-END:variables
  //------------------------------------------------------------------

    private class BienDetailCargarSwingWorker extends SwingWorker<List<Object>, Void>
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
                    case opLOAD_COMPUESTOS:
                        arguments.add(partes.obtenerListaPartes());
                        arguments.add(parent.obtenerCompuestos((BienVariante) arguments.get(1)));
                        break;

                    case opLOAD_PARTES:
                        arguments.add(partes.obtenerLista());
                        break;

                    case opLOAD_IDENTIFICADORES:
                        arguments.add(parent.obtenerIdentificadores((BienVariante) arguments.get(1)));
                        break;

                    case opLOAD_LOTES:
                        arguments.add(parent.obtenerLotes((BienVariante) arguments.get(1)));
                        break;

                    case opLOAD_ACTIVIDADES:
                        arguments.add(parent.obtenerActividades((BienVariante) arguments.get(1)));
                        break;

                    case opLOAD_MONEDAS:
                        arguments.add(monedas.obtenerLista());
                        arguments.add(areas_precios.obtenerListaHojas());
                        break;

                    case opLOAD_PRECIOS:
                        arguments.add(monedas.obtenerLista());
                         arguments.add(areas_precios.obtenerListaHojas());
                        arguments.add(parent.obtenerPrecios((BienVariante) arguments.get(1)));
                        break;

                    case opLOAD_MINIATURAS:
                        arguments.add(parent.obtenerFotos((BienVariante) arguments.get(1)));
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
                        case opLOAD_COMPUESTOS:
                            setDisplayData((List<BienVariante>) results.get(2), (List<BienCompuestoVista>) results.get(3));
                            break;

                        case opLOAD_PARTES:
                            setDisplayData((List<BienVariante>) results.get(1), null);
                            break;

                        case opLOAD_IDENTIFICADORES:
                            setDisplayData((List<VarianteIdentificadorVista>) results.get(2));
                            break;

                        case opLOAD_LOTES:
                            List<Valor> listaValores = new ArrayList<>();
                            listaValores.add(new Valor(0, "Seleccionar lote"));
                            listaValores.add(new Valor(1, "PEPS - Primeras Entradas, Primeras Salidas "));
                            listaValores.add(new Valor(2, "UEPS - Ultimas Entradas, Primeras Salidas"));
                            setDisplayLote(listaValores, (List<ControlInventario>) results.get(2));
                            break;

                        case opLOAD_ACTIVIDADES:
                            List<Valor> listaFormas = new ArrayList<>();
                            listaFormas.add(new Valor(0, "Tiempo(Días)"));
                            listaFormas.add(new Valor(1, "Kilometraje"));
                            listaFormas.add(new Valor(2, "Usos"));
                            setDisplayActividades(listaFormas, (List<PlanMantenimiento>) results.get(2));
                            break;

                        case opLOAD_MONEDAS:
                            setDisplayPrecio((List<Moneda>) results.get(1),  (List<AreaPrecio>) results.get(2), null);
                            break;

                        case opLOAD_PRECIOS:
                            setDisplayPrecio((List<Moneda>) results.get(2), (List<AreaPrecio>) results.get(3), (List<ControlPrecio>) results.get(4));
                            break;

                        case opLOAD_MINIATURAS:
                            setDisplayPictures((List<BienVarianteFoto>) results.get(2));
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
