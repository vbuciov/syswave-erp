package com.syswave.datos.miempresa;

import com.syswave.datos.common.SingletonDataAccess;
import com.syswave.entidades.miempresa.Persona_tiene_Existencia;
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
public class PersonaTieneExistenciaDataAccess extends SingletonDataAccess<Persona_tiene_Existencia>
{

    public final String insertProcedure = "persona_tiene_existencia_insert(?,?,?,?)";
    public final String updateProcedure = "persona_tiene_existencia_update(?,?,?,?,?,?)";

    public PersonaTieneExistenciaDataAccess(IMediatorDataSource mysource)
    {
        super(mysource, "persona_tiene_existencias", "linea", "id_persona",
              "entrada", "id_ubicacion", "existencia");
        setBasicOrderBy("id_persona", "linea");
        setInsertProcedure(insertProcedure);
        setUpdateProcedure(updateProcedure);
    }

    //---------------------------------------------------------------------
    @Override
    public Persona_tiene_Existencia onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
    {
        Persona_tiene_Existencia current = new Persona_tiene_Existencia();

        for (int i = 1; i <= e.getColumnCount(); i++)
        {
            switch (e.getColumnName(i))
            {
                case "linea":
                    current.setLinea(e.getInt(i));
                    break;
                case "id_persona":
                    current.setIdPersona(e.getInt(i));
                    break;
                case "entrada":
                    current.setEntrada(e.getInt(i));
                    break;
                case "id_ubicacion":
                    current.setIdUbicacion(e.getInt(i));
                    break;
                case "existencia":
                    current.setExistencia(e.getFloat(i));
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
    protected void onConvertTransfer(Persona_tiene_Existencia values, DataSetEvent e) throws SQLException, UnsupportedOperationException
    {
        if (e.getDML() == insertProcedure)
        {
            e.setInt(1, values.getIdPersona());//"vid_persona"
            e.setInt(2, values.getEntrada());//"ventrada"
            e.setInt(3, values.getIdUbicacion());//"vid_ubicacion"
            e.setFloat(4, values.getExistencia());//"vexistencia"
        }

        else if (e.getDML() == updateProcedure)
        {
            e.setInt(1, values.getLinea_Viejo());//"vlinea_old"
            e.setInt(2, values.getIdPersona_Viejo());//"vid_persona_old"
            e.setInt(3, values.getIdPersona());//"vid_persona_new"
            e.setInt(4, values.getEntrada());//"ventrada"
            e.setInt(5, values.getIdUbicacion());//"vid_ubicacion"
            e.setFloat(6, values.getExistencia());//"vexistencia"
        }
    }

    //--------------------------------------------------------------------    
    @Override
    protected void onConvertKeyResult(DataGetEvent e, Persona_tiene_Existencia value) throws SQLException, UnsupportedOperationException
    {
        for (int i = 1; i <= e.getColumnCount(); i++)
        {
            switch (e.getColumnName(i))
            {
                case "linea":
                    value.setLinea(e.getInt(i));
                    break;
                case "id_persona":
                    value.setIdPersona(e.getInt(i));
                    break;
                case "entrada":
                    value.setEntrada(e.getInt(i));
                    break;
                case "id_ubicacion":
                    value.setIdUbicacion(e.getInt(i));
                    break;
                case "existencia":
                    value.setExistencia(e.getFloat(i));
                    break;
            }
        }
        /*  if (e.getColumnCount() > 0)
            value.setId(e.getInt(1));*/
    }

    //---------------------------------------------------------------------
    @Override
    public void onSendValues(DataSendToQueryEvent e, Persona_tiene_Existencia values)
    {
        if (values.getLinea() != Persona_tiene_Existencia.EMPTY_INT)
        {
            if (e.isCreateOperation())
                e.addInteger("linea", values.getLinea());

            else if (e.isUpdateOperation())
            {
                e.addInteger("linea", values.getLinea());
                e.addInteger("linea", QueryParameter.Operator.EQUAL, values.getLinea_Viejo());
            }

            else
                e.addInteger("linea", QueryParameter.Operator.EQUAL, values.getLinea());
        }

        if (values.getIdPersona() != Persona_tiene_Existencia.EMPTY_INT)
        {
            if (e.isCreateOperation())
                e.addInteger("id_persona", values.getIdPersona());

            else if (e.isUpdateOperation())
            {
                e.addInteger("id_persona", values.getIdPersona());
                e.addInteger("id_persona", QueryParameter.Operator.EQUAL, values.getIdPersona_Viejo());
            }

            else
                e.addInteger("id_persona", QueryParameter.Operator.EQUAL, values.getIdPersona());
        }

        if (values.getEntrada() != Persona_tiene_Existencia.EMPTY_INT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addInteger("entrada", values.getEntrada());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addInteger("entrada", QueryParameter.Operator.EQUAL, values.getEntrada());
        }

        if (values.getIdUbicacion() != Persona_tiene_Existencia.EMPTY_INT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addInteger("id_ubicacion", values.getIdUbicacion());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addInteger("id_ubicacion", QueryParameter.Operator.EQUAL, values.getIdUbicacion());
        }

        if (values.getExistencia() != Persona_tiene_Existencia.EMPTY_FLOAT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addFloat("existencia", values.getExistencia());

            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addFloat("existencia", QueryParameter.Operator.EQUAL, values.getExistencia());
        }
    }
}
