package com.syswave.entidades.keys;

import com.syswave.entidades.common.Entidad;

/**
 *
 * @author victor
 */
public abstract class CompositeKeyByIdDocumentoConsecutivo extends PrimaryKeyByConsecutivo {
        private int[] idDocumento;
    
    //----------------------------------------------------------------------
    public CompositeKeyByIdDocumentoConsecutivo()
    {
        super();
        idDocumento = new int[Entidad.Atributo.values().length];
        Entidad.initializeIntegerPK(idDocumento);
    }
    
     //---------------------------------------------------------------------
   public String getCompositeKey ()
   {
      return String.format("%d,%d", getConsecutivo_Viejo(), getIdDocumento_Viejo());
   }


    //----------------------------------------------------------------------
    public int getIdDocumento()
    {
        return idDocumento[Entidad.Atributo.Actual.ordinal()];
    }

    //---------------------------------------------------------------------
    public int getIdDocumento(Entidad.Atributo tipo)
    {
        switch (tipo)
        {
            case Viejo:
                if (isModified())
                    return idDocumento[Entidad.Atributo.Viejo.ordinal()];
            default:
                return idDocumento[Entidad.Atributo.Actual.ordinal()];
        }
    }

    //---------------------------------------------------------------------
    public int getIdDocumento_Viejo()
    {
        if (isModified())
            return idDocumento[Entidad.Atributo.Viejo.ordinal()] != EMPTY_INT
                    ? idDocumento[Entidad.Atributo.Viejo.ordinal()] : idDocumento[Entidad.Atributo.Actual.ordinal()];
        else
            return idDocumento[Entidad.Atributo.Actual.ordinal()];
    }

    //---------------------------------------------------------------------
    public void setIdDocumento(int value)
    {
        if (isWaiting())
            idDocumento[Entidad.Atributo.Viejo.ordinal()] = idDocumento[Entidad.Atributo.Actual.ordinal()];

        idDocumento[Entidad.Atributo.Actual.ordinal()] = value;
    }

    //---------------------------------------------------------------------
    protected void assign(CompositeKeyByIdDocumentoConsecutivo that)
    {
        super.assign(that);
        setIdDocumento(that.getIdDocumento());
    }
    
}
