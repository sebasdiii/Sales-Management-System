
import java.util.Scanner;
import java.io.*;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

/**
 *
 * @author chong
 */

/*public class NewMain {
    public static void main(String[] args) throws IOException{
        Scanner scanner = new Scanner(System.in);
        User user = new User("initialUserID", "initialUserPW", "initialemail","initialUserType");

        boolean loginSuccessful = false;

        while (!loginSuccessful) {
            System.out.println("Enter User ID (or '0' to exit):");
            String username = scanner.nextLine();

            if (username.equalsIgnoreCase("0")) {
                System.out.println("Exiting...");
                break;
            }
            System.out.println("Enter User Password:");
            String password = scanner.nextLine();

            loginSuccessful = user.login(username, password);
            menu m = new menu();
            if (loginSuccessful) {
                System.out.println("Login successful! Welcome, " + user.getuserType() + "!");

                if ("ADMINISTRATOR".equals(user.getuserType())) {
                    m.adminpage();
                } else if ("SALESMANAGER".equals(user.getuserType())) {
                    m.salesmanagerpage(); // Call the salesManagerPage method
                } else if ("PURCHASEMANAGER".equals(user.getuserType())) {   
                    m.purchasemanagerpage();
                }
            } else {
                System.out.println("Invalid credentials. Please try again.");
            }
        }
    }
}*/

public class NewMain {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        
        while (true) { // Wrap the login and menu logic in a loop
            User user = new User("initialUserID", "initialUserPW", "initialemail", "initialUserType");

            boolean loginSuccessful = false;

            while (!loginSuccessful) {
                System.out.print("""
                            ===============================================
                            LOGIN PAGE
                            ===============================================
                            """);
                System.out.println("Enter User ID (or '0' to exit):");
                String username = scanner.nextLine();

                if (username.equalsIgnoreCase("0")) {
                    System.out.println("Exiting...");
                    return; // Exit the program if the user chooses to exit
                }
                System.out.println("Enter User Password:");
                String password = scanner.nextLine();

                loginSuccessful = user.login(username, password);
                menu m = new menu();
                if (loginSuccessful) {
                    System.out.println("Login successful! Welcome, " + user.getuserType() + "!");

                    if ("ADMINISTRATOR".equals(user.getuserType())) {
                        m.adminpage();
                    } else if ("SALESMANAGER".equals(user.getuserType())) {
                        m.salesmanagerpage(); // Call the salesManagerPage method
                    } else if ("PURCHASEMANAGER".equals(user.getuserType())) {
                        m.purchasemanagerpage();
                    }
                } else {
                    System.out.println("Invalid credentials. Please try again.");
                }
            }
        }
    }
}




        
        
    

       

        

    
    

