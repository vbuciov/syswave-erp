package com.syswave.forms.configuracion;

import com.syswave.entidades.common.Entidad;
import com.syswave.entidades.configuracion.NodoPermiso;
import com.syswave.entidades.configuracion.Usuario;
import com.syswave.entidades.configuracion.Usuario_tiene_Permiso;
import com.syswave.forms.common.JTableDataView;
import com.syswave.swing.models.POJOTableModel;
import com.syswave.swing.table.renders.PasswordTableCellRenderer;
import com.syswave.forms.databinding.UsuariosTableModel;
import com.syswave.logicas.configuracion.UsuarioTienePermisosBusinessLogic;
import com.syswave.logicas.configuracion.UsuariosBusinessLogic;
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
 * Esta clase realiza las peticiones a la capa de negocios, para dibujar en la
 * presentación.
 *
 * @author Victor Manuel Bucio Vargas
 */
public class JUsuariosDataView extends JTableDataView implements IUsuarioMediator, TableModelListener
{

    private final int opLOAD = 0;
    private final int opINSERT_LIST = 1;
    private final int opUPDATE_LIST = 2;
    private final int opDELETE_LIST = 3;
    private final int opINSERT = 4;
    private final int opUPDATE = 5;
    private final int opSAVE = 6;
    private boolean can_browse, can_create, can_update, can_delete, can_grant;
    private int tipoOP;
    public final static String usuario = "usu";
    public final static String rol = "rol";

    UsuariosTableModel bsUsuarios;
    UsuariosBusinessLogic Usuarios;
    UsuarioTienePermisosBusinessLogic UsuarioPermisos;

    UsuariosCargarSwingWorker swCargador;
    List<Usuario_tiene_Permiso> permisos;
    TableColumn colClave;

    //---------------------------------------------------------------------
    private void grantAllPermisions()
    {
        can_browse = true;
        can_create = true;
        can_update = true;
        can_delete = true;
        can_grant = true;
    }

    //---------------------------------------------------------------------
    public JUsuariosDataView(IWindowContainer container, int tOP, String keyName)
    {
        super(container);
        tipoOP = tOP;
        initAttributes();
        grantAllPermisions();
        setName(keyName);

    }

    //---------------------------------------------------------------------
    public JUsuariosDataView(IWindowContainer container, int tOP, String keyName, List<Usuario_tiene_Permiso> values)
    {
        super(container);
        tipoOP = tOP;
        initAttributes();
        grant(values);
        setName(keyName);
    }

    //---------------------------------------------------------------------
    public final void grant(List<Usuario_tiene_Permiso> values)
    {
        if (permisos != null && permisos.size() > 0)
            permisos.clear();

        can_browse = false;
        can_create = false;
        can_update = false;
        can_delete = false;
        can_grant = false;

        permisos = values;

        for (int i = 0; i < values.size(); i++) {
            if (values.get(i).getLlave().equals("browse"))
                can_browse = true;

            else if (values.get(i).getLlave().equals("create"))
                can_create = true;

            else if (values.get(i).getLlave().equals("update"))
                can_update = true;

            else if (values.get(i).getLlave().equals("delete"))
                can_delete = true;

            else if (values.get(i).getLlave().equals("grant"))
                can_grant = true;
        }
    }

    //---------------------------------------------------------------------
    private void initAttributes()
    {
        Usuarios = new UsuariosBusinessLogic("configuracion");
        UsuarioPermisos = new UsuarioTienePermisosBusinessLogic("configuracion");
        if (tipoOP == 0)
            bsUsuarios = new UsuariosTableModel(new String[]{
                "Identificador:{identificador}",
                "Contraseña:{clave}",
                "Activo:{es_activo}"
            });
        else
            bsUsuarios = new UsuariosTableModel(new String[]{
                "Identificador:{identificador}",
                "Activo:{es_activo}"
            });
        bsUsuarios.addTableModelListener(this);

        jtbData.setModel(bsUsuarios);

        if (jtbData.getColumnCount() > 2) {
            colClave = jtbData.getColumnModel().getColumn(1);
            colClave.setCellRenderer(new PasswordTableCellRenderer());
        }
    }

    //---------------------------------------------------------------------
    public void showDetail(Usuario elemento)
    {
        JUsuariosDetailView dialogo = new JUsuariosDetailView(this);
        mainContainer.addWindow(dialogo);

        if (elemento != null)
            dialogo.prepareForModify(elemento);

        else
            dialogo.prepareForNew(tipoOP);

        dialogo.setAllowGrant(can_grant);
        mainContainer.showCenter(dialogo);
    }

    //---------------------------------------------------------------------
    @Override
    public void doCreateProcess()
    {
        if (can_create)
            showDetail(null);
        else
            JOptionPane.showMessageDialog(this, "No cuenta con los permisos necesarios para INSERTAR elementos");
    }

    //---------------------------------------------------------------------
    @Override
    public void doUpdateProcess()
    {
        if (can_update) {
            int index = jtbData.getSelectedRow();

            if (index >= 0) {
                index = jtbData.convertRowIndexToModel(index);
                showDetail(bsUsuarios.getElementAt(index));
            }
        }
        else
            JOptionPane.showMessageDialog(this, "No cuenta con los permisos necesarios para ACTUALIZAR elementos");
    }

    //---------------------------------------------------------------------
    @Override
    public void doDeleteProcess()
    {
        if (can_delete) {
            if (JOptionPane.showConfirmDialog(this, String.format("Ha seleccionado %d registro(s) para ser eleiminado(s)\n¿Esta seguro de continuar?", jtbData.getSelectedRowCount())) == JOptionPane.OK_OPTION) {
                if (swCargador == null || swCargador.isDone()) {
                    List<Object> parametros = new ArrayList<Object>();
                    swCargador = new UsuariosCargarSwingWorker();
                    swCargador.addPropertyChangeListener(this);
                    parametros.add(opDELETE_LIST);
                    int[] rowsHandlers = jtbData.getSelectedRows();
                    for (int i = 0; i < rowsHandlers.length; i++) {
                        rowsHandlers[i] = jtbData.convertRowIndexToModel(rowsHandlers[i]);
                    }
                    parametros.add(bsUsuarios.getElementsAt(rowsHandlers));
                    parametros.add(rowsHandlers);
                    swCargador.execute(parametros);
                }
            }
        }

        else
            JOptionPane.showMessageDialog(this, "No cuenta con los permisos necesarios para eliminar elementos");

    }

    //---------------------------------------------------------------------
    @Override
    public void doOpenProcess()
    {
        if (can_update) {
            int index = jtbData.getSelectedRow();

            if (index >= 0) {
                index = jtbData.convertRowIndexToModel(index);
                showDetail(bsUsuarios.getElementAt(index));
            }
        }
        else
            JOptionPane.showMessageDialog(this, "No cuenta con los permisos necesarios para ACTUALIZAR elementos");
    }

    //---------------------------------------------------------------------
    @Override
    public void doRetrieveProcess()
    {
        if (can_browse) {
            if (swCargador == null || swCargador.isDone()) {
                if (can_browse /*existePermiso("browse")*/) {
                    List<Object> parametros = new ArrayList<Object>();
                    Usuario filtro = new Usuario();
                    swCargador = new UsuariosCargarSwingWorker();
                    swCargador.addPropertyChangeListener(this);
                    filtro.setEs_tipo(tipoOP);
                    filtro.acceptChanges();
                    parametros.add(opLOAD);
                    parametros.add(filtro);
                    swCargador.execute(parametros);
                }

                else
                    JOptionPane.showMessageDialog(this, "No cuenta con los permisos necesarios para acceder a la información");
            }
        }
        else
            JOptionPane.showMessageDialog(this, "No cuenta con los permisos necesarios para VISUALIZAR ESTA INFORMACION");
    }

    //---------------------------------------------------------------------
    @Override
    public void doSaveProcess()
    {
        int elementosTotales = 0;
        List<Usuario> modificados = new ArrayList<>();
        List<Usuario> agregados = new ArrayList<>();
        List<Usuario> datos = bsUsuarios.getData();

        for (Usuario elemento : datos) {
            if (elemento.isNew())
                agregados.add(elemento);

            else if (elemento.isModified())
                modificados.add(elemento);
        }

        elementosTotales = modificados.size() + agregados.size();

        if (elementosTotales > 0) {
            TableCellEditor editor = jtbData.getCellEditor();
            if (editor != null)
                editor.stopCellEditing();

            if (swCargador == null || swCargador.isDone()) {
                List<Object> parametros = new ArrayList<Object>();
                swCargador = new UsuariosCargarSwingWorker();
                swCargador.addPropertyChangeListener(this);
                parametros.add(opSAVE);
                parametros.add(agregados);
                parametros.add(modificados);
                swCargador.execute(parametros);
            }
        }
        else
            JOptionPane.showMessageDialog(this, "No existen cambios a guardar");
    }

    //---------------------------------------------------------------------
    @Override
    public List<NodoPermiso> obtenerSemilla(Usuario elementoActual)
    {
        NodoPermiso filtro = new NodoPermiso();
        filtro.setId_usuario(elementoActual.getIdentificador_Viejo());
        filtro.setId_origen_dato(mainContainer.getOrigenDatoActual().getId());
        return UsuarioPermisos.obtenerSemilla(filtro);
    }

    //---------------------------------------------------------------------
    private void setDisplayData(List<Usuario> values)
    {
        bsUsuarios.setData(values);
        printRecordCount();
    }

    //---------------------------------------------------------------------
    private void setDisplayData(Usuario value)
    {
        //value.acceptChanges();
        bsUsuarios.addRow(value);
        jbMessage.setText("Nuevo agregado");
        printRecordCount();

    }

    //---------------------------------------------------------------------
    private void resetElement(Usuario value)
    {
        //value.acceptChanges();
        bsUsuarios.fireTableRowsUpdated(jtbData.getSelectedRow(), jtbData.getSelectedRow());
        jbMessage.setText("Cambio guardado");
    }

    //---------------------------------------------------------------------
    @Override
    public void onAcceptNewElement(Usuario nuevo, List<NodoPermiso> permisos)
    {
        if (swCargador == null || swCargador.isDone()) {
            List<Object> parametros = new ArrayList<Object>();
            swCargador = new UsuariosCargarSwingWorker();
            swCargador.addPropertyChangeListener(this);
            parametros.add(opINSERT);
            parametros.add(nuevo);
            parametros.add(permisos);
            parametros.add(mainContainer.getOrigenDatoActual().getId());

            jbMessage.setText("Guardando...");
            swCargador.execute(parametros);
        }
    }

    //---------------------------------------------------------------------
    @Override
    public void onAcceptModifyElement(Usuario elemento, List<NodoPermiso> permisos)
    {
        if (swCargador == null || swCargador.isDone()) {
            List<Object> parametros = new ArrayList<Object>();
            swCargador = new UsuariosCargarSwingWorker();
            swCargador.addPropertyChangeListener(this);
            parametros.add(opUPDATE);
            parametros.add(elemento);
            parametros.add(permisos);
            parametros.add(mainContainer.getOrigenDatoActual().getId());

            jbMessage.setText("Guardando...");
            swCargador.execute(parametros);
        }
    }

    //---------------------------------------------------------------------
    @Override
    public void tableChanged(TableModelEvent e)
    {
        //Nota: Cuando una columna comienza a editarse, también se dispara el evento... pero
        //no sirve de nada porque no marca la columna exacta.
        if (e.getType() == TableModelEvent.UPDATE && e.getColumn() != TableModelEvent.ALL_COLUMNS) {
            int row = e.getFirstRow();

            if (row != TableModelEvent.HEADER_ROW) {
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

    //------------------------------------------------------------------
    private class UsuariosCargarSwingWorker extends SwingWorker<List<Object>, Void>
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
            if (!isCancelled() && arguments != null && arguments.size() > 0) {
                int opcion = (int) arguments.get(0); //Debe haber un entero en la primera posición
                setProgress(50);

                switch (opcion) {
                    case opLOAD:
                        arguments.add(Usuarios.obtenerLista((Usuario) arguments.get(1)));
                        break;

                    case opINSERT:
                        arguments.add(Usuarios.agregar((Usuario) arguments.get(1)));
                        arguments.add(UsuarioPermisos.guardar((Usuario) arguments.get(1),
                                                              (List<NodoPermiso>) arguments.get(2),
                                                              (int) arguments.get(3)));
                        break;

                    case opUPDATE:
                        arguments.add(Usuarios.actualizar((Usuario) arguments.get(1)));
                        arguments.add(UsuarioPermisos.guardar((Usuario) arguments.get(1),
                                                              (List<NodoPermiso>) arguments.get(2),
                                                              (int) arguments.get(3)));
                        break;

                    case opDELETE_LIST:
                        List<Usuario> selecteds = (List<Usuario>) arguments.get(1);
                        Usuarios.borrar(selecteds);
                        break;

                    case opSAVE:
                        List<Usuario> adds = (List<Usuario>) arguments.get(1);
                        List<Usuario> modifieds = (List<Usuario>) arguments.get(2);
                        if (adds.size() > 0)
                            arguments.add(Usuarios.agregar(adds));
                        if (modifieds.size() > 0)
                            arguments.add(Usuarios.actualizar(modifieds));
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
            try {
                if (!isCancelled()) {
                    List<Object> results = get();
                    int opcion = (int) results.get(0);
                    switch (opcion) {
                        case opLOAD:
                            setDisplayData((List<Usuario>) results.get(2));
                            break;

                        case opINSERT:
                            if (Usuarios.esCorrecto())
                                setDisplayData((Usuario) results.get(1));

                            else
                                jbMessage.setText(Usuarios.getMensaje());
                            break;

                        case opUPDATE:
                            if (Usuarios.esCorrecto())
                                resetElement((Usuario) results.get(1));

                            else
                                jbMessage.setText(Usuarios.getMensaje());
                            break;

                        case opDELETE_LIST:
                            if (Usuarios.esCorrecto()) {
                                bsUsuarios.removeRows((int[]) results.get(2));
                                printRecordCount();
                            }
                            break;

                        case opSAVE:
                            jbMessage.setText(Usuarios.getMensaje());
                            if (Usuarios.esCorrecto())
                                acceptChanges();
                            break;
                    }

                }
            }
            catch (InterruptedException ignore) {
            }
            catch (java.util.concurrent.ExecutionException e) {
                String why;
                Throwable cause = e.getCause();
                if (cause != null) {
                    why = cause.getMessage();
                }
                else {
                    why = e.getMessage();
                }
                System.err.println("Error retrieving file: " + why);
            }
            finally {
                if (arguments != null && arguments.size() > 0)
                    arguments.clear();
            }
        }
    }
}
