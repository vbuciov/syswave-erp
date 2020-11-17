package com.syswave.entidades.miempresa_vista;

import com.syswave.entidades.miempresa.PersonaIdentificador;
import com.syswave.entidades.miempresa.Valor;

/**
 * Estructura de Join entre PersonaIdentificador y TipoIdentificador, expande:
 * LLave, Descripción, esActivo.
 *
 * @author Victor Manuel Bucio Vargas
 */
public class PersonaIdentificadorVista extends PersonaIdentificador
{

    protected Valor fk_identificador_tipo;
    private boolean activo;

    public PersonaIdentificadorVista()
    {
        super();
        fk_identificador_tipo = super.getHasOneTipoIdentificador();
        activo = true;
    }

    //---------------------------------------------------------------------
    public String getLlave()
    {
        return fk_identificador_tipo.getValor();
    }

    //---------------------------------------------------------------------
    public void setLlave(String value)
    {
        fk_identificador_tipo.setValor(value);
    }

    //---------------------------------------------------------------------
    public String getDescripcion()
    {
        return fk_identificador_tipo.getDescripcion();
    }

    //---------------------------------------------------------------------
    public void setDescripcion(String value)
    {
        fk_identificador_tipo.setDescripcion(value);
    }

    //---------------------------------------------------------------------
    public boolean esActivo()
    {
        return activo;
    }

    //---------------------------------------------------------------------
    public void setEsActivo(boolean value)
    {
        activo = value;
    }
}
