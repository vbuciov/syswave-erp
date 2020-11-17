package com.syswave.datos.miempresa;

import com.syswave.datos.common.SingletonDataAccess;
import com.syswave.entidades.miempresa.Valor;
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
public class ValoresDataAccess extends SingletonDataAccess<Valor>
{

    private final String insertProcedure = "valor_insert(?,?,?,?,?)";

    public ValoresDataAccess(IMediatorDataSource mysource)
    {
        super(mysource, "valores", "id", "valor", "seccion", "descripcion", "formato",
              "es_activo");
        setInsertProcedure(insertProcedure);
    }

    //--------------------------------------------------------------------
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

    //---------------------------------------------------------------------
    /**
     * InsertProcedure is using an executeUpdateStoredProcedure
     *
     * @throws java.sql.SQLException
     */
    @Override
    protected void onConvertTransfer(Valor values, DataSetEvent e) throws SQLException, UnsupportedOperationException
    {
        if (e.getDML() == insertProcedure)
        {
            e.setString(1, values.getValor(), 45);//"vvalor"
            e.setString(2, values.getSeccion(), 100);//"vseccion"
            e.setString(3, values.getDescripcion(), 100);//"vdescripcion"
            e.setString(4, values.getFormato(), 45);//"vformato"
            e.setBoolean(5, values.esActivo());//"ves_activo"
        }

    }

    //--------------------------------------------------------------------    
    @Override
    protected void onConvertKeyResult(DataGetEvent e, Valor value) throws SQLException, UnsupportedOperationException
    {
        for (int i = 1; i <= e.getColumnCount(); i++)
        {
            switch (e.getColumnName(i))
            {
                case "id":
                    value.setId(e.getInt(i));
                    break;
                case "valor":
                    value.setValor(e.getString(i));
                    break;
                case "seccion":
                    value.setSeccion(e.getString(i));
                    break;
                case "descripcion":
                    value.setDescripcion(e.getString(i));
                    break;
                case "formato":
                    value.setFormato(e.getString(i));
                    break;
                case "es_activo":
                    value.setActivo(e.getBoolean(i));
                    break;
            }
        }
        /*  if (e.getColumnCount() > 0)
            value.setId(e.getInt(1));*/
    }

    //---------------------------------------------------------------------
    @Override
    public void onSendValues(DataSendToQueryEvent e, Valor values)
    {

        if (e.isCreateOperation())
        {
            e.addString("valor", values.getValor(), 45);
            e.addString("seccion", values.getSeccion(), 100);
            e.addString("descripcion", values.getDescripcion(), 100);
            e.addString("formato", values.getFormato(), 45);
            e.addBoolean("es_activo", values.esActivo());
        }

        else if (e.isUpdateOperation())
        {
            e.addString("valor", values.getValor(), 45);
            e.addString("seccion", values.getSeccion(), 100);
            e.addString("descripcion", values.getDescripcion(), 100);
            e.addString("formato", values.getFormato(), 45);
            e.addBoolean("es_activo", values.esActivo());

            //e.SendParameters.AddInteger ("fk_pers_id", values.Pers_Id)
            e.addInteger("id", QueryParameter.Operator.EQUAL, values.getId_Viejo());

        }

        else if (e.isRetrieveOperation() || e.isDeleteOperation())
        {
            if (values.getId() != Valor.EMPTY_INT)
                e.addInteger("id", QueryParameter.Operator.EQUAL, values.getId());

            if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
            {
                if (!values.getValor().equals(Valor.EMPTY_STRING))
                    e.addString("valor", QueryParameter.Operator.LIKE, String.format("%s%%", values.getValor()));

                if (!values.getSeccion().equals(Valor.EMPTY_STRING))
                    e.addString("seccion", QueryParameter.Operator.EQUAL, values.getSeccion());

                if (!values.getDescripcion().equals(Valor.EMPTY_STRING))
                    e.addString("descripcion", QueryParameter.Operator.LIKE, String.format("%s%%", values.getDescripcion()));

                if (!values.getFormato().equals(Valor.EMPTY_STRING))
                    e.addString("formato", QueryParameter.Operator.LIKE, String.format("%s%%", values.getFormato()));

                if (values.isSet())
                    e.addBoolean("es_activo", QueryParameter.Operator.EQUAL, values.esActivo());
            }
        }

    }
}
