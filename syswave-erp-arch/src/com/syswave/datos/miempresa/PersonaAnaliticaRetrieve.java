package com.syswave.datos.miempresa;

import com.syswave.datos.common.SingletonRetrieveDataAccess;
import com.syswave.entidades.miempresa_vista.Personal;
import com.syswave.entidades.miempresa_vista.AnaliticaVista;
import datalayer.api.IMediatorDataSource;
import datalayer.events.DataGetEvent;
import datalayer.utils.QueryDataTransfer;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Gilberto Aaron Jimenez Montelongo
 */
public class PersonaAnaliticaRetrieve extends SingletonRetrieveDataAccess<AnaliticaVista>
{

    private final String searchProcedure = "persona_formato_analitico_select1";

    public PersonaAnaliticaRetrieve(IMediatorDataSource mysource)
    {
        super(mysource);
        /*super("persona_complementos");*/
    }

    public List<AnaliticaVista> PersonaAnaliticaBuscar(Personal generales, String noEmp)
    {
        QueryDataTransfer parametros = new QueryDataTransfer(searchProcedure);

        if (generales != null)
        {
            parametros.addInteger("vid", generales.getId_Tipo_Persona());
            parametros.addString("vnombre", generales.getNombres());
            parametros.addString("vapellido", generales.getApellidos());
            parametros.addDate("vnacimiento", generales.getNacimiento());
            parametros.addBoolean("ves_activo", generales.esActivo());
            parametros.addInteger("Vid_tipo_persona", generales.getId_Tipo_Persona());
            parametros.addString("vnacionalidad", generales.getNacionalidad());

        }
        else
        {
            parametros.addInteger("vid", -1);
            parametros.addString("vnombres", "");
            parametros.addString("vapellidos", "");
            parametros.addDate("vnacimiento", Personal.EMPTY_DATE);
            parametros.addBoolean("ves_activo", true);
            parametros.addInteger("Vid_tipo_persona", -1);
            parametros.addString("vnacionalidad", "");

        }
        if (!noEmp.equals(""))
            parametros.addString("vno_empleado", noEmp);
        else
            parametros.addString("vno_empleado", "");

        return executeReadStoredProcedure(parametros); //MySQL   
    }

    @Override
    public AnaliticaVista onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
    {
        AnaliticaVista current = new AnaliticaVista();

        for (int i = 1; i <= e.getColumnCount(); i++)
        {
            switch (e.getColumnName(i))
            {
                case "id":
                    current.setId(e.getInt(i));
                    break;
                case "NoEmpleado":
                    current.setNoEmpleado(e.getString(i));
                    break;
                case "NombreCompleto":
                    current.setNombreCompleto(e.getString(i));
                    break;
                case "Departamento":
                    current.setDepartamento(e.getString(i));
                    break;
                case "Puesto":
                    current.setPuesto(e.getString(i));
                    break;
                case "FechaIngreso":
                    current.setFechaIngreso(e.getString(i));
                    break;
                case "NSS":
                    current.setNss(e.getString(i));
                    break;
                case "RFC":
                    current.setRfc(e.getString(i));
                    break;
                case "CURP":
                    current.setCurp(e.getString(i));
                    break;
                case "Religion":
                    current.setReligion(e.getString(i));
                    break;
                case "PadecimientosAlergias":
                    current.setPadecimientosOAlergias(e.getString(i));
                    break;
                case "Edad":
                    current.setEdad(e.getInt(i));
                    break;
                case "FechaNac":
                    current.setNacimiento(e.getString(i));
                    break;
                case "Cumpleaños":
                    current.setCumpleaños(e.getString(i));
                    break;
                case "Genero":
                    current.setGenero(e.getString(i));
                    break;
                case "Escolaridad":
                    current.setEscolaridad(e.getString(i));
                    break;
                case "Antiguedad":
                    current.setAntiguedad(e.getInt(i));
                    break;
                case "Direccion":
                    current.setDirecciones(e.getString(i));
                    break;
                case "LugarProcedencia":
                    current.setLugarDeProcedencia(e.getString(i));
                    break;
                case "Telefono":
                    current.setTelCasa(e.getString(i));
                    break;
                case "TipoSangre":
                    current.setTipoSangre(e.getString(i));
                    break;
                case "EstadoCivil":
                    current.setEstadoCivil(e.getString(i));
                    break;
                case "Intereses":
                    current.setIntereses(e.getString(i));
                    break;
                case "Habilidades":
                    current.setHabilidades(e.getString(i));
                    break;
                case "nacionalidad":
                    current.setNacionalidad(e.getString(i));
                    break;
                case "Aficiones":
                    current.setPasatiempos(e.getString(i));
                    break;
                case "CursoTaller":
                    current.setFormacion(e.getString(i));
                    break;
                case "Duracion":
                    current.setDuracion(e.getString(i));
                    break;
                case "Reportes":
                    current.setReportes(e.getString(i));
                    break;
                case "Planta":
                    current.setPlanta(e.getString(i));
                    break;
                case "Contacto_de_emergencia":
                    current.setContactoEmergencia(e.getString(i));
                    break;
                case "Otros_Aributos":
                    current.setAtributos(e.getString(i));
                    break;
            }
        }

        current.setSearchOnlyByPrimaryKey(true);
        current.acceptChanges();

        return current;
    }
}