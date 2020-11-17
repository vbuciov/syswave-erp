package com.syswave.entidades.miempresa;

import com.syswave.entidades.keys.PrimaryKeyById;

/**
 * Representa uno de los precios que puede tener un bien o servicio.
 *
 * @author Victor Manuel Bucio Vargas
 */
public class ControlPrecio extends PrimaryKeyById
{

    private String descripcion;
    private float costo_directo, margen, precio_final;
    private int id_variante, id_moneda, id_area, factor;
    private boolean tiene_analisis;
    //private int es_tipo;

    protected ForeignKey navigation;

    //---------------------------------------------------------------------
    public ControlPrecio()
    {
        super();
        createAtributes();
        initAtributes();
    }

    //---------------------------------------------------------------------
    public ControlPrecio(ControlPrecio that)
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
        id_variante = EMPTY_INT;
        id_moneda = EMPTY_INT;
        id_area = EMPTY_INT;
        descripcion = EMPTY_STRING;
        costo_directo = EMPTY_FLOAT;
        margen = EMPTY_FLOAT;
        factor = EMPTY_INT;
        precio_final = EMPTY_FLOAT;
        tiene_analisis = false;
        //es_tipo = EMPTY_INT;
    }

    //---------------------------------------------------------------------
    private void assign(ControlPrecio that)
    {
        super.assign(that);

        id_variante = that.id_variante;
        id_moneda = that.id_moneda;
        id_area = that.id_area;
        descripcion = that.descripcion;
        costo_directo = that.costo_directo;
        margen = that.margen;
        factor = that.factor;
        precio_final = that.precio_final;
        tiene_analisis = that.tiene_analisis;
        //es_tipo = that.es_tipo;

        this.navigation = that.navigation;
    }

    //---------------------------------------------------------------------
    public int getIdVariante()
    {
        return null != navigation.getFk_precio_id_variante()
                ? navigation.getFk_precio_id_variante().getId()
                : id_variante;
    }

    //---------------------------------------------------------------------
    public void setIdVariante(int value)
    {
        navigation.releaseBienVariante();

        id_variante = value;
    }

    //---------------------------------------------------------------------
    public BienVariante getHasOneBienVariante()
    {
        return null != navigation.getFk_precio_id_variante()
                ? navigation.getFk_precio_id_variante()
                : navigation.joinBienVariante(id_variante);
    }

    //---------------------------------------------------------------------
    public void setHasOneBienVariante(BienVariante value)
    {
        navigation.setFk_precio_id_variante(value);
    }

    //---------------------------------------------------------------------
    public int getIdMoneda()
    {
        return null != navigation.getFk_precio_id_moneda()
                ? navigation.getFk_precio_id_moneda().getId()
                : id_moneda;
    }

    //---------------------------------------------------------------------
    public void setIdMoneda(int value)
    {
        navigation.releaseMoneda();

        id_moneda = value;
    }

    //---------------------------------------------------------------------
    public Moneda getHasOneMoneda()
    {
        return null != navigation.getFk_precio_id_moneda()
                ? navigation.getFk_precio_id_moneda()
                : navigation.joinMoneda(id_moneda);
    }

    //---------------------------------------------------------------------    
    public void setHasOneMoneda(Moneda value)
    {
        navigation.setFk_precio_id_moneda(value);
    }

    //---------------------------------------------------------------------
    public void setIdAreaPrecio(int value)
    {
        navigation.releaseAreaPrecio();

        id_area = value;
    }

    //---------------------------------------------------------------------
    public int getIdAreaPrecio()
    {
        return null != navigation.getFk_precio_id_area()
                ? navigation.getFk_precio_id_area().getId()
                : id_area;
    }

    //---------------------------------------------------------------------
    public AreaPrecio getHasOneAreaPrecio()
    {
        return null != navigation.getFk_precio_id_area()
                ? navigation.getFk_precio_id_area()
                : navigation.joinAreaPrecio(id_area);
    }

    //---------------------------------------------------------------------
    public void setHasOneAreaPrecio(AreaPrecio value)
    {
        navigation.setFk_precio_id_area(value);
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
    public float getCostoDirecto()
    {
        return costo_directo;
    }

    //---------------------------------------------------------------------
    public void setCostoDirecto(float valor)
    {
        this.costo_directo = valor;
    }

    //---------------------------------------------------------------------
    public float getMargen()
    {
        return margen;
    }

    //---------------------------------------------------------------------
    public void setMargen(float margen)
    {
        this.margen = margen;
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
    public float getPrecioFinal()
    {
        return precio_final;
    }

    //---------------------------------------------------------------------
    public void setPrecioFinal(float value)
    {
        precio_final = value;
    }

    //---------------------------------------------------------------------
    public boolean tieneAnalisis()
    {
        return tiene_analisis;
    }

    //---------------------------------------------------------------------
    public void setTieneAnalisis(boolean tiene_analisis)
    {
        this.tiene_analisis = tiene_analisis;
    }

    //---------------------------------------------------------------------
    /*public int getEsTipo()
    {
        return es_tipo;
    }

    //---------------------------------------------------------------------
    public void setEsTipo(int es_tipo)
    {
        this.es_tipo = es_tipo;
    }*/
    //---------------------------------------------------------------------
    @Override
    public void clear()
    {
        initAtributes();
        navigation.releaseAreaPrecio();
        navigation.releaseMoneda();
        navigation.releaseBienVariante();
    }

    //---------------------------------------------------------------------
    public void copy(ControlPrecio that)
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

        protected BienVariante fk_precio_id_variante;
        protected Moneda fk_precio_id_moneda;
        protected AreaPrecio fk_precio_id_area;

        //---------------------------------------------------------------------
        /**
         * Este método es una fabrica que devuelve una instancia de BienVariante
         */
        public BienVariante joinBienVariante(int id_variante)
        {
            fk_precio_id_variante = new BienVariante();
            fk_precio_id_variante.setId(id_variante);
            return fk_precio_id_variante;
        }

        //---------------------------------------------------------------------
        public Moneda joinMoneda(int id_moneda)
        {
            fk_precio_id_moneda = new Moneda();
            fk_precio_id_moneda.setId(id_moneda);
            return fk_precio_id_moneda;

        }

        //---------------------------------------------------------------------
        public AreaPrecio joinAreaPrecio(int id_area_precio)
        {

            fk_precio_id_area = new AreaPrecio();
            fk_precio_id_area.setId(id_area_precio);
            return fk_precio_id_area;
        }

        //---------------------------------------------------------------------
        public BienVariante getFk_precio_id_variante()
        {
            return fk_precio_id_variante;
        }

        //---------------------------------------------------------------------
        public void setFk_precio_id_variante(BienVariante value)
        {
            this.fk_precio_id_variante = value;
            this.fk_precio_id_variante.setDependentOn(false);
        }

        //---------------------------------------------------------------------
        public void setFk_precio_id_moneda(Moneda value)
        {
            this.fk_precio_id_moneda = value;
            this.fk_precio_id_moneda.setDependentOn(false);
        }

        public Moneda getFk_precio_id_moneda()
        {
            return fk_precio_id_moneda;
        }

        //---------------------------------------------------------------------
        public AreaPrecio getFk_precio_id_area()
        {
            return fk_precio_id_area;
        }

        //---------------------------------------------------------------------
        public void setFk_precio_id_area(AreaPrecio fk_precio_id_area)
        {
            this.fk_precio_id_area = fk_precio_id_area;
            this.fk_precio_id_area.setDependentOn(false);
        }

        public void releaseBienVariante()
        {
            fk_precio_id_variante = null;
        }

        public void releaseMoneda()
        {
            fk_precio_id_moneda = null;
        }

        public void releaseAreaPrecio()
        {
            fk_precio_id_area = null;
        }

        public void acceptChanges()
        {
            if (null != fk_precio_id_area && fk_precio_id_area.isDependentOn())
                fk_precio_id_area.acceptChanges();
            
            if (null != fk_precio_id_moneda && fk_precio_id_moneda.isDependentOn())
                fk_precio_id_moneda.acceptChanges();
            
            if (null != fk_precio_id_variante && fk_precio_id_variante.isDependentOn())
                fk_precio_id_variante.acceptChanges();
        }
    }
}
