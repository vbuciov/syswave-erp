package com.syswave.entidades.miempresa;

import com.syswave.entidades.configuracion.Localidad;
import com.syswave.entidades.keys.CompositeKeyByIdPersonaConsecutivo;

/**
 * Representa una Dirección de persona.
 *
 * @author Victor Manuel Bucio Vargas
 */
public class PersonaDireccion extends CompositeKeyByIdPersonaConsecutivo
{

    private String codigo_postal, calle, colonia, no_exterior, no_interior;
    private int idLocalidad;

    protected ForeignKey navigation;

    //---------------------------------------------------------------------
    public PersonaDireccion()
    {
        super();
        createAtributes();
        initAtributes();
    }

    //---------------------------------------------------------------------
    public PersonaDireccion(PersonaDireccion that)
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
    private void assign(PersonaDireccion that)
    {
        super.assign(that);

        codigo_postal = that.codigo_postal;
        calle = that.calle;
        colonia = that.colonia;
        no_exterior = that.no_exterior;
        no_interior = that.no_interior;
        idLocalidad = that.idLocalidad;

        navigation = that.navigation;
    }

    //---------------------------------------------------------------------
    private void initAtributes()
    {
        codigo_postal = EMPTY_STRING;
        calle = EMPTY_STRING;
        colonia = EMPTY_STRING;
        no_exterior = EMPTY_STRING;
        no_interior = EMPTY_STRING;
        idLocalidad = EMPTY_INT;
    }

    //---------------------------------------------------------------------
    @Override
    public int getIdPersona()
    {
        return null != navigation.getFk_persona_direccion()
                ? navigation.getFk_persona_direccion().getId()
                : super.getIdPersona();
    }
    
        //---------------------------------------------------------------------
    @Override
    public int getIdPersona_Viejo()
    {
        return null != navigation.getFk_persona_direccion()
                ? navigation.getFk_persona_direccion().getId_Viejo()
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
        return null != navigation.getFk_persona_direccion()
                ? navigation.getFk_persona_direccion()
                : navigation.joinPersonas(super.getIdPersona());
    }

    //---------------------------------------------------------------------
    public void setHasOnePersona(Persona value)
    {
        navigation.setFk_persona_direccion(value);
    }

    //---------------------------------------------------------------------
    public String getCodigoPostal()
    {
        return codigo_postal;
    }

    //---------------------------------------------------------------------
    public void setCodigoPostal(String value)
    {
        this.codigo_postal = value;
    }

    //---------------------------------------------------------------------
    public String getCalle()
    {
        return calle;
    }

    //---------------------------------------------------------------------
    public void setCalle(String value)
    {
        this.calle = value;
    }

    //---------------------------------------------------------------------
    public String getColonia()
    {
        return colonia;
    }

    //---------------------------------------------------------------------
    public void setColonia(String value)
    {
        this.colonia = value;
    }

    //---------------------------------------------------------------------
    public String getNoExterior()
    {
        return no_exterior;
    }

    //---------------------------------------------------------------------
    public void setNoExterior(String value)
    {
        this.no_exterior = value;
    }

    //---------------------------------------------------------------------
    public String getNoInterior()
    {
        return no_interior;
    }

    //---------------------------------------------------------------------
    public void setNoInterior(String value)
    {
        this.no_interior = value;
    }

    //---------------------------------------------------------------------
    public int getIdLocalidad()
    {
        return null != navigation.getFk_direcion_localidad()
                ? navigation.getFk_direcion_localidad().getId()
                : idLocalidad;
    }

    //---------------------------------------------------------------------
    public void setIdLocalidad(int value)
    {
        idLocalidad = value;
        navigation.releaseLocalidad();
    }

    //---------------------------------------------------------------------
    public Localidad getHasOneLocalidad()
    {
        return null != navigation.getFk_direcion_localidad()
                ? navigation.getFk_direcion_localidad()
                : navigation.joinLocalidad(idLocalidad);
    }

    //---------------------------------------------------------------------
    public void setHasOneLocalidad(Localidad value)
    {
        navigation.setFk_direcion_localidad(value);
    }

    //---------------------------------------------------------------------
    @Override
    public void clear()
    {
        initAtributes();
        navigation.releaseLocalidad();
        navigation.releasePersona();
    }

    //---------------------------------------------------------------------
    public void copy(PersonaDireccion that)
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

        protected Persona fk_persona_direccion;
        protected Localidad fk_direcion_localidad;

        //---------------------------------------------------------------------
        public Persona getFk_persona_direccion()
        {
            return fk_persona_direccion;
        }

        //---------------------------------------------------------------------
        public void setFk_persona_direccion(Persona value)
        {
            this.fk_persona_direccion = value;
            this.fk_persona_direccion.setDependentOn(false);
        }

        //---------------------------------------------------------------------
        public Localidad getFk_direcion_localidad()
        {
            return fk_direcion_localidad;
        }

        //---------------------------------------------------------------------
        public void setFk_direcion_localidad(Localidad value)
        {
            this.fk_direcion_localidad = value;
            this.fk_direcion_localidad.setDependentOn(false);
        }

        //---------------------------------------------------------------------
        public Persona joinPersonas(int id_persona)
        {
            fk_persona_direccion = new Persona();
            fk_persona_direccion.setId(id_persona);
            return fk_persona_direccion;
        }

        //---------------------------------------------------------------------
        public void releasePersona()
        {
            fk_persona_direccion = null;
        }

        //---------------------------------------------------------------------
        public Localidad joinLocalidad(int id_localidad)
        {
            fk_direcion_localidad = new Localidad();
            fk_direcion_localidad.setId(id_localidad);
            return fk_direcion_localidad;
        }

        //---------------------------------------------------------------------
        public void releaseLocalidad()
        {
            fk_direcion_localidad = null;
        }

        //---------------------------------------------------------------------   
        private void acceptChanges()
        {
            if (null != fk_persona_direccion && fk_persona_direccion.isDependentOn())
                fk_persona_direccion.acceptChanges();

            if (null != fk_direcion_localidad && fk_direcion_localidad.isDependentOn())
                fk_direcion_localidad.acceptChanges();
        }
    }
}
