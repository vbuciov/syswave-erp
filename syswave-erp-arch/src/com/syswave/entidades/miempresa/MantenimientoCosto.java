package com.syswave.entidades.miempresa;

import com.syswave.entidades.keys.CompositeKeyByIdMantenimientoConsecutivo;

/**
 *
 * @author sis2
 */
public class MantenimientoCosto extends CompositeKeyByIdMantenimientoConsecutivo
{

    private String descripcion;
    private float monto;
    private Integer idMantenimiento;

    protected ForeignKey navigation;

    //--------------------------------------------------------------------
    public MantenimientoCosto()
    {
        super();
        createAtributes();
        InitAtributes();
    }

    //--------------------------------------------------------------------
    public MantenimientoCosto(MantenimientoCosto that)
    {
        super();
        createAtributes();
        assign(that);
    }

    //--------------------------------------------------------------------
    private void createAtributes()
    {
        navigation = new ForeignKey();
    }

    //--------------------------------------------------------------------
    private void InitAtributes()
    {
        descripcion = EMPTY_STRING;
        monto = EMPTY_FLOAT;
        idMantenimiento = EMPTY_INT;
    }

    //--------------------------------------------------------------------
    private void assign(MantenimientoCosto that)
    {
        super.assign(that);
        descripcion = that.descripcion;
        monto = that.monto;
        idMantenimiento = that.idMantenimiento;

        navigation = that.navigation;
    }

    //---------------------------------------------------------------------
    @Override
    public int getIdMantenimiento()
    {
        return null != navigation.getFk_mantenimiento_costo_id_mantenimiento()
                ? navigation.getFk_mantenimiento_costo_id_mantenimiento().getId()
                : super.getIdMantenimiento();
    }
    
        //---------------------------------------------------------------------
    @Override
    public int getIdMantenimiento_Viejo()
    {
        return null != navigation.getFk_mantenimiento_costo_id_mantenimiento()
                ? navigation.getFk_mantenimiento_costo_id_mantenimiento().getId_Viejo()
                : super.getIdMantenimiento_Viejo();
    }

    //---------------------------------------------------------------------
    @Override
    public void setIdMantenimiento(int value)
    {
        idMantenimiento = value;
        navigation.releaseMantenimiento();
    }

    //---------------------------------------------------------------------
    public Mantenimiento getHasOneMantemiento()
    {
        return null != navigation.getFk_mantenimiento_costo_id_mantenimiento()
                ? navigation.getFk_mantenimiento_costo_id_mantenimiento()
                : navigation.joinMantenimiento(super.getIdMantenimiento());
    }

    //---------------------------------------------------------------------
    public void setHasOneMantenimiento(Mantenimiento value)
    {
        navigation.setFk_mantenimiento_costo_id_mantenimiento(value);
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
    @Override
    public void clear()
    {
        InitAtributes();
        navigation.releaseMantenimiento();
    }

    //---------------------------------------------------------------------
    public void copy(MantenimientoCosto that)
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

        private Mantenimiento Fk_mantenimiento_costo_id_mantenimiento;

        //---------------------------------------------------------------------
        public Mantenimiento getFk_mantenimiento_costo_id_mantenimiento()
        {
            return Fk_mantenimiento_costo_id_mantenimiento;
        }

        //---------------------------------------------------------------------
        public void setFk_mantenimiento_costo_id_mantenimiento(Mantenimiento value)
        {
            this.Fk_mantenimiento_costo_id_mantenimiento = value;
            this.Fk_mantenimiento_costo_id_mantenimiento.setDependentOn(false);
        }

        //---------------------------------------------------------------------
        public Mantenimiento joinMantenimiento(int id_mantenimiento)
        {
            Fk_mantenimiento_costo_id_mantenimiento = new Mantenimiento();
            Fk_mantenimiento_costo_id_mantenimiento.setId(id_mantenimiento);
            return Fk_mantenimiento_costo_id_mantenimiento;
        }

        //---------------------------------------------------------------------
        public void releaseMantenimiento()
        {
            Fk_mantenimiento_costo_id_mantenimiento = null;
        }

        //---------------------------------------------------------------------   
        private void acceptChanges()
        {
            if (null != Fk_mantenimiento_costo_id_mantenimiento && Fk_mantenimiento_costo_id_mantenimiento.isDependentOn())
                Fk_mantenimiento_costo_id_mantenimiento.acceptChanges();
        }
    }
}
