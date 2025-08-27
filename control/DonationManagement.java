/**
 *
 * @author YEOH YIK YAO
 */
package control;
import java.util.Scanner;
import entity.*;
import adt.*;
import dao.DonationManagementDao;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

public class DonationManagement {
    private static Scanner scanner = new Scanner(System.in);
    private DonationManagementDao donationDao;
    private static DonationManagement instance = null;
    private static donorSystem donorS = donorSystem.getInstance();
    private InventoryControl inventoryC;
    private DictionaryInterface<Integer, Donation> donationList = new HashedDictionary<>();
    private static int currentID = 7;
    
    public DonationManagement(){
        donationDao = new DonationManagementDao();
        this.donationList = donationDao.initializeDonationList();
    }
    
    public static DonationManagement getInstance(){
        if (instance == null){
            instance = new DonationManagement();
        }
        return instance;
    }
    
    
    public DictionaryInterface<Integer, Donation> getDonationList(){
        return donationList;
    }

    public static void managementMenu(){
        System.out.println("1) Add a new donation");
        System.out.println("2) Remove a donation");
        System.out.println("3) Search donation details");
        System.out.println("4) Amend donation details");
        System.out.println("5) Track donated items in categories");
        System.out.println("6) List donation by different donor");
        System.out.println("7) List all donations");
        System.out.println("8) Filter donation based on criteria");
        System.out.println("9) Generate summary reports");
        System.out.println("10) Exit ");
    }
    
    public void addNewDonation() {
        ListInterface<Donor> donorList = donorS.getDonorList();
        System.out.print("Enter Donor ID: ");
        int donorID = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        Iterator<Donor> donorIterator = donorList.getIterator();
        Donor foundDonor = null;

        // Find the donor by ID
        while (donorIterator.hasNext()) {
            Donor donor = donorIterator.next();
            if (donor.getDonorID() == donorID) {
                foundDonor = donor;
                break;
            }
        }

        if (foundDonor != null) {
            DictionaryInterface<Item, Integer> itemList = new HashedDictionary<>();
            inventoryC = InventoryControl.getInstance();
            boolean addMoreItems = true;
            while (addMoreItems) {
                Item item = new Item();
                System.out.print("Enter Donation Item Name: ");
                String itemName = scanner.nextLine().toLowerCase();
                item.setItemName(itemName);

                System.out.print("Enter Donation Category: ");
                String donationCategory = scanner.nextLine().toLowerCase();
                item.setCategory(donationCategory);

                System.out.print("Enter Donation Amount: ");
                int donationAmount = scanner.nextInt();
                scanner.nextLine(); 
               
                itemList.add(item, donationAmount);
                
                inventoryC.addAmount(itemName,donationAmount);
                inventoryC.addAmountOriginal(itemName, donationAmount);
                System.out.print("Do you want to add another item? (y/n): ");
                String response = scanner.nextLine().trim().toLowerCase();
                addMoreItems = response.equals("y");
            }

            // Create a new Donation object with the list of items
            Donation newDonation = new Donation(foundDonor, itemList);
            donationList.add(currentID++, newDonation);
            System.out.println("Donation added successfully!");

        } else {
            System.out.println("Donor not found. Please ensure the Donor ID is correct.");
        }
    }
    
    
    
    public boolean removeDonation() {
        boolean isRemoved = false;
        int donationID;
        
        System.out.println("Enter donation ID to remove: ");
        donationID = scanner.nextInt();
        scanner.nextLine();
        Donation donation = donationList.getValue(donationID);
        if(donation != null){
            donationList.remove(donationID);
            isRemoved = true;                
            System.out.println("Donation with ID " + donationID + " has been removed.");
        }
        

        if (!isRemoved) {
            System.out.println("Donation with ID " + donationID + " not found.");
        }

        return isRemoved;
    }
    
    public void searchDonation() {
        System.out.print("Enter Donation ID: ");
        int donationID = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        
        boolean hasDonations = false;
        Donation donation = donationList.getValue(donationID);
                
        if (donation != null) {
            System.out.println("--------------------------------------------------------------------------------------------------------");
            System.out.println("Donation ID: "+ donationID);
            System.out.println(donation);
            System.out.println("--------------------------------------------------------------------------------------------------------");
            hasDonations = true;
        }

        if (!hasDonations) {
            System.out.println("No donations found for Donation ID " + donationID);
        }
    }
    
    public boolean amendDonation() {
        boolean isEdited = false;     
        int donationID;
        String edit;

        
        System.out.println("Enter donation ID to amend: ");
        donationID = scanner.nextInt();
        scanner.nextLine();
        Donation donation = donationList.getValue(donationID);
        
        if (donation!=null){
            boolean editMoreItems = true;
            DictionaryInterface<Item, Integer> itemList = donation.getItemList();
            Iterator<EntryInterface<Item, Integer>> itemIterator = itemList.getIterator();
            System.out.print("Do you want to edit donation date? y for Yes, other for No: ");
            edit = scanner.nextLine();
            if(edit.equals("y")){
                System.out.print("Enter new Donation Date (dd/MM/yyyy) (current: " + donation.getDate() + "): ");
                String newDate = scanner.nextLine();
                donation.setDate(newDate);
            }

            while (editMoreItems && itemIterator.hasNext()) {              
                EntryInterface<Item, Integer> itemEntry = itemIterator.next();
                Item item = itemEntry.getKey();
                int amount = itemEntry.getValue();            
                System.out.println("--------------------------------------------------------------------------------------------------------");
                System.out.println("Current item in the donation:");
                System.out.println("Item Name: " + item.getItemName() + "\nCategory: " + item.getCategory() + "\nAmount: " + amount);
                System.out.println("--------------------------------------------------------------------------------------------------------");
                System.out.print("Do you want to edit donation name? y for Yes, other for No: ");
                edit = scanner.nextLine();
                if(edit.equals("y")){
                    System.out.print("Enter new Donation Name: ");
                    String newName = scanner.nextLine();
                    item.setItemName(newName);
                }

                System.out.print("Do you want to edit donation category? y for Yes, other for No: ");
                edit = scanner.nextLine();
                if(edit.equals("y")){
                    System.out.print("Enter new Donation Category: ");
                    String newCategory = scanner.nextLine();
                    item.setCategory(newCategory);
                }


                System.out.print("Do you want to edit donation amount? y for Yes, other for No: ");
                edit = scanner.nextLine();
                int newAmount;
                if(edit.equals("y")){
                    System.out.print("Enter new Amount: ");
                    newAmount = scanner.nextInt();
                    scanner.nextLine();
                }else{
                    newAmount = amount;
                }



                itemList.add(item, newAmount);
                System.out.println("Donation details updated successfully!");
                isEdited = true;

                System.out.print("Do you want to amend another item? (y/n): ");
                String response = scanner.nextLine().trim().toLowerCase();
                editMoreItems = response.equals("y");
                if(!itemIterator.hasNext()){
                    System.out.println("No more Item in this Donation !!!");
                }
            }
               
            
        }
        

        if (!isEdited) {
            System.out.println("Donation with ID " + donationID + " not found.");
        }

        return isEdited;
    }
    
    public void trackCategory() {
        DictionaryInterface<String, Integer> categoryInventory = new HashedDictionary<>();
        
         Iterator<EntryInterface<Integer, Donation>> iterator = donationList.getIterator();
    while (iterator.hasNext()) {
        EntryInterface<Integer, Donation> entry = iterator.next();
        Donation donation = entry.getValue();

        // Get the item list from the donation
        DictionaryInterface<Item, Integer> itemList = donation.getItemList();

        // Iterate through each item in the donation
        Iterator<EntryInterface<Item, Integer>> itemIterator = itemList.getIterator();
        while (itemIterator.hasNext()) {
            EntryInterface<Item, Integer> itemEntry = itemIterator.next();
            Item item = itemEntry.getKey();
            Integer amount = itemEntry.getValue();

            // Get the category of the item
            String itemCategory = item.getCategory();

            // Aggregate the quantity of items by category
            if (categoryInventory.contains(itemCategory)) {
                Integer currentAmount = categoryInventory.getValue(itemCategory);
                categoryInventory.replace(itemCategory, currentAmount + amount);
            } else {
                categoryInventory.add(itemCategory, amount);
            }
        }
    }

        
        Iterator<EntryInterface<String, Integer>> categoryIterator = categoryInventory.getIterator();

        System.out.println("--------------------------------------------------------------------------------------------------------");
        while (categoryIterator.hasNext()) {
            EntryInterface<String, Integer> entry = categoryIterator.next();
            String category = entry.getKey();
            Integer amount = entry.getValue();
            System.out.println(category + " : " + amount);            
          
        }
        System.out.println("--------------------------------------------------------------------------------------------------------");

        
    }

    public void listDonationsByDonorID() {
        System.out.print("Enter Donor ID: ");
        int donorID = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        Iterator<EntryInterface<Integer, Donation>> iterator = donationList.getIterator();
        boolean hasDonations = false;

        System.out.println("Donations made by Donor ID " + donorID + ":");

        while (iterator.hasNext()) {
            EntryInterface<Integer, Donation> entry = iterator.next();
            Integer donationID = entry.getKey();
            Donation donation = entry.getValue();
            
            if (entry != null && donation.getDonor().getDonorID() == donorID) {
                System.out.println("--------------------------------------------------------------------------------------------------------");
                System.out.println("Donation ID: "+ donationID);
                System.out.println(donation);
                System.out.println("--------------------------------------------------------------------------------------------------------");
                hasDonations = true;
            }
        }

        if (!hasDonations) {
            System.out.println("No donations found for Donor ID " + donorID);
        }
    }
   


    
    public void listAllDonations() {
        Iterator<EntryInterface<Integer, Donation>> iterator = donationList.getIterator();

        System.out.println("Listing all donations:");
        while (iterator.hasNext()) {
            EntryInterface<Integer, Donation> entry = iterator.next();
            Integer donationID= entry.getKey();
            Donation donation = entry.getValue();
            
            System.out.println("--------------------------------------------------------------------------------------------------------");
            System.out.println("Donation ID: "+ donationID);
            System.out.println(donation);
            System.out.println("--------------------------------------------------------------------------------------------------------");
        }
    }
    
    public void filterDonations() {
        System.out.println("1. Category");
        System.out.println("2. Date Range");
        System.out.println("3. Quantity");
        System.out.print("Filter donations by:");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1: // Filter by Category
                System.out.print("Enter donation category: ");
                String category = scanner.nextLine().toLowerCase();
                filterByCategory(category);
                break;
            case 2: // Filter by Date Range
                System.out.print("Enter start date (dd/MM/yyyy): ");
                String startDateStr = scanner.nextLine();
                System.out.print("Enter end date (dd/MM/yyyy): ");
                String endDateStr = scanner.nextLine();
                filterByDateRange(startDateStr, endDateStr);
                break;
            case 3: // Filter by Quantity
                System.out.print("Enter minimum quantity: ");
                int minQuantity = scanner.nextInt();
                scanner.nextLine();
                filterByQuantity(minQuantity);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
    
    private void filterByCategory(String category) {
        Iterator<EntryInterface<Integer, Donation>> iterator = donationList.getIterator();
        boolean hasDonations = false;

        System.out.println("Donations with category " + category + ":");

        while (iterator.hasNext()) {
            EntryInterface<Integer, Donation> entry = iterator.next();
            Integer donationID = entry.getKey();
            Donation donation = entry.getValue();

            HashedDictionary<Item, Integer> filteredItemList = ((HashedDictionary<Item, Integer>) donation.getItemList())
                    .filterByKey(item -> item.getCategory().equals(category));

            if (!filteredItemList.isEmpty()) {
                System.out.println("--------------------------------------------------------------------------------------------------------");
                System.out.println("Donation ID: " + donationID);
                StringBuilder result = new StringBuilder();
                result.append("Donor: ").append(donation.getDonor()).append("\nDate: ").append(donation.getDate()).append("\nDonated Items:\n");

                Iterator<EntryInterface<Item, Integer>> itemIterator = filteredItemList.getIterator();
                while (itemIterator.hasNext()) {
                    EntryInterface<Item, Integer> itemEntry = itemIterator.next();
                    Item item = itemEntry.getKey();
                    Integer amount = itemEntry.getValue();

                    result.append(item.getItemName()).append(" (").append(item.getCategory()).append("): ").append(amount).append("\n");
                }

                System.out.println(result);
                System.out.println("--------------------------------------------------------------------------------------------------------");

                hasDonations = true;
            }
        }

        if (!hasDonations) {
            System.out.println("No donations found for category " + category);
        }
    }


    private void filterByDateRange(String startDateStr, String endDateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate startDate = LocalDate.parse(startDateStr, formatter);
        LocalDate endDate = LocalDate.parse(endDateStr, formatter);
        
        Iterator<EntryInterface<Integer, Donation>> iterator = donationList.getIterator();
        boolean hasDonations = false;

        System.out.println("Donations between " + startDate + " and " + endDate + " :");

        while (iterator.hasNext()) {
            
            EntryInterface<Integer, Donation> entry = iterator.next();
            Integer donationID = entry.getKey();
            Donation donation = entry.getValue();
            LocalDate donationDate = donation.getDate();

            
            if ((donationDate.isEqual(startDate) || donationDate.isAfter(startDate)) &&
                (donationDate.isEqual(endDate) || donationDate.isBefore(endDate))) {
                System.out.println("--------------------------------------------------------------------------------------------------------");
                System.out.println("Donation ID: "+ donationID);
                System.out.println(donation);
                System.out.println("--------------------------------------------------------------------------------------------------------");
                hasDonations = true;
            }
        }

        if (!hasDonations) {
            System.out.println("No donations found between " + startDate + " and " + endDate + " :");
        }
    }

    private void filterByQuantity(int minAmount) {
        Iterator<EntryInterface<Integer, Donation>> iterator = donationList.getIterator();
        boolean hasDonations = false;

        System.out.println("Donations with a minimum amount of " + minAmount + ":");

        while (iterator.hasNext()) {
            EntryInterface<Integer, Donation> entry = iterator.next();
            Integer donationID = entry.getKey();
            Donation donation = entry.getValue();

            HashedDictionary<Item, Integer> filteredItemList = ((HashedDictionary<Item, Integer>) donation.getItemList())
                    .filterByValue(amount -> amount >= minAmount);

            if (!filteredItemList.isEmpty()) {
                System.out.println("--------------------------------------------------------------------------------------------------------");
                System.out.println("Donation ID: " + donationID);
                StringBuilder result = new StringBuilder();
                result.append("Donor: ").append(donation.getDonor()).append("\nDate: ").append(donation.getDate()).append("\nDonated Items:\n");

                Iterator<EntryInterface<Item, Integer>> itemIterator = filteredItemList.getIterator();
                while (itemIterator.hasNext()) {
                    EntryInterface<Item, Integer> itemEntry = itemIterator.next();
                    Item item = itemEntry.getKey();
                    Integer amount = itemEntry.getValue();

                    result.append(item.getItemName()).append(" (").append(item.getCategory()).append("): ").append(amount).append("\n");
                }

                System.out.println(result);
                System.out.println("--------------------------------------------------------------------------------------------------------");

                hasDonations = true;
            }
        }

        if (!hasDonations) {
            System.out.println("No donations found with a minimum amount of " + minAmount);
        }
    }
   

    
    public void generateReport() {
        inventoryC = InventoryControl.getInstance();
        DictionaryInterface<String, Integer> inventory = inventoryC.getOriginalInventoryList();
        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();
        String[] quarter = {"Q1", "Q2", "Q3", "Q4"};
        System.out.println("--------------------------------------------------------------------------------------------------------");
        System.out.println("Summary Report:");
        System.out.println("--------------------------------------------------------------------------------------------------------");
        
        ListInterface<DictionaryInterface<String,Integer>> inventories = new ArrayList<>();
        
        inventories.add(getQuarterInventory("01/01/"+currentYear, "31/03/"+currentYear));
        inventories.add(getQuarterInventory("01/04/"+currentYear, "30/06/"+currentYear));
        inventories.add(getQuarterInventory("01/07/"+currentYear, "30/09/"+currentYear));
        inventories.add(getQuarterInventory("01/10/"+currentYear, "31/12/"+currentYear));
        
        
        for(int i = 0; i<inventories.getNumberOfEntries(); i++){
            System.out.println("--------------------------------------------------------------------------------------------------------");
            System.out.println("Total donations in "+ quarter[i]);
            System.out.println("--------------------------------------------------------------------------------------------------------");

            DictionaryInterface<String,Integer> quarterInventory = inventories.getEntry(i+1);
            Iterator<EntryInterface<String, Integer>> inventoryIterator = quarterInventory.getIterator();
            while (inventoryIterator.hasNext()) {
                EntryInterface<String, Integer> entry = inventoryIterator.next();
                String itemName = entry.getKey();
                Integer totalQuantity = entry.getValue();
                System.out.println(itemName + " : " + totalQuantity);
            }
            System.out.println("--------------------------------------------------------------------------------------------------------");
        }
        
        
        System.out.println("--------------------------------------------------------------------------------------------------------");
        System.out.println("Total donations" );
        System.out.println("--------------------------------------------------------------------------------------------------------");
        Iterator<EntryInterface<String, Integer>> inventoryIterator = inventory.getIterator();
        while (inventoryIterator.hasNext()) {
            EntryInterface<String, Integer> entry = inventoryIterator.next();
            String itemName = entry.getKey();
            Integer totalQuantity = entry.getValue();
            System.out.println(itemName + " : " + totalQuantity);
        }

        System.out.println("--------------------------------------------------------------------------------------------------------");
        

    }
    
    private DictionaryInterface<String, Integer> getQuarterInventory(String startDateStr, String endDateStr){
        DictionaryInterface<String, Integer> inventory = new HashedDictionary<>();
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate startDate = LocalDate.parse(startDateStr, formatter);
        LocalDate endDate = LocalDate.parse(endDateStr, formatter);
        
        Iterator<EntryInterface<Integer, Donation>> donationIterator = donationList.getIterator();
        
        while (donationIterator.hasNext()) {
            EntryInterface<Integer, Donation> entry = donationIterator.next();
            Donation donation = entry.getValue();
            
            LocalDate donationDate = donation.getDate();
            DictionaryInterface<Item, Integer> itemList = donation.getItemList();
            
            Iterator<EntryInterface<Item, Integer>> itemIterator = itemList.getIterator();
            
            if ((donationDate.isEqual(startDate) || donationDate.isAfter(startDate)) &&
                (donationDate.isEqual(endDate) || donationDate.isBefore(endDate))){
                while (itemIterator.hasNext()) {
                    EntryInterface<Item, Integer> itemEntry = itemIterator.next();
                    Item item = itemEntry.getKey();
                    String itemName = item.getItemName();
                    Integer quantity = itemEntry.getValue();

                    // Aggregate quantities by item name
                    if (inventory.contains(itemName)) {
                        Integer currentQuantity = inventory.getValue(itemName);
                        inventory.replace(itemName, currentQuantity + quantity);
                    } else {
                        inventory.add(itemName, quantity);
                    }
                }
            }               
        }
        return inventory;
    }
    
    
}
