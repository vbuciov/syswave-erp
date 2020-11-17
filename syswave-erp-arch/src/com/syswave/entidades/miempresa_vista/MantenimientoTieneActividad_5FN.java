package com.syswave.entidades.miempresa_vista;

import com.syswave.entidades.miempresa.Mantenimiento_tiene_Actividad;
import com.syswave.entidades.miempresa.PlanMantenimiento;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class MantenimientoTieneActividad_5FN extends Mantenimiento_tiene_Actividad
{
    protected PlanMantenimiento fk_mantenimiento_plan_id_actividad;
    
    public MantenimientoTieneActividad_5FN(){
        fk_mantenimiento_plan_id_actividad = super.getHasOneActividad();
    }
    
    public String getActividad ()
    {
        return fk_mantenimiento_plan_id_actividad.getActividad();
    }
    
    public void setActividad(String value)
    {
        fk_mantenimiento_plan_id_actividad.setActividad(value);
    }
}
