package com.syswave.forms.miempresa;

import com.syswave.entidades.miempresa.Moneda;
import com.syswave.entidades.miempresa.MonedaCambio;
import java.util.List;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public interface IMonedaCambioMediator
{
   void showDetail(MonedaCambio elemento);
   void onAcceptNewElement(MonedaCambio nuevo);
   void onAcceptModifyElement(MonedaCambio modificado);
   String obtenerOrigenDato ();
   List<Moneda> obtenerMonedas();
}
