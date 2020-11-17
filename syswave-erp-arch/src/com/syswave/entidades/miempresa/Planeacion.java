package com.syswave.entidades.miempresa;

import com.syswave.entidades.common.IEntidadRecursiva;
import com.syswave.entidades.keys.PrimaryKeyById;
import java.io.Serializable;
import java.sql.Date;

/**
 * Es una entrada en el registro de sucesos del sistema.
 *
 * @author Victor Manuel Bucio Vargas
 * @version 28 febrero 2014
 */
public class Planeacion extends PrimaryKeyById implements Serializable, IEntidadRecursiva
{

    private String nombre;
    private Boolean activo;
    private Date fecha_inicio, fecha_fin;
    private String descripcion;
    private Float tiempo_estimado, tiempo_real;
    private Integer unidad, prioridad;
    private Double presupuesto;
    private Integer idPadre, nivel;
    private Integer idEstatus;

    protected ForeignKey navigation;

    //---------------------------------------------------------------------
    public Planeacion()
    {
        super();
        createAtributes();
        initAtributes();
    }

    //---------------------------------------------------------------------
    public Planeacion(Planeacion that)
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
        nombre = EMPTY_STRING;
        activo = true;
        fecha_inicio = new Date(EMPTY_DATE.getTime());
        fecha_fin = new Date(EMPTY_DATE.getTime());
        descripcion = EMPTY_STRING;
        tiempo_estimado = EMPTY_FLOAT;
        tiempo_real = EMPTY_FLOAT;
        unidad = EMPTY_INT;
        prioridad = EMPTY_INT;
        presupuesto = 0.0;
        idPadre = EMPTY_INT;
        nivel = EMPTY_INT;
        idEstatus = EMPTY_INT;
    }

    //---------------------------------------------------------------------
    private void assign(Planeacion that)
    {
        super.assign(that);
        nombre = that.nombre;
        activo = that.activo;
        fecha_inicio = that.fecha_inicio;
        fecha_fin = that.fecha_fin;
        descripcion = that.descripcion;
        tiempo_estimado = that.tiempo_estimado;
        tiempo_real = that.tiempo_real;
        unidad = that.unidad;
        prioridad = that.prioridad;
        presupuesto = that.presupuesto;
        idPadre = that.idPadre;
        nivel = that.nivel;
        idEstatus = that.idEstatus;

        navigation = that.navigation;
    }

    //---------------------------------------------------------------------
    @Override
    public void clear()
    {
        initAtributes();
    }

    //---------------------------------------------------------------------
    public void copy(Planeacion that)
    {
        assign(that);
    }

    //---------------------------------------------------------------------
    @Override
    public Integer getIdPadre()
    {
        return idPadre;
    }

    //---------------------------------------------------------------------
    @Override
    public Integer getNivel()
    {
        return nivel;
    }

    //---------------------------------------------------------------------
    public String getNombre()
    {
        return nombre;
    }

    //---------------------------------------------------------------------
    public void setNombre(String nombre)
    {
        this.nombre = nombre;
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
    public Date getFecha_inicio()
    {
        return fecha_inicio;
    }

    //---------------------------------------------------------------------
    public void setFecha_inicio(Date fecha_inicio)
    {
        this.fecha_inicio = fecha_inicio;
    }

    //---------------------------------------------------------------------
    public Date getFecha_fin()
    {
        return fecha_fin;
    }

    //---------------------------------------------------------------------
    public void setFecha_fin(Date fecha_fin)
    {
        this.fecha_fin = fecha_fin;
    }

    //---------------------------------------------------------------------
    public String getDescripcion()
    {
        return descripcion;
    }

    //---------------------------------------------------------------------
    public void setDescripcion(String descripcion)
    {
        this.descripcion = descripcion;
    }

    //---------------------------------------------------------------------
    public Float getTiempo_estimado()
    {
        return tiempo_estimado;
    }

    //---------------------------------------------------------------------
    public void setTiempo_estimado(Float tiempo_estimado)
    {
        this.tiempo_estimado = tiempo_estimado;
    }

    //---------------------------------------------------------------------
    public Float getTiempo_real()
    {
        return tiempo_real;
    }

    //---------------------------------------------------------------------
    public void setTiempo_real(Float tiempo_real)
    {
        this.tiempo_real = tiempo_real;
    }

    //---------------------------------------------------------------------
    public Integer getUnidad()
    {
        return unidad;
    }

    //---------------------------------------------------------------------
    public void setUnidad(Integer unidad)
    {
        this.unidad = unidad;
    }

    //---------------------------------------------------------------------
    public Integer getPrioridad()
    {
        return prioridad;
    }

    //---------------------------------------------------------------------
    public void setPrioridad(Integer prioridad)
    {
        this.prioridad = prioridad;
    }

    //---------------------------------------------------------------------
    public Double getPresupuesto()
    {
        return presupuesto;
    }

    //---------------------------------------------------------------------
    public void setPresupuesto(Double presupuesto)
    {
        this.presupuesto = presupuesto;
    }

    //---------------------------------------------------------------------
    public Integer getIdEstatus()
    {
        return null != navigation.getFk_planeacion_estatus()
                ? navigation.getFk_planeacion_estatus().getId()
                : idEstatus;
    }

    //---------------------------------------------------------------------
    public void setIdEstatus(Integer value)
    {
        idEstatus = value;
        navigation.releaseEstatus();
    }

    //---------------------------------------------------------------------
    public Valor getHasOneEstatus()
    {
        return null != navigation.getFk_planeacion_estatus()
                ? navigation.getFk_planeacion_estatus()
                : navigation.joinEstatus(idEstatus);
    }

    //---------------------------------------------------------------------
    public void setHasOneEstatus(Valor value)
    {
        navigation.setFk_planeacion_estatus(value);
    }

    //---------------------------------------------------------------------
    protected class ForeignKey
    {

        private Valor fk_planeacion_estatus;

        //---------------------------------------------------------------------
        public Valor getFk_planeacion_estatus()
        {
            return fk_planeacion_estatus;
        }

        //---------------------------------------------------------------------
        public void setFk_planeacion_estatus(Valor value)
        {
            this.fk_planeacion_estatus = value;
            this.fk_planeacion_estatus.setDependentOn(false);
        }

        //---------------------------------------------------------------------
        public Valor joinEstatus(int id_status)
        {
            fk_planeacion_estatus = new Valor();
            fk_planeacion_estatus.setId(id_status);
            return fk_planeacion_estatus;
        }

        //---------------------------------------------------------------------
        public void releaseEstatus()
        {
            fk_planeacion_estatus = null;
        }

        //---------------------------------------------------------------------   
        private void acceptChanges()
        {
            if (null != fk_planeacion_estatus && fk_planeacion_estatus.isDependentOn())
                fk_planeacion_estatus.acceptChanges();
        }
    }
}
