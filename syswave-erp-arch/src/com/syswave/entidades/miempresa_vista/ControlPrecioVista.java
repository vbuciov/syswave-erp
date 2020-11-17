package com.syswave.entidades.miempresa_vista;

import com.syswave.entidades.miempresa.AreaPrecio;
import com.syswave.entidades.miempresa.Bien;
import com.syswave.entidades.miempresa.BienVariante;
import com.syswave.entidades.miempresa.ControlPrecio;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class ControlPrecioVista extends ControlPrecio
{
    private final BienVariante fk_precio_id_variante;
    private final Bien fk_variante_bien;
    private final AreaPrecio fk_precio_area;
    private String categoria, moneda;
    
    private String identificador_muestra;
    private float existencia_muestra;

    //---------------------------------------------------------------------
    public ControlPrecioVista()
    {
        super();
        fk_precio_id_variante = navigation.joinBienVariante(EMPTY_INT);
        fk_variante_bien = fk_precio_id_variante.getHasOneGrupo();
        fk_precio_area = navigation.joinAreaPrecio(EMPTY_INT);
        initAtributes();
    }

    //---------------------------------------------------------------------
    private void initAtributes()
    {
        identificador_muestra = EMPTY_STRING;
        existencia_muestra = EMPTY_FLOAT;
        categoria = EMPTY_STRING;
        //unidad_masa = EMPTY_STRING;
        moneda = EMPTY_STRING;
    }

    //---------------------------------------------------------------------
    @Override
    public int getIdVariante()
    {
       return fk_precio_id_variante.getId();
    }

    //---------------------------------------------------------------------
    @Override
    public void setIdVariante(int value)
    {
        fk_precio_id_variante.setId(value);
    }
    
   //---------------------------------------------------------------------
    public void setMoneda(String value)
    {
        moneda = value;
    }

    //---------------------------------------------------------------------
    public String getMoneda()
    {
        return moneda;
    }
    
    //---------------------------------------------------------------------
    public void setPresentacion(String value)
    {
        fk_precio_id_variante.setDescripcion(value);
    }

    //---------------------------------------------------------------------
    public String getPresentacion()
    {
        return fk_precio_id_variante.getDescripcion();
    }

    //---------------------------------------------------------------------
    public int getEsServicio()
    {
        return fk_variante_bien.getEsTipo();
    }

    //---------------------------------------------------------------------
    public void setEsServicio(int value)
    {
        fk_variante_bien.setEsTipo(value);
    }

    //---------------------------------------------------------------------
    public void setIdCategoria(int value)
    {
        fk_variante_bien.setIdCategoria(value);
    }

    //---------------------------------------------------------------------
    public int getIdCategoria()
    {
        return fk_variante_bien.getIdCategoria();
    }

    //---------------------------------------------------------------------
    public String getCategoria()
    {
        return categoria;
    }

    //---------------------------------------------------------------------
    public void setCategoria(String value)
    {
        categoria = value;
    }

    //---------------------------------------------------------------------
    public int getIdBien()
    {
        return fk_precio_id_variante.getIdBien();
    }

    //---------------------------------------------------------------------
    public void setIdBien(int value)
    {
       fk_variante_bien.setId(value);
       fk_precio_id_variante.setIdBien(value);
    }
    
    //---------------------------------------------------------------------
    public void setTipoBien (String value)
    {
        fk_variante_bien.setNombre(value);
    }
    
    //---------------------------------------------------------------------
    public String getTipoBien ()
    {
        return fk_variante_bien.getNombre();
    }
    
    //---------------------------------------------------------------------
    /*public int getIdUnidadMasa()
    {
       return fk_precio_id_variante.getIdUnidadMasa();
    }
    
     //---------------------------------------------------------------------
    public void setIdUnidadMasa(int value)
    {
        fk_precio_id_variante.setIdUnidadMasa(value);
    }
    
    //---------------------------------------------------------------------
    public String getUnidad()
    {
       return unidad_masa;
    }
    
     //---------------------------------------------------------------------
    public void setUnidad(String value)
    {
        unidad_masa = value;
    }*/

    //---------------------------------------------------------------------
    public boolean esActivo()
    {
        return fk_precio_id_variante.esActivo();
    }

    //---------------------------------------------------------------------
    public void setActivo(boolean value)
    {
        fk_precio_id_variante.setEsActivo(value);
    }

    //---------------------------------------------------------------------
    public boolean esComercializar()
    {
        return fk_precio_id_variante.EsComercializar();
    }

    //---------------------------------------------------------------------
    public void setComercializar(boolean value)
    {
        fk_precio_id_variante.setEsComercializar(value);
    }

    //---------------------------------------------------------------------
    public boolean esInventario()
    {
        return fk_precio_id_variante.esInventario();
    }

    //---------------------------------------------------------------------
    public void setEsInventario(boolean value)
    {
        fk_precio_id_variante.setEsInventario(value);
    }

    //---------------------------------------------------------------------
    public String getIdentificador_muestra()
    {
        return identificador_muestra;
    }

    //---------------------------------------------------------------------
    public void setIdentificador_muestra(String value)
    {
        this.identificador_muestra = value;
    }

    //---------------------------------------------------------------------
    public float getExistencia_muestra()
    {
        return existencia_muestra;
    }

    //---------------------------------------------------------------------
    public void setExistencia_muestra(float value)
    {
        this.existencia_muestra = value;
    }
        
    //---------------------------------------------------------------------
    @Override
    public void setIdAreaPrecio(int value)
    {
        fk_precio_area.setId(value);
    }

    //---------------------------------------------------------------------
    public void setAreaPrecio(String value)
    {
        this.fk_precio_area.setDescripcion(value);
    }

    //---------------------------------------------------------------------
    public String getAreaPrecio()
    {
        return fk_precio_area.getDescripcion();
    }
    
   //---------------------------------------------------------------------
    public int getEsTipo()
    {
        return fk_precio_area.getEsTipo();
    }

    //---------------------------------------------------------------------
    public void setEsTipo(int value)
    {
        this.fk_precio_area.setEsTipo(value);
    }
    
        //----------------------------------------------------------------------
   public boolean esCostoInDirecto()
   {
      return fk_precio_area.esCostoDirecto();
   }

   //----------------------------------------------------------------------
   public void setCostoDirecto(boolean value)
   {
      this.fk_precio_area.setCostoDirecto(value);
   }
   
       //----------------------------------------------------------------------
    public boolean esCostoVariable()
    {
        return fk_precio_area.esCostoVariable();
    }

    //----------------------------------------------------------------------
    public void setEsCostoVariable(boolean value)
    {
        this.fk_precio_area.setEsCostoVariable(value);
    }

    //---------------------------------------------------------------------
    @Override
    public void clear()
    {
        super.clear();
        initAtributes();
    }
}