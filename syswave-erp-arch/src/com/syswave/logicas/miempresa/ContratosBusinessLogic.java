package com.syswave.logicas.miempresa;

import com.syswave.datos.common.DataSourceManager;
import com.syswave.datos.miempresa.ContratosDataAccess;
import com.syswave.entidades.miempresa.Contrato;
import datalayer.api.IMediatorDataSource;
import java.util.List;

/**
 *
 * @author sis5
 */
public class ContratosBusinessLogic
{

    private String mensaje;
    private ContratosDataAccess tabla;

    //--------------------------------------------------------------------
    public ContratosBusinessLogic()
    {
        IMediatorDataSource mysource = DataSourceManager.getMainDataSourceInstance();
        tabla = new ContratosDataAccess(mysource);
    }

    //--------------------------------------------------------------------
    public ContratosBusinessLogic(String esquema)
    {
        this();
        tabla.setEschema(esquema);
    }

    //--------------------------------------------------------------------
    public boolean agregar(Contrato nuevo)
    {
        boolean resultado = tabla.Create(nuevo)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }

    //--------------------------------------------------------------------
    public boolean agregar(List<Contrato> nuevos)
    {
        boolean resultado = tabla.Create(nuevos)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }

    //--------------------------------------------------------------------
    public boolean actualizar(Contrato elemento)
    {
        boolean resultado = tabla.Update(elemento)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }

    //--------------------------------------------------------------------
    public boolean actualizar(List<Contrato> elementos)
    {
        boolean resultado = tabla.Update(elementos)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }

    //--------------------------------------------------------------------
    public boolean borrar(Contrato elemento)
    {
        boolean resultado = tabla.Delete(elemento)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }

    //--------------------------------------------------------------------
    public boolean borrar(List<Contrato> elementos)
    {
        boolean resultado = tabla.Delete(elementos)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }

    //--------------------------------------------------------------------
    public List<Contrato> obtenerLista()
    {
        List<Contrato> resultado = tabla.Retrieve();
        mensaje = tabla.getMessage();
        return resultado;
    }

    //--------------------------------------------------------------------
    public List<Contrato> obtenerLista(Contrato filtro)
    {
        List<Contrato> resultado = tabla.Retrieve(filtro);
        mensaje = tabla.getMessage();
        
        return resultado;
    }
    
     //--------------------------------------------------------------------
    public List<Contrato> obtenerListaSinContenido()
    {
        List<Contrato> resultado = tabla.retrieveSmall();
        mensaje = tabla.getMessage();
        return resultado;
    }

    //--------------------------------------------------------------------
    public List<Contrato> obtenerListaSinContenido(Contrato filtro)
    {
        List<Contrato> resultado = tabla.retrieveSmall(filtro);
        mensaje = tabla.getMessage();
        
        return resultado;
    }
    
    
    //---------------------------------------------------------------------
    public boolean recargar(Contrato elemento)
    {
        List<Contrato> resultado = tabla.Retrieve(elemento);
        if (resultado.size() > 0)
        {
            elemento.copy(resultado.get(0));
        }

        mensaje = tabla.getMessage();

        return tabla.getState() != ContratosDataAccess.DATASOURCE_ERROR;
    }

    //--------------------------------------------------------------------
    public boolean esCorrecto()
    {
        return tabla.getState() != ContratosDataAccess.DATASOURCE_ERROR;
    }

    //--------------------------------------------------------------------
    public String getMensaje()
    {
        return mensaje;
    }
    
    
    //--------------------------------------
    public Contrato[] obtenerIdNombreContratos()
    {
        List<Contrato> listaC = tabla.Retrieve();
        Contrato[] resultado = (Contrato[]) listaC.toArray();
        
        return resultado;
    }
}
