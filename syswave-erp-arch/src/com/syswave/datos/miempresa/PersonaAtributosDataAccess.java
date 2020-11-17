package com.syswave.datos.miempresa;

import com.syswave.datos.common.SingletonDataAccess;
import com.syswave.entidades.miempresa.PersonaAtributo;
import datalayer.api.IMediatorDataSource;
import datalayer.events.DataGetEvent;
import datalayer.events.DataSendToQueryEvent;
import datalayer.events.DataSetEvent;
import datalayer.utils.QueryParameter;
import datalayer.utils.Relation;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class PersonaAtributosDataAccess extends SingletonDataAccess<PersonaAtributo>
{

    private final String insertProcedure = "persona_atributo_insert(?,?,?,?,?)";
    private final Relation noDiseassesBasic;
    private final Relation onlyDiseasesBasic;

    public PersonaAtributosDataAccess(IMediatorDataSource mysource)
    {
        super(mysource, "persona_atributos", "consecutivo", "id_persona",
              "nombre", "es_tipo", "tratamiento", "descripcion");
        setInsertProcedure(insertProcedure);
        noDiseassesBasic = new Relation(String.format("%s as A", getTable()),
                                        new String[]
                                        {
                                            "consecutivo",
                                            "id_persona",
                                            "nombre",
                                            "es_tipo"
                                        });
        onlyDiseasesBasic = new Relation(String.format("%s as B", getTable()),
                                         getColumns());
    }

    //--------------------------------------------------------------------
    public List<PersonaAtributo> retrieveWhioutDiseases(PersonaAtributo consultado)
    {
        return submitQuery(noDiseassesBasic, consultado);
    }

    //--------------------------------------------------------------------
    public List<PersonaAtributo> retrieveWhithDiseases(PersonaAtributo consultado)
    {
        return submitQuery(onlyDiseasesBasic, consultado);
    }

    //--------------------------------------------------------------------
    @Override
    public PersonaAtributo onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
    {
        PersonaAtributo current = new PersonaAtributo();

        for (int i = 1; i <= e.getColumnCount(); i++)
        {
            switch (e.getColumnName(i))
            {
                case "consecutivo":
                    current.setConsecutivo(e.getInt(i));
                    break;
                case "id_persona":
                    current.setIdPersona(e.getInt(i));
                    break;
                case "nombre":
                    current.setNombre(e.getString(i));
                    break;
                case "es_tipo":
                    current.setEsTipo(e.getInt(i));
                    break;
                case "tratamiento":
                    current.setTratamiento(e.getString(i));
                    break;
                case "descripcion":
                    current.setDescripcion(e.getString(i));
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
    protected void onConvertTransfer(PersonaAtributo values, DataSetEvent e) throws SQLException, UnsupportedOperationException
    {
        if (e.getDML() == insertProcedure)
        {
            //e.setInteger("vconsecutivo", values.getConsecutivo());
            e.setInt(1, values.getIdPersona());//"vid_persona"
            e.setString(2, values.getNombre());//"vnombre"
            e.setInt(3, values.getEsTipo());//"ves_tipo"
            e.setString(4, values.getTratamiento(), 255);//"vtratamiento"
            e.setString(5, values.getDescripcion(), 255);//"vdescripcion"
        }
    }

    //--------------------------------------------------------------------    
    @Override
    protected void onConvertKeyResult(DataGetEvent e, PersonaAtributo value) throws SQLException, UnsupportedOperationException
    {
        for (int i = 1; i <= e.getColumnCount(); i++)
        {
            switch (e.getColumnName(i))
            {
                case "consecutivo":
                    value.setConsecutivo(e.getInt(i));
                    break;
                case "id_persona":
                    value.setIdPersona(e.getInt(i));
                    break;
                case "nombre":
                    value.setNombre(e.getString(i));
                    break;
                case "es_tipo":
                    value.setEsTipo(e.getInt(i));
                    break;
                case "tratamiento":
                    value.setTratamiento(e.getString(i));
                    break;
                case "descripcion":
                    value.setDescripcion(e.getString(i));
                    break;
            }
        }
        /*  if (e.getColumnCount() > 0)
            value.setId(e.getInt(1));*/
    }

    //---------------------------------------------------------------------
    @Override
    public void onSendValues(DataSendToQueryEvent e, PersonaAtributo values)
    {
        if (values.getConsecutivo()!= PersonaAtributo.EMPTY_INT)
        {

            if (e.isCreateOperation())
                e.addInteger("consecutivo", values.getConsecutivo());

            else if (e.isUpdateOperation())
            {
                e.addInteger("consecutivo", values.getConsecutivo());
                e.addInteger("consecutivo", QueryParameter.Operator.EQUAL, values.getConsecutivo_Viejo());
            }

            else
                e.addInteger("consecutivo", QueryParameter.Operator.EQUAL, values.getConsecutivo_Viejo());
        }

        if (values.getIdPersona()!= PersonaAtributo.EMPTY_INT)
        {

            if (e.isCreateOperation())
                e.addInteger("id_persona", values.getIdPersona());

            else if (e.isUpdateOperation())
            {
                e.addInteger("id_persona", values.getIdPersona());
                e.addInteger("id_persona", QueryParameter.Operator.EQUAL, values.getIdPersona_Viejo());
            }

            else
                e.addInteger("id_persona", QueryParameter.Operator.EQUAL, values.getIdPersona_Viejo());
        }

        if (values.getNombre() != PersonaAtributo.EMPTY_STRING)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addString("nombre", values.getNombre());

            else if (!values.isSearchOnlyByPrimaryKey())
                e.addString("nombre", QueryParameter.Operator.EQUAL, values.getNombre());
        }

        if (values.getEsTipo() != PersonaAtributo.EMPTY_INT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addInteger("es_tipo", values.getEsTipo());

            else if (!values.isSearchOnlyByPrimaryKey())
            {
                if (e.getTableName().endsWith("A"))
                    e.addInteger("es_tipo", QueryParameter.Operator.LOWER_THAN, values.getEsTipo());

                else if (e.getTableName().endsWith("B"))
                    e.addInteger("es_tipo", QueryParameter.Operator.GREATER_THAN, values.getEsTipo());

                else
                    e.addInteger("es_tipo", QueryParameter.Operator.EQUAL, values.getEsTipo());
            }
        }

        if (values.getTratamiento() != PersonaAtributo.EMPTY_STRING)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addString("tratamiento", values.getTratamiento());

            else if (!values.isSearchOnlyByPrimaryKey())
                e.addString("tratamiento", QueryParameter.Operator.LIKE, String.format("%%%s%%", values.getNombre()));
        }

        if (values.getDescripcion() != PersonaAtributo.EMPTY_STRING)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addString("descripcion", values.getDescripcion());

            else if (!values.isSearchOnlyByPrimaryKey())
                e.addString("descripcion", QueryParameter.Operator.LIKE, String.format("%%%s%%", values.getDescripcion()));
        }
    }
}
