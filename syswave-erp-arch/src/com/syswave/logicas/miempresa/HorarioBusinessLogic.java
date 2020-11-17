package com.syswave.logicas.miempresa;

import com.syswave.datos.common.DataSourceManager;
import com.syswave.datos.miempresa.HorariosDataAccess;
import com.syswave.entidades.miempresa.Horario;
import datalayer.api.IMediatorDataSource;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sis5
 */
public class HorarioBusinessLogic
{

    private String mensaje;
    private HorariosDataAccess tabla;

    //--------------------------------------------------------------------
    public HorarioBusinessLogic()
    {
        IMediatorDataSource mysource = DataSourceManager.getMainDataSourceInstance();
        tabla = new HorariosDataAccess(mysource);
    }

    //--------------------------------------------------------------------
    public HorarioBusinessLogic(String esquema)
    {
        this();
        tabla.setEschema(esquema);
    }

    //--------------------------------------------------------------------
    public boolean agregar(Horario nuevo)
    {
        boolean resultado = tabla.Create(nuevo)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }

    //--------------------------------------------------------------------
    public boolean agregar(List<Horario> nuevos)
    {
        boolean resultado = tabla.Create(nuevos)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }

    //--------------------------------------------------------------------
    public boolean actualizar(Horario elemento)
    {
        boolean resultado = tabla.Update(elemento)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }

    //--------------------------------------------------------------------
    public boolean actualizar(List<Horario> elementos)
    {
        boolean resultado = tabla.Update(elementos)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }

    //--------------------------------------------------------------------
    public boolean borrar(Horario elemento)
    {
        boolean resultado = tabla.Delete(elemento)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }

    //--------------------------------------------------------------------
    public boolean borrar(List<Horario> elementos)
    {
        boolean resultado = tabla.Delete(elementos)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }

    //--------------------------------------------------------------------
    public List<Horario> obtenerLista()
    {
        List<Horario> resultado = tabla.Retrieve();
        mensaje = tabla.getMessage();
        return resultado;
    }

    //--------------------------------------------------------------------
    public List<Horario> obtenerLista(Horario filtro)
    {
        List<Horario> resultado = tabla.Retrieve(filtro);
        mensaje = tabla.getMessage();
        return resultado;
    }

    //--------------------------------------------------------------------
    public boolean esCorrecto()
    {
        return tabla.getState() != HorariosDataAccess.DATASOURCE_ERROR;
    }

    //--------------------------------------------------------------------
    public String getMensaje()
    {
        return mensaje;
    }
    
    //--------------------------------------------------------------------
    public boolean guardar(List<Horario> elementos, List<Horario> borrados)
   {
      List<Horario> nuevos = new ArrayList<>();
      List<Horario> modificados = new ArrayList<>();
      
      for (Horario actual : elementos)
      {
         if (actual.isNew())
            nuevos.add(actual);

         else if (actual.isModified())
            modificados.add(actual);
      }
         
      return (nuevos.isEmpty() || agregar(nuevos)) && (modificados.isEmpty() || actualizar(modificados)) &&  (borrados == null || borrados.isEmpty() || borrar(borrados));
  
   }

}
