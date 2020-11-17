package com.syswave.datos.common;

import com.syswave.entidades.common.Entidad;
import datalayer.BootstrapDataAccess;
import datalayer.api.IMediatorDataSource;
import java.util.List;

/**
 * En este trabajo se requiere que todos los dataAccess Compartan conexión al
 * mismo server.
 *
 * @author Victor Manuel Bucio Vargas
 * @param <T> El tipo de dato con el que se realizara el acceso a datos
 */
public abstract class SingletonDataAccess<T extends Entidad> extends BootstrapDataAccess<T>
{

    //--------------------------------------------------------------------
    /**
     * Genera las operaciones basicas CRUD para la tabla especificada.
     * Nota: obtiene el origen de los datos de forma automatica.
     *
     * @param tableName Tabla especificada
     */
    public SingletonDataAccess(IMediatorDataSource mysource, String tableName)
    {
        //super (FilesBase.getInstance());
        super(mysource, tableName);
    }

    //--------------------------------------------------------------------
    /**
     * Genera las operaciones basicas CRUD para la tabla especificada. 
     * Nota 1: Sin embargo solo para la proyeccion especificada.
     * Nota 2: obtiene el origen de los datos de forma automatica.
     * @param tableName Tabla especificada
     * @param columns Proyeccion.
     */
    public SingletonDataAccess(IMediatorDataSource mysource, String tableName, String... columns)
    {
        super(mysource, tableName, columns);
    }

    //--------------------------------------------------------------------
    /**
     * Genera las operaciones basicas CRUD para la tabla especificada. 
     * Nota 1: Permite especificar cuales columnas son la llave primaria, sin embargo,
     * no todos los motores de base de datos permiten este manejo por lo que hay
     * que remitirnos a la documentacion del motor en cuestión.
     * Nota 2: obtiene el origen de los datos de forma automatica.
     * @param tableName Tabla especificada
     * @param idColumnsNames Nombres de las columnas llave
     * @param columns Proyeccion.
     */
    public SingletonDataAccess(IMediatorDataSource mysource, String tableName, String[] idColumnsNames, String... columns)
    {
        super(mysource, tableName, idColumnsNames, columns);
    }

    //--------------------------------------------------------------------
    /**
     * Genera las operaciones basicas CRUD para la tabla especificada. 
     * Nota 1: Permite especificar cuales columnas son la llave primaria por indice, sin
     * embargo, no todos los motores de base de datos permiten este manejo por
     * lo que hay que remitirnos a la documentacion del motor en cuestión.
     * Nota 2: obtiene el origen de los datos de forma automatica.
     * @param tableName Tabla especificada
     * @param idColumnsIndexes indices
     * @param columns Proyeccion.
     */
    public SingletonDataAccess(IMediatorDataSource mysource, String tableName, int[] idColumnsIndexes, String... columns)
    {
        super(mysource, tableName, idColumnsIndexes, columns);
    }

    //---------------------------------------------------------------------
    /**
     * Utiliza el elemento proporcionado para crear un registro en la tabla a la
     * cual esta asociado este objeto de acceso a los datos. Nota: Al terminar
     * la operacion el estado del objeto también es modificado acorde a la
     * especificación de SYSWAVE.
     *
     * @param elemento Elemento a utilizar en la operacion.
     * @return Cantidad de elementos afectados por la operación.
     */
    @Override
    public int Create(T elemento)
    {
        int resultado = super.Create(elemento);

        if (resultado > 0)
        {
            elemento.setSearchOnlyByPrimaryKey(true);
            elemento.acceptChanges();
        }

        return resultado;
    }

    //---------------------------------------------------------------------
    /**
     * Utiliza los elemento proporcionados para realizar una operación por lotes
     * a la cual esta asociado este objeto de acceso a los datos y de esta forma
     * crear un registro en la tabla por cada elemento. Nota: Al terminar la
     * operacion el estado del objeto también es modificado acorde a la
     * especificación de SYSWAVE.
     *
     * @param elemento Elementos a utilizar en esta operación.
     * @return Cantidad de elementos afectados por la operación.
     */
    @Override
    public int Create(List<T> elemento)
    {
        int resultado = super.Create(elemento);

        if (resultado > 0)
        {
            for (T actual : elemento)
            {
                actual.setSearchOnlyByPrimaryKey(true);
                actual.acceptChanges();
            }
        }

        return resultado;
    }

    //---------------------------------------------------------------------
    /**
     * Utiliza el elemento proporcionado para actualizar un registro en la tabla
     * a la cual esta asociado este objeto de acceso a los datos. Nota: Al
     * terminar la operacion el estado del objeto también es modificado acorde a
     * la especificación de SYSWAVE.
     *
     * @param elemento Elemento a utilizar en la operacion.
     * @return Cantidad de elementos afectados por la operación.
     */
    @Override
    public int Update(T elemento)
    {
        int resultado = super.Update(elemento);

        if (resultado > 0)
            elemento.acceptChanges();

        return resultado;
    }

    //---------------------------------------------------------------------
    /**
     * Utiliza los elemento proporcionados para realizar una operación por lotes
     * a la cual esta asociado este objeto de acceso a los datos y de esta forma
     * actualiza un registro en la tabla por cada elemento. Nota: Al terminar la
     * operacion el estado del objeto también es modificado acorde a la
     * especificación de SYSWAVE.
     *
     * @param elemento Elementos a utilizar en esta operación.
     * @return Cantidad de elementos afectados por la operación.
     */
    @Override
    public int Update(List<T> elemento)
    {
        int resultado = super.Update(elemento);

        if (resultado > 0)
        {
            for (T actual : elemento)
                actual.acceptChanges();
        }

        return resultado;
    }

    //---------------------------------------------------------------------
    /**
     * Utiliza el elemento proporcionado para eliminar un registro en la tabla a
     * la cual esta asociado este objeto de acceso a los datos. Nota: Al
     * terminar la operacion el estado del objeto también es modificado acorde a
     * la especificación de SYSWAVE.
     *
     * @param elemento Elemento a utilizar en la operacion.
     * @return Cantidad de elementos afectados por la operación.
     */
    @Override
    public int Delete(T elemento)
    {
        int resultado = super.Delete(elemento);

        if (resultado > 0)
            elemento.setDeleted();

        return resultado;
    }

    //---------------------------------------------------------------------
    /**
     * Utiliza los elemento proporcionados para realizar una operación por lotes
     * a la cual esta asociado este objeto de acceso a los datos y de esta forma
     * elimina un registro en la tabla por cada elemento. Nota: Al terminar la
     * operacion el estado del objeto también es modificado acorde a la
     * especificación de SYSWAVE.
     *
     * @param elemento Elementos a utilizar en esta operación.
     * @return Cantidad de elementos afectados por la operación.
     */
    @Override
    public int Delete(List<T> elemento)
    {
        int resultado = super.Delete(elemento);

        if (resultado > 0)
        {
            for (T actual : elemento)
                actual.setDeleted();
        }

        return resultado;
    }
}