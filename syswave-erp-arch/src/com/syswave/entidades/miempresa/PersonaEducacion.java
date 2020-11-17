package com.syswave.entidades.miempresa;

import com.syswave.entidades.keys.CompositeKeyByIdPersonaConsecutivo;
import java.util.Date;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class PersonaEducacion extends CompositeKeyByIdPersonaConsecutivo
{

    private String nombre, titulo;
    private Date fecha_inicio, fecha_fin;
    private boolean cursando;
    private int tipo;

    protected ForeignKey navigation;

    //--------------------------------------------------------------------
    public PersonaEducacion()
    {
        super();
        createAtributes();
        initAtributes();
    }

    //--------------------------------------------------------------------
    public PersonaEducacion(PersonaEducacion that)
    {
        super();
        createAtributes();
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
        titulo = EMPTY_STRING;
        fecha_inicio = EMPTY_DATE;
        fecha_fin = EMPTY_DATE;
        cursando = false;
        tipo = EMPTY_INT;
    }

    //---------------------------------------------------------------------
    private void assign(PersonaEducacion that)
    {
        super.assign(that);
        nombre = that.nombre;
        titulo = that.titulo;
        fecha_inicio = that.fecha_inicio;
        fecha_fin = that.fecha_fin;
        cursando = that.cursando;
        tipo = that.tipo;

        navigation = that.navigation;
    }

    //---------------------------------------------------------------------
    @Override
    public int getIdPersona()
    {
        return null != navigation.getFk_persona_educaciones_id_persona()
                ? navigation.getFk_persona_educaciones_id_persona().getId()
                : super.getIdPersona();
    }
    
        //---------------------------------------------------------------------
    @Override
    public int getIdPersona_Viejo()
    {
        return null != navigation.getFk_persona_educaciones_id_persona()
                ? navigation.getFk_persona_educaciones_id_persona().getId_Viejo()
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
        return null != navigation.getFk_persona_educaciones_id_persona()
                ? navigation.getFk_persona_educaciones_id_persona()
                : navigation.joinPersonas(super.getIdPersona());
    }

    //---------------------------------------------------------------------
    public void setHasOnePersona(Persona value)
    {
        navigation.setFk_persona_educaciones_id_persona(value);
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
    public String getTitulo()
    {
        return titulo;
    }

    //---------------------------------------------------------------------
    public void setTitulo(String value)
    {
        this.titulo = value;
    }

    //---------------------------------------------------------------------
    public Date getFechaInicio()
    {
        return fecha_inicio;
    }

    //---------------------------------------------------------------------
    public void setFechaInicio(Date value)
    {
        this.fecha_inicio = value;
    }

    //---------------------------------------------------------------------
    public Date getFechaFin()
    {
        return fecha_fin;
    }

    //---------------------------------------------------------------------
    public void setFechFin(Date value)
    {
        this.fecha_fin = value;
    }

    //---------------------------------------------------------------------
    public boolean esCursando()
    {
        return cursando;
    }

    //---------------------------------------------------------------------
    public void setCursando(boolean value)
    {
        this.cursando = value;
    }

    //---------------------------------------------------------------------
    public int getTipo()
    {
        return tipo;
    }

    //---------------------------------------------------------------------
    public void setTipo(int es_tipo)
    {
        this.tipo = es_tipo;
    }

    //---------------------------------------------------------------------
    @Override
    public void clear()
    {
        initAtributes();
        navigation.releasePersona();
    }

    //---------------------------------------------------------------------
    public void copy(PersonaEducacion that)
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

        private Persona fk_persona_educaciones_id_persona;

        //---------------------------------------------------------------------
        public Persona getFk_persona_educaciones_id_persona()
        {
            return fk_persona_educaciones_id_persona;
        }

        //---------------------------------------------------------------------
        public void setFk_persona_educaciones_id_persona(Persona value)
        {
            this.fk_persona_educaciones_id_persona = value;
            this.fk_persona_educaciones_id_persona.setDependentOn(false);
        }

        //---------------------------------------------------------------------
        public Persona joinPersonas(int id_persona)
        {
            fk_persona_educaciones_id_persona = new Persona();
            fk_persona_educaciones_id_persona.setId(id_persona);
            return fk_persona_educaciones_id_persona;
        }

        //---------------------------------------------------------------------
        public void releasePersona()
        {
            fk_persona_educaciones_id_persona = null;
        }

        //---------------------------------------------------------------------   
        private void acceptChanges()
        {
            if (null != fk_persona_educaciones_id_persona && fk_persona_educaciones_id_persona.isDependentOn())
                fk_persona_educaciones_id_persona.acceptChanges();
        }
    }
}
