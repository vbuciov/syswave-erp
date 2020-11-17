package com.syswave.entidades.miempresa_vista;

import com.syswave.entidades.miempresa.DocumentoDetalle;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class DocumentoDetalleNavigable extends DocumentoDetalle
{
   List<DocumentoDetalle_tiene_PrecioVista> partes;
   
   public DocumentoDetalleNavigable ()
   {
      createAtributes ();
   }
   
   private void createAtributes ()
   {
      partes = new ArrayList<DocumentoDetalle_tiene_PrecioVista>();
   }

   public List<DocumentoDetalle_tiene_PrecioVista> getPartes()
   {
      return partes;
   }

   public void setPartes(List<DocumentoDetalle_tiene_PrecioVista> partes)
   {
      this.partes = partes;
   }
   
   public void agregarParte (DocumentoDetalle_tiene_PrecioVista nueva)
   {
      nueva.setHasOneDocumentoDetalle(this);
      partes.add(nueva);
   }
   
   public boolean tienePartes ()
   {
      return partes.size() > 0;
   }
}
