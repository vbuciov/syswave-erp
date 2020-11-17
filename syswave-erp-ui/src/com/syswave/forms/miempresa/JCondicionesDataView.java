package com.syswave.forms.miempresa;

import com.syswave.entidades.miempresa.Condicion;
import com.syswave.forms.common.JTableDataView;
import com.syswave.forms.databinding.CondicionesTableModel;
import com.syswave.logicas.miempresa.CondicionesBusinessLogic;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingWorker;
import com.syswave.forms.common.IWindowContainer;

/**
 *
 * @author Aimee García
 */
public class JCondicionesDataView extends JTableDataView implements ICondicionesMediator {

    CondicionesBusinessLogic condiciones;
    CondicionesTableModel tmCondiciones;

    public JCondicionesDataView(IWindowContainer container) {
        super(container);

        condiciones = new CondicionesBusinessLogic(container.getOrigenDatoActual().getNombre());
        tmCondiciones = new CondicionesTableModel(new String[]{
            "Nombre:{nombre}",
            "¿Activo?:{es_activo}"
        });

        jtbData.setModel(tmCondiciones);
    }

    @Override
    public void doCreateProcess() {
        JCondicionesDetailView condicion_detalle = new JCondicionesDetailView(this);
        mainContainer.addWindow(condicion_detalle);
        mainContainer.showCenter(condicion_detalle);
        //condicion_detalle.setLogic(condiciones);
    }

    @Override
    public void doUpdateProcess() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void doDeleteProcess() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void doRetrieveProcess() {
        CondicionesSwingWorker swSecondPlane = new CondicionesSwingWorker();
        swSecondPlane.execute();
    }

    @Override
    public void doSaveProcess() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void doOpenProcess() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onAcceptNewElement(Condicion nuevo) {
        if (condiciones.agregar(nuevo)) {
            tmCondiciones.addRow(nuevo);
        }
    }

    @Override
    public void onAcceptModifyElement(Condicion modificado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public class CondicionesSwingWorker extends SwingWorker<List<Object>, Void> {

        @Override
        protected List<Object> doInBackground() throws Exception {
            List<Object> results = new ArrayList<>();
            
            results.add(condiciones.obtenerLista());
            
            return results;
        }

        @Override
        protected void done() {
            try {
                List<Object> results = get();
                
                tmCondiciones.setData((List<Condicion>)results.get(0));
            } catch (InterruptedException ex) {
                Logger.getLogger(JCondicionesDataView.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ExecutionException ex) {
                Logger.getLogger(JCondicionesDataView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    

}
