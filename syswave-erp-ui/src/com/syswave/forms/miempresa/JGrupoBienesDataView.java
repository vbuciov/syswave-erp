package com.syswave.forms.miempresa;

import com.syswave.entidades.common.Entidad;
import com.syswave.entidades.configuracion.Usuario_tiene_Permiso;
import com.syswave.entidades.miempresa.Bien;
import com.syswave.entidades.miempresa.BienVariante;
import com.syswave.entidades.miempresa.Categoria;
import com.syswave.entidades.miempresa.Valor;
import com.syswave.forms.common.JTableDataView;
import com.syswave.forms.databinding.BienesTableModel;
import com.syswave.forms.databinding.CategoriasComboBoxModel;
import com.syswave.swing.models.POJOTableModel;
import com.syswave.forms.databinding.ValorComboBoxModel;
import com.syswave.swing.table.editors.LookUpComboBoxTableCellEditor;
import com.syswave.swing.table.renders.LookUpComboBoxTableCellRenderer;
import com.syswave.logicas.miempresa.BienVariantesBusinessLogic;
import com.syswave.logicas.miempresa.BienesBusinessLogic;
import com.syswave.logicas.miempresa.CategoriasBusinessLogic;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import com.syswave.forms.common.IWindowContainer;

/**
 *
 * @author Victor Manuel Bucio Vargas.
 */
public class JGrupoBienesDataView extends JTableDataView implements ITipoBienMediator, TableModelListener
{

    private final int opLOAD = 0;
    private final int opINSERT_LIST = 1;
    private final int opUPDATE_LIST = 2;
    private final int opDELETE_LIST = 3;
    private final int opINSERT = 4;
    private final int opUPDATE = 5;
    private final int opSAVE = 6;

    private boolean can_browse, can_create, can_update, can_delete;

    CategoriasComboBoxModel bsCategoriasRender;
    CategoriasComboBoxModel bsCategoriasEditor;
    ValorComboBoxModel bsTiposRender;
    ValorComboBoxModel bsTiposEditor;

    BienesTableModel bsBienes;
    BienesBusinessLogic bienes;
    CategoriasBusinessLogic categorias;
    BienVariantesBusinessLogic variantes;

    TableColumn colCategoria;
    TableColumn colTipo;
    BienSwingWorker swSecondPlane;
    List<Usuario_tiene_Permiso> permisos;
    String esquema;

    //---------------------------------------------------------------------
    private void grantAllPermisions()
    {
        can_browse = true;
        can_create = true;
        can_update = true;
        can_delete = true;
    }

    // -------------------------------------------------------------------
    public JGrupoBienesDataView(IWindowContainer container)
    {
        super(container);
        initAttributes();
        grantAllPermisions();
    }

    // -------------------------------------------------------------------
    public JGrupoBienesDataView(IWindowContainer container, List<Usuario_tiene_Permiso> values)
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

        bsBienes.setReadOnly(!can_update);
    }

    private void initAttributes()
    {
        esquema = mainContainer.getOrigenDatoActual().getNombre();
        bsBienes = new BienesTableModel(new String[]
        {
            "Categoria:{id_categoria}", "Nombre:{nombre}", "Tipo:{es_tipo}"
        });
        bsBienes.addTableModelListener(this);
        bsCategoriasRender = new CategoriasComboBoxModel();
        bsCategoriasEditor = new CategoriasComboBoxModel();
        bsTiposRender = new ValorComboBoxModel();
        bsTiposEditor = new ValorComboBoxModel();
        bienes = new BienesBusinessLogic(esquema);
        categorias = new CategoriasBusinessLogic(bienes.getEsquema());
        variantes = new BienVariantesBusinessLogic(bienes.getEsquema());
        jtbData.setModel(bsBienes);

        if (jtbData.getColumnCount() > 0)
        {
            colTipo = jtbData.getColumnModel().getColumn(2);
            bsTiposRender.addElement(new Valor(0, "Bien"));
            bsTiposRender.addElement(new Valor(1, "Servicio"));
            bsTiposEditor.setData(bsTiposRender.getData());
            colTipo.setCellRenderer(new LookUpComboBoxTableCellRenderer<Categoria>(bsTiposRender));
            colTipo.setCellEditor(new LookUpComboBoxTableCellEditor<Valor>(bsTiposEditor));

            colCategoria = jtbData.getColumnModel().getColumn(0);
            colCategoria.setCellRenderer(new LookUpComboBoxTableCellRenderer<Categoria>(bsCategoriasRender));
            colCategoria.setCellEditor(new LookUpComboBoxTableCellEditor<Categoria>(bsCategoriasEditor));

            //Nota: Debido a los renders que se estan utilizando es necesario tener un renglón más alto.
            jtbData.setRowHeight((int) (jtbData.getRowHeight() * 1.5));
        }
    }

    // -------------------------------------------------------------------
    @Override
    public void showDetail(Bien elemento)
    {
        if (bsCategoriasEditor.getData().size() > 0)
        {
            JGrupoBienesDetailView dialogo = new JGrupoBienesDetailView(this);
            mainContainer.addWindow(dialogo);

            if (elemento != null)
                dialogo.prepareForModify(elemento);

            else
                dialogo.prepareForNew();

            mainContainer.showCenter(dialogo);
        }

        else
            JOptionPane.showMessageDialog(this, "Para poder continuar es necesario tener categorías especificadas", "Información", JOptionPane.WARNING_MESSAGE);
    }

    // -------------------------------------------------------------------
    @Override
    public void onAcceptNewElement(Bien nuevo, List<BienVariante> variantes, List<BienVariante> variantes_borradas)
    {
        if (swSecondPlane == null || swSecondPlane.isDone())
        {
            List<Object> parametros = new ArrayList<Object>();
            swSecondPlane = new BienSwingWorker();
            swSecondPlane.addPropertyChangeListener(this);
            parametros.add(opINSERT);
            parametros.add(nuevo);
            parametros.add(variantes);
            parametros.add(variantes_borradas);

            jbMessage.setText("Guardando...");
            swSecondPlane.execute(parametros);
        }

    }

    // -------------------------------------------------------------------
    @Override
    public void onAcceptModifyElement(Bien modificado, List<BienVariante> variantes, List<BienVariante> variantes_borradas)
    {
        if (swSecondPlane == null || swSecondPlane.isDone())
        {
            List<Object> parametros = new ArrayList<Object>();
            swSecondPlane = new BienSwingWorker();
            swSecondPlane.addPropertyChangeListener(this);
            parametros.add(opUPDATE);
            parametros.add(modificado);
            parametros.add(variantes);
            parametros.add(variantes_borradas);

            jbMessage.setText("Guardando...");
            swSecondPlane.execute(parametros);
        }
    }

    //---------------------------------------------------------------------
    private void resetElement(Bien value)
    {
        //value.acceptChanges();
        bsBienes.fireTableRowsUpdated(jtbData.getSelectedRow(), jtbData.getSelectedRow());
        jbMessage.setText("Cambio guardado");
    }

    // -------------------------------------------------------------------
    @Override
    public String obtenerOrigenDato()
    {
        return esquema;
    }

    // -------------------------------------------------------------------
    @Override
    public List<Valor> obtenerTipos()
    {
        return bsTiposEditor.getData();
    }

    // -------------------------------------------------------------------
    @Override
    public List<Categoria> obtenerCategorias()
    {
        return bsCategoriasEditor.getData();
    }

    // -------------------------------------------------------------------
    @Override
    public List<BienVariante> obtenerVariantes(Bien elemento)
    {
        BienVariante filtro = new BienVariante();
        filtro.setIdBien(elemento.getId());
        filtro.acceptChanges(); //Don't use boolean flags.

        return variantes.obtenerLista(filtro);
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
                showDetail(bsBienes.getElementAt(index));
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
                    swSecondPlane = new BienSwingWorker();
                    swSecondPlane.addPropertyChangeListener(this);
                    parametros.add(opDELETE_LIST);
                    int[] rowsHandlers = jtbData.getSelectedRows();
                    for (int i = 0; i < rowsHandlers.length; i++)
                    {
                        rowsHandlers[i] = jtbData.convertRowIndexToModel(rowsHandlers[i]);
                    }
                    parametros.add(bsBienes.getElementsAt(rowsHandlers));
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
                swSecondPlane = new BienSwingWorker();
                swSecondPlane.addPropertyChangeListener(this);
                parametros.add(opLOAD);
                jbMessage.setText("Consultando información...");
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
        List<Bien> modificados = new ArrayList<>();
        List<Bien> agregados = new ArrayList<>();
        List<Bien> datos = bsBienes.getData();

        TableCellEditor editor = jtbData.getCellEditor();
        if (editor != null)
            editor.stopCellEditing();

        for (Bien elemento : datos)
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
                swSecondPlane = new BienSwingWorker();
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
                showDetail(bsBienes.getElementAt(index));
            }
        }
        else
            JOptionPane.showMessageDialog(this, "No cuenta con los permisos necesarios para ACTUALIZAR elementos");
    }

    // -------------------------------------------------------------------
    private void setDisplayData(List<Categoria> categorias, List<Bien> values)
    {
        bsCategoriasRender.setData(categorias);
        bsCategoriasEditor.setData(categorias);
        bsBienes.setData(values);

        printRecordCount();
        jbMessage.setText("Información obtenida");
    }

    //---------------------------------------------------------------------
    private void setDisplayData(Bien value)
    {
        //value.acceptChanges();
        bsBienes.addRow(value);
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
    private class BienSwingWorker extends SwingWorker<List<Object>, Void>
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
                        results.add(bienes.obtenerLista());
                        results.add(categorias.obtenerListaHojas());
                        break;

                    case opINSERT:
                        arguments.add(bienes.agregar((Bien) arguments.get(1)));
                        arguments.add(variantes.guardar((List<BienVariante>) arguments.get(2),
                                (List<BienVariante>) arguments.get(3)));
                        break;

                    case opUPDATE:
                        arguments.add(bienes.actualizar((Bien) arguments.get(1)));
                        arguments.add(variantes.guardar((List<BienVariante>) arguments.get(2),
                                (List<BienVariante>) arguments.get(3)));
                        break;

                    case opDELETE_LIST:
                        List<Bien> selecteds = (List<Bien>) arguments.get(1);
                        bienes.borrar(selecteds);
                        break;

                    case opSAVE:
                        List<Bien> adds = (List<Bien>) arguments.get(1);
                        List<Bien> modifieds = (List<Bien>) arguments.get(2);
                        if (adds.size() > 0)
                            arguments.add(bienes.agregar(adds));
                        if (modifieds.size() > 0)
                            arguments.add(bienes.actualizar(modifieds));
                        break;

                    default:
                        results.add(bienes.obtenerLista());
                        results.add(categorias.obtenerLista());
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
                                List<Bien> listaBienes = (List<Bien>) results.get(1);
                                List<Categoria> listaTipos = (List<Categoria>) results.get(2);
                                if (listaTipos.size() > 0)
                                    setDisplayData(listaTipos, listaBienes);
                                else if (!bienes.esCorrecto())
                                    JOptionPane.showMessageDialog(null, bienes.getMensaje());
                                break;

                            case opINSERT:
                                if (bienes.esCorrecto())
                                    setDisplayData((Bien) arguments.get(1));

                                else
                                    jbMessage.setText(bienes.getMensaje());
                                break;

                            case opUPDATE:
                                if (bienes.esCorrecto())
                                    resetElement((Bien) arguments.get(1));

                                else
                                    jbMessage.setText(bienes.getMensaje());
                                break;

                            case opDELETE_LIST:
                                if (bienes.esCorrecto())
                                {
                                    bsBienes.removeRows((int[]) arguments.get(2));
                                    printRecordCount();
                                
                                    jbMessage.setText("Elemento(s) eliminado(s)");
                                }
                                else
                                    jbMessage.setText(bienes.getEsquema());
                                break;

                            case opSAVE:
                                jbMessage.setText(bienes.getMensaje());
                                if (bienes.esCorrecto())
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
