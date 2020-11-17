package com.syswave.forms.common;

import com.codeproject.JClosableTabbedPane;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class JListadoInternalFrame extends javax.swing.JInternalFrame implements PropertyChangeListener
{

    //---------------------------------------------------------------------

    /**
     * Creates new form JListadoInternalFrame
     */
    public JListadoInternalFrame()
    {
        initComponents();
        initEvents();
    }

    //---------------------------------------------------------------------
    private void initEvents()
    {
        ActionListener actionManager = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                jmActualizacion_actionPerformed(e);
            }
        };

        ActionListener navigatioManager = new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                jmNavigation_actionPerformed(e);
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

        tpContenido.addChangeListener(changeListenerManager);

        jmNuevo.addActionListener(actionManager);
        jbToolNuevo.addActionListener(actionManager);
        jmModificar.addActionListener(actionManager);
        jbToolModificar.addActionListener(actionManager);
        jmEliminar.addActionListener(actionManager);
        jbToolEliminar.addActionListener(actionManager);
        jmAbrir.addActionListener(actionManager);
        jmGuardar.addActionListener(actionManager);
        jbToolGuardar.addActionListener(actionManager);
        jmImprimir.addActionListener(actionManager);
        jmExportExcel.addActionListener(actionManager);
        jmCerrar.addActionListener(actionManager);
        jmCerrarTodo.addActionListener(actionManager);
        jmRecargar.addActionListener(actionManager);

        jmSiguiente.addActionListener(navigatioManager);
        jmPrimero.addActionListener(navigatioManager);
        jmAnterior.addActionListener(navigatioManager);
        jmUltimo.addActionListener(navigatioManager);
        jmFiltros.addActionListener(navigatioManager);
        jmBuscar.addActionListener(navigatioManager);
        jmBuscarSiguiente.addActionListener(navigatioManager);
        jmSeleccionarTodo.addActionListener(navigatioManager);
    }

    //---------------------------------------------------------------------
    private void bodyTabbed_stateChanged(ChangeEvent e)
    {
        //JTabbedPane pane = (JTabbedPane) e.getSource();
        if (isThereSelectedPage())
            jbToolGuardar.setEnabled(((JDataView) tpContenido.getSelectedComponent()).isThereChanges());
    }

    //---------------------------------------------------------------------
    private void jmActualizacion_actionPerformed(ActionEvent evt)
    {
        Object sender = evt.getSource();

        if (sender == jmNuevo || sender == jbToolNuevo)
            createRequest();

        else if (sender == jmModificar || sender == jbToolModificar)
            updateRequest();

        else if (sender == jmEliminar || sender == jbToolEliminar)
            deleteRequest();

        else if (sender == jmAbrir)
            openRequest();

        else if (sender == jmGuardar || sender == jbToolGuardar)
            saveRequest();

        else if (sender == jmImprimir)
            printRequest();

        else if (sender == jmExportExcel)
            exportToXLSRequest();

        else if (sender == jmCerrar)
            closeRequest();

        else if (sender == jmCerrarTodo)
            closeAllRequest();

        else if (sender == jmRecargar)
            retrieveRequest();
    }

    //---------------------------------------------------------------------
    private void jmNavigation_actionPerformed(ActionEvent evt)
    {
        Object sender = evt.getSource();

        if (sender == jmPrimero)
            firstRequest();

        else if (sender == jmSiguiente)
            nextRequest();

        else if (sender == jmAnterior)
            priorRequest();

        else if (sender == jmUltimo)
            lastRequest();

        else if (sender == jmFiltros)
        {
            if (((javax.swing.JCheckBoxMenuItem) sender).isSelected())
                filterRequest();
            else
                undoFilterRequest();
        }

        else if (sender == jmBuscarSiguiente)
            searchNextRequest();

        else if (sender == jmBuscar)
            searchRequest();

        else if (sender == jmSeleccionarTodo)
            selectAllRequest();
    }

    //---------------------------------------------------------------------
    /**
     * Agrega una pestaña más al listado e indica el contenedor padre o
     * DesktopPane.
     *
     * @param titulo Es el titulo que tendra la pestaña.
     * @param newView Es el tipo de vista que se utilizará.
     */
    public void addView(String titulo, JDataView newView)
    {
        tpContenido.addTab(titulo, newView);
        tpContenido.setSelectedComponent(newView);
        newView.addPropertyChangeListener(this);
    }

    //---------------------------------------------------------------------
    /**
     * Determina si existe una instancia de la clase, en alguna pestaña
     * existente.
     *
     * @param view La clase que se quiere evaluar.
     * @return true si contiene la clase
     */
    public int indexOf(Class view)
    {
        int i = 0, index = -1;
        boolean seEncontro = false;
        while (!seEncontro && i < tpContenido.getTabCount())
        {
            Component current = tpContenido.getComponentAt(i++);
            seEncontro = current.getClass().getName().equals(view.getName()) && current != null;
        }

        if (seEncontro)
            index = i;

        return index;

    }

    //---------------------------------------------------------------------
    /**
     * Determina si existe una instancia de la clase, en alguna pestaña
     * existente.
     *
     * @param view La clase que se quiere evaluar.
     * @return true si contiene la clase
     */
    public boolean containsView(Class view)
    {
        int i = 0;
        boolean seEncontro = false;
        while (!seEncontro && i < tpContenido.getTabCount())
        {
            Component current = tpContenido.getComponentAt(i++);
            seEncontro = current.getClass().getName().equals(view.getName()) && current != null;
        }
        return seEncontro;
    }

    //---------------------------------------------------------------------
    /**
     * Determina si existe una instancia de la clase, en alguna pestaña
     * existente.
     *
     * @param view La clase que se quiere evaluar.
     * @return true si contiene la clase
     */
    public boolean selectIfcontainsView(Class view)
    {
        int i = 0;
        boolean seEncontro = false;
        while (!seEncontro && i < tpContenido.getTabCount())
        {
            Component current = tpContenido.getComponentAt(i++);
            seEncontro = current != null && current.getClass().getName().equals(view.getName());
        }

        if (seEncontro)
            tpContenido.setSelectedIndex(i - 1);

        return seEncontro;
    }

    //---------------------------------------------------------------------
    /**
     * Determina si existe una instancia de la clase, en alguna pestaña
     * existente.
     *
     * @param name El nombre de la instancia que se quiere evaluar
     * @return true si contiene la clase
     */
    public boolean selectIfcontainsName(String name)
    {
        int i = 0;
        boolean seEncontro = false;
        while (!seEncontro && i < tpContenido.getTabCount())
        {
            Component current = tpContenido.getComponentAt(i++);
            seEncontro = current != null && current.getName() != null && current.getName().equals(name);
        }

        if (seEncontro)
            tpContenido.setSelectedIndex(i - 1);

        return seEncontro;
    }

    //---------------------------------------------------------------------

    /**
     * Realiza la petición a la pestaña actual, de cargar la información.
     */
    public void retrieveRequest()
    {
        if (isThereSelectedPage())
            ((JDataView) tpContenido.getSelectedComponent()).doRetrieveProcess();
    }

    //---------------------------------------------------------------------
    /**
     * Realiza la petición a la pestaña actual, de guardar los cambios.
     */
    public void saveRequest()
    {
        if (isThereSelectedPage())
            ((JDataView) tpContenido.getSelectedComponent()).doSaveProcess();
    }

    //---------------------------------------------------------------------
    /**
     * Realiza la petición a la pestaña actual, de crear un nuevo elemento.
     */
    public void createRequest()
    {
        if (isThereSelectedPage())
            ((JDataView) tpContenido.getSelectedComponent()).doCreateProcess();
    }

    //---------------------------------------------------------------------
    /**
     * Realiza la petición a la pestaña actual, de modificar el elemento
     * seleccionado.
     */
    public void updateRequest()
    {
        if (isThereSelectedPage())
            ((JDataView) tpContenido.getSelectedComponent()).doUpdateProcess();
    }

    //---------------------------------------------------------------------
    /**
     * Realiza la petición a la pestaña actual, de borrar el elemento
     * seleccionado.
     */
    public void deleteRequest()
    {
        if (isThereSelectedPage())
            ((JDataView) tpContenido.getSelectedComponent()).doDeleteProcess();
    }

    //---------------------------------------------------------------------
    /**
     * Realiza la petición a la pestaña actual, de abrir para solo lectura el
     * registro.
     */
    public void openRequest()
    {
        if (isThereSelectedPage())
            ((JDataView) tpContenido.getSelectedComponent()).doOpenProcess();
    }

    //---------------------------------------------------------------------
    /**
     * Realiza la petición a la pestaña actual, de imprimir la información
     * desplegada.
     */
    public void printRequest()
    {
        if (isThereSelectedPage())
            ((JDataView) tpContenido.getSelectedComponent()).doPrint();
    }

    //---------------------------------------------------------------------
    /**
     * Indica si hay alguna página seleccionada.
     */
    public boolean isThereSelectedPage()
    {
        return tpContenido.getSelectedComponent() != null;
    }

    //---------------------------------------------------------------------
    public void firstRequest()
    {
        if (isThereSelectedPage())
            ((JDataView) tpContenido.getSelectedComponent()).goToFirst();
    }

    //---------------------------------------------------------------------
    public void nextRequest()
    {
        if (isThereSelectedPage())
            ((JDataView) tpContenido.getSelectedComponent()).goToNext();
    }

    //---------------------------------------------------------------------
    public void priorRequest()
    {
        if (isThereSelectedPage())
            ((JDataView) tpContenido.getSelectedComponent()).goToPrior();
    }

    //-------------------------------------------------------------------
    public void lastRequest()
    {
        if (isThereSelectedPage())
            ((JDataView) tpContenido.getSelectedComponent()).goToLast();
    }

    //---------------------------------------------------------------------
    private void filterRequest()
    {
        if (isThereSelectedPage())
            ((JDataView) tpContenido.getSelectedComponent()).doFilter(true);
    }

    //---------------------------------------------------------------------
    private void undoFilterRequest()
    {
        if (isThereSelectedPage())
            ((JDataView) tpContenido.getSelectedComponent()).doFilter(false);
    }

    //---------------------------------------------------------------------
    private void searchRequest()
    {
        if (isThereSelectedPage())
            ((JDataView) tpContenido.getSelectedComponent()).doStartSearch();
    }

    //---------------------------------------------------------------------
    private void searchNextRequest()
    {
        if (isThereSelectedPage())
            ((JDataView) tpContenido.getSelectedComponent()).doNextSearch();
    }

    //---------------------------------------------------------------------
    public void selectAllRequest()
    {
        if (isThereSelectedPage())
            ((JDataView) tpContenido.getSelectedComponent()).selectAll();
    }

    //---------------------------------------------------------------------
    private void exportToXLSRequest()
    {
        if (isThereSelectedPage())
            ((JDataView) tpContenido.getSelectedComponent()).doExportToXLS();
    }

    //---------------------------------------------------------------------
    private void closeRequest()
    {
        if (tpContenido.getSelectedIndex() >= 0)
            tpContenido.removeTabAt(tpContenido.getSelectedIndex());
    }

    //---------------------------------------------------------------------
    private void closeAllRequest()
    {
        while (tpContenido.getTabCount() > 0)
        {
            tpContenido.removeTabAt(0);
        }
    }

    //---------------------------------------------------------------------
    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        if (evt.getOldValue() != evt.getNewValue())
        {
            if (evt.getPropertyName().equals("state"))
                jbToolGuardar.setEnabled((boolean) evt.getNewValue());
        }
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

        jToolBar1 = new javax.swing.JToolBar();
        jbToolNuevo = new javax.swing.JButton();
        jbToolModificar = new javax.swing.JButton();
        jbToolEliminar = new javax.swing.JButton();
        jbToolGuardar = new javax.swing.JButton();
        tpContenido = new JClosableTabbedPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        jmArchivo = new javax.swing.JMenu();
        jmNuevo = new javax.swing.JMenuItem();
        jmModificar = new javax.swing.JMenuItem();
        jmAbrir = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jmExportar = new javax.swing.JMenu();
        jmExportExcel = new javax.swing.JMenuItem();
        jmExportPDF = new javax.swing.JMenuItem();
        jSeparator9 = new javax.swing.JPopupMenu.Separator();
        jmGuardar = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jmImprimir = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        jmPropiedades = new javax.swing.JMenuItem();
        jmCerrar = new javax.swing.JMenuItem();
        jmCerrarTodo = new javax.swing.JMenuItem();
        jmEditar = new javax.swing.JMenu();
        jmCortar = new javax.swing.JMenuItem();
        jmCopiar = new javax.swing.JMenuItem();
        jmPegar = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        jmSeleccionarTodo = new javax.swing.JMenuItem();
        jSeparator7 = new javax.swing.JPopupMenu.Separator();
        jmEliminar = new javax.swing.JMenuItem();
        jmVer = new javax.swing.JMenu();
        jmRecargar = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        jmchkConVentana = new javax.swing.JCheckBoxMenuItem();
        jmchkConBarraHerramientas = new javax.swing.JCheckBoxMenuItem();
        jmFiltros = new javax.swing.JCheckBoxMenuItem();
        jSeparator8 = new javax.swing.JPopupMenu.Separator();
        jmOrdernar = new javax.swing.JMenu();
        jrmLlavePrimaria = new javax.swing.JRadioButtonMenuItem();
        jmIr = new javax.swing.JMenu();
        jmPrimero = new javax.swing.JMenuItem();
        jmAnterior = new javax.swing.JMenuItem();
        jmSiguiente = new javax.swing.JMenuItem();
        jmUltimo = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        jmBuscar = new javax.swing.JMenuItem();
        jmBuscarSiguiente = new javax.swing.JMenuItem();
        jmAyuda = new javax.swing.JMenu();
        jmTodaAyuda = new javax.swing.JMenuItem();
        jmAcercaDe = new javax.swing.JMenuItem();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setPreferredSize(new java.awt.Dimension(800, 600));

        jToolBar1.setRollover(true);

        jbToolNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/syswave/forms/resources/button-add.png"))); // NOI18N
        jbToolNuevo.setText("Nuevo");
        jbToolNuevo.setFocusable(false);
        jbToolNuevo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbToolNuevo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jbToolNuevo);

        jbToolModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/syswave/forms/resources/pencil.png"))); // NOI18N
        jbToolModificar.setText("Modificar");
        jbToolModificar.setFocusable(false);
        jbToolModificar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbToolModificar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jbToolModificar);

        jbToolEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/syswave/forms/resources/button-subtract.png"))); // NOI18N
        jbToolEliminar.setText("Eliminar");
        jbToolEliminar.setFocusable(false);
        jbToolEliminar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbToolEliminar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jbToolEliminar);

        jbToolGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/syswave/forms/resources/database-upload.png"))); // NOI18N
        jbToolGuardar.setText("Guardar");
        jbToolGuardar.setToolTipText("");
        jbToolGuardar.setFocusable(false);
        jbToolGuardar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbToolGuardar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jbToolGuardar);

        getContentPane().add(jToolBar1, java.awt.BorderLayout.PAGE_START);
        getContentPane().add(tpContenido, java.awt.BorderLayout.CENTER);

        jmArchivo.setText("Archivo");

        jmNuevo.setText("Nuevo");
        jmArchivo.add(jmNuevo);

        jmModificar.setText("Modificar");
        jmArchivo.add(jmModificar);

        jmAbrir.setText("Abrir");
        jmArchivo.add(jmAbrir);
        jmArchivo.add(jSeparator1);

        jmExportar.setText("Exportar");

        jmExportExcel.setText("Excel");
        jmExportar.add(jmExportExcel);

        jmExportPDF.setText("PDF");
        jmExportPDF.setEnabled(false);
        jmExportar.add(jmExportPDF);

        jmArchivo.add(jmExportar);
        jmArchivo.add(jSeparator9);

        jmGuardar.setText("Guardar");
        jmArchivo.add(jmGuardar);
        jmArchivo.add(jSeparator2);

        jmImprimir.setText("Imprimir");
        jmArchivo.add(jmImprimir);
        jmArchivo.add(jSeparator3);

        jmPropiedades.setText("Propiedades");
        jmPropiedades.setEnabled(false);
        jmArchivo.add(jmPropiedades);

        jmCerrar.setText("Cerrar");
        jmArchivo.add(jmCerrar);

        jmCerrarTodo.setText("Cerrar todo");
        jmArchivo.add(jmCerrarTodo);

        jMenuBar1.add(jmArchivo);

        jmEditar.setText("Editar");

        jmCortar.setText("Cortar");
        jmCortar.setEnabled(false);
        jmEditar.add(jmCortar);

        jmCopiar.setText("Copiar");
        jmCopiar.setEnabled(false);
        jmEditar.add(jmCopiar);

        jmPegar.setText("Pegar");
        jmPegar.setEnabled(false);
        jmEditar.add(jmPegar);
        jmEditar.add(jSeparator4);

        jmSeleccionarTodo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        jmSeleccionarTodo.setText("Seleccionar todo");
        jmEditar.add(jmSeleccionarTodo);
        jmEditar.add(jSeparator7);

        jmEliminar.setText("Eliminar");
        jmEditar.add(jmEliminar);

        jMenuBar1.add(jmEditar);

        jmVer.setText("Ver");

        jmRecargar.setText("Recargar");
        jmVer.add(jmRecargar);
        jmVer.add(jSeparator5);

        jmchkConVentana.setSelected(true);
        jmchkConVentana.setText("Vetana al editar y crear");
        jmchkConVentana.setEnabled(false);
        jmVer.add(jmchkConVentana);

        jmchkConBarraHerramientas.setSelected(true);
        jmchkConBarraHerramientas.setText("Barra herramientas");
        jmchkConBarraHerramientas.setEnabled(false);
        jmVer.add(jmchkConBarraHerramientas);

        jmFiltros.setText("Filtrado");
        jmVer.add(jmFiltros);
        jmVer.add(jSeparator8);

        jmOrdernar.setText("Ordenar elementos");

        jrmLlavePrimaria.setSelected(true);
        jrmLlavePrimaria.setText("Llave primaria");
        jmOrdernar.add(jrmLlavePrimaria);

        jmVer.add(jmOrdernar);

        jMenuBar1.add(jmVer);

        jmIr.setText("Ir");

        jmPrimero.setText("Primero");
        jmIr.add(jmPrimero);

        jmAnterior.setText("Anterior");
        jmIr.add(jmAnterior);

        jmSiguiente.setText("Siguiente");
        jmIr.add(jmSiguiente);

        jmUltimo.setText("Ultimo");
        jmIr.add(jmUltimo);
        jmIr.add(jSeparator6);

        jmBuscar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_B, java.awt.event.InputEvent.CTRL_MASK));
        jmBuscar.setMnemonic('B');
        jmBuscar.setText("Buscar");
        jmIr.add(jmBuscar);

        jmBuscarSiguiente.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F3, 0));
        jmBuscarSiguiente.setText("Buscar siguiente");
        jmIr.add(jmBuscarSiguiente);

        jMenuBar1.add(jmIr);

        jmAyuda.setText("Ayuda");

        jmTodaAyuda.setText("Todos los temas");
        jmTodaAyuda.setEnabled(false);
        jmAyuda.add(jmTodaAyuda);

        jmAcercaDe.setText("Acerca de");
        jmAcercaDe.setEnabled(false);
        jmAyuda.add(jmAcercaDe);

        jMenuBar1.add(jmAyuda);

        setJMenuBar(jMenuBar1);

        setSize(new java.awt.Dimension(800, 550));
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    private javax.swing.JPopupMenu.Separator jSeparator7;
    private javax.swing.JPopupMenu.Separator jSeparator8;
    private javax.swing.JPopupMenu.Separator jSeparator9;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JButton jbToolEliminar;
    private javax.swing.JButton jbToolGuardar;
    private javax.swing.JButton jbToolModificar;
    private javax.swing.JButton jbToolNuevo;
    private javax.swing.JMenuItem jmAbrir;
    private javax.swing.JMenuItem jmAcercaDe;
    private javax.swing.JMenuItem jmAnterior;
    private javax.swing.JMenu jmArchivo;
    private javax.swing.JMenu jmAyuda;
    private javax.swing.JMenuItem jmBuscar;
    private javax.swing.JMenuItem jmBuscarSiguiente;
    private javax.swing.JMenuItem jmCerrar;
    private javax.swing.JMenuItem jmCerrarTodo;
    private javax.swing.JMenuItem jmCopiar;
    private javax.swing.JMenuItem jmCortar;
    private javax.swing.JMenu jmEditar;
    private javax.swing.JMenuItem jmEliminar;
    private javax.swing.JMenuItem jmExportExcel;
    private javax.swing.JMenuItem jmExportPDF;
    private javax.swing.JMenu jmExportar;
    private javax.swing.JCheckBoxMenuItem jmFiltros;
    private javax.swing.JMenuItem jmGuardar;
    private javax.swing.JMenuItem jmImprimir;
    private javax.swing.JMenu jmIr;
    private javax.swing.JMenuItem jmModificar;
    private javax.swing.JMenuItem jmNuevo;
    private javax.swing.JMenu jmOrdernar;
    private javax.swing.JMenuItem jmPegar;
    private javax.swing.JMenuItem jmPrimero;
    private javax.swing.JMenuItem jmPropiedades;
    private javax.swing.JMenuItem jmRecargar;
    private javax.swing.JMenuItem jmSeleccionarTodo;
    private javax.swing.JMenuItem jmSiguiente;
    private javax.swing.JMenuItem jmTodaAyuda;
    private javax.swing.JMenuItem jmUltimo;
    private javax.swing.JMenu jmVer;
    private javax.swing.JCheckBoxMenuItem jmchkConBarraHerramientas;
    private javax.swing.JCheckBoxMenuItem jmchkConVentana;
    private javax.swing.JRadioButtonMenuItem jrmLlavePrimaria;
    private javax.swing.JTabbedPane tpContenido;
    // End of variables declaration//GEN-END:variables

}
