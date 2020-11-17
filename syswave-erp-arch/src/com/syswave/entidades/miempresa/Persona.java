package com.syswave.entidades.miempresa;

import com.syswave.entidades.keys.PrimaryKeyById;
import java.io.Serializable;
import java.util.Date;

/**
 * Indica el tipo de persona.
 *
 * @author Victor Manuel Bucio Vargas
 * @version 1 Marzo 2014
 */
public class Persona extends PrimaryKeyById implements Serializable
{

    private String nombres, apellidos, observaciones;
    private Date nacimiento;
    private Boolean activo;
    private int id_tipo_persona;

    protected ForeignKey navigation;

    //---------------------------------------------------------------------
    public Persona()
    {
        super();
        createAtributes();
        initAtributes();
    }

    //---------------------------------------------------------------------
    public Persona(Persona that)
    {
        super();
        createAtributes();
        assign(that);
    }

    //---------------------------------------------------------------------
    private void createAtributes()
    {
        navigation = new ForeignKey();
    }

    //---------------------------------------------------------------------
    private void initAtributes()
    {
        nombres = EMPTY_STRING;
        apellidos = EMPTY_STRING;
        nacimiento = new Date(EMPTY_DATE.getTime());
        id_tipo_persona = EMPTY_INT;
        activo = true;
    }

    //---------------------------------------------------------------------
    private void assign(Persona that)
    {
        super.assign(that);

        nombres = that.nombres;
        apellidos = that.apellidos;
        nacimiento = that.nacimiento;
        activo = that.activo;
        id_tipo_persona = that.id_tipo_persona;

        navigation = that.navigation;
    }

    //---------------------------------------------------------------------
    public String getNombres()
    {
        return nombres;
    }

    //---------------------------------------------------------------------
    public void setNombres(String nombres)
    {
        this.nombres = nombres;
    }

    //---------------------------------------------------------------------
    public String getApellidos()
    {
        return apellidos;
    }

    //---------------------------------------------------------------------
    public void setApellidos(String apellidos)
    {
        this.apellidos = apellidos;
    }

    //---------------------------------------------------------------------
    public Date getNacimiento()
    {
        return nacimiento;
    }

    //---------------------------------------------------------------------
    public void setNacimiento(Date nacimiento)
    {
        if (nacimiento != null)
            this.nacimiento = nacimiento;
    }

    //---------------------------------------------------------------------
    public boolean esActivo()
    {
        return activo;
    }

    //---------------------------------------------------------------------
    public void setActivo(Boolean activo)
    {
        this.activo = activo;
    }

    //---------------------------------------------------------------------
    public Integer getId_tipo_persona()
    {
        return null != navigation.getFk_persona_tipo()
                ? navigation.getFk_persona_tipo().getId()
                : id_tipo_persona;
    }

    //---------------------------------------------------------------------
    public void setId_tipo_pesrona(Integer value)
    {
        id_tipo_persona = value;
        navigation.releaseTipoPersonas();
    }

    //---------------------------------------------------------------------
    public TipoPersona getHasOneTipoPersona()
    {
        return null != navigation.getFk_persona_tipo()
                ? navigation.getFk_persona_tipo()
                : navigation.joinTipoPersonas(id_tipo_persona);
    }

    //---------------------------------------------------------------------
    public void setFk_persona_tipo(TipoPersona value)
    {
        navigation.setFk_persona_tipo(value);
    }

    //---------------------------------------------------------------------
    public String getNombreCompleto()
    {
        return String.format("%s %s", nombres, apellidos);
    }

    //---------------------------------------------------------------------
    public String getObservaciones()
    {
        return observaciones;
    }

    //---------------------------------------------------------------------
    public void setObservaciones(String observaciones)
    {
        this.observaciones = observaciones;
    }

    //---------------------------------------------------------------------
    @Override
    public void clear()
    {
        initAtributes();
        navigation.releaseTipoPersonas();
    }

    //---------------------------------------------------------------------
    public void copy(Persona that)
    {
        assign(that);
    }

    //---------------------------------------------------------------------
    @Override
    public void acceptChanges()
    {
        super.acceptChanges();
        navigation.acceptChanges();
    }

    //---------------------------------------------------------------------
    protected class ForeignKey
    {

        protected TipoPersona fk_persona_tipo;

        //---------------------------------------------------------------------
        public TipoPersona getFk_persona_tipo()
        {
            return fk_persona_tipo;
        }

        //---------------------------------------------------------------------
        public void setFk_persona_tipo(TipoPersona value)
        {
            this.fk_persona_tipo = value;
            this.fk_persona_tipo.setDependentOn(false);
        }

        //---------------------------------------------------------------------
        public TipoPersona joinTipoPersonas(int id_tipo_persona)
        {
            fk_persona_tipo = new TipoPersona();
            fk_persona_tipo.setId(id_tipo_persona);
            return fk_persona_tipo;
        }

        //---------------------------------------------------------------------
        public void releaseTipoPersonas()
        {
            fk_persona_tipo = null;
        }

        //---------------------------------------------------------------------   
        private void acceptChanges()
        {
            if (null != fk_persona_tipo && fk_persona_tipo.isDependentOn())
                fk_persona_tipo.acceptChanges();
        }
    }
}
