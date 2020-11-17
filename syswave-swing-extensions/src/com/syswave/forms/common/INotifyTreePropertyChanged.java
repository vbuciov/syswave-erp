package com.syswave.forms.common;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public interface INotifyTreePropertyChanged<T>
{
   void onModifiedAdded(T element);
   boolean canAddToModified (T element);
}
