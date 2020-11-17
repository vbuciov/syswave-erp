package com.syswave.entidades.miempresa_vista;

import com.syswave.entidades.common.Entidad;

/**
 * Representa una relación familiar que originalente es bidireccional
 * en una sola dirección
 * @author carlos soto
 */
public class PersonalTieneFamiliar extends Entidad
{
   protected Personal fk_founded, fk_searched;
   private int[] prototype;

   private boolean fk_founded_realated, fk_searched_related;

   //-----------------------------------------------------------------------
   public PersonalTieneFamiliar()
   {
      createAtributes();
      initAtributes();
   }

   //-----------------------------------------------------------------------
   public PersonalTieneFamiliar(PersonalTieneFamiliar that)
   {
      createAtributes();
      asign(that);
   }
   
     //-----------------------------------------------------------------------
   private void asign(PersonalTieneFamiliar that)
   {

      setPrototype(that.prototype[Atributo.Actual.ordinal()]);

      if (!fk_founded_realated)
         fk_founded.copy(that.fk_founded);
      if (!fk_searched_related)
         fk_searched.copy(that.fk_searched);
   }

   //-----------------------------------------------------------------------
   private void createAtributes()
   {
      prototype = new int[Atributo.values().length];
      fk_founded = new Personal();
      fk_searched = new Personal();
      fk_founded_realated = false;
      fk_searched_related = false;
   }

   //-----------------------------------------------------------------------
   private void initAtributes()
   {
      for (int i = 0; i < prototype.length; i++)
      {
         prototype[i] = EMPTY_INT;
      }
   }

   //-----------------------------------------------------------------------
   public int getPrototype()
   {
      return prototype[Atributo.Actual.ordinal()];
   }

   //-----------------------------------------------------------------------
   public int getPrototype_Viejo()
   {
      return prototype[Atributo.Viejo.ordinal()] != EMPTY_INT ? prototype[Atributo.Viejo.ordinal()] : prototype[Atributo.Actual.ordinal()];
   }

   //-----------------------------------------------------------------------
   public void setPrototype(int value)
   {
      if (isWaiting())
         prototype[Atributo.Viejo.ordinal()] = prototype[Atributo.Actual.ordinal()];

      prototype[Atributo.Actual.ordinal()] = value;
   }

   //-----------------------------------------------------------------------
   public int getIdFounded()
   {
      return fk_founded.getIdPersona();
   }
   
   //-----------------------------------------------------------------------
   public int getIdFounded_Viejo()
   {
      return fk_founded.getIdPersona_Viejo();
   }

   //-----------------------------------------------------------------------
   public void setIdFounded(int value)
   {
      if (!fk_founded_realated)
         fk_founded.setIdPersona(value);
   }

   //-----------------------------------------------------------------------
   public int getIdSearched()
   {
      return fk_searched.getIdPersona();
   }
   
   //-----------------------------------------------------------------------
   public int getIdSearched_Viejo()
   {
      return fk_searched.getIdPersona_Viejo();
   }

   //-----------------------------------------------------------------------
   public void setIdSearched(int value)
   {
      if (!fk_searched_related)
         fk_searched.setIdPersona(value);
   }

    //-----------------------------------------------------------------------
    /*public String getNombres()
    {
    return fk_searched.getNombres();
    }
    //-----------------------------------------------------------------------
    public void setNombres (String value)
    {
    fk_searched.setNombres(value);
    }
    //-----------------------------------------------------------------------
    public String getApellidos ()
    {
    return fk_searched.getApellidos();
    }
    //-----------------------------------------------------------------------
    public void setApellidos (String value)
    {
    fk_searched.setApellidos(value);
    }
    //-----------------------------------------------------------------------
    public Date getNacimiento()
    {
    return fk_searched.getNacimiento();
    }
    //-----------------------------------------------------------------------
    public void setNacimiento(Date value)
    {
    fk_searched.setNacimiento(value);
    } 
    //-----------------------------------------------------------------------
    public boolean esActivo()
    {
    return fk_searched.esActivo();
    }
    //-----------------------------------------------------------------------
    
    public void setActivo(boolean value)
    {
    fk_searched.setActivo(value);
    }
        
    //-----------------------------------------------------------------------
    public String getNacionalidad()
    {
    return fk_searched.getNacionalidad();
    }
    
    //-----------------------------------------------------------------------
    public void setNacionalidad(String value)
    {
    fk_searched.setNacionalidad(value);
    }
     
    //-----------------------------------------------------------------------
    public void setReligion(String value)
    {
    fk_searched.setReligion(value);
    }
    //-----------------------------------------------------------------------
    public String getReligion()
    {
    return fk_searched.getReligion();
    }
    //-----------------------------------------------------------------------
    public void setGenero(Boolean value)
    {
    fk_searched.setGenero(value);
    }
    //-----------------------------------------------------------------------
    public Boolean getGenero()
    {
    return fk_searched.esGenero();
    }      
    
    public void setGenero(int value)
    {
    fk_searched.setGenero(value);
    }
    //---------------------------------------------------------------------- 
    public int getGeneroI()
    {
    return fk_searched.getGenero();
    }
    //-----------------------------------------------------------------------
    public void setEstadoCivil(Integer value)
    {
    fk_searched.setEstadoCivil(value);
    }
    //-----------------------------------------------------------------------
    public Integer getEstadoCivil()
    {
    return fk_searched.getEstadoCivil();
    }
    //-----------------------------------------------------------------------
    public void setTipoSangre(Integer value)
    {
    fk_searched.setEsTipoSangre(value);
    }
    //-----------------------------------------------------------------------
    public Integer getTipoSangre()
    {
    return fk_searched.getEsTipoSangre();
    }
    //-----------------------------------------------------------------------
    
    public void setAltura(float value)
    {
    fk_searched.setAltura(value);
    }
    //-----------------------------------------------------------------------
    
    public Float getAltura()
    {
    return fk_searched.getAltura();
    }
    //-----------------------------------------------------------------------
    
    public void setPeso(float value)
    {
    fk_searched.setPeso(value);
    }
    //-----------------------------------------------------------------------
    public Float getPeso()
    {
    return fk_searched.getPeso();
    }*/
   //-----------------------------------------------------------------------
   public Personal getFk_founded()
   {
      return fk_founded;
   }

   //-----------------------------------------------------------------------
   public void setFk_founded(Personal value)
   {
      this.fk_founded = value;
      fk_founded_realated = true;
   }

   //-----------------------------------------------------------------------
   public Personal getFk_searched()
   {
      return fk_searched;
   }

   //-----------------------------------------------------------------------
   public void setFk_searched(Personal value)
   {
      this.fk_searched = value;
      fk_searched_related = true;
   }

   //-----------------------------------------------------------------------  
   @Override
   public void clear()
   {
      initAtributes();

      if (!fk_founded_realated)
         fk_founded.clear();
      if (!fk_searched_related)
         fk_searched.clear();
   }

   //-----------------------------------------------------------------------
   public void copy(PersonalTieneFamiliar that)
   {
      asign(that);
   }
   
        //---------------------------------------------------------------------
   @Override
   public void acceptChanges()
   {
      super.acceptChanges();
      //Nota: Solo Las llaves foraneas y primarias se manejan así
      if (!fk_founded_realated)
         fk_founded.acceptChanges();
      if (!fk_searched_related)
         fk_searched.acceptChanges();
   }
}
