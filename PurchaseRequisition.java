


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


public class PurchaseRequisition {
    private static final String PR_FILE ="C:\\Users\\chong\\OneDrive\\Desktop\\java\\purchaseRequisitions.txt";

    private String prID;
    private String prDate;
    private Item prItem;
    private int prQuantity;
    private double prPrice;
    private boolean status;
    private String createdBy;

    public PurchaseRequisition() {
       
    }

    public PurchaseRequisition(String prID, String prDate, Item prItem, int prQuantity, double prPrice, boolean status, String createdBy) {
        this.prID = prID;
        this.prDate = prDate;
        this.prItem = prItem;
        this.prQuantity = prQuantity;
        this.prPrice = prPrice;
        this.status = status;
        this.createdBy = createdBy;
    }

    // Getter and Setter methods for all attributes

    public String getPrID() {
        return prID;
    }

    public void setPrID(String prID) {
        this.prID = prID;
    }

    public String getPrDate() {
        return prDate;
    }

    public void setPrDate(String prDate) {
        this.prDate = prDate;
    }

    public Item getPrItem() {
        return prItem;
    }

    public void setPrItem(Item prItem) {
        this.prItem = prItem;
    }

    public int getPrQuantity() {
        return prQuantity;
    }

    public void setPrQuantity(int prQuantity) {
        this.prQuantity = prQuantity;
    }

    public double getPrPrice() {
        return prPrice;
    }

    public void setPrPrice(double prPrice) {
        this.prPrice = prPrice;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public String toString() {

        return "Purchase Requisition ID: " + prID + "\n" +
               "Date: " + prDate + "\n" +
               "Item: " + prItem + "\n" +
               "Quantity: " + prQuantity + "\n" +
               "Price: " + prPrice + "\n" +
               "Status: " + (status ? "Approved" : "Pending") + "\n" +
               "Created by: " + createdBy;
    }

    public void addPr() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter Purchase Requisition ID:");
        String newPrID = sc.nextLine();
        System.out.println("Enter Date (DD/MM/YYYY):");
        String newPrDate = sc.nextLine();
        System.out.println("Enter Item ID:");
        String itemID = sc.nextLine();

        Item newPrItem = fetchItemFromItemList(itemID);

        if (newPrItem == null) {
            System.out.println("Item not found. Purchase Requisition cannot be added.");
            return;
        }

        System.out.println("Enter Quantity:");
        int newPrQuantity = Integer.parseInt(sc.nextLine());
        System.out.println("Enter Price per Item:");
        double newPrPrice = Double.parseDouble(sc.nextLine());

        boolean newStatus = false;

        System.out.println("Enter Created by (Sales Manager):");
        String newCreatedBy = sc.nextLine();

        PurchaseRequisition newPurchaseRequisition = new PurchaseRequisition(newPrID, newPrDate, newPrItem, newPrQuantity, newPrPrice, newStatus, newCreatedBy);

        try (FileWriter fw = new FileWriter(PR_FILE, true);
            BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(newPurchaseRequisition.getPrID() + "," + newPurchaseRequisition.getPrDate() + "," + newPurchaseRequisition.getPrItem().getitemID() + "," + newPurchaseRequisition.getPrItem().getitemName() +
                "," + newPurchaseRequisition.getPrQuantity() + "," + newPurchaseRequisition.getPrItem().getitemPrice() + "," + newPurchaseRequisition.isStatus() + "," + newPurchaseRequisition.getCreatedBy());
            bw.newLine();
            System.out.println("Purchase Requisition successfully added!");
        } catch (IOException e) {
            System.out.println("Error writing to file.");
        }
    
    }
    public void deletePr() {
        viewPr();
        System.out.println("Enter Purchase Requisition ID to delete:");
        Scanner sc = new Scanner(System.in);
        String prIDToDelete = sc.nextLine();

        List<PurchaseRequisition> purchaseRequisitions = getPurchaseRequisitionsFromFile();
        PurchaseRequisition foundPr = null;

        for (PurchaseRequisition pr : purchaseRequisitions) {
            if (pr.getPrID().equals(prIDToDelete)) {
                foundPr = pr;
                break;
            }
        }

        if (foundPr == null) {
            System.out.println("Purchase Requisition not found.");
            return;
        }

        purchaseRequisitions.remove(foundPr);
        savePurchaseRequisitionsToFile(purchaseRequisitions);
        System.out.println("Purchase Requisition deleted.");
    }
    private List<PurchaseRequisition> getPurchaseRequisitionsFromFile() {
        List<PurchaseRequisition> purchaseRequisitions = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(PR_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String prID = parts[0];
                String prDate = parts[1];
                String itemID = parts[2];

                // You may need to adjust the code to fetch itemName, prQuantity, prPrice, status, and createdBy
                String itemName = parts[3];
                int prQuantity;
                double prPrice;
                boolean status;
                String createdBy;

                try {
                    prQuantity = Integer.parseInt(parts[4]);
                    prPrice = Double.parseDouble(parts[5]);
                    status = Boolean.parseBoolean(parts[6]);
                    createdBy = parts[7];
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    System.out.println("Error parsing data for Purchase Requisition with ID: " + prID);
                    continue; // Skip this line and move to the next one
                }

                // Fetch the Item object based on the itemID
                Item prItem = fetchItemFromItemList(itemID);

                if (prItem != null) {
                    // Construct the PurchaseRequisition object
                    PurchaseRequisition purchaseRequisition = new PurchaseRequisition(prID, prDate, prItem, prQuantity, prPrice, status, createdBy);
                    purchaseRequisitions.add(purchaseRequisition);
                } else {
                    System.out.println("Item not found for Purchase Requisition with ID: " + prID);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading from file.");
        }
        return purchaseRequisitions;
    }
    private void savePurchaseRequisitionsToFile(List<PurchaseRequisition> purchaseRequisitions) {
        try (FileWriter fw = new FileWriter(PR_FILE);
             BufferedWriter bw = new BufferedWriter(fw)) {
            for (PurchaseRequisition pr : purchaseRequisitions) {
                bw.write(pr.getPrID() + "," + pr.getPrDate() + "," + pr.getPrItem() + ","
                        + pr.getPrQuantity() + "," + pr.getPrPrice() + "," + pr.isStatus() + ","
                        + pr.getCreatedBy());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to file.");
        }
    }
    
    public void editPr() {
        viewPr();
        System.out.println("Enter Purchase Requisition ID to edit:");
        Scanner sc = new Scanner(System.in);
        String prIDToEdit = sc.nextLine();
        // Logic to find and edit purchase requisition in PR_FILE
        // For simplicity, a full purchase requisition list will be read, edited in-memory, and then written back
        List<PurchaseRequisition> purchaseRequisitions = getPurchaseRequisitionsFromFile();
        PurchaseRequisition foundPr = null;

        for (PurchaseRequisition pr : purchaseRequisitions) {
            if (pr.getPrID().equals(prIDToEdit)) {
                foundPr = pr;
                break;
            }
        }

        if (foundPr == null) {
            System.out.println("Purchase Requisition not found.");
            return;
        }

        PurchaseRequisition updatedPr = getUpdatedPrDetails();
        purchaseRequisitions.remove(foundPr);
        purchaseRequisitions.add(updatedPr);
        savePurchaseRequisitionsToFile(purchaseRequisitions);
        System.out.println("Purchase Requisition updated.");
    }

    private PurchaseRequisition getUpdatedPrDetails() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter new Purchase Requisition ID:");
        String newPrID = sc.nextLine();
        System.out.println("Enter new Date:");
        String newPrDate = sc.nextLine();
        System.out.println("Enter new Item ID:");
        String newItemID = sc.nextLine();

        // Fetch the Item object based on the newItemID
        Item newPrItem = fetchItemFromItemList(newItemID);
    
        if (newPrItem == null) {
            System.out.println("Item not found. Purchase Requisition cannot be updated.");
            return null;
        }

        System.out.println("Enter new Quantity:");
        int newPrQuantity = Integer.parseInt(sc.nextLine());
        System.out.println("Enter new Price:");
        double newPrPrice = Double.parseDouble(sc.nextLine());
        boolean newStatus = false;
        System.out.println("Enter new Created by (Sales Manager):");
        String newCreatedBy = sc.nextLine();

        return new PurchaseRequisition(newPrID, newPrDate, newPrItem, newPrQuantity, newPrPrice, newStatus, newCreatedBy);
    }

    
    public void viewPr() {
        List<PurchaseRequisition> purchaseRequisitions = getPurchaseRequisitionsFromFile();

        if (purchaseRequisitions.isEmpty()) {
            System.out.println("No Purchase Requisitions found.");
            return;
        }

    // Display the header
        System.out.println("Purchase Requisition ID,Date,Item ID,Item Name,Quantity,Price,Status,Created By");

        for (PurchaseRequisition pr : purchaseRequisitions) {
            Item item = pr.getPrItem(); // Get the associated Item
            String itemID = (item != null) ? item.getitemID() : "N/A";
            String itemName = (item != null) ? item.getitemName() : "N/A";

            System.out.println(pr.getPrID() + "," + pr.getPrDate() + "," + itemID + "," + itemName + ","
                    + pr.getPrQuantity() + "," + pr.getPrPrice() + "," + (pr.isStatus() ? "Approved" : "Pending") + ","
                    + pr.getCreatedBy());
        }
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
    
}