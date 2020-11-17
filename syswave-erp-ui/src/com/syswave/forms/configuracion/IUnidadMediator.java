package com.syswave.forms.configuracion;

import com.syswave.entidades.configuracion.Unidad;

/**
 *
 * @author victor
 */
public interface IUnidadMediator
{
      void onAcceptModifyElement(Unidad elemento);
   void onAcceptNewElement(Unidad nuevo);
}
