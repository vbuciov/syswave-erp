package com.syswave.entidades.miempresa;

import com.syswave.entidades.keys.PrimaryKeyById;
import java.util.Date;

/**
 * TODO: Utilizar una estrategia de llave compuesta
 * @author Carlos Soto, Victor Manuel Bucio Vargas
 */
public class PersonaTieneIncidencia extends PrimaryKeyById
{

    private Date fecha;
    private Date hora;
    private String observaciones;
    private int idPersona;
    private int tipo_incidencia;

    protected ForeignKey navigation;

    //-------------------------------------------------------------------------
    public PersonaTieneIncidencia()
    {
        super();
        navigation = new ForeignKey();
        initAtributes();
    }

    //-------------------------------------------------------------------------
    public PersonaTieneIncidencia(PersonaTieneIncidencia that)
    {
        super();
        navigation = new ForeignKey();
        assign(that);
    }

    //-------------------------------------------------------------------------
    private void initAtributes()
    {
        this.fecha = EMPTY_DATE;
        this.hora = EMPTY_DATE;
        this.observaciones = EMPTY_STRING;
        this.idPersona = EMPTY_INT;
        this.tipo_incidencia = EMPTY_INT;
    }

    //-------------------------------------------------------------------------
    private void assign(PersonaTieneIncidencia that)
    {
        this.fecha = that.fecha;
        this.hora = that.hora;
        this.idPersona = that.idPersona;
        this.tipo_incidencia = that.tipo_incidencia;
        this.observaciones = that.observaciones;

        navigation = that.navigation;
    }

    //-------------------------------------------------------------------------
    @Override
    public void clear()
    {
        initAtributes();
    }

    //-------------------------------------------------------------------------
    public Date getFecha()
    {
        return fecha;
    }

    //-------------------------------------------------------------------------
    public void setFecha(Date fecha)
    {
        this.fecha = fecha;
    }

    //-------------------------------------------------------------------------
    public Date getHora()
    {
        return hora;
    }

    //-------------------------------------------------------------------------
    public void setHora(Date hora)
    {
        this.hora = hora;
    }

    //-------------------------------------------------------------------------
    public int getIdPersona()
    {
        return null != navigation.getFk_persona_tiene_incidencia()
                ? navigation.getFk_persona_tiene_incidencia().getIdPersona()
                : idPersona;
    }

    //-------------------------------------------------------------------------
    public void setIdPersona(int id_persona)
    {
        this.idPersona = id_persona;
        navigation.releasePersona();
    }

    //-------------------------------------------------------------------------
    public PersonaComplemento getHasOnePersona()
    {
        return null != navigation.getFk_persona_tiene_incidencia()
                ? navigation.getFk_persona_tiene_incidencia()
                : navigation.joinPersonaComplemento(idPersona);
    }

    //-------------------------------------------------------------------------
    public void setHasOnePersona(PersonaComplemento value)
    {
        navigation.setFk_persona_tiene_incidencia(value);
    }

    //-------------------------------------------------------------------------
    public int getTipo_incidencia()
    {
        return tipo_incidencia;
    }

    //-------------------------------------------------------------------------
    public void setTipo_incidencia(int tipo_incidencia)
    {
        this.tipo_incidencia = tipo_incidencia;
    }

    //-------------------------------------------------------------------------
    public String getObservaciones()
    {
        return observaciones;
    }

    //-------------------------------------------------------------------------
    public void setObservaciones(String observaciones)
    {
        this.observaciones = observaciones;
    }

    //-------------------------------------------------------------------------
    public void copy(PersonaTieneIncidencia that)
    {
        assign(that);
    }

    //---------------------------------------------------------------------
    protected class ForeignKey
    {

        private PersonaComplemento fk_persona_tiene_incidencia;

        //---------------------------------------------------------------------
        public PersonaComplemento getFk_persona_tiene_incidencia()
        {
            return fk_persona_tiene_incidencia;
        }

        //---------------------------------------------------------------------
        public void setFk_persona_tiene_incidencia(PersonaComplemento value)
        {
            this.fk_persona_tiene_incidencia = value;
            this.fk_persona_tiene_incidencia.setDependentOn(false);
        }

        //---------------------------------------------------------------------
        public PersonaComplemento joinPersonaComplemento(int id_persona)
        {
            fk_persona_tiene_incidencia = new PersonaComplemento();
            fk_persona_tiene_incidencia.setIdPersona(id_persona);
            return fk_persona_tiene_incidencia;
        }

        //---------------------------------------------------------------------
        public void releasePersona()
        {
            fk_persona_tiene_incidencia = null;
        }

        //---------------------------------------------------------------------   
        private void acceptChanges()
        {
            if (null != fk_persona_tiene_incidencia && fk_persona_tiene_incidencia.isDependentOn())
                fk_persona_tiene_incidencia.acceptChanges();
        }
    }
}
