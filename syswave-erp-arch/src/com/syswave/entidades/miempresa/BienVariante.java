package com.syswave.entidades.miempresa;

import com.syswave.entidades.keys.PrimaryKeyById;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class BienVariante extends PrimaryKeyById
{

    private String descripcion;
    private boolean es_activo, es_armable, es_comercializar, es_inventario;
    //private float masa, ancho, alto, largo;
    //id_unidad_masa, id_unidad_longitud, 
    private int id_bien, nivel, inventario_como, mantenimiento_como, valor_esperado;

    protected ForeignKey navigation;

    //---------------------------------------------------------------------
    public BienVariante()
    {
        super();
        createAtributes();
        initAtributes();
    }

    //---------------------------------------------------------------------
    public BienVariante(BienVariante that)
    {
        super();
        createAtributes();
        assign(that);
    }

    //---------------------------------------------------------------------
    private void createAtributes()
    {
        navigation = new ForeignKey();
    }

    //---------------------------------------------------------------------
    private void initAtributes()
    {
        id_bien = EMPTY_INT;
        //id_unidad_masa = EMPTY_INT; 
        //id_unidad_longitud = EMPTY_INT;
        descripcion = EMPTY_STRING;
        es_activo = false;
        es_armable = false;
        es_comercializar = false;
        es_inventario = false;
        //masa = EMPTY_FLOAT;
        //ancho = EMPTY_FLOAT;
        //alto = EMPTY_FLOAT;
        //largo = EMPTY_FLOAT;
        nivel = EMPTY_INT;
        inventario_como = EMPTY_INT;
        mantenimiento_como = EMPTY_INT;
        valor_esperado = EMPTY_INT;
    }

    //---------------------------------------------------------------------
    private void assign(BienVariante that)
    {
        super.assign(that);
        //fk_variante_bien = that.fk_variante_bien;
        id_bien = that.id_bien;
        //id_unidad_masa = that.id_unidad_masa; 
        //id_unidad_longitud = that.id_unidad_longitud;
        descripcion = that.descripcion;
        es_activo = that.es_activo;
        es_armable = that.es_armable;
        es_comercializar = that.es_comercializar;
        es_inventario = that.es_inventario;
        //masa = that.masa;
        //ancho = that.ancho;
        //alto = that.alto;
        //largo = that.largo;
        nivel = that.nivel;
        inventario_como = that.inventario_como;
        mantenimiento_como = that.mantenimiento_como;
        valor_esperado = that.valor_esperado;

        navigation = that.navigation;
    }

    //---------------------------------------------------------------------
    public int getIdBien()
    {
        return null != navigation.getFk_variante_bien()
                ? navigation.getFk_variante_bien().getId()
                : id_bien;
    }

    //---------------------------------------------------------------------
    public void setIdBien(int value)
    {
        navigation.releaseGrupo();
        id_bien = value;
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
    public boolean esActivo()
    {
        return es_activo;
    }

    //---------------------------------------------------------------------
    public void setEsActivo(boolean es_activo)
    {
        this.es_activo = es_activo;
    }

    //---------------------------------------------------------------------
    public boolean esArmable()
    {
        return es_armable;
    }

    //---------------------------------------------------------------------
    public void setEsArmable(boolean es_armable)
    {
        this.es_armable = es_armable;
    }

    //---------------------------------------------------------------------
    public boolean EsComercializar()
    {
        return es_comercializar;
    }

    //---------------------------------------------------------------------
    public void setEsComercializar(boolean es_comercializar)
    {
        this.es_comercializar = es_comercializar;
    }

    //---------------------------------------------------------------------
    public boolean esInventario()
    {
        return es_inventario;
    }

    //---------------------------------------------------------------------
    public void setEsInventario(boolean es_inventario)
    {
        this.es_inventario = es_inventario;
    }

    //---------------------------------------------------------------------
    /*public float getMasa()
   {
      return masa;
   }

   //---------------------------------------------------------------------
   public void setMasa(float masa)
   {
      this.masa = masa;
   }

   //---------------------------------------------------------------------
   public float getAncho()
   {
      return ancho;
   }

   //---------------------------------------------------------------------
   public void setAncho(float ancho)
   {
      this.ancho = ancho;
   }

   //---------------------------------------------------------------------
   public float getAlto()
   {
      return alto;
   }

   //---------------------------------------------------------------------
   public void setAlto(float alto)
   {
      this.alto = alto;
   }

   //---------------------------------------------------------------------
   public float getLargo()
   {
      return largo;
   }

   //---------------------------------------------------------------------
   public void setLargo(float largo)
   {
      this.largo = largo;
   }*/
    //---------------------------------------------------------------------
    public int getNivel()
    {
        return nivel;
    }

    //---------------------------------------------------------------------
    public void setNivel(int nivel)
    {
        this.nivel = nivel;
    }

    //---------------------------------------------------------------------
    public int getInventarioCcomo()
    {
        return inventario_como;
    }

    //---------------------------------------------------------------------
    public void setInventarioCcomo(int value)
    {
        this.inventario_como = value;
    }

    //---------------------------------------------------------------------
    public int getMantenimientoComo()
    {
        return mantenimiento_como;
    }

    //---------------------------------------------------------------------
    public void setMantenimientoComo(int value)
    {
        this.mantenimiento_como = value;
    }

    //---------------------------------------------------------------------
    public String obtenerEtiquetaMantenimiento()
    {
        switch (mantenimiento_como)
        {
            case 0:
                return "Tiempo(Días)";

            case 1:
                return "Kilometraje";

            case 2:
                return "Usos";

            default:
                return "Desconocido";
        }
    }

    //---------------------------------------------------------------------
    /**
     * Según el Matenimiento Como, es el valor contra el cual se compara la
     * operación.
     */
    public int getValorEsperado()
    {
        return valor_esperado;
    }

    //---------------------------------------------------------------------
    /**
     * Según el Matenimiento Como, es el valor contra el cual se compara la
     * operación.
     */
    public void setValorEsperado(int value)
    {
        this.valor_esperado = value;
    }

    //---------------------------------------------------------------------
    /*public int getIdUnidadLongitud ()
   {
       return null != navigation.getFk_variante_id_unidad_longitud()?
              navigation.getFk_variante_id_unidad_longitud().getId():
              id_unidad_longitud;
   }
   
   //---------------------------------------------------------------------
   public void setIdUnidadLongitud (int value)
   {      
       if (null != navigation.getFk_variante_id_unidad_longitud())
           navigation.releaseUnidadLongitud();
        
       id_unidad_longitud = value;
   }
   
   //---------------------------------------------------------------------
   public int getIdUnidadMasa ()
   {
       return null != navigation.getFk_variante_id_unidad_masa()?
              navigation.getFk_variante_id_unidad_masa().getId():
              id_unidad_masa;
   }
   
   //---------------------------------------------------------------------
   public void setIdUnidadMasa (int value)
   {      
        if ( null != navigation.getFk_variante_id_unidad_masa())
                navigation.releaseUnidadMasa();
        
        id_unidad_masa = value;
   }
   
   //---------------------------------------------------------------------
   public Unidad getHasOneUnidadMasa()
   {
       return null != navigation.getFk_variante_id_unidad_masa()?
              navigation.getFk_variante_id_unidad_masa() :
              navigation.joinUnidadMasa(id_unidad_masa);
   }
   
   //---------------------------------------------------------------------
   public void setHasOneUnidadMasa(Unidad value)
   {
       navigation.setFk_variante_id_unidad_masa(value);
   }
   
   //---------------------------------------------------------------------
   public Unidad getHasOneUnidadLongitud()
   {
       return null != navigation.getFk_variante_id_unidad_longitud()?
              navigation.getFk_variante_id_unidad_longitud() :
              navigation.joinUnidadLongitud(id_unidad_masa);
   }
   
   //---------------------------------------------------------------------
   public void setHasOneUnidadLongitud(Unidad value)
   {
       navigation.setFk_variante_id_unidad_longitud(value);
   }*/
    //---------------------------------------------------------------------
    public Bien getHasOneGrupo()
    {
        return null != navigation.getFk_variante_bien()
                ? navigation.getFk_variante_bien()
                : navigation.joinGrupo(id_bien);
    }

    //---------------------------------------------------------------------
    public void setHasOneGrupo(Bien value)
    {
        navigation.setFk_variante_bien(value);
    }

    //---------------------------------------------------------------------
    @Override
    public void clear()
    {
        initAtributes();
        navigation.releaseGrupo();
        /*navigation.releaseUnidadLongitud();
        navigation.releaseUnidadMasa();*/
    }

    //---------------------------------------------------------------------
    public void copy(BienVariante that)
    {
        assign(that);
    }

    //---------------------------------------------------------------------
    @Override
    public void acceptChanges()
    {
        super.acceptChanges();
        navigation.acceptChanges();
    }

    //---------------------------------------------------------------------   
    public class ForeignKey
    {

        protected Bien fk_variante_bien;
        //protected Unidad fk_variante_id_unidad_masa, fk_variante_id_unidad_longitud;

        //---------------------------------------------------------------------
        public Bien joinGrupo(int id_grupo)
        {
            fk_variante_bien = new Bien();
            fk_variante_bien.setId(id_grupo);
            return fk_variante_bien;
        }

        //---------------------------------------------------------------------
        /*public Unidad joinUnidadMasa(int id_unidad)
        {
            fk_variante_id_unidad_masa = new Unidad();
            fk_variante_id_unidad_masa.setId(id_unidad);
            return fk_variante_id_unidad_masa;
        }

        //---------------------------------------------------------------------
        public Unidad joinUnidadLongitud(int id_longitud)
        {
            fk_variante_id_unidad_longitud = new Unidad();
            fk_variante_id_unidad_longitud.setId(id_longitud);
            return fk_variante_id_unidad_longitud;
        }

        //---------------------------------------------------------------------
        public void releaseUnidadLongitud()
        {
            fk_variante_id_unidad_longitud = null;
        }

        //---------------------------------------------------------------------
        public void releaseUnidadMasa()
        {
            fk_variante_id_unidad_masa = null;
        }*/
        //---------------------------------------------------------------------
        public void releaseGrupo()
        {
            fk_variante_bien = null;
        }

        //---------------------------------------------------------------------
        public Bien getFk_variante_bien()
        {
            return fk_variante_bien;
        }

        //---------------------------------------------------------------------
        public void setFk_variante_bien(Bien value)
        {
            this.fk_variante_bien = value;
            this.fk_variante_bien.setDependentOn(false);
        }

        //---------------------------------------------------------------------
        /*public Unidad getFk_variante_id_unidad_masa()
        {
            return fk_variante_id_unidad_masa;
        }

        //---------------------------------------------------------------------
        public void setFk_variante_id_unidad_masa(Unidad value)
        {
            this.fk_variante_id_unidad_masa = value;
            this.fk_variante_id_unidad_masa.setDependentOn(false);
        }

        //---------------------------------------------------------------------
        public Unidad getFk_variante_id_unidad_longitud()
        {
            return fk_variante_id_unidad_longitud;
        }

        //---------------------------------------------------------------------
        public void setFk_variante_id_unidad_longitud(Unidad value)
        {
            this.fk_variante_id_unidad_longitud = value;
            this.fk_variante_id_unidad_longitud.setDependentOn(false);
        }*/
        //---------------------------------------------------------------------
        private void acceptChanges()
        {
            if (null != fk_variante_bien && fk_variante_bien.isDependentOn())
                fk_variante_bien.acceptChanges();

            /* if (null != fk_variante_id_unidad_longitud && fk_variante_id_unidad_longitud.isDependentOn())
                fk_variante_id_unidad_longitud.acceptChanges();

            if (null != fk_variante_id_unidad_masa && fk_variante_id_unidad_masa.isDependentOn())
                fk_variante_id_unidad_masa.acceptChanges();*/
        }
    }
}
