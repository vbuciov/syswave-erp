package com.syswave.entidades.miempresa;

import com.syswave.entidades.common.IEntidadRecursiva;
import com.syswave.entidades.keys.PrimaryKeyById;

/**
 * Son las categorias de los bienes.
 *
 * @author Victor Manuel Bucio Vargas
 */
public class Categoria extends PrimaryKeyById implements IEntidadRecursiva
{

    private String nombre, siglas;
    private int id_padre, nivel;
    private boolean es_activo;

    //----------------------------------------------------------------------
    public Categoria()
    {
        super();
        initAtributes();
    }

    //----------------------------------------------------------------------
    public Categoria(Categoria that)
    {
        super();
        assign(that);
    }

    //----------------------------------------------------------------------
    private void assign(Categoria that)
    {
        super.assign(that);
        nombre = that.nombre;
        siglas = that.siglas;
        id_padre = that.id_padre;
        nivel = that.nivel;
        es_activo = that.es_activo;
    }

    //----------------------------------------------------------------------
    private void initAtributes()
    {
        nombre = EMPTY_STRING;
        siglas = EMPTY_STRING;
        id_padre = EMPTY_INT;
        nivel = EMPTY_INT;
        es_activo = true;
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

    //----------------------------------------------------------------------
    public boolean esActivo()
    {
        return es_activo;
    }

    //----------------------------------------------------------------------
    public void setEsActivo(boolean value)
    {
        this.es_activo = value;
    }

    //----------------------------------------------------------------------
    @Override
    public void clear()
    {
        initAtributes();
    }

    //---------------------------------------------------------------------
    public void copy(Categoria that)
    {
        assign(that);
    }
}
