package com.syswave.entidades.miempresa;

import com.syswave.entidades.keys.PrimaryKeyById;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class Bien extends PrimaryKeyById
{

    private String nombre;
    private int es_tipo;
    private int id_categoria;
    private int id_clase_inventario;

    protected ForeignKey navigation;

    //---------------------------------------------------------------------
    public Bien()
    {
        super();
        createAtributes();
        initAtributes();
    }

    //---------------------------------------------------------------------
    public Bien(Bien that)
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
        nombre = EMPTY_STRING;
        es_tipo = EMPTY_INT;
        id_categoria = EMPTY_INT;
        id_clase_inventario = EMPTY_INT;
    }

    //---------------------------------------------------------------------
    private void assign(Bien that)
    {
        super.assign(that);
        nombre = that.nombre;
        es_tipo = that.es_tipo;
        id_categoria = that.id_categoria;

        navigation = that.navigation;
    }

    //---------------------------------------------------------------------
    public String getNombre()
    {
        return nombre;
    }

    //---------------------------------------------------------------------
    public void setNombre(String value)
    {
        this.nombre = value;
    }

    //---------------------------------------------------------------------
    public int getEsTipo()
    {
        return es_tipo;
    }

    //---------------------------------------------------------------------
    public void setEsTipo(int value)
    {
        this.es_tipo = value;
    }

    //---------------------------------------------------------------------
    public int getIdCategoria()
    {
        return null != navigation.getFk_bien_categoria()
                ? navigation.getFk_bien_categoria().getId()
                : id_categoria;
    }

    //---------------------------------------------------------------------
    public void setIdCategoria(int value)
    {
        navigation.releaseCategoria();
        id_categoria = value;
    }

    //---------------------------------------------------------------------
    public int getIdClaseInventario()
    {
        return null != navigation.getFk_bien_clase_inventario()
                ? navigation.getFk_bien_clase_inventario().getId()
                : id_clase_inventario;
    }

    //---------------------------------------------------------------------
    public void setIdClaseInventario(int value)
    {
        navigation.releaseClaseInventario();
        id_clase_inventario = value;
    }

    //---------------------------------------------------------------------
    public Categoria getHasOneCategoria()
    {
        return null != navigation.getFk_bien_categoria()
                ? navigation.getFk_bien_categoria()
                : navigation.joinCategorias(id_categoria);
    }

    //--------------------------------------------------------------------
    public void setHasOneCategoria(Categoria value)
    {
        navigation.setFk_bien_categoria(value);
    }

    //---------------------------------------------------------------------
    public ClaseInventario getHasOneClaseInventario()
    {
        return null != navigation.getFk_bien_clase_inventario()
                ? navigation.getFk_bien_clase_inventario()
                : navigation.joinClaseInventario(id_clase_inventario);
    }

    //--------------------------------------------------------------------
    public void setHasOneClaseInventario(ClaseInventario value)
    {
        navigation.setFk_bien_clase_inventario(value);
    }

    //---------------------------------------------------------------------
    @Override
    public void clear()
    {
        initAtributes();
        navigation.releaseCategoria();
        navigation.releaseClaseInventario();
    }

    //---------------------------------------------------------------------
    public void copy(Bien that)
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

        protected Categoria fk_bien_categoria;
        protected ClaseInventario fk_bien_clase_inventario;

        //---------------------------------------------------------------------
        public Categoria getFk_bien_categoria()
        {
            return fk_bien_categoria;
        }

        //---------------------------------------------------------------------
        public void setFk_bien_categoria(Categoria value)
        {
            this.fk_bien_categoria = value;
            this.fk_bien_categoria.setDependentOn(false);
        }

        //---------------------------------------------------------------------
        public ClaseInventario getFk_bien_clase_inventario()
        {
            return fk_bien_clase_inventario;
        }

        //---------------------------------------------------------------------
        public void setFk_bien_clase_inventario(ClaseInventario value)
        {
            this.fk_bien_clase_inventario = value;
            this.fk_bien_clase_inventario.setDependentOn(false);
        }

        //---------------------------------------------------------------------
        public Categoria joinCategorias(int id_categoria)
        {
            fk_bien_categoria = new Categoria();
            fk_bien_categoria.setId(id_categoria);
            return fk_bien_categoria;
        }

        //---------------------------------------------------------------------
        public void releaseCategoria()
        {
            fk_bien_categoria = null;
        }

        //---------------------------------------------------------------------
        public ClaseInventario joinClaseInventario(int id_categoria)
        {
            fk_bien_clase_inventario = new ClaseInventario();
            fk_bien_clase_inventario.setId(id_categoria);
            return fk_bien_clase_inventario;
        }

        //---------------------------------------------------------------------
        public void releaseClaseInventario()
        {
            fk_bien_clase_inventario = null;
        }

        //---------------------------------------------------------------------   
        private void acceptChanges()
        {
            if (null != fk_bien_clase_inventario && fk_bien_clase_inventario.isDependentOn())
                fk_bien_clase_inventario.acceptChanges();

            if (null != fk_bien_categoria && fk_bien_categoria.isDependentOn())
                fk_bien_categoria.acceptChanges();
        }
    }
}
