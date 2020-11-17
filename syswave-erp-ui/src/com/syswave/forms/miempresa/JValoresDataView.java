package com.syswave.forms.miempresa;

import com.syswave.entidades.common.Entidad;
import com.syswave.entidades.configuracion.Usuario_tiene_Permiso;
import com.syswave.entidades.miempresa.Valor;
import com.syswave.forms.common.JTableDataView;
import com.syswave.swing.models.POJOTableModel;
import com.syswave.forms.databinding.ValorTableModel;
import com.syswave.logicas.miempresa.ValoresBusinessLogic;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellEditor;
import com.syswave.forms.common.IWindowContainer;

/**
 * Lista de valores según la sección solicitada.
 *
 * @author Victor Manuel Bucio Vargas
 */
public class JValoresDataView extends JTableDataView implements IValorMediator, TableModelListener
{

    private final int opLOAD = 0;
    private final int opINSERT_LIST = 1;
    private final int opUPDATE_LIST = 2;
    private final int opDELETE_LIST = 3;
    private final int opINSERT = 4;
    private final int opUPDATE = 5;
    private final int opSAVE = 6;

    private boolean can_browse, can_create, can_update, can_delete;

    private ValorTableModel bsValores;
    private ValoresBusinessLogic valores;
    private ValoresSwingWorker swSecondPlane;
    private List<Usuario_tiene_Permiso> permisos;
    private String default_seccion;   // "contratos.areas_negocio";

    //--------------------------------------------------------------------------
    public JValoresDataView(IWindowContainer container)
    {
        super(container);
        initAttributes(container);
        grantAllPermisions();
    }

    //--------------------------------------------------------------------------
    public JValoresDataView(IWindowContainer container, List<Usuario_tiene_Permiso> values)
    {
        super(container);
        initAttributes(container);
        grant(values);
    }

    //--------------------------------------------------------------------------
    public JValoresDataView(IWindowContainer container, String params, List<Usuario_tiene_Permiso> values)
    {
        super(container);
        initAttributes(container);
        grant(values);
        configure(params);
    }

    //---------------------------------------------------------------------
    /**
     * Este método sirve para asignar los valores provenientes de los
     * parametros.
     * @param params
     */
    public final void configure(String params)
    {
        String[] analyze = params.split("&");

        for (int i = 0; i < analyze.length; i++)
        {
            if (analyze[i].contains("s="))
                default_seccion = analyze[i].substring(analyze[i].indexOf("=") + 1);
        }
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

        bsValores.setReadOnly(!can_update);
    }

    //---------------------------------------------------------------------
    private void initAttributes(IWindowContainer container)
    {
        default_seccion = "sistema.tablas";
        valores = new ValoresBusinessLogic(container.getOrigenDatoActual().getNombre());
        bsValores = new ValorTableModel(new String[]
        {
            "Clave:{valor}",
            "Descripción:{descripcion}",
            "Activo:{es_activo}"
        });
        bsValores.addTableModelListener(this);
        jtbData.setModel(bsValores);
    }

    //--------------------------------------------------------------------
    private void grantAllPermisions()
    {
        can_browse = true;
        can_create = true;
        can_update = true;
        can_delete = true;
    }

    //--------------------------------------------------------------------------
    @Override
    public void doCreateProcess()
    {
        if (can_create)
            showDetail(null);
        else
            JOptionPane.showMessageDialog(this, "No cuenta con los permisos necesarios para INSERTAR elementos");
    }

    //--------------------------------------------------------------------------
    @Override
    public void doUpdateProcess()
    {
        if (can_update)
        {
            int index = jtbData.getSelectedRow();

            if (index >= 0)
            {
                index = jtbData.convertRowIndexToModel(index);
                showDetail(bsValores.getElementAt(index));
            }
        }
        else
            JOptionPane.showMessageDialog(this, "No cuenta con los permisos necesarios para ACTUALIZAR elementos");
    }

    //--------------------------------------------------------------------------
    @Override
    public void doDeleteProcess()
    {
        if (can_delete)
        {
           ArrayList algo;
            if (JOptionPane.showConfirmDialog(this, String.format("Ha seleccionado %d registro(s) para ser eleiminado(s)\n¿Esta seguro de continuar?", jtbData.getSelectedRowCount())) == JOptionPane.OK_OPTION)
                if (swSecondPlane == null || swSecondPlane.isDone())
                {
                    List<Object> parametros = new ArrayList<Object>();
                    jbMessage.setText("Eliminando elemento(s)");
                    swSecondPlane = new ValoresSwingWorker();
                    swSecondPlane.addPropertyChangeListener(this);
                    parametros.add(opDELETE_LIST);
                    int[] modelIndexes = jtbData.getSelectedRows();
                    int[] rowHandlenrs = Arrays.copyOf(modelIndexes, modelIndexes.length);
                    for (int i = 0; i < modelIndexes.length; i++)
                    {
                        modelIndexes[i] = jtbData.convertRowIndexToModel(modelIndexes[i]);
                    }
                    parametros.add(bsValores.getElementsAt(modelIndexes));
                    parametros.add(rowHandlenrs);
                    swSecondPlane.execute(parametros);
                }
        }
        else
            JOptionPane.showMessageDialog(this, "No cuenta con los permisos necesarios para eliminar elementos");
    }

    //--------------------------------------------------------------------------
    @Override
    public void doRetrieveProcess()
    {
        if (can_browse)
        {
            if (swSecondPlane == null || swSecondPlane.isDone())
            {
                List<Object> parametros = new ArrayList<Object>();
                jbMessage.setText("Obteniendo áreas de negocio...");
                swSecondPlane = new ValoresSwingWorker();
                swSecondPlane.addPropertyChangeListener(this);

                Valor filtro = new Valor();
                filtro.setSeccion(default_seccion);
                parametros.add(opLOAD);
                parametros.add(filtro);
                swSecondPlane.execute(parametros);
            }
        }
        else
            JOptionPane.showMessageDialog(this, "No cuenta con los permisos necesarios para acceder a la información");
    }

    //--------------------------------------------------------------------------
    @Override
    public void doSaveProcess()
    {
        int elementosTotales = 0;
        List<Valor> agregados = new ArrayList<>();
        List<Valor> modificados = new ArrayList<>();
        List<Valor> datos = bsValores.getData();

        TableCellEditor editor = jtbData.getCellEditor();
        if (editor != null)
            editor.stopCellEditing();

        for (Valor elemento : datos)
        {
            if (elemento.isNew())
                agregados.add(elemento);
            else if (elemento.isModified())
                modificados.add(elemento);
        }

        elementosTotales = modificados.size() + agregados.size();

        if (elementosTotales > 0)
            if (swSecondPlane == null || swSecondPlane.isDone())
            {
                List<Object> parametros = new ArrayList<Object>();
                swSecondPlane = new ValoresSwingWorker();
                swSecondPlane.addPropertyChangeListener(this);
                parametros.add(opSAVE);
                parametros.add(agregados);
                parametros.add(modificados);
                swSecondPlane.execute(parametros);
            }
            else
                JOptionPane.showMessageDialog(this, "No existen cambios a guardar");
    }

    //--------------------------------------------------------------------------
    @Override
    public void doOpenProcess()
    {
        if (can_update)
        {
            int index = jtbData.getSelectedRow();

            if (index >= 0)
            {
                index = jtbData.convertRowIndexToModel(index);
                showDetail(bsValores.getElementAt(index));
            }
        }
        else
            JOptionPane.showMessageDialog(this, "No cuenta con los permisos necesarios para ACTUALIZAR elementos");
    }

    //--------------------------------------------------------------------------
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

    //--------------------------------------------------------------------------
    @Override
    public void showDetail(Valor elemento)
    {
        JValoresDetailView dialogo = new JValoresDetailView(this, default_seccion);

        mainContainer.addWindow(dialogo);

        if (elemento != null)
            dialogo.prepareForModify(elemento);

        else
            dialogo.prepareForNew();
        mainContainer.showCenter(dialogo);
    }

    //--------------------------------------------------------------------------
    @Override
    public void onAcceptNewElement(Valor nuevo)
    {
        if (swSecondPlane == null || swSecondPlane.isDone())
        {
            List<Object> parametros = new ArrayList<Object>();
            swSecondPlane = new ValoresSwingWorker();
            swSecondPlane.addPropertyChangeListener(this);
            parametros.add(opINSERT);
            parametros.add(nuevo);
            swSecondPlane.execute(parametros);
        }
    }

    //--------------------------------------------------------------------------
    @Override
    public void onAcceptModifyElement(Valor modificado)
    {
        if (swSecondPlane == null || swSecondPlane.isDone())
        {
            List<Object> parametros = new ArrayList<Object>();
            swSecondPlane = new ValoresSwingWorker();
            swSecondPlane.addPropertyChangeListener(this);
            parametros.add(opUPDATE);
            parametros.add(modificado);
            swSecondPlane.execute(parametros);
        }
    }

    //--------------------------------------------------------------------------
    @Override
    public String obtenerOrigenDato()
    {
        return mainContainer.getOrigenDatoActual().getNombre();
    }

    //--------------------------------------------------------------------------
    public void setDisplayData(List<Valor> dataSource)
    {
        jbMessage.setText("Áreas de negocio obtenidas");
        bsValores.setData(dataSource);
        printRecordCount();

    }

    //--------------------------------------------------------------------------
    public void setDisplayData(Valor elemento)
    {
        jbMessage.setText("Nuevo agregado");
        bsValores.addRow(elemento);
        printRecordCount();
    }

    //--------------------------------------------------------------------
    private void resetCurrentElement()
    {
        jbMessage.setText("Cambio guardado");
        bsValores.fireTableRowsUpdated(jtbData.getSelectedRow(), jtbData.getSelectedRow());
    }

    //--------------------------------------------------------------------------
    private class ValoresSwingWorker extends SwingWorker<List<Object>, Void>
    {

        private List<Object> arguments;

        //--------------------------------------------------------------------------
        public void execute(List<Object> values)
        {
            arguments = values;
            execute();
        }

        //--------------------------------------------------------------------------
        @Override
        protected List<Object> doInBackground() throws Exception
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
                        arguments.add(valores.obtenerLista((Valor) arguments.get(1)));
                        break;
                    case opINSERT:
                        arguments.add(valores.agregar((Valor) arguments.get(1)));
                        break;
                    case opUPDATE:
                        arguments.add(valores.actualizar((Valor) arguments.get(1)));
                        break;
                    case opSAVE:
                        List<Valor> agregados = (List<Valor>) arguments.get(1);
                        List<Valor> modificados = (List<Valor>) arguments.get(2);
                        if (agregados.size() > 0)
                            results.add(valores.agregar(agregados));
                        if (modificados.size() > 0)
                            results.add(valores.actualizar(modificados));
                        break;
                    case opDELETE_LIST:
                        List<Valor> borrados = (List<Valor>) arguments.get(1);
                        results.add(valores.borrar(borrados));
                        break;
                }
                setProgress(100);
                return results;
            }
            else
                return null;
        }

        //--------------------------------------------------------------------
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
                                List<Valor> listValor = (List<Valor>) arguments.get(2);
                                setDisplayData(listValor);
                                break;
                            case opINSERT:
                                jbMessage.setText(valores.getMensaje());
                                if (valores.esCorrecto())
                                    setDisplayData((Valor) arguments.get(1));
                                break;
                            case opUPDATE:
                                jbMessage.setText(valores.getMensaje());
                                if (valores.esCorrecto())
                                    resetCurrentElement();
                                break;
                            case opSAVE:
                                jbMessage.setText(valores.getMensaje());
                                if (valores.esCorrecto())
                                    acceptChanges();
                                break;
                            case opDELETE_LIST:
                                if (valores.esCorrecto())
                                {
                                    bsValores.removeRows((int[]) arguments.get(2));
                                    printRecordCount();

                                    jbMessage.setText("Elemento(s) eliminado(s)");
                                }
                                else
                                    jbMessage.setText(valores.getMensaje());
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
                {
                    arguments.clear();
                }
            }
        }

    }
}
