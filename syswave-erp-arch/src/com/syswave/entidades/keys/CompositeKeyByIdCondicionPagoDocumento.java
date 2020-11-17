/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.syswave.entidades.keys;

import com.syswave.entidades.common.Entidad;
import static com.syswave.entidades.common.Entidad.EMPTY_INT;

/**
 *
 * @author victor
 */
public abstract class CompositeKeyByIdCondicionPagoDocumento extends PrimaryKeyByIdDocumento
{
    private int[] idCondicionPago;
    
    //----------------------------------------------------------------------
    public CompositeKeyByIdCondicionPagoDocumento()
    {
        super();
        idCondicionPago = new int[Entidad.Atributo.values().length];
        Entidad.initializeIntegerPK(idCondicionPago);
    }
    
   //---------------------------------------------------------------------
   public String getCompositeKey ()
   {
      return String.format("%d,%d",getIdDocumento_Viejo(), getIdCondicionPago_Viejo());
   }

    //----------------------------------------------------------------------
    public int getIdCondicionPago()
    {
        return idCondicionPago[Entidad.Atributo.Actual.ordinal()];
    }

    //---------------------------------------------------------------------
    public int getIdCondicionPago(Entidad.Atributo tipo)
    {
        switch (tipo)
        {
            case Viejo:
                if (isModified())
                    return idCondicionPago[Entidad.Atributo.Viejo.ordinal()];
            default:
                return idCondicionPago[Entidad.Atributo.Actual.ordinal()];
        }
    }

    //---------------------------------------------------------------------
    public int getIdCondicionPago_Viejo()
    {
        if (isModified())
            return idCondicionPago[Entidad.Atributo.Viejo.ordinal()] != EMPTY_INT
                    ? idCondicionPago[Entidad.Atributo.Viejo.ordinal()] : idCondicionPago[Entidad.Atributo.Actual.ordinal()];
        else
            return idCondicionPago[Entidad.Atributo.Actual.ordinal()];
    }

    //---------------------------------------------------------------------
    public void setIdCondicionPago(int value)
    {
        if (isWaiting())
            idCondicionPago[Entidad.Atributo.Viejo.ordinal()] = idCondicionPago[Entidad.Atributo.Actual.ordinal()];

        idCondicionPago[Entidad.Atributo.Actual.ordinal()] = value;
    }

    //---------------------------------------------------------------------
    protected void assign(CompositeKeyByIdCondicionPagoDocumento that)
    {
        super.assign(that);
        setIdCondicionPago(that.getIdCondicionPago());
    }
}
