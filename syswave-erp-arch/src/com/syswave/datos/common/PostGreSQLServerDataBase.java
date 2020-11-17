package com.syswave.datos.common;

import datalayer.api.ISQLExceptionFormatter;
import datalayer.sources.DBMSDataSource;
import datalayer.utils.DefaultMetaData;
import datalayer.utils.DefaultParameter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.List;

/**
 * Se encarga de gestionar las operaciones al origen de datos.
 *
 * @author Victor Manuel Bucio Vargas
 */
public class PostGreSQLServerDataBase extends DBMSDataSource 
{
    public final static String driver = "org.postgresql.Driver";
    public final static String url_format = "jdbc:postgresql://%s:%d/%s";
    
       //---------------------------------------------------------------------
   public PostGreSQLServerDataBase(String host, String port, String esquema, String user, String pass)
   {
      super(driver,
            String.format(url_format, host, port, esquema),
            user, pass, "SELECT 1 FROM DUAL");

      setFormatter(new ISQLExceptionFormatter()
      {
         @Override
         public String getFriendlyMessage(SQLException ex)
         {
            return formatFriendlyMessage(ex);
         }
      });
   }
   
          //---------------------------------------------------------------------
   public PostGreSQLServerDataBase(String host, String esquema, String user, String pass)
   {
      super(driver,
            String.format(url_format, host, 5432, esquema),
            user, pass, "SELECT 1 FROM DUAL");

      setFormatter(new ISQLExceptionFormatter()
      {
         @Override
         public String getFriendlyMessage(SQLException ex)
         {
            return formatFriendlyMessage(ex);
         }
      });
   }
 
   //---------------------------------------------------------------------
    @Override
    public List<DefaultParameter> getSchemaInformation(String procedure, Connection session) throws SQLException
    {
        return null;
    } 
    
    //----------------------------------------------------------------
    private int traduceSQLType(String value)
    {
        switch (value)
        {
            case "int":
                return java.sql.Types.INTEGER;
                
            case "varchar":
                return java.sql.Types.VARCHAR;
             
            case "bit":
                return java.sql.Types.BOOLEAN;
                
             case "double":
                return java.sql.Types.DOUBLE;
                
              case "numeric":
                return java.sql.Types.NUMERIC;
                
             case "smallint":
                return java.sql.Types.SMALLINT;
                
             case "decimal":
                return java.sql.Types.DECIMAL;
                
             case "float":
                return java.sql.Types.FLOAT;
                
             case "tinyint":
                return java.sql.Types.TINYINT;
                
             case "blob":
                return java.sql.Types.BLOB;
                
             case "char":
                return java.sql.Types.CHAR;
                
             case "date":
                return java.sql.Types.DATE;
                
              case "time":
                return java.sql.Types.TIME;
                
             case "timestamp":
                return java.sql.Types.TIMESTAMP;
        }
        
        return java.sql.Types.OTHER;
    }
    
     //----------------------------------------------------------------
    private int traduceDirection (String value)
    {
        switch (value)
        {
            case "IN":
                return DefaultMetaData.IN_PARAM;
                
            case "OUT":
                return DefaultMetaData.OUT_PARAM;
                
            case "OUTIN":
                return DefaultMetaData.OUT_IN_PARAM;
        }
        
        return DefaultMetaData.NOTHING_PARAM;
    }
   
   //----------------------------------------------------------------
   public String formatFriendlyMessage(SQLException ex)
   {
        switch (ex.getSQLState())
        {
            case "08001": //sqlclient_unable_to_establish_sqlconnection
                return "El servidor no se encuentra disponible";

            case "08004": //sqlserver_rejected_establishment_of_sqlconnection
                return "Imposible establecer conexión con el servidor a través del usuario especificado en la configuración";

            case "08006": //connection_failure
                return "El servidor ya no admite más conexiones, reportelo a su administrador";
                
            default:
                return ex.getMessage();
        }
   }    

    //----------------------------------------------------------------
   /*private boolean isBreakConection(SQLException e)
     {
     return e.getErrorCode() == -1;
     }

     //----------------------------------------------------------------
     private boolean isTimeOut(SQLException e)
     {
     return e.getErrorCode() == -2;
     }

     //----------------------------------------------------------------
     private boolean isNotExistProcedure(SQLException e)
     {
     //SELECT *  FROM master.dbo.sysmessages
     return e.getErrorCode() == 2812;
     }

     //----------------------------------------------------------------
     private boolean isInvalidDatabase(SQLException e)
     {
     //SELECT * FROM master.dbo.sysmessages
     return e.getErrorCode() == 4060;
     }

     //----------------------------------------------------------------
     private boolean isLoginFailed(SQLException e)
     {
     //SELECT * FROM master.dbo.sysmessages
     return e.getErrorCode() == 18456;
     }

     //----------------------------------------------------------------
     private boolean isForeingKeyViolation(SQLException e)
     {
     //SELECT * FROM master.dbo.sysmessages
     return e.getErrorCode() == 547;
     }

     //----------------------------------------------------------------
     private boolean isUniqueOrPrimaryOrConstraintViolation(SQLException e)
     {
     //SELECT * FROM master.dbo.sysmessages
     return e.getErrorCode() == 2627;
     }*/
}
