package com.syswave.forms.miempresa;

import com.syswave.entidades.miempresa.Horario;
import com.syswave.entidades.miempresa.Jornada;
import java.util.List;

/**
 *
 * @author sis5
 */
public interface IJornadaMediator {
    void showDetail(Jornada elemento);
    void onAcceptNewElement(Jornada nuevo, List<Horario> listHorarios);
    void onAcceptModifyElement(Jornada modificado, List<Horario> listHorarios, List<Horario> eliminados);
    List<Jornada> obtenerJornada(Jornada elemento);
    List<Horario> obtenerHorarios(Jornada elemento);
    String obtenerOrigenDato();
}
