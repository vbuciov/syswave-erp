package com.syswave.entidades.common;

import java.util.Calendar;
import java.util.Date;

/**
 * La clase base de cualquier entidad.
 *
 * @author Victor Manuel Bucio Vargas
 */
public abstract class Entidad implements IEntidad
{

    protected enum Estatus
    {
        Nuevo, Modificado, Eliminado, Ninguno
    };

    protected enum Atributo
    {
        Actual, Viejo
    };

    public static final Integer EMPTY_INT;
    public static final String EMPTY_STRING;
    public static final Date EMPTY_DATE;
    public static final Character EMPTY_CHAR;
    public static final Float EMPTY_FLOAT;
    public static final Double EMPTY_DOUBLE;
    public static final Short EMPTY_SHORT;
    private Estatus actual;
    private boolean selected, searchOnlyByPrimaryKey, dependentOn;

    static
    {
        Calendar defaultDate = Calendar.getInstance();
        defaultDate.set(1900, 0, 1, 0, 0, 0);
        EMPTY_DATE = defaultDate.getTime();
        EMPTY_INT = -1;
        EMPTY_STRING = "%s";
        EMPTY_CHAR = '\0';
        EMPTY_FLOAT = -1.0F;
        EMPTY_DOUBLE = -1.0D;
        EMPTY_SHORT = -1;
    }

    //--------------------------------------------------------------------
    public Entidad()
    {
        actual = Estatus.Nuevo;
        selected = false;
        searchOnlyByPrimaryKey = false;
        dependentOn = true;
    }

    //----------------------------------------------------------------------
    public static void initializeIntegerPK(int[] id)
    {
        for (int i = 0; i < id.length; i++)
            id[i] = EMPTY_INT;
    }
    
    //----------------------------------------------------------------------
    public static void initializeIntegerPK(Integer[] id)
    {
        for (int i = 0; i < id.length; i++)
            id[i] = EMPTY_INT;
    }

    //----------------------------------------------------------------------
    public static void initializeStringPK(String[] id)
    {
        for (int i = 0; i < id.length; i++)
            id[i] = EMPTY_STRING;
    }

    //--------------------------------------------------------------------
    /**
     * Pone una marca para identificar un objeto nuevo. Para lógica de negocios.
     */
    @Override
    public void setNew()
    {
        actual = Estatus.Nuevo;
    }

    //--------------------------------------------------------------------
    /**
     * Pone una marca para identificar un objeto modificado. Para lógica de
     * negocios.
     */
    @Override
    public void setModified()
    {
        actual = Estatus.Modificado;
    }

    //--------------------------------------------------------------------
    /**
     * Pone una marca para identificar un objeto eliminado. Para lógica de
     * negocios.
     */
    @Override
    public void setDeleted()
    {
        actual = Estatus.Eliminado;
    }

    //--------------------------------------------------------------------
    /**
     * Pone al objeto en modo de espera, llaves viejas deben ser almacenadas.
     */
    @Override
    public void acceptChanges()
    {
        actual = Estatus.Ninguno;
    }

    //--------------------------------------------------------------------
    /**
     * Indica si el objeto esta marcado como nuevo.
     */
    @Override
    public boolean isNew()
    {
        return actual == Estatus.Nuevo;
    }

    //--------------------------------------------------------------------
    /*
     * Indica si el objeto esta marcado como modificado.
     */
    @Override
    public boolean isModified()
    {
        return actual == Estatus.Modificado;
    }

    //--------------------------------------------------------------------
    /**
     * Indica si el objeto esta marcado como eliminado.
     */
    @Override
    public boolean isDeleted()
    {
        return actual == Estatus.Eliminado;
    }

    //--------------------------------------------------------------------
    /**
     * Indica si el objeto esta en cualquier modalidad distinta a la espera.
     */
    @Override
    public boolean isSet()
    {
        return actual != Estatus.Ninguno;
    }

    //--------------------------------------------------------------------
    /**
     * Indica si el objeto esta esperando valores, previo a cambiar de estado.
     */
    public boolean isWaiting()
    {
        return actual == Estatus.Ninguno;
    }

    //--------------------------------------------------------------------
    /**
     * Indica si el objeto esta seleccionado.
     */
    @Override
    public boolean isSelected()
    {
        return selected;
    }

    //--------------------------------------------------------------------
    /**
     * Indica si una entidad fue selecionada a traves de un check list.<br>
     * Pero también sirve para indicar cuando una operación de busqueda<br>
     * esta estrictamente limitada a la llaver primaria, esto incluso si<br>
     * otros criterios han sido introducidos en campos adicionales.
     */
    @Override
    public void setSelected(boolean value)
    {
        selected = value;
    }

    /**
     * Indica si el objeto solo realiza busquedas por llave primaria.
     */
    public boolean isSearchOnlyByPrimaryKey()
    {
        return searchOnlyByPrimaryKey;
    }

    /**
     * Establece un valor que indica si el objeto solo hace busquedas por llave
     * primaria.
     */
    public void setSearchOnlyByPrimaryKey(boolean value)
    {
        this.searchOnlyByPrimaryKey = value;
    }

    /**
     * Indica si el objeto debe considerarse dependiente de un todo
     */
    public boolean isDependentOn()
    {
        return dependentOn;
    }

    /*
    *Establece si el objeto es parte de otro objeto.
     */
    public void setDependentOn(boolean value)
    {
        this.dependentOn = value;
    }

}
