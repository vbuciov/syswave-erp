package com.syswave.datos.miempresa;

import com.syswave.datos.common.SingletonRetrieveDataAccess;
import com.syswave.entidades.miempresa_vista.PersonaIdentificadorVista;
import datalayer.api.IMediatorDataSource;
import datalayer.events.DataGetEvent;
import datalayer.utils.QueryDataTransfer;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class PersonaIdentificadoresRetrieve extends SingletonRetrieveDataAccess<PersonaIdentificadorVista> 
{

    public PersonaIdentificadoresRetrieve(IMediatorDataSource mysource)
    {
        super(mysource);
        //super("persona_identificadores");
    }

   //---------------------------------------------------------------------
   public List<PersonaIdentificadorVista> Retrieve(int id_persona)
   {
      QueryDataTransfer parametros = new QueryDataTransfer("persona_identificadores_5FN");
      parametros.addInteger("vid_persona", id_persona);
      return executeReadStoredProcedure(parametros);
   }

   //---------------------------------------------------------------------
   @Override
   public PersonaIdentificadorVista onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
    {
      PersonaIdentificadorVista current = new PersonaIdentificadorVista();

      for (int i = 1; i <= e.getColumnCount(); i++)
      {
          switch (e.getColumnName(i))
          {
              case "clave":
                  current.setClave(e.getString(i));
                  break;
              case "id_persona":
                  current.setIdPersona(e.getInt(i));
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