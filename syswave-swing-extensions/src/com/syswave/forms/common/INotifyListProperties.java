package com.syswave.forms.common;

/**
 * Permite indicar las propiedades que serán utilizadas de un POJO, en un JList.
 * @author Victor Manuel Bucio Vargas
 * @param <T>
 */
public interface INotifyListProperties<T>
{
   String onGetText(T item);
   void onSetText (T item, String value);
   Object onGetValue (T item);
}
