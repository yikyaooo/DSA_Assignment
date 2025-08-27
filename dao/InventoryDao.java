/**
 *
 * @author raphael
 */
package dao;

import adt.CircularLinkedListInterface;
import adt.DictionaryInterface;
import adt.EntryInterface;
import adt.HashedDictionary;
import control.DistributionControl;
import control.DonationManagement;
import entity.Distribution;
import entity.Donation;
import entity.Item;
import java.util.Iterator;

public class InventoryDao {
    private static DictionaryInterface<String, Integer> inventoryList = new HashedDictionary<>();
    private static DictionaryInterface<String, Integer> reducedInventoryList = new HashedDictionary<>();
    private static DonationManagement donationS = DonationManagement.getInstance();
    private static DistributionControl distributionC = DistributionControl.getInstance();

    public DictionaryInterface<String, Integer> initializeOriginalInventoryList(){

        DictionaryInterface<Integer, Donation> donations = donationS.getDonationList();

        Iterator<EntryInterface<Integer, Donation>> donationIterator = donations.getIterator();
        while (donationIterator.hasNext()) {
            EntryInterface<Integer, Donation> donationEntry = donationIterator.next();
            Donation donation = donationEntry.getValue();

            // Iterate over each item in the donation
            DictionaryInterface<Item, Integer> itemList = donation.getItemList();
            Iterator<EntryInterface<Item, Integer>> itemIterator = itemList.getIterator();

            while (itemIterator.hasNext()) {
                EntryInterface<Item, Integer> itemEntry = itemIterator.next();
                Item item = itemEntry.getKey();
                String itemName = item.getItemName();
                Integer quantity = itemEntry.getValue();

                // Aggregate quantities by item name
                if (inventoryList.contains(itemName)) {
                    Integer currentQuantity = inventoryList.getValue(itemName);
                    inventoryList.replace(itemName, currentQuantity + quantity);
                } else {
                    inventoryList.add(itemName, quantity);
                }
            }
        }
        return inventoryList;
    }

    public DictionaryInterface<String, Integer> initializeInventoryList(){
        DictionaryInterface<Integer, Donation> donations = donationS.getDonationList();
        CircularLinkedListInterface<Distribution> distributionList = distributionC.getDistributionList();
        Iterator<Distribution> distributionIterator = distributionList.getIterator();
        
        
        Iterator<EntryInterface<Integer, Donation>> donationIterator = donations.getIterator();
        while (donationIterator.hasNext()) {
            EntryInterface<Integer, Donation> donationEntry = donationIterator.next();
            Donation donation = donationEntry.getValue();

            // Iterate over each item in the donation
            DictionaryInterface<Item, Integer> itemList = donation.getItemList();
            Iterator<EntryInterface<Item, Integer>> itemIterator = itemList.getIterator();

            while (itemIterator.hasNext()) {
                EntryInterface<Item, Integer> itemEntry = itemIterator.next();
                Item item = itemEntry.getKey();
                String itemName = item.getItemName();
                Integer quantity = itemEntry.getValue();

                // Aggregate quantities by item name
                if (reducedInventoryList.contains(itemName)) {
                    Integer currentQuantity = reducedInventoryList.getValue(itemName);
                    reducedInventoryList.replace(itemName, currentQuantity + quantity);
                } else {
                    reducedInventoryList.add(itemName, quantity);
                }
            }
        }
        
        while (distributionIterator.hasNext()){
            Distribution currentDistribution = distributionIterator.next();
            DictionaryInterface<String,Integer> itemDistributed = currentDistribution.getItemDistributed();
            Iterator<EntryInterface<String,Integer>> itemIterator = itemDistributed.getIterator();
            
            while(itemIterator.hasNext()){
                EntryInterface<String, Integer> itemEntry = itemIterator.next();
                String itemName = itemEntry.getKey();
                Integer quantity = itemEntry.getValue();

                // Aggregate quantities by item name
                if (reducedInventoryList.contains(itemName)) {
                    Integer currentQuantity = reducedInventoryList.getValue(itemName);
                    reducedInventoryList.replace(itemName, currentQuantity - quantity);
                }
            }
        }
        
        return reducedInventoryList;  
    }
}
