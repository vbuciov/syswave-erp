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
 * @author Victor Manuel Bucio Vargas
 */
public abstract class PrimaryKeyByIdDocumento extends Entidad
{
    private int[] idDocumento;

    //----------------------------------------------------------------------
    public PrimaryKeyByIdDocumento()
    {
        idDocumento = new int[Entidad.Atributo.values().length];
        Entidad.initializeIntegerPK(idDocumento);
    }

    //----------------------------------------------------------------------
    public int getIdDocumento()
    {
        return idDocumento[Entidad.Atributo.Actual.ordinal()];
    }

    //---------------------------------------------------------------------
    public int getIdDocumento(Entidad.Atributo tipo)
    {
        switch (tipo)
        {
            case Viejo:
                if (isModified())
                    return idDocumento[Entidad.Atributo.Viejo.ordinal()];
            default:
                return idDocumento[Entidad.Atributo.Actual.ordinal()];
        }
    }

    //---------------------------------------------------------------------
    public int getIdDocumento_Viejo()
    {
        if (isModified())
            return idDocumento[Entidad.Atributo.Viejo.ordinal()] != EMPTY_INT
                    ? idDocumento[Entidad.Atributo.Viejo.ordinal()] : idDocumento[Entidad.Atributo.Actual.ordinal()];
        else
            return idDocumento[Entidad.Atributo.Actual.ordinal()];
    }

    //---------------------------------------------------------------------
    public void setIdDocumento(int value)
    {
        if (isWaiting())
            idDocumento[Entidad.Atributo.Viejo.ordinal()] = idDocumento[Entidad.Atributo.Actual.ordinal()];

        idDocumento[Entidad.Atributo.Actual.ordinal()] = value;
    }

    //---------------------------------------------------------------------
    protected void assign(PrimaryKeyByIdDocumento that)
    {
         setIdDocumento(that.getIdDocumento());
    }
}
