package com.syswave.datos.miempresa;

import com.syswave.datos.common.SingletonRetrieveDataAccess;
import com.syswave.entidades.miempresa_vista.PersonaDetalle_5FN;
import datalayer.api.IMediatorDataSource;
import datalayer.events.DataGetEvent;
import datalayer.utils.QueryDataTransfer;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class PersonaDetallesRetrieve extends SingletonRetrieveDataAccess<PersonaDetalle_5FN> 
{

    public PersonaDetallesRetrieve(IMediatorDataSource mysource)
    {
        super(mysource);
    }
   //---------------------------------------------------------------------
   public List<PersonaDetalle_5FN> Retrieve(int id_persona)
   {
      QueryDataTransfer parametros = new QueryDataTransfer("persona_detalles_5FN");
      parametros.addInteger("vid_persona", id_persona);
      return executeReadStoredProcedure(parametros);
   }

   //---------------------------------------------------------------------
   @Override
   public PersonaDetalle_5FN onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
   {
      PersonaDetalle_5FN current = new PersonaDetalle_5FN();

      for (int i = 1; i <= e.getColumnCount(); i++)
      {
          switch (e.getColumnName(i))
          {
              case "valor":
                  current.setValor(e.getString(i));
                  break;
              case "id_persona":
                  current.setIdPersona(e.getInt(i));
                  break;
              case "id_tipo_detalle":
                  current.setIdTipoDetalle(e.getInt(i));
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