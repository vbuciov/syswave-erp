package com.syswave.entidades.miempresa;

import com.syswave.entidades.keys.PrimaryKeyById;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class Condicion extends PrimaryKeyById
{

    private String nombre;
    private boolean es_activo;

    //----------------------------------------------------------------------
    public Condicion()
    {
        super();
        initAtributes();
    }

    //----------------------------------------------------------------------
    public Condicion(Condicion that)
    {
        super();
        assign(that);
    }

    //----------------------------------------------------------------------
    private void assign(Condicion that)
    {
        super.assign(that);
        nombre = that.nombre;
        es_activo = that.es_activo;
    }

    //----------------------------------------------------------------------
    private void initAtributes()
    {
        nombre = EMPTY_STRING;
    }

    //----------------------------------------------------------------------
    public String getNombre()
    {
        return nombre;
    }

    //----------------------------------------------------------------------
    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    //----------------------------------------------------------------------
    public boolean isActivo()
    {
        return es_activo;
    }

    //----------------------------------------------------------------------
    public void setActivo(Boolean activo)
    {
        es_activo = activo;
    }

    //----------------------------------------------------------------------
    @Override
    public void clear()
    {
        initAtributes();
    }

    //---------------------------------------------------------------------
    public void copy(Condicion that)
    {
        assign(that);
    }
}
