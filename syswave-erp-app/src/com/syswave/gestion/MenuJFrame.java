package com.syswave.gestion;

import com.syswave.entidades.configuracion.ModuloInstalado;
import com.syswave.entidades.configuracion.OrigenDato;
import com.syswave.entidades.configuracion.Usuario;
import com.syswave.entidades.configuracion.Usuario_tiene_Permiso;
import com.syswave.forms.common.IWindowContainer;
import com.syswave.swing.JDerechosInternalFrame;
import com.syswave.forms.common.JListadoInternalFrame;
import com.syswave.forms.configuracion.JLocalidadDataView;
import com.syswave.forms.configuracion.JUnidadDataView;
import com.syswave.forms.configuracion.JUsuariosDataView;
import com.syswave.forms.miempresa.JBienDataView;
import com.syswave.forms.miempresa.JCategoriaDataView;
import com.syswave.forms.miempresa.JControlAlmacenesDataView;
import com.syswave.forms.miempresa.JControlInventariosDataView;
import com.syswave.forms.miempresa.JControlPreciosDataView;
import com.syswave.forms.miempresa.JDocumentosComercialesDataView;
import com.syswave.forms.miempresa.JMonedasDataView;
import com.syswave.forms.miempresa.JPersonaDataView;
import com.syswave.forms.miempresa.JPersonaTieneExistenciasDataView;
import com.syswave.forms.miempresa.JGrupoBienesDataView;
import com.syswave.forms.miempresa.JTipoPersonasDataView;
import com.syswave.forms.miempresa.JTiposComprobantesDataView;
import com.syswave.forms.miempresa.JUbicacionesDataView;
import com.syswave.swing.JBusinessMenuItem;
import com.syswave.forms.miempresa.JCondicionesDataView;
import com.syswave.forms.miempresa.JAreasPreciosDataView;
import com.syswave.forms.miempresa.JContratoDataView;
import com.syswave.forms.miempresa.JPersonaContratoDataView;
import com.syswave.forms.miempresa.JJornadaDataView;
import com.syswave.forms.miempresa.JMantenimientoDataView;
import com.syswave.forms.miempresa.JMonedaCambioDataView;
import com.syswave.forms.miempresa.JPersonaCreditoCuentaDataView;
import com.syswave.forms.miempresa.JPersonalDataView;
import com.syswave.forms.miempresa.JHorarioDataView;
import com.syswave.forms.miempresa.JPersonaSalariosDataView;
import com.syswave.forms.miempresa.JPuestoDataView;
import com.syswave.forms.miempresa.JValoresDataView;
import com.syswave.logicas.configuracion.ModulosInstaladosBusinessLogic;
import com.syswave.logicas.configuracion.UsuarioTienePermisosBusinessLogic;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

/**
 * Es la pantalla principal
 *
 * @author Victor Manuel BUcio Vargas
 */
public class MenuJFrame extends javax.swing.JFrame implements ActionListener, IWindowContainer
{

    private final int opLOAD_MODULOS = 0;
    private final int opBUSCA_MODULO = 1;
    private final int Usu = 0;
    private final int Rol = 1;
    JListadoInternalFrame Listados;
    Usuario_tiene_Permiso sessionActual;
    ModulosInstaladosBusinessLogic ModulosInstalados;
    UsuarioTienePermisosBusinessLogic UsuarioTienePermisos;

    DeterminarModuloSwingWorker swDeterminarModulo;

    //---------------------------------------------------------------------
    /**
     * Creates new form MenuJFrame
     */
    public MenuJFrame()
    {
        initComponents();
        initEvents();
        setExtendedState(MAXIMIZED_BOTH);
        jlbLocale.setText(java.util.Locale.getDefault().getDisplayName());
        Listados = new JListadoInternalFrame();
        sessionActual = new Usuario_tiene_Permiso();
        ModulosInstalados = new ModulosInstaladosBusinessLogic("configuracion");
        UsuarioTienePermisos = new UsuarioTienePermisosBusinessLogic("configuracion");

    }

    //---------------------------------------------------------------------
    private void initEvents()
    {
        ActionListener actionListenerManager
                = new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent evt)
                    {
                        if (evt.getSource() == jmSalir || evt.getSource() == jmCerrarSession)
                            jmCerrarSession_actionPerformed(evt);

                        else // if (evt.getSource() == jmUsuario)
                            determinarModulo_actionPerformed(evt);
                    }
                };

        jmCerrarSession.addActionListener(actionListenerManager);
        jmSalir.addActionListener(actionListenerManager);
        jmUsuario.addActionListener(actionListenerManager);
        jmRoles.addActionListener(actionListenerManager);
        jmLocalidades.addActionListener(actionListenerManager);
        jmUnidades.addActionListener(actionListenerManager);
    }

    //---------------------------------------------------------------------
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jdpWindowsContainer = new javax.swing.JDesktopPane();
        jpnEstatusBar = new javax.swing.JPanel();
        jlbEstatusConexion = new javax.swing.JLabel();
        jlbUsuario = new javax.swing.JLabel();
        jlbEsquema = new javax.swing.JLabel();
        jlbLocale = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jmArchivo = new javax.swing.JMenu();
        jmControlAcceso = new javax.swing.JMenu();
        jmUsuario = new javax.swing.JMenuItem();
        jmRoles = new javax.swing.JMenuItem();
        jmValoresAdicionales = new javax.swing.JMenu();
        jmLocalidades = new javax.swing.JMenuItem();
        jmUnidades = new javax.swing.JMenuItem();
        jmConfiguracion = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jmCerrarSession = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jmSalir = new javax.swing.JMenuItem();
        jmAcercaDe = new javax.swing.JMenu();
        jmVisorSucesos = new javax.swing.JMenuItem();
        jmAyuda = new javax.swing.JMenuItem();
        jmCreditos = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(800, 600));
        getContentPane().add(jdpWindowsContainer, java.awt.BorderLayout.CENTER);

        jpnEstatusBar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jpnEstatusBar.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));

        jlbEstatusConexion.setText("Conectado");
        jlbEstatusConexion.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        jlbEstatusConexion.setPreferredSize(new java.awt.Dimension(100, 17));
        jpnEstatusBar.add(jlbEstatusConexion);

        jlbUsuario.setText("Administrador");
        jlbUsuario.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        jlbUsuario.setPreferredSize(new java.awt.Dimension(100, 17));
        jpnEstatusBar.add(jlbUsuario);

        jlbEsquema.setText("Empresa");
        jlbEsquema.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        jlbEsquema.setPreferredSize(new java.awt.Dimension(100, 17));
        jpnEstatusBar.add(jlbEsquema);

        jlbLocale.setText("Locale");
        jlbLocale.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        jlbLocale.setPreferredSize(new java.awt.Dimension(200, 17));
        jpnEstatusBar.add(jlbLocale);

        getContentPane().add(jpnEstatusBar, java.awt.BorderLayout.SOUTH);

        jmArchivo.setText("Menu");
        jmArchivo.setName("jmArchivo"); // NOI18N

        jmControlAcceso.setText("Control de acceso");
        jmControlAcceso.setName("jmControlAcceso"); // NOI18N

        jmUsuario.setText("Usuarios");
        jmUsuario.setName("jmArchivo/jmControlAcceso/jmUsuario"); // NOI18N
        jmControlAcceso.add(jmUsuario);

        jmRoles.setText("Roles");
        jmRoles.setName("jmArchivo/jmControlAcceso/jmRoles"); // NOI18N
        jmControlAcceso.add(jmRoles);

        jmArchivo.add(jmControlAcceso);

        jmValoresAdicionales.setText("Valores adicionales");

        jmLocalidades.setText("Localidades");
        jmLocalidades.setName("jmArchivo/jmValoresAdicionales/jmLocalidades"); // NOI18N
        jmValoresAdicionales.add(jmLocalidades);

        jmUnidades.setText("Unidades de medida");
        jmUnidades.setName("jmArchivo/jmValoresAdicionales/jmUnidades"); // NOI18N
        jmValoresAdicionales.add(jmUnidades);

        jmArchivo.add(jmValoresAdicionales);

        jmConfiguracion.setText("Configuración");
        jmConfiguracion.setEnabled(false);
        jmArchivo.add(jmConfiguracion);
        jmArchivo.add(jSeparator1);

        jmCerrarSession.setText("Cerrar session");
        jmArchivo.add(jmCerrarSession);
        jmArchivo.add(jSeparator2);

        jmSalir.setText("Salir");
        jmArchivo.add(jmSalir);

        jMenuBar1.add(jmArchivo);

        jmAcercaDe.setText("Acerca de");

        jmVisorSucesos.setText("Visor de sucesos");
        jmAcercaDe.add(jmVisorSucesos);

        jmAyuda.setText("Ayuda");
        jmAcercaDe.add(jmAyuda);

        jmCreditos.setText("Creditos");
        jmCreditos.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jmCreditosActionPerformed(evt);
            }
        });
        jmAcercaDe.add(jmCreditos);

        jMenuBar1.add(jmAcercaDe);

        setJMenuBar(jMenuBar1);

        setSize(new java.awt.Dimension(810, 630));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    //---------------------------------------------------------------------
    /**
     * Establece el origen de los datos y el usuario autenticado.
     *
     * @param currentUser Usuario actual.
     * @param currentSource Origen de datos.
     */
    public void iniciarSession(Usuario currentUser, OrigenDato currentSource)
    {
        sessionActual.setHasOneUsuario(currentUser);
        sessionActual.setHasOneOrigenDato(currentSource);

        jlbUsuario.setText(currentUser.getIdentificador());
        jlbEsquema.setText(currentSource.getNombre());
    }

    //---------------------------------------------------------------------
    private void jmCreditosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmCreditosActionPerformed
        // TODO add your handling code here:
        JDerechosInternalFrame About = new JDerechosInternalFrame();
        addWindow(About);
        showCenter(About);

    }//GEN-LAST:event_jmCreditosActionPerformed

    //---------------------------------------------------------------------
    /**
     * Cierra la session de usuario actual, y regresa a la pantalla de inicio.
     */
    private void jmCerrarSession_actionPerformed(ActionEvent evt)
    {
        if (evt.getSource() == jmCerrarSession)
        {
            Application.restart();
            close();
        }

        else
        {
            Application.terminate();
        }
    }

    //--------------------------------------------------------------------
    /**
     * Cierra el formulario y libera los recursos asociados.
     */
    private void close()
    {
        setVisible(false);
        dispose();
    }

    //--------------------------------------------------------------------
    /**
     * Construye todos los elementos dinámicos del menú.
     */
    public void consultarModulosMenu()
    {
        if (swDeterminarModulo == null || swDeterminarModulo.isDone())
        {
            List<Object> parametros = new ArrayList<Object>();
            swDeterminarModulo = new DeterminarModuloSwingWorker();
            ModuloInstalado buscar = new ModuloInstalado("");
            buscar.setEstatico(false);
            buscar.setActivo(true);

            parametros.add(opLOAD_MODULOS);
            parametros.add(buscar);
            swDeterminarModulo.execute(parametros);
        }
    }

    //--------------------------------------------------------------------
    private void construirMenu_threadDone(List<ModuloInstalado> semilla)
    {
        JMenuItem nodo;
        ModuloInstalado elemento;
        jMenuBar1.remove(jmAcercaDe);
        //long start = System.currentTimeMillis();
        int[] indices = busquedaBinariaPlus(semilla, 0, 0, 0); //Padre, Nivel, Inicio
        while (indices[0] <= indices[1])
        {
            elemento = semilla.get(indices[0]);
            if (elemento.isLeaf())
            {
                nodo = new JBusinessMenuItem(elemento, elemento.getTitulo());
                nodo.setName(elemento.getURI());
                nodo.addActionListener(this);
            }
            else
                nodo = new JMenu(elemento.getTitulo());
            semilla.remove(indices[0]);

            jMenuBar1.add(nodo);

            if (semilla.size() > 0)
                fillChilds(semilla, elemento.getId(), nodo, indices[1], 1);

            indices[1]--;
        }

        jMenuBar1.add(jmAcercaDe);

        if (semilla.size() > 0)
            System.out.println(String.format("%d elementos no puedieron ser construidos, asegurese de que esta ordenada la semilla", semilla.size()));
    }

    /* //-------------------
     private void temp()
     {
     java.util.Set algo;
     try
     {
     algo = new java.util.jar.JarFile("lib/entidades.jar").getManifest().getMainAttributes().entrySet();
     JOptionPane.showMessageDialog(this, algo.toString());
     }
     catch (IOException ex)
     {
     Logger.getLogger(MenuJFrame.class.getName()).log(Level.SEVERE, null, ex);
     }
      
     }*/
    //----------------------------------------------------------------
    /**
     * Llena el nodo según encuentre a sus respectivos hijos.
     */
    private void fillChilds(List<ModuloInstalado> semilla, int id_padre, JMenuItem elementoPadre, int inicio, int nivel)
    {
        JMenuItem nodo;
        int[] indices;
        ModuloInstalado elemento;

        //Nota: Verificamo si el máximo valor coincide con el primer elemento al menos.
        if (!semilla.isEmpty() && elementoPadre != null)
        {
            //1.- Obtenemos el rango en el que se encuentran los elementos sin padre
            indices = busquedaBinariaPlus(semilla, id_padre, nivel, inicio); //Padre, Nivel, Inicio

            //2.- Recorremos los elementos especificados por el rango [i, j]
            while (indices[0] <= indices[1])
            {
                elemento = semilla.get(indices[0]);
                if (elemento.isLeaf())
                {
                    nodo = new JBusinessMenuItem(elemento, elemento.getTitulo());
                    nodo.setName(elemento.getURI());
                    nodo.addActionListener(this);
                }
                else
                    nodo = new JMenu(elemento.getTitulo());
                semilla.remove(indices[0]);

                elementoPadre.add(nodo);

                if (semilla.size() > 0)
                    fillChilds(semilla, elemento.getId(), nodo, indices[1], nivel + 1);

                indices[1]--;
            }

        }
    }

    //----------------------------------------------------------------
    /**
     * Realiza la busqueda de todos los elementos que presentan el mismo valor.
     */
    private int[] busquedaBinariaPlus(List<ModuloInstalado> semilla, int id_padre_buscado, int nivel_buscado, int inicio)
    {
        int[] Resultado = new int[2];
        int mitad, idPadreActual, nivelActual;
        boolean forzarSalida = false;

        Resultado[0] = inicio;
        Resultado[1] = semilla.size() - 1;
        mitad = (Resultado[0] + Resultado[1]) / 2;

        while (!forzarSalida && Resultado[0] <= Resultado[1])
        {
            idPadreActual = semilla.get(mitad).getIdPadre();
            nivelActual = semilla.get(mitad).getNivel();

            if (idPadreActual == id_padre_buscado)
            {
                Resultado[0] = mitad - contar(false, semilla, mitad, id_padre_buscado); //CONTAMOS A LA IZQUIERDA DEL ELEMENTO
                Resultado[1] = mitad + contar(true, semilla, mitad, id_padre_buscado); //CONTAMOS A LA DERECHA DEL ELEMENTO
                forzarSalida = true;
            }

            else if ((nivelActual == nivel_buscado && idPadreActual > id_padre_buscado) || nivelActual > nivel_buscado)
            {
                Resultado[1] = mitad - 1;
                mitad = (Resultado[0] + Resultado[1]) / 2;
            }

            else if (idPadreActual < id_padre_buscado || nivelActual < nivel_buscado)
            {
                Resultado[0] = mitad + 1;
                mitad = (Resultado[0] + Resultado[1]) / 2;
            }

            else
            {
                forzarSalida = true;
                Resultado[0] = 0;
                Resultado[1] = -1;
            }
        }

        return Resultado;
    }

    //----------------------------------------------------------------
    /**
     * Cuenta todos los elementos a la izquierda o derecha del elemento con el
     * mismo valor
     */
    private int contar(boolean Lado, List<ModuloInstalado> semilla, int mitad, int id_padre)
    {
        int i, contador = 0;
        boolean continuar = true;

        if (Lado)
            i = mitad + 1;
        else
            i = mitad - 1;

        while (continuar && i >= 0 && i < semilla.size())
        {
            continuar = semilla.get(i).getIdPadre() == id_padre; //Esta línea cambia según el tipo

            if (continuar)
            {
                contador++;

                if (Lado)
                    i += 1;

                else
                    i -= 1;
            }
        }

        return contador;
    }

    //--------------------------------------------------------------------
    /**
     * Maneja la activación de un elemento dinamico del menú
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        JMenuItem currentMenu = (JMenuItem) e.getSource();

        if (currentMenu instanceof JBusinessMenuItem)
        {
            ModuloInstalado current = (ModuloInstalado) ((JBusinessMenuItem) currentMenu).getTag();
            desplegarModulo(current);
        }
    }

    //--------------------------------------------------------------------
    /**
     * Despliega el módulo solicitado.
     *
     * @param solicitado El modulo que se va a desplegar.
     */
    public void desplegarModulo(ModuloInstalado solicitado)
    {
        if (!Listados.isVisible())
        {
            addWindow(Listados);
            showCenter(Listados);
        }

        //Validar que módulo solicitado realmente haya sido encontrado.
        sessionActual.setId_modulo(solicitado.getId());

        if (solicitado.getURI().startsWith("jmInventario/jmBienes"))
        {
            if (!Listados.selectIfcontainsView(JGrupoBienesDataView.class))
                Listados.addView(solicitado.getTitulo(), new JGrupoBienesDataView(this,
                                                                                 UsuarioTienePermisos.obtenerLista(sessionActual)));
        }

        else if (solicitado.getURI().startsWith("jmInventarios/jmBienVariantes"))
        {
            if (!Listados.selectIfcontainsView(JBienDataView.class))
                Listados.addView(solicitado.getTitulo(), new JBienDataView(this,
                                                                           UsuarioTienePermisos.obtenerLista(sessionActual)));
        }

        else if (solicitado.getURI().startsWith("jmInventarios/jmCategorias"))
        {
            if (!Listados.selectIfcontainsView(JCategoriaDataView.class))
                Listados.addView(solicitado.getTitulo(), new JCategoriaDataView(this,
                                                                                UsuarioTienePermisos.obtenerLista(sessionActual)));
        }

        else if (solicitado.getURI().startsWith("jmInventarios/jmAlmacen/jmUbicacion"))
        {
            if (!Listados.selectIfcontainsView(JUbicacionesDataView.class))
                Listados.addView(solicitado.getTitulo(), new JUbicacionesDataView(this,
                                                                                  UsuarioTienePermisos.obtenerLista(sessionActual)));
        }

        else if (solicitado.getURI().startsWith("jmInventarios/jmExistencias"))
        {
            if (!Listados.selectIfcontainsView(JControlInventariosDataView.class))
                Listados.addView(solicitado.getTitulo(), new JControlInventariosDataView(this,
                                                                                         UsuarioTienePermisos.obtenerLista(sessionActual)));
        }

        else if (solicitado.getURI().startsWith("JControlAlmacenesDataView"))
        {
            if (!Listados.selectIfcontainsView(JControlAlmacenesDataView.class))
                Listados.addView(solicitado.getTitulo(), new JControlAlmacenesDataView(this,
                                                                                       UsuarioTienePermisos.obtenerLista(sessionActual)));
        }

        else if (solicitado.getURI().startsWith("jmInventarios/jmAlmacen/jmPersonaExistencia"))
        {
            if (!Listados.selectIfcontainsView(JPersonaTieneExistenciasDataView.class))
                Listados.addView(solicitado.getTitulo(), new JPersonaTieneExistenciasDataView(this,
                                                                                              UsuarioTienePermisos.obtenerLista(sessionActual)));
        }

        else if (solicitado.getURI().startsWith("jmInventarios/jmAnalisis/jmMonedas"))
        {
            if (!Listados.selectIfcontainsView(JMonedasDataView.class))
                Listados.addView(solicitado.getTitulo(), new JMonedasDataView(this,
                                                                              UsuarioTienePermisos.obtenerLista(sessionActual)));
        }

        else if (solicitado.getURI().startsWith("jmInventarios/jmAnalisis/jmAreasPrecios"))
        {
            if (!Listados.selectIfcontainsView(JAreasPreciosDataView.class))
                Listados.addView(solicitado.getTitulo(), new JAreasPreciosDataView(this,
                                                                                   UsuarioTienePermisos.obtenerLista(sessionActual)));
        }

        else if (solicitado.getURI().startsWith("jmInventarios/jmAnalisis/jmPrecios"))
        {
            if (!Listados.selectIfcontainsView(JControlPreciosDataView.class))
                Listados.addView(solicitado.getTitulo(), new JControlPreciosDataView(this,
                                                                                     UsuarioTienePermisos.obtenerLista(sessionActual)));
        }

        else if (solicitado.getURI().startsWith("jmActores/jmRelaciones"))
        {
            if (!Listados.selectIfcontainsView(JTipoPersonasDataView.class))
                Listados.addView(solicitado.getTitulo(), new JTipoPersonasDataView(this,
                                                                                   UsuarioTienePermisos.obtenerLista(sessionActual)));
        }

        else if (solicitado.getURI().startsWith("jmActores/jmPersonas"))
        {
            if (!Listados.selectIfcontainsView(JPersonaDataView.class))
                Listados.addView(solicitado.getTitulo(), new JPersonaDataView(this,
                                                                              UsuarioTienePermisos.obtenerLista(sessionActual)));
        }

        else if (solicitado.getURI().startsWith("jmComercio/jmTipoDocumentos"))
        {
            if (!Listados.selectIfcontainsView(JTiposComprobantesDataView.class))
                Listados.addView(solicitado.getTitulo(), new JTiposComprobantesDataView(this,
                                                                                        UsuarioTienePermisos.obtenerLista(sessionActual)));
        }

        else if (solicitado.getURI().startsWith("jmComercio/jmControl/jmCondiciones"))
        {
            if (!Listados.selectIfcontainsView(JCondicionesDataView.class))
                Listados.addView(solicitado.getTitulo(), new JCondicionesDataView(this/*,
                                                                                    UsuarioTienePermisos.obtenerLista(sessionActual)*/));
        }

        else if (solicitado.getURI().startsWith("jmComercio/jmDocumentos"))
        {
            if (!Listados.selectIfcontainsView(JDocumentosComercialesDataView.class))
                Listados.addView(solicitado.getTitulo(), new JDocumentosComercialesDataView(this,
                                                                                            UsuarioTienePermisos.obtenerLista(sessionActual)));
        }

        else if (solicitado.getURI().startsWith("jmComercio/jmTasaCambio"))
        {
            if (!Listados.selectIfcontainsView(JMonedaCambioDataView.class))
                Listados.addView(solicitado.getTitulo(), new JMonedaCambioDataView(this,
                                                                                   UsuarioTienePermisos.obtenerLista(sessionActual)));
        }

        else if (solicitado.getURI().startsWith("jmComercio/jmTasaCambio"))
        {
            if (!Listados.selectIfcontainsView(JMonedaCambioDataView.class))
                Listados.addView(solicitado.getTitulo(), new JMonedaCambioDataView(this,
                                                                                   UsuarioTienePermisos.obtenerLista(sessionActual)));
        }

        else if (solicitado.getURI().startsWith("jmActores/jmCuentas"))
        {
            if (!Listados.selectIfcontainsView(JPersonaCreditoCuentaDataView.class))
                Listados.addView(solicitado.getTitulo(), new JPersonaCreditoCuentaDataView(this,
                                                                                           UsuarioTienePermisos.obtenerLista(sessionActual)));
        }

        else if (solicitado.getURI().startsWith("jmInventarios/jmControlMantenimiento"))
        {
            if (!Listados.selectIfcontainsView(JMantenimientoDataView.class))
                Listados.addView(solicitado.getTitulo(), new JMantenimientoDataView(this,
                                                                                    UsuarioTienePermisos.obtenerLista(sessionActual)));
        }

        else if (solicitado.getURI().startsWith("jmActores/jmPersonal"))
        {
            if (!Listados.selectIfcontainsView(JPersonalDataView.class))
                Listados.addView(solicitado.getTitulo(), new JPersonalDataView(this,
                                                                               UsuarioTienePermisos.obtenerLista(sessionActual)));
        }

        else if (solicitado.getURI().startsWith("jmActores/jmJornadas"))
        {
            if (!Listados.selectIfcontainsView(JJornadaDataView.class))
                Listados.addView(solicitado.getTitulo(), new JJornadaDataView(this,
                                                                              UsuarioTienePermisos.obtenerLista(sessionActual)));
        }

        else if (solicitado.getURI().startsWith("jmActores/jmHorarios"))
        {
            if (!Listados.selectIfcontainsView(JHorarioDataView.class))
                Listados.addView(solicitado.getTitulo(), new JHorarioDataView(this,
                                                                              UsuarioTienePermisos.obtenerLista(sessionActual)));
        }

        else if (solicitado.getURI().startsWith("jmActores/jmPuestos"))
        {
            if (!Listados.selectIfcontainsView(JPuestoDataView.class))
                Listados.addView(solicitado.getTitulo(), new JPuestoDataView(this,
                                                                             UsuarioTienePermisos.obtenerLista(sessionActual)));
        }

        else if (solicitado.getURI().startsWith("JValoresDataView"))
        {
            String[] parts = solicitado.getURI().split("\\?");
            if (!Listados.selectIfcontainsView(JValoresDataView.class))
            {
                Listados.addView(solicitado.getTitulo(), parts.length > 1?  
                        new JValoresDataView(this, parts[1],
                                UsuarioTienePermisos.obtenerLista(sessionActual)):
                        new JValoresDataView(this, 
                                UsuarioTienePermisos.obtenerLista(sessionActual)));
            }
        }

        else if (solicitado.getURI().startsWith("jmActores/jmContratos"))
        {
            if (!Listados.selectIfcontainsView(JPersonaContratoDataView.class))
                Listados.addView(solicitado.getTitulo(), new JPersonaContratoDataView(this,
                                                                                      UsuarioTienePermisos.obtenerLista(sessionActual)));
        }

        else if (solicitado.getURI().startsWith("jmActores/jmMachotes"))
        {
            if (!Listados.selectIfcontainsView(JContratoDataView.class))
                Listados.addView(solicitado.getTitulo(), new JContratoDataView(this,
                                                                               UsuarioTienePermisos.obtenerLista(sessionActual)));
        }

        else if (solicitado.getURI().startsWith("jmActores/jmSalarios"))
        {
            if (!Listados.selectIfcontainsView(JPersonaSalariosDataView.class))
                Listados.addView(solicitado.getTitulo(), new JPersonaSalariosDataView(this,
                                                                                      UsuarioTienePermisos.obtenerLista(sessionActual)));
        }

        /*else if (solicitado.startsWith().equals("jmInventarios/jmMovsExistencia"))
         {
         if (!Listados.selectIfcontainsView(JPersonaCreditoCuentaDataView.class))
         Listados.addView(solicitado.getTitulo(), new JPersonaCreditoCuentaDataView(this));
         }*/
        if (Listados.isThereSelectedPage())
            Listados.retrieveRequest();

        else
            JOptionPane.showMessageDialog(this, "No se pudo determinar que modulo se pretendía desplegar, revise su codificación", "Error", JOptionPane.ERROR_MESSAGE);
    }

    //---------------------------------------------------------------------
    /**
     * Determina que módulo desplegar para el elemento estatico de la interfaz
     * de usuario.
     */
    private void determinarModulo_actionPerformed(ActionEvent evt)
    {

        if (swDeterminarModulo == null || swDeterminarModulo.isDone())
        {
            //  JOptionPane.showMessageDialog(this, ((JMenuItem)evt.getSource()).getName());
            List<Object> parametros = new ArrayList<Object>();
            swDeterminarModulo = new DeterminarModuloSwingWorker();
            String uri = ((JMenuItem) evt.getSource()).getName();
            ModuloInstalado buscar = new ModuloInstalado(uri);
            buscar.setActivo(true);
            buscar.setEstatico(true);

            parametros.add(opBUSCA_MODULO);
            parametros.add(evt);
            parametros.add(buscar);
            swDeterminarModulo.execute(parametros);
        }
    }

    //--------------------------------------------------------------------
    public void desplegarModulo_threadDone(JMenuItem elemento, ModuloInstalado solicitado)
    {
        if (!Listados.isVisible())
        {
            addWindow(Listados);
            showCenter(Listados);
        }

        //Validar que módulo solicitado realmente haya sido encontrado.
        sessionActual.getHasOnePermiso().setHasOneModuloInstalado(solicitado);

        if (elemento == jmUsuario)
        {
            if (!Listados.selectIfcontainsName(JUsuariosDataView.usuario))
                Listados.addView(solicitado.getTitulo(), new JUsuariosDataView(this, Usu, JUsuariosDataView.usuario,
                                                                               UsuarioTienePermisos.obtenerLista(sessionActual)));
        }
        
        else if (elemento == jmRoles)
        {
            if (!Listados.selectIfcontainsName(JUsuariosDataView.rol))
                Listados.addView(solicitado.getTitulo(), new JUsuariosDataView(this, Rol, JUsuariosDataView.rol,
                                                                               UsuarioTienePermisos.obtenerLista(sessionActual)));
        }

        else if (elemento == jmLocalidades)
        {
            if (!Listados.selectIfcontainsView(JLocalidadDataView.class))
                Listados.addView(solicitado.getTitulo(), new JLocalidadDataView(this,
                                                                                UsuarioTienePermisos.obtenerLista(sessionActual)));
        }

        else if (elemento == jmUnidades)
        {
            if (!Listados.selectIfcontainsView(JUnidadDataView.class))
                Listados.addView(solicitado.getTitulo(), new JUnidadDataView(this,
                                                                             UsuarioTienePermisos.obtenerLista(sessionActual)));
        }

        if (Listados.isThereSelectedPage())
            Listados.retrieveRequest();

        else
            JOptionPane.showMessageDialog(this, "No se pudo determinar que modulo se pretendía desplegar, revise su codificación", "Error", JOptionPane.ERROR_MESSAGE);
    }

    //---------------------------------------------------------------------
    @Override
    public OrigenDato getOrigenDatoActual()
    {
        return sessionActual.getHasOneOrigenDato();
    }

    //---------------------------------------------------------------------
    public void setOrigenDatoActual(OrigenDato value)
    {
        sessionActual.setHasOneOrigenDato(value);
        jlbEsquema.setText(value.getNombre());
    }

    //---------------------------------------------------------------------
    public void setUsuarioActual(Usuario value)
    {
        sessionActual.setHasOneUsuario(value);
        jlbUsuario.setText(value.getIdentificador());
    }

    //---------------------------------------------------------------------
    @Override
    public Usuario getUsuarioActual()
    {
        return sessionActual.getHasOneUsuario();
    }

    //--------------------------------------------------------------------
    /**
     * Agrega un dialogo hacia el contenedor principal de ventanas.
     *
     * @param dialog El dialogo a agregar.
     */
    @Override
    public void addWindow(JInternalFrame dialog)
    {
        jdpWindowsContainer.add(dialog);
    }

    //--------------------------------------------------------------------
    @Override
    public JFrame getRootFrame()
    {
        return this;
    }

   //---------------------------------------------------------------------
   /*Obsoleto
     public void prepareCenter(JInternalFrame dialog)
     {
     Dimension desktopSize = jdpWindowsContainer.getSize();
     Dimension frameSize = dialog.getSize();
     dialog.setLocation((desktopSize.width - frameSize.width)/2,
     (desktopSize.height- frameSize.height)/2);
     }*/
    //--------------------------------------------------------------------
    /**
     * Muestra en el centro del Contenedor el dialogo.
     *
     * @param dialog El Dialogo a centrar
     */
    @Override
    public void showCenter(JInternalFrame dialog)
    {
        Dimension desktopSize = jdpWindowsContainer.getSize();
        Dimension frameSize = dialog.getSize();

        if (frameSize.getHeight() < 1 || frameSize.getWidth() < 1)
        {
            dialog.pack();
            frameSize = dialog.getSize();
        }

        dialog.setLocation((desktopSize.width - frameSize.width) / 2,
                           (desktopSize.height - frameSize.height) / 2);
        dialog.setVisible(true);

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JDesktopPane jdpWindowsContainer;
    private javax.swing.JLabel jlbEsquema;
    private javax.swing.JLabel jlbEstatusConexion;
    private javax.swing.JLabel jlbLocale;
    private javax.swing.JLabel jlbUsuario;
    private javax.swing.JMenu jmAcercaDe;
    private javax.swing.JMenu jmArchivo;
    private javax.swing.JMenuItem jmAyuda;
    private javax.swing.JMenuItem jmCerrarSession;
    private javax.swing.JMenuItem jmConfiguracion;
    private javax.swing.JMenu jmControlAcceso;
    private javax.swing.JMenuItem jmCreditos;
    private javax.swing.JMenuItem jmLocalidades;
    private javax.swing.JMenuItem jmRoles;
    private javax.swing.JMenuItem jmSalir;
    private javax.swing.JMenuItem jmUnidades;
    private javax.swing.JMenuItem jmUsuario;
    private javax.swing.JMenu jmValoresAdicionales;
    private javax.swing.JMenuItem jmVisorSucesos;
    private javax.swing.JPanel jpnEstatusBar;
    // End of variables declaration//GEN-END:variables

    private class DeterminarModuloSwingWorker extends SwingWorker<List<Object>, Void>
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
            if (!isCancelled() && arguments != null && arguments.size() > 0)
            {
                int opcion = (int) arguments.get(0); //Debe haber un entero en la primera posición

                switch (opcion)
                {
                    case opBUSCA_MODULO:
                        ModuloInstalado buscar = (ModuloInstalado) arguments.get(2); //Debe haber un entero en la primera posición
                        ModulosInstalados.recargar(buscar);
                        break;

                    case opLOAD_MODULOS:
                        ModuloInstalado filtro = (ModuloInstalado) arguments.get(1); //Debe haber un entero en la primera posición
                        arguments.add(ModulosInstalados.obtenerListaConstruccion(filtro));
                        break;
                }

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
                    if (results != null)
                    {
                        int opcion = (int) results.get(0); //Debe haber un entero en la primera posición

                        switch (opcion)
                        {
                            case opBUSCA_MODULO:
                                ActionEvent evt = (ActionEvent) results.get(1);
                                ModuloInstalado solicitado = (ModuloInstalado) results.get(2);
                                desplegarModulo_threadDone((JMenuItem) evt.getSource(), solicitado);
                                break;

                            case opLOAD_MODULOS:
                                construirMenu_threadDone((List<ModuloInstalado>) results.get(2));
                                //temp();
                                break;
                        }
                    }

                    else
                        JOptionPane.showMessageDialog(null, ModulosInstalados.getMensaje());
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
