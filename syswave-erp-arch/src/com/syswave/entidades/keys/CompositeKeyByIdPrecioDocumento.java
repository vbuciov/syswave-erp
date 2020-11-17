package com.syswave.entidades.keys;

import com.syswave.entidades.common.Entidad;
import static com.syswave.entidades.common.Entidad.EMPTY_INT;

/**
 *
 * @author victor
 */
public abstract class CompositeKeyByIdPrecioDocumento extends CompositeKeyByIdDocumentoConsecutivo
{

    private int[] idPrecio;

    //----------------------------------------------------------------------
    public CompositeKeyByIdPrecioDocumento()
    {
        super();
        idPrecio = new int[Entidad.Atributo.values().length];
        Entidad.initializeIntegerPK(idPrecio);
    }

    //---------------------------------------------------------------------
    @Override
    public String getCompositeKey()
    {
        return String.format("%d,%d,%d", getConsecutivo_Viejo(), getIdDocumento_Viejo(), getIdPrecio_Viejo());
    }

    //----------------------------------------------------------------------
    public int getIdPrecio()
    {
        return idPrecio[Entidad.Atributo.Actual.ordinal()];
    }

    //---------------------------------------------------------------------
    public int getIdPrecio(Entidad.Atributo tipo)
    {
        switch (tipo)
        {
            case Viejo:
                if (isModified())
                    return idPrecio[Entidad.Atributo.Viejo.ordinal()];
            default:
                return idPrecio[Entidad.Atributo.Actual.ordinal()];
        }
    }

    //---------------------------------------------------------------------
    public int getIdPrecio_Viejo()
    {
        if (isModified())
            return idPrecio[Entidad.Atributo.Viejo.ordinal()] != EMPTY_INT
                    ? idPrecio[Entidad.Atributo.Viejo.ordinal()] : idPrecio[Entidad.Atributo.Actual.ordinal()];
        else
            return idPrecio[Entidad.Atributo.Actual.ordinal()];
    }

    //---------------------------------------------------------------------
    public void setIdPrecio(int value)
    {
        if (isWaiting())
            idPrecio[Entidad.Atributo.Viejo.ordinal()] = idPrecio[Entidad.Atributo.Actual.ordinal()];

        idPrecio[Entidad.Atributo.Actual.ordinal()] = value;
    }

    //---------------------------------------------------------------------
    protected void assign(CompositeKeyByIdPrecioDocumento that)
    {
        super.assign(that);
        setIdPrecio(that.getIdPrecio());
    }
}
