package com.syswave.datos.miempresa;

import com.syswave.datos.common.SingletonDataAccess;
import com.syswave.entidades.miempresa.Bien;
import com.syswave.entidades.miempresa.BienVariante;
import datalayer.api.IMediatorDataSource;
import datalayer.events.DataGetEvent;
import datalayer.events.DataSendToQueryEvent;
import datalayer.utils.QueryParameter;
import datalayer.utils.Relation;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class PartesRetrieve extends SingletonDataAccess<BienVariante>
{

    private final Relation shortBasic;

    public PartesRetrieve(IMediatorDataSource mysource)
    {
        super(mysource, "partes", "id", "id_bien", "descripcion", "tipo_bien",
              "es_inventario", "nivel");
        setWithAutoID(false);
        shortBasic = new Relation(getTable(), new String[]
                          {
                              "id",
                              "descripcion" });
    }

    //---------------------------------------------------------------------
    public List<BienVariante> SmallRetrieve(BienVariante Filter)
    {
        return submitQuery(shortBasic);
    }

    //---------------------------------------------------------------------
    @Override
    public void onSendValues(DataSendToQueryEvent e, BienVariante values)
    {
        if (e.isRetrieveOperation())
        {
            if (values.getId() != BienVariante.EMPTY_INT)
                e.addInteger("id", QueryParameter.Operator.EQUAL, values.getId_Viejo());

            if (values.getIdBien() != BienVariante.EMPTY_INT)
                e.addInteger("id_bien", QueryParameter.Operator.EQUAL, values.getIdBien());

            if (!values.getDescripcion().equals(BienVariante.EMPTY_STRING))
                e.addString("descripcion", QueryParameter.Operator.LIKE, values.getDescripcion());

            if (values.isSet())
            {
                // e.addBoolean("es_activo",  QueryParameter.Operator.EQUAL, values.esActivo());
                e.addBoolean("es_inventario", QueryParameter.Operator.EQUAL, values.esInventario());
            }

            if (values.getNivel() != BienVariante.EMPTY_INT)
                e.addInteger("nivel", QueryParameter.Operator.EQUAL, values.getNivel());
        }
    }

    //---------------------------------------------------------------------
    @Override
    public BienVariante onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
    {
        BienVariante current = new BienVariante();
        Bien tipo_bien = current.getHasOneGrupo();

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
                case "tipo_bien":
                    tipo_bien.setNombre(e.getString(i));
                    break;
                case "es_inventario":
                    current.setEsInventario(e.getBoolean(i));
                    break;
                case "nivel":
                    current.setNivel(e.getInt(i));
                    break;
            }
        }

        current.setSearchOnlyByPrimaryKey(true);
        current.acceptChanges();

        return current;
    }
}