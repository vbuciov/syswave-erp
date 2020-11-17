package com.syswave.entidades.miempresa;

import com.syswave.entidades.common.IEntidadRecursiva;
import com.syswave.entidades.keys.PrimaryKeyById;
import java.io.Serializable;

/**
 * Indica el tipo de persona.
 *
 * @author Victor Manuel Bucio Vargas
 * @version 1 Marzo 2014
 */
public class TipoPersona extends PrimaryKeyById implements IEntidadRecursiva, Serializable
{

    private String nombre, siglas;
    private int id_padre, nivel;
    private Boolean activo, usa_mantenimiento, usa_personal;

    //---------------------------------------------------------------------
    public TipoPersona()
    {
        super();
        initAtributes();
    }

    //---------------------------------------------------------------------
    public TipoPersona(TipoPersona that)
    {
        super();
        assign(that);
    }

    //---------------------------------------------------------------------
    private void initAtributes()
    {
        nombre = EMPTY_STRING;
        siglas = EMPTY_STRING;
        id_padre = EMPTY_INT;
        nivel = EMPTY_INT;
        activo = true;
        usa_mantenimiento = false;
        usa_personal = true;
    }

    //---------------------------------------------------------------------
    private void assign(TipoPersona that)
    {
        super.assign(that);
        nombre = that.nombre;
        siglas = that.siglas;
        id_padre = that.id_padre;
        nivel = that.nivel;
        activo = that.activo;
        usa_mantenimiento = that.usa_mantenimiento;
        usa_personal = that.usa_personal;
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

    //----------------------------------------------------------------------
    public String getSiglas()
    {
        return siglas;
    }

    //----------------------------------------------------------------------
    public void setSiglas(String siglas)
    {
        this.siglas = siglas;
    }

    //----------------------------------------------------------------------
    @Override
    public Integer getIdPadre()
    {
        return id_padre;
    }

    //----------------------------------------------------------------------
    public void setIdPadre(int value)
    {
        this.id_padre = value;
    }

    //----------------------------------------------------------------------
    @Override
    public Integer getNivel()
    {
        return nivel;
    }

    //----------------------------------------------------------------------
    public void setNivel(int value)
    {
        this.nivel = value;
    }

    //---------------------------------------------------------------------
    public Boolean esActivo()
    {
        return activo;
    }

    //---------------------------------------------------------------------
    public void setActivo(Boolean activo)
    {
        this.activo = activo;
    }

    //---------------------------------------------------------------------
    public Boolean esUsaMantenimiento()
    {
        return usa_mantenimiento;
    }

    //---------------------------------------------------------------------
    public void setUsaMantenimiento(Boolean value)
    {
        this.usa_mantenimiento = value;
    }

    //---------------------------------------------------------------------
    public Boolean esUsaPersonal()
    {
        return usa_personal;
    }

    //---------------------------------------------------------------------
    public void setUsaPersonal(Boolean value)
    {
        this.usa_personal = value;
    }

    //---------------------------------------------------------------------
    @Override
    public void clear()
    {
        initAtributes();
    }

    //---------------------------------------------------------------------
    public void copy(TipoPersona that)
    {
        assign(that);
    }
}
