package com.syswave.forms.miempresa;

import com.syswave.entidades.common.Entidad;
import com.syswave.entidades.configuracion.Usuario_tiene_Permiso;
import com.syswave.entidades.miempresa.PersonaAtributo;
import com.syswave.entidades.miempresa.PersonaDireccion;
import com.syswave.entidades.miempresa.PersonaEducacion;
import com.syswave.entidades.miempresa.PersonaFoto;
import com.syswave.entidades.miempresa.PersonaIdentificador;
import com.syswave.entidades.miempresa.PersonaTieneIncidencia;
import com.syswave.entidades.miempresa_vista.Personal;
import com.syswave.entidades.miempresa_vista.PersonalTieneFamiliar;
import com.syswave.entidades.miempresa.TipoPersona;
import com.syswave.entidades.miempresa_vista.AnaliticaVista;
import com.syswave.entidades.miempresa.Valor;
import com.syswave.entidades.miempresa_vista.PersonaDetalle_5FN;
import com.syswave.entidades.miempresa_vista.PersonaIdentificadorVista;
import com.syswave.forms.common.JTableDataView;
import com.syswave.swing.models.POJOTableModel;
import com.syswave.forms.databinding.EstadoCivilComboBoxModel;
import com.syswave.forms.databinding.ValorComboBoxModel;
import com.syswave.forms.databinding.PersonalTableModel;
import com.syswave.forms.databinding.TipoPersonasComboBoxModel;
import com.syswave.swing.table.editors.LookUpComboBoxTableCellEditor;
import com.syswave.swing.table.renders.LookUpComboBoxTableCellRenderer;
import com.syswave.logicas.miempresa.PersonaAtributosBusinessLogic;
import com.syswave.logicas.miempresa.PersonaDetalleBusinessLogic;
import com.syswave.logicas.miempresa.PersonaDireccionesBusinessLogic;
import com.syswave.logicas.miempresa.PersonaEducacionesBusinessLogic;
import com.syswave.logicas.miempresa.PersonaFotosBusinessLogic;
import com.syswave.logicas.miempresa.PersonaIdentificadoresBusinessLogic;
import com.syswave.logicas.miempresa.PersonaIncidenciasBusinessLogic;
import com.syswave.logicas.miempresa.PersonalTieneFamiliarBussinesLogic;
import com.syswave.logicas.miempresa.PersonasBusinessLogic;
import com.syswave.logicas.miempresa.TipoPersonasBusinessLogic;
import com.toedter.calendar.JDateChooserCellEditor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import com.syswave.forms.common.IWindowContainer;

/**
 *
 * @author Gilberto Aaron Jimenez Montelongo
 */
public class JPersonalDataView extends JTableDataView implements IPersonalMediator, TableModelListener
{

    private final int opLOAD = 0;
    private final int opINSERT_LIST = 1;
    private final int opUPDATE_LIST = 2;
    private final int opDELETE_LIST = 3;
    private final int opINSERT = 4;
    private final int opUPDATE = 5;
    private final int opSAVE = 6;

    private HSSFWorkbook wb;
    private HSSFSheet sheet;
    private HSSFRow rowTitulos;
    private HSSFRow rowCuerpo;
    private HSSFCellStyle style1;
    private HSSFCellStyle style2;
    private HSSFFont font1;
    private HSSFFont font2;
    private String[] titulos;
    private boolean can_browse, can_create, can_update, can_delete;

    private TipoPersonasComboBoxModel bsPersonalRender;
    private TipoPersonasComboBoxModel bsPersonalEditor;

    private ValorComboBoxModel bsGeneroRender;
    private ValorComboBoxModel bsGeneroEditor;

    private EstadoCivilComboBoxModel bsEstadoCivilRender;
    private EstadoCivilComboBoxModel bsEstadoCivilEditor;

    private PersonalTableModel bsPersonas;
    private PersonasBusinessLogic personas;
    private TipoPersonasBusinessLogic tipoPersonas;
    private PersonaDireccionesBusinessLogic direcciones;
    private PersonaIdentificadoresBusinessLogic identificadores;
    private PersonaFotosBusinessLogic fotos;
    private PersonaEducacionesBusinessLogic formaciones;
    private PersonaAtributosBusinessLogic atributos;
    private PersonaDetalleBusinessLogic detalles;
    private PersonaIncidenciasBusinessLogic incidencias;
    private PersonalTieneFamiliarBussinesLogic familiares;

    private JPopupMenu Pmenu;
    private JMenuItem menuItem;
    private TableColumn colNombres, colApellidos, colTipoPersona, colFechaNacimiento,
            colActivo, colNacionalidad, colReligion, colSexo, colEstadoCivil,
            colNoEmpleado;
    private PersonalSwingWorker swSecondPlane;
    private List<Usuario_tiene_Permiso> permisos;

    //---------------------------------------------------------------------
    private void grantAllPermisions()
    {
        can_browse = true;
        can_create = true;
        can_update = true;
        can_delete = true;
    }

    // -------------------------------------------------------------------
    public JPersonalDataView(IWindowContainer container)
    {
        super(container);
        initAttributes();
        grantAllPermisions();
        initEvents();
    }

    // -------------------------------------------------------------------
    public JPersonalDataView(IWindowContainer container, List<Usuario_tiene_Permiso> values)
    {
        super(container);
        initAttributes();
        grant(values);
        initEvents();
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

    //---------------------------------------------------------------------
    private void initAttributes()
    {
        bsPersonas = new PersonalTableModel(new String[]
        {
            "No.Empleado:{no_empleado}", "Nombre(s):{nombres}",
            "Apellido(s):{apellidos}", "Nacimiento:{nacimiento}",
            "Relación de negocio:{id_tipo_persona}", "Nacionalidad:{nacionalidad}",
            "Religión:{religion}", "Sexo:{es_genero}", "Estado Civil:{es_estado_civil}",
            "Activo:{es_activo}"/*, "Tipo de Sangre:{tipo_sangre}", "Altura:{altura}",
         "Peso:{peso}"*/

        });

        titulos = new String[]
        {
            "No.Empleado", "Nombre", "Departamento", "Puesto", "Fecha Ingreso",
            "# Seguro Social", "RFC", "CURP", "Religión", "Enfermedades/Alergías",
            "Edad", "Fecha de Nacimiento", "Cumpleaños", "Género", "Escolaridad",
            "Antigüedad", "Días de vacaciones", "Dirección", "Lugar de procedencia",
            "Teléfono", "Tipo de sangre", "# Hijos", "Tiene hijos", "Edo. civil",
            "Áreas de interés", "Habilidades", "Aficiones", "Cursos", "Tiempo",
            "Nombre y referencias", "Vacaciones (Le corresponden y cuantas ha tomado)",
            "Reportes", "Examen médico", "Planta", "Cambios de personal", "Papelería faltante"
        };

        /*titulos = new String[]
         {
         "No.Empleado", "Nombre", "Fecha de Nacimiento", "Edad",
         "Cumpleaños", "Nacionalidad", "Departamento", "Seguro Social", "RFC",
         "CURP", "Formacion", "Escolaridad", "Direccion", "Lugar de Procedencia",
         "Tel_Casa", "Religion", "Tipo de Sangre", "Genero", "Estado Civil",
         "Intereses", "Habilidades", "Pasatiempos", "Contacto de emergencia",
         "Otros Atributos", "Padecimientos y/o Alergias"
         };*/
        bsPersonas.addTableModelListener(this);
        bsPersonalRender = new TipoPersonasComboBoxModel();
        bsPersonalEditor = new TipoPersonasComboBoxModel();
        bsGeneroRender = new ValorComboBoxModel();
        bsGeneroEditor = new ValorComboBoxModel();
        Pmenu = new JPopupMenu();
        menuItem = new JMenuItem("Exportar a Excell");

        bsEstadoCivilRender = new EstadoCivilComboBoxModel();
        bsEstadoCivilEditor = new EstadoCivilComboBoxModel();

        personas = new PersonasBusinessLogic(mainContainer.getOrigenDatoActual().getNombre());
        tipoPersonas = new TipoPersonasBusinessLogic(personas.getEsquema());
        direcciones = new PersonaDireccionesBusinessLogic(personas.getEsquema());
        identificadores = new PersonaIdentificadoresBusinessLogic(personas.getEsquema());
        fotos = new PersonaFotosBusinessLogic(personas.getEsquema());
        formaciones = new PersonaEducacionesBusinessLogic(personas.getEsquema());
        atributos = new PersonaAtributosBusinessLogic(personas.getEsquema());
        detalles = new PersonaDetalleBusinessLogic(personas.getEsquema());
        incidencias = new PersonaIncidenciasBusinessLogic(personas.getEsquema());
        familiares= new PersonalTieneFamiliarBussinesLogic();
        jtbData.setModel(bsPersonas);
        jtbData.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        if (jtbData.getColumnCount() > 0)
        {
            colNoEmpleado = jtbData.getColumnModel().getColumn(0);
            colNoEmpleado.setPreferredWidth(100);

            colNombres = jtbData.getColumnModel().getColumn(1);
            colNombres.setPreferredWidth(120);

            colApellidos = jtbData.getColumnModel().getColumn(2);
            colApellidos.setPreferredWidth(120);

            colFechaNacimiento = jtbData.getColumnModel().getColumn(3);
            colFechaNacimiento.setCellEditor(new JDateChooserCellEditor());
            colFechaNacimiento.setPreferredWidth(100);

            colTipoPersona = jtbData.getColumnModel().getColumn(4);
            colTipoPersona.setCellRenderer(new LookUpComboBoxTableCellRenderer<TipoPersona>(bsPersonalRender));
            colTipoPersona.setCellEditor(new LookUpComboBoxTableCellEditor<TipoPersona>(bsPersonalEditor));
            colTipoPersona.setPreferredWidth(200);

            colNacionalidad = jtbData.getColumnModel().getColumn(5);
            colNacionalidad.setPreferredWidth(100);

            colReligion = jtbData.getColumnModel().getColumn(6);
            colReligion.setPreferredWidth(100);

            colSexo = jtbData.getColumnModel().getColumn(7);
            colSexo.setCellRenderer(new LookUpComboBoxTableCellRenderer<Valor>(bsGeneroRender));
            colSexo.setCellEditor(new LookUpComboBoxTableCellEditor<Valor>(bsGeneroEditor));
            colSexo.setPreferredWidth(80);

            colEstadoCivil = jtbData.getColumnModel().getColumn(8);
            colEstadoCivil.setCellRenderer(new LookUpComboBoxTableCellRenderer<Valor>(bsEstadoCivilRender));
            colEstadoCivil.setCellEditor(new LookUpComboBoxTableCellEditor<Valor>(bsEstadoCivilEditor));
            colEstadoCivil.setPreferredWidth(100);

            colActivo = jtbData.getColumnModel().getColumn(9);
            colActivo.setPreferredWidth(80);

            //Nota: Debido a los renders que se estan utilizando es necesario tener un renglón más alto.
            jtbData.setRowHeight((int) (jtbData.getRowHeight() * 1.5));
        }
    }

    private void initEvents()
    {
        menuItem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                doExportToXLS();
            }
        });
        Pmenu.add(menuItem);
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

    // -------------------------------------------------------------------
    @Override
    public void showDetail(Personal elemento)
    {
        JPersonalDetailView dialogo = new JPersonalDetailView(this);
        mainContainer.addWindow(dialogo);

        if (elemento != null)
            dialogo.prepareForModify(elemento);

        else
            dialogo.prepareForNew();

        mainContainer.showCenter(dialogo);
    }

    // -------------------------------------------------------------------
    @Override
    public void onAcceptNewElement(Personal nuevo, List<PersonaDireccion> direcciones,
                                   List<PersonaIdentificadorVista> lstIdentificadores,
                                   List<PersonaIdentificador> lstMedios,
                                   PersonaFoto foto,
                                   List<PersonaEducacion> listaFormacion,
                                   List<PersonaAtributo> listaAtributos,
                                   List<PersonaDetalle_5FN> listaDetalles,
                                   List<PersonaTieneIncidencia> listaIncidencias,
                                   List<PersonalTieneFamiliar> listaFamilia    )
    {
        if (swSecondPlane == null || swSecondPlane.isDone())
        {
            List<Object> parametros = new ArrayList<Object>();
            swSecondPlane = new PersonalSwingWorker();
            swSecondPlane.addPropertyChangeListener(this);
            parametros.add(opINSERT);
            parametros.add(nuevo);
            parametros.add(direcciones);
            parametros.add(lstIdentificadores);
            parametros.add(lstMedios);
            parametros.add(foto);
            parametros.add(listaFormacion);
            parametros.add(listaAtributos);
            parametros.add(listaDetalles);
            parametros.add(listaIncidencias);
            parametros.add(listaFamilia);

            jbMessage.setText("Guardando...");
            swSecondPlane.execute(parametros);
        }

    }

    // -------------------------------------------------------------------
    @Override
    public void onAcceptModifyElement(Personal modificado,
                                      List<PersonaDireccion> direcciones,
                                      List<PersonaDireccion> direcciones_borradas,
                                      List<PersonaIdentificadorVista> lstIdentificadores,
                                      List<PersonaIdentificador> lstMedios,
                                      List<PersonaIdentificador> lstMedios_borrados,
                                      PersonaFoto foto, PersonaFoto foto_borrada,
                                      List<PersonaEducacion> listaFormacion,
                                      List<PersonaEducacion> listaFormacion_borrado,
                                      List<PersonaAtributo> listaAtributos,
                                      List<PersonaAtributo> listaAtributos_borrado,
                                      List<PersonaDetalle_5FN> listaDetalles,
                                      List<PersonaTieneIncidencia> listaIncidencias,
                                      List<PersonaTieneIncidencia> listaIncidencias_borrado,
                                      List<PersonalTieneFamiliar> listaFamiliar,
                                      List<PersonalTieneFamiliar> listaFamiliar_borrado
                                      )
    {
        if (swSecondPlane == null || swSecondPlane.isDone())
        {
            List<Object> parametros = new ArrayList<Object>();
            swSecondPlane = new PersonalSwingWorker();
            swSecondPlane.addPropertyChangeListener(this);
            parametros.add(opUPDATE);
            parametros.add(modificado);
            parametros.add(direcciones);
            parametros.add(direcciones_borradas);
            parametros.add(lstIdentificadores);
            parametros.add(lstMedios);
            parametros.add(lstMedios_borrados);
            parametros.add(foto);
            parametros.add(foto_borrada);
            parametros.add(listaFormacion);
            parametros.add(listaFormacion_borrado);
            parametros.add(listaAtributos);
            parametros.add(listaAtributos_borrado);
            parametros.add(listaDetalles);
            parametros.add(listaIncidencias);
            parametros.add(listaIncidencias_borrado);
            parametros.add(listaFamiliar);
            parametros.add(listaFamiliar_borrado);

            jbMessage.setText("Guardando...");
            swSecondPlane.execute(parametros);
        }
    }

    //---------------------------------------------------------------------
    private void resetElement(Personal value)
    {
        value.acceptChanges();
        bsPersonas.fireTableRowsUpdated(jtbData.getSelectedRow(), jtbData.getSelectedRow());
        jbMessage.setText("Cambio guardado");
    }

    //---------------------------------------------------------------------
    @Override
    public void doExportToXLS()
    {
        String[] choices =
        {
            "Normal", "Vista Analitica"
        };
        String seleccion = (String) JOptionPane.showInputDialog(null, "Escoge un Formato",
                                                                "Seleccion de formatos", JOptionPane.QUESTION_MESSAGE, null, choices, choices[1]);

        if (seleccion != null)
        { // parent component of the dialog

            switch (seleccion)
            {
                case "Normal":
                    super.doExportToXLS(); // doExport(ruta);
                    break;
                case "Vista Analitica":
                    vistaAnaliticaExport();
                    break;
            }

        }
    }

    // -------------------------------------------------------------------
    private void vistaAnaliticaExport()
    {
        List<AnaliticaVista> arguments = null;
        String ruta, noEmp = "";

        fileChooser.setDialogTitle("Donde quiere guardar el archivo");
        fileChooser.setCurrentDirectory(null);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int userSelection = fileChooser.showSaveDialog(mainContainer.getRootFrame());
        wb = new HSSFWorkbook();
        sheet = wb.createSheet("Inventario de Personal");
        HSSFCell cell;
        font1 = wb.createFont();
        font2 = wb.createFont();
        FileOutputStream fileOut;
        int i, j;

        font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        rowTitulos = sheet.createRow(0);
        rowTitulos.setHeightInPoints((short) 20);
        style1 = wb.createCellStyle();
        style1.setFillForegroundColor(HSSFColor.LIGHT_ORANGE.index);
        style1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        font1.setFontHeightInPoints((short) 15);
        style1.setFont(font1);
        rowTitulos.setRowStyle(style1);

        style2 = wb.createCellStyle();
        style2.setFillForegroundColor(HSSFColor.WHITE.index);
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        font2.setFontHeightInPoints((short) 11);
        style2.setFont(font2);
        CellStyle cellStyle = wb.createCellStyle();
        CreationHelper createHelper = wb.getCreationHelper();
        cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("MMMM dd, yyyy"));

        if (userSelection == JFileChooser.APPROVE_OPTION)
        {
            File fileToSave = fileChooser.getSelectedFile();

            ruta = (fileToSave.getAbsolutePath().endsWith(".xls")) ? fileToSave.getAbsolutePath() : fileToSave.getAbsolutePath() + ".xls";

            if (ruta != null)
            {
                // List<Object> results = new ArrayList<>();
                //results.add(opcion);
                arguments = personas.obtenerVistaAnalitica((Personal) arguments, noEmp);
                for (i = 0; i < titulos.length; i++)
                {
                    cell = rowTitulos.createCell(i);
                    cell.setCellStyle(style1);
                    cell.setCellValue(titulos[i]);
                    sheet.autoSizeColumn(i);
                }

                for (j = 0; j < arguments.size(); j++)
                {
                    int anios = arguments.get(j).getAntiguedad() / 12;
                    int meses = arguments.get(j).getAntiguedad() % 12;

                    rowCuerpo = sheet.createRow(j + 1);
                    rowCuerpo.setHeightInPoints((short) 12);

                    rowCuerpo.createCell(0).setCellValue(arguments.get(j).getNoEmpleado());
                    rowCuerpo.createCell(1).setCellValue(arguments.get(j).getNombreCompleto());
                    rowCuerpo.createCell(2).setCellValue(arguments.get(j).getDepartamento());
                    rowCuerpo.createCell(3).setCellValue(arguments.get(j).getPuesto());
                    rowCuerpo.createCell(4).setCellValue(arguments.get(j).getFechaIngreso());
                    rowCuerpo.createCell(5).setCellValue(arguments.get(j).getNss());
                    rowCuerpo.createCell(6).setCellValue(arguments.get(j).getRfc());
                    rowCuerpo.createCell(7).setCellValue(arguments.get(j).getCurp());
                    rowCuerpo.createCell(8).setCellValue(arguments.get(j).getReligion());
                    rowCuerpo.createCell(9).setCellValue(arguments.get(j).getPadecimientosOAlergias());
                    rowCuerpo.createCell(10).setCellValue(arguments.get(j).getEdad());
                    rowCuerpo.createCell(11).setCellValue(arguments.get(j).getNacimiento());
                    rowCuerpo.createCell(12).setCellValue(arguments.get(j).getCumpleaños());
                    rowCuerpo.createCell(13).setCellValue(arguments.get(j).getGenero());
                    rowCuerpo.createCell(14).setCellValue(arguments.get(j).getEscolaridad());
                    rowCuerpo.createCell(15).setCellValue(anios + " año(s) " + meses + " mes(es)");
                    rowCuerpo.createCell(17).setCellValue(arguments.get(j).getDirecciones());
                    rowCuerpo.createCell(18).setCellValue(arguments.get(j).getLugarDeProcedencia());
                    rowCuerpo.createCell(19).setCellValue(arguments.get(j).getTelCasa());
                    rowCuerpo.createCell(20).setCellValue(arguments.get(j).getTipoSangre());
                    rowCuerpo.createCell(23).setCellValue(arguments.get(j).getEstadoCivil());
                    rowCuerpo.createCell(24).setCellValue(arguments.get(j).getIntereses());
                    rowCuerpo.createCell(25).setCellValue(arguments.get(j).getHabilidades());
                    rowCuerpo.createCell(26).setCellValue(arguments.get(j).getPasatiempos());
                    rowCuerpo.createCell(27).setCellValue(arguments.get(j).getFormacion());
                    rowCuerpo.createCell(28).setCellValue(arguments.get(j).getDuracion());
                    rowCuerpo.createCell(31).setCellValue(arguments.get(j).getReportes());
                    rowCuerpo.createCell(33).setCellValue(arguments.get(j).getPlanta());

                    try
                    {
                        fileOut = new FileOutputStream(ruta);
                        wb.write(fileOut);
                        fileOut.close();
                    }
                    catch (FileNotFoundException ex)
                    {
                        JOptionPane.showMessageDialog(this, ex.getMessage());
                    }
                    catch (IOException ex)
                    {
                        JOptionPane.showMessageDialog(this, ex.getMessage());
                    }
                }
            }
        }

    }

    // -------------------------------------------------------------------
    @Override
    public List<TipoPersona> obtenerTipoPersonas()
    {
        return bsPersonalEditor.getData();
    }

    // -------------------------------------------------------------------
    @Override
    public List<PersonaDireccion> obtenerDirecciones(Personal elemento
    )
    {
        PersonaDireccion filtro = new PersonaDireccion();
        filtro.setIdPersona(elemento.getIdPersona_Viejo());

        return direcciones.obtenerLista(filtro);
    }

    //--------------------------------------------------------------------
    @Override
    public List<PersonaIdentificadorVista> obtenerIdentificadores(Personal elemento
    )
    {
        return identificadores.obtenerListaVista(elemento.getIdPersona());
    }

    //--------------------------------------------------------------------
    @Override
    public List<PersonaFoto> obtenerFotos(Personal elemento
    )
    {
        PersonaFoto filtro = new PersonaFoto();
        filtro.setIdPersona(elemento.getIdPersona());
        filtro.setConsecutivo(1); //Solo devolvemos la primera imagen.

        return fotos.obtenerMiniaturas(filtro);
    }

    //--------------------------------------------------------------------
    @Override
    public List<Valor> obtenerTipoMedios()
    {
        return identificadores.obtenerTiposMedio();
    }

    //--------------------------------------------------------------------
    @Override
    public List<PersonaIdentificador> obtenerMedios(Personal elemento
    )
    {
        PersonaIdentificador filtro = new PersonaIdentificador();
        filtro.setClave("");
        filtro.setIdPersona(elemento.getIdPersona());

        return identificadores.obtenerListaMedios(filtro);
    }

    //--------------------------------------------------------------------
    @Override
    public List<PersonaEducacion> obtenerFormacion(Personal elemento
    )
    {
        PersonaEducacion filtro = new PersonaEducacion();
        filtro.setIdPersona(elemento.getIdPersona());
        filtro.acceptChanges();

        return formaciones.obtenerLista(filtro);
    }

    //--------------------------------------------------------------------
    @Override
    public List<PersonaAtributo> obtenerAtributos(Personal elemento
    )
    {
        PersonaAtributo filtro = new PersonaAtributo();
        filtro.setIdPersona(elemento.getIdPersona());
        filtro.setEsTipo(3); //Todo lo inferior a 3 son atributos.

        return atributos.obtenerListaSinPadecimientos(filtro);
    }

    //--------------------------------------------------------------------
    @Override
    public List<PersonaAtributo> obtenerPadecimientos(Personal elemento)
    {
        PersonaAtributo filtro = new PersonaAtributo();
        filtro.setIdPersona(elemento.getIdPersona());
        filtro.setEsTipo(2); //Todo lo superior al tipo 2, son padecimientos.

        return atributos.obtenerListaPadecimientos(filtro);
    }

    //--------------------------------------------------------------------
    @Override
    public List<PersonaDetalle_5FN> obtenerCaracteristicas(Personal elemento)
    {
        return detalles.obtenerListaVista(elemento.getIdPersona());
    }

    //--------------------------------------------------------------------
    @Override
    public List<PersonaTieneIncidencia> obtenerIncidencias(Personal elemento)
    {
        PersonaTieneIncidencia filtro = new PersonaTieneIncidencia();
        filtro.setIdPersona(elemento.getIdPersona());

        return incidencias.obtenerLista(filtro);
    }
    //--------------------------------------------------------------------------
    @Override
    public List<PersonalTieneFamiliar> obtenerFamiliares(PersonalTieneFamiliar elemento)
    {
        return familiares.obtenerLista(elemento);
    }
    
    //--------------------------------------------------------------------------
    @Override
    public List<Personal> obtenerSoloPersonal(Personal filtro)
    {
       return personas.obtenerListaPersonalExcluyente(filtro);
    }

    //--------------------------------------------------------------------

    @Override
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
                    swSecondPlane = new PersonalSwingWorker();
                    swSecondPlane.addPropertyChangeListener(this);
                    parametros.add(opDELETE_LIST);
                    int[] rowsHandlers = jtbData.getSelectedRows();
                    for (int i = 0; i < rowsHandlers.length; i++)
                    {
                        rowsHandlers[i] = jtbData.convertRowIndexToModel(rowsHandlers[i]);
                    }
                    parametros.add(bsPersonas.getElementsAt(rowsHandlers));
                    parametros.add(rowsHandlers);
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
                jbMessage.setText("Obteniendo inventario de personal...");
                swSecondPlane = new PersonalSwingWorker();
                swSecondPlane.addPropertyChangeListener(this);
                TipoPersona filtro = new TipoPersona();
                filtro.setNombre("");
                filtro.setSiglas("");
                filtro.setActivo(true);
                //filtro.setUsaMantenimiento(false);
                filtro.setUsaPersonal(true);
                parametros.add(opLOAD);
                parametros.add(filtro);
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
        List<Personal> modificados = new ArrayList<>();
        List<Personal> agregados = new ArrayList<>();
        List<Personal> datos = bsPersonas.getData();

        TableCellEditor editor = jtbData.getCellEditor();
        if (editor != null)
            editor.stopCellEditing();

        for (Personal elemento : datos)
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
                swSecondPlane = new PersonalSwingWorker();
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
    private void setDisplayData(List<TipoPersona> tipos, List<Personal> values)
    {
        bsPersonalRender.setData(tipos);
        bsPersonalEditor.setData(tipos);
        bsPersonas.setData(values);

        printRecordCount();
        jbMessage.setText("Inventario de personal obtenido");
    }

    //---------------------------------------------------------------------
    private void setDisplayData(Personal value)
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

                setChanges(); //Reportamos cambios pendientes
                jbMessage.setText("Cambios pendientes");
            }
        }
    }

    //---------------------------------------------------------------------
    private void cargarGeneros()
    {
        List<Valor> lista = new ArrayList<>();
        lista.add(new Valor(0, "Masculino"));
        lista.add(new Valor(1, "Femenino"));

        bsGeneroEditor.setData(lista);
        bsGeneroRender.setData(lista);
    }

    //---------------------------------------------------------------------
    private void cargarEstadoCivil()
    {
        List<Valor> lista = new ArrayList<>();
        lista.add(new Valor(0, "Soltero"));
        lista.add(new Valor(1, "Casado"));
        lista.add(new Valor(2, "Divorciado"));
        lista.add(new Valor(3, "Viudo"));
        lista.add(new Valor(4, "Union Libre"));

        bsEstadoCivilRender.setData(lista);
        bsEstadoCivilEditor.setData(lista);
    }

    @Override
    public List<Valor> obtenerEstadoCivil()
    {
        return bsEstadoCivilEditor.getData();

    }

    

    

    

    /*private void doExport(String ruta)
     {

     wb = new HSSFWorkbook();
     sheet = wb.createSheet("Inventario de Personal");
     HSSFCell cell;
     font1 = wb.createFont();
     font2 = wb.createFont();
     FileOutputStream fileOut = null;
     int i, j;

     font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
     rowTitulos = sheet.createRow(0);
     rowTitulos.setHeightInPoints((short) 20);
     style1 = wb.createCellStywble();
     style1.setFillForegroundColor(HSSFColor.LIGHT_ORANGE.index);
     style1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
     font1.setFontHeightInPoints((short) 15);
     style1.setFont(font1);

     for (i = 0; i < jtbData.getColumnCount(); i++)
     {
     cell = rowTitulos.createCell(i);
     cell.setCellStyle(style1);
     cell.setCellValue(jtbData.getColumnName(i));
     sheet.autoSizeColumn(i);

     }

     style2 = wb.createCellStyle();
     style2.setFillForegroundColor(HSSFColor.WHITE.index);
     style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
     font2.setFontHeightInPoints((short) 11);
     style2.setFont(font2);
     for (j = 0; j < jtbData.getRowCount(); j++)
     {
     rowCuerpo = sheet.createRow(j + 1);
     rowCuerpo.setHeightInPoints((short) 12);
     for (i = 0; i < jtbData.getColumnCount(); i++)
     {
     cell = rowCuerpo.createCell(i);
     cell.setCellStyle(style2);

                
     switch (jtbData.getColumnName(i))
     {
                    

     case "Sexo":
     cell.setCellValue(bsGeneroRender.getElementAt(bsGeneroEditor.indexOfValue(jtbData.getValueAt(j, i))).getText());
     sheet.autoSizeColumn(i);

     break;

     case "Estado Civil":
     cell.setCellValue(((int)jtbData.getValueAt(j, i)==-1)?"No Especificado" : bsEstadoCivilRender.getElementAt(bsEstadoCivilEditor.indexOfValue(jtbData.getValueAt(j, i))).getDescripcion());
     sheet.autoSizeColumn(i);

     break;

     case "Activo":
     cell.setCellValue((bsPersonas.getValueAt(j, i).toString() == "true") ? "Activo" : "Inactivo");
     sheet.autoSizeColumn(i);

     break;

     case "Relación de negocio":
     cell.setCellValue(bsPersonalRender.getElementAt(bsPersonalEditor.indexOfValue(jtbData.getValueAt(j, i))).getNombre());
     sheet.autoSizeColumn(i);

     break;

     default:
     cell.setCellValue(jtbData.getValueAt(j, i).toString());
     sheet.autoSizeColumn(i);

     break;
     }
     }
     }

     try
     {
     fileOut = new FileOutputStream(ruta);
     } catch (FileNotFoundException ex)
     {
     JOptionPane.showMessageDialog(this, "No se pudo crear el libro");
     }

     try
     {
     wb.write(fileOut);
     fileOut.close();
     } catch (IOException ex)
     {
     JOptionPane.showMessageDialog(this, "No se pudo crear el libro");
     }

     }*/
    //---------------------------------------------------------------------
    //---------------------------------------------------------------------
    //--  Esta clase controla el cargado en segundo plano de los origenes de datos.
    //---------------------------------------------------------------------
    private class PersonalSwingWorker extends SwingWorker<List<Object>, Void>
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
                        results.add(personas.obtenerListaPersonalPorTipo((TipoPersona) arguments.get(1)));
                        results.add(tipoPersonas.obtenerListaHojas());
                        break;

                    case opINSERT:
                        results.add(personas.agregar((Personal) arguments.get(1)));
                        results.add(direcciones.guardar((List<PersonaDireccion>) arguments.get(2),
                                                        null));
                        results.add(identificadores.guardar((List<PersonaIdentificadorVista>) arguments.get(3)));
                        results.add(identificadores.guardar((List<PersonaIdentificador>) arguments.get(4), null));
                        results.add(fotos.guardar((PersonaFoto) arguments.get(5), null));
                        results.add(formaciones.guardar((List<PersonaEducacion>) arguments.get(6), null));
                        results.add(atributos.guardar((List<PersonaAtributo>) arguments.get(7), null));
                        results.add(detalles.guardar((List<PersonaDetalle_5FN>) arguments.get(8)));
                        results.add(incidencias.guardar((List<PersonaTieneIncidencia>) arguments.get(9)));
                        results.add(familiares.guardar((List<PersonalTieneFamiliar>) arguments.get(10)));
                        break;

                    case opUPDATE:
                        results.add(personas.actualizar((Personal) arguments.get(1)));
                        results.add(direcciones.guardar((List<PersonaDireccion>) arguments.get(2),
                                                        (List<PersonaDireccion>) arguments.get(3)));
                        results.add(identificadores.guardar((List<PersonaIdentificadorVista>) arguments.get(4)));
                        results.add(identificadores.guardar((List<PersonaIdentificador>) arguments.get(5),
                                                            (List<PersonaIdentificador>) arguments.get(6)));
                        results.add(fotos.guardar((PersonaFoto) arguments.get(7), (PersonaFoto) arguments.get(8)));
                        results.add(formaciones.guardar((List<PersonaEducacion>) arguments.get(9),
                                                        (List<PersonaEducacion>) arguments.get(10)));
                        results.add(atributos.guardar((List<PersonaAtributo>) arguments.get(11),
                                                      (List<PersonaAtributo>) arguments.get(12)));
                        results.add(detalles.guardar((List<PersonaDetalle_5FN>) arguments.get(13)));
                        results.add(incidencias.guardar((List<PersonaTieneIncidencia>) arguments.get(14),
                                                        (List<PersonaTieneIncidencia>) arguments.get(15)));
                        results.add(familiares.guardar((List<PersonalTieneFamiliar>) arguments.get(16), 
                                                       (List<PersonalTieneFamiliar>) arguments.get(17)));
                        break;

                    case opDELETE_LIST:
                        List<Personal> selecteds = (List<Personal>) arguments.get(1);
                        results.add(personas.borrarPersonal(selecteds));
                        break;

                    case opSAVE:
                        List<Personal> adds = (List<Personal>) arguments.get(1);
                        List<Personal> modifieds = (List<Personal>) arguments.get(2);
                        if (adds.size() > 0)
                            results.add(personas.agregarPersonal(adds));
                        if (modifieds.size() > 0)
                            results.add(personas.actualizarPersonal(modifieds));
                        break;

                    default:
                        results.add(personas.obtenerListaPersonal());
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
                                List<Personal> listaUsuarios = (List<Personal>) results.get(1);
                                List<TipoPersona> listaTipos = (List<TipoPersona>) results.get(2);
                                if (listaTipos.size() > 0)
                                {
                                    cargarGeneros();
                                    cargarEstadoCivil();
                                    setDisplayData(listaTipos, listaUsuarios);
                                }
                                else if (!tipoPersonas.esCorrecto())
                                    JOptionPane.showMessageDialog(null, tipoPersonas.getMensaje());
                                break;

                            case opINSERT:
                                if (personas.esCorrecto())
                                    setDisplayData((Personal) arguments.get(1));

                                else
                                    jbMessage.setText(personas.getMensaje());
                                break;

                            case opUPDATE:
                                if (personas.esCorrecto())
                                    resetElement((Personal) arguments.get(1));

                                else
                                    jbMessage.setText(personas.getMensaje());
                                break;

                            case opDELETE_LIST:
                                if (personas.esCorrecto())
                                {
                                    bsPersonas.removeRows((int[]) arguments.get(2));
                                    printRecordCount();
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
