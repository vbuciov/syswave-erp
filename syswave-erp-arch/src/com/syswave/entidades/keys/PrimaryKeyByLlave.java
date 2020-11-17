package com.syswave.entidades.keys;

import com.syswave.entidades.common.Entidad;
import java.io.Serializable;

/**
 * Esta clase base representan elementos Llave y Valor
 * @author Victor Manuel Bucio Vargas
 * @version 25 febrero 2014
 */
public abstract class PrimaryKeyByLlave extends Entidad implements Serializable
{
   private String[] llave; //Todas las llaves primarias se controlan así.
   
   //---------------------------------------------------------------------
   private void createAtributes ()
   {
      llave = new String[Atributo.values().length];
   }
   
   //---------------------------------------------------------------------
   private void initAtributes ()
   {
      for (int i = 0; i < llave.length; i++)
         llave[i]  = EMPTY_STRING;
   }
   
   //---------------------------------------------------------------------
   protected void assign(PrimaryKeyByLlave that)
   {
       setLlave(that.llave[Atributo.Actual.ordinal()]);
   }
   
   //---------------------------------------------------------------------
   public PrimaryKeyByLlave()
   {
      createAtributes ();
      initAtributes();
   }
   
   //---------------------------------------------------------------------
   /**
    * Este constructor sirve para realizar busquedas por llave en instancias anonimas
    * @param llave La seccion con la que esta relacionada esta instancia
    */
   public PrimaryKeyByLlave (String llave)
   {
       createAtributes ();
       this.llave[Atributo.Actual.ordinal()] = llave;
   }
   
   //---------------------------------------------------------------------
   /**
    * Obtiene el valor del campo llave
    * @return llave
    */
   public String getLlave()
   {
      return llave[Atributo.Actual.ordinal()];
   }
   
      //---------------------------------------------------------------------
   /**
    * Obtiene el valor del campo llave
    * @return llave
    */
   public String getLlave_Viejo()
   {
      //return llave[Atributo.Viejo.ordinal()] == EMPTY_STRING?llave[Atributo.Actual.ordinal()]:llave[Atributo.Viejo.ordinal()];
        if (isModified())
         return llave[Atributo.Viejo.ordinal()];
      else
         return llave[Atributo.Actual.ordinal()];
   }


   //---------------------------------------------------------------------
   /**
    * Establece el valor del campo llave
    * @param value llave
    */
   public void setLlave(String value)
   {
      //Nota: Revisamos si esta apuntando a la dirección del valor vacio
      //if (this.llave[Atributo.Viejo.ordinal()] == EMPTY_STRING)
      if (isWaiting())
         this.llave[Atributo.Viejo.ordinal()] = this.llave[Atributo.Actual.ordinal()];
      this.llave[Atributo.Actual.ordinal()] = value;
   }

   //---------------------------------------------------------------------
   @Override
   public void clear()
   {
      initAtributes();
   }  

   //---------------------------------------------------------------------
   public void copy (PrimaryKeyByLlave that)
   {
      assign(that);
   }
}