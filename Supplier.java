/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author chong
 */
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class Supplier {
    private String supplierID, supplierName;
    
    public Supplier(String Name){
    }
    public Supplier(String supplierID, String supplierName){
        this.supplierID = supplierID;
        this.supplierName = supplierName;
    }
    
    public void getsupplierID(String supplierID){
        this.supplierID = supplierID;
    }
    
    public String setsupplierID(){
        return supplierID;
    }
    
    public void getsupplierName(String supplierName){
        this.supplierName = supplierName;
    }
    
    public String setsupplierName(){
        return supplierName;
    }
    private List<String> readSuppliersFromFile() {
        List<String> suppliers = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("C:\\Users\\chong\\OneDrive\\Desktop\\java\\supplier.txt"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                suppliers.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return suppliers;
    }
     
    // Write suppliers to file
    private void writeSuppliersToFile(List<String> suppliers) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("C:\\Users\\chong\\OneDrive\\Desktop\\java\\supplier.txt"))) {
            for (String supplier : suppliers) {
                bufferedWriter.write(supplier);
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void viewSupplier(){
        List<String> suppliers = readSuppliersFromFile();
        
        System.out.println("Supplier List");
        for (String Supplier : suppliers)
            System.out.println(Supplier);
    }
    public void addSupplier() {
        // Read the current suppliers from the file
        List<String> suppliers = readSuppliersFromFile();

        Scanner scanner = new Scanner(System.in);
        viewSupplier();
        // Prompt the user for the new supplier's details
        System.out.print("Enter Supplier ID: ");
        String ID = scanner.nextLine();

        // Check if the supplier ID already exists
        for (String supplierData : suppliers) {
            String[] parts = supplierData.split(",");
            if (parts.length >= 1 && parts[0].equalsIgnoreCase(ID)) {
                System.out.println("Supplier with ID " + ID + " already exists. Cannot add duplicate.");
                return;
            }
        }

        System.out.print("Enter Supplier Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Item IDs supplied by the supplier (comma-separated): ");
        String itemIDs = scanner.nextLine();

        // Create a string representation of the new supplier
        String newSupplier = ID + "," + name + "," + itemIDs;

        // Add the new supplier to the list
        suppliers.add(newSupplier);

        // Save the updated data
        writeSuppliersToFile(suppliers);

        System.out.println("Supplier added successfully.");
}

    public void editSupplier() {
        List<String> suppliers = readSuppliersFromFile();
        viewSupplier();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the ID of the supplier you want to edit: ");
        String editID = scanner.nextLine();

        // Find the supplier to edit
        int supplierIndex = -1;
        for (int i = 0; i < suppliers.size(); i++) {
            if (suppliers.get(i).startsWith(editID + ",")) {
                supplierIndex = i;
                break;
            }
        }

        if (supplierIndex == -1) {
            System.out.println("Supplier with ID " + editID + " not found.");
            return;
        }

        // Ask the user what information they want to modify
        System.out.println("What information would you like to modify?");
        System.out.println("1. Supplier ID");
        System.out.println("2. Supplier Name");
        System.out.println("3. Item IDs supplied by the supplier");
        System.out.print("Enter the number of your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        // Ask for the new value based on the user's choice
        String updatedInfo;
        switch (choice) {
            case 1:
                System.out.print("Enter the new supplier ID: ");
                updatedInfo = scanner.nextLine();
                break;
            case 2:
                System.out.print("Enter the new supplier name: ");
                updatedInfo = scanner.nextLine();
                break;
            case 3:
                System.out.print("Enter the new item IDs supplied by the supplier (comma-separated): ");
                updatedInfo = scanner.nextLine();
                break;
            default:
                System.out.println("Invalid choice.");
                return;
        }

        // Update the supplier's information in memory
        String[] parts = suppliers.get(supplierIndex).split(",");
        if (choice == 3) {
            // If editing item IDs, replace everything after the third part (index 2)
            for (int i = 2; i < parts.length; i++) {
                parts[i] = "";
            }
            // Set the new item IDs
            String[] newItemIDs = updatedInfo.split(",");
            for (int i = 0; i < newItemIDs.length; i++) {
                parts[i + 2] = newItemIDs[i];
            }
        } else {
            // For other choices, update the corresponding part based on choice
            if (parts.length >= choice) {
                parts[choice - 1] = updatedInfo;
            } else {
                System.out.println("Invalid choice.");
                return;
            }
        }

        // Join the parts back together and update the supplier's information
        suppliers.set(supplierIndex, String.join(",", parts));
        
        // Save the updated data
        writeSuppliersToFile(suppliers);

        System.out.println("Supplier updated successfully.");
}

    public void deleteSupplier() {
        // Read the data from the supplier.txt file
        List<String> suppliers = readSuppliersFromFile();
        viewSupplier();
        // Ask the user for the supplier ID to delete
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the ID of the supplier you want to delete: ");
        String deleteID = scanner.nextLine();

        // Find the supplier to delete
        int supplierIndex = -1;
        for (int i = 0; i < suppliers.size(); i++) {
            if (suppliers.get(i).startsWith(deleteID + ",")) {
                supplierIndex = i;
                break;
            }
        }

        if (supplierIndex == -1) {
            System.out.println("Supplier with ID " + deleteID + " not found.");
            return;
        }

        // Remove the supplier from the list
        suppliers.remove(supplierIndex);

        // Write the updated data back to the supplier.txt file
        writeSuppliersToFile(suppliers);

        System.out.println("Supplier deleted successfully.");
    }
}
