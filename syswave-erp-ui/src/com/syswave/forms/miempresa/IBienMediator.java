package com.syswave.forms.miempresa;

import com.syswave.entidades.miempresa.Bien;
import com.syswave.entidades.miempresa.BienCompuesto;
import com.syswave.entidades.miempresa.BienVariante;
import com.syswave.entidades.miempresa.BienVarianteFoto;
import com.syswave.entidades.miempresa.ControlInventario;
import com.syswave.entidades.miempresa.ControlPrecio;
import com.syswave.entidades.miempresa.PlanMantenimiento;
import com.syswave.entidades.miempresa_vista.BienCompuestoVista;
import com.syswave.entidades.miempresa_vista.VarianteIdentificadorVista;
import java.util.List;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public interface IBienMediator
{
   void onAcceptNewElement(BienVariante elemento, List<BienCompuestoVista> compuestos, 
                                  List<VarianteIdentificadorVista> identificadores,  
                                  List<ControlInventario> lotes, List<PlanMantenimiento> actividades,
                                  List<ControlPrecio> precios,
                                  BienVarianteFoto foto);
   void onAcceptModifyElement(BienVariante nuevo, List<BienCompuestoVista> compuestos, List<BienCompuesto> compuestos_borrados, 
                                     List<VarianteIdentificadorVista> identificadores, 
                                     List<ControlInventario> lotes, List<ControlInventario> lotes_borrados,
                                     List<PlanMantenimiento> actividades, List<PlanMantenimiento> actividades_borrados,
                                     List<ControlPrecio> precios, List<ControlPrecio> precios_borrados,
                                     BienVarianteFoto foto, BienVarianteFoto foto_borrada);

   String obtenerOrigenDato ();
   void showDetail(BienVariante elemento);
   List<Bien> obtenerTiposBienes();
   List<BienCompuestoVista> obtenerCompuestos (BienVariante elemento);

   List<VarianteIdentificadorVista> obtenerIdentificadores(BienVariante elemento);
   List<ControlInventario> obtenerLotes(BienVariante elemento);
   List<PlanMantenimiento> obtenerActividades(BienVariante elemento);
   List<ControlPrecio> obtenerPrecios(BienVariante elemento);
   List<BienVarianteFoto> obtenerFotos(BienVariante elemento);
}
