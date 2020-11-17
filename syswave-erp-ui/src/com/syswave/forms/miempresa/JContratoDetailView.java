package com.syswave.forms.miempresa;

import com.syswave.entidades.miempresa.Contrato;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author sis5
 */
public class JContratoDetailView extends javax.swing.JInternalFrame
{

    Contrato elementoActual;
    boolean esNuevo;
    IContratoMediator parent;
    byte[] data;

    JContratoDetailView(IContratoMediator owner)
    {
        initComponents();
        initEvents();
        esNuevo = true;
        elementoActual = new Contrato();
        parent = owner;
    }

    private void initEvents()
    {
        ActionListener actionListenerManager
                = new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent evt)
                    {
                        try
                        {
                            finish_actionPerformed(evt);
                        }
                        catch (IOException ex)
                        {
                            Logger.getLogger(JContratoDetailView.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                };
        jbAceptar.addActionListener(actionListenerManager);
        jbCancelar.addActionListener(actionListenerManager);
        jbBuscar.addActionListener(actionListenerManager);
    }

    //---------------------------------------------------------------------
    private void finish_actionPerformed(ActionEvent evt) throws IOException
    {
        Object sender = evt.getSource();

        if (sender == jbAceptar)
        {
            if (readElement(elementoActual))
                if (esNuevo)
                {
                    parent.onAcceptNewElement(elementoActual);
                    close();
                }
                else
                {
                    parent.onAcceptModifyElement(elementoActual);
                    close();
                };
        }
        else if (sender == jbCancelar)
            close();

        else if (sender == jbBuscar)
        {
            FileNameExtensionFilter filtroDoc = new FileNameExtensionFilter("DOC & DOCX", "doc", "docx");
            jfcMachote.setFileFilter(filtroDoc);
            jfcMachote.setDialogTitle("Elija el formato del contrato");
            if (jfcMachote.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
            {
                String nombre = jfcMachote.getName(jfcMachote.getSelectedFile());
                
                jtfNombre.setText(nombre.substring(0, nombre.lastIndexOf(".")));
                Path ruta = (Path) jfcMachote.getSelectedFile().toPath();
                jtfRuta.setText(ruta.toString());
                data = Files.readAllBytes(ruta);
            }

        }
    }

    //---------------------------------------------------------------------
    public void close()
    {
        setVisible(false);
        dispose();
    }

    private boolean readElement(Contrato elemento)
    {
        boolean resultado = false;

        if (!jtfNombre.getText().isEmpty())
        {
            elemento.setNombre(jtfNombre.getText());
            elemento.setFormato(".doc");

            if (data != null)
            {
                elemento.setContenido(data);
                elemento.setLongitud(data.length);
            }

            resultado = true;
        }

        return resultado;
    }

    private void writeElement(Contrato elemento)
    {
        jtfNombre.setText(elemento.getNombre());
    }

    void prepareForModify(Contrato elemento)
    {
        this.setTitle(String.format("Modificando el contrato %s", elemento.getNombre()));
        esNuevo = false;
        elementoActual = elemento;
        writeElement(elemento);
    }

    void prepareForNew()
    {
        this.setTitle("Nuevo");
        elementoActual = new Contrato();
        esNuevo = true;
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
        java.awt.GridBagConstraints gridBagConstraints;

        jfcMachote = new javax.swing.JFileChooser();
        jpEncabezado = new javax.swing.JPanel();
        jlbIcono = new javax.swing.JLabel();
        jpActions = new javax.swing.JPanel();
        jbCancelar = new javax.swing.JButton();
        jbAceptar = new javax.swing.JButton();
        jpBody = new javax.swing.JTabbedPane();
        tabGeneral = new javax.swing.JPanel();
        jpDatos = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jtfNombre = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jtfRuta = new javax.swing.JTextField();
        jbBuscar = new javax.swing.JButton();

        setClosable(true);
        setMaximizable(true);
        setResizable(true);

        jpEncabezado.setBackground(new java.awt.Color(81, 163, 131));

        jlbIcono.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/syswave/forms/resources/window.png"))); // NOI18N

        javax.swing.GroupLayout jpEncabezadoLayout = new javax.swing.GroupLayout(jpEncabezado);
        jpEncabezado.setLayout(jpEncabezadoLayout);
        jpEncabezadoLayout.setHorizontalGroup(
            jpEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpEncabezadoLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jlbIcono)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpEncabezadoLayout.setVerticalGroup(
            jpEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpEncabezadoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlbIcono)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jpEncabezado, java.awt.BorderLayout.NORTH);

        jpActions.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jbCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/syswave/forms/resources/button-cross.png"))); // NOI18N
        jbCancelar.setText("Cancelar");
        jpActions.add(jbCancelar);

        jbAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/syswave/forms/resources/button-tick.png"))); // NOI18N
        jbAceptar.setText("Aceptar");
        jpActions.add(jbAceptar);

        getContentPane().add(jpActions, java.awt.BorderLayout.SOUTH);

        jpDatos.setLayout(new java.awt.GridBagLayout());

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Nombre:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        jpDatos.add(jLabel1, gridBagConstraints);

        jtfNombre.setPreferredSize(new java.awt.Dimension(120, 27));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jpDatos.add(jtfNombre, gridBagConstraints);
        jpDatos.add(jLabel3, new java.awt.GridBagConstraints());

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Archivo:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        jpDatos.add(jLabel2, gridBagConstraints);

        jtfRuta.setEnabled(false);
        jtfRuta.setPreferredSize(new java.awt.Dimension(220, 27));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jpDatos.add(jtfRuta, gridBagConstraints);

        jbBuscar.setText("Buscar");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        jpDatos.add(jbBuscar, gridBagConstraints);

        tabGeneral.add(jpDatos);

        jpBody.addTab("General", tabGeneral);

        getContentPane().add(jpBody, java.awt.BorderLayout.CENTER);

        setBounds(0, 0, 449, 320);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JButton jbAceptar;
    private javax.swing.JButton jbBuscar;
    private javax.swing.JButton jbCancelar;
    private javax.swing.JFileChooser jfcMachote;
    private javax.swing.JLabel jlbIcono;
    private javax.swing.JPanel jpActions;
    private javax.swing.JTabbedPane jpBody;
    private javax.swing.JPanel jpDatos;
    private javax.swing.JPanel jpEncabezado;
    private javax.swing.JTextField jtfNombre;
    private javax.swing.JTextField jtfRuta;
    private javax.swing.JPanel tabGeneral;
    // End of variables declaration//GEN-END:variables
}