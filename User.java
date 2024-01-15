
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author chong
 */


public class User {
    private String userID, userPW, email, userType;
    
    
    public User(String userID, String userPW, String email, String userType){
        this.userID = userID;
        this.userPW = userPW;
        this.email = email;
        this.userType = userType;
        
    }
    
    
    public void setuserID(String userID){
        this.userID = userID;
    }
    public String getuserID(){
        return userID;
    }
        
    public void setuserPW(String userPW){
        this.userPW = userPW;
    }
    public String getuserPW(){
        return userPW;
    }
    public void setemail(String email){
        this.email = email;
    }
    public String getemail(){
        return email;
    }
    public void setuserType(String userType){
        this.userType = userType;
    }
    public String getuserType(){
        return userType;
    }
    
    
    public boolean login(String userID, String userPW){
        try {
            FileReader fr = new FileReader("C:\\Users\\chong\\OneDrive\\Desktop\\java\\usercredentials.txt");
            BufferedReader br = new BufferedReader(fr);

            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length == 4) {
                    String storedUsername = values[0];
                    String storedPassword = values[1];
                    String storedUserType = values[3];

                    if (userID.equals(storedUsername) && userPW.equals(storedPassword)) {
                        // Successful login
                        this.userID = userID;
                        this.userPW = userPW;
                        this.userType = storedUserType;
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
         return false;
    }

}
