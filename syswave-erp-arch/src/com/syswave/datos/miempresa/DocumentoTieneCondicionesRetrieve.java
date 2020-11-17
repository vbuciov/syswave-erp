package com.syswave.datos.miempresa;

import com.syswave.datos.common.SingletonRetrieveDataAccess;
import com.syswave.entidades.miempresa_vista.Documento_tiene_Condicion_5FN;
import datalayer.api.IMediatorDataSource;
import datalayer.events.DataGetEvent;
import datalayer.utils.QueryDataTransfer;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class DocumentoTieneCondicionesRetrieve extends SingletonRetrieveDataAccess<Documento_tiene_Condicion_5FN>
{
    public DocumentoTieneCondicionesRetrieve(IMediatorDataSource mysource)
    {
        super(mysource);
        /*super("documento_tiene_condiciones_5FN", "id", "seccion", "descripcion"
              , "es_activo", "id_documento", "id_condicion", "condicion"
              , "valor", "unidad");
        setWithAutoID(false);*/
    }
   
   //---------------------------------------------------------------------
   public List<Documento_tiene_Condicion_5FN> Retrieve (int id_documento)
   {
      QueryDataTransfer parametros = new QueryDataTransfer("documento_tiene_condiciones_5FN");
      parametros.addInteger("vid_documento", id_documento);
      return executeReadStoredProcedure(parametros); //MySQL
   }

   //---------------------------------------------------------------------
   @Override
   public Documento_tiene_Condicion_5FN onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
    {
      Documento_tiene_Condicion_5FN current = new Documento_tiene_Condicion_5FN();

      for (int i = 1; i <= e.getColumnCount(); i++)
      {
          switch (e.getColumnName(i))
          {
              case "id":
                  current.setId(e.getInt(i));
                  break;
              case "seccion":
                  current.setSeccion(e.getString(i));
                  break;
              case "descripcion":
                  current.setDescripcion(e.getString(i));
                  break;
              case "es_activo":
                  current.setActivo(e.getBoolean(i));
                  break;
              case "id_documento":
                  current.setIdDocumento(e.getInt(i));
                  break;
              case "id_condicion":
                  current.setIdCondicionPago(e.getInt(i));
                  break;
              case "condicion":
                  current.setCondicion(e.getString(i));
                  break;
              case "valor":
                  current.setValor(e.getInt(i));
                  break;
              case "unidad":
                  current.setUnidad(e.getInt(i));
                  break;
          }
      }

      current.setSearchOnlyByPrimaryKey(true);
      current.acceptChanges();

      return current;
   }    
}