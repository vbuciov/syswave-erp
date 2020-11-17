package com.syswave.forms.common;

/**
 * Permite indicar las propiedades que serán utilizadas de un POJO, en un JTree.
 * @author Victor Manuel Bucio Vargas
 * @param <E>
 */
public interface INotifyTreeProperties<E>
{
   String onGetText(E item);
   void onSetText (E item, String value);
   Object onGetIDValue (E item);
   boolean onGetChecked (E element);
   void onSetChecked(E element, boolean value);
   Object onGetIDParentValue (E element);
   int onGetLevel (E element);
   boolean greaterThan(Object current_node, Object value);
   boolean lessThan(Object current_node, Object value);
   Object getNullParent();
}
