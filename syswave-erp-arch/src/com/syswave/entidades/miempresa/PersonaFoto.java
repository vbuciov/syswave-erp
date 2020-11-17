package com.syswave.entidades.miempresa;

import com.syswave.entidades.keys.CompositeKeyByIdPersonaConsecutivo;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class PersonaFoto extends CompositeKeyByIdPersonaConsecutivo
{

    private int acho, alto, longitud;
    private String formato, observacion;
    private byte[] imagen;
    private byte[] miniatura;

    protected ForeignKey navigation;

    //--------------------------------------------------------------------
    public PersonaFoto()
    {
        super();
        createAtributes();
        initAtributes();
    }

    //--------------------------------------------------------------------
    public PersonaFoto(PersonaFoto that)
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
        acho = EMPTY_INT;
        alto = EMPTY_INT;
        longitud = EMPTY_INT;
        formato = EMPTY_STRING;
        observacion = EMPTY_STRING;
        imagen = null;
        miniatura = null;
    }

    //---------------------------------------------------------------------
    private void assign(PersonaFoto that)
    {
        super.assign(that);
        acho = that.acho;
        alto = that.alto;
        longitud = that.longitud;
        formato = that.formato;
        observacion = that.observacion;
        imagen = that.imagen;
        miniatura = that.miniatura;
        navigation = that.navigation;
    }

    //---------------------------------------------------------------------
    @Override
    public int getIdPersona()
    {
        return null != navigation.getFk_persona_fotos_id_persona()
                ? navigation.getFk_persona_fotos_id_persona().getId()
                : super.getIdPersona();
    }

    //---------------------------------------------------------------------
    @Override
    public int getIdPersona_Viejo()
    {
        return null != navigation.getFk_persona_fotos_id_persona()
                ? navigation.getFk_persona_fotos_id_persona().getId_Viejo()
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
        return null != navigation.getFk_persona_fotos_id_persona()
                ? navigation.getFk_persona_fotos_id_persona()
                : navigation.joinPersonas(super.getIdPersona());
    }

    //---------------------------------------------------------------------
    public void setHasOnePersona(Persona value)
    {
        navigation.setFk_persona_fotos_id_persona(value);
    }

    //---------------------------------------------------------------------
    public int getAcho()
    {
        return acho;
    }

    //---------------------------------------------------------------------
    public void setAcho(int value)
    {
        this.acho = value;
    }

    //---------------------------------------------------------------------
    public int getAlto()
    {
        return alto;
    }

    //---------------------------------------------------------------------
    public void setAlto(int value)
    {
        this.alto = value;
    }

    //---------------------------------------------------------------------
    public int getLongitud()
    {
        return longitud;
    }

    //---------------------------------------------------------------------
    public void setLongitud(int value)
    {
        this.longitud = value;
    }

    //---------------------------------------------------------------------
    public String getFormato()
    {
        return formato;
    }

    //---------------------------------------------------------------------
    public void setFormato(String value)
    {
        this.formato = value;
    }

    //---------------------------------------------------------------------
    public String getObservacion()
    {
        return observacion;
    }

    //---------------------------------------------------------------------
    public void setObservacion(String value)
    {
        this.observacion = value;
    }

    //---------------------------------------------------------------------
    public byte[] getImagen()
    {
        return imagen;
    }

    //---------------------------------------------------------------------
    public void setImagen(byte[] value)
    {
        this.imagen = value;
    }

    //---------------------------------------------------------------------
    public byte[] getMiniatura()
    {
        return miniatura;
    }

    //---------------------------------------------------------------------
    public void setMiniatura(byte[] value)
    {
        this.miniatura = value;
    }

    //--------------------------------------------------------------------
    @Override
    public void clear()
    {
        initAtributes();
    }

    //--------------------------------------------------------------------
    public void copy(PersonaFoto that)
    {
        assign(that);
    }

    @Override
    public void acceptChanges()
    {
        super.acceptChanges();
        navigation.acceptChanges();
    }

    //---------------------------------------------------------------------
    protected class ForeignKey
    {

        private Persona fk_persona_fotos_id_persona;

        //---------------------------------------------------------------------
        public Persona getFk_persona_fotos_id_persona()
        {
            return fk_persona_fotos_id_persona;
        }

        //---------------------------------------------------------------------
        public void setFk_persona_fotos_id_persona(Persona value)
        {
            this.fk_persona_fotos_id_persona = value;
            this.fk_persona_fotos_id_persona.setDependentOn(false);
        }

        //---------------------------------------------------------------------
        public Persona joinPersonas(int id_persona)
        {
            fk_persona_fotos_id_persona = new Persona();
            fk_persona_fotos_id_persona.setId(id_persona);
            return fk_persona_fotos_id_persona;
        }

        //---------------------------------------------------------------------
        public void releasePersona()
        {
            fk_persona_fotos_id_persona = null;
        }

        //---------------------------------------------------------------------   
        private void acceptChanges()
        {
            if (null != fk_persona_fotos_id_persona && fk_persona_fotos_id_persona.isDependentOn())
                fk_persona_fotos_id_persona.acceptChanges();
        }
    }
}
