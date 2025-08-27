/**
 *
 * @author GAN WEI JIAN
 */
package entity;

public class Volunteer {
    
    private int volunteerID;
    private static int currentID = 1;
    private String name;
    private String phone;
    
    
    public Volunteer(){
        
    }
    
    public Volunteer(String name, String phone){
        this.volunteerID = currentID;
        currentID ++;
        this.name = name;
        this.phone = phone;
    }
    
    public void setVolunteerID(int volunteerID){
        this.volunteerID = volunteerID;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public void setPhone(String phone){
        this.phone = phone;
    }
    
    public int getVolunteerID(){
        return volunteerID;
    }
    
    public String getName(){
        return name;
    }
    
    
    public String getPhone(){
        return phone;
    }
    
    public String getVolunteerName() {
        return this.name;
    }
    
    @Override
    public String toString(){
        return "VolunteerID: " + volunteerID + "    Name: " + name +  "      Phone: " + phone; 
    }


    
}

