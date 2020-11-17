package com.syswave.entidades.miempresa;

import com.syswave.entidades.keys.CompositeKeyByIdPrecioDocumento;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class DocumentoDetalle_tiene_Precio extends CompositeKeyByIdPrecioDocumento
{

    private float precio, cantidad, total;

    protected ForeignKey navigation;

    //---------------------------------------------------------------------
    public DocumentoDetalle_tiene_Precio()
    {
        super();
        createAtributes();
        initAtributes();
    }

    //---------------------------------------------------------------------
    public DocumentoDetalle_tiene_Precio(DocumentoDetalle_tiene_Precio that)
    {
        super();
        createAtributes();
        assign(that);
    }

    //---------------------------------------------------------------------
    private void assign(DocumentoDetalle_tiene_Precio that)
    {
        super.assign(that);
        precio = that.precio;
        cantidad = that.cantidad;
        total = that.total;
        navigation = that.navigation;
    }

    //---------------------------------------------------------------------
    private void createAtributes()
    {
        navigation = new ForeignKey();
    }

    //---------------------------------------------------------------------
    private void initAtributes()
    {
        precio = EMPTY_FLOAT;
        cantidad = EMPTY_FLOAT;
        total = EMPTY_FLOAT;
    }

    //---------------------------------------------------------------------
    @Override
    public void setIdDocumento(int value)
    {
        super.setIdDocumento(value);
        navigation.releaseDocumentoDetalle();
    }

    //---------------------------------------------------------------------
    @Override
    public int getIdDocumento()
    {
        return null != navigation.getFk_precio_id_documento_detalle()
                ? navigation.getFk_precio_id_documento_detalle().getIdDocumento()
                : super.getIdDocumento();
    }
    
        //---------------------------------------------------------------------
    @Override
    public int getIdDocumento_Viejo()
    {
        return null != navigation.getFk_precio_id_documento_detalle()
                ? navigation.getFk_precio_id_documento_detalle().getIdDocumento_Viejo()
                : super.getIdDocumento_Viejo();
    }

    //---------------------------------------------------------------------
    @Override
    public void setConsecutivo(int value)
    {
        super.setConsecutivo(value);
        navigation.releaseDocumentoDetalle();
    }

    //---------------------------------------------------------------------
    @Override
    public int getConsecutivo()
    {
        return null != navigation.getFk_precio_id_documento_detalle()
                ? navigation.getFk_precio_id_documento_detalle().getConsecutivo()
                : super.getConsecutivo();
    }
    
        //---------------------------------------------------------------------
    @Override
    public int getConsecutivo_Viejo()
    {
        return null != navigation.getFk_precio_id_documento_detalle()
                ? navigation.getFk_precio_id_documento_detalle().getConsecutivo_Viejo()
                : super.getConsecutivo_Viejo();
    }

    //---------------------------------------------------------------------
    public String getIdDocumentoDetalle()
    {
        return null != navigation.getFk_precio_id_documento_detalle()
                ? navigation.getFk_precio_id_documento_detalle().getCompositeKey()
                : String.format("%d,%d", getConsecutivo_Viejo(), getIdDocumento_Viejo());
    }

    //-----------------------------------------------------------------------
    public float getPrecio()
    {
        return precio;
    }

    //-----------------------------------------------------------------------
    public void setPrecio(float value)
    {
        this.precio = value;
    }

    //-----------------------------------------------------------------------
    public float getCantidad()
    {
        return cantidad;
    }

    //-----------------------------------------------------------------------
    public void setCantidad(float value)
    {
        this.cantidad = value;
    }

    //-----------------------------------------------------------------------
    public float getTotal()
    {
        return total;
    }

    //-----------------------------------------------------------------------
    public void setTotal(float value)
    {
        this.total = value;
    }

    //---------------------------------------------------------------------
    @Override
    public void clear()
    {
        initAtributes();
    }

    //--------------------------------------------------------------------
    public void copy(DocumentoDetalle_tiene_Precio that)
    {
        assign(that);
    }

    //--------------------------------------------------------------------
    public DocumentoDetalle getHasOneDocumentoDetalle()
    {
        return null != navigation.getFk_precio_id_documento_detalle()
                ? navigation.getFk_precio_id_documento_detalle()
                : navigation.joinDocumentoDetalle(super.getIdDocumento(),
                                                  super.getConsecutivo());
    }

    //--------------------------------------------------------------------
    public void setHasOneDocumentoDetalle(DocumentoDetalle value)
    {
        navigation.setFk_precio_id_documento_detalle(value);
    }

    //--------------------------------------------------------------------
    public ControlPrecio getHasOneControlPrecio()
    {
        return null != navigation.getFk_fk_documento_detalle_id_precio()
                ? navigation.getFk_fk_documento_detalle_id_precio()
                : navigation.joinControlPrecio(super.getIdPrecio());
    }

    //---------------------------------------------------------------------
    public void setHasOneControlPrecio(ControlPrecio value)
    {
        navigation.setFk_fk_documento_detalle_id_precio(value);
    }

    protected class ForeignKey
    {

        private DocumentoDetalle fk_precio_id_documento_detalle;
        private ControlPrecio fk_documento_detalle_id_precio;

        public DocumentoDetalle joinDocumentoDetalle(int id_documento, int consecutivo)
        {
            fk_precio_id_documento_detalle = new DocumentoDetalle();
            fk_precio_id_documento_detalle.setIdDocumento(id_documento);
            fk_precio_id_documento_detalle.setConsecutivo(consecutivo);

            return fk_precio_id_documento_detalle;
        }

        public ControlPrecio joinControlPrecio(int id_precio)
        {
            fk_documento_detalle_id_precio = new ControlPrecio();
            fk_documento_detalle_id_precio.setId(id_precio);

            return fk_documento_detalle_id_precio;
        }

        public void releaseControlPrecio()
        {
            fk_documento_detalle_id_precio = null;
        }

        public void releaseDocumentoDetalle()
        {
            fk_precio_id_documento_detalle = null;
        }

        //-----------------------------------------------------------------------
        public DocumentoDetalle getFk_precio_id_documento_detalle()
        {
            return fk_precio_id_documento_detalle;
        }

        //-----------------------------------------------------------------------
        public void setFk_precio_id_documento_detalle(DocumentoDetalle value)
        {
            this.fk_precio_id_documento_detalle = value;
            this.fk_precio_id_documento_detalle.setDependentOn(false);
        }

        //-----------------------------------------------------------------------
        public ControlPrecio getFk_fk_documento_detalle_id_precio()
        {
            return fk_documento_detalle_id_precio;
        }

        //-----------------------------------------------------------------------
        public void setFk_fk_documento_detalle_id_precio(ControlPrecio value)
        {
            this.fk_documento_detalle_id_precio = value;
            this.fk_documento_detalle_id_precio.setDependentOn(false);
        }

        //---------------------------------------------------------------------
        public void acceptChanges()
        {
            if (fk_documento_detalle_id_precio != null && fk_documento_detalle_id_precio.isDependentOn())
                fk_documento_detalle_id_precio.acceptChanges();

            if (fk_precio_id_documento_detalle != null && fk_documento_detalle_id_precio.isDependentOn())
                fk_precio_id_documento_detalle.acceptChanges();
        }
    }
}
