/**
 *
 * @author GAN WEI JIAN
 */
package entity;
import adt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Event {
    private int eventID;
    private static int currentID = 1;
    private String eventName;
    private LocalDate eventDate;
    private String eventLocation;
    private String eventDescription;
    private ListInterface<Volunteer> volunteerList = new ArrayList<>();

    public Event(String eventName, String dateString, String eventLocation, String eventDescription, ListInterface<Volunteer> volunteerList) {
        this.eventID = currentID;
        currentID ++;
        this.eventName = eventName;
        setEventDate(dateString);
        this.eventLocation = eventLocation;
        this.eventDescription = eventDescription;
        this.volunteerList = volunteerList; 
    }

    // Getters and setters
    public int getEventID(){
        return eventID;
    }
    
    public void setEventID(int eventID){
        this.eventID = eventID;
    }
    
    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            this.eventDate = LocalDate.parse(dateString, formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Please use YYYY-MM-DD.");
        }
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public ListInterface<Volunteer> getVolunteerList() {
        return volunteerList;
    }

    public void setVolunteerAssigned(ListInterface<Volunteer> volunteerList) {
        this.volunteerList = volunteerList;
    }

    @Override
    public String toString() {
        return "Event ID: " + eventID +  "\nEvent Name: " + eventName + "\nDate: " + eventDate + "\nLocation: " + eventLocation +
                "\nDescription: " + eventDescription + "\nVolunteer Assigned: " + volunteerList;
    }
}
