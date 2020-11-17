package com.syswave.entidades.miempresa;

import com.syswave.entidades.keys.CompositeKeyByBienIds;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class BienCompuesto extends CompositeKeyByBienIds
{

    private float cantidad;

    protected ForeignKey navigation;

    //---------------------------------------------------------------------
    public BienCompuesto()
    {
        super();
        createAttributes();
        initAtributes();
    }

    //---------------------------------------------------------------------
    public BienCompuesto(BienCompuesto that)
    {
        super();
        createAttributes();
        assign(that);
    }

    //---------------------------------------------------------------------
    private void createAttributes()
    {
        navigation = new ForeignKey();

    }

    //---------------------------------------------------------------------
    private void initAtributes()
    {
        cantidad = EMPTY_FLOAT;
    }

    //---------------------------------------------------------------------
    private void assign(BienCompuesto that)
    {
        super.assign(that);
        cantidad = that.cantidad;
        this.navigation = that.navigation;
    }

    //---------------------------------------------------------------------
    @Override
    public int getIdBienFormal()
    {
        return null != navigation.getFk_compuesto_formal()
                ? navigation.getFk_compuesto_formal().getId()
                : super.getIdBienFormal();
    }

    //---------------------------------------------------------------------
    @Override
    public void setIdBienFormal(int value)
    {
        super.setIdBienFormal(value);
        navigation.releaseBienVarianteCompuestoFormal();
    }

    //---------------------------------------------------------------------
    @Override
    public int getIdBienParte()
    {
        return null != navigation.getFk_compuesto_parte()
                ? navigation.getFk_compuesto_parte().getId()
                : super.getIdBienParte();
    }
    
        //---------------------------------------------------------------------
    @Override
    public int getIdBienFormal_Viejo()
    {
        return null != navigation.getFk_compuesto_formal()
                ? navigation.getFk_compuesto_formal().getId_Viejo()
                : super.getIdBienFormal_Viejo();
    }

    //---------------------------------------------------------------------
    @Override
    public void setIdBienParte(int value)
    {
        super.setIdBienParte(value);
        navigation.releaseBienVarianteCompuestoParte();
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
    public BienVariante getHasOneBienVarianteFormal()
    {
        return null != navigation.getFk_compuesto_formal()
                ? navigation.getFk_compuesto_formal()
                : navigation.joinBienVarianteCompuestoFormal(super.getIdBienFormal());
    }

    //---------------------------------------------------------------------
    public void setHasOneBienVarianteFormal(BienVariante value)
    {
        navigation.setFk_compuesto_formal(value);
    }

    //---------------------------------------------------------------------
    public BienVariante getHasOneBienVarianteParte()
    {
        return null != navigation.getFk_compuesto_parte()
                ? navigation.getFk_compuesto_parte()
                : navigation.joinBienVarianteCompuestoParte(super.getIdBienParte());
    }

    //---------------------------------------------------------------------
    public void setHasOneBienVarianteParte(BienVariante value)
    {
        navigation.setFk_compuesto_parte(value);
    }

    //---------------------------------------------------------------------
    @Override
    public void clear()
    {
        initAtributes();
        navigation.releaseBienVarianteCompuestoFormal();
        navigation.releaseBienVarianteCompuestoParte();
    }

    //---------------------------------------------------------------------
    public void copy(BienCompuesto that)
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

        protected BienVariante fk_compuesto_formal;
        protected BienVariante fk_compuesto_parte;

        public BienVariante joinBienVarianteCompuestoFormal(int id_variante)
        {
            fk_compuesto_formal = new BienVariante();
            fk_compuesto_formal.setId(id_variante);

            return fk_compuesto_formal;
        }

        public BienVariante joinBienVarianteCompuestoParte(int id_variante)
        {
            fk_compuesto_parte = new BienVariante();
            fk_compuesto_parte.setId(id_variante);

            return fk_compuesto_parte;
        }

        public void releaseBienVarianteCompuestoFormal()
        {
            fk_compuesto_formal = null;
        }

        public void releaseBienVarianteCompuestoParte()
        {
            fk_compuesto_parte = null;
        }

        //---------------------------------------------------------------------
        public BienVariante getFk_compuesto_formal()
        {
            return fk_compuesto_formal;
        }

        //---------------------------------------------------------------------
        public void setFk_compuesto_formal(BienVariante value)
        {
            this.fk_compuesto_formal = value;
            this.fk_compuesto_formal.setDependentOn(false);
        }

        //---------------------------------------------------------------------
        public BienVariante getFk_compuesto_parte()
        {
            return fk_compuesto_parte;
        }

        //---------------------------------------------------------------------
        public void setFk_compuesto_parte(BienVariante value)
        {
            this.fk_compuesto_parte = value;
            this.fk_compuesto_parte.setDependentOn(false);
        }

        //---------------------------------------------------------------------
        private void acceptChanges()
        {
            if (null != fk_compuesto_formal && fk_compuesto_formal.isDependentOn())
                fk_compuesto_formal.acceptChanges();

            if (null != fk_compuesto_parte && fk_compuesto_parte.isDependentOn())
                fk_compuesto_parte.acceptChanges();
        }

    }
}
