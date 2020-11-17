package com.syswave.entidades.keys;

import com.syswave.entidades.common.Entidad;
import static com.syswave.entidades.common.Entidad.EMPTY_INT;

/**
 *
 * @author victor
 */
public abstract class CompositeKeyByIdUbicacionEntrada extends PrimaryKeyByEntrada
{

    private int[] idUbicacion;

    //----------------------------------------------------------------------
    public CompositeKeyByIdUbicacionEntrada()
    {
        super();
        idUbicacion = new int[Atributo.values().length];
        Entidad.initializeIntegerPK(idUbicacion);
    }

    //---------------------------------------------------------------------
    public String getCompositeKey()
    {
        return String.format("%d,%d",  getEntrada_Viejo(), getIdUbicacion_Viejo());
    }

    //----------------------------------------------------------------------
    public int getIdUbicacion()
    {
        return idUbicacion[Atributo.Actual.ordinal()];
    }

    //---------------------------------------------------------------------
    public int getIdUbicacion(Atributo tipo)
    {
        switch (tipo)
        {
            case Viejo:
                if (isModified())
                    return idUbicacion[Atributo.Viejo.ordinal()];
            default:
                return idUbicacion[Atributo.Actual.ordinal()];
        }
    }

    //---------------------------------------------------------------------
    public int getIdUbicacion_Viejo()
    {
        if (isModified())
            return idUbicacion[Atributo.Viejo.ordinal()] != EMPTY_INT
                    ? idUbicacion[Atributo.Viejo.ordinal()] : idUbicacion[Atributo.Actual.ordinal()];
        else
            return idUbicacion[Atributo.Actual.ordinal()];
    }

    //---------------------------------------------------------------------
    public void setIdUbicacion(int value)
    {
        if (isWaiting())
            idUbicacion[Atributo.Viejo.ordinal()] = idUbicacion[Atributo.Actual.ordinal()];

        idUbicacion[Atributo.Actual.ordinal()] = value;
    }

    //---------------------------------------------------------------------
    protected void assign(CompositeKeyByIdUbicacionEntrada that)
    {
        super.assign(that);
        setIdUbicacion(that.getIdUbicacion());
    }
}
