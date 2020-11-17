package com.syswave.logicas.miempresa;

import com.syswave.datos.common.DataSourceManager;
import com.syswave.datos.miempresa.PersonaAnaliticaRetrieve;
import com.syswave.datos.miempresa.PersonaDetalladaRetrieve;
import com.syswave.datos.miempresa.PersonalDataAccess;
import com.syswave.datos.miempresa.PersonasDataAccess;
import com.syswave.entidades.miempresa.Persona;
import com.syswave.entidades.miempresa.PersonaDireccion;
import com.syswave.entidades.miempresa.PersonaIdentificador;
import com.syswave.entidades.miempresa_vista.Personal;
import com.syswave.entidades.miempresa.TipoPersona;
import com.syswave.entidades.miempresa_vista.AnaliticaVista;
import com.syswave.entidades.miempresa_vista.PersonaDetalladaVista;
import datalayer.api.IMediatorDataSource;
import java.util.List;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class PersonasBusinessLogic
{

    private String mensaje;
    private PersonasDataAccess tabla;
    private PersonaDetalladaRetrieve vista;
    private PersonaAnaliticaRetrieve vistaAnalitica;

    private PersonalDataAccess tablaPersonal;

    //---------------------------------------------------------------------
    public PersonasBusinessLogic()
    {
        IMediatorDataSource mysource = DataSourceManager.getMainDataSourceInstance();
        tabla = new PersonasDataAccess(mysource);
        vista = new PersonaDetalladaRetrieve(mysource);
        tablaPersonal = new PersonalDataAccess(mysource);
        vistaAnalitica = new PersonaAnaliticaRetrieve(mysource);
    }
    
    //---------------------------------------------------------------------
    public PersonasBusinessLogic(IMediatorDataSource source)
    {
        tabla = new PersonasDataAccess(source);
        vista = new PersonaDetalladaRetrieve(source);
        tablaPersonal = new PersonalDataAccess(source);
        vistaAnalitica = new PersonaAnaliticaRetrieve(source);
    }

    //---------------------------------------------------------------------
    public PersonasBusinessLogic(String esquema)
    {
        this();
        tabla.setEschema(esquema);
        vista.setEschema(esquema);
        tablaPersonal.setEschema(esquema);
        vistaAnalitica.setEschema(esquema);
    }

    //---------------------------------------------------------------------
    public boolean agregar(Persona nuevo)
    {
        boolean resultado = tabla.Create(nuevo)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    public boolean agregar(Personal nuevo)
    {
        boolean resultado = tablaPersonal.Create(nuevo)>0;
        mensaje = tablaPersonal.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    public boolean agregar(List<Persona> nuevos)
    {
        boolean resultado = tabla.Create(nuevos)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    public boolean agregarPersonal(List<Personal> nuevos)
    {
        boolean resultado = tablaPersonal.Create(nuevos)>0;
        mensaje = tablaPersonal.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    public boolean actualizar(Persona elemento)
    {
        boolean resultado = tabla.Update(elemento)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    public boolean actualizar(Personal elemento)
    {
        boolean resultado = tablaPersonal.Update(elemento)>0;
        mensaje = tablaPersonal.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    public boolean actualizar(List<Persona> elementos)
    {
        boolean resultado = tabla.Update(elementos)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    public boolean actualizarPersonal(List<Personal> elementos)
    {
        boolean resultado = tablaPersonal.Update(elementos)>0;
        mensaje = tablaPersonal.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    public boolean borrar(Persona elemento)
    {
        boolean resultado = tabla.Delete(elemento)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    public boolean borrar(Personal elemento)
    {
        boolean resultado = tablaPersonal.Delete(elemento)>0;
        mensaje = tablaPersonal.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    public boolean borrar(List<Persona> elementos)
    {
        boolean resultado = tabla.Delete(elementos)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    public boolean borrarPersonal(List<Personal> elementos)
    {
        boolean resultado = tablaPersonal.Delete(elementos)>0;
        mensaje = tablaPersonal.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    public List<Persona> obtenerLista()
    {
        List<Persona> resultado = tabla.Retrieve();

        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    public List<Personal> obtenerListaPersonal()
    {
        List<Personal> resultado = tablaPersonal.Retrieve();

        mensaje = tablaPersonal.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    public List<Persona> obtenerLista(Persona elemento)
    {
        List<Persona> resultado = tabla.Retrieve(elemento);

        mensaje = tabla.getMessage();

        return resultado;
    }
    
    //---------------------------------------------------------------------
    public Persona obtenerPersonaPorId(int id)
    {
        Persona filter = new Persona();
        filter.setId(id);
        List<Persona> resultado = tabla.Retrieve(filter);
        
        if (resultado.size() > 0)
            filter.copy(resultado.get(0));
        
        else 
            filter = null;

        mensaje = tablaPersonal.getMessage();

        return filter;
    }

    //---------------------------------------------------------------------
    public List<Personal> obtenerListaPersonal(Personal elemento)
    {
        List<Personal> resultado = tablaPersonal.Retrieve(elemento);

        mensaje = tablaPersonal.getMessage();

        return resultado;
    }
    
      //---------------------------------------------------------------------
    public List<Personal> obtenerListaPersonalExcluyente(Personal elemento)
    {
        List<Personal> resultado = tablaPersonal.Retrieve(elemento);

        mensaje = tablaPersonal.getMessage();

        return resultado;
    }
    
     //---------------------------------------------------------------------
    public List<Personal> obtenerListaPersonalPorTipo()
    {
        List<Personal> resultado = tablaPersonal.retrieveByType();

        mensaje = tablaPersonal.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    public List<Personal> obtenerListaPersonalPorTipo(TipoPersona filtro)
    {
        List<Personal> resultado = tablaPersonal.retrieveByType(filtro);

        mensaje = tablaPersonal.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    public List<Persona> obtenerListaPorTipo(TipoPersona filtro)
    {
        List<Persona> resultado = tabla.retrieveByType(filtro);

        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    public List<PersonaDetalladaVista> buscarPersona(TipoPersona tipo, Persona generales, PersonaIdentificador identificador, PersonaIdentificador medio, PersonaDireccion direccion)
    {
        List<PersonaDetalladaVista> resultado = vista.PersonaBuscar(tipo, generales, identificador, medio, direccion);

        mensaje = vista.getMessage();

        return resultado;
    }
    //---------------------------------------------------------------------
    public List<AnaliticaVista> obtenerVistaAnalitica(Personal generales, String noEmp)
    {
        
        List<AnaliticaVista> resultado = vistaAnalitica.PersonaAnaliticaBuscar(generales,noEmp);
        mensaje = tabla.getMessage();
        return resultado;
        
    }

    //---------------------------------------------------------------------
    public boolean recargar(Persona elemento)
    {
        List<Persona> resultado = tabla.Retrieve(elemento);
        
        if (resultado.size() > 0)
        {
            elemento.copy(resultado.get(0));
        }

        mensaje = tabla.getMessage();

        return tabla.getState() != PersonalDataAccess.DATASOURCE_ERROR;
    }

    //---------------------------------------------------------------------
    public boolean recargar(Personal elemento)
    {
        List<Personal> resultado = tablaPersonal.Retrieve(elemento);
        if (resultado.size() > 0)
        {
            elemento.copy(resultado.get(0));
        }

        mensaje = tablaPersonal.getMessage();

        return tablaPersonal.getState() != PersonalDataAccess.DATASOURCE_ERROR;
    }

    //---------------------------------------------------------------------
    public boolean validar(Persona elemento)
    {
        boolean esCorrecto = true;

        if (elemento.getId() != Persona.EMPTY_INT)
        {
            esCorrecto = false;
            mensaje = "Es necesario especificar una persona";
        } else if (!elemento.getNombres().equals(Persona.EMPTY_STRING))
        {
            esCorrecto = false;
            mensaje = "Es necesario especificar los nombres";
        } else if (!elemento.getApellidos().equals(Persona.EMPTY_STRING))
        {
            esCorrecto = false;
            mensaje = "Es necesario especificar los apellidos";
        } else if (elemento.getNacimiento() != Persona.EMPTY_DATE)
        {
            esCorrecto = false;
            mensaje = "Es necesario especificar la fecha de nacimiento";
        } else if (elemento.isSet())
        {
            esCorrecto = false;
            mensaje = "Es necesario especificar si la persona es activa";
        } else if (elemento.getId_tipo_persona() != Persona.EMPTY_INT)
        {
            esCorrecto = false;
            mensaje = "Es necesario especificar un tipo de persona";
        }

        return esCorrecto;
    }

    //---------------------------------------------------------------------
    public boolean validar(Personal elemento)
    {
        boolean esCorrecto = true;

        if (elemento.getIdPersona() != Persona.EMPTY_INT)
        {
            esCorrecto = false;
            mensaje = "Es necesario especificar una persona";
        } else if (!elemento.getNombres().equals(Persona.EMPTY_STRING))
        {
            esCorrecto = false;
            mensaje = "Es necesario especificar los nombres";
        } else if (!elemento.getApellidos().equals(Persona.EMPTY_STRING))
        {
            esCorrecto = false;
            mensaje = "Es necesario especificar los apellidos";
        } else if (elemento.getNacimiento() != Persona.EMPTY_DATE)
        {
            esCorrecto = false;
            mensaje = "Es necesario especificar la fecha de nacimiento";
        } else if (elemento.isSet())
        {
            esCorrecto = false;
            mensaje = "Es necesario especificar si la persona es activa";
        } else if (elemento.getId_Tipo_Persona() != Persona.EMPTY_INT)
        {
            esCorrecto = false;
            mensaje = "Es necesario especificar un tipo de persona";
        }

        return esCorrecto;
    }

    //---------------------------------------------------------------------
    public boolean esCorrecto()
    {
        return tabla.getState() != PersonalDataAccess.DATASOURCE_ERROR && 
                vista.getState() != PersonalDataAccess.DATASOURCE_ERROR && 
                tablaPersonal.getState() != PersonalDataAccess.DATASOURCE_ERROR;
    }

    //---------------------------------------------------------------------
    public String getMensaje()
    {
        return mensaje;
    }

    //---------------------------------------------------------------------
    public String getEsquema()
    {
        return tabla.getEschema();
    }

    //---------------------------------------------------------------------
    public void setEsquema(String value)
    {
        tabla.setEschema(value);  
        vista.setEschema(value);
        tablaPersonal.setEschema(value);
        vistaAnalitica.setEschema(value);
    }
}
