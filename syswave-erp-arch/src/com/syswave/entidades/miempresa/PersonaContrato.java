package com.syswave.entidades.miempresa;

import com.syswave.entidades.keys.CompositeKeyByIdPersonaConsecutivo;
import java.util.Date;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class PersonaContrato extends CompositeKeyByIdPersonaConsecutivo
{

    private Date fecha_inicio, fecha_terminacion;
    private int es_tipo;
    private String ruta_digital;
    private Integer idArea, idPuesto, idJornada;

    protected ForeignKey navigation;

    //---------------------------------------------------------------------
    public PersonaContrato()
    {
        super();
        createAtributes();
        initAtributes();
    }

    //---------------------------------------------------------------------
    public PersonaContrato(PersonaContrato that)
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
    private void assign(PersonaContrato that)
    {
        super.assign(that);
        fecha_inicio = that.fecha_inicio;
        fecha_terminacion = that.fecha_terminacion;
        es_tipo = that.es_tipo;
        ruta_digital = that.ruta_digital;

        navigation = that.navigation;
    }

    //---------------------------------------------------------------------
    private void initAtributes()
    {
        fecha_inicio = EMPTY_DATE;
        fecha_terminacion = EMPTY_DATE;
        es_tipo = EMPTY_INT;
        ruta_digital = EMPTY_STRING;
    }

    //---------------------------------------------------------------------
    @Override
    public int getIdPersona()
    {
        return null != navigation.getFk_persona_contrato_id_persona()
                ? navigation.getFk_persona_contrato_id_persona().getId()
                : super.getIdPersona();
    }

    //---------------------------------------------------------------------
    @Override
    public int getIdPersona_Viejo()
    {
        return null != navigation.getFk_persona_contrato_id_persona()
                ? navigation.getFk_persona_contrato_id_persona().getId_Viejo()
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
    public void setIdArea(int value)
    {
        idArea = value;
        navigation.releaseArea();
    }

    //---------------------------------------------------------------------
    public int getIdArea()
    {
        return null != navigation.getFk_persona_contratos_id_area()
                ? navigation.getFk_persona_contratos_id_area().getId()
                : idArea;
    }

    //---------------------------------------------------------------------
    public void setIdPuesto(int value)
    {
        idPuesto = value;
        navigation.releasePuesto();
    }

    //---------------------------------------------------------------------
    public int getIdPuesto()
    {
        return null != navigation.getFk_persona_contratos_id_puesto()
                ? navigation.getFk_persona_contratos_id_puesto().getId()
                : idPuesto;
    }

    //---------------------------------------------------------------------
    public void setIdJornada(int value)
    {
        idJornada = value;
        navigation.releaseJornada();
    }

    //---------------------------------------------------------------------
    public int getIdJornada()
    {
        return null != navigation.getFk_persona_contratos_id_jornada()
                ? navigation.getFk_persona_contratos_id_jornada().getId()
                : idJornada;
    }

    //---------------------------------------------------------------------
    public Persona getHasOnePersona()
    {
        return null != navigation.getFk_persona_contrato_id_persona()
                ? navigation.getFk_persona_contrato_id_persona()
                : navigation.joinPersona(super.getIdPersona());
    }

    //---------------------------------------------------------------------
    public void setHasOnePersona(Persona value)
    {
        navigation.setFk_persona_contrato_id_persona(value);
    }

    //---------------------------------------------------------------------
    public Valor getHasOneArea()
    {
        return null != navigation.getFk_persona_contratos_id_area()
                ? navigation.getFk_persona_contratos_id_area()
                : navigation.joinArea(idArea);
    }

    //---------------------------------------------------------------------
    public void setHasOneArea(Valor value)
    {
        navigation.setFk_persona_contratos_id_area(value);
    }

    //---------------------------------------------------------------------
    public Puesto getHasOnePuesto()
    {
        return null != navigation.getFk_persona_contratos_id_puesto()
                ? navigation.getFk_persona_contratos_id_puesto()
                : navigation.joinPuesto(idPuesto);
    }

    //---------------------------------------------------------------------
    public void setHasOnePuesto(Puesto value)
    {
        navigation.setFk_persona_contratos_id_puesto(value);
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
    public Date getFechaTerminacion()
    {
        return fecha_terminacion;
    }

    //---------------------------------------------------------------------
    public void setFechaTerminacion(Date value)
    {
        this.fecha_terminacion = value;
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
    public String getRutaDigital()
    {
        return ruta_digital;
    }

    //---------------------------------------------------------------------
    public void setRutaDigital(String value)
    {
        this.ruta_digital = value;
    }

    //---------------------------------------------------------------------
    @Override
    public void clear()
    {
        initAtributes();
    }

    //---------------------------------------------------------------------
    public void copy(PersonaContrato that)
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

        private Persona fk_persona_contrato_id_persona;
        private Valor fk_persona_contratos_id_area;
        private Puesto fk_persona_contratos_id_puesto;
        private Jornada fk_persona_contratos_id_jornada;

        //---------------------------------------------------------------------
        public Persona getFk_persona_contrato_id_persona()
        {
            return fk_persona_contrato_id_persona;
        }

        //---------------------------------------------------------------------
        public void setFk_persona_contrato_id_persona(Persona value)
        {
            this.fk_persona_contrato_id_persona = value;
            this.fk_persona_contrato_id_persona.setDependentOn(false);
        }

        //---------------------------------------------------------------------
        public Persona joinPersona(int id_persona)
        {
            fk_persona_contrato_id_persona = new Persona();
            fk_persona_contrato_id_persona.setId(id_persona);
            return fk_persona_contrato_id_persona;
        }

        //---------------------------------------------------------------------
        public void releasePersona()
        {
            fk_persona_contrato_id_persona = null;
        }

        //---------------------------------------------------------------------
        public Valor getFk_persona_contratos_id_area()
        {
            return fk_persona_contratos_id_area;
        }

        //---------------------------------------------------------------------
        public void setFk_persona_contratos_id_area(Valor value)
        {
            this.fk_persona_contratos_id_area = value;
            this.fk_persona_contratos_id_area.setDependentOn(false);
        }

        //---------------------------------------------------------------------
        public Valor joinArea(int id_area)
        {
            fk_persona_contratos_id_area = new Valor();
            fk_persona_contratos_id_area.setId(id_area);
            return fk_persona_contratos_id_area;
        }

        //---------------------------------------------------------------------
        public void releaseArea()
        {
            fk_persona_contratos_id_area = null;
        }

        //---------------------------------------------------------------------
        public Puesto getFk_persona_contratos_id_puesto()
        {
            return fk_persona_contratos_id_puesto;
        }

        //---------------------------------------------------------------------
        public void setFk_persona_contratos_id_puesto(Puesto value)
        {
            this.fk_persona_contratos_id_puesto = value;
            this.fk_persona_contratos_id_puesto.setDependentOn(false);
        }

        //---------------------------------------------------------------------
        public Puesto joinPuesto(int id_puesto)
        {
            fk_persona_contratos_id_puesto = new Puesto();
            fk_persona_contratos_id_puesto.setId(id_puesto);
            return fk_persona_contratos_id_puesto;
        }

        //---------------------------------------------------------------------
        public void releasePuesto()
        {
            fk_persona_contratos_id_puesto = null;
        }

        //---------------------------------------------------------------------
        public Jornada getFk_persona_contratos_id_jornada()
        {
            return fk_persona_contratos_id_jornada;
        }

        //---------------------------------------------------------------------
        public void setFk_persona_contratos_id_jornada(Jornada value)
        {
            this.fk_persona_contratos_id_jornada = value;
            this.fk_persona_contratos_id_jornada.setDependentOn(false);
        }

        //---------------------------------------------------------------------
        public Jornada joinJornada(int id_jornada)
        {
            fk_persona_contratos_id_jornada = new Jornada();
            fk_persona_contratos_id_jornada.setId(id_jornada);
            return fk_persona_contratos_id_jornada;
        }

        //---------------------------------------------------------------------
        public void releaseJornada()
        {
            fk_persona_contratos_id_jornada = null;
        }

        //---------------------------------------------------------------------   
        private void acceptChanges()
        {
            if (null != fk_persona_contrato_id_persona && fk_persona_contrato_id_persona.isDependentOn())
                fk_persona_contrato_id_persona.acceptChanges();

            if (null != fk_persona_contratos_id_area && fk_persona_contratos_id_area.isDependentOn())
                fk_persona_contratos_id_area.acceptChanges();

            if (null != fk_persona_contratos_id_puesto && fk_persona_contratos_id_puesto.isDependentOn())
                fk_persona_contratos_id_puesto.acceptChanges();

            if (null != fk_persona_contratos_id_jornada && fk_persona_contratos_id_jornada.isDependentOn())
                fk_persona_contratos_id_jornada.acceptChanges();
        }
    }
}
