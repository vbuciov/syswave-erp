package com.syswave.forms.miempresa;

import com.syswave.entidades.miempresa.ControlAlmacen;
import com.syswave.entidades.miempresa.Mantenimiento;
import com.syswave.entidades.miempresa.MantenimientoCosto;
import com.syswave.entidades.miempresa.Moneda;
import com.syswave.entidades.miempresa.Persona;
import com.syswave.entidades.miempresa.Valor;
import com.syswave.entidades.miempresa_vista.MantenimientoDescripcion_5FN;
import com.syswave.entidades.miempresa_vista.MantenimientoTieneActividad_5FN;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public interface IMantenimientoMediator
{
    void onAcceptNewElement(Mantenimiento elemento, List<MantenimientoDescripcion_5FN> descripciones,
            List<MantenimientoTieneActividad_5FN> actividades,List<MantenimientoCosto> listacostos);
    void onAcceptModifyElement(Mantenimiento nuevo, List<MantenimientoDescripcion_5FN> descripciones,
             List<MantenimientoTieneActividad_5FN> actividades, List<MantenimientoCosto> listacostos,
             List<MantenimientoCosto> listacostos_borrados);
    void showDetail(Mantenimiento elemento);
    String obtenerOrigenDato ();

    List<Valor> obtenerTiposMantenimiento();
    List<Persona> obtenerResponsables();
    List<ControlAlmacen> obtenerSeries();
    List<Moneda> obtenerMonedas();

    List<MantenimientoDescripcion_5FN> obtenerDescripciones(Mantenimiento elemento);
    List<MantenimientoTieneActividad_5FN> obtenerCheckList (Mantenimiento elemento, ControlAlmacen serie);
    List<MantenimientoCosto> obtenerCostos(Mantenimiento elemento);
    void printHelp(String title, String resourcePath);
    void printHelp(String title, String resourcePath, Map parameters);
    
}
