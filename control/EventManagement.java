/**
 *
 * @author GAN WEI JIAN
 */
package control;


import java.util.Scanner;
import java.util.Iterator;
import dao.EventDao;
import entity.*;
import adt.*;

public class EventManagement {
    private static Scanner scanner = new Scanner(System.in);
    private EventDao eventDao;
    private static EventManagement instance = null;
    private static ArrayQueueInterface<Event> eventQueue = new ArrayQueue<>();
    private static ListInterface<Volunteer> volunteerList = new ArrayList<>();
    
    public EventManagement(){
        eventDao = new EventDao();
        this.volunteerList = eventDao.getVolunteerList();
        this.eventQueue = eventDao.initializeEventList();
    }
    
    public static EventManagement getInstance(){
        if (instance == null){
            instance = new EventManagement();
        }
        return instance;
    }
    
    
    public ArrayQueueInterface<Event> getEventQueue(){
        return eventQueue;
    }
    
    public ListInterface<Volunteer> getVolunteerList(){
        return volunteerList;
    }

    private Volunteer findVolunteerByID(int volunteerID) {
    Iterator<Volunteer> volunteerIterator = volunteerList.getIterator();
    while (volunteerIterator.hasNext()) {
        Volunteer volunteer = volunteerIterator.next();
        if (volunteer.getVolunteerID() == volunteerID) {
            return volunteer;
            }
        }
        return null; 
    }

    

    public void addEvent() {
        System.out.println("-----------------------------------");
        System.out.print("\nEnter event name: ");
        String name = scanner.nextLine();
        System.out.print("Enter event date (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        System.out.print("Enter event location: ");
        String location = scanner.nextLine();
        System.out.print("Enter event description: ");
        String description = scanner.nextLine();
        
        ListInterface<Volunteer> assignedVolunteers = new ArrayList<>();
        boolean addMoreVolunteer = true;
        
        while(addMoreVolunteer){
            System.out.print("Enter volunteer ID: ");
            int volunteerID = scanner.nextInt();
            scanner.nextLine(); 
            Iterator<Volunteer> iterator = volunteerList.getIterator();
            Volunteer foundVolunteer = null;

            while (iterator.hasNext()) {
                Volunteer volunteer = iterator.next();
                if (volunteer.getVolunteerID() == volunteerID) {
                    foundVolunteer = volunteer;
                    break;
                }
            }
            
            if(foundVolunteer != null){
                assignedVolunteers.add(foundVolunteer);
                System.out.println("Volunteer added successfully! ");

            }else{
                System.out.println("Volunteer not found. Please ensure the Volunteer ID is correct.");
            } 
                       
            System.out.print("Do you want to add another volunteer? (y/n): ");
            String response = scanner.nextLine().trim().toLowerCase();
            addMoreVolunteer = response.equals("y");
        }  

        Event event = new Event(name, date, location, description, assignedVolunteers);
        eventQueue.enqueue(event);
        System.out.println("\nEvent added successfully!");
        System.out.println("-----------------------------------");
    }

    public void removeEvent() {
        if (!eventQueue.isEmpty()) {
            Event removedEvent = eventQueue.dequeue();
            System.out.println("-----------------------------------");
            System.out.println("\nRemoved Event: \n" + removedEvent + "\n");
            } else {
                System.out.println("\nNo events to remove. ");
                System.out.println("-----------------------------------");
         }
    }

    public void searchEvent() {
        System.out.println("-----------------------------------");
        System.out.print("Enter event ID to search: ");
        int eventID = Integer.parseInt(scanner.nextLine());
        boolean found = false;

        Iterator<Event> eventIterator = eventQueue.iterator();
        while (eventIterator.hasNext()) {
            Event event = eventIterator.next();
            if (event.getEventID() == eventID) {
                System.out.println("Event found: \n" + event);
                System.out.println("-----------------------------------");
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("-----------------------------------");
            System.out.println("Event not found.");
            System.out.println("-----------------------------------");
        }
    }

    public void amendEvent() {
        System.out.println("-----------------------------------");
        System.out.print("Enter event ID to amend: ");
        int eventID = Integer.parseInt(scanner.nextLine());
        boolean found = false;

        Iterator<Event> eventIterator = eventQueue.iterator();
        while (eventIterator.hasNext()) {
            Event event = eventIterator.next();
            if (event.getEventID() == eventID) {
                // Display current event details
                System.out.println("\nCurrent Event Details:");
                System.out.println(event);

                System.out.print("Enter new event name (or press Enter to keep current): ");
                String newName = scanner.nextLine();
                if (!newName.isEmpty()) event.setEventName(newName);

                System.out.print("Enter new event date (or press Enter to keep current): ");
                String newDate = scanner.nextLine();
                if (!newDate.isEmpty()) event.setEventDate(newDate);

                System.out.print("Enter new event location (or press Enter to keep current): ");
                String newLocation = scanner.nextLine();
                if (!newLocation.isEmpty()) event.setEventLocation(newLocation);

                System.out.print("Enter new event description (or press Enter to keep current): ");
                String newDescription = scanner.nextLine();
                if (!newDescription.isEmpty()) event.setEventDescription(newDescription);

                System.out.print("Do you want to update the volunteer list? (y/n): ");
                String updateVolunteers = scanner.nextLine();
                if (updateVolunteers.equalsIgnoreCase("y")) {
                    ListInterface<Volunteer> newVolunteerList = new ArrayList<>();
                    boolean addMoreVolunteer = true;

                    while (addMoreVolunteer) {
                        System.out.print("Enter volunteer ID: ");
                        int volunteerID = scanner.nextInt();
                        scanner.nextLine();
                        Volunteer foundVolunteer = findVolunteerByID(volunteerID);

                        if (foundVolunteer != null) {
                            newVolunteerList.add(foundVolunteer);
                            System.out.println("Volunteer added successfully!");
                        } else {
                            System.out.println("Volunteer not found. Please ensure the Volunteer ID is correct.");
                        }

                        System.out.print("Do you want to add another volunteer? (y/n): ");
                        String response = scanner.nextLine().trim().toLowerCase();
                        addMoreVolunteer = response.equals("y");
                    }
                    event.setVolunteerAssigned(newVolunteerList);
                }

                System.out.println("Event updated successfully!");
                System.out.println("-----------------------------------");
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("-----------------------------------");
            System.out.println("Event not found.");
        }
    }

    public void listAllEvents() {
        if (!eventQueue.isEmpty()) {
              System.out.println("-----------------------------------");
              System.out.println("\nAll Events:");
              Iterator<Event> eventIterator = eventQueue.iterator();
              while (eventIterator.hasNext()) {
                  Event event = eventIterator.next();
                  System.out.println(event + "\n");
              }
          } else {
              System.out.println("No events available.");
          }
      }

    public void removeEventFromVolunteer() {
        System.out.println("-----------------------------------");
        System.out.print("Enter event ID to remove a volunteer from: ");
        int eventID = Integer.parseInt(scanner.nextLine());
        boolean eventFound = false;

        Iterator<Event> eventIterator = eventQueue.iterator();
        while (eventIterator.hasNext()) {
            Event event = eventIterator.next();
            if (event.getEventID() == eventID) {
                eventFound = true;

                boolean continueRemoving = true;

                while (continueRemoving) {
                    // Display current volunteers in the event
                    System.out.println("\nCurrent Volunteers for Event ID " + eventID + ":");
                    ListInterface<Volunteer> currentVolunteers = event.getVolunteerList();
                    Iterator<Volunteer> volunteerIterator = currentVolunteers.getIterator();
                    while (volunteerIterator.hasNext()) {
                        Volunteer volunteer = volunteerIterator.next();
                        System.out.println("Volunteer ID: " + volunteer.getVolunteerID() + ", Name: " + volunteer.getVolunteerName());
                    }

                    System.out.print("Enter volunteer ID to remove: ");
                    int volunteerID = Integer.parseInt(scanner.nextLine());

                    ListInterface<Volunteer> updatedVolunteerList = new ArrayList<>();
                    volunteerIterator = currentVolunteers.getIterator();
                    boolean volunteerFound = false;
                    while (volunteerIterator.hasNext()) {
                        Volunteer volunteer = volunteerIterator.next();
                        if (volunteer.getVolunteerID() == volunteerID) {
                            volunteerFound = true;
                        } else {
                            updatedVolunteerList.add(volunteer);
                        }
                    }

                    event.setVolunteerAssigned(updatedVolunteerList);

                    if (volunteerFound) {
                        System.out.println("Volunteer ID " + volunteerID + " removed from event ID " + eventID);
                    } else {
                        System.out.println("Volunteer with ID " + volunteerID + " not found in event ID " + eventID);
                    }

                    // Ask if the user wants to remove another volunteer
                    System.out.print("Do you want to remove another volunteer? (y/n): ");
                    String response = scanner.nextLine().trim().toLowerCase();
                    if (!response.equals("y")) {
                        continueRemoving = false;
                    }
                }

                break;
            }
        }

        if (!eventFound) {
            System.out.println("Event with ID " + eventID + " not found.");
        }

        System.out.println("-----------------------------------");
    }

    public void listEventsForVolunteer() {
        System.out.println("-----------------------------------");
        System.out.print("Enter volunteer ID to list events for: ");
        int volunteerID = Integer.parseInt(scanner.nextLine());

        // Find the volunteer directly within this method
        Volunteer volunteer = null;
        Iterator<Volunteer> volunteerIterator = volunteerList.getIterator();
        while (volunteerIterator.hasNext()) {
            Volunteer v = volunteerIterator.next();
            if (v.getVolunteerID() == volunteerID) {
                volunteer = v;
                break;
            }
        }

        if (volunteer == null) {
            System.out.println("Volunteer not found.");
            System.out.println("-----------------------------------");
            return;
        }

        System.out.println("Events for Volunteer ID " + volunteerID + ":");
        Iterator<Event> eventIterator = eventQueue.iterator();
        boolean found = false;
        while (eventIterator.hasNext()) {
            Event event = eventIterator.next();
            if (event.getVolunteerList().contains(volunteer)) {
                System.out.println(event + "\n");
                found = true;
            }
        }

        if (!found) {
            System.out.println("-----------------------------------");
            System.out.println("No events found for this volunteer.");
            System.out.println("-----------------------------------");
        }
    }

    public void generateSummaryReports() {
        System.out.println("-----------------------------------");
        System.out.println("Summary of All Events:");

        int totalEvents = eventQueue.size();
        int totalVolunteersAssigned = 0;
        int uniqueVolunteersCount = 0;
        int[] volunteerIDs = new int[1000]; // Adjust size if necessary
        int volunteerIDIndex = 0;

        // Iterate through all events
        Iterator<Event> eventIterator = eventQueue.iterator();
        while (eventIterator.hasNext()) {
            Event event = eventIterator.next();

            // Manually count volunteers in the event
            Iterator<Volunteer> volunteerIterator = event.getVolunteerList().getIterator();
            int volunteerCount = 0;
            while (volunteerIterator.hasNext()) {
                volunteerIterator.next();
                volunteerCount++;
            }
            totalVolunteersAssigned += volunteerCount;

            // Check each volunteer in the event
            volunteerIterator = event.getVolunteerList().getIterator();
            while (volunteerIterator.hasNext()) {
                Volunteer volunteer = volunteerIterator.next();
                int volunteerID = volunteer.getVolunteerID();

                // Check if the volunteer ID is already in the array
                boolean isUnique = true;
                for (int i = 0; i < volunteerIDIndex; i++) {
                    if (volunteerIDs[i] == volunteerID) {
                        isUnique = false;
                        break;
                    }
                }

                // If unique, add to array
                if (isUnique) {
                    volunteerIDs[volunteerIDIndex] = volunteerID;
                    volunteerIDIndex++;
                    uniqueVolunteersCount++;
                }
            }
        }

        System.out.println("Total Events: " + totalEvents);
        System.out.println("Total Volunteers Assigned: " + totalVolunteersAssigned);
        System.out.println("Total Unique Volunteers: " + uniqueVolunteersCount);
        System.out.println("-----------------------------------");
    }
}
    
