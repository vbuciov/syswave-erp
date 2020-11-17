package com.syswave.forms.miempresa;

import com.syswave.entidades.miempresa.Valor;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public interface IValorBusquedaMediator
{
      void onAcceptNewElement(Valor nuevo);
  // void onAcceptNewElement(List<Valor> nuevos);
   String getEsquema(); 
   
}
