package com.syswave.datos.miempresa;

import com.syswave.datos.common.SingletonRetrieveDataAccess;
import com.syswave.entidades.miempresa_vista.DocumentoContadoMovimiento_5FN;
import datalayer.api.IMediatorDataSource;
import datalayer.events.DataGetEvent;
import datalayer.utils.QueryDataTransfer;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class DocumentoContadoMovimientoRetrieve extends SingletonRetrieveDataAccess<DocumentoContadoMovimiento_5FN> 
{

    public DocumentoContadoMovimientoRetrieve(IMediatorDataSource mysource)
    {
        super(mysource);
        /*super("documento_contado_abonos");
        setWithAutoID(false);*/
    }
   
   //---------------------------------------------------------------------
   public List<DocumentoContadoMovimiento_5FN> Retrieve (int id_documento)
   {
      QueryDataTransfer parametros = new QueryDataTransfer("documento_contado_abonos_5FN");
      parametros.addInteger("vid_documento", id_documento);
      return executeReadStoredProcedure(parametros); //MySQL
   }

      //--------------------------------------------------------------------    
    @Override
    public DocumentoContadoMovimiento_5FN onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
    {
      DocumentoContadoMovimiento_5FN current = new DocumentoContadoMovimiento_5FN();

      for (int i = 1; i <= e.getColumnCount(); i++)
      {
          switch (e.getColumnName(i))
          {
              case "id_tipo_comprobante":
                  current.setIdTipoComprobante(e.getInt(i));
                  break;
              case "nombre":
                  current.setNombre(e.getString(i));
                  break;
              case "linea":
                  current.setLinea(e.getInt(i));
                  break;
              case "id_documento":
                  current.setIdDocumento(e.getInt(i));
                  break;
              case "monto":
                  current.setMonto(e.getFloat(i));
                  break;
              case "concepto":
                  current.setConcepto(e.getString(i));
                  break;
          }
      }

      current.setSearchOnlyByPrimaryKey(true);
      current.acceptChanges();

      return current;
   }  
}