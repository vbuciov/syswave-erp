package com.syswave.forms.miempresa;

import com.orbital.AutoCompleteDocument;
import com.syswave.entidades.miempresa.Horario;
import com.syswave.entidades.miempresa.Jornada;
import com.syswave.forms.databinding.JornadaComboBoxModel;
import com.syswave.swing.renders.POJOListCellRenderer;
import com.syswave.logicas.miempresa.JornadasBusinessLogic;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SwingWorker;

/**
 *
 * @author sis5
 */
public class JHorarioDetailView extends javax.swing.JInternalFrame
{

    private final int opLOAD = 0;
    Horario elementoActual;
    boolean esNuevo;
    JornadasBusinessLogic jornadas;
    JornadaComboBoxModel bsJornadas;
    IHorarioMediator parent;
    HorarioDetailSwingWorker swSecondPlane;

    //---------------------------------------------------------------------
    public JHorarioDetailView(IHorarioMediator owner)
    {
        esNuevo = true;
        initComponents();
        initEvents();
        parent = owner;
        jornadas = new JornadasBusinessLogic(parent.obtenerOrigenDato());
        jsHoraInicial.setEditor(new JSpinner.DateEditor(jsHoraInicial, "HH:mm"));
        jsHoraFinal.setEditor(new JSpinner.DateEditor(jsHoraFinal, "HH:mm"));
        bsJornadas = new JornadaComboBoxModel();
        cbJornadas.setModel(bsJornadas);
        cbJornadas.setRenderer(new POJOListCellRenderer<Jornada>());
        AutoCompleteDocument.enable(cbJornadas);
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
    
   


    //---------------------------------------------------------------------
    public void close()
    {
        setVisible(false);
        dispose();
    }

    //---------------------------------------------------------------------
    void prepareForModify(Horario elemento)
    {
        this.setTitle("Modificando " + elemento.getNombre());
        this.elementoActual = elemento;
        esNuevo = false;
        bsJornadas.setData(parent.obtenerJornadas());
        writeElement(elemento);
    }

    //---------------------------------------------------------------------
    void prepareForNew()
    {
        this.setTitle("Nuevo");
        elementoActual = new Horario();
        esNuevo = true;
        bsJornadas.setData(parent.obtenerJornadas());
    }
    
    //---------------------------------------------------------------------
    private boolean readElement(Horario elemento)
    {
        boolean resultado = false;

        if (!jtfNombre.getText().isEmpty() && cbJornadas.getSelectedIndex() > -1)
        {
            Date hrIni = (Date) jsHoraInicial.getValue();
            Date hrFin = (Date) jsHoraFinal.getValue();
            if (hrFin.compareTo(hrIni) < 0)
                JOptionPane.showMessageDialog(this, "La hora final no debe de ser menor a la inicial.", "", JOptionPane.PLAIN_MESSAGE);
            else
            {
                elemento.setIdJornada((int) bsJornadas.getSelectedValue());
                elemento.setNombre(jtfNombre.getText());
                elemento.setHoraInicio((Date)jsHoraInicial.getValue());
                elemento.setHoraFin((Date) jsHoraFinal.getValue());
                resultado = true;
            }
        }
        else
            JOptionPane.showMessageDialog(this, "Asegúrese de proporcionar el nombre del horario y/o la jornada.", "", JOptionPane.PLAIN_MESSAGE);
        
        return resultado;
    }

    //---------------------------------------------------------------------
    private void writeElement(Horario elemento)
    {
        cbJornadas.setSelectedItem(bsJornadas.getElementAt(bsJornadas.indexOfValue(elemento.getIdJornada())));
        jtfNombre.setText(elemento.getNombre());
        jsHoraInicial.setValue(elemento.getHoraInicio());
        jsHoraFinal.setValue(elemento.getHoraFin());
    }

    //---------------------------------------------------------------------
    private void setDisplayData(List<Jornada> datos)
    {
        bsJornadas.setData(datos);
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
        jpActions = new javax.swing.JPanel();
        jbCancelar = new javax.swing.JButton();
        jbAceptar = new javax.swing.JButton();
        jpBody = new javax.swing.JTabbedPane();
        jpDatos = new javax.swing.JPanel();
        jpDimensiones = new javax.swing.JPanel();
        jlbJornada = new javax.swing.JLabel();
        cbJornadas = new javax.swing.JComboBox();
        jlblNombre = new javax.swing.JLabel();
        jtfNombre = new javax.swing.JTextField();
        jblHoraInicio = new javax.swing.JLabel();
        jsHoraInicial = new JSpinner(new javax.swing.SpinnerDateModel(new java.util.Date(), null, null, java.util.Calendar.HOUR_OF_DAY));
        jblHoraFin = new javax.swing.JLabel();
        jsHoraFinal = new JSpinner(new javax.swing.SpinnerDateModel(new java.util.Date(), null, null, java.util.Calendar.HOUR_OF_DAY));

        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setName(""); // NOI18N

        jpEncabezado.setBackground(new java.awt.Color(176, 79, 80));
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

        jpDimensiones.setPreferredSize(new java.awt.Dimension(450, 110));
        jpDimensiones.setLayout(new java.awt.GridLayout(4, 2));

        jlbJornada.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbJornada.setText("ID Jornada:");
        jpDimensiones.add(jlbJornada);

        jpDimensiones.add(cbJornadas);

        jlblNombre.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlblNombre.setText("Nombre:");
        jpDimensiones.add(jlblNombre);
        jpDimensiones.add(jtfNombre);

        jblHoraInicio.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jblHoraInicio.setText("Hora de inicio:");
        jpDimensiones.add(jblHoraInicio);
        jpDimensiones.add(jsHoraInicial);

        jblHoraFin.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jblHoraFin.setText("Hora de termino:");
        jblHoraFin.setToolTipText("");
        jpDimensiones.add(jblHoraFin);
        jpDimensiones.add(jsHoraFinal);

        jpDatos.add(jpDimensiones);

        jpBody.addTab("General", jpDatos);

        getContentPane().add(jpBody, java.awt.BorderLayout.CENTER);

        setBounds(0, 0, 503, 295);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cbJornadas;
    private javax.swing.JButton jbAceptar;
    private javax.swing.JButton jbCancelar;
    private javax.swing.JLabel jblHoraFin;
    private javax.swing.JLabel jblHoraInicio;
    private javax.swing.JLabel jlbIcono;
    private javax.swing.JLabel jlbJornada;
    private javax.swing.JLabel jlblNombre;
    private javax.swing.JPanel jpActions;
    private javax.swing.JTabbedPane jpBody;
    private javax.swing.JPanel jpDatos;
    private javax.swing.JPanel jpDimensiones;
    private javax.swing.JPanel jpEncabezado;
    private javax.swing.JSpinner jsHoraFinal;
    private javax.swing.JSpinner jsHoraInicial;
    private javax.swing.JTextField jtfNombre;
    // End of variables declaration//GEN-END:variables

    private class HorarioDetailSwingWorker extends SwingWorker<List<Object>, Void>
    {

        private List<Object> arguments;

        //------------------------------------------------------------------
        public void execute(List<Object> values)
        {
            arguments = values;
            execute();
        }

        @Override
        protected List<Object> doInBackground() throws Exception
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
                        results.add(jornadas.obtenerLista());
                        break;
                }
                setProgress(100);
                return results;
            }
            else
                return null;
        }

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
                                List<Jornada> listaJornadas = (List<Jornada>) results.get(1);
                                setDisplayData(listaJornadas);
                                break;

                        }
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
                {
                    arguments.clear();
                }
            }
        }
    }
}
