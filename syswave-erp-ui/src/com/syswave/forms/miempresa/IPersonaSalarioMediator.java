package com.syswave.forms.miempresa;

import com.syswave.entidades.miempresa.Moneda;
import com.syswave.entidades.miempresa.Persona;
import com.syswave.entidades.miempresa.PersonaSalario;
import com.syswave.entidades.miempresa.SalarioComponente;
import java.util.List;

/**
 *
 * @author sis5
 */
public interface IPersonaSalarioMediator
{
    void onAcceptModifyElement(PersonaSalario elemento, List<SalarioComponente> nuevosComponentes, List<SalarioComponente> eliminados);
    void onAcceptNewElement(PersonaSalario nuevo, List<SalarioComponente> nuevosComponentes);
    List<Persona> obtenerListaPersonas();
    List<Moneda> obtenerListaMonedas();
    List<SalarioComponente> obtenerSalarioComponentes(SalarioComponente filtro);
}
