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
public abstract class PrimaryKeyByIdVariante extends Entidad
{
        private int[] idVariante;

    //----------------------------------------------------------------------
    public PrimaryKeyByIdVariante()
    {
        idVariante = new int[Entidad.Atributo.values().length];
        Entidad.initializeIntegerPK(idVariante);
    }

    //----------------------------------------------------------------------
    public int getIdVariante()
    {
        return idVariante[Entidad.Atributo.Actual.ordinal()];
    }

    //---------------------------------------------------------------------
    public int getIdVariante(Entidad.Atributo tipo)
    {
        switch (tipo)
        {
            case Viejo:
                if (isModified())
                    return idVariante[Entidad.Atributo.Viejo.ordinal()];
            default:
                return idVariante[Entidad.Atributo.Actual.ordinal()];
        }
    }

    //---------------------------------------------------------------------
    public int getIdVariante_Viejo()
    {
        if (isModified())
            return idVariante[Entidad.Atributo.Viejo.ordinal()] != EMPTY_INT
                    ? idVariante[Entidad.Atributo.Viejo.ordinal()] : idVariante[Entidad.Atributo.Actual.ordinal()];
        else
            return idVariante[Entidad.Atributo.Actual.ordinal()];
    }

    //---------------------------------------------------------------------
    public void setIdVariante(int value)
    {
        if (isWaiting())
            idVariante[Entidad.Atributo.Viejo.ordinal()] = idVariante[Entidad.Atributo.Actual.ordinal()];

        idVariante[Entidad.Atributo.Actual.ordinal()] = value;
    }

    //---------------------------------------------------------------------
    protected void assign(PrimaryKeyByIdVariante that)
    {
         setIdVariante(that.getIdVariante());
    }
}
