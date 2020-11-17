package com.syswave.entidades.miempresa;

import com.syswave.entidades.keys.CompositeKeyByPlaneacionIds;
import java.io.Serializable;

/**
 * Indica que documentos estan relacionados con una planeacion.
 *
 * @author Victor Manuel Bucio Vargas
 * @version 1 Marzo 2014
 */
public class PlaneacionPredecesor extends CompositeKeyByPlaneacionIds implements Serializable
{

    protected ForeignKey navigation;

    //---------------------------------------------------------------------
    private void createAtributes()
    {
        navigation = new ForeignKey();
    }

    //---------------------------------------------------------------------
    private void assign(PlaneacionPredecesor that)
    {
        super.assign(that);
        navigation = that.navigation;
    }

    //---------------------------------------------------------------------
    public PlaneacionPredecesor()
    {
        super();
        createAtributes();
    }

    //---------------------------------------------------------------------
    public PlaneacionPredecesor(PlaneacionPredecesor that)
    {
        super();
        createAtributes();
        assign(that);
    }

    //---------------------------------------------------------------------
    @Override
    public int getId_planeacion_siguiente()
    {
        return null != navigation.getFk_planeacion_siguiente()
                ? navigation.getFk_planeacion_siguiente().getId()
                : super.getId_planeacion_siguiente();

    }

    //---------------------------------------------------------------------
    @Override
    public int getId_planeacion_siguiente_Viejo()
    {
        return null != navigation.getFk_planeacion_siguiente()
                ? navigation.getFk_planeacion_siguiente().getId_Viejo()
                : super.getId_planeacion_siguiente_Viejo();

    }

    //---------------------------------------------------------------------
    @Override
    public void setId_planeacion_siguiente(int value)
    {
        super.setId_planeacion_siguiente(value);
        navigation.releasePlaneacionSiguiente();
    }

    //---------------------------------------------------------------------
    public Planeacion getHasOnePlaneacionSiguiente()
    {
        return null != navigation.getFk_planeacion_siguiente()
                ? navigation.getFk_planeacion_siguiente()
                : navigation.joinPlaneacionSiguiente(super.getId_planeacion_siguiente());
    }

    //---------------------------------------------------------------------
    public void setHasOnePlaneacionSiguiente(Planeacion value)
    {
        navigation.setFk_planeacion_siguiente(value);
    }

    //---------------------------------------------------------------------
    @Override
    public int getId_planeacion_anterior()
    {
        return null != navigation.getFk_planeacion_anterior()
                ? navigation.getFk_planeacion_anterior().getId()
                : super.getId_planeacion_anterior();
    }

    //---------------------------------------------------------------------
    @Override
    public int getId_planeacion_anterior_Viejo()
    {
        return null != navigation.getFk_planeacion_anterior()
                ? navigation.getFk_planeacion_anterior().getId_Viejo()
                : super.getId_planeacion_anterior_Viejo();
    }

    //---------------------------------------------------------------------
    @Override
    public void setId_planeacion_anterior(int value)
    {
        super.setId_planeacion_anterior(value);
        navigation.releasePlaneacionAnterior();
    }

    //---------------------------------------------------------------------
    public Planeacion getHasOnePlaneacionAnterior()
    {
        return null != navigation.getFk_planeacion_anterior()
                ? navigation.getFk_planeacion_anterior()
                : navigation.joinPlaneacionAnterior(super.getId_planeacion_anterior());
    }

    //---------------------------------------------------------------------
    public void setHasOnePlaneacionAnterior(Planeacion value)
    {
        navigation.setFk_planeacion_anterior(value);
    }

    //---------------------------------------------------------------------
    @Override
    public void clear()
    {
        navigation.releasePlaneacionAnterior();
        navigation.releasePlaneacionSiguiente();
    }

    //---------------------------------------------------------------------
    public void copy(PlaneacionPredecesor that)
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
    public class ForeignKey
    {

        private Planeacion fk_planeacion_siguiente;
        private Planeacion fk_planeacion_anterior;

        public Planeacion joinPlaneacionSiguiente(int id_planeacion_siguiente)
        {
            fk_planeacion_siguiente = new Planeacion();
            fk_planeacion_siguiente.setId(id_planeacion_siguiente);

            return fk_planeacion_siguiente;
        }

        public Planeacion joinPlaneacionAnterior(int id_planeacion_anterior)
        {
            fk_planeacion_anterior = new Planeacion();
            fk_planeacion_anterior.setId(id_planeacion_anterior);

            return fk_planeacion_anterior;
        }

        public void releasePlaneacionSiguiente()
        {
            fk_planeacion_siguiente = null;
        }

        public void releasePlaneacionAnterior()
        {
            fk_planeacion_anterior = null;
        }

        //---------------------------------------------------------------------
        public Planeacion getFk_planeacion_siguiente()
        {
            return fk_planeacion_siguiente;
        }

        //---------------------------------------------------------------------
        public void setFk_planeacion_siguiente(Planeacion value)
        {
            this.fk_planeacion_siguiente = value;
            this.fk_planeacion_siguiente.setDependentOn(false);
        }

        //---------------------------------------------------------------------
        public Planeacion getFk_planeacion_anterior()
        {
            return fk_planeacion_anterior;
        }

        //---------------------------------------------------------------------
        public void setFk_planeacion_anterior(Planeacion value)
        {
            this.fk_planeacion_anterior = value;
            this.fk_planeacion_anterior.setDependentOn(false);
        }

        //---------------------------------------------------------------------
        private void acceptChanges()
        {
            if (null != fk_planeacion_siguiente && fk_planeacion_siguiente.isDependentOn())
                fk_planeacion_siguiente.acceptChanges();

            if (null != fk_planeacion_anterior && fk_planeacion_anterior.isDependentOn())
                fk_planeacion_anterior.acceptChanges();
        }

    }
}
