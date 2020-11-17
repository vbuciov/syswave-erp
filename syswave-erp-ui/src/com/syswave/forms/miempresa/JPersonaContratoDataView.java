package com.syswave.forms.miempresa;

import com.syswave.entidades.common.Entidad;
import com.syswave.entidades.configuracion.Usuario_tiene_Permiso;
import com.syswave.entidades.miempresa.Contrato;
import com.syswave.entidades.miempresa_vista.ImpresionContrato;
import com.syswave.entidades.miempresa.Jornada;
import com.syswave.entidades.miempresa.PersonaContrato;
import com.syswave.entidades.miempresa_vista.Personal;
import com.syswave.entidades.miempresa.Puesto;
import com.syswave.entidades.miempresa.TipoPersona;
import com.syswave.entidades.miempresa.Valor;
import com.syswave.forms.common.JTableDataView;
import com.syswave.swing.models.POJOTableModel;
import com.syswave.forms.databinding.AreaComboBoxModel;
import com.syswave.forms.databinding.PersonaContratoTableModel;
import com.syswave.forms.databinding.JornadaComboBoxModel;
import com.syswave.forms.databinding.PersonalComboBoxModel;
import com.syswave.forms.databinding.PuestoComboBoxModel;
import com.syswave.forms.databinding.ValorComboBoxModel;
import com.syswave.swing.table.editors.LookUpComboBoxTableCellEditor;
import com.syswave.swing.table.renders.LookUpComboBoxTableCellRenderer;
import com.syswave.logicas.miempresa.ContratosBusinessLogic;
import com.syswave.logicas.miempresa.PersonaContratosBusinessLogic;
import com.syswave.logicas.miempresa.JornadasBusinessLogic;
import com.syswave.logicas.miempresa.PersonasBusinessLogic;
import com.syswave.logicas.miempresa.PuestoBusinessLogic;
import com.syswave.logicas.miempresa.ValoresBusinessLogic;
import com.toedter.calendar.JDateChooserCellEditor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import com.syswave.forms.common.IWindowContainer;

/**
 *
 * @author sis5
 */
public class JPersonaContratoDataView extends JTableDataView implements IPersonaContratoMediator, TableModelListener
{

    private final int opLOAD = 0;
    private final int opINSERT_LIST = 1;
    private final int opUPDATE_LIST = 2;
    private final int opDELETE_LIST = 3;
    private final int opINSERT = 4;
    private final int opUPDATE = 5;
    private final int opSAVE = 6;

    private boolean can_browse, can_create, can_update, can_delete;

    PersonaContratosBusinessLogic personaContratos;
    PersonasBusinessLogic personas;
    ValoresBusinessLogic areas;
    PuestoBusinessLogic puestos;
    JornadasBusinessLogic jornadas;
    ContratosBusinessLogic contratos;
    PersonalComboBoxModel bsPersonasRender;
    PersonalComboBoxModel bsPersonasEditor;

    AreaComboBoxModel bsAreasRender;
    AreaComboBoxModel bsAreasEditor;

    PuestoComboBoxModel bsPuestosRender;
    PuestoComboBoxModel bsPuestosEditor;

    JornadaComboBoxModel bsJornadaRender;
    JornadaComboBoxModel bsJornadaEditor;

    ValorComboBoxModel bsTipoContratoRender;
    ValorComboBoxModel bsTipoContratoEditor;

    private PersonaContratoTableModel bsContrato;

    TableColumn colPersona, colArea, colPuesto, colJornada, colFechaIni,
            colFechaTer, colEsTipo;

    JPopupMenu Pmenu;
    JMenuItem menuItem;

    JFileChooser jfcContrato;

    private ContratoSwingWorker swSecondPlane;
    List<Usuario_tiene_Permiso> permisos;

    //------------------------------------------------------------------
    public JPersonaContratoDataView(IWindowContainer container)
    {
        super(container);
        initAttributes(container);
        initEvents();
        grantAllPermisions();
    }

    //------------------------------------------------------------------
    public JPersonaContratoDataView(IWindowContainer container, List<Usuario_tiene_Permiso> values)
    {
        super(container);
        initAttributes(container);
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

        bsContrato.setReadOnly(!can_update);
    }

    //---------------------------------------------------------------------
    private void initAttributes(IWindowContainer container)
    {
        personas = new PersonasBusinessLogic(container.getOrigenDatoActual().getNombre());
        areas = new ValoresBusinessLogic(personas.getEsquema());
        puestos = new PuestoBusinessLogic(personas.getEsquema());
        jornadas = new JornadasBusinessLogic(personas.getEsquema());
        personaContratos = new PersonaContratosBusinessLogic(personas.getEsquema());
        contratos = new ContratosBusinessLogic(personas.getEsquema());

        bsPersonasRender = new PersonalComboBoxModel();
        bsPersonasEditor = new PersonalComboBoxModel();

        bsAreasRender = new AreaComboBoxModel();
        bsAreasEditor = new AreaComboBoxModel();

        bsPuestosRender = new PuestoComboBoxModel();
        bsPuestosEditor = new PuestoComboBoxModel();

        bsJornadaRender = new JornadaComboBoxModel();
        bsJornadaEditor = new JornadaComboBoxModel();

        bsTipoContratoRender = new ValorComboBoxModel();
        bsTipoContratoEditor = new ValorComboBoxModel();

        bsContrato = new PersonaContratoTableModel(new String[]
        {
            "No.:{consecutivo}",
            "Persona:{id_persona}",
            "Área:{id_area}",
            "Puesto:{id_puesto}",
            "Fecha de inicio:{fecha_inicio}",
            "Fecha de termino:{fecha_terminacion}",
            "Jornada:{id_jornada}",
            "Tipo:{es_tipo}"
        });
        bsContrato.addTableModelListener(this);
        jtbData.setModel(bsContrato);
        jtbData.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        Pmenu = new JPopupMenu();
        menuItem = new JMenuItem("Generar contrato");
        Pmenu.add(menuItem);

        jfcContrato = new JFileChooser();

        if (jtbData.getColumnCount() > 0)
        {
            colPersona = jtbData.getColumnModel().getColumn(1);
            colPersona.setCellRenderer(new LookUpComboBoxTableCellRenderer<Personal>(bsPersonasRender));
            colPersona.setCellEditor(new LookUpComboBoxTableCellEditor<Personal>(bsPersonasEditor));
            colPersona.setPreferredWidth(220);

            colArea = jtbData.getColumnModel().getColumn(2);
            colArea.setCellRenderer(new LookUpComboBoxTableCellRenderer<Valor>(bsAreasRender));
            colArea.setCellEditor(new LookUpComboBoxTableCellEditor<Valor>(bsAreasEditor));
            colArea.setPreferredWidth(200);

            colPuesto = jtbData.getColumnModel().getColumn(3);
            colPuesto.setCellRenderer(new LookUpComboBoxTableCellRenderer<Puesto>(bsPuestosRender));
            colPuesto.setCellEditor(new LookUpComboBoxTableCellEditor<Puesto>(bsPuestosEditor));
            colPuesto.setPreferredWidth(200);

            colFechaIni = jtbData.getColumnModel().getColumn(4);
            colFechaIni.setCellEditor(new JDateChooserCellEditor());
            colFechaIni.setPreferredWidth(150);

            colFechaTer = jtbData.getColumnModel().getColumn(5);
            colFechaTer.setCellEditor(new JDateChooserCellEditor());
            colFechaTer.setPreferredWidth(150);

            colJornada = jtbData.getColumnModel().getColumn(6);
            colJornada.setCellRenderer(new LookUpComboBoxTableCellRenderer<Jornada>(bsJornadaRender));
            colJornada.setCellEditor(new LookUpComboBoxTableCellEditor<Jornada>(bsJornadaEditor));
            colJornada.setPreferredWidth(220);

            colEsTipo = jtbData.getColumnModel().getColumn(7);
            colEsTipo.setCellRenderer(new LookUpComboBoxTableCellRenderer<Valor>(bsTipoContratoRender));
            colEsTipo.setCellEditor(new LookUpComboBoxTableCellEditor<Valor>(bsTipoContratoEditor));
            colEsTipo.setPreferredWidth(200);

            //Nota: Debido a los renders que se estan utilizando es necesario tener un renglón más alto.
            jtbData.setRowHeight((int) (jtbData.getRowHeight() * 1.5));
        }
    }

    private void initEvents()
    {
        //------Eventos
        menuItem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    imprimeContrato();
                }
                catch (IOException ex)
                {
                    Logger.getLogger(JPersonaContratoDataView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

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
        //-----Eventos
    }

    //------------------------------------------------------------------
    private void grantAllPermisions()
    {
        can_browse = true;
        can_create = true;
        can_update = true;
        can_delete = true;
    }

    //------------------------------------------------------------------
    private void imprimeContrato() throws FileNotFoundException, IOException
    {
        if (jtbData.getSelectedRow() > -1)
        {
            List<Contrato> listContratos = contratos.obtenerListaSinContenido();
            String[] choices = convertToStringArray(listContratos);
            String seleccion = (String) JOptionPane.showInputDialog(null, "Escoge un Formato",
                    "Seleccion de formatos", JOptionPane.QUESTION_MESSAGE,
                    null, choices, choices[0]);
            Contrato documento = seleccion != null ? buscarSeleccion(listContratos, seleccion) : null;

            if (documento != null)
            {
                contratos.recargar(documento); //Bajamos el contenido del machote
                byte[] data = documento.getContenido();
                PersonaContrato contratoPersona = bsContrato.getElementAt(jtbData.convertRowIndexToModel(jtbData.getSelectedRow()));

                try (FileOutputStream fos = new FileOutputStream("temp.doc"))
                {
                    fos.write(data);
                }

                catch (IOException ex)
                {
                    System.out.println(ex.getMessage());
                }

                ImpresionContrato paraImprimir = personaContratos.obtieneDatosImpresion(contratoPersona);

                File doc = new File("temp.doc");
                FileInputStream fileInputStream = new FileInputStream(doc);
                BufferedInputStream buffInputStream = new BufferedInputStream(fileInputStream);

                HWPFDocument word = new HWPFDocument(new POIFSFileSystem(buffInputStream));

                Range range = word.getRange();
                String[] fechaInicio = contratoPersona.getFechaInicio().toString().split("-");
                String[] fechaFin = contratoPersona.getFechaTerminacion() != null
                        ? contratoPersona.getFechaTerminacion().toString().split("-") : new String[]
                        {
                            "", "", ""
                        };

                range.replaceText("<@personal.nombre>", paraImprimir.getNombre().toUpperCase());
                range.replaceText("<@personal.nacionalidad>", paraImprimir.getNacionalidad().toUpperCase());
                range.replaceText("<@personal.edad>", Integer.toString(paraImprimir.getEdad()));
                range.replaceText("<@personal.estado_civil>", paraImprimir.getEstado_civil().toUpperCase());
                range.replaceText("<@personal.conyugue>", " ");
                range.replaceText("<@personal.sexo>", paraImprimir.getGenero().toUpperCase());
                range.replaceText("<@personal.rfc>", paraImprimir.getRfc().toUpperCase());
                range.replaceText("<@personal.curp>", paraImprimir.getCurp().toUpperCase());
                range.replaceText("<@personal.direccion>", paraImprimir.getDireccion().toUpperCase());
                range.replaceText("<@contrato.puesto>", paraImprimir.getPuesto().toUpperCase());
                range.replaceText("<@contrato.area_negocio>", paraImprimir.getAreaNegocio().toUpperCase());
                range.replaceText("<@contrato.dia_inicio>", fechaInicio[2]);
                range.replaceText("<@contrato.mes_inicio>", obtenerNombreMes(fechaInicio[1]));
                range.replaceText("<@contrato.año_inicio>", fechaInicio[0]);
                range.replaceText("<@contrato.dia_termino>", fechaFin[2]);
                range.replaceText("<@contrato.mes_termino>", obtenerNombreMes(fechaFin[1]));
                range.replaceText("<@contrato.año_termino>", fechaFin[0]);
                range.replaceText("<@jornada.descripcion>", paraImprimir.getJornada().toUpperCase());

                if (jfcContrato.showSaveDialog(this) == JFileChooser.APPROVE_OPTION)
                {
                    File fichero = jfcContrato.getSelectedFile();
                    if (!fichero.getPath().endsWith(".doc") || fichero.getPath().endsWith(".docx"))
                        fichero = new File(fichero.getPath() + ".doc");

                    try (OutputStream output = new FileOutputStream(fichero))
                    {
                        output.flush();
                        word.write(output);
                    }

                    catch (IOException ex)
                    {
                        System.out.println(ex.getMessage());
                    }
                }
            }

        }
        else
            JOptionPane.showMessageDialog(this, "Asegúrese de que esté seleccionada una persona.");

    }

    /**
     * Guarda en un arreglo de Strings todos los nombres de los contratos a
     * partir de una lista de Contratos y regresa ese arreglo de Strings.
     */
    private String[] convertToStringArray(List<Contrato> listContratos)
    {

        String[] resultado = new String[listContratos.size()];

        int i = 0;

        for (Contrato dato : listContratos)
        {
            resultado[i] = dato.getNombre();
            i++;
        }

        return resultado;
    }

    private Contrato buscarSeleccion(List<Contrato> listContratos, String seleccion)
    {
        Contrato seleccionado = null;
        boolean seEncontro = false;
        int i = 0;

        while (!seEncontro && i < listContratos.size())
        {
            seEncontro = listContratos.get(i).getNombre() == seleccion;
            if (!seEncontro)
                i++;
            else
                seleccionado = listContratos.get(i);
        }

        return seleccionado;
    }

    private String obtenerNombreMes(String no)
    {
        String mes = "";
        switch (no)
        {
            case "01":
                mes = "ENERO";
                break;

            case "02":
                mes = "FEBRERO";
                break;

            case "03":
                mes = "MARZO";
                break;

            case "04":
                mes = "ABRIL";
                break;

            case "05":
                mes = "MAYO";
                break;

            case "06":
                mes = "JUNIO";
                break;

            case "07":
                mes = "JULIO";
                break;

            case "08":
                mes = "AGOSTO";
                break;

            case "09":
                mes = "SEPTIEMBRE";
                break;

            case "10":
                mes = "OCTUBRE";
                break;

            case "11":
                mes = "NOVIEMBRE";
                break;

            case "12":
                mes = "DICIEMBRE";
                break;
        }
        return mes;
    }

    //------------------------------------------------------------------
    private void crearTiposContratos()
    {
        List<Valor> SonTipos = new ArrayList<>();

        SonTipos.add(new Valor(0, "Permanente"));
        SonTipos.add(new Valor(1, "Temporal"));

        bsTipoContratoRender.setData(SonTipos);
        bsTipoContratoEditor.setData(SonTipos);
    }

    private void showDetail(PersonaContrato elemento)
    {
        JPersonaContratoDetailView dialogo = new JPersonaContratoDetailView(this);
        mainContainer.addWindow(dialogo);

        if (elemento != null)
            dialogo.prepareForModify(elemento);

        else
            dialogo.prepareForNew();

        mainContainer.showCenter(dialogo);
    }

    //------------------------------------------------------------------
    @Override
    public void doCreateProcess()
    {
        if (can_create)
            showDetail(null);

        else
            JOptionPane.showMessageDialog(this, "No cuenta con los permisos necesarios para INSERTAR elementos");
    }

    //------------------------------------------------------------------
    @Override
    public void doUpdateProcess()
    {
        if (can_update)
        {
            int index = jtbData.getSelectedRow();

            if (index >= 0)
            {
                index = jtbData.convertRowIndexToModel(index);
                showDetail(bsContrato.getElementAt(index));
            }
        }
        else
            JOptionPane.showMessageDialog(this, "No cuenta con los permisos necesarios para ACTUALIZAR elementos");
    }

    //------------------------------------------------------------------
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
                    jbMessage.setText("Eliminando elemento(s)...");
                    swSecondPlane = new ContratoSwingWorker();
                    swSecondPlane.addPropertyChangeListener(this);
                    parametros.add(opDELETE_LIST);
                    int[] rowsHandlers = jtbData.getSelectedRows();
                    for (int i = 0; i < rowsHandlers.length; i++)
                    {
                        rowsHandlers[i] = jtbData.convertRowIndexToModel(rowsHandlers[i]);
                    }
                    parametros.add(bsContrato.getElementsAt(rowsHandlers));
                    parametros.add(rowsHandlers);
                    swSecondPlane.execute(parametros);
                }
            }
        }

        else
            JOptionPane.showMessageDialog(this, "No cuenta con los permisos necesarios para eliminar elementos");
    }

    //------------------------------------------------------------------
    @Override
    public void doRetrieveProcess()
    {
        if (can_browse)
        {
            if (swSecondPlane == null || swSecondPlane.isDone())
            {
                List<Object> parametros = new ArrayList<Object>();
                jbMessage.setText("Obteniendo contratos...");
                swSecondPlane = new ContratoSwingWorker();
                swSecondPlane.addPropertyChangeListener(this);
                parametros.add(opLOAD);
                Valor filtro = new Valor();
                filtro.setSeccion("contratos.areas_negocio");
                TipoPersona filtro2 = new TipoPersona();
                filtro2.setSiglas("");
                filtro2.setNombre("");
                filtro2.setUsaPersonal(true);

                parametros.add(filtro2);
                parametros.add(filtro);

                swSecondPlane.execute(parametros);
            }
        }
        else
            JOptionPane.showMessageDialog(this, "No cuenta con los permisos necesarios para acceder a la información");
    }

    //------------------------------------------------------------------
    @Override
    public void doSaveProcess()
    {
        int elementosTotales = 0;
        List<PersonaContrato> modificados = new ArrayList<>();
        List<PersonaContrato> agregados = new ArrayList<>();
        List<PersonaContrato> datos = bsContrato.getData();

        TableCellEditor editor = jtbData.getCellEditor();
        if (editor != null)
            editor.stopCellEditing();

        for (PersonaContrato elemento : datos)
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
                swSecondPlane = new ContratoSwingWorker();
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

    //------------------------------------------------------------------
    @Override
    public void doOpenProcess()
    {
        if (can_update)
        {
            int index = jtbData.getSelectedRow();

            if (index >= 0)
            {
                index = jtbData.convertRowIndexToModel(index);
                showDetail(bsContrato.getElementAt(index));
            }
        }
        else
            JOptionPane.showMessageDialog(this, "No cuenta con los permisos necesarios para ACTUALIZAR elementos");
    }

    //------------------------------------------------------------------
    @Override
    public void onAcceptModifyElement(PersonaContrato elemento)
    {
        if (swSecondPlane == null || swSecondPlane.isDone())
        {
            List<Object> parametros = new ArrayList<Object>();
            swSecondPlane = new ContratoSwingWorker();
            swSecondPlane.addPropertyChangeListener(this);
            parametros.add(opUPDATE);
            parametros.add(elemento);
            jbMessage.setText("Guardando...");
            swSecondPlane.execute(parametros);
        }
    }

    //------------------------------------------------------------------
    @Override
    public void onAcceptNewElement(PersonaContrato nuevo)
    {
        if (swSecondPlane == null || swSecondPlane.isDone())
        {
            List<Object> parametros = new ArrayList<Object>();
            swSecondPlane = new ContratoSwingWorker();
            swSecondPlane.addPropertyChangeListener(this);
            parametros.add(opINSERT);
            parametros.add(nuevo);

            jbMessage.setText("Guardando...");
            swSecondPlane.execute(parametros);
        }
    }

    //------------------------------------------------------------------
    @Override
    public String obtenerOrigenDato()
    {
        return mainContainer.getOrigenDatoActual().getNombre();
    }

    //------------------------------------------------------------------
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

                //Se esta cambiando la llave foranea primaria.
                if (jtbData.getColumnModel().getColumn(e.getColumn()) == colPersona
                        && !((PersonaContrato) data).getHasOnePersona().isSet())
                    ((PersonaContrato) data).getHasOnePersona().setModified();

                setChanges();
                jbMessage.setText("Cambios pendientes");
            }
        }
    }

    //------------------------------------------------------------------
    private void setDisplayData(List<Personal> personas, List<Valor> areas, List<Puesto> puestos, List<Jornada> jorndas, List<PersonaContrato> listContracts)
    {
        bsPersonasRender.setData(personas);
        bsPersonasEditor.setData(personas);

        bsAreasRender.setData(areas);
        bsAreasEditor.setData(areas);

        bsPuestosRender.setData(puestos);
        bsPuestosEditor.setData(puestos);

        bsJornadaRender.setData(jorndas);
        bsJornadaEditor.setData(jorndas);

        bsContrato.setData(listContracts);
        
         printRecordCount();
        jbMessage.setText("Lista de contratos obtenidos");
    }

    //------------------------------------------------------------------
    private void setDisplayData(PersonaContrato nuevo)
    {
        jbMessage.setText("Nuevo agregado");
        bsContrato.addRow(nuevo);
        printRecordCount();
    }

    //------------------------------------------------------------------
    @Override
    public JornadaComboBoxModel obtenerComboJornadas()
    {
        return bsJornadaEditor;
    }

    //------------------------------------------------------------------
    @Override
    public PersonalComboBoxModel obtenerComboPersonas()
    {
        return bsPersonasEditor;
    }

    //------------------------------------------------------------------
    @Override
    public AreaComboBoxModel obtenerComboAreas()
    {
        return bsAreasEditor;
    }

    @Override
    public PuestoComboBoxModel obtenerComboPuestos()
    {
        return bsPuestosEditor;
    }

    //------------------------------------------------------------------
    private void resetCurrentElement()
    {
        jbMessage.setText("Cambios guardados");
        bsContrato.fireTableRowsUpdated(jtbData.getSelectedRow(), jtbData.getSelectedRow());
    }

    //----------------------------------------------------------------
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
        public List<Object> doInBackground()
        {
            setProgress(50);
            if (!isCancelled() && arguments != null && arguments.size() > 0)
            {
                int opcion = (int) arguments.get(0); //Debe haber un entero en la primera posición
                switch (opcion)
                {
                    case opLOAD:
                        arguments.add(personas.obtenerListaPersonalPorTipo((TipoPersona) arguments.get(1)));
                        arguments.add(areas.obtenerLista((Valor) arguments.get(2)));
                        arguments.add(puestos.obtenerLista());
                        arguments.add(jornadas.obtenerLista());
                        arguments.add(personaContratos.obtenerLista());
                        break;

                    case opINSERT:
                        arguments.add(personaContratos.agregar((PersonaContrato) arguments.get(1)));
                        break;

                    case opUPDATE:
                        arguments.add(personaContratos.actualizar((PersonaContrato) arguments.get(1)));
                        break;

                    case opDELETE_LIST:
                        arguments.add(personaContratos.borrar((List<PersonaContrato>) arguments.get(1)));
                        break;

                    case opSAVE:
                        List<PersonaContrato> agregados = (List<PersonaContrato>) arguments.get(1);
                        List<PersonaContrato> modificados = (List<PersonaContrato>) arguments.get(2);
                        if (agregados.size() > 0)
                            arguments.add(personaContratos.agregar(agregados));
                        if (modificados.size() > 0)
                            arguments.add(personaContratos.actualizar(modificados));
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
                                List<Personal> listPersons = (List<Personal>) arguments.get(3);
                                List<Valor> listAreas = (List<Valor>) arguments.get(4);
                                List<Puesto> listPuesto = (List<Puesto>) arguments.get(5);
                                List<Jornada> listJornada = (List<Jornada>) arguments.get(6);
                                List<PersonaContrato> listContracts = (List<PersonaContrato>) arguments.get(7);
                                crearTiposContratos();
                                setDisplayData(listPersons, listAreas, listPuesto, listJornada, listContracts);
                                break;

                            case opINSERT:
                                if (personaContratos.esCorrecto())
                                    setDisplayData((PersonaContrato) arguments.get(1));
                                break;

                            case opUPDATE:
                                if (personaContratos.esCorrecto())
                                    resetCurrentElement();
                                break;

                            case opDELETE_LIST:
                                if (personaContratos.esCorrecto())
                                {
                                    bsContrato.removeRows((int[]) arguments.get(2));
                                     printRecordCount();
                                
                                    jbMessage.setText("Elemento(s) eliminado(s)");
                                }
                                else
                                    jbMessage.setText(personaContratos.getEsquema());
                                break;

                            case opSAVE:
                                jbMessage.setText(personaContratos.getMensaje());
                                if (personaContratos.esCorrecto())
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
