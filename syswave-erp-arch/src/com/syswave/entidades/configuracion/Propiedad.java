package com.syswave.entidades.configuracion;

import com.syswave.entidades.keys.CompositeKeyBySeccionHash;
import java.io.Serializable;


/**
 * Son relaciona llave y valor, para cuestiones diversas
 * @author Victor Manuel Bucio Vargas
 * @version 25 febrero 2014
 */
public class Propiedad extends CompositeKeyBySeccionHash implements Serializable
{
   //---------------------------------------------------------------------
   public Propiedad()
   {
      super();
   }
   
   //---------------------------------------------------------------------
   public Propiedad(Propiedad that)
   {
      super();
      super.assign(that);
   }

   //---------------------------------------------------------------------
    /**
    * Este constructor sirve para realizar busquedas por seccion en instancias anonimas
    * @param seccion La seccion con la que esta relacionada esta instancia
    */
   public Propiedad(String seccion)
   {
      super();
      super.setSeccion(seccion);
   }

   //---------------------------------------------------------------------
    /**
    * Este constructor sirve para realizar busquedas por seccion y llave en instancias anonimas
    * @param seccion La seccion con la que esta relacionada esta instancia
    * @param llave La llave con la que esta relacionada esta instancia
    */
   public Propiedad(String seccion, String llave)
   {
      super(llave);
      super.setSeccion(seccion);
   }
         
   //---------------------------------------------------------------------
   /**
    * Copia los valores del objeto recibido en esta instancia
    * @param that El objeto del cual se tomaran los valores.
    */
   public void copy (Propiedad that)
   {
      super.copy(that);
   }
}