/**
 *
 * @author raphael
 */
package control;

import adt.CircularLinkedList;
import adt.CircularLinkedListInterface;
import adt.DictionaryInterface;
import adt.EntryInterface;
import adt.HashedDictionary;
import adt.LinkedListInterface;
import boundary.systemMain;
import dao.DistributionDao;
import entity.Distribution;
import entity.Donee;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Scanner;


public class DistributionControl {

    private static Scanner scanner = new Scanner(System.in);
    private DistributionDao distributionDao;
    private static DistributionControl instance = null;
    private DoneeSystem doneeS;
    private InventoryControl inventoryC;
    private CircularLinkedListInterface<Distribution> distributionList = new CircularLinkedList<>();

    public DistributionControl() {
        distributionDao = new DistributionDao();
        distributionList = distributionDao.initializeDistributionList();
    }

    public static DistributionControl getInstance() {
        if (instance == null) {
            instance = new DistributionControl();
        }
        return instance;
    }

    public CircularLinkedListInterface<Distribution> getDistributionList() {
        return distributionList;
    }

    public void addNewDistribution() {
        doneeS = DoneeSystem.getInstance();
        LinkedListInterface<Donee> doneeList = doneeS.getDoneeList();
        systemMain.clearScreen();
        System.out.print("Kindly enter a Donee ID: ");
        int doneeID = scanner.nextInt();
        scanner.nextLine();
        Iterator<Donee> doneeIterator = doneeList.getIterator();
        Donee foundDonee = null;

        while (doneeIterator.hasNext()) {
            Donee donee = doneeIterator.next();
            if (donee.getDoneeID() == doneeID) {
                foundDonee = donee;
                break;
            }
        }

        if (foundDonee != null) {
            inventoryC = InventoryControl.getInstance();
            DictionaryInterface<String, Integer> inventoryList = inventoryC.getInventoryList();
            LinkedListInterface<String> itemsNeeded = foundDonee.getItems();
            String doneeType = foundDonee.getType();
            Iterator<EntryInterface<String, Integer>> inventoryIterator;
            Iterator<String> itemIterator = itemsNeeded.getIterator();

            boolean itemsNeededFound = true;  // Assume all items are found initially

// Iterate over each item in the itemsNeeded list
            while (itemIterator.hasNext()) {
                String item = itemIterator.next();
                boolean itemFoundInInventory = false;  // Flag to track if current item is found in inventory

                // Reset the inventory iterator for each item to check
                inventoryIterator = inventoryList.getIterator();

                // Iterate over each item in the inventory list
                while (inventoryIterator.hasNext()) {
                    EntryInterface<String, Integer> inventoryEntry = inventoryIterator.next();
                    String inventoryItem = inventoryEntry.getKey();
                    // Check if the inventory item matches the needed item
                    if (inventoryItem.equals(item)) {
                        itemFoundInInventory = true;  // Mark as found
                        break;  // No need to continue searching in the inventory
                    }
                }

                // If the current item is not found in the inventory, set the flag to false
                if (!itemFoundInInventory) {
                    itemsNeededFound = false;
                    break;  // No need to check further as one needed item is missing
                }
            }

            if (itemsNeededFound) {
                int donationQuantity;

                // Determine the donation quantity based on doneeType
                if (doneeType.equals("Individual")) {
                    donationQuantity = 3;
                } else if (doneeType.equals("Family")) {
                    donationQuantity = 5;
                } else { // Assuming "Organisation" or any other type
                    donationQuantity = 7;
                }

                // Check if the inventory has enough of each item to donate
                itemIterator = itemsNeeded.getIterator();
                boolean canDonate = true;

                while (itemIterator.hasNext()) {
                    String item = itemIterator.next();
                    inventoryIterator = inventoryList.getIterator();
                    boolean sufficientInventory = false;

                    while (inventoryIterator.hasNext()) {
                        EntryInterface<String, Integer> inventoryEntry = inventoryIterator.next();
                        String inventoryItem = inventoryEntry.getKey();
                        int inventoryQuantity = inventoryEntry.getValue();

                        if (inventoryItem.equalsIgnoreCase(item)) {  // Make sure case doesn't matter
                            if (inventoryItem.equalsIgnoreCase("cash")) {
                                if (inventoryQuantity >= donationQuantity * 100) {
                                    sufficientInventory = true;  // Sufficient quantity to donate
                                }
                            } else if (inventoryQuantity >= donationQuantity) {
                                sufficientInventory = true;
                            }
                            break;  // Item found and checked, exit loop
                        }
                    }

                    if (!sufficientInventory) {
                        canDonate = false;  // Not enough inventory for this item
                        break;  // Exit early if any item does not have enough inventory
                    }
                }

                // If all items have sufficient inventory, proceed with donation
                if (canDonate) {
                    System.out.print("Kindly enter the city and state item being distributed to(Eg:Kajang, Semenyih): ");
                    String address = scanner.nextLine();
                    DictionaryInterface<String, Integer> itemDistributed = new HashedDictionary<>();
                    itemIterator = itemsNeeded.getIterator();

                    while (itemIterator.hasNext()) {
                        String item = itemIterator.next();
                        inventoryIterator = inventoryList.getIterator();

                        while (inventoryIterator.hasNext()) {
                            EntryInterface<String, Integer> inventoryEntry = inventoryIterator.next();
                            String inventoryItem = inventoryEntry.getKey();
                            //int inventoryQuantity = inventoryEntry.getValue();

                            if (inventoryItem.equals(item)) {
                                if (item.equals("cash")) {
                                    donationQuantity *= 100;
                                }
                                // Update the inventory quantity after donation
                                //inventoryEntry.setValue(inventoryQuantity - donationQuantity);
                                itemDistributed.add(item, donationQuantity);
                                inventoryC.reduceAmount(item, donationQuantity);
                                System.out.println("\n\nDonated " + donationQuantity + " of " + item + " to " + foundDonee.getName());
                                break;
                            }
                        }
                    }
                    Distribution newDistribution = new Distribution(foundDonee, address, itemDistributed);
                    displayDistributionDetails(newDistribution);
                    distributionList.add(newDistribution);
                } else {
                    System.out.println("Not enough inventory to fulfill the donation for " + foundDonee.getName());
                }
            } else {
                System.out.println("Some items needed are missing in the inventory.");
            }

        } else {
            System.out.println("Unable to find the Donee ID.");
        }
    }

    public void updateDistribution() {
        Iterator<Distribution> distributionIterator = distributionList.getIterator();
        int distributionID;
        systemMain.clearScreen();
        System.out.print("Kindly enter distribution ID wished to be updated: ");
        distributionID = scanner.nextInt();
        int option;

        Distribution updateTarget = null;
        while (distributionIterator.hasNext()) {
            Distribution distribution = distributionIterator.next();
            if (distribution.getDistributionID() == distributionID) {
                updateTarget = distribution;
            }
        }

        if (updateTarget != null) {
            boolean continueUpdating = true;
            String continueUpdate;
            boolean editMoreItems = true;
            DictionaryInterface<String, Integer> itemDistributed = updateTarget.getItemDistributed();
            Iterator<EntryInterface<String, Integer>> itemIterator = itemDistributed.getIterator();
            while (continueUpdating) {
                System.out.println("What would you like to update?");
                System.out.println("1) Address Distributed");
                System.out.println("2) Date Distributed");
                System.out.println("3) Arrival Status");
                System.out.println("4) Quantity of Item Distributed");
                System.out.println("5) Exit");

                System.out.print("Kindly enter option: ");
                option = scanner.nextInt();
                scanner.nextLine();

                switch (option) {
                    case 1:
                        System.out.print("Kindly enter new address: ");
                        String address = scanner.nextLine();
                        updateTarget.setAddress(address);
                        System.out.print("Adress update successful. ");
                        System.out.print("\nContinue updating this distribution(Yes/No?): ");
                        continueUpdate = scanner.next().toLowerCase();
                        if (continueUpdate.equals("no")) {
                            continueUpdating = false;
                        }
                        break;
                    case 2:
                        System.out.print("Kindly enter date distributed(dd/MM/yyyy): ");
                        String newDate = scanner.nextLine();
                        updateTarget.setDate(newDate);
                        System.out.print("Date distributed update successful. ");
                        System.out.print("\nContinue updating this distribution(Yes/No?): ");
                        continueUpdate = scanner.next().toLowerCase();
                        if (continueUpdate.equals("no")) {
                            continueUpdating = false;
                        }
                        break;
                    case 3:
                        System.out.print("Has the distribution arrived to its destination(Yes/No): ");
                        String arrived = scanner.next().toLowerCase();

                        if (arrived.equals("yes")) {
                            updateTarget.setArrived(true);
                            System.out.println("Update successful");
                        } else if (arrived.equals("no")) {
                            updateTarget.setArrived(false);
                            System.out.println("Update successful");
                        } else {
                            System.out.print("Invalid option");
                        }
                        System.out.print("\nContinue updating this distribution(Yes/No?): ");
                        continueUpdate = scanner.next().toLowerCase();
                        if (continueUpdate.equals("no")) {
                            continueUpdating = false;
                        }
                        break;
                    case 4:
                        inventoryC = InventoryControl.getInstance();
                        DictionaryInterface<String, Integer> inventoryList = inventoryC.getInventoryList();
                        Iterator<EntryInterface<String, Integer>> inventoryIterator;
                        int newAmount;
                        while (editMoreItems && itemIterator.hasNext()) {
                            EntryInterface<String, Integer> currentItem = itemIterator.next();
                            String itemName = currentItem.getKey();
                            int amount = currentItem.getValue();
                            System.out.println("--------------------------------------------------------------------------------------------------------");
                            System.out.println("Current item in the donation:");
                            System.out.println("Item Name: " + itemName + "\nAmount Distributed: " + amount);
                            System.out.println("--------------------------------------------------------------------------------------------------------");
                            System.out.print("Do you want to change the amount distributed)(Y/N): ");
                            String change = scanner.next().toUpperCase();
                            if (change.equals("Y")) {
                                inventoryIterator = inventoryList.getIterator();
                                while (inventoryIterator.hasNext()) {
                                    EntryInterface<String, Integer> currentInventoryItem = inventoryIterator.next();
                                    String inventoryItemName = currentInventoryItem.getKey();
                                    int inventoryItemTotal = currentInventoryItem.getValue();
                                    if (inventoryItemName.equals(itemName)) {
                                        inventoryItemTotal += amount;
                                        System.out.println("The current total amount of " + inventoryItemName + " is " + inventoryItemTotal);
                                        System.out.print("Kindly add the new amount of item distributed: ");
                                        newAmount = scanner.nextInt();
                                        currentItem.setValue(newAmount);
                                        currentInventoryItem.setValue(inventoryItemTotal - newAmount);
                                        System.out.println("Update successful");
                                    }
                                }
                            }
                        }

                        System.out.print("\nContinue updating this distribution(Yes/No?): ");
                        continueUpdate = scanner.next().toLowerCase();
                        if (continueUpdate.equals("no")) {

                            continueUpdating = false;
                        }
                        break;
                    case 5:
                        continueUpdating = false;
                        break;
                }

            }

            displayDistributionDetails(updateTarget);
        } else {
            System.out.println("Distribution with ID " + distributionID + " does not exist.");
        }
    }

    public void removeDistribution() {
        Iterator<Distribution> distributionIterator = distributionList.getIterator();
        int distributionID;
        systemMain.clearScreen();
        System.out.print("Kindly enter distribution ID wished to be deleted: ");
        distributionID = scanner.nextInt();
        scanner.nextLine();

        Distribution removalTarget = null;
        while (distributionIterator.hasNext()) {
            Distribution distribution = distributionIterator.next();
            if (distribution.getDistributionID() == distributionID) {
                removalTarget = distribution;
            }
        }
        if (removalTarget != null) {
            boolean successful = distributionList.delete(removalTarget);
            if (successful) {
                System.out.println("Deletion of distribution with ID " + distributionID + " is successful");
            } else {
                System.out.println("Failed to delete distribution with ID " + distributionID);
            }
        } else {
            System.out.println("Distribution with ID " + distributionID + " does not exist.");
        }
    }

    public void monitorItems() {
        boolean continueMonitor = true;

        while (continueMonitor) {
            systemMain.clearScreen();
            System.out.println("What would you like to track?");
            System.out.println("1) All distribution details.");
            System.out.println("2) Arrival status of distribution");
            System.out.println("3) Total amount of each item a donee received.");
            System.out.println("4) Exit");
            System.out.print("Kindly enter your choice: ");
            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    // Resetting the iterator for each new loop
                    Iterator<Distribution> distributionIterator1 = distributionList.getIterator();
                    while (distributionIterator1.hasNext()) {
                        Distribution currentDistribution = distributionIterator1.next();
                        displayDistributionDetails(currentDistribution);
                    }
                    break;

                case 2:
                    System.out.println("Would you like to see those that have arrived, or have not?");
                    System.out.println("1) Has arrived");
                    System.out.println("2) Has not arrived");
                    System.out.print("Kindly enter your choice: ");
                    int choice = scanner.nextInt();

                    // Resetting the iterator again
                    Iterator<Distribution> distributionIterator2 = distributionList.getIterator();
                    while (distributionIterator2.hasNext()) {
                        Distribution currentDistribution = distributionIterator2.next();
                        boolean hasArrived = currentDistribution.getArrived();

                        if ((choice == 1 && hasArrived) || (choice == 2 && !hasArrived)) {
                            System.out.println("Distribution ID: " + currentDistribution.getDistributionID());
                            System.out.println("Address: " + currentDistribution.getAddress());
                            System.out.println("Items distributed:");

                            DictionaryInterface<String, Integer> itemDistributed = currentDistribution.getItemDistributed();
                            Iterator<EntryInterface<String, Integer>> itemIterator = itemDistributed.getIterator();

                            while (itemIterator.hasNext()) {
                                EntryInterface<String, Integer> entry = itemIterator.next();
                                String item = entry.getKey();
                                Integer quantity = entry.getValue();
                                System.out.println(item + ": " + quantity);
                            }
                        }
                    }
                    break;

                case 3:
                    Iterator<Distribution> distributionIterator3 = distributionList.getIterator();
                    System.out.print("Kindly enter Donee ID: ");
                    int doneeID = scanner.nextInt();
                    scanner.nextLine(); // Consume newline character

                    // Dictionary to hold the total amount of each item received by the Donee
                    DictionaryInterface<String, Integer> itemTotals = new HashedDictionary<>();
                    boolean doneeExist = false;

                    // Iterate through distributions to find the Donee with the specified ID
                    while (distributionIterator3.hasNext()) {
                        Distribution currentDistribution = distributionIterator3.next();
                        // Assuming Distribution has a method getDoneeID() or similar to get the Donee ID
                        if (doneeID == currentDistribution.getDonee().getDoneeID() && currentDistribution.getArrived()) {
                            doneeExist = true;

                            // Aggregate item quantities for this Donee
                            Iterator<EntryInterface<String, Integer>> itemIterator = currentDistribution.getItemDistributed().getIterator();
                            while (itemIterator.hasNext()) {
                                EntryInterface<String, Integer> currentItem = itemIterator.next();
                                String itemName = currentItem.getKey();
                                int amount = currentItem.getValue();

                                // Add to the totals for this Donee
                                if (itemTotals.contains(itemName)) {
                                    int currentTotal = itemTotals.getValue(itemName);
                                    itemTotals.add(itemName, currentTotal + amount);
                                } else {
                                    itemTotals.add(itemName, amount);
                                }
                            }
                        }
                    }

                    if (doneeExist) {
                        // Display the total amounts of each item received by the Donee
                        System.out.println("Total amounts of each item received by Donee ID " + doneeID + ":");
                        Iterator<EntryInterface<String, Integer>> totalIterator = itemTotals.getIterator();
                        while (totalIterator.hasNext()) {
                            EntryInterface<String, Integer> entry = totalIterator.next();
                            String itemName = entry.getKey();
                            int totalAmount = entry.getValue();
                            System.out.println(itemName + ": " + totalAmount);
                        }
                    } else {
                        System.out.println("Donee with ID " + doneeID + " does not exist in the distribution list or have not received any items.");
                    }
                    break;

                case 4:
                    continueMonitor = false;
                    break;

                default:
                    System.out.println("Invalid option, please try again.");
            }

            if (continueMonitor) {
                System.out.print("Do you wish to continue monitoring? (Yes/No): ");
                String toContinue = scanner.next().toLowerCase();
                if (toContinue.equals("no")) {
                    continueMonitor = false;
                }
            }
        }
    }

    public void generateQuarterlyReport() {
    LocalDate currentDate = LocalDate.now();
    int currentYear = currentDate.getYear();
    String[] quarterLabels = {"Q1", "Q2", "Q3", "Q4"};
    
    System.out.println("--------------------------------------------------------------------------------------------------------");
    System.out.println("Quarterly Distribution Report:");
    System.out.println("--------------------------------------------------------------------------------------------------------");

    CircularLinkedListInterface<DictionaryInterface<String, Integer>> quarterlyReports = new CircularLinkedList<>();

    quarterlyReports.add(getQuarterDistribution("01/01/" + currentYear, "31/03/" + currentYear));
    quarterlyReports.add(getQuarterDistribution("01/04/" + currentYear, "30/06/" + currentYear));
    quarterlyReports.add(getQuarterDistribution("01/07/" + currentYear, "30/09/" + currentYear));
    quarterlyReports.add(getQuarterDistribution("01/10/" + currentYear, "31/12/" + currentYear));

    for (int i = 0; i < quarterlyReports.getNumberOfEntries(); i++) {
        System.out.println("--------------------------------------------------------------------------------------------------------");
        System.out.println("Total distributions in " + quarterLabels[i]);
        System.out.println("--------------------------------------------------------------------------------------------------------");

        DictionaryInterface<String, Integer> quarterReport = quarterlyReports.getEntry(i + 1);
        Iterator<EntryInterface<String, Integer>> itemIterator = quarterReport.getIterator();
        while (itemIterator.hasNext()) {
            EntryInterface<String, Integer> entry = itemIterator.next();
            String itemName = entry.getKey();
            Integer totalQuantity = entry.getValue();
            System.out.println(itemName + " : " + totalQuantity);
        }
        System.out.println("--------------------------------------------------------------------------------------------------------");
    }

    System.out.println("--------------------------------------------------------------------------------------------------------");
    System.out.println("Total distributions for the year:");
    System.out.println("--------------------------------------------------------------------------------------------------------");

    DictionaryInterface<String, Integer> totalYearlyReport = getTotalAnnualDistribution();
    Iterator<EntryInterface<String, Integer>> totalItemIterator = totalYearlyReport.getIterator();
    while (totalItemIterator.hasNext()) {
        EntryInterface<String, Integer> entry = totalItemIterator.next();
        String itemName = entry.getKey();
        Integer totalQuantity = entry.getValue();
        System.out.println(itemName + " : " + totalQuantity);
    }

    System.out.println("--------------------------------------------------------------------------------------------------------");
}

private DictionaryInterface<String, Integer> getQuarterDistribution(String startDateStr, String endDateStr) {
    DictionaryInterface<String, Integer> distributionReport = new HashedDictionary<>();

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    LocalDate startDate = LocalDate.parse(startDateStr, formatter);
    LocalDate endDate = LocalDate.parse(endDateStr, formatter);

    Iterator<Distribution> distributionIterator = distributionList.getIterator();

    while (distributionIterator.hasNext()) {
        Distribution distribution = distributionIterator.next();
        LocalDate distributionDate = distribution.getDate();

        // Check if distribution date is within the range and items have arrived
        if ((distributionDate.isEqual(startDate) || distributionDate.isAfter(startDate)) &&
            (distributionDate.isEqual(endDate) || distributionDate.isBefore(endDate)) &&
            distribution.getArrived()) {  // Check if items have arrived

            DictionaryInterface<String, Integer> itemsDistributed = distribution.getItemDistributed();
            Iterator<EntryInterface<String, Integer>> itemIterator = itemsDistributed.getIterator();

            while (itemIterator.hasNext()) {
                EntryInterface<String, Integer> itemEntry = itemIterator.next();
                String itemName = itemEntry.getKey();
                Integer quantity = itemEntry.getValue();

                if (distributionReport.contains(itemName)) {
                    Integer currentQuantity = distributionReport.getValue(itemName);
                    distributionReport.replace(itemName, currentQuantity + quantity);
                } else {
                    distributionReport.add(itemName, quantity);
                }
            }
        }
    }
    return distributionReport;
}


private DictionaryInterface<String, Integer> getTotalAnnualDistribution() {
    DictionaryInterface<String, Integer> annualDistribution = new HashedDictionary<>();

    Iterator<Distribution> distributionIterator = distributionList.getIterator();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    LocalDate currentDate = LocalDate.now();
    int currentYear = currentDate.getYear();

    while (distributionIterator.hasNext()) {
        Distribution distribution = distributionIterator.next();
        LocalDate distributionDate = distribution.getDate();

        // Check if distribution date is within the current year and items have arrived
        if (distributionDate.getYear() == currentYear && distribution.getArrived()) {  // Check if items have arrived

            DictionaryInterface<String, Integer> itemsDistributed = distribution.getItemDistributed();
            Iterator<EntryInterface<String, Integer>> itemIterator = itemsDistributed.getIterator();

            while (itemIterator.hasNext()) {
                EntryInterface<String, Integer> itemEntry = itemIterator.next();
                String itemName = itemEntry.getKey();
                Integer quantity = itemEntry.getValue();

                if (annualDistribution.contains(itemName)) {
                    Integer currentQuantity = annualDistribution.getValue(itemName);
                    annualDistribution.replace(itemName, currentQuantity + quantity);
                } else {
                    annualDistribution.add(itemName, quantity);
                }
            }
        }
    }
    return annualDistribution;
}



    private void displayDistributionDetails(Distribution distribution) {
        System.out.println("\n\nDistribution ID: " + distribution.getDistributionID());
        System.out.println("Donee Details: " + distribution.getDonee().toString());
        System.out.println("Address to distribute: " + distribution.getAddress());
        System.out.println("Items arrival status: " + distribution.getArrived());
        System.out.println("Items distributed: ");

        DictionaryInterface<String, Integer> itemDistributed = distribution.getItemDistributed();
        Iterator<EntryInterface<String, Integer>> itemIterator = itemDistributed.getIterator();

        while (itemIterator.hasNext()) {
            EntryInterface<String, Integer> entry = itemIterator.next();
            String item = entry.getKey();
            Integer quantity = entry.getValue();
            System.out.println(item + ": " + quantity);
        }

        System.out.println("Date distributed: " + distribution.getDate());
    }

}
