/**
 *
 * @author YEOH YIK YAO
 */
package adt;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;


public class HashedDictionary<K, V> implements DictionaryInterface<K, V>{

    private EntryInterface<K, V>[] hashTable;					     
    private int numberOfEntries;
    private int locationsUsed;	         
    private static final int DEFAULT_SIZE = 101; 		   
    private static final double MAX_LOAD_FACTOR = 0.5; 

    public HashedDictionary() {
        this(DEFAULT_SIZE); 
    } 

    public HashedDictionary(int tableSize) {
        int powerOfTwoSize = getNextPowerOfTwo(tableSize);

        hashTable = new EntryInterface[powerOfTwoSize];
        numberOfEntries = 0;
        locationsUsed = 0;
    } 

    public String toString() {
        String outputStr = "";
        for (int index = 0; index < hashTable.length; index++) {
            outputStr += String.format("%4d. ", index);
            if (hashTable[index] == null) {
              outputStr += "null\n";
            } else if (hashTable[index].isRemoved()) {
              outputStr += "notIn\n";
            } else {
              outputStr += hashTable[index].getKey() + " " + hashTable[index].getValue() + "\n";
            }
        } 
        
        outputStr += "\n";
        return outputStr;
    } 


    public V add(K key, V value) {
        V oldValue; 

        if (isHashTableTooFull()) {
          rehash();
        }

        int index = getHashIndex(key);
        index = probe(index, key); 

        if ((hashTable[index] == null) || hashTable[index].isRemoved()) { 
            hashTable[index] = new TableEntry<K, V>(key, value);
            numberOfEntries++;
            locationsUsed++;
            oldValue = null;
        } else { 
            oldValue = hashTable[index].getValue();
            hashTable[index].setValue(value);
        } 

        return oldValue;
    } 

    public V remove(K key) {
        V removedValue = null;

        int index = getHashIndex(key);
        index = locate(index, key);

        if (index != -1) {
          removedValue = hashTable[index].getValue();
          hashTable[index].setToRemoved();
          numberOfEntries--;
        } 

        return removedValue;
    } 

    public V getValue(K key) {
        V result = null;

        int index = getHashIndex(key);
        index = locate(index, key);

        if (index != -1) {
            result = hashTable[index].getValue(); 
        }

        return result;
    }


    private int probe(int index, K key) {
        boolean found = false;
        int removedStateIndex = -1; 
        int stepSize = secondaryHash(key); // Calculate the step size using the secondary hash function

        while (!found && (hashTable[index] != null)) {
            if (hashTable[index].isIn()) {
                if (key.equals(hashTable[index].getKey())) {
                    found = true; 
                } else {
                    index = (index + stepSize) % hashTable.length; // Use double hashing for probing
                }
            } else { 
                if (removedStateIndex == -1) {
                    removedStateIndex = index;
                }
                index = (index + stepSize) % hashTable.length; // Use double hashing for probing
            } 
        } 

        if (found || (removedStateIndex == -1)) {
            return index;            
        } else {
            return removedStateIndex; 
        }
    } 

    // The secondary hash function
    private int secondaryHash(K key) {
        int hashCode = key.hashCode();
        int prime = 7; // Choose a small prime number
        return prime - (hashCode % prime); // Ensures the step size is non-zero
    }


    private int locate(int index, K key) {
        boolean found = false;

        while (!found && (hashTable[index] != null)) {
            if (hashTable[index].isIn() && key.equals(hashTable[index].getKey())) {
                found = true; 
            } else {
                index = (index + 1) % hashTable.length;        
            }
        }

        int result = -1;
        if (found) {
            result = index;
        }

        return result;
    }

    public boolean contains(K key) {
        return getValue(key) != null;
    } 

    public boolean isEmpty() {
        return numberOfEntries == 0;
    } 

    public boolean isFull() {
        return false;
    } 

    public int getSize() {
        return numberOfEntries;
    } 

    public boolean replace(K key, V newValue) {
        int index = getHashIndex(key); 
        index = locate(index, key); 

        if (index != -1) { 
            hashTable[index].setValue(newValue); 
            return true; 
        }
        return false; 
    }

    public final void clear() {
        for (int index = 0; index < hashTable.length; index++) {
            hashTable[index] = null;
        }

        numberOfEntries = 0;
        locationsUsed = 0;
    } 

    private int getHashIndex(K key) {
        return key.hashCode() & (hashTable.length - 1);
    }

    private void rehash() {
        EntryInterface<K, V>[] oldTable = hashTable;
        int oldSize = hashTable.length;
        int newSize = getNextPowerOfTwo(oldSize + oldSize);
        hashTable = new TableEntry[newSize]; 
        numberOfEntries = 0;
        locationsUsed = 0;

        for (int index = 0; index < oldSize; index++) {
            if ((oldTable[index] != null) && oldTable[index].isIn()) {
                add(oldTable[index].getKey(), oldTable[index].getValue());
            }
        }  
        
    } 
    
    private boolean isHashTableTooFull() {
        return locationsUsed > MAX_LOAD_FACTOR * hashTable.length;
    } 

    private int getNextPowerOfTwo(int integer) {
        int powerOfTwo = 1;
        while (powerOfTwo < integer) {
            powerOfTwo *= 2;
        }
        return powerOfTwo;
    }
    

    public HashedDictionary<K, V> filterByKey(Predicate<? super K> keyPredicate) {
        HashedDictionary<K, V> filteredDictionary = new HashedDictionary<>(hashTable.length);

        for (int index = 0; index < hashTable.length; index++) {
            if (hashTable[index] != null && hashTable[index].isIn()) {
                K key = hashTable[index].getKey();
                if (keyPredicate.test(key)) {
                    filteredDictionary.add(key, hashTable[index].getValue());
                }
            }
        }

        return filteredDictionary;
    }

    
    public HashedDictionary<K, V> filterByValue(Predicate<? super V> valuePredicate) {
        HashedDictionary<K, V> filteredDictionary = new HashedDictionary<>(hashTable.length);

        for (int index = 0; index < hashTable.length; index++) {
            if (hashTable[index] != null && hashTable[index].isIn()) {
                V value = hashTable[index].getValue();
                if (valuePredicate.test(value)) {
                    filteredDictionary.add(hashTable[index].getKey(), value);
                }
            }
        }

        return filteredDictionary;
    }

   

   private class TableEntry<S, T> implements EntryInterface<S,T>{
        private S key;
        private T value;
        private boolean inTable;

        public TableEntry(S searchKey, T dataValue) {
            key = searchKey;
            value = dataValue;
            inTable = true;
        } 

        public S getKey() {
            return key;
        } 

        public T getValue() {
            return value;
        } 

        public void setValue(T newValue) {
            value = newValue;
        } 

        public boolean isIn() {
            return inTable;
        } 

        public boolean isRemoved() { 
            return !inTable;
        } 

        public void setToRemoved() {
            key = null;
            value = null;
            inTable = false;
        } 

        public void setToIn() {
            inTable = true;
        } 
    } 

  
    public Iterator<EntryInterface<K,V>> getIterator(){
        return new HashedDictionaryIterator();
    }
    
    private class HashedDictionaryIterator implements Iterator<EntryInterface<K, V>>{
        private int currentIndex = 0;
        
        public HashedDictionaryIterator(){
          currentIndex = 0;
        }

        @Override
        public boolean hasNext() {
            while (currentIndex < hashTable.length && (hashTable[currentIndex] == null || hashTable[currentIndex].isRemoved())) {
                currentIndex++;
            }
            return currentIndex < hashTable.length;
        }

        @Override
        public EntryInterface<K, V> next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements");
            }
            return hashTable[currentIndex++];
        }
    }
} 
