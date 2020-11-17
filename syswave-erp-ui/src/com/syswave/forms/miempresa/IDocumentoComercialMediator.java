package com.syswave.forms.miempresa;

import com.syswave.entidades.miempresa.DocumentoContadoMovimiento;
import com.syswave.entidades.miempresa.DocumentoDetalle;
import com.syswave.entidades.miempresa.Moneda;
import com.syswave.entidades.miempresa.PersonaDireccion_tiene_Documento;
import com.syswave.entidades.miempresa.TipoComprobante;
import com.syswave.entidades.miempresa_vista.DocumentoComercial;
import com.syswave.entidades.miempresa_vista.DocumentoContadoMovimiento_5FN;
import com.syswave.entidades.miempresa_vista.DocumentoDetalleNavigable;
import com.syswave.entidades.miempresa_vista.DocumentoDetalle_tiene_PrecioVista;
import com.syswave.entidades.miempresa_vista.PersonaDireccion_tiene_Documento_5FN;
import java.util.List;
import javax.swing.JInternalFrame;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public interface IDocumentoComercialMediator
{
   void showDetail(DocumentoComercial elemento);
   void onAcceptNewElement(DocumentoComercial nuevo, List<DocumentoDetalleNavigable> detalles, 
                                                     List<PersonaDireccion_tiene_Documento_5FN> direcciones,
                                                     List<DocumentoContadoMovimiento_5FN> pagos);
   void onAcceptModifyElement(DocumentoComercial modificado, List<DocumentoDetalleNavigable> detalles, 
                                                             List<DocumentoDetalle> detalles_deleted,
                                                             List<PersonaDireccion_tiene_Documento_5FN> direcciones, 
                                                             List<PersonaDireccion_tiene_Documento> direcciones_deleted,
                                                             List<DocumentoContadoMovimiento_5FN> pagos,
                                                             List<DocumentoContadoMovimiento> pagos_deleted);
   List<TipoComprobante> obtenerTiposComprobantes ();
   List<Moneda> obtenerMonedas ();
   List<PersonaDireccion_tiene_Documento_5FN> obtenerPersonaDireccionDocumentos (DocumentoComercial elemento);
   List<DocumentoDetalleNavigable> obtenerDocumentoDetalles(DocumentoComercial elemento);
   List<DocumentoDetalle_tiene_PrecioVista> obtenerDetalleTienePrecios(DocumentoComercial elemento);
   List<DocumentoDetalle_tiene_PrecioVista> obtenerDetalleTienePrecios(DocumentoDetalle elemento);
   List<DocumentoContadoMovimiento_5FN> obtenerFormasPago (DocumentoComercial elemento);
   
   String getEsquema();
   void agregaContenedorPrincipal (JInternalFrame Dialog);
   void mostrarCentrado (JInternalFrame Dialog);
}
