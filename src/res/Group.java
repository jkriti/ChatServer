/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package res;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author define
 */
public class Group implements Serializable{
    public ClientDetails admin;
    public String title;
    public String desc;
    public String created_on;
    public ArrayList<ClientDetails> active;
    public ArrayList<ChatDetails> chat;
    public ArrayList<ClientDetails> requests;
    
}
