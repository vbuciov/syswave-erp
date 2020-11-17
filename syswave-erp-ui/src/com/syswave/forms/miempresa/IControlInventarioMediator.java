package com.syswave.forms.miempresa;

import com.syswave.entidades.miempresa.BienVariante;
import com.syswave.entidades.miempresa.ControlAlmacen;
import com.syswave.entidades.miempresa.ControlInventario;
import java.util.List;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public interface IControlInventarioMediator
{
   void onAcceptNewElement(ControlInventario nuevo, List<ControlAlmacen> almacenes, List<ControlAlmacen> almacenes_borrados);
   void onAcceptModifyElement(ControlInventario modificado, List<ControlAlmacen> almacenes, List<ControlAlmacen> almacenes_borrados);
   String obtenerOrigenDato ();
   List<ControlAlmacen> obtenerControlAlmacenes(ControlInventario elemento);
   List<BienVariante> obtenerPartes();
   
}
