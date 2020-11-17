package com.syswave.forms.configuracion;

import com.syswave.entidades.configuracion.NodoPermiso;
import com.syswave.entidades.configuracion.Usuario;
import java.util.List;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public interface IUsuarioMediator
{
   void onAcceptNewElement(Usuario nuevo, List<NodoPermiso> permisos);
   void onAcceptModifyElement(Usuario elemento, List<NodoPermiso> permisos);

   public List<NodoPermiso> obtenerSemilla(Usuario elementoActual);
}
