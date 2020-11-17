package com.syswave.entidades.keys;

import com.syswave.entidades.common.Entidad;
import static com.syswave.entidades.common.Entidad.EMPTY_INT;

/**
 *
 * @author victor
 */
public abstract class CompositeKeyByIdMantenimientoLinea extends PrimaryKeyByLinea
{

    private int[] idMantenimiento;

    //----------------------------------------------------------------------
    public CompositeKeyByIdMantenimientoLinea()
    {
        super();
        idMantenimiento = new int[Entidad.Atributo.values().length];
        Entidad.initializeIntegerPK(idMantenimiento);
    }

    //---------------------------------------------------------------------
    public String getCompositeKey()
    {
        return String.format("%d,%d", getLinea_Viejo(), getIdMantenimiento_Viejo());
    }

    //----------------------------------------------------------------------
    public int getIdMantenimiento()
    {
        return idMantenimiento[Entidad.Atributo.Actual.ordinal()];
    }

    //---------------------------------------------------------------------
    public int getIdMantenimiento(Entidad.Atributo tipo)
    {
        switch (tipo)
        {
            case Viejo:
                if (isModified())
                    return idMantenimiento[Entidad.Atributo.Viejo.ordinal()];
            default:
                return idMantenimiento[Entidad.Atributo.Actual.ordinal()];
        }
    }

    //---------------------------------------------------------------------
    public int getIdMantenimiento_Viejo()
    {
        if (isModified())
            return idMantenimiento[Entidad.Atributo.Viejo.ordinal()] != EMPTY_INT
                    ? idMantenimiento[Entidad.Atributo.Viejo.ordinal()] : idMantenimiento[Entidad.Atributo.Actual.ordinal()];
        else
            return idMantenimiento[Entidad.Atributo.Actual.ordinal()];
    }

    //---------------------------------------------------------------------
    public void setIdMantenimiento(int value)
    {
        if (isWaiting())
            idMantenimiento[Entidad.Atributo.Viejo.ordinal()] = idMantenimiento[Entidad.Atributo.Actual.ordinal()];

        idMantenimiento[Entidad.Atributo.Actual.ordinal()] = value;
    }

    //---------------------------------------------------------------------
    protected void assign(CompositeKeyByIdMantenimientoLinea that)
    {
        super.assign(that);
        setIdMantenimiento(that.getIdMantenimiento());
    }
}
