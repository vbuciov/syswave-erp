package com.syswave.entidades.configuracion;

import com.syswave.entidades.common.IEntidadRecursiva;
import com.syswave.entidades.keys.PrimaryKeyById;
import java.io.Serializable;

/**
 * Es una localicación geografica
 *
 * @author Victor Manuel Bucio Vargas
 * @version 26 febrero 2014
 */
public class Localidad extends PrimaryKeyById implements Serializable, IEntidadRecursiva
{

    private String nombre, siglas;
    private Integer idPadre, nivel;
    private Boolean activo;

    //---------------------------------------------------------------------
    private void initAtributes()
    {
        nombre = EMPTY_STRING;
        siglas = EMPTY_STRING;
        idPadre = EMPTY_INT;
        nivel = EMPTY_INT;
        activo = true;
    }

    //---------------------------------------------------------------------
    private void assign(Localidad that)
    {
        super.assign(that);
        nombre = that.nombre;
        siglas = that.siglas;
        idPadre = that.idPadre;
        nivel = that.nivel;
        activo = that.activo;
    }

    //---------------------------------------------------------------------
    public Localidad()
    {
        super();
        initAtributes();
    }

    //---------------------------------------------------------------------
    public Localidad(Localidad that)
    {
        super();
        assign(that);
    }

    //---------------------------------------------------------------------
    /**
     * Este constructor sirve para realizar busquedas por nivel en instancias
     * anonimas
     *
     * @param nivel El nivel que se relacionara a la instancia.
     */
    public Localidad(Integer nivel)
    {
        super();
        initAtributes();
        this.nivel = nivel;
    }

    //---------------------------------------------------------------------
    /**
     * Este constructor sirve para realizar busquedas por Id en instancias
     * anonimas
     *
     * @param nivel El nivel que se relacionara a la instancia.
     * @param id El id que se relacionara a la instancia
     */
    public Localidad(Integer nivel, Integer id)
    {
        super();
        initAtributes();
        this.nivel = nivel;
        super.setId(id);
    }

    //---------------------------------------------------------------------
    /**
     * Este constructor sirve para realizar busquedas por Id, y idPadre en
     * instancias anonimas
     *
     * @param nivel El nivel que se relacionara a la instancia.
     * @param id El id que se relacionara a la instancia
     * @param idPadre el idPadre que se relacionara a la instancia
     */
    public Localidad(Integer nivel, Integer id, Integer idPadre)
    {
        super();
        initAtributes();
        super.setId(id);
        this.idPadre = idPadre;
        this.nivel = nivel;
    }

    //---------------------------------------------------------------------
    @Override
    public Integer getIdPadre()
    {
        return idPadre;
    }

    //---------------------------------------------------------------------
    public void setIdPadre(Integer value)
    {
        idPadre = value;
    }

    //---------------------------------------------------------------------
    @Override
    public Integer getNivel()
    {
        return nivel;
    }

    //---------------------------------------------------------------------
    public void setNivel(Integer value)
    {
        nivel = value;
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
    @Override
    public void clear()
    {
        initAtributes();
    }

    //---------------------------------------------------------------------
    public void copy(Localidad that)
    {
        assign(that);
    }

}
