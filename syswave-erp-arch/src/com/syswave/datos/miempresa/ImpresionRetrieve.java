package com.syswave.datos.miempresa;

import com.syswave.datos.common.SingletonRetrieveDataAccess;
import com.syswave.entidades.miempresa_vista.ImpresionContrato;
import com.syswave.entidades.miempresa.PersonaContrato;
import datalayer.api.IMediatorDataSource;
import datalayer.events.DataGetEvent;
import datalayer.utils.QueryDataTransfer;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author sis5
 */
public class ImpresionRetrieve extends SingletonRetrieveDataAccess<ImpresionContrato> 
{

    private final String searchProcedure = "impresion_contrato";

    public ImpresionRetrieve(IMediatorDataSource mysource)
    {
        super(mysource);
        /*super("persona_complementos");*/
    }

    public ImpresionContrato buscarDatosContrato(PersonaContrato datos)
    {
        QueryDataTransfer parametros = new QueryDataTransfer(searchProcedure);
        
        parametros.addInteger("vconsecutivo", datos.getConsecutivo());
        parametros.addInteger("vid_persona", datos.getIdPersona());
        parametros.addInteger("vid_area", datos.getIdArea());
        parametros.addInteger("vid_puesto", datos.getIdPuesto());
        parametros.addInteger("vid_jornada", datos.getIdJornada());
        
        List<ImpresionContrato> e = executeReadStoredProcedure(parametros);
        
        return e.get(0);
    }

    @Override
    public ImpresionContrato onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
    {
        ImpresionContrato current = new ImpresionContrato();

        for (int i = 1; i <= e.getColumnCount(); i++)
        {
            switch (e.getColumnName(i))
            {
                case "consecutivo":
                    current.setConsecutivo(e.getInt(i));
                    break;
                case "Nombre_Completo":
                    current.setNombre(e.getString(i));
                    break;
                case "Edad":
                    current.setEdad(e.getInt(i));
                    break;
                case "Genero":
                    current.setGenero(e.getString(i));
                    break;
                case "Estado_Civil":
                    current.setEstado_civil(e.getString(i));
                    break;
                case "RFC":
                    current.setRfc(e.getString(i));
                    break;
                case "CURP":
                    current.setCurp(e.getString(i));
                    break;
                case "Puesto":
                    current.setPuesto(e.getString(i));
                    break;
                case "Area":
                    current.setAreaNegocio(e.getString(i));
                    break;
                case "Jornada":
                    current.setJornada(e.getString(i));
                    break;
                case "nacionalidad":
                    current.setNacionalidad(e.getString(i));
                    break;
                case "Direccion":
                    current.setDireccion(e.getString(i));
                    break;
            }
        }
        current.setSearchOnlyByPrimaryKey(true);
        current.acceptChanges();

        return current;
    }
}