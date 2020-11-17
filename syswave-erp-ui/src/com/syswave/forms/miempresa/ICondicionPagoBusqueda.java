package com.syswave.forms.miempresa;

import com.syswave.entidades.miempresa.CondicionPago;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public interface ICondicionPagoBusqueda
{
   void onAcceptNewElement(CondicionPago nuevo);
  // void onAcceptNewElement(List<CondicionPago> nuevos);
   String getEsquema(); 
   
}
