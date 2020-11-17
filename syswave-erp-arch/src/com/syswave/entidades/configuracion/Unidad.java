package com.syswave.entidades.configuracion;

import com.syswave.entidades.common.IEntidadRecursiva;
import com.syswave.entidades.keys.PrimaryKeyById;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class Unidad extends PrimaryKeyById implements IEntidadRecursiva
{

    String nombre, abreviatura;
    int escala;
    int id_padre;
    int nivel;
    boolean es_activo;
    int es_tipo;

    //--------------------------------------------------------------
    private void initAtributes()
    {
        nombre = EMPTY_STRING;
        escala = EMPTY_INT;
        id_padre = EMPTY_INT;
        nivel = EMPTY_INT;
        es_activo = false;
        es_tipo = EMPTY_INT;
        abreviatura = EMPTY_STRING;
    }

    //--------------------------------------------------------------
    private void assign(Unidad that)
    {
        super.assign(that);
        nombre = that.nombre;
        escala = that.escala;
        id_padre = that.id_padre;
        nivel = that.nivel;
        es_activo = that.es_activo;
        es_tipo = that.es_tipo;
        abreviatura = that.abreviatura;
    }

    //--------------------------------------------------------------
    public Unidad()
    {
        super();
        initAtributes();
    }

    //--------------------------------------------------------------
    public Unidad(Unidad that)
    {
        super();
        initAtributes();
        assign(that);
    }

    //--------------------------------------------------------------
    public String getNombre()
    {
        return nombre;
    }

    //--------------------------------------------------------------
    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    //--------------------------------------------------------------
    public int getEscala()
    {
        return escala;
    }

    //--------------------------------------------------------------
    public void setEscala(int escala)
    {
        this.escala = escala;
    }

    //--------------------------------------------------------------
    public boolean esActivo()
    {
        return es_activo;
    }

    //--------------------------------------------------------------
    public void setEsActivo(boolean es_activo)
    {
        this.es_activo = es_activo;
    }

    //--------------------------------------------------------------
    public int getEsTipo()
    {
        return es_tipo;
    }

    //--------------------------------------------------------------
    public void setEsTipo(int es_tipo)
    {
        this.es_tipo = es_tipo;
    }

    //--------------------------------------------------------------
    @Override
    public Integer getIdPadre()
    {
        return id_padre;
    }

    //--------------------------------------------------------------
    public void setIdPadre(int value)
    {
        id_padre = value;
    }

    //--------------------------------------------------------------
    @Override
    public Integer getNivel()
    {
        return nivel;
    }

    //--------------------------------------------------------------
    public void setNivel(int value)
    {
        nivel = value;
    }

    //--------------------------------------------------------------
    public String getAbreviatura()
    {
        return abreviatura;
    }

    //--------------------------------------------------------------
    public void setAbreviatura(String abreviatura)
    {
        this.abreviatura = abreviatura;
    }

    //--------------------------------------------------------------
    @Override
    public void clear()
    {
        initAtributes();
    }

    //--------------------------------------------------------------
    public void copy(Unidad that)
    {
        assign(that);
    }

}
