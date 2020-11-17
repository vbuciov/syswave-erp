package com.syswave.forms.common;

import com.syswave.entidades.configuracion.OrigenDato;
import com.syswave.entidades.configuracion.Usuario;

/**
 * Representa la información principal de una sessión.
 * @author Victor Manuel Bucio Vargas
 */
public interface ISessionContainer
{
   //---------------------------------------------------------------------
   OrigenDato getOrigenDatoActual();
   
   //---------------------------------------------------------------------
   Usuario getUsuarioActual();
}
