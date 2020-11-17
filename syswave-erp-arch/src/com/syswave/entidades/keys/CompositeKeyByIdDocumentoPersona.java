package com.syswave.entidades.keys;

import com.syswave.entidades.common.Entidad;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public abstract class CompositeKeyByIdDocumentoPersona extends CompositeKeyByIdPersonaConsecutivo
{

    private int[] idDocumento;

    //----------------------------------------------------------------------
    public CompositeKeyByIdDocumentoPersona()
    {
        super();
        idDocumento = new int[Atributo.values().length];
        Entidad.initializeIntegerPK(idDocumento);
    }

    //---------------------------------------------------------------------
    @Override
    public String getCompositeKey()
    {
        return String.format("%d,%d,%d", getConsecutivo_Viejo(), getIdPersona_Viejo(), getIdDocumento_Viejo());
    }

    //----------------------------------------------------------------------
    public int getIdDocumento()
    {
        return idDocumento[Atributo.Actual.ordinal()];
    }

    //---------------------------------------------------------------------
    public int getIdDocumento(Atributo tipo)
    {
        switch (tipo)
        {
            case Viejo:
                if (isModified())
                    return idDocumento[Atributo.Viejo.ordinal()];
            default:
                return idDocumento[Atributo.Actual.ordinal()];
        }
    }

    //---------------------------------------------------------------------
    public int getIdDocumento_Viejo()
    {
        if (isModified())
            return idDocumento[Atributo.Viejo.ordinal()] != EMPTY_INT
                    ? idDocumento[Atributo.Viejo.ordinal()] : idDocumento[Atributo.Actual.ordinal()];
        else
            return idDocumento[Atributo.Actual.ordinal()];
    }

    //---------------------------------------------------------------------
    public void setIdDocumento(int value)
    {
        if (isWaiting())
            idDocumento[Atributo.Viejo.ordinal()] = idDocumento[Atributo.Actual.ordinal()];

        idDocumento[Atributo.Actual.ordinal()] = value;
    }

    //---------------------------------------------------------------------
    protected void assign(CompositeKeyByIdDocumentoPersona that)
    {
        super.assign(that);
        setIdDocumento(that.getIdDocumento());
    }
}
