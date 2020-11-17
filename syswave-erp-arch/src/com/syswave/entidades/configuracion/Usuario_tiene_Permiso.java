package com.syswave.entidades.configuracion;

import com.syswave.entidades.keys.CompositeKeyByIdUsuarioOrigen;
import java.io.Serializable;

/**
 * Representa un permiso asignado a un usuario
 *
 * @author Victor Manuel Bucio Vargas
 * @version 26 febrero 2014
 */
public class Usuario_tiene_Permiso extends CompositeKeyByIdUsuarioOrigen implements Serializable
{

    private Boolean usado;

    protected ForeignKey navigation;
    //---------------------------------------------------------------------

    private void createAtributes()
    {
        navigation = new ForeignKey();
    }

    //---------------------------------------------------------------------
    /**
     * Este método inicializa a valores de construcción los campos.
     */
    private void initAtributes()
    {
        usado = false;
    }

    //---------------------------------------------------------------------
    /**
     * Este método asigna las propiedades de otro objeto en el actual
     */
    private void assign(Usuario_tiene_Permiso that)
    {
        super.assign(that);
        this.usado = that.usado;
        navigation = that.navigation;
    }

    //---------------------------------------------------------------------
    public Usuario_tiene_Permiso()
    {
        super();
        createAtributes();
        initAtributes();
    }

    //---------------------------------------------------------------------
    public Usuario_tiene_Permiso(Usuario_tiene_Permiso that)
    {
        super();
        createAtributes();
        assign(that);
    }

    //---------------------------------------------------------------------
    @Override
    public String getId_usuario()
    {
        return null != navigation.getFk_usuario_tiene_permiso()
                ? navigation.getFk_usuario_tiene_permiso().getIdentificador()
                : super.getId_usuario();
    }
    
    //---------------------------------------------------------------------
    @Override
     public String getId_usuario_Viejo()
    {
        return null != navigation.getFk_usuario_tiene_permiso()
                ? navigation.getFk_usuario_tiene_permiso().getIdentificador_Viejo()
                : super.getId_usuario_Viejo();
    }

    //---------------------------------------------------------------------
    @Override
    public void setId_usuario(String value)
    {
        super.setId_usuario(value);
        navigation.releaseUsuario();
    }

    //---------------------------------------------------------------------
    @Override
    public Integer getId_origen_dato()
    {
        return null != navigation.getFk_usuario_tiene_origen_dato()
                ? navigation.getFk_usuario_tiene_origen_dato().getId()
                : super.getId_origen_dato();
    }
    
    //---------------------------------------------------------------------
    @Override
    public Integer getId_origen_dato_Viejo()
    {
        return null != navigation.getFk_usuario_tiene_origen_dato()
                ? navigation.getFk_usuario_tiene_origen_dato().getId_Viejo()
                : super.getId_origen_dato_Viejo();
    }

    //---------------------------------------------------------------------
    @Override
    public void setId_origen_dato(Integer value)
    {
        super.setId_origen_dato(value);
        navigation.releaseOrigenDato();
    }

    //---------------------------------------------------------------------
    @Override
    public Integer getId_modulo()
    {
        return null != navigation.getfk_permiso_tiene_usuario()
                ? navigation.getfk_permiso_tiene_usuario().getId_modulo()
                : super.getId_modulo();
    }
    
        //---------------------------------------------------------------------
    @Override
    public Integer getId_modulo_Viejo()
    {
        return null != navigation.getfk_permiso_tiene_usuario()
                ? navigation.getfk_permiso_tiene_usuario().getId_modulo_Viejo()
                : super.getId_modulo_Viejo();
    }

    //---------------------------------------------------------------------
    @Override
    public void setId_modulo(Integer value)
    {
        super.setId_modulo(value);
        navigation.releasePermiso();
    }

    //---------------------------------------------------------------------
    @Override
    public String getLlave()
    {
        return null != navigation.getfk_permiso_tiene_usuario()
                ? navigation.getfk_permiso_tiene_usuario().getLlave()
                : super.getLlave();
    }
    
        //---------------------------------------------------------------------
    @Override
    public String getLlave_Viejo()
    {
        return null != navigation.getfk_permiso_tiene_usuario()
                ? navigation.getfk_permiso_tiene_usuario().getLlave_Viejo()
                : super.getLlave_Viejo();
    }

    //---------------------------------------------------------------------
    @Override
    public void setLlave(String value)
    {
        super.setLlave(value);
        navigation.releasePermiso();
    }

    //---------------------------------------------------------------------
    public OrigenDato getHasOneOrigenDato()
    {
        return null != navigation.getFk_usuario_tiene_origen_dato()
                ? navigation.getFk_usuario_tiene_origen_dato()
                : navigation.joinOrigenDato(super.getId_origen_dato());
    }

    //---------------------------------------------------------------------
    public void setHasOneOrigenDato(OrigenDato value)
    {
        navigation.setFk_usuario_tiene_origen_dato(value);
    }

    //---------------------------------------------------------------------
    public Usuario getHasOneUsuario()
    {
        return null != navigation.getFk_usuario_tiene_permiso()
                ? navigation.getFk_usuario_tiene_permiso()
                : navigation.joinUsuario(super.getId_usuario());
    }

    //---------------------------------------------------------------------
    public void setHasOneUsuario(Usuario fk_usuario_tiene_permiso)
    {
        navigation.setFk_usuario_tiene_permiso(fk_usuario_tiene_permiso);
    }

    //---------------------------------------------------------------------
    public Permiso getHasOnePermiso()
    {
        return null != navigation.getfk_permiso_tiene_usuario()
                ? navigation.getfk_permiso_tiene_usuario()
                : navigation.joinPermiso(super.getId_modulo(), super.getLlave());
    }

    //---------------------------------------------------------------------
    public void setHasOnePermiso(Permiso fk_permiso_tiene_usuario)
    {
        navigation.setFk_permiso_tiene_usuario(fk_permiso_tiene_usuario);
    }

    //---------------------------------------------------------------------
    public Boolean esUsado()
    {
        return usado;
    }

    //---------------------------------------------------------------------
    public void setUsado(Boolean usado)
    {
        this.usado = usado;
    }

    //---------------------------------------------------------------------
    @Override
    public void clear()
    {
        usado = false;
        navigation.releaseOrigenDato();
        navigation.releasePermiso();
        navigation.releaseUsuario();
    }

    //---------------------------------------------------------------------
    public void copy(Usuario_tiene_Permiso that)
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

        private Usuario fk_usuario_tiene_permiso;
        private Permiso fk_permiso_tiene_usuario;
        private OrigenDato fk_usuario_tiene_origen_dato;

        //---------------------------------------------------------------------
        public Usuario getFk_usuario_tiene_permiso()
        {
            return fk_usuario_tiene_permiso;
        }

        //---------------------------------------------------------------------
        public void setFk_usuario_tiene_permiso(Usuario value)
        {
            this.fk_usuario_tiene_permiso = value;
            this.fk_usuario_tiene_permiso.setDependentOn(false);
        }

        //---------------------------------------------------------------------
        public Usuario joinUsuario(String id_usuario)
        {
            fk_usuario_tiene_permiso = new Usuario();
            fk_usuario_tiene_permiso.setIdentificador(id_usuario);
            return fk_usuario_tiene_permiso;
        }

        //---------------------------------------------------------------------
        public void releaseUsuario()
        {
            fk_usuario_tiene_permiso = null;
        }

        //---------------------------------------------------------------------
        public Permiso getfk_permiso_tiene_usuario()
        {
            return fk_permiso_tiene_usuario;
        }

        //---------------------------------------------------------------------
        public void setFk_permiso_tiene_usuario(Permiso value)
        {
            this.fk_permiso_tiene_usuario = value;
            this.fk_permiso_tiene_usuario.setDependentOn(false);
        }

        //---------------------------------------------------------------------
        public Permiso joinPermiso(int id_modulo, String llave)
        {
            fk_permiso_tiene_usuario = new Permiso();
            fk_permiso_tiene_usuario.setId_modulo(id_modulo);
            fk_permiso_tiene_usuario.setLlave(llave);
            return fk_permiso_tiene_usuario;
        }

        //---------------------------------------------------------------------
        public void releasePermiso()
        {
            fk_permiso_tiene_usuario = null;
        }

        //---------------------------------------------------------------------
        public OrigenDato getFk_usuario_tiene_origen_dato()
        {
            return fk_usuario_tiene_origen_dato;
        }

        //---------------------------------------------------------------------
        public void setFk_usuario_tiene_origen_dato(OrigenDato value)
        {
            this.fk_usuario_tiene_origen_dato = value;
            this.fk_usuario_tiene_origen_dato.setDependentOn(false);
        }

        //---------------------------------------------------------------------
        public OrigenDato joinOrigenDato(int id_origen)
        {
            fk_usuario_tiene_origen_dato = new OrigenDato();
            fk_usuario_tiene_origen_dato.setId(id_origen);
            return fk_usuario_tiene_origen_dato;
        }

        //---------------------------------------------------------------------
        public void releaseOrigenDato()
        {
            fk_usuario_tiene_origen_dato = null;
        }

        //---------------------------------------------------------------------   
        private void acceptChanges()
        {
            if (null != fk_usuario_tiene_permiso && fk_usuario_tiene_permiso.isDependentOn())
                fk_usuario_tiene_permiso.acceptChanges();

            if (null != fk_permiso_tiene_usuario && fk_permiso_tiene_usuario.isDependentOn())
                fk_permiso_tiene_usuario.acceptChanges();

            if (null != fk_usuario_tiene_origen_dato && fk_usuario_tiene_origen_dato.isDependentOn())
                fk_usuario_tiene_origen_dato.acceptChanges();

        }
    }
}
