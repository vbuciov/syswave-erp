package com.syswave.entidades.miempresa;

import com.syswave.entidades.keys.CompositeKeyByIdPersonaConsecutivo;

/**
 * Intereses, Habilidades, Aficiones, Enfermedades, Alergias.
 *
 * @author Victor Manuel Bucio Vargas
 */
public class PersonaAtributo extends CompositeKeyByIdPersonaConsecutivo
{

    private String nombre, tratamiento, descripcion;
    private int es_tipo;

    protected ForeignKey navigation;

    //---------------------------------------------------------------------
    public PersonaAtributo()
    {
        super();
        createAtributes();
        initAtributes();
    }

    //---------------------------------------------------------------------
    public PersonaAtributo(PersonaAtributo that)
    {
        super();
        createAtributes();
        assign(that);
    }

    //--------------------------------------------------------------------
    private void createAtributes()
    {
        navigation = new ForeignKey();
    }

    //--------------------------------------------------------------------
    private void initAtributes()
    {
        nombre = EMPTY_STRING;
        es_tipo = EMPTY_INT;
        tratamiento = EMPTY_STRING;
        descripcion = EMPTY_STRING;
    }

    //---------------------------------------------------------------------
    private void assign(PersonaAtributo that)
    {
        super.assign(that);
        nombre = that.nombre;
        es_tipo = that.es_tipo;
        tratamiento = that.tratamiento;
        descripcion = that.descripcion;

        navigation = that.navigation;
    }

    //---------------------------------------------------------------------
    @Override
    public int getIdPersona()
    {
        return null != navigation.getFk_persona_atributos_id_persona()
                ? navigation.getFk_persona_atributos_id_persona().getId()
                : super.getIdPersona();
    }
    
        //---------------------------------------------------------------------
    @Override
    public int getIdPersona_Viejo()
    {
        return null != navigation.getFk_persona_atributos_id_persona()
                ? navigation.getFk_persona_atributos_id_persona().getId_Viejo()
                : super.getIdPersona_Viejo();
    }

    //---------------------------------------------------------------------
    @Override
    public void setIdPersona(int value)
    {
        super.setIdPersona(value);
        navigation.releasePersona();
    }

    //---------------------------------------------------------------------
    public Persona getHasOnePersona()
    {
        return null != navigation.getFk_persona_atributos_id_persona()
                ? navigation.getFk_persona_atributos_id_persona()
                : navigation.joinPersona(super.getIdPersona());
    }

    //---------------------------------------------------------------------
    public void setHasOnePersona(Persona value)
    {
        navigation.setFk_persona_atributos_id_persona(value);
    }

    //---------------------------------------------------------------------
    public String getNombre()
    {
        return nombre;
    }

    //---------------------------------------------------------------------
    public void setNombre(String value)
    {
        this.nombre = value;
    }

    //---------------------------------------------------------------------
    public int getEsTipo()
    {
        return es_tipo;
    }

    //---------------------------------------------------------------------
    public void setEsTipo(int value)
    {
        this.es_tipo = value;
    }

    //---------------------------------------------------------------------
    public String getTratamiento()
    {
        return tratamiento;
    }

    //---------------------------------------------------------------------
    public void setTratamiento(String value)
    {
        this.tratamiento = value;
    }

    //---------------------------------------------------------------------
    public String getDescripcion()
    {
        return descripcion;
    }

    //---------------------------------------------------------------------
    public void setDescripcion(String value)
    {
        this.descripcion = value;
    }

    //---------------------------------------------------------------------
    @Override
    public void clear()
    {
        initAtributes();
        navigation.releasePersona();
    }

    //---------------------------------------------------------------------
    public void copy(PersonaAtributo that)
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

        private Persona fk_persona_atributos_id_persona;

        //---------------------------------------------------------------------
        public Persona getFk_persona_atributos_id_persona()
        {
            return fk_persona_atributos_id_persona;
        }

        //---------------------------------------------------------------------
        public void setFk_persona_atributos_id_persona(Persona value)
        {
            this.fk_persona_atributos_id_persona = value;
            this.fk_persona_atributos_id_persona.setDependentOn(false);
        }

        //---------------------------------------------------------------------
        public Persona joinPersona(int id_persona)
        {
            fk_persona_atributos_id_persona = new Persona();
            fk_persona_atributos_id_persona.setId(id_persona);
            return fk_persona_atributos_id_persona;
        }

        //---------------------------------------------------------------------
        public void releasePersona()
        {
            fk_persona_atributos_id_persona = null;
        }

        //---------------------------------------------------------------------   
        private void acceptChanges()
        {
            if (null != fk_persona_atributos_id_persona && fk_persona_atributos_id_persona.isDependentOn())
                fk_persona_atributos_id_persona.acceptChanges();
        }
    }
}
