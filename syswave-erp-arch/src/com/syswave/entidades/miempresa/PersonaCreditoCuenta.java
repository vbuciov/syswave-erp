package com.syswave.entidades.miempresa;

import com.syswave.entidades.keys.CompositeKeyByIdPersonaConsecutivo;
import java.util.Date;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class PersonaCreditoCuenta extends CompositeKeyByIdPersonaConsecutivo
{

    private String numero, observacion;
    private float saldo_actual, saldo_limite;
    private Date fecha_inicial;
    private int esTipo;
    private int id_moneda;
    private boolean activo;

    protected ForeignKey navigation;

    //---------------------------------------------------------------------
    public PersonaCreditoCuenta()
    {
        super();
        createAtributes();
        initAtributes();
    }

    //---------------------------------------------------------------------
    public PersonaCreditoCuenta(PersonaCreditoCuenta that)
    {
        super();
        createAtributes();
        asign(that);
    }

    //---------------------------------------------------------------------  
    private void createAtributes()
    {
        navigation = new ForeignKey();
    }

    //---------------------------------------------------------------------
    private void initAtributes()
    {
        numero = EMPTY_STRING;
        saldo_actual = EMPTY_FLOAT;
        saldo_limite = EMPTY_FLOAT;
        fecha_inicial = EMPTY_DATE;
        esTipo = EMPTY_INT;
        id_moneda = EMPTY_INT;
        activo = true;
        observacion = EMPTY_STRING;
    }

    //---------------------------------------------------------------------
    private void asign(PersonaCreditoCuenta that)
    {
        super.assign(that);
        numero = that.numero;
        saldo_actual = that.saldo_actual;
        saldo_limite = that.saldo_limite;
        fecha_inicial = that.fecha_inicial;
        esTipo = that.esTipo;
        activo = that.activo;
        observacion = that.observacion;
        id_moneda = that.id_moneda;

        navigation = that.navigation;
    }

    //---------------------------------------------------------------------
    @Override
    public int getIdPersona()
    {
        return null != navigation.getFk_persona_tiene_saldos_id_persona()
                ? navigation.getFk_persona_tiene_saldos_id_persona().getId()
                : super.getIdPersona();
    }

    //---------------------------------------------------------------------
    @Override
    public int getIdPersona_Viejo()
    {
        return null != navigation.getFk_persona_tiene_saldos_id_persona()
                ? navigation.getFk_persona_tiene_saldos_id_persona().getId_Viejo()
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
        return null != navigation.getFk_persona_tiene_saldos_id_persona()
                ? navigation.getFk_persona_tiene_saldos_id_persona()
                : navigation.joinPersonas(super.getIdPersona());
    }

    //---------------------------------------------------------------------
    public void setHasOnePersona(Persona value)
    {
        navigation.setFk_persona_tiene_saldos_id_persona(value);
    }

    //---------------------------------------------------------------------
    public int getIdMoneda()
    {
        return null != navigation.getFk_persona_tiene_saldos_id_moneda()
                ? navigation.getFk_persona_tiene_saldos_id_moneda().getId()
                : id_moneda;
    }

    //---------------------------------------------------------------------
    public void setIdMoneda(int value)
    {
        navigation.releaseMonedas();
        id_moneda = value;
    }

    //---------------------------------------------------------------------
    public Moneda getHasOneMoneda()
    {
        return null != navigation.getFk_persona_tiene_saldos_id_moneda()
                ? navigation.getFk_persona_tiene_saldos_id_moneda()
                : navigation.joinMonedas(id_moneda);
    }

    //---------------------------------------------------------------------
    public void setHasOneMoneda(Moneda value)
    {
        navigation.setFk_persona_tiene_saldos_id_moneda(value);
    }

    //---------------------------------------------------------------------
    public String getNumero()
    {
        return numero;
    }

    //---------------------------------------------------------------------
    public void setNumero(String numero)
    {
        this.numero = numero;
    }

    //---------------------------------------------------------------------
    public float getSaldoActual()
    {
        return saldo_actual;
    }

    public void setSaldoActual(float value)
    {
        this.saldo_actual = value;
    }

    //---------------------------------------------------------------------
    public float getSaldoLimite()
    {
        return saldo_limite;
    }

    //---------------------------------------------------------------------
    public void setSaldoLimite(float value)
    {
        this.saldo_limite = value;
    }

    //---------------------------------------------------------------------
    public Date getFechaInicial()
    {
        return fecha_inicial;
    }

    //---------------------------------------------------------------------
    public void setFechaInicial(Date value)
    {
        this.fecha_inicial = value;
    }

    //---------------------------------------------------------------------
    public int getEsTipo()
    {
        return esTipo;
    }

    //---------------------------------------------------------------------
    public void setEsTipo(int value)
    {
        this.esTipo = value;
    }

    //---------------------------------------------------------------------
    public boolean esActivo()
    {
        return activo;
    }

    //---------------------------------------------------------------------
    public void setActivo(boolean value)
    {
        this.activo = value;
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
    @Override
    public void clear()
    {
        initAtributes();
        navigation.releaseMonedas();
        navigation.releasePersona();
    }

    //---------------------------------------------------------------------
    public void copy(PersonaCreditoCuenta that)
    {
        asign(that);
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

        protected Persona fk_persona_tiene_saldos_id_persona;
        protected Moneda fk_persona_tiene_saldos_id_moneda;

        //---------------------------------------------------------------------
        public Persona getFk_persona_tiene_saldos_id_persona()
        {
            return fk_persona_tiene_saldos_id_persona;
        }

        //---------------------------------------------------------------------
        public void setFk_persona_tiene_saldos_id_persona(Persona value)
        {
            this.fk_persona_tiene_saldos_id_persona = value;
            this.fk_persona_tiene_saldos_id_persona.setDependentOn(false);
        }

        //---------------------------------------------------------------------
        public Moneda getFk_persona_tiene_saldos_id_moneda()
        {
            return fk_persona_tiene_saldos_id_moneda;
        }

        //---------------------------------------------------------------------
        public void setFk_persona_tiene_saldos_id_moneda(Moneda value)
        {
            this.fk_persona_tiene_saldos_id_moneda = value;
            this.fk_persona_tiene_saldos_id_moneda.setDependentOn(false);
        }

        //---------------------------------------------------------------------
        public Persona joinPersonas(int id_persona)
        {
            fk_persona_tiene_saldos_id_persona = new Persona();
            fk_persona_tiene_saldos_id_persona.setId(id_persona);
            return fk_persona_tiene_saldos_id_persona;
        }

        //---------------------------------------------------------------------
        public void releasePersona()
        {
            fk_persona_tiene_saldos_id_persona = null;
        }

        //---------------------------------------------------------------------
        public Moneda joinMonedas(int id_moneda)
        {
            fk_persona_tiene_saldos_id_moneda = new Moneda();
            fk_persona_tiene_saldos_id_moneda.setId(id_moneda);
            return fk_persona_tiene_saldos_id_moneda;
        }

        //---------------------------------------------------------------------
        public void releaseMonedas()
        {
            fk_persona_tiene_saldos_id_moneda = null;
        }

        //---------------------------------------------------------------------   
        private void acceptChanges()
        {
            if (null != fk_persona_tiene_saldos_id_persona && fk_persona_tiene_saldos_id_persona.isDependentOn())
                fk_persona_tiene_saldos_id_persona.acceptChanges();

            if (null != fk_persona_tiene_saldos_id_moneda && fk_persona_tiene_saldos_id_moneda.isDependentOn())
                fk_persona_tiene_saldos_id_moneda.acceptChanges();
        }
    }
}
