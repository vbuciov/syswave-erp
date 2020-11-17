package com.syswave.entidades.miempresa;

import com.syswave.entidades.keys.CompositeKeyByIdMovimientoPersona;
import java.util.Date;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class CreditoCuentaMovimiento extends CompositeKeyByIdMovimientoPersona
{

    private Date fecha_elaboracion;
    private float monto, monto_minimo, saldo_anterior, saldo_aplicado;
    private String concepto, referencia;
    private int plazo_dado, unidad, tasa_interes, factor_minimo, es_origen;
    private boolean es_letra;
    private int id_tipo_comprobante;

    protected ForeignKey navigation;

    //---------------------------------------------------------------------
    public CreditoCuentaMovimiento()
    {
        super();
        createAtributes();
        initAtributes();
    }

    //---------------------------------------------------------------------
    public CreditoCuentaMovimiento(CreditoCuentaMovimiento that)
    {
        super();
        createAtributes();
        asign(that);
    }

    //---------------------------------------------------------------------
    private void initAtributes()
    {
        fecha_elaboracion = EMPTY_DATE;
        monto = EMPTY_FLOAT;
        monto_minimo = EMPTY_FLOAT;
        saldo_anterior = EMPTY_FLOAT;
        saldo_aplicado = EMPTY_FLOAT;
        concepto = EMPTY_STRING;
        referencia = EMPTY_STRING;
        plazo_dado = EMPTY_INT;
        unidad = EMPTY_INT;
        tasa_interes = EMPTY_INT;
        factor_minimo = EMPTY_INT;
        es_origen = EMPTY_INT;
        es_letra = false;
        id_tipo_comprobante = EMPTY_INT;
    }

    //---------------------------------------------------------------------
    private void createAtributes()
    {
        navigation = new ForeignKey();
    }

    //---------------------------------------------------------------------
    private void asign(CreditoCuentaMovimiento that)
    {
        super.assign(that);

        fecha_elaboracion = that.fecha_elaboracion;
        monto = that.monto;
        monto_minimo = that.monto_minimo;
        saldo_anterior = that.saldo_anterior;
        saldo_aplicado = that.saldo_aplicado;
        concepto = that.concepto;
        referencia = that.referencia;
        plazo_dado = that.plazo_dado;
        unidad = that.unidad;
        tasa_interes = that.tasa_interes;
        factor_minimo = that.factor_minimo;
        es_origen = that.es_origen;
        es_letra = that.es_letra;
        id_tipo_comprobante = that.id_tipo_comprobante;

        navigation = that.navigation;
    }

    //---------------------------------------------------------------------
    @Override
    public int getConsecutivo()
    {
        return null != navigation.getFk_cuenta_movimientos_id_cuenta()
                ? navigation.getFk_cuenta_movimientos_id_cuenta().getConsecutivo()
                : super.getConsecutivo();
    }

    //---------------------------------------------------------------------
    @Override
    public int getConsecutivo_Viejo()
    {
        return null != navigation.getFk_cuenta_movimientos_id_cuenta()
                ? navigation.getFk_cuenta_movimientos_id_cuenta().getConsecutivo_Viejo()
                : super.getConsecutivo_Viejo();
    }

    //---------------------------------------------------------------------
    @Override
    public void setConsecutivo(int value)
    {
        super.setConsecutivo(value);
        navigation.releasePersonaCreditoCuentas();
    }

    //---------------------------------------------------------------------
    @Override
    public int getIdPersona()
    {
        {
            return null != navigation.getFk_cuenta_movimientos_id_cuenta()
                    ? navigation.getFk_cuenta_movimientos_id_cuenta().getIdPersona()
                    : super.getIdPersona();
        }
    }

    //---------------------------------------------------------------------
    @Override
    public int getIdPersona_Viejo()
    {
        {
            return null != navigation.getFk_cuenta_movimientos_id_cuenta()
                    ? navigation.getFk_cuenta_movimientos_id_cuenta().getIdPersona_Viejo()
                    : super.getIdPersona_Viejo();
        }
    }

    //---------------------------------------------------------------------    
    @Override
    public void setIdPersona(int value)
    {
        super.setIdPersona(value);
        navigation.releasePersonaCreditoCuentas();
    }

    //---------------------------------------------------------------------
    public int getIdTipoComprobante()
    {
        return null != navigation.getFk_cuenta_movimientos_id_tipo_comprobante()
                ? navigation.getFk_cuenta_movimientos_id_tipo_comprobante().getId()
                : id_tipo_comprobante;
    }

    //---------------------------------------------------------------------
    public void setIdTipoComprobante(int value)
    {
        id_tipo_comprobante = value;
        navigation.releaseTipoComprobantes();
    }

    //---------------------------------------------------------------------
    public PersonaCreditoCuenta getHasOnePersonaCreditoCuenta()
    {
        return null != navigation.getFk_cuenta_movimientos_id_cuenta()
                ? navigation.getFk_cuenta_movimientos_id_cuenta()
                : navigation.joinPersonaCreditoCuentas(super.getConsecutivo(),
                                                       super.getIdPersona());
    }

    //---------------------------------------------------------------------
    public void setHasOnePersonaCreditoCuenta(PersonaCreditoCuenta value)
    {
        navigation.setFk_cuenta_movimientos_id_cuenta(value);
    }

    //---------------------------------------------------------------------
    public Date getFechaElaboracion()
    {
        return fecha_elaboracion;
    }

    //---------------------------------------------------------------------
    public void setFechaElaboracion(Date value)
    {
        this.fecha_elaboracion = value;
    }

    //---------------------------------------------------------------------
    public TipoComprobante getHasOneTipoComprobante()
    {
        return null != navigation.getFk_cuenta_movimientos_id_tipo_comprobante()
                ? navigation.getFk_cuenta_movimientos_id_tipo_comprobante()
                : navigation.joinTipoComprobantes(id_tipo_comprobante);
    }

    //---------------------------------------------------------------------
    public void setHasOneTipoComprobante(TipoComprobante value)
    {
        navigation.setFk_cuenta_movimientos_id_tipo_comprobante(value);
    }

    //---------------------------------------------------------------------
    public float getMonto()
    {
        return monto;
    }

    //---------------------------------------------------------------------
    public void setMonto(float monto)
    {
        this.monto = monto;
    }

    //---------------------------------------------------------------------
    public float getMontoMinimo()
    {
        return monto_minimo;
    }

    //---------------------------------------------------------------------
    public void setMontoMinimo(float value)
    {
        this.monto_minimo = value;
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
    public float getSaldoAplicado()
    {
        return saldo_aplicado;
    }

    //---------------------------------------------------------------------
    public void setSaldoAplicado(float value)
    {
        this.saldo_aplicado = value;
    }

    //---------------------------------------------------------------------
    public String getConcepto()
    {
        return concepto;
    }

    //---------------------------------------------------------------------
    public void setConcepto(String value)
    {
        this.concepto = value;
    }

    //---------------------------------------------------------------------
    public String getReferencia()
    {
        return referencia;
    }

    //---------------------------------------------------------------------
    public void setReferencia(String value)
    {
        this.referencia = value;
    }

    //---------------------------------------------------------------------
    public int getPlazoDado()
    {
        return plazo_dado;
    }

    //---------------------------------------------------------------------
    public void setPlazoDado(int value)
    {
        this.plazo_dado = value;
    }

    //---------------------------------------------------------------------
    public int getUnidad()
    {
        return unidad;
    }

    //---------------------------------------------------------------------
    public void setUnidad(int value)
    {
        this.unidad = value;
    }

    //---------------------------------------------------------------------
    public int getTasaInteres()
    {
        return tasa_interes;
    }

    //---------------------------------------------------------------------
    public void setTasa_interes(int value)
    {
        this.tasa_interes = value;
    }

    //---------------------------------------------------------------------
    public int getFactorMinimo()
    {
        return factor_minimo;
    }

    //---------------------------------------------------------------------
    public void setFactorMinimo(int value)
    {
        this.factor_minimo = value;
    }

    //---------------------------------------------------------------------
    public boolean esLetra()
    {
        return es_letra;
    }

    //---------------------------------------------------------------------
    public void setEsletra(boolean value)
    {
        this.es_letra = value;
    }

    //---------------------------------------------------------------------
    public int getEsOrigen()
    {
        return es_origen;
    }

    //---------------------------------------------------------------------
    public void setEsOrigen(int value)
    {
        this.es_origen = value;
    }

    //---------------------------------------------------------------------
    @Override
    public void clear()
    {
        initAtributes();
        navigation.releasePersonaCreditoCuentas();
        navigation.releaseTipoComprobantes();
    }

    //---------------------------------------------------------------------
    public void copy(CreditoCuentaMovimiento that)
    {
        asign(that);
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

        protected PersonaCreditoCuenta fk_cuenta_movimientos_id_cuenta;
        protected TipoComprobante fk_cuenta_movimientos_id_tipo_comprobante;

        //---------------------------------------------------------------------
        public PersonaCreditoCuenta getFk_cuenta_movimientos_id_cuenta()
        {
            return fk_cuenta_movimientos_id_cuenta;
        }

        //---------------------------------------------------------------------
        public void setFk_cuenta_movimientos_id_cuenta(PersonaCreditoCuenta value)
        {
            this.fk_cuenta_movimientos_id_cuenta = value;
            this.fk_cuenta_movimientos_id_cuenta.setDependentOn(false);
        }

        //---------------------------------------------------------------------
        public TipoComprobante getFk_cuenta_movimientos_id_tipo_comprobante()
        {
            return fk_cuenta_movimientos_id_tipo_comprobante;
        }

        //---------------------------------------------------------------------
        public void setFk_cuenta_movimientos_id_tipo_comprobante(TipoComprobante value)
        {
            this.fk_cuenta_movimientos_id_tipo_comprobante = value;
            this.fk_cuenta_movimientos_id_tipo_comprobante.setDependentOn(false);
        }

        //---------------------------------------------------------------------
        public PersonaCreditoCuenta joinPersonaCreditoCuentas(int consecutivo, int id_persona)
        {
            fk_cuenta_movimientos_id_cuenta = new PersonaCreditoCuenta();
            fk_cuenta_movimientos_id_cuenta.setConsecutivo(consecutivo);
            fk_cuenta_movimientos_id_cuenta.setIdPersona(id_persona);
            return fk_cuenta_movimientos_id_cuenta;
        }

        //---------------------------------------------------------------------
        public void releasePersonaCreditoCuentas()
        {
            fk_cuenta_movimientos_id_cuenta = null;
        }

        //---------------------------------------------------------------------
        public TipoComprobante joinTipoComprobantes(int id_tipo_comprobante)
        {
            fk_cuenta_movimientos_id_tipo_comprobante = new TipoComprobante();
            fk_cuenta_movimientos_id_tipo_comprobante.setId(id_tipo_comprobante);
            return fk_cuenta_movimientos_id_tipo_comprobante;
        }

        //---------------------------------------------------------------------
        public void releaseTipoComprobantes()
        {
            fk_cuenta_movimientos_id_tipo_comprobante = null;
        }

        //---------------------------------------------------------------------   
        private void acceptChanges()
        {
            if (null != fk_cuenta_movimientos_id_cuenta && fk_cuenta_movimientos_id_cuenta.isDependentOn())
                fk_cuenta_movimientos_id_cuenta.acceptChanges();

            if (null != fk_cuenta_movimientos_id_tipo_comprobante && fk_cuenta_movimientos_id_tipo_comprobante.isDependentOn())
                fk_cuenta_movimientos_id_tipo_comprobante.acceptChanges();
        }
    }

}
