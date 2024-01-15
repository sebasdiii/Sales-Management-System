

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
public class menu {
    Scanner scanner = new Scanner(System.in);
    

    public menu(){
    }
    public void adminpage() throws IOException {
        boolean exit = false;

        while (!exit) {
            System.out.print("""
                            ===============================================
                            WELCOME TO ADMINISTRATOR PAGE
                            ===============================================
                            """);
            System.out.println("Please Select an option to proceed with:");
            System.out.println("1) Create user credentials");
            System.out.println("2) Delete user credentials");
            System.out.println("3) View user ceredentials");
            System.out.println("4) Edit user credentials");
            System.out.println("0) Log out");
            System.out.println("===============================================");
            System.out.print("\nOption: ");
            
            int choice = scanner.nextInt();
            Admin admin = new Admin();

            switch (choice) {
                case 1:
                    admin.createUser();
                    break;
                case 2:
                    admin.delete();
                    break;
                case 3:
                    admin.viewUser();
                    break;
                case 4:
                    admin.editUser();
                    break;
                case 0: // Exit option
                    exit = true; // Set the exit flag to true to exit the loop
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }
    public void purchasemanagerpage(){
        boolean exit = false;
        while (! exit) {
            System.out.print("""
                            ===============================================
                            WELCOME TO PURCHASE MANAGER PAGE
                            ===============================================
                            """);
            System.out.println("Please Select an option to proceed with:");
            System.out.println("1) View List of Items");
            System.out.println("2) View List of Suppliers");
            System.out.println("3) View Purchase Requisitions");
            System.out.println("4) Manage Purchase Orders");
            System.out.println("0) Log out");
            System.out.println("===============================================");
            System.out.print("\nOption: ");

            int choice = scanner.nextInt();
            
            Supplier s = new Supplier("");
            Item item = new Item();
            PurchaseRequisition Pr = new PurchaseRequisition();
            PurchaseOrders Po = new PurchaseOrders();

            switch (choice) {
                case 1:
                    item.viewItem();
                    System.out.println("View List of Items option selected.");
                    break; 
                case 2:
                    s.viewSupplier();
                    System.out.println("View List of Suppliers option selected.");
                    break; 
                case 3:
                    Pr.viewPr();
                    System.out.println("View Purchase Requisitions option selected.");
                    break; 
                case 4:
                    purchaseOrders();
                    break;
                case 0:
                    exit = true;
                    System.out.println("LOGGING OUT.");
                    return; 
                // Add cases for other options 
            }
        }
    }
    public void salesmanagerpage(){
        boolean exit = false;
        while (!exit) {
            System.out.print("""
                            ===============================================
                            WELCOME TO SALES MANAGER PAGE
                            ===============================================
                            """);
            System.out.println("Please Select an option to proceed with:");
            System.out.println("1) Item Entry");
            System.out.println("2) Supplier Entry");
            System.out.println("3) Daily Item-wise Sales Entry");
            System.out.println("4) Manage Purchase Requisition");
            System.out.println("5) View Purchase Orders List");
            System.out.println("0) Log out");
            System.out.println("===============================================");
            System.out.print("\nOption: ");

            int choice = scanner.nextInt();
            PurchaseOrders po = new PurchaseOrders();
            switch (choice) {
                case 1:
                    itemEntry();
                    break;
                case 2:
                    supplierEntry();
                    break;
                case 3:
                    salesEntry();
                    break;
                case 4:
                    purchaseRequisition();
                    break;
                case 5:
                    po.viewPO();
                    break;
                case 0:
                    exit = true;
                    System.out.println("Logging out.");
                    return; 
                default:
                    System.out.println("Invalid option. Please try again.");
                    
                // Add cases for other options 
            }
        }
    }
    
    public void itemEntry() {
        System.out.print("""
                            ===============================================
                            ITEM ENTRY MENU
                            ===============================================
                            """);
        System.out.println("Please Select an option to proceed with:");
        System.out.println("1) Add Item");
        System.out.println("2) Edit Item");
        System.out.println("3) Delete Item");
        System.out.println("4) View Item");        
        System.out.println("0) Back to Main Menu");
        System.out.println("===============================================");
        System.out.print("\nOption: ");
        int choice = scanner.nextInt();
        Item item = new Item();
        switch (choice) {
            case 1:
                item.addItem();
                break;
            case 2:
                item.editItem();
                break;
            case 3:
                item.deleteItem();;
                break;
            case 4:
                item.viewItem();
                break; 
            case 0:
                salesmanagerpage();// Return to the Sales Manager menu
                break; 
            default:
                System.out.println("Invalid option selected.Please try again!!");
        } 
    }
    
    public void supplierEntry() {
        System.out.print("""
                            ===============================================
                            SUPPLIER ENTRY MENU
                            ===============================================
                            """);
        System.out.println("Please Select an option to proceed with:");
        System.out.println("1) Add Supplier");
        System.out.println("2) Edit supplier");
        System.out.println("3) Delete Supplier");
        System.out.println("4) View Supplier");
        System.out.println("0) Back to Main Menu");
        System.out.println("===============================================");
        System.out.print("\nOption: ");
        int choice = scanner.nextInt();
        Supplier s = new Supplier("");
        switch (choice) {
            case 1:
                s.addSupplier();
                break;
            case 2:
                s.editSupplier();
                break;
            case 3:
                s.deleteSupplier();
                break;
            case 4:
                s.viewSupplier();
                break; 
            case 0:
                salesmanagerpage();// Return to the Sales Manager menu
                break; 
            default:
                System.out.println("Invalid option selected.Please try again!!");
        } 
    }
   
    public void salesEntry() {
        System.out.print("""
                            ===============================================
                            SALES ENTRY MENU
                            ===============================================
                            """);
        System.out.println("Please Select an option to proceed with:");
        System.out.println("1) Add Sales");
        System.out.println("2) Edit Sales");
        System.out.println("3) Delete Sales");
        System.out.println("4) View Sales");
        System.out.println("0) Back to Main Menu");
        System.out.println("===============================================");
        System.out.print("\nOption: ");
        int choice = scanner.nextInt();
        Sales sale = new Sales();
        switch (choice) {
            case 1:
                sale.addSales();
                break;
            case 2:
                sale.editSales();
                break;
            case 3:
                sale.deleteSales();
                break;
            case 4:
                sale.viewSales();
                break; 
            case 0:
                salesmanagerpage();// Return to the Sales Manager menu
                break; 
            default:
                System.out.println("Invalid option selected.Please try again!!");
        } 
    }
    
    public void purchaseRequisition() {
        while (true){
            System.out.println("\n\n========================");
            System.out.println("   Purchase Requisition Menu  ");
            System.out.println("========================");
            System.out.println("1. Add Purchase Requisition");
            System.out.println("2. Edit Purchase Requisition");
            System.out.println("3. Delete Purchase Requisition");
            System.out.println("4. View Purchase Requisitions");
            System.out.println("0. Back to Main Menu");
            System.out.println("========================");
            System.out.print("\nOption: ");
        
        int choice = scanner.nextInt();
        PurchaseRequisition Pr = new PurchaseRequisition();
        switch (choice) {
            case 1:
                Pr.addPr();
                break;
            case 2:
                Pr.editPr();
                break;
            case 3:
                Pr.deletePr();
                break;
            case 4:
                Pr.viewPr();
                break; 
            case 0:
                salesmanagerpage();
                break;
            default:
                System.out.println("Invalid option selected.Please try again!!");
        } 
    }
}
    
    public void purchaseOrders() {
        while (true){
            System.out.print("""
                            ===============================================
                            PURCHASE ORDER MENU
                            ===============================================
                            """);
            System.out.println("Please Select an option to proceed with:");
            System.out.println("1) View Purchase Requisitions");
            System.out.println("2) Approve Purchase Requisitions");
            System.out.println("3) Delete Purchase Orders");
            System.out.println("4) View Purchase Orders");
            System.out.println("0) Back to Main Menu");
            System.out.println("===============================================");
            System.out.print("\nOption: ");
        
        int choice = scanner.nextInt();
        PurchaseRequisition Pr = new PurchaseRequisition();
        PurchaseOrders Po = new PurchaseOrders();

        switch (choice) {
            case 1:
                Pr.viewPr();
                break;
            case 2:
                Po.createPurchaseOrder();
                break;
            case 3:
                Po.deletePurchaseOrder();
                break;
            case 4:
                Po.viewPO();
                break; 
            case 0:
                purchasemanagerpage();
                break;
            default:
                System.out.println("Invalid option selected.Please try again!!");
        } 
    }
    }
}
