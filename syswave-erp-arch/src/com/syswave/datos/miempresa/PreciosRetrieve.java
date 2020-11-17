package com.syswave.datos.miempresa;

import com.syswave.datos.common.SingletonRetrieveDataAccess;
import com.syswave.entidades.miempresa.AreaPrecio;
import com.syswave.entidades.miempresa.Categoria;
import com.syswave.entidades.miempresa.VarianteIdentificador;
import com.syswave.entidades.miempresa_vista.ControlPrecioVista;
import datalayer.api.IMediatorDataSource;
import datalayer.events.DataGetEvent;
import datalayer.utils.QueryDataTransfer;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class PreciosRetrieve extends SingletonRetrieveDataAccess<ControlPrecioVista>
{

    private final String searchProcedure = "precio_categoria_buscar";
    private final String searchProcedure2 = "precio_area_buscar";

    public PreciosRetrieve(IMediatorDataSource mysource)
    {
        super(mysource);
    }

    public List<ControlPrecioVista> PrecioBuscar(Categoria tipo, int es_tipo, ControlPrecioVista generales, VarianteIdentificador identificador)
    {
        QueryDataTransfer parametros = new QueryDataTransfer(searchProcedure);

        if (tipo != null)
            parametros.addInteger("vid_categoria_base", tipo.getId());

        else
            parametros.addInteger("vid_categoria_base", -1);

        parametros.addInteger("ves_tipo", es_tipo);

        if (generales != null)
        {
            parametros.addBoolean("vusa_generales", true);
            parametros.addString("vpresentacion", generales.getPresentacion(), 500);
            parametros.addString("vcategoria", generales.getCategoria(), 500);
            parametros.addString("vdescripcion", generales.getDescripcion(), 500);
            parametros.addInteger("vid_moneda", generales.getIdMoneda());
            parametros.addString("vmoneda", generales.getMoneda(), 45);
            parametros.addInteger("vid_variante", generales.getIdVariante());
            parametros.addInteger("vid_bien", generales.getIdBien());
        }

        else
        {
            parametros.addBoolean("vusa_generales", false);
            parametros.addString("vpresentacion", "");
            parametros.addString("vcategoria", "");
            parametros.addString("vdescripcion", "");
            parametros.addInteger("vid_moneda", -1);
            parametros.addString("vmoneda", "");
            parametros.addInteger("vid_variante", -1);
            parametros.addInteger("vid_bien", -1);
        }

        if (identificador != null)
        {
            parametros.addBoolean("vusa_identificador", true);
            parametros.addInteger("vid_tipo_identificador", identificador.getIdTipoIdentificador());
            parametros.addString("vclave_identificador", identificador.getValor(), 45);
        }

        else
        {
            parametros.addBoolean("vusa_identificador", false);
            parametros.addInteger("vid_tipo_identificador", -1);
            parametros.addString("vclave_identificador", "");
        }

        return executeReadStoredProcedure(parametros);
    }

    public List<ControlPrecioVista> PrecioBuscar(AreaPrecio tipo, int es_tipo, ControlPrecioVista generales, VarianteIdentificador identificador)
    {
        QueryDataTransfer parametros = new QueryDataTransfer(searchProcedure2);

        if (tipo != null)
            parametros.addInteger("vid_area_precio_base", tipo.getId());

        else
            parametros.addInteger("vid_area_precio_base", -1);

        parametros.addInteger("ves_tipo", es_tipo);

        if (generales != null)
        {
            parametros.addBoolean("vusa_generales", true);
            parametros.addString("vpresentacion", generales.getPresentacion(), 500);
            parametros.addString("vcategoria", generales.getCategoria(), 500);
            parametros.addString("vdescripcion", generales.getDescripcion(), 500);
            parametros.addInteger("vid_moneda", generales.getIdMoneda());
            parametros.addString("vmoneda", generales.getMoneda(), 45);
            parametros.addInteger("vid_variante", generales.getIdVariante());
            parametros.addInteger("vid_bien", generales.getIdBien());
        }

        else
        {
            parametros.addBoolean("vusa_generales", false);
            parametros.addString("vpresentacion", "");
            parametros.addString("vcategoria", "");
            parametros.addString("vdescripcion", "");
            parametros.addInteger("vid_moneda", -1);
            parametros.addString("vmoneda", "");
            parametros.addInteger("vid_variante", -1);
            parametros.addInteger("vid_bien", -1);
        }

        if (identificador != null)
        {
            parametros.addBoolean("vusa_identificador", true);
            parametros.addInteger("vid_tipo_identificador", identificador.getIdTipoIdentificador());
            parametros.addString("vclave_identificador", identificador.getValor(), 45);
        }

        else
        {
            parametros.addBoolean("vusa_identificador", false);
            parametros.addInteger("vid_tipo_identificador", -1);
            parametros.addString("vclave_identificador", "");
        }

        return executeReadStoredProcedure(parametros);
    }

    //---------------------------------------------------------------------
    @Override
    public ControlPrecioVista onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
    {
        ControlPrecioVista current = new ControlPrecioVista();

        for (int i = 1; i <= e.getColumnCount(); i++)
        {
            switch (e.getColumnName(i))
            {
                case "id":
                    current.setId(e.getInt(i));
                    break;
                case "presentacion":
                    current.setPresentacion(e.getString(i));
                    break;
                case "es_servicio":
                    current.setEsServicio(e.getInt(i));
                    break;
                case "id_categoria":
                    current.setIdCategoria(e.getInt(i));
                    break;
                case "categoria":
                    current.setCategoria(e.getString(i));
                    break;
                case "descripcion":
                    current.setDescripcion(e.getString(i));
                    break;
                case "es_tipo":
                    current.setEsTipo(e.getInt(i));
                    break;
                case "mercado":
                    current.setPrecioFinal(e.getFloat(i));
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
                    current.setIdBien(e.getInt(i));
                    break;
                case "es_activo":
                    current.setActivo(e.getBoolean(i));
                    break;
                case "es_comercializar":
                    current.setComercializar(e.getBoolean(i));
                    break;
                case "es_inventario":
                    current.setEsInventario(e.getBoolean(i));
                    break;
                case "identificador":
                    current.setIdentificador_muestra(e.getString(i));
                    break;
                case "existencia":
                    current.setExistencia_muestra(e.getFloat(i));
                    break;
                case "id_area_precio":
                    current.setIdAreaPrecio(e.getInt(i));
                    break;
                case "area_precio":
                    current.setAreaPrecio(e.getString(i));
                    break;
            }
        }

        current.setSearchOnlyByPrimaryKey(true);
        current.acceptChanges();

        return current;
    }
}
