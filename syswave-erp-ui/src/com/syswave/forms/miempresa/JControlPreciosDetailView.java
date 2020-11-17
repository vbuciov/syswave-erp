package com.syswave.forms.miempresa;

import com.orbital.AutoCompleteDocument;
import com.syswave.entidades.common.Entidad;
import com.syswave.entidades.miempresa.AreaPrecio;
import com.syswave.entidades.miempresa.Bien;
import com.syswave.entidades.miempresa.BienVariante;
import com.syswave.entidades.miempresa.ControlPrecio;
import com.syswave.entidades.miempresa.ControlPrecio_tiene_Area;
import com.syswave.entidades.miempresa.ControlPrecio_tiene_Tipo;
import com.syswave.entidades.miempresa.DesgloseCosto;
import com.syswave.entidades.miempresa.Moneda;
import com.syswave.entidades.miempresa.Valor;
import com.syswave.entidades.miempresa_vista.ControlPrecioVista;
import com.syswave.entidades.miempresa_vista.DesgloseCosto_5FN;
import com.syswave.swing.JBusinessMenuItem;
import com.syswave.swing.models.POJOTableModel;
import com.syswave.forms.databinding.AreasPreciosComboBoxModel;
import com.syswave.forms.databinding.BienVariantesComboBoxModel;
import com.syswave.forms.databinding.ControlPrecioAreaTableModel;
import com.syswave.forms.databinding.ControlPrecioTipoTableModel;
import com.syswave.forms.databinding.DesgloseCostos_5FNTableModel;
import com.syswave.forms.databinding.MonedasComboBoxModel;
import com.syswave.forms.databinding.ValorComboBoxModel;
import com.syswave.swing.renders.POJOListCellRenderer;
import com.syswave.logicas.miempresa.DesgloseCostosBusinessLogic;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
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
public class JControlPreciosDetailView extends javax.swing.JInternalFrame implements IPrecioBusquedaMediator, TableModelListener, PropertyChangeListener
{

    private final int opLOAD_DETAILS = 0;

    private IControlPrecio parent;
    private ControlPrecio elementoActual;

    private DesgloseCostosBusinessLogic desglose_costos;
    private BienVariantesComboBoxModel bsPresentacionesRender;
    private DesgloseCostos_5FNTableModel bsDesgloseCostos;
    private MonedasComboBoxModel bsMonedasRender;
    //private ValorComboBoxModel bsTipoPrecioRender;
    private AreasPreciosComboBoxModel bsAreasPreciosRender;
    private ControlPrecioAreaTableModel bsDesgloseAreas;
    private ControlPrecioTipoTableModel bsDesgloseBienes;
    
    private NumberFormatter floatFormat;

    private boolean esNuevo, esEscribiendo, construidoTotalesArea, 
                    construidoTotalesBienes;

    private ControlPreciosCargarSwingWorker swCargador;
    private TableColumn colNumber, colAreaPrecio, colRecurso, 
            //colUnidad,
            colCantidad, colTotal, colMonto, colFactor,
            colPrecio, colSubTotal, colObservacion;

    private float costo_directo;

    private List<DesgloseCosto> cache_eliminados;

    /**
     * Creates new form JBienDetailView
     *
     * @param owner
     */
    public JControlPreciosDetailView(IControlPrecio owner)
    {
        parent = owner;
        initAtributes(/*owner.obtenerOrigenDato()*/);
        initComponents();
        initEvents();
        showTaskHeader("", false);

        if (jtbDesgloseCostos.getColumnCount() > 0)
        {
            colNumber = jtbDesgloseCostos.getColumnModel().getColumn(0);
            colNumber.setPreferredWidth(40);

            colAreaPrecio = jtbDesgloseCostos.getColumnModel().getColumn(1);
            /* colRecursos.setCellRenderer(new LookUpComboBoxTableCellRenderer<ControlPrecioVista>(bsPreciosRender));
             colRecursos.setCellEditor(new LookUpComboBoxTableCellEditor<ControlPrecioVista>(bsPreciosEditor));*/
            colAreaPrecio.setPreferredWidth(120);

            colRecurso = jtbDesgloseCostos.getColumnModel().getColumn(2);
            colRecurso.setPreferredWidth(200);

            /*colUnidad = jtbDesgloseCostos.getColumnModel().getColumn(3);
            colUnidad.setPreferredWidth(80);*/

            colCantidad = jtbDesgloseCostos.getColumnModel().getColumn(3);
            colCantidad.setPreferredWidth(80);

            colPrecio = jtbDesgloseCostos.getColumnModel().getColumn(4);
            colPrecio.setPreferredWidth(80);

            colSubTotal = jtbDesgloseCostos.getColumnModel().getColumn(5);
            colSubTotal.setPreferredWidth(80);

            colMonto = jtbDesgloseCostos.getColumnModel().getColumn(6);
            colMonto.setPreferredWidth(80);

            colFactor = jtbDesgloseCostos.getColumnModel().getColumn(7);
            colFactor.setPreferredWidth(60);

            colTotal = jtbDesgloseCostos.getColumnModel().getColumn(8);
            colTotal.setPreferredWidth(80);

            colObservacion = jtbDesgloseCostos.getColumnModel().getColumn(9);
            colObservacion.setPreferredWidth(250);

            jtbDesgloseCostos.setRowHeight((int) (jtbDesgloseCostos.getRowHeight() * 1.5));
        }
        AutoCompleteDocument.enable(jcbPresentaciones);
        //AutoCompleteDocument.enable(jcbTipo);
        AutoCompleteDocument.enable(jcbAreasPrecios);
    }

    //---------------------------------------------------------------------
    private void initAtributes(/*String esquema*/)
    {
        esNuevo = true;
        esEscribiendo = false;
        construidoTotalesArea = false;
        construidoTotalesBienes = false;
        bsPresentacionesRender = new BienVariantesComboBoxModel();
        bsDesgloseCostos = new DesgloseCostos_5FNTableModel(new String[]
        {
            "No.:{#}", "Area Precio:{area_precio}", "Recurso:{presentacion}", 
            "Cantidad:{cantidad}", "Precio:{precio}", "SubTotal:{subtotal}", 
            "Monto:{monto}", "%:{factor}", "Total:{total}", 
            "Observación:{observacion}"
        });
        bsDesgloseCostos.addTableModelListener(this);
        bsDesgloseAreas = new ControlPrecioAreaTableModel(new String[]{
            "Descripción:{descripcion}", "Cantidad:{cantidad}", "Subtotal:{subtotal}", 
            "Monto:{monto}", "%:{factor}", "Total:{total}"
        });
        
        bsDesgloseBienes = new ControlPrecioTipoTableModel(new String[]{
            "Descripción:{descripcion}", "Cantidad:{cantidad}", "Subtotal:{subtotal}", 
            "Monto:{monto}", "%:{factor}", "Total:{total}"
        });
        
        bsMonedasRender = new MonedasComboBoxModel();
        //bsTipoPrecioRender = new ValorComboBoxModel();
        bsAreasPreciosRender = new AreasPreciosComboBoxModel();
        //bsPreciosRender = new ControlPrecioVistaComboBoxModel();
        //bsPreciosEditor = new ControlPrecioVistaComboBoxModel();
        floatFormat = new NumberFormatter(new DecimalFormat("#.####"));
        floatFormat.setValueClass(Float.class);

        cache_eliminados = new ArrayList<>();
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

        /*DocumentListener documentListener = new DocumentListener()
         {
         @Override
         public void changedUpdate(DocumentEvent documentEvent)
         {
         textChanged(documentEvent);
         }

         @Override
         public void insertUpdate(DocumentEvent documentEvent)
         {
         textChanged(documentEvent);
         }

         @Override
         public void removeUpdate(DocumentEvent documentEvent)
         {
         textChanged(documentEvent);
         }
         };*/
        ActionListener composeActionListener
                = new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent evt)
                    {
                        detailsToolBar_actionPerformed(evt);
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
        jbAceptar.addActionListener(actionListenerManager);
        jbCancelar.addActionListener(actionListenerManager);
        /*jtfBase.getDocument().addDocumentListener(documentListener);
         jtfMargen.getDocument().addDocumentListener(documentListener);
         jtfFactor.getDocument().addDocumentListener(documentListener);
         jtfFinal.getDocument().addDocumentListener(documentListener);*/

        jtfBase.addPropertyChangeListener("value", this);
        jtfMargen.addPropertyChangeListener("value", this);
        jtfFactor.addPropertyChangeListener("value", this);
        jtfFinal.addPropertyChangeListener("value", this);
        jToolNuevoLinea.addActionListener(composeActionListener);
        jToolEliminarLinea.addActionListener(composeActionListener);
        tpCuerpo.addChangeListener(changeListenerManager);
    }

    //--------------------------------------------------------------------
    /**
     * Construye una instancia de tabla con detección de cambios en celdas.
     */
    private JTable createJtbDetalles()
    {
        return new JTable(bsDesgloseCostos)
        {
            @Override // Always selectAll()
            public void editingStopped(ChangeEvent e)
            {
                jtbAnalisis_onCellValueChanged(this, getCellEditor());
                super.editingStopped(e);
            }
        };
    }

    //---------------------------------------------------------------------
    private void bodyTabbed_stateChanged(ChangeEvent e)
    {
        /*if (swCargador == null || swCargador.isDone())
         {*/
        JTabbedPane pane = (JTabbedPane) e.getSource();
        if (pane.getSelectedComponent() == tabTotalesArea && !construidoTotalesArea)
        {
            /*List<Object> parametros = new ArrayList<Object>();
            swCargador = new BienDetailCargarSwingWorker();
            if (esNuevo)
                parametros.add(opLOAD_PARTES);
            else
            {
                parametros.add(opLOAD_COMPUESTOS);
                parametros.add(elementoActual);
            }
            swCargador.execute(parametros);*/
            construirDesgloseAreas(bsDesgloseCostos);
            construidoTotalesArea = true;
        }
        
        else if (pane.getSelectedComponent() == tabBienes && !construidoTotalesBienes)
        {
            /*List<Object> parametros = new ArrayList<Object>();
            swCargador = new BienDetailCargarSwingWorker();
            if (esNuevo)
                parametros.add(opLOAD_PARTES);
            else
            {
                parametros.add(opLOAD_COMPUESTOS);
                parametros.add(elementoActual);
            }
            swCargador.execute(parametros);*/
            construirDesgloseTipos(bsDesgloseCostos);
            construidoTotalesBienes = true;
        }
        //}
    }
    
    //---------------------------------------------------------------------
    private void construirDesgloseAreas(DesgloseCostos_5FNTableModel bsOrigenCostos)
    {
        for (int i= 0; i < bsOrigenCostos.getRowCount(); i++)
        {
            DesgloseCosto_5FN actual = bsOrigenCostos.getElementAt(i);
            actualizarTotalArea(actual.getIdArea_precio(), actual.getCantidad(), actual.getTotal() );
        }
    }
    
    //---------------------------------------------------------------------
    private void actualizarTotalArea (int id_area, float cantidad, float total)
    {
        ControlPrecio_tiene_Area element = getArea(id_area);
        element.setCantidad(element.getCantidad() + cantidad);
        element.setSubtotal(element.getSubtotal()+ total);
        element.setTotal(element.getTotal() + total);
    }
    
    //---------------------------------------------------------------------
    private ControlPrecio_tiene_Area getArea (int id_area)
    {
        ControlPrecio_tiene_Area area = null;
        boolean seEncontro = false;
        int i = 0;
        
        while (!seEncontro && i < bsDesgloseAreas.getRowCount())
        {
            seEncontro = bsDesgloseAreas.getElementAt(i).getIdAreaPrecio() == id_area;
            if (seEncontro)
                area = bsDesgloseAreas.getElementAt(i);
            i++;
        }
        
        
        if (area == null || !seEncontro)
        {
            area = new ControlPrecio_tiene_Area();
            area.setCantidad(0.0F);
            area.setSubtotal(0.0F);
            area.setMonto(0.0F);
            area.setFactor(0);
            area.setTotal(0.0F);
            area.setIdAreaPrecio(id_area);
            bsDesgloseAreas.addRow(area);
        }
        
        return area;
    }
    
     //---------------------------------------------------------------------
    private void construirDesgloseTipos(DesgloseCostos_5FNTableModel bsOrigenCostos)
    {
        for (int i= 0; i < bsOrigenCostos.getRowCount(); i++)
        {
            DesgloseCosto_5FN actual = bsOrigenCostos.getElementAt(i);
            actualizarTotalTipo(actual.getId_grupo(), actual.getCantidad(), actual.getTotal() );
        }
    }
    
    //---------------------------------------------------------------------
    private void actualizarTotalTipo (int id_grupo, float cantidad, float total)
    {
        ControlPrecio_tiene_Tipo element = getGrupo(id_grupo);
         element.setCantidad(element.getCantidad() + cantidad);
        element.setSubtotal(element.getSubtotal()+ total);
        element.setTotal(element.getTotal() + total);
    }
    
    //---------------------------------------------------------------------
    private ControlPrecio_tiene_Tipo getGrupo (int id_grupo)
    {
        ControlPrecio_tiene_Tipo type = null;
        boolean seEncontro = false;
        int i = 0;
        
        while (!seEncontro && i < bsDesgloseBienes.getRowCount())
        {
            seEncontro = bsDesgloseBienes.getElementAt(i).getIdBien()== id_grupo;
            if (seEncontro)
                type = bsDesgloseBienes.getElementAt(i);
            i++;
        }
        
        
        if (type == null || !seEncontro)
        {
            type = new ControlPrecio_tiene_Tipo();
            type.setCantidad(0.0F);
            type.setSubtotal(0.0F);
            type.setMonto(0.0F);
            type.setFactor(0);
            type.setTotal(0.0F);
            type.setIdBien(id_grupo);
            bsDesgloseBienes.addRow(type);
        }
        
        return type;
    }

    //---------------------------------------------------------------------
    private void finish_actionPerformed(ActionEvent evt)
    {
        Object sender = evt.getSource();

        if (sender == jbAceptar)
        {
            if (readElement(elementoActual))
            {
                if (jtbDesgloseCostos.isEditing())
                {
                    TableCellEditor editor = jtbDesgloseCostos.getCellEditor();
                    if (editor != null)
                        editor.stopCellEditing();
                }

                if (esNuevo)
                    parent.onAcceptNewElement(elementoActual, bsDesgloseCostos.getData());

                else
                {
                    elementoActual.setModified();
                    parent.onAcceptModifyElement(elementoActual, bsDesgloseCostos.getData(), cache_eliminados);
                }

                close();
            }
        }

        else
            close();
    }

    //---------------------------------------------------------------------
    private void detailsToolBar_actionPerformed(ActionEvent evt)
    {
        //int rowIndex;

        if (evt.getSource() == jToolNuevoLinea)
        {
            popupAreasIndirectas.show(jToolNuevoLinea, 0, (int) jToolNuevoLinea.getPreferredSize().getHeight());

        }

        else if (evt.getSource() == jToolEliminarLinea)
        {
            if (JOptionPane.showConfirmDialog(this, String.format("Ha seleccionado %d registro(s) para ser eleiminado(s)\n¿Esta seguro de continuar?", jtbDesgloseCostos.getSelectedRowCount())) == JOptionPane.OK_OPTION)
            {
                int[] rowsHandlers = jtbDesgloseCostos.getSelectedRows();
                List<DesgloseCosto_5FN> selecteds = bsDesgloseCostos.removeRows(rowsHandlers);

                for (DesgloseCosto elemento : selecteds)
                {
                    if (!elemento.isNew())
                        cache_eliminados.add(elemento);

                    //actualizarTotal(elemento.getCantidad() * elemento.getPrecio_final() * -1, elemento.getCantidad() * elemento.getMargen() * -1);
                }

                //invalidateChangeOfType(jtbDesgloseCostos.getRowCount() > 0);
                //reescribirTotales();
            }
        }
    }

   //---------------------------------------------------------------------
   /*private void composeToolBar_actionPerformed (ActionEvent evt)
     {
     int rowIndex;
      
     if (evt.getSource() == jtoolNuevoCompuesto)
     {
         
     BienCompuestoVista nuevaDireccion = new BienCompuestoVista();
     nuevaDireccion.setFk_compuesto_formal(elementoActual);
     nuevaDireccion.setCantidad(1);
     nuevaDireccion.setNombreUnidad("");
     rowIndex = bsCompuestos.addRow(nuevaDireccion);
     jtbPartes.setRowSelectionInterval(rowIndex, rowIndex);
     //jtbDirecciones.editCellAt(rowIndex, 0);
     }
      
     else if (evt.getSource() == jtoolEliminarCompuesto)
     {
     if ( JOptionPane.showConfirmDialog(this,String.format("Ha seleccionado %d registro(s) para ser eleiminado(s)\n¿Esta seguro de continuar?",  jtbPartes.getSelectedRowCount()) ) == JOptionPane.OK_OPTION)
     {
     int[] rowsHandlers = jtbPartes.getSelectedRows();
     List<BienCompuestoVista> selecteds = bsCompuestos.removeRows(rowsHandlers);

     for (BienCompuestoVista elemento : selecteds )
     {
     if ( !elemento.isNew() )
     deleteds.add(elemento);
     }
     }
     }
     }*/
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
            }
        }
    }

    //---------------------------------------------------------------------
    private void crearPopUpAreasIndirectas(List<AreaPrecio> data)
    {
        JBusinessMenuItem nodo;
        ActionListener popupAreasPreciosActionListener = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                popupAreasPrecio_actionPerformed(e);
            }
        };

        for (AreaPrecio elemento : data)
        {
            if (!elemento.esCostoDirecto())
            {
                nodo = new JBusinessMenuItem(elemento.getDescripcion());
                nodo.setTag(elemento);
                nodo.setName(String.valueOf(elemento.getId()));
                nodo.addActionListener(popupAreasPreciosActionListener);
                popupAreasIndirectas.add(nodo);
            }
        }
    }

    //---------------------------------------------------------------------
    public void prepareForNew()
    {
        elementoActual = new ControlPrecio();
        esNuevo = true;
        this.setTitle("Nuevo");
        bsPresentacionesRender.setData(parent.obtenerPresentaciones());
        bsMonedasRender.setData(parent.obtenerMonedas());
        //bsTipoPrecioRender.setData(parent.obtenerTipoPrecios());
        bsAreasPreciosRender.setData(parent.obtenerAreasPrecios());
        desglose_costos = parent.obtenerLogicaDesglose();
        crearPopUpAreasIndirectas(bsAreasPreciosRender.getData());

        //Valores iniciales
        elementoActual.setId(0); //Marcamos un id temporal, menor que 1, y distinto al valor vacio.
        elementoActual.setCostoDirecto(0.0F);
        elementoActual.setMargen(0.0F);
        elementoActual.setFactor(0);
        elementoActual.setPrecioFinal(0.0F);
        elementoActual.setDescripcion("");

        writeElement(elementoActual);
    }

    //---------------------------------------------------------------------
    public void prepareForModify(ControlPrecio elemento)
    {
        this.elementoActual = elemento;
        esNuevo = false;
        this.setTitle(String.format("Modificando %s", elemento.getDescripcion()));
        bsPresentacionesRender.setData(parent.obtenerPresentaciones());
        bsMonedasRender.setData(parent.obtenerMonedas());
        //bsTipoPrecioRender.setData(parent.obtenerTipoPrecios());
        bsAreasPreciosRender.setData(parent.obtenerAreasPrecios());
        desglose_costos = parent.obtenerLogicaDesglose();
        crearPopUpAreasIndirectas(bsAreasPreciosRender.getData());
        writeElement(elemento);
        cargarRecursos(elemento);
    }

    //---------------------------------------------------------------------
    public void writeElement(ControlPrecio elemento)
    {
        esEscribiendo = true;
        //jcbTipo.setSelectedItem(bsTipoPrecioRender.getElementAt(bsTipoPrecioRender.indexOfValue(elemento.getEsTipo())));
        jtfDescripcion.setText(elemento.getDescripcion());
        jtfBase.setValue(elemento.getCostoDirecto());
        jtfMargen.setValue(elemento.getMargen());
        jtfFactor.setValue(elemento.getFactor());
        jtfFinal.setValue(elemento.getPrecioFinal());
        jcbMonedas.setSelectedItem(bsMonedasRender.getElementAt(bsMonedasRender.indexOfValue(elemento.getIdMoneda())));
        jcbPresentaciones.setSelectedItem(bsPresentacionesRender.getElementAt(bsPresentacionesRender.indexOfValue(elemento.getIdVariante())));
        jcbAreasPrecios.setSelectedItem(bsAreasPreciosRender.getElementAt(bsAreasPreciosRender.indexOfValue(elemento.getIdAreaPrecio())));
        esEscribiendo = false;

        costo_directo = elemento.getCostoDirecto();
    }

    //---------------------------------------------------------------------
    private boolean readElement(ControlPrecio elemento)
    {
        boolean resultado = false;
        String mensaje = "";

        if (!jtfDescripcion.getText().isEmpty())
        {
            resultado = true;
            elemento.setDescripcion(jtfDescripcion.getText());
            //elemento.setEsTipo((int) bsTipoPrecioRender.getSelectedValue());
            elemento.setCostoDirecto((float) jtfBase.getValue());
            elemento.setMargen((float) jtfMargen.getValue());
            elemento.setFactor((int) jtfFactor.getValue());
            elemento.setPrecioFinal((float) jtfFinal.getValue());
            elemento.setIdMoneda((int) bsMonedasRender.getSelectedValue());
            elemento.setIdVariante((int) bsPresentacionesRender.getSelectedValue());
            elemento.setIdAreaPrecio((int) bsAreasPreciosRender.getSelectedValue());

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
    private void cargarRecursos(ControlPrecio elemento)
    {
        if (swCargador == null || swCargador.isDone())
        {
            List<Object> parametros = new ArrayList<Object>();
            swCargador = new ControlPreciosCargarSwingWorker();
            swCargador.addPropertyChangeListener(this);
            DesgloseCosto filtro = new DesgloseCosto();
            filtro.setIdPrecioVariable(elemento.getId());

            parametros.add(opLOAD_DETAILS);
            parametros.add(filtro);

            showTaskHeader("Cargando análisis, espere un momento....", true);
            swCargador.execute(parametros);
        }
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

    //---------------------------------------------------------------------
    /**
     * Este método detecta los cambios en los TextFields.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        //int progress = cargarOrigenesDatos.getProgress();
        if ("progress".equals(evt.getPropertyName()))
            jpbAvances.setValue((Integer) evt.getNewValue());

        else if ("value".equals(evt.getPropertyName()) && !esEscribiendo)
        {
            JFormattedTextField actual = (JFormattedTextField) evt.getSource();

            esEscribiendo = true;
            if (actual == jtfBase)
            {
                float base = (float) actual.getValue();

                jtfMargen.setValue(base * (int) jtfFactor.getValue() / 100.0000F);
                jtfFinal.setValue(base + (float) jtfMargen.getValue());
                costo_directo = base;
            }

            else if (actual == jtfMargen)
            {
                float margen = (float) actual.getValue();
                float base_temp = (float) jtfBase.getValue();

                if (base_temp > 0)
                    jtfFactor.setValue((int) (margen * 100.0000F / base_temp));

                else
                    jtfFactor.setValue(0);

                jtfFinal.setValue(base_temp + margen);
            }

            else if (actual == jtfFactor)
            {
                int factor = (int) actual.getValue();

                jtfMargen.setValue(factor / 100.0000F * (float) jtfBase.getValue());
                jtfFinal.setValue((float) jtfMargen.getValue() + (float) jtfBase.getValue());
            }

            else if (actual == jtfFinal)
            {
                float pfinal = (float) actual.getValue();
                float base_temp = (float) jtfBase.getValue();

                jtfMargen.setValue(pfinal - base_temp);
                if (base_temp > 0)
                    jtfFactor.setValue((int) ((float) jtfMargen.getValue() * 100.0000F / base_temp));

                else
                    jtfFactor.setValue(0);
            }

            esEscribiendo = false;
        }
    }

    //---------------------------------------------------------------------
    /*private void textChanged(DocumentEvent documentEvent)
     {
     DocumentEvent.EventType type = documentEvent.getType();
     String typeString = null;
        
     if (type.equals(DocumentEvent.EventType.CHANGE))
     {
     typeString = "Change";
     }
       
     else if (type.equals(DocumentEvent.EventType.INSERT))
     {
     typeString = "Insert";
     }
        
     else if (type.equals(DocumentEvent.EventType.REMOVE))
     {
     typeString = "Remove";
     }
        
     System.out.print("Type : " + typeString);
     Document source = documentEvent.getDocument();
     int length = source.getLength();
     System.out.println("Length: " + length);
     }*/
    //---------------------------------------------------------------------
    public void close()
    {
        setVisible(false);
        dispose();

    }

    //----------------------------------------------------------------------
    public void jtbAnalisis_onCellValueChanged(JTable sender, TableCellEditor editor)
    {
        TableColumnModel columnas = sender.getColumnModel();
        int modelColIndex = sender.convertColumnIndexToModel(sender.getEditingColumn());
        int modelRowIndex = sender.convertRowIndexToModel(sender.getEditingRow());
        TableColumn actual = columnas.getColumn(modelColIndex);
        DesgloseCostos_5FNTableModel dataBinding = (DesgloseCostos_5FNTableModel) sender.getModel();
        DesgloseCosto_5FN seleccionado = dataBinding.getElementAt(modelRowIndex);

        if (actual == colCantidad)
        {
            float cantidad_new = (float) editor.getCellEditorValue();
            float subtotal_new, monto_new;

            subtotal_new = seleccionado.getPrecio() * cantidad_new;
            monto_new = seleccionado.getFactor() / 100.0000F * subtotal_new;
            actualizarCostoDirecto(subtotal_new + monto_new - seleccionado.getTotal());
            dataBinding.setValueAt(subtotal_new, modelRowIndex, colSubTotal.getModelIndex());
            dataBinding.setValueAt(monto_new, modelRowIndex, colMonto.getModelIndex());
            dataBinding.setValueAt(subtotal_new + monto_new, modelRowIndex, colTotal.getModelIndex());
            reescribirTotales();
        }

        else if (actual == colPrecio)
        {
            float precio = (float) editor.getCellEditorValue();
            float subtotal_new, monto_new;

            subtotal_new = precio * seleccionado.getCantidad();
            monto_new = seleccionado.getFactor() / 100.0000F * subtotal_new;
            actualizarCostoDirecto(subtotal_new + monto_new - seleccionado.getTotal());
            dataBinding.setValueAt(subtotal_new, modelRowIndex, colSubTotal.getModelIndex());
            dataBinding.setValueAt(monto_new, modelRowIndex, colMonto.getModelIndex());
            dataBinding.setValueAt(subtotal_new + monto_new, modelRowIndex, colTotal.getModelIndex());
            reescribirTotales();
        }

        else if (actual == colSubTotal)
        {
            float cantidad_old = seleccionado.getCantidad();
            float subtotal_new = (float) editor.getCellEditorValue();
            float precio_new = subtotal_new / (cantidad_old > 0 ? cantidad_old : 1.0F);
            float monto_new = seleccionado.getFactor() / 100.0000F * subtotal_new;

            actualizarCostoDirecto(subtotal_new + monto_new - seleccionado.getTotal());
            dataBinding.setValueAt(precio_new, modelRowIndex, colPrecio.getModelIndex());
            dataBinding.setValueAt(monto_new, modelRowIndex, colMonto.getModelIndex());
            dataBinding.setValueAt(subtotal_new + monto_new, modelRowIndex, colTotal.getModelIndex());
            reescribirTotales();
        }

        else if (actual == colMonto)
        {
            float monto_new = (float) editor.getCellEditorValue();
            float subtotal_old = seleccionado.getSubtotal();
            float total_new;

            if (subtotal_old > 0)
                dataBinding.setValueAt((int) (monto_new * 100.0000F / subtotal_old), modelRowIndex, colFactor.getModelIndex());

            else
                dataBinding.setValueAt(0, modelRowIndex, colFactor.getModelIndex());

            total_new = subtotal_old + monto_new;
            actualizarCostoDirecto(total_new - seleccionado.getTotal());
            dataBinding.setValueAt(total_new, modelRowIndex, colTotal.getModelIndex());
            reescribirTotales();
        }

        else if (actual == colFactor)
        {
            int factor_new = (int) editor.getCellEditorValue();
            float montoo_new, subtotal_old = seleccionado.getSubtotal();

            montoo_new = factor_new / 100.0000F * subtotal_old;
            actualizarCostoDirecto(subtotal_old + montoo_new - seleccionado.getTotal());
            dataBinding.setValueAt(montoo_new, modelRowIndex, colMonto.getModelIndex());
            dataBinding.setValueAt(subtotal_old + montoo_new, modelRowIndex, colTotal.getModelIndex());
            reescribirTotales();
        }

        else if (actual == colTotal)
        {
            float total_new = (float) editor.getCellEditorValue();
            float monto_new, subtotal_old = seleccionado.getSubtotal();

            monto_new = total_new - subtotal_old;
            actualizarCostoDirecto(subtotal_old + monto_new - seleccionado.getTotal());

            dataBinding.setValueAt(monto_new, modelRowIndex, colMonto.getModelIndex());
            if (subtotal_old > 0)
                dataBinding.setValueAt((int) (monto_new * 100.0000F / subtotal_old), modelRowIndex, colFactor.getModelIndex());

            else
                dataBinding.setValueAt(0, modelRowIndex, colFactor.getModelIndex());
            dataBinding.setValueAt(subtotal_old + monto_new, modelRowIndex, colTotal.getModelIndex());
            reescribirTotales();
        }

    }

    //---------------------------------------------------------------------
    private void actualizarCostoDirecto(float importe_neto)
    {
        costo_directo = costo_directo + importe_neto;
        //margen = factor / 100.0000F * costo_directo;
        //factor = (int) (margen * 100.00000F / costo_directo);
    }

    //---------------------------------------------------------------------
    private void reescribirTotales()
    {
        jtfBase.setValue(costo_directo);
    }

    //-------------------------------------------------------------------
    /**
     * Solicitamos una ventana de búsqueda de personas.
     */
    protected void popupAreasPrecio_actionPerformed(ActionEvent e)
    {
        JMenuItem currentMenu = (JMenuItem) e.getSource();

        if (currentMenu instanceof JBusinessMenuItem)
        {
            AreaPrecio current = (AreaPrecio) ((JBusinessMenuItem) currentMenu).getTag();

            //JOptionPane.showMessageDialog(this, current.getNombre());
            JControlPreciosBusqueda dialogo = new JControlPreciosBusqueda(this);

            dialogo.limitarPrecios(current, 1);
            dialogo.setTitle(String.format("Busqueda de %s", current.getDescripcion()));
            dialogo.busquedaInicial();

            parent.agregaContenedorPrincipal(dialogo);
            parent.mostrarCentrado(dialogo);
        }
    }

    //----------------------------------------------------------------------
    public void setDisplayData(List<DesgloseCosto_5FN> listPartes)
    {
        if (listPartes != null)
            bsDesgloseCostos.setData(listPartes);
    }

    //---------------------------------------------------------------------
    private void writeCurrency(int idMoneda)
    {
        if (bsMonedasRender.getSize() > 0)
            jcbMonedas.setSelectedIndex(bsMonedasRender.indexOfValue(idMoneda));
    }

    //---------------------------------------------------------------------
   /* private void invalidateChangeOfType(boolean value)
    {
        jcbTipo.setEnabled(!value);
    }*/

    //---------------------------------------------------------------------
    /*public boolean esConocido(ControlPrecioVista elemento)
     {
     boolean seEncontro = false;
     int i = 0;
     ControlPrecioVista actual;

     while (i < bsPreciosEditor.getSize() && !seEncontro)
     {
     actual = bsPreciosEditor.getElementAt(i++);
     seEncontro = actual.getId() == elemento.getId();
     }

     return seEncontro;
     }*/
    //---------------------------------------------------------------------
    @Override
    public void onAcceptNewElement(ControlPrecioVista nuevo)
    {
        /*if (!esConocido(nuevo))
         {
         bsPreciosEditor.addElement(nuevo);
         bsPreciosRender.addElement(nuevo);
         }*/

        crearNuevoDetalle(nuevo);
        //invalidateChangeOfType(true);

        //Nota: Si después de crear el nuevo detalle este queda pendiente.
        //es porque nos falta definir un tipo de cambio.
       /* if (pendientes_cambio.size() > 0)
         showMoneyChangeDialog(listaUltimosTiposCambio);
         else
         reescribirTotales();*/
    }

    //---------------------------------------------------------------------
    @Override
    public void onAcceptNewElement(List<ControlPrecioVista> nuevos)
    {
        crearNuevoDetalle(nuevos);
        //invalidateChangeOfType(true);

        //Nota: Si después de crear el nuevo detalle este queda pendiente.
        //es porque nos falta definir un tipo de cambio.
       /* if (pendientes_cambio.size() > 0)
         showMoneyChangeDialog(listaUltimosTiposCambio);
         else
         reescribirTotales();*/
    }

    //---------------------------------------------------------------------
    private void crearNuevoDetalle(List<ControlPrecioVista> nuevos)
    {
        DesgloseCosto_5FN nuevaParte;
        //Nota: Todos los precios en la lista deberían ser el mismo.
        int id_moneda = nuevos.get(0).getIdMoneda();

        //2.- Determinamos la moneda utilizada
        if (jcbMonedas.getSelectedIndex() < 0)
            writeCurrency(id_moneda);

        //1.- Establecemos datos del control de precio que vamos a relacionar.
        for (ControlPrecioVista nuevo : nuevos)
        {
            nuevaParte = new DesgloseCosto_5FN();
            nuevaParte.setHasOnePrecioVariable(elementoActual);
            nuevaParte.setIdPrecioIndirecto(nuevo.getId());
            nuevaParte.setCategoria(nuevo.getCategoria());
            nuevaParte.setPresentacion(nuevo.getPresentacion());
            nuevaParte.setArea_precio(nuevo.getAreaPrecio());
            nuevaParte.setTipo_bien(nuevo.getTipoBien());
            //nuevaParte.setUnidad(nuevo.getUnidad());
            nuevaParte.setCantidad(1);
            //nuevaParte.setFactor_cantidad(conversion);
            nuevaParte.setFactor(0);
            nuevaParte.setMonto(0);
            nuevaParte.setPrecio(nuevo.getPrecioFinal());
            //nuevaParte.setFactor_precio(conversion);
            nuevaParte.setObservacion("");
            //nuevaParte.getFk().copy(nuevo.getFk_precio_id_moneda());
            //nuevaParte.setTotal(total); calcular después de cambio de moneda.

            //1.1.- Relacionamos la parte con el detalle.
            /*if (calcularPrecioDetalle(nuevaParte))
             {
             bsDesgloseCostos.addRow(nuevaParte);
             actualizarTotal(nuevaParte.getSubtotal(), 0.0F);
             }

             else
             pendientes_cambio.add(nuevaParte);*/
            bsDesgloseCostos.addRow(nuevaParte);
        }
    }

    //---------------------------------------------------------------------
    /**
     * Crea un detalle relacionado con el Control de precio dado.
     */
    private void crearNuevoDetalle(ControlPrecioVista nuevo)
    {
        DesgloseCosto_5FN nuevaParte = new DesgloseCosto_5FN();

        //2.- Determinamos la moneda utilizada
        if (jcbMonedas.getSelectedIndex() < 0) //No se había seleccionado una moneda previamente.
            writeCurrency(nuevo.getIdMoneda());

        //3.- Esblecemos  datos del detalle y lo relacionamos con el nuevo precio.
        nuevaParte.setHasOnePrecioVariable(elementoActual);
        nuevaParte.setIdPrecioIndirecto(nuevo.getId());
        nuevaParte.setCategoria(nuevo.getCategoria());
        nuevaParte.setPresentacion(nuevo.getPresentacion());
        nuevaParte.setArea_precio(nuevo.getAreaPrecio());
        nuevaParte.setTipo_bien(nuevo.getTipoBien());
        //nuevaParte.setUnidad(nuevo.getUnidad());
        nuevaParte.setCantidad(1);
        //nuevaParte.setFactor_cantidad(conversion);
        nuevaParte.setFactor(0);
        nuevaParte.setMonto(0);
        nuevaParte.setPrecio(nuevo.getPrecioFinal());
        //nuevaParte.setFactor_precio(conversion);
        nuevaParte.setObservacion("");

        //4.- Calculamos el precio que tendrá el nuevo detalle, basado en sus 
        //Partes.
        /*if (calcularPrecioDetalle(nuevoDetalle))
         {
         bsDesgloseCostos.addRow(nuevoDetalle);
         actualizarTotal(nuevoDetalle.getSubtotal(), 0.0F);
         }

         else
         pendientes_cambio.add(nuevoDetalle);*/
        bsDesgloseCostos.addRow(nuevaParte);
    }

    //---------------------------------------------------------------------
    @Override
    public String getEsquema()
    {
        return parent.getEsquema();
    }

    //---------------------------------------------------------------------
    @Override
    public boolean sonValidosVariosPrecios()
    {
        return jcbMonedas.getSelectedIndex() >= 0;
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

        popupAreasIndirectas = new javax.swing.JPopupMenu();
        jpEncabezado = new javax.swing.JPanel();
        jlbIcono = new javax.swing.JLabel();
        jpAreaMensajes = new javax.swing.JPanel();
        jlbMensajes = new javax.swing.JLabel();
        jpbAvances = new javax.swing.JProgressBar();
        jpContenido = new javax.swing.JPanel();
        tpCuerpo = new javax.swing.JTabbedPane();
        tabCostosDirectos = new javax.swing.JPanel();
        jspDesgloseCostos = new javax.swing.JScrollPane();
        jtbDesgloseCostos = createJtbDetalles ();
        jtoolDesgloseCostos = new javax.swing.JToolBar();
        jToolNuevoLinea = new javax.swing.JButton();
        jToolEliminarLinea = new javax.swing.JButton();
        tabTotalesArea = new javax.swing.JPanel();
        jspTotalesArea = new javax.swing.JScrollPane();
        jtbTotalesArea = new javax.swing.JTable();
        tabBienes = new javax.swing.JPanel();
        jspTotalesBienes = new javax.swing.JScrollPane();
        jtbTotalesBienes = new javax.swing.JTable();
        jpAreaDescriptiva = new javax.swing.JPanel();
        jpContenidoArea1 = new javax.swing.JPanel();
        jlbPresentacion = new javax.swing.JLabel();
        jcbPresentaciones = new javax.swing.JComboBox();
        jlbDescripcion = new javax.swing.JLabel();
        jtfDescripcion = new javax.swing.JTextField();
        jlbAreaPrecio = new javax.swing.JLabel();
        jcbAreasPrecios = new javax.swing.JComboBox();
        jpMontosTotales = new javax.swing.JPanel();
        jpCostoDirecto = new javax.swing.JPanel();
        jlbBase = new javax.swing.JLabel();
        jtfBase = new JFormattedTextField (floatFormat);
        jpMargenUtilidad = new javax.swing.JPanel();
        jlbMargen = new javax.swing.JLabel();
        jtfMargen = new JFormattedTextField (floatFormat);
        jpFactor = new javax.swing.JPanel();
        jlbFactor = new javax.swing.JLabel();
        jtfFactor = new JFormattedTextField (1);
        jpTotal = new javax.swing.JPanel();
        jlbTotal = new javax.swing.JLabel();
        jtfFinal = new JFormattedTextField (floatFormat);
        jpMoneda = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jcbMonedas = new javax.swing.JComboBox();
        jpAcciones = new javax.swing.JPanel();
        jbCancelar = new javax.swing.JButton();
        jbAceptar = new javax.swing.JButton();

        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setMinimumSize(new java.awt.Dimension(800, 600));

        jpEncabezado.setBackground(new java.awt.Color(51, 185, 87));
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

        jpAreaMensajes.setBackground(new java.awt.Color(51, 185, 87));
        jpAreaMensajes.setPreferredSize(new java.awt.Dimension(600, 30));
        jpAreaMensajes.setLayout(new java.awt.GridLayout(2, 1));

        jlbMensajes.setText("<sin mensaje>");
        jlbMensajes.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jpAreaMensajes.add(jlbMensajes);

        jpbAvances.setValue(50);
        jpbAvances.setStringPainted(true);
        jpAreaMensajes.add(jpbAvances);

        jpEncabezado.add(jpAreaMensajes);

        getContentPane().add(jpEncabezado, java.awt.BorderLayout.NORTH);

        jpContenido.setLayout(new java.awt.BorderLayout());

        tpCuerpo.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
        tpCuerpo.setPreferredSize(new java.awt.Dimension(300, 300));

        tabCostosDirectos.setLayout(new java.awt.BorderLayout());

        jtbDesgloseCostos.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jspDesgloseCostos.setViewportView(jtbDesgloseCostos);

        tabCostosDirectos.add(jspDesgloseCostos, java.awt.BorderLayout.CENTER);

        jtoolDesgloseCostos.setRollover(true);

        jToolNuevoLinea.setText("Nuevo");
        jToolNuevoLinea.setFocusable(false);
        jToolNuevoLinea.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jToolNuevoLinea.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jtoolDesgloseCostos.add(jToolNuevoLinea);

        jToolEliminarLinea.setText("Eliminar");
        jToolEliminarLinea.setFocusable(false);
        jToolEliminarLinea.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jToolEliminarLinea.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jtoolDesgloseCostos.add(jToolEliminarLinea);

        tabCostosDirectos.add(jtoolDesgloseCostos, java.awt.BorderLayout.PAGE_START);

        tpCuerpo.addTab("Desglose Costos", tabCostosDirectos);

        tabTotalesArea.setLayout(new java.awt.BorderLayout());

        jtbTotalesArea.setModel(bsDesgloseAreas);
        jspTotalesArea.setViewportView(jtbTotalesArea);

        tabTotalesArea.add(jspTotalesArea, java.awt.BorderLayout.CENTER);

        tpCuerpo.addTab("Totales por área", tabTotalesArea);

        tabBienes.setLayout(new java.awt.BorderLayout());

        jtbTotalesBienes.setModel(bsDesgloseBienes);
        jspTotalesBienes.setViewportView(jtbTotalesBienes);

        tabBienes.add(jspTotalesBienes, java.awt.BorderLayout.CENTER);

        tpCuerpo.addTab("Totales por bienes", tabBienes);

        jpContenido.add(tpCuerpo, java.awt.BorderLayout.CENTER);
        tpCuerpo.getAccessibleContext().setAccessibleName("");

        jpAreaDescriptiva.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        java.awt.GridBagLayout jpContenidoArea1Layout = new java.awt.GridBagLayout();
        jpContenidoArea1Layout.columnWidths = new int[] {0, 6, 0, 6, 0, 6, 0};
        jpContenidoArea1Layout.rowHeights = new int[] {0, 5, 0};
        jpContenidoArea1.setLayout(jpContenidoArea1Layout);

        jlbPresentacion.setText("Presentacion:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        jpContenidoArea1.add(jlbPresentacion, gridBagConstraints);

        jcbPresentaciones.setModel(bsPresentacionesRender);
        jcbPresentaciones.setPreferredSize(new java.awt.Dimension(300, 25));
        jcbPresentaciones.setRenderer(new POJOListCellRenderer<BienVariante>());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 1, 0, 0);
        jpContenidoArea1.add(jcbPresentaciones, gridBagConstraints);

        jlbDescripcion.setText("Descripción:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        jpContenidoArea1.add(jlbDescripcion, gridBagConstraints);

        jtfDescripcion.setPreferredSize(new java.awt.Dimension(400, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.ipadx = 66;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jpContenidoArea1.add(jtfDescripcion, gridBagConstraints);

        jlbAreaPrecio.setText("Área de precio:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        jpContenidoArea1.add(jlbAreaPrecio, gridBagConstraints);

        jcbAreasPrecios.setModel(bsAreasPreciosRender);
        jcbAreasPrecios.setPreferredSize(new java.awt.Dimension(200, 25));
        jcbAreasPrecios.setRenderer(new POJOListCellRenderer<AreaPrecio>());
        jcbAreasPrecios.addItemListener(new java.awt.event.ItemListener()
        {
            public void itemStateChanged(java.awt.event.ItemEvent evt)
            {
                jcbAreasPreciosItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jpContenidoArea1.add(jcbAreasPrecios, gridBagConstraints);

        jpAreaDescriptiva.add(jpContenidoArea1);

        jpContenido.add(jpAreaDescriptiva, java.awt.BorderLayout.NORTH);

        jpMontosTotales.setPreferredSize(new java.awt.Dimension(800, 45));
        jpMontosTotales.setVerifyInputWhenFocusTarget(false);
        jpMontosTotales.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 0));

        jpCostoDirecto.setPreferredSize(new java.awt.Dimension(120, 40));
        jpCostoDirecto.setLayout(new java.awt.BorderLayout());

        jlbBase.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jlbBase.setText("Costo directo: $");
        jpCostoDirecto.add(jlbBase, java.awt.BorderLayout.NORTH);

        jtfBase.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        jtfBase.setText("000,000,000,000");
        jpCostoDirecto.add(jtfBase, java.awt.BorderLayout.CENTER);

        jpMontosTotales.add(jpCostoDirecto);

        jpMargenUtilidad.setPreferredSize(new java.awt.Dimension(120, 40));
        jpMargenUtilidad.setLayout(new java.awt.BorderLayout());

        jlbMargen.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jlbMargen.setText("Margen Utilidad:");
        jpMargenUtilidad.add(jlbMargen, java.awt.BorderLayout.NORTH);

        jtfMargen.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        jtfMargen.setText("000,000,000,000");
        jpMargenUtilidad.add(jtfMargen, java.awt.BorderLayout.CENTER);

        jpMontosTotales.add(jpMargenUtilidad);

        jpFactor.setPreferredSize(new java.awt.Dimension(120, 40));
        jpFactor.setLayout(new java.awt.BorderLayout());

        jlbFactor.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jlbFactor.setText("Factor %");
        jpFactor.add(jlbFactor, java.awt.BorderLayout.NORTH);

        jtfFactor.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        jtfFactor.setText("000,000,000,000");
        jpFactor.add(jtfFactor, java.awt.BorderLayout.CENTER);

        jpMontosTotales.add(jpFactor);

        jpTotal.setPreferredSize(new java.awt.Dimension(120, 40));
        jpTotal.setLayout(new java.awt.BorderLayout());

        jlbTotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jlbTotal.setText("Precio Final: $");
        jlbTotal.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jpTotal.add(jlbTotal, java.awt.BorderLayout.NORTH);

        jtfFinal.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        jtfFinal.setText("000,000,000,000");
        jpTotal.add(jtfFinal, java.awt.BorderLayout.SOUTH);

        jpMontosTotales.add(jpTotal);

        jpMoneda.setPreferredSize(new java.awt.Dimension(100, 40));
        jpMoneda.setLayout(new java.awt.BorderLayout());

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText("Moneda:");
        jLabel2.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel2.setPreferredSize(new java.awt.Dimension(24, 15));
        jLabel2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jpMoneda.add(jLabel2, java.awt.BorderLayout.NORTH);

        jcbMonedas.setModel(bsMonedasRender);
        jcbMonedas.setMinimumSize(new java.awt.Dimension(250, 25));
        jcbMonedas.setPreferredSize(new java.awt.Dimension(200, 25));
        jcbMonedas.setRenderer(new POJOListCellRenderer<Moneda>());
        jpMoneda.add(jcbMonedas, java.awt.BorderLayout.SOUTH);

        jpMontosTotales.add(jpMoneda);

        jpContenido.add(jpMontosTotales, java.awt.BorderLayout.SOUTH);

        getContentPane().add(jpContenido, java.awt.BorderLayout.CENTER);

        jpAcciones.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jbCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/syswave/forms/resources/button-cross.png"))); // NOI18N
        jbCancelar.setText("Cancelar");
        jpAcciones.add(jbCancelar);

        jbAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/syswave/forms/resources/button-tick.png"))); // NOI18N
        jbAceptar.setText("Aceptar");
        jpAcciones.add(jbAceptar);

        getContentPane().add(jpAcciones, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents

    private void jcbAreasPreciosItemStateChanged(java.awt.event.ItemEvent evt)//GEN-FIRST:event_jcbAreasPreciosItemStateChanged
    {//GEN-HEADEREND:event_jcbAreasPreciosItemStateChanged
        // TODO add your handling code here:

        if (evt.getStateChange() == ItemEvent.SELECTED)
        {
            if (jcbAreasPrecios.getSelectedIndex() >= 0)
                tpCuerpo.setVisible(bsAreasPreciosRender.getCurrent().esCostoVariable());
            pack();
        }
    }//GEN-LAST:event_jcbAreasPreciosItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JButton jToolEliminarLinea;
    private javax.swing.JButton jToolNuevoLinea;
    private javax.swing.JButton jbAceptar;
    private javax.swing.JButton jbCancelar;
    private javax.swing.JComboBox jcbAreasPrecios;
    private javax.swing.JComboBox jcbMonedas;
    private javax.swing.JComboBox jcbPresentaciones;
    private javax.swing.JLabel jlbAreaPrecio;
    private javax.swing.JLabel jlbBase;
    private javax.swing.JLabel jlbDescripcion;
    private javax.swing.JLabel jlbFactor;
    private javax.swing.JLabel jlbIcono;
    private javax.swing.JLabel jlbMargen;
    private javax.swing.JLabel jlbMensajes;
    private javax.swing.JLabel jlbPresentacion;
    private javax.swing.JLabel jlbTotal;
    private javax.swing.JPanel jpAcciones;
    private javax.swing.JPanel jpAreaDescriptiva;
    private javax.swing.JPanel jpAreaMensajes;
    private javax.swing.JPanel jpContenido;
    private javax.swing.JPanel jpContenidoArea1;
    private javax.swing.JPanel jpCostoDirecto;
    private javax.swing.JPanel jpEncabezado;
    private javax.swing.JPanel jpFactor;
    private javax.swing.JPanel jpMargenUtilidad;
    private javax.swing.JPanel jpMoneda;
    private javax.swing.JPanel jpMontosTotales;
    private javax.swing.JPanel jpTotal;
    private javax.swing.JProgressBar jpbAvances;
    private javax.swing.JScrollPane jspDesgloseCostos;
    private javax.swing.JScrollPane jspTotalesArea;
    private javax.swing.JScrollPane jspTotalesBienes;
    private javax.swing.JTable jtbDesgloseCostos;
    private javax.swing.JTable jtbTotalesArea;
    private javax.swing.JTable jtbTotalesBienes;
    private javax.swing.JFormattedTextField jtfBase;
    private javax.swing.JTextField jtfDescripcion;
    private javax.swing.JFormattedTextField jtfFactor;
    private javax.swing.JFormattedTextField jtfFinal;
    private javax.swing.JFormattedTextField jtfMargen;
    private javax.swing.JToolBar jtoolDesgloseCostos;
    private javax.swing.JPopupMenu popupAreasIndirectas;
    private javax.swing.JPanel tabBienes;
    private javax.swing.JPanel tabCostosDirectos;
    private javax.swing.JPanel tabTotalesArea;
    private javax.swing.JTabbedPane tpCuerpo;
    // End of variables declaration//GEN-END:variables

    //------------------------------------------------------------------
    private class ControlPreciosCargarSwingWorker extends SwingWorker<List<Object>, Void>
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
                    case opLOAD_DETAILS:
                        setProgress(50);
                        arguments.add(desglose_costos.obtenerListaVista((DesgloseCosto) arguments.get(1)));
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
                        case opLOAD_DETAILS:
                            if (desglose_costos.esCorrecto())
                            {
                                setDisplayData((List<DesgloseCosto_5FN>) results.get(2));
                                showTaskHeader("", false);
                            }
                            else
                                JOptionPane.showMessageDialog(null, desglose_costos.getMensaje(), "Error", JOptionPane.ERROR_MESSAGE);
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
