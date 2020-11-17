package com.syswave.entidades.keys;

import com.syswave.entidades.common.Entidad;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public abstract class CompositeKeyByIdMovimientoPersona extends CompositeKeyByIdPersonaConsecutivo
{

    private int[] movimiento;

    //----------------------------------------------------------------------
    public CompositeKeyByIdMovimientoPersona()
    {
        super();
        movimiento = new int[Atributo.values().length];
        Entidad.initializeIntegerPK(movimiento);
    }

    //---------------------------------------------------------------------
    @Override
    public String getCompositeKey()
    {
        return String.format("%d,%d,%d", getConsecutivo_Viejo(), getIdPersona_Viejo(), getMovimiento_Viejo());
    }

    //----------------------------------------------------------------------
    public int getMovimiento()
    {
        return movimiento[Atributo.Actual.ordinal()];
    }

    //---------------------------------------------------------------------
    public int getMovimiento(Atributo tipo)
    {
        switch (tipo)
        {
            case Viejo:
                if (isModified())
                    return movimiento[Atributo.Viejo.ordinal()];
            default:
                return movimiento[Atributo.Actual.ordinal()];
        }
    }

    //---------------------------------------------------------------------
    public int getMovimiento_Viejo()
    {
        if (isModified())
            return movimiento[Atributo.Viejo.ordinal()] != EMPTY_INT
                    ? movimiento[Atributo.Viejo.ordinal()] : movimiento[Atributo.Actual.ordinal()];
        else
            return movimiento[Atributo.Actual.ordinal()];
    }

    //---------------------------------------------------------------------
    public void setMovimiento(int value)
    {
        if (isWaiting())
            movimiento[Atributo.Viejo.ordinal()] = movimiento[Atributo.Actual.ordinal()];

        movimiento[Atributo.Actual.ordinal()] = value;
    }

    //---------------------------------------------------------------------
    protected void assign(CompositeKeyByIdMovimientoPersona that)
    {
        super.assign(that);
        setMovimiento(that.getMovimiento());
    }
}
