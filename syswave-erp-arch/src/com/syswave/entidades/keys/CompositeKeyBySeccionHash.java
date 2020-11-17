/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.syswave.entidades.keys;

import com.syswave.entidades.common.Entidad;
import static com.syswave.entidades.common.Entidad.EMPTY_STRING;
import com.syswave.entidades.common.EntidadHash;

/**
 *
 * @author victor
 */
public abstract class CompositeKeyBySeccionHash extends EntidadHash
{

    private String[] seccion;

    //----------------------------------------------------------------------
    public CompositeKeyBySeccionHash()
    {
        super();
        seccion = new String[Entidad.Atributo.values().length];
        Entidad.initializeStringPK(seccion);
    }

    //----------------------------------------------------------------------
    public CompositeKeyBySeccionHash(String llave)
    {
        super(llave);
        seccion = new String[Entidad.Atributo.values().length];
        Entidad.initializeStringPK(seccion);
    }

    //---------------------------------------------------------------------
    public String getCompositeKey()
    {
        return String.format("%s,%d", getSeccion_Viejo(), getLlave_Viejo());
    }

    //----------------------------------------------------------------------
    public String getSeccion()
    {
        return seccion[Entidad.Atributo.Actual.ordinal()];
    }

    //---------------------------------------------------------------------
    public String getSeccion(Entidad.Atributo tipo)
    {
        switch (tipo)
        {
            case Viejo:
                if (isModified())
                    return seccion[Entidad.Atributo.Viejo.ordinal()];
            default:
                return seccion[Entidad.Atributo.Actual.ordinal()];
        }
    }

    //---------------------------------------------------------------------
    public String getSeccion_Viejo()
    {
        if (isModified())
            return seccion[Entidad.Atributo.Viejo.ordinal()] != EMPTY_STRING
                    ? seccion[Entidad.Atributo.Viejo.ordinal()] : seccion[Entidad.Atributo.Actual.ordinal()];
        else
            return seccion[Entidad.Atributo.Actual.ordinal()];
    }

    //---------------------------------------------------------------------
    public void setSeccion(String value)
    {
        if (isWaiting())
            seccion[Entidad.Atributo.Viejo.ordinal()] = seccion[Entidad.Atributo.Actual.ordinal()];

        seccion[Entidad.Atributo.Actual.ordinal()] = value;
    }

    //---------------------------------------------------------------------
    protected void assign(CompositeKeyBySeccionHash that)
    {
        super.assign(that);
        setSeccion(that.getSeccion());
    }
}
