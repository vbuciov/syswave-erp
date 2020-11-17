package com.syswave.datos.common;

import datalayer.api.IMediatorDataSource;
import java.util.Properties;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public final class DataSourceManager
{
    private static IMediatorDataSource instance;
    
    //--------------------------------------------------------------------
    /**
     * Obtiene un origen de datos acorde a los valores especificados en el 
     * archivo main-settings.xml
     * @return El origen de datos que corresponde a la configuración dada.
     */
    public static IMediatorDataSource getMainDataSourceInstance()
    {
        if (null == instance)
            instance = getDataSourceFor(ConfigurationManager.getConnectionProperties("main-settings.xml"));
        
        return instance;
    }
    
    //--------------------------------------------------------------------
    /**
     * Obtiene un DataSource acorde a las propiedades dadas siempre y cuando
     * este soportado.
     * @param values
     * @return 
     */
    public static IMediatorDataSource getDataSourceFor(Properties values)
    {
        IMediatorDataSource vendorSource = null;
        String vendor = values.getProperty("vendor", "unknown"),     
               host = values.getProperty("host", "localhost"), 
               port = values.getProperty("port", "3306"),
               user = values.getProperty("user", "root"), 
               password = values.getProperty("password", "1234"), 
               schema = values.getProperty("schema", "configuracion");

           switch (vendor)
           {
              case "postgre":
                 vendorSource = new PostGreSQLServerDataBase(host, port, schema, user, password);
                 break;

              case "mysql":
                 vendorSource = new MySQLServerDataBase(host, port, schema, user, password);
                 break;
           }
              
         return vendorSource;
    }
    
     //--------------------------------------------------------------------
    /**
     * Obtiene un DataSource acorde a las propiedades dadas siempre y cuando
     * este soportado.
     * @return 
     */
    public static IMediatorDataSource getDataSourceFor(String vendor, String host, String port, String user, String password, String schema)
    {
        IMediatorDataSource vendorSource = null;

        switch (vendor)
        {
           case "postgre":
              vendorSource = new PostGreSQLServerDataBase(host, port, schema, user, password);
              break;

           case "mysql":
              vendorSource = new MySQLServerDataBase(host, port, schema, user, password);
              break;
        }
              
         return vendorSource;
    }
}