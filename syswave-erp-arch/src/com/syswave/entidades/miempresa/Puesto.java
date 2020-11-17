package com.syswave.entidades.miempresa;

import com.syswave.entidades.common.IEntidadRecursiva;
import com.syswave.entidades.keys.PrimaryKeyById;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class Puesto extends PrimaryKeyById implements IEntidadRecursiva
{

    private String nombre;
    private int id_padre, nivel;
    private boolean activo;
    
        //---------------------------------------------------------------------
    public Puesto()
    {
        super();
        initAtributes();
    }

    //---------------------------------------------------------------------
    public Puesto(Puesto that)
    {
        super();
        assign(that);
    }

    //---------------------------------------------------------------------
    private void initAtributes()
    {
        nombre = EMPTY_STRING;
        id_padre = EMPTY_INT;
        nivel = EMPTY_INT;
        activo = true;
    }

    //---------------------------------------------------------------------
    private void assign(Puesto that)
    {
        super.assign(that);
        nombre = that.nombre;
        id_padre = that.id_padre;
        nivel = that.nivel;
        activo = that.activo;
    }

    //---------------------------------------------------------------------
    @Override
    public Integer getIdPadre()
    {
        return id_padre;
    }

    //---------------------------------------------------------------------
    @Override
    public Integer getNivel()
    {
        return nivel;
    }

    //---------------------------------------------------------------------
    public String getNombre()
    {
        return nombre;
    }

    //---------------------------------------------------------------------
    public void setNombre(String value)
    {
        this.nombre = value;
    }

    //---------------------------------------------------------------------
    public void setIdPadre(int value)
    {
        this.id_padre = value;
    }

    //---------------------------------------------------------------------
    public void setNivel(int value)
    {
        this.nivel = value;
    }

    //---------------------------------------------------------------------
    public boolean isActivo()
    {
        return activo;
    }

    //---------------------------------------------------------------------
    public void setActivo(boolean value)
    {
        this.activo = value;
    }

    //---------------------------------------------------------------------
    @Override
    public void clear()
    {
        initAtributes();
    }

    //---------------------------------------------------------------------
    public void copy(Puesto that)
    {
        assign(that);
    }

}
