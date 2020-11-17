package com.syswave.entidades.miempresa;

import com.syswave.entidades.keys.CompositeKeyByIdVarianteConsecutivo;
import java.util.Date;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class ControlInventario extends CompositeKeyByIdVarianteConsecutivo
{

    private String lote;
    private float existencia, minimo, maximo, reorden;
    private Date fecha_entrada, fecha_caducidad, fecha_devolucion;

    protected ForeignKey navigation;

    //---------------------------------------------------------------------
    public ControlInventario()
    {
        super();
        createAtributes();
        initAtributes();

    }

    //---------------------------------------------------------------------
    public ControlInventario(ControlInventario that)
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
        lote = EMPTY_STRING;
        existencia = EMPTY_FLOAT;
        minimo = EMPTY_FLOAT;
        maximo = EMPTY_FLOAT;
        reorden = EMPTY_FLOAT;
        fecha_entrada = EMPTY_DATE;
        fecha_caducidad = EMPTY_DATE;
        fecha_devolucion = EMPTY_DATE;
    }

    //---------------------------------------------------------------------
    private void assign(ControlInventario that)
    {
        super.assign(that);
        lote = that.lote;
        existencia = that.existencia;
        minimo = that.minimo;
        maximo = that.maximo;
        reorden = that.reorden;
        fecha_entrada = that.fecha_entrada;
        fecha_caducidad = that.fecha_caducidad;
        fecha_devolucion = that.fecha_devolucion;

        navigation = that.navigation;
    }

    //---------------------------------------------------------------------
    @Override
    public int getIdVariante()
    {
        return null != navigation.getFk_inventario_variante_id()
                ? navigation.getFk_inventario_variante_id().getId()
                : super.getIdVariante();
    }

    //---------------------------------------------------------------------
    @Override
    public int getIdVariante_Viejo()
    {
        return null != navigation.getFk_inventario_variante_id()
                ? navigation.getFk_inventario_variante_id().getId_Viejo()
                : super.getIdVariante_Viejo();
    }

    //---------------------------------------------------------------------
    @Override
    public void setIdVariante(int value)
    {
        super.setIdVariante(value);
        navigation.releaseBienVariante();
    }

    //---------------------------------------------------------------------
    public BienVariante getHasOneBienVariante()
    {
        return null != navigation.getFk_inventario_variante_id()
                ? navigation.getFk_inventario_variante_id()
                : navigation.joinBienVariante(super.getIdVariante());
    }

    //---------------------------------------------------------------------
    public void setHasOneBienVariante(BienVariante value)
    {
        navigation.setFk_inventario_variante_id(value);
    }

    //---------------------------------------------------------------------
    public String getLote()
    {
        return lote;
    }

    //---------------------------------------------------------------------
    public void setLote(String lote)
    {
        this.lote = lote;
    }

    //---------------------------------------------------------------------
    public float getExistencia()
    {
        return existencia;
    }

    //---------------------------------------------------------------------
    public void setExistencia(float existencia)
    {
        this.existencia = existencia;
    }

    //---------------------------------------------------------------------
    public float getMinimo()
    {
        return minimo;
    }

    //---------------------------------------------------------------------
    public void setMinimo(float minimo)
    {
        this.minimo = minimo;
    }

    //---------------------------------------------------------------------
    public float getMaximo()
    {
        return maximo;
    }

    //---------------------------------------------------------------------
    public void setMaximo(float maximo)
    {
        this.maximo = maximo;
    }

    //---------------------------------------------------------------------
    public float getReorden()
    {
        return reorden;
    }

    //---------------------------------------------------------------------
    public void setReorden(float reorden)
    {
        this.reorden = reorden;
    }

    //---------------------------------------------------------------------
    public Date getFecha_entrada()
    {
        return fecha_entrada;
    }

    //---------------------------------------------------------------------
    public void setFecha_entrada(Date fecha_entrada)
    {
        this.fecha_entrada = fecha_entrada;
    }

    //---------------------------------------------------------------------
    public Date getFecha_caducidad()
    {
        return fecha_caducidad;
    }

    //---------------------------------------------------------------------
    public void setFecha_caducidad(Date fecha_caducidad)
    {
        this.fecha_caducidad = fecha_caducidad;
    }

    //---------------------------------------------------------------------
    public Date getFecha_devolucion()
    {
        return fecha_devolucion;
    }

    //---------------------------------------------------------------------
    public void setFecha_devolucion(Date fecha_devolucion)
    {
        this.fecha_devolucion = fecha_devolucion;
    }

    //---------------------------------------------------------------------
    @Override
    public void clear()
    {
        initAtributes();
        navigation.releaseBienVariante();
    }

    //---------------------------------------------------------------------
    public void copy(ControlInventario that)
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

        private BienVariante fk_inventario_variante_id;

        //---------------------------------------------------------------------
        public BienVariante getFk_inventario_variante_id()
        {
            return fk_inventario_variante_id;
        }

        //---------------------------------------------------------------------
        public void setFk_inventario_variante_id(BienVariante value)
        {
            this.fk_inventario_variante_id = value;
            this.fk_inventario_variante_id.setDependentOn(false);
        }

        //---------------------------------------------------------------------
        public BienVariante joinBienVariante(int id_variante)
        {
            fk_inventario_variante_id = new BienVariante();
            fk_inventario_variante_id.setId(id_variante);
            return fk_inventario_variante_id;
        }

        //---------------------------------------------------------------------
        public void releaseBienVariante()
        {
            fk_inventario_variante_id = null;
        }

        //---------------------------------------------------------------------   
        private void acceptChanges()
        {
            if (null != fk_inventario_variante_id && fk_inventario_variante_id.isDependentOn())
                fk_inventario_variante_id.acceptChanges();
        }
    }
}
