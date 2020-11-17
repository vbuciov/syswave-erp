package com.syswave.entidades.miempresa;

import com.syswave.entidades.keys.PrimaryKeyById;
import java.io.Serializable;
import java.util.Date;

/**
 * Sumariza/Consolida los montos generados por una actividad comercial.
 *
 * @author Victor Manuel Bucio Vargas
 * @version 1 Marzo 2014
 */
public class Documento extends PrimaryKeyById implements Serializable
{

    private String folio, serie;
    private Date fecha_creacion, fecha_vigencia;
    private Boolean activo, aplicado;
    private Float subtotal, monto, total, pagado, saldo_actual;
    private Integer factor;
    private Integer idEStatus;
    private Integer idTipoComprobante;
    private Integer idMoneda;

    protected ForeignKey navigation;

    //---------------------------------------------------------------------
    public Documento()
    {
        super();
        createAtributes();
        initAtributes();
    }

    //---------------------------------------------------------------------
    public Documento(Documento that)
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
        folio = EMPTY_STRING;
        serie = EMPTY_STRING;
        fecha_creacion = EMPTY_DATE;  //new Date(EMPTY_DATE.getTime());
        fecha_vigencia = EMPTY_DATE; //new Date(EMPTY_DATE.getTime());
        activo = true;
        aplicado = false;
        subtotal = EMPTY_FLOAT;
        factor = EMPTY_INT;
        monto = EMPTY_FLOAT;
        total = EMPTY_FLOAT;
        pagado = EMPTY_FLOAT;
        saldo_actual = EMPTY_FLOAT;
        idEStatus = EMPTY_INT;
        idTipoComprobante = EMPTY_INT;
        idMoneda = EMPTY_INT;
    }

    //---------------------------------------------------------------------
    private void assign(Documento that)
    {
        super.assign(that);
        folio = that.folio;
        serie = that.serie;
        fecha_creacion = that.fecha_creacion;
        fecha_vigencia = that.fecha_vigencia;
        activo = that.activo;
        aplicado = that.aplicado;
        subtotal = that.subtotal;
        factor = that.factor;
        monto = that.monto;
        total = that.total;
        pagado = that.pagado;
        saldo_actual = that.saldo_actual;
        idEStatus = that.idEStatus;
        idTipoComprobante = that.idTipoComprobante;
        idMoneda = that.idMoneda;

        navigation = that.navigation;
    }

    //---------------------------------------------------------------------
    public String getFolio()
    {
        return folio;
    }

    //---------------------------------------------------------------------
    public void setFolio(String folio)
    {
        this.folio = folio;
    }

    //---------------------------------------------------------------------
    public String getSerie()
    {
        return serie;
    }

    //---------------------------------------------------------------------
    public void setSerie(String serie)
    {
        this.serie = serie;
    }

    //---------------------------------------------------------------------
    public String getIdentificadorCompleto()
    {
        return String.format("%s %s", serie, folio);
    }

    //---------------------------------------------------------------------
    public Date getFechaElaboracion()
    {
        return fecha_creacion;
    }

    //---------------------------------------------------------------------
    public void setFechaElaboracion(Date fecha_creacion)
    {
        this.fecha_creacion = fecha_creacion;
    }

    //---------------------------------------------------------------------
    public Date getFechaVigencia()
    {
        return fecha_vigencia;
    }

    //---------------------------------------------------------------------
    public void setFechaVigencia(Date fecha_vigencia)
    {
        this.fecha_vigencia = fecha_vigencia;
    }

    //---------------------------------------------------------------------
    public Boolean esActivo()
    {
        return activo;
    }

    //---------------------------------------------------------------------
    public void setActivo(Boolean activo)
    {
        this.activo = activo;
    }

    //---------------------------------------------------------------------
    public Float getSubtotal()
    {
        return subtotal;
    }

    //---------------------------------------------------------------------
    public void setSubtotal(Float subtotal)
    {
        this.subtotal = subtotal;
    }

    //---------------------------------------------------------------------
    public Integer getFactor()
    {
        return factor;
    }

    //---------------------------------------------------------------------
    public void setFactor(Integer value)
    {
        factor = value;
    }

    //---------------------------------------------------------------------
    public void setGravamen(Integer value)
    {
        this.factor = value;
    }

    //---------------------------------------------------------------------
    public Float getMonto()
    {
        return monto;
    }

    //---------------------------------------------------------------------
    public void setMonto(Float monto)
    {
        this.monto = monto;
    }

    //---------------------------------------------------------------------
    public Float getTotal()
    {
        return total;
    }

    //---------------------------------------------------------------------
    public void setTotal(Float total)
    {
        this.total = total;
    }

    //---------------------------------------------------------------------
    public Boolean esAplicado()
    {
        return aplicado;
    }

    //---------------------------------------------------------------------
    public void setAplicado(Boolean aplicado)
    {
        this.aplicado = aplicado;
    }

    //---------------------------------------------------------------------
    public Integer getIdEstatus()
    {
        return null != navigation.getFk_documento_estatus()
                ? navigation.getFk_documento_estatus().getId()
                : idEStatus;
    }

    //---------------------------------------------------------------------
    public void setIdEstatus(Integer value)
    {
        idEStatus = value;
        navigation.releaseDocumentoEstatus();
    }

    //---------------------------------------------------------------------
    public Integer getIdTipoComprobante()
    {
        return null != navigation.getFk_documento_tipo_comprobante()
                ? navigation.getFk_documento_tipo_comprobante().getId()
                : idTipoComprobante;
    }

    //---------------------------------------------------------------------
    public void setIdTipoComprobante(Integer value)
    {
        idTipoComprobante = value;
        navigation.releaseTipoComprobante();
    }

    //---------------------------------------------------------------------
    public int getIdMoneda()
    {
        return null != navigation.getFk_documento_id_moneda()
                ? navigation.getFk_documento_id_moneda().getId()
                : idMoneda;
    }

    //---------------------------------------------------------------------
    public void setIdMoneda(int value)
    {
        idMoneda = value;
        navigation.releaseMoneda();
    }

    //---------------------------------------------------------------------
    public Float getPagado()
    {
        return pagado;
    }

    //---------------------------------------------------------------------
    public void setPagado(Float value)
    {
        this.pagado = value;
    }

    //---------------------------------------------------------------------
    public Float getSaldoActual()
    {
        return saldo_actual;
    }

    //---------------------------------------------------------------------
    public void setSaldoActual(Float value)
    {
        this.saldo_actual = value;
    }

    //---------------------------------------------------------------------
    public Valor getHasOneDocumentoEstatus()
    {
        return null != navigation.getFk_documento_estatus()
                ? navigation.getFk_documento_estatus()
                : navigation.joinDocumentoEstatus(idEStatus);
    }

    //---------------------------------------------------------------------
    public void setHasOneDocumentoEstatus(Valor value)
    {
        navigation.setFk_documento_estatus(value);
    }

    //---------------------------------------------------------------------
    public TipoComprobante getHasOneTipoComprobante()
    {
        return null != navigation.getFk_documento_tipo_comprobante()
                ? navigation.getFk_documento_tipo_comprobante()
                : navigation.joinTipoComprobante(idTipoComprobante);
    }

    //---------------------------------------------------------------------
    public void setHasOneTipoComprobante(TipoComprobante value)
    {
        navigation.setFk_documento_tipo_comprobante(value);
    }

    //---------------------------------------------------------------------
    public Moneda getHasOneMoneda()
    {
        return null != navigation.getFk_documento_id_moneda()
                ? navigation.getFk_documento_id_moneda()
                : navigation.joinMoneda(idMoneda);
    }

    //---------------------------------------------------------------------
    public void setHasOneMoneda(Moneda value)
    {
        navigation.setFk_documento_id_moneda(value);
    }

    //---------------------------------------------------------------------
    @Override
    public void clear()
    {
        initAtributes();
        navigation.releaseDocumentoEstatus();
        navigation.releaseMoneda();
        navigation.releaseTipoComprobante();
    }

    //---------------------------------------------------------------------
    public void copy(Documento that)
    {
        assign(that);
    }

    //---------------------------------------------------------------------
    protected class ForeignKey
    {

        private Valor fk_documento_estatus;
        private TipoComprobante fk_documento_tipo_comprobante;
        private Moneda fk_documento_id_moneda;

        //---------------------------------------------------------------------
        public Valor getFk_documento_estatus()
        {
            return fk_documento_estatus;
        }

        //---------------------------------------------------------------------
        public void setFk_documento_estatus(Valor value)
        {
            this.fk_documento_estatus = value;
            this.fk_documento_estatus.setDependentOn(false);
        }

        //---------------------------------------------------------------------
        public TipoComprobante getFk_documento_tipo_comprobante()
        {
            return fk_documento_tipo_comprobante;
        }

        //---------------------------------------------------------------------
        public void setFk_documento_tipo_comprobante(TipoComprobante value)
        {
            this.fk_documento_tipo_comprobante = value;
            this.fk_documento_tipo_comprobante.setDependentOn(false);
        }

        //---------------------------------------------------------------------
        public Moneda getFk_documento_id_moneda()
        {
            return fk_documento_id_moneda;
        }

        //---------------------------------------------------------------------
        public void setFk_documento_id_moneda(Moneda value)
        {
            this.fk_documento_id_moneda = value;
            this.fk_documento_id_moneda.setDependentOn(false);
        }

        //---------------------------------------------------------------------
        public Valor joinDocumentoEstatus(int id_estatus)
        {
            fk_documento_estatus = new Valor();
            fk_documento_estatus.setId(id_estatus);
            return fk_documento_estatus;
        }

        //---------------------------------------------------------------------
        public void releaseDocumentoEstatus()
        {
            fk_documento_estatus = null;
        }

        //---------------------------------------------------------------------
        public TipoComprobante joinTipoComprobante(int id_tipo_comprobante)
        {
            fk_documento_tipo_comprobante = new TipoComprobante();
            fk_documento_tipo_comprobante.setId(id_tipo_comprobante);
            return fk_documento_tipo_comprobante;
        }

        //---------------------------------------------------------------------
        public void releaseTipoComprobante()
        {
            fk_documento_tipo_comprobante = null;
        }

        //---------------------------------------------------------------------
        public Moneda joinMoneda(int id_moneda)
        {
            fk_documento_id_moneda = new Moneda();
            fk_documento_id_moneda.setId(id_moneda);
            return fk_documento_id_moneda;
        }

        //---------------------------------------------------------------------
        public void releaseMoneda()
        {
            fk_documento_id_moneda = null;
        }

        //---------------------------------------------------------------------   
        private void acceptChanges()
        {
            if (null != fk_documento_id_moneda && fk_documento_id_moneda.isDependentOn())
                fk_documento_id_moneda.acceptChanges();

            if (null != fk_documento_tipo_comprobante && fk_documento_tipo_comprobante.isDependentOn())
                fk_documento_tipo_comprobante.acceptChanges();

            if (null != fk_documento_estatus && fk_documento_estatus.isDependentOn())
                fk_documento_estatus.acceptChanges();

        }
    }
}
