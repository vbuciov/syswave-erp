package com.syswave.logicas.miempresa;

import com.syswave.datos.common.DataSourceManager;
import com.syswave.datos.miempresa.PersonaIncidenciasDataAccess;
import com.syswave.entidades.miempresa.PersonaTieneIncidencia;
import datalayer.api.IMediatorDataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Carlos Soto
 */
public class PersonaIncidenciasBusinessLogic
{

    private PersonaIncidenciasDataAccess tabla;
    private String mensaje;

    public PersonaIncidenciasBusinessLogic()
    {
        IMediatorDataSource mysource = DataSourceManager.getMainDataSourceInstance();
        tabla = new PersonaIncidenciasDataAccess(mysource);
    }

    public PersonaIncidenciasBusinessLogic(String esquema)
    {
        this();
        tabla.setEschema(esquema);
    }

    //--------------------------------------------------------------------
    /**
     * Agrega el elemento especificado en una transaccion.
     */
    public boolean agregar(PersonaTieneIncidencia nuevo)
    {
        boolean resultado = tabla.Create(nuevo)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }

    //--------------------------------------------------------------------
    /**
     * Agrega los elementos especificados en una misma transaccion.
     */
    public boolean agregar(List<PersonaTieneIncidencia> nuevos)
    {
        boolean resultado = tabla.Create(nuevos)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }

    //--------------------------------------------------------------------
    /**
     * Actualiza el elemento especificado en una transaccion.
     */
    public boolean actualizar(PersonaTieneIncidencia elemento)
    {
        boolean resultado = tabla.Update(elemento)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }

    //--------------------------------------------------------------------
    /**
     * Actualiza todos los elementos especificados en una misma transaccion.
     */
    public boolean actualizar(List<PersonaTieneIncidencia> elementos)
    {
        boolean resultado = tabla.Update(elementos)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    /**
     * Borra el elemento especificado en una transaccion.
     */
    public boolean borrar(PersonaTieneIncidencia elemento)
    {
        boolean resultado = tabla.Delete(elemento)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    /**
     * Borra los elementos especificado en una sola transaccion.
     */
    public boolean borrar(List<PersonaTieneIncidencia> elementos)
    {
        boolean resultado = tabla.Delete(elementos)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    /**
     * Obtiene todos los elementos.
     */
    public List<PersonaTieneIncidencia> obtenerLista()
    {
        List<PersonaTieneIncidencia> resultado = tabla.Retrieve();

        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    /**
     * Obtiene todos los elementos que cumplan con el criterio.
     */
    public List<PersonaTieneIncidencia> obtenerLista(PersonaTieneIncidencia elemento)
    {
        List<PersonaTieneIncidencia> resultado = tabla.Retrieve(elemento);

        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    /**
     * Recarga la información del elemento especificado.
     */
    public boolean recargar(PersonaTieneIncidencia elemento)
    {
        List<PersonaTieneIncidencia> resultado = tabla.Retrieve(elemento);
        if (resultado.size() > 0)
        {
            elemento.copy(resultado.get(0));
        }

        mensaje = tabla.getMessage();

        return tabla.getState() != PersonaIncidenciasDataAccess.DATASOURCE_ERROR;
    }

    //---------------------------------------------------------------------
    public boolean guardar(List<PersonaTieneIncidencia> elementos)
    {
        List<PersonaTieneIncidencia> nuevos = new ArrayList<>();
        List<PersonaTieneIncidencia> modificados = new ArrayList<>();

        for (PersonaTieneIncidencia actual : elementos)
        {
            if (actual.isNew())
                nuevos.add(actual);

            else if (actual.isModified())
                modificados.add(actual);
        }

        return (nuevos.isEmpty() || agregar(nuevos)) && (modificados.isEmpty() || actualizar(modificados));

    }

    //---------------------------------------------------------------------
    public boolean guardar(List<PersonaTieneIncidencia> elementos, List<PersonaTieneIncidencia> borrados)
    {
        List<PersonaTieneIncidencia> nuevos = new ArrayList<>();
        List<PersonaTieneIncidencia> modificados = new ArrayList<>();

        for (PersonaTieneIncidencia actual : elementos)
        {
            if (actual.isNew())
                nuevos.add(actual);

            else if (actual.isModified())
                modificados.add(actual);
        }

        return (nuevos.isEmpty() || agregar(nuevos)) && (modificados.isEmpty() || actualizar(modificados)) && (borrados == null || borrados.isEmpty() || borrar(borrados));

    }

   //---------------------------------------------------------------------
   /*public boolean validar (PersonaTieneIncidencias elemento)
     {
     boolean esCorrecto = true;
      
     if (!elemento.getNombre().equals(PersonaTieneIncidencias.EMPTY_STRING))
     {
     esCorrecto = false;
     mensaje = "Es necesario especificar los nombres";
     }
       
     return esCorrecto;
     }
     */
    //---------------------------------------------------------------------
    public boolean esCorrecto()
    {
        return tabla.getState() != PersonaIncidenciasDataAccess.DATASOURCE_ERROR;
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
    }

}
