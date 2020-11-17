/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.syswave.entidades.keys;

import com.syswave.entidades.common.Entidad;
import static com.syswave.entidades.common.Entidad.EMPTY_STRING;
import java.io.Serializable;

/**
 *
 * @author victor
 */
public class PrimaryKeyByIdentificador extends Entidad implements Serializable
{
   private String[] identificador; //Todas las llaves primarias se controlan así.
   
   //---------------------------------------------------------------------
   private void createAtributes ()
   {
      identificador = new String[Entidad.Atributo.values().length];
   }
   
   //---------------------------------------------------------------------
   private void initAtributes ()
   {
      for (int i = 0; i < identificador.length; i++)
         identificador[i]  = EMPTY_STRING;
   }
   
   //---------------------------------------------------------------------
   protected void assign(PrimaryKeyByIdentificador that)
   {
       setIdentificador(that.identificador[Entidad.Atributo.Actual.ordinal()]);
   }
   
   //---------------------------------------------------------------------
   public PrimaryKeyByIdentificador()
   {
      createAtributes ();
      initAtributes();
   }
   
   //---------------------------------------------------------------------
   /**
    * Este constructor sirve para realizar busquedas por llave en instancias anonimas
    */
   public PrimaryKeyByIdentificador (String identificador)
   {
       createAtributes ();
       this.identificador[Entidad.Atributo.Actual.ordinal()] = identificador;
   }
   
   //---------------------------------------------------------------------
   /**
    * Obtiene el identificador asignado al usuario.
    * @return identificador
    */

   public String getIdentificador()
   {
      return identificador[Entidad.Atributo.Actual.ordinal()];
   }
   
      //---------------------------------------------------------------------
   /**
    * Obtiene el identificador asignado al usuario.
    * @return identificador
    */
   public String getIdentificador_Viejo()
   {
      //return llave[Atributo.Viejo.ordinal()] == EMPTY_STRING?llave[Atributo.Actual.ordinal()]:llave[Atributo.Viejo.ordinal()];
        if (isModified())
         return identificador[Entidad.Atributo.Viejo.ordinal()];
      else
         return identificador[Entidad.Atributo.Actual.ordinal()];
   }


   //---------------------------------------------------------------------
   /**
    * Estable un valor que sirve para identificar de forma única al usuario.
    * @param value Es el valor que se asignara
    */
   public void setIdentificador(String value)
   {
      //Nota: Revisamos si esta apuntando a la dirección del valor vacio
      //if (this.llave[Atributo.Viejo.ordinal()] == EMPTY_STRING)
      if (isWaiting())
         this.identificador[Entidad.Atributo.Viejo.ordinal()] = this.identificador[Entidad.Atributo.Actual.ordinal()];
      this.identificador[Entidad.Atributo.Actual.ordinal()] = value;
   }

   //---------------------------------------------------------------------
   @Override
   public void clear()
   {
      initAtributes();
   }  

   //---------------------------------------------------------------------
   public void copy (PrimaryKeyByIdentificador that)
   {
      assign(that);
   }
}
