/**
 *
 * @author YEOH YIK YAO
 */
package adt;


import java.util.Iterator;
import java.util.function.Predicate;


public interface DictionaryInterface<K, V> {
    public V add(K key, V value);
    public V remove(K key);
    public V getValue(K key);
    public boolean replace(K key, V newValue);
    public boolean contains(K key);
    public boolean isEmpty();
    public boolean isFull();
    public int getSize();
    public void clear();
    public Iterator<EntryInterface<K,V>> getIterator();
    public HashedDictionary<K, V> filterByValue(Predicate<? super V> valuePredicate);
    public HashedDictionary<K, V> filterByKey(Predicate<? super K> keyPredicate);
} 

