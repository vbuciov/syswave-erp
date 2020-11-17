package com.syswave.forms.miempresa;

import com.syswave.entidades.miempresa.TipoPersona;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
interface ITipoPersonaMediator
{
         void onAcceptModifyElement(TipoPersona elemento);
   void onAcceptNewElement(TipoPersona nuevo);
}
