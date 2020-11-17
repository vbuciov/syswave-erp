package com.syswave.entidades.miempresa;

import com.syswave.entidades.keys.PrimaryKeyById;

/**
 * Esta clase representa un registro de machote de contrato.
 *
 * @author sis5
 */
public class Contrato extends PrimaryKeyById
{

    private String nombre, formato;
    private int longitud;
    private byte[] contenido;

    public Contrato()
    {
        super();
        initAtributes();
    }

    public Contrato(Contrato that)
    {
        super();
        assign(that);
    }

    private void initAtributes()
    {
        nombre = EMPTY_STRING;
        formato = EMPTY_STRING;
        longitud = EMPTY_INT;
        contenido = null;
    }

    private void assign(Contrato that)
    {
        super.assign(that);
        formato = that.formato;
        longitud = that.longitud;
        contenido = that.contenido;
        nombre = that.nombre;
    }

    //---------------------------------------------------------------------
    public String getFormato()
    {
        return formato;
    }

    //---------------------------------------------------------------------
    public void setFormato(String formato)
    {
        this.formato = formato;
    }

    //---------------------------------------------------------------------
    public int getLongitud()
    {
        return longitud;
    }

    //---------------------------------------------------------------------
    public void setLongitud(int longitud)
    {
        this.longitud = longitud;
    }

    //---------------------------------------------------------------------
    public byte[] getContenido()
    {
        return contenido;
    }

    //---------------------------------------------------------------------
    public void setContenido(byte[] contenido)
    {
        this.contenido = contenido;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    @Override
    public void clear()
    {
        initAtributes();
    }

    public void copy(Contrato that)
    {
        assign(that);
    }
}
