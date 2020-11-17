package com.syswave.entidades.miempresa_vista;

import com.syswave.entidades.miempresa.BienCompuesto;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class BienCompuestoVista extends BienCompuesto
{
   //private BienVariante fk_compuesto_parte, fk_compuesto_formal; 
   private String unidad, descripcion;
    
   //---------------------------------------------------------------------
   public BienCompuestoVista ()
   {
      super();
      //fk_compuesto_parte = navigation.joinBienVarianteCompuestoParte(EMPTY_INT);
      //fk_compuesto_formal = navigation.joinBienVarianteCompuestoFormal(EMPTY_INT);
      unidad = EMPTY_STRING;
   }
   
   //---------------------------------------------------------------------
   public BienCompuestoVista(BienCompuestoVista that)
   {
      super(that);
      //fk_compuesto_parte = navigation.joinBienVarianteCompuestoParte(EMPTY_INT);
      //fk_compuesto_formal = navigation.joinBienVarianteCompuestoFormal(EMPTY_INT);
      unidad = EMPTY_STRING;      
   }
   
   //---------------------------------------------------------------------
   public String getDescripcion ()
   {
      return descripcion;
   }
   
   //---------------------------------------------------------------------
   public void setDescripcion (String value)
   {
      descripcion = value;
   }
   
   //---------------------------------------------------------------------
   public void setNombreUnidad (String value)
   {
      unidad = value;
   }
   
   //---------------------------------------------------------------------
   public String getNombreUnidad()
   {
      return unidad;
   }
}