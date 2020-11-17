package com.syswave.entidades.miempresa_vista;

import com.syswave.entidades.miempresa.ControlPrecio;
import com.syswave.entidades.miempresa.DocumentoDetalle_tiene_Precio;
import com.syswave.entidades.miempresa.Moneda;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class DocumentoDetalle_tiene_PrecioVista extends DocumentoDetalle_tiene_Precio
{
   ControlPrecio fk_documento_detalle_id_precio;
   Moneda fk_id_moneda_actual;
   
   private int es_tipo;
   String moneda;
   
   public DocumentoDetalle_tiene_PrecioVista()
   {
      createAtributes ();
      moneda = EMPTY_STRING;
      es_tipo = EMPTY_INT;
   }
   
   private void createAtributes ()
   {
      fk_id_moneda_actual = new Moneda();
      fk_documento_detalle_id_precio = navigation.getFk_fk_documento_detalle_id_precio();
   }
   
   /**
    * Obtiene el Id de la moneda en la que esta expresado el precio.
    */
   public int getIdMonedaActual ()
   {
      return fk_id_moneda_actual.getId();
   }
   
    /**
    * Establece el Id de la moneda en la que esta expresado el precio.
    */
   public void setIdMonedaActual (int  value)
   {
      fk_id_moneda_actual.setId(value);
   }
    
   
   /**
    * Obtiene la descripción del precio del producto.
    */
   public String getDescripcion()
   {
      return fk_documento_detalle_id_precio.getDescripcion();
   }
   
     /**
    * Establece la descripción del precio del producto.
     * @param value
    */
   public void setDescripcion (String value)
   {
      fk_documento_detalle_id_precio.setDescripcion(value);
   }
   
   /**
    * Obtiene un valor que indica si el precio es Compra o Venta.
     * @return 
    */
   public int getEsTipo ()
   {
      return es_tipo;
   }
   
    /**
    * Establece un valor que indica si el precio es Compra o Venta.
     * @param value
    */
   public void setEsTipo (int value)
   {
      es_tipo = value;
   }
   
    /**
    * Obtiene el valor de mercado que tiene el producto.
     * @return 
    */
   public float getMercado ()
   {
      return fk_documento_detalle_id_precio.getPrecioFinal();
   }
   
      /**
    * Establece el valor de mercado que tiene el producto.
    */
   public void setMerado (float value)
   {
      fk_documento_detalle_id_precio.setPrecioFinal(value);
   }
   
      /**
    * Obtiene la moneda en que esta el precio que tiene el producto.
    */
   public int getIdMonedaOriginal()
   {
      return fk_documento_detalle_id_precio.getIdMoneda();
   }
  
       /**
    * Establece la moneda en que esta el precio que tiene el producto.
    */
   public void setIdMonedaOriginal(int value)
   {
      fk_documento_detalle_id_precio.setIdMoneda(value);
   }
   
       /**
    * Obtiene el nombre de la moneda en que esta el precio que tiene el producto.
    */
   public String getMoneda ()
   {
      return moneda;
   }
   
        /**
    * Establece el nombre de la moneda en que esta el precio que tiene el producto.
    */
   public void setMoneda(String value)
   {
      moneda = value;
   }
  
    /**
    * Obtiene el producto.
    */
   public int getIdVariante ()
   {
      return fk_documento_detalle_id_precio.getIdVariante();
   }
   
    /**
    * Establece el producto.
    */
   public void setIdVariante(int value)
   {
      fk_documento_detalle_id_precio.setIdVariante(value);
   }
}