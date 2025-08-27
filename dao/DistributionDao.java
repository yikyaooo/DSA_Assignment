/**
 *
 * @author raphael
 */
package dao;

import adt.CircularLinkedList;
import adt.CircularLinkedListInterface;
import adt.DictionaryInterface;
import adt.HashedDictionary;
import adt.LinkedListInterface;
import control.DoneeSystem;
import entity.Distribution;
import entity.Donee;

public class DistributionDao {
    private static CircularLinkedListInterface<Distribution> distributionList = new CircularLinkedList<>();
    private static DoneeSystem doneeS = DoneeSystem.getInstance();
    
    public CircularLinkedListInterface<Distribution> initializeDistributionList(){
        LinkedListInterface<Donee> doneeList = doneeS.getDoneeList();
        DictionaryInterface<String, Integer> itemList1 = new HashedDictionary<>();
        DictionaryInterface<String, Integer> itemList2 = new HashedDictionary<>();
        DictionaryInterface<String, Integer> itemList3 = new HashedDictionary<>();
        DictionaryInterface<String, Integer> itemList4 = new HashedDictionary<>();
        DictionaryInterface<String, Integer> itemList5 = new HashedDictionary<>();
        DictionaryInterface<String, Integer> itemList6 = new HashedDictionary<>();
        
        itemList1.add("meat", 1);
        itemList2.add("clothes", 2);
        itemList3.add("water", 4);
        itemList4.add("rice", 5);
        itemList5.add("cash",100);
        itemList6.add("cash", 500);
        itemList1.add("books",7);
        itemList1.add("fish", 8);
        itemList2.add("coke", 9);
        itemList3.add("fish",1);
                
        distributionList.add(new Distribution(doneeList.getEntry(1),"Kajang","19/01/2024", true, itemList1));
        distributionList.add(new Distribution(doneeList.getEntry(3),"Semenyih","11/04/2024", true, itemList2));
        distributionList.add(new Distribution(doneeList.getEntry(1),"Temiang", "21/09/2024", true, itemList3));
        distributionList.add(new Distribution(doneeList.getEntry(4),"Ipoh", "27/11/2024", true, itemList4));
        distributionList.add(new Distribution(doneeList.getEntry(5),"Johor Bahru", itemList5));
        distributionList.add(new Distribution(doneeList.getEntry(6),"Wangsa Maju,", itemList6));
        
        
        
        
        return distributionList;
    }
}
