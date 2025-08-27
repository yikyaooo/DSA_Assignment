/**
 *
 * @author SIM JIN YANG
 */
package entity;
import adt.LinkedList;
import java.util.Iterator;

public class Donee {
    private int doneeID;
    private static int currentID = 1;
    private String name;
    private String type;
    private LinkedList<String> items;
    private String phone;

    
    public Donee(String name, String type, LinkedList<String> items, String phone){
        this.doneeID = currentID;
        currentID ++;
        this.name = name;
        this.type = type;
        this.items = items;
        this.phone = phone;
    }
    
    public void setDoneeID(int doneeID) {
        this.doneeID = doneeID;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setGroup(String type) {
        this.type = type;
    }
    
    
    public void setItems(LinkedList<String> items) {
        this.items = items;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public int getDoneeID() {
        return doneeID;
    }
    
    public String getName() {
        return name;
    }
    
    public String getType() {
        return type;
    }
    
    public LinkedList<String> getItems() {
        return items;
    }
    
    public String getPhone() {
        return phone;
    }
    
    @Override
    public String toString() {
        // Convert items list to a readable string
        StringBuilder itemsString = new StringBuilder();
        Iterator<String> itemsIterator = items.getIterator();
        
        // Append each category to the StringBuilder
        while (itemsIterator.hasNext()) {
            itemsString.append(itemsIterator.next());
            if (itemsIterator.hasNext()) {
                itemsString.append(", ");
            }
        }

        return "DoneeID: " + doneeID +
               "    Name: " + name +
               "    Group: " + type +
               "    Category: [" + itemsString.toString() + "]" +
               "    Phone: " + phone;
    }
    
}
