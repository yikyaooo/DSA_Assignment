/**
 *
 * @author SIM JIN YANG
 */
package boundary;
import control.DoneeSystem;
import java.util.Scanner;

public class DoneeUI {
    private static DoneeSystem doneeS = DoneeSystem.getInstance();
    private systemMain systemMain;
    public static DoneeUI instance = null;
    
    public static DoneeUI getInstance(){
        if(instance == null){
            instance = new DoneeUI();
        }
        return instance;
    }
    
    public void DoneeMenu(){
        
        System.out.println("1) Add a new donee");
        System.out.println("2) Remove a donee");
        System.out.println("3) Update donee details");
        System.out.println("4) Search donee details");
        System.out.println("5) List all donees");
        System.out.println("6) Filter donee based on criteria");
        System.out.println("7) Generate summary reports");
        System.out.println("8) Sort donees");
        System.out.println("9) Exit");
    }
    
    public void DoneeStart(){
        Scanner scanner = new Scanner(System.in);
        int choice;
        boolean continueManagement = true;
        do {
            DoneeMenu();
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
            switch (choice) {
                case 1:
                    doneeS.addNewDonee();
                    break;
                case 2:
                    doneeS.removeDonee();
                    break;
                case 3:
                    doneeS.updateDonee();
                    break;
                case 4:
                    doneeS.searchDonee();
                    break;
                case 5:
                    doneeS.listDonees();
                    break;
                case 6:
                    doneeS.filterDonee();
                    break;
                case 7:
                    doneeS.generateDoneeReport();
                    break;
                case 8:
                    doneeS.sortDonees(); 
                    
                    break;
                case 9:
                    continueManagement = false;
                    System.out.println("Exiting");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
            System.out.print("Press [ENTER] to continue....");
            scanner.nextLine();
            systemMain.clearScreen();
        } while (continueManagement);
    }
}
