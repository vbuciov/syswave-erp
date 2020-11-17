package com.syswave.forms.configuracion;

import com.syswave.entidades.configuracion.Localidad;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public interface ILocalidadMediator
{
   void onAcceptModifyElement(Localidad elemento);
   void onAcceptNewElement(Localidad nuevo);
   
}
