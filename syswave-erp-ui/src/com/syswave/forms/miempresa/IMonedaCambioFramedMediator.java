package com.syswave.forms.miempresa;

import com.syswave.entidades.miempresa.Moneda;
import com.syswave.entidades.miempresa_vista.MonedaCambioVista;
import java.util.List;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public interface IMonedaCambioFramedMediator
{
   List<Moneda> obtenerMonedas();
   void onAcceptModifyElement(List<MonedaCambioVista> elementos);
   void onCancelModifyElement(List<MonedaCambioVista> elementos);
   void bodyEnabled (boolean value);
   
}
