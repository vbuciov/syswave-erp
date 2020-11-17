package com.syswave.forms.common;

/**
 * Establece las operaciones que debe contemplar un catalogo.
 * @author Victor Manuel Bucio Vargas
 */
public interface ICatalogOperations 
{
   void doCreateProcess();
   void doUpdateProcess();
   void doDeleteProcess();
   void doRetrieveProcess();
   void doSaveProcess();
   void doOpenProcess(); 
   //void doExport(String fileName);
   boolean isThereChanges();
   void acceptChanges ();   
   void setChanges ();
   void goToFirst();
   void goToLast();
   void goToNext();
   void goToPrior();
   void doFilter(boolean value);
   void selectAll();
   void doPrint();
   void doExportToXLS();
   void doStartSearch();
   void doNextSearch();
}
