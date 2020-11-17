package com.syswave.entidades.miempresa;

import com.syswave.entidades.keys.CompositeKeyByIdVarianteConsecutivo;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class BienVarianteFoto extends CompositeKeyByIdVarianteConsecutivo
{

    private int acho, alto, longitud, id_variante;
    private String formato, observacion;
    private byte[] imagen;
    private byte[] miniatura;

    protected ForeignKey navigation;

    //--------------------------------------------------------------------
    public BienVarianteFoto()
    {
        super();
        createAtributes();
        initAtributes();
    }

    //--------------------------------------------------------------------
    public BienVarianteFoto(BienVarianteFoto that)
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
    private void initAtributes()
    {
        acho = EMPTY_INT;
        alto = EMPTY_INT;
        longitud = EMPTY_INT;
        formato = EMPTY_STRING;
        observacion = EMPTY_STRING;
        id_variante = EMPTY_INT;
        imagen = null;
        miniatura = null;
    }

    //---------------------------------------------------------------------
    private void assign(BienVarianteFoto that)
    {
        super.assign(that);
        acho = that.acho;
        alto = that.alto;
        longitud = that.longitud;
        formato = that.formato;
        observacion = that.observacion;
        imagen = that.imagen;
        miniatura = that.miniatura;
        id_variante = that.id_variante;

        navigation = that.navigation;
    }

    //---------------------------------------------------------------------
    @Override
    public int getIdVariante()
    {
        return null != navigation.getFk_bien_variante_foto_id_presentacion()
                ? navigation.getFk_bien_variante_foto_id_presentacion().getId()
                : id_variante;
    }

    //---------------------------------------------------------------------
    @Override
    public void setIdVariante(int value)
    {
        id_variante = value;
        navigation.releaseBienVariantes();
    }

    //---------------------------------------------------------------------
    public BienVariante getHasOnePresentacion()
    {
        return null != navigation.getFk_bien_variante_foto_id_presentacion()
                ? navigation.getFk_bien_variante_foto_id_presentacion()
                : navigation.joinBienVariantes(id_variante);
    }

    //---------------------------------------------------------------------
    public void setHasOnePresentacion(BienVariante value)
    {
        navigation.setFk_bien_variante_foto_id_presentacion(value);
    }

    //---------------------------------------------------------------------
    public int getAcho()
    {
        return acho;
    }

    //---------------------------------------------------------------------
    public void setAcho(int value)
    {
        this.acho = value;
    }

    //---------------------------------------------------------------------
    public int getAlto()
    {
        return alto;
    }

    //---------------------------------------------------------------------
    public void setAlto(int value)
    {
        this.alto = value;
    }

    //---------------------------------------------------------------------
    public int getLongitud()
    {
        return longitud;
    }

    //---------------------------------------------------------------------
    public void setLongitud(int value)
    {
        this.longitud = value;
    }

    //---------------------------------------------------------------------
    public String getFormato()
    {
        return formato;
    }

    //---------------------------------------------------------------------
    public void setFormato(String value)
    {
        this.formato = value;
    }

    //---------------------------------------------------------------------
    public String getObservacion()
    {
        return observacion;
    }

    //---------------------------------------------------------------------
    public void setObservacion(String value)
    {
        this.observacion = value;
    }

    //---------------------------------------------------------------------
    public byte[] getImagen()
    {
        return imagen;
    }

    //---------------------------------------------------------------------
    public void setImagen(byte[] value)
    {
        this.imagen = value;
    }

    //---------------------------------------------------------------------
    public byte[] getMiniatura()
    {
        return miniatura;
    }

    //---------------------------------------------------------------------
    public void setMiniatura(byte[] value)
    {
        this.miniatura = value;
    }

    //--------------------------------------------------------------------
    @Override
    public void clear()
    {
        initAtributes();
    }

    //--------------------------------------------------------------------
    public void copy(BienVarianteFoto that)
    {
        assign(that);
    }

    //--------------------------------------------------------------------
    @Override
    public void acceptChanges()
    {
        super.acceptChanges();
        navigation.acceptChanges();
    }

    //---------------------------------------------------------------------   
    public class ForeignKey
    {

        protected BienVariante fk_bien_variante_foto_id_presentacion;

        //---------------------------------------------------------------------
        public BienVariante joinBienVariantes(int id_presentacion)
        {
            fk_bien_variante_foto_id_presentacion = new BienVariante();
            fk_bien_variante_foto_id_presentacion.setId(id_presentacion);
            return fk_bien_variante_foto_id_presentacion;
        }

        //---------------------------------------------------------------------
        public void releaseBienVariantes()
        {
            fk_bien_variante_foto_id_presentacion = null;
        }

        //---------------------------------------------------------------------
        public BienVariante getFk_bien_variante_foto_id_presentacion()
        {
            return fk_bien_variante_foto_id_presentacion;
        }

        //---------------------------------------------------------------------
        public void setFk_bien_variante_foto_id_presentacion(BienVariante value)
        {
            this.fk_bien_variante_foto_id_presentacion = value;
            this.fk_bien_variante_foto_id_presentacion.setDependentOn(false);
        }

        //---------------------------------------------------------------------
        public void acceptChanges()
        {
            if (null != fk_bien_variante_foto_id_presentacion && fk_bien_variante_foto_id_presentacion.isDependentOn())
                fk_bien_variante_foto_id_presentacion.acceptChanges();
        }
    }

}
