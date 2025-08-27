/**
 *
 * @author SIM JIN YANG
 */
package adt;
import java.util.Iterator;
import java.util.Comparator;


public class LinkedList<T> implements LinkedListInterface<T>{
    private Node firstNode; // reference to first node
    private int numberOfEntries;
    
    public LinkedList(){
        clear();
    }
    
    @Override
    public boolean add(T newEntry){
        Node newNode = new Node(newEntry); //create the new node
        
        if(isEmpty()){
           firstNode = newNode;
        }else {
            Node currentNode = firstNode;
            while (currentNode.next != null){
                currentNode = currentNode.next;
            }
            currentNode.next = newNode; // make last node reference new node
        }
        numberOfEntries++;
        return true;
    }
    
    @Override
    public boolean add(int newPosition, T newEntry){
        boolean isSuccessful = true;
        
        if ((newPosition >= 1) && (newPosition <= numberOfEntries + 1)){
            Node newNode = new Node(newEntry);
            
            if (isEmpty() || (newPosition == 1)){
                newNode.next = firstNode;
                firstNode = newNode;
            }else{
                Node nodeBefore = firstNode;
                for (int i = 1; i < newPosition - 1; ++i){
                    nodeBefore = nodeBefore.next; // advance nodeBefore to its nextNode
                    
                }
                newNode.next = nodeBefore.next;
                nodeBefore.next = newNode;
            }
            
            
        numberOfEntries++;
        }else{
            isSuccessful = false;
        }
        return isSuccessful;
        
    }
    
    @Override
    public boolean remove(T entry) {
        boolean isRemoved = false;

        // Handle the case where the list is empty
        if (isEmpty()) {
            return isRemoved;
        }

        // Special case for removing the first node
        if (firstNode.data.equals(entry)) {
            firstNode = firstNode.next;
            numberOfEntries--;
            isRemoved = true;
        } else {
            // Find the node to remove
            Node currentNode = firstNode;
            Node nodeBefore = null;

            while (currentNode != null && !currentNode.data.equals(entry)) {
                nodeBefore = currentNode;
                currentNode = currentNode.next;
            }

            // If the node is found
            if (currentNode != null) {
                nodeBefore.next = currentNode.next;
                numberOfEntries--;
                isRemoved = true;
            }
        }

        return isRemoved;
    }
        
    @Override
    public void clear(){
        firstNode = null;
        numberOfEntries = 0;
    }
        
    @Override
    public boolean replace(int givenPosition, T newEntry){
        boolean isSuccessful = true;
        
        if ((givenPosition >= 1) && (givenPosition <= numberOfEntries)){
            Node currentNode = firstNode;
            for (int i = 0; i < givenPosition - 1; ++i){
                currentNode = currentNode.next;	
            }
            currentNode.data = newEntry;
        }else{
            isSuccessful = false;
        }
        return isSuccessful;
    }
        
    @Override
    public T getEntry(int givenPosition){
        T result = null;
        
        if ((givenPosition >= 1) && (givenPosition <= numberOfEntries)){
            Node currentNode = firstNode;
            for (int i = 0; i < givenPosition - 1; ++i){
                currentNode = currentNode.next;	
            }
            result = currentNode.data;
        }
        return result;
    }
        
    @Override
    public boolean contains(T anEntry){
        boolean found = false;
        Node currentNode = firstNode;
        
        while(!found && (currentNode != null)){
          if (anEntry.equals(currentNode.data)){
                found = true;
            }else{
                currentNode = currentNode.next;
            }  
        }
       return found;
    }
        
    @Override
    public int getNumberOfEntries(){
        return numberOfEntries;
    }
        
    @Override
    public boolean isEmpty(){
        boolean result;
        result = numberOfEntries == 0;
        return result;
    }
        
    @Override
    public boolean isFull(){
        return false;
    }

    
    private class Node{
        private T data; 
        private Node next;
        
        private Node(T data){
            this.data = data;
            next = null;
        }
        
        private Node(T data, Node node){
            this.data = data;
            this.next = next;
        }
    
    }
    
    @Override
    public Iterator<T> getIterator(){
        return new LinkedListIterator();
    }
    
    private class LinkedListIterator implements Iterator<T> {
        private Node currentNode;

        private LinkedListIterator() {
            currentNode = firstNode;
        }

        @Override
        public boolean hasNext() {
            return currentNode != null;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            T data = currentNode.data;
            currentNode = currentNode.next;
            return data;
        }
    }
    
    public void reverse(){
        Node prev = null;
        Node current = firstNode;
        Node next = null;
    
        while (current != null) {
            next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }

        firstNode = prev;
    }
    
    @Override
    public void sort(Comparator<T> comparator) {
        if (comparator == null) {
            throw new IllegalArgumentException("Comparator cannot be null");
        }

        if (numberOfEntries > 1) {
            firstNode = mergeSort(firstNode, comparator);
        }
    }
    
    public void mergeSort(Comparator<T> comparator) {
        if (comparator == null) {
            throw new IllegalArgumentException("Comparator cannot be null");
        }

        if (numberOfEntries > 1) {
            firstNode = mergeSort(firstNode, comparator);
        }
    }

    private Node mergeSort(Node head, Comparator<T> comparator) {
        if (head == null || head.next == null) {
            return head;
        }

        Node middle = getMiddle(head);
        Node nextOfMiddle = middle.next;
        middle.next = null;

        Node left = mergeSort(head, comparator);
        Node right = mergeSort(nextOfMiddle, comparator);

        return merge(left, right, comparator);
    }

    private Node getMiddle(Node head) {
        if (head == null) return head;

        Node slow = head;
        Node fast = head;

        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow;
    }

    private Node merge(Node left, Node right, Comparator<T> comparator) {
        if (left == null) return right;
        if (right == null) return left;

        Node result;

        if (comparator.compare(left.data, right.data) <= 0) {
            result = left;
            result.next = merge(left.next, right, comparator);
        } else {
            result = right;
            result.next = merge(left, right.next, comparator);
        }

        return result;
    }

    
    @Override
    public String toString() {
        StringBuilder listString = new StringBuilder("[");
        Iterator<T> iterator = getIterator();
        while (iterator.hasNext()) {
            listString.append(iterator.next().toString());
            if (iterator.hasNext()) {
                listString.append(", ");
            }
        }
        listString.append("]");
        return listString.toString();
    }

    
    
    

    
    
}       
