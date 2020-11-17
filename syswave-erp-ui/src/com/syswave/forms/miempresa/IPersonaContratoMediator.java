package com.syswave.forms.miempresa;

import com.syswave.entidades.miempresa.PersonaContrato;
import com.syswave.forms.databinding.AreaComboBoxModel;
import com.syswave.forms.databinding.JornadaComboBoxModel;
import com.syswave.forms.databinding.PersonalComboBoxModel;
import com.syswave.forms.databinding.PuestoComboBoxModel;

/**
 *
 * @author sis5
 */
public interface IPersonaContratoMediator
{
    void onAcceptModifyElement(PersonaContrato elemento);

    void onAcceptNewElement(PersonaContrato nuevo);
    
    PersonalComboBoxModel obtenerComboPersonas();
    AreaComboBoxModel obtenerComboAreas();
    PuestoComboBoxModel obtenerComboPuestos();
    JornadaComboBoxModel obtenerComboJornadas();
    
    String obtenerOrigenDato();
}
