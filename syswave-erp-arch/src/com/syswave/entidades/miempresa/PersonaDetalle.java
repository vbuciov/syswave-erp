package com.syswave.entidades.miempresa;

import com.syswave.entidades.keys.CompositeKeyByIdTipoDetallePersona;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class PersonaDetalle extends CompositeKeyByIdTipoDetallePersona
{

    private String valor;

    protected ForeignKey navigation;

    //--------------------------------------------------------------------
    public PersonaDetalle()
    {
        super();
        createAttributes();
        initAttributes();
    }

    //--------------------------------------------------------------------
    public PersonaDetalle(PersonaDetalle that)
    {
        super();
        createAttributes();
        assign(that);
    }

    //--------------------------------------------------------------------
    private void createAttributes()
    {
        navigation = new ForeignKey();
    }

    //--------------------------------------------------------------------
    private void initAttributes()
    {
        valor = EMPTY_STRING;
    }

    //--------------------------------------------------------------------
    private void assign(PersonaDetalle that)
    {
        super.assign(that);
        valor = that.valor;
        navigation = that.navigation;
    }

    //--------------------------------------------------------------------
    @Override
    public int getIdPersona()
    {
        return null != navigation.getFk_persona_detalles_id_persona()
                ? navigation.getFk_persona_detalles_id_persona().getId()
                : super.getIdPersona();
    }

    //--------------------------------------------------------------------
    @Override
    public int getIdPersona_Viejo()
    {
        return null != navigation.getFk_persona_detalles_id_persona()
                ? navigation.getFk_persona_detalles_id_persona().getId_Viejo()
                : super.getIdPersona_Viejo();
    }

    //--------------------------------------------------------------------
    @Override
    public void setIdPersona(int value)
    {
        super.setIdPersona(value);
        navigation.releasePersona();
    }

    //--------------------------------------------------------------------
    @Override
    public int getIdTipoDetalle()
    {
        return null != navigation.getFk_persona_detalles_id_tipo_detalle()
                ? navigation.getFk_persona_detalles_id_tipo_detalle().getId()
                : super.getIdTipoDetalle();
    }

    //--------------------------------------------------------------------
    @Override
    public int getIdTipoDetalle_Viejo()
    {
        return null != navigation.getFk_persona_detalles_id_tipo_detalle()
                ? navigation.getFk_persona_detalles_id_tipo_detalle().getId_Viejo()
                : super.getIdTipoDetalle_Viejo();
    }

    //--------------------------------------------------------------------
    @Override
    public void setIdTipoDetalle(int value)
    {
        super.setIdTipoDetalle(value);
        navigation.releaseTipoDetalle();
    }

    //--------------------------------------------------------------------
    public Persona getHasOnePersona()
    {
        return null != navigation.getFk_persona_detalles_id_persona()
                ? navigation.getFk_persona_detalles_id_persona()
                : navigation.joinPersonas(super.getIdPersona());
    }

    //--------------------------------------------------------------------
    public void setHasOnePersona(Persona value)
    {
        navigation.setFk_persona_detalles_id_persona(value);
    }

    //--------------------------------------------------------------------
    public Valor getHasOneTipoDetalle()
    {
        return null != navigation.getFk_persona_detalles_id_tipo_detalle()
                ? navigation.getFk_persona_detalles_id_tipo_detalle()
                : navigation.joinTipoDetalle(super.getIdTipoDetalle());
    }

    //--------------------------------------------------------------------
    public void setHasOneTipoDetalle(Valor value)
    {
        navigation.setFk_persona_detalles_id_tipo_detalle(value);
    }

    //--------------------------------------------------------------------
    public String getValor()
    {
        return valor;
    }

    //--------------------------------------------------------------------
    public void setValor(String value)
    {
        this.valor = value;
    }

    //--------------------------------------------------------------------
    @Override
    public void clear()
    {
        initAttributes();
        navigation.releasePersona();
        navigation.releaseTipoDetalle();
    }

    //--------------------------------------------------------------------
    public void copy(PersonaDetalle that)
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

        private Valor fk_persona_detalles_id_tipo_detalle;
        private Persona fk_persona_detalles_id_persona;

        //---------------------------------------------------------------------
        public Persona getFk_persona_detalles_id_persona()
        {
            return fk_persona_detalles_id_persona;
        }

        //---------------------------------------------------------------------
        public void setFk_persona_detalles_id_persona(Persona value)
        {
            this.fk_persona_detalles_id_persona = value;
            this.fk_persona_detalles_id_persona.setDependentOn(false);
        }

        //---------------------------------------------------------------------
        public Valor getFk_persona_detalles_id_tipo_detalle()
        {
            return fk_persona_detalles_id_tipo_detalle;
        }

        //---------------------------------------------------------------------
        public void setFk_persona_detalles_id_tipo_detalle(Valor value)
        {
            this.fk_persona_detalles_id_tipo_detalle = value;
            this.fk_persona_detalles_id_tipo_detalle.setDependentOn(false);
        }

        //---------------------------------------------------------------------
        public Persona joinPersonas(int id_persona)
        {
            fk_persona_detalles_id_persona = new Persona();
            fk_persona_detalles_id_persona.setId(id_persona);
            return fk_persona_detalles_id_persona;
        }

        //---------------------------------------------------------------------
        public void releasePersona()
        {
            fk_persona_detalles_id_persona = null;
        }

        //---------------------------------------------------------------------
        public Valor joinTipoDetalle(int id_tipo_detalle)
        {
            fk_persona_detalles_id_tipo_detalle = new Valor();
            fk_persona_detalles_id_tipo_detalle.setId(id_tipo_detalle);
            return fk_persona_detalles_id_tipo_detalle;
        }

        //---------------------------------------------------------------------
        public void releaseTipoDetalle()
        {
            fk_persona_detalles_id_tipo_detalle = null;
        }

        //---------------------------------------------------------------------   
        private void acceptChanges()
        {
            if (null != fk_persona_detalles_id_persona && fk_persona_detalles_id_persona.isDependentOn())
                fk_persona_detalles_id_persona.acceptChanges();

            if (null != fk_persona_detalles_id_tipo_detalle && fk_persona_detalles_id_tipo_detalle.isDependentOn())
                fk_persona_detalles_id_tipo_detalle.acceptChanges();
        }
    }
}
