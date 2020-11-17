package com.syswave.entidades.miempresa;

import com.syswave.entidades.keys.CompositeKeyByIdPersonaConsecutivo;
import java.util.Date;

/**
 *
 * @author sis5
 */
public class PersonaSalario extends CompositeKeyByIdPersonaConsecutivo
{

    private Date fechaVigor;
    private Double sueldoNeto;
    private Integer frecuencia;
    private Integer idMoneda;

    protected ForeignKey navigation;

    public PersonaSalario()
    {
        super();
        createAtributes();
        initAtributes();
    }

    public PersonaSalario(PersonaSalario that)
    {
        super();
        createAtributes();
        assign(that);
    }

    private void createAtributes()
    {
        navigation = new ForeignKey();
    }

    private void initAtributes()
    {
        fechaVigor = EMPTY_DATE;
        sueldoNeto = EMPTY_DOUBLE;
        frecuencia = EMPTY_INT;
        idMoneda = EMPTY_INT;
    }

    private void assign(PersonaSalario that)
    {
        super.assign(that);
        fechaVigor = that.fechaVigor;
        sueldoNeto = that.sueldoNeto;
        frecuencia = that.frecuencia;
        idMoneda = that.idMoneda;

        navigation = that.navigation;
    }

    //---------------------------------------------------------------------
    @Override
    public int getIdPersona()
    {
        return null != navigation.getFk_persona_salario_id_persona()
                ? navigation.getFk_persona_salario_id_persona().getId()
                : super.getIdPersona();
    }
    
        //---------------------------------------------------------------------
    @Override
    public int getIdPersona_Viejo()
    {
        return null != navigation.getFk_persona_salario_id_persona()
                ? navigation.getFk_persona_salario_id_persona().getId_Viejo()
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
    public Persona getFk_persona()
    {
        return null != navigation.getFk_persona_salario_id_persona()
                ? navigation.getFk_persona_salario_id_persona()
                : navigation.joinPersonas(super.getIdPersona());
    }

    //---------------------------------------------------------------------
    public void setHasOnePersona(Persona value)
    {
        navigation.setFk_persona_salario_id_persona(value);
    }

    public Date getFechaVigor()
    {
        return fechaVigor;
    }

    public void setFechaVigor(Date fechaVigor)
    {
        this.fechaVigor = fechaVigor;
    }

    public Double getSueldoNeto()
    {
        return sueldoNeto;
    }

    public void setSueldoNeto(Double sueldoNeto)
    {
        this.sueldoNeto = sueldoNeto;
    }

    public Integer getFrecuencia()
    {
        return frecuencia;
    }

    public void setFrecuencia(Integer frecuencia)
    {
        this.frecuencia = frecuencia;
    }

    //---------------------------------------------------------------------
    public int getIdMoneda()
    {
        return null != navigation.getFk_persona_salario_id_moneda()
                ? navigation.getFk_persona_salario_id_moneda().getId()
                : idMoneda;
    }

    //---------------------------------------------------------------------
    public void setIdMoneda(int value)
    {
        idMoneda = value;
        navigation.releaseMonedas();
    }

    //---------------------------------------------------------------------
    public Moneda getHasOneMoneda()
    {
        return null != navigation.getFk_persona_salario_id_moneda()
                ? navigation.getFk_persona_salario_id_moneda()
                : navigation.joinMonedas(idMoneda);
    }

    //---------------------------------------------------------------------
    public void setHasOneMoneda(Moneda value)
    {
        navigation.setFk_persona_salario_id_moneda(value);
    }

    //---------------------------------------------------------------------
    @Override
    public void clear()
    {
        initAtributes();
        navigation.releaseMonedas();
        navigation.releasePersona();
    }

    //---------------------------------------------------------------------
    public void copy(PersonaSalario that)
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

        private Persona fk_persona_salario_id_persona;
        private Moneda fk_persona_salario_id_moneda;

        //---------------------------------------------------------------------
        public Persona getFk_persona_salario_id_persona()
        {
            return fk_persona_salario_id_persona;
        }

        //---------------------------------------------------------------------
        public void setFk_persona_salario_id_persona(Persona value)
        {
            this.fk_persona_salario_id_persona = value;
            this.fk_persona_salario_id_persona.setDependentOn(false);
        }

        //---------------------------------------------------------------------
        public Persona joinPersonas(int id_persona)
        {
            fk_persona_salario_id_persona = new Persona();
            fk_persona_salario_id_persona.setId(id_persona);
            return fk_persona_salario_id_persona;
        }

        //---------------------------------------------------------------------
        public void releasePersona()
        {
            fk_persona_salario_id_persona = null;
        }

        //---------------------------------------------------------------------
        public Moneda getFk_persona_salario_id_moneda()
        {
            return fk_persona_salario_id_moneda;
        }

        //---------------------------------------------------------------------
        public void setFk_persona_salario_id_moneda(Moneda value)
        {
            this.fk_persona_salario_id_moneda = value;
            this.fk_persona_salario_id_moneda.setDependentOn(false);
        }

        //---------------------------------------------------------------------
        public Moneda joinMonedas(int id_moneda)
        {
            fk_persona_salario_id_moneda = new Moneda();
            fk_persona_salario_id_moneda.setId(id_moneda);
            return fk_persona_salario_id_moneda;
        }

        //---------------------------------------------------------------------
        public void releaseMonedas()
        {
            fk_persona_salario_id_moneda = null;
        }

        //---------------------------------------------------------------------   
        private void acceptChanges()
        {
            if (null != fk_persona_salario_id_persona && fk_persona_salario_id_persona.isDependentOn())
                fk_persona_salario_id_persona.acceptChanges();

            if (null != fk_persona_salario_id_moneda && fk_persona_salario_id_moneda.isDependentOn())
                fk_persona_salario_id_moneda.acceptChanges();
        }
    }
}
