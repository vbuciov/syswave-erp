package com.syswave.entidades.miempresa_vista;

import com.syswave.entidades.miempresa.Bien;
import com.syswave.entidades.miempresa.BienVariante;
import com.syswave.entidades.miempresa.ControlPrecio;
import com.syswave.entidades.miempresa.DesgloseCosto;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class DesgloseCosto_5FN extends DesgloseCosto
{

    private ControlPrecio fk_precio_indirecto;
    private ControlPrecio fk_precio_variable;
    private BienVariante fk_precio_id_variante;
    private Bien fk_variante_bien;
    private String categoria, moneda, area_precio;
    //unidad

    public DesgloseCosto_5FN()
    {
        super();
        fk_precio_indirecto = navigation.joinPrecioIndirecto(EMPTY_INT);
        fk_precio_variable = navigation.joinPrecioVariable(EMPTY_INT);
        fk_precio_id_variante = fk_precio_indirecto.getHasOneBienVariante();
        fk_variante_bien = fk_precio_id_variante.getHasOneGrupo();
        //initAtributes();
    }

    public DesgloseCosto_5FN(DesgloseCosto_5FN that)
    {
        super(that);
        initAtributes();
        //asign(that);
    }

    /*private void asign(DesgloseCosto_5FN that)
     {
     //categoria = that.categoria;
     //setCategoria(that.getCategoria());
     //presentacion = that.presentacion;
     //area_precio = that.area_precio;
     //setArea_precio(that.getArea_precio());
     //unidad = that.unidad;
     //tipo_bien = that.tipo_bien;
     }*/
    private void initAtributes()
    {
        categoria = EMPTY_STRING;
        moneda = EMPTY_STRING;
        //presentacion = EMPTY_STRING;
        area_precio = EMPTY_STRING;
        //unidad = EMPTY_STRING;
        // tipo_bien = EMPTY_STRING;
    }

    public int getId_grupo()
    {
        return fk_variante_bien.getId();
    }

    public void setId_grupo(int value)
    {
        fk_variante_bien.setId(value);
    }

    public String getTipo_bien()
    {
        return fk_variante_bien.getNombre();
    }

    public void setTipo_bien(String tipo_bien)
    {
        fk_variante_bien.setNombre(tipo_bien);
    }

    public int getIdCategoria()
    {
        return fk_variante_bien.getIdCategoria();
    }

    public void setIdCategoria(int value)
    {
        fk_variante_bien.setIdCategoria(value);
    }

    public String getCategoria()
    {
        return categoria;
    }

    public void setCategoria(String value)
    {
        categoria = value;
    }

    public String getPresentacion()
    {
        return fk_precio_id_variante.getDescripcion();
    }

    public void setPresentacion(String presentaciones)
    {
        this.fk_precio_id_variante.setDescripcion(presentaciones);
    }

    public int getIdVariante()
    {
        return fk_precio_id_variante.getId();
    }

    public void setIdVariante(int value)
    {
        fk_precio_id_variante.setId(value);
    }

    /*public int getIdUnidad()
    {
        return fk_precio_id_variante.getIdUnidadMasa();
    }

    public void setIdUnidad(int unidad)
    {
        fk_precio_id_variante.setIdUnidadMasa(unidad);
    }

    public String getUnidad()
    {
        return unidad;
    }

    public void setUnidad(String value)
    {
        unidad = value;
    }*/

    public int getIdArea_precio()
    {
        return fk_precio_variable.getIdAreaPrecio();
    }

    public void setIdArea_precio(int value)
    {
        fk_precio_variable.setIdAreaPrecio(value);
    }

    public String getArea_precio()
    {
        return area_precio;
    }

    public void setArea_precio(String value)
    {
        area_precio = value;
    }

    public void setIdMoneda(int value)
    {
        fk_precio_indirecto.setIdMoneda(value);
    }

    public int getIdMoneda()
    {
        return fk_precio_indirecto.getIdMoneda();
    }

    public void setMoneda(String value)
    {
        moneda = value;
    }

    public String getMoneda()
    {
        return moneda;
    }

    /*@Override
     public void clear()
     {
     initAtributes();
     super.clear(); //To change body of generated methods, choose Tools | Templates.
     }

     public void copy(DesgloseCosto_5FN that)
     {
     asign(that);
     copy((DesgloseCosto) that);
     }*/
}
