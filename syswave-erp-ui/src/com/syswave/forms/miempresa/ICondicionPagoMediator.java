package com.syswave.forms.miempresa;

import com.syswave.entidades.miempresa.CondicionPago;
import com.syswave.entidades.miempresa.Valor;
import java.util.List;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public interface ICondicionPagoMediator
{
 void showDetail(CondicionPago elemento);
    void onAcceptNewElement(CondicionPago nuevo);
    void onAcceptModifyElement(CondicionPago modificado);
    String obtenerOrigenDato ();  
    List<Valor> obtenerTiposCondicion ();
}
