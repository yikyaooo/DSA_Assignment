/**
 *
 * @author SIM JIN YANG
 */
package adt;

import java.util.Iterator;
import java.util.Comparator;

public interface LinkedListInterface<T> {
    
    void sort(Comparator<T> comparator);
    
    public boolean add(T newEntry);
    
    public boolean add(int newPosition, T newEntry);
    
    public boolean remove(T entry);
    
    public void clear();
    
    public boolean replace(int givenPosition, T newEntry);
    
    public T getEntry(int givenPosition);
    
    public boolean contains(T anEntry);
    
    public int getNumberOfEntries();
    
    public boolean isEmpty();
    
    public boolean isFull();    
    
    public Iterator<T> getIterator();
    
    public void reverse();
    
    
}
