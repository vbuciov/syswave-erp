/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.syswave.entidades.keys;

import com.syswave.entidades.common.Entidad;
import static com.syswave.entidades.common.Entidad.EMPTY_INT;

/**
 *
 * @author victor
 */
public abstract class CompositeKeyByIdOrigenModulo extends CompositeKeyByIdModuloHash
{

    private Integer[] id_origen_dato;

    //----------------------------------------------------------------------
    public CompositeKeyByIdOrigenModulo()
    {
        super();
        id_origen_dato = new Integer[Atributo.values().length];
        Entidad.initializeIntegerPK(id_origen_dato);
    }

    public CompositeKeyByIdOrigenModulo(String llave)
    {
        super(llave);
        id_origen_dato = new Integer[Atributo.values().length];
        Entidad.initializeIntegerPK(id_origen_dato);
    }

    //---------------------------------------------------------------------
    @Override
    public String getCompositeKey()
    {
        return String.format("%s,%d,%d", getLlave_Viejo(), getId_modulo_Viejo(), getId_origen_dato_Viejo());
    }

    //----------------------------------------------------------------------
    public Integer getId_origen_dato()
    {
        return id_origen_dato[Atributo.Actual.ordinal()];
    }

    //---------------------------------------------------------------------
    public Integer getId_origen_dato(Atributo tipo)
    {
        switch (tipo)
        {
            case Viejo:
                if (isModified())
                    return id_origen_dato[Atributo.Viejo.ordinal()];
            default:
                return id_origen_dato[Atributo.Actual.ordinal()];
        }
    }

    //---------------------------------------------------------------------
    public Integer getId_origen_dato_Viejo()
    {
        if (isModified())
            return id_origen_dato[Atributo.Viejo.ordinal()] != EMPTY_INT
                    ? id_origen_dato[Atributo.Viejo.ordinal()] : id_origen_dato[Atributo.Actual.ordinal()];
        else
            return id_origen_dato[Atributo.Actual.ordinal()];
    }

    //---------------------------------------------------------------------
    public void setId_origen_dato(Integer value)
    {
        if (isWaiting())
            id_origen_dato[Atributo.Viejo.ordinal()] = id_origen_dato[Atributo.Actual.ordinal()];

        id_origen_dato[Atributo.Actual.ordinal()] = value;
    }

    //---------------------------------------------------------------------
    protected void assign(CompositeKeyByIdOrigenModulo that)
    {
        super.assign(that);
        setId_origen_dato(that.getId_origen_dato());
    }
}
