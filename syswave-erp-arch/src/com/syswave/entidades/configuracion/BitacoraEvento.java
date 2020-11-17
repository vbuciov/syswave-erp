package com.syswave.entidades.configuracion;

import com.syswave.entidades.keys.PrimaryKeyById;
import java.io.Serializable;
import java.util.Date;

/**
 * Es una entrada en el registro de sucesos del sistema.
 *
 * @author Victor Manuel Bucio Vargas
 * @version 26 febrero 2014
 */
public class BitacoraEvento extends PrimaryKeyById implements Serializable
{

    private Date fecha;
    private String mensaje, origen, id_usuario;
    private Integer tipo;

    protected ForeignKey navigation;

    //---------------------------------------------------------------------
    private void createAtributes()
    {
        navigation = new ForeignKey();
    }

    //---------------------------------------------------------------------
    private void initAtributes()
    {
        fecha = EMPTY_DATE;
        mensaje = EMPTY_STRING;
        origen = EMPTY_STRING;
        tipo = EMPTY_INT;
        id_usuario = EMPTY_STRING;
    }

    //---------------------------------------------------------------------
    private void assign(BitacoraEvento that)
    {
        super.assign(that);
        fecha = that.fecha;
        mensaje = that.mensaje;
        origen = that.origen;
        tipo = that.tipo;
        id_usuario = that.id_usuario;

        navigation = that.navigation;
    }

    //---------------------------------------------------------------------
    public BitacoraEvento()
    {
        createAtributes();
        initAtributes();
    }

    //---------------------------------------------------------------------
    public BitacoraEvento(BitacoraEvento that)
    {
        createAtributes();
        assign(that);
    }

    //---------------------------------------------------------------------
    public Date getFecha()
    {
        return fecha;
    }

    //---------------------------------------------------------------------
    public void setFecha(Date fecha)
    {
        this.fecha = fecha;
    }

    //---------------------------------------------------------------------
    public String getMensaje()
    {
        return mensaje;
    }

    //---------------------------------------------------------------------
    public void setMensaje(String mensaje)
    {
        this.mensaje = mensaje;
    }

    //---------------------------------------------------------------------
    public String getOrigen()
    {
        return origen;
    }

    //---------------------------------------------------------------------
    public void setOrigen(String origen)
    {
        this.origen = origen;
    }

    //---------------------------------------------------------------------
    public Integer esTipo()
    {
        return tipo;
    }

    //---------------------------------------------------------------------
    public void setTipo(Integer tipo)
    {
        this.tipo = tipo;
    }

    //---------------------------------------------------------------------
    public String getId_usuario()
    {
        return null != navigation.getFk_BitacoraEvento_usuario()
                ? navigation.getFk_BitacoraEvento_usuario().getIdentificador()
                : id_usuario;
    }
    
    //---------------------------------------------------------------------
    public void setId_usuario(String value)
    {
        id_usuario = value;
        navigation.releaseUsuario();
    }

    //---------------------------------------------------------------------
    public void setHasOneUsuario(Usuario value)
    {
        navigation.setFk_BitacoraEvento_usuario(value);
    }

    //---------------------------------------------------------------------
    public Usuario getHasOneUsuario()
    {
        return null != navigation.getFk_BitacoraEvento_usuario()
                ? navigation.getFk_BitacoraEvento_usuario()
                : navigation.joinUsuario(id_usuario);
    }

    //---------------------------------------------------------------------
    @Override
    public void clear()
    {
        initAtributes();
    }

    //---------------------------------------------------------------------
    public void copy(BitacoraEvento that)
    {
        assign(that);
    }

    //---------------------------------------------------------------------   
    public class ForeignKey
    {

        private Usuario fk_BitacoraEvento_usuario;

        //---------------------------------------------------------------------
        public Usuario joinUsuario(String identificador_usuario)
        {
            fk_BitacoraEvento_usuario = new Usuario();
            fk_BitacoraEvento_usuario.setIdentificador(identificador_usuario);
            return fk_BitacoraEvento_usuario;
        }

        //---------------------------------------------------------------------
        public void releaseUsuario()
        {
            fk_BitacoraEvento_usuario = null;
        }

        //---------------------------------------------------------------------
        public Usuario getFk_BitacoraEvento_usuario()
        {
            return fk_BitacoraEvento_usuario;
        }

        //---------------------------------------------------------------------
        public void setFk_BitacoraEvento_usuario(Usuario value)
        {
            this.fk_BitacoraEvento_usuario = value;
            this.fk_BitacoraEvento_usuario.setDependentOn(false);
        }

        //---------------------------------------------------------------------
        private void acceptChanges()
        {
            if (null != fk_BitacoraEvento_usuario && fk_BitacoraEvento_usuario.isDependentOn())
                fk_BitacoraEvento_usuario.acceptChanges();

        }
    }
}
