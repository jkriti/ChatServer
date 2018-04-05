/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverapp;

import java.net.*;
import java.io.*;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import res.ChatDetails;
import res.ClientDetails;
import res.Group;

/**
 *
 * @author define
 */
public class ClientHandler extends Thread{
    private Socket client;
    private String id,name;
    
    public ClientHandler(Socket client){
        this.client=client;
        this.start();
    }
    
    public void run(){
        try{
            while(true){
                ObjectInputStream in=new ObjectInputStream(this.client.getInputStream());
                String req=in.readObject().toString();
                if(req.equals("LoginDetails")){
                    String logid=in.readObject().toString();
                    String pass=in.readObject().toString(); 
                    String query="select  * from User_Master where LOGIN_ID='" + logid + "' and PASSWORD='" + pass + "'";
                    ResultSet rs=res.ConnectionFactory.getInstance().getResultSet(query);
                    ObjectOutputStream out=new ObjectOutputStream(this.client.getOutputStream());
                    
                    if(rs.next()){
                        this.id=rs.getInt(1) + "";
                        this.name=rs.getString(4);
                        out.writeObject("Success");
                        out.writeObject(this.id);
                        out.writeObject(this.name);
                        out.writeObject(res.CommRes.groups);
                        
                        
                        String status=name + " logged in at " + res.CommRes.getDateTime(Calendar.getInstance());
                        res.CommRes.serverwin.setNetworkStatus(status);
                        
                        res.ClientDetails details=new res.ClientDetails();
                        details.id=this.id;
                        details.client=this.client;
                        res.CommRes.onlineUsers.add(details);
                        
                        
                        
                    }else{
                        out.writeObject("Failed");
                    }
                }
                if(req.equals("NewGroup")){
                    String grpName=in.readObject().toString();
                    String grpDesc=in.readObject().toString();
                    
                    Group group=new Group();
                    group.admin=new ClientDetails();
                    group.admin.id= this.id;
                    group.admin.client=this.client;
                    group.admin.name=this.name;
                    group.title=grpName;
                    group.desc=grpDesc;
                    group.created_on=res.CommRes.getDateTime(Calendar.getInstance());
                    group.active=new ArrayList<ClientDetails>();
                    group.chat=new ArrayList<ChatDetails>();
                    group.requests=new ArrayList<ClientDetails>();
                    ClientDetails details=new ClientDetails();
                    details.id=this.id;
                    details.name=this.name;
                    details.client=this.client;
                    group.active.add(details);
                    
                    res.CommRes.groups.add(group);
                    
                    for(int i=0;i<res.CommRes.onlineUsers.size();i++){
                        ClientDetails cdetails=res.CommRes.onlineUsers.get(i);
                        ObjectOutputStream tmp=new ObjectOutputStream(cdetails.client.getOutputStream());
                        tmp.writeObject("NewGroup");
                        tmp.writeObject(group);
                    }
                    
                }
                if(req.equals("JoinRequest")){
                   int  index=Integer.parseInt(in.readObject().toString());
                    ClientDetails details=new ClientDetails();
                    details.id=this.id;
                    details.client=this.client;
                    details.name=this.name;
                    
                    Group group=res.CommRes.groups.get(index);
                    group.requests.add(details);
                    ObjectOutputStream out=new ObjectOutputStream(group.admin.client.getOutputStream());
                    out.writeObject("JoinRequest");
                    out.writeObject(index + "");
                    out.writeObject(details);
                }
                if(req.equals("accept")){
                   int grpindex=Integer.parseInt(in.readObject().toString()); 
                   int userindex=Integer.parseInt(in.readObject().toString());
                   Group group=res.CommRes.groups.get(grpindex);
                   ClientDetails details=group.requests.get(userindex);
                   group.active.add(details);
                   group.requests.remove(userindex);
                   for(int i=0;i<res.CommRes.onlineUsers.size();i++)
                   {
                    ClientDetails xdetails=res.CommRes.onlineUsers.get(i);
                    ObjectOutputStream tmp=new ObjectOutputStream(xdetails.client.getOutputStream());
                    tmp.writeObject("Accept");
                    tmp.writeObject(grpindex);
                    tmp.writeObject(group);
                   }
                }
                
                //deleting group
                
                if(req.equals("GroupDel")){
                    int index=Integer.parseInt(in.readObject().toString());
                   
                    res.CommRes.groups.remove(index);
                    for(int i=0;i<res.CommRes.onlineUsers.size();i++){
                        ClientDetails cdetails=res.CommRes.onlineUsers.get(i);
                        ObjectOutputStream tmp=new ObjectOutputStream(cdetails.client.getOutputStream());
                        tmp.writeObject("GroupDel");
                        tmp.writeObject(index+"");
                    }
                    
                }
                
                //exiting from group
                
                if(req.equals("GroupExit")){
                    int index=Integer.parseInt(in.readObject().toString());
                    int member_id=Integer.parseInt(this.id);
                    Group group=res.CommRes.groups.get(index);
                    for(int i=0;i<group.active.size();i++){
                      if(group.active.get(i).id.equals(member_id)){
                      int pos=i;
                      res.CommRes.groups.get(index).active.remove(pos);break;}}
                      for(int i=0;i<res.CommRes.onlineUsers.size();i++){
                        ClientDetails cdetails=res.CommRes.onlineUsers.get(i);
                        ObjectOutputStream tmp=new ObjectOutputStream(cdetails.client.getOutputStream());
                        tmp.writeObject("GroupExit");
                        tmp.writeObject(index+"");
                        tmp.writeObject(member_id+"");
                    }
                    
                }
                if(req.equals("ChatMesg")){
                    String mesg=in.readObject().toString();
                    int index=Integer.parseInt(in.readObject().toString());
                    Group group=res.CommRes.groups.get(index);
                    
                    ChatDetails cdetails=new ChatDetails();
                    cdetails.content=mesg;
                    cdetails.type="text";
                    cdetails.name=this.name;
                    cdetails.sent_on=res.CommRes.getDateTime(Calendar.getInstance());
                    group.chat.add(cdetails);
                    for(int i=0;i<group.active.size();i++)
                    {
                     ClientDetails xdetails=group.active.get(i);
                     ObjectOutputStream tmp=new ObjectOutputStream(xdetails.client.getOutputStream());
                     
                     tmp.writeObject("ChatMesg");
                     tmp.writeObject(index);
                     tmp.writeObject(cdetails);
                     
                    }
                }
            }
        }catch(Exception ex){
            
        }
    }
}
