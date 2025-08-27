/**
 *
 * @author raphael
 */
package boundary;

import control.DistributionControl;
import java.util.Scanner;


public class DistributionUI {
    private static DistributionControl distributionC = DistributionControl.getInstance();
    private systemMain systemMain;
    public static DistributionUI instance = null;
    
    
    public static DistributionUI getInstance(){
        if (instance == null){
            instance = new DistributionUI();
        }
        return instance;
    }
    
    public void distributionMenu(){
        System.out.println("1) Add a new donation distribution");
        System.out.println("2) Update donation distribution details");
        System.out.println("3) Remove a donation distribution");
        System.out.println("4) Monitor distributed items");
        System.out.println("5) Generate Distribution Reports");
        System.out.println("6) Exit");
    }
    
    
    public void distributionStart(){
        Scanner scanner = new Scanner(System.in);
        int choice; 
        boolean continueDistribution = true;
        do {
            distributionMenu();
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();
            switch(choice){
                case 1:
                    distributionC.addNewDistribution();
                    break;
                case 2:
                    distributionC.updateDistribution();
                    break;
                case 3:
                    distributionC.removeDistribution();
                    break;
                case 4:
                    distributionC.monitorItems();
                    break;
                case 5:
                    distributionC.generateQuarterlyReport();
                    break;
                case 6:
                    continueDistribution = false;
                    System.out.println("Exiting");
            }
            System.out.print("Press [ENTER] to continue....");
            scanner.nextLine();
            systemMain.clearScreen();
              
        } while (continueDistribution == true); 
    }
}
