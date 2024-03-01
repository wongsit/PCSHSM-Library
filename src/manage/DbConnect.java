/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author khanchaiwongsit
 */
public class DbConnect {
    //private int id;
    //private String host;
    //private String databasename;
    //private String username;
    //private String password;
   // private String url;
    /*
    public DbConnect(int ID, String Host,String DatabaseName, String Username,String Password){
        this.id = ID;
        this.host = Host;
        this.databasename = DatabaseName;
        this.username = Username;
        this.password = Password;
    }
     public int getID(){
         return id;
     }
     
     private String getHost(){
         return host;
     }
     
      private String getDatabaseName(){
          return databasename;
      }
      
      private String getUsername(){
          return username;
      }
      
      private String getPassword(){
          return password;
      }
      */
      
    public static Connection getConnection() {
        String[] data;
        data = new String[4];
       String path = "D:\\connect.txt";
        File file = new File(path);
        try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;
                int i=0;
                while ((line = br.readLine()) != null) {
                        System.out.println(line);
                        data[i]=line;
                        i++;
                }
                br.close();
        } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        }
        /*
        Connection con;
        String host ="localhost";                      //IP Server
        String databasename = "smartLib";      //Datatbase
        String username = "root";                    //username
        String password = "12345678";             //Password
         //for Server extend
        String host ="159.192.103.100";            //IP Server
         String databasename = "smartLib";     //Datatbase
         String username = "khanchai";           //username
         String password = "griffinics";              //Password
         */
        Connection con;
         String host =data[0];                      //IP Server
        String databasename = data[1];      //Datatbase
        String username = data[2];                    //username
        String password =data[3];             //Password
         String url = "jdbc:mysql://" + host + "/" + databasename + "?useUnicode=true&characterEncoding=UTF-8";
        try{
            Class.forName(com.mysql.jdbc.Driver.class.getName());
            con = DriverManager.getConnection(url, username,password);
            return con;
        }catch(ClassNotFoundException e){
                e.printStackTrace();
            return null;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
