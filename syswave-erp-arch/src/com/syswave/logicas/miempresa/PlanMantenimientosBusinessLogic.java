package com.syswave.logicas.miempresa;

import com.syswave.datos.common.DataSourceManager;
import com.syswave.datos.miempresa.PlanMantenimientosDataAccess;
import com.syswave.entidades.miempresa.PlanMantenimiento;
import com.syswave.logicas.exceptions.BusinessOperationException;
import datalayer.api.IMediatorDataSource;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class PlanMantenimientosBusinessLogic
{

    private String mensaje;
    private PlanMantenimientosDataAccess tabla;
    //private PlanMantenimientoComercialRetrieve vista;

    //---------------------------------------------------------------------
    public PlanMantenimientosBusinessLogic()
    {
        IMediatorDataSource mysource = DataSourceManager.getMainDataSourceInstance();
        tabla = new PlanMantenimientosDataAccess(mysource);
        // vista = new PlanMantenimientoComercialRetrieve();
    }

    //---------------------------------------------------------------------
    public PlanMantenimientosBusinessLogic(String esquema)
    {
        this();
        tabla.setEschema(esquema);

        /*vista = new PlanMantenimientoComercialRetrieve();
         vista.setEschema(esquema);*/
    }

    //---------------------------------------------------------------------
    public boolean agregar(PlanMantenimiento nuevo)
    {
        boolean resultado = tabla.Create(nuevo)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    public boolean agregar(List<PlanMantenimiento> nuevos)
    {
        boolean resultado = tabla.Create(nuevos)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    public boolean actualizar(PlanMantenimiento elemento)
    {
        boolean resultado = tabla.Update(elemento)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    public boolean actualizar(List<PlanMantenimiento> elementos)
    {
        boolean resultado = tabla.Update(elementos)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    public boolean borrar(PlanMantenimiento elemento)
    {
        boolean resultado = tabla.Delete(elemento)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    public boolean borrar(List<PlanMantenimiento> elementos)
    {
        boolean resultado = tabla.Delete(elementos)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    public List<PlanMantenimiento> obtenerLista()
    {
        List<PlanMantenimiento> resultado = tabla.Retrieve();

        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    public List<PlanMantenimiento> obtenerLista(PlanMantenimiento elemento)
    {
        List<PlanMantenimiento> resultado = tabla.Retrieve(elemento);

        mensaje = tabla.getMessage();

        return resultado;
    }

      //---------------------------------------------------------------------
   /*public List<PlanMantenimientoComercial> obtenerListaVista ()
     {
     List<PlanMantenimientoComercial> resultado = vista.Retrieve();
      
     mensaje = vista.getMessage();
      
     return resultado;
     }
   
     //---------------------------------------------------------------------
     public List<PlanMantenimientoComercial> obtenerListaVista (PlanMantenimientoComercial elemento)
     {
     List<PlanMantenimientoComercial> resultado = vista.Retrieve(elemento);
      
     mensaje = vista.getMessage();
      
     return resultado;
     }*/
    //---------------------------------------------------------------------
    public boolean recargar(PlanMantenimiento elemento)
    {
        List<PlanMantenimiento> resultado = tabla.Retrieve(elemento);
        if (resultado.size() > 0)
        {
            elemento.copy(resultado.get(0));
        }

        mensaje = tabla.getMessage();

        return tabla.getState() != PlanMantenimientosDataAccess.DATASOURCE_ERROR;
    }

    //---------------------------------------------------------------------
    public boolean validar(PlanMantenimiento elemento) throws BusinessOperationException
    {
        boolean esCorrecto = true;

        if (elemento.getActividad().equals(PlanMantenimiento.EMPTY_STRING))
        {
            esCorrecto = false;
            throw new BusinessOperationException("Es necesario anotar un nombre a la actividad", 1);
        }

        else if (elemento.getIdVariante() == PlanMantenimiento.EMPTY_INT)
        {
            esCorrecto = false;
            throw new BusinessOperationException("Es necesario relacionar el plan con una presentacion", 1);
        }

        return esCorrecto;
    }

    //---------------------------------------------------------------------
    public boolean esCorrecto()
    {
        return tabla.getState() != PlanMantenimientosDataAccess.DATASOURCE_ERROR;
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
    public boolean guardar(List<PlanMantenimiento> elementos, List<PlanMantenimiento> borrados)
    {

        List<PlanMantenimiento> nuevos = new ArrayList<>();
        List<PlanMantenimiento> modificados = new ArrayList<>();

        for (PlanMantenimiento actual : elementos)
        {
            if (actual.isNew())
                nuevos.add(actual);

            else if (actual.isModified())
                modificados.add(actual);
        }

        return (nuevos.isEmpty() || agregar(nuevos)) && (modificados.isEmpty() || actualizar(modificados)) && (borrados == null || borrados.isEmpty() || borrar(borrados));
    }
}
