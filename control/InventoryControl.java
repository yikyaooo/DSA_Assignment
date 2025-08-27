/**
 *
 * @author YEOH YIK YAO
 */
package control;

import adt.DictionaryInterface;
import adt.HashedDictionary;
import dao.InventoryDao;

public class InventoryControl {
    private InventoryDao inventoryDao;
    private static InventoryControl instance = null;
    private DictionaryInterface<String, Integer> originalInventoryList = new HashedDictionary<>();    
    private DictionaryInterface<String, Integer> inventoryList = new HashedDictionary<>();    
    public InventoryControl(){
        inventoryDao = new InventoryDao();
        this.inventoryList = inventoryDao.initializeInventoryList();
        this.originalInventoryList = inventoryDao.initializeOriginalInventoryList();
        
    }


    public static InventoryControl getInstance(){
        if (instance == null){
            instance = new InventoryControl();
        }
        return instance;
    }
    
    public DictionaryInterface<String, Integer> getInventoryList(){
        return inventoryList;
    }

    public DictionaryInterface<String, Integer> getOriginalInventoryList() {
        return originalInventoryList;
    }
    
    
    public void addAmountOriginal(String name, int amount){
        Integer currentAmountInInventory = originalInventoryList.getValue(name);
        if (currentAmountInInventory != null) {
            currentAmountInInventory += amount;
            originalInventoryList.add(name, currentAmountInInventory);
        }else{
            originalInventoryList.add(name, amount);
        }
    }
    
    public void addAmount(String name, int amount){
        Integer currentAmountInInventory = inventoryList.getValue(name);
        if (currentAmountInInventory != null) {
            currentAmountInInventory += amount;
            inventoryList.add(name, currentAmountInInventory);
        }else{
            inventoryList.add(name, amount);
        }
    }
    public void reduceAmount(String name, int amount){
        Integer currentAmountInInventory = inventoryList.getValue(name);
        currentAmountInInventory -= amount;
        inventoryList.add(name,currentAmountInInventory);
    }
}
