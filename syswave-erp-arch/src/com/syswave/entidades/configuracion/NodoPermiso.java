package com.syswave.entidades.configuracion;

import com.syswave.entidades.common.IEntidadRecursiva;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class NodoPermiso extends Usuario_tiene_Permiso implements IEntidadRecursiva
{
   int id_padre, nivel;
   String valor;
   boolean existe;

   //---------------------------------------------------------------------
   public NodoPermiso ()
   {
      existe = false;
      valor = EMPTY_STRING;
      id_padre = EMPTY_INT;
      nivel = EMPTY_INT;
   }
   
   //---------------------------------------------------------------------
   public String getValor ()
   {
      return valor;
   }
   
   //---------------------------------------------------------------------
   public void setValor (String value)
   {
     valor = value;
   }

   //---------------------------------------------------------------------
   @Override
   public Integer getIdPadre()
   {
      return id_padre;
   }
   
   //---------------------------------------------------------------------
   public void setIdPadre (Integer value)
   {
      id_padre = value;
   }

   //---------------------------------------------------------------------
   @Override
   public Integer getNivel()
   {
      return nivel;
   }
   
   //---------------------------------------------------------------------
   public void setNivel (Integer value)
   {
      nivel = value;   
   }

   //---------------------------------------------------------------------
   public void setExiste(boolean aBoolean)
   {
      existe = aBoolean;
   }
   
   //---------------------------------------------------------------------
   public boolean esExiste ()
   {
      return existe;
   }

 
}