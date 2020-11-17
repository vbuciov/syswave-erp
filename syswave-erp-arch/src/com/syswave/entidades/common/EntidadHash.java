/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.syswave.entidades.common;

import com.syswave.entidades.keys.PrimaryKeyByLlave;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public abstract class EntidadHash extends PrimaryKeyByLlave
{
    
    private String valor;

    //-------------------------------------------------------------------------
    public EntidadHash()
    {
        super();
        valor = EMPTY_STRING;
    }

    //-------------------------------------------------------------------------
    public EntidadHash(String llave)
    {
        super(llave);
        this.valor = EMPTY_STRING;
    }

    //-------------------------------------------------------------------------
    public EntidadHash(String llave, String valor)
    {
        super(llave);
        this.valor = valor;
    }

    //---------------------------------------------------------------------
    protected void assign(EntidadHash that)
    {
        super.assign(that);
        valor = that.valor;
    }

    //---------------------------------------------------------------------
    /**
     * Obtiene el valor del campo valor
     *
     * @return valor
     */
    public String getValor()
    {
        return valor;
    }

    //---------------------------------------------------------------------
    /**
     * Establece el valor del campo valor
     *
     * @param value valor
     */
    public void setValor(String value)
    {
        this.valor = value;
    }

    //---------------------------------------------------------------------
    @Override
    public void clear()
    {
        super.clear();
        valor = EMPTY_STRING;
    }

    //---------------------------------------------------------------------
    public void copy(EntidadHash that)
    {
        assign(that);
    }
}
