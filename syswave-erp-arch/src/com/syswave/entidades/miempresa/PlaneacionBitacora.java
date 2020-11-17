package com.syswave.entidades.miempresa;

import com.syswave.entidades.keys.CompositeKeyByIdPlaneacionConsecutivo;
import java.io.Serializable;
import java.sql.Date;

/**
 * Es una historia de usuario.
 *
 * @author Victor Manuel Bucio Vargas
 * @version 1 Marzo 2014
 */
public class PlaneacionBitacora extends CompositeKeyByIdPlaneacionConsecutivo implements Serializable
{

    private Date fecha;
    private String descripcion;
    private Float tiempo;
    private Integer unidad;
    private Integer idPersona;

    protected ForeignKey navigation;

    //---------------------------------------------------------------------
    public PlaneacionBitacora()
    {
        super();
        createAtributes();
        initAtributes();
    }

    //---------------------------------------------------------------------   
    public PlaneacionBitacora(PlaneacionBitacora that)
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
        fecha = new Date(EMPTY_DATE.getTime());
        descripcion = EMPTY_STRING;
        tiempo = EMPTY_FLOAT;
        unidad = EMPTY_INT;
        idPersona = EMPTY_INT;
    }

    //---------------------------------------------------------------------
    private void assign(PlaneacionBitacora that)
    {
        super.assign(that);
        fecha = that.fecha;
        descripcion = that.descripcion;
        tiempo = that.tiempo;
        unidad = that.unidad;
        navigation = that.navigation;
    }

    //---------------------------------------------------------------------
    @Override
    public int getIdPlaneacion()
    {
        return null != navigation.getfk_planeacion_bitacora()
                ? navigation.getfk_planeacion_bitacora().getId()
                : super.getIdPlaneacion();
    }

//---------------------------------------------------------------------
    @Override
    public int getIdPlaneacion_Viejo()
    {
        return null != navigation.getfk_planeacion_bitacora()
                ? navigation.getfk_planeacion_bitacora().getId_Viejo()
                : super.getIdPlaneacion_Viejo();
    }
    
    //---------------------------------------------------------------------
    @Override
    public void setIdPlaneacion(int value)
    {
        super.setIdPlaneacion(value);
        navigation.releasePlaneacion();
    }

    //---------------------------------------------------------------------
    public Planeacion getHasOnePlaneacion()
    {
        return null != navigation.getfk_planeacion_bitacora()
                ? navigation.getfk_planeacion_bitacora()
                : navigation.joinPlaneacion(super.getIdPlaneacion());
    }

    //---------------------------------------------------------------------
    public void setHasOnePlaneacion(Planeacion value)
    {
        navigation.setFk_planeacion_bitacora(value);
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
    public String getDescripcion()
    {
        return descripcion;
    }

    //---------------------------------------------------------------------
    public void setDescripcion(String descripcion)
    {
        this.descripcion = descripcion;
    }

    //---------------------------------------------------------------------
    public Float getTiempo()
    {
        return tiempo;
    }

    //---------------------------------------------------------------------
    public void setTiempo(Float tiempo)
    {
        this.tiempo = tiempo;
    }

    //---------------------------------------------------------------------
    public Integer getUnidad()
    {
        return unidad;
    }

    //---------------------------------------------------------------------
    public void setUnidad(Integer unidad)
    {
        this.unidad = unidad;
    }

    //---------------------------------------------------------------------
    public Integer getIdPersona()
    {
        return null != navigation.getFk_planeacion_bitacora_id_persona()
                ? navigation.getFk_planeacion_bitacora_id_persona().getId()
                : idPersona;

    }

    //---------------------------------------------------------------------
    public void setIdPersona(Integer value)
    {
        idPersona = value;
        navigation.releasePersona();
    }

    //---------------------------------------------------------------------
    public Persona getHasOnePersona()
    {
        return null != navigation.getFk_planeacion_bitacora_id_persona()
                ? navigation.getFk_planeacion_bitacora_id_persona()
                : navigation.joinPersonas(idPersona);
    }

    //---------------------------------------------------------------------
    public void setHasOnePersona(Persona value)
    {
        navigation.setFk_planeacion_bitacora_id_persona(value);
    }

    //---------------------------------------------------------------------
    @Override
    public void clear()
    {
        initAtributes();
        navigation.releasePersona();
        navigation.releasePlaneacion();
    }

    //---------------------------------------------------------------------
    public void copy(PlaneacionBitacora that)
    {
        assign(that);
    }

    @Override
    public void acceptChanges()
    {
        super.acceptChanges();
        navigation.acceptChanges();
    }

    //---------------------------------------------------------------------
    protected class ForeignKey
    {

        private Planeacion fk_planeacion_bitacora;
        private Persona fk_planeacion_bitacora_id_persona;

        //---------------------------------------------------------------------
        public Persona getFk_planeacion_bitacora_id_persona()
        {
            return fk_planeacion_bitacora_id_persona;
        }

        //---------------------------------------------------------------------
        public void setFk_planeacion_bitacora_id_persona(Persona value)
        {
            this.fk_planeacion_bitacora_id_persona = value;
            this.fk_planeacion_bitacora_id_persona.setDependentOn(false);
        }

        //---------------------------------------------------------------------
        public Persona joinPersonas(int id_persona)
        {
            fk_planeacion_bitacora_id_persona = new Persona();
            fk_planeacion_bitacora_id_persona.setId(id_persona);
            return fk_planeacion_bitacora_id_persona;
        }

        //---------------------------------------------------------------------
        public void releasePersona()
        {
            fk_planeacion_bitacora_id_persona = null;
        }

        //---------------------------------------------------------------------
        public Planeacion getfk_planeacion_bitacora()
        {
            return fk_planeacion_bitacora;
        }

        //---------------------------------------------------------------------
        public void setFk_planeacion_bitacora(Planeacion value)
        {
            this.fk_planeacion_bitacora = value;
            this.fk_planeacion_bitacora.setDependentOn(false);
        }

        //---------------------------------------------------------------------
        public Planeacion joinPlaneacion(int id_planeacion)
        {
            fk_planeacion_bitacora = new Planeacion();
            fk_planeacion_bitacora.setId(id_planeacion);
            return fk_planeacion_bitacora;
        }

        //---------------------------------------------------------------------
        public void releasePlaneacion()
        {
            fk_planeacion_bitacora = null;
        }

        //---------------------------------------------------------------------   
        private void acceptChanges()
        {
            if (null != fk_planeacion_bitacora_id_persona && fk_planeacion_bitacora_id_persona.isDependentOn())
                fk_planeacion_bitacora_id_persona.acceptChanges();

            if (null != fk_planeacion_bitacora && fk_planeacion_bitacora.isDependentOn())
                fk_planeacion_bitacora.acceptChanges();
        }
    }
}
