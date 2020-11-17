package com.syswave.entidades.miempresa;

import com.syswave.entidades.keys.CompositeKeyByMonedaIds;
import java.util.Date;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class MonedaCambio extends CompositeKeyByMonedaIds
{

    private float proporcion;
    private Date fecha_validez;

    protected ForeignKey navigation;

    //---------------------------------------------------------------------
    public MonedaCambio()
    {
        super();
        createAtributes();
        initAtributes();
    }

    //---------------------------------------------------------------------
    public MonedaCambio(MonedaCambio that)
    {
        super();
        createAtributes();
        assign(that);
    }

    //---------------------------------------------------------------------
    private void initAtributes()
    {
        proporcion = EMPTY_FLOAT;
        fecha_validez = EMPTY_DATE;
    }

    //---------------------------------------------------------------------
    private void createAtributes()
    {
        navigation = new ForeignKey();
    }

    //---------------------------------------------------------------------
    private void assign(MonedaCambio that)
    {
        super.assign(that);
        proporcion = that.proporcion;
        fecha_validez = that.fecha_validez;

        navigation = that.navigation;
    }

    //---------------------------------------------------------------------
    @Override
    public void clear()
    {
        initAtributes();
        navigation.releaseMonedaDestino();
        navigation.releaseMonedaOrigen();
    }

    //---------------------------------------------------------------------
    public void copy(MonedaCambio that)
    {
        assign(that);
    }

    //---------------------------------------------------------------------
    public float getProporcion()
    {
        return proporcion;
    }

    //---------------------------------------------------------------------
    public void setProporcion(float proporcion)
    {
        this.proporcion = proporcion;
    }

    //---------------------------------------------------------------------
    public Moneda getHasOneMonedaOrigen()
    {
        return null != navigation.getFk_moneda_cambio_id_moneda_origen()
                ? navigation.getFk_moneda_cambio_id_moneda_origen()
                : navigation.joinMonedaOrigen(super.getIdMonedaOrigen());
    }

    //---------------------------------------------------------------------
    public void setHasOneMonedaOrigen(Moneda value)
    {
        navigation.setFk_moneda_cambio_id_moneda_origen(value);
    }

    //---------------------------------------------------------------------
    @Override
    public int getIdMonedaOrigen()
    {
        return null != navigation.getFk_moneda_cambio_id_moneda_origen()
                ? navigation.getFk_moneda_cambio_id_moneda_origen().getId()
                : super.getIdMonedaOrigen();
    }

    //---------------------------------------------------------------------
    @Override
    public int getIdMonedaOrigen_Viejo()
    {
        return null != navigation.getFk_moneda_cambio_id_moneda_origen()
                ? navigation.getFk_moneda_cambio_id_moneda_origen().getId_Viejo()
                : super.getIdMonedaOrigen_Viejo();
    }

    //---------------------------------------------------------------------
    @Override
    public void setIdMonedaOrigen(int value)
    {
        super.setIdMonedaOrigen(value);
        navigation.releaseMonedaOrigen();
    }
    //---------------------------------------------------------------------

    public Moneda getHasOneMonedaDestino()
    {
        return null != navigation.getFk_moneda_cambio_id_moneda_destino()
                ? navigation.getFk_moneda_cambio_id_moneda_destino()
                : navigation.joinMonedaDestino(super.getIdMonedaDestino());
    }

    //---------------------------------------------------------------------
    public void setHasOneMonedaDestino(Moneda value)
    {
        navigation.setFk_moneda_cambio_id_moneda_destino(value);
    }

    //---------------------------------------------------------------------
    @Override
    public int getIdMonedaDestino()
    {
        return null != navigation.getFk_moneda_cambio_id_moneda_destino()
                ? navigation.getFk_moneda_cambio_id_moneda_destino().getId()
                : super.getIdMonedaDestino();
    }

    //---------------------------------------------------------------------
    @Override
    public int getIdMonedaDestino_Viejo()
    {
        return null != navigation.getFk_moneda_cambio_id_moneda_destino()
                ? navigation.getFk_moneda_cambio_id_moneda_destino().getId_Viejo()
                : super.getIdMonedaDestino_Viejo();
    }

    //---------------------------------------------------------------------
    @Override
    public void setIdMonedaDestino(int value)
    {
        super.setIdMonedaDestino(value);
        navigation.releaseMonedaDestino();
    }

    //---------------------------------------------------------------------
    public Date getFecha_validez()
    {
        return fecha_validez;
    }

    //---------------------------------------------------------------------
    public void setFecha_validez(Date fecha_validez)
    {
        this.fecha_validez = fecha_validez;
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

        private Moneda fk_moneda_cambio_id_moneda_origen;
        private Moneda fk_moneda_cambio_id_moneda_destino;

        //---------------------------------------------------------------------
        public Moneda getFk_moneda_cambio_id_moneda_origen()
        {
            return fk_moneda_cambio_id_moneda_origen;
        }

        //---------------------------------------------------------------------
        public void setFk_moneda_cambio_id_moneda_origen(Moneda value)
        {
            this.fk_moneda_cambio_id_moneda_origen = value;
            this.fk_moneda_cambio_id_moneda_origen.setDependentOn(false);
        }

        //---------------------------------------------------------------------
        public Moneda joinMonedaOrigen(int id_moneda)
        {
            fk_moneda_cambio_id_moneda_origen = new Moneda();
            fk_moneda_cambio_id_moneda_origen.setId(id_moneda);
            return fk_moneda_cambio_id_moneda_origen;
        }

        //---------------------------------------------------------------------
        public void releaseMonedaOrigen()
        {
            fk_moneda_cambio_id_moneda_origen = null;
        }

        //---------------------------------------------------------------------
        public Moneda getFk_moneda_cambio_id_moneda_destino()
        {
            return fk_moneda_cambio_id_moneda_destino;
        }

        //---------------------------------------------------------------------
        public void setFk_moneda_cambio_id_moneda_destino(Moneda value)
        {
            this.fk_moneda_cambio_id_moneda_destino = value;
            this.fk_moneda_cambio_id_moneda_destino.setDependentOn(false);
        }

        //---------------------------------------------------------------------
        public Moneda joinMonedaDestino(int id_moneda)
        {
            fk_moneda_cambio_id_moneda_destino = new Moneda();
            fk_moneda_cambio_id_moneda_destino.setId(id_moneda);
            return fk_moneda_cambio_id_moneda_destino;
        }

        //---------------------------------------------------------------------
        public void releaseMonedaDestino()
        {
            fk_moneda_cambio_id_moneda_destino = null;
        }

        //---------------------------------------------------------------------   
        private void acceptChanges()
        {
            if (null != fk_moneda_cambio_id_moneda_origen && fk_moneda_cambio_id_moneda_origen.isDependentOn())
                fk_moneda_cambio_id_moneda_origen.acceptChanges();

            if (null != fk_moneda_cambio_id_moneda_destino && fk_moneda_cambio_id_moneda_destino.isDependentOn())
                fk_moneda_cambio_id_moneda_destino.acceptChanges();
        }
    }
}
