package com.syswave.entidades.miempresa;

import com.syswave.entidades.keys.CompositeKeyByIdPlaneacionDocumento;
import java.io.Serializable;

/**
 * Indica que documentos estan relacionados con una planeacion.
 *
 * @author Victor Manuel Bucio Vargas
 * @version 1 Marzo 2014
 */
public class Documento_tiene_Planeacion extends CompositeKeyByIdPlaneacionDocumento implements Serializable
{

    protected ForeignKey navigation;

    //---------------------------------------------------------------------
    public Documento_tiene_Planeacion()
    {
        super();
        createAtributes();
    }

    //---------------------------------------------------------------------
    public Documento_tiene_Planeacion(Documento_tiene_Planeacion that)
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
    private void assign(Documento_tiene_Planeacion that)
    {
        navigation = that.navigation;
    }

    //---------------------------------------------------------------------
    @Override
    public int getIdDocumento()
    {
        return null != navigation.getFk_planeacion_id_documento()
                ? navigation.getFk_planeacion_id_documento().getId()
                : super.getIdDocumento();
    }

    //---------------------------------------------------------------------
    @Override
    public int getIdDocumento_Viejo()
    {
        return null != navigation.getFk_planeacion_id_documento()
                ? navigation.getFk_planeacion_id_documento().getId_Viejo()
                : super.getIdDocumento_Viejo();
    }

    //---------------------------------------------------------------------
    @Override
    public void setIdDocumento(int value)
    {
        super.setIdDocumento(value);
        navigation.releaseDocumento();
    }

    //---------------------------------------------------------------------
    public Documento getHasOneDocumento()
    {
        return null != navigation.getFk_planeacion_id_documento()
                ? navigation.getFk_planeacion_id_documento()
                : navigation.joinDocumento(super.getIdDocumento());
    }

    //---------------------------------------------------------------------
    public void setHasOneDocumento(Documento value)
    {
        navigation.setFk_planeacion_id_documento(value);
    }

    //---------------------------------------------------------------------
    @Override
    public int getIdPlaneacion()
    {
        return null != navigation.getFk_documento_id_planeacion()
                ? navigation.getFk_documento_id_planeacion().getId()
                : super.getIdPlaneacion();
    }

    //---------------------------------------------------------------------
    @Override
    public int getIdPlaneacion_Viejo()
    {
        return null != navigation.getFk_documento_id_planeacion()
                ? navigation.getFk_documento_id_planeacion().getId_Viejo()
                : super.getIdPlaneacion_Viejo();
    }

    //---------------------------------------------------------------------
    @Override
    public void setIdPlaneacion(int value)
    {
        super.setIdPlaneacion(value);
    }

    //---------------------------------------------------------------------
    public Planeacion getHasOnePlaneacion()
    {
        return null != navigation.getFk_documento_id_planeacion()
                ? navigation.getFk_documento_id_planeacion()
                : navigation.joinPlaneacion(super.getIdPlaneacion());
    }

    //---------------------------------------------------------------------
    public void setFk_documento_id_planeacion(Planeacion value)
    {
        navigation.setFk_documento_id_planeacion(value);
    }

    //---------------------------------------------------------------------
    @Override
    public void clear()
    {
        navigation.releaseDocumento();
        navigation.releasePlaneacion();
    }

    //---------------------------------------------------------------------
    public void copy(Documento_tiene_Planeacion that)
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

        private Documento fk_planeacion_id_documento;
        private Planeacion fk_documento_id_planeacion;

        //---------------------------------------------------------------------
        public Documento getFk_planeacion_id_documento()
        {
            return fk_planeacion_id_documento;
        }

        //---------------------------------------------------------------------
        public void setFk_planeacion_id_documento(Documento value)
        {
            this.fk_planeacion_id_documento = value;
            this.fk_planeacion_id_documento.setDependentOn(false);
        }

        //---------------------------------------------------------------------
        public Documento joinDocumento(int id_documento)
        {
            fk_planeacion_id_documento = new Documento();
            fk_planeacion_id_documento.setId(id_documento);
            return fk_planeacion_id_documento;
        }

        //---------------------------------------------------------------------
        public void releaseDocumento()
        {
            fk_planeacion_id_documento = null;
        }

        //---------------------------------------------------------------------
        public Planeacion getFk_documento_id_planeacion()
        {
            return fk_documento_id_planeacion;
        }

        //---------------------------------------------------------------------
        public void setFk_documento_id_planeacion(Planeacion value)
        {
            this.fk_documento_id_planeacion = value;
            this.fk_documento_id_planeacion.setDependentOn(false);
        }

        //---------------------------------------------------------------------
        public Planeacion joinPlaneacion(int id_planeacion)
        {
            fk_documento_id_planeacion = new Planeacion();
            fk_documento_id_planeacion.setId(id_planeacion);
            return fk_documento_id_planeacion;
        }

        //---------------------------------------------------------------------
        public void releasePlaneacion()
        {
            fk_documento_id_planeacion = null;
        }

        //---------------------------------------------------------------------   
        private void acceptChanges()
        {
            if (null != fk_planeacion_id_documento && fk_planeacion_id_documento.isDependentOn())
                fk_planeacion_id_documento.acceptChanges();

            if (null != fk_documento_id_planeacion && fk_documento_id_planeacion.isDependentOn())
                fk_documento_id_planeacion.acceptChanges();

        }
    }
}
