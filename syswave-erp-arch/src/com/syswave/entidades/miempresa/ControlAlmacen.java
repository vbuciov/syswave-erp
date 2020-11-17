package com.syswave.entidades.miempresa;

import com.syswave.entidades.keys.CompositeKeyByIdUbicacionEntrada;

/**
 * Desglose de la existencia según el lugar donde se encuentran.
 *
 * @author Victor Manuel Bucio Vargas
 */
public class ControlAlmacen extends CompositeKeyByIdUbicacionEntrada
{

    private float cantidad;
    private String serie, observaciones;
    private int valor_acumulado;
    private int consecutivo, id_variante;

    protected ForeignKey navigation;

    //---------------------------------------------------------------------
    public ControlAlmacen()
    {
        super();
        createAtributes();
        initAtributes();
    }

    //---------------------------------------------------------------------
    public ControlAlmacen(ControlAlmacen that)
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
        serie = EMPTY_STRING;
        valor_acumulado = EMPTY_INT;
        observaciones = EMPTY_STRING;
        consecutivo = EMPTY_INT;
        id_variante = EMPTY_INT;
    }

    //---------------------------------------------------------------------
    private void assign(ControlAlmacen that)
    {
        super.assign(that);
        cantidad = that.cantidad;
        serie = that.serie;
        valor_acumulado = that.valor_acumulado;
        observaciones = that.observaciones;
        consecutivo = that.consecutivo;
        id_variante = that.id_variante;

        navigation = that.navigation;
    }

    //---------------------------------------------------------------------
    @Override
    public int getIdUbicacion()
    {
        return null != navigation.getFk_control_almacen_id_ubicacion()
                ? navigation.getFk_control_almacen_id_ubicacion().getId()
                : super.getIdUbicacion();
    }

    //---------------------------------------------------------------------
    @Override
    public int getIdUbicacion_Viejo()
    {
        return null != navigation.getFk_control_almacen_id_ubicacion()
                ? navigation.getFk_control_almacen_id_ubicacion().getId_Viejo()
                : super.getIdUbicacion_Viejo();
    }

    //---------------------------------------------------------------------
    @Override
    public void setIdUbicacion(int value)
    {
        super.setIdUbicacion(value);
        navigation.releaseUbicacion();
    }

    //---------------------------------------------------------------------
    public int getIdVariante()
    {
        return null != navigation.getFk_control_almacen_id_inventario()
                ? navigation.getFk_control_almacen_id_inventario().getIdVariante()
                : id_variante;
    }

    //---------------------------------------------------------------------
    public void setIdVariante(int value)
    {
        id_variante = value;
        navigation.releaseControlInventario();
    }

    //---------------------------------------------------------------------
    public String getIdLote()
    {
        return null != navigation.getFk_control_almacen_id_inventario()
                ? navigation.getFk_control_almacen_id_inventario().getCompositeKey()
                : String.format("%d,%d", consecutivo, id_variante);
    }

    //---------------------------------------------------------------------
    public int getConsecutivo()
    {
        return null != navigation.getFk_control_almacen_id_inventario()
                ? navigation.getFk_control_almacen_id_inventario().getConsecutivo()
                : consecutivo;
    }

    //---------------------------------------------------------------------
    public void setConsecutivo(int value)
    {
        consecutivo = value;
        navigation.releaseControlInventario();
    }

    //---------------------------------------------------------------------
    public Ubicacion getHasOneUbicacion()
    {
        return null != navigation.getFk_control_almacen_id_ubicacion()
                ? navigation.getFk_control_almacen_id_ubicacion()
                : navigation.joinUbicacion(super.getIdUbicacion());
    }

    //---------------------------------------------------------------------
    public void setHasOneUbicacion(Ubicacion value)
    {
        navigation.setFk_control_almacen_id_ubicacion(value);
    }

    //---------------------------------------------------------------------
    public ControlInventario getHasOneControlInventario()
    {
        return null != navigation.getFk_control_almacen_id_inventario()
                ? navigation.getFk_control_almacen_id_inventario()
                : navigation.joinControlInventario(consecutivo, id_variante);
    }

    //---------------------------------------------------------------------
    public void setHasOneControlInventario(ControlInventario value)
    {
        navigation.setFk_control_almacen_id_inventario(value);
    }

    //---------------------------------------------------------------------
    public float getCantidad()
    {
        return cantidad;
    }

    //---------------------------------------------------------------------
    public void setCantidad(float cantidad)
    {
        this.cantidad = cantidad;
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
    /**
     * Obtiene el acumulado que lleva la serie según el tipo de mantenimiento.
     *
     * @return
     * @see bien_variantes mantenimiento_como
     */
    public int getValorAcumulado()
    {
        return valor_acumulado;
    }

    //---------------------------------------------------------------------
    /**
     * Obtiene el acumulado que lleva la serie según el tipo de mantenimiento.
     *
     * @param value
     * @see bien_variantes mantenimiento_como
     */
    public void setValoAcumulado(int value)
    {
        this.valor_acumulado = value;
    }

    public String getObservaciones()
    {
        return observaciones;
    }

    public void setObservaciones(String observaciones)
    {
        this.observaciones = observaciones;
    }

    //---------------------------------------------------------------------
    @Override
    public void clear()
    {
        initAtributes();
        navigation.releaseControlInventario();
        navigation.releaseUbicacion();
    }

    //---------------------------------------------------------------------
    public void copy(ControlAlmacen that)
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

        private Ubicacion fk_control_almacen_id_ubicacion;
        private ControlInventario fk_control_almacen_id_inventario;

        //---------------------------------------------------------------------
        public Ubicacion getFk_control_almacen_id_ubicacion()
        {
            return fk_control_almacen_id_ubicacion;
        }

        //---------------------------------------------------------------------
        public void setFk_control_almacen_id_ubicacion(Ubicacion value)
        {
            this.fk_control_almacen_id_ubicacion = value;
            this.fk_control_almacen_id_ubicacion.setDependentOn(false);
        }

        //---------------------------------------------------------------------
        public Ubicacion joinUbicacion(int id_ubicacion)
        {
            fk_control_almacen_id_ubicacion = new Ubicacion();
            fk_control_almacen_id_ubicacion.setId(id_ubicacion);
            return fk_control_almacen_id_ubicacion;
        }

        //---------------------------------------------------------------------
        public void releaseUbicacion()
        {
            fk_control_almacen_id_ubicacion = null;
        }

        //---------------------------------------------------------------------
        public ControlInventario getFk_control_almacen_id_inventario()
        {
            return fk_control_almacen_id_inventario;
        }

        //---------------------------------------------------------------------
        public void setFk_control_almacen_id_inventario(ControlInventario value)
        {
            this.fk_control_almacen_id_inventario = value;
            this.fk_control_almacen_id_inventario.setDependentOn(false);
        }

        //---------------------------------------------------------------------
        public ControlInventario joinControlInventario(int consecutivo, int id_variante)
        {
            fk_control_almacen_id_inventario = new ControlInventario();
            fk_control_almacen_id_inventario.setConsecutivo(consecutivo);
            fk_control_almacen_id_inventario.setIdVariante(id_variante);
            return fk_control_almacen_id_inventario;
        }

        //---------------------------------------------------------------------
        public void releaseControlInventario()
        {
            fk_control_almacen_id_inventario = null;
        }

        //---------------------------------------------------------------------   
        private void acceptChanges()
        {
            if (null != fk_control_almacen_id_inventario && fk_control_almacen_id_inventario.isDependentOn())
                fk_control_almacen_id_inventario.acceptChanges();

            if (null != fk_control_almacen_id_ubicacion && fk_control_almacen_id_ubicacion.isDependentOn())
                fk_control_almacen_id_ubicacion.acceptChanges();
        }
    }
}
