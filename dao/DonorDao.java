/**
 *
 * @author OOI KERR CHII
 */
package dao;

import adt.ArrayList;
import adt.ListInterface;
import entity.Donor;


public class DonorDao {
    private static ListInterface<Donor> donorList = new ArrayList<>();
    public ListInterface<Donor> initializeDonorList(){
        Donor donor1 = new Donor("YYY","private", "0123456789");
        Donor donor2 = new Donor("AAA","private", "0123456789");
        Donor donor3 = new Donor("BBB","private", "0123456789");
        Donor donor4 = new Donor("CCC","private", "0123456789");
        Donor donor5 = new Donor("DDD","private", "0123456789");
        Donor donor6 = new Donor("EEE","private", "0123456789");
        Donor donor7 = new Donor("FFF","private", "0123456789");

        donorList.add(donor1);
        donorList.add(donor2);
        donorList.add(donor3);
        donorList.add(donor4);
        donorList.add(donor5);
        donorList.add(donor6);
        donorList.add(donor7);
        return donorList;
    }
}
