/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.syswave.entidades.common;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public interface IEntidad
{
    //--------------------------------------------------------------------
    void setNew ();
    
    //--------------------------------------------------------------------
    void setModified ();
    
    //--------------------------------------------------------------------
    void setDeleted ();
    
    //--------------------------------------------------------------------
    void acceptChanges ();
    
    //--------------------------------------------------------------------
    boolean isNew ();
    
    //--------------------------------------------------------------------
    boolean isModified ();
    
    //--------------------------------------------------------------------
    boolean isDeleted ();
    
    //--------------------------------------------------------------------
    boolean isSet ();
    
    //--------------------------------------------------------------------
    void clear ();
    
    //--------------------------------------------------------------------
    boolean isSelected ();
    
    //--------------------------------------------------------------------
    void setSelected (boolean value);
}
