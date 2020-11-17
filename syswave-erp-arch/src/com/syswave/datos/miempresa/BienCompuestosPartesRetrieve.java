package com.syswave.datos.miempresa;

import com.syswave.datos.common.SingletonDataAccess;
import com.syswave.entidades.miempresa.BienCompuesto;
import com.syswave.entidades.miempresa_vista.BienCompuestoVista;
import datalayer.api.IMediatorDataSource;
import datalayer.events.DataGetEvent;
import datalayer.events.DataSendToQueryEvent;
import datalayer.utils.Join;
import datalayer.utils.ProductRelation;
import datalayer.utils.QueryParameter;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class BienCompuestosPartesRetrieve extends SingletonDataAccess<BienCompuestoVista> 
{
    public final String nombreTabla2 = "bien_variantes AS bv";
    public final String nombreTabla3 = "configuracion.unidades AS u";
    ProductRelation CompuestoParte;

    //---------------------------------------------------------------------
    public BienCompuestosPartesRetrieve(IMediatorDataSource mysource)
    {
        super(mysource, "bien_compuestos", "id_bien_formal", "id_bien_parte", "cantidad");
        setWithAutoID(false);
        CompuestoParte = new ProductRelation(getTable(), 
                             new Join[]{new Join (nombreTabla2, "bv.id = id_bien_parte"),
                             new Join (nombreTabla3, "u.id = id_unidad_masa")},
                             new String[]{"id_bien_formal", 
                                          "id_bien_parte",
                                          "cantidad",
                                          "u.nombre"});
    }
   
   //---------------------------------------------------------------------
    @Override
   public List<BienCompuestoVista> Retrieve()
   {
      return submitQuery(CompuestoParte);
   }
   
   //---------------------------------------------------------------------
    @Override
   public List<BienCompuestoVista> Retrieve(BienCompuestoVista filter)
   {
      return submitQuery(CompuestoParte, filter);
   }

   //---------------------------------------------------------------------
    @Override
    public BienCompuestoVista onConvertResultRow(DataGetEvent e) throws SQLException, UnsupportedOperationException
    {
      BienCompuestoVista current = new BienCompuestoVista();

      for (int i = 1; i <= e.getColumnCount(); i++)
      {
          switch (e.getColumnName(i))
          {
              case "id_bien_formal":
                  current.setIdBienFormal(e.getInt(i));
                  break;
              case "id_bien_parte":
                  current.setIdBienParte(e.getInt(i));
                  break;
              case "cantidad":
                  current.setCantidad(e.getFloat(i));
                  break;
              case "nombre":
                  current.setNombreUnidad(e.getString(i));
                  break;
          }
      }
   
      current.setSearchOnlyByPrimaryKey(true);
      current.acceptChanges();

      return current;
   }

    //---------------------------------------------------------------------
    @Override
    public void onSendValues(DataSendToQueryEvent e, BienCompuestoVista argument)
    {
      if (e.isRetrieveOperation())
      {
         if (argument.getIdBienFormal() != BienCompuesto.EMPTY_INT)
            e.addInteger("id_bien_formal", QueryParameter.Operator.EQUAL, argument.getIdBienFormal_Viejo());
         
         if ( argument.getIdBienParte() != BienCompuesto.EMPTY_INT)
            e.addInteger("id_bien_parte", QueryParameter.Operator.EQUAL, argument.getIdBienParte_Viejo());
     
         if (argument.getCantidad() != BienCompuesto.EMPTY_FLOAT)
            e.addFloat("cantidad", QueryParameter.Operator.EQUAL, argument.getCantidad());
      }
   }
}
