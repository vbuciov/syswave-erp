package com.syswave.forms.miempresa;

import com.syswave.entidades.common.Entidad;
import com.syswave.entidades.configuracion.Usuario_tiene_Permiso;
import com.syswave.entidades.miempresa.Moneda;
import com.syswave.entidades.miempresa.PersonaCreditoCuenta;
import com.syswave.entidades.miempresa.TipoPersona;
import com.syswave.entidades.miempresa.Valor;
import com.syswave.entidades.miempresa_vista.PersonaCreditoCuenta_5FN;
import com.syswave.forms.common.JTableDataView;
import com.syswave.swing.models.POJOTableModel;
import com.syswave.forms.databinding.MonedasComboBoxModel;
import com.syswave.forms.databinding.PersonaCreditoCuenta5FNTableModel;
import com.syswave.forms.databinding.TipoPersonasComboBoxModel;
import com.syswave.forms.databinding.ValorComboBoxModel;
import com.syswave.swing.table.editors.LookUpComboBoxTableCellEditor;
import com.syswave.swing.table.renders.LookUpComboBoxTableCellRenderer;
import com.syswave.logicas.miempresa.MonedasBusinessLogic;
import com.syswave.logicas.miempresa.PersonaCreditoCuentasBusinessLogic;
import com.syswave.logicas.miempresa.TipoPersonasBusinessLogic;
import java.util.ArrayList;
import java.util.List;
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
public class JPersonaCreditoCuentaDataView extends JTableDataView implements IPersonaCreditoCuentaMediator, TableModelListener
{

    private final int opLOAD = 0;
    private final int opINSERT_LIST = 1;
    private final int opUPDATE_LIST = 2;
    private final int opDELETE_LIST = 3;
    private final int opINSERT = 4;
    private final int opUPDATE = 5;
    private final int opSAVE = 6;

    private boolean can_browse, can_create, can_update, can_delete;

    PersonaCreditoCuenta5FNTableModel bsPersonaCreditoCuentas;
    ValorComboBoxModel bsTipoCuentaEditor;
    ValorComboBoxModel bsTipoCuentaRender;
    MonedasComboBoxModel bsMonedasEditor;
    MonedasComboBoxModel bsMonedasRender;
    TipoPersonasComboBoxModel bsTipoPersonasRender;
    TipoPersonasComboBoxModel bsTipoPersonasEditor;

    PersonaCreditoCuentasBusinessLogic cuentas;
    MonedasBusinessLogic monedas;
    TipoPersonasBusinessLogic tipoPersonas;

    TableColumn colNombreCompleto, colTipoPersona, colClave, colNumero, colSaldo,
            colLimite, colMoneda, colTipo, colActivo,
            colNota;
    PersonaCreditoCuentaSwingWorker swSecondPlane;
    List<Usuario_tiene_Permiso> permisos;

    //---------------------------------------------------------------------
    private void grantAllPermisions()
    {
        can_browse = true;
        can_create = true;
        can_update = true;
        can_delete = true;
    }

    // -------------------------------------------------------------------
    public JPersonaCreditoCuentaDataView(IWindowContainer container)
    {
        super(container);
        initAttributes();
        grantAllPermisions();
        construirTiposCuenta();
    }

    // -------------------------------------------------------------------
    public JPersonaCreditoCuentaDataView(IWindowContainer container, List<Usuario_tiene_Permiso> values)
    {
        super(container);
        initAttributes();
        grant(values);
        construirTiposCuenta();
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

        bsPersonaCreditoCuentas.setReadOnly(!can_update);
    }

    private void initAttributes()
    {
        bsPersonaCreditoCuentas = new PersonaCreditoCuenta5FNTableModel(new String[]
        {
            "Nombre:{nombre_completo}", "Tipo:{id_tipo_persona}",
            "Numero:{numero}", "Saldo:{saldo_actual}", "Limite:{saldo_limite}",
            "Moneda:{id_moneda}", "Tipo:{es_tipo}", "Activo:{es_activo}",
            "Nota:{observacion}"
        });
        bsPersonaCreditoCuentas.addTableModelListener(this);
        cuentas = new PersonaCreditoCuentasBusinessLogic(mainContainer.getOrigenDatoActual().getNombre());
        monedas = new MonedasBusinessLogic(cuentas.getEsquema());
        tipoPersonas = new TipoPersonasBusinessLogic(cuentas.getEsquema());
        jtbData.setModel(bsPersonaCreditoCuentas);

        bsTipoCuentaEditor = new ValorComboBoxModel();
        bsTipoCuentaRender = new ValorComboBoxModel();
        bsMonedasEditor = new MonedasComboBoxModel();
        bsMonedasRender = new MonedasComboBoxModel();
        bsTipoPersonasRender = new TipoPersonasComboBoxModel();
        bsTipoPersonasEditor = new TipoPersonasComboBoxModel();

        jtbData.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        if (jtbData.getColumnCount() > 0)
        {
            colNombreCompleto = jtbData.getColumnModel().getColumn(0);
            colNombreCompleto.setPreferredWidth(200);

            colTipoPersona = jtbData.getColumnModel().getColumn(1);
            colTipoPersona.setCellRenderer(new LookUpComboBoxTableCellRenderer<TipoPersona>(bsTipoPersonasRender));
            colTipoPersona.setCellEditor(new LookUpComboBoxTableCellEditor<TipoPersona>(bsTipoPersonasEditor));
            colTipoPersona.setPreferredWidth(200);

            colNumero = jtbData.getColumnModel().getColumn(2);
            colNumero.setPreferredWidth(100);
            colSaldo = jtbData.getColumnModel().getColumn(3);
            colSaldo.setPreferredWidth(100);
            colLimite = jtbData.getColumnModel().getColumn(4);
            colLimite.setPreferredWidth(100);
            colMoneda = jtbData.getColumnModel().getColumn(5);
            colMoneda.setCellRenderer(new LookUpComboBoxTableCellRenderer<Moneda>(bsMonedasRender));
            colMoneda.setCellEditor(new LookUpComboBoxTableCellEditor<Moneda>(bsMonedasEditor));
            colMoneda.setPreferredWidth(100);
            colTipo = jtbData.getColumnModel().getColumn(6);
            colTipo.setCellRenderer(new LookUpComboBoxTableCellRenderer<Valor>(bsTipoCuentaRender));
            colTipo.setCellEditor(new LookUpComboBoxTableCellEditor<Valor>(bsTipoCuentaEditor));
            colTipo.setPreferredWidth(100);
            colActivo = jtbData.getColumnModel().getColumn(7);
            colActivo.setPreferredWidth(100);
            colNota = jtbData.getColumnModel().getColumn(8);
            colNota.setPreferredWidth(300);

            jtbData.setRowHeight((int) (jtbData.getRowHeight() * 1.5));
        }
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

    // -------------------------------------------------------------------
    @Override
    public void showDetail(PersonaCreditoCuenta_5FN elemento)
    {
        JPersonaCreditoCuentasDetailView dialogo = new JPersonaCreditoCuentasDetailView(this);
        mainContainer.addWindow(dialogo);

        if (elemento != null)
            dialogo.prepareForModify(elemento);

        else
            dialogo.prepareForNew();

        mainContainer.showCenter(dialogo);
    }

    // -------------------------------------------------------------------
    @Override
    public void onAcceptNewElement(PersonaCreditoCuenta_5FN nuevo)
    {
        if (swSecondPlane == null || swSecondPlane.isDone())
        {
            List<Object> parametros = new ArrayList<Object>();
            swSecondPlane = new PersonaCreditoCuentaSwingWorker();
            swSecondPlane.addPropertyChangeListener(this);
            parametros.add(opINSERT);
            parametros.add(nuevo);

            jbMessage.setText("Guardando...");
            swSecondPlane.execute(parametros);
        }

    }

    // -------------------------------------------------------------------
    @Override
    public void onAcceptModifyElement(PersonaCreditoCuenta_5FN modificado)
    {
        if (swSecondPlane == null || swSecondPlane.isDone())
        {
            List<Object> parametros = new ArrayList<Object>();
            swSecondPlane = new PersonaCreditoCuentaSwingWorker();
            swSecondPlane.addPropertyChangeListener(this);
            parametros.add(opUPDATE);
            parametros.add(modificado);

            jbMessage.setText("Guardando...");
            swSecondPlane.execute(parametros);
        }
    }

    // -------------------------------------------------------------------
    @Override
    public String getEsquema()
    {
        return mainContainer.getOrigenDatoActual().getNombre();
    }

    // -------------------------------------------------------------------
    @Override
    public List<Valor> obtenerTiposCuenta()
    {
        return bsTipoCuentaEditor.getData();
    }

    // -------------------------------------------------------------------
    @Override
    public List<Moneda> obtenerMonedas()
    {
        return bsMonedasEditor.getData();
    }

    // -------------------------------------------------------------------
    @Override
    public String obtenerTipoPersona(int id_tipo_persona)
    {
        return bsTipoPersonasEditor.getElementAt(bsTipoPersonasEditor.indexOfValue(id_tipo_persona)).getNombre();
    }

    //---------------------------------------------------------------------
    private void resetElement(PersonaCreditoCuenta value)
    {
        //value.acceptChanges();
        bsPersonaCreditoCuentas.fireTableRowsUpdated(jtbData.getSelectedRow(), jtbData.getSelectedRow());
        jbMessage.setText("Cambio guardado");
    }

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
                showDetail(bsPersonaCreditoCuentas.getElementAt(index));
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
                    swSecondPlane = new PersonaCreditoCuentaSwingWorker();
                    swSecondPlane.addPropertyChangeListener(this);
                    parametros.add(opDELETE_LIST);
                    int[] rowsHandlers = jtbData.getSelectedRows();
                    for (int i = 0; i < rowsHandlers.length; i++)
                    {
                        rowsHandlers[i] = jtbData.convertRowIndexToModel(rowsHandlers[i]);
                    }
                    parametros.add(bsPersonaCreditoCuentas.getElementsAt(rowsHandlers));
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
                swSecondPlane = new PersonaCreditoCuentaSwingWorker();
                swSecondPlane.addPropertyChangeListener(this);
                parametros.add(opLOAD);
                jbMessage.setText("Obteniendo cuentas...");
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
        List<PersonaCreditoCuenta> modificados = new ArrayList<>();
        List<PersonaCreditoCuenta> agregados = new ArrayList<>();
        List<PersonaCreditoCuenta_5FN> datos = bsPersonaCreditoCuentas.getData();

        TableCellEditor editor = jtbData.getCellEditor();
        if (editor != null)
            editor.stopCellEditing();

        for (PersonaCreditoCuenta_5FN elemento : datos)
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
                swSecondPlane = new PersonaCreditoCuentaSwingWorker();
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

    //-------------------------------------------------------------------
    @Override
    public void doOpenProcess()
    {
        if (can_update)
        {
            int index = jtbData.getSelectedRow();

            if (index >= 0)
            {
                index = jtbData.convertRowIndexToModel(index);
                showDetail(bsPersonaCreditoCuentas.getElementAt(index));
            }
        }
        else
            JOptionPane.showMessageDialog(this, "No cuenta con los permisos necesarios para ACTUALIZAR elementos");
    }

    // -------------------------------------------------------------------
    private void setDisplayData(List<PersonaCreditoCuenta_5FN> values, List<Moneda> listaMonedas, List<TipoPersona> listaTipos)
    {
        bsTipoPersonasEditor.setData(listaTipos);
        bsTipoPersonasRender.setData(listaTipos);

        bsMonedasEditor.setData(listaMonedas);
        bsMonedasRender.setData(listaMonedas);

        bsPersonaCreditoCuentas.setData(values);
        
         printRecordCount();
        
        jbMessage.setText("Cuentas obtenidas");
    }

    //---------------------------------------------------------------------
    private void setDisplayData(PersonaCreditoCuenta_5FN value)
    {
        bsPersonaCreditoCuentas.addRow(value);
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
    //--  Esta clase controla el cargado en segundo plano de los origenes de datos.
    //---------------------------------------------------------------------
    private class PersonaCreditoCuentaSwingWorker extends SwingWorker<List<Object>, Void>
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
                        results.add(cuentas.obtenerLista5FN());
                        results.add(monedas.obtenerLista());
                        results.add(tipoPersonas.obtenerListaHojas());
                        break;

                    case opINSERT:
                        arguments.add(cuentas.agregar((PersonaCreditoCuenta) arguments.get(1)));
                        /*arguments.add(direcciones.guardar((List<PersonaCreditoCuentaDireccion>)arguments.get(2), 
                         (List<PersonaCreditoCuentaDireccion>)arguments.get(3)));*/
                        break;

                    case opUPDATE:
                        arguments.add(cuentas.actualizar((PersonaCreditoCuenta) arguments.get(1)));
                        /*arguments.add(direcciones.guardar((List<PersonaCreditoCuentaDireccion>)arguments.get(2), 
                         (List<PersonaCreditoCuentaDireccion>)arguments.get(3)));*/
                        break;

                    case opDELETE_LIST:
                        List<PersonaCreditoCuenta> selecteds = (List<PersonaCreditoCuenta>) arguments.get(1);
                        cuentas.borrar(selecteds);
                        break;

                    case opSAVE:
                        List<PersonaCreditoCuenta> adds = (List<PersonaCreditoCuenta>) arguments.get(1);
                        List<PersonaCreditoCuenta> modifieds = (List<PersonaCreditoCuenta>) arguments.get(2);
                        if (adds.size() > 0)
                            arguments.add(cuentas.agregar(adds));
                        if (modifieds.size() > 0)
                            arguments.add(cuentas.actualizar(modifieds));
                        break;

                    default:
                        results.add(cuentas.obtenerLista5FN());
                        results.add(monedas.obtenerLista());
                        results.add(tipoPersonas.obtenerListaHojas());
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
                                List<Moneda> listaMonedas = (List<Moneda>) results.get(2);
                                List<TipoPersona> listaTipos = (List<TipoPersona>) results.get(3);
                                if (listaMonedas.size() > 0)
                                    setDisplayData((List<PersonaCreditoCuenta_5FN>) results.get(1), listaMonedas, listaTipos);
                                else if (!cuentas.esCorrecto())
                                    JOptionPane.showMessageDialog(null, cuentas.getMensaje());
                                break;

                            case opINSERT:
                                if (cuentas.esCorrecto())
                                    setDisplayData((PersonaCreditoCuenta_5FN) arguments.get(1));

                                else
                                    jbMessage.setText(cuentas.getMensaje());
                                break;

                            case opUPDATE:
                                if (cuentas.esCorrecto())
                                    resetElement((PersonaCreditoCuenta_5FN) arguments.get(1));

                                else
                                    jbMessage.setText(cuentas.getMensaje());
                                break;

                            case opDELETE_LIST:
                                if (cuentas.esCorrecto())
                                {
                                    bsPersonaCreditoCuentas.removeRows((int[]) arguments.get(2));
                                     printRecordCount();
                                
                                    jbMessage.setText("Elemento(s) eliminado(s)");
                                }
                                else
                                    jbMessage.setText(cuentas.getEsquema());
                                break;

                            case opSAVE:
                                jbMessage.setText(cuentas.getMensaje());
                                if (cuentas.esCorrecto())
                                    acceptChanges();
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
