package com.syswave.datos.common;

import datalayer.api.ISQLExceptionFormatter;
import datalayer.sources.DBMSDataSource;
import datalayer.utils.DefaultMetaData;
import datalayer.utils.DefaultParameter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Se encarga de gestionar las operaciones al origen de datos.
 * @author Victor Manuel Bucio Vargas
 */
public class MySQLServerDataBase extends DBMSDataSource 
{
   public final static String driver = "com.mysql.jdbc.Driver";
   public final static String url_format = "jdbc:mysql://%s:%s/%s";
   //private static MySQLDataSource instance;

   //---------------------------------------------------------------------
   public MySQLServerDataBase(String host, String port, String esquema, String user, String pass)
   {
      super(driver,
            String.format(url_format, host, port, esquema),
            user, pass, "SELECT 1");

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
  /*public String extractName (PreparedStatement request)
   {
       com.mysql.jdbc.JDBC4CallableStatement view = (com.mysql.jdbc.JDBC4CallableStatement)request;
       com.mysql.jdbc.JDBC4CallableStatement.class.
       //view.
       //view.get
       return "";
   }*/

   //---------------------------------------------------------------------
   /*public static MySQLDataSource getInstance()
   {
      if (instance == null)
         instance = new MySQLDataSource("localhost", "3306", "miempresa", "root", "1234");

      return instance;
   }*/
   
    //---------------------------------------------------------------------
    @Override
    public List<DefaultParameter> getSchemaInformation(String procedure, Connection session) throws SQLException
    {
        PreparedStatement BufferRequest;
        ResultSet response;
        ResultSetMetaData columns;
        List<DefaultParameter> result = new ArrayList<>();
        DefaultParameter current;
        DefaultMetaData information;
        
        BufferRequest = session.prepareStatement("SELECT ORDINAL_POSITION, PARAMETER_MODE, PARAMETER_NAME, DATA_TYPE," +
                                                  "CHARACTER_MAXIMUM_LENGTH, NUMERIC_PRECISION, NUMERIC_SCALE " +
                                                  "FROM information_schema.parameters " +
                                                  "WHERE SPECIFIC_NAME = ? " +
                                                  "AND ROUTINE_TYPE = 'PROCEDURE'");
        BufferRequest.setString(1, procedure);
        response = BufferRequest.executeQuery();
        columns = response.getMetaData();
        
        while (response.next())
        {
            current = new DefaultParameter();
            information = new DefaultMetaData();
            current.setMetaData(information);
           
            for (int i=1; i<= columns.getColumnCount(); i++)
            {
                if (columns.getColumnName(i).equals("ORDINAL_POSITION"))
                    information.setOrdinalIndex(response.getInt(i));
                
                else if (columns.getColumnName(i).equals("PARAMETER_MODE"))
                    information.setDirection(traduceDirection(response.getString(i)));
                
                else if (columns.getColumnName(i).equals("PARAMETER_NAME"))
                    current.setKeyName(response.getString(i));
                
                 else if (columns.getColumnName(i).equals("DATA_TYPE"))
                     current.setSQLType(traduceSQLType(response.getString(i)));
                
                else if (columns.getColumnName(i).equals("CHARACTER_MAXIMUM_LENGTH"))
                    information.setSize(response.getInt(i));
                
                else if (columns.getColumnName(i).equals("NUMERIC_PRECISION"))
                    information.setPrecision(response.getInt(i));
                
                else if (columns.getColumnName(i).equals("NUMERIC_SCALE"))
                    information.setScale(response.getInt(i));
            }
            result.add(current);
        }
         
         BufferRequest.close();
         response.close();
         
         return result;
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
      switch (ex.getErrorCode())
      {
         case 0:
            return "El servidor no se encuentra disponible";

         case 1021: // HY000 (ER_DISK_FULL)
            return "El disco duro de la máquina servidor parece estar lleno";

         case 1022: //23000 (ER_DUP_KEY)
            return "El identificador introducido, ya esta siendo utilizado en otro registro";

         case 1062: //23000 (ER_DUP_ENTRY)
            return "El identificador introducido, ya esta siendo utilizado en otro registro";

         case 1030: //HY000 (ER_GET_ERRNO)
            return "Ha ocurrido una operación no permitida por el servidor, contacte a su administrador";

         case 1036: // HY000 (ER_OPEN_AS_READONLY)
            return "El administrador no ha concedido permisos para registrar información en este apartado";

         case 1040: //08004 (ER_CON_COUNT_ERROR)
            return "El servidor ya no admite más conexiones, reportelo a su administrador";

         case 1044: //42000 (ER_DBACCESS_DENIED_ERROR)
            return "Imposible establecer conexión con el servidor a través del usuario especificado en la configuración";

         case 1045: //28000 (ER_ACCESS_DENIED_ERROR)
            return "No se pudo obtener acceso al servidor con el usuario especificado o la contraseña es incorrecta";

         case 1046: //3D000 (ER_NO_DB_ERROR)
            return "La conexión esta establecida, pero no se ha seleccionado una base de datos";

         case 1053: //08S01 (ER_SERVER_SHUTDOWN)
            return "El servidor nego la operación, debido a que se esta apagando";

         case 1077: // HY000 (ER_NORMAL_SHUTDOWN)
            return "El servidor ha sido apagado";

         case 1079: //HY000 (ER_SHUTDOWN_COMPLETE)
            return "El servidor ha terminado de apagarse";

         case 1169: //23000 (ER_DUP_UNIQUE)
            return "Ha introducido una clave repetida, asegurese de no repetir valores de claves";

         case 1451:
            return "Ese valor esta relacionado con otro registro";

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
