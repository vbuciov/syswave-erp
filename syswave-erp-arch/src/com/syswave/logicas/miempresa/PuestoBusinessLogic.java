package com.syswave.logicas.miempresa;

import com.syswave.datos.common.DataSourceManager;
import com.syswave.datos.miempresa.PuestosDataAccess;
import com.syswave.entidades.miempresa.Puesto;
import datalayer.api.IMediatorDataSource;
import java.util.List;

/**
 *
 * @author sis5
 */
public class PuestoBusinessLogic
{

    private String mensaje;
    private PuestosDataAccess tabla;

    //---------------------------------------------------------------------
    public PuestoBusinessLogic()
    {
        IMediatorDataSource mysource = DataSourceManager.getMainDataSourceInstance();
        tabla = new PuestosDataAccess(mysource);
    }

    //---------------------------------------------------------------------
    public PuestoBusinessLogic(String Esquema)
    {
        this();
        tabla.setEschema(Esquema);
    }

    //---------------------------------------------------------------------
    /**
     * Agrega un elemento nuevo
     *
     * @param nuevo El elemento a agregar
     * @return Indica si la operación se llevo acabo correctamente.
     */
    public boolean agregar(Puesto nuevo)
    {
        boolean resultado = tabla.Create(nuevo)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    /**
     * Agrega una lista de elementos nuevos
     *
     * @param nuevos
     * @return Indica si la operación se llevo acabo correctamente.
     */
    public boolean agregar(List<Puesto> nuevos)
    {
        boolean resultado = tabla.Create(nuevos)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    /**
     * Actualiza la información del elemento recibido
     *
     * @param elemento
     * @return Indica si la operación se llevo acabo correctamente..
     */
    public boolean actualizar(Puesto elemento)
    {
        boolean resultado = tabla.Update(elemento)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    /**
     * Actualiza la información de la lista de elementos recibidos.
     *
     * @param elementos
     * @return Indica si la operación se llevo acabo correctamente.
     */
    public boolean actualizar(List<Puesto> elementos)
    {
        boolean resultado = tabla.Update(elementos)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    /**
     * Borra el elemento recibido
     *
     * @param elemento
     * @return Indica si la operación se realizó correctamente
     */
    public boolean borrar(Puesto elemento)
    {
        boolean resultado = tabla.Delete(elemento)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    /**
     * Borra el elemento recibido
     *
     * @param elementos
     * @return Indica si la operación se realizó correctamente
     */
    public boolean borrar(List<Puesto> elementos)
    {
        boolean resultado = tabla.Delete(elementos)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    /**
     * Obtiene la información existente de modulos instalados.
     *
     * @return Una lista con todos los elementos.
     */
    public List<Puesto> obtenerLista()
    {
        List<Puesto> resultado = tabla.Retrieve();

        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    /**
     * Obtiene aquellos elementos que coinciden en información con el elemento
     * recibido
     *
     * @param elemento
     * @return Una lista con los elementos.
     */
    public List<Puesto> obtenerLista(Puesto elemento)
    {
        List<Puesto> resultado = tabla.Retrieve(elemento);

        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    /**
     * Obtiene la información existente de modulos instalados.
     *
     * @return Una lista con todos los elementos.
     */
    public List<Puesto> obtenerListaHojas()
    {
        List<Puesto> resultado = tabla.RetrieveLeafsPaths();

        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    /**
     * Obtiene aquellos elementos que coinciden en información con el elemento
     * recibido
     *
     * @param elemento
     * @return Una lista con los elementos.
     */
    public List<Puesto> obtenerListaHojas(Puesto elemento)
    {
        List<Puesto> resultado = tabla.RetrieveLeafsPaths(elemento);

        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    /**
     * Recarga la información del elemento recibido.
     *
     * @param elemento
     * @return Indica si la operación se llevo correctamente.
     */
    public boolean recargar(Puesto elemento)
    {
        List<Puesto> resultado = tabla.Retrieve(elemento);
        if (resultado.size() > 0)
        {
            elemento.copy(resultado.get(0));
        }

        mensaje = tabla.getMessage();

        return tabla.getState() != PuestosDataAccess.DATASOURCE_ERROR;
    }

    //---------------------------------------------------------------------
    public boolean valirdar(Puesto elemento)
    {
        boolean esCorrecto = true;

        if (elemento.getNombre().equals(Puesto.EMPTY_STRING))
        {
            esCorrecto = false;
            mensaje = "Es necesario especificar un nombre";
        }

        return esCorrecto;
    }

    //---------------------------------------------------------------------
    public boolean esCorrecto()
    {
        return tabla.getState() != PuestosDataAccess.DATASOURCE_ERROR; 
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
