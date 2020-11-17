
package com.syswave.forms.miempresa;

import com.syswave.entidades.miempresa.Moneda;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public interface IMonedaMediator
{
    void showDetail(Moneda elemento);
    void onAcceptNewElement(Moneda nuevo);
    void onAcceptModifyElement(Moneda modificado);  
}
