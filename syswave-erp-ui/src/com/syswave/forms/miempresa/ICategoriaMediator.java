package com.syswave.forms.miempresa;

import com.syswave.entidades.miempresa.Categoria;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public interface ICategoriaMediator
{
      void onAcceptModifyElement(Categoria elemento);
   void onAcceptNewElement(Categoria nuevo);
   
}
