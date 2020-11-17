package com.syswave.forms.miempresa;

import com.syswave.entidades.common.Entidad;
import com.syswave.entidades.configuracion.Usuario_tiene_Permiso;
import com.syswave.entidades.miempresa.Persona;
import com.syswave.entidades.miempresa.PersonaCreditoCuenta;
import com.syswave.entidades.miempresa.PersonaDireccion;
import com.syswave.entidades.miempresa.PersonaFoto;
import com.syswave.entidades.miempresa.PersonaIdentificador;
import com.syswave.entidades.miempresa.TipoPersona;
import com.syswave.entidades.miempresa.Valor;
import com.syswave.entidades.miempresa_vista.PersonaIdentificadorVista;
import com.syswave.forms.common.JTableDataView;
import com.syswave.swing.models.POJOTableModel;
import com.syswave.forms.databinding.PersonaTableModel;
import com.syswave.forms.databinding.TipoPersonasComboBoxModel;
import com.syswave.swing.table.editors.LookUpComboBoxTableCellEditor;
import com.syswave.swing.table.renders.LookUpComboBoxTableCellRenderer;
import com.syswave.logicas.miempresa.PersonaCreditoCuentasBusinessLogic;
import com.syswave.logicas.miempresa.PersonaDireccionesBusinessLogic;
import com.syswave.logicas.miempresa.PersonaFotosBusinessLogic;
import com.syswave.logicas.miempresa.PersonaIdentificadoresBusinessLogic;
import com.syswave.logicas.miempresa.PersonasBusinessLogic;
import com.syswave.logicas.miempresa.TipoPersonasBusinessLogic;
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
 * @author Victor Manuel Bucio Vargas.
 */
public class JPersonaDataView extends JTableDataView implements IPersonaMediator, TableModelListener
{

    private final int opLOAD = 0;
    private final int opINSERT_LIST = 1;
    private final int opUPDATE_LIST = 2;
    private final int opDELETE_LIST = 3;
    private final int opINSERT = 4;
    private final int opUPDATE = 5;
    private final int opSAVE = 6;

    private boolean can_browse, can_create, can_update, can_delete;

    TipoPersonasComboBoxModel bsTipoPersonasRender;
    TipoPersonasComboBoxModel bsTipoPersonasEditor;

    PersonaTableModel bsPersonas;
    PersonasBusinessLogic personas;
    TipoPersonasBusinessLogic tipoPersonas;
    PersonaDireccionesBusinessLogic direcciones;
    PersonaIdentificadoresBusinessLogic identificadores;
    PersonaCreditoCuentasBusinessLogic cuentas;
    PersonaFotosBusinessLogic fotos;

    TableColumn colTipoPersona;
    TableColumn colFechaNacimiento;
    PersonaSwingWorker swSecondPlane;
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
    public JPersonaDataView(IWindowContainer container)
    {
        super(container);
        initAttributes();
        grantAllPermisions();
    }

    // -------------------------------------------------------------------
    public JPersonaDataView(IWindowContainer container, List<Usuario_tiene_Permiso> values)
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

        bsPersonas.setReadOnly(!can_update);
    }

    private void initAttributes()
    {
        bsPersonas = new PersonaTableModel(new String[]
        {
            "Nombre(s):{nombres}", "Apellido(s):{apellidos}", "Nacimiento:{nacimiento}",
            "Relación de negocio:{id_tipo_persona}", "Activo:{es_activo}"
        });
        bsPersonas.addTableModelListener(this);
        bsTipoPersonasRender = new TipoPersonasComboBoxModel();
        bsTipoPersonasEditor = new TipoPersonasComboBoxModel();
        personas = new PersonasBusinessLogic(mainContainer.getOrigenDatoActual().getNombre());
        tipoPersonas = new TipoPersonasBusinessLogic(personas.getEsquema());
        direcciones = new PersonaDireccionesBusinessLogic(personas.getEsquema());
        identificadores = new PersonaIdentificadoresBusinessLogic(personas.getEsquema());
        cuentas = new PersonaCreditoCuentasBusinessLogic(personas.getEsquema());
        fotos = new PersonaFotosBusinessLogic(personas.getEsquema());
        jtbData.setModel(bsPersonas);

        if (jtbData.getColumnCount() > 0)
        {
            colFechaNacimiento = jtbData.getColumnModel().getColumn(2);
            colFechaNacimiento.setCellEditor(new JDateChooserCellEditor());

            colTipoPersona = jtbData.getColumnModel().getColumn(3);
            colTipoPersona.setCellRenderer(new LookUpComboBoxTableCellRenderer<TipoPersona>(bsTipoPersonasRender));
            colTipoPersona.setCellEditor(new LookUpComboBoxTableCellEditor<TipoPersona>(bsTipoPersonasEditor));
            colTipoPersona.setPreferredWidth(200);

            //Nota: Debido a los renders que se estan utilizando es necesario tener un renglón más alto.
            jtbData.setRowHeight((int) (jtbData.getRowHeight() * 1.5));
        }
    }

    // -------------------------------------------------------------------
    @Override
    public void showDetail(Persona elemento)
    {
        JPersonaDetailView dialogo = new JPersonaDetailView(this);
        mainContainer.addWindow(dialogo);

        if (elemento != null)
            dialogo.prepareForModify(elemento);

        else
            dialogo.prepareForNew();

        mainContainer.showCenter(dialogo);
    }

    // -------------------------------------------------------------------
    @Override
    public void onAcceptNewElement(Persona nuevo, List<PersonaDireccion> direcciones,
            List<PersonaIdentificadorVista> lstIdentificadores,
            List<PersonaIdentificador> lstMedios,
            List<PersonaCreditoCuenta> lstCuentas,
            PersonaFoto foto)
    {
        if (swSecondPlane == null || swSecondPlane.isDone())
        {
            List<Object> parametros = new ArrayList<Object>();
            swSecondPlane = new PersonaSwingWorker();
            swSecondPlane.addPropertyChangeListener(this);
            parametros.add(opINSERT);
            parametros.add(nuevo);
            parametros.add(direcciones);
            parametros.add(lstIdentificadores);
            parametros.add(lstMedios);
            parametros.add(lstCuentas);
            parametros.add(foto);

            jbMessage.setText("Guardando...");
            swSecondPlane.execute(parametros);
        }

    }

    // -------------------------------------------------------------------
    @Override
    public void onAcceptModifyElement(Persona modificado,
            List<PersonaDireccion> direcciones,
            List<PersonaDireccion> direcciones_borradas,
            List<PersonaIdentificadorVista> lstIdentificadores,
            List<PersonaIdentificador> lstMedios,
            List<PersonaIdentificador> lstMedios_borrados,
            List<PersonaCreditoCuenta> lstCuentas,
            List<PersonaCreditoCuenta> lstCuentas_borrados,
            PersonaFoto foto,
            PersonaFoto foto_borrada)
    {
        if (swSecondPlane == null || swSecondPlane.isDone())
        {
            List<Object> parametros = new ArrayList<Object>();
            swSecondPlane = new PersonaSwingWorker();
            swSecondPlane.addPropertyChangeListener(this);
            parametros.add(opUPDATE);
            parametros.add(modificado);
            parametros.add(direcciones);
            parametros.add(direcciones_borradas);
            parametros.add(lstIdentificadores);
            parametros.add(lstMedios);
            parametros.add(lstMedios_borrados);
            parametros.add(lstCuentas);
            parametros.add(lstCuentas_borrados);
            parametros.add(foto);
            parametros.add(foto_borrada);

            jbMessage.setText("Guardando...");
            swSecondPlane.execute(parametros);
        }
    }

    //---------------------------------------------------------------------
    private void resetElement(Persona value)
    {
        //value.acceptChanges();
        bsPersonas.fireTableRowsUpdated(jtbData.getSelectedRow(), jtbData.getSelectedRow());
        jbMessage.setText("Cambio guardado");
    }

    // -------------------------------------------------------------------
    @Override
    public List<TipoPersona> obtenerTipoPersonas()
    {
        return bsTipoPersonasEditor.getData();
    }

    // -------------------------------------------------------------------
    @Override
    public List<PersonaDireccion> obtenerDirecciones(Persona elemento)
    {
        PersonaDireccion filtro = new PersonaDireccion();
        filtro.setIdPersona(elemento.getId_Viejo());

        return direcciones.obtenerLista(filtro);
    }

    //--------------------------------------------------------------------
    @Override
    public List<PersonaIdentificadorVista> obtenerIdentificadores(Persona elemento)
    {
        return identificadores.obtenerListaVista(elemento.getId());
    }

    //--------------------------------------------------------------------
    @Override
    public List<Valor> obtenerTipoMedios()
    {
        return identificadores.obtenerTiposMedio();
    }

    //--------------------------------------------------------------------
    @Override
    public List<PersonaIdentificador> obtenerMedios(Persona elemento)
    {
        PersonaIdentificador filtro = new PersonaIdentificador();
        filtro.setClave("");
        filtro.setIdPersona(elemento.getId());

        return identificadores.obtenerListaMedios(filtro);
    }

    //--------------------------------------------------------------------
    public List<PersonaCreditoCuenta> obtenerCuentas(Persona elemento)
    {
        PersonaCreditoCuenta filtro = new PersonaCreditoCuenta();
        filtro.setIdPersona(elemento.getId());

        return cuentas.obtenerLista(filtro);
    }

    //--------------------------------------------------------------------
    @Override
    public List<PersonaFoto> obtenerFotos(Persona elemento)
    {
        PersonaFoto filtro = new PersonaFoto();
        filtro.setIdPersona(elemento.getId());
        filtro.setConsecutivo(1); //Solo devolvemos la primera imagen.

        return fotos.obtenerMiniaturas(filtro);
    }

    //--------------------------------------------------------------------
    public String getEsquema()
    {
        return mainContainer.getOrigenDatoActual().getNombre();
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
                showDetail(bsPersonas.getElementAt(index));
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
                    swSecondPlane = new PersonaSwingWorker();
                    swSecondPlane.addPropertyChangeListener(this);
                    parametros.add(opDELETE_LIST);
                    int[] rowsHandlers = jtbData.getSelectedRows();
                    for (int i = 0; i < rowsHandlers.length; i++)
                    {
                        rowsHandlers[i] = jtbData.convertRowIndexToModel(rowsHandlers[i]);
                    }
                    parametros.add(bsPersonas.getElementsAt(rowsHandlers));
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
                swSecondPlane = new PersonaSwingWorker();
                swSecondPlane.addPropertyChangeListener(this);
                parametros.add(opLOAD);
                jbMessage.setText("Obteniendo personas...");
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
        List<Persona> modificados = new ArrayList<>();
        List<Persona> agregados = new ArrayList<>();
        List<Persona> datos = bsPersonas.getData();

        TableCellEditor editor = jtbData.getCellEditor();
        if (editor != null)
            editor.stopCellEditing();

        for (Persona elemento : datos)
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
                swSecondPlane = new PersonaSwingWorker();
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
                showDetail(bsPersonas.getElementAt(index));
            }
        }
        else
            JOptionPane.showMessageDialog(this, "No cuenta con los permisos necesarios para ACTUALIZAR elementos");
    }

    // -------------------------------------------------------------------
    private void setDisplayData(List<TipoPersona> tipos, List<Persona> values)
    {
        bsTipoPersonasRender.setData(tipos);
        bsTipoPersonasEditor.setData(tipos);
        bsPersonas.setData(values);
        
         printRecordCount();
        jbMessage.setText("Personas obtenidas");
    }

    //---------------------------------------------------------------------
    private void setDisplayData(Persona value)
    {
        //value.acceptChanges();
        bsPersonas.addRow(value);
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
    private class PersonaSwingWorker extends SwingWorker<List<Object>, Void>
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
                        results.add(personas.obtenerLista());
                        results.add(tipoPersonas.obtenerListaHojas());
                        break;

                    case opINSERT:
                        results.add(personas.agregar((Persona) arguments.get(1)));
                        results.add(direcciones.guardar((List<PersonaDireccion>) arguments.get(2),
                                null));
                        results.add(identificadores.guardar((List<PersonaIdentificadorVista>) arguments.get(3)));
                        results.add(identificadores.guardar((List<PersonaIdentificador>) arguments.get(4), null));
                        results.add(cuentas.guardar((List<PersonaCreditoCuenta>) arguments.get(5),null));
                        results.add(fotos.guardar((PersonaFoto) arguments.get(6), null));
                        break;

                    case opUPDATE:
                        results.add(personas.actualizar((Persona) arguments.get(1)));
                        results.add(direcciones.guardar((List<PersonaDireccion>) arguments.get(2),
                                (List<PersonaDireccion>) arguments.get(3)));
                        results.add(identificadores.guardar((List<PersonaIdentificadorVista>) arguments.get(4)));
                        results.add(identificadores.guardar((List<PersonaIdentificador>) arguments.get(5),
                                (List<PersonaIdentificador>) arguments.get(6)));
                        results.add(cuentas.guardar((List<PersonaCreditoCuenta>) arguments.get(7),
                                (List<PersonaCreditoCuenta>) arguments.get(8)));
                        results.add(fotos.guardar((PersonaFoto) arguments.get(9),
                                (PersonaFoto) arguments.get(10)));

                        break;

                    case opDELETE_LIST:
                        List<Persona> selecteds = (List<Persona>) arguments.get(1);
                        results.add(personas.borrar(selecteds));
                        break;

                    case opSAVE:
                        List<Persona> adds = (List<Persona>) arguments.get(1);
                        List<Persona> modifieds = (List<Persona>) arguments.get(2);
                        if (adds.size() > 0)
                            results.add(personas.agregar(adds));
                        if (modifieds.size() > 0)
                            results.add(personas.actualizar(modifieds));
                        break;

                    default:
                        results.add(personas.obtenerLista());
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
                                List<Persona> listaUsuarios = (List<Persona>) results.get(1);
                                List<TipoPersona> listaTipos = (List<TipoPersona>) results.get(2);
                                if (listaTipos.size() > 0)
                                    setDisplayData(listaTipos, listaUsuarios);
                                else if (!tipoPersonas.esCorrecto())
                                    JOptionPane.showMessageDialog(null, tipoPersonas.getMensaje());
                                break;

                            case opINSERT:
                                if (personas.esCorrecto())
                                    setDisplayData((Persona) arguments.get(1));

                                else
                                    jbMessage.setText(personas.getMensaje());
                                break;

                            case opUPDATE:
                                if (personas.esCorrecto())
                                    resetElement((Persona) arguments.get(1));

                                else
                                    jbMessage.setText(personas.getMensaje());
                                break;

                            case opDELETE_LIST:
                                if (personas.esCorrecto())
                                {
                                    bsPersonas.removeRows((int[]) arguments.get(2));
                                     printRecordCount();
                                
                                    jbMessage.setText("Elemento(s) eliminado(s)");
                                }
                                else
                                    jbMessage.setText(personas.getEsquema());
                                break;

                            case opSAVE:
                                jbMessage.setText(personas.getMensaje());
                                if (personas.esCorrecto())
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
