/**
 *
 * @author YEOH YIK YAO
 */
package entity;

public class Item {
    private String itemName;
    private String category;
    
    public Item(){
        
    }
    
    public Item(String itemName, String category){
        this.itemName = itemName;
        this.category = category;
    }
    
    public void setItemName(String itemName){
        this.itemName = itemName;
    }
    
    public void setCategory(String category){
        this.category = category;
    }
    
    public String getItemName(){
        return itemName;
    }
    
    public String getCategory(){
        return category;
    }
    
    @Override
    public String toString(){
        return "Item Name: " + itemName + "     Category: " + category +"\n";
    }
}
