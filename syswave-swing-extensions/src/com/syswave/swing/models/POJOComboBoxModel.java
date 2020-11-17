package com.syswave.swing.models;

import com.syswave.forms.common.INotifyListProperties;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.MutableComboBoxModel;

/**
 * Permite utilizar colecciones de objetos de negocios para ser dibujados en un comboBox.
 * @author Victor Manuel Bucio Vargas
 * @param <E>
 */
public abstract class POJOComboBoxModel<E> extends AbstractListModel<E> implements MutableComboBoxModel<E>, INotifyListProperties<E>, Serializable
{
   private List<E> data;
   private E selected;
  
   //---------------------------------------------------------------------
   public POJOComboBoxModel()
   {
      data = new ArrayList<E>();
      selected = null;
   }
   
   //---------------------------------------------------------------------
   public POJOComboBoxModel(List<E> dataSource) 
   {
        data = dataSource;

        if ( data.size()> 0 ) {
            selected = data.get( 0 );
        }
    }

   //---------------------------------------------------------------------
   @Override
   public void setSelectedItem(Object anObject)
   {
      //El objeto se rechaza cuando
      if ( anObject == null || data.isEmpty() || anObject instanceof String /*|| !data.contains(anObject)*/)
      {
         selected = null;
         fireContentsChanged(this, -1, -1);
      }
      
      //Si el objeto recibido es una cadena, estamos hablando de un cambio de texto.
      /*else if (anObject instanceof String)
      {
         if (selected != null)
         {
            onSetText(selected, (String)anObject);
            fireContentsChanged(this, -1, -1);
         }
      }*/

      //Cuando no se ha seleccionado un elemento previo o el elemento es distito al actual.
      else if ( selected == null || !selected.equals(anObject))
      {
         selected = data.get(data.indexOf(anObject));
         fireContentsChanged(this, -1, -1);
      }
   }
    
   //---------------------------------------------------------------------
   /**
    * Obtiene el elemento seleccionado, debería ser utilizado unicamente por un ListRender.
    */
   @Override
   public Object getSelectedItem()
   {
      return selected;
   }
   
   //---------------------------------------------------------------------
   /**
    * Obtiene el texto asociado al elemento actualmente seleccionado.
    * @return 
    */
   public String getSelectedText()
   {
      return selected != null ? onGetText(selected):"";
   }
 
   //---------------------------------------------------------------------
   /**
    * Obtiene el valor asociado al elemento actualmente seleccionado.
    * @return 
    */
   public Object getSelectedValue ()
   {
     return selected != null ? onGetValue(selected):null;
   }
   
   //---------------------------------------------------------------------
   /**
    * Establece el elemento seleccionado a traves de la propiedad asociada al valor.
    * @param value
    */
   /*public void setSelectedValue (Object value)
   {
      if ( value == null || data.isEmpty() )
      {
         selected = null;
         fireContentsChanged(this, -1, -1);
      }

      //Cuando no se ha seleccionado un elemento previo o el elemento es distito al actual.
      else if ( selected == null || ! onGetValue(selected).equals(value))
      {
         selected = data.get(indexOfValue(value));
         fireContentsChanged(this, -1, -1);
      }
   }*/
   
   //---------------------------------------------------------------------
   /**
    * Obtiene el elemento seleccionado para uso del usuario.
    * @return 
    */
   public E getCurrent ()
   {
      return selected;
   }
   
   //---------------------------------------------------------------------
   /**
    * Devuelte la cantidad de elementos.
    */
   @Override
   public int getSize()
   {
      return data.size();
   }
   
   //---------------------------------------------------------------------
   /**
    * Devuelve el elemento de la posición especificada.
    */
   @Override
   public E getElementAt(int index)
   {
      if (index >= 0 && index < data.size())
         return data.get(index);
      else
         return null;
   }

   //---------------------------------------------------------------------
   /**
    * Returns the index-position of the specified object in the list.
    *
    * @param anObject
    * @return an int representing the index position, where 0 is the first
    * position
    */
   public int getIndexOf(Object anObject)
   {
      return data.indexOf(anObject);
   }
   
      //---------------------------------------------------------------------
   /**
    * Returns the index-position of the specified by value property of object in the list.
    *
    * @param toFind
    * @return an int representing the index position, where 0 is the first
    * position
    */

   public int indexOfValue(Object toFind)
   {
      int index = -1;
      boolean isFind = false;
      int i=0;
      
      while (!isFind && i < data.size())
      {
         isFind = onGetValue(data.get(i)).equals(toFind);
         if (isFind)
            index = i;
         else
            i++;  
      }
      
      return index;
   }

  //---------------------------------------------------------------------
   @Override
   public void addElement(E anObject)
   {
      if (anObject != null)
      {
         data.add(anObject);
         fireIntervalAdded(this, data.size() - 1, data.size() - 1);

         if (data.size() == 1 && selected == null)
            setSelectedItem(anObject);
      }
   }

   //---------------------------------------------------------------------
   // implements javax.swing.MutableComboBoxModel
   @Override
   public void insertElementAt(E anObject, int index)
   {
      data.add(index, anObject);
      fireIntervalAdded(this, index, index);
   }

   //---------------------------------------------------------------------
   // implements javax.swing.MutableComboBoxModel
   @Override
   public void removeElementAt(int index)
   {
      if (getElementAt(index) == selected)
      {
         if (index == 0)
            setSelectedItem(getSize() == 1 ? null : getElementAt(index + 1));

         else
            setSelectedItem(getElementAt(index - 1));
      }

      data.remove(index);

      fireIntervalRemoved(this, index, index);
   }

   //---------------------------------------------------------------------
   // implements javax.swing.MutableComboBoxModel
   @Override
   public void removeElement(Object anObject)
   {
      int index = data.indexOf(anObject);
      
      if (index != -1)
         removeElementAt(index);
   }

   //---------------------------------------------------------------------
   /**
    * Empties the list.
    */
   public void removeAllElements()
   {
      if (data.size() > 0)
      {
         int firstIndex = 0;
         int lastIndex = data.size() - 1;
         data.clear();
         selected = null;
         fireIntervalRemoved(this, firstIndex, lastIndex);
      }
      else
         selected = null;
   }
   
    //---------------------------------------------------------------------
   /**
    * Obtiene la colección de elementos mostrados por el comboBox.
    * @return Lista de elementos 
   **/
   public List<E> getData()
   {
      return data;
   }

   //---------------------------------------------------------------------
   /**
    * Establece los elementos que serán mostrados por el comboBox.
    * @param dataSource Datos
    */
   public void setData(List<E> dataSource)
   {
      if (data != null && data.size() > 0)
      {
         int firstIndex = 0;
         int lastIndex = data.size() - 1;
         selected = null;
         fireIntervalRemoved(this, firstIndex, lastIndex);
      }
 
      this.data = dataSource;
      if (data != null && data.size() > 0)
         fireIntervalAdded(this, 0, data.size() - 1);
   }
}