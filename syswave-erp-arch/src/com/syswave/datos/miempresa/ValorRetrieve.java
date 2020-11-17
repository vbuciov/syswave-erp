package com.syswave.datos.miempresa;

import com.syswave.datos.common.SingletonDataAccess;
import com.syswave.entidades.miempresa.Valor;
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
public class ValorRetrieve extends SingletonDataAccess<Valor>
{
    private final Relation tipos_medios, tipos_identificador, tipos_condicion;

    public ValorRetrieve(IMediatorDataSource mysource)
    {
        super(mysource, "valores", "id", "valor", "descripcion", "formato", "es_activo");
        setWithAutoID(false);
        tipos_medios = new Relation("tipos_medios", getColumns());
        tipos_identificador = new Relation("tipos_identificador", getColumns());
        tipos_condicion = new Relation("tipos_condicion", getColumns());
    }

    //---------------------------------------------------------------------
    public List<Valor> retrieveTiposMedio()
    {
        return submitQuery(tipos_medios);
    }

    //---------------------------------------------------------------------
    public List<Valor> retrieveTiposMedio(Valor Filter)
    {
        return submitQuery(tipos_medios, Filter);
    }

    //---------------------------------------------------------------------
    public List<Valor> retrieveTiposMedioReferenciado()
    {
        return executeReadStoredProcedure("tipo_medio_used_select()");
    }

    //---------------------------------------------------------------------
    public List<Valor> retrieveTiposIdentificador()
    {

        return submitQuery(tipos_identificador);
    }

    //---------------------------------------------------------------------
    public List<Valor> retrieveTiposIdentificador(Valor Filter)
    {
        return submitQuery(tipos_identificador, Filter);
    }

    //---------------------------------------------------------------------
    public List<Valor> retrieveTiposIdentificadorReferenciado()
    {
        return executeReadStoredProcedure("tipo_identificador_used_select()");
    }

    //---------------------------------------------------------------------
    public List<Valor> retrieveTiposCondicion()
    {
        return submitQuery(tipos_condicion);
    }

    //---------------------------------------------------------------------
    public List<Valor> retrieveTiposCondicion(Valor Filter)
    {
        return submitQuery(tipos_condicion, Filter);
    }

    //---------------------------------------------------------------------
    @Override
    public Valor onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
    {
        Valor current = new Valor();

        for (int i = 1; i <= e.getColumnCount(); i++)
        {
            switch (e.getColumnName(i))
            {
                case "id":
                    current.setId(e.getInt(i));
                    break;
                case "valor":
                    current.setValor(e.getString(i));
                    break;
                case "seccion":
                    current.setSeccion(e.getString(i));
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
            }
        }

        current.setSearchOnlyByPrimaryKey(true);
        current.acceptChanges();

        return current;
    }

    //--------------------------------------------------------------------    
    /*@Override
    protected void onConvertKeyResult(DataGetEvent e, Valor value) throws SQLException, UnsupportedOperationException
    {
          if (e.getColumnCount() > 0)
            value.setId(e.getInt(1));
    }*/

    //---------------------------------------------------------------------
    @Override
    public void onSendValues(DataSendToQueryEvent e, Valor values)
    {
        if (values.getId() != Valor.EMPTY_INT)
        {
            if (e.isRetrieveOperation())
                e.addInteger("id", QueryParameter.Operator.EQUAL, values.getId_Viejo());
        }

        if (!values.getValor().equals(Valor.EMPTY_STRING))
        {
            if (e.isRetrieveOperation())
                e.addString("valor", QueryParameter.Operator.EQUAL, values.getValor());
        }

        if (!values.getDescripcion().equals(Valor.EMPTY_STRING))
        {
            if (e.isRetrieveOperation())
                e.addString("descripcion", QueryParameter.Operator.EQUAL, values.getDescripcion());
        }

        if (values.isSet())
        {
            if (e.isRetrieveOperation())
                e.addBoolean("es_activo", QueryParameter.Operator.EQUAL, values.esActivo());
        }
    }
}