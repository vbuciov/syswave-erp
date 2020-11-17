package com.syswave.forms.common;                         

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.InputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 * Esta clase representa las vistas de listado que se puede mostrar en un
 * ListadoInternalFrame
 *
 * @author Victor Manuel Bucio Vargas
 */
public abstract class JDataView extends JPanel implements ICatalogOperations/*, IRequiredDesktopPane*/
{
    protected IWindowContainer mainContainer;
    boolean stateChange;
    List<PropertyChangeListener> observers;

    /**
     * Creates new form TableViewJPanel
     *
     * @param container Contenedor principal para agregar sub-ventanas.
     */
    public JDataView(IWindowContainer container)
    {
        observers = new ArrayList<>();
        mainContainer = container;
        initComponents();
        stateChange = false;
        
    }
    
     public JDataView(IWindowContainer container, String name)
    {
        observers = new ArrayList<>();
        mainContainer = container;
        initComponents();
        stateChange = false;
        setName(name);
        
    }

    //----------------------------------------------------------------------
    /**
     * Especifica quien es el contenedor principal de ventanas.
     *
     * @param value Indica quien es el contenedor principal de ventanas.
     */
    public void setDesktopPane(IWindowContainer value)
    {
        mainContainer = value;
    }

    // -------------------------------------------------------------------
    @Override
    public final boolean isThereChanges()
    {
        return stateChange;
    }

    // -------------------------------------------------------------------
    @Override
    public final void acceptChanges()
    {
        propagateChanges(new PropertyChangeEvent(this, "state", stateChange, false));
        stateChange = false;
    }

    // -------------------------------------------------------------------
    @Override
    public final void setChanges()
    {
         propagateChanges(new PropertyChangeEvent(this, "state", stateChange, true));
        stateChange = true;
    }
    
    //--------------------------------------------------------------------
    @Override
    public final void addPropertyChangeListener (PropertyChangeListener value)
    {
        if (observers != null)
            observers.add(value);
    }
    
     //--------------------------------------------------------------------
    @Override
    public final void removePropertyChangeListener (PropertyChangeListener value)
    {
        if (observers != null)
            observers.remove(value);
    }
    
    //--------------------------------------------------------------------
    private void propagateChanges(PropertyChangeEvent evt)
    {
        for(PropertyChangeListener element: observers)
            element.propertyChange(evt);
    }

    // -------------------------------------------------------------------
    /**
     * Llama a un reporte a través de JasperSoft
     * @param forJasper Conexión de Jasper
     * @param title Titulo a poner en el visor.
     * @param resourcePath Ruta hacia el recurso.
     */
    public final void jasperSoftToPrint(Connection forJasper, String title, String resourcePath)
    {

        if (forJasper != null)
        {
            try
            {
                InputStream is;
                is = getClass().getResourceAsStream(resourcePath);

                if (is != null)
                {
                    JasperPrint jp = JasperFillManager.fillReport(is, null, forJasper);
                    jp.setName(title);
                    JasperViewer.viewReport(jp, false);
                    forJasper.close();
                }
            }
            catch (JRException ex)
            {
                Logger.getLogger(JDataView.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }

    //-------------------------------------------------------------------
    /**
     * Llama a un reporte a través de JasperSoft
     * @param forJasper Conexión de Jasper
     * @param title Titulo a poner en el visor.
     * @param resourcePath Ruta hacia el recurso.
     * @param parameters Parametros a enviar al reporte
     */
    public final void jasperSoftToPrint(Connection forJasper, String title, String resourcePath, Map parameters)
    {
        if (forJasper != null)
        {
            try
            {
                InputStream is;
                is = getClass().getResourceAsStream(resourcePath);

                if (is != null)
                {
                    JasperPrint jp = JasperFillManager.fillReport(is, parameters, forJasper);
                    jp.setName(title);
                    JasperViewer.viewReport(jp, false);
                    //forJasper.close();
                }
            }
            catch (JRException ex)
            {
                Logger.getLogger(JDataView.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
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

        jpEstatusBar = new javax.swing.JPanel();
        jbContador = new javax.swing.JLabel();
        jbMessage = new javax.swing.JLabel();
        jbProgress = new javax.swing.JProgressBar();

        setLayout(new java.awt.BorderLayout());

        jpEstatusBar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        java.awt.FlowLayout flowLayout1 = new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 2, 0);
        flowLayout1.setAlignOnBaseline(true);
        jpEstatusBar.setLayout(flowLayout1);

        jbContador.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jbContador.setPreferredSize(new java.awt.Dimension(100, 22));
        jpEstatusBar.add(jbContador);

        jbMessage.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jbMessage.setPreferredSize(new java.awt.Dimension(500, 22));
        jpEstatusBar.add(jbMessage);

        jbProgress.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jbProgress.setPreferredSize(new java.awt.Dimension(130, 22));
        jpEstatusBar.add(jbProgress);

        add(jpEstatusBar, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JLabel jbContador;
    protected javax.swing.JLabel jbMessage;
    protected javax.swing.JProgressBar jbProgress;
    private javax.swing.JPanel jpEstatusBar;
    // End of variables declaration//GEN-END:variables
}
