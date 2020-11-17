package com.syswave.entidades.miempresa;

import com.syswave.entidades.keys.PrimaryKeyById;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class CondicionPago extends PrimaryKeyById
{

    private String nombre;
    private int valor, unidad;
    private int id_tipo_condicion;

    protected ForeignKey navigation;

    //----------------------------------------------------------------------
    public CondicionPago()
    {
        super();
        createAtributes();
        initAtributes();
    }

    //----------------------------------------------------------------------
    public CondicionPago(CondicionPago that)
    {
        super();
        createAtributes();
        assign(that);
    }

    //----------------------------------------------------------------------
    private void assign(CondicionPago that)
    {
        super.assign(that);
        nombre = that.nombre;

        valor = that.valor;
        unidad = that.unidad;
        id_tipo_condicion = that.id_tipo_condicion;

        navigation = that.navigation;
    }

    //----------------------------------------------------------------------
    private void createAtributes()
    {
        navigation = new ForeignKey();
    }

    //----------------------------------------------------------------------
    private void initAtributes()
    {
        nombre = EMPTY_STRING;
        valor = EMPTY_INT;
        unidad = EMPTY_INT;
        id_tipo_condicion = EMPTY_INT;
    }

    //----------------------------------------------------------------------
    public String getNombre()
    {
        return nombre;
    }

    //----------------------------------------------------------------------
    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    //----------------------------------------------------------------------
    public Integer getValor()
    {
        return valor;
    }

    //----------------------------------------------------------------------
    public void setValor(int value)
    {
        this.valor = value;
    }

    //----------------------------------------------------------------------
    public Integer getUnidad()
    {
        return unidad;
    }

    //----------------------------------------------------------------------
    public void setUnidad(int value)
    {
        this.unidad = value;
    }

    //----------------------------------------------------------------------
    public int getId_tipo_condicion()
    {
        return null != navigation.getFk_condicion_id_tipo_condicion()
                ? navigation.getFk_condicion_id_tipo_condicion().getId()
                : id_tipo_condicion;
    }

    //----------------------------------------------------------------------
    public void setId_tipo_condicion(int id_tipo_condicion)
    {
        this.id_tipo_condicion = id_tipo_condicion;
        navigation.releaseTipoCondicion();
    }

    //----------------------------------------------------------------------
    public Valor getHasOneTipoCondicion()
    {
        return null != navigation.getFk_condicion_id_tipo_condicion()
                ? navigation.getFk_condicion_id_tipo_condicion()
                : navigation.joinTipoCondicion(id_tipo_condicion);
    }

    //----------------------------------------------------------------------
    public void setHasOneTipoCondicion(Valor value)
    {
        navigation.setFk_bien_categoria(value);
    }

    //----------------------------------------------------------------------
    @Override
    public void clear()
    {
        initAtributes();
        navigation.releaseTipoCondicion();
    }

    //----------------------------------------------------------------------
    @Override
    public void acceptChanges()
    {
        super.acceptChanges();
        navigation.acceptChanges();
    }

    //---------------------------------------------------------------------
    public void copy(CondicionPago that)
    {
        assign(that);
    }

    //---------------------------------------------------------------------
    protected class ForeignKey
    {

        private Valor fk_condicion_id_tipo_condicion;

        //---------------------------------------------------------------------
        public Valor getFk_condicion_id_tipo_condicion()
        {
            return fk_condicion_id_tipo_condicion;
        }

        //---------------------------------------------------------------------
        public void setFk_bien_categoria(Valor value)
        {
            this.fk_condicion_id_tipo_condicion = value;
            this.fk_condicion_id_tipo_condicion.setDependentOn(false);
        }

        //---------------------------------------------------------------------
        public Valor joinTipoCondicion(int id_tipo_condicion)
        {
            fk_condicion_id_tipo_condicion = new Valor();
            fk_condicion_id_tipo_condicion.setId(id_tipo_condicion);
            return fk_condicion_id_tipo_condicion;
        }

        //---------------------------------------------------------------------
        public void releaseTipoCondicion()
        {
            fk_condicion_id_tipo_condicion = null;
        }

        //---------------------------------------------------------------------   
        private void acceptChanges()
        {
            if (null != fk_condicion_id_tipo_condicion && fk_condicion_id_tipo_condicion.isDependentOn())
                fk_condicion_id_tipo_condicion.acceptChanges();
        }
    }
}
