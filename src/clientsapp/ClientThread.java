/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientsapp;

/**
 *
 * @author User
 */
import java.net.*;
import java.io.*;

/**
 *
 * @author define
 */
public class ClientThread extends Thread{
    private Socket client;
    public ClientThread(Socket client){
        this.client=client;
        this.start();
    }
    
    public void run(){
        try{
            while(true){
                ObjectInputStream in=new ObjectInputStream(this.client.getInputStream());
                String req=in.readObject().toString();
                if(req.equals("")){
                    
                }
                if(req.equals("")){
                    
                }
                if(req.equals("")){
                    
                }
                if(req.equals("")){
                    
                }
                if(req.equals("")){
                    
                }
            }
        }catch(Exception ex){
            
        }
    }
}
