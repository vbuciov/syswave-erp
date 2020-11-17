package com.syswave.entidades.miempresa;

import com.syswave.entidades.keys.CompositeKeyByIdVarianteLinea;

/**
 * Pertenece al CMMS, registra las actividades contempladas para un
 * mantenimiento preventivo de alguna de las presentaciones.
 *
 * @author Victor Manuel Bucio Vargas
 */
public class PlanMantenimiento extends CompositeKeyByIdVarianteLinea
{

    private String actividad;
    private boolean activo;

    protected ForeignKey navigation;

    //---------------------------------------------------------------------
    public PlanMantenimiento()
    {
        super();
        createAtributes();
        initAtributes();
    }

    //---------------------------------------------------------------------
    public PlanMantenimiento(PlanMantenimiento that)
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
        actividad = EMPTY_STRING;
        activo = true;
    }

    //---------------------------------------------------------------------
    private void assign(PlanMantenimiento that)
    {
        super.assign(that);
        actividad = that.actividad;
        activo = that.activo;

        navigation = that.navigation;
    }

    //---------------------------------------------------------------------
    public BienVariante getHasOnePresentacion()
    {
        return null != navigation.getFk_plan_mantenimiento_id_presentacion()
                ? navigation.getFk_plan_mantenimiento_id_presentacion()
                : navigation.joinBienVariante(super.getIdVariante());
    }

    //---------------------------------------------------------------------
    public void setHasOnePresentacion(BienVariante value)
    {
        navigation.setFk_plan_mantenimiento_id_presentacion(value);
    }

    //----------------------------------------------------------------------
    @Override
    public int getIdVariante()
    {
        return null != navigation.getFk_plan_mantenimiento_id_presentacion()
                ? navigation.getFk_plan_mantenimiento_id_presentacion().getId()
                : super.getIdVariante();
    }

    //----------------------------------------------------------------------
    @Override
    public int getIdVariante_Viejo()
    {
        return null != navigation.getFk_plan_mantenimiento_id_presentacion()
                ? navigation.getFk_plan_mantenimiento_id_presentacion().getId_Viejo()
                : super.getIdVariante_Viejo();
    }

    //----------------------------------------------------------------------
    @Override
    public void setIdVariante(int value)
    {
        super.setIdVariante(value);
        navigation.releaseBienVariante();
    }

    //---------------------------------------------------------------------
    public String getActividad()
    {
        return actividad;
    }

    //---------------------------------------------------------------------
    public void setActividad(String value)
    {
        this.actividad = value;
    }

    //---------------------------------------------------------------------
    public boolean esActivo()
    {
        return activo;
    }

    //---------------------------------------------------------------------
    public void setActivo(boolean value)
    {
        this.activo = value;
    }

    //---------------------------------------------------------------------
    @Override
    public void clear()
    {
        initAtributes();
        navigation.releaseBienVariante();
    }

    //---------------------------------------------------------------------
    public void copy(PlanMantenimiento that)
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

        private BienVariante fk_plan_mantenimiento_id_presentacion;

        //---------------------------------------------------------------------
        public BienVariante getFk_plan_mantenimiento_id_presentacion()
        {
            return fk_plan_mantenimiento_id_presentacion;
        }

        //---------------------------------------------------------------------
        public void setFk_plan_mantenimiento_id_presentacion(BienVariante value)
        {
            this.fk_plan_mantenimiento_id_presentacion = value;
            this.fk_plan_mantenimiento_id_presentacion.setDependentOn(false);
        }

        //---------------------------------------------------------------------
        public BienVariante joinBienVariante(int id_variante)
        {
            fk_plan_mantenimiento_id_presentacion = new BienVariante();
            fk_plan_mantenimiento_id_presentacion.setId(id_variante);
            return fk_plan_mantenimiento_id_presentacion;
        }

        //---------------------------------------------------------------------
        public void releaseBienVariante()
        {
            fk_plan_mantenimiento_id_presentacion = null;
        }

        //---------------------------------------------------------------------   
        private void acceptChanges()
        {
            if (null != fk_plan_mantenimiento_id_presentacion && fk_plan_mantenimiento_id_presentacion.isDependentOn())
                fk_plan_mantenimiento_id_presentacion.acceptChanges();
        }
    }
}
