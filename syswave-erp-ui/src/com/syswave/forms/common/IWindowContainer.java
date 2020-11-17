/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.syswave.forms.common;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;

/**
 * Esta interfaz representa a la ventana principal que contiene el JDesktopPane
 * @author Victor Manuel Bucio Vargas
 */
public interface IWindowContainer extends ISessionContainer
{
       //---------------------------------------------------------------------
   void addWindow (JInternalFrame dialog);
   
   //---------------------------------------------------------------------
   //void prepareCenter(JInternalFrame dialog);
   
   //---------------------------------------------------------------------
   void showCenter (JInternalFrame dialog);
   
   //---------------------------------------------------------------------
   JFrame getRootFrame();
}
