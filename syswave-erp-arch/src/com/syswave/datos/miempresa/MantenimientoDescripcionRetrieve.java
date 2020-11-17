package com.syswave.datos.miempresa;

import com.syswave.datos.common.SingletonRetrieveDataAccess;
import com.syswave.entidades.miempresa_vista.MantenimientoDescripcion_5FN;
import datalayer.api.IMediatorDataSource;
import datalayer.events.DataGetEvent;
import datalayer.utils.QueryDataTransfer;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class MantenimientoDescripcionRetrieve extends SingletonRetrieveDataAccess<MantenimientoDescripcion_5FN>
{

    public MantenimientoDescripcionRetrieve(IMediatorDataSource mysource)
    {
        super(mysource);
        /*super("mantenimiento_descripciones");
        setWithAutoID(false);*/
    }
    //---------------------------------------------------------------------

    public List<MantenimientoDescripcion_5FN> Retrieve(int id_mantenimiento)
    {
        QueryDataTransfer parametros = new QueryDataTransfer("mantenimiento_descripciones_5FN");

        parametros.addInteger("vid_mantenimiento", id_mantenimiento);

        return executeReadStoredProcedure(parametros);
    }

    //---------------------------------------------------------------------
    @Override
    public MantenimientoDescripcion_5FN onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
    {
        MantenimientoDescripcion_5FN current = new MantenimientoDescripcion_5FN();

        for (int i = 1; i <= e.getColumnCount(); i++)
        {
            switch (e.getColumnName(i))
            {
                case "valor":
                    current.setValor(e.getString(i));
                    break;
                case "descripcion":
                    current.setDescripcion(e.getString(i));
                    break;
                case "formato":
                    current.setFormato(e.getString(i));
                    break;
                case "es_activo":
                    current.setActivo(e.getBoolean(i));
                    break;
                case "linea":
                    current.setLinea(e.getInt(i));
                    break;
                case "id_mantenimiento":
                    current.setIdMantenimiento(e.getInt(i));
                    break;
                case "id_tipo_descripcion":
                    current.setIdTipoDescripcion(e.getInt(i));
                    break;
                case "texto":
                    current.setTexto(e.getString(i));
                    break;
            }
        }

        current.setSearchOnlyByPrimaryKey(true);
        current.acceptChanges();

        return current;
    }
}
