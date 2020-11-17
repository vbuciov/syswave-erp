package com.syswave.gestion;

import com.syswave.entidades.configuracion.OrigenDato;
import com.syswave.entidades.configuracion.Usuario;
import com.syswave.swing.renders.POJOListCellRenderer;
import com.syswave.gestion.databinding.OrigenesDatosComboBoxModel;
import com.syswave.logicas.configuracion.OrigenesDatosBusinessLogic;
import com.syswave.logicas.configuracion.UsuariosBusinessLogic;
import java.awt.Cursor;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import org.apache.commons.codec.binary.Hex;

/**
 * Permite la autentificación de usuario.
 *
 * @author Victor Manuel Bucio Vargas
 */
public class LoginJFrame extends javax.swing.JFrame implements PropertyChangeListener
{

    private final int CARGAR_ORIGENES = 0;
    private final int CARGAR_USUARIOS = 1;
    private OrigenesDatosBusinessLogic OrigenesDatos;
    private UsuariosBusinessLogic Usuarios;
    private CargarDatosSwingWorker swCargarDatos;
    private OrigenesDatosComboBoxModel bsOrigenDatos;
    boolean caps_actived;

    //---------------------------------------------------------------------
    /**
     * Creates new form LoginJFrame
     */
    public LoginJFrame()
    {
        initComponents();
        initAtributes();

    }

    //---------------------------------------------------------------------
    private void initAtributes()
    {
        OrigenesDatos = new OrigenesDatosBusinessLogic("configuracion");
        Usuarios = new UsuariosBusinessLogic(OrigenesDatos.getEsquema());
        caps_actived = Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK);
    }

    //---------------------------------------------------------------------
    /**
     * Esta función carga los recursos necesarios para operar.
     *
     * @since Todas las pantallas utilizan esto
     */
    public void cargarRecursos()
    {
        List<Object> arguments = new ArrayList<Object>();
        OrigenDato filtro = new OrigenDato();
        filtro.setActivo(true);
        filtro.acceptChanges();
        arguments.add(CARGAR_ORIGENES);
        arguments.add(filtro);
        showTaskHeader("Cargado recursos...", true);
        swCargarDatos = new CargarDatosSwingWorker();
        swCargarDatos.addPropertyChangeListener(this);
        swCargarDatos.execute(arguments);
        setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/com/syswave/gestion/resources/home.png")));
    }

    //---------------------------------------------------------------------
    /**
     * 
     */
    private void autentificarUsuario(Usuario nuevo)
    {
        List<Object> arguments = new ArrayList<Object>();
        arguments.add(CARGAR_USUARIOS);
        arguments.add(nuevo);
        showTaskHeader("Autentificando...", true);
        swCargarDatos = new CargarDatosSwingWorker();
        swCargarDatos.addPropertyChangeListener(this);
        swCargarDatos.execute(arguments);
    }

    //---------------------------------------------------------------------
    /**
     * Hace aparecer la barra de progreso, con algún mensaje especificado
     */
    private void showTaskHeader(String mensaje, boolean progress)
    {
        jlbMensajes.setText(mensaje);
        jpbAvances.setVisible(progress);
        jpbAvances.setValue(jpbAvances.getMinimum());
        if (progress)
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        else
            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }

    //--------------------------------------------------------------------
    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        //int progress = cargarOrigenesDatos.getProgress();
        if ("progress".equals(evt.getPropertyName()))
            jpbAvances.setValue((Integer) evt.getNewValue());
    }

    //---------------------------------------------------------------------
    private void abrirMenu(Usuario autentificado)
    {
        if (autentificado.esActivo()) {
            //setTitle(((OrigenDato)jcbOrigenDato.getSelectedItem()).getNombre());
            MenuJFrame Menu = new MenuJFrame();
            /*Menu.setOrigenDatoActual((OrigenDato) jcbOrigenDato.getSelectedItem());
             Menu.setUsuarioActual(autentificado);*/
            Menu.iniciarSession(autentificado, bsOrigenDatos.getCurrent());
            Menu.consultarModulosMenu();
            clear();
            Menu.setVisible(true);
            this.setVisible(false);
        }
        else
            JOptionPane.showMessageDialog(this, "Su usuario esta deshabilitado, contacte a su administrador", "Información", JOptionPane.PLAIN_MESSAGE);
    }

    //---------------------------------------------------------------------
    private void clear()
    {
        jtfUser.setText("");
        jtfPassword.setText(jtfUser.getText());
        jcbOrigenDato.setSelectedIndex(-1);

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

        jpActions = new javax.swing.JPanel();
        jbCancelar = new javax.swing.JButton();
        jbAceptar = new javax.swing.JButton();
        jpBody = new javax.swing.JPanel();
        jpEntrada = new javax.swing.JPanel();
        jlbUser = new javax.swing.JLabel();
        jtfUser = new javax.swing.JTextField();
        jlbPassword = new javax.swing.JLabel();
        jtfPassword = new javax.swing.JPasswordField();
        jlbOrigenDatos = new javax.swing.JLabel();
        jcbOrigenDato = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jpEncabezado = new javax.swing.JPanel();
        jlbIcono = new javax.swing.JLabel();
        jpAreaMensajes = new javax.swing.JPanel();
        jlbMensajes = new javax.swing.JLabel();
        jpbAvances = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jpActions.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jpActions.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jbCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/syswave/gestion/resources/button-cross.png"))); // NOI18N
        jbCancelar.setLabel("Cancelar");
        jbCancelar.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jbCancelarActionPerformed(evt);
            }
        });
        jpActions.add(jbCancelar);

        jbAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/syswave/gestion/resources/button-tick.png"))); // NOI18N
        jbAceptar.setLabel("Aceptar");
        jbAceptar.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jbAceptarActionPerformed(evt);
            }
        });
        jpActions.add(jbAceptar);

        getContentPane().add(jpActions, java.awt.BorderLayout.SOUTH);

        jpBody.setPreferredSize(new java.awt.Dimension(400, 80));
        java.awt.FlowLayout flowLayout1 = new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 25);
        flowLayout1.setAlignOnBaseline(true);
        jpBody.setLayout(flowLayout1);

        jpEntrada.setPreferredSize(new java.awt.Dimension(300, 100));
        jpEntrada.setLayout(new java.awt.GridLayout(4, 2, 3, 1));

        jlbUser.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbUser.setLabelFor(jtfUser);
        jlbUser.setText("Usuario:");
        jpEntrada.add(jlbUser);
        jpEntrada.add(jtfUser);

        jlbPassword.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbPassword.setLabelFor(jtfPassword);
        jlbPassword.setText("Contraseña:");
        jpEntrada.add(jlbPassword);

        jtfPassword.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusGained(java.awt.event.FocusEvent evt)
            {
                jtfPasswordFocusGained(evt);
            }
        });
        jtfPassword.addKeyListener(new java.awt.event.KeyAdapter()
        {
            public void keyPressed(java.awt.event.KeyEvent evt)
            {
                jtfPasswordKeyPressed(evt);
            }
        });
        jpEntrada.add(jtfPassword);

        jlbOrigenDatos.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbOrigenDatos.setLabelFor(jcbOrigenDato);
        jlbOrigenDatos.setText("Origen Datos:");
        jpEntrada.add(jlbOrigenDatos);

        jcbOrigenDato.setRenderer(new POJOListCellRenderer<OrigenDato>());
        jpEntrada.add(jcbOrigenDato);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jpEntrada.add(jLabel1);

        jpBody.add(jpEntrada);

        getContentPane().add(jpBody, java.awt.BorderLayout.CENTER);

        jpEncabezado.setBackground(new java.awt.Color(255, 92, 0));
        jpEncabezado.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Introduzca su contraseña", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Abyssinica SIL", 0, 10))); // NOI18N
        jpEncabezado.setPreferredSize(new java.awt.Dimension(10, 80));
        java.awt.FlowLayout flowLayout2 = new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 20, 0);
        flowLayout2.setAlignOnBaseline(true);
        jpEncabezado.setLayout(flowLayout2);

        jlbIcono.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbIcono.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/syswave/gestion/resources/login.png"))); // NOI18N
        jlbIcono.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlbIcono.setFocusTraversalPolicyProvider(true);
        jlbIcono.setFocusable(false);
        jlbIcono.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jlbIcono.setIconTextGap(0);
        jlbIcono.setPreferredSize(new java.awt.Dimension(40, 50));
        jlbIcono.setRequestFocusEnabled(false);
        jpEncabezado.add(jlbIcono);

        jpAreaMensajes.setBackground(new java.awt.Color(255, 92, 0));
        jpAreaMensajes.setLayout(new java.awt.GridLayout(2, 1));

        jlbMensajes.setText("<sin mensaje>");
        jlbMensajes.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jpAreaMensajes.add(jlbMensajes);

        jpbAvances.setValue(50);
        jpbAvances.setPreferredSize(new java.awt.Dimension(250, 20));
        jpbAvances.setStringPainted(true);
        jpAreaMensajes.add(jpbAvances);

        jpEncabezado.add(jpAreaMensajes);

        getContentPane().add(jpEncabezado, java.awt.BorderLayout.NORTH);

        setSize(new java.awt.Dimension(431, 342));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    //---------------------------------------------------------------------
    private void jbAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAceptarActionPerformed
        Login();
    }//GEN-LAST:event_jbAceptarActionPerformed

    //---------------------------------------------------------------------
   private void jbCancelarActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jbCancelarActionPerformed
   {//GEN-HEADEREND:event_jbCancelarActionPerformed
       // TODO add your handling code here:
       Application.terminate();
   }//GEN-LAST:event_jbCancelarActionPerformed

    private void jtfPasswordKeyPressed(java.awt.event.KeyEvent evt)//GEN-FIRST:event_jtfPasswordKeyPressed
    {//GEN-HEADEREND:event_jtfPasswordKeyPressed
        // TODO add your handling code here:
        int k = evt.getKeyCode();
        if (k == KeyEvent.VK_ENTER) {
            Login();
        }
           else if (evt.getKeyCode() == KeyEvent.VK_CAPS_LOCK) {
            if (caps_actived)
                jLabel1.setText("Mayusculas OFF");
            else
                jLabel1.setText("Mayusculas ON");
            caps_actived = !caps_actived;
        }
    }//GEN-LAST:event_jtfPasswordKeyPressed

    private void jtfPasswordFocusGained(java.awt.event.FocusEvent evt)//GEN-FIRST:event_jtfPasswordFocusGained
    {//GEN-HEADEREND:event_jtfPasswordFocusGained
        // TODO add your handling code here:
        if (caps_actived)
            jLabel1.setText("Mayusculas ON");

        else
            jLabel1.setText("Mayusculas OFF");
    }//GEN-LAST:event_jtfPasswordFocusGained

    private void Login()
    {
        if (jcbOrigenDato.getSelectedIndex() >= 0) {
            try {
                //MD5
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(new String(jtfPassword.getPassword()).getBytes());
                byte[] mb = md.digest();

                Usuario solicita = new Usuario(jtfUser.getText(), new String(Hex.encodeHex(mb)));

                if (Usuarios.valirdar(solicita))
                    autentificarUsuario(solicita);
                else
                    showTaskHeader(Usuarios.getMensaje(), false);
                //jtfUser.setText(solicita.getClave());
            }
            catch (NoSuchAlgorithmException e) {
                //Error
            }
        }
        else {
            showTaskHeader("Debe seleccionar el origen de datos", false);
        }
    }

   //---------------------------------------------------------------------
    //--  Esta clase controla el cargado en segundo plano de los origenes de datos.
    //---------------------------------------------------------------------
    private class CargarDatosSwingWorker extends SwingWorker<List<Object>, Void>
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
            if (!isCancelled() && arguments != null && arguments.size() > 0) {
                int opcion = (int) arguments.get(0); //Debe haber un entero en la primera posición
                List<Object> results = new ArrayList<>();
                results.add(opcion);
                switch (opcion) {
                    case CARGAR_USUARIOS:
                        Usuario buscar = (Usuario) arguments.get(1);
                        results.add(Usuarios.obtenerLista(buscar));
                        break;

                    default:
                        results.add(OrigenesDatos.obtenerLista((OrigenDato)arguments.get(1)));
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
            try {
                if (!isCancelled()) {
                    List<Object> results = get();

                    if (results.size() > 0) {
                        showTaskHeader("Origenes de datos cargados", false);
                        int opcion = (int) results.get(0);

                        switch (opcion) {
                            case CARGAR_USUARIOS:
                                List<Usuario> listaUsuarios = (List<Usuario>) results.get(1);
                                if (listaUsuarios.size() > 0)
                                    abrirMenu(listaUsuarios.get(0));
                                else if (Usuarios.esCorrecto())
                                    showTaskHeader("Usuario o contraseña invalido", false);
                                else
                                    JOptionPane.showMessageDialog(null, Usuarios.getMensaje());
                                break;

                            default:
                                List<OrigenDato> listaOrigenes = (List<OrigenDato>) results.get(1);
                                if (listaOrigenes.size() > 0) {
                                    bsOrigenDatos = new OrigenesDatosComboBoxModel(listaOrigenes);
                                    jcbOrigenDato.setModel(bsOrigenDatos);
                                }
                                else
                                    JOptionPane.showMessageDialog(null, OrigenesDatos.getMensaje());
                                break;
                        }

                        results.clear();
                    }

                    else
                        showTaskHeader("No se obtuvieron resultados del proceso", false);
                }
            }
            catch (InterruptedException ignore) {
            }
            catch (java.util.concurrent.ExecutionException e) {
                String why;
                Throwable cause = e.getCause();

                /*if (cause instanceof NullPointerException)
                 {
                 int opcion = (int)arguments.get(0); //Debe haber un entero en la primera posición
                 switch (opcion)
                 {
                 case CARGAR_USUARIOS:
                 why = Usuarios.getMensaje();
                 break;

                 default:
                 why = OrigenesDatos.getMensaje();
                 break;
                 }

                 }
            
                 else*/ if (cause != null) {
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton jbAceptar;
    private javax.swing.JButton jbCancelar;
    private javax.swing.JComboBox jcbOrigenDato;
    private javax.swing.JLabel jlbIcono;
    private javax.swing.JLabel jlbMensajes;
    private javax.swing.JLabel jlbOrigenDatos;
    private javax.swing.JLabel jlbPassword;
    private javax.swing.JLabel jlbUser;
    private javax.swing.JPanel jpActions;
    private javax.swing.JPanel jpAreaMensajes;
    private javax.swing.JPanel jpBody;
    private javax.swing.JPanel jpEncabezado;
    private javax.swing.JPanel jpEntrada;
    private javax.swing.JProgressBar jpbAvances;
    private javax.swing.JPasswordField jtfPassword;
    private javax.swing.JTextField jtfUser;
    // End of variables declaration//GEN-END:variables

}
