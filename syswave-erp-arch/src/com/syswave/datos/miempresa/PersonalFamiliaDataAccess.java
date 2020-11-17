package com.syswave.datos.miempresa;

import com.syswave.datos.common.SingletonDataAccess;
import com.syswave.entidades.miempresa_vista.PersonalTieneFamiliar;
import datalayer.api.IMediatorDataSource;
import datalayer.events.DataGetEvent;
import datalayer.events.DataSetEvent;
import datalayer.utils.QueryDataTransfer;
import java.sql.SQLException;
import java.util.List;

/**
 * Traduce un objeto Persona Tiene Familia, a persona_relaciones
 * TODO: Revisar todos los métodos porque quedo inestable este modulo.
 * @author Carlos Daniel Soto Hernández
 */
public class PersonalFamiliaDataAccess extends SingletonDataAccess<PersonalTieneFamiliar>
{
   public final String selectProcedure = "personal_familia_select";
   public final String insertProcedure = "personal_familia_insert";
   public final String deleteFunction = "personal_familia_delete";
   public final String updateProcedure = "personal_familia_update";

    public PersonalFamiliaDataAccess(IMediatorDataSource mysource)
    {
        super(mysource, "persona_relaciones", "id_persona_a", "id_persona_b", "es_tipo" );
        setInsertProcedure(insertProcedure);
        setUpdateProcedure(updateProcedure);
    }

   //---------------------------------------------------------------------
   @Override
   public int Delete(PersonalTieneFamiliar elemento)
   {
      //return Delete(nombreTabla, elemento, this);
      QueryDataTransfer parametros = new QueryDataTransfer(deleteFunction);
      parametros.addInteger("vid_founded", elemento.getIdFounded_Viejo());
      parametros.addInteger("vid_searched", elemento.getIdSearched_Viejo());
      parametros.addInteger("vprototype", elemento.getPrototype_Viejo());
      int affectado = (int) executeFunction(parametros);

      if (affectado > 0)
         elemento.setDeleted();

      return affectado;
   }

   //---------------------------------------------------------------------
   @Override
   public int Delete(List<PersonalTieneFamiliar> elemento)
   {
      //return Delete(nombreTabla, elemento, this);
      QueryDataTransfer parametros = new QueryDataTransfer(deleteFunction);
      int affectado = 0, e;

      for (PersonalTieneFamiliar cada : elemento)
      {
         parametros.addInteger("vid_founded", cada.getIdFounded_Viejo());
         parametros.addInteger("vid_searched", cada.getIdSearched_Viejo());
         parametros.addInteger("vprototype", cada.getPrototype_Viejo());
         e = (int) executeFunction(parametros);
         affectado += e;
         parametros.clear();

         if (e > 0)
            cada.setDeleted();
      }

      return affectado;
   }

   //---------------------------------------------------------------------
   @Override
   public List<PersonalTieneFamiliar> Retrieve(PersonalTieneFamiliar elemento)
   {
      QueryDataTransfer parametros = new QueryDataTransfer(selectProcedure);
      parametros.addInteger("vid_searched", elemento.getIdSearched());
      return executeReadStoredProcedure(parametros);
   }

   //---------------------------------------------------------------------
   @Override
    public PersonalTieneFamiliar onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
    {
      PersonalTieneFamiliar nuevo = new PersonalTieneFamiliar();

      for (int i = 1; i <= e.getColumnCount(); i++)
      {
          switch (e.getColumnName(i))
          {
              case "id_founded":
                  nuevo.setIdFounded(e.getInt(i));
                  break;
              case "id_searched":
                  nuevo.setIdSearched(e.getInt(i));
                  break;
              case "prototype":
                  nuevo.setPrototype(e.getInt(i));
                  break;
          }
      }

      nuevo.setSearchOnlyByPrimaryKey(true);
      nuevo.acceptChanges();

      return nuevo;
   }
    
        //---------------------------------------------------------------------
    /**
     * InsertProcedure is using an executeUpdateStoredProcedure
     *
     * @throws java.sql.SQLException
     */
    @Override
    protected void onConvertTransfer(PersonalTieneFamiliar values, DataSetEvent e) throws SQLException, UnsupportedOperationException
    {
        if (e.getDML() == insertProcedure)
        {
         e.setInt(1, values.getIdFounded());//"vid_founded"
         e.setInt(2, values.getIdSearched());//"vid_searched"
         e.setInt(3, values.getPrototype());//"vprototype"
        }

        else if (e.getDML() == updateProcedure)
        {
         e.setInt(1, values.getIdFounded());//"vid_founded_new"
         e.setInt(2, values.getIdFounded());//"vid_founded_old"
         e.setInt(3, values.getIdSearched());//"vid_searched_new"
         e.setInt(4, values.getIdSearched());//"vid_searched_old"
         e.setInt(5, values.getPrototype());//"vprototype_new"
         e.setInt(6, values.getPrototype());//"vprototype_old"
        }
    }

    //--------------------------------------------------------------------    
    @Override
    protected void onConvertKeyResult(DataGetEvent e, PersonalTieneFamiliar value) throws SQLException, UnsupportedOperationException
    {
        for (int i = 1; i <= e.getColumnCount(); i++)
        {
          switch (e.getColumnName(i))
          {
              case "id_founded":
                  value.setIdFounded(e.getInt(i));
                  break;
              case "id_searched":
                  value.setIdSearched(e.getInt(i));
                  break;
              case "prototype":
                  value.setPrototype(e.getInt(i));
                  break;
          }
        }
        /*  if (e.getColumnCount() > 0)
            value.setId(e.getInt(1));*/
    }
}