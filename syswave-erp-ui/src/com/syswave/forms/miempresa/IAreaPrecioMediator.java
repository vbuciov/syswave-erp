package com.syswave.forms.miempresa;

import com.syswave.entidades.miempresa.AreaPrecio;
import com.syswave.entidades.miempresa.Valor;
import java.util.List;

/**
 *
 * @author sis2
 */
public interface IAreaPrecioMediator
{
       void onAcceptModifyElement(AreaPrecio elemento);
   void onAcceptNewElement(AreaPrecio nuevo);
   List<Valor> obtenerTipoPrecios();
}
