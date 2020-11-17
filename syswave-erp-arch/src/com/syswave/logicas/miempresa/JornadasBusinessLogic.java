package com.syswave.logicas.miempresa;

import com.syswave.datos.common.DataSourceManager;
import com.syswave.datos.miempresa.JornadaDataAccess;
import com.syswave.entidades.miempresa.Jornada;
import datalayer.api.IMediatorDataSource;
import java.util.List;

/**
 *
 * @author sis5
 */
public class JornadasBusinessLogic
{

    private String mensaje;
    private JornadaDataAccess tabla;

    //--------------------------------------------------------------------
    public JornadasBusinessLogic()
    {
        IMediatorDataSource mysource = DataSourceManager.getMainDataSourceInstance();
        tabla = new JornadaDataAccess(mysource);
    }

    //--------------------------------------------------------------------
    public JornadasBusinessLogic(String esquema)
    {
        this();
        tabla.setEschema(esquema);
    }

    //--------------------------------------------------------------------
    public boolean agregar(Jornada nuevo)
    {
        boolean resultado = tabla.Create(nuevo)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }

    //--------------------------------------------------------------------
    public boolean agregar(List<Jornada> nuevos)
    {
        boolean resultado = tabla.Create(nuevos)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }

    //--------------------------------------------------------------------
    public boolean actualizar(Jornada elemento)
    {
        boolean resultado = tabla.Update(elemento)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }

    //--------------------------------------------------------------------
    public boolean actualizar(List<Jornada> elementos)
    {
        boolean resultado = tabla.Update(elementos)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }

    //--------------------------------------------------------------------
    public boolean borrar(Jornada elemento)
    {
        boolean resultado = tabla.Delete(elemento)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }

    //--------------------------------------------------------------------
    public boolean borrar(List<Jornada> elementos)
    {
        boolean resultado = tabla.Delete(elementos)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }

    //--------------------------------------------------------------------
    public List<Jornada> obtenerLista()
    {
        List<Jornada> resultado = tabla.Retrieve();
        mensaje = tabla.getMessage();
        return resultado;
    }

    //--------------------------------------------------------------------
    public List<Jornada> obtenerLista(Jornada elemento)
    {
        List<Jornada> resultado = tabla.Retrieve(elemento);

        mensaje = tabla.getMessage();

        return resultado;
    }

    //--------------------------------------------------------------------
    public boolean esCorrecto()
    {
        return tabla.getState() != JornadaDataAccess.DATASOURCE_ERROR;
    }

    //--------------------------------------------------------------------
    public String getMensaje()
    {
        return mensaje;
    }
    
    //--------------------------------------------------------------------
    public String getEsquema ()
    {
        return tabla.getEschema();
    }

}
