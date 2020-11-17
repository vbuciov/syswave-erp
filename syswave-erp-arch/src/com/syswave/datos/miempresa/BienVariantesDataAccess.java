package com.syswave.datos.miempresa;

import com.syswave.datos.common.SingletonDataAccess;
import com.syswave.entidades.miempresa.BienVariante;
import datalayer.api.IMediatorDataSource;
import datalayer.events.DataGetEvent;
import datalayer.events.DataSendToQueryEvent;
import datalayer.events.DataSetEvent;
import datalayer.utils.QueryParameter;
import java.sql.SQLException;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class BienVariantesDataAccess extends SingletonDataAccess<BienVariante>
{

    public final String insertProcedure = "bien_variante_insert(?,?,?,?,?,?,?,?,?,?)";

    //---------------------------------------------------------------------
    public BienVariantesDataAccess(IMediatorDataSource mysource)
    {
        super(mysource, "bien_variantes", "id", "id_bien", "descripcion", "es_activo",
              "es_armable", "es_comercializar", 
              "es_inventario", "nivel", "inventario_como",
              "mantenimiento_como", "valor_esperado");
        setBasicOrderBy("id_bien", "id");
        setInsertProcedure(insertProcedure);
        //"masa", "id_unidad_masa",
        //"ancho", "alto", "largo", "id_unidad_longitud",
    }

    //---------------------------------------------------------------------
    /**
     * InsertProcedure is using an executeUpdateStoredProcedure
     *
     * @throws java.sql.SQLException
     */
    @Override
    protected void onConvertTransfer(BienVariante values, DataSetEvent e) throws SQLException, UnsupportedOperationException
    {
        if (e.getDML() == insertProcedure)
        {
            e.setInt(1, values.getIdBien()); //"vid_bien"
            e.setString(2, values.getDescripcion(), 90);//"vdescripcion"
            e.setBoolean(3, values.esActivo()); //"ves_activo"
            e.setBoolean(4, values.esArmable());//"ves_armable"
            e.setBoolean(5, values.EsComercializar());//"ves_comercializar"
            e.setBoolean(6, values.esInventario());//"ves_inventario"
            e.setInt(7, values.getNivel());//"vnivel"
            e.setInt(8, values.getInventarioCcomo()); //"vinventario_como"
            e.setInt(9, values.getMantenimientoComo()); //"vmantenimiento_como"
            e.setInt(10, values.getValorEsperado());//"vvalor_esperado"
        }
    }

    //--------------------------------------------------------------------    
    @Override
    protected void onConvertKeyResult(DataGetEvent e, BienVariante value) throws SQLException, UnsupportedOperationException
    {
        if (e.getDML() == insertProcedure)
        {
            for (int i = 1; i <= e.getColumnCount(); i++)
            {
                switch (e.getColumnName(i))
                {
                    case "id":
                        value.setId(e.getInt(i));
                        break;
                    case "id_bien":
                        value.setIdBien(e.getInt(i));
                        break;
                    case "descripcion":
                        value.setDescripcion(e.getString(i));
                        break;
                    case "es_activo":
                        value.setEsActivo(e.getBoolean(i));
                        break;
                    case "es_armable":
                        value.setEsArmable(e.getBoolean(i));
                        break;
                    case "es_comercializar":
                        value.setEsComercializar(e.getBoolean(i));
                        break;
                    case "es_inventario":
                        value.setEsInventario(e.getBoolean(i));
                        break;
                    case "nivel":
                        value.setNivel(e.getInt(i));
                        break;
                    case "inventario_como":
                        value.setInventarioCcomo(e.getInt(i));
                        break;
                    case "mantenimiento_como":
                        value.setMantenimientoComo(e.getInt(i));
                        break;
                    case "valor_esperado":
                        value.setValorEsperado(e.getInt(i));
                        break;
                }
            }
        }
        /*else
            if (e.getColumnCount() > 0)
                value.setId(e.getInt(1));*/
    }

    //--------------------------------------------------------------------
    @Override
    public void onSendValues(DataSendToQueryEvent e, BienVariante values)
    {
        if (values.getId() != BienVariante.EMPTY_INT)
        {
            if (e.isCreateOperation())
                e.addInteger("id", values.getId());

            else if (e.isUpdateOperation())
            {
                e.addInteger("id", values.getId());
                e.addInteger("id", QueryParameter.Operator.EQUAL, values.getId_Viejo());
            }

            else
                e.addInteger("id", QueryParameter.Operator.EQUAL, values.getId_Viejo());
        }

        if (values.getIdBien() != BienVariante.EMPTY_INT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addInteger("id_bien", values.getIdBien());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addInteger("id_bien", QueryParameter.Operator.EQUAL, values.getIdBien());
        }

        if (!values.getDescripcion().equals(BienVariante.EMPTY_STRING))
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addString("descripcion", values.getDescripcion(), 45);

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addString("descripcion", QueryParameter.Operator.EQUAL, values.getDescripcion());
        }

        //Nota: Los boleanos solo se trabajan como valores o criterios de busqueda si el objeto reporta cambios.
        //Debido a que no existe una representación de nulo para los mismos.
        if (values.isSet())
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
            {
                e.addBoolean("es_activo", values.esActivo());
                e.addBoolean("es_armable", values.esArmable());
                e.addBoolean("es_comercializar", values.EsComercializar());
                e.addBoolean("es_inventario", values.esInventario());
            }

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addBoolean("es_activo", QueryParameter.Operator.EQUAL, values.esActivo());
        }

        if (values.getNivel() != BienVariante.EMPTY_INT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addInteger("nivel", values.getNivel());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addInteger("nivel", QueryParameter.Operator.EQUAL, values.getNivel());
        }

        if (values.getInventarioCcomo() != BienVariante.EMPTY_INT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addInteger("inventario_como", values.getInventarioCcomo());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addInteger("inventario_como", QueryParameter.Operator.EQUAL, values.getInventarioCcomo());
        }

        if (values.getMantenimientoComo() != BienVariante.EMPTY_INT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addInteger("mantenimiento_como", values.getMantenimientoComo());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addInteger("mantenimiento_como", QueryParameter.Operator.EQUAL, values.getMantenimientoComo());
        }

        if (values.getMantenimientoComo() != BienVariante.EMPTY_INT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addInteger("valor_esperado", values.getValorEsperado());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addInteger("valor_esperado", QueryParameter.Operator.EQUAL, values.getValorEsperado());
        }
    }

    //--------------------------------------------------------------------
    @Override
    public BienVariante onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
    {
        BienVariante current = new BienVariante();

        for (int i = 1; i <= e.getColumnCount(); i++)
        {
            switch (e.getColumnName(i))
            {
                case "id":
                    current.setId(e.getInt(i));
                    break;
                case "id_bien":
                    current.setIdBien(e.getInt(i));
                    break;
                case "descripcion":
                    current.setDescripcion(e.getString(i));
                    break;
                case "es_activo":
                    current.setEsActivo(e.getBoolean(i));
                    break;
                case "es_armable":
                    current.setEsArmable(e.getBoolean(i));
                    break;
                case "es_comercializar":
                    current.setEsComercializar(e.getBoolean(i));
                    break;
                case "es_inventario":
                    current.setEsInventario(e.getBoolean(i));
                    break;
                case "nivel":
                    current.setNivel(e.getInt(i));
                    break;
                case "inventario_como":
                    current.setInventarioCcomo(e.getInt(i));
                    break;
                case "mantenimiento_como":
                    current.setMantenimientoComo(e.getInt(i));
                    break;
                case "valor_esperado":
                    current.setValorEsperado(e.getInt(i));
                    break;
            }
        }

        current.setSearchOnlyByPrimaryKey(true);
        current.acceptChanges();

        return current;
    }
}
