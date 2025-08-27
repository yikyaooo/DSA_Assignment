/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import adt.LinkedList;
import adt.LinkedListInterface;
import entity.Donee;

/**
 *
 * @author raphael
 */
public class DoneeDao {
    private static LinkedListInterface<Donee> doneeList = new LinkedList<>();
    
    public LinkedListInterface<Donee> initializeDoneeList(){
        
        // Create items as LinkedLists
        LinkedList<String> items1 = new LinkedList<>();
        items1.add("meat");

        LinkedList<String> items2 = new LinkedList<>();
        items2.add("clothes");

        LinkedList<String> items3 = new LinkedList<>();
        items3.add("water");
        items3.add("rice");

        LinkedList<String> items4 = new LinkedList<>();
        items4.add("coke");

        LinkedList<String> items5 = new LinkedList<>();
        items5.add("cash");

        LinkedList<String> items6 = new LinkedList<>();
        items6.add("fish");

        LinkedList<String> items7 = new LinkedList<>();
        items7.add("fish");

        // Create Donee objects with the new items lists
        Donee donee1 = new Donee("John Doe", "Individual", items1, "9876543210");
        Donee donee2 = new Donee("Jane Smith", "Family", items2, "9876543211");
        Donee donee3 = new Donee("Alice Johnson", "Family", items3, "9876543212");
        Donee donee4 = new Donee("Bob Brown", "Individual", items4, "9876543213");
        Donee donee5 = new Donee("Charlie Davis", "Individual", items5, "9876543214");
        Donee donee6 = new Donee("Diana Evans", "Organization", items6, "9876543215");
        Donee donee7 = new Donee("Edward Wilson", "Individual", items7, "9876543216");




        // Adding Donee objects to the list
        doneeList.add(donee1);
        doneeList.add(donee2);
        doneeList.add(donee3);
        doneeList.add(donee4);
        doneeList.add(donee5);
        doneeList.add(donee6);
        doneeList.add(donee7);
        
        return doneeList;
    }
}
