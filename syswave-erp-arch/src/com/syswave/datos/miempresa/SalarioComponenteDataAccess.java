package com.syswave.datos.miempresa;

import com.syswave.datos.common.SingletonDataAccess;
import com.syswave.entidades.miempresa.SalarioComponente;
import datalayer.api.IMediatorDataSource;
import datalayer.events.DataGetEvent;
import datalayer.events.DataSendToQueryEvent;
import datalayer.events.DataSetEvent;
import datalayer.utils.QueryParameter;
import java.sql.SQLException;

/**
 *
 * @author sis5
 */
public class SalarioComponenteDataAccess extends SingletonDataAccess<SalarioComponente>
{

    private final String insertProcedure = "salario_componente_insert(?,?,?,?,?,?,?,?,?,?,?)";

    public SalarioComponenteDataAccess(IMediatorDataSource mysource)
    {
        super(mysource, "salario_componentes", "id", "consecutivo_salario",
              "id_persona_salario", "nombre", "cantidad", "es_frecuencia",
              "comentario", "numero_cuenta", "clabe", "proveedor_cuenta",
              "es_tipo_cuenta", "maximo_cuenta");
        setInsertProcedure(insertProcedure);
    }

    @Override
    public SalarioComponente onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
    {
        SalarioComponente current = new SalarioComponente();

        for (int i = 1; i <= e.getColumnCount(); i++)
        {
            switch (e.getColumnName(i))
            {
                case "id":
                    current.setId(e.getInt(i));
                    break;
                case "consecutivo_salario":
                    current.setConsecutivoSalario(e.getInt(i));
                    break;
                case "id_persona_salario":
                    current.setIdPersona(e.getInt(i));
                    break;
                case "nombre":
                    current.setNombre(e.getString(i));
                    break;
                case "cantidad":
                    current.setCantidad(e.getDouble(i));
                    break;
                case "es_frecuencia":
                    current.setEsFrecuencia(e.getInt(i));
                    break;
                case "comentario":
                    current.setComentario(e.getString(i));
                    break;
                case "numero_cuenta":
                    current.setNumeroCuenta(e.getString(i));
                    break;
                case "clabe":
                    current.setClabe(e.getString(i));
                    break;
                case "proveedor_cuenta":
                    current.setProveedorCuenta(e.getString(i));
                    break;
                case "es_tipo_cuenta":
                    current.setEsTipoCuenta(e.getInt(i));
                    break;
                case "maximo_cuenta":
                    current.setMaximoCuenta(e.getDouble(i));
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
    protected void onConvertTransfer(SalarioComponente values, DataSetEvent e) throws SQLException, UnsupportedOperationException
    {
        if (e.getDML() == insertProcedure)
        {
            e.setInt(1, values.getConsecutivoSalario());//"vconsecutivo_salario"
            e.setInt(2, values.getIdPersona());//"vid_persona_salario"
            e.setString(3, values.getNombre());//"vnombre"
            e.setDouble(4, values.getCantidad());//"vcantidad"
            e.setInt(5, values.getEsFrecuencia());//"ves_frecuencia"
            e.setString(6, values.getComentario());//"vcomentario"
            e.setString(7, values.getNumeroCuenta());//"vnumero_cuenta"
            e.setString(8, values.getClabe());//"vclabe"
            e.setString(9, values.getProveedorCuenta());//"vproveedor_cuenta"
            e.setInt(10, values.getEsTipoCuenta());//"ves_tipo_cuenta"
            e.setDouble(11, values.getMaximoCuenta());//"vmaximo_cuenta"
        }
    }

    //--------------------------------------------------------------------    
    @Override
    protected void onConvertKeyResult(DataGetEvent e, SalarioComponente value) throws SQLException, UnsupportedOperationException
    {
        for (int i = 1; i <= e.getColumnCount(); i++)
        {
            switch (e.getColumnName(i))
            {
                case "id":
                    value.setId(e.getInt(i));
                    break;
                case "consecutivo_salario":
                    value.setConsecutivoSalario(e.getInt(i));
                    break;
                case "id_persona_salario":
                    value.setIdPersona(e.getInt(i));
                    break;
                case "nombre":
                    value.setNombre(e.getString(i));
                    break;
                case "cantidad":
                    value.setCantidad(e.getDouble(i));
                    break;
                case "es_frecuencia":
                    value.setEsFrecuencia(e.getInt(i));
                    break;
                case "comentario":
                    value.setComentario(e.getString(i));
                    break;
                case "numero_cuenta":
                    value.setNumeroCuenta(e.getString(i));
                    break;
                case "clabe":
                    value.setClabe(e.getString(i));
                    break;
                case "proveedor_cuenta":
                    value.setProveedorCuenta(e.getString(i));
                    break;
                case "es_tipo_cuenta":
                    value.setEsTipoCuenta(e.getInt(i));
                    break;
                case "maximo_cuenta":
                    value.setMaximoCuenta(e.getDouble(i));
                    break;
            }
        }
        /*  if (e.getColumnCount() > 0)
            value.setId(e.getInt(1));*/
    }

    //---------------------------------------------------------------------
    @Override
    public void onSendValues(DataSendToQueryEvent e, SalarioComponente values)
    {
        if (values.getId() != SalarioComponente.EMPTY_INT)
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

        if (values.getConsecutivoSalario() != SalarioComponente.EMPTY_INT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addInteger("consecutivo_salario", values.getConsecutivoSalario());
            else if (!values.isSearchOnlyByPrimaryKey())
                e.addInteger("consecutivo_salario", QueryParameter.Operator.EQUAL, values.getConsecutivoSalario());
        }

        if (values.getIdPersona() != SalarioComponente.EMPTY_INT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addInteger("id_persona_salario", values.getIdPersona());
            else if (!values.isSearchOnlyByPrimaryKey())
                e.addInteger("id_persona_salario", QueryParameter.Operator.EQUAL, values.getIdPersona());
        }

        if (values.getNombre() != SalarioComponente.EMPTY_STRING)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addString("nombre", values.getNombre());
            else if (!values.isSearchOnlyByPrimaryKey())
                e.addString("nombre", QueryParameter.Operator.EQUAL, values.getNombre());
        }

        if (values.getCantidad() != SalarioComponente.EMPTY_DOUBLE)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addDouble("cantidad", values.getCantidad());
            else if (!values.isSearchOnlyByPrimaryKey())
                e.addDouble("cantidad", QueryParameter.Operator.EQUAL, values.getCantidad());
        }

        if (values.getEsFrecuencia() != SalarioComponente.EMPTY_INT)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addInteger("es_frecuencia", values.getEsFrecuencia());
            else if (!values.isSearchOnlyByPrimaryKey())
                e.addInteger("es_frecuencia", QueryParameter.Operator.EQUAL, values.getEsFrecuencia());
        }

        if (values.getComentario() != SalarioComponente.EMPTY_STRING)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addString("comentario", values.getComentario());
            else if (!values.isSearchOnlyByPrimaryKey())
                e.addString("comentario", QueryParameter.Operator.EQUAL, values.getComentario());
        }

        if (values.getNumeroCuenta() != SalarioComponente.EMPTY_STRING)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addString("numero_cuenta", values.getNumeroCuenta());
            else if (!values.isSearchOnlyByPrimaryKey())
                e.addString("numero_cuenta", QueryParameter.Operator.EQUAL, values.getNumeroCuenta());
        }

        if (values.getClabe() != SalarioComponente.EMPTY_STRING)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addString("clabe", values.getClabe());
            else if (!values.isSearchOnlyByPrimaryKey())
                e.addString("clabe", QueryParameter.Operator.EQUAL, values.getClabe());
        }

        if (values.getProveedorCuenta() != SalarioComponente.EMPTY_STRING)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addString("proveedor_cuenta", values.getProveedorCuenta());
            else if (!values.isSearchOnlyByPrimaryKey())
                e.addString("proveedor_cuenta", QueryParameter.Operator.EQUAL, values.getProveedorCuenta());
        }

        if (values.getMaximoCuenta() != SalarioComponente.EMPTY_DOUBLE)
        {
            if (e.isCreateOperation() || e.isUpdateOperation())
                e.addDouble("maximo_cuenta", values.getMaximoCuenta());
            else if (!values.isSearchOnlyByPrimaryKey())
                e.addDouble("maximo_cuenta", QueryParameter.Operator.EQUAL, values.getMaximoCuenta());
        }
    }
}
