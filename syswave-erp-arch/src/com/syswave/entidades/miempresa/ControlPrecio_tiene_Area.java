package com.syswave.entidades.miempresa;

import com.syswave.entidades.common.Entidad;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class ControlPrecio_tiene_Area extends Entidad
{

    private float subtotal, monto, total, cantidad;
    private int factor;
    private int id_area_precio;

    protected ForeignKey navigation;

    //--------------------------------------------------------------------
    public ControlPrecio_tiene_Area()
    {
        super();
        createAtributes();
        initAtributes();
    }

    //--------------------------------------------------------------------
    private void initAtributes()
    {
        cantidad = EMPTY_FLOAT;
        subtotal = EMPTY_FLOAT;
        monto = EMPTY_FLOAT;
        total = EMPTY_FLOAT;
        factor = EMPTY_INT;
        id_area_precio = EMPTY_INT;
    }

    //--------------------------------------------------------------------
    public void assign(ControlPrecio_tiene_Area that)
    {
        cantidad = that.cantidad;
        subtotal = that.subtotal;
        monto = that.monto;
        total = that.total;
        factor = that.factor;
        id_area_precio = that.id_area_precio;

        navigation = that.navigation;
    }

    //--------------------------------------------------------------------
    private void createAtributes()
    {
        navigation = new ForeignKey();
    }

    //--------------------------------------------------------------------
    public int getIdAreaPrecio()
    {
        return null != navigation.getFk_control_precio_id_area_precio()
                ? navigation.getFk_control_precio_id_area_precio().getId()
                : id_area_precio;
    }

    //--------------------------------------------------------------------
    public void setIdAreaPrecio(int value)
    {
        id_area_precio = value;
        navigation.releaseAreaPrecio();
    }

    //--------------------------------------------------------------------
    public AreaPrecio getHasOneAreaPrecio()
    {
        return null != navigation.getFk_control_precio_id_area_precio()
                ? navigation.getFk_control_precio_id_area_precio()
                : navigation.joinAreaPrecio(id_area_precio);
    }

    //--------------------------------------------------------------------
    public void setHasOneAreaPrecio(AreaPrecio value)
    {
        navigation.setFk_control_precio_id_area_precio(value);
    }

    //--------------------------------------------------------------------
    public float getCantidad()
    {
        return cantidad;
    }

    //--------------------------------------------------------------------
    public void setCantidad(float cantidad)
    {
        this.cantidad = cantidad;
    }

    //--------------------------------------------------------------------
    public float getSubtotal()
    {
        return subtotal;
    }

    //--------------------------------------------------------------------
    public void setSubtotal(float subtotal)
    {
        this.subtotal = subtotal;
    }

    //--------------------------------------------------------------------
    public float getMonto()
    {
        return monto;
    }

    //--------------------------------------------------------------------
    public void setMonto(float monto)
    {
        this.monto = monto;
    }

    //--------------------------------------------------------------------
    public float getTotal()
    {
        return total;
    }

    //--------------------------------------------------------------------
    public void setTotal(float total)
    {
        this.total = total;
    }

    //--------------------------------------------------------------------
    public int getFactor()
    {
        return factor;
    }

    //--------------------------------------------------------------------
    public void setFactor(int factor)
    {
        this.factor = factor;
    }

    //--------------------------------------------------------------------
    @Override
    public void clear()
    {
        initAtributes();
    }

    //--------------------------------------------------------------------
    public void copy(ControlPrecio_tiene_Area that)
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
    public class ForeignKey
    {

        private AreaPrecio fk_control_precio_id_area_precio;

        //---------------------------------------------------------------------
        /**
         * Este método es una fabrica que devuelve una instancia de AreaPrecio
         */
        public AreaPrecio joinAreaPrecio(int id_area_precio)
        {
            fk_control_precio_id_area_precio = new AreaPrecio();
            fk_control_precio_id_area_precio.setId(id_area_precio);
            return fk_control_precio_id_area_precio;
        }

        //---------------------------------------------------------------------
        public AreaPrecio getFk_control_precio_id_area_precio()
        {
            return fk_control_precio_id_area_precio;
        }

        //---------------------------------------------------------------------
        public void setFk_control_precio_id_area_precio(AreaPrecio value)
        {
            this.fk_control_precio_id_area_precio = value;
            this.fk_control_precio_id_area_precio.setDependentOn(false);
        }

        public void releaseAreaPrecio()
        {
            fk_control_precio_id_area_precio = null;
        }

        public void acceptChanges()
        {
            if (null != fk_control_precio_id_area_precio && fk_control_precio_id_area_precio.isDependentOn())
                fk_control_precio_id_area_precio.acceptChanges();
        }
    }
}
