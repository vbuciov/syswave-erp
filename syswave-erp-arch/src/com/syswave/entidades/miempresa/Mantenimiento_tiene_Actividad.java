package com.syswave.entidades.miempresa;

import com.syswave.entidades.keys.CompositeKeyByIdMantenimientoVariante;

/**
 * Pertenece al CMMS, registra las actividades realizadas realmente al momento
 * de efectuar un mantenimiento preventivo.
 *
 * @author Victor Manuel Bucio Vargas
 */
public class Mantenimiento_tiene_Actividad extends CompositeKeyByIdMantenimientoVariante
{

    protected ForeignKey navigation;

    //--------------------------------------------------------------------
    public Mantenimiento_tiene_Actividad()
    {
        super();
        createAtributes();
    }

    //--------------------------------------------------------------------
    public Mantenimiento_tiene_Actividad(Mantenimiento_tiene_Actividad that)
    {
        super();
        createAtributes();
        asign(that);
    }

    //--------------------------------------------------------------------
    private void createAtributes()
    {
        navigation = new ForeignKey();
    }

    //--------------------------------------------------------------------
    private void asign(Mantenimiento_tiene_Actividad that)
    {
        super.assign(that);
        navigation = that.navigation;
    }

    //--------------------------------------------------------------------
    @Override
    public int getLinea()
    {
        return null != navigation.getFk_mantenimiento_plan_id_actividad()
                ? navigation.getFk_mantenimiento_plan_id_actividad().getLinea()
                : super.getLinea();
    }

    //--------------------------------------------------------------------
    @Override
    public int getLinea_Viejo()
    {
        return null != navigation.getFk_mantenimiento_plan_id_actividad()
                ? navigation.getFk_mantenimiento_plan_id_actividad().getLinea_Viejo()
                : super.getLinea_Viejo();
    }

    //--------------------------------------------------------------------
    @Override
    public void setLinea(int value)
    {
        super.setLinea(value);
        navigation.releasePlanMantenimiento();
    }

    //--------------------------------------------------------------------
    @Override
    public int getIdVariante()
    {
        return null != navigation.getFk_mantenimiento_plan_id_actividad()
                ? navigation.getFk_mantenimiento_plan_id_actividad().getIdVariante()
                : super.getIdVariante();
    }

    //--------------------------------------------------------------------
    @Override
    public int getIdVariante_Viejo()
    {
        return null != navigation.getFk_mantenimiento_plan_id_actividad()
                ? navigation.getFk_mantenimiento_plan_id_actividad().getIdVariante_Viejo()
                : super.getIdVariante_Viejo();
    }

    //--------------------------------------------------------------------
    @Override
    public void setIdVariante(int value)
    {
        super.setIdVariante(value);
        navigation.releasePlanMantenimiento();
    }

    //--------------------------------------------------------------------
    @Override
    public int getIdMantenimiento()
    {
        return null != navigation.getFk_mantenimiento_plan_id_mantenimiento()
                ? navigation.getFk_mantenimiento_plan_id_mantenimiento().getId()
                : super.getIdMantenimiento();
    }

    //--------------------------------------------------------------------
    @Override
    public int getIdMantenimiento_Viejo()
    {
        return null != navigation.getFk_mantenimiento_plan_id_mantenimiento()
                ? navigation.getFk_mantenimiento_plan_id_mantenimiento().getId_Viejo()
                : super.getIdMantenimiento_Viejo();
    }

    //--------------------------------------------------------------------
    @Override
    public void setIdMantenimiento(int value)
    {
        super.setIdMantenimiento(value);
        navigation.releaseMantenimiento();
    }

    //--------------------------------------------------------------------
    public PlanMantenimiento getHasOneActividad()
    {
        return null != navigation.getFk_mantenimiento_plan_id_actividad()
                ? navigation.getFk_mantenimiento_plan_id_actividad()
                : navigation.joinPlanMantenimiento(super.getIdVariante(), super.getLinea());
    }

    //--------------------------------------------------------------------
    public void setHasOneActividad(PlanMantenimiento value)
    {
        navigation.setFk_mantenimiento_plan_id_actividad(value);
    }

    //--------------------------------------------------------------------
    public Mantenimiento getHasOneMantenimiento()
    {
        return null != navigation.getFk_mantenimiento_plan_id_mantenimiento()
                ? navigation.getFk_mantenimiento_plan_id_mantenimiento()
                : navigation.joinMantenimiento(super.getIdMantenimiento());
    }

    //--------------------------------------------------------------------
    public void setHasOneMantenimiento(Mantenimiento value)
    {
        navigation.setFk_mantenimiento_plan_id_mantenimiento(value);
    }

    //--------------------------------------------------------------------
    @Override
    public void clear()
    {
        navigation.releaseMantenimiento();
        navigation.releasePlanMantenimiento();
    }

    //--------------------------------------------------------------------
    public void copy(Mantenimiento_tiene_Actividad that)
    {
        asign(that);
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

        private PlanMantenimiento fk_mantenimiento_plan_id_actividad;
        private Mantenimiento fk_mantenimiento_plan_id_mantenimiento;

        //---------------------------------------------------------------------
        public PlanMantenimiento getFk_mantenimiento_plan_id_actividad()
        {
            return fk_mantenimiento_plan_id_actividad;
        }

        //---------------------------------------------------------------------
        public void setFk_mantenimiento_plan_id_actividad(PlanMantenimiento value)
        {
            this.fk_mantenimiento_plan_id_actividad = value;
            this.fk_mantenimiento_plan_id_actividad.setDependentOn(false);
        }

        //---------------------------------------------------------------------
        public PlanMantenimiento joinPlanMantenimiento(int id_variante, int linea)
        {
            fk_mantenimiento_plan_id_actividad = new PlanMantenimiento();
            fk_mantenimiento_plan_id_actividad.setIdVariante(id_variante);
            fk_mantenimiento_plan_id_actividad.setLinea(linea);
            return fk_mantenimiento_plan_id_actividad;
        }

        //---------------------------------------------------------------------
        public void releasePlanMantenimiento()
        {
            fk_mantenimiento_plan_id_actividad = null;
        }

        //---------------------------------------------------------------------
        public Mantenimiento getFk_mantenimiento_plan_id_mantenimiento()
        {
            return fk_mantenimiento_plan_id_mantenimiento;
        }

        //---------------------------------------------------------------------
        public void setFk_mantenimiento_plan_id_mantenimiento(Mantenimiento value)
        {
            this.fk_mantenimiento_plan_id_mantenimiento = value;
            this.fk_mantenimiento_plan_id_mantenimiento.setDependentOn(false);
        }

        //---------------------------------------------------------------------
        public Mantenimiento joinMantenimiento(int id_mantenimiento)
        {
            fk_mantenimiento_plan_id_mantenimiento = new Mantenimiento();
            fk_mantenimiento_plan_id_mantenimiento.setId(id_mantenimiento);
            return fk_mantenimiento_plan_id_mantenimiento;
        }

        //---------------------------------------------------------------------
        public void releaseMantenimiento()
        {
            fk_mantenimiento_plan_id_mantenimiento = null;
        }

        //---------------------------------------------------------------------   
        private void acceptChanges()
        {
            if (null != fk_mantenimiento_plan_id_actividad && fk_mantenimiento_plan_id_actividad.isDependentOn())
                fk_mantenimiento_plan_id_actividad.acceptChanges();

            if (null != fk_mantenimiento_plan_id_mantenimiento && fk_mantenimiento_plan_id_mantenimiento.isDependentOn())
                fk_mantenimiento_plan_id_mantenimiento.acceptChanges();
        }
    }
}
