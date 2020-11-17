package com.syswave.forms.miempresa;
import com.syswave.entidades.miempresa.Persona;
import com.syswave.entidades.miempresa.PersonaCreditoCuenta;
import com.syswave.entidades.miempresa.PersonaDireccion;
import com.syswave.entidades.miempresa.PersonaFoto;
import com.syswave.entidades.miempresa.PersonaIdentificador;
import com.syswave.entidades.miempresa.TipoPersona;
import com.syswave.entidades.miempresa.Valor;
import com.syswave.entidades.miempresa_vista.PersonaIdentificadorVista;
import java.util.List;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public interface IPersonaMediator {
    void showDetail(Persona elemento);
    void onAcceptNewElement(Persona nuevo, List<PersonaDireccion> direcciones, 
                            List<PersonaIdentificadorVista> lstIdentificadores, 
                            List<PersonaIdentificador> lstMedios,
                            List<PersonaCreditoCuenta> lstCuentas,
                            PersonaFoto foto);
    void onAcceptModifyElement(Persona modificado, 
                               List<PersonaDireccion> direcciones, 
                               List<PersonaDireccion> direcciones_borradas, 
                               List<PersonaIdentificadorVista> lstIdentificadores, 
                               List<PersonaIdentificador> lstMedios, 
                               List<PersonaIdentificador> lstMedios_borrados,
                               List<PersonaCreditoCuenta> lstCuentas,
                               List<PersonaCreditoCuenta> lstCuentas_borrados,
                                PersonaFoto foto,
                                 PersonaFoto foto_borrada);
    List<TipoPersona> obtenerTipoPersonas ();
    List<PersonaDireccion> obtenerDirecciones (Persona elemento);

    List<PersonaIdentificadorVista> obtenerIdentificadores(Persona elemento);
    List<Valor> obtenerTipoMedios();
    List<PersonaIdentificador> obtenerMedios(Persona elemento);
    List<PersonaCreditoCuenta> obtenerCuentas(Persona elemento);
    List<PersonaFoto> obtenerFotos(Persona persona);
    String getEsquema();
}
