/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package res;

import java.io.Serializable;
import java.net.Socket;

/**
 *
 * @author define
 */
public class ClientDetails implements Serializable{
        public String id;
        public String name;
        public transient Socket client;
}
