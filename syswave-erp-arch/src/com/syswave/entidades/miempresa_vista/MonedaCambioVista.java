package com.syswave.entidades.miempresa_vista;

import com.syswave.entidades.miempresa.MonedaCambio;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class MonedaCambioVista extends MonedaCambio
{

    private String MonedaOrigen, MonedaDestino;

    public MonedaCambioVista()
    {
        MonedaOrigen = EMPTY_STRING;
        MonedaDestino = EMPTY_STRING;
    }

    public String getOrigen()
    {
        return MonedaOrigen;
    }

    public void setOrigen(String value)
    {
        MonedaOrigen = value;
    }

    public String getDestino()
    {
        return MonedaDestino;
    }

    public void setDestino(String value)
    {
        MonedaDestino = value;
    }
}
