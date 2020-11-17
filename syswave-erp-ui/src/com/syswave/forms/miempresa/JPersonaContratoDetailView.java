package com.syswave.forms.miempresa;

import com.orbital.AutoCompleteDocument;
import com.syswave.entidades.miempresa.Jornada;
import com.syswave.entidades.miempresa.Persona;
import com.syswave.entidades.miempresa.PersonaContrato;
import com.syswave.entidades.miempresa.Puesto;
import com.syswave.entidades.miempresa.Valor;
import com.syswave.swing.renders.POJOListCellRenderer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author sis5
 */
public class JPersonaContratoDetailView extends javax.swing.JInternalFrame
{

    IPersonaContratoMediator parent;
    PersonaContrato elementoActual;
    boolean esNuevo;

    public JPersonaContratoDetailView(IPersonaContratoMediator owner)
    {
        initComponents();
        initEvents();
        esNuevo = true;
        parent = owner;
        jcPersona.setModel(parent.obtenerComboPersonas());
        jcPersona.setRenderer(new POJOListCellRenderer<Persona>());
        AutoCompleteDocument.enable(jcPersona);
        jcArea.setModel(parent.obtenerComboAreas());
        jcArea.setRenderer(new POJOListCellRenderer<Valor>());
        AutoCompleteDocument.enable(jcArea);
        jcPuesto.setModel(parent.obtenerComboPuestos());
        jcPuesto.setRenderer(new POJOListCellRenderer<Puesto>());
        AutoCompleteDocument.enable(jcPuesto);
        jcJornada.setModel(parent.obtenerComboJornadas());
        jcJornada.setRenderer(new POJOListCellRenderer<Jornada>());
        AutoCompleteDocument.enable(jcJornada); 
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

        jbAceptar.addActionListener(actionListenerManager);
        jbCancelar.addActionListener(actionListenerManager);

    }

    //---------------------------------------------------------------------
    private void finish_actionPerformed(ActionEvent evt)
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
        else
            close();
    }

    public boolean readElement(PersonaContrato elemento)
    {
        boolean resultado = false;

        if (jchPermanente.isSelected())
        {
            if (jcPersona.getSelectedIndex() > -1 && jcArea.getSelectedIndex() > -1
                    && jcPuesto.getSelectedIndex() > -1 && jdFechaInicio.getDate() != null 
                    && jcJornada.getSelectedIndex() > -1)
            {
               elemento.setIdPersona((int) parent.obtenerComboPersonas().getSelectedValue());
                elemento.setIdArea((int) parent.obtenerComboAreas().getSelectedValue());
                elemento.setIdPuesto((int) parent.obtenerComboPuestos().getSelectedValue());
                elemento.setFechaInicio(jdFechaInicio.getDate());
                elemento.setFechaTerminacion(jdFechaFin.getDate());
                elemento.setIdJornada((int) parent.obtenerComboJornadas().getSelectedValue());
                elemento.setEsTipo(jchPermanente.isSelected() ? 0 : 1);
                resultado = true; 
            }
            else
                JOptionPane.showMessageDialog(this, "Asegúrese de proporcionar todos los datos.", "", JOptionPane.PLAIN_MESSAGE);
        }
        else
        {
            if (jcPersona.getSelectedIndex() > -1 && jcArea.getSelectedIndex() > -1
                    && jcPuesto.getSelectedIndex() > -1 && jdFechaInicio.getDate() != null
                    && jdFechaFin.getDate() != null && jcJornada.getSelectedIndex() > -1)
            {
                elemento.setIdPersona((int) parent.obtenerComboPersonas().getSelectedValue());
                elemento.setIdArea((int) parent.obtenerComboAreas().getSelectedValue());
                elemento.setIdPuesto((int) parent.obtenerComboPuestos().getSelectedValue());
                elemento.setFechaInicio(jdFechaInicio.getDate());
                elemento.setFechaTerminacion(jdFechaFin.getDate());
                elemento.setIdJornada((int) parent.obtenerComboJornadas().getSelectedValue());
                elemento.setEsTipo(jchPermanente.isSelected() ? 0 : 1);
                resultado = true;
            }
            else
                JOptionPane.showMessageDialog(this, "Asegúrese de proporcionar todos los datos.", "", JOptionPane.PLAIN_MESSAGE);
        }

        return resultado;
    }

    //---------------------------------------------------------------------
    private void writeElement(PersonaContrato elemento)
    {
        jcPersona.setSelectedItem(parent.obtenerComboPersonas().getElementAt(parent.obtenerComboPersonas().indexOfValue(elemento.getIdPersona())));
        jcArea.setSelectedItem(parent.obtenerComboAreas().getElementAt(parent.obtenerComboAreas().indexOfValue(elemento.getIdArea())));
        jcPuesto.setSelectedItem(parent.obtenerComboPuestos().getElementAt(parent.obtenerComboPuestos().indexOfValue(elemento.getIdPuesto())));
        jcJornada.setSelectedItem(parent.obtenerComboJornadas().getElementAt(parent.obtenerComboJornadas().indexOfValue(elemento.getIdJornada())));
        jdFechaInicio.setDate(elemento.getFechaInicio());
        jdFechaFin.setDate(elemento.getFechaTerminacion());
        jchPermanente.setSelected((elemento.getEsTipo() == 0));
    }

    //---------------------------------------------------------------------
    public void close()
    {
        setVisible(false);
        dispose();
    }

    void prepareForModify(PersonaContrato elemento)
    {
        this.setTitle("Modificando contrato " + elemento.getConsecutivo() + " del empleado " + elemento.getIdPersona());
        elementoActual = elemento;
        esNuevo = false;
        writeElement(elemento);
    }

    void prepareForNew()
    {
        this.setTitle("Nuevo");
        elementoActual = new PersonaContrato();
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

        jLabel1 = new javax.swing.JLabel();
        jpEncabezado = new javax.swing.JPanel();
        jlbIcono = new javax.swing.JLabel();
        jpActions = new javax.swing.JPanel();
        jbCancelar = new javax.swing.JButton();
        jbAceptar = new javax.swing.JButton();
        jpBody = new javax.swing.JTabbedPane();
        tabGeneral = new javax.swing.JPanel();
        jpDatos1 = new javax.swing.JPanel();
        jlbPersona = new javax.swing.JLabel();
        jcPersona = new javax.swing.JComboBox();
        jlbArea = new javax.swing.JLabel();
        jcArea = new javax.swing.JComboBox();
        jlbPuesto = new javax.swing.JLabel();
        jcPuesto = new javax.swing.JComboBox();
        jlbFechaInicio = new javax.swing.JLabel();
        jdFechaInicio = new com.toedter.calendar.JDateChooser();
        jlbFechaTerminacion = new javax.swing.JLabel();
        jdFechaFin = new com.toedter.calendar.JDateChooser();
        jlbJornada = new javax.swing.JLabel();
        jcJornada = new javax.swing.JComboBox();
        jlbPermanente = new javax.swing.JLabel();
        jchPermanente = new javax.swing.JCheckBox();

        jLabel1.setText("jLabel1");

        setClosable(true);
        setMaximizable(true);
        setResizable(true);

        jpEncabezado.setBackground(new java.awt.Color(15, 120, 158));
        jpEncabezado.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jlbIcono.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/syswave/forms/resources/window.png"))); // NOI18N
        jpEncabezado.add(jlbIcono);

        getContentPane().add(jpEncabezado, java.awt.BorderLayout.NORTH);

        jpActions.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jbCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/syswave/forms/resources/button-cross.png"))); // NOI18N
        jbCancelar.setText("Cancelar");
        jpActions.add(jbCancelar);

        jbAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/syswave/forms/resources/button-tick.png"))); // NOI18N
        jbAceptar.setText("Aceptar");
        jpActions.add(jbAceptar);

        getContentPane().add(jpActions, java.awt.BorderLayout.SOUTH);

        tabGeneral.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        java.awt.GridBagLayout jpDatos1Layout = new java.awt.GridBagLayout();
        jpDatos1Layout.columnWidths = new int[] {0, 6, 0};
        jpDatos1Layout.rowHeights = new int[] {0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0};
        jpDatos1.setLayout(jpDatos1Layout);

        jlbPersona.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbPersona.setText("Empleado:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        jpDatos1.add(jlbPersona, gridBagConstraints);
        jlbPersona.getAccessibleContext().setAccessibleName("");

        jcPersona.setPreferredSize(new java.awt.Dimension(400, 27));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jpDatos1.add(jcPersona, gridBagConstraints);

        jlbArea.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbArea.setText("Área:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        jpDatos1.add(jlbArea, gridBagConstraints);

        jcArea.setPreferredSize(new java.awt.Dimension(200, 27));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jpDatos1.add(jcArea, gridBagConstraints);

        jlbPuesto.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbPuesto.setText("Puesto:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        jpDatos1.add(jlbPuesto, gridBagConstraints);

        jcPuesto.setPreferredSize(new java.awt.Dimension(200, 27));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jpDatos1.add(jcPuesto, gridBagConstraints);

        jlbFechaInicio.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbFechaInicio.setText("Fecha inicio:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        jpDatos1.add(jlbFechaInicio, gridBagConstraints);

        jdFechaInicio.setPreferredSize(new java.awt.Dimension(200, 27));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jpDatos1.add(jdFechaInicio, gridBagConstraints);

        jlbFechaTerminacion.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbFechaTerminacion.setText("Fecha terminación:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        jpDatos1.add(jlbFechaTerminacion, gridBagConstraints);

        jdFechaFin.setPreferredSize(new java.awt.Dimension(200, 27));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jpDatos1.add(jdFechaFin, gridBagConstraints);

        jlbJornada.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbJornada.setText("Jornada:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        jpDatos1.add(jlbJornada, gridBagConstraints);

        jcJornada.setPreferredSize(new java.awt.Dimension(350, 27));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jpDatos1.add(jcJornada, gridBagConstraints);

        jlbPermanente.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbPermanente.setText("Permanente:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        jpDatos1.add(jlbPermanente, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jpDatos1.add(jchPermanente, gridBagConstraints);

        tabGeneral.add(jpDatos1);

        jpBody.addTab("General", tabGeneral);

        getContentPane().add(jpBody, java.awt.BorderLayout.CENTER);

        setBounds(0, 0, 644, 444);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton jbAceptar;
    private javax.swing.JButton jbCancelar;
    private javax.swing.JComboBox jcArea;
    private javax.swing.JComboBox jcJornada;
    private javax.swing.JComboBox jcPersona;
    private javax.swing.JComboBox jcPuesto;
    private javax.swing.JCheckBox jchPermanente;
    private com.toedter.calendar.JDateChooser jdFechaFin;
    private com.toedter.calendar.JDateChooser jdFechaInicio;
    private javax.swing.JLabel jlbArea;
    private javax.swing.JLabel jlbFechaInicio;
    private javax.swing.JLabel jlbFechaTerminacion;
    private javax.swing.JLabel jlbIcono;
    private javax.swing.JLabel jlbJornada;
    private javax.swing.JLabel jlbPermanente;
    private javax.swing.JLabel jlbPersona;
    private javax.swing.JLabel jlbPuesto;
    private javax.swing.JPanel jpActions;
    private javax.swing.JTabbedPane jpBody;
    private javax.swing.JPanel jpDatos1;
    private javax.swing.JPanel jpEncabezado;
    private javax.swing.JPanel tabGeneral;
    // End of variables declaration//GEN-END:variables

}
