/**
 *
 * @author SIM JIN YANG
 */
package control;
import java.util.Scanner;
import entity.*;
import adt.*;
import dao.DoneeDao;
import java.util.Iterator;
import java.util.Comparator;

public class DoneeSystem {
    private static Scanner scanner = new Scanner(System.in);
    private DoneeDao doneeDao;
    private LinkedListInterface<Donee> doneeList = new LinkedList<>();
    private static DoneeSystem instance = null;
    
    public DoneeSystem(){
        doneeDao = new DoneeDao();
        this.doneeList = doneeDao.initializeDoneeList();
    }
    
    public static DoneeSystem getInstance(){
        if(instance == null){
            instance = new DoneeSystem();
        }
        return instance;
    }
    
    public LinkedListInterface<Donee> getDoneeList(){
        return doneeList;
    }
    
    public void addNewDonee() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Donee Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Donee Type: ");
        String type = scanner.nextLine();

        // Initialize your custom LinkedList for categories
        LinkedList<String> categories = new LinkedList<>();
        String item;
        do {
            System.out.print("Enter Donation Item needed(or 'done' to finish): ");
            item = scanner.nextLine();
            if (!item.equalsIgnoreCase("done")) {
                categories.add(item);
            }
        } while (!item.equalsIgnoreCase("done"));

        System.out.print("Enter Donee Contact Number: ");
        String phone = scanner.nextLine();

        // Create a new Donee object
        Donee newDonee = new Donee(name, type, categories, phone);

        doneeList.add(newDonee);
        System.out.println("Donee added successfully!");
    }
    
    
    public void removeDonee() {
        Scanner scanner = new Scanner(System.in); // Ensure you have a scanner object

        System.out.print("Enter Donee ID to remove: ");
        int doneeID = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        Donee doneeToRemove = null;

        // Find the Donee object with the specified ID
        Iterator<Donee> iterator = doneeList.getIterator();
        while (iterator.hasNext()) {
            Donee donee = iterator.next();
            if (donee.getDoneeID() == doneeID) {
                doneeToRemove = donee;
                break;
            }
        }

        // If found, remove the Donee
        if (doneeToRemove != null) {
            boolean isRemoved = doneeList.remove(doneeToRemove);  // Remove by entry

            if (isRemoved) {
                System.out.println("Donee with ID " + doneeID + " has been removed.");
            } else {
                System.out.println("Failed to remove donee with ID " + doneeID + ".");
            }
        } else {
            System.out.println("Donee with ID " + doneeID + " not found.");
        }
    }

    
    public void updateDonee() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Donee ID to update: ");
        int doneeID = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Iterator<Donee> iterator = doneeList.getIterator();
        Donee doneeToUpdate = null;

        // Find the donee with the specified ID
        while (iterator.hasNext()) {
            Donee donee = iterator.next();
            if (donee.getDoneeID() == doneeID) {
                doneeToUpdate = donee;
                break;
            }
        }

        if (doneeToUpdate != null) {
            // Update donee details
            System.out.print("Enter new name (current: " + doneeToUpdate.getName() + "): ");
            String newName = scanner.nextLine();
            if (!newName.trim().isEmpty()) {
                doneeToUpdate.setName(newName);
            }

            System.out.print("Enter new type (current: " + doneeToUpdate.getType() + "): ");
            String newGroup = scanner.nextLine();
            if (!newGroup.trim().isEmpty()) {
                doneeToUpdate.setGroup(newGroup);
            }

            // Update categories
            LinkedList<String> categories = doneeToUpdate.getItems();
            System.out.println("Current categories: " + categories);
            System.out.println("Options for categories:");
            System.out.println("1) Add new item");
            System.out.println("2) Remove existing item");
            System.out.println("3) Replace all categories");
            System.out.println("4) Leave categories unchanged");
            System.out.print("Enter your choice: ");
            int itemChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (itemChoice) {
                case 1: // Add new item
                    String newItem;
                    do {
                        System.out.print("Enter item to add (or 'done' to finish): ");
                        newItem = scanner.nextLine();
                        if (!newItem.equalsIgnoreCase("done")) {
                            if (!categories.contains(newItem)) {
                                categories.add(newItem);
                            } else {
                                System.out.println("Item already exists.");
                            }
                        }
                    } while (!newItem.equalsIgnoreCase("done"));
                    break;

                case 2: // Remove existing item
                    String itemToRemove;
                    do {
                        System.out.print("Enter item to remove (or 'done' to finish): ");
                        itemToRemove = scanner.nextLine();
                        if (!itemToRemove.equalsIgnoreCase("done")) {
                            if (categories.remove(itemToRemove)) {
                                System.out.println("Item removed.");
                            } else {
                                System.out.println("Item not found.");
                            }
                        }
                    } while (!itemToRemove.equalsIgnoreCase("done"));
                    break;

                case 3: // Replace all categories
                    LinkedList<String> newItems = new LinkedList<>();
                    String item;
                    do {
                        System.out.print("Enter new item (or 'done' to finish): ");
                        item = scanner.nextLine();
                        if (!item.equalsIgnoreCase("done")) {
                            newItems.add(item);
                        }
                    } while (!item.equalsIgnoreCase("done"));
                    doneeToUpdate.setItems(newItems);
                    break;

                case 4: // Leave categories unchanged
                    break;

                default:
                    System.out.println("Invalid choice. Items will remain unchanged.");
                    break;
            }

            System.out.print("Enter new phone number (current: " + doneeToUpdate.getPhone() + "): ");
            String newPhone = scanner.nextLine();
            if (!newPhone.trim().isEmpty()) {
                doneeToUpdate.setPhone(newPhone);
            }

            System.out.println("Donee details updated successfully!");
        } else {
            System.out.println("Donee with ID " + doneeID + " not found.");
        }
    }

    

    public void searchDonee() {
        Scanner scanner = new Scanner(System.in); // Ensure you have a scanner object

        System.out.print("Enter Donee ID to search: ");
        int doneeID = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        Iterator<Donee> iterator = doneeList.getIterator();
        boolean found = false;

        while (iterator.hasNext()) {
            Donee donee = iterator.next();
            if (donee.getDoneeID() == doneeID) {
                System.out.println(donee);
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Donee with ID " + doneeID + " not found.");
        }
    }
    
    public void listDonees() {
        Iterator<Donee> iterator = doneeList.getIterator();
        System.out.println("Listing all donees:");

        while (iterator.hasNext()) {
            Donee donee = iterator.next();
            System.out.println(donee);
        }
    }
 
    
    
    public void filterDonee() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter item to filter by: ");
        String itemToFilter = scanner.nextLine();

        Iterator<Donee> iterator = doneeList.getIterator();
        boolean hasDonees = false;

        System.out.println("Donees with item " + itemToFilter + ":");

        while (iterator.hasNext()) {
            Donee donee = iterator.next();
            // Check if the donee's categories contain the specified item
            Iterator<String> itemIterator = donee.getItems().getIterator();
            while (itemIterator.hasNext()) {
                if (itemIterator.next().equals(itemToFilter)) {
                    System.out.println(donee);
                    hasDonees = true;
                    break;
                }
            }
        }

        if (!hasDonees) {
            System.out.println("No donees found with item " + itemToFilter);
        }
    }
    
    public void reverseDonees() {
        doneeList.reverse();
        System.out.println("Donees list has been reversed.");
    }
    
    public void generateDoneeReport() {
        DictionaryInterface<String, Integer> itemCount = new HashedDictionary<>();
        Iterator<Donee> iterator = doneeList.getIterator();

        // Iterate through the list of Donees and count by item
        while (iterator.hasNext()) {
            Donee donee = iterator.next();
            LinkedList<String> categories = donee.getItems(); // Assuming getItems() returns a LinkedList<String>

            // Use the iterator for LinkedList<String> to iterate over categories
            Iterator<String> itemIterator = categories.getIterator();
            while (itemIterator.hasNext()) {
                String item = itemIterator.next();
                if (itemCount.contains(item)) {
                    int count = itemCount.getValue(item);
                    itemCount.replace(item, count + 1);
                } else {
                    itemCount.add(item, 1);
                }
            }
        }

        // Output the report
        Iterator<EntryInterface<String, Integer>> reportIterator = itemCount.getIterator();
        System.out.println("Donee count by item:");
        while (reportIterator.hasNext()) {
            EntryInterface<String, Integer> entry = reportIterator.next();
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }


    
    public void sortDonees() {
        System.out.println("Select sorting criteria:");
        System.out.println("1) Sort by Name");
        System.out.println("2) Sort by Group");
        System.out.println("3) Sort by Item");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        Comparator<Donee> comparator = null;

        switch (choice) {
            case 1:
                comparator = Comparator.comparing(Donee::getName);
                break;
            case 2:
                comparator = Comparator.comparing(Donee::getType);
                break;
            case 3:
                comparator = (d1, d2) -> {
                    LinkedList<String> categories1 = d1.getItems();
                    LinkedList<String> categories2 = d2.getItems();

                    // Compare by the first item in the list
                    String item1 = categories1.getEntry(1); // Assuming 1-based index
                    String item2 = categories2.getEntry(1); // Assuming 1-based index

                    if (item1 == null && item2 == null) {
                        return 0; // Both are null, treat as equal
                    } else if (item1 == null) {
                        return -1; // Null comes before non-null
                    } else if (item2 == null) {
                        return 1; // Non-null comes after null
                    } else {
                        return item1.compareTo(item2);
                    }
                };
                break;
            default:
                System.out.println("Invalid choice. No sorting applied.");
                return;
        }

        // Clone the original list
        LinkedListInterface<Donee> originalList = cloneList(doneeList);

        if (comparator != null) {
            // Sort the doneeList using the provided comparator
            doneeList.sort(comparator);

            // Display the sorted list
            System.out.println("Sorted Donees:");
            Iterator<Donee> sortedIterator = doneeList.getIterator();
            while (sortedIterator.hasNext()) {
                Donee donee = sortedIterator.next();
                System.out.println(donee);
            }

            // Restore the original list
            doneeList = originalList;
        }
    }

    /**
     * Creates a deep copy of the given list.
     */
    private LinkedListInterface<Donee> cloneList(LinkedListInterface<Donee> list) {
        LinkedListInterface<Donee> clonedList = new LinkedList<>();

        Iterator<Donee> iterator = list.getIterator();
        while (iterator.hasNext()) {
            clonedList.add(iterator.next());
        }

        return clonedList;
    }








}
