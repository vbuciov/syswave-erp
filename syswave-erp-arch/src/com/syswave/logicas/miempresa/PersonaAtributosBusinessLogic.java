package com.syswave.logicas.miempresa;

import com.syswave.datos.common.DataSourceManager;
import com.syswave.datos.miempresa.PersonaAtributosDataAccess;
import com.syswave.entidades.miempresa.PersonaAtributo;
import datalayer.api.IMediatorDataSource;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class PersonaAtributosBusinessLogic
{
    private String mensaje;
    private PersonaAtributosDataAccess tabla;
    
    //---------------------------------------------------------------------
    public PersonaAtributosBusinessLogic()
    {
        IMediatorDataSource mysource = DataSourceManager.getMainDataSourceInstance();
        tabla = new PersonaAtributosDataAccess(mysource);
    }

    //---------------------------------------------------------------------
    public PersonaAtributosBusinessLogic(String esquema)
    {
       this();
        tabla.setEschema(esquema);
    }

    //---------------------------------------------------------------------
    public boolean agregar(PersonaAtributo nuevo)
    {
        boolean resultado = tabla.Create(nuevo)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    public boolean agregar(List<PersonaAtributo> nuevos)
    {
        boolean resultado = tabla.Create(nuevos)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    public boolean actualizar(PersonaAtributo elemento)
    {
        boolean resultado = tabla.Update(elemento)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    public boolean actualizar(List<PersonaAtributo> elementos)
    {
        boolean resultado = tabla.Update(elementos)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    public boolean borrar(PersonaAtributo elemento)
    {
        boolean resultado = tabla.Delete(elemento)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    public boolean borrar(List<PersonaAtributo> elementos)
    {
        boolean resultado = tabla.Delete(elementos)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    public List<PersonaAtributo> obtenerLista()
    {
        List<PersonaAtributo> resultado = tabla.Retrieve();

        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    public List<PersonaAtributo> obtenerLista(PersonaAtributo elemento)
    {
        List<PersonaAtributo> resultado = tabla.Retrieve(elemento);

        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    public List<PersonaAtributo> obtenerListaSinPadecimientos(PersonaAtributo elemento)
    {
        List<PersonaAtributo> resultado = tabla.retrieveWhioutDiseases(elemento);

        mensaje = tabla.getMessage();

        return resultado;
    }

    
    //---------------------------------------------------------------------
    public List<PersonaAtributo> obtenerListaPadecimientos(PersonaAtributo elemento)
    {
        List<PersonaAtributo> resultado = tabla.retrieveWhithDiseases(elemento);

        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    public boolean recargar(PersonaAtributo elemento)
    {
        List<PersonaAtributo> resultado = tabla.Retrieve(elemento);
        if (resultado.size() > 0)
        {
            elemento.copy(resultado.get(0));
        }

        mensaje = tabla.getMessage();

        return tabla.getState() != PersonaAtributosDataAccess.DATASOURCE_ERROR;
    }

    //---------------------------------------------------------------------
    public boolean validar(PersonaAtributo elemento)
    {
        boolean esCorrecto = true;

        if (elemento.getIdPersona()!= PersonaAtributo.EMPTY_INT)
        {
            esCorrecto = false;
            mensaje = "Es necesario relacionar una persona";
        }

        return esCorrecto;
    }

    //---------------------------------------------------------------------
    public boolean esCorrecto()
    {
        return tabla.getState() != PersonaAtributosDataAccess.DATASOURCE_ERROR; 
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
    public boolean guardar(List<PersonaAtributo> elementos, List<PersonaAtributo> borrados)
    {
        List<PersonaAtributo> nuevos = new ArrayList<>();
        List<PersonaAtributo> modificados = new ArrayList<>();

        for (PersonaAtributo actual : elementos)
        {
            if (actual.isNew())
                nuevos.add(actual);

            else if (actual.isModified())
                modificados.add(actual);
        }

        return (nuevos.isEmpty() || agregar(nuevos)) && (modificados.isEmpty() || actualizar(modificados)) && (borrados == null || borrados.isEmpty() || borrar(borrados));
    }
    
    //---------------------------------------------------------------------
    public boolean guardar(PersonaAtributo elemento, PersonaAtributo borrado)
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
