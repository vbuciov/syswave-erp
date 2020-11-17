/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.syswave.logicas.miempresa;

import com.syswave.datos.common.DataSourceManager;
import com.syswave.datos.miempresa.BienVarianteFotosDataAccess;
import com.syswave.entidades.miempresa.BienVarianteFoto;
import datalayer.api.IMediatorDataSource;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sis2
 */
public class BienVarianteFotosBusinessLogic
{
     private String mensaje;
    private BienVarianteFotosDataAccess tabla;
    
    //---------------------------------------------------------------------
    public BienVarianteFotosBusinessLogic()
    {
        IMediatorDataSource mysource = DataSourceManager.getMainDataSourceInstance();
        tabla = new BienVarianteFotosDataAccess(mysource);
    }

    //---------------------------------------------------------------------
    public BienVarianteFotosBusinessLogic(String esquema)
    {
        this();
        tabla.setEschema(esquema);
    }

    //---------------------------------------------------------------------
    public boolean agregar(BienVarianteFoto nuevo)
    {
        boolean resultado = tabla.Create(nuevo)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    public boolean agregar(List<BienVarianteFoto> nuevos)
    {
        boolean resultado = tabla.Create(nuevos)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    public boolean actualizar(BienVarianteFoto elemento)
    {
        boolean resultado = tabla.Update(elemento)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    public boolean actualizar(List<BienVarianteFoto> elementos)
    {
        boolean resultado = tabla.Update(elementos)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    public boolean borrar(BienVarianteFoto elemento)
    {
        boolean resultado = tabla.Delete(elemento)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    public boolean borrar(List<BienVarianteFoto> elementos)
    {
        boolean resultado = tabla.Delete(elementos)>0;
        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    public List<BienVarianteFoto> obtenerLista()
    {
        List<BienVarianteFoto> resultado = tabla.Retrieve();

        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    public List<BienVarianteFoto> obtenerLista(BienVarianteFoto elemento)
    {
        List<BienVarianteFoto> resultado = tabla.Retrieve(elemento);

        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    public List<BienVarianteFoto> obtenerMiniaturas()
    {
        List<BienVarianteFoto> resultado = tabla.retrieveThumbnail();

        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    public List<BienVarianteFoto> obtenerMiniaturas(BienVarianteFoto elemento)
    {
        List<BienVarianteFoto> resultado = tabla.retrieveThumbnail(elemento);

        mensaje = tabla.getMessage();

        return resultado;
    }

    //---------------------------------------------------------------------
    public boolean recargar(BienVarianteFoto elemento)
    {
        List<BienVarianteFoto> resultado = tabla.Retrieve(elemento);
        if (resultado.size() > 0)
        {
            elemento.copy(resultado.get(0));
        }

        mensaje = tabla.getMessage();

        return tabla.getState() != BienVarianteFotosDataAccess.DATASOURCE_ERROR;
    }

    //---------------------------------------------------------------------
    public boolean validar(BienVarianteFoto elemento)
    {
        boolean esCorrecto = true;

        if (elemento.getIdVariante()!= BienVarianteFoto.EMPTY_INT)
        {
            esCorrecto = false;
            mensaje = "Es necesario relacionar una presentación";
        }

        return esCorrecto;
    }

    //---------------------------------------------------------------------
    public boolean esCorrecto()
    {
        return tabla.getState() != BienVarianteFotosDataAccess.DATASOURCE_ERROR;
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
    public boolean guardar(List<BienVarianteFoto> elementos, List<BienVarianteFoto> borrados)
    {
        List<BienVarianteFoto> nuevos = new ArrayList<>();
        List<BienVarianteFoto> modificados = new ArrayList<>();

        for (BienVarianteFoto actual : elementos)
        {
            if (actual.isNew())
                nuevos.add(actual);

            else if (actual.isModified())
                modificados.add(actual);
        }

        return (nuevos.isEmpty() || agregar(nuevos)) && (modificados.isEmpty() || actualizar(modificados)) && (borrados.isEmpty() || borrar(borrados));
    }
    
    //---------------------------------------------------------------------
    public boolean guardar(BienVarianteFoto elemento, BienVarianteFoto borrado)
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