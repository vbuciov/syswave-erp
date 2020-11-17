package com.syswave.entidades.keys;

import com.syswave.entidades.common.Entidad;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class CompositeKeyByBienIds extends Entidad
{
    private int[] id_bien_formal, id_bien_parte;

    //---------------------------------------------------------------------
    private void createAtributes()
    {
        id_bien_formal = new int[Atributo.values().length];
        id_bien_parte = new int[Atributo.values().length];
    }

    //---------------------------------------------------------------------
    private void initAtributes()
    {
        Entidad.initializeIntegerPK(id_bien_formal);
        Entidad.initializeIntegerPK(id_bien_parte);
    }

    //---------------------------------------------------------------------
    public CompositeKeyByBienIds()
    {
        createAtributes();
        initAtributes();
    }

    //---------------------------------------------------------------------
    public String getCompositeKey()
    {
        return String.format("%d,%d", getIdBienFormal_Viejo(), getIdBienParte_Viejo());
    }

    //---------------------------------------------------------------------
    public int getIdBienFormal()
    {
        return id_bien_formal[Atributo.Actual.ordinal()];
    }

    //---------------------------------------------------------------------
    public void setIdBienFormal(int value)
    {
        if (isWaiting())
            id_bien_formal[Atributo.Viejo.ordinal()]
                    = id_bien_formal[Atributo.Actual.ordinal()];

        id_bien_formal[Atributo.Actual.ordinal()] = value;
    }

    //---------------------------------------------------------------------
    public int getIdBienFormal_Viejo()
    {
        if (isModified())
            return id_bien_formal[Atributo.Viejo.ordinal()] != EMPTY_INT
                    ? id_bien_formal[Atributo.Viejo.ordinal()]
                    : getIdBienFormal();
        else
            return getIdBienFormal();
    }

    //---------------------------------------------------------------------
    public int getIdBienParte()
    {
        return id_bien_parte[Atributo.Actual.ordinal()];
    }

    //---------------------------------------------------------------------
    public void setIdBienParte(int value)
    {
        if (isWaiting())
            id_bien_parte[Atributo.Viejo.ordinal()]
                    = id_bien_parte[Atributo.Actual.ordinal()];

        id_bien_parte[Atributo.Actual.ordinal()] = value;
    }

    //---------------------------------------------------------------------
    public int getIdBienParte_Viejo()
    {
        if (isModified())
            return id_bien_parte[Atributo.Viejo.ordinal()] != EMPTY_INT
                    ? id_bien_parte[Atributo.Viejo.ordinal()]
                    : getIdBienParte();
        else
            return getIdBienParte();
    }

    //---------------------------------------------------------------------
    @Override
    public void clear()
    {
        initAtributes();
    }

    //---------------------------------------------------------------------
    public void assign(CompositeKeyByBienIds that)
    {
       setIdBienFormal(that.getIdBienFormal());
       setIdBienParte(that.getIdBienParte());
    }
}
