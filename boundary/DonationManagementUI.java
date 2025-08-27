/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boundary;

import control.DonationManagement;
import java.util.Scanner;

/**
 *
 * @author raphael
 */
public class DonationManagementUI {
    private static DonationManagement donationM = DonationManagement.getInstance();
    private systemMain systemMain;
    public static DonationManagementUI instance = null;
    
    
    public static DonationManagementUI getInstance(){
        if (instance == null){
            instance = new DonationManagementUI();
        }
        return instance;
    }
    
    public void managementMenu(){
        System.out.println("1) Add a new donation");
        System.out.println("2) Remove a donation");
        System.out.println("3) Search donation details");
        System.out.println("4) Amend donation details");
        System.out.println("5) Track donated items in categories");
        System.out.println("6) List donation by different donor");
        System.out.println("7) List all donations");
        System.out.println("8) Filter donation based on criteria");
        System.out.println("9) Generate summary reports");
        System.out.println("10) Exit ");
    }
    
    
    public void managementStart(){
        Scanner scanner = new Scanner(System.in);
        int choice; 
        boolean continueManagement = true;
        do {
            managementMenu();
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();
            switch(choice){
                case 1:
                    donationM.addNewDonation();
                    break;
                case 2:
                    donationM.removeDonation();
                    break;
                case 3:
                    donationM.searchDonation();
                    break;
                case 4:
                    donationM.amendDonation();
                    break;
                case 5:
                    donationM.trackCategory();
                    break;
                case 6:
                    donationM.listDonationsByDonorID();
                    break;
                case 7:
                    donationM.listAllDonations();
                    break;
                case 8:
                    donationM.filterDonations();
                    break;
                case 9:
                    donationM.generateReport();
                    break;
                case 10:
                    continueManagement = false;
                    System.out.println("Exiting");
            }
            System.out.print("Press [ENTER] to continue....");
            scanner.nextLine();
            systemMain.clearScreen();
              
        } while (continueManagement == true); 
    }
}
