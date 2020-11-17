package com.syswave.forms.miempresa;

import com.syswave.entidades.miempresa.Puesto;
import java.util.List;

/**
 *
 * @author sis5
 */
public interface IPuestoMediator
{

    void onAcceptModifyElement(Puesto elemento);

    void onAcceptNewElement(Puesto nuevo);
    
    List<Puesto> obtenerPuestos();
    
    String obtenerOrigenDato();
}
