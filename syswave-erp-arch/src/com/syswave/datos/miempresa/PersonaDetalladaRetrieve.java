package com.syswave.datos.miempresa;

import com.syswave.datos.common.SingletonRetrieveDataAccess;
import com.syswave.entidades.miempresa.Persona;
import com.syswave.entidades.miempresa.PersonaDireccion;
import com.syswave.entidades.miempresa.PersonaIdentificador;
import com.syswave.entidades.miempresa.TipoPersona;
import com.syswave.entidades.miempresa_vista.PersonaDetalladaVista;
import datalayer.api.IMediatorDataSource;
import datalayer.events.DataGetEvent;
import datalayer.utils.QueryDataTransfer;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class PersonaDetalladaRetrieve extends SingletonRetrieveDataAccess<PersonaDetalladaVista>
{
    private final String searchProcedure = "persona_buscar";

    public PersonaDetalladaRetrieve(IMediatorDataSource mysource)
    {
        super(mysource);
        /*super("personas", "id", "nombres", "apellidos", "nacimiento", "es_activo"
                ,"id_tipo_persona");*/
    }
   
   public List<PersonaDetalladaVista> PersonaBuscar (TipoPersona tipo, Persona generales, PersonaIdentificador identificador, PersonaIdentificador medio, PersonaDireccion direccion)
   {
      QueryDataTransfer parametros = new QueryDataTransfer(searchProcedure);

      if (tipo != null)
         parametros.addInteger("vid_tipo_persona_base", tipo.getId());
      
      else
         parametros.addInteger("vid_tipo_persona_base", -1);
      
      
      if (generales != null)
      {
         parametros.addBoolean("vusa_generales", true);
         parametros.addString("vnombres", generales.getNombres());
         parametros.addString("vapellidos", generales.getApellidos());
         parametros.addDate("vnacimiento", generales.getNacimiento());
      }
      
      else
      {
         parametros.addBoolean("vusa_generales", false);
         parametros.addString("vnombres", "");
         parametros.addString("vapellidos", "");
         parametros.addDate("vnacimiento", Persona.EMPTY_DATE);
      }
         
      
      if (identificador != null)
      {
         parametros.addBoolean("vusa_identificador", true);
         parametros.addString("vclave_identificador", identificador.getClave());
         parametros.addInteger("vid_tipo_identificador", identificador.getIdTipoIdentificador());
      }
      
      else
      {
          parametros.addBoolean("vusa_identificador", false);
         parametros.addString("vclave_identificador","");
         parametros.addInteger("vid_tipo_identificador", -1);
      }
      
      if (medio != null)
      {
         parametros.addBoolean("vusa_medio", true);
         parametros.addString("vclave_medio", medio.getClave());
         parametros.addInteger("vid_tipo_medio", medio.getIdTipoIdentificador());
      }
      
      else
      {
          parametros.addBoolean("vusa_medio", false);
         parametros.addString("vclave_medio", "");
         parametros.addInteger("vid_tipo_medio", -1);
      }
      
      if (direccion!= null)
      {
         parametros.addBoolean("vusa_direccion", true);
         parametros.addInteger("vid_localidad", direccion.getIdLocalidad());
         parametros.addString("vcalle", direccion.getCalle());
         parametros.addString("vcolonia", direccion.getColonia());
         parametros.addString("vcodigo_postal", direccion.getCodigoPostal());
         parametros.addString("vno_exterior", direccion.getNoExterior());
         parametros.addString("vno_interior", direccion.getNoInterior());
      }
      
      else
      {
          parametros.addBoolean("vusa_direccion", false);
         parametros.addInteger("vid_localidad", -1);
         parametros.addString("vcalle", "");
         parametros.addString("vcolonia", "");
         parametros.addString("vcodigo_postal", "");
         parametros.addString("vno_exterior", "");
         parametros.addString("vno_interior", "");
      }
      
      return executeReadStoredProcedure(parametros);
   }

   @Override
   public PersonaDetalladaVista onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
    {
        PersonaDetalladaVista current = new PersonaDetalladaVista();

        for (int i = 1; i <= e.getColumnCount(); i++) {
            switch (e.getColumnName(i))
            {
                case "id":
                    current.setId(e.getInt(i));
                    break;
                case "nombres":
                    current.setNombres(e.getString(i));
                    break;
                case "apellidos":
                    current.setApellidos(e.getString(i));
                    break;
                case "nacimiento":
                    current.setNacimiento(e.getDate(i));
                    break;
                case "es_activo":
                    current.setActivo(e.getBoolean(i));
                    break;
                case "id_tipo_persona":
                    current.setId_tipo_pesrona(e.getInt(i));
                    break;
                case "identificador":
                    current.setIdentificador_muestra(e.getString(i));
                    break;
                case "medios":
                    current.setMedio_muestra(e.getString(i));
                    break;
                case "direccion":
                    current.setDireccion_muestra(e.getString(i));
                    break;
            }
            
        }

        current.setSearchOnlyByPrimaryKey(true);
        current.acceptChanges();

        return current;
   }  
}