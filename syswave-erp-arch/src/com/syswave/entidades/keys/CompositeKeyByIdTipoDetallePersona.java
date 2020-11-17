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
public abstract class CompositeKeyByIdTipoDetallePersona extends PrimaryKeyByIdPersona
{
     private int[] idTipoDetalle;

    //----------------------------------------------------------------------
    public CompositeKeyByIdTipoDetallePersona()
    {
        super();
        idTipoDetalle = new int[Atributo.values().length];
        Entidad.initializeIntegerPK(idTipoDetalle);
    }

    //---------------------------------------------------------------------
    public String getCompositeKey()
    {
        return String.format("%d,%d", getIdTipoDetalle_Viejo(), getIdPersona_Viejo());
    }

    //----------------------------------------------------------------------
    public int getIdTipoDetalle()
    {
        return idTipoDetalle[Atributo.Actual.ordinal()];
    }

    //---------------------------------------------------------------------
    public int getIdTipoDetalle(Atributo tipo)
    {
        switch (tipo)
        {
            case Viejo:
                if (isModified())
                    return idTipoDetalle[Atributo.Viejo.ordinal()];
            default:
                return idTipoDetalle[Atributo.Actual.ordinal()];
        }
    }

    //---------------------------------------------------------------------
    public int getIdTipoDetalle_Viejo()
    {
        if (isModified())
            return idTipoDetalle[Atributo.Viejo.ordinal()] != EMPTY_INT
                    ? idTipoDetalle[Atributo.Viejo.ordinal()] : idTipoDetalle[Atributo.Actual.ordinal()];
        else
            return idTipoDetalle[Atributo.Actual.ordinal()];
    }

    //---------------------------------------------------------------------
    public void setIdTipoDetalle(int value)
    {
        if (isWaiting())
            idTipoDetalle[Atributo.Viejo.ordinal()] = idTipoDetalle[Atributo.Actual.ordinal()];

        idTipoDetalle[Atributo.Actual.ordinal()] = value;
    }

    //---------------------------------------------------------------------
    protected void assign(CompositeKeyByIdTipoDetallePersona that)
    {
        super.assign(that);
        setIdTipoDetalle(that.getIdTipoDetalle());
    }   
}
