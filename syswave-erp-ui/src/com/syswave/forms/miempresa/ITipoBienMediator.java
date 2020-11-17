package com.syswave.forms.miempresa;

import com.syswave.entidades.miempresa.Bien;
import com.syswave.entidades.miempresa.BienVariante;
import com.syswave.entidades.miempresa.Categoria;
import com.syswave.entidades.miempresa.Valor;
import java.util.List;

/**
 *
 * @author victor
 */
public interface ITipoBienMediator
{
   void showDetail(Bien elemento);
   void onAcceptNewElement(Bien nuevo, List<BienVariante> variantes, List<BienVariante> variantes_borradas);
   void onAcceptModifyElement(Bien modificado, List<BienVariante> variantes, List<BienVariante> variantes_borradas);
   String obtenerOrigenDato();
   
   List<Valor> obtenerTipos ();
    List<Categoria> obtenerCategorias ();
    List<BienVariante> obtenerVariantes (Bien elemento);
}
