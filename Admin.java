
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author chong
 */
public class Admin extends User {
    
    public Admin(){
       super("", "", "", "");
    }
    
    public Admin(String userID, String userPW, String email, String userType) {
        super(userID, userPW, email, userType);
        
    }
    public void delete() throws IOException {
            try {
                FileReader fr = new FileReader("C:\\Users\\chong\\OneDrive\\Desktop\\java\\usercredentials.txt");
                BufferedReader br = new BufferedReader(fr);

                StringBuilder fileContent = new StringBuilder();
                String line;

                Scanner scanner = new Scanner(System.in);
                System.out.println("Enter User ID to delete:");
                String userIDToDelete = scanner.nextLine();

                boolean found = false;

                while ((line = br.readLine()) != null) {
                    String values[] = line.split(",");
                    if (values.length == 4) {
                        String UserID = values[0];

                        if (UserID.equals(userIDToDelete)) {
                            found = true;

                            System.out.println("Account found:");
                            System.out.println("User ID: " + UserID);
                            System.out.println("User Role: " + values[3]);

                            System.out.println("Are you sure you want to delete this account? (Y/N)");
                            String confirmation = scanner.nextLine();

                            if (confirmation.equalsIgnoreCase("Y")) {
                                System.out.println("Account deleted.");
                            } else {
                                System.out.println("Deletion canceled.");
                                fileContent.append(line).append("\n");
                            }
                        } else {
                            fileContent.append(line).append("\n");
                        }
                    }
                }

                if (!found) {
                    System.out.println("Account not found.");
            } else {
                // Write back to the file after deletion
                FileWriter fw = new FileWriter("C:\\Users\\chong\\OneDrive\\Desktop\\java\\usercredentials.txt");
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(fileContent.toString());
                bw.close();
                fw.close();
            }
        } catch (IOException e) {
            System.out.println("An error occurred while deleting the account.");
        }
    }
    
    private static final String USERS_FILE = "C:\\Users\\chong\\OneDrive\\Desktop\\java\\usercredentials.txt";

    
    
   
    
    public void createUser() {
        User newUser = getUserDetails();
        if (newUser != null) {
            try (FileWriter fw = new FileWriter(USERS_FILE, true);
                 BufferedWriter bw = new BufferedWriter(fw)) {
                bw.write(newUser.getuserID() + "," + newUser.getuserPW() + "," + newUser.getemail()+ "," + newUser.getuserType());
                bw.newLine();
                System.out.println("User successfully added!");
            } catch (IOException e) {
                System.out.println("Error writing to file.");
            }
        }
    }

    private User getUserDetails() {
        Scanner sc = new Scanner(System.in);
        boolean userIdExists = true;
        String newUserId = "";

        while (userIdExists) {
            System.out.println("Enter user ID:");
            newUserId = sc.nextLine();
            userIdExists = checkUserIdExists(newUserId);
            if (userIdExists) {
                System.out.println("User ID already exists");
            }
        }
        System.out.println("Enter user password:");
        String newPassword = sc.nextLine();
        System.out.println("Enter user email:");
        String newUseremail = sc.nextLine();
        
        String newUserType = selectUserType(sc);

        return new User(newUserId, newPassword, newUseremail, newUserType);
    }
    private String selectUserType(Scanner sc) {
    System.out.println("Select user type:");
    System.out.println("1. Administrator");
    System.out.println("2. Sales Manager");
    System.out.println("3. Purchase Manager");
    System.out.print("Enter the number of your choice: ");
    
    int choice = sc.nextInt();
    sc.nextLine(); // Consume the newline character

    switch (choice) {
        case 1:
            return "ADMINISTRATOR";
        case 2:
            return "SALESMANAGER";
        case 3:
            return "PURCHASEMANAGER";
        default:
            System.out.println("Invalid choice. Defaulting to 'User'.");
            return "User";
    }
}






    private boolean checkUserIdExists(String userId) {
        File file = new File(USERS_FILE);

        try (Scanner check = new Scanner(file)) {
            while (check.hasNextLine()) {
                String line = check.nextLine();
                String[] data = line.split(",");
                if (data.length > 0 && data[0].equals(userId)) {
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + USERS_FILE);
        }
        return false;
    }
    
    
    public void viewUser() {
        List<User> users = getUsersFromFile();
        // Display the header
        System.out.println("USERID,PASSWORD,EMAIL,ROLE");
        for (User user : users) {
            System.out.println(user.getuserID() + "," + user.getuserPW() + "," + user.getemail()+","+user.getuserType());
        }
    }
    
     public void editUser() {
        viewUser();
        System.out.println("Enter user ID to edit:");
        Scanner sc = new Scanner(System.in);
        String userId = sc.nextLine();
        // Logic to find and edit user in USERS_FILE
        // For simplicity, a full user list will be read, edited in-memory, and then written back
        List<User> users = getUsersFromFile();
        User foundUser = null;
        for (User user : users) {
            if (user.getuserID().equals(userId)) {
                foundUser = user;
                break;
            }
        }
        if (foundUser == null) {
            System.out.println("User not found.");
            return;
        }
        User updatedUser = getUserDetails();
        users.remove(foundUser);
        users.add(updatedUser);
        saveUsersToFile(users);

    }

    private void saveUsersToFile(List<User> users) {
        try (FileWriter fw = new FileWriter(USERS_FILE);
             BufferedWriter bw = new BufferedWriter(fw)) {
            for (User user : users) {
                bw.write(user.getuserID() + "," + user.getuserPW() + "," + user.getemail()+","+user.getuserType());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to file.");
        }
    }

    private List<User> getUsersFromFile() {
        List<User> users = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String userID = parts[0];
                String userPW = parts[1];
                String useremail = parts[2];
                String usertype = parts[3];

                users.add(new User(userID,userPW,useremail,usertype));
            }
        } catch (IOException e) {
            System.out.println("Error reading from file.");
        }
        return users;
    }
    
}
