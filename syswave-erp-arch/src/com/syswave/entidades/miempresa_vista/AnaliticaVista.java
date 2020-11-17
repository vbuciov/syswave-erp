package com.syswave.entidades.miempresa_vista;

import com.syswave.entidades.common.Entidad;
import java.io.Serializable;

/**
 *
 * @author Gilberto Aaron Jimenez Montelongo
 */
public class AnaliticaVista extends Entidad implements Serializable
{

    private int id, edad, antiguedad;
    private String noEmpleado, nombreCompleto,
            departamento, puesto, reportes, planta, duracion,
            cumpleaños, nacionalidad, nss, curp, rfc, formacion,
            escolaridad, direcciones, lugarDeProcedencia, telCasa,
            religion, tipoSangre, genero, estadoCivil, intereses,
            habilidades, pasatiempos, contactoEmergencia, atributos,
            padecimientosOAlergias, tipoPersona, fechaIngreso, nacimiento;

    //private Date  nacimiento;
    private Boolean activo;

    //---------------------------------------------------------------------
    private void initAtributes()
    {
        id = EMPTY_INT;
        noEmpleado = EMPTY_STRING;
        nombreCompleto = EMPTY_STRING;
        edad = EMPTY_INT;
        cumpleaños = EMPTY_STRING;
        nacionalidad = EMPTY_STRING;
        nss = EMPTY_STRING;
        curp = EMPTY_STRING;
        rfc = EMPTY_STRING;
        formacion = EMPTY_STRING;
        escolaridad = EMPTY_STRING;
        direcciones = EMPTY_STRING;
        lugarDeProcedencia = EMPTY_STRING;
        telCasa = EMPTY_STRING;
        religion = EMPTY_STRING;
        tipoSangre = EMPTY_STRING;
        genero = EMPTY_STRING;
        estadoCivil = EMPTY_STRING;
        intereses = EMPTY_STRING;
        habilidades = EMPTY_STRING;
        pasatiempos = EMPTY_STRING;
        contactoEmergencia = EMPTY_STRING;
        atributos = EMPTY_STRING;
        padecimientosOAlergias = EMPTY_STRING;

        nacimiento = EMPTY_STRING;
        activo = true;
        fechaIngreso = EMPTY_STRING;

        edad = EMPTY_INT;
        duracion = EMPTY_STRING;
        departamento = EMPTY_STRING;
        puesto = EMPTY_STRING;
        planta = EMPTY_STRING;
        reportes = EMPTY_STRING;
        antiguedad = EMPTY_INT;
    }

    //---------------------------------------------------------------------
    public AnaliticaVista()
    {
        initAtributes();
    }

    //---------------------------------------------------------------------
    public AnaliticaVista(AnaliticaVista that)
    {
        asign(that);
    }

    //---------------------------------------------------------------------
    private void asign(AnaliticaVista that)
    {
        id = that.id;
        noEmpleado = that.noEmpleado;
        nombreCompleto = that.nombreCompleto;
        edad = that.edad;
        cumpleaños = that.cumpleaños;
        nacionalidad = that.nacionalidad;
        nss = that.nss;
        curp = that.curp;
        rfc = that.rfc;
        formacion = that.formacion;
        escolaridad = that.escolaridad;
        direcciones = that.direcciones;
        lugarDeProcedencia = that.lugarDeProcedencia;
        telCasa = that.telCasa;
        religion = that.religion;
        tipoSangre = that.tipoSangre;
        genero = that.genero;
        estadoCivil = that.estadoCivil;
        intereses = that.intereses;
        habilidades = that.habilidades;
        pasatiempos = that.pasatiempos;
        contactoEmergencia = that.contactoEmergencia;
        atributos = that.atributos;
        padecimientosOAlergias = that.padecimientosOAlergias;

        nacimiento = that.nacimiento;
        activo = that.activo;

        duracion = that.duracion;
        departamento = that.departamento;
        puesto = that.puesto;
        reportes = that.reportes;
        planta = that.planta;
        fechaIngreso = that.fechaIngreso;
        antiguedad = that.antiguedad;

    }

    public int getAntiguedad()
    {
        return antiguedad;
    }

    public void setAntiguedad(int antiguedad)
    {
        this.antiguedad = antiguedad;
    }

    public String getDuracion()
    {
        return duracion;
    }

    public void setDuracion(String duracion)
    {
        this.duracion = duracion;
    }

    public String getDepartamento()
    {
        return departamento;
    }

    public void setDepartamento(String departamento)
    {
        this.departamento = departamento;
    }

    public String getPuesto()
    {
        return puesto;
    }

    public void setPuesto(String puesto)
    {
        this.puesto = puesto;
    }

    public String getReportes()
    {
        return reportes;
    }

    public void setReportes(String reportes)
    {
        this.reportes = reportes;
    }

    public String getPlanta()
    {
        return planta;
    }

    public void setPlanta(String planta)
    {
        this.planta = planta;
    }

    public String getFechaIngreso()
    {
        return fechaIngreso;
    }

    public void setFechaIngreso(String fechaIngreso)
    {
        this.fechaIngreso = fechaIngreso;
    }

    public String getTipoPersona()
    {
        return tipoPersona;
    }

    public void setTipoPersona(String tipoPersona)
    {
        this.tipoPersona = tipoPersona;
    }

    public String getNoEmpleado()
    {
        return noEmpleado;
    }

    public void setNoEmpleado(String noEmpleado)
    {
        this.noEmpleado = noEmpleado;
    }

    public int getEdad()
    {
        return edad;
    }

    public void setEdad(int edad)
    {
        this.edad = edad;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getNombreCompleto()
    {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto)
    {
        this.nombreCompleto = nombreCompleto;
    }

    public String getCumpleaños()
    {
        return cumpleaños;
    }

    public void setCumpleaños(String cumpleaños)
    {
        this.cumpleaños = cumpleaños;
    }

    public String getNacionalidad()
    {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad)
    {
        this.nacionalidad = nacionalidad;
    }

    public String getNss()
    {
        return nss;
    }

    public void setNss(String nss)
    {
        this.nss = nss;
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

    public String getFormacion()
    {
        return formacion;
    }

    public void setFormacion(String formacion)
    {
        this.formacion = formacion;
    }

    public String getEscolaridad()
    {
        return escolaridad;
    }

    public void setEscolaridad(String escolaridad)
    {
        this.escolaridad = escolaridad;
    }

    public String getDirecciones()
    {
        return direcciones;
    }

    public void setDirecciones(String direcciones)
    {
        this.direcciones = direcciones;
    }

    public String getLugarDeProcedencia()
    {
        return lugarDeProcedencia;
    }

    public void setLugarDeProcedencia(String lugarDeProcedencia)
    {
        this.lugarDeProcedencia = lugarDeProcedencia;
    }

    public String getTelCasa()
    {
        return telCasa;
    }

    public void setTelCasa(String telCasa)
    {
        this.telCasa = telCasa;
    }

    public String getReligion()
    {
        return religion;
    }

    public void setReligion(String religion)
    {
        this.religion = religion;
    }

    public String getTipoSangre()
    {
        return tipoSangre;
    }

    public void setTipoSangre(String tipoSangre)
    {
        this.tipoSangre = tipoSangre;
    }

    public String getGenero()
    {
        return genero;
    }

    public void setGenero(String genero)
    {
        this.genero = genero;
    }

    public String getEstadoCivil()
    {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil)
    {
        this.estadoCivil = estadoCivil;
    }

    public String getIntereses()
    {
        return intereses;
    }

    public void setIntereses(String intereses)
    {
        this.intereses = intereses;
    }

    public String getHabilidades()
    {
        return habilidades;
    }

    public void setHabilidades(String habilidades)
    {
        this.habilidades = habilidades;
    }

    public String getPasatiempos()
    {
        return pasatiempos;
    }

    public void setPasatiempos(String pasatiempos)
    {
        this.pasatiempos = pasatiempos;
    }

    public String getContactoEmergencia()
    {
        return contactoEmergencia;
    }

    public void setContactoEmergencia(String contactoEmergencia)
    {
        this.contactoEmergencia = contactoEmergencia;
    }

    public String getAtributos()
    {
        return atributos;
    }

    public void setAtributos(String atributos)
    {
        this.atributos = atributos;
    }

    public String getPadecimientosOAlergias()
    {
        return padecimientosOAlergias;
    }

    public void setPadecimientosOAlergias(String padecimientosOAlergias)
    {
        this.padecimientosOAlergias = padecimientosOAlergias;
    }

    public String getNacimiento()
    {
        return nacimiento;
    }

    public void setNacimiento(String nacimiento)
    {
        this.nacimiento = nacimiento;
    }

    public Boolean getActivo()
    {
        return activo;
    }

    public void setActivo(Boolean activo)
    {
        this.activo = activo;
    }

    //---------------------------------------------------------------------
    public void copy(AnaliticaVista that)
    {
        asign(that);
    }

    @Override
    public void clear()
    {
        initAtributes();

    }

}
