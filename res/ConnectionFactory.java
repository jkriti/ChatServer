/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package res;
import java.sql.*;
import java.util.*;
/**
 *
 * @author DELL
 */
public class ConnectionFactory {
    private static ConnectionFactory instance;
    private Connection con;
    private Statement stmt;
    private ConnectionFactory(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            this.con=DriverManager.getConnection("jdbc:mysql://localhost/chat_room","root","");
            this.stmt=con.createStatement();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    static{
        instance=new ConnectionFactory();
    }
    public static ConnectionFactory getInstance(){
        return instance;
    }
    
    public  ResultSet getResultSet(String query)throws Exception{
        ResultSet rs=this.stmt.executeQuery(query);
        return rs;
    }
    public int setData(String query)throws Exception{
        int n=stmt.executeUpdate(query);
        return n;
    }
    public ArrayList<ArrayList> getData(String query)throws Exception{
        ResultSet rs=this.stmt.executeQuery(query);
        ArrayList<ArrayList> main=new ArrayList<ArrayList>();
        ResultSetMetaData rsmd=rs.getMetaData();
        int count=rsmd.getColumnCount();
        while(rs.next()){
            ArrayList<String> sub=new ArrayList<String>();
            for(int i=1;i<=count;i++)
                sub.add(rs.getString(i));
            main.add(sub);
        }
        return main;
    }
}














