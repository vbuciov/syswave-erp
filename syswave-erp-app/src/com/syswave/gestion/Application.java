package com.syswave.gestion;

import com.syswave.datos.common.DataSourceManager;
import datalayer.api.IMediatorDataSource;


/**
 * Sistema ERP
 * 
 * @author Victor Manuel Bucio Vargas
 */
public class Application extends Thread
{
   private static LoginJFrame Inicio;
   
   //---------------------------------------------------------------------
   /**
    * @param args the command line arguments
    */
   public static void main(String[] args)
   {
      // TODO code application logic here
      Application gestion = new Application();
      gestion.EnableVisualStyles();
      gestion.checkConnection();
      gestion.start();
   }
   
   //---------------------------------------------------------------------
   public void EnableVisualStyles()
   {
      /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
       * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
       */
      //</editor-fold>
      try
      {
       
         for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
         {
            if ("Nimbus".equals(info.getName()))
               {
               javax.swing.UIManager.setLookAndFeel(info.getClassName());
               break;
            }
         }
      }
      catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex)
      {
         java.util.logging.Logger.getLogger(LoginJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
      }

   }

   //---------------------------------------------------------------------
   @Override
   public void run()
   {
      super.run(); //To change body of generated methods, choose Tools | Templates.
      Inicio = new LoginJFrame();
      Inicio.setVisible(true);
      Inicio.cargarRecursos();
      Inicio.setTitle("Inicio de sessión");
   }
   
   //---------------------------------------------------------------------
   /*public static LoginJFrame getInicio()
   {
      return Inicio;
   }*/
   
   //---------------------------------------------------------------------
   public static void restart()
   {
      Inicio.setVisible(true);
   }
      
   //---------------------------------------------------------------------
   public static void terminate ()
   {
      System.exit(0);
   }
   
   //---------------------------------------------------------------------
   public void checkConnection()
   {
       long start, end, res;
       start = System.currentTimeMillis();
       IMediatorDataSource source = DataSourceManager.getMainDataSourceInstance();

       if (source == null || !source.Connect())    
       {
            System.out.println("No fue posible conectarse con el servidor");
            System.out.println("Revise los parametros de conexion en el archivo main-settings.xml");
       }
        else
        {
            end = System.currentTimeMillis();
            res = end - start;
            System.out.println("Segundos En conexion: "+(res/1000.0) % 60 );
            System.out.println("Conexion establecida exitosamente");
        }        
   }
}