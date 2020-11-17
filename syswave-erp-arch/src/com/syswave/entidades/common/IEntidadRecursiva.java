package com.syswave.entidades.common;

/**
 * Esta clase como mediador ayuda a diferenciar las entidades que son arboles.
 * @author Victor Manuel Bucio Vargas
 */
public interface IEntidadRecursiva extends IEntidad
{
   Integer getIdPadre();
   Integer getNivel();
}
