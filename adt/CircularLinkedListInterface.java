/**
 *
 * @author raphael
 */
package adt;

import java.util.Iterator;


public interface CircularLinkedListInterface<T> {
    public boolean add(T newEntry);
    
    public boolean add(int newPosition, T newEntry);
    
    public T remove(int givenPosition);
    
    public boolean delete(T entry);
    
    public void clear();
    
    public boolean replace(int givenPosition, T newEntry);
    
    public T getEntry(int givenPosition);
    
    public boolean contains(T anEntry);
    
    public int getNumberOfEntries();
    
    public boolean isEmpty();
    
    public boolean isFull();    
    
    public Iterator<T> getIterator();
}
