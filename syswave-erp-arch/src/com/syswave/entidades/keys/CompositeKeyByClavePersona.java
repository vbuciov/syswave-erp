/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.syswave.entidades.keys;

import com.syswave.entidades.common.Entidad;

/**
 *
 * @author victor
 */
public abstract class CompositeKeyByClavePersona extends PrimaryKeyByIdPersona
{
       private String[] clave;

    //----------------------------------------------------------------------
    public CompositeKeyByClavePersona()
    {
        super();
        clave = new String[Atributo.values().length];
        Entidad.initializeStringPK(clave);
    }

    //---------------------------------------------------------------------
   public String getCompositeKey()
   {
        return String.format("%s,%d", getClave_Viejo(), getIdPersona_Viejo());
   }
  


    //----------------------------------------------------------------------
    public String getClave()
    {
        return clave[Atributo.Actual.ordinal()];
    }

    //---------------------------------------------------------------------
    public String getClave(Atributo tipo)
    {
        switch (tipo)
        {
            case Viejo:
                if (isModified())
                    return clave[Atributo.Viejo.ordinal()];
            default:
                return clave[Atributo.Actual.ordinal()];
        }
    }

    //---------------------------------------------------------------------
    public String getClave_Viejo()
    {
        if (isModified())
            return clave[Atributo.Viejo.ordinal()] != EMPTY_STRING
                    ? clave[Atributo.Viejo.ordinal()] : clave[Atributo.Actual.ordinal()];
        else
            return clave[Atributo.Actual.ordinal()];
    }

    //---------------------------------------------------------------------
    public void setClave(String value)
    {
        if (isWaiting())
            clave[Atributo.Viejo.ordinal()] = clave[Atributo.Actual.ordinal()];

        clave[Atributo.Actual.ordinal()] = value;
    }

    //---------------------------------------------------------------------
    protected void assign(CompositeKeyByClavePersona that)
    {
        super.assign(that);
        setClave(that.getClave());
    } 
}
