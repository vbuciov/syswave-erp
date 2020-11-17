package com.syswave.datos.miempresa;

import com.syswave.datos.common.SingletonDataAccess;
import com.syswave.entidades.miempresa.Horario;
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
public class HorariosDataAccess extends SingletonDataAccess<Horario>
{

    private final String insertProcedure = "horario_insert(?,?,?,?)";

    public HorariosDataAccess(IMediatorDataSource mysource)
    {
        super(mysource, "horarios", "id", "id_jornada",
              "nombre", "hora_inicio", "hora_fin");
        setInsertProcedure(insertProcedure);
    }

    //--------------------------------------------------------------------
    @Override
    public Horario onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
    {
        Horario current = new Horario();

        for (int i = 1; i <= e.getColumnCount(); i++)
        {
            switch (e.getColumnName(i))
            {
                case "id":
                    current.setId(e.getInt(i));
                    break;
                case "id_jornada":
                    current.setIdJornada(e.getInt(i));
                    break;
                case "nombre":
                    current.setNombre(e.getString(i));
                    break;
                case "hora_inicio":
                    current.setHoraInicio(e.getTime(i));
                    break;
                case "hora_fin":
                    current.setHoraFin(e.getTime(i));
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
    protected void onConvertTransfer(Horario values, DataSetEvent e) throws SQLException, UnsupportedOperationException
    {
        if (e.getDML() == insertProcedure)
        {
            e.setInt(1, values.getIdJornada());//"vid_jornada"
            e.setString(2, values.getNombre());//"vnombre"
            e.setTime(3, values.getHoraInicio());//"vhora_inicio"
            e.setTime(4, values.getHoraFin());//"vhora_inicio"
        }
    }

    //--------------------------------------------------------------------    
    @Override
    protected void onConvertKeyResult(DataGetEvent e, Horario value) throws SQLException, UnsupportedOperationException
    {
        for (int i = 1; i <= e.getColumnCount(); i++)
        {
            switch (e.getColumnName(i))
            {
                case "id":
                    value.setId(e.getInt(i));
                    break;
                case "id_jornada":
                    value.setIdJornada(e.getInt(i));
                    break;
                case "nombre":
                    value.setNombre(e.getString(i));
                    break;
                case "hora_inicio":
                    value.setHoraInicio(e.getTime(i));
                    break;
                case "hora_fin":
                    value.setHoraFin(e.getTime(i));
                    break;
            }
        }
        /*  if (e.getColumnCount() > 0)
            value.setId(e.getInt(1));*/
    }

    //---------------------------------------------------------------------
    @Override
    public void onSendValues(DataSendToQueryEvent e, Horario values)
    {
        if (values.getId() != Horario.EMPTY_INT)
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

        if (values.getIdJornada() != Horario.EMPTY_INT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addFloat("id_jornada", values.getIdJornada());
            else if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
                e.addFloat("id_jornada", QueryParameter.Operator.EQUAL, values.getIdJornada());
        }

        if (values.getNombre() != Horario.EMPTY_STRING)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addString("nombre", values.getNombre());
            else if (!values.isSearchOnlyByPrimaryKey())
                e.addString("nombre", QueryParameter.Operator.EQUAL, values.getNombre());
        }

        if (values.getHoraFin() != Horario.EMPTY_DATE)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addTime("hora_inicio", values.getHoraInicio());
            else if (!values.isSearchOnlyByPrimaryKey())
                e.addTime("hora_inicio", QueryParameter.Operator.EQUAL, values.getHoraInicio());
        }

        if (values.getHoraFin() != Horario.EMPTY_DATE)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addTime("hora_fin", values.getHoraFin());
            else if (!values.isSearchOnlyByPrimaryKey())
                e.addTime("hora_fin", QueryParameter.Operator.EQUAL, values.getHoraFin());
        }
    }
}
