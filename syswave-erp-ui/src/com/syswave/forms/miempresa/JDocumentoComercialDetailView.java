package com.syswave.forms.miempresa;

import com.syswave.entidades.common.Entidad;
import com.syswave.entidades.miempresa.Categoria;
import com.syswave.entidades.miempresa.DocumentoContadoMovimiento;
import com.syswave.entidades.miempresa.DocumentoDetalle;
import com.syswave.entidades.miempresa.Moneda;
import com.syswave.entidades.miempresa.Persona;
import com.syswave.entidades.miempresa.PersonaDireccion;
import com.syswave.entidades.miempresa.PersonaDireccion_tiene_Documento;
import com.syswave.entidades.miempresa.TipoComprobante;
import com.syswave.entidades.miempresa.TipoPersona;
import com.syswave.entidades.miempresa_vista.ControlPrecioVista;
import com.syswave.entidades.miempresa_vista.DocumentoComercial;
import com.syswave.entidades.miempresa_vista.DocumentoContadoMovimiento_5FN;
import com.syswave.entidades.miempresa_vista.DocumentoDetalleNavigable;
import com.syswave.entidades.miempresa_vista.DocumentoDetalle_tiene_PrecioVista;
import com.syswave.entidades.miempresa_vista.MonedaCambioVista;
import com.syswave.entidades.miempresa_vista.PersonaDireccion_tiene_Documento_5FN;
import com.syswave.swing.JBusinessMenuItem;
import com.syswave.forms.databinding.DocumentoContadoMov5FNTableModel;
import com.syswave.forms.databinding.DocumentoDetalleNavigableTableModel;
import com.syswave.forms.databinding.MonedasComboBoxModel;
import com.syswave.swing.models.POJOTableModel;
import com.syswave.forms.databinding.PersonaDireccionDocumentos5FNTableModel;
import com.syswave.forms.databinding.TiposComprobantesComboBoxModel;
import com.syswave.swing.renders.POJOListCellRenderer;
import com.syswave.logicas.miempresa.CategoriasBusinessLogic;
import com.syswave.logicas.miempresa.MonedaCambioBusinessLogic;
import com.syswave.logicas.miempresa.TipoPersonasBusinessLogic;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFormattedTextField;
import javax.swing.JMenuItem;
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
import javax.swing.text.NumberFormatter;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class JDocumentoComercialDetailView extends javax.swing.JInternalFrame implements TableModelListener, IPersonaBusquedaMediator, IPrecioBusquedaMediator, IMonedaCambioFramedMediator, PropertyChangeListener
{

    private final int opLOAD_ADDRESS = 0;
    private final int opLOAD_RESOURCES = 1;
    private final int opLOAD_RESOURCES_AND_DETAILS = 2;
    private final int opSAVE_PRICE = 3;
    private final int opSAVE_PRICE_AND_PRICE_PARTS = 4;
    private final int opLOAD_PRICE_PARTS = 5;
    private final int opLOAD_PAIDS = 6;
    private final int ID_RECEPTOR = 1;

    private IDocumentoComercialMediator parent;
    private DocumentoComercial elementoActual;
    private TiposComprobantesComboBoxModel bsTipoComprobanteRender;
    private MonedasComboBoxModel bsMonedas;
    private PersonaDireccionDocumentos5FNTableModel bsPersonaDireccionDocumentos;
    private DocumentoDetalleNavigableTableModel bsDetalles;
    private DocumentoContadoMov5FNTableModel bsContadoPagos;

    private boolean esNuevo, esNuevoDetalle, construidoDirecciones, construidoCondiciones,
            construidoRelacionPrecios, construidoDesglose, revirtiendoMoneda;
    private TipoPersonasBusinessLogic tiposPersonas;
    private CategoriasBusinessLogic categorias;
    private MonedaCambioBusinessLogic monedasCambio;

    private TableColumn colValor, colLlave, colDescripcion,
            colTipoMedio, colClave, colNombre, colRelacion,
            colLocalidad, colCalle, colColonia,
            colNo, colCantidad, colDescripcionLinea, colPrecio, colImporte,
            colGravamen, colPorcentaje, colImporteNeto,
            colNombreAbono, coloMontoAbono,
            colObservacionAbono;

    private DocumentoDetailCargarSwingWorker swCargador;
    private List<PersonaDireccion_tiene_Documento> direcciones_deleteds;
    private List<DocumentoDetalle> detalles_deleteds;
    private List<DocumentoContadoMovimiento> pagos_deleted;
    private List<DocumentoDetalleNavigable> pendientes_cambio;

    private float subtotal, gravamen, total, pagado, saldo_actual, cambio;
    private int factor;
    private List<MonedaCambioVista> listaUltimosTiposCambio;
    private Moneda selected_old;
    private NumberFormatter floatFormat;

    /**
     * Creates new form JDocumentoDetailView
     *
     * @param owner
     */
    public JDocumentoComercialDetailView(IDocumentoComercialMediator owner)
    {
        initAtributes(owner.getEsquema());
        initComponents();
        initEvents();
        parent = owner;

        if (jtPersonaTieneDocumentos.getColumnCount() > 0)
        {
            colNombre = jtPersonaTieneDocumentos.getColumnModel().getColumn(1);
            colNombre.setPreferredWidth(250);

            colRelacion = jtPersonaTieneDocumentos.getColumnModel().getColumn(2);
            colRelacion.setPreferredWidth(250);

            colLocalidad = jtPersonaTieneDocumentos.getColumnModel().getColumn(3);
            /*colLocalidad.setCellRenderer(new LookUpComboBoxTableCellRenderer<Localidad>(bsLocalidadRender));
             colLocalidad.setCellEditor(new LookUpComboBoxTableCellEditor<Localidad>(bsLocalidadEditor));*/
            colLocalidad.setPreferredWidth(250);

            colCalle = jtPersonaTieneDocumentos.getColumnModel().getColumn(4);
            colCalle.setPreferredWidth(150);

            colColonia = jtPersonaTieneDocumentos.getColumnModel().getColumn(5);
            colColonia.setPreferredWidth(150);

            //jtPersonaTieneDocumentos.setRowHeight((int)(jtPersonaTieneDocumentos.getRowHeight() * 1.5));
        }

        if (jtbDocumentoDetalles.getColumnCount() > 0)
        {
            colNo = jtbDocumentoDetalles.getColumnModel().getColumn(0);
            colNo.setPreferredWidth(50);

            colCantidad = jtbDocumentoDetalles.getColumnModel().getColumn(1);
            colCantidad.setPreferredWidth(80);

            colDescripcionLinea = jtbDocumentoDetalles.getColumnModel().getColumn(2);
            colDescripcionLinea.setPreferredWidth(300);

            colPrecio = jtbDocumentoDetalles.getColumnModel().getColumn(3);
            colPrecio.setPreferredWidth(80);

            colImporte = jtbDocumentoDetalles.getColumnModel().getColumn(4);
            colImporte.setPreferredWidth(80);

            colGravamen = jtbDocumentoDetalles.getColumnModel().getColumn(5);
            colGravamen.setPreferredWidth(80);

            colPorcentaje = jtbDocumentoDetalles.getColumnModel().getColumn(6);
            colPorcentaje.setPreferredWidth(80);

            colImporteNeto = jtbDocumentoDetalles.getColumnModel().getColumn(7);
            colImporteNeto.setPreferredWidth(80);
        }

        if (jtDocumentoContadoPagos.getColumnCount() > 0)
        {
            colNombreAbono = jtDocumentoContadoPagos.getColumnModel().getColumn(0);
            colNombreAbono.setPreferredWidth(200);

            coloMontoAbono = jtDocumentoContadoPagos.getColumnModel().getColumn(1);
            coloMontoAbono.setPreferredWidth(100);

            colObservacionAbono = jtDocumentoContadoPagos.getColumnModel().getColumn(2);
            colObservacionAbono.setPreferredWidth(300);

            //jtDocumentoContadoPagos.setRowHeight((int)(jtDocumentoTieneCondiciones.getRowHeight() * 1.5));
        }
    }

    //--------------------------------------------------------------------
    /**
     * Construye una instancia de tabla con detección de cambios en celdas.
     */
    private JTable createJtbDetalles()
    {
        return new JTable()
        {
            @Override // Always selectAll()
            public void editingStopped(ChangeEvent e)
            {
                jtbDetalles_onCellValueChanged(this, getCellEditor());
                super.editingStopped(e);
            }
        };
    }

    //--------------------------------------------------------------------
    /**
     * Construye una instancia de tabla con detección de cambios en celdas.
     */
    private JTable createJtbPagos()
    {
        return new JTable()
        {
            @Override // Always selectAll()
            public void editingStopped(ChangeEvent e)
            {
                jtbPagos_onCellValueChanged(this, getCellEditor());
                super.editingStopped(e);
            }
        };
    }

    //---------------------------------------------------------------------
    private void initAtributes(String Esquema)
    {
        esNuevo = true;
        construidoDirecciones = false;
        construidoCondiciones = false;
        construidoRelacionPrecios = false;
        construidoDesglose = false;
        revirtiendoMoneda = false;
        esNuevoDetalle = true;
        tiposPersonas = new TipoPersonasBusinessLogic(Esquema);
        categorias = new CategoriasBusinessLogic(Esquema);
        monedasCambio = new MonedaCambioBusinessLogic(Esquema);
        floatFormat = new NumberFormatter(new DecimalFormat("#.####"));
        floatFormat.setValueClass(Float.class);

        bsTipoComprobanteRender = new TiposComprobantesComboBoxModel();
        bsMonedas = new MonedasComboBoxModel();
        /* bsTipoMedioRender = new ValorComboBoxModel();
         bsTipoMedioEditor = new ValorComboBoxModel();*/
        bsPersonaDireccionDocumentos = new PersonaDireccionDocumentos5FNTableModel(new String[]
        {
            "Rol:{rol}", "Nombre:{nombre}", "Relación:{tipo_persona}",
            "Localidad:{localidad}", "Calle:{calle}", "Colonia:{colonia}",
            "C.P:{codigo_postal}", "No. Exterior:{no_exterior}",
            "No. Interior:{no_interior}"
        });
        bsPersonaDireccionDocumentos.setReadOnly(true);
        bsPersonaDireccionDocumentos.addTableModelListener(this);
        
        bsDetalles = new DocumentoDetalleNavigableTableModel(new String[]
        {
            "No.:{#}}", "Cantidad:{cantidad}", "Descripcion:{descripcion}",
            "Precio U.:{precio}", "Importe:{importe}", "Gravamen:{monto}",
            "% Gravado:{factor}", "Importe Neto:{importe_neto}"
        });
        bsDetalles.addTableModelListener(this);

        bsContadoPagos = new DocumentoContadoMov5FNTableModel(new String[]
        {
            "Nombre:{nombre}", "Monto:{monto}", "Observación:{concepto}"
        });
        bsContadoPagos.addTableModelListener(this);

        direcciones_deleteds = new ArrayList<>();
        detalles_deleteds = new ArrayList<>();
        pendientes_cambio = new ArrayList<>();
        pagos_deleted = new ArrayList<>();
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

        ActionListener detailsActionListener
                = new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent evt)
                    {
                        detailsToolBar_actionPerformed(evt);
                    }
                };

        ActionListener addresssActionListener
                = new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent evt)
                    {
                        addressToolBar_actionPerformed(evt);
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
                jcbMoneda_itemStateChanged(e);
            }
        };

        jbAceptar.addActionListener(actionListenerManager);
        jbCancelar.addActionListener(actionListenerManager);
        jtpAreaDetalles.addChangeListener(changeListenerManager);
        jToolNuevoLinea.addActionListener(detailsActionListener);
        jToolEliminarLinea.addActionListener(detailsActionListener);
        jbtnEspecificar.addActionListener(addresssActionListener);
        jbtnQuitar.addActionListener(addresssActionListener);
        jcbMoneda.addItemListener(valueChangeItemListener);
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
    public void jtbDetalles_onCellValueChanged(JTable sender, TableCellEditor editor)
    {
        TableColumnModel columnas = sender.getColumnModel();
        int modelColIndex = sender.convertColumnIndexToModel(sender.getEditingColumn());
        int modelRowIndex = sender.convertRowIndexToModel(sender.getEditingRow());
        TableColumn actual = columnas.getColumn(modelColIndex);
        DocumentoDetalleNavigableTableModel dataBinding = (DocumentoDetalleNavigableTableModel) sender.getModel();
        DocumentoDetalleNavigable seleccionado = dataBinding.getElementAt(modelRowIndex);

        if (actual == colCantidad)
        {
            float cantidad_new = (float) editor.getCellEditorValue();
            float importe_new, monto_new; 

            importe_new = seleccionado.getPrecio() * cantidad_new;
            monto_new = seleccionado.getFactor() / 100.0000F * importe_new;
            actualizarTotal(importe_new + monto_new - seleccionado.getImporte_neto(), monto_new - seleccionado.getMonto() );
            dataBinding.setValueAt(importe_new, modelRowIndex, colImporte.getModelIndex());
            dataBinding.setValueAt(monto_new, modelRowIndex, colGravamen.getModelIndex());
            dataBinding.setValueAt(importe_new + monto_new, modelRowIndex, colImporteNeto.getModelIndex());
            reescribirTotales();
        }

        else if (actual == colPrecio)
        {
            float precio = (float) editor.getCellEditorValue();
            float importe_new, monto_new; 

            importe_new = precio * seleccionado.getCantidad();
            monto_new = seleccionado.getFactor() / 100.0000F * importe_new;
            actualizarTotal(importe_new + monto_new - seleccionado.getImporte_neto(), monto_new - seleccionado.getMonto());
            dataBinding.setValueAt(importe_new, modelRowIndex, colImporte.getModelIndex());
            dataBinding.setValueAt(monto_new, modelRowIndex, colGravamen.getModelIndex());
            dataBinding.setValueAt(importe_new + monto_new, modelRowIndex, colImporteNeto.getModelIndex());
            reescribirTotales();
        }

        else if (actual == colImporte)
        {
            float cantidad_old = seleccionado.getCantidad();
            float importe_new = (float) editor.getCellEditorValue();
            float precio_new = importe_new / (cantidad_old > 0 ? cantidad_old : 1.0F);
            float monto_new = seleccionado.getFactor() / 100.0000F * importe_new;

            actualizarTotal(importe_new + monto_new - seleccionado.getImporte_neto(), monto_new - seleccionado.getMonto());
            dataBinding.setValueAt(precio_new, modelRowIndex, colPrecio.getModelIndex());
            dataBinding.setValueAt(monto_new, modelRowIndex, colGravamen.getModelIndex());
            dataBinding.setValueAt(importe_new + monto_new, modelRowIndex, colImporteNeto.getModelIndex());
            reescribirTotales();
        }

        else if (actual == colGravamen)
        {
            float monto_new = (float) editor.getCellEditorValue();
            float importe_old = seleccionado.getImporte();
            float importe_neto_new;

            if (importe_old > 0)
                dataBinding.setValueAt((int) (monto_new * 100.0000F / importe_old), modelRowIndex, colPorcentaje.getModelIndex());

            else
                dataBinding.setValueAt(0, modelRowIndex, colPorcentaje.getModelIndex());

             importe_neto_new = importe_old + monto_new;
            actualizarTotal(importe_neto_new - seleccionado.getImporte_neto(), monto_new - seleccionado.getMonto());
            dataBinding.setValueAt(importe_neto_new, modelRowIndex, colImporteNeto.getModelIndex());
            reescribirTotales();
        }

        else if (actual == colPorcentaje)
        {
            int porcentaje_new = (int) editor.getCellEditorValue();
            float montoo_new, importe_old = seleccionado.getImporte();

            montoo_new = porcentaje_new / 100.0000F * importe_old;
            actualizarTotal(importe_old + montoo_new - seleccionado.getImporte_neto(), montoo_new - seleccionado.getMonto());
            dataBinding.setValueAt(montoo_new, modelRowIndex, colGravamen.getModelIndex());
            dataBinding.setValueAt(importe_old + montoo_new, modelRowIndex, colImporteNeto.getModelIndex());
            reescribirTotales();
        }

        else if (actual == colImporteNeto)
        {
            float importe_neto_new = (float) editor.getCellEditorValue();
            float monto_new, importe_old = seleccionado.getImporte();

            monto_new = importe_neto_new - importe_old;
            
            actualizarTotal(importe_old + monto_new - seleccionado.getImporte_neto(), monto_new - seleccionado.getMonto());

            dataBinding.setValueAt(monto_new, modelRowIndex, colGravamen.getModelIndex());
            if (importe_old > 0)
                dataBinding.setValueAt((int) (monto_new * 100.0000F / importe_old), modelRowIndex, colPorcentaje.getModelIndex());

            else
                dataBinding.setValueAt(0, modelRowIndex, colPorcentaje.getModelIndex());
            dataBinding.setValueAt(importe_old + monto_new, modelRowIndex, colImporteNeto.getModelIndex());
            reescribirTotales();
        }
    }

    //----------------------------------------------------------------------
    public void jtbPagos_onCellValueChanged(JTable sender, TableCellEditor editor)
    {
        TableColumnModel columnas = sender.getColumnModel();
        int modelColIndex = sender.convertColumnIndexToModel(sender.getEditingColumn());
        int modelRowIndex = sender.convertRowIndexToModel(sender.getEditingRow());
        TableColumn actual = columnas.getColumn(modelColIndex);
        DocumentoContadoMov5FNTableModel dataBinding = (DocumentoContadoMov5FNTableModel) sender.getModel();
        DocumentoContadoMovimiento seleccionado = dataBinding.getElementAt(modelRowIndex);

        if (actual == coloMontoAbono)
        {
            float monto_new = (float) editor.getCellEditorValue();

            actualizarPago(monto_new - seleccionado.getMonto());
            reescribirPagos();
        }
    }

    //---------------------------------------------------------------------
    /**
     *
     */
    private void jcbMoneda_itemStateChanged(ItemEvent evt)
    {
        //JComboBox combo = (JComboBox) evt.getSource();
        //Item previo al cambio
        if (evt.getStateChange() == ItemEvent.DESELECTED)
        {
            if (!revirtiendoMoneda)
            {
                //Nota: Si es la primera vez que se selecciona un item, este evento no se ejecuta.
                //porque no había nada previo seleccionado.
            /*Moneda selected_new = ((MonedasComboBoxModel) combo.getModel()).getCurrent();
                 Moneda selected_old = (Moneda) evt.getItem();
                 //JOptionPane.showMessageDialog(this, String.format("Se esta pasando de %s a %s", selected_old.getNombre(), selected_new.getNombre()));*/

                esNuevoDetalle = bsDetalles.getData().isEmpty();

                if (!esNuevoDetalle)
                {
                    //Reespaldamos la selección anterior en caso de que se cancele la operación.
                    selected_old = (Moneda) evt.getItem();

                    //Llenamos los pendientes de cambio.
                    for (DocumentoDetalleNavigable detalle : bsDetalles.getData())
                        pendientes_cambio.add(detalle);

                    subtotal = 0.0F;
                    gravamen = 0.0F;
                    total = 0.0F;
                    factor = 0;

                    if (esNuevo || construidoRelacionPrecios)
                        revisarDetallesPendientesPrecio(esNuevoDetalle);

                    else if (swCargador == null || swCargador.isDone())
                    {
                        List<Object> parametros = new ArrayList<Object>();
                        swCargador = new DocumentoDetailCargarSwingWorker();
                        swCargador.addPropertyChangeListener(this);
                        parametros.add(opLOAD_PRICE_PARTS);
                        parametros.add(false);
                        parametros.add(pendientes_cambio);
                        parametros.add(esNuevoDetalle); //No Agregar pendientes al terminar
                        showTaskHeader("Cargando recursos, espero un momento....", true);
                        swCargador.execute(parametros);
                        construidoRelacionPrecios = true;
                    }
                }
            }
            else
            {
                selected_old = null;
                revirtiendoMoneda = false;
            }
        }

        //Item después del cambio
      /*else if (evt.getStateChange() == ItemEvent.SELECTED) {
         Object item = evt.getItem();
         // do something with object
         } */
    }

    //---------------------------------------------------------------------
    private void cargarRecursos(DocumentoComercial elemento)
    {
        if (swCargador == null || swCargador.isDone())
        {
            Categoria filtroCategoria = new Categoria();
            filtroCategoria.setNivel(0); //Solo el nivel 0
            List<Object> parametros = new ArrayList<Object>();
            swCargador = new DocumentoDetailCargarSwingWorker();
            swCargador.addPropertyChangeListener(this);

            if (elemento != null)
            {
                parametros.add(opLOAD_RESOURCES_AND_DETAILS);
                parametros.add(elemento);
            }
            else
                parametros.add(opLOAD_RESOURCES);
            parametros.add(filtroCategoria);
            showTaskHeader("Cargando recursos, espero un momento....", true);
            swCargador.execute(parametros);
        }
    }

    //---------------------------------------------------------------------
    private void bodyTabbed_stateChanged(ChangeEvent e)
    {
        if (swCargador == null || swCargador.isDone())
        {
            JTabbedPane pane = (JTabbedPane) e.getSource();
            //JOptionPane.showMessageDialog(this, pane.getTitleAt(pane.getSelectedIndex()));
            if (pane.getSelectedComponent() == jTabPersonas && !construidoDirecciones)
            {
                TipoPersona filtroTipos = new TipoPersona();
                filtroTipos.setNivel(0); //Solo el nivel 0
                filtroTipos.acceptChanges();
                List<Object> parametros = new ArrayList<Object>();
                swCargador = new DocumentoDetailCargarSwingWorker();
                swCargador.addPropertyChangeListener(this);

                parametros.add(opLOAD_ADDRESS);
                parametros.add(elementoActual);
                parametros.add(filtroTipos);
                showTaskHeader("Cargando información, espero un momento....", true);
                swCargador.execute(parametros);
                construidoDirecciones = true;
            }

            else if (pane.getSelectedComponent() == jTabDesglose && !construidoDesglose)
            {
                List<Object> parametros = new ArrayList<Object>();
                swCargador = new DocumentoDetailCargarSwingWorker();
                swCargador.addPropertyChangeListener(this);

                parametros.add(opLOAD_PAIDS);
                parametros.add(elementoActual);
                showTaskHeader("Cargando información, espero un momento....", true);
                swCargador.execute(parametros);
                construidoDesglose = true;
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
                tryStopDetailCellEditor(jtPersonaTieneDocumentos);
                tryStopDetailCellEditor(jtbDocumentoDetalles);
                tryStopDetailCellEditor(jtDocumentoContadoPagos);

                if (esNuevo)
                    parent.onAcceptNewElement(elementoActual,
                                              bsDetalles.getData(),
                                              bsPersonaDireccionDocumentos.getData(),
                                              bsContadoPagos.getData());

                else
                {
                    elementoActual.setModified();
                    parent.onAcceptModifyElement(elementoActual,
                                                 bsDetalles.getData(),
                                                 detalles_deleteds,
                                                 bsPersonaDireccionDocumentos.getData(),
                                                 direcciones_deleteds,
                                                 bsContadoPagos.getData(),
                                                 pagos_deleted);
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
    private void detailsToolBar_actionPerformed(ActionEvent evt)
    {
        //int rowIndex;

        if (evt.getSource() == jToolNuevoLinea)
        {
            /*DocumentoDireccion nuevaDireccion = new DocumentoDireccion();
             nuevaDireccion.setFk_persona_direccion(elementoActual);
             nuevaDireccion.setCodigoPostal("");
             nuevaDireccion.setCalle("");
             nuevaDireccion.setColonia("");
             nuevaDireccion.setNoInterior("");
             nuevaDireccion.setNoExterior("");
             rowIndex = bsDocumentoDireccion.addRow(nuevaDireccion);
             jtbDirecciones.setRowSelectionInterval(rowIndex, rowIndex);*/
            //jtbDirecciones.editCellAt(rowIndex, 0);
            //Nota: Mostramos el popup de Tipos de personas, y la ubicamos en las coordenadas del botón especificar.
            popupCategorias.show(jToolNuevoLinea, 0, (int) jToolNuevoLinea.getPreferredSize().getHeight());

        }

        else if (evt.getSource() == jToolEliminarLinea)
        {
            if (JOptionPane.showConfirmDialog(this, String.format("Ha seleccionado %d registro(s) para ser eleiminado(s)\n¿Esta seguro de continuar?", jtbDocumentoDetalles.getSelectedRowCount())) == JOptionPane.OK_OPTION)
            {
                int[] rowsHandlers = jtbDocumentoDetalles.getSelectedRows();
                List<DocumentoDetalleNavigable> selecteds = bsDetalles.removeRows(rowsHandlers);

                for (DocumentoDetalleNavigable elemento : selecteds)
                {
                    if (!elemento.isNew())
                        detalles_deleteds.add(elemento);

                    actualizarTotal(elemento.getImporte_neto() * -1, elemento.getMonto() * -1);
                }

                invalidateChangeOfType(bsDetalles.getRowCount() > 0);
                reescribirTotales();
            }
        }
    }

    //---------------------------------------------------------------------
    private void addressToolBar_actionPerformed(ActionEvent evt)
    {
        //int rowIndex;

        if (evt.getSource() == jbtnEspecificar)
        {
            /*DocumentoDireccion nuevaDireccion = new DocumentoDireccion();
             nuevaDireccion.setFk_persona_direccion(elementoActual);
             nuevaDireccion.setCodigoPostal("");
             nuevaDireccion.setCalle("");
             nuevaDireccion.setColonia("");
             nuevaDireccion.setNoInterior("");
             nuevaDireccion.setNoExterior("");
             rowIndex = bsDocumentoDireccion.addRow(nuevaDireccion);
             jtbDirecciones.setRowSelectionInterval(rowIndex, rowIndex);*/
         //jtbDirecciones.editCellAt(rowIndex, 0);

            //Nota: Mostramos el popup de Tipos de personas, y la ubicamos en las coordenadas del botón especificar.
            popupTipoPersonas.show(jbtnEspecificar, 0, (int) jbtnEspecificar.getPreferredSize().getHeight());
        }

        else if (evt.getSource() == jbtnQuitar)
        {
            if (JOptionPane.showConfirmDialog(this, String.format("Ha seleccionado %d registro(s) para ser eleiminado(s)\n¿Esta seguro de continuar?", jtPersonaTieneDocumentos.getSelectedRowCount())) == JOptionPane.OK_OPTION)
            {
                PersonaDireccion_tiene_Documento_5FN new_reset, elemento;
                int[] rowsHandlers = jtPersonaTieneDocumentos.getSelectedRows();
                List<PersonaDireccion_tiene_Documento_5FN> selecteds = bsPersonaDireccionDocumentos.getElementsAt(rowsHandlers);

                for (int i = 0; i < selecteds.size(); i++)
                {
                    elemento = selecteds.get(i);
                    //¿Ya se había relacionado el precio con el documento?
                    if (elemento.getIdDocumento() == elementoActual.getId())
                    {
                        if (!elemento.isNew())
                            direcciones_deleteds.add(elemento);

                        new_reset = new PersonaDireccion_tiene_Documento_5FN();
                        new_reset.setSearchOnlyByPrimaryKey(elemento.isSearchOnlyByPrimaryKey());
                        new_reset.setRol(elemento.getRol());
                        new_reset.setRol(elemento.getEsRol());
                        //new_reset.getFk_documento_persona_direccion_id().clear();
                        new_reset.setNombre("");
                        new_reset.setTipo_persona("");
                        new_reset.setLocalidad("");

                        new_reset.setCalle("");
                        new_reset.setColonia("");
                        new_reset.setCodigoPostal("");
                        new_reset.setNoExterior("");
                        new_reset.setNoInterior("");
                        new_reset.acceptChanges();

                        if (elemento.getEsRol() == ID_RECEPTOR)
                            generarTextoReceptor(elemento.getNombre());
                    }
                }

                bsPersonaDireccionDocumentos.fireTableDataChanged();
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

                if (model == bsContadoPagos)
                    onAcceptElementChanged(model, row, (DocumentoContadoMovimiento_5FN) data);

                else if (!data.isSet())
                    data.setModified();
            }
        }
    }

//---------------------------------------------------------------------
    private void onAcceptElementChanged(POJOTableModel sender, int row, DocumentoContadoMovimiento_5FN elemento)
    {
        if (elemento.getMonto() > 0)
        {
            if (!elemento.isSet())
            {
                if (elemento.getIdDocumento() == DocumentoContadoMovimiento.EMPTY_INT
                        || elemento.getIdDocumento() != elementoActual.getId())
                {
                    elemento.setHasOneDocumento(elementoActual);
                    elemento.setNew();
                }

                else
                    elemento.setModified();
            }
        }

        //¿Ya se había relacionado el precio con el documento?
        else if (elemento.getIdDocumento() == elementoActual.getId())
        {
            if (!elemento.isNew())
                pagos_deleted.add(elemento);

            DocumentoContadoMovimiento_5FN new_reset = new DocumentoContadoMovimiento_5FN();
            new_reset.setSearchOnlyByPrimaryKey(elemento.isSearchOnlyByPrimaryKey());
            new_reset.setIdTipoComprobante(elemento.getIdTipoComprobante());
            new_reset.setNombre(elemento.getNombre());
            new_reset.setMonto(elemento.getMonto());
            new_reset.setConcepto("");
            new_reset.acceptChanges();

            sender.getData().set(row, new_reset);
        }

        /*bsPersonaDireccionDocumentos.fireTableRowsUpdated(index, index);
         if (seleccionado.getEsRol() == ID_RECEPTOR)
         generarTextoReceptor(inDireccion.getFk_persona_direccion());*/
    }

    //---------------------------------------------------------------------
    public void prepareForNew()
    {
        elementoActual = new DocumentoComercial();
        esNuevo = true;
        subtotal = 0.0F;
        gravamen = 0.0F;
        total = 0.0F;
        factor = 0;
        saldo_actual = 0.0F;
        pagado = 0.0F;
        cambio = 0.0F;
        this.setTitle("Nuevo documento");
        bsTipoComprobanteRender.setData(parent.obtenerTiposComprobantes());
        bsMonedas.setData(parent.obtenerMonedas());
        cargarRecursos(null);
        //writeElement(elementoActual);
    }

    //---------------------------------------------------------------------
    public void prepareForModify(DocumentoComercial elemento)
    {
        this.elementoActual = elemento;
        esNuevo = false;
        this.setTitle(String.format("Modificando %s", elemento.getIdentificadorCompleto()));
        bsTipoComprobanteRender.setData(parent.obtenerTiposComprobantes());
        bsMonedas.setData(parent.obtenerMonedas());
        writeElement(elemento);
        cargarRecursos(elemento);
    }

    //---------------------------------------------------------------------
    public void writeElement(DocumentoComercial elemento)
    {
        jtfFolio.setText(elemento.getFolio());
        jtfSerie.setText(elemento.getSerie());
        jdcFechaElaboracion.setDate(elemento.getFechaElaboracion());
        jdcFechaVigencia.setDate(elemento.getFechaVigencia());
        jtfReceptor.setText(elemento.getReceptor());
        jtfCondiciones.setText(elemento.getCondiciones());
        jcbTipoComprobante.setSelectedItem(bsTipoComprobanteRender.getElementAt(bsTipoComprobanteRender.indexOfValue(elemento.getIdTipoComprobante())));
        subtotal = elemento.getSubtotal();
        gravamen = elemento.getMonto();
        total = elemento.getTotal();
        factor = elemento.getFactor();
        saldo_actual = elemento.getSaldoActual();
        pagado = elemento.getPagado();
        cambio = total - pagado;
        if (cambio < 0)
            cambio *= -1;
        else
            cambio = 0;
        /*jtfSubtotal.setValue(subtotal);
         jtfFactor.setValue(factor);
         jtfMonto.setValue(gravamen);
         jtfTotal.setValue(total);*/
        reescribirTotales();
        writeCurrency(elemento.getIdMoneda());
    }

    //---------------------------------------------------------------------
    private void actualizarTotal(float importe_neto, float monto)
    {
        total = total + importe_neto;
        gravamen = gravamen + monto;
        subtotal = total - gravamen;
        factor = (int) (gravamen * 100.00000F / subtotal);
        saldo_actual += total - pagado;

        if (saldo_actual < 0)
        {
            cambio = saldo_actual * -1;
            saldo_actual = 0;
        }

        else
            cambio = 0;

        /*elementoActual.setTotal(elementoActual.getTotal() + importe);
         elementoActual.setMonto(elementoActual.getMonto() + monto);
      
         elementoActual.setSubtotal(elementoActual.getTotal() - elementoActual.getMonto());
         elementoActual.setFactor((int)(elementoActual.getMonto() * 100.0000F / elementoActual.getSubtotal()));*/
    }

    //---------------------------------------------------------------------
    private void actualizarPago(float pago)
    {
        pagado += pago;
        saldo_actual -= pago;

        if (saldo_actual < 0)
        {
            cambio -= saldo_actual; //Convertimos el positiva la diferencia.
            saldo_actual = 0;
        }

        else
        {
            saldo_actual -= cambio;
            cambio = 0;
        }
    }

    //---------------------------------------------------------------------
    private void reescribirTotales()
    {
        jtfSubtotal.setValue(subtotal);
        jtfFactor.setValue(factor);
        jtfMonto.setValue(gravamen);
        jtfTotal.setValue(total);
        reescribirPagos();
    }

    //---------------------------------------------------------------------
    private void reescribirPagos()
    {
        jtfSaldoActual.setValue(saldo_actual);
        jtfCambio.setValue(cambio);
        jtfPagado.setValue(pagado);
    }

    //---------------------------------------------------------------------
    private void writeCurrency(int idMoneda)
    {
        if (bsMonedas.getSize() > 0)
            jcbMoneda.setSelectedIndex(bsMonedas.indexOfValue(idMoneda));
    }

    //---------------------------------------------------------------------
    private void invalidateChangeOfType(boolean value)
    {
        jcbTipoComprobante.setEnabled(!value);
    }

    //---------------------------------------------------------------------
    private boolean readElement(DocumentoComercial elemento)
    {
        boolean resultado = false;
        String mensaje = "";

        if (!jtfFolio.getText().isEmpty() && jcbTipoComprobante.getSelectedIndex() >= 0)
        {
            resultado = true;
            if (!elemento.isSet())
                elemento.setModified();
            elemento.setFolio(jtfFolio.getText());
            elemento.setSerie(jtfSerie.getText());
            elemento.setFechaElaboracion(jdcFechaElaboracion.getDate());
            elemento.setFechaVigencia(jdcFechaVigencia.getDate());
            elemento.setReceptor(jtfReceptor.getText());
            elemento.setCondiciones(jtfCondiciones.getText());
            elemento.setIdTipoComprobante((int) bsTipoComprobanteRender.getSelectedValue());
            elemento.setSubtotal((float) jtfSubtotal.getValue());
            elemento.setFactor((int) jtfFactor.getValue());
            elemento.setMonto((float) jtfMonto.getValue());
            elemento.setTotal((float) jtfTotal.getValue());
            elemento.setIdMoneda((int) bsMonedas.getSelectedValue());
            elemento.setPagado((float) jtfPagado.getValue());
            elemento.setSaldoActual((float) jtfSaldoActual.getValue());
            if (bsPersonaDireccionDocumentos.getRowCount() > 0)
            {
                elemento.setIdReceptor(getIdReceptor(bsPersonaDireccionDocumentos));
                elemento.setReceptor(jtfReceptor.getText());
            }

        /*    if (bsCondiciones.getRowCount() > 0)
            {
                elemento.setCondiciones(jtfCondiciones.getText());
            }*/
        }

        else
            mensaje = "Asegurese de proporcionar el Folio y tipo del documento";

        if (!resultado)
            JOptionPane.showMessageDialog(this, mensaje, "", JOptionPane.PLAIN_MESSAGE);

        return resultado;
    }

    //---------------------------------------------------------------------
    private int getIdReceptor(PersonaDireccionDocumentos5FNTableModel binding)
    {
        int id = -1, i = 0;
        List<PersonaDireccion_tiene_Documento_5FN> elementos = binding.getData();

        while (id < 0 && i < elementos.size())
        {
            if (elementos.get(i).getEsRol() == ID_RECEPTOR)
                id = elementos.get(i).getIdPersona();
            else
                i++;
        }

        return id;
    }

    //---------------------------------------------------------------------
    public void close()
    {
        setVisible(false);
        dispose();
    }

    //----------------------------------------------------------------------
    public void setDisplayData(List<PersonaDireccion_tiene_Documento_5FN> listaDirecciones, List<TipoPersona> lstTipos)
    {
        construirPopUpTipoPersonas(lstTipos);
        if (listaDirecciones != null)
            bsPersonaDireccionDocumentos.setData(listaDirecciones);
    }

    //----------------------------------------------------------------------
    private void setDisplayDataContadoAbonos(List<DocumentoContadoMovimiento_5FN> listaFormasPago)
    {
        if (listaFormasPago != null)
            bsContadoPagos.setData(listaFormasPago);
    }

    //----------------------------------------------------------------------
    public void setDisplayDetails(List<Categoria> listaCategorias, List<DocumentoDetalleNavigable> listaDetalles, List<MonedaCambioVista> listaTiposCambio)
    {
        construirPopUpCategorias(listaCategorias);

        if (listaDetalles != null)
        {
            bsDetalles.setData(listaDetalles);
            invalidateChangeOfType(listaDetalles.size() > 0);
        }

        listaUltimosTiposCambio = listaTiposCambio;
    }

    //----------------------------------------------------------------------
    private void revisarDetallesPendientesPrecio(boolean agregar)
    {
        int i = 0;
        while (i < pendientes_cambio.size())
        {
            if (calcularPrecioDetalle(pendientes_cambio.get(i)))
            {
                DocumentoDetalleNavigable actual = pendientes_cambio.remove(i);
                if (!actual.isSet())
                    actual.setModified();
                if (agregar)
                    bsDetalles.addRow(actual);
                actualizarTotal(actual.getImporte_neto(), actual.getMonto());
            }
            else
                i++; //Sigue pendiente de cambio el mismo detalle.
        }

        //Si existen detalles pendientes de cambio... entonces... otra vez...
        if (pendientes_cambio.size() > 0)
            showMoneyChangeDialog(listaUltimosTiposCambio);

        else
        {
            if (!agregar)
            {
                bsDetalles.fireTableDataChanged();
                esNuevoDetalle = true;
            }
            if (selected_old != null)
                selected_old = null; //Ya no hace falta regresar.
            reescribirTotales();
        }
    }

    //----------------------------------------------------------------------
    private void construirPopUpTipoPersonas(List<TipoPersona> lstTipos)
    {
        JMenuItem nodo;
        ActionListener popupTipoPersonasActionListener = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                popupTipoPersonas_actionPerformed(e);
            }
        };

        for (TipoPersona elemento : lstTipos)
        {
            nodo = new JBusinessMenuItem(elemento, elemento.getNombre());
            nodo.setName(String.valueOf(elemento.getId()));
            nodo.addActionListener(popupTipoPersonasActionListener);
            popupTipoPersonas.add(nodo);
        }
    }

    //----------------------------------------------------------------------
    private void construirPopUpCategorias(List<Categoria> lstCategorias)
    {
        JBusinessMenuItem nodo;
        ActionListener popupCategoriasActionListener = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                popupCategorias_actionPerformed(e);
            }
        };

        for (Categoria elemento : lstCategorias)
        {
            nodo = new JBusinessMenuItem(elemento, elemento.getNombre());
            nodo.setName(String.valueOf(elemento.getId()));
            nodo.addActionListener(popupCategoriasActionListener);
            popupCategorias.add(nodo);
        }
    }

    //--------------------------------------------------------------------
    /**
     * Solicitamos una ventana de búsqueda de personas.
     */
    protected void popupTipoPersonas_actionPerformed(ActionEvent e)
    {
        JMenuItem currentMenu = (JMenuItem) e.getSource();

        if (currentMenu instanceof JBusinessMenuItem)
        {
            TipoPersona current = (TipoPersona) ((JBusinessMenuItem) currentMenu).getTag();
            //JOptionPane.showMessageDialog(this, current.getNombre());
            JPersonaDireccionBusqueda dialogo = new JPersonaDireccionBusqueda(this);

            dialogo.limitarTiposPersonas(current);
            dialogo.setTitle(String.format("Busqueda de %s", current.getNombre()));
            dialogo.cargarCriteriosBusqueda();

            parent.agregaContenedorPrincipal(dialogo);
            parent.mostrarCentrado(dialogo);
        }
    }

    //-------------------------------------------------------------------
    /**
     * Solicitamos una ventana de búsqueda de personas.
     */
    protected void popupCategorias_actionPerformed(ActionEvent e)
    {
        JMenuItem currentMenu = (JMenuItem) e.getSource();

        if (currentMenu instanceof JBusinessMenuItem)
        {
            Categoria current = (Categoria) ((JBusinessMenuItem) currentMenu).getTag();

            //JOptionPane.showMessageDialog(this, current.getNombre());
            JControlPreciosBusqueda dialogo = new JControlPreciosBusqueda(this);

            dialogo.limitarPrecios(current, bsTipoComprobanteRender.getCurrent().getPermitePrecios());
            dialogo.setTitle(String.format("Busqueda de %s", current.getNombre()));
            dialogo.busquedaInicial();

            parent.agregaContenedorPrincipal(dialogo);
            parent.mostrarCentrado(dialogo);
        }
    }

    //---------------------------------------------------------------------
    @Override
    public void onAcceptNewElement(PersonaDireccion nuevo)
    {
        int index = jtPersonaTieneDocumentos.getSelectedRow();

        if (index >= 0)
        {
            PersonaDireccion_tiene_Documento seleccionado = bsPersonaDireccionDocumentos.getElementAt(index);
            PersonaDireccion inDireccion;

            inDireccion = seleccionado.getHasOnePersonaDireccion();
            inDireccion.copy(nuevo);

            if (!inDireccion.isSet())
            {
                inDireccion.setModified(); //Retener llave vieja que se haya guardado.
                inDireccion.getHasOnePersona().setModified();
            }

            if (!seleccionado.isSet())
            {
                if (seleccionado.getIdDocumento() == PersonaDireccion_tiene_Documento.EMPTY_INT
                        || seleccionado.getIdDocumento() != elementoActual.getId())
                {
                    seleccionado.setHasOneDocumento(elementoActual);
                    seleccionado.setNew();
                }

                else
                    seleccionado.setModified();
            }

            bsPersonaDireccionDocumentos.fireTableRowsUpdated(index, index);
            if (seleccionado.getEsRol() == ID_RECEPTOR)
                generarTextoReceptor(inDireccion.getHasOnePersona().getNombreCompleto());
        }

        else
            JOptionPane.showMessageDialog(this, "No ha seleccionado un rol");
    }

    //---------------------------------------------------------------------
    private void generarTextoReceptor(String value)
    {
        jtfReceptor.setText(value);
    }

    //---------------------------------------------------------------------
    @Override
    public boolean sonValidosVariosPrecios()
    {
        return jcbMoneda.getSelectedIndex() >= 0;
    }

    //---------------------------------------------------------------------
    @Override
    public void onAcceptNewElement(ControlPrecioVista nuevo)
    {
        crearNuevoDetalle(nuevo);
        invalidateChangeOfType(true);

        //Nota: Si después de crear el nuevo detalle este queda pendiente.
        //es porque nos falta definir un tipo de cambio.
        if (pendientes_cambio.size() > 0)
            showMoneyChangeDialog(listaUltimosTiposCambio);
        else
            reescribirTotales();
    }

    //---------------------------------------------------------------------
    @Override
    public void onAcceptNewElement(List<ControlPrecioVista> nuevos)
    {
        if (JOptionPane.showConfirmDialog(this, "¿Desea crear un paquete?", "Pregunta", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
            crearNuevoDetalle(nuevos);

        else
        {
            for (ControlPrecioVista nuevo : nuevos)
                crearNuevoDetalle(nuevo);
        }
        invalidateChangeOfType(true);

        //Nota: Si después de crear el nuevo detalle este queda pendiente.
        //es porque nos falta definir un tipo de cambio.
        if (pendientes_cambio.size() > 0)
            showMoneyChangeDialog(listaUltimosTiposCambio);
        else
            reescribirTotales();
    }

    //---------------------------------------------------------------------
    /**
     * Muestra el dialogo de captura de tipos de cambio con información inicial.
     */
    private void showMoneyChangeDialog(List<MonedaCambioVista> TiposCambio)
    {
        JMonedaCambioFramedDataView dialogo = new JMonedaCambioFramedDataView(this);

        dialogo.setTitle("Se requiere capturar el tipo de cambio");
        dialogo.prepareForModify(TiposCambio);

        parent.agregaContenedorPrincipal(dialogo);
        parent.mostrarCentrado(dialogo);
    }

    //---------------------------------------------------------------------
    private void crearNuevoDetalle(List<ControlPrecioVista> nuevos)
    {
        DocumentoDetalleNavigable nuevoDetalle = new DocumentoDetalleNavigable();
        DocumentoDetalle_tiene_PrecioVista nuevaParte;
        StringBuilder descripcion = new StringBuilder();
        //Nota: Todos los precios en la lista deberían ser el mismo.
        int id_moneda = nuevos.get(0).getIdMoneda();

        //1.- Establecemos datos del control de precio que vamos a relacionar.
        for (ControlPrecioVista nuevo : nuevos)
        {
            nuevaParte = new DocumentoDetalle_tiene_PrecioVista();
            nuevaParte.setIdPrecio(nuevo.getId());
            nuevaParte.setCantidad(1);
            nuevaParte.setPrecio(nuevo.getPrecioFinal());
            nuevaParte.setIdMonedaActual(nuevo.getIdMoneda());
         //nuevaParte.setTotal(total); calcular después de cambio de moneda.

            //1.1.- Calculamos la descripción del detalle.
            if (descripcion.length() > 0)
                descripcion.append(", ");
            descripcion.append(nuevo.getPresentacion());
         //precio_unitario += nuevo.getMercado();

            //1.1.- Relacionamos la parte con el detalle.
            nuevoDetalle.agregarParte(nuevaParte); //Se relaciona con el detalle la parte.
        }

        //2.- Determinamos la moneda utilizada
        if (jcbMoneda.getSelectedIndex() < 0)
            writeCurrency(id_moneda);

        //3.- Esblecemos  datos del detalle y lo relacionamos con el nuevo precio.
        nuevoDetalle.setHasOneDocumento(elementoActual);
        nuevoDetalle.setCantidad(1);
        nuevoDetalle.setDescripcion(descripcion.toString());
        nuevoDetalle.setFactor(0);

        //4.- Calculamos el precio que tendrá el nuevo detalle, basado en sus 
        //Partes.
        if (calcularPrecioDetalle(nuevoDetalle))
        {
            bsDetalles.addRow(nuevoDetalle);
            actualizarTotal(nuevoDetalle.getImporte_neto(), 0.0F);
        }

        else
            pendientes_cambio.add(nuevoDetalle);
    }

    //---------------------------------------------------------------------
    /**
     * Crea un detalle relacionado con el Control de precio dado.
     */
    private void crearNuevoDetalle(ControlPrecioVista nuevo)
    {
        DocumentoDetalleNavigable nuevoDetalle = new DocumentoDetalleNavigable();
        DocumentoDetalle_tiene_PrecioVista nuevaParte = new DocumentoDetalle_tiene_PrecioVista();

        //1.- Establecemos datos del control de precio que vamos a relacionar.
        nuevaParte.setIdPrecio(nuevo.getId());
        nuevaParte.setCantidad(1);
        nuevaParte.setPrecio(nuevo.getPrecioFinal()); //Precio en una moneda especifica.
        nuevaParte.setIdMonedaActual(nuevo.getIdMoneda());
        //nuevaParte.setTotal(total); calcular después de cambio de moneda.
        nuevoDetalle.agregarParte(nuevaParte); //Se relaciona con el detalle la parte.

        //2.- Determinamos la moneda utilizada
        if (jcbMoneda.getSelectedIndex() < 0) //No se había seleccionado una moneda previamente.
            writeCurrency(nuevo.getIdMoneda());

        //3.- Esblecemos  datos del detalle y lo relacionamos con el nuevo precio.
        nuevoDetalle.setHasOneDocumento(elementoActual);
        nuevoDetalle.setCantidad(1);
        nuevoDetalle.setDescripcion(nuevo.getPresentacion());
        nuevoDetalle.setFactor(0);

        //4.- Calculamos el precio que tendrá el nuevo detalle, basado en sus 
        //Partes.
        if (calcularPrecioDetalle(nuevoDetalle))
        {
            bsDetalles.addRow(nuevoDetalle);
            actualizarTotal(nuevoDetalle.getImporte_neto(), 0.0F);
        }

        else
            pendientes_cambio.add(nuevoDetalle);
    }

    //---------------------------------------------------------------------
    private boolean contiene(MonedaCambioVista nuevo, List<MonedaCambioVista> lista)
    {
        boolean existe = false;
        int i = 0;
        MonedaCambioVista presente;

        while (!existe && i < lista.size())
        {
            presente = lista.get(i++);
            existe = (nuevo.getIdMonedaOrigen() == presente.getIdMonedaOrigen()
                    && nuevo.getIdMonedaDestino() == presente.getIdMonedaDestino())
                    || (nuevo.getIdMonedaOrigen() == presente.getIdMonedaDestino()
                    && nuevo.getIdMonedaDestino() == presente.getIdMonedaOrigen());

        }

        return existe;
    }

    //---------------------------------------------------------------------
    private float calcularProporcion(int id_moneda_origen, Moneda destino)
    {
        float proporcion;
        MonedaCambioVista cambiar = new MonedaCambioVista();

        cambiar.setIdMonedaOrigen(id_moneda_origen);
        cambiar.getHasOneMonedaDestino().copy(destino);
        proporcion = monedasCambio.obtenerProporcionRequerida(cambiar, listaUltimosTiposCambio);

        if (!(proporcion > 0) && !contiene(cambiar, listaUltimosTiposCambio))
            listaUltimosTiposCambio.add(0, cambiar); //Debería especificarse la conversión directa

        return proporcion;
    }

    //---------------------------------------------------------------------
    /**
     * Calcula el precio de un detalle basado en sus partes.<br>
     * Nota: El tipo de cambio que sería necesario se agrega automaticamente.
     *
     * @param detalleActual será calculado.
     * @return Indica si se pudo completar la operación satisfactoriamente.
     */
    public boolean calcularPrecioDetalle(DocumentoDetalleNavigable detalleActual)
    {
        boolean interrumpir = false;
        List<DocumentoDetalle_tiene_PrecioVista> partes = detalleActual.getPartes();
        DocumentoDetalle_tiene_PrecioVista parteActual;
        int i = 0;
        float proporcion = 0.0F;
        int id_moneda_origen = -1;

        detalleActual.setPrecio(0.0F);
        //Nota: Todas las partes deberían estar expresadas en la misma moneda.
        while (!interrumpir && i < partes.size())
        {
            parteActual = partes.get(i++);
            //La moneda de la parte es distinta a la moneda del documento.
            if (((int) bsMonedas.getSelectedValue()) != parteActual.getIdMonedaActual())
            {
                //La nueva moneda es distinta a la anteriormente comprobada.
                if (id_moneda_origen != parteActual.getIdMonedaActual())
                {
                    id_moneda_origen = parteActual.getIdMonedaActual();
                    proporcion = calcularProporcion(parteActual.getIdMonedaActual(), bsMonedas.getCurrent());
                }

                if (proporcion > 0)
                {
                    parteActual.setPrecio(parteActual.getPrecio() * proporcion);
                    //Nota: Indicamos que ya esta expresado el cambio de moneda.
                    //parteActual.setIdMonedaActual((int) bsMonedas.getSelectedValue()); 
                    parteActual.setIdMonedaActual(bsMonedas.getCurrent().getId());
                }
                else
                    interrumpir = true;
            }

            //hubo algún falló durante la conversión, o no se requirio convertir la moneda.
            if (!interrumpir)
            {
                if (!parteActual.isSet())
                    parteActual.setModified();
                parteActual.setTotal(parteActual.getPrecio() * parteActual.getCantidad());
                detalleActual.setPrecio(detalleActual.getPrecio() + parteActual.getTotal());
                detalleActual.setMonto(detalleActual.getPrecio() * detalleActual.getFactor() / 100.00F);
                detalleActual.setImporte_neto(detalleActual.getPrecio() + detalleActual.getMonto());
                detalleActual.setImporte(detalleActual.getCantidad() * detalleActual.getImporte_neto());
            }
        }

        return !interrumpir;
    }

    //---------------------------------------------------------------------
    /**
     * Indica que monedas esta utilizando actualmente el formulario.
     *
     * @return
     */
    @Override
    public List<Moneda> obtenerMonedas()
    {
        return bsMonedas.getData();
    }

    //---------------------------------------------------------------------
    /**
     * Se aceptó la lista de tipos de cambio.
     *
     * @param elementos
     */
    @Override
    public void onAcceptModifyElement(List<MonedaCambioVista> elementos)
    {
        //Procedemos a guardar el tipo de cambio.
        if (swCargador == null || swCargador.isDone())
        {
            List<Object> parametros = new ArrayList<Object>();
            swCargador = new DocumentoDetailCargarSwingWorker();
            swCargador.addPropertyChangeListener(this);

            if (esNuevo || construidoRelacionPrecios)
            {
                parametros.add(opSAVE_PRICE);
                parametros.add(elementos);
                parametros.add(false);
                parametros.add(esNuevoDetalle); //Agregar pendientes al terminar
            }
            else
            {
                parametros.add(opSAVE_PRICE_AND_PRICE_PARTS);
                parametros.add(elementos);
                parametros.add(pendientes_cambio);
                parametros.add(esNuevoDetalle); //Agregar pendientes al terminar
                construidoRelacionPrecios = true;
            }
            showTaskHeader("Guardando cambios, espero un momento....", true);
            swCargador.execute(parametros);
        }
    }

    //---------------------------------------------------------------------
    @Override
    public void onCancelModifyElement(List<MonedaCambioVista> elementos)
    {
        //1.- Purgamos la lista de ultimos tipos de cambio.
        int i = 0;
        while (i < elementos.size())
        {
            if (elementos.get(i).isNew())
                elementos.remove(0);
            else
                i++;
        }

        //2.- Eliminamos todos los pendientes de cambio.
        pendientes_cambio.clear();

        //3.- Revertimos los cambios en la moneda, si los hubiere.
        if (selected_old != null)
        {
            revirtiendoMoneda = true;
            jcbMoneda.setSelectedItem(selected_old);
        }
    }

    //---------------------------------------------------------------------
    @Override
    public void bodyEnabled(boolean value)
    {
        //setEnabled(value);
        jpContenido.setEnabled(value);
        jpAcciones.setEnabled(value);

    }


    //---------------------------------------------------------------------
    @Override
    public String getEsquema()
    {
        return parent.getEsquema();
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

        popupTipoPersonas = new javax.swing.JPopupMenu();
        popupCategorias = new javax.swing.JPopupMenu();
        jpContenido = new javax.swing.JPanel();
        jpAreaDocumento = new javax.swing.JPanel();
        jpDatosDocumentos = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jtfCondiciones = new javax.swing.JTextField();
        jlbReceptor = new javax.swing.JLabel();
        jdcFechaVigencia = new com.toedter.calendar.JDateChooser();
        jbtnNuevoFolio = new javax.swing.JButton();
        jlbFecha = new javax.swing.JLabel();
        jdcFechaElaboracion = new com.toedter.calendar.JDateChooser();
        jlbVigencia = new javax.swing.JLabel();
        jtfReceptor = new javax.swing.JTextField();
        jtfSerie = new javax.swing.JTextField();
        jlbCondiciones = new javax.swing.JLabel();
        jtfFolio = new javax.swing.JTextField();
        jlbTipoComprobante = new javax.swing.JLabel();
        jcbTipoComprobante = new javax.swing.JComboBox();
        jtpAreaDetalles = new javax.swing.JTabbedPane();
        jTabLineas = new javax.swing.JPanel();
        jspDocumentoDetalles = new javax.swing.JScrollPane();
        jtbDocumentoDetalles = createJtbDetalles ();
        jtoolDocumentoDetalles = new javax.swing.JToolBar();
        jToolNuevoLinea = new javax.swing.JButton();
        jToolEliminarLinea = new javax.swing.JButton();
        jTabPersonas = new javax.swing.JPanel();
        jToolPersonaTieneDocumentos = new javax.swing.JToolBar();
        jbtnEspecificar = new javax.swing.JButton();
        jbtnQuitar = new javax.swing.JButton();
        jspPersonaTieneDocumentos = new javax.swing.JScrollPane();
        jtPersonaTieneDocumentos = new javax.swing.JTable();
        jTabDesglose = new javax.swing.JPanel();
        jpDesgloseArea1 = new javax.swing.JPanel();
        jpSubtotal = new javax.swing.JPanel();
        jlbSubtotal = new javax.swing.JLabel();
        jtfSubtotal = new JFormattedTextField(floatFormat);
        jlbFactor = new javax.swing.JLabel();
        jtfFactor = new JFormattedTextField(0);
        jlbMonto = new javax.swing.JLabel();
        jtfMonto = new JFormattedTextField(floatFormat);
        jpDesgloseArea2 = new javax.swing.JPanel();
        jpCelda1DesgloseArea2 = new javax.swing.JPanel();
        jspDocumentoContadoPagos = new javax.swing.JScrollPane();
        jtDocumentoContadoPagos = createJtbPagos();
        jpDesglosePagos = new javax.swing.JPanel();
        jpSubtotal1 = new javax.swing.JPanel();
        jlbPagado = new javax.swing.JLabel();
        jtfPagado = new JFormattedTextField(floatFormat);
        jlbSaldoActual = new javax.swing.JLabel();
        jtfSaldoActual = new JFormattedTextField(floatFormat);
        jlbCambio = new javax.swing.JLabel();
        jtfCambio = new JFormattedTextField(floatFormat);
        jpMontosTotales = new javax.swing.JPanel();
        jpTotal = new javax.swing.JPanel();
        jlbTotal = new javax.swing.JLabel();
        jtfTotal = new JFormattedTextField(floatFormat);
        jpMoneda = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jcbMoneda = new javax.swing.JComboBox();
        jpEncabezado = new javax.swing.JPanel();
        jlbIcono = new javax.swing.JLabel();
        jpAreaMensajes = new javax.swing.JPanel();
        jlbMensajes = new javax.swing.JLabel();
        jpbAvances = new javax.swing.JProgressBar();
        jpAcciones = new javax.swing.JPanel();
        jbCancelar = new javax.swing.JButton();
        jbAceptar = new javax.swing.JButton();

        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jpContenido.setLayout(new java.awt.BorderLayout());

        jpAreaDocumento.setPreferredSize(new java.awt.Dimension(800, 115));
        jpAreaDocumento.setLayout(new java.awt.BorderLayout());

        jpDatosDocumentos.setPreferredSize(new java.awt.Dimension(780, 102));
        jpDatosDocumentos.setLayout(new java.awt.GridBagLayout());

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Folio:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        jpDatosDocumentos.add(jLabel1, gridBagConstraints);

        jtfCondiciones.setEditable(false);
        jtfCondiciones.setEnabled(false);
        jtfCondiciones.setPreferredSize(new java.awt.Dimension(350, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 4;
        jpDatosDocumentos.add(jtfCondiciones, gridBagConstraints);

        jlbReceptor.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbReceptor.setText("Receptor:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        jpDatosDocumentos.add(jlbReceptor, gridBagConstraints);

        jdcFechaVigencia.setPreferredSize(new java.awt.Dimension(200, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 1;
        jpDatosDocumentos.add(jdcFechaVigencia, gridBagConstraints);

        jbtnNuevoFolio.setText("+");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        jpDatosDocumentos.add(jbtnNuevoFolio, gridBagConstraints);

        jlbFecha.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbFecha.setText("Fecha:");
        jlbFecha.setPreferredSize(new java.awt.Dimension(80, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        jpDatosDocumentos.add(jlbFecha, gridBagConstraints);

        jdcFechaElaboracion.setPreferredSize(new java.awt.Dimension(200, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        jpDatosDocumentos.add(jdcFechaElaboracion, gridBagConstraints);

        jlbVigencia.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jlbVigencia.setText("Vigencia:");
        jlbVigencia.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jlbVigencia.setPreferredSize(new java.awt.Dimension(80, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        jpDatosDocumentos.add(jlbVigencia, gridBagConstraints);

        jtfReceptor.setEditable(false);
        jtfReceptor.setEnabled(false);
        jtfReceptor.setPreferredSize(new java.awt.Dimension(350, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jpDatosDocumentos.add(jtfReceptor, gridBagConstraints);

        jtfSerie.setPreferredSize(new java.awt.Dimension(50, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        jpDatosDocumentos.add(jtfSerie, gridBagConstraints);

        jlbCondiciones.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbCondiciones.setText("Condiciones:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        jpDatosDocumentos.add(jlbCondiciones, gridBagConstraints);

        jtfFolio.setPreferredSize(new java.awt.Dimension(170, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        jpDatosDocumentos.add(jtfFolio, gridBagConstraints);

        jlbTipoComprobante.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbTipoComprobante.setText("Tipo:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        jpDatosDocumentos.add(jlbTipoComprobante, gridBagConstraints);

        jcbTipoComprobante.setModel(bsTipoComprobanteRender);
        jcbTipoComprobante.setPreferredSize(new java.awt.Dimension(240, 25));
        jcbTipoComprobante.setRenderer(new POJOListCellRenderer<TipoComprobante>());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jpDatosDocumentos.add(jcbTipoComprobante, gridBagConstraints);

        jpAreaDocumento.add(jpDatosDocumentos, java.awt.BorderLayout.CENTER);

        jpContenido.add(jpAreaDocumento, java.awt.BorderLayout.PAGE_START);

        jtpAreaDetalles.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jtpAreaDetalles.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
        jtpAreaDetalles.setToolTipText("");
        jtpAreaDetalles.setPreferredSize(new java.awt.Dimension(16, 200));

        jTabLineas.setLayout(new java.awt.BorderLayout());

        jtbDocumentoDetalles.setModel(bsDetalles);
        jtbDocumentoDetalles.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jspDocumentoDetalles.setViewportView(jtbDocumentoDetalles);

        jTabLineas.add(jspDocumentoDetalles, java.awt.BorderLayout.CENTER);

        jtoolDocumentoDetalles.setRollover(true);

        jToolNuevoLinea.setText("Nuevo");
        jToolNuevoLinea.setFocusable(false);
        jToolNuevoLinea.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jToolNuevoLinea.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jtoolDocumentoDetalles.add(jToolNuevoLinea);

        jToolEliminarLinea.setText("Eliminar");
        jToolEliminarLinea.setFocusable(false);
        jToolEliminarLinea.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jToolEliminarLinea.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jtoolDocumentoDetalles.add(jToolEliminarLinea);

        jTabLineas.add(jtoolDocumentoDetalles, java.awt.BorderLayout.PAGE_START);

        jtpAreaDetalles.addTab("Líneas", jTabLineas);

        jTabPersonas.setLayout(new java.awt.BorderLayout());

        jToolPersonaTieneDocumentos.setRollover(true);

        jbtnEspecificar.setText("Especificar");
        jbtnEspecificar.setFocusable(false);
        jbtnEspecificar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbtnEspecificar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolPersonaTieneDocumentos.add(jbtnEspecificar);

        jbtnQuitar.setText("Quitar");
        jbtnQuitar.setFocusable(false);
        jbtnQuitar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbtnQuitar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolPersonaTieneDocumentos.add(jbtnQuitar);

        jTabPersonas.add(jToolPersonaTieneDocumentos, java.awt.BorderLayout.NORTH);

        jtPersonaTieneDocumentos.setModel(bsPersonaDireccionDocumentos);
        jtPersonaTieneDocumentos.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jspPersonaTieneDocumentos.setViewportView(jtPersonaTieneDocumentos);

        jTabPersonas.add(jspPersonaTieneDocumentos, java.awt.BorderLayout.CENTER);

        jtpAreaDetalles.addTab("Personas", jTabPersonas);

        jTabDesglose.setLayout(new java.awt.BorderLayout());

        jpDesgloseArea1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jpSubtotal.setLayout(new java.awt.GridLayout(3, 2));

        jlbSubtotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbSubtotal.setText("Subtotal: $");
        jpSubtotal.add(jlbSubtotal);

        jtfSubtotal.setEditable(false);
        jtfSubtotal.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        jtfSubtotal.setText("000,000,000,000");
        jtfSubtotal.setEnabled(false);
        jtfSubtotal.setPreferredSize(new java.awt.Dimension(120, 25));
        jpSubtotal.add(jtfSubtotal);

        jlbFactor.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbFactor.setText("Factor(%):");
        jpSubtotal.add(jlbFactor);

        jtfFactor.setEditable(false);
        jtfFactor.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        jtfFactor.setText("000,000,000,000");
        jtfFactor.setEnabled(false);
        jtfFactor.setPreferredSize(new java.awt.Dimension(120, 25));
        jpSubtotal.add(jtfFactor);

        jlbMonto.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbMonto.setText("Monto: $");
        jpSubtotal.add(jlbMonto);

        jtfMonto.setEditable(false);
        jtfMonto.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        jtfMonto.setText("000,000,000,000");
        jtfMonto.setEnabled(false);
        jtfMonto.setPreferredSize(new java.awt.Dimension(120, 25));
        jpSubtotal.add(jtfMonto);

        jpDesgloseArea1.add(jpSubtotal);

        jTabDesglose.add(jpDesgloseArea1, java.awt.BorderLayout.NORTH);

        jpDesgloseArea2.setLayout(new java.awt.GridLayout(1, 1));

        jpCelda1DesgloseArea2.setLayout(new java.awt.BorderLayout());

        jtDocumentoContadoPagos.setModel(bsContadoPagos);
        jtDocumentoContadoPagos.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jspDocumentoContadoPagos.setViewportView(jtDocumentoContadoPagos);

        jpCelda1DesgloseArea2.add(jspDocumentoContadoPagos, java.awt.BorderLayout.CENTER);

        jpSubtotal1.setLayout(new java.awt.GridLayout(3, 2));

        jlbPagado.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbPagado.setText("Pagado: $");
        jpSubtotal1.add(jlbPagado);

        jtfPagado.setEditable(false);
        jtfPagado.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        jtfPagado.setText("000,000,000,000");
        jtfPagado.setEnabled(false);
        jtfPagado.setPreferredSize(new java.awt.Dimension(120, 25));
        jpSubtotal1.add(jtfPagado);

        jlbSaldoActual.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbSaldoActual.setText("Restante: $");
        jpSubtotal1.add(jlbSaldoActual);

        jtfSaldoActual.setEditable(false);
        jtfSaldoActual.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        jtfSaldoActual.setText("000,000,000,000");
        jtfSaldoActual.setEnabled(false);
        jtfSaldoActual.setPreferredSize(new java.awt.Dimension(120, 25));
        jpSubtotal1.add(jtfSaldoActual);

        jlbCambio.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbCambio.setText("Cambio: $");
        jpSubtotal1.add(jlbCambio);

        jtfCambio.setEditable(false);
        jtfCambio.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        jtfCambio.setText("000,000,000,000");
        jtfCambio.setEnabled(false);
        jtfCambio.setPreferredSize(new java.awt.Dimension(120, 25));
        jpSubtotal1.add(jtfCambio);

        jpDesglosePagos.add(jpSubtotal1);

        jpCelda1DesgloseArea2.add(jpDesglosePagos, java.awt.BorderLayout.EAST);

        jpDesgloseArea2.add(jpCelda1DesgloseArea2);

        jTabDesglose.add(jpDesgloseArea2, java.awt.BorderLayout.CENTER);

        jtpAreaDetalles.addTab("Desglose", jTabDesglose);

        jpContenido.add(jtpAreaDetalles, java.awt.BorderLayout.CENTER);

        jpMontosTotales.setPreferredSize(new java.awt.Dimension(800, 45));
        jpMontosTotales.setVerifyInputWhenFocusTarget(false);
        jpMontosTotales.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 5, 0));

        jpTotal.setPreferredSize(new java.awt.Dimension(150, 40));
        jpTotal.setLayout(new java.awt.BorderLayout());

        jlbTotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jlbTotal.setText("Total: $");
        jlbTotal.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jpTotal.add(jlbTotal, java.awt.BorderLayout.CENTER);

        jtfTotal.setEditable(false);
        jtfTotal.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        jtfTotal.setText("000,000,000,000");
        jtfTotal.setEnabled(false);
        jtfTotal.setPreferredSize(new java.awt.Dimension(120, 25));
        jpTotal.add(jtfTotal, java.awt.BorderLayout.SOUTH);

        jpMontosTotales.add(jpTotal);

        jpMoneda.setPreferredSize(new java.awt.Dimension(100, 40));
        jpMoneda.setLayout(new java.awt.BorderLayout());

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText("Moneda:");
        jLabel2.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel2.setPreferredSize(new java.awt.Dimension(24, 15));
        jLabel2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jpMoneda.add(jLabel2, java.awt.BorderLayout.CENTER);

        jcbMoneda.setModel(bsMonedas);
        jcbMoneda.setRenderer(new POJOListCellRenderer<Moneda>());
        jpMoneda.add(jcbMoneda, java.awt.BorderLayout.SOUTH);

        jpMontosTotales.add(jpMoneda);

        jpContenido.add(jpMontosTotales, java.awt.BorderLayout.SOUTH);

        getContentPane().add(jpContenido, java.awt.BorderLayout.CENTER);

        jpEncabezado.setBackground(new java.awt.Color(223, 212, 99));
        jpEncabezado.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP));
        jpEncabezado.setPreferredSize(new java.awt.Dimension(704, 54));
        java.awt.FlowLayout flowLayout1 = new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 20, 0);
        flowLayout1.setAlignOnBaseline(true);
        jpEncabezado.setLayout(flowLayout1);

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

        jpAcciones.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jpAcciones.setPreferredSize(new java.awt.Dimension(215, 50));
        jpAcciones.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 5, 3));

        jbCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/syswave/forms/resources/button-cross.png"))); // NOI18N
        jbCancelar.setText("Cancelar");
        jpAcciones.add(jbCancelar);

        jbAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/syswave/forms/resources/button-tick.png"))); // NOI18N
        jbAceptar.setText("Aceptar");
        jpAcciones.add(jbAceptar);

        getContentPane().add(jpAcciones, java.awt.BorderLayout.PAGE_END);

        setBounds(0, 0, 842, 582);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jTabDesglose;
    private javax.swing.JPanel jTabLineas;
    private javax.swing.JPanel jTabPersonas;
    private javax.swing.JButton jToolEliminarLinea;
    private javax.swing.JButton jToolNuevoLinea;
    private javax.swing.JToolBar jToolPersonaTieneDocumentos;
    private javax.swing.JButton jbAceptar;
    private javax.swing.JButton jbCancelar;
    private javax.swing.JButton jbtnEspecificar;
    private javax.swing.JButton jbtnNuevoFolio;
    private javax.swing.JButton jbtnQuitar;
    private javax.swing.JComboBox jcbMoneda;
    private javax.swing.JComboBox jcbTipoComprobante;
    private com.toedter.calendar.JDateChooser jdcFechaElaboracion;
    private com.toedter.calendar.JDateChooser jdcFechaVigencia;
    private javax.swing.JLabel jlbCambio;
    private javax.swing.JLabel jlbCondiciones;
    private javax.swing.JLabel jlbFactor;
    private javax.swing.JLabel jlbFecha;
    private javax.swing.JLabel jlbIcono;
    private javax.swing.JLabel jlbMensajes;
    private javax.swing.JLabel jlbMonto;
    private javax.swing.JLabel jlbPagado;
    private javax.swing.JLabel jlbReceptor;
    private javax.swing.JLabel jlbSaldoActual;
    private javax.swing.JLabel jlbSubtotal;
    private javax.swing.JLabel jlbTipoComprobante;
    private javax.swing.JLabel jlbTotal;
    private javax.swing.JLabel jlbVigencia;
    private javax.swing.JPanel jpAcciones;
    private javax.swing.JPanel jpAreaDocumento;
    private javax.swing.JPanel jpAreaMensajes;
    private javax.swing.JPanel jpCelda1DesgloseArea2;
    private javax.swing.JPanel jpContenido;
    private javax.swing.JPanel jpDatosDocumentos;
    private javax.swing.JPanel jpDesgloseArea1;
    private javax.swing.JPanel jpDesgloseArea2;
    private javax.swing.JPanel jpDesglosePagos;
    private javax.swing.JPanel jpEncabezado;
    private javax.swing.JPanel jpMoneda;
    private javax.swing.JPanel jpMontosTotales;
    private javax.swing.JPanel jpSubtotal;
    private javax.swing.JPanel jpSubtotal1;
    private javax.swing.JPanel jpTotal;
    private javax.swing.JProgressBar jpbAvances;
    private javax.swing.JScrollPane jspDocumentoContadoPagos;
    private javax.swing.JScrollPane jspDocumentoDetalles;
    private javax.swing.JScrollPane jspPersonaTieneDocumentos;
    private javax.swing.JTable jtDocumentoContadoPagos;
    private javax.swing.JTable jtPersonaTieneDocumentos;
    private javax.swing.JTable jtbDocumentoDetalles;
    private javax.swing.JFormattedTextField jtfCambio;
    private javax.swing.JTextField jtfCondiciones;
    private javax.swing.JFormattedTextField jtfFactor;
    private javax.swing.JTextField jtfFolio;
    private javax.swing.JFormattedTextField jtfMonto;
    private javax.swing.JFormattedTextField jtfPagado;
    private javax.swing.JTextField jtfReceptor;
    private javax.swing.JFormattedTextField jtfSaldoActual;
    private javax.swing.JTextField jtfSerie;
    private javax.swing.JFormattedTextField jtfSubtotal;
    private javax.swing.JFormattedTextField jtfTotal;
    private javax.swing.JToolBar jtoolDocumentoDetalles;
    private javax.swing.JTabbedPane jtpAreaDetalles;
    private javax.swing.JPopupMenu popupCategorias;
    private javax.swing.JPopupMenu popupTipoPersonas;
    // End of variables declaration//GEN-END:variables

    //------------------------------------------------------------------
    private class DocumentoDetailCargarSwingWorker extends SwingWorker<List<Object>, Void>
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

                //setProgress(0);
                switch (opcion)
                {
                    case opLOAD_ADDRESS:
                        setProgress(30);
                        arguments.add(parent.obtenerPersonaDireccionDocumentos((DocumentoComercial) arguments.get(1)));
                        setProgress(60);
                        arguments.add(tiposPersonas.obtenerLista((TipoPersona) arguments.get(2)));
                        break;

                    case opLOAD_RESOURCES:
                        setProgress(60);
                        arguments.add(categorias.obtenerLista((Categoria) arguments.get(1)));
                        setProgress(80);
                        arguments.add(monedasCambio.obtenerUltimosTiposCambio(true));
                        break;

                    case opLOAD_RESOURCES_AND_DETAILS:
                        setProgress(30);
                        arguments.add(parent.obtenerDocumentoDetalles((DocumentoComercial) arguments.get(1)));
                        setProgress(60);
                        arguments.add(categorias.obtenerLista((Categoria) arguments.get(2)));
                        setProgress(80);
                        arguments.add(monedasCambio.obtenerUltimosTiposCambio(true));
                        break;

                    case opSAVE_PRICE:
                        setProgress(60);
                        arguments.add(monedasCambio.guardar((List<MonedaCambioVista>) arguments.get(1)));
                        break;

                    case opSAVE_PRICE_AND_PRICE_PARTS:
                        setProgress(30);
                        arguments.add(monedasCambio.guardar((List<MonedaCambioVista>) arguments.get(1)));
                    case opLOAD_PRICE_PARTS:
                        List<DocumentoDetalleNavigable> pendientes = (List<DocumentoDetalleNavigable>) arguments.get(2);
                        DocumentoDetalleNavigable actual;
                        setProgress(50);
                        List<DocumentoDetalle_tiene_PrecioVista> todoEnDocumento = parent.obtenerDetalleTienePrecios(elementoActual);

                        for (int i = 0; i < pendientes.size(); i++)
                        {
                            setProgress(i * 50 / pendientes.size());
                            actual = pendientes.get(i);
                            if (!actual.isNew())
                                llenarDetallePartes(actual, todoEnDocumento);
                            /*if (!actual.isNew() && !actual.tienePartes())
                             actual.getPartes().addAll(parent.obtenerDetalleTienePrecios(actual));*/

                        }
                        if (todoEnDocumento.size() > 0)
                            todoEnDocumento.clear();
                        break;

                    case opLOAD_PAIDS:
                        setProgress(60);
                        arguments.add(parent.obtenerFormasPago((DocumentoComercial) arguments.get(1)));
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
                        case opLOAD_ADDRESS:
                            if (tiposPersonas.esCorrecto())
                            {
                                setDisplayData((List<PersonaDireccion_tiene_Documento_5FN>) results.get(3),
                                               (List<TipoPersona>) results.get(4));
                                showTaskHeader("", false);
                            }
                            else
                                JOptionPane.showMessageDialog(null, tiposPersonas.getMensaje(), "Error", JOptionPane.ERROR_MESSAGE);
                            break;

                        case opLOAD_RESOURCES:
                            if (categorias.esCorrecto())
                            {
                                List<MonedaCambioVista> listaCambios = (List<MonedaCambioVista>) results.get(3);
                                setDisplayDetails((List<Categoria>) results.get(2), null, listaCambios);
                                showTaskHeader("", false);
                            }
                            else
                                JOptionPane.showMessageDialog(null, categorias.getMensaje(), "Error", JOptionPane.ERROR_MESSAGE);
                            break;

                        case opLOAD_RESOURCES_AND_DETAILS:
                            if (categorias.esCorrecto())
                            {
                                List<MonedaCambioVista> listaCambios = (List<MonedaCambioVista>) results.get(5);
                                setDisplayDetails((List<Categoria>) results.get(4), (List<DocumentoDetalleNavigable>) results.get(3), listaCambios);
                                showTaskHeader("", false);
                            }
                            else
                                JOptionPane.showMessageDialog(null, categorias.getMensaje(), "Error", JOptionPane.ERROR_MESSAGE);
                            break;

                        case opSAVE_PRICE:
                        case opSAVE_PRICE_AND_PRICE_PARTS:
                            if (monedasCambio.esCorrecto())
                            {
                                revisarDetallesPendientesPrecio((boolean) results.get(3));
                                showTaskHeader("", false);
                            }
                            else
                                JOptionPane.showMessageDialog(null, monedasCambio.getMensaje(), "Error", JOptionPane.ERROR_MESSAGE);

                            break;

                        case opLOAD_PRICE_PARTS:
                            revisarDetallesPendientesPrecio((boolean) results.get(3));
                            showTaskHeader("", false);
                            break;

                        case opLOAD_PAIDS:
                            setDisplayDataContadoAbonos((List<DocumentoContadoMovimiento_5FN>) results.get(2));
                            showTaskHeader("", false);
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

        private void llenarDetallePartes(DocumentoDetalleNavigable actual, List<DocumentoDetalle_tiene_PrecioVista> todos)
        {
            boolean termino = false;
            int i = 0, consecutivo = -1;
            DocumentoDetalle_tiene_PrecioVista elementoPrecio;

            while (!termino && i < todos.size())
            {
                elementoPrecio = todos.get(i);

                //Todos los elementos deben venir odernados por el consecutivo al que pertenecen.
                if (elementoPrecio.getConsecutivo() == actual.getConsecutivo())
                {
                    consecutivo = actual.getConsecutivo();
                    actual.agregarParte(todos.remove(i));
                }

                else if (consecutivo > 0)
                    termino = true;

                else
                    i++;
            }
        }
           
   }
}
