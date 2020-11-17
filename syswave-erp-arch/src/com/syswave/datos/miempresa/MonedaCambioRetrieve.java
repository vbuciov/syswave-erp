package com.syswave.datos.miempresa;

import com.syswave.datos.common.SingletonRetrieveDataAccess;
import com.syswave.entidades.miempresa_vista.MonedaCambioVista;
import datalayer.api.IMediatorDataSource;
import datalayer.events.DataGetEvent;
import datalayer.utils.QueryDataTransfer;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class MonedaCambioRetrieve extends SingletonRetrieveDataAccess<MonedaCambioVista>
{
    public MonedaCambioRetrieve(IMediatorDataSource mysource)
    {
        super(mysource);
        /*super("moneda_cambio", "consecutivo" , "id_moneda_origen"
            , "origen", "proporcion", "id_moneda_destino", "destino"
            , "fecha_validez");
        setWithAutoID(false);*/
    }

    //---------------------------------------------------------------------
    public List<MonedaCambioVista> ultimosTiposCambio(boolean hoy)
    {
        QueryDataTransfer parametros = new QueryDataTransfer("ultimos_tipos_cambio_select");

        parametros.addBoolean("vfecha_hoy", hoy);

        return executeReadStoredProcedure(parametros);
    }

    //---------------------------------------------------------------------
    @Override
    public MonedaCambioVista onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
    {
        MonedaCambioVista current = new MonedaCambioVista();

        for (int i = 1; i <= e.getColumnCount(); i++)
        {
            switch (e.getColumnName(i))
            {
                case "consecutivo":
                    current.setConsecutivo(e.getInt(i));
                    break;
                case "id_moneda_origen":
                    current.setIdMonedaOrigen(e.getInt(i));
                    break;
                case "origen":
                    current.setOrigen(e.getString(i));
                    break;
                case "proporcion":
                    current.setProporcion(e.getFloat(i));
                    break;
                case "id_moneda_destino":
                    current.setIdMonedaDestino(e.getInt(i));
                    break;
                case "destino":
                    current.setDestino(e.getString(i));
                    break;
                case "fecha_validez":
                    current.setFecha_validez(e.getDate(i));
                    break;
            }
        }

        current.setSearchOnlyByPrimaryKey(true);
        current.acceptChanges();

        return current;
    }
}