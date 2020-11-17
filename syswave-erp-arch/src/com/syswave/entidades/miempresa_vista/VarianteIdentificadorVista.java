package com.syswave.entidades.miempresa_vista;

import com.syswave.entidades.miempresa.VarianteIdentificador;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class VarianteIdentificadorVista extends VarianteIdentificador
{

    private String llave, descripcion;
    private boolean activo;

    public VarianteIdentificadorVista()
    {
        super();
        llave = EMPTY_STRING;
        descripcion = EMPTY_STRING;
        activo = false;
    }

    //---------------------------------------------------------------------
    public String getLlave()
    {
        return llave;
    }

    //---------------------------------------------------------------------
    public void setLlave(String value)
    {
        llave = value;
    }

    //---------------------------------------------------------------------
    public String getDescripcion()
    {
        return descripcion;
    }

    //---------------------------------------------------------------------
    public void setDescripcion(String value)
    {
        descripcion = value;
    }

    //---------------------------------------------------------------------
    public boolean esActivo()
    {
        return activo;
    }

    //---------------------------------------------------------------------
    public void setEsActivo(boolean value)
    {
        activo = value;
    }
}
