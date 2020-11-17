package com.syswave.forms.miempresa;

import com.syswave.entidades.miempresa.Horario;
import com.syswave.entidades.miempresa.Jornada;
import java.util.List;

/**
 *
 * @author sis5
 */
public interface IHorarioMediator
{
    void showDetail(Horario elemento);
    void onAcceptNewElement(Horario nuevo);
    void onAcceptModifyElement(Horario modificado);
    List<Horario> obtenerHorario(Horario elemento);
    List<Jornada> obtenerJornadas(); 
    String obtenerOrigenDato();
}
