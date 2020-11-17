package com.syswave.entidades.miempresa;

import com.syswave.entidades.keys.CompositeKeyByIdPersonaLinea;

/**
 * Representa una cantidad de presentaciones asignadas a una persona.
 *
 * @author Victor Manuel Bucio Vargas
 */
public class Persona_tiene_Existencia extends CompositeKeyByIdPersonaLinea
{

    private float existencia;
    private int idUbicacion, entrada;

    protected ForeignKey navigation;

    //---------------------------------------------------------------------
    public Persona_tiene_Existencia()
    {
        super();
        createAtributes();
        initAtributes();
    }

    //---------------------------------------------------------------------
    public Persona_tiene_Existencia(Persona_tiene_Existencia that)
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
    private void assign(Persona_tiene_Existencia that)
    {
        super.assign(that);
        existencia = that.existencia;
        idUbicacion = that.idUbicacion;
        entrada = that.entrada;
        navigation = that.navigation;
    }

    //---------------------------------------------------------------------
    private void initAtributes()
    {
        existencia = EMPTY_FLOAT;
        idUbicacion = EMPTY_INT;
        entrada = EMPTY_INT;
    }

    //---------------------------------------------------------------------
    @Override
    public int getIdPersona()
    {
        return null != navigation.getFk_persona_existencia_id_persona()
                ? navigation.getFk_persona_existencia_id_persona().getId()
                : super.getIdPersona();
    }
    
       //---------------------------------------------------------------------
    @Override
    public int getIdPersona_Viejo()
    {
        return null != navigation.getFk_persona_existencia_id_persona()
                ? navigation.getFk_persona_existencia_id_persona().getId_Viejo()
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
        return null != navigation.getFk_persona_existencia_id_persona()
                ? navigation.getFk_persona_existencia_id_persona()
                : navigation.joinPersonas(super.getIdPersona());
    }

    //---------------------------------------------------------------------
    public void setHasOnePersona(Persona value)
    {
        navigation.setFk_persona_existencia_id_persona(value);
    }

    //---------------------------------------------------------------------
    public String getIdSerie()
    {
        return null != navigation.getFk_persona_existencia_id_serie()
                ? navigation.getFk_persona_existencia_id_serie().getCompositeKey()
                : String.format("%d,%d", entrada, idUbicacion);
    }

    //---------------------------------------------------------------------
    public int getEntrada()
    {
        return null != navigation.getFk_persona_existencia_id_serie()
                ? navigation.getFk_persona_existencia_id_serie().getEntrada()
                : entrada;
    }

    //---------------------------------------------------------------------
    public void setEntrada(int value)
    {
        entrada = value;
        navigation.releaseControlAlmacen();
    }

    //---------------------------------------------------------------------
    public int getIdUbicacion()
    {
        return null != navigation.getFk_persona_existencia_id_serie()
                ? navigation.getFk_persona_existencia_id_serie().getIdUbicacion()
                : idUbicacion;
    }

    //---------------------------------------------------------------------
    public void setIdUbicacion(int value)
    {
        idUbicacion = value;
        navigation.releaseControlAlmacen();
    }

    //---------------------------------------------------------------------
    public ControlAlmacen getHasOneControlAlmacen()
    {
        return null != navigation.getFk_persona_existencia_id_serie()
                ? navigation.getFk_persona_existencia_id_serie()
                : navigation.joinControlAlmacen(idUbicacion, entrada);
    }

    //---------------------------------------------------------------------
    public void setHasOneControlAlmacen(ControlAlmacen value)
    {
        navigation.setFk_persona_existencia_id_serie(value);
    }

    //---------------------------------------------------------------------
    public float getExistencia()
    {
        return existencia;
    }

    //---------------------------------------------------------------------
    public void setExistencia(float existencia)
    {
        this.existencia = existencia;
    }

    //---------------------------------------------------------------------
    @Override
    public void clear()
    {
        initAtributes();
        navigation.releaseControlAlmacen();
        navigation.releasePersona();
    }

    //---------------------------------------------------------------------
    public void copy(Persona_tiene_Existencia that)
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

        private Persona fk_persona_existencia_id_persona;
        private ControlAlmacen fk_persona_existencia_id_serie;

        //---------------------------------------------------------------------
        public Persona getFk_persona_existencia_id_persona()
        {
            return fk_persona_existencia_id_persona;
        }

        //---------------------------------------------------------------------
        public void setFk_persona_existencia_id_persona(Persona value)
        {
            this.fk_persona_existencia_id_persona = value;
            this.fk_persona_existencia_id_persona.setDependentOn(false);
        }

        //---------------------------------------------------------------------
        public Persona joinPersonas(int id_persona)
        {
            fk_persona_existencia_id_persona = new Persona();
            fk_persona_existencia_id_persona.setId(id_persona);
            return fk_persona_existencia_id_persona;
        }

        //---------------------------------------------------------------------
        public void releasePersona()
        {
            fk_persona_existencia_id_persona = null;
        }

        //---------------------------------------------------------------------
        public ControlAlmacen getFk_persona_existencia_id_serie()
        {
            return fk_persona_existencia_id_serie;
        }

        //---------------------------------------------------------------------
        public void setFk_persona_existencia_id_serie(ControlAlmacen value)
        {
            this.fk_persona_existencia_id_serie = value;
            this.fk_persona_existencia_id_serie.setDependentOn(false);
        }

        //---------------------------------------------------------------------
        public ControlAlmacen joinControlAlmacen(int id_ubicacion, int entrada)
        {
            fk_persona_existencia_id_serie = new ControlAlmacen();
            fk_persona_existencia_id_serie.setIdUbicacion(id_ubicacion);
            fk_persona_existencia_id_serie.setEntrada(entrada);
            return fk_persona_existencia_id_serie;
        }

        //---------------------------------------------------------------------
        public void releaseControlAlmacen()
        {
            fk_persona_existencia_id_serie = null;
        }

        //---------------------------------------------------------------------   
        private void acceptChanges()
        {
            if (null != fk_persona_existencia_id_persona && fk_persona_existencia_id_persona.isDependentOn())
                fk_persona_existencia_id_persona.acceptChanges();

            if (null != fk_persona_existencia_id_serie && fk_persona_existencia_id_serie.isDependentOn())
                fk_persona_existencia_id_serie.acceptChanges();
        }
    }
}
