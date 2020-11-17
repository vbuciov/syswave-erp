package com.syswave.datos.miempresa;

import com.syswave.datos.common.SingletonDataAccess;
import com.syswave.entidades.miempresa.PersonaDireccion;
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
public class PersonaDireccionesDataAccess extends SingletonDataAccess<PersonaDireccion>
{

    private final String insertProcedure = "persona_direccion_insert(?,?,?,?,?,?,?)";
    public final String updateProcedure = "persona_direccion_update(?,?,?,?,?,?,?,?,?)";

    public PersonaDireccionesDataAccess(IMediatorDataSource mysource)
    {
        super(mysource, "persona_direcciones", "id_persona", "consecutivo", "codigo_postal",
              "id_localidad", "calle", "colonia", "no_exterior", "no_interior");
        setInsertProcedure(insertProcedure);
        setUpdateProcedure(updateProcedure);
    }

    //---------------------------------------------------------------------
    @Override
    public PersonaDireccion onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
    {
        PersonaDireccion current = new PersonaDireccion();

        for (int i = 1; i <= e.getColumnCount(); i++)
        {
            switch (e.getColumnName(i))
            {
                case "id_persona":
                    current.setIdPersona(e.getInt(i));
                    break;
                case "consecutivo":
                    current.setConsecutivo(e.getInt(i));
                    break;
                case "codigo_postal":
                    current.setCodigoPostal(e.getString(i));
                    break;
                case "id_localidad":
                    current.setIdLocalidad(e.getInt(i));
                    break;
                case "calle":
                    current.setCalle(e.getString(i));
                    break;
                case "colonia":
                    current.setColonia(e.getString(i));
                    break;
                case "no_exterior":
                    current.setNoExterior(e.getString(i));
                    break;
                case "no_interior":
                    current.setNoInterior(e.getString(i));
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
    protected void onConvertTransfer(PersonaDireccion values, DataSetEvent e) throws SQLException, UnsupportedOperationException
    {
        if (e.getDML() == insertProcedure)
        {
            e.setInt(1, values.getIdPersona());//"vid_persona",
            e.setString(2, values.getCodigoPostal(), 15);//"vcodigo_postal"
            e.setInt(3, values.getIdLocalidad());//"vid_localidad"
            e.setString(4, values.getCalle(), 45);//"vcalle"
            e.setString(5, values.getColonia(), 45);//"vcolonia"
            e.setString(6, values.getNoExterior(), 10);//"vno_exterior"
            e.setString(7, values.getNoInterior(), 10);//"vno_interior"
        }

        else if (e.getDML() == updateProcedure)
        {
            e.setInt(1, values.getConsecutivo_Viejo());//"vconsecutivo_old"
            e.setInt(2, values.getIdPersona_Viejo());//"vid_persona_old"
            e.setInt(3, values.getIdPersona_Viejo());//"vid_persona_new"
            e.setString(4, values.getCodigoPostal(), 15);//"vcodigo_postal"
            e.setInt(5, values.getIdLocalidad());//"vid_localidad"
            e.setString(6, values.getCalle(), 45);//"vcalle"
            e.setString(7, values.getColonia(), 45);//"vcolonia"
            e.setString(8, values.getNoExterior(), 10);//"vno_exterior"
            e.setString(9, values.getNoInterior(), 10);//"vno_interior"
        }
    }

    //--------------------------------------------------------------------    
    @Override
    protected void onConvertKeyResult(DataGetEvent e, PersonaDireccion value) throws SQLException, UnsupportedOperationException
    {
        for (int i = 1; i <= e.getColumnCount(); i++)
        {
            switch (e.getColumnName(i))
            {
                case "id_persona":
                    value.setIdPersona(e.getInt(i));
                    break;
                case "consecutivo":
                    value.setConsecutivo(e.getInt(i));
                    break;
                case "codigo_postal":
                    value.setCodigoPostal(e.getString(i));
                    break;
                case "id_localidad":
                    value.setIdLocalidad(e.getInt(i));
                    break;
                case "calle":
                    value.setCalle(e.getString(i));
                    break;
                case "colonia":
                    value.setColonia(e.getString(i));
                    break;
                case "no_exterior":
                    value.setNoExterior(e.getString(i));
                    break;
                case "no_interior":
                    value.setNoInterior(e.getString(i));
                    break;
            }
        }
        /*  if (e.getColumnCount() > 0)
            value.setId(e.getInt(1));*/
    }

    //---------------------------------------------------------------------
    @Override
    public void onSendValues(DataSendToQueryEvent e, PersonaDireccion values)
    {

        if (e.isCreateOperation())
        {
            e.addInteger("id_persona", values.getIdPersona());
            e.addInteger("consecutivo", values.getConsecutivo());
            e.addString("codigo_postal", values.getCodigoPostal(), 15);
            e.addInteger("id_localidad", values.getIdLocalidad());
            e.addString("calle", values.getCalle(), 45);
            e.addString("colonia", values.getColonia(), 45);
            e.addString("no_exterior", values.getNoExterior(), 10);
            e.addString("no_interior", values.getNoInterior(), 10);

        }

        else if (e.isUpdateOperation())
        {
            e.addString("codigo_postal", values.getCodigoPostal(), 15);
            e.addInteger("id_localidad", values.getIdLocalidad());
            e.addString("calle", values.getCalle(), 45);
            e.addString("colonia", values.getColonia(), 45);
            e.addString("no_exterior", values.getNoExterior(), 10);
            e.addString("no_interior", values.getNoInterior(), 10);

            //e.SendParameters.AddInteger ("fk_pers_id", values.Pers_Id)
            e.addInteger("id_persona", QueryParameter.Operator.EQUAL, values.getIdPersona_Viejo());
            e.addInteger("consecutivo", QueryParameter.Operator.EQUAL, values.getConsecutivo_Viejo());
        }

        else if (e.isRetrieveOperation() || e.isDeleteOperation())
        {
            if (values.getIdPersona() != PersonaDireccion.EMPTY_INT)
                e.addInteger("id_persona", QueryParameter.Operator.EQUAL, values.getIdPersona());

            if (values.getConsecutivo() != PersonaDireccion.EMPTY_INT)
                e.addInteger("consecutivo", QueryParameter.Operator.EQUAL, values.getConsecutivo());

            if (!values.isSearchOnlyByPrimaryKey()) //Busca por la llave primaria unicamente.
            {
                if (!values.getCodigoPostal().equals(PersonaDireccion.EMPTY_STRING))
                    e.addString("codigo_postal", QueryParameter.Operator.EQUAL, values.getCodigoPostal());

                if (values.getIdLocalidad() != PersonaDireccion.EMPTY_INT)
                    e.addInteger("id_localidad", QueryParameter.Operator.EQUAL, values.getIdLocalidad());

                if (!values.getCodigoPostal().equals(PersonaDireccion.EMPTY_STRING))
                    e.addString("calle", QueryParameter.Operator.EQUAL, values.getCalle());

                if (!values.getCodigoPostal().equals(PersonaDireccion.EMPTY_STRING))
                    e.addString("colonia", QueryParameter.Operator.EQUAL, values.getColonia());

                if (!values.getCodigoPostal().equals(PersonaDireccion.EMPTY_STRING))
                    e.addString("no_exterior", QueryParameter.Operator.EQUAL, values.getNoExterior());

                if (!values.getCodigoPostal().equals(PersonaDireccion.EMPTY_STRING))
                    e.addString("no_interior", QueryParameter.Operator.EQUAL, values.getNoInterior());
            }
        }
    }
}