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
public abstract class CompositeKeyByIdPrecioVariableLinea extends PrimaryKeyByLinea
{
     private int[] idPrecioVariable;

    //----------------------------------------------------------------------
    public CompositeKeyByIdPrecioVariableLinea()
    {
        super();
        idPrecioVariable = new int[Entidad.Atributo.values().length];
        Entidad.initializeIntegerPK(idPrecioVariable);
    }

    //---------------------------------------------------------------------
    public String getCompositeKey()
    {
        return String.format("%d,%d", getLinea_Viejo(), getIdPrecioVariable_Viejo());
    }

    //----------------------------------------------------------------------
    public int getIdPrecioVariable()
    {
        return idPrecioVariable[Entidad.Atributo.Actual.ordinal()];
    }

    //---------------------------------------------------------------------
    public int getIdPrecioVariable(Entidad.Atributo tipo)
    {
        switch (tipo)
        {
            case Viejo:
                if (isModified())
                    return idPrecioVariable[Entidad.Atributo.Viejo.ordinal()];
            default:
                return idPrecioVariable[Entidad.Atributo.Actual.ordinal()];
        }
    }

    //---------------------------------------------------------------------
    public int getIdPrecioVariable_Viejo()
    {
        if (isModified())
            return idPrecioVariable[Entidad.Atributo.Viejo.ordinal()] != EMPTY_INT
                    ? idPrecioVariable[Entidad.Atributo.Viejo.ordinal()] : idPrecioVariable[Entidad.Atributo.Actual.ordinal()];
        else
            return idPrecioVariable[Entidad.Atributo.Actual.ordinal()];
    }

    //---------------------------------------------------------------------
    public void setIdPrecioVariable(int value)
    {
        if (isWaiting())
            idPrecioVariable[Entidad.Atributo.Viejo.ordinal()] = idPrecioVariable[Entidad.Atributo.Actual.ordinal()];

        idPrecioVariable[Entidad.Atributo.Actual.ordinal()] = value;
    }

    //---------------------------------------------------------------------
    protected void assign(CompositeKeyByIdPrecioVariableLinea that)
    {
        super.assign(that);
        setIdPrecioVariable(that.getIdPrecioVariable());
    }   
}
