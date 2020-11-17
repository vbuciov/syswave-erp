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
public abstract class CompositeKeyByMonedaIds extends PrimaryKeyByConsecutivo
{
        private int[] idMonedaOrigen, idMonedaDestino;

    //---------------------------------------------------------------------
    private void createAtributes()
    {
        idMonedaOrigen = new int[Entidad.Atributo.values().length];
        idMonedaDestino = new int[Entidad.Atributo.values().length];
    }

    //---------------------------------------------------------------------
    private void initAtributes()
    {
        Entidad.initializeIntegerPK(idMonedaOrigen);
        Entidad.initializeIntegerPK(idMonedaDestino);
    }

    //---------------------------------------------------------------------
    public CompositeKeyByMonedaIds()
    {
        createAtributes();
        initAtributes();
    }

    //---------------------------------------------------------------------
    public String getCompositeKey()
    {
        return String.format("%d,%d,%d", getConsecutivo_Viejo(), getIdMonedaOrigen_Viejo(), getIdMonedaDestino_Viejo());
    }

    //---------------------------------------------------------------------
    public int getIdMonedaOrigen()
    {
        return idMonedaOrigen[Entidad.Atributo.Actual.ordinal()];
    }

    //---------------------------------------------------------------------
    public void setIdMonedaOrigen(int value)
    {
        if (isWaiting())
            idMonedaOrigen[Entidad.Atributo.Viejo.ordinal()]
                    = idMonedaOrigen[Entidad.Atributo.Actual.ordinal()];

        idMonedaOrigen[Entidad.Atributo.Actual.ordinal()] = value;
    }

    //---------------------------------------------------------------------
    public int getIdMonedaOrigen_Viejo()
    {
        if (isModified())
            return idMonedaOrigen[Entidad.Atributo.Viejo.ordinal()] != EMPTY_INT
                    ? idMonedaOrigen[Entidad.Atributo.Viejo.ordinal()]
                    : getIdMonedaOrigen();
        else
            return getIdMonedaOrigen();
    }

    //---------------------------------------------------------------------
    public int getIdMonedaDestino()
    {
        return idMonedaDestino[Entidad.Atributo.Actual.ordinal()];
    }

    //---------------------------------------------------------------------
    public void setIdMonedaDestino(int value)
    {
        if (isWaiting())
            idMonedaDestino[Entidad.Atributo.Viejo.ordinal()]
                    = idMonedaDestino[Entidad.Atributo.Actual.ordinal()];

        idMonedaDestino[Entidad.Atributo.Actual.ordinal()] = value;
    }

    //---------------------------------------------------------------------
    public int getIdMonedaDestino_Viejo()
    {
        if (isModified())
            return idMonedaDestino[Entidad.Atributo.Viejo.ordinal()] != EMPTY_INT
                    ? idMonedaDestino[Entidad.Atributo.Viejo.ordinal()]
                    : getIdMonedaDestino();
        else
            return getIdMonedaDestino();
    }

    //---------------------------------------------------------------------
    @Override
    public void clear()
    {
        initAtributes();
    }

    //---------------------------------------------------------------------
    public void assign(CompositeKeyByMonedaIds that)
    {
       setIdMonedaOrigen(that.getIdMonedaOrigen());
       setIdMonedaDestino(that.getIdMonedaDestino());
    }
}
