/**
 *
 * @author OOI KERR CHII
 */
package entity;

public class Donor{
    private int donorID;
    private static int currentID = 1;
    private String name;
    private String category;
    private String phone;
    
    
    public Donor(){
        
    }
    
    public Donor(String name,String category, String phone){
        this.donorID = currentID;
        currentID ++;
        this.name = name;
        this.category = category;
        this.phone = phone;
    }
    
    public void setDonorID(int donorID){
        this.donorID = donorID;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    
    public void setCategory(String category){
        this.category = category;
    }
    
    public void setPhone(String phone){
        this.phone = phone;
    }
    
    public int getDonorID(){
        return donorID;
    }
    
    public String getName(){
        return name;
    }
    
    
    public String getCategory(){
        return category;
    }
    
    public String getPhone(){
        return phone;
    }
    
    @Override
    public String toString(){
        return "DonorID: " + donorID + "    Name: " + name + "    Category: " + category + "      Phone: " + phone; 
    }
}
