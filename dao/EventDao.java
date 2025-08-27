/**
 *
 * @author GAN WEI JIAN
 */
package dao;

import adt.ArrayList;
import adt.ArrayQueue;
import adt.ArrayQueueInterface;
import adt.ListInterface;
import entity.Event;
import entity.Volunteer;

public class EventDao {
    
    private static ListInterface<Volunteer> volunteers1 = new ArrayList<>();
    private static ListInterface<Volunteer> volunteers2 = new ArrayList<>();
    private static ListInterface<Volunteer> volunteers3 = new ArrayList<>();
    private static ListInterface<Volunteer> volunteers4 = new ArrayList<>();
    private static ListInterface<Volunteer> volunteers5 = new ArrayList<>();
    private static ListInterface<Volunteer> volunteers6 = new ArrayList<>();
    private static ArrayQueueInterface<Event> eventQueue = new ArrayQueue<>();
    private static VolunteerDao vd = new VolunteerDao();
    private static ListInterface<Volunteer> volunteerList = vd.initialiseVolunteerList();
    public ArrayQueueInterface<Event> initializeEventList(){
        

        volunteers1.add(volunteerList.getEntry(1));
        volunteers2.add(volunteerList.getEntry(2));
        volunteers3.add(volunteerList.getEntry(3));
        volunteers4.add(volunteerList.getEntry(4));
        volunteers5.add(volunteerList.getEntry(5));
        volunteers6.add(volunteerList.getEntry(6));
        
        volunteers1.add(volunteerList.getEntry(7));
        volunteers2.add(volunteerList.getEntry(3));
        volunteers3.add(volunteerList.getEntry(4));
        volunteers4.add(volunteerList.getEntry(5));
        volunteers5.add(volunteerList.getEntry(6));
        volunteers6.add(volunteerList.getEntry(1));
        
        volunteers1.add(volunteerList.getEntry(3));
        volunteers2.add(volunteerList.getEntry(6));
        volunteers3.add(volunteerList.getEntry(7));
       
        
        Event event1 = new Event("10th year anniversary", "2024-01-02", "TARUMT", "Drink Party", volunteers1);
        Event event2 = new Event("Blessed Foundation", "2024-03-11", "TARC", "Charity", volunteers2);
        Event event3 = new Event("Donation", "2024-04-21", "Sunway University", "Drink Party", volunteers3);
        Event event4 = new Event("Happy Go Lucky", "2024-06-11", "Taylors", "Food Party", volunteers4);
        Event event5 = new Event("Shaking Drill", "2024-07-14", "Stadium Bukit Jalil", "Jalo", volunteers5);
        Event event6 = new Event("Relax", "2024-11-29", "University Malaya", "Bello", volunteers6);
        
        eventQueue.enqueue(event1);
        eventQueue.enqueue(event2);
        eventQueue.enqueue(event3);
        eventQueue.enqueue(event4);
        eventQueue.enqueue(event5);
        eventQueue.enqueue(event6);

        
        return eventQueue;
     }
    
    public ListInterface<Volunteer> getVolunteerList(){
        return volunteerList;
    }
}
