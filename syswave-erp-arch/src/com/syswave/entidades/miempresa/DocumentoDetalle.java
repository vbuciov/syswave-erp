package com.syswave.entidades.miempresa;

import com.syswave.entidades.keys.CompositeKeyByIdDocumentoConsecutivo;

/**
 * Es cada línea de un documento o comprobante.
 *
 * @author Victor Manuel Bucio Vargas
 */
public class DocumentoDetalle extends CompositeKeyByIdDocumentoConsecutivo
{

    private float cantidad, precio, importe, monto, importe_neto;
    private int factor;
    private String descripcion;

    protected ForeignKey navigation;

    //----------------------------------------------------------------------
    public DocumentoDetalle()
    {
        super();
        createAtributes();
        initAtributes();
    }

    //---------------------------------------------------------------------
    public DocumentoDetalle(DocumentoDetalle that)
    {
        super();
        createAtributes();
        assign(that);
    }

    //---------------------------------------------------------------------
    private void createAtributes()
    {
        navigation = new ForeignKey();
    }

    //---------------------------------------------------------------------
    private void initAtributes()
    {
        cantidad = EMPTY_FLOAT;
        precio = EMPTY_FLOAT;
        importe = EMPTY_FLOAT;
        monto = EMPTY_FLOAT;
        factor = EMPTY_INT;
        importe_neto = EMPTY_FLOAT;
        descripcion = EMPTY_STRING;
    }

    //---------------------------------------------------------------------
    private void assign(DocumentoDetalle that)
    {
        super.assign(that);
        cantidad = that.cantidad;
        precio = that.precio;
        importe = that.importe;
        monto = that.monto;
        factor = that.factor;
        importe_neto = that.importe_neto;
        descripcion = that.descripcion;
        navigation = that.navigation;
    }

    //---------------------------------------------------------------------
    @Override
    public int getIdDocumento()
    {
        return navigation.getFk_documento_detalle() != null
                ? navigation.getFk_documento_detalle().getId()
                : super.getIdDocumento();
    }

    //---------------------------------------------------------------------
    @Override
    public int getIdDocumento_Viejo()
    {
        return navigation.getFk_documento_detalle() != null
                ? navigation.getFk_documento_detalle().getId_Viejo()
                : super.getIdDocumento_Viejo();
    }

    //---------------------------------------------------------------------
    @Override
    public void setIdDocumento(int value)
    {
        super.setIdDocumento(value);
        navigation.releaseDocumento();
    }

    //---------------------------------------------------------------------
    public Documento getHasOneDocumento()
    {
        return navigation.getFk_documento_detalle() != null
                ? navigation.getFk_documento_detalle()
                : navigation.joinDocumento(super.getIdDocumento());
    }

    //---------------------------------------------------------------------
    public void setHasOneDocumento(Documento value)
    {
        navigation.setFk_documento_detalle(value);
    }

    //---------------------------------------------------------------------
    public float getCantidad()
    {
        return cantidad;
    }

    //---------------------------------------------------------------------
    public void setCantidad(float value)
    {
        this.cantidad = value;
    }

    //---------------------------------------------------------------------
    public float getPrecio()
    {
        return precio;
    }

    //---------------------------------------------------------------------
    public void setPrecio(float value)
    {
        this.precio = value;
    }

    //---------------------------------------------------------------------
    public float getImporte()
    {
        return importe;
    }

    //---------------------------------------------------------------------
    public void setImporte(float value)
    {
        this.importe = value;
    }

    //---------------------------------------------------------------------
    public float getMonto()
    {
        return monto;
    }

    //---------------------------------------------------------------------
    public void setMonto(float value)
    {
        this.monto = value;
    }

    //---------------------------------------------------------------------
    public float getImporte_neto()
    {
        return importe_neto;
    }

    //---------------------------------------------------------------------
    public void setImporte_neto(float value)
    {
        this.importe_neto = value;
    }

    //---------------------------------------------------------------------
    public int getFactor()
    {
        return factor;
    }

    //---------------------------------------------------------------------
    public void setFactor(int value)
    {
        this.factor = value;
    }

    //---------------------------------------------------------------------
    public String getDescripcion()
    {
        return descripcion;
    }

    //---------------------------------------------------------------------
    public void setDescripcion(String value)
    {
        this.descripcion = value;
    }

    //---------------------------------------------------------------------
    @Override
    public void clear()
    {
        initAtributes();
        navigation.releaseDocumento();
    }

    //---------------------------------------------------------------------
    public void copy(DocumentoDetalle that)
    {
        assign(that);
    }

    //---------------------------------------------------------------------
    @Override
    public void acceptChanges()
    {
        super.acceptChanges();
        navigation.acceptChanges();
    }

    //---------------------------------------------------------------------
    protected class ForeignKey
    {

        private Documento fk_documento_detalle;

        //---------------------------------------------------------------------
        public Documento getFk_documento_detalle()
        {
            return fk_documento_detalle;
        }

        //---------------------------------------------------------------------
        public void setFk_documento_detalle(Documento value)
        {
            this.fk_documento_detalle = value;
            this.fk_documento_detalle.setDependentOn(false);
        }

        //---------------------------------------------------------------------
        public Documento joinDocumento(int id_documento)
        {
            fk_documento_detalle = new Documento();
            fk_documento_detalle.setId(id_documento);
            return fk_documento_detalle;
        }

        //---------------------------------------------------------------------
        public void releaseDocumento()
        {
            fk_documento_detalle = null;
        }

        //---------------------------------------------------------------------   
        private void acceptChanges()
        {
            if (null != fk_documento_detalle && fk_documento_detalle.isDependentOn())
                fk_documento_detalle.acceptChanges();
        }
    }
}
