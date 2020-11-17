package com.syswave.entidades.miempresa_vista;

import com.syswave.entidades.miempresa.PersonaDetalle;
import com.syswave.entidades.miempresa.Valor;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class PersonaDetalle_5FN extends PersonaDetalle
{

    protected Valor fk_persona_detalles_id_tipo_detalle;

    //---------------------------------------------------------------------
    public PersonaDetalle_5FN()
    {
        fk_persona_detalles_id_tipo_detalle = super.getHasOneTipoDetalle();
    }

    //---------------------------------------------------------------------
    public String getLlave()
    {
        return fk_persona_detalles_id_tipo_detalle.getValor();
    }

    //---------------------------------------------------------------------
    public void setLlave(String value)
    {
        fk_persona_detalles_id_tipo_detalle.setValor(value);
    }

    //---------------------------------------------------------------------
    public String getDescripcion()
    {
        return fk_persona_detalles_id_tipo_detalle.getDescripcion();
    }

    //---------------------------------------------------------------------
    public void setDescripcion(String value)
    {
        fk_persona_detalles_id_tipo_detalle.setDescripcion(value);
    }

    //---------------------------------------------------------------------
    public boolean esActivo()
    {
        return fk_persona_detalles_id_tipo_detalle.esActivo();
    }

    //---------------------------------------------------------------------
    public void setEsActivo(boolean value)
    {
        fk_persona_detalles_id_tipo_detalle.setActivo(value);
    }
}
