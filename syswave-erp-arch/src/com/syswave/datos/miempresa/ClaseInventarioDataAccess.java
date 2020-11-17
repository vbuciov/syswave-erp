package com.syswave.datos.miempresa;

import com.syswave.datos.common.SingletonDataAccess;
import com.syswave.entidades.miempresa.ClaseInventario;
import datalayer.api.IMediatorDataSource;
import datalayer.events.DataGetEvent;
import datalayer.events.DataSendToQueryEvent;
import datalayer.utils.QueryParameter;
import java.sql.SQLException;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class ClaseInventarioDataAccess extends SingletonDataAccess<ClaseInventario>
{
    public ClaseInventarioDataAccess(IMediatorDataSource mysource)
    {
        super(mysource, "clases_inventarios", 
              "id", "nombre", "es_comerciable", "es_armable");
    }
    
    //--------------------------------------------------------------------    
    @Override
    protected void onConvertKeyResult(DataGetEvent e, ClaseInventario value) throws SQLException, UnsupportedOperationException
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
                    case "nombre":
                        value.setNombre(e.getString(i));
                        break;
                    case "es_comerciable":
                        value.setesComercial(e.getBoolean(i));
                        break;
                    case "es_armable":
                        value.setesArmable(e.getBoolean(i));
                        break;
                }
            }
        }
        else
            if (e.getColumnCount() > 0)
                value.setId(e.getInt(1));
    }

    //--------------------------------------------------------------------
    @Override
    public void onSendValues(DataSendToQueryEvent e, ClaseInventario argument)
    {
        if (argument.getId() != ClaseInventario.EMPTY_INT)
        {
            if (e.isCreateOperation())
                e.addInteger("id", argument.getId());

            else if (e.isUpdateOperation())
            {
                e.addInteger("id", argument.getId());
                e.addInteger("id", QueryParameter.Operator.EQUAL, argument.getId_Viejo());
            }

            else
                e.addInteger("id", QueryParameter.Operator.EQUAL, argument.getId_Viejo());
        }

        if (argument.getNombre() != ClaseInventario.EMPTY_STRING)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addString("nombre", argument.getNombre());

            else if (!argument.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addString("nombre", QueryParameter.Operator.EQUAL, argument.getNombre());
        }
        
        //Nota: Debido a la inexistencia de una reprentación de nulos en booleanos
        //Estos solo son trabajados cuando el objeto reporta cambios.
        if (argument.isSet())
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addBoolean("es_comerciable", argument.esComercial());

            else if (!argument.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addBoolean("es_comerciable", QueryParameter.Operator.EQUAL, argument.esComercial());            
           
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addBoolean("es_armable", argument.esArmable());

            else if (!argument.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addBoolean("es_armable", QueryParameter.Operator.EQUAL, argument.esArmable());
        }
    }

    //---------------------------------------------------------------------
    @Override
    public ClaseInventario onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
    {
        ClaseInventario current = new ClaseInventario();

        for (int i = 1; i <= e.getColumnCount(); i++)
        {

            switch (e.getColumnName(i))
            {
                case "id":
                    current.setId(e.getInt(i));
                    break;
                case "nombre":
                    current.setNombre(e.getString(i));
                    break;
                case "es_comerciable":
                    current.setesComercial(e.getBoolean(i));
                    break;
                case "es_armable":
                    current.setesArmable(e.getBoolean(i));
                    break;
            }

        }

        current.setSearchOnlyByPrimaryKey(true);
        current.acceptChanges();

        return current;
    }
}