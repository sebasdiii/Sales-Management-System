
import java.io.*;
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
public class PurchaseOrders {
    private static final String PR_FILE = "C:\\Users\\chong\\OneDrive\\Desktop\\java\\purchaseRequisitions.txt";
    private static final String PO_FILE = "C:\\Users\\chong\\OneDrive\\Desktop\\java\\purchaseOrders.txt";
    
    private String poID;
    private String poDate;
    private PurchaseRequisition poItem;
    private String createdBy;

    public PurchaseOrders(String poID, String poDate, PurchaseRequisition poItem, String createdBy) {
        this.poID = poID;
        this.poDate = poDate;
        this.poItem = poItem;
        this.createdBy = createdBy;
    }

    public PurchaseOrders() {
        
    }

    // Getter and Setter methods for poID
    public String getPoID() {
        return poID;
    }

    public void setPoID(String poID) {
        this.poID = poID;
    }

    // Getter and Setter methods for poDate
    public String getPoDate() {
        return poDate;
    }

    public void setPoDate(String poDate) {
        this.poDate = poDate;
    }

    // Getter and Setter methods for poItem
    public PurchaseRequisition getPoItem() {
        return poItem;
    }

    public void setPoItem(PurchaseRequisition poItem) {
        this.poItem = poItem;
    }

    // Getter and Setter methods for createdBy
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public String toString() {
        return "Purchase Order ID: " + poID + "\n" +
               "Date: " + poDate + "\n" +
               "Purchase Requisition: " + poItem.getPrID() + "\n" +
               "Created By: " + createdBy;
    }
    
    
    public void viewPurchaseRequisitions() {
        List<PurchaseRequisition> purchaseRequisitions = getPurchaseRequisitionsFromFile();

        if (purchaseRequisitions.isEmpty()) {
            System.out.println("No Purchase Requisitions found.");
            return;
        }

        System.out.println("Purchase Requisition ID,Date,Item ID,Item Name,Quantity,Price,Status,Created By");

        for (PurchaseRequisition pr : purchaseRequisitions) {
            Item item = pr.getPrItem();
            String itemID = (item != null) ? item.getitemID() : "N/A";
            String itemName = (item != null) ? item.getitemName() : "N/A";

            System.out.println(pr.getPrID() + "," + pr.getPrDate() + "," + itemID + "," + itemName + ","
                    + pr.getPrQuantity() + "," + pr.getPrPrice() + "," + (pr.isStatus() ? "Approved" : "Pending") + ","
                    + pr.getCreatedBy());
        }
    }

    public void createPurchaseOrder() {
        viewPurchaseRequisitions();

        System.out.println("Enter Purchase Requisition ID to approve:");
        Scanner sc = new Scanner(System.in);
        String prIDToApprove = sc.nextLine();

        List<PurchaseRequisition> purchaseRequisitions = getPurchaseRequisitionsFromFile();
        PurchaseRequisition foundPr = null;

        for (PurchaseRequisition pr : purchaseRequisitions) {
            if (pr.getPrID().equals(prIDToApprove)) {
                foundPr = pr;
                break;
            }
        }

        if (foundPr == null) {
            System.out.println("Purchase Requisition not found.");
            return;
        }

        if (foundPr.isStatus()) {
            System.out.println("Purchase Requisition is already approved.");
            return;
        }

        // Generate a unique purchase order ID
        String poID = generateUniquePurchaseOrderID();

        // Prompt the user to enter the userID
         System.out.println("Enter UserID (Purchase Manager ID):");
         String purchaseManagerID = sc.nextLine();

         // Update the status and Purchase Manager ID
         foundPr.setStatus(true);
         foundPr.setCreatedBy(purchaseManagerID);

         // Save the approved Purchase Requisition as a Purchase Order
         savePurchaseOrderToFile(foundPr, poID);
         System.out.println("Purchase Order created with ID: " + poID);

         // Update the purchase requisitions file with the updated status
         savePurchaseRequisitionsToFile(purchaseRequisitions);
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
                bw.write(pr.getPrID() + "," + pr.getPrDate() + "," + pr.getPrItem().getitemID() + ","
                        + pr.getPrItem().getitemName() + ","
                        + pr.getPrQuantity() + "," + pr.getPrPrice() + "," + pr.isStatus() + ","
                        + pr.getCreatedBy());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to file.");
        }
    }

    private void savePurchaseOrderToFile(PurchaseRequisition purchaseRequisition, String purchaseOrderID) {
        try (FileWriter fw = new FileWriter(PO_FILE, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(purchaseOrderID + "," + purchaseRequisition.getPrDate() + ","
                    + purchaseRequisition.getPrItem().getitemID() + ","
                    + purchaseRequisition.getPrItem().getitemName() + ","
                    + purchaseRequisition.getCreatedBy());
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error writing to file.");
        }
    }

    private String generateUniquePurchaseOrderID() {
        int nextID = getNextPurchaseOrderCounter();
        return "PO" + String.format("%03d", nextID);
    }

    private int getNextPurchaseOrderCounter() {
        int counter = 1;

        try {
            File counterFile = new File("C:\\Users\\chong\\OneDrive\\Desktop\\java\\po_counter.txt");
            if (counterFile.exists()) {
                try (BufferedReader br = new BufferedReader(new FileReader(counterFile))) {
                    String line = br.readLine();
                    if (line != null) {
                        counter = Integer.parseInt(line);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Increment the counter for the next purchase order
        counter++;

        // Save the updated counter back to the file
        try (FileWriter fw = new FileWriter("C:\\Users\\chong\\OneDrive\\Desktop\\java\\po_counter.txt");
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(String.valueOf(counter));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return counter;
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
    
    private List<PurchaseOrders> getPurchaseOrdersFromFile() {
        List<PurchaseOrders> purchaseOrders = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(PO_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length != 4) {
                    // Skip lines that don't have the expected number of elements
                    System.out.println("Skipping invalid line: " + line);
                    continue;
                }
                String poID = parts[0];
                String poDateStr = parts[1];
                String prID = parts[2];

                // Fetch the associated PurchaseRequisition object based on the prID
                PurchaseRequisition pr = fetchPurchaseRequisitionFromID(prID);

                if (pr != null) {
                    // Construct the PurchaseOrders object
                    PurchaseOrders purchaseOrder = new PurchaseOrders(poID, poDateStr, pr, parts[3]); // Use parts[3] for createdBy
                    purchaseOrders.add(purchaseOrder);
                } else {
                    System.out.println("Purchase Requisition not found for Purchase Order with ID: " + poID);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading from file.");
        }
        return purchaseOrders;
    }





    // Helper method to fetch a PurchaseRequisition object based on its ID
    private PurchaseRequisition fetchPurchaseRequisitionFromID(String prID) {
        List<PurchaseRequisition> purchaseRequisitions = getPurchaseRequisitionsFromFile();

        for (PurchaseRequisition pr : purchaseRequisitions) {
            if (pr.getPrID().equals(prID)) {
                return pr;
            }
        }

        return null; // Purchase Requisition not found
    }

    public void viewPO() {
        List<PurchaseOrders> purchaseOrders = getPurchaseOrdersFromFile();

        if (purchaseOrders.isEmpty()) {
            System.out.println("No Purchase Orders found.");
            return;
        }

        System.out.println("Purchase Order ID,Date,Purchase Requisition ID,Approved By");

        for (PurchaseOrders po : purchaseOrders) {
            PurchaseRequisition pr = po.getPoItem();

            System.out.println(po.getPoID() + "," + po.getPoDate() + "," + pr.getPrID() + ","
                                + po.getCreatedBy());
        }
    }

    public void deletePurchaseOrder() {
        List<PurchaseOrders> purchaseOrders = getPurchaseOrdersFromFile();

        if (purchaseOrders.isEmpty()) {
            System.out.println("No Purchase Orders found.");
            return;
        }

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the Purchase Order ID to delete:");
        String poIDToDelete = sc.nextLine();

        PurchaseOrders poToDelete = null;

        for (PurchaseOrders po : purchaseOrders) {
            if (po.getPoID().equals(poIDToDelete)) {
                poToDelete = po;
                break;
            }
        }

        if (poToDelete == null) {
            System.out.println("Purchase Order with ID " + poIDToDelete + " not found.");
            return;
        }

        // Remove the purchase order from the list
        purchaseOrders.remove(poToDelete);

        // Update the purchase orders file
        savePurchaseOrdersToFile(purchaseOrders);

        System.out.println("Purchase Order with ID " + poIDToDelete + " has been deleted.");
    }

    private void savePurchaseOrdersToFile(List<PurchaseOrders> purchaseOrders) {
        try (FileWriter fw = new FileWriter(PO_FILE);
             BufferedWriter bw = new BufferedWriter(fw)) {
            for (PurchaseOrders po : purchaseOrders) {
                // Write the PurchaseOrders data to the file
                bw.write(po.getPoID() + "," + po.getPoDate() + "," + po.getPoItem().getPrID() + ","
                        + po.getCreatedBy());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to file.");
        }
    }
}