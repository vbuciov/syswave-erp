package com.syswave.entidades.miempresa;

import com.syswave.entidades.keys.PrimaryKeyById;
import java.io.Serializable;

/**
 * Es un multivaluado para diversas tablas.
 * @author Victor Manuel Bucio Vargas
 * @version 28 febrero 2014
 */
public class Valor extends PrimaryKeyById implements Serializable
{
   private String valor, seccion, descripcion, formato;
   private Boolean activo;
      
   //---------------------------------------------------------------------   
   private void initAtributes ()
   {   
      valor = EMPTY_STRING;
      seccion = EMPTY_STRING;
      descripcion = EMPTY_STRING;
      formato = EMPTY_STRING;
      activo = true;
   }

   //---------------------------------------------------------------------
   private void assign(Valor that)
   {
       super.assign(that);
      valor = that.valor;
      seccion = that.seccion;
      descripcion = that.descripcion;
      formato = that.formato;
      activo = that.activo;      
   }
   
    //---------------------------------------------------------------------
   public Valor ()
   {
      super();
      initAtributes();
   }
   
   //---------------------------------------------------------------------
   public Valor (int id, String descripcion)
   {
      super();
      initAtributes();
      super.setId(id);
      this.descripcion = descripcion;
   }
   
   //---------------------------------------------------------------------
   public Valor (Valor that)
   {
      super();
      assign(that);
   }

   //---------------------------------------------------------------------
   public String getValor()
   {
      return valor;
   }

   //---------------------------------------------------------------------
   public void setValor(String valor)
   {
      this.valor = valor;
   }

   //---------------------------------------------------------------------
   public String getSeccion()
   {
      return seccion;
   }

   //---------------------------------------------------------------------
   public void setSeccion(String seccion)
   {
      this.seccion = seccion;
   }
   
   //---------------------------------------------------------------------
   public String getDescripcion()
   {
      return descripcion;
   }

   //---------------------------------------------------------------------
   public void setDescripcion(String descripcion)
   {
      this.descripcion = descripcion;
   }
   
   //---------------------------------------------------------------------
   public void setFormato(String value)
   {
      formato = value;
   }
   
   //---------------------------------------------------------------------
   public String getFormato ()
   {
      return formato;
   }

   //---------------------------------------------------------------------
   public Boolean esActivo()
   {
      return activo;
   }

   //---------------------------------------------------------------------
   public void setActivo(Boolean activo)
   {
      this.activo = activo;
   }
   
   //---------------------------------------------------------------------
   @Override
   public void clear()
   {
      initAtributes();
   }
   
   //---------------------------------------------------------------------
   public void copy (Valor that)
   {
      assign(that);
   }  
}