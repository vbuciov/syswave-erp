package com.syswave.forms.miempresa;

import com.syswave.entidades.miempresa.ControlAlmacen;
import com.syswave.entidades.miempresa.Persona;
import com.syswave.entidades.miempresa.Persona_tiene_Existencia;
import java.util.List;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public interface IPersonaTieneExistenciaMediator
{
   void onAcceptNewElement(Persona_tiene_Existencia nuevo);
   void onAcceptModifyElement(Persona_tiene_Existencia modificado);
   void showDetail(Persona_tiene_Existencia elemento);
   List<Persona> obtenerPersonas ();
    
   List<ControlAlmacen> obtenerSeries (); 
}
