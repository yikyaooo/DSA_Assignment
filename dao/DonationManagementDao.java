/**
 *
 * @author YEOH YIK YAO
 */
package dao;

import adt.DictionaryInterface;
import adt.HashedDictionary;
import adt.ListInterface;
import control.donorSystem;
import entity.Donation;
import entity.Donor;
import entity.Item;

public class DonationManagementDao {
    private static DictionaryInterface<Integer, Donation> donationList = new HashedDictionary<>();
    private static donorSystem donorS = donorSystem.getInstance();
    
    public DictionaryInterface<Integer, Donation> initializeDonationList(){
        ListInterface<Donor> donorList = donorS.getDonorList();
        DictionaryInterface<Item, Integer> itemList1 = new HashedDictionary<>();
        DictionaryInterface<Item, Integer> itemList2 = new HashedDictionary<>();
        DictionaryInterface<Item, Integer> itemList3 = new HashedDictionary<>();
        DictionaryInterface<Item, Integer> itemList4 = new HashedDictionary<>();
        DictionaryInterface<Item, Integer> itemList5 = new HashedDictionary<>();
        DictionaryInterface<Item, Integer> itemList6 = new HashedDictionary<>();
        
        itemList1.add(new Item("meat", "food"), 3);
        itemList2.add(new Item("clothes", "kind"), 5);
        itemList3.add(new Item("water", "drink"), 7);
        itemList4.add(new Item("rice", "food"), 6);
        itemList5.add(new Item("cash", "money"), 300);
        itemList6.add(new Item("cash", "money"), 600);
        itemList1.add(new Item("books", "kind"), 11);
        itemList1.add(new Item("fish", "food"), 15);
        itemList2.add(new Item("coke", "drink"), 9);
        itemList3.add(new Item("fish", "food"), 2);
        
        donationList.add(1, new Donation(donorList.getEntry(2),"15/01/2024", itemList2));
        donationList.add(2, new Donation(donorList.getEntry(5),"03/02/2024", itemList5));
        donationList.add(3, new Donation(donorList.getEntry(1),"20/04/2024", itemList1));
        donationList.add(4, new Donation(donorList.getEntry(1),"10/05/2024", itemList6));
        donationList.add(5, new Donation(donorList.getEntry(3),"02/06/2024", itemList3));
        donationList.add(6, new Donation(donorList.getEntry(4),"18/08/2024", itemList4));
        
        
        return donationList;
    }
}
