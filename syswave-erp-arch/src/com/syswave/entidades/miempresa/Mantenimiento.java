package com.syswave.entidades.miempresa;

import com.syswave.entidades.keys.PrimaryKeyById;
import java.util.Date;

/**
 * Pertenece al CMMS, registra la bitacora de mantenimiento de series.
 *
 * @author Victor Manuel Bucio Vargas
 */
public class Mantenimiento extends PrimaryKeyById
{

    private String folio, nota;
    private Date fecha_creacion;
    private Boolean activo;
    private Float costo_total;
    private Date hora_inicio, hora_final, fecha_finalizacion;
    private int es_tipo, valor_planeado, valor_usado;
    private int idPersona, entradaSerie, idUbicacionSerie, idMoneda;

    protected ForeignKey navigation;

    //---------------------------------------------------------------------
    public Mantenimiento()
    {
        super();
        createAtributes();
        initAtributes();
    }

    //---------------------------------------------------------------------
    public Mantenimiento(Mantenimiento that)
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
        nota = EMPTY_STRING;
        fecha_creacion = EMPTY_DATE;  //new Date(EMPTY_DATE.getTime());
        activo = true;
        costo_total = EMPTY_FLOAT;
        hora_inicio = EMPTY_DATE;
        hora_final = EMPTY_DATE;
        es_tipo = EMPTY_INT;
        valor_planeado = EMPTY_INT;
        valor_usado = EMPTY_INT;
        fecha_finalizacion = EMPTY_DATE;
        idPersona = EMPTY_INT;
        entradaSerie = EMPTY_INT;
        idUbicacionSerie = EMPTY_INT;
        idMoneda = EMPTY_INT;
    }

    //---------------------------------------------------------------------
    private void assign(Mantenimiento that)
    {
        super.assign(that);
        folio = that.folio;
        nota = that.nota;
        fecha_creacion = that.fecha_creacion;
        activo = that.activo;
        costo_total = that.costo_total;
        hora_inicio = that.hora_inicio;
        hora_final = that.hora_final;
        es_tipo = that.es_tipo;
        valor_planeado = that.valor_planeado;
        valor_usado = that.valor_usado;
        fecha_finalizacion = that.fecha_finalizacion;
        idPersona = that.idPersona;
        entradaSerie = that.entradaSerie;
        idUbicacionSerie = that.idUbicacionSerie;
        idMoneda = that.idMoneda;

        navigation = that.navigation;
    }

    //---------------------------------------------------------------------
    public String getFolio()
    {
        return folio;
    }

    //---------------------------------------------------------------------
    public void setFolio(String value)
    {
        this.folio = value;
    }

    //---------------------------------------------------------------------
    public String getNota()
    {
        return nota;
    }

    //---------------------------------------------------------------------
    public void setNota(String value)
    {
        this.nota = value;
    }

    //---------------------------------------------------------------------
    public Date getFechaCreacion()
    {
        return fecha_creacion;
    }

    //---------------------------------------------------------------------
    public void setFechaCreacion(Date value)
    {
        this.fecha_creacion = value;
    }

    //---------------------------------------------------------------------
    public Boolean esActivo()
    {
        return activo;
    }

    //---------------------------------------------------------------------
    public void setActivo(Boolean value)
    {
        this.activo = value;
    }

    //---------------------------------------------------------------------
    public Float getCostoTotal()
    {
        return costo_total;
    }

    //---------------------------------------------------------------------
    public void setCostoTotal(Float value)
    {
        this.costo_total = value;
    }

    //---------------------------------------------------------------------
    public Date getHoraInicio()
    {
        return hora_inicio;
    }

    //---------------------------------------------------------------------
    public void setHoraInicio(Date value)
    {
        this.hora_inicio = value;
    }

    //---------------------------------------------------------------------
    public Date getHoraFinal()
    {
        return hora_final;
    }

    //---------------------------------------------------------------------
    public void setHoraFinal(Date value)
    {
        this.hora_final = value;
    }

    //---------------------------------------------------------------------
    public int getEsTipo()
    {
        return es_tipo;
    }

    //---------------------------------------------------------------------
    public void setEsTipo(int value)
    {
        this.es_tipo = value;
    }

    //---------------------------------------------------------------------
    public int getIdPersona()
    {
        return null != navigation.getFk_mantenimiento_id_responsable()
                ? navigation.getFk_mantenimiento_id_responsable().getId()
                : idPersona;
    }

    //---------------------------------------------------------------------
    public void setIdPersona(int value)
    {
        idPersona = value;
        navigation.releasePersona();
    }

    //---------------------------------------------------------------------
    public int getIdUbicacionSerie()
    {
        return null != navigation.getFk_mantenimiento_id_serie()
                ? navigation.getFk_mantenimiento_id_serie().getIdUbicacion()
                : idUbicacionSerie;

    }

    //---------------------------------------------------------------------
    public void setIdUbicacionSerie(int value)
    {
        idUbicacionSerie = value;
        navigation.releaseControlAlmacen();
    }

    //---------------------------------------------------------------------
    public int getEntradaSerie()
    {
        return null != navigation.getFk_mantenimiento_id_serie()
                ? navigation.getFk_mantenimiento_id_serie().getEntrada()
                : entradaSerie;
    }

    //---------------------------------------------------------------------
    public void setEntradaSerie(int value)
    {
        entradaSerie = value;
        navigation.releaseControlAlmacen();
    }

    //----------------------------------------------------------------------
    public String getIdSerie()
    {
        return null != navigation.getFk_mantenimiento_id_serie()
                ? navigation.getFk_mantenimiento_id_serie().getCompositeKey()
                :  String.format("%d,%d", entradaSerie, idUbicacionSerie);
    }

    //---------------------------------------------------------------------
    public int getIdMoneda()
    {
        return null != navigation.getFk_mantenimiento_id_moneda()
                ? navigation.getFk_mantenimiento_id_moneda().getId()
                : idMoneda;
    }

    //---------------------------------------------------------------------
    public void setIdMoneda(int value)
    {
        idMoneda = value;
        navigation.releaseMoneda();
    }

    //---------------------------------------------------------------------
    public int getValorPlaneado()
    {
        return valor_planeado;
    }

    //---------------------------------------------------------------------
    public void setValorPlaneado(int value)
    {
        this.valor_planeado = value;
    }

    //---------------------------------------------------------------------
    public int getValorUsado()
    {
        return valor_usado;
    }

    //---------------------------------------------------------------------
    public void setValorUsado(int value)
    {
        this.valor_usado = value;
    }

    //---------------------------------------------------------------------
    public Date getFechaFinalizacion()
    {
        return fecha_finalizacion;
    }

    //---------------------------------------------------------------------
    public void setFechaFinalizacion(Date fecha_finalizacion)
    {
        this.fecha_finalizacion = fecha_finalizacion;
    }

    //---------------------------------------------------------------------
    public Persona getHasOnePersonaResponsable()
    {
        return null != navigation.getFk_mantenimiento_id_responsable()
                ? navigation.getFk_mantenimiento_id_responsable()
                : navigation.joinPersona(idPersona);
    }

    //---------------------------------------------------------------------
    public void setHasOnePersonaResponsable(Persona value)
    {
        navigation.setFk_documento_detalle(value);
    }

    //---------------------------------------------------------------------
    public ControlAlmacen getFk_mantenimiento_id_serie()
    {
        return null != navigation.getFk_mantenimiento_id_serie()
                ? navigation.getFk_mantenimiento_id_serie()
                : navigation.joinControlAlmacen(entradaSerie, idUbicacionSerie);
    }

    //---------------------------------------------------------------------
    public void setHasOneControlAlmacen(ControlAlmacen value)
    {
        navigation.setFk_documento_detalle(value);
    }

    //---------------------------------------------------------------------
    public Moneda getHasOneMoneda()
    {
        return null != navigation.getFk_mantenimiento_id_moneda()
                ? navigation.getFk_mantenimiento_id_moneda()
                : navigation.joinMoneda(idMoneda);
    }

    //---------------------------------------------------------------------
    public void setHasOneMoneda(Moneda value)
    {
        navigation.setFk_mantenimiento_id_moneda(value);
    }

    //---------------------------------------------------------------------
    @Override
    public void clear()
    {
        initAtributes();

        navigation.releaseControlAlmacen();
        navigation.releaseMoneda();
        navigation.releasePersona();
    }

    //---------------------------------------------------------------------
    public void copy(Mantenimiento that)
    {
        assign(that);
    }

    @Override
    public void acceptChanges()
    {
        super.acceptChanges(); 
        navigation.acceptChanges();
    }
    
    

    //---------------------------------------------------------------------
    protected class ForeignKey
    {

        private Persona fk_mantenimiento_id_responsable;
        private ControlAlmacen fk_mantenimiento_id_serie;
        private Moneda fk_mantenimiento_id_moneda;

        //---------------------------------------------------------------------
        public Persona getFk_mantenimiento_id_responsable()
        {
            return fk_mantenimiento_id_responsable;
        }

        //---------------------------------------------------------------------
        public void setFk_documento_detalle(Persona value)
        {
            this.fk_mantenimiento_id_responsable = value;
            this.fk_mantenimiento_id_responsable.setDependentOn(false);
        }

        //---------------------------------------------------------------------
        public Persona joinPersona(int id_persona)
        {
            fk_mantenimiento_id_responsable = new Persona();
            fk_mantenimiento_id_responsable.setId(id_persona);
            return fk_mantenimiento_id_responsable;
        }

        //---------------------------------------------------------------------
        public void releasePersona()
        {
            fk_mantenimiento_id_responsable = null;
        }

        //---------------------------------------------------------------------
        public ControlAlmacen getFk_mantenimiento_id_serie()
        {
            return fk_mantenimiento_id_serie;
        }

        //---------------------------------------------------------------------
        public void setFk_documento_detalle(ControlAlmacen value)
        {
            this.fk_mantenimiento_id_serie = value;
            this.fk_mantenimiento_id_serie.setDependentOn(false);
        }

        //---------------------------------------------------------------------
        public ControlAlmacen joinControlAlmacen(int entrada, int id_ubicacion)
        {
            fk_mantenimiento_id_serie = new ControlAlmacen();
            fk_mantenimiento_id_serie.setEntrada(entrada);
            fk_mantenimiento_id_serie.setIdUbicacion(id_ubicacion);
            return fk_mantenimiento_id_serie;
        }

        //---------------------------------------------------------------------
        public void releaseControlAlmacen()
        {
            fk_mantenimiento_id_responsable = null;
        }

        //---------------------------------------------------------------------
        public Moneda getFk_mantenimiento_id_moneda()
        {
            return fk_mantenimiento_id_moneda;
        }

        //---------------------------------------------------------------------
        public void setFk_mantenimiento_id_moneda(Moneda value)
        {
            this.fk_mantenimiento_id_moneda = value;
            this.fk_mantenimiento_id_moneda.setDependentOn(false);
        }

        //---------------------------------------------------------------------
        public Moneda joinMoneda(int id_moneda)
        {
            fk_mantenimiento_id_moneda = new Moneda();
            fk_mantenimiento_id_moneda.setId(id_moneda);
            return fk_mantenimiento_id_moneda;
        }

        //---------------------------------------------------------------------
        public void releaseMoneda()
        {
            fk_mantenimiento_id_moneda = null;
        }

        //---------------------------------------------------------------------   
        private void acceptChanges()
        {
            if (null != fk_mantenimiento_id_responsable && fk_mantenimiento_id_responsable.isDependentOn())
                fk_mantenimiento_id_responsable.acceptChanges();

            if (null != fk_mantenimiento_id_serie && fk_mantenimiento_id_serie.isDependentOn())
                fk_mantenimiento_id_serie.acceptChanges();

            if (null != fk_mantenimiento_id_moneda && fk_mantenimiento_id_moneda.isDependentOn())
                fk_mantenimiento_id_moneda.acceptChanges();
        }
    }
}
