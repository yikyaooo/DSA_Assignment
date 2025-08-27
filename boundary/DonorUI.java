/**
 *
 * @author OOI KERR CHII
 */
package boundary;
import adt.ListInterface;
import java.util.Scanner;
import control.donorSystem;
import entity.Donor;

public class DonorUI {

    private static donorSystem donorS = donorSystem.getInstance();
    private systemMain systemMain;
    public static DonorUI instance = null;

    public static DonorUI getInstance() {
        if (instance == null) {
            instance = new DonorUI();
        }
        return instance;
    }

    public void DonorMenu() {
        System.out.println("1) Add a new donor");
        System.out.println("2) Remove a donor");
        System.out.println("3) Update donor details");
        System.out.println("4) Search donor details");
        System.out.println("5) List all donors");
        System.out.println("6) Filter donor based on criteria");
        System.out.println("7) Sort donors");
        System.out.println("8) Generate summary reports");
        System.out.println("9) Exit");
    }

    public void DonorStart() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        boolean continueManagement = true;
        do {
            DonorMenu();
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    donorS.addNewDonor();
                    break;
                case 2:
                    donorS.removeDonor();
                    break;
                case 3:
                    donorS.updateDonor();
                    break;
                case 4:
                    donorS.searchDonor();
                    break;
                case 5:
                    donorS.listDonor();
                    break;
                case 6:
                    donorS.filterDonor();
                    break;
                case 7:
                    System.out.print("Enter sorting criteria (name/id): ");
                    String criteria = scanner.nextLine();
                    donorS.sortDonors(criteria);
                    break;
                case 8:
                    donorS.generateReport();
                    break;
                case 9:
                    continueManagement = false;
                    System.out.println("Exiting");
            }
            System.out.print("Press [ENTER] to continue....");
            scanner.nextLine();
            systemMain.clearScreen();

        } while (continueManagement == true);
    }
}
