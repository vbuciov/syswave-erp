package com.syswave.logicas.miempresa;

import com.syswave.datos.common.DataSourceManager;
import com.syswave.datos.miempresa.ImpresionRetrieve;
import com.syswave.datos.miempresa.PersonasContratosDataAccess;
import com.syswave.entidades.miempresa_vista.ImpresionContrato;
import com.syswave.entidades.miempresa.PersonaContrato;
import datalayer.api.IMediatorDataSource;
import java.util.List;

/**
 *
 * @author sis5
 */
public class PersonaContratosBusinessLogic
{
    private String mensaje;
    private PersonasContratosDataAccess tabla;
    private ImpresionRetrieve consultaDatos;

    //--------------------------------------------------------------------
    public PersonaContratosBusinessLogic()
    {
        IMediatorDataSource mysource = DataSourceManager.getMainDataSourceInstance();
        tabla = new PersonasContratosDataAccess(mysource);
        consultaDatos = new ImpresionRetrieve(mysource);
    }

    //--------------------------------------------------------------------
    public PersonaContratosBusinessLogic(String esquema)
    {
        this();
        tabla.setEschema(esquema);
        consultaDatos.setEschema(esquema);
    }

    //---------------------------------------------------------------------
    public boolean agregar(PersonaContrato nuevo)
    {
        boolean resultado = tabla.Create(nuevo)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    public boolean agregar(List<PersonaContrato> nuevos)
    {
        boolean resultado = tabla.Create(nuevos)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    public boolean actualizar(PersonaContrato elemento)
    {
        boolean resultado = tabla.Update(elemento)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    public boolean actualizar(List<PersonaContrato> elementos)
    {
        boolean resultado = tabla.Update(elementos)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    public boolean borrar(PersonaContrato elemento)
    {
        boolean resultado = tabla.Delete(elemento)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    public boolean borrar(List<PersonaContrato> elementos)
    {
        boolean resultado = tabla.Delete(elementos)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    public List<PersonaContrato> obtenerLista()
    {
        List<PersonaContrato> resultado = tabla.Retrieve();

        mensaje = tabla.getMessage();

        return resultado;
    }
    
    //---------------------------------------------------------------------
    public ImpresionContrato obtieneDatosImpresion(PersonaContrato pers)
    {
        ImpresionContrato resultado = consultaDatos.buscarDatosContrato(pers);
        
        mensaje = consultaDatos.getMessage();
        
        return resultado;
    }

    //---------------------------------------------------------------------
    public boolean esCorrecto()
    {
        return tabla.getState() != PersonasContratosDataAccess.DATASOURCE_ERROR; 
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
