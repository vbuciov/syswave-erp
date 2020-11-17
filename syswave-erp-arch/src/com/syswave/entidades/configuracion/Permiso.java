package com.syswave.entidades.configuracion;

import com.syswave.entidades.keys.CompositeKeyByIdModuloHash;
import java.io.Serializable;

/**
 * Representa los alias, que comparten un mismo usuario físico
 *
 * @author Victor Manuel Bucio Vargas
 * @version 26 febrero 2014
 */
public class Permiso extends CompositeKeyByIdModuloHash implements Serializable
{

    private Boolean activo, una_vez;

    protected ForeignKey navigation;

    //---------------------------------------------------------------------
    private void createAtributes()
    {
        navigation = new ForeignKey();
    }

    //---------------------------------------------------------------------
    private void initAtributes()
    {
        activo = true;
        una_vez = false;
    }

    //---------------------------------------------------------------------
    private void asign(Permiso that)
    {
        super.assign(that);
        activo = that.activo;
        una_vez = that.una_vez;
    }

    //---------------------------------------------------------------------
    public Permiso()
    {
        super();
        createAtributes();
        initAtributes();
    }

    //---------------------------------------------------------------------
    public Permiso(Permiso that)
    {
        super();
        createAtributes();
        asign(that);
    }

    //---------------------------------------------------------------------
    public Permiso(String llave)
    {
        super(llave);
        createAtributes();
        initAtributes();
    }

    //---------------------------------------------------------------------
    @Override
    public Integer getId_modulo()
    {
        return null != navigation.getfk_modulo_instalado()
                ? navigation.getfk_modulo_instalado().getId()
                : super.getId_modulo();
    }
    
    //---------------------------------------------------------------------
    @Override
    public Integer getId_modulo_Viejo()
    {
        return null != navigation.getfk_modulo_instalado()
                ? navigation.getfk_modulo_instalado().getId_Viejo()
                : super.getId_modulo_Viejo();
    }

    //---------------------------------------------------------------------
    @Override
    public void setId_modulo(Integer value)
    {
        super.setId_modulo(value);
        navigation.releaseModuloInstalado();
    }

    //---------------------------------------------------------------------
    public ModuloInstalado getHasOneModulo_instalado()
    {
        return null != navigation.getfk_modulo_instalado()
                ? navigation.getfk_modulo_instalado()
                : navigation.joinModuloInstalado(super.getId_modulo());
    }

    //---------------------------------------------------------------------
    public void setHasOneModuloInstalado(ModuloInstalado fk_modulo_instalado)
    {
        navigation.setfk_modulo_instalado(fk_modulo_instalado);
    }

    //---------------------------------------------------------------------
    public Boolean esActivo()
    {
        return activo;
    }

    //---------------------------------------------------------------------
    public void setActivo(Boolean activo)
    {
        this.activo = activo;
    }

    //---------------------------------------------------------------------
    public Boolean esUna_vez()
    {
        return una_vez;
    }

    //---------------------------------------------------------------------
    public void setUna_vez(Boolean una_vez)
    {
        this.una_vez = una_vez;
    }

    //---------------------------------------------------------------------
    @Override
    public void clear()
    {
        super.clear();
        navigation.releaseModuloInstalado();
        initAtributes();
    }

    //---------------------------------------------------------------------
    public void copy(Permiso that)
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

        private ModuloInstalado fk_modulo_instalado;

        //---------------------------------------------------------------------
        public ModuloInstalado getfk_modulo_instalado()
        {
            return fk_modulo_instalado;
        }

        //---------------------------------------------------------------------
        public void setfk_modulo_instalado(ModuloInstalado value)
        {
            this.fk_modulo_instalado = value;
            this.fk_modulo_instalado.setDependentOn(false);
        }

        //---------------------------------------------------------------------
        public ModuloInstalado joinModuloInstalado(int id_modulo_instalado)
        {
            fk_modulo_instalado = new ModuloInstalado();
            fk_modulo_instalado.setId(id_modulo_instalado);
            return fk_modulo_instalado;
        }

        //---------------------------------------------------------------------
        public void releaseModuloInstalado()
        {
            fk_modulo_instalado = null;
        }

        //---------------------------------------------------------------------   
        private void acceptChanges()
        {
            if (null != fk_modulo_instalado && fk_modulo_instalado.isDependentOn())
                fk_modulo_instalado.acceptChanges();
        }
    }
}
