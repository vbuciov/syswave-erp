package com.syswave.entidades.miempresa;

import com.syswave.entidades.keys.CompositeKeyByIdDocumentoLinea;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class DocumentoCreditoMovimiento extends CompositeKeyByIdDocumentoLinea
{
    
    private float monto, saldo_anterior, saldo_final;
    private int consecutivo, idPersona, movimiento;
    
    protected ForeignKey navigation;

    //---------------------------------------------------------------------
    public DocumentoCreditoMovimiento()
    {
        super();
        createAtributes();
        initAtributes();
    }

    //---------------------------------------------------------------------
    public DocumentoCreditoMovimiento(DocumentoCreditoMovimiento that)
    {
        super();
        createAtributes();
        assign(that);
    }

    //---------------------------------------------------------------------
    private void initAtributes()
    {
        monto = EMPTY_FLOAT;
        saldo_anterior = EMPTY_FLOAT;
        saldo_final = EMPTY_FLOAT;
        consecutivo = EMPTY_INT;
        idPersona = EMPTY_INT;
        movimiento = EMPTY_INT;
    }

    //---------------------------------------------------------------------
    private void createAtributes()
    {
        navigation = new ForeignKey();
    }

    //---------------------------------------------------------------------
    private void assign(DocumentoCreditoMovimiento that)
    {
        super.assign(that);
        monto = that.monto;
        saldo_anterior = that.saldo_anterior;
        saldo_final = that.saldo_final;
        consecutivo = that.consecutivo;
        idPersona = that.idPersona;
        movimiento = that.movimiento;
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
    public float getSaldoAnterior()
    {
        return saldo_anterior;
    }

    //---------------------------------------------------------------------
    public void setSaldoAnterior(float value)
    {
        this.saldo_anterior = value;
    }

    //---------------------------------------------------------------------
    public float getSaldoFinal()
    {
        return saldo_final;
    }

    //---------------------------------------------------------------------
    public void setSaldoFinal(float value)
    {
        this.saldo_final = value;
    }

    //---------------------------------------------------------------------
    public int getMovimiento()
    {
        return navigation.getFk_documento_credito_movimiento_id_cuenta_movimiento() != null
                ? navigation.getFk_documento_credito_movimiento_id_cuenta_movimiento().getMovimiento()
                : movimiento;
    }

    //---------------------------------------------------------------------
    public void setMovimiento(int value)
    {
        movimiento = value;
        navigation.releaseCreditoCuentaMovimiento();
    }

    //---------------------------------------------------------------------
    public int getConsecutivo()
    {
        return navigation.getFk_documento_credito_movimiento_id_cuenta_movimiento() != null
                ? navigation.getFk_documento_credito_movimiento_id_cuenta_movimiento().getConsecutivo()
                : consecutivo;
    }

    //---------------------------------------------------------------------
    public void setConsecutivo(int value)
    {
        consecutivo = value;
        navigation.releaseCreditoCuentaMovimiento();
    }

    //---------------------------------------------------------------------
    public int getIdPersona()
    {
        return navigation.getFk_documento_credito_movimiento_id_cuenta_movimiento() != null
                ? navigation.getFk_documento_credito_movimiento_id_cuenta_movimiento().getIdPersona()
                : idPersona;
    }

    //---------------------------------------------------------------------
    public void setIdPersona(int value)
    {
        idPersona = value;
        navigation.releaseCreditoCuentaMovimiento();
    }

    //---------------------------------------------------------------------
    public CreditoCuentaMovimiento getHasOneCreditoCuentaMovimiento()
    {
        return navigation.getFk_documento_credito_movimiento_id_cuenta_movimiento() != null
                ? navigation.getFk_documento_credito_movimiento_id_cuenta_movimiento()
                : navigation.joinCreditoCuentaMovimiento(consecutivo, idPersona, movimiento);
    }

    //---------------------------------------------------------------------
    public void setHasOneCreditoCuentaMovimiento(CreditoCuentaMovimiento value)
    {
        navigation.setFk_documento_credito_movimiento_id_cuenta_movimiento(value);
    }

    //---------------------------------------------------------------------
    @Override
    public void clear()
    {
        initAtributes();
        navigation.releaseDocumento();
        navigation.releaseCreditoCuentaMovimiento();
    }

    //---------------------------------------------------------------------
    public void copy(DocumentoCreditoMovimiento that)
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
        private CreditoCuentaMovimiento fk_documento_credito_movimiento_id_cuenta_movimiento;

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
        public CreditoCuentaMovimiento getFk_documento_credito_movimiento_id_cuenta_movimiento()
        {
            return fk_documento_credito_movimiento_id_cuenta_movimiento;
        }

        //---------------------------------------------------------------------
        public void setFk_documento_credito_movimiento_id_cuenta_movimiento(CreditoCuentaMovimiento value)
        {
            this.fk_documento_credito_movimiento_id_cuenta_movimiento = value;
            this.fk_documento_credito_movimiento_id_cuenta_movimiento.setDependentOn(false);
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
        public CreditoCuentaMovimiento joinCreditoCuentaMovimiento(int consecutivo, int id_persona, int movimiento)
        {
            fk_documento_credito_movimiento_id_cuenta_movimiento = new CreditoCuentaMovimiento();
            fk_documento_credito_movimiento_id_cuenta_movimiento.setConsecutivo(consecutivo);
            fk_documento_credito_movimiento_id_cuenta_movimiento.setIdPersona(id_persona);
            fk_documento_credito_movimiento_id_cuenta_movimiento.setMovimiento(movimiento);            
            return fk_documento_credito_movimiento_id_cuenta_movimiento;
        }

        //---------------------------------------------------------------------
        public void releaseCreditoCuentaMovimiento()
        {
            fk_documento_credito_movimiento_id_cuenta_movimiento = null;
        }

        //---------------------------------------------------------------------   
        private void acceptChanges()
        {
            if (null != fk_documento_pagos_id_documento && fk_documento_pagos_id_documento.isDependentOn())
                fk_documento_pagos_id_documento.acceptChanges();
            
            if (null != fk_documento_credito_movimiento_id_cuenta_movimiento && fk_documento_credito_movimiento_id_cuenta_movimiento.isDependentOn())
                fk_documento_credito_movimiento_id_cuenta_movimiento.acceptChanges();
            
        }
    }
}
