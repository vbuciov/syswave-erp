/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.syswave.entidades.keys;

import com.syswave.entidades.common.Entidad;
import static com.syswave.entidades.common.Entidad.EMPTY_INT;
import com.syswave.entidades.common.EntidadHash;

/**
 *
 * @author victor
 */
public abstract class CompositeKeyByIdModuloHash extends EntidadHash
{

    private Integer[] id_modulo;

    //----------------------------------------------------------------------
    public CompositeKeyByIdModuloHash()
    {
        super();
        id_modulo = new Integer[Atributo.values().length];
        Entidad.initializeIntegerPK(id_modulo);
    }

    public CompositeKeyByIdModuloHash(String llave)
    {
        super(llave);
        id_modulo = new Integer[Atributo.values().length];
        Entidad.initializeIntegerPK(id_modulo);
    }
    
    

    //---------------------------------------------------------------------
    public String getCompositeKey()
    {
        return String.format("%s,%d", getLlave_Viejo(), getId_modulo_Viejo());
    }

    //----------------------------------------------------------------------
    public Integer getId_modulo()
    {
        return id_modulo[Atributo.Actual.ordinal()];
    }

    //---------------------------------------------------------------------
    public Integer getId_modulo(Atributo tipo)
    {
        switch (tipo)
        {
            case Viejo:
                if (isModified())
                    return id_modulo[Atributo.Viejo.ordinal()];
            default:
                return id_modulo[Atributo.Actual.ordinal()];
        }
    }

    //---------------------------------------------------------------------
    public Integer getId_modulo_Viejo()
    {
        if (isModified())
            return id_modulo[Atributo.Viejo.ordinal()] != EMPTY_INT
                    ? id_modulo[Atributo.Viejo.ordinal()] : id_modulo[Atributo.Actual.ordinal()];
        else
            return id_modulo[Atributo.Actual.ordinal()];
    }

    //---------------------------------------------------------------------
    public void setId_modulo(Integer value)
    {
        if (isWaiting())
            id_modulo[Atributo.Viejo.ordinal()] = id_modulo[Atributo.Actual.ordinal()];

        id_modulo[Atributo.Actual.ordinal()] = value;
    }

    //---------------------------------------------------------------------
    protected void assign(CompositeKeyByIdModuloHash that)
    {
        super.assign(that);
        setId_modulo(that.getId_modulo());
    }
}
