/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package serverapp;
import java.net.*;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author pc
 */
public class ServerApp {

    /**
     * @param args the command line arguments
     */
   // public static ServerWin serverWin;
    public static void main(String[] args) {
        // TODO code application logic here
        try{
            ServerSocket server=new ServerSocket(3344);
            res.CommRes.onlineUsers=new ArrayList<res.ClientDetails>();
            res.CommRes.groups=new ArrayList<res.Group>();
            try {
                    for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                        if ("Nimbus".equals(info.getName())) {
                            javax.swing.UIManager.setLookAndFeel(info.getClassName());
                            break;
                        }
                    }
                } catch (ClassNotFoundException ex) {
                    java.util.logging.Logger.getLogger(ServerWin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                    java.util.logging.Logger.getLogger(ServerWin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    java.util.logging.Logger.getLogger(ServerWin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                    java.util.logging.Logger.getLogger(ServerWin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
                //</editor-fold>

                /* Create and display the form */
                java.awt.EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        res.CommRes.serverwin =new ServerWin();
                        res.CommRes.serverwin.setVisible(true);
                    }
                });
         
                while(true){
                    Socket client=server.accept();
                    new ClientHandler(client);
                }
        }catch(Exception ex){
            
        }
        
        
        
    }
    
}
