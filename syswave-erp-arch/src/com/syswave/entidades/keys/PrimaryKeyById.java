package com.syswave.entidades.keys;

import com.syswave.entidades.common.Entidad;

/**
 * Provides a standard simple ID handle
 *
 * @author Victor Manuel Bucio Vargas
 */
public abstract class PrimaryKeyById extends Entidad
{

    private Integer[] id;

    //----------------------------------------------------------------------
    public PrimaryKeyById()
    {
        id = new Integer[Entidad.Atributo.values().length];
        Entidad.initializeIntegerPK(id);
    }

    //----------------------------------------------------------------------
    /**
     * Obtiene llave primaria o campo id
     *
     * @return id
     */
    public Integer getId()
    {
        return id[Atributo.Actual.ordinal()];
    }

    //---------------------------------------------------------------------
    /**
     * Obtiene llave primaria o campo id
     *
     * @param tipo
     * @return id
     */
    public Integer getId(Atributo tipo)
    {
        switch (tipo)
        {
            case Viejo:
                if (isModified())
                    return id[Atributo.Viejo.ordinal()];
            default:
                return id[Atributo.Actual.ordinal()];
        }
    }

    //---------------------------------------------------------------------
    /**
     * Obtiene el valor más antiguo de la llave primaria o campo id
     *
     * @return id
     */
    public Integer getId_Viejo()
    {
        if (isModified())
            return id[Atributo.Viejo.ordinal()] != EMPTY_INT
                    ? id[Atributo.Viejo.ordinal()] : id[Atributo.Actual.ordinal()];
        else
            return id[Atributo.Actual.ordinal()];
    }

    //---------------------------------------------------------------------
    /**
     * Establece la llave primaria
     *
     * @param value Es el valor a asignar
     */
    public void setId(Integer value)
    {
        if (isWaiting())
            id[Atributo.Viejo.ordinal()] = id[Atributo.Actual.ordinal()];

        id[Atributo.Actual.ordinal()] = value;
    }

    //---------------------------------------------------------------------
    protected void assign(PrimaryKeyById that)
    {
        setId(that.getId());
    }
}
