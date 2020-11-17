package com.syswave.entidades.miempresa_vista;

import com.syswave.entidades.common.Entidad;

/**
 *
 * @author sis5
 */
public class ImpresionContrato extends Entidad
{

    private String nombre, direccion, nacionalidad, estado_civil, curp, rfc,
            puesto, genero, jornada, area_negocio;

    private int consecutivo, edad;

    public ImpresionContrato()
    {
        initAtributes();
    }

    public ImpresionContrato(ImpresionContrato elemento)
    {
        copy(elemento);
    }

    //---------------------------------------------------------------------
    public void copy(ImpresionContrato that)
    {
        asign(that);
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public String getDireccion()
    {
        return direccion;
    }

    public void setDireccion(String direccion)
    {
        this.direccion = direccion;
    }

    public String getNacionalidad()
    {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad)
    {
        this.nacionalidad = nacionalidad;
    }

    public String getEstado_civil()
    {
        return estado_civil;
    }

    public void setEstado_civil(String estado_civil)
    {
        this.estado_civil = estado_civil;
    }

    public String getCurp()
    {
        return curp;
    }

    public void setCurp(String curp)
    {
        this.curp = curp;
    }

    public String getRfc()
    {
        return rfc;
    }

    public void setRfc(String rfc)
    {
        this.rfc = rfc;
    }

    public String getPuesto()
    {
        return puesto;
    }

    public void setPuesto(String puesto)
    {
        this.puesto = puesto;
    }

    public String getGenero()
    {
        return genero;
    }

    public void setGenero(String genero)
    {
        this.genero = genero;
    }

    public String getJornada()
    {
        return jornada;
    }

    public void setJornada(String jornada)
    {
        this.jornada = jornada;
    }

    public int getConsecutivo()
    {
        return consecutivo;
    }

    public void setConsecutivo(int id)
    {
        this.consecutivo = id;
    }

    public int getEdad()
    {
        return edad;
    }

    public void setEdad(int edad)
    {
        this.edad = edad;
    }

    private void initAtributes()
    {
        nombre = EMPTY_STRING;
        direccion = EMPTY_STRING;
        nacionalidad = EMPTY_STRING;
        estado_civil = EMPTY_STRING;
        curp = EMPTY_STRING;
        rfc = EMPTY_STRING;
        puesto = EMPTY_STRING;
        area_negocio = EMPTY_STRING;
        genero = EMPTY_STRING;
        jornada = EMPTY_STRING;

        consecutivo = EMPTY_INT;
        edad = EMPTY_INT;
    }

    private void asign(ImpresionContrato that)
    {
        nombre = that.nombre;
        direccion = that.direccion;
        nacionalidad = that.nacionalidad;
        estado_civil = that.estado_civil;
        curp = that.curp;
        rfc = that.rfc;
        puesto = that.puesto;
        area_negocio = that.area_negocio;
        genero = that.genero;
        jornada = that.jornada;

        consecutivo = that.consecutivo;
        edad = that.edad;
    }

    @Override
    public void clear()
    {
        initAtributes();
    }

    public String getAreaNegocio()
    {
      return area_negocio;
    }
    
    public void setAreaNegocio (String value)
    {
        area_negocio = value;
    }

}
