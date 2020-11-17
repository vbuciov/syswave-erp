package com.syswave.forms.miempresa;

import com.syswave.entidades.common.Entidad;
import com.syswave.entidades.configuracion.Usuario_tiene_Permiso;
import com.syswave.entidades.miempresa.Contrato;
import com.syswave.forms.common.JTableDataView;
import com.syswave.swing.models.POJOTableModel;
import com.syswave.forms.databinding.ContratoTableModel;
import com.syswave.logicas.miempresa.ContratosBusinessLogic;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.SwingWorker;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellEditor;
import com.syswave.forms.common.IWindowContainer;

/**
 *
 * @author sis5
 */
public class JContratoDataView extends JTableDataView implements TableModelListener, IContratoMediator
{

    private final int opLOAD = 0;
    private final int opINSERT_LIST = 1;
    private final int opUPDATE_LIST = 2;
    private final int opDELETE_LIST = 3;
    private final int opINSERT = 4;
    private final int opUPDATE = 5;
    private final int opSAVE = 6;

    private boolean can_browse, can_create, can_update, can_delete;

    ContratosBusinessLogic contrato;
    ContratoTableModel bsContratos;
    JFileChooser jcfContrato;
    JPopupMenu Pmenu;
    JMenuItem menuDescargar;
    ContratoSwingWorker swSecondPlane;
    List<Usuario_tiene_Permiso> permisos;

    //--------------------------------------------------------------------
    public JContratoDataView(IWindowContainer container)
    {
        super(container);
        initAttributes();
        initEvents();
        grantAllPermisions();
    }

    //--------------------------------------------------------------------
    public JContratoDataView(IWindowContainer container, List<Usuario_tiene_Permiso> values)
    {
        super(container);
        initAttributes();
        initEvents();
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

        bsContratos.setReadOnly(!can_update);
    }

    //--------------------------------------------------------------------
    private void initAttributes()
    {
        contrato = new ContratosBusinessLogic(mainContainer.getOrigenDatoActual().getNombre());
        bsContratos = new ContratoTableModel(new String[]
        {
            "Nombre:{nombre}",
            "Formato:{formato}",
            "Longitud:{longitud}"
        });
        bsContratos.addTableModelListener(this);
        jtbData.setModel(bsContratos);
        jcfContrato = new JFileChooser();
        crearPopUp();
    }
    
    private void initEvents()
    {
        ActionListener actionListener = new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    finish_actionPerformed(e);
                }
                catch (IOException ex)
                {
                    Logger.getLogger(JContratoDataView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        menuDescargar.addActionListener(actionListener);
        
        jtbData.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseReleased(MouseEvent Me)
            {
                if (Me.getButton() == MouseEvent.BUTTON3)
                {
                    Pmenu.show(Me.getComponent(), Me.getX(), Me.getY());
                }
            }
        });
    }

    //--------------------------------------------------------------------
    private void grantAllPermisions()
    {
        can_browse = true;
        can_create = true;
        can_update = true;
        can_delete = true;
    }

    private void finish_actionPerformed(ActionEvent e) throws FileNotFoundException, IOException
    {
        Contrato value = bsContratos.getElementAt(jtbData.getSelectedRow());
        contrato.recargar(value);
        if (jcfContrato.showSaveDialog(this) == JFileChooser.APPROVE_OPTION)
        {
            FileOutputStream stream = new FileOutputStream( jcfContrato.getSelectedFile().toPath().toString() + "." + value.getFormato());
            try 
            {
                stream.write(value.getContenido());
            } 
            finally 
            {
                stream.close();
            }
        }
    }
    
    //--------------------------------------------------------------------
    public void showDetail(Contrato elemento)
    {
        JContratoDetailView dialogo = new JContratoDetailView(this);
        mainContainer.addWindow(dialogo);

        if (elemento != null)
            dialogo.prepareForModify(elemento);

        else
            dialogo.prepareForNew();

        mainContainer.showCenter(dialogo);
    }
    
    //--------------------------------------------------------------------
    private void crearPopUp()
    {
        Pmenu = new JPopupMenu();
        menuDescargar = new JMenuItem("Descargar machote");
        Pmenu.add(menuDescargar);
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
                    data.setModified();

                setChanges();
                jbMessage.setText("Cambios pendientes");
            }
        }
    }

    //--------------------------------------------------------------------
    @Override
    public void doCreateProcess()
    {
        if (can_create)
            showDetail(null);

        else
            JOptionPane.showMessageDialog(this, "No cuenta con los permisos necesarios para INSERTAR elementos");
    }

    //--------------------------------------------------------------------
    @Override
    public void doUpdateProcess()
    {
        if (can_update)
        {
            int index = jtbData.getSelectedRow();

            if (index >= 0)
            {
                index = jtbData.convertRowIndexToModel(index);
                showDetail(bsContratos.getElementAt(index));
            }
        }
        else
            JOptionPane.showMessageDialog(this, "No cuenta con los permisos necesarios para ACTUALIZAR elementos");
    }

    //--------------------------------------------------------------------
    @Override
    public void doDeleteProcess()
    {
        if (can_delete)
            if (JOptionPane.showConfirmDialog(this, String.format("Ha seleccionado %d registro(s) para ser eleiminado(s)\n¿Esta seguro de continuar?", jtbData.getSelectedRowCount())) == JOptionPane.OK_OPTION)
                if (swSecondPlane == null || swSecondPlane.isDone())
                {
                    List<Object> parametros = new ArrayList<Object>();
                    jbMessage.setText("Eliminando elemento(s)...");
                    swSecondPlane = new ContratoSwingWorker();
                    swSecondPlane.addPropertyChangeListener(this);
                    parametros.add(opDELETE_LIST);
                    int[] rowsHandlers = jtbData.getSelectedRows();
                    for (int i = 0; i < rowsHandlers.length; i++)
                    {
                        rowsHandlers[i] = jtbData.convertRowIndexToModel(rowsHandlers[i]);
                    }
                    parametros.add(bsContratos.getElementsAt(rowsHandlers));
                    parametros.add(rowsHandlers);
                    swSecondPlane.execute(parametros);
                }
                else
                    JOptionPane.showMessageDialog(this, "No cuenta con los permisos necesarios para eliminar elementos");
    }

    //--------------------------------------------------------------------
    @Override
    public void doRetrieveProcess()
    {
        if (can_browse)
        {
            if (swSecondPlane == null || swSecondPlane.isDone())
            {
                List<Object> parametros = new ArrayList<Object>();
                jbMessage.setText("Consultado machotes...");
                swSecondPlane = new ContratoSwingWorker();
                swSecondPlane.addPropertyChangeListener(this);
                parametros.add(opLOAD);
                swSecondPlane.execute(parametros);
            }
        }
        else
        {
            JOptionPane.showMessageDialog(this, "No cuenta con los permisos necesarios para acceder a la información");
        }
    }

    //--------------------------------------------------------------------
    @Override
    public void doSaveProcess()
    {
        int elementosTotales = 0;
        List<Contrato> agregados = new ArrayList<>();
        List<Contrato> modificados = new ArrayList<>();
        List<Contrato> datos = bsContratos.getData();

        TableCellEditor editor = jtbData.getCellEditor();
        if (editor != null)
            editor.stopCellEditing();

        for (Contrato elemento : datos)
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
                swSecondPlane = new ContratoSwingWorker();
                swSecondPlane.addPropertyChangeListener(this);
                parametros.add(opSAVE);
                parametros.add(agregados);
                parametros.add(modificados);
                swSecondPlane.execute(parametros);
            }
            else
                JOptionPane.showMessageDialog(this, "No existen cambios a guardar");
    }

    //--------------------------------------------------------------------
    @Override
    public void doOpenProcess()
    {
        if (can_update)
        {
            int index = jtbData.getSelectedRow();

            if (index >= 0)
            {
                index = jtbData.convertRowIndexToModel(index);
                showDetail(bsContratos.getElementAt(index));
            }
        }
        else
            JOptionPane.showMessageDialog(this, "No cuenta con los permisos necesarios para ACTUALIZAR elementos");
    }

    //--------------------------------------------------------------------
    @Override
    public void onAcceptModifyElement(Contrato elemento)
    {
        if (swSecondPlane == null || swSecondPlane.isDone())
        {
            List<Object> parametros = new ArrayList<Object>();
            jbMessage.setText("Modificando elemento...");
            swSecondPlane = new ContratoSwingWorker();
            swSecondPlane.addPropertyChangeListener(this);
            parametros.add(opUPDATE);
            parametros.add(elemento);
            swSecondPlane.execute(parametros);
        }
    }

    //--------------------------------------------------------------------
    @Override
    public void onAcceptNewElement(Contrato nuevo)
    {
        if (swSecondPlane == null || swSecondPlane.isDone())
        {
            List<Object> parametros = new ArrayList<Object>();
            jbMessage.setText("Agregando nuevo machote...");
            swSecondPlane = new ContratoSwingWorker();
            swSecondPlane.addPropertyChangeListener(this);
            parametros.add(opINSERT);
            parametros.add(nuevo);
            swSecondPlane.execute(parametros);
        }
    }

    //--------------------------------------------------------------------
    @Override
    public String obtenerOrigenDato()
    {
        return mainContainer.getOrigenDatoActual().getNombre();
    }

    //--------------------------------------------------------------------
    private void setDisplayData(List<Contrato> contratos)
    {
        jbMessage.setText("Machotes obtenidos");
        bsContratos.setData(contratos);
        
        printRecordCount();
    }
    
    //--------------------------------------------------------------------
    private void setDisplayData(Contrato nuevo)
    {
        jbMessage.setText("Nuevo agregado");
        bsContratos.addRow(nuevo);
        printRecordCount();
    }

    //------------------------------------------------------------------
    private void resetCurrentElement()
    {
        jbMessage.setText("Cambio guardado");
        bsContratos.fireTableRowsUpdated(jtbData.getSelectedRow(), jtbData.getSelectedRow());
    }

    /*---------------------------------------------------------
     -----------------------Clase privada-----------------------
     ---------------------------------------------------------*/
    private class ContratoSwingWorker extends SwingWorker<List<Object>, Void>
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
            setProgress(50);
            if (!isCancelled() && arguments != null && arguments.size() > 0)
            {
                int opcion = (int) arguments.get(0); //Debe haber un entero en la primera posición
                switch (opcion)
                {
                    case opLOAD:
                        arguments.add(contrato.obtenerListaSinContenido());
                        break;
                    case opINSERT:
                        arguments.add(contrato.agregar((Contrato) arguments.get(1)));
                        break;
                    case opUPDATE:
                        arguments.add(contrato.actualizar((Contrato) arguments.get(1)));
                        break;
                    case opDELETE_LIST:
                        List<Contrato> borrados = (List<Contrato>) arguments.get(1);
                        arguments.add(contrato.borrar(borrados));
                        break;
                    case opSAVE:
                        List<Contrato> agregados = (List<Contrato>) arguments.get(1);
                        List<Contrato> modificados = (List<Contrato>) arguments.get(2);
                        if (agregados.size() > 0)
                            arguments.add(contrato.agregar(agregados));
                        if (modificados.size() > 0)
                            arguments.add(contrato.actualizar(modificados));
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
                                List<Contrato> listContratos = (List<Contrato>) arguments.get(1);
                                if (listContratos.size() > 0)
                                    setDisplayData(listContratos);
                                break;
                            case opINSERT:
                                if (contrato.esCorrecto())
                                    setDisplayData((Contrato) arguments.get(1));
                                break;
                            case opUPDATE:
                                if (contrato.esCorrecto())
                                    resetCurrentElement();
                                break;
                            case opDELETE_LIST:
                                if (contrato.esCorrecto())
                                {
                                    bsContratos.removeRows((int[]) arguments.get(2));
                                     printRecordCount();
                                
                                    jbMessage.setText("Elemento(s) eliminado(s)");
                                }
                                else 
                                    jbMessage.setText(contrato.getMensaje());
                                break;
                            case opSAVE:
                                jbMessage.setText(contrato.getMensaje());
                                if (contrato.esCorrecto())
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
