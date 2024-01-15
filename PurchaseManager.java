/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author chong
 */
public class PurchaseManager extends User{

    public PurchaseManager(String userID, String userPW, String email, String userType) {
        super(userID, userPW, email, userType);
    }
    public void PurchaseManagerPage(){
        System.out.println("Welcome to Purchase Manager Page!");
        System.out.println("Please Select an option to proceed with:");
        System.out.println("1) View Item List");
        System.out.println("2) View Supplier List");
        System.out.println("3) View Requisition");
        System.out.println("4) Generate Purchase Order");
        System.out.println("4) View Purchase Orders List");
    }
}
