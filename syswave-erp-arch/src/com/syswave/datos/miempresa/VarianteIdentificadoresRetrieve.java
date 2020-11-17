package com.syswave.datos.miempresa;

import com.syswave.datos.common.SingletonRetrieveDataAccess;
import com.syswave.entidades.miempresa_vista.VarianteIdentificadorVista;
import datalayer.api.IMediatorDataSource;
import datalayer.events.DataGetEvent;
import datalayer.utils.QueryDataTransfer;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class VarianteIdentificadoresRetrieve extends SingletonRetrieveDataAccess<VarianteIdentificadorVista> 
{
    public VarianteIdentificadoresRetrieve(IMediatorDataSource mysource)
    {
        super(mysource);
        /*super("persona_identificadores");
        setWithAutoID(false);*/
    }

   //---------------------------------------------------------------------
   public List<VarianteIdentificadorVista> Retrieve(int id_variante)
   {
      QueryDataTransfer parametros = new QueryDataTransfer("variante_identificadores_5FN");
      parametros.addInteger("vid_variante", id_variante);
      return executeReadStoredProcedure(parametros);
   }

   //---------------------------------------------------------------------
   @Override
    public VarianteIdentificadorVista onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
    {
      VarianteIdentificadorVista current = new VarianteIdentificadorVista();

      for (int i = 1; i <= e.getColumnCount(); i++)
      {
          switch (e.getColumnName(i))
          {
              case "valor":
                  current.setValor(e.getString(i));
                  break;
              case "id_variante":
                  current.setIdVariante(e.getInt(i));
                  break;
              case "id_tipo_identificador":
                  current.setIdTipoIdentificador(e.getInt(i));
                  break;
              case "llave":
                  current.setLlave(e.getString(i));
                  break;
              case "descripcion":
                  current.setDescripcion(e.getString(i));
                  break;
              case "es_activo":
                  current.setEsActivo(e.getBoolean(i));
                  break;
          }
      }

      //SI el elemento ya se encuentra asociado entonces se acepta.
      current.setSearchOnlyByPrimaryKey(true);
      current.acceptChanges();

      return current;
   }   
}