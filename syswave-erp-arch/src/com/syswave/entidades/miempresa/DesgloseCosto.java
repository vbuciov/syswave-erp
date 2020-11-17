package com.syswave.entidades.miempresa;

import com.syswave.entidades.keys.CompositeKeyByIdPrecioVariableLinea;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class DesgloseCosto extends CompositeKeyByIdPrecioVariableLinea
{

    private float cantidad, precio, subtotal, monto,
            total, factor_cantidad, factor_precio;
    private int id_precio_indirecto, factor;
    private String observacion;

    protected ForeignKey navigation;

    //----------------------------------------------------------------------
    public DesgloseCosto()
    {
        super();
        createAtributes();
        initAtributes();
    }

    //---------------------------------------------------------------------
    public DesgloseCosto(DesgloseCosto that)
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
        id_precio_indirecto = EMPTY_INT;
        cantidad = EMPTY_FLOAT;
        precio = EMPTY_FLOAT;
        factor_cantidad = EMPTY_FLOAT;
        factor_precio = EMPTY_FLOAT;
        subtotal = EMPTY_FLOAT;
        monto = EMPTY_FLOAT;
        factor = EMPTY_INT;
        total = EMPTY_FLOAT;
        observacion = EMPTY_STRING;
    }

    //---------------------------------------------------------------------
    private void assign(DesgloseCosto that)
    {
        super.assign(that);
        id_precio_indirecto = that.id_precio_indirecto;
        cantidad = that.cantidad;
        precio = that.precio;
        subtotal = that.subtotal;
        monto = that.monto;
        factor = that.factor;
        total = that.total;
        observacion = that.observacion;
        navigation = that.navigation;
    }

    //---------------------------------------------------------------------
    @Override
    public int getIdPrecioVariable()
    {
        return null != navigation.getFk_precio_variable()
                ? navigation.getFk_precio_variable().getId()
                : super.getIdPrecioVariable();
    }

    //---------------------------------------------------------------------
    @Override
    public int getIdPrecioVariable_Viejo()
    {
        return null != navigation.getFk_precio_variable()
                ? navigation.getFk_precio_variable().getId_Viejo()
                : super.getIdPrecioVariable_Viejo();
    }

    //---------------------------------------------------------------------
    @Override
    public void setIdPrecioVariable(int value)
    {
        super.setIdPrecioVariable(value);
        navigation.releasePrecioVariable();
    }

    //---------------------------------------------------------------------
    public int getIdPrecioIndirecto()
    {
        return null != navigation.getFk_precio_indirecto()
                ? navigation.getFk_precio_indirecto().getId()
                : id_precio_indirecto;
    }

    //---------------------------------------------------------------------
    public void setIdPrecioIndirecto(int value)
    {
        id_precio_indirecto = value;
        navigation.releasePrecioIndirecto();
    }

    //---------------------------------------------------------------------
    public ControlPrecio getHasOnePrecioIndirecto()
    {
        return null != navigation.getFk_precio_indirecto()
                ? navigation.getFk_precio_indirecto()
                : navigation.joinPrecioIndirecto(id_precio_indirecto);
    }

    //---------------------------------------------------------------------
    public void setHasOnePrecioIndirecto(ControlPrecio value)
    {
        navigation.setFk_precio_indirecto(value);
    }

    //---------------------------------------------------------------------
    public ControlPrecio getHasOnePrecioVariable()
    {
        return null != navigation.getFk_precio_variable()
                ? navigation.getFk_precio_variable()
                : navigation.joinPrecioVariable(super.getIdPrecioVariable());
    }

    //---------------------------------------------------------------------
    public void setHasOnePrecioVariable(ControlPrecio value)
    {
        navigation.setFk_precio_variable(value);
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
    public float getPrecio()
    {
        return precio;
    }

    //---------------------------------------------------------------------
    public float getFactor_cantidad()
    {
        return factor_cantidad;
    }

    //---------------------------------------------------------------------
    public void setFactor_cantidad(float value)
    {
        this.factor_cantidad = value;
    }

    //---------------------------------------------------------------------
    public float getFactor_precio()
    {
        return factor_precio;
    }

    //---------------------------------------------------------------------
    public void setFactor_precio(float value)
    {
        this.factor_precio = value;
    }

    //---------------------------------------------------------------------
    public void setPrecio(float precio)
    {
        this.precio = precio;
    }

    //---------------------------------------------------------------------
    public float getSubtotal()
    {
        return subtotal;
    }

    //---------------------------------------------------------------------
    public void setSubtotal(float subtotal)
    {
        this.subtotal = subtotal;
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
    public float getTotal()
    {
        return total;
    }

    //---------------------------------------------------------------------
    public void setTotal(float precio_final)
    {
        this.total = precio_final;
    }

    //---------------------------------------------------------------------
    public int getFactor()
    {
        return factor;
    }

    //---------------------------------------------------------------------
    public void setFactor(int factor)
    {
        this.factor = factor;
    }

    //---------------------------------------------------------------------
    public String getObservacion()
    {
        return observacion;
    }

    //---------------------------------------------------------------------
    public void setObservacion(String observacion)
    {
        this.observacion = observacion;
    }

    //---------------------------------------------------------------------
    @Override
    public void clear()
    {
        initAtributes();
        navigation.releasePrecioIndirecto();
        navigation.releasePrecioVariable();
    }

    //---------------------------------------------------------------------
    public void copy(DesgloseCosto that)
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
    public class ForeignKey
    {

        private ControlPrecio fk_precio_variable;
        private ControlPrecio fk_precio_indirecto;

        //---------------------------------------------------------------------
        public ControlPrecio joinPrecioVariable(int id_precio_variable)
        {
            fk_precio_variable = new ControlPrecio();
            fk_precio_variable.setId(id_precio_variable);
            return fk_precio_variable;
        }

        //---------------------------------------------------------------------
        public ControlPrecio joinPrecioIndirecto(int id_precio_indirecto)
        {
            fk_precio_indirecto = new ControlPrecio();
            fk_precio_indirecto.setId(id_precio_indirecto);
            return fk_precio_indirecto;
        }

        //---------------------------------------------------------------------
        public void releasePrecioVariable()
        {
            fk_precio_variable = null;
        }

        //---------------------------------------------------------------------
        public void releasePrecioIndirecto()
        {
            fk_precio_indirecto = null;
        }

        //---------------------------------------------------------------------
        public ControlPrecio getFk_precio_variable()
        {
            return fk_precio_variable;
        }

        //---------------------------------------------------------------------
        public void setFk_precio_variable(ControlPrecio value)
        {
            this.fk_precio_variable = value;
            this.fk_precio_variable.setDependentOn(false);
        }

        //---------------------------------------------------------------------
        public ControlPrecio getFk_precio_indirecto()
        {
            return fk_precio_indirecto;
        }

        //---------------------------------------------------------------------
        public void setFk_precio_indirecto(ControlPrecio value)
        {
            this.fk_precio_indirecto = value;
            this.fk_precio_indirecto.setDependentOn(false);
        }

        //---------------------------------------------------------------------
        private void acceptChanges()
        {
            if (null != fk_precio_indirecto && fk_precio_indirecto.isDependentOn())
                fk_precio_indirecto.acceptChanges();

            if (null != fk_precio_variable && fk_precio_variable.isDependentOn())
                fk_precio_variable.acceptChanges();

        }
    }
}
