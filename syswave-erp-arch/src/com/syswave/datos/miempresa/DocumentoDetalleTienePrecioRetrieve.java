package com.syswave.datos.miempresa;

import com.syswave.datos.common.SingletonRetrieveDataAccess;
import com.syswave.entidades.miempresa_vista.DocumentoDetalle_tiene_PrecioVista;
import datalayer.api.IMediatorDataSource;
import datalayer.events.DataGetEvent;
import datalayer.utils.QueryDataTransfer;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class DocumentoDetalleTienePrecioRetrieve extends SingletonRetrieveDataAccess<DocumentoDetalle_tiene_PrecioVista>
{
    public DocumentoDetalleTienePrecioRetrieve(IMediatorDataSource mysource)
    {
        super(mysource);
       /*super("documento_detalle_tiene_precios", "id_precio", "id_documento"
              , "consecutivo", "precio", "id_moneda_actual", "cantidad"
              , "total", "descripcion", "es_tipo", "mercado", "nombre"
              , "id_variante");
        setWithAutoID(false);*/
    }
   
   //---------------------------------------------------------------------
   public List<DocumentoDetalle_tiene_PrecioVista> Retrieve (int id_documento, int consecutivo)
   {
      QueryDataTransfer parametros = new QueryDataTransfer("documento_detalle_tiene_precio_5FN");
      parametros.addInteger("vid_documento", id_documento);
      parametros.addInteger("vconsecutivo", consecutivo);
      
      return executeReadStoredProcedure(parametros); //MySQL
   }

    //--------------------------------------------------------------------    
    @Override
    public DocumentoDetalle_tiene_PrecioVista onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
    {
      DocumentoDetalle_tiene_PrecioVista current = new DocumentoDetalle_tiene_PrecioVista();

      for (int i = 1; i <= e.getColumnCount(); i++)
      {
          switch (e.getColumnName(i))
          {
              case "id_precio":
                  current.setIdPrecio(e.getInt(i));
                  break;
              case "id_documento":
                  current.setIdDocumento(e.getInt(i));
                  break;
              case "consecutivo":
                  current.setConsecutivo(e.getInt(i));
                  break;
              case "precio":
                  current.setPrecio(e.getFloat(i));
                  break;
              case "id_moneda_actual":
                  current.setIdMonedaActual(e.getInt(i));
                  break;
              case "cantidad":
                  current.setCantidad(e.getFloat(i));
                  break;
              case "total":
                  current.setTotal(e.getFloat(i));
                  break;
              case "descripcion":
                  current.setDescripcion(e.getString(i));
                  break;
              case "es_tipo":
                  current.setEsTipo(e.getInt(i));
                  break;
              case "mercado":
                  current.setMerado(e.getFloat(i));
                  break;
              case "nombre":
                  current.setMoneda(e.getString(i));
                  break;
              case "id_variante":
                  current.setIdVariante(e.getInt(i));
                  break;
          }
      }

      current.setSearchOnlyByPrimaryKey(true);
      current.acceptChanges();

      return current;
   }    
}