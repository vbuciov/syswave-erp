package com.syswave.datos.miempresa;

import com.syswave.datos.common.SingletonRetrieveDataAccess;
import com.syswave.entidades.miempresa.DesgloseCosto;
import com.syswave.entidades.miempresa_vista.DesgloseCosto_5FN;
import datalayer.api.IMediatorDataSource;
import datalayer.events.DataGetEvent;
import datalayer.utils.QueryDataTransfer;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author sis2
 */
public class DesgloseCostos_5FNRetrieve extends SingletonRetrieveDataAccess<DesgloseCosto_5FN>
{
    private final String searchProcedure = "desglose_costos_5FN";

    //--------------------------------------------------------------------    
    public DesgloseCostos_5FNRetrieve(IMediatorDataSource mysource)
    {
        super(mysource);
        /*super("desglose_costos", "linea", "id_precio_variable", 
              "id_precio_indirecto", "cantidad", "factor_cantidad", 
              "precio", "factor_precio", 
              "subtotal", "factor", "monto", "total", "observacion");      
        setWithAutoID(false);*/
    }

    //--------------------------------------------------------------------    
    public List<DesgloseCosto_5FN> Retrieve(DesgloseCosto filter)
    {
        QueryDataTransfer parametros = new QueryDataTransfer(searchProcedure);

        parametros.addInteger("vlinea", filter.getLinea());
        parametros.addInteger("vid_precio_variable", filter.getIdPrecioVariable());
        parametros.addInteger("vid_precio_indirecto", filter.getIdPrecioIndirecto());
        return executeReadStoredProcedure(parametros); //MySQL
    }

    //--------------------------------------------------------------------    
    @Override
    public DesgloseCosto_5FN onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
    {
        DesgloseCosto_5FN current = new DesgloseCosto_5FN();

        for (int i = 1; i <= e.getColumnCount(); i++)
        {
            switch (e.getColumnName(i))
            {
                case "linea":
                    current.setLinea(e.getInt(i));
                    break;
                case "id_precio_variable":
                    current.setIdPrecioVariable(e.getInt(i));
                    break;
                case "id_precio_indirecto":
                    current.setIdPrecioIndirecto(e.getInt(i));
                    break;
                case "presentacion":
                    current.setPresentacion(e.getString(i));
                    break;
                case "id_categoria":
                    current.setIdCategoria(e.getInt(i));
                    break;
                case "categoria":
                    current.setCategoria(e.getString(i));
                    break;
                case "id_area_precio":
                    current.setIdArea_precio(e.getInt(i));
                    break;
                case "area_precio":
                    current.setArea_precio(e.getString(i));
                    break;
                case "id_moneda":
                    current.setIdMoneda(e.getInt(i));
                    break;
                case "moneda":
                    current.setMoneda(e.getString(i));
                    break;
                case "id_variante":
                    current.setIdVariante(e.getInt(i));
                    break;
                case "id_bien":
                    current.setId_grupo(e.getInt(i));
                    break;
                case "tipo_bien":
                    current.setTipo_bien(e.getString(i));
                    break;
                case "cantidad":
                    current.setCantidad(e.getFloat(i));
                    break;
                case "factor_cantidad":
                    current.setFactor_cantidad(e.getFloat(i));
                    break;
                case "precio":
                    current.setPrecio(e.getFloat(i));
                    break;
                case "factor_precio":
                    current.setFactor_precio(e.getFloat(i));
                    break;
                case "subtotal":
                    current.setSubtotal(e.getFloat(i));
                    break;
                case "factor":
                    current.setFactor(e.getInt(i));
                    break;
                case "monto":
                    current.setMonto(e.getFloat(i));
                    break;
                case "total":
                    current.setTotal(e.getFloat(i));
                    break;
                case "observacion":
                    current.setObservacion(e.getString(i));
                    break;
            }
        }

        current.setSearchOnlyByPrimaryKey(true);
        current.acceptChanges();

        return current;
    }
}