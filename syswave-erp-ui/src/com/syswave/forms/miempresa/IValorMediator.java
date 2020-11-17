package com.syswave.forms.miempresa;

import com.syswave.entidades.miempresa.Valor;
import java.util.List;

/**
 *
 * @author sis5
 */
public interface IValorMediator
{
    void showDetail(Valor elemento);
    void onAcceptNewElement(Valor nuevo);
    void onAcceptModifyElement(Valor modificado);
    String obtenerOrigenDato();
}
