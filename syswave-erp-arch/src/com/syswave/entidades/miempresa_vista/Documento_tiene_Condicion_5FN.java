package com.syswave.entidades.miempresa_vista;

import com.syswave.entidades.miempresa.CondicionPago;
import com.syswave.entidades.miempresa.Documento_tiene_Condicion;
import com.syswave.entidades.miempresa.Valor;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class Documento_tiene_Condicion_5FN extends Documento_tiene_Condicion
{

    private CondicionPago fk_documento_condicion_id;
    private Valor fk_condicion_id_tipo_condicion;

    //---------------------------------------------------------------------
    public Documento_tiene_Condicion_5FN()
    {
        super();
        fk_documento_condicion_id = super.getHasOneCondicion();
        fk_condicion_id_tipo_condicion = fk_documento_condicion_id.getHasOneTipoCondicion();
    }

    //---------------------------------------------------------------------
    public Documento_tiene_Condicion_5FN(Documento_tiene_Condicion_5FN that)
    {
        super(that);
    }

    //---------------------------------------------------------------------
    public int getId()
    {
        return fk_condicion_id_tipo_condicion.getId();
    }

    //---------------------------------------------------------------------
    public void setId(int value)
    {
        fk_condicion_id_tipo_condicion.setId(value);
    }

    //---------------------------------------------------------------------
    public String getSeccion()
    {
        return fk_condicion_id_tipo_condicion.getSeccion();
    }

    //---------------------------------------------------------------------
    public void setSeccion(String value)
    {
        fk_condicion_id_tipo_condicion.setSeccion(value);
    }

    //---------------------------------------------------------------------
    public String getDescripcion()
    {
        return fk_condicion_id_tipo_condicion.getDescripcion();
    }

    //---------------------------------------------------------------------
    public void setDescripcion(String value)
    {
        fk_condicion_id_tipo_condicion.setDescripcion(value);
    }

    //---------------------------------------------------------------------
    public boolean esActivo()
    {
        return fk_condicion_id_tipo_condicion.esActivo();
    }

    //---------------------------------------------------------------------
    public void setActivo(boolean value)
    {
        fk_condicion_id_tipo_condicion.setActivo(value);
    }

    //---------------------------------------------------------------------
    public String getCondicion()
    {
        return fk_documento_condicion_id.getNombre();
    }

    //---------------------------------------------------------------------
    public void setCondicion(String value)
    {
        this.fk_documento_condicion_id.setNombre(value);
    }

    //---------------------------------------------------------------------
    public int getValor()
    {
        return fk_documento_condicion_id.getValor();
    }

    //---------------------------------------------------------------------
    public void setValor(int value)
    {
        fk_documento_condicion_id.setValor(value);
    }

    //---------------------------------------------------------------------
    public int getUnidad()
    {
        return fk_documento_condicion_id.getUnidad();
    }

    //---------------------------------------------------------------------
    public void setUnidad(int value)
    {
        fk_documento_condicion_id.setUnidad(value);
    }
}
