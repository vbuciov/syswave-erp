package com.syswave.entidades.miempresa_vista;

import com.syswave.entidades.miempresa.DocumentoContadoMovimiento;
import com.syswave.entidades.miempresa.TipoComprobante;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class DocumentoContadoMovimiento_5FN extends DocumentoContadoMovimiento
{

    String tipoComprobante;

    public String getNombre()
    {
        return tipoComprobante;
    }

    public void setNombre(String value)
    {
        tipoComprobante = value;
    }
}
