package com.syswave.entidades.miempresa;

import com.syswave.entidades.keys.PrimaryKeyById;

/**
 *
 * @author sis5
 */
public class SalarioComponente extends PrimaryKeyById
{

    private String nombre, comentario, numeroCuenta, clabe, proveedorCuenta;
    private Double cantidad, maximoCuenta;
    private Integer esFrecuencia, esTipoCuenta;
    private Integer idPersona, consecutivoPersona;

    protected ForeignKey navigation;

    public SalarioComponente()
    {
        super();
        createAtributes();
        initAtributes();
    }

    public SalarioComponente(SalarioComponente that)
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
        nombre = EMPTY_STRING;
        comentario = EMPTY_STRING;
        numeroCuenta = EMPTY_STRING;
        clabe = EMPTY_STRING;
        proveedorCuenta = EMPTY_STRING;
        cantidad = EMPTY_DOUBLE;
        maximoCuenta = EMPTY_DOUBLE;
        esFrecuencia = EMPTY_INT;
        esTipoCuenta = EMPTY_INT;
        idPersona = EMPTY_INT;
        consecutivoPersona = EMPTY_INT;
    }

    private void assign(SalarioComponente that)
    {
        super.assign(that);
        nombre = that.nombre;
        comentario = that.comentario;
        numeroCuenta = that.numeroCuenta;
        clabe = that.clabe;
        proveedorCuenta = that.proveedorCuenta;
        cantidad = that.cantidad;
        maximoCuenta = that.maximoCuenta;
        esFrecuencia = that.esFrecuencia;
        esTipoCuenta = that.esTipoCuenta;
        idPersona = that.idPersona;
        consecutivoPersona = that.consecutivoPersona;

        navigation = that.navigation;
    }

    public int getIdPersona()
    {
        return navigation.getFk_persona_salario() != null
                ? navigation.getFk_persona_salario().getIdPersona()
                : idPersona;
    }

    public void setIdPersona(int value)
    {
        idPersona = value;
        navigation.releasePersonaSalario();
    }

    public int getConsecutivoSalario()
    {
        return navigation.getFk_persona_salario() != null
                ? navigation.getFk_persona_salario().getConsecutivo()
                : consecutivoPersona;
    }

    public void setConsecutivoSalario(int value)
    {
        consecutivoPersona = value;
        navigation.releasePersonaSalario();
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public String getComentario()
    {
        return comentario;
    }

    public void setComentario(String comentario)
    {
        this.comentario = comentario;
    }

    public String getNumeroCuenta()
    {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta)
    {
        this.numeroCuenta = numeroCuenta;
    }

    public String getClabe()
    {
        return clabe;
    }

    public void setClabe(String clabe)
    {
        this.clabe = clabe;
    }

    public String getProveedorCuenta()
    {
        return proveedorCuenta;
    }

    public void setProveedorCuenta(String proveedorCuenta)
    {
        this.proveedorCuenta = proveedorCuenta;
    }

    public Double getCantidad()
    {
        return cantidad;
    }

    public void setCantidad(Double cantidad)
    {
        this.cantidad = cantidad;
    }

    public Double getMaximoCuenta()
    {
        return maximoCuenta;
    }

    public void setMaximoCuenta(Double maximoCuenta)
    {
        this.maximoCuenta = maximoCuenta;
    }

    public Integer getEsFrecuencia()
    {
        return esFrecuencia;
    }

    public void setEsFrecuencia(Integer esFrecuencia)
    {
        this.esFrecuencia = esFrecuencia;
    }

    public Integer getEsTipoCuenta()
    {
        return esTipoCuenta;
    }

    public void setEsTipoCuenta(Integer esTipoCuenta)
    {
        this.esTipoCuenta = esTipoCuenta;
    }

    @Override
    public void clear()
    {
        initAtributes();
        navigation.releasePersonaSalario();
    }

    public void copy(SalarioComponente that)
    {
        assign(that);
    }

    public PersonaSalario getHasOnePersonaSalario()
    {
        return navigation.getFk_persona_salario() != null
                ? navigation.getFk_persona_salario()
                : navigation.joinPersonaSalario(idPersona, consecutivoPersona);
    }

    public void setHasOnePersonaSalario(PersonaSalario value)
    {
        navigation.setFk_persona_salario(value);
    }

    //---------------------------------------------------------------------
    protected class ForeignKey
    {

        private PersonaSalario fk_persona_salario;

        //---------------------------------------------------------------------
        public PersonaSalario getFk_persona_salario()
        {
            return fk_persona_salario;
        }

        //---------------------------------------------------------------------
        public void setFk_persona_salario(PersonaSalario value)
        {
            this.fk_persona_salario = value;
            this.fk_persona_salario.setDependentOn(false);
        }

        //---------------------------------------------------------------------
        public PersonaSalario joinPersonaSalario(int id_persona, int consecutivo)
        {
            fk_persona_salario = new PersonaSalario();
            fk_persona_salario.setIdPersona(id_persona);
            fk_persona_salario.setConsecutivo(consecutivo);

            return fk_persona_salario;
        }

        //---------------------------------------------------------------------
        public void releasePersonaSalario()
        {
            fk_persona_salario = null;
        }

        //---------------------------------------------------------------------   
        private void acceptChanges()
        {
            if (null != fk_persona_salario && fk_persona_salario.isDependentOn())
                fk_persona_salario.acceptChanges();
        }
    }
}
