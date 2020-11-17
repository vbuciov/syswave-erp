package com.syswave.forms.configuracion;

import com.syswave.entidades.configuracion.NodoPermiso;
import com.syswave.entidades.configuracion.Usuario;
import com.syswave.swing.tree.editors.POJOCheckBoxTreeCellEditor;
import com.syswave.swing.tree.renders.POJOCheckBoxTreeCellRenderer;
import com.syswave.forms.databinding.PermisosTreeModel;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingWorker;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import org.apache.commons.codec.binary.Hex;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class JUsuariosDetailView extends javax.swing.JInternalFrame
{

    IUsuarioMediator parent;
    boolean esNuevo, construidoArbol;
    Usuario elementoActual;
    PermisosTreeModel bsPermisos;
    PermisosCargarSwingWorker swCargador;

    //---------------------------------------------------------------------
    /**
     * Creates new form JUsuariosInternalFrame
     *
     * @param owner Propietario del formulario
     */
    public JUsuariosDetailView(IUsuarioMediator owner)
    {
        initAtributes();
        initComponents();
        initEvents();
        parent = owner;
    }

    //---------------------------------------------------------------------
    public void setAllowGrant(boolean value)
    {
        jpPermisos.setVisible(value);
    }

    //---------------------------------------------------------------------
    private void initAtributes()
    {
        bsPermisos = new PermisosTreeModel();
        esNuevo = true;
        construidoArbol = false;
        elementoActual = null;
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
                        finish_actionPerformed(evt);
                    }
                };

        ChangeListener changeListenerManager = new ChangeListener()
        {

            @Override
            public void stateChanged(ChangeEvent e)
            {
                bodyTabbed_stateChanged(e);
            }
        };

        jbAceptar.addActionListener(actionListenerManager);
        jbCancelar.addActionListener(actionListenerManager);
        tpCuerpo.addChangeListener(changeListenerManager);
    }

    //---------------------------------------------------------------------
    private void bodyTabbed_stateChanged(ChangeEvent e)
    {
        JTabbedPane pane = (JTabbedPane) e.getSource();
        if (pane.getSelectedComponent() == jpPermisos && !construidoArbol) {
            List<Object> parametros = new ArrayList<Object>();
            swCargador = new PermisosCargarSwingWorker();
            swCargador.execute(parametros);
        }
    }

    //---------------------------------------------------------------------
    private void finish_actionPerformed(ActionEvent evt)
    {
        Object sender = evt.getSource();

        if (sender == jbAceptar) {
            if (readElement(elementoActual)) {
                if (esNuevo)
                    parent.onAcceptNewElement(elementoActual, bsPermisos.getModifiedDatas());

                else
                    parent.onAcceptModifyElement(elementoActual, bsPermisos.getModifiedDatas());

                close();
            }
        }

        else
            close();
    }

    //---------------------------------------------------------------------
    public void prepareForNew(int TIPO_OBJ)
    {
        elementoActual = new Usuario();

        if (TIPO_OBJ == 0) {

            elementoActual.setEs_tipo(TIPO_OBJ);
            esNuevo = true;
            this.setTitle("Nuevo Usuario");
            writeElement(elementoActual);
        }
        else {
            elementoActual.setEs_tipo(TIPO_OBJ);
            esNuevo = true;
            this.setTitle("Nuevo Rol");
            writeElement(elementoActual);
            //writeElement(elementoActual);
        }
    }

    //---------------------------------------------------------------------
    public void prepareForModify(Usuario elemento)
    {
        this.elementoActual = elemento;
        esNuevo = false;
        this.setTitle(String.format("Modificando %s", elemento.getIdentificador()));
        writeElement(elemento);
    }

    //---------------------------------------------------------------------
    public void writeElement(Usuario elemento)
    {
        if (elementoActual.getEs_tipo() == 0) {
            jtfUsuario.setText(elemento.getIdentificador());
            jtfClave.setText(elemento.getClave());
            jckActivo.setSelected(elemento.esActivo());
        }
        else {
            jtfUsuario.setText(elemento.getIdentificador());
            jckActivo.setSelected(elemento.esActivo());
            jtfClave.setText(elemento.getClave());
            //jtfClave.setVisible(false);
            jtfClave.setEnabled(false);
            jlbClave.setEnabled(false);
            lbConfirmarClave.setEnabled(false);
            jtfConfirmarClave.setEnabled(false);
            //jtfConfirmarClave.setVisible(false);

        }
    }

    //---------------------------------------------------------------------
    private boolean readElement(Usuario elemento)
    {
        boolean resultado = false;
        String mensaje = "";
        if (elemento.getEs_tipo() == 0) {
            if (!jtfUsuario.getText().isEmpty() && jtfClave.getPassword().length > 0) {
                resultado = true;

                elemento.setIdentificador(jtfUsuario.getText());

                if (esNuevo || jtfConfirmarClave.getPassword().length > 0) {
                    if (Arrays.equals(jtfClave.getPassword(), jtfConfirmarClave.getPassword())) {
                        try {
                            //MD5
                            MessageDigest md = MessageDigest.getInstance("MD5");
                            md.update(new String(jtfClave.getPassword()).getBytes());
                            byte[] mb = md.digest();

                            elemento.setClave(new String(Hex.encodeHex(mb)));
                            //jtfUser.setText(solicita.getClave());
                        }
                        catch (NoSuchAlgorithmException e) {
                            //Error
                        }
                    }

                    else {
                        resultado = false;
                        mensaje = "La contraseña no coincide";
                    }
                }

                elemento.setActivo(jckActivo.isSelected());
                if (!elemento.isSet())
                    elemento.setModified();
                elemento.setEs_tipo(0);
            }

            else
                mensaje = "Asegurese de proporcionar el identificador y la contraseña del usuario";
        }
        else {
            if (!jtfUsuario.getText().isEmpty()) {
                resultado = true;

                elemento.setIdentificador(jtfUsuario.getText());
                elemento.setActivo(jckActivo.isSelected());
                if (!elemento.isSet())
                    elemento.setModified();
                elemento.setEs_tipo(1);
                elemento.setClave("");
            }

            else
                mensaje = "Proporcione un Identificador";

        }

        if (!resultado)
            JOptionPane.showMessageDialog(this, mensaje, "", JOptionPane.PLAIN_MESSAGE);

        return resultado;
    }

    //----------------------------------------------------------------------
    public void setDisplayData(List<NodoPermiso> semilla)
    {
        bsPermisos.setData(semilla);
        tPermisos.expandPath(new TreePath(((DefaultMutableTreeNode) bsPermisos.getRoot()).getPath()));
        construidoArbol = true;
    }

    //---------------------------------------------------------------------
    public void close()
    {
        this.setVisible(false);
        this.dispose();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jpEncabezado = new javax.swing.JPanel();
        jlbIcono = new javax.swing.JLabel();
        jpAreaMensajes = new java.awt.Panel();
        tpCuerpo = new javax.swing.JTabbedPane();
        jpGeneral = new javax.swing.JPanel();
        jpContenido = new javax.swing.JPanel();
        jlbUsuario = new javax.swing.JLabel();
        jtfUsuario = new javax.swing.JTextField();
        jlbClave = new javax.swing.JLabel();
        jtfClave = new javax.swing.JPasswordField();
        lbConfirmarClave = new javax.swing.JLabel();
        jtfConfirmarClave = new javax.swing.JPasswordField();
        jckActivo = new javax.swing.JCheckBox();
        jlbActivo = new javax.swing.JLabel();
        jpPermisos = new javax.swing.JPanel();
        spPermisos = new javax.swing.JScrollPane();
        tPermisos = new javax.swing.JTree();
        jpAcciones = new javax.swing.JPanel();
        jbAceptar = new javax.swing.JButton();
        jbCancelar = new javax.swing.JButton();

        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setPreferredSize(new java.awt.Dimension(510, 470));

        jpEncabezado.setBackground(new java.awt.Color(0, 153, 153));
        jpEncabezado.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jpEncabezado.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 20, 5));

        jlbIcono.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/syswave/forms/resources/window.png"))); // NOI18N
        jlbIcono.setAlignmentX(0.5F);
        jlbIcono.setFocusTraversalPolicyProvider(true);
        jlbIcono.setFocusable(false);
        jlbIcono.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jlbIcono.setIconTextGap(0);
        jlbIcono.setPreferredSize(new java.awt.Dimension(40, 50));
        jlbIcono.setRequestFocusEnabled(false);
        jpEncabezado.add(jlbIcono);

        jpAreaMensajes.setBackground(java.awt.SystemColor.desktop);
        jpAreaMensajes.setLayout(new java.awt.GridLayout(2, 1));
        jpEncabezado.add(jpAreaMensajes);

        getContentPane().add(jpEncabezado, java.awt.BorderLayout.PAGE_START);

        jpContenido.setPreferredSize(new java.awt.Dimension(250, 200));
        jpContenido.setLayout(new java.awt.GridLayout(8, 1));

        jlbUsuario.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jlbUsuario.setLabelFor(jtfUsuario);
        jlbUsuario.setText("Identificador de session:");
        jlbUsuario.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jlbUsuario.setPreferredSize(new java.awt.Dimension(200, 15));
        jpContenido.add(jlbUsuario);
        jpContenido.add(jtfUsuario);

        jlbClave.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jlbClave.setLabelFor(jtfClave);
        jlbClave.setText("Clave de acceso:");
        jlbClave.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jpContenido.add(jlbClave);
        jpContenido.add(jtfClave);

        lbConfirmarClave.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbConfirmarClave.setLabelFor(jtfClave);
        lbConfirmarClave.setText("Clave de acceso:");
        lbConfirmarClave.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jpContenido.add(lbConfirmarClave);
        jpContenido.add(jtfConfirmarClave);

        jckActivo.setText("Activo:");
        jckActivo.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jpContenido.add(jckActivo);
        jpContenido.add(jlbActivo);

        jpGeneral.add(jpContenido);

        tpCuerpo.addTab("General", jpGeneral);

        jpPermisos.setLayout(new java.awt.BorderLayout());

        tPermisos.setModel(bsPermisos);
        tPermisos.setCellEditor(new POJOCheckBoxTreeCellEditor<NodoPermiso>());
        tPermisos.setCellRenderer(new POJOCheckBoxTreeCellRenderer<NodoPermiso>());
        tPermisos.setEditable(true);
        tPermisos.setRootVisible(false);
        tPermisos.setToggleClickCount(1);
        spPermisos.setViewportView(tPermisos);

        jpPermisos.add(spPermisos, java.awt.BorderLayout.CENTER);

        tpCuerpo.addTab("Permisos", jpPermisos);

        getContentPane().add(tpCuerpo, java.awt.BorderLayout.CENTER);

        jpAcciones.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jbAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/syswave/forms/resources/button-tick.png"))); // NOI18N
        jbAceptar.setText("Aceptar");
        jpAcciones.add(jbAceptar);

        jbCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/syswave/forms/resources/button-cross.png"))); // NOI18N
        jbCancelar.setText("Cancelar");
        jpAcciones.add(jbCancelar);

        getContentPane().add(jpAcciones, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jbAceptar;
    private javax.swing.JButton jbCancelar;
    private javax.swing.JCheckBox jckActivo;
    private javax.swing.JLabel jlbActivo;
    private javax.swing.JLabel jlbClave;
    private javax.swing.JLabel jlbIcono;
    private javax.swing.JLabel jlbUsuario;
    private javax.swing.JPanel jpAcciones;
    private java.awt.Panel jpAreaMensajes;
    private javax.swing.JPanel jpContenido;
    private javax.swing.JPanel jpEncabezado;
    private javax.swing.JPanel jpGeneral;
    private javax.swing.JPanel jpPermisos;
    private javax.swing.JPasswordField jtfClave;
    private javax.swing.JPasswordField jtfConfirmarClave;
    private javax.swing.JTextField jtfUsuario;
    private javax.swing.JLabel lbConfirmarClave;
    private javax.swing.JScrollPane spPermisos;
    private javax.swing.JTree tPermisos;
    private javax.swing.JTabbedPane tpCuerpo;
    // End of variables declaration//GEN-END:variables

    //------------------------------------------------------------------
    private class PermisosCargarSwingWorker extends SwingWorker<List<Object>, Void>
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
            if (!isCancelled() && arguments != null) {
                arguments.add(parent.obtenerSemilla(elementoActual));
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
                    List<NodoPermiso> semilla = (List<NodoPermiso>) results.get(0);
                    setDisplayData(semilla);
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
