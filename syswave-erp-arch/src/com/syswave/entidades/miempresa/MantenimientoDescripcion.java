package com.syswave.entidades.miempresa;

import com.syswave.entidades.keys.CompositeKeyByIdMantenimientoLinea;

/**
 * Pertenece al CMMS, registra descripciones detalladas del procedimiento
 * efectuado, de forma categorizada.
 *
 * @author Victor Manuel Bucio Vargas
 */
public class MantenimientoDescripcion extends CompositeKeyByIdMantenimientoLinea
{

    private String texto;
    private Integer idTipoDescripcion;

    protected ForeignKey navigation;
    
        //---------------------------------------------------------------------
    public MantenimientoDescripcion()
    {
        super();
        createAtributes();
        initAtributes();
    }

    //---------------------------------------------------------------------
    public MantenimientoDescripcion(MantenimientoDescripcion that)
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
        texto = EMPTY_STRING;
        idTipoDescripcion = EMPTY_INT;
    }

    //---------------------------------------------------------------------
    private void assign(MantenimientoDescripcion that)
    {
        super.assign(that);
        texto = that.texto;
        idTipoDescripcion = that.idTipoDescripcion;
        navigation = that.navigation;
    }

    //---------------------------------------------------------------------
    @Override
    public int getIdMantenimiento()
    {
        return null != navigation.getFk_mantenimiento_desc_id_mantenimiento()
                ? navigation.getFk_mantenimiento_desc_id_mantenimiento().getId()
                : super.getIdMantenimiento();
    }
    
    //---------------------------------------------------------------------
    @Override
    public int getIdMantenimiento_Viejo()
    {
        return null != navigation.getFk_mantenimiento_desc_id_mantenimiento()
                ? navigation.getFk_mantenimiento_desc_id_mantenimiento().getId_Viejo()
                : super.getIdMantenimiento_Viejo();
    }

    //---------------------------------------------------------------------
    @Override
    public void setIdMantenimiento(int value)
    {
        super.setIdMantenimiento(value);
        navigation.releaseMantenimiento();
    }

    //---------------------------------------------------------------------
    public void setIdTipoDescripcion(int value)
    {
        idTipoDescripcion = value;
        navigation.releaseTipoDescripcion();
    }

    //---------------------------------------------------------------------
    public int getIdTipoDescripcion()
    {
        return null != navigation.getFk_mantenimiento_desc_id_tipo_descripcion()
                ? navigation.getFk_mantenimiento_desc_id_tipo_descripcion().getId()
                : idTipoDescripcion;
    }

    //---------------------------------------------------------------------
    public String getTexto()
    {
        return texto;
    }

    //---------------------------------------------------------------------
    public void setTexto(String value)
    {
        this.texto = value;
    }

    //---------------------------------------------------------------------
    public Mantenimiento getHasOneMantenimiento()
    {
        return null != navigation.getFk_mantenimiento_desc_id_mantenimiento()
                ? navigation.getFk_mantenimiento_desc_id_mantenimiento()
                : navigation.joinMantenimiento(super.getIdMantenimiento());
    }

    //---------------------------------------------------------------------
    public void setHasOneMantenimiento(Mantenimiento value)
    {
        navigation.setFk_mantenimiento_desc_id_mantenimiento(value);
    }

    //---------------------------------------------------------------------
    public Valor getHasOneTipoDescripcion()
    {
        return null != navigation.getFk_mantenimiento_desc_id_tipo_descripcion()
                ? navigation.getFk_mantenimiento_desc_id_tipo_descripcion()
                : navigation.joinTipoDescripcion(idTipoDescripcion);
    }

    //---------------------------------------------------------------------
    public void setHasOneTipoDescripcion(Valor value)
    {
        navigation.setFk_mantenimiento_desc_id_tipo_descripcion(value);
    }

    //---------------------------------------------------------------------
    @Override
    public void clear()
    {
        initAtributes();
        navigation.releaseMantenimiento();
        navigation.releaseTipoDescripcion();
    }

    //---------------------------------------------------------------------
    public void copy(MantenimientoDescripcion that)
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

        private Mantenimiento fk_mantenimiento_desc_id_mantenimiento;
        private Valor fk_mantenimiento_desc_id_tipo_descripcion;

        //---------------------------------------------------------------------
        public Mantenimiento getFk_mantenimiento_desc_id_mantenimiento()
        {
            return fk_mantenimiento_desc_id_mantenimiento;
        }

        //---------------------------------------------------------------------
        public void setFk_mantenimiento_desc_id_mantenimiento(Mantenimiento value)
        {
            this.fk_mantenimiento_desc_id_mantenimiento = value;
            this.fk_mantenimiento_desc_id_mantenimiento.setDependentOn(false);
        }

        //---------------------------------------------------------------------
        public Mantenimiento joinMantenimiento(int id_mantenimiento)
        {
            fk_mantenimiento_desc_id_mantenimiento = new Mantenimiento();
            fk_mantenimiento_desc_id_mantenimiento.setId(id_mantenimiento);
            return fk_mantenimiento_desc_id_mantenimiento;
        }

        //---------------------------------------------------------------------
        public void releaseMantenimiento()
        {
            fk_mantenimiento_desc_id_mantenimiento = null;
        }

        //---------------------------------------------------------------------
        public Valor getFk_mantenimiento_desc_id_tipo_descripcion()
        {
            return fk_mantenimiento_desc_id_tipo_descripcion;
        }

        //---------------------------------------------------------------------
        public void setFk_mantenimiento_desc_id_tipo_descripcion(Valor value)
        {
            this.fk_mantenimiento_desc_id_tipo_descripcion = value;
            this.fk_mantenimiento_desc_id_tipo_descripcion.setDependentOn(false);
        }

        //---------------------------------------------------------------------
        public Valor joinTipoDescripcion(int id_tipo_descripcion)
        {
            fk_mantenimiento_desc_id_tipo_descripcion = new Valor();
            fk_mantenimiento_desc_id_tipo_descripcion.setId(id_tipo_descripcion);
            return fk_mantenimiento_desc_id_tipo_descripcion;
        }

        //---------------------------------------------------------------------
        public void releaseTipoDescripcion()
        {
            fk_mantenimiento_desc_id_tipo_descripcion = null;
        }

        //---------------------------------------------------------------------   
        private void acceptChanges()
        {
            if (null != fk_mantenimiento_desc_id_mantenimiento && fk_mantenimiento_desc_id_mantenimiento.isDependentOn())
                fk_mantenimiento_desc_id_mantenimiento.acceptChanges();

            if (null != fk_mantenimiento_desc_id_tipo_descripcion && fk_mantenimiento_desc_id_tipo_descripcion.isDependentOn())
                fk_mantenimiento_desc_id_tipo_descripcion.acceptChanges();
        }
    }
}
