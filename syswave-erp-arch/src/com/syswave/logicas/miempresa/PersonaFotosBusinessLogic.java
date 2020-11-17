package com.syswave.logicas.miempresa;

import com.syswave.datos.common.DataSourceManager;
import com.syswave.datos.miempresa.PersonaFotosDataAccess;
import com.syswave.entidades.miempresa.PersonaFoto;
import datalayer.api.IMediatorDataSource;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class PersonaFotosBusinessLogic
{
    private String mensaje;
    private PersonaFotosDataAccess tabla;
    
    //---------------------------------------------------------------------
    public PersonaFotosBusinessLogic()
    {
        IMediatorDataSource mysource = DataSourceManager.getMainDataSourceInstance();
        tabla = new PersonaFotosDataAccess(mysource);
    }

    //---------------------------------------------------------------------
    public PersonaFotosBusinessLogic(String esquema)
    {
        this();
        tabla.setEschema(esquema);
    }

    //---------------------------------------------------------------------
    public boolean agregar(PersonaFoto nuevo)
    {
        boolean resultado = tabla.Create(nuevo)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    public boolean agregar(List<PersonaFoto> nuevos)
    {
        boolean resultado = tabla.Create(nuevos)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    public boolean actualizar(PersonaFoto elemento)
    {
        boolean resultado = tabla.Update(elemento)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    public boolean actualizar(List<PersonaFoto> elementos)
    {
        boolean resultado = tabla.Update(elementos)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    public boolean borrar(PersonaFoto elemento)
    {
        boolean resultado = tabla.Delete(elemento)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    public boolean borrar(List<PersonaFoto> elementos)
    {
        boolean resultado = tabla.Delete(elementos)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    public List<PersonaFoto> obtenerLista()
    {
        List<PersonaFoto> resultado = tabla.Retrieve();

        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    public List<PersonaFoto> obtenerLista(PersonaFoto elemento)
    {
        List<PersonaFoto> resultado = tabla.Retrieve(elemento);

        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    public List<PersonaFoto> obtenerMiniaturas()
    {
        List<PersonaFoto> resultado = tabla.retrieveThumbnail();

        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    public List<PersonaFoto> obtenerMiniaturas(PersonaFoto elemento)
    {
        List<PersonaFoto> resultado = tabla.retrieveThumbnail(elemento);

        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    public boolean recargar(PersonaFoto elemento)
    {
        List<PersonaFoto> resultado = tabla.Retrieve(elemento);
        if (resultado.size() > 0)
        {
            elemento.copy(resultado.get(0));
        }

        mensaje = tabla.getMessage();

        return tabla.getState() != PersonaFotosDataAccess.DATASOURCE_ERROR;
    }

    //---------------------------------------------------------------------
    public boolean validar(PersonaFoto elemento)
    {
        boolean esCorrecto = true;

        if (elemento.getIdPersona()!= PersonaFoto.EMPTY_INT)
        {
            esCorrecto = false;
            mensaje = "Es necesario relacionar una persona";
        }

        return esCorrecto;
    }

    //---------------------------------------------------------------------
    public boolean esCorrecto()
    {
        return tabla.getState() != PersonaFotosDataAccess.DATASOURCE_ERROR; 
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
    public boolean guardar(List<PersonaFoto> elementos, List<PersonaFoto> borrados)
    {
        List<PersonaFoto> nuevos = new ArrayList<>();
        List<PersonaFoto> modificados = new ArrayList<>();

        for (PersonaFoto actual : elementos)
        {
            if (actual.isNew())
                nuevos.add(actual);

            else if (actual.isModified())
                modificados.add(actual);
        }

        return (nuevos.isEmpty() || agregar(nuevos)) && (modificados.isEmpty() || actualizar(modificados)) && (borrados.isEmpty() || borrar(borrados));
    }
    
    //---------------------------------------------------------------------
    public boolean guardar(PersonaFoto elemento, PersonaFoto borrado)
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
