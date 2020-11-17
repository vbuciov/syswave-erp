package com.syswave.entidades.miempresa;

import com.syswave.entidades.keys.CompositeKeyByIdDocumentoLinea;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class DocumentoContadoMovimiento extends CompositeKeyByIdDocumentoLinea
{

    private float monto;
    private String concepto;
    private int id_tipo_comprobante;

    protected ForeignKey navigation;

    //---------------------------------------------------------------------
    public DocumentoContadoMovimiento()
    {
        super();
        createAtributes();
        initAtributes();
    }

    //---------------------------------------------------------------------
    public DocumentoContadoMovimiento(DocumentoContadoMovimiento that)
    {
        super();
        createAtributes();
        assign(that);
    }

    //---------------------------------------------------------------------
    private void initAtributes()
    {
        concepto = EMPTY_STRING;
        monto = EMPTY_FLOAT;
        id_tipo_comprobante = EMPTY_INT;
    }

    //---------------------------------------------------------------------
    private void createAtributes()
    {
        navigation = new ForeignKey();
    }

    //---------------------------------------------------------------------
    private void assign(DocumentoContadoMovimiento that)
    {
        super.assign(that);
        concepto = that.concepto;
        monto = that.monto;
        id_tipo_comprobante = that.id_tipo_comprobante;
        navigation = that.navigation;
    }

    //---------------------------------------------------------------------
    @Override
    public int getIdDocumento()
    {
        return navigation.getFk_documento_pagos_id_documento() != null
                ? navigation.getFk_documento_pagos_id_documento().getId()
                : super.getIdDocumento();
    }
    
        //---------------------------------------------------------------------
    @Override
    public int getIdDocumento_Viejo()
    {
        return navigation.getFk_documento_pagos_id_documento() != null
                ? navigation.getFk_documento_pagos_id_documento().getId_Viejo()
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
    public int getIdTipoComprobante()
    {
        return navigation.getFk_documento_tipo_comprobante() != null
                ? navigation.getFk_documento_tipo_comprobante().getId()
                : id_tipo_comprobante;
    }

    //---------------------------------------------------------------------
    public void setIdTipoComprobante(int value)
    {
        id_tipo_comprobante = value;
        navigation.releaseTipoComprobante();
    }

    //---------------------------------------------------------------------
    public Documento getHasOneDocumento()
    {
        return navigation.getFk_documento_pagos_id_documento() != null
                ? navigation.getFk_documento_pagos_id_documento()
                : navigation.joinDocumento(super.getIdDocumento());
    }

    //---------------------------------------------------------------------
    public void setHasOneDocumento(Documento value)
    {
        navigation.setFk_documento_pagos_id_documento(value);
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
    public String getConcepto()
    {
        return concepto;
    }

    //---------------------------------------------------------------------
    public void setConcepto(String concepto)
    {
        this.concepto = concepto;
    }

    //---------------------------------------------------------------------
    public TipoComprobante getHasOneTipoComprobante()
    {
        return navigation.getFk_documento_tipo_comprobante() != null
                ? navigation.getFk_documento_tipo_comprobante()
                : navigation.joinTipoComprobante(id_tipo_comprobante);
    }

    //---------------------------------------------------------------------
    public void setFk_documento_pagos_id_tipo_comprobante(TipoComprobante value)
    {
        navigation.setFk_documento_tipo_comprobante(value);
    }

    //---------------------------------------------------------------------
    @Override
    public void clear()
    {
        initAtributes();
        navigation.releaseDocumento();
        navigation.releaseTipoComprobante();
    }

    //---------------------------------------------------------------------
    public void copy(DocumentoContadoMovimiento that)
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

        private Documento fk_documento_pagos_id_documento;
        private TipoComprobante fk_documento_pagos_id_tipo_comprobante;

        //---------------------------------------------------------------------
        public Documento getFk_documento_pagos_id_documento()
        {
            return fk_documento_pagos_id_documento;
        }

        //---------------------------------------------------------------------
        public void setFk_documento_pagos_id_documento(Documento value)
        {
            this.fk_documento_pagos_id_documento = value;
            this.fk_documento_pagos_id_documento.setDependentOn(false);
        }

        //---------------------------------------------------------------------
        public TipoComprobante getFk_documento_tipo_comprobante()
        {
            return fk_documento_pagos_id_tipo_comprobante;
        }

        //---------------------------------------------------------------------
        public void setFk_documento_tipo_comprobante(TipoComprobante value)
        {
            this.fk_documento_pagos_id_tipo_comprobante = value;
            this.fk_documento_pagos_id_tipo_comprobante.setDependentOn(false);
        }

        //---------------------------------------------------------------------
        public Documento joinDocumento(int id_documento)
        {
            fk_documento_pagos_id_documento = new Documento();
            fk_documento_pagos_id_documento.setId(id_documento);
            return fk_documento_pagos_id_documento;
        }

        //---------------------------------------------------------------------
        public void releaseDocumento()
        {
            fk_documento_pagos_id_documento = null;
        }

        //---------------------------------------------------------------------
        public TipoComprobante joinTipoComprobante(int id_tipo_comprobante)
        {
            fk_documento_pagos_id_tipo_comprobante = new TipoComprobante();
            fk_documento_pagos_id_tipo_comprobante.setId(id_tipo_comprobante);
            return fk_documento_pagos_id_tipo_comprobante;
        }

        //---------------------------------------------------------------------
        public void releaseTipoComprobante()
        {
            fk_documento_pagos_id_tipo_comprobante = null;
        }

        //---------------------------------------------------------------------   
        private void acceptChanges()
        {
            if (null != fk_documento_pagos_id_documento && fk_documento_pagos_id_documento.isDependentOn())
                fk_documento_pagos_id_documento.acceptChanges();

            if (null != fk_documento_pagos_id_tipo_comprobante && fk_documento_pagos_id_tipo_comprobante.isDependentOn())
                fk_documento_pagos_id_tipo_comprobante.acceptChanges();

        }
    }
}
