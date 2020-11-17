/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.syswave.forms.miempresa;

import com.syswave.entidades.miempresa.Condicion;

/**
 *
 * @author Aimee García
 */
public interface ICondicionesMediator {
    void onAcceptNewElement(Condicion nuevo);
    void onAcceptModifyElement(Condicion modificado);
    
}
