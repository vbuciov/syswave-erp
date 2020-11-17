package com.syswave.entidades.miempresa_vista;

import com.syswave.entidades.miempresa.Persona;
import com.syswave.entidades.miempresa.PersonaComplemento;
import java.util.Date;

/**
 *
 * @author Gilberto Aaron Jimenez Montelongo
 */
public class Personal extends PersonaComplemento
{
    private String nacionalidad, no_empleado, observaciones;
    
    protected Persona fk_persona_complemento;
    
    //------------------------------------------------------------------------------    
    public Personal()
    {
        super();
        initAtributes();
        fk_persona_complemento = super.getHasOnePersona();
    }
    
    //------------------------------------------------------------------------------    
    public Personal(Personal that)
    {
        super(that);
        fk_persona_complemento = super.getHasOnePersona();
        assign(that); 
    }
    
    //------------------------------------------------------------------------------    
    private void assign(Personal that)
    {
        //fk_persona_direccion = that.fk_persona_direccion;
        nacionalidad = that.nacionalidad;
        // El campo no_empleado  No es parte de la entidad, solo esta para fines de lectura.
        //esto significa que al insertar o actualizar no se puede leer este dato 
       //no_empleado = that.no_empleado;
        observaciones = that.observaciones;
    }
    
    //------------------------------------------------------------------------------    
    private void initAtributes()
    {
        nacionalidad = EMPTY_STRING;
        no_empleado = EMPTY_STRING;
        observaciones = EMPTY_STRING;
    }
    
 
    //------------------------------------------------------------------------------   
    public String getNombres()
    {
        return fk_persona_complemento.getNombres();
    }
    
    //------------------------------------------------------------------------------    
    public void setNombres(String nombre)
    {
        fk_persona_complemento.setNombres(nombre);
    }

    //------------------------------------------------------------------------------    
    public String getApellidos()
    {
        return fk_persona_complemento.getApellidos();
    }
    
    //------------------------------------------------------------------------------    
    public void setApellidos(String apellido)
    {
        fk_persona_complemento.setApellidos(apellido);

    }

    //------------------------------------------------------------------------------    
    public String getNombreCompleto()
    {
        //return String.format("%s %s", fk_persona_complemento.getNombres(), fk_persona_complemento.getApellidos());
        return fk_persona_complemento.getNombreCompleto();
    }

    //------------------------------------------------------------------------------
    public Date getNacimiento()
    {
        return fk_persona_complemento.getNacimiento();
    }

    //------------------------------------------------------------------------------
    public void setNacimiento(Date nacimiento)
    {
        fk_persona_complemento.setNacimiento(nacimiento);
    }
    
    //------------------------------------------------------------------------------
    public boolean esActivo()
    {
        return fk_persona_complemento.esActivo();
    }

    //------------------------------------------------------------------------------
    public void setActivo(boolean activo)
    {
        fk_persona_complemento.setActivo(activo);
    }
    
    //------------------------------------------------------------------------------
    public int getId_Tipo_Persona()
    {
        return fk_persona_complemento.getId_tipo_persona();
    }

     //------------------------------------------------------------------------------
    public void setId_Tipo_Persona(int value)
    {
        fk_persona_complemento.setId_tipo_pesrona(value);
    }

    //------------------------------------------------------------------------------
    public String getNacionalidad()
    {
        return nacionalidad;
    }

    //------------------------------------------------------------------------------
    public void setNacionalidad(String nacionalidad)
    {
        this.nacionalidad = nacionalidad;
    }

    //--------------------------------------------------------------------
    public String getNo_empleado()
    {
        return no_empleado;
    }

    //--------------------------------------------------------------------
    public void setNo_empleado(String value)
    {
        this.no_empleado = value;
    }
    
    //------------------------------------------------------------------------------
    public String getObservaciones()
    {
        return observaciones;
    }

    //------------------------------------------------------------------------------
    public void setObservaciones(String observaciones)
    {
        this.observaciones = observaciones;
    }
    
    //------------------------------------------------------------------------------
    @Override
    public void clear()
    {
        initAtributes();
        super.clear();
    }

    //---------------------------------------------------------------------
    public void copy(Personal that)
    {
        assign(that);
        super.copy(that);
    }
}
