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
public abstract class CompositeKeyByIdPlaneacionDocumento extends PrimaryKeyByIdDocumento
{
    private int[] idPlaneacion;

    //----------------------------------------------------------------------
    public CompositeKeyByIdPlaneacionDocumento()
    {
        super();
        idPlaneacion = new int[Atributo.values().length];
        Entidad.initializeIntegerPK(idPlaneacion);
    }

    //---------------------------------------------------------------------
    public String getCompositeKey()
    {
        return String.format("%d,%d", getIdDocumento_Viejo(), getIdPlaneacion_Viejo());
    }

    //----------------------------------------------------------------------
    public int getIdPlaneacion()
    {
        return idPlaneacion[Atributo.Actual.ordinal()];
    }

    //---------------------------------------------------------------------
    public int getIdPlaneacion(Atributo tipo)
    {
        switch (tipo)
        {
            case Viejo:
                if (isModified())
                    return idPlaneacion[Atributo.Viejo.ordinal()];
            default:
                return idPlaneacion[Atributo.Actual.ordinal()];
        }
    }

    //---------------------------------------------------------------------
    public int getIdPlaneacion_Viejo()
    {
        if (isModified())
            return idPlaneacion[Atributo.Viejo.ordinal()] != EMPTY_INT
                    ? idPlaneacion[Atributo.Viejo.ordinal()] : idPlaneacion[Atributo.Actual.ordinal()];
        else
            return idPlaneacion[Atributo.Actual.ordinal()];
    }

    //---------------------------------------------------------------------
    public void setIdPlaneacion(int value)
    {
        if (isWaiting())
            idPlaneacion[Atributo.Viejo.ordinal()] = idPlaneacion[Atributo.Actual.ordinal()];

        idPlaneacion[Atributo.Actual.ordinal()] = value;
    }

    //---------------------------------------------------------------------
    protected void assign(CompositeKeyByIdPlaneacionDocumento that)
    {
        super.assign(that);
        setIdPlaneacion(that.getIdPlaneacion());
    }
}
