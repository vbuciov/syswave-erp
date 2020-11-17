package com.syswave.forms.miempresa;

import com.syswave.entidades.miempresa.PersonaDireccion;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public interface IPersonaBusquedaMediator
{
   void onAcceptNewElement(PersonaDireccion nuevo);
   String getEsquema(); 
}
