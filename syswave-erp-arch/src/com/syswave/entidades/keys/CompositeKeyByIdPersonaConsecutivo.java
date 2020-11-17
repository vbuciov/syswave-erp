package com.syswave.entidades.keys;

import com.syswave.entidades.common.Entidad;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public abstract class CompositeKeyByIdPersonaConsecutivo extends PrimaryKeyByConsecutivo
{
    private int[] idPersona;
    
    //----------------------------------------------------------------------
    public CompositeKeyByIdPersonaConsecutivo()
    {
        super();
        idPersona = new int[Atributo.values().length];
        Entidad.initializeIntegerPK(idPersona);
    }
    
   //---------------------------------------------------------------------
   public String getCompositeKey ()
   {
      return String.format("%d,%d", getConsecutivo_Viejo(), getIdPersona_Viejo());
   }

    //----------------------------------------------------------------------
    public int getIdPersona()
    {
        return idPersona[Atributo.Actual.ordinal()];
    }

    //---------------------------------------------------------------------
    public int getIdPersona(Atributo tipo)
    {
        switch (tipo)
        {
            case Viejo:
                if (isModified())
                    return idPersona[Atributo.Viejo.ordinal()];
            default:
                return idPersona[Atributo.Actual.ordinal()];
        }
    }

    //---------------------------------------------------------------------
    public int getIdPersona_Viejo()
    {
        if (isModified())
            return idPersona[Atributo.Viejo.ordinal()] != EMPTY_INT
                    ? idPersona[Atributo.Viejo.ordinal()] : idPersona[Atributo.Actual.ordinal()];
        else
            return idPersona[Atributo.Actual.ordinal()];
    }

    //---------------------------------------------------------------------
    public void setIdPersona(int value)
    {
        if (isWaiting())
            idPersona[Atributo.Viejo.ordinal()] = idPersona[Atributo.Actual.ordinal()];

        idPersona[Atributo.Actual.ordinal()] = value;
    }

    //---------------------------------------------------------------------
    protected void assign(CompositeKeyByIdPersonaConsecutivo that)
    {
        super.assign(that);
        setIdPersona(that.getIdPersona());
    }
}
