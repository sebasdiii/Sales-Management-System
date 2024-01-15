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

/**
 *
 * @author chong
 */
public class Sales {
    private String Date;
    private int saleQuantity;
    private double saleTotal;
    private Item saleItem;
    
    public Sales() {
        this.Date = null;
        this.saleItem = null;
    }
    
    public Sales(String Date, int saleQuantity, double saleTotal, Item saleItem){
        this.Date = Date;
        this.saleQuantity = saleQuantity;
        this.saleTotal = saleTotal;
        this.saleItem = saleItem;
    }
    
    public void setDate(String Date){
        this.Date = Date;
    }
    public String getDate(){
        return Date;
    }
    public void setsaleQuantity(int saleQuantity){
        this.saleQuantity = saleQuantity;
    }
    public int getsaleQuantity(){
        return saleQuantity;
    }
    public void setsaleTotal(double saleTotal){
        this.saleTotal = saleTotal;
    }
    public double getsaleTotal(){
        return saleTotal;
    }
    public void setsaleItem(Item saleItem){
        this.saleItem = saleItem;
    }
    public Item getsaleItem(){
        return saleItem;
    }
    private double calculateSaleTotal(int saleQuantity, double itemPrice) {
        return saleQuantity * itemPrice;
    }
    
    private Item fetchItemFromItemList(String itemID) {
         // Read item data from file
        List<String> items = Item.readItemsFromFile();

        for (String itemData : items) {
            String[] parts = itemData.split(",");
            if (parts.length == 5 && parts[0].equals(itemID)) {
                String itemName = parts[1];
                int stock = Integer.parseInt(parts[2]);
                double itemPrice = Double.parseDouble(parts[3]);
                String supplierName = parts[4];
                Supplier supplier = new Supplier(supplierName);
                return new Item(itemID, itemName, stock, supplier, itemPrice);
            }
        }

        return null; // Item not found
    }

    private List<String> readSalesFromFile() {
        List<String> sales = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("C:\\Users\\chong\\OneDrive\\Desktop\\java\\sales.txt"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sales.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sales;
    }

    private void writeSalesToFile(List<String> sales) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("C:\\Users\\chong\\OneDrive\\Desktop\\java\\sales.txt"))) {
            for (String sale : sales) {
                bufferedWriter.write(sale);
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateStockInItemFile(String itemID, int newStock) {
        List<String> items = Item.readItemsFromFile();
        for (int i = 0; i < items.size(); i++) {
            String itemData = items.get(i);
            String[] parts = itemData.split(",");
            if (parts.length == 5 && parts[0].equals(itemID)) {
                parts[2] = String.valueOf(newStock); // Update the stock
                items.set(i, String.join(",", parts)); // Update the item data
                break; // No need to continue searching
            }
        }
        // Save the updated item data back to the item.txt file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\chong\\OneDrive\\Desktop\\java\\item.txt"))) {
            for (String item : items) {
                writer.write(item);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    public void addSales() {
        List<String> sales = readSalesFromFile();
        Scanner scanner = new Scanner(System.in);
        viewSales();
        // Prompt the user for the new sale's details
        String date = "";
        String itemID = "";
        int salequantity = 0;
        
        while (true) {
            System.out.print("Enter Sale Date (MM/DD/YYYY): ");
            date = scanner.nextLine();

            System.out.print("Enter Item ID: ");
            itemID = scanner.nextLine();

            boolean saleExists = false;
            for (String saleData : sales) {
                String[] saleParts = saleData.split(",");
                if (saleParts.length >= 5 &&
                    saleParts[0].equals(date) &&
                    saleParts[1].equals(itemID)) {
                    saleExists = true;
                    System.out.println("A sale with the same Date and Item ID already exists.");
                    break; // Exit the loop
                }
            }

            if (!saleExists) {
                break; // Exit the loop if no duplicate sale found
            }
        }

        // Fetch the item and its price here
        Item SALEITEM = fetchItemFromItemList(itemID);
        if (SALEITEM == null) {
            System.out.println("Item not found.");
            return;
        }

        int availableStock = SALEITEM.getStock();

        while (true) {
            System.out.print("Enter Sale Quantity: ");
            salequantity = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            if (salequantity <= 0 || salequantity > availableStock) {
                System.out.println("Invalid sale quantity. Quantity should be between 1 and " + availableStock);
            } else {
                
                break; // Exit the loop if salequantity is valid
            }
        }

        // Fetch the item and its price here
        double itemPrice = SALEITEM.getitemPrice();

        // Calculate total sales based on item price
        double saletotal = calculateSaleTotal(salequantity, itemPrice);

        // Create a string representation of the new sale
        String newSale = date + "," + itemID + "," + salequantity + "," + itemPrice + "," + saletotal;
        
        //calculate the sale quantity and modify the stock by minus the salequantity 
        SALEITEM.setStock(availableStock-salequantity);
        updateStockInItemFile(itemID, availableStock - salequantity);
        // Add the new sale to the list
        sales.add(newSale);

        // Save the updated data
        writeSalesToFile(sales);

        System.out.println("Sale added successfully.");
    }
    
    public void editSales() {
    List<String> sales = readSalesFromFile();
    Scanner scanner = new Scanner(System.in);
    
    // Prompt the user for the date and item ID to edit
    String editDate = "";
    String editItemID = "";
    
    viewSales();
    while (true) {
        System.out.print("Enter the Sale Date (MM/DD/YYYY): ");
        editDate = scanner.nextLine();

        System.out.print("Enter the Item ID: ");
        editItemID = scanner.nextLine();

        // Find the sale to edit
        int saleIndex = -1;
        int originalSaleQuantity = 0;
        double originalItemPrice = 0.0;
        
        for (int i = 0; i < sales.size(); i++) {
            String saleData = sales.get(i);
            String[] saleParts = saleData.split(",");

            // Ensure that there are enough elements and check for a match
            if (saleParts.length >= 5 &&
                saleParts[0].equals(editDate) && 
                saleParts[1].equals(editItemID)) {
                saleIndex = i;
                originalSaleQuantity = Integer.parseInt(saleParts[2]);
                originalItemPrice = Double.parseDouble(saleParts[3]);
                break;
            }
        }
        if (saleIndex == -1) {
            System.out.println("Sale with Date " + editDate + " and Item ID " + editItemID + " not found.");
            return;
        }

        // Ask the user what information they want to modify
        System.out.println("What information would you like to modify?");
        System.out.println("1. Sale Quantity");
        System.out.println("2. Item Price");
        System.out.println("3. Total Sales");

        System.out.print("Enter the number of your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        // Validate the user's choice
        if (choice < 1 || choice > 3) {
            System.out.println("Invalid choice.");
            return;
        }

        // Ask for the new value based on the user's choice
        String updatedInfo;
        double newTotalSales = 0.0;
        switch (choice) {
            case 1:
                // Editing Sale Quantity
                int availableStock = fetchItemFromItemList(editItemID).getStock();
                while (true) {
                    System.out.print("Enter the new sale quantity: ");
                    updatedInfo = scanner.nextLine();
                    int updatedQuantity = Integer.parseInt(updatedInfo);

                    if (updatedQuantity <= 0 || updatedQuantity > availableStock) {
                        System.out.println("Invalid sale quantity. Quantity should be between 1 and " + availableStock);
                    } else {
                        int quantityDifference = updatedQuantity - originalSaleQuantity;
                        Item SALEITEM = fetchItemFromItemList(editItemID);
                        int newStock = SALEITEM.getStock();
                        
                        if (quantityDifference > 0) {
                            newStock = newStock - quantityDifference;
                        } else if(quantityDifference <0){
                            newStock = newStock + (quantityDifference*-1);                           
                        }
                        SALEITEM.setStock(newStock);
                        updateStockInItemFile(editItemID, newStock);
                        
                        newTotalSales = calculateSaleTotal(updatedQuantity, originalItemPrice);
                        break; // Exit the loop if sale quantity is valid
                    }
                }
                break;
            case 2:
                // Editing Item Price
                System.out.print("Enter the new item price per pack: ");
                updatedInfo = scanner.nextLine();
                break;
            case 3:
                // Editing Total Sales
                System.out.print("Enter the new total sales: ");
                updatedInfo = scanner.nextLine();
                newTotalSales = Double.parseDouble(updatedInfo);
                break;
            default:
                System.out.println("Invalid choice.");
                return;
        }

        // Update the sale's information in memory
        String[] saleParts = sales.get(saleIndex).split(",");
        saleParts[choice + 1] = updatedInfo;
        saleParts[4] = String.valueOf(newTotalSales); 
        sales.set(saleIndex, String.join(",", saleParts));

        // Save the updated data
        writeSalesToFile(sales);
        
        

        System.out.println("Sale updated successfully.");
                // Ask if the user wants to edit another sale

        System.out.print("Do you want to edit another sale? (yes/no): ");
        String anotherEdit = scanner.nextLine().toLowerCase();
        if (!anotherEdit.equals("yes")) {
            break;
        }
    }
    }

    
    public void viewSales(){
        List<String> sales = readSalesFromFile();

        // Print the items in item.txt
        System.out.println("Sales List:");
        for (String sale : sales) {
            System.out.println(sale);
        }
    }
    
    public void deleteSales() {
        List<String> sales = readSalesFromFile();
        Scanner scanner = new Scanner(System.in);
        viewSales();
        // Prompt the user for the sale to delete
        System.out.print("Enter the Sale Date (MM/DD/YYYY) to delete: ");
        String deleteDate = scanner.nextLine();

        System.out.print("Enter the Item ID to delete: ");
        String deleteItemID = scanner.nextLine();

        // Find the sale to delete
        int saleIndex = -1;
        int saleQuantity = 0;
        String itemID = "";

        for (int i = 0; i < sales.size(); i++) {
            String saleData = sales.get(i);
            String[] saleParts = saleData.split(",");

            // Ensure that there are enough elements and check for a match
            if (saleParts.length >= 5 &&
                saleParts[0].equals(deleteDate) && 
                saleParts[1].equals(deleteItemID)) {
                saleIndex = i;
                saleQuantity = Integer.parseInt(saleParts[2]);
                itemID = saleParts[1];
                break;
            }
        }

        if (saleIndex == -1) {
            System.out.println("Sale with Date " + deleteDate + " and Item ID " + deleteItemID + " not found.");
            return;
        }

        // Confirm deletion with the user
        System.out.println("Are you sure you want to delete this sale?");
        System.out.println("Date: " + deleteDate);
        System.out.println("Item ID: " + deleteItemID);
        System.out.println("Sale Quantity: " + saleQuantity);
        System.out.print("Confirm (yes/no): ");
        String confirmDelete = scanner.nextLine().toLowerCase();

        if (confirmDelete.equals("yes")) {
            // Delete the sale entry
            sales.remove(saleIndex);

            // Update stock in item.txt
            Item item = fetchItemFromItemList(itemID);
            if (item != null) {
                int currentStock = item.getStock();
                item.setStock(currentStock + saleQuantity);
                updateStockInItemFile(itemID, currentStock + saleQuantity);

                // Save the updated data
                writeSalesToFile(sales);
                System.out.println("Sale deleted successfully. Stock updated.");
            } else {
                System.out.println("Item not found. Sale deleted, but stock not updated.");
            }
        } else {
            System.out.println("Deletion canceled.");
        }
    }
}

   
