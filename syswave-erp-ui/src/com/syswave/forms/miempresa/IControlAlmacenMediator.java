package com.syswave.forms.miempresa;

import com.syswave.entidades.miempresa.ControlAlmacen;
import com.syswave.entidades.miempresa.ControlInventario;
import com.syswave.entidades.miempresa.Persona_tiene_Existencia;
import com.syswave.entidades.miempresa.Ubicacion;
import java.util.List;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public interface IControlAlmacenMediator
{
   void onAcceptNewElement(ControlAlmacen nuevo, List<Persona_tiene_Existencia> existencias);
   void onAcceptModifyElement(ControlAlmacen modificado, List<Persona_tiene_Existencia> existencias, List<Persona_tiene_Existencia> existencias_borradas);
   String obtenerOrigenDato ();
   List<ControlInventario> obtenerLotes();
   List<Ubicacion> obtenerUbicaciones();
   List<Persona_tiene_Existencia> obtenerPersonaExistencias(ControlAlmacen valor);

   //String obtenerEtiqueta(int mantenimientoComo);
}
