/**
 *
 * @author GAN WEI JIAN
 */
package dao;

import adt.ArrayList;
import adt.ListInterface;
import entity.Volunteer;

public class VolunteerDao {
    private static ListInterface<Volunteer> volunteerList = new ArrayList<>();

    public ListInterface<Volunteer> initialiseVolunteerList(){
        Volunteer volunteer1 = new Volunteer("Tan Char bel", "0123456789");
        Volunteer volunteer2 = new Volunteer("La Fei", "0123456789");
        Volunteer volunteer3 = new Volunteer("O K C", "0123456789");
        Volunteer volunteer4 = new Volunteer("Yang Mie Mie", "0123456789");
        Volunteer volunteer5 = new Volunteer("Yao Mateh", "0123456789");
        Volunteer volunteer6 = new Volunteer("Gam Jian Ge", "0123456789");
        Volunteer volunteer7 = new Volunteer("Chin Bao Bao", "0123456789");

        volunteerList.add(volunteer1);
        volunteerList.add(volunteer2);
        volunteerList.add(volunteer3);
        volunteerList.add(volunteer4);
        volunteerList.add(volunteer5);
        volunteerList.add(volunteer6);
        volunteerList.add(volunteer7);
        
        return volunteerList;
    }
}
