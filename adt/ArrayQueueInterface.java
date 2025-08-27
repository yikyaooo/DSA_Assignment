/**
 *
 * @author GAN WEI JIAN
 */
package adt;

import entity.Event;
import java.util.Iterator;


public interface ArrayQueueInterface<T> extends Iterable<T>{
    
    void enqueue(T newEntry);

   T dequeue(); 
   
    T getFront(); 
    boolean isEmpty();

    void clear();

    public T get(int i);

    public int size();

//    public Iterator<Event> iterator();

}

