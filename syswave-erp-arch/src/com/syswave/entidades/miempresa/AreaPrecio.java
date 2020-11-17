package com.syswave.entidades.miempresa;

import com.syswave.entidades.common.IEntidadRecursiva;
import com.syswave.entidades.keys.PrimaryKeyById;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class AreaPrecio extends PrimaryKeyById implements IEntidadRecursiva
{
   private String descripcion;
   private int id_padre, nivel, es_tipo;
   private boolean es_costo_directo, es_costo_variable;
   
   //----------------------------------------------------------------------
   public AreaPrecio ()
   {
      super();
      initAtributes ();
   }
   
   //----------------------------------------------------------------------
   public AreaPrecio (AreaPrecio that)
   {
      super();
      assign(that);
   }
   
   //----------------------------------------------------------------------
   private void assign (AreaPrecio that)
   {
      super.assign(that);
      descripcion = that.descripcion;
      id_padre = that.id_padre;
      nivel = that.nivel;
      es_costo_directo = that.es_costo_directo;
      es_costo_variable = that.es_costo_variable;
      es_tipo = that.es_tipo;
   }
   
   
   //----------------------------------------------------------------------
   private void initAtributes ()
   {      
      descripcion = EMPTY_STRING;
      id_padre = EMPTY_INT;
      nivel = EMPTY_INT;
      es_costo_directo = true;
      es_costo_variable = false;
      es_tipo = EMPTY_INT;
   }
   
 
   //----------------------------------------------------------------------
   public String getDescripcion()
   {
      return descripcion;
   }

   //----------------------------------------------------------------------
   public void setDescripcion(String value)
   {
      this.descripcion = value;
   }

   //----------------------------------------------------------------------
   @Override
   public Integer getIdPadre()
   {
      return id_padre;
   }

   //----------------------------------------------------------------------
   public void setIdPadre(int value)
   {
      this.id_padre = value;
   }
   
   //----------------------------------------------------------------------
   @Override
   public Integer getNivel()
   {
      return nivel;
   }
   
   //----------------------------------------------------------------------
   public void setNivel(int value)
   {
      this.nivel = value;
   }

   //----------------------------------------------------------------------
   public boolean esCostoDirecto()
   {
      return es_costo_directo;
   }
   
    //----------------------------------------------------------------------
   public boolean esCostoInDirecto()
   {
      return !es_costo_directo;
   }

   //----------------------------------------------------------------------
   public void setCostoDirecto(boolean value)
   {
      this.es_costo_directo = value;
   }
   
       //----------------------------------------------------------------------
    public boolean esCostoVariable()
    {
        return es_costo_variable;
    }

    //----------------------------------------------------------------------
    public void setEsCostoVariable(boolean value)
    {
        this.es_costo_variable = value;
    }

   //----------------------------------------------------------------------
    public int getEsTipo()
    {
        return es_tipo;
    }

    //----------------------------------------------------------------------
    public void setEsTipo(int value)
    {
        this.es_tipo = value;
    }
     
   //----------------------------------------------------------------------
   @Override
   public void clear()
   {
     initAtributes ();
   }
   
   //---------------------------------------------------------------------
   public void copy (AreaPrecio that)
   {
      assign(that);
   }
    
}
