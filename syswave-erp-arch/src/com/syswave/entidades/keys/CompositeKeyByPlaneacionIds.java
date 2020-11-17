package com.syswave.entidades.keys;

import com.syswave.entidades.common.Entidad;
import static com.syswave.entidades.common.Entidad.EMPTY_INT;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public abstract class CompositeKeyByPlaneacionIds extends Entidad
{
    private int[] id_planeacion_anterior, id_planeacion_siguiente;

    //---------------------------------------------------------------------
    private void createAtributes()
    {
        id_planeacion_anterior = new int[Entidad.Atributo.values().length];
        id_planeacion_siguiente = new int[Entidad.Atributo.values().length];
    }

    //---------------------------------------------------------------------
    private void initAtributes()
    {
        Entidad.initializeIntegerPK(id_planeacion_anterior);
        Entidad.initializeIntegerPK(id_planeacion_siguiente);
    }

    //---------------------------------------------------------------------
    public CompositeKeyByPlaneacionIds()
    {
        createAtributes();
        initAtributes();
    }

    //---------------------------------------------------------------------
    public String getCompositeKey()
    {
        return String.format("%d,%d", getId_planeacion_anterior_Viejo(), getId_planeacion_siguiente_Viejo());
    }

    //---------------------------------------------------------------------
    public int getId_planeacion_anterior()
    {
        return id_planeacion_anterior[Entidad.Atributo.Actual.ordinal()];
    }

    //---------------------------------------------------------------------
    public void setId_planeacion_anterior(int value)
    {
        if (isWaiting())
            id_planeacion_anterior[Entidad.Atributo.Viejo.ordinal()]
                    = id_planeacion_anterior[Entidad.Atributo.Actual.ordinal()];

        id_planeacion_anterior[Entidad.Atributo.Actual.ordinal()] = value;
    }

    //---------------------------------------------------------------------
    public int getId_planeacion_anterior_Viejo()
    {
        if (isModified())
            return id_planeacion_anterior[Entidad.Atributo.Viejo.ordinal()] != EMPTY_INT
                    ? id_planeacion_anterior[Entidad.Atributo.Viejo.ordinal()]
                    : getId_planeacion_anterior();
        else
            return getId_planeacion_anterior();
    }

    //---------------------------------------------------------------------
    public int getId_planeacion_siguiente()
    {
        return id_planeacion_siguiente[Entidad.Atributo.Actual.ordinal()];
    }

    //---------------------------------------------------------------------
    public void setId_planeacion_siguiente(int value)
    {
        if (isWaiting())
            id_planeacion_siguiente[Entidad.Atributo.Viejo.ordinal()]
                    = id_planeacion_siguiente[Entidad.Atributo.Actual.ordinal()];

        id_planeacion_siguiente[Entidad.Atributo.Actual.ordinal()] = value;
    }

    //---------------------------------------------------------------------
    public int getId_planeacion_siguiente_Viejo()
    {
        if (isModified())
            return id_planeacion_siguiente[Entidad.Atributo.Viejo.ordinal()] != EMPTY_INT
                    ? id_planeacion_siguiente[Entidad.Atributo.Viejo.ordinal()]
                    : getId_planeacion_siguiente();
        else
            return getId_planeacion_siguiente();
    }

    //---------------------------------------------------------------------
    @Override
    public void clear()
    {
        initAtributes();
    }

    //---------------------------------------------------------------------
    public void assign(CompositeKeyByPlaneacionIds that)
    {
       setId_planeacion_anterior(that.getId_planeacion_anterior());
       setId_planeacion_siguiente(that.getId_planeacion_siguiente());
    }
}
