package com.syswave.logicas.miempresa;

import com.syswave.datos.common.DataSourceManager;
import com.syswave.datos.miempresa.PersonaEducacionesDataAccess;
import com.syswave.entidades.miempresa.PersonaEducacion;
import datalayer.api.IMediatorDataSource;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class PersonaEducacionesBusinessLogic
{
    private String mensaje;
    private PersonaEducacionesDataAccess tabla;
    
    //---------------------------------------------------------------------
    public PersonaEducacionesBusinessLogic()
    {
        IMediatorDataSource mysource = DataSourceManager.getMainDataSourceInstance();
        tabla = new PersonaEducacionesDataAccess(mysource);
    }

    //---------------------------------------------------------------------
    public PersonaEducacionesBusinessLogic(String esquema)
    {
        this();
        tabla.setEschema(esquema);
    }

    //---------------------------------------------------------------------
    public boolean agregar(PersonaEducacion nuevo)
    {
        boolean resultado = tabla.Create(nuevo)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    public boolean agregar(List<PersonaEducacion> nuevos)
    {
        boolean resultado = tabla.Create(nuevos)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    public boolean actualizar(PersonaEducacion elemento)
    {
        boolean resultado = tabla.Update(elemento)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    public boolean actualizar(List<PersonaEducacion> elementos)
    {
        boolean resultado = tabla.Update(elementos)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    public boolean borrar(PersonaEducacion elemento)
    {
        boolean resultado = tabla.Delete(elemento)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    public boolean borrar(List<PersonaEducacion> elementos)
    {
        boolean resultado = tabla.Delete(elementos)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    public List<PersonaEducacion> obtenerLista()
    {
        List<PersonaEducacion> resultado = tabla.Retrieve();

        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    public List<PersonaEducacion> obtenerLista(PersonaEducacion elemento)
    {
        List<PersonaEducacion> resultado = tabla.Retrieve(elemento);

        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    public boolean recargar(PersonaEducacion elemento)
    {
        List<PersonaEducacion> resultado = tabla.Retrieve(elemento);
        if (resultado.size() > 0)
        {
            elemento.copy(resultado.get(0));
        }

        mensaje = tabla.getMessage();

        return tabla.getState() != PersonaEducacionesDataAccess.DATASOURCE_ERROR;
    }

    //---------------------------------------------------------------------
    public boolean validar(PersonaEducacion elemento)
    {
        boolean esCorrecto = true;

        if (elemento.getIdPersona()!= PersonaEducacion.EMPTY_INT)
        {
            esCorrecto = false;
            mensaje = "Es necesario relacionar una persona";
        }

        return esCorrecto;
    }

    //---------------------------------------------------------------------
    public boolean esCorrecto()
    {
        return tabla.getState() != PersonaEducacionesDataAccess.DATASOURCE_ERROR;
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
        //vista.setEschema(value);
    }

    //---------------------------------------------------------------------
    public boolean guardar(List<PersonaEducacion> elementos, List<PersonaEducacion> borrados)
    {
        List<PersonaEducacion> nuevos = new ArrayList<>();
        List<PersonaEducacion> modificados = new ArrayList<>();

        for (PersonaEducacion actual : elementos)
        {
            if (actual.isNew())
                nuevos.add(actual);

            else if (actual.isModified())
                modificados.add(actual);
        }

        return (nuevos.isEmpty() || agregar(nuevos)) && (modificados.isEmpty() || actualizar(modificados)) && (borrados == null ||borrados.isEmpty() || borrar(borrados));
    }
    
    //---------------------------------------------------------------------
    public boolean guardar(PersonaEducacion elemento, PersonaEducacion borrado)
    {
        if (elemento.isNew())
            return agregar(elemento);
        
        else if (elemento.isModified())
            return actualizar(elemento);
        
        if (borrado != null )
            return borrar(borrado);
            
        return false;
    }
}
