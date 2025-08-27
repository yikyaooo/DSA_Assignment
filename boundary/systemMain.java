/**
 *
 * @author raphael
 */
package boundary;
import java.util.Scanner;

public class systemMain {
    private static Scanner scanner = new Scanner(System.in);
    public static void clearScreen() {
        for (int i = 0; i < 100; i++) {
            System.out.println();
        }
    }
    
       
    public static void mainMenu(){
        System.out.println("1) Donor system");
        System.out.println("2) Donee system");
        System.out.println("3) Donation Management system");
        System.out.println("4) Donation Distribution System"); 
        System.out.println("5) Event system");
        System.out.println("6) Exit");
    }
    
    public static void main(String[] args){
        DoneeUI doneeUI = DoneeUI.getInstance();
        DonorUI donorUI = DonorUI.getInstance();
        DonationManagementUI donationManagementUI = DonationManagementUI.getInstance();
        DistributionUI distributionUI = DistributionUI.getInstance();
        EventUI eventUI = EventUI.getInstance();
        boolean continueMain = true;
        do {
            mainMenu();
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            switch(choice){
                case 1:
                    clearScreen();
                    donorUI.DonorStart();
                    clearScreen();
                    break;
                case 2:
                    clearScreen();
                    doneeUI.DoneeStart();
                    clearScreen();
                    break;
                case 3:
                    clearScreen();
                    donationManagementUI.managementStart();
                    clearScreen();
                    break;
                case 4:
                    clearScreen();
                    distributionUI.distributionStart();
                    clearScreen();
                    break;
                case 5:
                    clearScreen();
                    eventUI.eventStart(); // Call the static method
                    clearScreen();
                    break;
                case 6:
                    continueMain = false;
            }
              
        } while (continueMain == true);

    }
}
