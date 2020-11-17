package com.syswave.entidades.keys;

import com.syswave.entidades.common.Entidad;
import static com.syswave.entidades.common.Entidad.EMPTY_INT;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public abstract class PrimaryKeyByEntrada extends Entidad
{
    private int[] entrada;

    //----------------------------------------------------------------------
    public PrimaryKeyByEntrada()
    {
        entrada = new int[Entidad.Atributo.values().length];
        Entidad.initializeIntegerPK(entrada);
    }

    //----------------------------------------------------------------------
    public int getEntrada()
    {
        return entrada[Entidad.Atributo.Actual.ordinal()];
    }

    //---------------------------------------------------------------------
    public int getEntrada(Entidad.Atributo tipo)
    {
        switch (tipo)
        {
            case Viejo:
                if (isModified())
                    return entrada[Entidad.Atributo.Viejo.ordinal()];
            default:
                return entrada[Entidad.Atributo.Actual.ordinal()];
        }
    }

    //---------------------------------------------------------------------
    public int getEntrada_Viejo()
    {
        if (isModified())
            return entrada[Entidad.Atributo.Viejo.ordinal()] != EMPTY_INT
                    ? entrada[Entidad.Atributo.Viejo.ordinal()] : entrada[Entidad.Atributo.Actual.ordinal()];
        else
            return entrada[Entidad.Atributo.Actual.ordinal()];
    }

    //---------------------------------------------------------------------
    public void setEntrada(int value)
    {
        if (isWaiting())
            entrada[Entidad.Atributo.Viejo.ordinal()] = entrada[Entidad.Atributo.Actual.ordinal()];

        entrada[Entidad.Atributo.Actual.ordinal()] = value;
    }

    //---------------------------------------------------------------------
    protected void assign(PrimaryKeyByEntrada that)
    {
         setEntrada(that.getEntrada());
    }
}
