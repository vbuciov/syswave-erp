package com.syswave.datos.miempresa;

import com.syswave.datos.common.SingletonRetrieveDataAccess;
import com.syswave.entidades.miempresa_vista.PersonaDireccion_tiene_Documento_5FN;
import datalayer.api.IMediatorDataSource;
import datalayer.events.DataGetEvent;
import datalayer.utils.QueryDataTransfer;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class PersonaDireccionTieneDocumentosRetrieve extends SingletonRetrieveDataAccess<PersonaDireccion_tiene_Documento_5FN>
{   

    public PersonaDireccionTieneDocumentosRetrieve(IMediatorDataSource mysource)
    {
        super(mysource);
    }
   //---------------------------------------------------------------------
   public List<PersonaDireccion_tiene_Documento_5FN> Retrieve (int id_documento)
   {
      QueryDataTransfer parametros = new QueryDataTransfer("persona_direccion_tiene_documentos_5FN");
      parametros.addInteger("vid_documento", id_documento);
      return executeReadStoredProcedure(parametros);
   }

   //---------------------------------------------------------------------
   @Override
   public PersonaDireccion_tiene_Documento_5FN onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
   {
      PersonaDireccion_tiene_Documento_5FN current = new PersonaDireccion_tiene_Documento_5FN();

      for (int i = 1; i <= e.getColumnCount(); i++)
      {
          switch (e.getColumnName(i))
          {
              case "rol":
                  current.setRol(e.getString(i));
                  break;
              case "id_persona":
                  current.setIdPersona(e.getInt(i));
                  break;
              case "consecutivo":
                  current.setConsecutivo(e.getInt(i));
                  break;
              case "id_documento":
                  current.setIdDocumento(e.getInt(i));
                  break;
              case "es_rol":
                  current.setRol(e.getInt(i));
                  break;
              case "nombre":
                  current.setNombre(e.getString(i));
                  break;
              case "tipo_persona":
                  current.setTipo_persona(e.getString(i));
                  break;
              case "codigo_postal":
                  current.setCodigoPostal(e.getString(i));
                  break;
              case "localidad":
                  current.setLocalidad(e.getString(i));
                  break;
              case "calle":
                  current.setCalle(e.getString(i));
                  break;
              case "colonia":
                  current.setColonia(e.getString(i));
                  break;
              case "no_exterior":
                  current.setNoExterior(e.getString(i));
                  break;
              case "no_interior":
                  current.setNoInterior(e.getString(i));
                  break;
          }
      }

      current.setSearchOnlyByPrimaryKey(true);
      current.acceptChanges();

      return current;
   }  
}