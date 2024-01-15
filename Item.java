
import java.io.BufferedReader;
import java.io.BufferedWriter;
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


public class Item {
    private String itemID, itemName;
    private int Stock;
    private Supplier supplier;
    private double itemPrice;
    
    
    public Item(String itemID, String itemName, int Stock, Supplier supplier, double itemPrice){
        this.itemID = itemID;
        this.itemName = itemName;
        this.Stock = Stock;
        this.supplier = supplier;
        this.itemPrice = itemPrice;
    }

    public Item(){
        this.itemID = null;
        this.itemName = null;
        this.supplier = null;
    }
    
    public void setitemPrice(double itemPrice){
        this.itemPrice = itemPrice;
    }
    
    public double getitemPrice(){
        return itemPrice;
    }
    
    public void setitemID(String itemID){
        this.itemID = itemID;
    }
    public String getitemID(){
        return itemID;
    }
    
    public void setitemName(String itemName){
        this.itemName = itemName;
    }
    public String getitemName(){
        return itemName;
    }
    
    public void setStock(int Stock){
        this.Stock = Stock;
        
    }
    public int getStock(){
        return Stock;
    }
    
    public void getsupplier(Supplier supplier){
        this.supplier = supplier;
    }
    public Supplier setsupplier(){
        return supplier;
    }
    public void updateStock(int saleQuantity) {
        if (saleQuantity <= Stock) {
            Stock -= saleQuantity;
        } else {
            System.out.println("Error: Sold quantity exceeds available stock.");
        }
    }
    
    public static List<String> readItemsFromFile() {
        List<String> items = new ArrayList<>();
        
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("C:\\Users\\chong\\OneDrive\\Desktop\\java\\item.txt"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                
                items.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return items;
    }

    private void writeItemsToFile(List<String> items) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("C:\\Users\\chong\\OneDrive\\Desktop\\java\\item.txt"))) {
            for (String item : items) {
                bufferedWriter.write(item);
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
   
    public void viewItem() {
        List<String> items = readItemsFromFile();

        // Print the items in item.txt
        System.out.println("Item List:");
        for (String item : items) {
            System.out.println(item);
        }
    }
    
    public void addItem() {
        List<String> items = readItemsFromFile();
        Scanner scanner = new Scanner(System.in);
        viewItem();
        // Prompt the user for the new item's details
        System.out.print("Enter Item ID: ");
        String ID = scanner.nextLine();
        
        for (String itemData : items) {
            String[] parts = itemData.split(",");
            if (parts.length >= 5 && parts[0].equals(ID)) {
                System.out.println("Item with ID " + ID + " already exists. Cannot add it again.");
                return; // Exit the method
            }
        }
        System.out.print("Enter Item Name: ");
        String Name = scanner.nextLine();

        System.out.print("Enter Stock (packs): ");
        int stock = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        
        System.out.print("Enter Item Price Per Pack: ");
        double price = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Enter Supplier: ");
        String suppliername = scanner.nextLine();

        // Create a string representation of the new item
        String newItem = ID + "," + Name + "," + stock + "," + price + "," + suppliername;

        // Add the new item to the list
        items.add(newItem);

        // Save the updated data
        writeItemsToFile(items);

        System.out.println("Item added successfully.");
        System.out.println("Updated item list:");
    }
    
    public void editItem() {
        List<String> items = readItemsFromFile();

        Scanner scanner = new Scanner(System.in);
        viewItem();
        System.out.print("Enter the ID of the item you want to edit: ");
        String editID = scanner.nextLine();

        // Find the item to edit
        int itemIndex = -1;
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).startsWith(editID + ",")) {
                itemIndex = i;
                break;
            }
        }

        if (itemIndex == -1) {
            System.out.println("Item with ID " + editID + " not found.");
            return;
        }

        // Ask the user what information they want to modify
        System.out.println("What information would you like to modify?");
        System.out.println("1. Name");
        System.out.println("2. Stock");
        System.out.println("3. Price");
        System.out.println("4. Supplier");

        System.out.print("Enter the number of your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        // Ask for the new value based on the user's choice
        String updatedInfo;
        switch (choice) {
            case 1:
                System.out.print("Enter the new name: ");
                updatedInfo = scanner.nextLine();
                break;
            case 2:
                System.out.print("Enter the new stock (packs): ");
                updatedInfo = scanner.nextLine();
                break;
            case 3:
                System.out.print("Enter the new price per pack: ");
                updatedInfo = scanner.nextLine();
                break;
            case 4:
                System.out.print("Enter the new supplier: ");
                updatedInfo = scanner.nextLine();
                break;
            default:
                System.out.println("Invalid choice.");
                return;
        }

        // Update the item's information in memory
        String[] parts = items.get(itemIndex).split(",");
        parts[choice] = updatedInfo;
        items.set(itemIndex, String.join(",", parts));

        // Save the updated data
        writeItemsToFile(items);

        System.out.println("Item updated successfully.");
    } 
    
    public void deleteItem() {
         // Read the data from the item.txt file
         List<String> items = readItemsFromFile();
         // Ask the user for the item ID to delete
         Scanner scanner = new Scanner(System.in);
         viewItem();
         System.out.print("Enter the ID of the item you want to delete: ");
         String deleteID = scanner.nextLine();
         // Find the item to delete
         int itemIndex = -1;
         for (int i = 0; i < items.size(); i++) {
             if (items.get(i).startsWith(deleteID + ",")) {
                 itemIndex = i;
                 break;
             }
         }
         if (itemIndex == -1) {
             System.out.println("Item with ID " + deleteID + " not found.");
             return;
         }
         // Remove the item from the list
         items.remove(itemIndex);
         // Write the updated data back to the item.txt file
         writeItemsToFile(items);
         System.out.println("Item deleted successfully.");
    }
    
    public String toString(){
        return itemID + "," + itemName;
    }
}
