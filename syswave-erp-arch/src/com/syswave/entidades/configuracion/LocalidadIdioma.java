package com.syswave.entidades.configuracion;

import com.syswave.entidades.common.Entidad;
import java.io.Serializable;

/**
 * Es una localicación geografica
 * @author Victor Manuel Bucio Vargas
 * @version 26 febrero 2014
 */
public class LocalidadIdioma extends Entidad implements Serializable
{
   private String[] idioma; //Todas las llaves primarias se manejan así.
   private String descripcion;
   protected Localidad fk_localidad_idioma;
   
   boolean fk_localidad_idioma_related;
   
   //---------------------------------------------------------------------
   private void createAtributes ()
   {
      idioma = new String[Atributo.values().length];
      fk_localidad_idioma = new Localidad();
       fk_localidad_idioma_related = false;
   }
   
   //---------------------------------------------------------------------
   private void initAtributes ()
   {
      for (int i = 0; i < idioma.length; i++)
      {
         idioma[i] = EMPTY_STRING;
      }
      descripcion = EMPTY_STRING;
   }

   //---------------------------------------------------------------------
   private void asign(LocalidadIdioma that)
   {
      setIdioma(that.idioma[Atributo.Actual.ordinal()]);
      //idioma[Atributo.Actual.ordinal()] = that.idioma[Atributo.Actual.ordinal()];
      descripcion = that.descripcion;
      if (!fk_localidad_idioma_related)
         fk_localidad_idioma.copy(that.fk_localidad_idioma);
      //fk_localidad_idioma = that.fk_localidad_idioma;
   }
   
   //---------------------------------------------------------------------
   public LocalidadIdioma()
   {
      createAtributes();
      initAtributes();
   }
   
   //---------------------------------------------------------------------
   public LocalidadIdioma(LocalidadIdioma that)
   {
      createAtributes();
      asign(that);
   }
   
   //---------------------------------------------------------------------
   public LocalidadIdioma(String idioma)
   {
      createAtributes();
      initAtributes();
      this.idioma[Atributo.Actual.ordinal()] = idioma;
   }
   
   //---------------------------------------------------------------------
   public String getCompositeKey ()
   {
      return String.format("%s,%d", getIdioma_Viejo(), getId_localidad_Viejo());
   }

   //---------------------------------------------------------------------
   public String getIdioma()
   {
      return idioma[Atributo.Actual.ordinal()];
   }
   
      //---------------------------------------------------------------------
   public String getIdioma_Viejo()
   {
      //Revisamos si la direccion del valor viejo es diferente a la dirección del valor de construcción.
      //return idioma[Atributo.Viejo.ordinal()] == EMPTY_STRING?idioma[Atributo.Actual.ordinal()] : idioma[Atributo.Viejo.ordinal()]  ;
        if (isModified())
         return idioma[Atributo.Viejo.ordinal()] != EMPTY_STRING?  idioma[Atributo.Viejo.ordinal()]:idioma[Atributo.Actual.ordinal()];
      else
         return idioma[Atributo.Actual.ordinal()];
   }

   //---------------------------------------------------------------------
   public void setIdioma(String value)
   {
      //if (idioma[Atributo.Viejo.ordinal()] == EMPTY_STRING)
      if (isWaiting())
         idioma[Atributo.Viejo.ordinal()] = idioma[Atributo.Actual.ordinal()];
      
      this.idioma[Atributo.Actual.ordinal()] = value;
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
   public Integer getId_localidad ()
   {
      return fk_localidad_idioma.getId();
   }
   
   //---------------------------------------------------------------------
   public Integer getId_localidad_Viejo ()
   {
      /*if (!fk_localidad_idioma_related)
      {
         if (isWaiting())
            fk_localidad_idioma.acceptChanges();
         else if (isModified())
            fk_localidad_idioma.setModified();
      }*/
      return fk_localidad_idioma.getId_Viejo();
   }
   
   //---------------------------------------------------------------------
   public void setId_localidad (Integer value)
   {
      if (!fk_localidad_idioma_related)
      {
        /*if (isWaiting())
         fk_localidad_idioma.acceptChanges();
    
        else if (isModified())
         fk_localidad_idioma.setModified();*/
    
         fk_localidad_idioma.setId(value);
      }
   }

   //---------------------------------------------------------------------
   public Localidad getFk_localidad_idioma()
   {
      return fk_localidad_idioma;
   }

   //---------------------------------------------------------------------
   public void setFk_localidad_idioma(Localidad fk_localidad_idioma)
   {
      //Nota: Indicamos la presencia de un objeto relacionado, por lo que 
      //se pierde la cualidad de modificarlo, indirectamente.
      fk_localidad_idioma_related = true;
      this.fk_localidad_idioma = fk_localidad_idioma;
   }
   
   //---------------------------------------------------------------------
   public boolean isfk_localidad_idioma_related()
   {
      return fk_localidad_idioma_related;
   }
  
   //---------------------------------------------------------------------
   @Override
   public void clear()
   {
      initAtributes();
      if (!fk_localidad_idioma_related)
         fk_localidad_idioma.clear();
   }
  
   //---------------------------------------------------------------------
   public void copy (LocalidadIdioma that)
   {
      asign(that);
   }  
   
     //---------------------------------------------------------------------
   @Override
   public void acceptChanges()
   {
      super.acceptChanges();
      //Nota: Solo Las llaves foraneas y primarias se manejan así
      if (!fk_localidad_idioma_related)
         fk_localidad_idioma.acceptChanges();
   }
   
   //---------------------------------------------------------------------
   /*
   Obsoleto, las llaves foraneas y primarias deben especificarse por separado.
   @Override
   public void setModified()
   {
      super.setModified();
      //Nota: Solo Las llaves foraneas y primarias se manejan así
      if (!fk_localidad_idioma_related)
         fk_localidad_idioma.setModified();
   }
   
   //---------------------------------------------------------------------
   @Override
   public void setDeleted()
   {
      super.setDeleted();
      //Nota: Solo Las llaves foraneas y primarias se manejan así
       if (!fk_localidad_idioma_related)
         fk_localidad_idioma.setDeleted();
   }

   //---------------------------------------------------------------------
   @Override
   public void setNew()
   {
      super.setNew();
      //Nota: Solo Las llaves foraneas y primarias se manejan así
       if (!fk_localidad_idioma_related)
         fk_localidad_idioma.setNew();
   }*/
}