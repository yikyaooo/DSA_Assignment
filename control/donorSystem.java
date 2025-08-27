/**
 *
 * @author OOI KERR CHII
 */
package control;
import java.util.Scanner;
import entity.*;
import adt.*;
import dao.DonorDao;
import java.util.Iterator;

public class donorSystem {
    private static Scanner scanner = new Scanner(System.in);
    private DonorDao donorDao;
    private ListInterface<Donor> donorList = new ArrayList<>();
    private static donorSystem instance = null;
    private static DonationManagement dm = DonationManagement.getInstance();
    
    
    public donorSystem(){ 
        donorDao = new DonorDao();
        this.donorList = donorDao.initializeDonorList();
    }
    
    public static donorSystem getInstance(){
        if (instance == null){
            instance = new donorSystem();
        }
        return instance;
    }
    
    public ListInterface<Donor> getDonorList(){
        return donorList;
    }
    
    public void addNewDonor() {
        System.out.print("Enter Donor Name: ");
        String name = scanner.nextLine();
        
        System.out.print("Enter Donor Category: ");
        String category = scanner.nextLine();
        
        System.out.print("Enter Donor Contact Number: ");
        String phone = scanner.nextLine();
        
        // Create a new Donor object
        Donor newDonor = new Donor(name, category, phone);
        
        donorList.add(newDonor);
        System.out.println("Donor added successfully!");
    }
    
    public void removeDonor() {
        System.out.print("Enter Donor ID to remove: ");
        int donorID = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        int indexToRemove = -1;

        // Find the index of the donor with the specified ID
        for (int i = 1; i <= donorList.getNumberOfEntries(); i++) {  // Loop from 1 to getNumberOfEntries()
            Donor donor = donorList.getEntry(i);
            if (donor.getDonorID() == donorID) {
                indexToRemove = i;
                break;
            }
        }

        // If found, remove the donor by index
        if (indexToRemove != -1) {
            donorList.remove(indexToRemove);  // Remove by index
            System.out.println("Donor with ID " + donorID + " has been removed.");
        } else {
            System.out.println("Donor with ID " + donorID + " not found.");
        }
    }
    
    public void updateDonor() {
        System.out.print("Enter Donor ID to update: ");
        int donorID = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        Iterator<Donor> iterator = donorList.getIterator();
        Donor donorToUpdate = null;

        while (iterator.hasNext()) {
            Donor donor = iterator.next();
            if (donor.getDonorID() == donorID) {
                donorToUpdate = donor;
                break;
            }
        }

        if (donorToUpdate != null) {
            // Show current donor details
            System.out.println("--------------------------------------------------------------------------------------------------------");
            System.out.println("Current Donor Details:");
            System.out.println("Name: " + donorToUpdate.getName());
            System.out.println("Category: " + donorToUpdate.getCategory());
            System.out.println("Phone: " + donorToUpdate.getPhone());
            System.out.println("--------------------------------------------------------------------------------------------------------");

            // Ask if the user wants to update each field
            System.out.print("Do you want to update the name? (yes/no): ");
            String updateName = scanner.nextLine();
            if (updateName.equalsIgnoreCase("yes")) {
                System.out.print("Enter new name: ");
                String newName = scanner.nextLine();
                donorToUpdate.setName(newName);
            }

            System.out.print("Do you want to update the category? (yes/no): ");
            String updateCategory = scanner.nextLine();
            if (updateCategory.equalsIgnoreCase("yes")) {
                System.out.print("Enter new category: ");
                String newCategory = scanner.nextLine();
                donorToUpdate.setCategory(newCategory);
            }

            System.out.print("Do you want to update the phone number? (yes/no): ");
            String updatePhone = scanner.nextLine();
            if (updatePhone.equalsIgnoreCase("yes")) {
                System.out.print("Enter new phone number: ");
                String newPhone = scanner.nextLine();
                donorToUpdate.setPhone(newPhone);
            }

            System.out.println("Donor details updated successfully!");
        } else {
            System.out.println("Donor with ID " + donorID + " not found.");
        }
    }
 
    public void searchDonor() {
        System.out.print("Enter Donor ID to search: ");
        int donorID = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        Iterator<Donor> iterator = donorList.getIterator();
        boolean found = false;

        while (iterator.hasNext()) {
            Donor donor = iterator.next();
            if (donor.getDonorID() == donorID) {
                System.out.println(donor);
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Donor with ID " + donorID + " not found.");
        }
    }

    
    public void listDonor() {
        DictionaryInterface<Integer, Donation> donationList = dm.getDonationList();
        Iterator<Donor> iterator = donorList.getIterator();
        while (iterator.hasNext()) {
            Donor donor = iterator.next();
            System.out.println("--------------------------------------------------");
            System.out.println("Donor ID: " + donor.getDonorID());
            System.out.println("Name: " + donor.getName());
            System.out.println("Category: " + donor.getCategory());
            System.out.println("Phone Number: " + donor.getPhone());

            // Print the donations made by this donor
            Iterator<EntryInterface<Integer, Donation>> donationIterator = donationList.getIterator();
            boolean hasDonations = false;
            while (donationIterator.hasNext()) {
                EntryInterface<Integer, Donation> entry = donationIterator.next();
                Donation donation = entry.getValue();
                if (donation.getDonor().getDonorID() == donor.getDonorID()) {
                    if (!hasDonations) {
                        System.out.println("Donations:");
                        hasDonations = true;
                    }
                    System.out.println("  Donation ID: " + entry.getKey());
                    DictionaryInterface<Item, Integer> itemList = donation.getItemList();
                    Iterator<EntryInterface<Item, Integer>> itemIterator = itemList.getIterator();
                    while (itemIterator.hasNext()) {
                        EntryInterface<Item, Integer> itemEntry = itemIterator.next();
                        Item item = itemEntry.getKey();
                        Integer amount = itemEntry.getValue();
                        System.out.println("    Item: " + item.getItemName() + " (" + item.getCategory() + "): " + amount);
                    }
                }
            }
            if (!hasDonations) {
                System.out.println("  No donations.");
            }
            System.out.println("--------------------------------------------------");
        }
    }

    
    public void filterDonor() {
        System.out.print("Enter category to filter by: ");
        String category = scanner.nextLine();

        Iterator<Donor> iterator = donorList.getIterator();
        boolean hasDonors = false;

        System.out.println("Donors with category " + category + ":");

        while (iterator.hasNext()) {
            Donor donor = iterator.next();
            if (donor.getCategory().equals(category)) {
                System.out.println(donor);
                hasDonors = true;
            }
        }

        if (!hasDonors) {
            System.out.println("No donors found with category " + category);
        }
    }
    
    public void sortDonors(String criteria) {
        int n = donorList.getNumberOfEntries();
        for (int i = 1; i <= n - 1; i++) {
            for (int j = 1; j <= n - i; j++) {
                Donor donor1 = donorList.getEntry(j);
                Donor donor2 = donorList.getEntry(j + 1);

                if (criteria.equalsIgnoreCase("name")) {
                    // Case-insensitive comparison for names
                    if (donor1.getName().compareToIgnoreCase(donor2.getName()) > 0) {
                        donorList.replace(j, donor2);
                        donorList.replace(j + 1, donor1);
                    }
                } else if (criteria.equalsIgnoreCase("id")) {
                    // Comparison for IDs
                    if (donor1.getDonorID() > donor2.getDonorID()) {
                        donorList.replace(j, donor2);
                        donorList.replace(j + 1, donor1);
                    }
                }
            }
        }
        // Print message after sorting
        if (criteria.equalsIgnoreCase("name")) {
            System.out.println("Donors sorted by name:");
        } else if (criteria.equalsIgnoreCase("id")) {
            System.out.println("Donors sorted by ID:");
        } else {
            System.out.println("Invalid sorting criteria.");
        }

        // Optionally, list the donors to show the result
        listDonor();
    }
 



    public void generateReport() {
        DictionaryInterface<String, Integer> categoryCount = new HashedDictionary<>();
        Iterator<Donor> iterator = donorList.getIterator();

        while (iterator.hasNext()) {
            Donor donor = iterator.next();
            String category = donor.getCategory();

            if (categoryCount.contains(category)) {
                int count = categoryCount.getValue(category);
                categoryCount.replace(category, count + 1);
            } else {
                categoryCount.add(category, 1);
            }
        }

        Iterator<EntryInterface<String, Integer>> reportIterator = categoryCount.getIterator();
        System.out.println("Donor count by category:");
        while (reportIterator.hasNext()) {
            EntryInterface<String, Integer> entry = reportIterator.next();
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}