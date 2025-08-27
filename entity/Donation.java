/**
 *
 * @author YEOH YIK YAO
 */
package entity;

import adt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

public class Donation {
    private Donor donor;
    private LocalDate date;
    private DictionaryInterface<Item, Integer> itemList = new HashedDictionary<>();


    public Donation(Donor donor,DictionaryInterface<Item, Integer> itemList ) {
        this.donor = donor;
        this.date = LocalDate.now();
        this.itemList = itemList;
    }
    
    public Donation(Donor donor, String dateString,DictionaryInterface<Item, Integer> itemList ) {
        this.donor = donor;
        setDate(dateString);
        this.itemList = itemList;
    }
    
    public void setDonor(Donor donor){
        this.donor = donor;
    }
    
    
    public void setDate(String dateString){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.date = LocalDate.parse(dateString, formatter);
    }

    
    public Donor getDonor(){
        return donor;
    }
    
    public LocalDate getDate(){
        return date;
    }
    
    public DictionaryInterface<Item, Integer> getItemList(){
        return itemList;
    }
    
    @Override
    public String toString(){
        StringBuilder result = new StringBuilder();
        result.append("Donor: ").append(donor).append("\nDate: ").append(date).append("\nDonated Items:\n");

        Iterator<EntryInterface<Item, Integer>> iterator = itemList.getIterator();
        while (iterator.hasNext()) {
            EntryInterface<Item, Integer> entry = iterator.next();
            result.append(entry.getKey().getItemName()).append(" (").append(entry.getKey().getCategory()).append("): ").append(entry.getValue()).append("\n");
        }

        return result.toString();
    }
}
