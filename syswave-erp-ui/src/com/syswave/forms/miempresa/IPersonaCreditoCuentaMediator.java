package com.syswave.forms.miempresa;

import com.syswave.entidades.miempresa.Moneda;
import com.syswave.entidades.miempresa.Valor;
import com.syswave.entidades.miempresa_vista.PersonaCreditoCuenta_5FN;
import java.util.List;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public interface IPersonaCreditoCuentaMediator
{
   void showDetail(PersonaCreditoCuenta_5FN elemento);
    void onAcceptNewElement(PersonaCreditoCuenta_5FN nuevo);
    void onAcceptModifyElement(PersonaCreditoCuenta_5FN modificado);  
    List<Valor> obtenerTiposCuenta();
    List<Moneda> obtenerMonedas();
    String obtenerTipoPersona(int id_tipo_persona);
    
    String getEsquema();
}
