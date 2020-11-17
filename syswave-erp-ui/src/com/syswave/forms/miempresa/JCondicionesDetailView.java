/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.syswave.forms.miempresa;

import com.syswave.entidades.miempresa.Condicion;
import com.syswave.forms.databinding.CondicionComponentesTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Aimee García
 */
public class JCondicionesDetailView extends javax.swing.JInternalFrame {

    CondicionComponentesTableModel tmComponentes;
    ICondicionesMediator owner;
   // CondicionesBusinessLogic condiciones;

    /**
     * Creates new form CondicionesDetailView
     */
    public JCondicionesDetailView(ICondicionesMediator the_owner) {
        owner = the_owner;
        tmComponentes = new CondicionComponentesTableModel(
                new String[]{"Valor:{valor}", "Unidad:{es_unidad}", "Tipo:{es_tipo}"}
        );
        initComponents();
        initEvents();
    }

    private void initEvents() {
        ActionListener accionl = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                jbtnCancelar_actionPerformed(ae);
            }
        };

        jbtnCancelar.addActionListener(accionl);
        jbtnAceptar.addActionListener(accionl);
    }

   /* public CondicionesBusinessLogic getLogic() {
        return condiciones;
    }

    public void setLogic(CondicionesBusinessLogic condiciones) {
        this.condiciones = condiciones;
    }*/
    
    

    private void jbtnCancelar_actionPerformed(ActionEvent ae) {
        if (ae.getSource() == jbtnCancelar)
        {
            dispose();
        }
        
        else if (ae.getSource() == jbtnAceptar)
        {
            Condicion nuevo = new Condicion();
            nuevo.setNombre(jtxtNombre.getText());
            nuevo.setActivo(ckbActivo.isSelected());
            owner.onAcceptNewElement(nuevo);
            //condiciones.agregar(nuevo);
        }
    }

        //---------------------------------------------------------------------
   /* private void conditionsToolBar_actionPerformed(ActionEvent evt)
     {
     //int rowIndex;

     if (evt.getSource() == jbtnEspecificarCondicion)
     {
     Documento_tiene_Condicion_5FN current = bsCondiciones.getElementAt(jtDocumentoTieneCondiciones.getSelectedRow());

     JCondicionesPagoBusqueda dialogo = new JCondicionesPagoBusqueda(this);

     dialogo.limitarSeccion(current.getId());
     dialogo.setTitle(String.format("Busqueda de %s", current.getDescripcion()));
     dialogo.busquedaInicial();

     parent.agregaContenedorPrincipal(dialogo);
     parent.mostrarCentrado(dialogo);
     }

     else if (evt.getSource() == jbtnQuitarCondicion)
     {
     if (JOptionPane.showConfirmDialog(this, String.format("Ha seleccionado %d registro(s) para ser eleiminado(s)\n¿Esta seguro de continuar?", jtDocumentoTieneCondiciones.getSelectedRowCount())) == JOptionPane.OK_OPTION)
     {
     Documento_tiene_Condicion_5FN new_reset, elemento;
     int[] rowsHandlers = jtDocumentoTieneCondiciones.getSelectedRows();
     List<Documento_tiene_Condicion_5FN> selecteds = bsCondiciones.getElementsAt(rowsHandlers);

     for (int i = 0; i < selecteds.size(); i++)
     {
     elemento = selecteds.get(i);
     //¿Ya se había relacionado el precio con el documento?
     if (elemento.getIdDocumento() == elementoActual.getId())
     {
     if (!elemento.isNew())
     condiciones_deleted.add(elemento);

     //Nota: Es necesario que el elemento actual pierda todo vinculo 
     //con el documento.
     new_reset = new Documento_tiene_Condicion_5FN();
     new_reset.setSearchOnlyByPrimaryKey(elemento.isSearchOnlyByPrimaryKey());
     //new_reset.getFk_documento_condicion_id().clear();
     new_reset.setCondicion("");
     new_reset.setId(elemento.getId());
     new_reset.setDescripcion(elemento.getDescripcion());
     new_reset.acceptChanges();

     bsCondiciones.getData().set(rowsHandlers[i], new_reset);
     }
     }

     bsCondiciones.fireTableDataChanged();
     generarTextoCondiciones();
     }
     }
     }

     //---------------------------------------------------------------------
     @Override
     public void onAcceptNewElement(Condicion nuevo)
     {
     int index = -1, i = 0; // jtDocumentoTieneCondiciones.getSelectedRow();

     while (index < 0 && i < bsCondiciones.getRowCount())
     {
     if (bsCondiciones.getElementAt(i).getId() == nuevo.getIdTipoCondicion())
     index = i;
     else
     i++;
     }

     if (index >= 0)
     {
     Documento_tiene_Condicion_5FN seleccionado = bsCondiciones.getElementAt(index);
     Condicion inCondicion;

     inCondicion = seleccionado.getFk_documento_condicion_id();

     inCondicion.setId(nuevo.getId());
     if (!inCondicion.isSet())
     inCondicion.setModified(); //Prevenir cambios en la llave vieja ya guardada.
     inCondicion.setNombre(nuevo.getNombre());
     inCondicion.setValor(nuevo.getValor());
     inCondicion.setUnidad(nuevo.getUnidad());
     //No copiar ni la seccion, ni la descripción. (Llave foranea al tipo no)
     //inCondicion.copy(nuevo);

     if (!seleccionado.isSet())
     {
     if (seleccionado.getIdDocumento() == Documento_tiene_Condicion_5FN.EMPTY_INT
     || seleccionado.getIdDocumento() != elementoActual.getId())
     {
     seleccionado.setFk_condicion_documento_id(elementoActual);
     seleccionado.setNew();
     }

     else
     seleccionado.setModified();
     }

     bsCondiciones.fireTableRowsUpdated(index, index);
     generarTextoCondiciones();
     }

     else
     JOptionPane.showMessageDialog(this, "No se pudo determinar en donde establecer el valor seleccionado");
     }

     //---------------------------------------------------------------------
     public void generarTextoCondiciones()
     {
     List<Documento_tiene_Condicion_5FN> elementos = bsCondiciones.getData();
     StringBuilder texto = new StringBuilder();

     for (Documento_tiene_Condicion_5FN parte : elementos)
     {
     if (texto.length() > 0 && !parte.getCondicion().isEmpty())
     texto.append(", ");
     texto.append(parte.getCondicion());
     }

     jtfCondiciones.setText(texto.toString());
     }*/
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

        jTabbedPane1 = new javax.swing.JTabbedPane();
        tabGeneral = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jtxtNombre = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        ckbActivo = new javax.swing.JCheckBox();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jToolBar1 = new javax.swing.JToolBar();
        jbtnInsertar = new javax.swing.JButton();
        jbtnEliminar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jbtnCancelar = new javax.swing.JButton();
        jbtnAceptar = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

        tabGeneral.setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 432;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 105);
        jPanel1.add(jtxtNombre, gridBagConstraints);

        jLabel1.setText("Nombre:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel1.add(jLabel1, gridBagConstraints);

        ckbActivo.setText("Activo?");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel1.add(ckbActivo, gridBagConstraints);

        tabGeneral.add(jPanel1, java.awt.BorderLayout.NORTH);

        jPanel3.setLayout(new java.awt.BorderLayout());

        jTable1.setModel(tmComponentes);
        jScrollPane1.setViewportView(jTable1);

        jPanel3.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jToolBar1.setRollover(true);

        jbtnInsertar.setText("Insertar");
        jbtnInsertar.setFocusable(false);
        jbtnInsertar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbtnInsertar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jbtnInsertar);

        jbtnEliminar.setText("Eliminar");
        jbtnEliminar.setFocusable(false);
        jbtnEliminar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbtnEliminar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jbtnEliminar);

        jPanel3.add(jToolBar1, java.awt.BorderLayout.PAGE_START);

        tabGeneral.add(jPanel3, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("General", tabGeneral);

        getContentPane().add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jbtnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/syswave/forms/resources/button-cross.png"))); // NOI18N
        jbtnCancelar.setLabel("Cancelar");
        jPanel2.add(jbtnCancelar);

        jbtnAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/syswave/forms/resources/button-tick.png"))); // NOI18N
        jbtnAceptar.setLabel("Aceptar");
        jPanel2.add(jbtnAceptar);

        getContentPane().add(jPanel2, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox ckbActivo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JButton jbtnAceptar;
    private javax.swing.JButton jbtnCancelar;
    private javax.swing.JButton jbtnEliminar;
    private javax.swing.JButton jbtnInsertar;
    private javax.swing.JTextField jtxtNombre;
    private javax.swing.JPanel tabGeneral;
    // End of variables declaration//GEN-END:variables

}
