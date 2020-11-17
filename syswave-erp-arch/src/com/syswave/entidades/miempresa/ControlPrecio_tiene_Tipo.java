package com.syswave.entidades.miempresa;

import com.syswave.entidades.common.Entidad;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class ControlPrecio_tiene_Tipo extends Entidad
{
    
    private float subtotal, monto, total, cantidad;
    private int factor;
    private int idBien;
    
    protected ForeignKey navigation;
    
       //--------------------------------------------------------------------
    public ControlPrecio_tiene_Tipo()
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
        idBien = EMPTY_INT;
    }

    //--------------------------------------------------------------------
    public void assign(ControlPrecio_tiene_Tipo that)
    {
        cantidad = that.cantidad;
        subtotal = that.subtotal;
        monto = that.monto;
        total = that.total;
        factor = that.factor;
        idBien = that.idBien;
        
        navigation = that.navigation;
    }

    //--------------------------------------------------------------------
    private void createAtributes()
    {
        navigation = new ForeignKey();
    }

    //--------------------------------------------------------------------
    public int getIdBien()
    {
        return null != navigation.getFk_control_precio_id_bien()
                ? navigation.getFk_control_precio_id_bien().getId()
                : idBien;
    }

    //--------------------------------------------------------------------
    public void setIdBien(int value)
    {
        idBien = value;
        navigation.releaseBien();
    }

    //--------------------------------------------------------------------
    public Bien getHasOneBien()
    {
        return null != navigation.getFk_control_precio_id_bien()
                ? navigation.getFk_control_precio_id_bien()
                : navigation.joinBien(idBien);
    }

    //--------------------------------------------------------------------
    public void setHasOneBien(Bien value)
    {
        navigation.setFk_control_precio_id_bien(value);
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
        navigation.releaseBien();
    }

    //--------------------------------------------------------------------
    public void copy(ControlPrecio_tiene_Tipo that)
    {
        assign(that);
    }

    //---------------------------------------------------------------------
    public class ForeignKey
    {
        
        private Bien fk_control_precio_id_bien;

        //---------------------------------------------------------------------
        /**
         * Este método es una fabrica que devuelve una instancia de AreaPrecio
         */
        public Bien joinBien(int id_bien)
        {
            fk_control_precio_id_bien = new Bien();
            fk_control_precio_id_bien.setId(id_bien);
            return fk_control_precio_id_bien;
        }

        //---------------------------------------------------------------------
        public Bien getFk_control_precio_id_bien()
        {
            return fk_control_precio_id_bien;
        }

        //---------------------------------------------------------------------
        public void setFk_control_precio_id_bien(Bien value)
        {
            this.fk_control_precio_id_bien = value;
            this.fk_control_precio_id_bien.setDependentOn(false);
        }
        
        public void releaseBien()
        {
            fk_control_precio_id_bien = null;
        }
        
        public void acceptChanges()
        {
            if (null != fk_control_precio_id_bien && fk_control_precio_id_bien.isDependentOn())
                fk_control_precio_id_bien.acceptChanges();
        }
    }
}
