package com.syswave.entidades.configuracion;

import com.syswave.entidades.keys.PrimaryKeyByIdentificador;
import java.io.Serializable;

/**
 * Representa los alias, que comparten un mismo usuario físico
 *
 * @author Victor Manuel Bucio Vargas
 * @version 26 febrero 2014
 */
public class Usuario extends PrimaryKeyByIdentificador implements Serializable
{

    private String clave;
    private Boolean activo;
    private int es_tipo;

    //---------------------------------------------------------------------
    /**
     * Este método inicializa a valores de construcción los campos.
     */
    private void initAtributes()
    {
        clave = EMPTY_STRING;
        activo = true;
        es_tipo = EMPTY_INT;
    }

    //---------------------------------------------------------------------
    /**
     * Este método asigna las propiedades de otro objeto en el actual
     */
    private void assign(Usuario that)
    {
        super.assign(that);
        clave = that.clave;
        activo = that.activo;
        es_tipo = that.es_tipo;
    }

    //---------------------------------------------------------------------
    public Usuario()
    {
        super();
        initAtributes();
    }

    //---------------------------------------------------------------------
    public Usuario(Usuario that)
    {
        super();
        assign(that);
    }

    //---------------------------------------------------------------------
    /**
     * Este constructor sirve para realizar busquedas por Identificador y clave
     * en instancias anonimas
     *
     * @param identificador El nombre de usuario que se relacionara a la
     * instancia.
     * @param clave Es la contraseña de usuario que se relacionara a la
     * instancia.
     */
    public Usuario(String identificador, String clave)
    {
        super();
        initAtributes();
        super.setIdentificador(identificador);
        this.clave = clave;

    }

    //---------------------------------------------------------------------
    /**
     * Obtiene la clave personal del usuario.
     *
     * @return clave
     */
    public String getClave()
    {
        return clave;
    }

    //---------------------------------------------------------------------
    /**
     * Establece la clave que es utilizada como contraseña personal del usuario
     *
     * @param value Es el valor que será utilizado como contraseña
     */
    public void setClave(String value)
    {
        this.clave = value;
    }

    //---------------------------------------------------------------------
    /**
     * Obtiene un valor para determinar si el usuario esta activo
     *
     * @return activo
     */
    public Boolean esActivo()
    {
        return activo;
    }

    //---------------------------------------------------------------------
    /**
     * Establece un valor que sirve para determinar si el usuario esta activo
     *
     * @param value Activo
     */
    public void setActivo(Boolean value)
    {
        this.activo = value;
    }

    //---------------------------------------------------------------------
    /**
     * Obtiene un valor que indica si el objeto es un usuario o un rol
     */
    public int getEs_tipo()
    {
        return es_tipo;
    }

    //---------------------------------------------------------------------
    /**
     * Establese un valor que indica si el objeto es un usuario o un rol
     */
    public void setEs_tipo(int es_tipo)
    {
        this.es_tipo = es_tipo;
    }

    //---------------------------------------------------------------------
    /**
     * Reinicializa los valores de cada campo a sus valores de construcción.
     */
    @Override
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
    public void copy(Usuario that)
    {
        assign(that);
    }
}
