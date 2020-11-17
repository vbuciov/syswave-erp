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
public abstract class CompositeKeyByIdUsuarioOrigen extends CompositeKeyByIdOrigenModulo
{
     private String[] id_usuario;

    //----------------------------------------------------------------------
    public CompositeKeyByIdUsuarioOrigen()
    {
        super();
        id_usuario = new String[Atributo.values().length];
        Entidad.initializeStringPK(id_usuario);
    }

    public CompositeKeyByIdUsuarioOrigen(String llave)
    {
        super(llave);
        id_usuario = new String[Atributo.values().length];
        Entidad.initializeStringPK(id_usuario);
    }

    //---------------------------------------------------------------------
    @Override
    public String getCompositeKey()
    {
        return String.format("%s,%d,%d,%d", getLlave_Viejo(), getId_modulo_Viejo(), getId_origen_dato_Viejo(), getId_usuario_Viejo());
    }

    //----------------------------------------------------------------------
    public String getId_usuario()
    {
        return id_usuario[Atributo.Actual.ordinal()];
    }

    //---------------------------------------------------------------------
    public String getId_usuario(Atributo tipo)
    {
        switch (tipo)
        {
            case Viejo:
                if (isModified())
                    return id_usuario[Atributo.Viejo.ordinal()];
            default:
                return id_usuario[Atributo.Actual.ordinal()];
        }
    }

    //---------------------------------------------------------------------
    public String getId_usuario_Viejo()
    {
        if (isModified())
            return id_usuario[Atributo.Viejo.ordinal()] != EMPTY_STRING
                    ? id_usuario[Atributo.Viejo.ordinal()] : id_usuario[Atributo.Actual.ordinal()];
        else
            return id_usuario[Atributo.Actual.ordinal()];
    }

    //---------------------------------------------------------------------
    public void setId_usuario(String value)
    {
        if (isWaiting())
            id_usuario[Atributo.Viejo.ordinal()] = id_usuario[Atributo.Actual.ordinal()];

        id_usuario[Atributo.Actual.ordinal()] = value;
    }

    //---------------------------------------------------------------------
    protected void assign(CompositeKeyByIdUsuarioOrigen that)
    {
        super.assign(that);
        setId_usuario(that.getId_usuario());
    }   
}
