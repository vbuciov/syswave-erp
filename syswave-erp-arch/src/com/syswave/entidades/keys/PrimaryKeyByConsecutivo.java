package com.syswave.entidades.keys;

import com.syswave.entidades.common.Entidad;
import static com.syswave.entidades.common.Entidad.EMPTY_INT;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public abstract class PrimaryKeyByConsecutivo extends Entidad
{
    private int[] consecutivo;

    //----------------------------------------------------------------------
    public PrimaryKeyByConsecutivo()
    {
        consecutivo = new int[Atributo.values().length];
        Entidad.initializeIntegerPK(consecutivo);
    }

    //----------------------------------------------------------------------
    public int getConsecutivo()
    {
        return consecutivo[Atributo.Actual.ordinal()];
    }

    //---------------------------------------------------------------------
    public int getConsecutivo(Atributo tipo)
    {
        switch (tipo)
        {
            case Viejo:
                if (isModified())
                    return consecutivo[Atributo.Viejo.ordinal()];
            default:
                return consecutivo[Atributo.Actual.ordinal()];
        }
    }

    //---------------------------------------------------------------------
    public int getConsecutivo_Viejo()
    {
        if (isModified())
            return consecutivo[Atributo.Viejo.ordinal()] != EMPTY_INT
                    ? consecutivo[Atributo.Viejo.ordinal()] : consecutivo[Atributo.Actual.ordinal()];
        else
            return consecutivo[Atributo.Actual.ordinal()];
    }

    //---------------------------------------------------------------------
    public void setConsecutivo(int value)
    {
        if (isWaiting())
            consecutivo[Atributo.Viejo.ordinal()] = consecutivo[Atributo.Actual.ordinal()];

        consecutivo[Atributo.Actual.ordinal()] = value;
    }

    //---------------------------------------------------------------------
    protected void assign(PrimaryKeyByConsecutivo that)
    {
         setConsecutivo(that.getConsecutivo());
    }
}
