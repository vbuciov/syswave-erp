package com.syswave.forms.miempresa;

import com.syswave.entidades.miempresa_vista.ControlPrecioVista;
import java.util.List;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public interface IPrecioBusquedaMediator
{
   void onAcceptNewElement(ControlPrecioVista nuevo);
   void onAcceptNewElement(List<ControlPrecioVista> nuevos);
   String getEsquema();   
   boolean sonValidosVariosPrecios();
}
