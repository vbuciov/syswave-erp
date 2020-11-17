package com.syswave.forms.miempresa;

import com.syswave.entidades.miempresa.AreaPrecio;
import com.syswave.entidades.miempresa.BienVariante;
import com.syswave.entidades.miempresa.ControlPrecio;
import com.syswave.entidades.miempresa.DesgloseCosto;
import com.syswave.entidades.miempresa.Moneda;
import com.syswave.entidades.miempresa.Valor;
import com.syswave.entidades.miempresa_vista.DesgloseCosto_5FN;
import com.syswave.logicas.miempresa.DesgloseCostosBusinessLogic;
import java.util.List;
import javax.swing.JInternalFrame;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public interface IControlPrecio
{

    void showDetail(ControlPrecio elemento);

    void onAcceptNewElement(ControlPrecio nuevo, List<DesgloseCosto_5FN> desgloses);

    void onAcceptModifyElement(ControlPrecio modificado, List<DesgloseCosto_5FN> desgloses, List<DesgloseCosto> desgloses_borrados);

    List<BienVariante> obtenerPresentaciones();

    List<Moneda> obtenerMonedas();

    //List<Valor> obtenerTipoPrecios();

    List<AreaPrecio> obtenerAreasPrecios();

    DesgloseCostosBusinessLogic obtenerLogicaDesglose();

    void agregaContenedorPrincipal(JInternalFrame Dialog);

    void mostrarCentrado(JInternalFrame Dialog);
    
    String getEsquema();
}
