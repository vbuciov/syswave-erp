package com.syswave.forms.miempresa;

import com.syswave.entidades.miempresa.PersonaAtributo;
import com.syswave.entidades.miempresa.PersonaDireccion;
import com.syswave.entidades.miempresa.PersonaEducacion;
import com.syswave.entidades.miempresa.PersonaFoto;
import com.syswave.entidades.miempresa.PersonaIdentificador;
import com.syswave.entidades.miempresa.PersonaTieneIncidencia;
import com.syswave.entidades.miempresa_vista.Personal;
import com.syswave.entidades.miempresa_vista.PersonalTieneFamiliar;
import com.syswave.entidades.miempresa.TipoPersona;
import com.syswave.entidades.miempresa.Valor;
import com.syswave.entidades.miempresa_vista.PersonaDetalle_5FN;
import com.syswave.entidades.miempresa_vista.PersonaIdentificadorVista;
import java.util.List;

/**
 *
 * @author Gilberto Aaron Jimenez Montelongo
 */
public interface IPersonalMediator
{
    void showDetail(Personal elemento);
    void onAcceptNewElement(Personal nuevo, List<PersonaDireccion> direcciones, 
                            List<PersonaIdentificadorVista> lstIdentificadores, 
                            List<PersonaIdentificador> lstMedios,
                            PersonaFoto foto,
                            List<PersonaEducacion> listaFormacion,
                             List<PersonaAtributo> listaAtributos,
                             List<PersonaDetalle_5FN> listaDetalles,
                             List<PersonaTieneIncidencia> listaIncidencias,
                             List<PersonalTieneFamiliar> listaFamiliar);
    void onAcceptModifyElement(Personal modificado, 
                               List<PersonaDireccion> direcciones, 
                               List<PersonaDireccion> direcciones_borradas, 
                               List<PersonaIdentificadorVista> lstIdentificadores, 
                               List<PersonaIdentificador> lstMedios, 
                               List<PersonaIdentificador> lstMedios_borrados,
                               PersonaFoto foto,
                               PersonaFoto foto_borrado,
                               List<PersonaEducacion> listaFormacion,
                               List<PersonaEducacion> listaFormacion_borrado,
                               List<PersonaAtributo> listaAtributos,
                               List<PersonaAtributo> listaAtributos_borrado,
                               List<PersonaDetalle_5FN> listaDetalles,
                               List<PersonaTieneIncidencia> listaIncidencias,
                               List<PersonaTieneIncidencia> listaIncidencias_borrados,
                               List<PersonalTieneFamiliar> listaFamilia,
                               List<PersonalTieneFamiliar> listaFamilia_borrados);
    List<TipoPersona> obtenerTipoPersonas ();
    List<PersonaDireccion> obtenerDirecciones (Personal elemento);
    List<PersonaIdentificadorVista> obtenerIdentificadores(Personal elemento);
    List<Valor> obtenerTipoMedios();
    List<PersonaIdentificador> obtenerMedios(Personal elemento);
    List<PersonaFoto> obtenerFotos (Personal elemento);
    List<PersonaEducacion> obtenerFormacion (Personal elemento);
    List<PersonaAtributo> obtenerAtributos (Personal elemento);
    List<PersonaAtributo> obtenerPadecimientos(Personal elemento);
    List<PersonaDetalle_5FN> obtenerCaracteristicas(Personal elemento);
    List<PersonaTieneIncidencia> obtenerIncidencias(Personal elemento);
    
    List<Valor> obtenerEstadoCivil();
    
    List<PersonalTieneFamiliar> obtenerFamiliares(PersonalTieneFamiliar elemento);
    
    String getEsquema();

    List<Personal> obtenerSoloPersonal(Personal filtro);
    
}
