package com.syswave.entidades.configuracion;

import com.syswave.entidades.common.IEntidadRecursiva;
import com.syswave.entidades.keys.PrimaryKeyById;
import java.io.Serializable;

/**
 * Son relaciona llave y valor, para cuestiones diversas
 *
 * @author Victor Manuel Bucio Vargas
 * @version 25 febrero 2014
 */
public class ModuloInstalado extends PrimaryKeyById implements Serializable, IEntidadRecursiva
{

    private String titulo, URI, params, icon;
    private Integer idPadre, nivel;
    private Boolean activo, estatico, leaf;

    //---------------------------------------------------------------------
    /**
     * Este método inicializa a valores de construcción los campos.
     */
    private void initAtributes()
    {
        titulo = EMPTY_STRING;
        URI = EMPTY_STRING;
        params = EMPTY_STRING;
        icon = EMPTY_STRING;
        idPadre = EMPTY_INT;
        nivel = EMPTY_INT;
        activo = true;
        estatico = false;
        leaf = false;
    }

    //---------------------------------------------------------------------
    /**
     * Este método asigna las propiedades de otro objeto en el actual
     */
    private void assign(ModuloInstalado that)
    {
        super.assign(that);
        titulo = that.titulo;
        URI = that.URI;
        //params = that.params; NOTA: Este campo todavía no se lee de la tabla.
        icon = that.icon;
        idPadre = that.idPadre;
        nivel = that.nivel;
        activo = that.activo;
        estatico = that.estatico;
    }

    //---------------------------------------------------------------------
    public ModuloInstalado()
    {
        super();
        initAtributes();
    }

    //---------------------------------------------------------------------
    public ModuloInstalado(ModuloInstalado that)
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
    public ModuloInstalado(Integer nivel)
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
    public ModuloInstalado(Integer nivel, Integer id)
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
    public ModuloInstalado(Integer nivel, Integer id, Integer idPadre)
    {
        super();
        initAtributes();
        super.setId(id);
        this.idPadre = idPadre;
        this.nivel = nivel;
    }

    /**
     * Este construcor sirve para realizar busquedas por URI.
     *
     * @param uri El el Uniform Resource Identifier
     */
    public ModuloInstalado(String uri)
    {
        super();
        initAtributes();
        this.URI = uri;
    }

    //---------------------------------------------------------------------
    /**
     * Obtiene el identificador del padre relacionado
     *
     * @return idPadre
     */
    @Override
    public Integer getIdPadre()
    {
        return idPadre;
    }

    //---------------------------------------------------------------------
    /**
     * Establece sirve para identificar si el elemento esta relacionado con otro
     * dentro de la misma tabla.
     *
     * @param value idPadre
     */
    public void setIdPadre(Integer value)
    {
        this.idPadre = value;
    }

    //---------------------------------------------------------------------
    /**
     * Obtiene el valor del nivel
     *
     * @return nivel
     */
    @Override
    public Integer getNivel()
    {
        return nivel;
    }

    //---------------------------------------------------------------------
    /**
     * Establece sirve para determinar el nivel de relaciones previas para
     * llegar a este.
     *
     * @param value
     */
    public void setNivel(Integer value)
    {
        this.nivel = value;
    }

    //---------------------------------------------------------------------
    /**
     * Obtiene el titulo asignado al módulo.
     *
     * @return titulo
     */
    public String getTitulo()
    {
        return titulo;
    }

    //---------------------------------------------------------------------
    /**
     * Establece sirve para desplegar una leyenda entendible al usuario.
     *
     * @param value Es el texto a asignar como titulo
     */
    public void setTitulo(String value)
    {
        this.titulo = value;
    }

    //---------------------------------------------------------------------
    public String getURI()
    {
        return URI;
    }

    //---------------------------------------------------------------------
    public void setURI(String URI)
    {
        this.URI = URI;
    }

    //---------------------------------------------------------------------
    public String getParams()
    {
        return params;
    }

    //---------------------------------------------------------------------
    public void setParams(String value)
    {
        this.params = value;
    }

    //---------------------------------------------------------------------
    public String getIcon()
    {
        return icon;
    }

    //---------------------------------------------------------------------
    public void setIcon(String icon)
    {
        this.icon = icon;
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
    public Boolean esEstatico()
    {
        return estatico;
    }

    //---------------------------------------------------------------------
    public void setEstatico(Boolean estatico)
    {
        this.estatico = estatico;
    }

    //---------------------------------------------------------------------
    public Boolean isLeaf()
    {
        return leaf;
    }

    //---------------------------------------------------------------------
    public void setLeaf(Boolean leaf)
    {
        this.leaf = leaf;
    }

    //---------------------------------------------------------------------
    @Override
    /**
     * Reinicializa los valores de cada campo a sus valores de construcción.
     */
    public void clear()
    {
        initAtributes();
    }

    //---------------------------------------------------------------------
    /**
     * Copia los valores del objeto recibido en esta instancia
     *
     * @param that El objeto del cual se tomaran los valores.
     */
    public void copy(ModuloInstalado that)
    {
        assign(that);
    }
}
