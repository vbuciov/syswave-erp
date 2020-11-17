package com.syswave.datos.common;

import com.syswave.entidades.common.Entidad;
import datalayer.BatchJDBCMappeableOperationSet;
import datalayer.api.IMediatorDataSource;

/**
 * Crea un DataAccess asociado con una fuente de datos en particular por default.
 * @author Victor Manuel Bucio Vargas
 * @param <T>
 */
public class SingletonRetrieveDataAccess<T extends Entidad> extends BatchJDBCMappeableOperationSet<T>
{
    /**
     * Instancia un objeto y obtiene el origen de datos en forma automatica.
     */
    public SingletonRetrieveDataAccess(IMediatorDataSource mysource)
    {
        super(mysource);
    }
    
}
