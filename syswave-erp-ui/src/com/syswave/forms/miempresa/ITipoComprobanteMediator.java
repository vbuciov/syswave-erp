package com.syswave.forms.miempresa;

import com.syswave.entidades.miempresa.TipoComprobante;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public interface ITipoComprobanteMediator
{
    void showDetail(TipoComprobante elemento);
    void onAcceptNewElement(TipoComprobante nuevo);
    void onAcceptModifyElement(TipoComprobante modificado);
    String obtenerOrigenDato ();
}
