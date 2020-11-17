package com.syswave.forms.miempresa;

import com.syswave.entidades.common.Entidad;
import com.syswave.entidades.configuracion.Usuario_tiene_Permiso;
import com.syswave.entidades.miempresa.Documento;
import com.syswave.entidades.miempresa.DocumentoContadoMovimiento;
import com.syswave.entidades.miempresa.DocumentoDetalle;
import com.syswave.entidades.miempresa.Moneda;
import com.syswave.entidades.miempresa.PersonaDireccion_tiene_Documento;
import com.syswave.entidades.miempresa.TipoComprobante;
import com.syswave.entidades.miempresa_vista.DocumentoComercial;
import com.syswave.entidades.miempresa_vista.DocumentoContadoMovimiento_5FN;
import com.syswave.entidades.miempresa_vista.DocumentoDetalleNavigable;
import com.syswave.entidades.miempresa_vista.DocumentoDetalle_tiene_PrecioVista;
import com.syswave.entidades.miempresa_vista.PersonaDireccion_tiene_Documento_5FN;
import com.syswave.forms.common.JTableDataView;
import com.syswave.forms.databinding.DocumentoComercialTableModel;
import com.syswave.forms.databinding.MonedasComboBoxModel;
import com.syswave.swing.models.POJOTableModel;
import com.syswave.forms.databinding.TiposComprobantesComboBoxModel;
import com.syswave.swing.table.editors.LookUpComboBoxTableCellEditor;
import com.syswave.swing.table.renders.LookUpComboBoxTableCellRenderer;
import com.syswave.logicas.miempresa.DocumentoContadoMovimientosBusinessLogic;
import com.syswave.logicas.miempresa.DocumentoDetalleTienePrecioBusinessLogic;
import com.syswave.logicas.miempresa.DocumentoDetallesBusinessLogic;
import com.syswave.logicas.miempresa.DocumentosBusinessLogic;
import com.syswave.logicas.miempresa.MonedasBusinessLogic;
import com.syswave.logicas.miempresa.PersonaDireccionTieneDocumentosBusinessLogic;
import com.syswave.logicas.miempresa.TiposComprobantesBusinessLogic;
import com.toedter.calendar.JDateChooserCellEditor;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import com.syswave.forms.common.IWindowContainer;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class JDocumentosComercialesDataView extends JTableDataView implements IDocumentoComercialMediator, TableModelListener
{

    private final int opLOAD = 0;
    private final int opINSERT_LIST = 1;
    private final int opUPDATE_LIST = 2;
    private final int opDELETE_LIST = 3;
    private final int opINSERT = 4;
    private final int opUPDATE = 5;
    private final int opSAVE = 6;

    private boolean can_browse, can_create, can_update, can_delete;

    TiposComprobantesComboBoxModel bsTipoComprobantesRender;
    TiposComprobantesComboBoxModel bsTipoComprobantesEditor;
    MonedasComboBoxModel bsMonedasRender;
    MonedasComboBoxModel bsMonedasEditor;

    DocumentoComercialTableModel bsDocumentos;
    DocumentosBusinessLogic documentos;
    DocumentoDetallesBusinessLogic detalles;
    PersonaDireccionTieneDocumentosBusinessLogic personaDireccionesDocumentos;
    DocumentoDetalleTienePrecioBusinessLogic detalleTienePrecios;
    TiposComprobantesBusinessLogic tipoComprobantes;
    DocumentoContadoMovimientosBusinessLogic movimientosContado;
    MonedasBusinessLogic monedas;

    TableColumn colTipoComprobante, colFecha, colSerie, colFolio,
            colReceptor, colCondiciones, colMoneda;
    DocumentoSwingWorker swSecondPlane;
    List<Usuario_tiene_Permiso> permisos;

    //---------------------------------------------------------------------
    private void grantAllPermisions()
    {
        can_browse = true;
        can_create = true;
        can_update = true;
        can_delete = true;
    }

    //-------------------------------------------------------------------
    public JDocumentosComercialesDataView(IWindowContainer container)
    {
        super(container);
        initAttributes();
        grantAllPermisions();
    }

    //-------------------------------------------------------------------
    public JDocumentosComercialesDataView(IWindowContainer container, List<Usuario_tiene_Permiso> values)
    {
        super(container);
        initAttributes();
        grant(values);
    }

    //---------------------------------------------------------------------
    /**
     * Este método sirve para asignar los permisos correspondientes.
     *
     * @param values
     */
    public final void grant(List<Usuario_tiene_Permiso> values)
    {
        if (permisos != null && permisos.size() > 0)
            permisos.clear();

        can_browse = false;
        can_create = false;
        can_update = false;
        can_delete = false;
        permisos = values;

        for (int i = 0; i < values.size(); i++)
        {
            if (values.get(i).getLlave().equals("browse"))
                can_browse = true;

            else if (values.get(i).getLlave().equals("create"))
                can_create = true;

            else if (values.get(i).getLlave().equals("update"))
                can_update = true;

            else if (values.get(i).getLlave().equals("delete"))
                can_delete = true;
        }

        bsDocumentos.setReadOnly(!can_update);
    }

    private void initAttributes()
    {
        bsDocumentos = new DocumentoComercialTableModel(new String[]
        {
            "Tipo:{id_tipo_comprobante}", "Serie:{serie}",
            "Folio:{folio}", "Receptor:{receptor}",
            "Condiciones:{condiciones}", "Fecha:{fecha_elaboracion}",
            "Total:{total}", "Moneda:{id_moneda}",
            "Estatus:{id_estatus}"
        });
        bsDocumentos.addTableModelListener(this);
        bsTipoComprobantesRender = new TiposComprobantesComboBoxModel();
        bsTipoComprobantesEditor = new TiposComprobantesComboBoxModel();
        bsMonedasRender = new MonedasComboBoxModel();
        bsMonedasEditor = new MonedasComboBoxModel();
        documentos = new DocumentosBusinessLogic(mainContainer.getOrigenDatoActual().getNombre());
        detalles = new DocumentoDetallesBusinessLogic(documentos.getEsquema());
        personaDireccionesDocumentos = new PersonaDireccionTieneDocumentosBusinessLogic(documentos.getEsquema());
        detalleTienePrecios = new DocumentoDetalleTienePrecioBusinessLogic(documentos.getEsquema());
        tipoComprobantes = new TiposComprobantesBusinessLogic(documentos.getEsquema());
        monedas = new MonedasBusinessLogic(documentos.getEsquema());
        movimientosContado = new DocumentoContadoMovimientosBusinessLogic(documentos.getEsquema());

        jtbData.setModel(bsDocumentos);
        jtbData.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        if (jtbData.getColumnCount() > 0)
        {
            colTipoComprobante = jtbData.getColumnModel().getColumn(0);
            colTipoComprobante.setCellRenderer(new LookUpComboBoxTableCellRenderer<TipoComprobante>(bsTipoComprobantesRender));
            colTipoComprobante.setCellEditor(new LookUpComboBoxTableCellEditor<TipoComprobante>(bsTipoComprobantesEditor));
            colTipoComprobante.setPreferredWidth(150);

            colSerie = jtbData.getColumnModel().getColumn(1);
            colSerie.setPreferredWidth(60);

            colFolio = jtbData.getColumnModel().getColumn(2);
            colFolio.setPreferredWidth(120);

            colReceptor = jtbData.getColumnModel().getColumn(3);
            colReceptor.setPreferredWidth(250);

            colCondiciones = jtbData.getColumnModel().getColumn(4);
            colCondiciones.setPreferredWidth(250);

            colFecha = jtbData.getColumnModel().getColumn(5);
            colFecha.setCellEditor(new JDateChooserCellEditor());
            colFecha.setPreferredWidth(150);

            colMoneda = jtbData.getColumnModel().getColumn(7);
            colMoneda.setCellRenderer(new LookUpComboBoxTableCellRenderer<Moneda>(bsMonedasRender));
            colMoneda.setCellEditor(new LookUpComboBoxTableCellEditor<Moneda>(bsMonedasEditor));
            colMoneda.setPreferredWidth(60);

            //Nota: Debido a los renders que se estan utilizando es necesario tener un renglón más alto.
            jtbData.setRowHeight((int) (jtbData.getRowHeight() * 1.5));
        }
    }

    // -------------------------------------------------------------------
    @Override
    public void showDetail(DocumentoComercial elemento)
    {
        JDocumentoComercialDetailView dialogo = new JDocumentoComercialDetailView(this);
        mainContainer.addWindow(dialogo);

        if (elemento != null)
            dialogo.prepareForModify(elemento);

        else
            dialogo.prepareForNew();

        mainContainer.showCenter(dialogo);
    }

    // -------------------------------------------------------------------
    @Override
    public void onAcceptNewElement(DocumentoComercial nuevo, List<DocumentoDetalleNavigable> detalles,
            List<PersonaDireccion_tiene_Documento_5FN> direcciones,
            List<DocumentoContadoMovimiento_5FN> pagos)
    {
        if (swSecondPlane == null || swSecondPlane.isDone())
        {
            List<Object> parametros = new ArrayList<Object>();
            swSecondPlane = new DocumentoSwingWorker();
            swSecondPlane.addPropertyChangeListener(this);
            parametros.add(opINSERT);
            parametros.add(nuevo);
            parametros.add(detalles);
            parametros.add(direcciones);
            parametros.add(pagos);

            jbMessage.setText("Guardando...");
            swSecondPlane.execute(parametros);
        }

    }

    // -------------------------------------------------------------------
    @Override
    public void onAcceptModifyElement(DocumentoComercial modificado, List<DocumentoDetalleNavigable> detalles,
            List<DocumentoDetalle> detalles_deleted,
            List<PersonaDireccion_tiene_Documento_5FN> direcciones,
            List<PersonaDireccion_tiene_Documento> direcciones_deleted,
            List<DocumentoContadoMovimiento_5FN> pagos,
            List<DocumentoContadoMovimiento> pagos_deleted)
    {
        if (swSecondPlane == null || swSecondPlane.isDone())
        {
            List<Object> parametros = new ArrayList<Object>();
            swSecondPlane = new DocumentoSwingWorker();
            swSecondPlane.addPropertyChangeListener(this);
            parametros.add(opUPDATE);
            parametros.add(modificado);
            parametros.add(detalles);
            parametros.add(detalles_deleted);
            parametros.add(direcciones);
            parametros.add(direcciones_deleted);
            parametros.add(pagos);
            parametros.add(pagos_deleted);

            jbMessage.setText("Guardando...");
            swSecondPlane.execute(parametros);
        }
    }

    //---------------------------------------------------------------------
    private void resetElement(Documento value)
    {
        value.acceptChanges();
        bsDocumentos.fireTableRowsUpdated(jtbData.getSelectedRow(), jtbData.getSelectedRow());
        jbMessage.setText("Cambio guardado");
    }

    //---------------------------------------------------------------------
    @Override
    public String getEsquema()
    {
        return mainContainer.getOrigenDatoActual().getNombre();
    }

    // -------------------------------------------------------------------
    @Override
    public List<TipoComprobante> obtenerTiposComprobantes()
    {
        return bsTipoComprobantesEditor.getData();
    }

    //--------------------------------------------------------------------
    @Override
    public List<Moneda> obtenerMonedas()
    {
        return bsMonedasEditor.getData();
    }

    //--------------------------------------------------------------------
    @Override
    public List<PersonaDireccion_tiene_Documento_5FN> obtenerPersonaDireccionDocumentos(DocumentoComercial elemento)
    {
        return personaDireccionesDocumentos.obtenerLista5FN(elemento.getId());
    }

    //--------------------------------------------------------------------
    @Override
    public List<DocumentoContadoMovimiento_5FN> obtenerFormasPago(DocumentoComercial elemento)
    {
        return movimientosContado.obtenerLista5FN(elemento.getId());
    }

    //--------------------------------------------------------------------
    @Override
    public List<DocumentoDetalleNavigable> obtenerDocumentoDetalles(DocumentoComercial elemento)
    {
        DocumentoDetalleNavigable filtro = new DocumentoDetalleNavigable();
        filtro.setHasOneDocumento(elemento);
        return detalles.obtenerListaSeries(filtro);
    }

    //--------------------------------------------------------------------
    @Override
    public List<DocumentoDetalle_tiene_PrecioVista> obtenerDetalleTienePrecios(DocumentoComercial elemento)
    {
        return detalleTienePrecios.obtenerLista5FN(elemento.getId(), -1);
    }

    //--------------------------------------------------------------------
    @Override
    public List<DocumentoDetalle_tiene_PrecioVista> obtenerDetalleTienePrecios(DocumentoDetalle elemento)
    {
        return detalleTienePrecios.obtenerLista5FN(elemento.getIdDocumento(), elemento.getConsecutivo());
    }

    //---------------------------------------------------------------------
    @Override
    public void agregaContenedorPrincipal(JInternalFrame Dialog)
    {
        mainContainer.addWindow(Dialog);
    }

    //---------------------------------------------------------------------
    @Override
    public void mostrarCentrado(JInternalFrame Dialog)
    {
        mainContainer.showCenter(Dialog);
    }

   //--------------------------------------------------------------------
  /* @Override
     public List<DocumentoIdentificador> obtenerMedios(Documento elemento)
     {
     DocumentoIdentificador filtro = new DocumentoIdentificador();
     filtro.setClave("");
     filtro.setIdDocumento(elemento.getId());
      
     return identificadores.obtenerListaMedios(filtro);
     }*/
    // -------------------------------------------------------------------
    @Override
    public void doCreateProcess()
    {
        if (can_create)
            showDetail(null);

        else
            JOptionPane.showMessageDialog(this, "No cuenta con los permisos necesarios para INSERTAR elementos");
    }

    // -------------------------------------------------------------------
    @Override
    public void doUpdateProcess()
    {
        if (can_update)
        {
            int index = jtbData.getSelectedRow();

            if (index >= 0)
            {
                index = jtbData.convertRowIndexToModel(index);
                showDetail(bsDocumentos.getElementAt(index));
            }
        }
        else
            JOptionPane.showMessageDialog(this, "No cuenta con los permisos necesarios para ACTUALIZAR elementos");
    }

    // -------------------------------------------------------------------
    @Override
    public void doDeleteProcess()
    {
        if (can_delete)
        {
            if (JOptionPane.showConfirmDialog(this, String.format("Ha seleccionado %d registro(s) para ser eleiminado(s)\n¿Esta seguro de continuar?", jtbData.getSelectedRowCount())) == JOptionPane.OK_OPTION)
            {
                if (swSecondPlane == null || swSecondPlane.isDone())
                {
                    List<Object> parametros = new ArrayList<Object>();
                    swSecondPlane = new DocumentoSwingWorker();
                    swSecondPlane.addPropertyChangeListener(this);
                    parametros.add(opDELETE_LIST);
                    int[] rowsHandlers = jtbData.getSelectedRows();
                    for (int i = 0; i < rowsHandlers.length; i++)
                    {
                        rowsHandlers[i] = jtbData.convertRowIndexToModel(rowsHandlers[i]);
                    }
                    parametros.add(bsDocumentos.getElementsAt(rowsHandlers));
                    parametros.add(rowsHandlers);
                    jbMessage.setText("Eliminando elemento(s)...");
                    swSecondPlane.execute(parametros);
                }
            }
        }

        else
            JOptionPane.showMessageDialog(this, "No cuenta con los permisos necesarios para eliminar elementos");
    }

    // -------------------------------------------------------------------
    @Override
    public void doRetrieveProcess()
    {
        if (can_browse)
        {
            if (swSecondPlane == null || swSecondPlane.isDone())
            {
                List<Object> parametros = new ArrayList<Object>();
                swSecondPlane = new DocumentoSwingWorker();
                swSecondPlane.addPropertyChangeListener(this);
                TipoComprobante filtro = new TipoComprobante();
                filtro.setComercial(true);
                filtro.setNombre("");
                parametros.add(opLOAD);
                parametros.add(filtro);
                jbMessage.setText("Obteniendo información...");
                swSecondPlane.execute(parametros);
            }
        }
        else
            JOptionPane.showMessageDialog(this, "No cuenta con los permisos necesarios para acceder a la información");
    }

    // -------------------------------------------------------------------
    @Override
    public void doSaveProcess()
    {
        int elementosTotales = 0;
        List<Documento> modificados = new ArrayList<>();
        List<Documento> agregados = new ArrayList<>();
        List<DocumentoComercial> datos = bsDocumentos.getData();

        TableCellEditor editor = jtbData.getCellEditor();
        if (editor != null)
            editor.stopCellEditing();

        for (DocumentoComercial elemento : datos)
        {
            if (elemento.isNew())
                agregados.add(elemento);

            else if (elemento.isModified())
                modificados.add(elemento);
        }

        elementosTotales = modificados.size() + agregados.size();

        if (elementosTotales > 0)
        {
            if (swSecondPlane == null || swSecondPlane.isDone())
            {
                List<Object> parametros = new ArrayList<Object>();
                swSecondPlane = new DocumentoSwingWorker();
                swSecondPlane.addPropertyChangeListener(this);
                parametros.add(opSAVE);
                parametros.add(agregados);
                parametros.add(modificados);
                swSecondPlane.execute(parametros);
            }
        }
        else
            JOptionPane.showMessageDialog(this, "No existen cambios a guardar");

    }

    // -------------------------------------------------------------------
    @Override
    public void doOpenProcess()
    {
        if (can_update)
        {
            int index = jtbData.getSelectedRow();

            if (index >= 0)
            {
                index = jtbData.convertRowIndexToModel(index);
                showDetail(bsDocumentos.getElementAt(index));
            }
        }
        else
            JOptionPane.showMessageDialog(this, "No cuenta con los permisos necesarios para ACTUALIZAR elementos");
    }

    // -------------------------------------------------------------------
    private void setDisplayData(List<Moneda> lstMonedas, List<TipoComprobante> tipos, List<DocumentoComercial> values)
    {
        bsMonedasEditor.setData(lstMonedas);
        bsMonedasRender.setData(lstMonedas);
        bsTipoComprobantesRender.setData(tipos);
        bsTipoComprobantesEditor.setData(tipos);
        if (values != null)
            bsDocumentos.setData(values);
        
        printRecordCount();
        jbMessage.setText("Información obtenida");
    }

    //---------------------------------------------------------------------
    private void setDisplayData(DocumentoComercial value)
    {
        bsDocumentos.addRow(value);
        jbMessage.setText("Nuevo agregado");
        printRecordCount();
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
                
                setChanges();
                jbMessage.setText("Cambios pendientes");
            }
        }
    }

    //---------------------------------------------------------------------
    @Override
    public void doPrint()
    {
        if (jtbData.getSelectedRow() >= 0)
        {
            Connection forJasper = documentos.getConexion();
            DocumentoComercial elemento = bsDocumentos.getElementAt(jtbData.getSelectedRow());
            Map parameters = new HashMap();
            parameters.put("id_documento", ((Integer) elemento.getId()).intValue());
            jasperSoftToPrint(forJasper, elemento.getSerie() + elemento.getFolio(),
                    "/com/syswave/reports/documentoComercial.jasper", parameters);
            documentos.setAvailableConextion(forJasper);
        }
    }

    //---------------------------------------------------------------------
    //--  Esta clase controla el cargado en segundo plano de los origenes de datos.
    //---------------------------------------------------------------------
    private class DocumentoSwingWorker extends SwingWorker<List<Object>, Void>
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
        public List<Object> doInBackground()
        {
            setProgress(50);
            if (!isCancelled() && arguments != null && arguments.size() > 0)
            {
                int opcion = (int) arguments.get(0); //Debe haber un entero en la primera posición
                List<Object> results = new ArrayList<>();
                results.add(opcion);
                switch (opcion)
                {
                    case opLOAD:
                        results.add(documentos.obtenerListaVista());
                        results.add(tipoComprobantes.obtenerListaHojasComerciales((TipoComprobante) arguments.get(1)));
                        results.add(monedas.obtenerLista());
                        break;

                    case opINSERT:
                        results.add(documentos.agregar((Documento) arguments.get(1)));

                        results.add(detalles.guardar((List<DocumentoDetalleNavigable>) arguments.get(2), null));
                        results.add(personaDireccionesDocumentos.guardar((List<PersonaDireccion_tiene_Documento_5FN>) arguments.get(3), null));
                        results.add(movimientosContado.guardar((List<DocumentoContadoMovimiento_5FN>) arguments.get(4), null));

                        break;

                    case opUPDATE:
                        results.add(documentos.actualizar((Documento) arguments.get(1)));
                        results.add(detalles.guardar((List<DocumentoDetalleNavigable>) arguments.get(2),
                                (List<DocumentoDetalle>) arguments.get(3)));
                        results.add(personaDireccionesDocumentos.guardar((List<PersonaDireccion_tiene_Documento_5FN>) arguments.get(4),
                                (List<PersonaDireccion_tiene_Documento>) arguments.get(5)));
                        results.add(movimientosContado.guardar((List<DocumentoContadoMovimiento_5FN>) arguments.get(6),
                                (List<DocumentoContadoMovimiento>) arguments.get(7)));

                        break;

                    case opDELETE_LIST:
                        List<Documento> selecteds = (List<Documento>) arguments.get(1);
                        results.add(documentos.borrar(selecteds));
                        break;

                    case opSAVE:
                        List<Documento> adds = (List<Documento>) arguments.get(1);
                        List<Documento> modifieds = (List<Documento>) arguments.get(2);
                        if (adds.size() > 0)
                            results.add(documentos.agregar(adds));
                        if (modifieds.size() > 0)
                            results.add(documentos.actualizar(modifieds));
                        break;

                    default:
                        results.add(documentos.obtenerListaVista());
                        results.add(tipoComprobantes.obtenerListaHojasComerciales((TipoComprobante) arguments.get(1)));
                        results.add(monedas.obtenerLista());
                        break;
                }

                setProgress(100);
                return results;
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

                    if (results.size() > 0)
                    {
                        int opcion = (int) results.get(0);

                        switch (opcion)
                        {
                            case opLOAD:
                                List<DocumentoComercial> listaDocumentos = (List<DocumentoComercial>) results.get(1);
                                List<TipoComprobante> listaTipos = (List<TipoComprobante>) results.get(2);
                                List<Moneda> listaMonedas = (List<Moneda>) results.get(3);

                                if (listaTipos.size() > 0)
                                    setDisplayData(listaMonedas, listaTipos, listaDocumentos);
                                else if (!tipoComprobantes.esCorrecto())
                                    JOptionPane.showMessageDialog(null, tipoComprobantes.getMensaje());
                                break;

                            case opINSERT:
                                if (documentos.esCorrecto())
                                    setDisplayData((DocumentoComercial) arguments.get(1));

                                else
                                    jbMessage.setText(documentos.getMensaje());
                                break;

                            case opUPDATE:
                                if (documentos.esCorrecto())
                                    resetElement((Documento) arguments.get(1));

                                else
                                    jbMessage.setText(documentos.getMensaje());
                                break;

                            case opDELETE_LIST:
                                if (documentos.esCorrecto())
                                {
                                    bsDocumentos.removeRows((int[]) arguments.get(2)); 
                                    jbMessage.setText("Elemento(s) eliminado(s)");
                                
                                     printRecordCount();
                                }
                                else
                                    jbMessage.setText(documentos.getEsquema());
                                break;

                            case opSAVE:
                                jbMessage.setText(documentos.getMensaje());
                                break;

                        }

                        results.clear();
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
