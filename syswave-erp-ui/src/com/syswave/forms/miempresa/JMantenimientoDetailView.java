package com.syswave.forms.miempresa;

import com.orbital.AutoCompleteDocument;
import com.syswave.entidades.common.Entidad;
import com.syswave.entidades.miempresa.ControlAlmacen;
import com.syswave.entidades.miempresa.Mantenimiento;
import com.syswave.entidades.miempresa.MantenimientoCosto;
import com.syswave.entidades.miempresa.Moneda;
import com.syswave.entidades.miempresa.Persona;
import com.syswave.entidades.miempresa.Valor;
import com.syswave.entidades.miempresa_vista.MantenimientoDescripcion_5FN;
import com.syswave.entidades.miempresa_vista.MantenimientoTieneActividad_5FN;
import com.syswave.swing.models.POJOTableModel;
import com.syswave.forms.databinding.ControlAlmacenesComboBoxModel;
import com.syswave.forms.databinding.MantemientoCostosTableModel;
import com.syswave.forms.databinding.MantenimientoDescripcion5FNTableModel;
import com.syswave.forms.databinding.MantenimientoTieneActividad_5FNTableModel;
import com.syswave.forms.databinding.MonedasComboBoxModel;
import com.syswave.forms.databinding.PersonasComboBoxModel;
import com.syswave.forms.databinding.ValorComboBoxModel;
import com.syswave.swing.renders.POJOListCellRenderer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
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

/**
 *
 * @author Victor Manuel Bucio Varggas
 */
public class JMantenimientoDetailView extends javax.swing.JInternalFrame implements TableModelListener
{

    private final int opLOAD_DESCRIPCIONES = 0;
    private final int opLOAD_ACTIVIDADES = 1;
    private final int opLOAD_COSTOS = 2;

    private MantemientoCostosTableModel bsCostosOperacion;
    private MonedasComboBoxModel bsMonedasRender;
    private ValorComboBoxModel bsTipoComprobantesRender;
    private PersonasComboBoxModel bsResponsables;
    private ControlAlmacenesComboBoxModel bsSeries;
    private MantenimientoDescripcion5FNTableModel bsDescripciones;
    private MantenimientoTieneActividad_5FNTableModel bsActividades;

    Mantenimiento elementoActual;

    boolean esNuevo, construidoDescripciones, construidoActividades, construidoCostos;
    IMantenimientoMediator parent;
    MantenimientoDetailCargarSwingWorker swCargador;
    TableColumn colTexto, colValor, colDescripcion, colSeleccion, colActividad,
            colConcepto, colMonto;

    List<MantenimientoCosto> costos_deleteds;

    //--------------------------------------------------------------------
    public JMantenimientoDetailView(IMantenimientoMediator owner)
    {
        initAtributes(owner.obtenerOrigenDato());
        initComponents();
        initEvents();
        jspHoraInicio.setEditor(new JSpinner.DateEditor(jspHoraInicio, "HH:mm"));
        jspHoraFinal.setEditor(new JSpinner.DateEditor(jspHoraFinal, "HH:mm"));
        parent = owner;
        AutoCompleteDocument.enable(jcbSeries);

        if (jtbDescripciones.getColumnCount() > 0)
        {
            colTexto = jtbDescripciones.getColumnModel().getColumn(0);
            colTexto.setPreferredWidth(350);

            colValor = jtbDescripciones.getColumnModel().getColumn(1);
            colValor.setPreferredWidth(70);

            colDescripcion = jtbDescripciones.getColumnModel().getColumn(2);
            colDescripcion.setPreferredWidth(220);

            jtbDescripciones.setRowHeight(jtbDescripciones.getRowHeight() * 3);
        }

        if (jtbCostos.getColumnCount() > 0)
        {
            colConcepto = jtbCostos.getColumnModel().getColumn(0);
            colConcepto.setPreferredWidth(200);

            colMonto = jtbCostos.getColumnModel().getColumn(1);
            colMonto.setPreferredWidth(100);
        }

        if (jtbActividades.getColumnCount() > 0)
        {
            colSeleccion = jtbActividades.getColumnModel().getColumn(0);
            colSeleccion.setPreferredWidth(70);

            colActividad = jtbActividades.getColumnModel().getColumn(1);
            colActividad.setPreferredWidth(450);
        }
        
        AutoCompleteDocument.enable(jcbResponsable);
    }

    //---------------------------------------------------------------------
    private void initAtributes(String esquema)
    {
        esNuevo = true;
        construidoDescripciones = false;
        construidoActividades = false;
        construidoCostos = false;
        bsResponsables = new PersonasComboBoxModel();
        bsMonedasRender = new MonedasComboBoxModel();
        bsTipoComprobantesRender = new ValorComboBoxModel();
        bsSeries = new ControlAlmacenesComboBoxModel();

        bsCostosOperacion = new MantemientoCostosTableModel(new String[]
        {
            "Descripción:{descripcion}", "Monto:{monto}"
        });
        bsCostosOperacion.addTableModelListener(this);

        bsDescripciones = new MantenimientoDescripcion5FNTableModel(new String[]
        {
            "Texto:{texto}", "Tipo:{valor}", "Descripción:{descripcion}"
        });
        bsDescripciones.addTableModelListener(this);

        bsActividades = new MantenimientoTieneActividad_5FNTableModel(new String[]
        {
            " :{selected}", "Actividad:{actividad}"
        });
        bsActividades.addTableModelListener(this);

        costos_deleteds = new ArrayList<>();
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
                if (e.getSource() == jcbTipo)
                    jcbTipo_itemStateChanged(e);

                else if (e.getSource() == jcbSeries)
                    jcbSeries_itemStateChanged(e);
            }
        };

        ActionListener actividadesActionListener
                = new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent evt)
                    {
                        actividadesToolBar_actionPerformed(evt);
                    }
                };

        ActionListener costosActionListener
                = new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent evt)
                    {
                        costosToolBar_actionPerformed(evt);
                    }
                };

        jbAceptar.addActionListener(actionListenerManager);
        jbCancelar.addActionListener(actionListenerManager);
        jtpBody.addChangeListener(changeListenerManager);
        jcbTipo.addItemListener(valueChangeItemListener);
        jcbSeries.addItemListener(valueChangeItemListener);
        jtoolNuevoPrecio.addActionListener(costosActionListener);
        jtoolEliminarPrecio.addActionListener(costosActionListener);
        jbtnImprimir.addActionListener(actividadesActionListener);
    }

    //--------------------------------------------------------------------
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
                    //Cualquier elemento de la descripción que haya sido manipulado
                    //debe quedar ligado al documento de mantenimiento actual.
                    if (model == bsDescripciones)
                    {
                        if (((MantenimientoDescripcion_5FN) data).getIdMantenimiento() != elementoActual.getId())
                        {
                            ((MantenimientoDescripcion_5FN) data).setHasOneMantenimiento(elementoActual);
                            data.setNew();
                        }

                        else
                            data.setModified();
                    }

                    else
                        data.setModified();
                }

                //Debido a que esto es un CheckList la lógica de revisión es un poco diferente.
                else if (model == bsActividades)
                {
                    if (((MantenimientoTieneActividad_5FN) data).getIdMantenimiento() != elementoActual.getId())
                    {
                        ((MantenimientoTieneActividad_5FN) data).setHasOneMantenimiento(elementoActual);
                        //data.setNew(); Nota: Los checkList vienen marcados como nuevos.
                    }
                }

            }
        }
    }

    //---------------------------------------------------------------------
    private void bodyTabbed_stateChanged(ChangeEvent e)
    {
        if (swCargador == null || swCargador.isDone())
        {
            JTabbedPane pane = (JTabbedPane) e.getSource();

            if (pane.getSelectedComponent() == tabDescripcion && !construidoDescripciones)
            {
                List<Object> parametros = new ArrayList<Object>();
                swCargador = new MantenimientoDetailCargarSwingWorker();
                parametros.add(opLOAD_DESCRIPCIONES);
                parametros.add(elementoActual);
                swCargador.execute(parametros);
                construidoDescripciones = true;
            }

            else if (pane.getSelectedComponent() == tabActividades && !construidoActividades)
            {
                if (bsSeries.getCurrent() != null)
                {
                    List<Object> parametros = new ArrayList<Object>();
                    swCargador = new MantenimientoDetailCargarSwingWorker();
                    parametros.add(opLOAD_ACTIVIDADES);
                    parametros.add(elementoActual);
                    parametros.add(bsSeries.getCurrent());
                    swCargador.execute(parametros);
                    construidoActividades = true;
                }
            }

            else if (pane.getSelectedComponent() == tabCostos && !construidoCostos)
            {
                if (!esNuevo)
                {
                    List<Object> parametros = new ArrayList<Object>();
                    swCargador = new MantenimientoDetailCargarSwingWorker();
                    parametros.add(opLOAD_COSTOS);
                    parametros.add(elementoActual);
                    swCargador.execute(parametros);

                }

                construidoCostos = true;
            }
        }
    }

    //---------------------------------------------------------------------
    private void jcbTipo_itemStateChanged(ItemEvent evt)
    {
      //JComboBox combo = (JComboBox) evt.getSource();
        //Item previo al cambio
      /*if (evt.getStateChange() == ItemEvent.DESELECTED)
         {
         //Nota: Si es la primera vez que se selecciona un item, este evento no se ejecuta.
         //porque no había nada previo seleccionado.
         if (!esNuevo)
         {
         //selected_old = (Moneda) evt.getItem();
         Valor tipo = (Valor) evt.getItem();
         setTitle(String.valueOf(tipo.getId()));
         }
         }*/

        //Item después del cambio
        if (evt.getStateChange() == ItemEvent.SELECTED)
        {
            Valor tipo = (Valor) evt.getItem();

            if (tipo.getId() < 1)
            {
                if (jtpBody.indexOfComponent(tabDescripcion) >= 0)
                    jtpBody.remove(tabDescripcion);

                //En un principio podrían ya estar incluidos.
                if (jtpBody.indexOfComponent(tabActividades) < 0)
                    jtpBody.add(tabActividades, "Actividades realizadas", 2);
            }

            else
            {
                if (jtpBody.indexOfComponent(tabActividades) >= 0)
                    jtpBody.remove(tabActividades);

                if (jtpBody.indexOfComponent(tabDescripcion) < 0)
                    jtpBody.add(tabDescripcion, "Descripción Detallada", 2);
            }
        }
    }

    //---------------------------------------------------------------------
    private void jcbSeries_itemStateChanged(ItemEvent evt)
    {
        //JComboBox combo = (JComboBox) evt.getSource();
        //Item previo al cambio
        if (evt.getStateChange() == ItemEvent.DESELECTED)
        {
            //Nota: Si es la primera vez que se selecciona un item, este evento no se ejecuta.
            //porque no había nada previo seleccionado.
            //Se hizo cambio en la serie, por tanto las actividades ya no aplican.
            if (construidoActividades )
            {
                if (bsActividades.getRowCount() > 0)
                    bsActividades.clear();
                construidoActividades = false;
            }
        }

        //Item después del cambio
        else if (evt.getStateChange() == ItemEvent.SELECTED)
        {
            ControlAlmacen tipo = (ControlAlmacen) evt.getItem();

            //if (esNuevo)
            ftxValorusado.setValue(tipo.getValorAcumulado());
            lblEtiquetaunidad.setText(tipo.getHasOneControlInventario().getHasOneBienVariante().obtenerEtiquetaMantenimiento());
            try
            {
                lblValorPlaneado.setText(ftxValorusado.getFormatter().valueToString(tipo.getHasOneControlInventario().getHasOneBienVariante().getValorEsperado()));
            }
            catch (ParseException ex)
            {
                Logger.getLogger(JMantenimientoDetailView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //---------------------------------------------------------------------

    private void actividadesToolBar_actionPerformed(ActionEvent evt)
    {
        if (evt.getSource() == jbtnImprimir)
        {
            if (jtbActividades.getRowCount() > 0)
            {
                Map parameters = new HashMap();
                parameters.put("id_variante", bsSeries.getCurrent().getIdVariante());
                parameters.put("id_mantenimiento", elementoActual.getId());
                parameters.put("mantenimiento_fecha", jdcFechaElaboracion.getDate());
                parameters.put("mantenimiento_responsable", bsResponsables.getCurrent().getNombreCompleto());
                parameters.put("mantenimiento_serie", bsSeries.getCurrent().getSerie());
                parent.printHelp(bsSeries.getCurrent().getSerie(),
                        "/com/carmona/cmms/actividadesrealizadas.jasper", parameters);
            }
        }
    }

    //---------------------------------------------------------------------

    private void costosToolBar_actionPerformed(ActionEvent evt)
    {
        int rowIndex;

        if (evt.getSource() == jtoolNuevoPrecio)
        {

            MantenimientoCosto nuevoCosto = new MantenimientoCosto();

            nuevoCosto.setHasOneMantenimiento(elementoActual);
            nuevoCosto.setDescripcion("");
            nuevoCosto.setMonto(0F);
            rowIndex = bsCostosOperacion.addRow(nuevoCosto);
            jtbCostos.setRowSelectionInterval(rowIndex, rowIndex);
            //jtbCostos.editCellAt(rowIndex, 0);
        }

        else if (evt.getSource() == jtoolEliminarPrecio)
        {
            if (JOptionPane.showConfirmDialog(this, String.format("Ha seleccionado %d registro(s) para ser eleiminado(s)\n¿Esta seguro de continuar?", jtbCostos.getSelectedRowCount())) == JOptionPane.OK_OPTION)
            {
                int[] rowsHandlers = jtbCostos.getSelectedRows();
                List<MantenimientoCosto> selecteds = bsCostosOperacion.removeRows(rowsHandlers);

                for (MantenimientoCosto elemento : selecteds)
                {
                    if (!elemento.isNew())
                        costos_deleteds.add(elemento);

                    actualizarTotal(elemento.getMonto() * -1);
                }
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
                tryStopDetailCellEditor(jtbDescripciones);
                tryStopDetailCellEditor(jtbActividades);
                revisarConsistenciaDatos(elementoActual);

                if (esNuevo)
                    parent.onAcceptNewElement(elementoActual, bsDescripciones.getData(), bsActividades.getData(), bsCostosOperacion.getData());

                else
                {
                    elementoActual.setModified();
                    parent.onAcceptModifyElement(elementoActual, bsDescripciones.getData(), bsActividades.getData(), bsCostosOperacion.getData(), costos_deleteds);
                }

                close();
            }
        }

        else
            close();
    }

    //---------------------------------------------------------------------
    private void revisarConsistenciaDatos(Mantenimiento elemento)
    {
        //Un mantenimiento preventivo no puede llevar descripción detallada.
        if (elemento.getEsTipo() < 1 && bsDescripciones.getRowCount() > 0)
            bsDescripciones.clear();

        //Un mantenimiento distinto al preventivo no puede tener 
        //actividades planificadas.
        else if (elemento.getEsTipo() > 0 && bsActividades.getRowCount() > 0)
            bsActividades.clear();

    }

    //---------------------------------------------------------------------
    private void tryStopDetailCellEditor(JTable subDetailView)
    {
        if (subDetailView.isEditing())
        {
            TableCellEditor editor = subDetailView.getCellEditor();
            if (editor != null)
                editor.stopCellEditing();
        }
    }

    //---------------------------------------------------------------------
    public void prepareForNew()
    {
        elementoActual = new Mantenimiento();
        esNuevo = true;
        this.setTitle("Nuevo");
        jtbAccionesActividades.setVisible(false);
        bsTipoComprobantesRender.setData(parent.obtenerTiposMantenimiento());
        bsResponsables.setData(parent.obtenerResponsables());
        bsSeries.setData(parent.obtenerSeries());
        bsMonedasRender.setData(parent.obtenerMonedas());

        //Valores iniciales
        elementoActual.setId(0); //Marcamos un id temporal, menor que 1, y distinto al valor vacio.
        elementoActual.setFolio("");
        elementoActual.setFechaCreacion(new Date());
        elementoActual.setActivo(true);
        if (bsTipoComprobantesRender.getSize() > 0)
            elementoActual.setEsTipo(bsTipoComprobantesRender.getData().get(0).getId());
        elementoActual.setHoraInicio(new Date());
        elementoActual.setHoraFinal(new Date());
        elementoActual.setNota("");
        elementoActual.setValorPlaneado(0);
        elementoActual.setValorUsado(0);
        if (bsSeries.getSize() > 0)
        {
            elementoActual.setIdUbicacionSerie(bsSeries.getData().get(0).getIdUbicacion());
            elementoActual.setEntradaSerie(bsSeries.getData().get(0).getEntrada());
        }
        if (bsResponsables.getData().size() > 0)
            elementoActual.setIdPersona(bsResponsables.getData().get(0).getId());
        elementoActual.setCostoTotal(0F);
        if (bsMonedasRender.getData().size() > 0)
            elementoActual.setIdMoneda(bsMonedasRender.getData().get(0).getId());

        elementoActual.setFechaFinalizacion(new Date());
        //Escribimos el objeto en la interfaz
        writeElement(elementoActual);
    }

    //---------------------------------------------------------------------
    public void prepareForModify(Mantenimiento elemento)
    {
        this.elementoActual = elemento;
        esNuevo = false;
        this.setTitle(String.format("Modificando Folio %s", elemento.getFolio()));
        bsTipoComprobantesRender.setData(parent.obtenerTiposMantenimiento());
        bsResponsables.setData(parent.obtenerResponsables());
        bsSeries.setData(parent.obtenerSeries());
        bsMonedasRender.setData(parent.obtenerMonedas());
        writeElement(elemento);
    }

    //---------------------------------------------------------------------
    public void writeElement(Mantenimiento elemento)
    {
        jcbTipo.setSelectedItem(bsTipoComprobantesRender.getElementAt(bsTipoComprobantesRender.indexOfValue(elemento.getEsTipo())));
        jtfFolio.setText(elemento.getFolio());
        jdcFechaElaboracion.setDate(elemento.getFechaCreacion());
        jcbResponsable.setSelectedItem(bsResponsables.getElementAt(bsResponsables.indexOfValue(elemento.getIdPersona())));
        jchkActivo.setSelected(elemento.esActivo());

        jcbSeries.setSelectedItem(bsSeries.getElementAt(bsSeries.indexOfValue(elemento.getIdSerie())));
        jspHoraInicio.setValue(elemento.getHoraInicio());
        jspHoraFinal.setValue(elemento.getHoraFinal());
        jtxtNota.setText(elemento.getNota());

        jtfCostoTotal.setValue(elemento.getCostoTotal());
        jcbMoneda.setSelectedItem(bsMonedasRender.getElementAt(bsMonedasRender.indexOfValue(elemento.getIdMoneda())));

        
        lblEtiquetaunidad.setText(bsSeries.getCurrent().getHasOneControlInventario().getHasOneBienVariante().obtenerEtiquetaMantenimiento());
        jdcfechaFinalizacion.setDate(elemento.getFechaFinalizacion());

        if (!esNuevo)
        {
            try
            {
                //lblValorPlaneado.setText( String.valueOf(bsSeries.getCurrent().getFk_control_almacen_id_inventario().getFk_inventario_variante_id().getValorEsperado()));
                lblValorPlaneado.setText(ftxValorusado.getFormatter().valueToString(elemento.getValorPlaneado()));
            }
            catch (ParseException ex)
            {
                Logger.getLogger(JMantenimientoDetailView.class.getName()).log(Level.SEVERE, null, ex);
            }

            ftxValorusado.setValue(elemento.getValorUsado());
        }

    }

    //---------------------------------------------------------------------
    private boolean readElement(Mantenimiento elemento)
    {
        /*boolean resultado = false;
        String mensaje = "";*/

        /*if (!jtfFolio.getText().isEmpty())
        {*/
            //resultado = true;
            elemento.setEsTipo((int) bsTipoComprobantesRender.getSelectedValue());
            elemento.setFolio(jtfFolio.getText());
            elemento.setFechaCreacion(jdcFechaElaboracion.getDate());
            elemento.setIdPersona((int) bsResponsables.getSelectedValue());
            elemento.setActivo(jchkActivo.isSelected());

            elemento.setIdUbicacionSerie(bsSeries.getCurrent().getIdUbicacion());
            elemento.setEntradaSerie(bsSeries.getCurrent().getEntrada());
            elemento.setHoraInicio((Date) jspHoraInicio.getValue());
            elemento.setHoraFinal((Date) jspHoraFinal.getValue());
            elemento.setNota(jtxtNota.getText());

            elemento.setCostoTotal((float) jtfCostoTotal.getValue());
            elemento.setIdMoneda((int) bsMonedasRender.getSelectedValue());
            if (jdcfechaFinalizacion.getDate() != null)
                elemento.setFechaFinalizacion(jdcfechaFinalizacion.getDate());
            try
            {
                elemento.setValorPlaneado((int) ftxValorusado.getFormatter().stringToValue(lblValorPlaneado.getText()));
            }
            catch (ParseException ex)
            {
                Logger.getLogger(JMantenimientoDetailView.class.getName()).log(Level.SEVERE, null, ex);
            }
            elemento.setValorUsado((int) ftxValorusado.getValue());

            /* if (construirLotes)
             elemento.setInventarioCcomo((int)bsFormasInventario.getSelectedValue());*/
            if (!elemento.isSet())
                elemento.setModified();
        /*}

        else
            mensaje = "Asegurese de proporcionar el folio";*/

        /*if (!resultado)
            JOptionPane.showMessageDialog(this, mensaje, "", JOptionPane.PLAIN_MESSAGE);

        return resultado;*/
            return true;
    }

    //---------------------------------------------------------------------
    public void close()
    {
        setVisible(false);
        dispose();
    }

    //----------------------------------------------------------------------
    public void jtbCostoso_onCellValueChanged(ChangeEvent e, TableCellEditor editor)
    {
        TableColumnModel columnas = jtbCostos.getColumnModel();
        int modelColIndex = jtbCostos.convertColumnIndexToModel(jtbCostos.getEditingColumn());
        int modelRowIndex = jtbCostos.convertRowIndexToModel(jtbCostos.getEditingRow());
        TableColumn actual = columnas.getColumn(modelColIndex);
        //MantemientoCostosTableModel dataBinding = (MantemientoCostosTableModel) jtbCostos.getModel();
        MantenimientoCosto seleccionado = bsCostosOperacion.getElementAt(modelRowIndex);

        if (actual == colMonto)
        {
            float valor_nuevo = (float) editor.getCellEditorValue();

            actualizarTotal(valor_nuevo - seleccionado.getMonto());
        }
    }

    //---------------------------------------------------------------------
    private void actualizarTotal(float diferencia)
    {
        float total = (float) jtfCostoTotal.getValue();
        total += diferencia;
        jtfCostoTotal.setValue(total);
    }
    


    //--------------------------------------------------------------------
    private void setDisplayData(List<MantenimientoDescripcion_5FN> list)
    {
        bsDescripciones.setData(list);
    }

    //--------------------------------------------------------------------
    private void setDisplayDataActivities(List<MantenimientoTieneActividad_5FN> lista)
    {
        bsActividades.setData(lista);
    }

    //--------------------------------------------------------------------
    private void setDisplayCostos(List<MantenimientoCosto> list)
    {
        bsCostosOperacion.setData(list);
    }

    //--------------------------------------------------------------------
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
        jpDatos1 = new javax.swing.JPanel();
        jlbTipoBien = new javax.swing.JLabel();
        jcbTipo = new javax.swing.JComboBox();
        jlbFolio = new javax.swing.JLabel();
        jtfFolio = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jdcFechaElaboracion = new com.toedter.calendar.JDateChooser();
        jlbResponsable = new javax.swing.JLabel();
        jcbResponsable = new javax.swing.JComboBox();
        jlbActivo = new javax.swing.JLabel();
        jchkActivo = new javax.swing.JCheckBox();
        tabDetalles = new javax.swing.JPanel();
        jpAnotacion = new javax.swing.JPanel();
        jlbNota = new javax.swing.JLabel();
        jspAnotaciones = new javax.swing.JScrollPane();
        jtxtNota = new javax.swing.JTextArea();
        jpCapturaSerie = new javax.swing.JPanel();
        jpDatos2 = new javax.swing.JPanel();
        jlbSerie = new javax.swing.JLabel();
        jcbSeries = new javax.swing.JComboBox();
        jlbHoraInicio = new javax.swing.JLabel();
        jspHoraInicio = new JSpinner(new javax.swing.SpinnerDateModel(new java.util.Date(), null, null, java.util.Calendar.HOUR_OF_DAY));
        jlbHoraFinal = new javax.swing.JLabel();
        jspHoraFinal = new JSpinner(new javax.swing.SpinnerDateModel(new java.util.Date(), null, null, java.util.Calendar.HOUR_OF_DAY));
        lblfechaFinalizacion = new javax.swing.JLabel();
        jdcfechaFinalizacion = new com.toedter.calendar.JDateChooser();
        jPanel1 = new javax.swing.JPanel();
        lblEtiquetaunidad = new javax.swing.JLabel();
        lblValorPlaneado = new javax.swing.JLabel();
        ftxValorusado = new JFormattedTextField (0);
        tabDescripcion = new javax.swing.JPanel();
        jbarDescripciones = new javax.swing.JToolBar();
        jspDescripciones = new javax.swing.JScrollPane();
        jtbDescripciones = new javax.swing.JTable();
        tabActividades = new javax.swing.JPanel();
        jspActividades = new javax.swing.JScrollPane();
        jtbActividades = new javax.swing.JTable();
        jtbAccionesActividades = new javax.swing.JToolBar();
        jbtnImprimir = new javax.swing.JButton();
        tabCostos = new javax.swing.JPanel();
        jbarCostos = new javax.swing.JToolBar();
        jtoolNuevoPrecio = new javax.swing.JButton();
        jtoolEliminarPrecio = new javax.swing.JButton();
        jspCostos = new javax.swing.JScrollPane();
        jtbCostos = new JTable(bsCostosOperacion)
        {
            @Override // Always selectAll()
            public void editingStopped(ChangeEvent e)
            {
                jtbCostoso_onCellValueChanged(e, getCellEditor());
                super.editingStopped(e);
            }
        };
        jpTotales = new javax.swing.JPanel();
        jlbCosto = new javax.swing.JLabel();
        jtfCostoTotal = new JFormattedTextField(0.0F);
        jlbMoneda = new javax.swing.JLabel();
        jcbMoneda = new javax.swing.JComboBox();
        jpAcciones = new javax.swing.JPanel();
        jbCancelar = new javax.swing.JButton();
        jbAceptar = new javax.swing.JButton();
        jpEncabezado = new javax.swing.JPanel();
        jlbIcono = new javax.swing.JLabel();
        jpAreaMensajes = new java.awt.Panel();

        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setPreferredSize(new java.awt.Dimension(800, 656));

        jtpBody.setName(""); // NOI18N

        tabGeneral.setName("tabGeneral"); // NOI18N
        tabGeneral.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        java.awt.GridBagLayout jpDatos1Layout = new java.awt.GridBagLayout();
        jpDatos1Layout.columnWidths = new int[] {0, 6, 0};
        jpDatos1Layout.rowHeights = new int[] {0, 5, 0, 5, 0, 5, 0, 5, 0};
        jpDatos1.setLayout(jpDatos1Layout);

        jlbTipoBien.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbTipoBien.setText("Tipo:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        jpDatos1.add(jlbTipoBien, gridBagConstraints);

        jcbTipo.setModel(bsTipoComprobantesRender);
        jcbTipo.setPreferredSize(new java.awt.Dimension(400, 27));
        jcbTipo.setRenderer(new POJOListCellRenderer<Valor>());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jpDatos1.add(jcbTipo, gridBagConstraints);

        jlbFolio.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbFolio.setText("Folio:");
        jlbFolio.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        jpDatos1.add(jlbFolio, gridBagConstraints);

        jtfFolio.setName("jtfFolio"); // NOI18N
        jtfFolio.setPreferredSize(new java.awt.Dimension(170, 27));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jpDatos1.add(jtfFolio, gridBagConstraints);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Fecha:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        jpDatos1.add(jLabel1, gridBagConstraints);

        jdcFechaElaboracion.setPreferredSize(new java.awt.Dimension(250, 27));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jpDatos1.add(jdcFechaElaboracion, gridBagConstraints);

        jlbResponsable.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbResponsable.setText("Responsable:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        jpDatos1.add(jlbResponsable, gridBagConstraints);

        jcbResponsable.setModel(bsResponsables);
        jcbResponsable.setName("jcbResponsable"); // NOI18N
        jcbResponsable.setPreferredSize(new java.awt.Dimension(400, 27));
        jcbResponsable.setRenderer(new POJOListCellRenderer<Persona>()
        );
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jpDatos1.add(jcbResponsable, gridBagConstraints);

        jlbActivo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
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

        tabGeneral.add(jpDatos1);

        jtpBody.addTab("General", tabGeneral);

        tabDetalles.setPreferredSize(new java.awt.Dimension(300, 200));
        tabDetalles.setLayout(new java.awt.BorderLayout());

        jpAnotacion.setLayout(new java.awt.BorderLayout());

        jlbNota.setText("Nota:");
        jpAnotacion.add(jlbNota, java.awt.BorderLayout.NORTH);

        jtxtNota.setColumns(20);
        jtxtNota.setRows(5);
        jspAnotaciones.setViewportView(jtxtNota);

        jpAnotacion.add(jspAnotaciones, java.awt.BorderLayout.CENTER);

        tabDetalles.add(jpAnotacion, java.awt.BorderLayout.SOUTH);

        jpCapturaSerie.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jpDatos2.setRequestFocusEnabled(false);
        java.awt.GridBagLayout jpDatos2Layout = new java.awt.GridBagLayout();
        jpDatos2Layout.columnWidths = new int[] {0, 6, 0, 6, 0, 6, 0};
        jpDatos2Layout.rowHeights = new int[] {0, 5, 0, 5, 0, 5, 0};
        jpDatos2.setLayout(jpDatos2Layout);

        jlbSerie.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbSerie.setText("Serie:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        jpDatos2.add(jlbSerie, gridBagConstraints);

        jcbSeries.setModel(bsSeries);
        jcbSeries.setPreferredSize(new java.awt.Dimension(550, 27));
        jcbSeries.setRenderer(new POJOListCellRenderer<ControlAlmacen>());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jpDatos2.add(jcbSeries, gridBagConstraints);

        jlbHoraInicio.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbHoraInicio.setText("Hora Inicio:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        jpDatos2.add(jlbHoraInicio, gridBagConstraints);

        jspHoraInicio.setPreferredSize(new java.awt.Dimension(150, 27));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jpDatos2.add(jspHoraInicio, gridBagConstraints);

        jlbHoraFinal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbHoraFinal.setText("Hora Final:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        jpDatos2.add(jlbHoraFinal, gridBagConstraints);

        jspHoraFinal.setPreferredSize(new java.awt.Dimension(150, 27));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jpDatos2.add(jspHoraFinal, gridBagConstraints);

        lblfechaFinalizacion.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblfechaFinalizacion.setText("Finalización:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        jpDatos2.add(lblfechaFinalizacion, gridBagConstraints);

        jdcfechaFinalizacion.setPreferredSize(new java.awt.Dimension(250, 27));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jpDatos2.add(jdcfechaFinalizacion, gridBagConstraints);

        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        lblEtiquetaunidad.setText("Desconocido");
        jPanel1.add(lblEtiquetaunidad);

        lblValorPlaneado.setText("0");
        jPanel1.add(lblValorPlaneado);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        jpDatos2.add(jPanel1, gridBagConstraints);

        ftxValorusado.setPreferredSize(new java.awt.Dimension(250, 27));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jpDatos2.add(ftxValorusado, gridBagConstraints);

        jpCapturaSerie.add(jpDatos2);

        tabDetalles.add(jpCapturaSerie, java.awt.BorderLayout.CENTER);

        jtpBody.addTab("Detalles", tabDetalles);

        tabDescripcion.setName("tabDescripcion"); // NOI18N
        tabDescripcion.setLayout(new java.awt.BorderLayout());

        jbarDescripciones.setRollover(true);
        tabDescripcion.add(jbarDescripciones, java.awt.BorderLayout.PAGE_START);

        jtbDescripciones.setModel(bsDescripciones);
        jtbDescripciones.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jspDescripciones.setViewportView(jtbDescripciones);

        tabDescripcion.add(jspDescripciones, java.awt.BorderLayout.CENTER);

        jtpBody.addTab("Descripción detallada", tabDescripcion);

        tabActividades.setLayout(new java.awt.BorderLayout());

        jtbActividades.setModel(bsActividades);
        jtbActividades.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jspActividades.setViewportView(jtbActividades);

        tabActividades.add(jspActividades, java.awt.BorderLayout.CENTER);

        jtbAccionesActividades.setRollover(true);

        jbtnImprimir.setText("Imprimir");
        jbtnImprimir.setFocusable(false);
        jbtnImprimir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbtnImprimir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jtbAccionesActividades.add(jbtnImprimir);

        tabActividades.add(jtbAccionesActividades, java.awt.BorderLayout.PAGE_START);

        jtpBody.addTab("Actividades realizadas", tabActividades);

        tabCostos.setName("tabIngredientes"); // NOI18N
        tabCostos.setLayout(new java.awt.BorderLayout());

        jbarCostos.setRollover(true);

        jtoolNuevoPrecio.setText("Nuevo");
        jtoolNuevoPrecio.setFocusable(false);
        jtoolNuevoPrecio.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jtoolNuevoPrecio.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbarCostos.add(jtoolNuevoPrecio);

        jtoolEliminarPrecio.setText("Eliminar");
        jtoolEliminarPrecio.setToolTipText("");
        jtoolEliminarPrecio.setFocusable(false);
        jtoolEliminarPrecio.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jtoolEliminarPrecio.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbarCostos.add(jtoolEliminarPrecio);

        tabCostos.add(jbarCostos, java.awt.BorderLayout.PAGE_START);

        jtbCostos.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jspCostos.setViewportView(jtbCostos);

        tabCostos.add(jspCostos, java.awt.BorderLayout.CENTER);

        jpTotales.setPreferredSize(new java.awt.Dimension(608, 40));
        jpTotales.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 10, 5));

        jlbCosto.setText("Costo Total:");
        jpTotales.add(jlbCosto);

        jtfCostoTotal.setPreferredSize(new java.awt.Dimension(150, 22));
        jpTotales.add(jtfCostoTotal);

        jlbMoneda.setText("Moneda:");
        jpTotales.add(jlbMoneda);

        jcbMoneda.setModel(bsMonedasRender);
        jcbMoneda.setPreferredSize(new java.awt.Dimension(80, 26));
        jcbMoneda.setRenderer(new POJOListCellRenderer<Moneda>());
        jpTotales.add(jcbMoneda);

        tabCostos.add(jpTotales, java.awt.BorderLayout.PAGE_END);

        jtpBody.addTab("Costos", tabCostos);

        getContentPane().add(jtpBody, java.awt.BorderLayout.CENTER);

        jpAcciones.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jbCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/syswave/forms/resources/button-cross.png"))); // NOI18N
        jbCancelar.setText("Cancelar");
        jpAcciones.add(jbCancelar);

        jbAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/syswave/forms/resources/button-tick.png"))); // NOI18N
        jbAceptar.setText("Aceptar");
        jpAcciones.add(jbAceptar);

        getContentPane().add(jpAcciones, java.awt.BorderLayout.PAGE_END);

        jpEncabezado.setBackground(new java.awt.Color(204, 204, 0));
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

        setBounds(0, 0, 813, 457);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFormattedTextField ftxValorusado;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton jbAceptar;
    private javax.swing.JButton jbCancelar;
    private javax.swing.JToolBar jbarCostos;
    private javax.swing.JToolBar jbarDescripciones;
    private javax.swing.JButton jbtnImprimir;
    private javax.swing.JComboBox jcbMoneda;
    private javax.swing.JComboBox jcbResponsable;
    private javax.swing.JComboBox jcbSeries;
    private javax.swing.JComboBox jcbTipo;
    private javax.swing.JCheckBox jchkActivo;
    private com.toedter.calendar.JDateChooser jdcFechaElaboracion;
    private com.toedter.calendar.JDateChooser jdcfechaFinalizacion;
    private javax.swing.JLabel jlbActivo;
    private javax.swing.JLabel jlbCosto;
    private javax.swing.JLabel jlbFolio;
    private javax.swing.JLabel jlbHoraFinal;
    private javax.swing.JLabel jlbHoraInicio;
    private javax.swing.JLabel jlbIcono;
    private javax.swing.JLabel jlbMoneda;
    private javax.swing.JLabel jlbNota;
    private javax.swing.JLabel jlbResponsable;
    private javax.swing.JLabel jlbSerie;
    private javax.swing.JLabel jlbTipoBien;
    private javax.swing.JPanel jpAcciones;
    private javax.swing.JPanel jpAnotacion;
    private java.awt.Panel jpAreaMensajes;
    private javax.swing.JPanel jpCapturaSerie;
    private javax.swing.JPanel jpDatos1;
    private javax.swing.JPanel jpDatos2;
    private javax.swing.JPanel jpEncabezado;
    private javax.swing.JPanel jpTotales;
    private javax.swing.JScrollPane jspActividades;
    private javax.swing.JScrollPane jspAnotaciones;
    private javax.swing.JScrollPane jspCostos;
    private javax.swing.JScrollPane jspDescripciones;
    private javax.swing.JSpinner jspHoraFinal;
    private javax.swing.JSpinner jspHoraInicio;
    private javax.swing.JToolBar jtbAccionesActividades;
    private javax.swing.JTable jtbActividades;
    private javax.swing.JTable jtbCostos;
    private javax.swing.JTable jtbDescripciones;
    private javax.swing.JFormattedTextField jtfCostoTotal;
    private javax.swing.JTextField jtfFolio;
    private javax.swing.JButton jtoolEliminarPrecio;
    private javax.swing.JButton jtoolNuevoPrecio;
    private javax.swing.JTabbedPane jtpBody;
    private javax.swing.JTextArea jtxtNota;
    private javax.swing.JLabel lblEtiquetaunidad;
    private javax.swing.JLabel lblValorPlaneado;
    private javax.swing.JLabel lblfechaFinalizacion;
    private javax.swing.JPanel tabActividades;
    private javax.swing.JPanel tabCostos;
    private javax.swing.JPanel tabDescripcion;
    private javax.swing.JPanel tabDetalles;
    private javax.swing.JPanel tabGeneral;
    // End of variables declaration//GEN-END:variables

//------------------------------------------------------------------
    private class MantenimientoDetailCargarSwingWorker extends SwingWorker<List<Object>, Void>
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
                    case opLOAD_DESCRIPCIONES:
                        arguments.add(parent.obtenerDescripciones((Mantenimiento) arguments.get(1)));
                        break;

                    case opLOAD_ACTIVIDADES:
                        arguments.add(parent.obtenerCheckList((Mantenimiento) arguments.get(1), (ControlAlmacen) arguments.get(2)));
                        break;

                    case opLOAD_COSTOS:
                        arguments.add(parent.obtenerCostos((Mantenimiento) arguments.get(1)));
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
                        case opLOAD_DESCRIPCIONES:
                            setDisplayData((List<MantenimientoDescripcion_5FN>) results.get(2));
                            break;

                        case opLOAD_ACTIVIDADES:
                            setDisplayDataActivities((List<MantenimientoTieneActividad_5FN>) results.get(3));
                            break;

                        case opLOAD_COSTOS:
                            setDisplayCostos((List<MantenimientoCosto>) results.get(2));
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
