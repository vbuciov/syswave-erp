package com.syswave.entidades.miempresa_vista;

import com.syswave.entidades.miempresa.MantenimientoDescripcion;
import com.syswave.entidades.miempresa.Valor;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class MantenimientoDescripcion_5FN extends MantenimientoDescripcion
{
    protected Valor fk_mantenimiento_desc_id_tipo_descripcion;
    
    public MantenimientoDescripcion_5FN(){
        fk_mantenimiento_desc_id_tipo_descripcion = super.getHasOneTipoDescripcion();
    }
    
    public String getValor()
    {
        return fk_mantenimiento_desc_id_tipo_descripcion.getValor();
    }
    
    public void setValor(String value)
    {
        fk_mantenimiento_desc_id_tipo_descripcion.setValor(value);
    }
    
    public String getDescripcion()
    {
        return fk_mantenimiento_desc_id_tipo_descripcion.getDescripcion();
    }
    
    public void setDescripcion(String value)
    {
        fk_mantenimiento_desc_id_tipo_descripcion.setDescripcion(value);
    }
    
    public String getFormato()
    {
        return fk_mantenimiento_desc_id_tipo_descripcion.getFormato();
    }
    
    public void setFormato(String value)
    {
        fk_mantenimiento_desc_id_tipo_descripcion.setFormato(value);
    }
    
    public boolean esActivo()
    {
        return fk_mantenimiento_desc_id_tipo_descripcion.esActivo();
    }
    
    public void setActivo(boolean value)
    {
        fk_mantenimiento_desc_id_tipo_descripcion.setActivo(value);
    }
}
