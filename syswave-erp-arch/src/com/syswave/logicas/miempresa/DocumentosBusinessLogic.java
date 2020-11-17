package com.syswave.logicas.miempresa;

import com.syswave.datos.common.DataSourceManager;
import com.syswave.datos.miempresa.DocumentoComercialRetrieve;
import com.syswave.datos.miempresa.DocumentosDataAccess;
import com.syswave.entidades.miempresa.Documento;
import com.syswave.entidades.miempresa_vista.DocumentoComercial;
import datalayer.api.IMediatorDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class DocumentosBusinessLogic
{
    private String mensaje;
    private DocumentosDataAccess tabla;
    private DocumentoComercialRetrieve vista;
    
   //---------------------------------------------------------------------
    public DocumentosBusinessLogic(){
        IMediatorDataSource mysource = DataSourceManager.getMainDataSourceInstance();
        tabla = new DocumentosDataAccess(mysource);
        vista = new DocumentoComercialRetrieve(mysource);
    }
    
   //---------------------------------------------------------------------
    public DocumentosBusinessLogic(String esquema)
    {
        this();
      tabla.setEschema(esquema);
       vista.setEschema(esquema);
    }
    
   //---------------------------------------------------------------------
    public boolean agregar (Documento nuevo)
   {
      boolean resultado = tabla.Create(nuevo)>0;
      mensaje = tabla.getMessage();
           
      return resultado;
   }
    
   //---------------------------------------------------------------------
    public boolean agregar (List<Documento> nuevos)
   {
      boolean resultado = tabla.Create(nuevos)>0;
      mensaje = tabla.getMessage();

      return resultado;
   }
    
    //---------------------------------------------------------------------
   public boolean actualizar (Documento elemento)
   {
      boolean resultado =  tabla.Update(elemento)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
    //---------------------------------------------------------------------
  public boolean actualizar (List<Documento> elementos)
   {
      boolean resultado = tabla.Update(elementos)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public boolean borrar (Documento elemento)
   {
      boolean resultado = tabla.Delete(elemento)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public boolean borrar (List<Documento> elementos)
   {
      boolean resultado = tabla.Delete(elementos)>0;
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public List<Documento> obtenerLista ()
   {
      List<Documento> resultado = tabla.Retrieve();
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public List<Documento> obtenerLista (Documento elemento)
   {
      List<Documento> resultado = tabla.Retrieve(elemento);
      
      mensaje = tabla.getMessage();
      
      return resultado;
   }
   
      //---------------------------------------------------------------------
   public List<DocumentoComercial> obtenerListaVista ()
   {
      List<DocumentoComercial> resultado = vista.Retrieve();
      
      mensaje = vista.getMessage();
      
      return resultado;
   }
   
   //---------------------------------------------------------------------
   public List<DocumentoComercial> obtenerListaVista (DocumentoComercial elemento)
   {
      List<DocumentoComercial> resultado = vista.Retrieve(elemento);
      
      mensaje = vista.getMessage();
      
      return resultado;
   }

   //---------------------------------------------------------------------
   public boolean recargar (Documento elemento)
   {
      List<Documento> resultado = tabla.Retrieve(elemento);
      if (resultado.size() > 0)
      {
         elemento.copy(resultado.get(0));
      }
      
      mensaje = tabla.getMessage();
      
      return tabla.getState() != DocumentosDataAccess.DATASOURCE_ERROR;
   }
   
   
   //---------------------------------------------------------------------
   public boolean validar (Documento elemento)
   {
      boolean esCorrecto = true;
      
      if (elemento.getFolio().equals(Documento.EMPTY_STRING))
      {
         esCorrecto = false;
         mensaje = "Es necesario especificar un folio";
      }
     
      
      else if (elemento.isSet())
      {
         esCorrecto = false;
         mensaje = "Es necesario especificar si la persona es activa";
      }
      

      return esCorrecto;
   }
   
   //---------------------------------------------------------------------
   public boolean esCorrecto ()
   {
      return tabla.getState() != DocumentosDataAccess.DATASOURCE_ERROR;
   }
   
   //---------------------------------------------------------------------
   public String getMensaje ()
   {
      return mensaje;
   }
   
   //---------------------------------------------------------------------
   public String getEsquema()
   {
      return tabla.getEschema();
   }
   
   //---------------------------------------------------------------------
   public void setEsquema (String value)
   {
      tabla.setEschema(value);
      vista.setEschema(value);
   }

    public Connection getConexion()
    {
        try
        {
            return DataSourceManager.getMainDataSourceInstance().createSession();
        }
        catch (SQLException ex)
        {
            Logger.getLogger(DocumentosBusinessLogic.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void setAvailableConextion(Connection value)
    {
        try
        {
            DataSourceManager.getMainDataSourceInstance().disconnect(value);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(DocumentosBusinessLogic.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
