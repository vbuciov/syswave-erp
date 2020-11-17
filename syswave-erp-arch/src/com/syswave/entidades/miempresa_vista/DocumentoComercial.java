package com.syswave.entidades.miempresa_vista;

import com.syswave.entidades.miempresa.Documento;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class DocumentoComercial extends Documento
{
   private Integer id_receptor, id_emisor;
   private String receptor, condiciones;
   
   //---------------------------------------------------------------------
   private void initAtributes ()
   {
      receptor = EMPTY_STRING;
      condiciones = EMPTY_STRING;
      id_receptor = EMPTY_INT;
      id_emisor = EMPTY_INT;
   }
   
   //---------------------------------------------------------------------
   public DocumentoComercial()
   {
      super();
      initAtributes ();
   }

   //---------------------------------------------------------------------
   public String getReceptor()
   {
      return receptor;
   }

   //---------------------------------------------------------------------
   public void setReceptor(String receptor)
   {
      this.receptor = receptor;
   }

   //---------------------------------------------------------------------
   public String getCondiciones()
   {
      return condiciones;
   }

   //---------------------------------------------------------------------
   public void setCondiciones(String condiciones)
   {
      this.condiciones = condiciones;
   }

   //---------------------------------------------------------------------
   public Integer getIdReceptor()
   {
      return id_receptor;
   }

   //---------------------------------------------------------------------
   public void setIdReceptor(Integer id_receptor)
   {
      this.id_receptor = id_receptor;
   }

   //---------------------------------------------------------------------
   public Integer getIdEmisor()
   {
      return id_emisor;
   }

   //---------------------------------------------------------------------
   public void setIdEmisor(Integer id_emisor)
   {
      this.id_emisor = id_emisor;
   }
   
   //---------------------------------------------------------------------
   public void Clear ()
   {
      super.clear();
      initAtributes();
   }
   
   //---------------------------------------------------------------------
   public void Copy (DocumentoComercial that)
   {
      super.copy(that);
      receptor = that.receptor;
      condiciones = that.condiciones;
      id_receptor = that.id_receptor;
      id_emisor = that.id_emisor;
   }  
}