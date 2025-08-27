/**
 *
 * @author GAN WEI JIAN
 */
package boundary;

import control.EventManagement;
import java.util.Scanner;


public class EventUI {
    private static EventManagement eventM = EventManagement.getInstance();
    private systemMain systemMain;
    public static EventUI instance = null;

    
    
    public static EventUI getInstance(){
        if (instance == null){
            instance = new EventUI();
        }
        return instance;
    }
    
    public void eventStart() {
        Scanner scanner = new Scanner(System.in);
        boolean continueEvent = true;
        do {
            System.out.println("\nEvent Management System");
            System.out.println("1) Add a new event");
            System.out.println("2) Remove an event");
            System.out.println("3) Search an event");
            System.out.println("4) Amend an event details");
            System.out.println("5) List all events");
            System.out.println("6) Remove an event from a volunteer");
            System.out.println("7) List all events for a volunteer");
            System.out.println("8) Generate summary reports");
            System.out.println("9) Back to main menu");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    eventM.addEvent();
                    break;
                case 2:
                    eventM.removeEvent();
                    break;
                case 3:
                    eventM.searchEvent();
                    break;
                case 4:
                    eventM.amendEvent();
                    break;
                case 5:
                    eventM.listAllEvents();
                    break;
                case 6:
                    eventM.removeEventFromVolunteer();
                    break;
                case 7:
                    eventM.listEventsForVolunteer();
                    break;
                case 8:
                    eventM.generateSummaryReports();
                    break;
                case 9:
                    continueEvent = false;
                    System.out.println("Exiting");
                    break;
                    
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            System.out.print("Press [ENTER] to continue....");
            scanner.nextLine();
            systemMain.clearScreen();
        } while (continueEvent);
    }
}
