package com.syswave.datos.miempresa;

import com.syswave.datos.common.SingletonDataAccess;
import com.syswave.entidades.miempresa.PersonaFoto;
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
public class PersonaFotosDataAccess extends SingletonDataAccess<PersonaFoto>
{

    private final String insertProcedure = "persona_foto_insert(?,?,?,?,?,?,?,?)";
    private final Relation shortBasic;

    public PersonaFotosDataAccess(IMediatorDataSource mysource)
    {
        super(mysource, "persona_fotos", "consecutivo", "id_persona", "ancho", "alto",
              "formato", "longitud", "imagen", "miniatura", "observacion");
        setInsertProcedure(insertProcedure);
        shortBasic = new Relation(getTable(),
                                  new String[]
                                  {
                                      "consecutivo",
                                      "id_persona",
                                      "ancho",
                                      "alto",
                                      "formato",
                                      "longitud",
                                      "miniatura",
                                      "observacion"
                                  });
    }

    //--------------------------------------------------------------------
    public List<PersonaFoto> retrieveThumbnail()
    {
        return submitQuery(shortBasic);
    }

    //--------------------------------------------------------------------
    public List<PersonaFoto> retrieveThumbnail(PersonaFoto consultado)
    {
        return submitQuery(shortBasic, consultado);
    }

    //--------------------------------------------------------------------
    @Override
    public PersonaFoto onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
    {
        PersonaFoto current = new PersonaFoto();

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
                case "ancho":
                    current.setAcho(e.getInt(i));
                    break;
                case "alto":
                    current.setAlto(e.getInt(i));
                    break;
                case "formato":
                    current.setFormato(e.getString(i));
                    break;
                case "longitud":
                    current.setLongitud(e.getInt(i));
                    break;
                case "imagen":
                    current.setImagen(e.getBytes(i));
                    break;
                case "miniatura":
                    current.setMiniatura(e.getBytes(i));
                    break;
                case "observacion":
                    current.setObservacion(e.getString(i));
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
    protected void onConvertTransfer(PersonaFoto values, DataSetEvent e) throws SQLException, UnsupportedOperationException
    {
        if (e.getDML() == insertProcedure)
        {
            //e.setInteger("vconsecutivo", values.getConsecutivo());
            e.setInt(1, values.getIdPersona());//"vid_persona"
            e.setInt(2, values.getAcho());//"vancho"
            e.setInt(3, values.getAlto());//"valto"
            e.setString(4, values.getFormato());//"vformato"
            e.setInt(5, values.getLongitud());//"vlongitud"
            e.setBytes(6, values.getImagen());//"vimagen"
            e.setBytes(7, values.getMiniatura());//"vminiatura"
            e.setString(8, values.getObservacion());//"vobservacion"
        }
    }

    //--------------------------------------------------------------------    
    @Override
    protected void onConvertKeyResult(DataGetEvent e, PersonaFoto value) throws SQLException, UnsupportedOperationException
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
                case "ancho":
                    value.setAcho(e.getInt(i));
                    break;
                case "alto":
                    value.setAlto(e.getInt(i));
                    break;
                case "formato":
                    value.setFormato(e.getString(i));
                    break;
                case "longitud":
                    value.setLongitud(e.getInt(i));
                    break;
                case "imagen":
                    value.setImagen(e.getBytes(i));
                    break;
                case "miniatura":
                    value.setMiniatura(e.getBytes(i));
                    break;
                case "observacion":
                    value.setObservacion(e.getString(i));
                    break;
            }
        }
        /*  if (e.getColumnCount() > 0)
            value.setId(e.getInt(1));*/
    }

    //---------------------------------------------------------------------
    @Override
    public void onSendValues(DataSendToQueryEvent e, PersonaFoto values)
    {
        if (values.getConsecutivo() != PersonaFoto.EMPTY_INT)
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

        if (values.getIdPersona() != PersonaFoto.EMPTY_INT)
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

        if (values.getAcho() != PersonaFoto.EMPTY_INT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addInteger("ancho", values.getAcho());

            else if (!values.isSearchOnlyByPrimaryKey())
                e.addInteger("ancho", QueryParameter.Operator.EQUAL, values.getAcho());
        }

        if (values.getAlto() != PersonaFoto.EMPTY_INT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addInteger("alto", values.getAlto());

            else if (!values.isSearchOnlyByPrimaryKey())
                e.addInteger("alto", QueryParameter.Operator.EQUAL, values.getAlto());
        }

        if (values.getFormato() != PersonaFoto.EMPTY_STRING)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addString("formato", values.getFormato());

            else if (!values.isSearchOnlyByPrimaryKey())
                e.addString("formato", QueryParameter.Operator.LIKE, values.getFormato());
        }

        if (values.getLongitud() != PersonaFoto.EMPTY_INT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addInteger("longitud", values.getLongitud());

            else if (!values.isSearchOnlyByPrimaryKey())
                e.addInteger("longitud", QueryParameter.Operator.EQUAL, values.getLongitud());
        }

        if (values.getImagen() != null)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addBytes("imagen", values.getImagen());
        }

        if (values.getMiniatura() != null)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addBytes("miniatura", values.getMiniatura());
        }

        if (values.getObservacion() != PersonaFoto.EMPTY_STRING)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addString("observacion", values.getObservacion());

            else if (!values.isSearchOnlyByPrimaryKey())
                e.addString("observacion", QueryParameter.Operator.LIKE, values.getObservacion());
        }
    }
}
