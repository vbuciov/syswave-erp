package com.syswave.entidades.miempresa;

import com.syswave.entidades.keys.PrimaryKeyById;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class ClaseInventario extends PrimaryKeyById
{
    private String nombre;
    private boolean es_comercial, es_armable;

    //--------------------------------------------------------------------
    public ClaseInventario()
    {
        super();
        initAttributes();
    }

    //--------------------------------------------------------------------
    private void initAttributes()
    {
        nombre = EMPTY_STRING;
        es_comercial = true;
        es_armable = true;
    }

    //----------------------------------------------------------------------
    private void asign(ClaseInventario that)
    {
        setId(that.getId());
        nombre = that.nombre;
        es_comercial = that.es_comercial;
        es_armable = that.es_armable;
    }

    //--------------------------------------------------------------------
    public String getNombre()
    {
        return nombre;
    }

    //--------------------------------------------------------------------
    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    //--------------------------------------------------------------------
    public boolean esComercial()
    {
        return es_comercial;
    }

    //--------------------------------------------------------------------
    public void setesComercial(boolean es_comercial)
    {
        this.es_comercial = es_comercial;
    }

    //--------------------------------------------------------------------
    public boolean esArmable()
    {
        return es_armable;
    }

    //--------------------------------------------------------------------
    public void setesArmable(boolean es_armable)
    {
        this.es_armable = es_armable;
    }
    
    //--------------------------------------------------------------------
    @Override
    public void clear()
    {
        initAttributes();
    }

    //---------------------------------------------------------------------
    public void copy(ClaseInventario that)
    {
        asign(that);
    }
}