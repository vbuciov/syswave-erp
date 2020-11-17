package com.syswave.entidades.miempresa;

import com.syswave.entidades.common.IEntidadRecursiva;
import com.syswave.entidades.keys.PrimaryKeyById;
import java.io.Serializable;

/**
 * Indica el tipo de comprobante de actividad comercial.
 * @author Victor Manuel Bucio Vargas
 * @version 1 Marzo 2014
 */
public class TipoComprobante extends PrimaryKeyById implements IEntidadRecursiva, Serializable
{
   private String nombre;
   private Boolean afecta_inventario, entrada, activo, 
                   afecta_saldos, tipo_saldo, comercial;
   private int id_padre, nivel, permite_precios, condicion_pago;
   
      //---------------------------------------------------------------------
   public TipoComprobante ()
   {
      super(); 
      initAtributes();
   }
  
   //---------------------------------------------------------------------
   public TipoComprobante (TipoComprobante that)
   {
      super(); 
      assign(that);
   }
   
   //---------------------------------------------------------------------
   private void initAtributes ()
   {
      nombre = EMPTY_STRING;
      afecta_inventario = false;
      entrada = false;
      activo = true;
      permite_precios = EMPTY_INT;
      condicion_pago = EMPTY_INT;
      afecta_saldos = false; 
      tipo_saldo = false; 
      comercial= true;
      id_padre = EMPTY_INT;
      nivel = EMPTY_INT;
   }
   
   //---------------------------------------------------------------------
   private void assign(TipoComprobante that)
   {
       super.assign(that);
      nombre =that.nombre;
      afecta_inventario = that.afecta_inventario;
      entrada = that.entrada;
      activo = that.activo;
      permite_precios = that.permite_precios;
      condicion_pago = that.condicion_pago;
      afecta_saldos = that.afecta_saldos; 
      tipo_saldo = that.tipo_saldo; 
      comercial= that.comercial;
      id_padre = that.id_padre;
      nivel = that.nivel;
   }
      
   //---------------------------------------------------------------------
   public String getNombre()
   {
      return nombre;
   }

   //---------------------------------------------------------------------
   public void setNombre(String nombre)
   {
      this.nombre = nombre;
   }

   //---------------------------------------------------------------------
   public Boolean esAfecta_inventario()
   {
      return afecta_inventario;
   }
   
   //---------------------------------------------------------------------
   public void setAfecta_inventario(Boolean afecta_inventario)
   {
      this.afecta_inventario = afecta_inventario;
   }

   //---------------------------------------------------------------------
   public Boolean esEntrada()
   {
      return entrada;
   }

   //---------------------------------------------------------------------
   public void setEntrada(Boolean entrada)
   {
      this.entrada = entrada;
   }

   //---------------------------------------------------------------------
   public Boolean esActivo()
   {
      return activo;
   }
   
   //---------------------------------------------------------------------
   public void setActivo(Boolean activo)
   {
      this.activo = activo;
   }

   //---------------------------------------------------------------------
   public int getPermitePrecios()
   {
      return permite_precios;
   }

   //---------------------------------------------------------------------
   public void setPermitePrecios(int permite_precios)
   {
      this.permite_precios = permite_precios;
   }

   //---------------------------------------------------------------------
   public int getCondicionPago()
   {
      return condicion_pago;
   }

   //---------------------------------------------------------------------
   public void setCondicion_pago(int condicion_pago)
   {
      this.condicion_pago = condicion_pago;
   }
   
   //---------------------------------------------------------------------
   public Boolean esAfectaSaldos()
   {
      return afecta_saldos;
   }

   //---------------------------------------------------------------------
   public void setAfectaSaldos(Boolean afecta_saldos)
   {
      this.afecta_saldos = afecta_saldos;
   }

   //---------------------------------------------------------------------
   public Boolean esTipoSaldo()
   {
      return tipo_saldo;
   }

   //---------------------------------------------------------------------
   public void setTipoSaldo(Boolean tipo_saldo)
   {
      this.tipo_saldo = tipo_saldo;
   }

   //---------------------------------------------------------------------
   public Boolean esComercial()
   {
      return comercial;
   }

   //---------------------------------------------------------------------
   public void setComercial(Boolean value)
   {
      this.comercial = value;
   }
   
   //---------------------------------------------------------------------
   @Override
   public Integer getIdPadre()
   {
      return id_padre;
   }
   
   //---------------------------------------------------------------------
   public void setIdPadre(int value)
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
   public void setNivel(int value)
   {
     nivel = value;
   }
   
   //---------------------------------------------------------------------
   @Override
   public void clear()
   {
      initAtributes();
   }
   
   //---------------------------------------------------------------------
   public void copy (TipoComprobante that)
   {
      assign(that);
   }  
}