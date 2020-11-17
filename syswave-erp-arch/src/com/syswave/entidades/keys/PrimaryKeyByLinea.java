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
public abstract class PrimaryKeyByLinea extends Entidad
{
    private int[] linea;

    //----------------------------------------------------------------------
    public PrimaryKeyByLinea()
    {
        linea = new int[Entidad.Atributo.values().length];
        Entidad.initializeIntegerPK(linea);
    }

    //----------------------------------------------------------------------
    public int getLinea()
    {
        return linea[Entidad.Atributo.Actual.ordinal()];
    }

    //---------------------------------------------------------------------
    public int getLinea(Entidad.Atributo tipo)
    {
        switch (tipo)
        {
            case Viejo:
                if (isModified())
                    return linea[Entidad.Atributo.Viejo.ordinal()];
            default:
                return linea[Entidad.Atributo.Actual.ordinal()];
        }
    }

    //---------------------------------------------------------------------
    public int getLinea_Viejo()
    {
        if (isModified())
            return linea[Entidad.Atributo.Viejo.ordinal()] != EMPTY_INT
                    ? linea[Entidad.Atributo.Viejo.ordinal()] : linea[Entidad.Atributo.Actual.ordinal()];
        else
            return linea[Entidad.Atributo.Actual.ordinal()];
    }

    //---------------------------------------------------------------------
    public void setLinea(int value)
    {
        if (isWaiting())
            linea[Entidad.Atributo.Viejo.ordinal()] = linea[Entidad.Atributo.Actual.ordinal()];

        linea[Entidad.Atributo.Actual.ordinal()] = value;
    }

    //---------------------------------------------------------------------
    protected void assign(PrimaryKeyByLinea that)
    {
         setLinea(that.getLinea());
    }
}
