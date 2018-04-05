/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package res;

import java.util.ArrayList;
import java.util.Calendar;
import serverapp.ServerWin;

/**
 *
 * @author define
 */
public class CommRes {
    public static ServerWin serverwin;
    public static ArrayList<ClientDetails> onlineUsers;
    public static ArrayList<Group> groups;
    
    public static String getDateTime(Calendar calendar){
        String datetime="";
        datetime=calendar.get(Calendar.DATE) + "/" +
                 calendar.get(Calendar.MONTH) + "/" +
                 calendar.get(Calendar.YEAR) + " " +
                 calendar.get(Calendar.HOUR) + ":" +
                 calendar.get(Calendar.MINUTE);
        return datetime;
    }
    
}
