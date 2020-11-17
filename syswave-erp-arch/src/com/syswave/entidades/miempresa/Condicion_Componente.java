package com.syswave.entidades.miempresa;

import com.syswave.entidades.keys.PrimaryKeyById;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class Condicion_Componente extends PrimaryKeyById
{

    private int valor;
    private short es_unidad;
    private short es_tipo;
    private int id_condicion;

    protected ForeignKey navigation;

    public Condicion_Componente()
    {
        super();
        createAtributes();
        initAtributes();
    }

    public Condicion_Componente(Condicion_Componente that)
    {
        super();
        createAtributes();
        assign(that);
    }

    private void createAtributes()
    {
        navigation = new ForeignKey();
    }

    private void initAtributes()
    {
        valor = EMPTY_INT;
        es_unidad = EMPTY_SHORT;
        es_tipo = EMPTY_SHORT;
        id_condicion = EMPTY_INT;
    }

    private void assign(Condicion_Componente that)
    {
        super.assign(that);
        valor = that.valor;
        es_unidad = that.es_unidad;
        es_tipo = that.es_tipo;
        id_condicion = that.id_condicion;
        navigation = that.navigation;
    }

    public int getValor()
    {
        return valor;
    }

    public void setValor(int valor)
    {
        this.valor = valor;
    }

    public short getEs_unidad()
    {
        return es_unidad;
    }

    public void setEs_unidad(short es_unidad)
    {
        this.es_unidad = es_unidad;
    }

    public short getEs_tipo()
    {
        return es_tipo;
    }

    public void setEs_tipo(short es_tipo)
    {
        this.es_tipo = es_tipo;
    }

    //---------------------------------------------------------------------
    public int getIdCondicion()
    {
        return null != navigation.getFk_condicion_id()
                ? navigation.getFk_condicion_id().getId()
                : id_condicion;
    }

    //---------------------------------------------------------------------
    public void setIdCondicion(int value)
    {

        id_condicion = value;
        navigation.releaseCondicion();
    }

    //---------------------------------------------------------------------
    public Condicion getHasOneCondicion()
    {
        return null != navigation.getFk_condicion_id()
                ? navigation.getFk_condicion_id()
                : navigation.joinCondicion(id_condicion);
    }

    //---------------------------------------------------------------------
    public void setHasOneCondicion(Condicion value)
    {
        navigation.setFk_bien_categoria(value);
    }

    //---------------------------------------------------------------------
    @Override
    public void clear()
    {
        initAtributes();
        navigation.releaseCondicion();
    }

    //---------------------------------------------------------------------
    @Override
    public void acceptChanges()
    {
        super.acceptChanges();
        navigation.acceptChanges();
    }

    //---------------------------------------------------------------------
    public void copy(Condicion_Componente that)
    {
        assign(that);
    }

    //---------------------------------------------------------------------
    protected class ForeignKey
    {

        private Condicion fk_condicion_id;

        //---------------------------------------------------------------------
        public Condicion getFk_condicion_id()
        {
            return fk_condicion_id;
        }

        //---------------------------------------------------------------------
        public void setFk_bien_categoria(Condicion value)
        {
            this.fk_condicion_id = value;
            this.fk_condicion_id.setDependentOn(false);
        }

        //---------------------------------------------------------------------
        public Condicion joinCondicion(int id_condicion)
        {
            fk_condicion_id = new Condicion();
            fk_condicion_id.setId(id_condicion);
            return fk_condicion_id;
        }

        //---------------------------------------------------------------------
        public void releaseCondicion()
        {
            fk_condicion_id = null;
        }

        //---------------------------------------------------------------------   
        private void acceptChanges()
        {
            if (null != fk_condicion_id && fk_condicion_id.isDependentOn())
                fk_condicion_id.acceptChanges();
        }
    }

}
