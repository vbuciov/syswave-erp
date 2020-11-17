package com.syswave.forms.miempresa;

import com.syswave.entidades.miempresa.Contrato;

/**
 *
 * @author sis5
 */
public interface IContratoMediator
{
    void onAcceptModifyElement(Contrato elemento);

    void onAcceptNewElement(Contrato nuevo);
    
    String obtenerOrigenDato();
}
