/**
 *
 * @author raphael
 */
package entity;

import adt.DictionaryInterface;
import adt.HashedDictionary;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Distribution {

    private int distributionID;
    private Donee donee;
    private static int currentID = 1;
    private LocalDate dateDistributed;
    private String address;
    private boolean arrived = false;
    private DictionaryInterface<String, Integer> itemDistributed = new HashedDictionary<>();

    public Distribution(Donee donee, String address, DictionaryInterface<String, Integer> itemDistributed) {
        distributionID = currentID;
        currentID++;
        this.donee = donee;
        this.address = address;
        this.itemDistributed = itemDistributed;
        dateDistributed = LocalDate.now();
    }
    
    
    public Distribution(Donee donee, String address, boolean arrived, DictionaryInterface<String, Integer> itemDistributed) {
        distributionID = currentID;
        currentID++;
        this.donee = donee;
        this.address = address;
        this.arrived = arrived;
        this.itemDistributed = itemDistributed;
        dateDistributed = LocalDate.now();
    }
    
    
    public Distribution(Donee donee, String address, String dateString, boolean arrived, DictionaryInterface<String, Integer> itemDistributed) {
        distributionID = currentID;
        currentID++;
        this.donee = donee;
        this.address = address;
        setDate(dateString);
        this.arrived = arrived;
        this.itemDistributed = itemDistributed;
    }
    
    
    public Distribution(){
        
    }
    
    public LocalDate getDate() {
        return dateDistributed;
    }

    public String getAddress() {
        return address;
    }

    public Donee getDonee() {
        return donee;
    }

    public int getDistributionID() {
        return distributionID;
    }

    public boolean getArrived() {
        return arrived;
    }

    public DictionaryInterface<String, Integer> getItemDistributed() {
        return itemDistributed;
    }

    public void setArrived(boolean arrived) {
        this.arrived = arrived;
    }
    
    public void setDonee(Donee donee){
        this.donee = donee;
    }
    
    public void setAddress(String address){
        this.address = address;
    }
    
    public void setDate(String dateString){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.dateDistributed = LocalDate.parse(dateString, formatter);
    }
    
    public void setItemDistributed(DictionaryInterface<String, Integer> itemDistributed) {
        this.itemDistributed = itemDistributed;
    }
}
