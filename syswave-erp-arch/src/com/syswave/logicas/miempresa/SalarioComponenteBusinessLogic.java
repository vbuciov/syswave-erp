package com.syswave.logicas.miempresa;

import com.syswave.datos.common.DataSourceManager;
import com.syswave.datos.miempresa.SalarioComponenteDataAccess;
import com.syswave.entidades.miempresa.SalarioComponente;
import datalayer.api.IMediatorDataSource;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sis5
 */
public class SalarioComponenteBusinessLogic
{

    SalarioComponenteDataAccess tabla;
    private String mensaje;

    public SalarioComponenteBusinessLogic()
    {
        IMediatorDataSource mysource = DataSourceManager.getMainDataSourceInstance();
        tabla = new SalarioComponenteDataAccess(mysource);
    }

    public SalarioComponenteBusinessLogic(String esquema)
    {
        this();
        tabla.setEschema(esquema);
    }

    //---------------------------------------------------------------------
    public boolean agregar(SalarioComponente nuevo)
    {
        boolean resultado = tabla.Create(nuevo)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    public boolean agregar(List<SalarioComponente> nuevos)
    {
        boolean resultado = tabla.Create(nuevos)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    public boolean actualizar(SalarioComponente elemento)
    {
        boolean resultado = tabla.Update(elemento)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    public boolean actualizar(List<SalarioComponente> elementos)
    {
        boolean resultado = tabla.Update(elementos)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    public boolean borrar(SalarioComponente elemento)
    {
        boolean resultado = tabla.Delete(elemento)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    public boolean borrar(List<SalarioComponente> elementos)
    {
        boolean resultado = tabla.Delete(elementos)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    public List<SalarioComponente> obtenerLista()
    {
        List<SalarioComponente> resultado = tabla.Retrieve();

        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    public List<SalarioComponente> obtenerLista(SalarioComponente filter)
    {
        List<SalarioComponente> resultado = tabla.Retrieve(filter);

        mensaje = tabla.getMessage();

        return resultado;
    }

    //--------------------------------------------------------------------
    public boolean guardar(List<SalarioComponente> elementos, List<SalarioComponente> borrados)
    {
        List<SalarioComponente> nuevos = new ArrayList<>();
        List<SalarioComponente> modificados = new ArrayList<>();

        for (SalarioComponente actual : elementos)
        {
            if (actual.isNew())
                nuevos.add(actual);

            else if (actual.isModified())
                modificados.add(actual);
        }

        return (nuevos.isEmpty() || agregar(nuevos)) && (modificados.isEmpty() || actualizar(modificados)) && (borrados == null || borrados.isEmpty() || borrar(borrados));

    }

    //---------------------------------------------------------------------
    public boolean esCorrecto()
    {
        return tabla.getState() != SalarioComponenteDataAccess.DATASOURCE_ERROR;
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
