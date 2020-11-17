package com.syswave.forms.miempresa;

import com.syswave.entidades.common.Entidad;
import com.syswave.entidades.configuracion.Usuario_tiene_Permiso;
import com.syswave.entidades.miempresa.Moneda;
import com.syswave.entidades.miempresa.Persona;
import com.syswave.entidades.miempresa.PersonaSalario;
import com.syswave.entidades.miempresa.SalarioComponente;
import com.syswave.entidades.miempresa.Valor;
import com.syswave.forms.common.JTableDataView;
import com.syswave.swing.models.POJOTableModel;
import com.syswave.forms.databinding.MonedasComboBoxModel;
import com.syswave.forms.databinding.PersonaSalarioTableModel;
import com.syswave.forms.databinding.PersonasComboBoxModel;
import com.syswave.forms.databinding.ValorComboBoxModel;
import com.syswave.swing.table.editors.LookUpComboBoxTableCellEditor;
import com.syswave.swing.table.renders.LookUpComboBoxTableCellRenderer;
import com.syswave.logicas.miempresa.MonedasBusinessLogic;
import com.syswave.logicas.miempresa.PersonaSalarioBusinessLogic;
import com.syswave.logicas.miempresa.PersonasBusinessLogic;
import com.syswave.logicas.miempresa.SalarioComponenteBusinessLogic;
import com.toedter.calendar.JDateChooserCellEditor;
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
 * @author sis5
 */
public class JPersonaSalariosDataView extends JTableDataView implements TableModelListener, IPersonaSalarioMediator
{

    private final int opLOAD = 0;
    private final int opINSERT_LIST = 1;
    private final int opUPDATE_LIST = 2;
    private final int opDELETE_LIST = 3;
    private final int opINSERT = 4;
    private final int opUPDATE = 5;
    private final int opSAVE = 6;

    private boolean can_browse, can_create, can_update, can_delete;

    PersonaSalarioBusinessLogic salario;
    PersonasBusinessLogic persona;
    MonedasBusinessLogic moneda;
    SalarioComponenteBusinessLogic componentes;

    PersonaSalarioTableModel bsSalario;
    PersonasComboBoxModel bsPersonasRender;
    PersonasComboBoxModel bsPersonasEditor;
    MonedasComboBoxModel bsMonedasRender;
    MonedasComboBoxModel bsMonedasEditor;
    ValorComboBoxModel bsFrecuenciaRender;
    ValorComboBoxModel bsFrecuenciaEditor;

    TableColumn colPersona, colFecha, colMoneda, colFrecuencia;

    SwingPersonaSalario swSecondPlane;
    List<Usuario_tiene_Permiso> permisos;

    //--------------------------------------------------------------------
    public JPersonaSalariosDataView(IWindowContainer container)
    {
        super(container);
        initAtributes();
        grantAllPermisions();
    }

    //--------------------------------------------------------------------
    public JPersonaSalariosDataView(IWindowContainer container, List<Usuario_tiene_Permiso> values)
    {
        super(container);
        initAtributes();
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

        bsSalario.setReadOnly(!can_update);
    }

    private void initAtributes()
    {
        salario = new PersonaSalarioBusinessLogic();
        salario.setEsquema(mainContainer.getOrigenDatoActual().getNombre());
        persona = new PersonasBusinessLogic();
        persona.setEsquema(salario.getEsquema());
        moneda = new MonedasBusinessLogic();
        moneda.setEsquema(salario.getEsquema());
        componentes = new SalarioComponenteBusinessLogic();
        componentes.setEsquema(salario.getEsquema());

        bsSalario = new PersonaSalarioTableModel(new String[]
        {
            "Persona:{id_persona}", "Fecha vigor:{fecha_vigor}",
            "Sueldo neto:{sueldo_neto}", "Moneda:{id_moneda}", "Frecuencia:{frecuencia}"
        });
        bsSalario.addTableModelListener(this);
        jtbData.setModel(bsSalario);

        bsPersonasRender = new PersonasComboBoxModel();
        bsPersonasEditor = new PersonasComboBoxModel();
        bsMonedasRender = new MonedasComboBoxModel();
        bsMonedasEditor = new MonedasComboBoxModel();
        bsFrecuenciaRender = new ValorComboBoxModel();
        bsFrecuenciaEditor = new ValorComboBoxModel();

        if (jtbData.getColumnCount() > 0)
        {
            colPersona = jtbData.getColumnModel().getColumn(0);
            colPersona.setCellRenderer(new LookUpComboBoxTableCellRenderer<Persona>(bsPersonasRender));
            colPersona.setCellEditor(new LookUpComboBoxTableCellEditor<Persona>(bsPersonasEditor));

            colFecha = jtbData.getColumnModel().getColumn(1);
            colFecha.setCellEditor(new JDateChooserCellEditor());
            colFecha.setPreferredWidth(150);

            colMoneda = jtbData.getColumnModel().getColumn(3);
            colMoneda.setCellRenderer(new LookUpComboBoxTableCellRenderer<Moneda>(bsMonedasRender));
            colMoneda.setCellEditor(new LookUpComboBoxTableCellEditor<Moneda>(bsMonedasEditor));

            colFrecuencia = jtbData.getColumnModel().getColumn(4);
            colFrecuencia.setCellRenderer(new LookUpComboBoxTableCellRenderer<Moneda>(bsFrecuenciaRender));
            colFrecuencia.setCellEditor(new LookUpComboBoxTableCellEditor<Moneda>(bsFrecuenciaEditor));

            //Nota: Debido a los renders que se estan utilizando es necesario tener un renglón más alto.
            jtbData.setRowHeight((int) (jtbData.getRowHeight() * 1.5));
        }
    }

    //---------------------------------------------------------------------
    private void grantAllPermisions()
    {
        can_browse = true;
        can_create = true;
        can_update = true;
        can_delete = true;
    }

    private void crearFrecuencia()
    {
        List<Valor> SonTipos = new ArrayList<>();

        SonTipos.add(new Valor(0, "Semanal"));
        SonTipos.add(new Valor(1, "Quincenal"));
        SonTipos.add(new Valor(2, "Mensual"));
        SonTipos.add(new Valor(3, "Anual"));

        bsFrecuenciaRender.setData(SonTipos);
        bsFrecuenciaEditor.setData(SonTipos);
    }

    private void showDetail(PersonaSalario elemento)
    {
        JPersonaSalariosDetailView dialogo = new JPersonaSalariosDetailView(this);

        mainContainer.addWindow(dialogo);

        if (elemento != null)
            dialogo.prepareForModify(elemento);

        else
            dialogo.prepareForNew();

        mainContainer.showCenter(dialogo);
    }

    @Override
    public void doCreateProcess()
    {
        if (can_create)
            showDetail(null);
        else
            JOptionPane.showMessageDialog(this, "No cuenta con los permisos necesarios para INSERTAR elementos");
    }

    @Override
    public void doUpdateProcess()
    {
        if (can_update)
        {
            int index = jtbData.getSelectedRow();

            if (index >= 0)
            {
                index = jtbData.convertRowIndexToModel(index);
                showDetail(bsSalario.getElementAt(index));
            }
        }

        else
            JOptionPane.showMessageDialog(this, "No cuenta con los permisos necesarios para ACTUALIZAR elementos");
    }

    @Override
    public void doDeleteProcess()
    {
        if (can_delete)
        {
            if (JOptionPane.showConfirmDialog(this, String.format("Ha seleccionado %d registro(s) para ser eleiminado(s)\n¿Esta seguro de continuar?", jtbData.getSelectedRowCount())) == JOptionPane.OK_OPTION)
                if (swSecondPlane == null || swSecondPlane.isDone())
                {
                    List<Object> parametros = new ArrayList<Object>();
                    jbMessage.setText("Eliminando elemento(s)...");
                    swSecondPlane = new SwingPersonaSalario();
                    parametros.add(opDELETE_LIST);
                    int[] rowsHandlers = jtbData.getSelectedRows();
                    for (int i = 0; i < rowsHandlers.length; i++)
                    {
                        rowsHandlers[i] = jtbData.convertRowIndexToModel(rowsHandlers[i]);
                    }
                    parametros.add(bsSalario.getElementsAt(rowsHandlers));
                    parametros.add(rowsHandlers);
                    swSecondPlane.execute(parametros);
                }
        }
        else
            JOptionPane.showMessageDialog(this, "No cuenta con los permisos necesarios para eliminar elementos");
    }

    @Override
    public void doRetrieveProcess()
    {
        if (can_browse)
        {
            if (swSecondPlane == null || swSecondPlane.isDone())
            {
                List<Object> parametros = new ArrayList<Object>();
                jbMessage.setText("Consultado salarios...");
                swSecondPlane = new SwingPersonaSalario();
                parametros.add(opLOAD);
                swSecondPlane.execute(parametros);
            }
        }
        else
            JOptionPane.showMessageDialog(this, "No cuenta con los permisos necesarios para acceder a la información");

    }

    @Override
    public void doSaveProcess()
    {
        int elementosTotales = 0;
        List<PersonaSalario> agregados = new ArrayList<>();
        List<PersonaSalario> modificados = new ArrayList<>();
        List<PersonaSalario> datos = bsSalario.getData();

        TableCellEditor editor = jtbData.getCellEditor();
        if (editor != null)
            editor.stopCellEditing();

        for (PersonaSalario elemento : datos)
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
                swSecondPlane = new SwingPersonaSalario();
                parametros.add(opSAVE);
                parametros.add(agregados);
                parametros.add(modificados);
                swSecondPlane.execute(parametros);
            }
            else
                JOptionPane.showMessageDialog(this, "No existen cambios a guardar");
    }

    @Override
    public void doOpenProcess()
    {
        if (can_update)
        {
            int index = jtbData.getSelectedRow();

            if (index >= 0)
            {
                index = jtbData.convertRowIndexToModel(index);
                showDetail(bsSalario.getElementAt(index));
            }
        }

        else
            JOptionPane.showMessageDialog(this, "No cuenta con los permisos necesarios para ACTUALIZAR elementos");
    }

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

    @Override
    public void onAcceptModifyElement(PersonaSalario elemento, List<SalarioComponente> nuevosComponentes, List<SalarioComponente> eliminados)
    {
        if (swSecondPlane == null || swSecondPlane.isDone())
        {
            List<Object> parametros = new ArrayList<Object>();
            jbMessage.setText("Agregando nuevo salario...");
            swSecondPlane = new SwingPersonaSalario();
            parametros.add(opUPDATE);
            parametros.add(elemento);
            parametros.add(nuevosComponentes);
            parametros.add(eliminados);
            swSecondPlane.execute(parametros);
        }
    }

    @Override
    public void onAcceptNewElement(PersonaSalario nuevo, List<SalarioComponente> nuevosComponentes)
    {
        if (swSecondPlane == null || swSecondPlane.isDone())
        {
            List<Object> parametros = new ArrayList<Object>();
            swSecondPlane = new SwingPersonaSalario();
            parametros.add(opINSERT);
            parametros.add(nuevo);
            parametros.add(nuevosComponentes);
            swSecondPlane.execute(parametros);
        }
    }

    @Override
    public List<Persona> obtenerListaPersonas()
    {
        return persona.obtenerLista();
    }

    @Override
    public List<Moneda> obtenerListaMonedas()
    {
        return moneda.obtenerLista();
    }

    private void setDisplayData(List<PersonaSalario> dataSource, List<Persona> listPersonas, List<Moneda> listMonedas)
    {
        bsPersonasRender.setData(listPersonas);
        bsPersonasEditor.setData(listPersonas);
        bsMonedasRender.setData(listMonedas);
        bsMonedasEditor.setData(listMonedas);
        crearFrecuencia();
        bsSalario.setData(dataSource);
        printRecordCount();
        jbMessage.setText("Salarios obtenidos");
    }

    private void setDisplayData(PersonaSalario nuevo)
    {
        //crearFrecuencia();
        bsSalario.addRow(nuevo);
        jbMessage.setText("Nuevo salario agregado");
        printRecordCount();
    }

    //--------------------------------------------------------------------
    private void resetCurrentElement()
    {
        jbMessage.setText("Cambio guardado");
        bsSalario.fireTableRowsUpdated(jtbData.getSelectedRow(), jtbData.getSelectedRow());
    }

    @Override
    public List<SalarioComponente> obtenerSalarioComponentes(SalarioComponente filtro)
    {
        return componentes.obtenerLista(filtro);
    }

    /*
     ============================================================================
     */
    private class SwingPersonaSalario extends SwingWorker<List<Object>, Void>
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
                switch (opcion)
                {
                    case opLOAD:
                        arguments.add(salario.obtenerLista());
                        arguments.add(persona.obtenerLista());
                        arguments.add(moneda.obtenerLista());
                        break;

                    case opINSERT:
                        arguments.add(salario.agregar((PersonaSalario) arguments.get(1)));
                        arguments.add(componentes.guardar((List<SalarioComponente>) arguments.get(2), null));
                        break;

                    case opUPDATE:
                        arguments.add(salario.actualizar((PersonaSalario) arguments.get(1)));
                        arguments.add(componentes.guardar((List<SalarioComponente>) arguments.get(2), (List<SalarioComponente>) arguments.get(3)));
                        break;

                    case opDELETE_LIST:
                        List<PersonaSalario> borrados = (List<PersonaSalario>) arguments.get(1);
                        arguments.add(salario.borrar(borrados));
                        break;

                    case opSAVE:
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

                    if (results.size() > 0)
                    {
                        int opcion = (int) results.get(0);

                        switch (opcion)
                        {
                            case opLOAD:
                                List<PersonaSalario> listaSalarios = (List<PersonaSalario>) results.get(1);
                                List<Persona> listPersona = (List<Persona>) arguments.get(2);
                                List<Moneda> listMoneda = (List<Moneda>) arguments.get(3);
                                setDisplayData(listaSalarios, listPersona, listMoneda);
                                break;

                            case opINSERT:
                                if (salario.esCorrecto())
                                    setDisplayData((PersonaSalario) arguments.get(1));
                                break;

                            case opUPDATE:
                                if (salario.esCorrecto() && componentes.esCorrecto())
                                    resetCurrentElement();
                                else
                                    jbMessage.setText(salario.getMensaje() + " " + componentes.getMensaje());
                                break;

                            case opDELETE_LIST:
                                if (salario.esCorrecto())
                                {
                                    bsSalario.removeRows((int[]) arguments.get(2));
                                    printRecordCount();
                                    jbMessage.setText("Elemento(s) eliminado(s)");
                                }
                                else
                                    jbMessage.setText(salario.getEsquema());
                                break;

                            case opSAVE:
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
