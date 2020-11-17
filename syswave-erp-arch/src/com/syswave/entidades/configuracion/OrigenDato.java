package com.syswave.entidades.configuracion;

import com.syswave.entidades.keys.PrimaryKeyById;
import java.io.Serializable;

/**
 * Representa los nombres de las bases de datos.
 *
 * @author Victor Manuel Bucio Vargas
 * @version 25 febrero 2014
 */
public class OrigenDato extends PrimaryKeyById implements Serializable
{

    private String nombre;
    private Boolean activo;

    //---------------------------------------------------------------------
    /**
     * Este método inicializa a valores de construcción los campos.
     */
    private void initAtributes()
    {
        nombre = EMPTY_STRING;
        activo = true;
    }

    //---------------------------------------------------------------------
    /**
     * Este método asigna las propiedades de otro objeto en el actual
     */
    private void assign(OrigenDato that)
    {
        super.assign(that);
        this.nombre = that.nombre;
        this.activo = that.activo;
    }

    //---------------------------------------------------------------------
    public OrigenDato()
    {
        super();
        initAtributes();
    }

    //---------------------------------------------------------------------
    public OrigenDato(OrigenDato that)
    {
        super();
        assign(that);
    }

    //---------------------------------------------------------------------
    /**
     * Este constructor sirve para realizar busquedas por Id en instancias
     * anonimas
     *
     * @param id El id que se relacionara a la instancia.
     */
    public OrigenDato(Integer id)
    {
        super();
        initAtributes();
        super.setId(id);
    }

    //---------------------------------------------------------------------
    /**
     * Este constructor sirve para realizar busquedas por Id y nombre, en
     * instancias anonimas
     *
     * @param id El id que se relacionara a la instancia
     * @param nombre el nombre que se relacionara a la instancia.
     */
    public OrigenDato(Integer id, String nombre)
    {
        super();
        initAtributes();
        super.setId(id);
        this.nombre = nombre;
    }

    //---------------------------------------------------------------------
    /**
     * Obtiene el valor del campo nombre
     *
     * @return nombre
     */
    public String getNombre()
    {
        return nombre;
    }

    //---------------------------------------------------------------------
    /**
     * Establece el valor del campo nombre
     *
     * @param value es un valor de tipo texto
     */
    public void setNombre(String value)
    {
        this.nombre = value;
    }

    //---------------------------------------------------------------------
    /**
     * Obtiene un valor que nos indica si esta activo
     *
     * @return activo
     */
    public Boolean esActivo()
    {
        return activo;
    }

    //---------------------------------------------------------------------
    /**
     * Establece un valor que nos indica si esta activo
     *
     * @param value es un valor boolean
     */
    public void setActivo(Boolean value)
    {
        this.activo = value;
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
    public void copy(OrigenDato that)
    {
        assign(that);
    }
}
