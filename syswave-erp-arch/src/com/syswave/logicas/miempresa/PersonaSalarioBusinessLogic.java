package com.syswave.logicas.miempresa;

import com.syswave.datos.common.DataSourceManager;
import com.syswave.datos.miempresa.PersonaSalariosDataAccess;
import com.syswave.entidades.miempresa.PersonaSalario;
import datalayer.api.IMediatorDataSource;
import java.util.List;

/**
 *
 * @author sis5
 */
public class PersonaSalarioBusinessLogic
{
    PersonaSalariosDataAccess tabla;
    private String mensaje;
    
    public PersonaSalarioBusinessLogic()
    {
        IMediatorDataSource mysource = DataSourceManager.getMainDataSourceInstance();
        tabla = new PersonaSalariosDataAccess(mysource);
    }
    
    public PersonaSalarioBusinessLogic(String esquema)
    {
        this();
        tabla.setEschema(esquema);
    }
    
    //---------------------------------------------------------------------
    public boolean agregar(PersonaSalario nuevo)
    {
        boolean resultado = tabla.Create(nuevo)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    public boolean agregar(List<PersonaSalario> nuevos)
    {
        boolean resultado = tabla.Create(nuevos)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }
    
    //---------------------------------------------------------------------
    public boolean actualizar(PersonaSalario elemento)
    {
        boolean resultado = tabla.Update(elemento)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    public boolean actualizar(List<PersonaSalario> elementos)
    {
        boolean resultado = tabla.Update(elementos)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }
    
    //---------------------------------------------------------------------
    public boolean borrar(PersonaSalario elemento)
    {
        boolean resultado = tabla.Delete(elemento)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    public boolean borrar(List<PersonaSalario> elementos)
    {
        boolean resultado = tabla.Delete(elementos)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }
    
    //---------------------------------------------------------------------
    public List<PersonaSalario> obtenerLista()
    {
        List<PersonaSalario> resultado = tabla.Retrieve();

        mensaje = tabla.getMessage();

        return resultado;
    }
    
    //---------------------------------------------------------------------
    public List<PersonaSalario> obtenerLista(PersonaSalario filter)
    {
        List<PersonaSalario> resultado = tabla.Retrieve(filter);

        mensaje = tabla.getMessage();

        return resultado;
    }
    
    //---------------------------------------------------------------------
    public boolean esCorrecto()
    {
        return tabla.getState() != PersonaSalariosDataAccess.DATASOURCE_ERROR;
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
