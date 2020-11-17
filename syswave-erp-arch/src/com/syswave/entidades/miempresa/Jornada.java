package com.syswave.entidades.miempresa;

import com.syswave.entidades.keys.PrimaryKeyById;
import java.io.Serializable;

/**
 *
 * @author sis5
 */
public class Jornada extends PrimaryKeyById implements Serializable
{

    private String nombre;
    private Integer tiempo_efectivo;
    private String descripcion;

    public Jornada()
    {
        super();
        initAtributes();
    }

    private void initAtributes()
    {
        nombre = EMPTY_STRING;
        tiempo_efectivo = EMPTY_INT;
        descripcion = EMPTY_STRING;
    }

    private void assign(Jornada that)
    {
        super.assign(that);
        this.nombre = that.nombre;
        this.tiempo_efectivo = that.tiempo_efectivo;
        this.descripcion = that.descripcion;
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
    public Integer getTiempo_efectivo()
    {
        return tiempo_efectivo;
    }

    //---------------------------------------------------------------------
    public void setTiempo_efectivo(int tiempo_efectivo)
    {
        this.tiempo_efectivo = tiempo_efectivo;
    }

    //---------------------------------------------------------------------
    public String getDescripcion()
    {
        return descripcion;
    }

    //---------------------------------------------------------------------
    public void setDescripcion(String descripcion)
    {
        this.descripcion = descripcion;
    }

    //---------------------------------------------------------------------
    @Override
    public void clear()
    {
        initAtributes();
    }

    //---------------------------------------------------------------------
    public void copy(Jornada that)
    {
        assign(that);
    }
}
