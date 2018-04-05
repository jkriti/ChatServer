/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientsapp;

import java.io.ObjectInputStream;
import java.util.Vector;
import javax.swing.JOptionPane;
import res.ChatDetails;
import res.ClientDetails;
import res.Group;

/**
 *
 * @author define
 */
public class ClientSideThread extends Thread{
    ChatWindow win;
    
    public ClientSideThread(ChatWindow win){
        this.win=win;
        start();
    }
    
    public void run(){
        try{
            while(true){
                ObjectInputStream in=new ObjectInputStream(res.CommRes.client.getInputStream());
                String resp=in.readObject().toString();
                
                if(resp.equals("NewGroup")){
                    Group group=(Group)in.readObject();
                    res.CommRes.groups.add(group);
                    win.addNewGroup(group);
                                       //display accordingly
                }
                if(resp.equals("JoinRequest")){
                    int index=Integer.parseInt(in.readObject().toString());
                    ClientDetails user=(ClientDetails)in.readObject();
                    res.CommRes.groups.get(index).requests.add(user);
                    if(index==this.win.getGroupId()){
                        Vector listData=new Vector<String>();
                        for(int i=0;i<res.CommRes.groups.get(index).requests.size();i++){
                            ClientDetails details=res.CommRes.groups.get(index).requests.get(i);
                            listData.add(details.name);
                        }
                        this.win.setRequestList(listData);
                    }
                }
                if(resp.equals("Accept")){
                    int index=Integer.parseInt(in.readObject().toString());
                    Group group=(Group)in.readObject();
                    res.CommRes.groups.remove(index);
                    res.CommRes.groups.add(index, group);
                    if(index==this.win.getGroupId()){
                        Vector listData=new Vector<String>();
                        for(int i=0;i<res.CommRes.groups.get(index).requests.size();i++){
                            ClientDetails details=res.CommRes.groups.get(index).requests.get(i);
                            listData.add(details.name);
                        }
                        this.win.setRequestList(listData);
                        
                        listData=new Vector<String>();
                        for(int i=0;i<res.CommRes.groups.get(index).active.size();i++){
                            ClientDetails details=res.CommRes.groups.get(index).active.get(i);
                            listData.add(details.name);
                        }
                        this.win.setActiveList(listData);
                    }
                    
                }
                
                if(resp.equals("ChatMesg")){
                    int index=Integer.parseInt(in.readObject().toString());
                    ChatDetails cdetails=(ChatDetails)in.readObject();
                    res.CommRes.groups.get(index).chat.add(cdetails);
                    if(this.win.getGroupId()==index){
                        this.win.updateChatTable(cdetails);
                    }
                    
                }
                
                if(resp.equals("GroupDel")){
                    int index=Integer.parseInt(in.readObject().toString());
                    res.CommRes.groups.remove(index);
                    //display accordingly
                }
                
                if(resp.equals("GroupExit")){
                    int index=Integer.parseInt(in.readObject().toString());
                    int member_id=Integer.parseInt(in.readObject().toString());
                    Group group=res.CommRes.groups.get(index);
                    for(int i=0;i<group.active.size();i++){
                    if(group.active.get(i).id.equals(member_id)){
                        res.CommRes.groups.get(index).active.remove(i);
                        break;
                    }
                   }
                   Vector listData=new Vector<String>();
                        for(int i=0;i<group.active.size();i++){
                            ClientDetails details=group.active.get(i);
                            listData.add(details.name);
                        }
                        this.win.setActiveList(listData); 
                    //display accordingly
                }
            }
        }catch(Exception ex){
            JOptionPane.showMessageDialog(win, "Error on client end :" + ex, "Client Side Thread", JOptionPane.ERROR_MESSAGE);
            System.out.println ("\nError on client Side Thread");
            ex.printStackTrace();
        }
    }
    
}
