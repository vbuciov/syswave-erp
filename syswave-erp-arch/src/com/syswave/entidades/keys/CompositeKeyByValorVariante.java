/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.syswave.entidades.keys;

import com.syswave.entidades.common.Entidad;
import static com.syswave.entidades.common.Entidad.EMPTY_STRING;

/**
 *
 * @author victor
 */
public abstract class CompositeKeyByValorVariante extends PrimaryKeyByIdVariante
{
           private String[] valor;

    //----------------------------------------------------------------------
    public CompositeKeyByValorVariante()
    {
        super();
        valor = new String[Atributo.values().length];
        Entidad.initializeStringPK(valor);
    }

    //---------------------------------------------------------------------
   public String getCompositeKey()
   {
        return String.format("%s,%d", getValor_Viejo(), getIdVariante_Viejo());
   }
  


    //----------------------------------------------------------------------
    public String getValor()
    {
        return valor[Atributo.Actual.ordinal()];
    }

    //---------------------------------------------------------------------
    public String getValor(Atributo tipo)
    {
        switch (tipo)
        {
            case Viejo:
                if (isModified())
                    return valor[Atributo.Viejo.ordinal()];
            default:
                return valor[Atributo.Actual.ordinal()];
        }
    }

    //---------------------------------------------------------------------
    public String getValor_Viejo()
    {
        if (isModified())
            return valor[Atributo.Viejo.ordinal()] != EMPTY_STRING
                    ? valor[Atributo.Viejo.ordinal()] : valor[Atributo.Actual.ordinal()];
        else
            return valor[Atributo.Actual.ordinal()];
    }

    //---------------------------------------------------------------------
    public void setValor(String value)
    {
        if (isWaiting())
            valor[Atributo.Viejo.ordinal()] = valor[Atributo.Actual.ordinal()];

        valor[Atributo.Actual.ordinal()] = value;
    }

    //---------------------------------------------------------------------
    protected void assign(CompositeKeyByValorVariante that)
    {
        super.assign(that);
        setValor(that.getValor());
    }
}
