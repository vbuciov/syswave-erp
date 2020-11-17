package com.syswave.entidades.miempresa;

import com.syswave.entidades.keys.PrimaryKeyById;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class Moneda extends PrimaryKeyById
{

    private String nombre, siglas;

    //---------------------------------------------------------------------
    public Moneda()
    {
        super();
        initAtributes();
    }

    //---------------------------------------------------------------------
    public Moneda(Moneda that)
    {
        super();
        assign(that);
    }

    //---------------------------------------------------------------------
    private void initAtributes()
    {
        nombre = EMPTY_STRING;
        siglas = EMPTY_STRING;
    }

    //---------------------------------------------------------------------
    private void assign(Moneda that)
    {
        super.assign(that);
        nombre = that.nombre;
        siglas = that.siglas;
    }

    //---------------------------------------------------------------------
    public String getNombre()
    {
        return nombre;
    }

    //---------------------------------------------------------------------
    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    //---------------------------------------------------------------------
    public String getSiglas()
    {
        return siglas;
    }

    //---------------------------------------------------------------------
    public void setSiglas(String siglas)
    {
        this.siglas = siglas;
    }

    //---------------------------------------------------------------------
    @Override
    public void clear()
    {
        initAtributes();
    }

    //---------------------------------------------------------------------
    public void copy(Moneda that)
    {
        assign(that);
    }
}
